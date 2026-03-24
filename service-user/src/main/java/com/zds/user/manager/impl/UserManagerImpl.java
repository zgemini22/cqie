package com.zds.user.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.constant.*;
import com.zds.biz.constant.user.*;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.TokenModel;
import com.zds.user.dao.TblOrganizationDao;
import com.zds.user.dao.TblRoleDao;
import com.zds.user.dao.TblUserDao;
import com.zds.user.dao.TblUserLoginDao;
import com.zds.user.manager.UserManager;
import com.zds.user.po.TblOrganization;
import com.zds.user.po.TblRole;
import com.zds.user.po.TblUser;
import com.zds.user.po.TblUserLogin;
import com.zds.user.service.TokenService;
import com.zds.user.util.SafeHashGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserManagerImpl implements UserManager {

    @Autowired
    private TblUserDao userDao;

    @Autowired
    private TblRoleDao roleDao;

    @Autowired
    private TblOrganizationDao organizationDao;

    @Autowired
    private TblUserLoginDao userLoginDao;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    /**
     * 是否开启默认初始密码
     */
    @Value("${user-settings.isDefaultPwd}")
    private boolean isDefaultPwd;

    /**
     * 默认初始密码
     */
    @Value("${user-settings.defaultPwd}")
    private String defaultPwd;

    /**
     * 是否允许同时登录
     */
    @Value("${user-settings.sameTimeLogin}")
    private boolean sameTimeLogin;

    @Override
    public Long addSystemUser(Long organizationId, Long roleId, String loginName, String password, String phone, String name, Boolean adminFlag, UserTypeEnum userTypeEnum) {
        //新增用户
        TblUser user = TblUser.builder()
                .name(name)
                .loginName(loginName)
                .phone(phone)
                .userType(userTypeEnum.getKey())
                .adminFlag(adminFlag)
                .userStatus(UserStatusEnum.ENABLE.getKey())
                .organizationId(organizationId)
                .roleId(roleId)
                .build();
        int count = userDao.insert(user);
        if (count == 1) {
            if (StringUtils.isEmpty(password)) {
                password = getNewPassword();
            }
            //保存加密后的密码
            userDao.updateById(TblUser.builder()
                    .id(user.getId())
                    .hashedPassword(SafeHashGenerator.getStretchedPassword(password, user.getId().toString()))
                    .build());
        }
        return user.getId();
    }

    @Override
    public boolean updateSystemUser(Long userId, Long roleId, String loginName, String password, String phone, String name) {
        String hashedPassword = StringUtils.isNotEmpty(password) ? SafeHashGenerator.getStretchedPassword(password, userId.toString()) : null;
        int count = userDao.updateById(TblUser.builder()
                .id(userId)
                .name(name)
                .loginName(loginName)
                .phone(phone)
                .roleId(roleId)
                .hashedPassword(hashedPassword)
                .build());
        return count == 1;
    }

    /**
     * 生成明文密码
     */
    private String getNewPassword() {
        String newPassword;
        if (isDefaultPwd && StringUtils.isNotEmpty(defaultPwd)) {
            newPassword = defaultPwd;
        } else {
            newPassword = RandomStringUtils.randomAlphanumeric(10);
        }
        return newPassword;
    }

    @Override
    public String getUserToken(TblUser user, String pwd, TokenTypeEnum tokenTypeEnum) {
        //检查用户状态
        if (user.getUserStatus().equals(UserStatusEnum.DISABLE.getKey())) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //检查是否锁定账户
//        if (user.getAccountLocked()) {
//            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
//        }
        if (StringUtils.isNotEmpty(pwd)) {
            checkUserPassword(user, pwd);
        }
        //检查用户所属角色的状态
        checkUserRoleStatus(user.getRoleId());
        //检查用户所属单位的状态
        checkUserOrgStatus(user.getOrganizationId());
        //查询是否存在生效中的token
        TokenModel model = tokenService.getTokenByUserId(user);
        if (!sameTimeLogin || model == null || !model.getRoleId().equals(user.getRoleId())) {
            //创建token
            model = tokenService.createToken(user, tokenTypeEnum);
        }
        return model.getToken();
    }

    /**
     * 验证密码是否正确
     */
    private void checkUserPassword(TblUser user, String pwd) {
        boolean passwordCorrect = SafeHashGenerator.getStretchedPassword(pwd, user.getId().toString()).equals(user.getHashedPassword());
        if (!passwordCorrect) {
            //累计登录失败次数
            addLoginFailCount(user.getLoginName());
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
    }

    private void checkUserRoleStatus(Long roleId) {
        if (roleId == null || roleId == 0L) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        TblRole role = roleDao.selectById(roleId);
        if (RoleStatusEnum.DISABLE.getKey().equals(role.getRoleStatus())) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
    }

    private void checkUserOrgStatus(Long orgId) {
        if (orgId == null) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        if (orgId.equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode())) {
            return;
        }
        TblOrganization organization = organizationDao.selectById(orgId);
        if (OrganizationStatusEnum.DISABLE.getKey().equals(organization.getOrganizationStatus())) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        if (OrganizationStatusEnum.FROZEN.getKey().equals(organization.getOrganizationStatus())) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
    }

    @Override
    public void checkLoginName(String loginName, Long userId) {
        LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblUser::getDeleted, false)
                .eq(TblUser::getLoginName, loginName);
        List<TblUser> list1 = userDao.selectList(wrapper);
        if (list1.size() > 0 && !list1.get(0).getId().equals(userId)) {
            throw new BaseException("登录名已被占用");
        }
    }

    @Override
    public void checkPhone(String phone, Long userId) {
        LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblUser::getDeleted, false)
                .eq(TblUser::getPhone, phone);
        List<TblUser> list2 = userDao.selectList(wrapper);
        if (list2.size() > 0 && !list2.get(0).getId().equals(userId)) {
            throw new BaseException("手机号已被占用");
        }
    }

    @Async
    @Override
    public void cleanUserTokenByOrg(Long organizationId) {
        LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblUser::getOrganizationId, organizationId);
        List<TblUser> list = userDao.selectList(wrapper);
        cleanUserTokenByUser(list.stream().map(TblUser::getId).collect(Collectors.toList()));
    }

    @Async
    @Override
    public void cleanUserTokenByOrg(List<Long> organizationIds) {
        LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(TblUser::getOrganizationId, organizationIds);
        List<TblUser> list = userDao.selectList(wrapper);
        cleanUserTokenByUser(list.stream().map(TblUser::getId).collect(Collectors.toList()));
    }

    @Async
    @Override
    public void cleanUserTokenByUser(Long userId) {
        tokenService.deleteToken(userId);
    }

    @Override
    public void cleanUserTokenByUser(List<Long> userIds) {
        tokenService.deleteToken(userIds);
    }

    @Override
    public Map<Long, String> getUserNameMap(List<Long> ids) {
        Map<Long, String> map = new HashMap<>();
        if (ids.size() > 0) {
            LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(TblUser::getCreateTime)
                    .in(TblUser::getId, ids);
            map = userDao.selectList(wrapper).stream()
                    .collect(Collectors.toMap(TblUser::getId, TblUser::getName, (a, b) -> b));
        }
        return map;
    }

    @Override
    public Map<Long, TblUser> getUserMap(List<Long> ids) {
        Map<Long, TblUser> map = new HashMap<>();
        if (ids.size() > 0) {
            LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(TblUser::getCreateTime)
                    .in(TblUser::getId, ids);
            map = userDao.selectList(wrapper).stream()
                    .collect(Collectors.toMap(TblUser::getId, x -> x, (a, b) -> b));
        }
        return map;
    }

    @Override
    public void addLoginRecord(Long userId, LoginWayEnum loginWayEnum) {
        userLoginDao.insert(TblUserLogin.builder()
                .userId(userId)
                .loginTime(Calendar.getInstance().getTime())
                .loginIp(threadLocalUtil.getIpAddr())
                .loginWay(loginWayEnum.getKey())
                .build());
    }

    @Override
    public void addLoginFailCount(String loginName) {
        //累计登录失败次数
        userDao.addLoginFailCount(loginName);
        TblUser po = userDao.selectOne(TblUser.getWrapper().eq(TblUser::getLoginName, loginName));
        if (po != null && po.getLoginFailCount() != null && po.getLoginFailCount() >= 5 && !po.getAccountLocked()) {
            //锁定用户
            userDao.updateById(TblUser.builder()
                    .id(po.getId())
                    .accountLocked(true)
                    .build());
            //清理redis中可能存在的用户token
            tokenService.deleteToken(po.getId());
        }
    }
}
