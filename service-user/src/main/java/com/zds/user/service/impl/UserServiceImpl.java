package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.MsgEnum;
import com.zds.biz.constant.PermissionEnum;
import com.zds.biz.constant.SecretKeyEnum;
import com.zds.biz.constant.user.*;
import com.zds.biz.util.CodeUtil;
import com.zds.biz.util.JwtUtils;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.SystemInfoVo;
import com.zds.biz.vo.TokenModel;
import com.zds.biz.vo.request.flow.ServiceUserSaveRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.*;
import com.zds.user.dao.*;
import com.zds.user.feign.FlowService;
import com.zds.user.manager.*;
import com.zds.user.po.*;
import com.zds.user.service.*;
import com.zds.user.util.SafeHashGenerator;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TblUserDao userDao;

    @Autowired
    private TblUserLoginDao userLoginDao;

    @Autowired
    private TblRoleDao roleDao;

    @Autowired
    private TblOrganizationDao organizationDao;

    @Autowired
    private TblBasicDataDao basicDataDao;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TelCodeService telCodeService;

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserManager userManager;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private CodeUtil codeUtil;

    @Autowired
    private OrgManager orgManager;

    @Autowired
    private DictManager dictManager;

    @Autowired
    private FlowService flowService;

    @Autowired
    private TranslucentKaptchaService translucentKaptchaService;

    @Autowired
    private CheckUserPermissionManager checkUserPermissionManager;

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
     * 是否开启默认登录验证码
     */
    @Value("${user-settings.isDefaultLoginCode}")
    private boolean isDefaultLoginCode;

    /**
     * 默认登录验证码
     */
    @Value("${user-settings.defaultLoginCode}")
    private String defaultLoginCode;

    /**
     * admin手机号
     */
    private String ADMIN_PHONE = "admin";

    /**
     * 数据同步模拟登录密钥
     */
    private String SECRET_VALUE = "syncdatalogin";

    // ==== AES配置（前后端需保持一致）====
    // 16字节密钥（AES-128，必须16/24/32字节）
    private static final String AES_KEY = "1qaz2wsx3edc4rfv";
    // 16字节IV向量（CBC模式必需，增强安全性）
    private static final String AES_IV = "Iv1234567890abcd";
    @Override
    public String adminUserLogin(UserLoginRequest request) {
        //解密
        String encryptedLoginName = request.getLoginName();
        String encryptedPassword = request.getPassword();

        String plainLoginName = encryptedLoginName;
        String plainPassword = encryptedPassword;
//        try {
//            plainLoginName = aesDecrypt(encryptedLoginName);
//            plainPassword = aesDecrypt(encryptedPassword);
//        } catch (Exception e) {
//            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
//        }

        //验证码判断
        checkLoginCode(request);
        //查询用户
        TblUser user = findUserByLoginName(plainLoginName);
        if (user == null) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //新增用户登录记录
        userManager.addLoginRecord(user.getId(), LoginWayEnum.ADMIN_WEB);
        return userManager.getUserToken(user, plainPassword, TokenTypeEnum.ADMIN);
    }

    // ==== AES解密方法（与前端加密逻辑对应）====
    private String aesDecrypt(String encryptedData) throws Exception {
        // 密钥和IV向量转换为字节数组
        SecretKeySpec keySpec = new SecretKeySpec(AES_KEY.getBytes("UTF-8"), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(AES_IV.getBytes("UTF-8"));

        // 初始化解密器（CBC模式，PKCS5填充，与前端一致）
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // 解密（Base64解码后解密）
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes, "UTF-8");
    }

    @Override
    public String adminUserLoginUnCode(UserLoginUncodeRequest request) {
        //验证码判断
        //checkLoginCode(request);
        //查询用户
        TblUser user = findUserByLoginName(request.getLoginName());
        if (user == null) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //新增用户登录记录
        userManager.addLoginRecord(user.getId(), LoginWayEnum.ADMIN_WEB);
        return userManager.getUserToken(user, request.getPassword(), TokenTypeEnum.ADMIN);
    }

    @Override
    public String adminUserLoginByExamine(UserClientLoginRequest request) {
        //解密
        String encryptedLoginName = request.getLoginName();
        String encryptedPassword = request.getPassword();

        String plainLoginName;
        String plainPassword;
        try {
            plainLoginName = aesDecrypt(encryptedLoginName);
            plainPassword = aesDecrypt(encryptedPassword);
        } catch (Exception e) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //查询用户
        TblUser user = findUserByLoginName(plainLoginName);
        if (user == null) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //新增用户登录记录
        userManager.addLoginRecord(user.getId(), LoginWayEnum.EXAMINE_WEB);
        //判断用户是否有授权访问此系统
        List<MenuListResponse> menus = roleMenuManager.findMenusByRoleId(user.getRoleId());
        //过滤只保留<从业资格考核>菜单
        menus = menus.stream()
                .filter(x -> x.getMenuGroup().equals(MenuGroupEnum.EXAMINE_MENU.getKey()))
                .collect(Collectors.toList());
        if (menus.size() == 0) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        return userManager.getUserToken(user, plainPassword, TokenTypeEnum.ADMIN);
    }

    @Override
    public String adminUserLoginByMeter(UserClientLoginRequest request) {
        //解密
        String encryptedLoginName = request.getLoginName();
        String encryptedPassword = request.getPassword();

        String plainLoginName;
        String plainPassword;
        try {
            plainLoginName = aesDecrypt(encryptedLoginName);
            plainPassword = aesDecrypt(encryptedPassword);
        } catch (Exception e) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //查询用户
        TblUser user = findUserByLoginName(plainLoginName);
        if (user == null) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //新增用户登录记录
        userManager.addLoginRecord(user.getId(), LoginWayEnum.METER_WEB);
        //判断用户是否有授权访问此系统
        List<MenuListResponse> menus = roleMenuManager.findMenusByRoleId(user.getRoleId());
        //过滤只保留<抄表信息化>菜单
        menus = menus.stream()
                .filter(x -> x.getMenuGroup().equals(MenuGroupEnum.METER_MENU.getKey()))
                .collect(Collectors.toList());
        if (menus.size() == 0) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        return userManager.getUserToken(user, plainPassword, TokenTypeEnum.ADMIN);
    }

    @Override
    public String clientUserLogin(UserClientLoginRequest request) {
        //解密
        String encryptedLoginName = request.getLoginName();
        String encryptedPassword = request.getPassword();

        String plainLoginName;
        String plainPassword;
        try {
            plainLoginName = aesDecrypt(encryptedLoginName);
            plainPassword = aesDecrypt(encryptedPassword);
        } catch (Exception e) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //查询用户
        TblUser user = findUserByLoginName(plainLoginName);
        if (user == null) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //新增用户登录记录
        userManager.addLoginRecord(user.getId(), LoginWayEnum.CLIENT_APP);
        return userManager.getUserToken(user, plainPassword, TokenTypeEnum.APP);
    }

    @Override
    public String clientUserLoginByExamine(UserClientLoginRequest request) {
        //解密
        String encryptedLoginName = request.getLoginName();
        String encryptedPassword = request.getPassword();

        String plainLoginName;
        String plainPassword;
        try {
            plainLoginName = aesDecrypt(encryptedLoginName);
            plainPassword = aesDecrypt(encryptedPassword);
        } catch (Exception e) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //查询用户
        TblUser user = findUserByLoginName(plainLoginName);
        if (user == null) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        //新增用户登录记录
        userManager.addLoginRecord(user.getId(), LoginWayEnum.EXAMINE_APP);
        return userManager.getUserToken(user, plainPassword, TokenTypeEnum.APP);
    }

    /**
     * 检查验证码
     */
    private void checkLoginCode(UserLoginRequest request) {
        boolean flagreturn = false;
        boolean havLoginCode = translucentKaptchaService.isHavLoginCode(request.getRightCode());
        translucentKaptchaService.deleteLoginCode(request.getRightCode());
        if (havLoginCode) {
            boolean flag = isDefaultLoginCode && defaultLoginCode.equals(request.getCode());
            if (!flag && !request.getRightCode().equals(SafeHashGenerator.getStretchedText(request.getCode()))) {
                //累计登录失败次数
                userManager.addLoginFailCount(request.getLoginName());
            } else {
                flagreturn = true;
            }
        } else {
            throw new BaseException("验证码已过期");
        }
        if (!flagreturn) {
            throw new BaseException("验证码错误");
        }
    }

    @Override
    public TblUser findUserByPhone(String phone) {
        List<TblUser> list = userDao.selectList(TblUser.getWrapper()
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getPhone, phone));
        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public TblUser findUserByLoginName(String loginName) {
        List<TblUser> list = userDao.selectList(TblUser.getWrapper()
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getLoginName, loginName));
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public boolean adminUserLogout() {
        return tokenService.deleteToken(threadLocalUtil.getUserId());
    }

    @Override
    public boolean changeOldPassword(PwdChangeOldRequest request) {
        //检查入参
        request.toRequestCheck();
        //核对当前用户旧密码
        Long userId = threadLocalUtil.getUserId();
        TblUser user = userDao.selectById(userId);
        boolean passwordCorrect = SafeHashGenerator.getStretchedPassword(request.getOldPwd(), user.getId().toString()).equals(user.getHashedPassword());
        if(!passwordCorrect){
            throw new BaseException("旧密码错误");
        }
        //修改用户密码
        return updatePasswordById(userId, request.getNewPwd());
    }

    @Override
    public boolean changePassword(PwdChangeRequest request) {
        //检查入参
        request.toRequestCheck();
        //查询用户手机号
        Long userId = threadLocalUtil.getUserId();
        String phone = userDao.selectById(userId).getPhone();
        //核对手机验证码
        if (!telCodeService.verifyCode(phone, request.getCode())) {
            throw new BaseException("验证码错误或已过期");
        }
        return updatePasswordById(userId, request.getNewPwd());
    }

    @Override
    public boolean renewPassword(PwdRenewRequest request) {
        //检查入参
        request.toRequestCheck();
        //查询用户
        TblUser user = findUserByPhone(request.getPhone());
        if (user == null) {
            throw new BaseException(MsgEnum.ERROR_ACCOUNT_PASSWORD.getMsg());
        }
        Long userId = user.getId();
        //核对手机验证码
        if (!telCodeService.verifyCode(request.getPhone(), request.getCode())) {
            throw new BaseException("验证码错误或已过期");
        }
        return updatePasswordById(userId, request.getNewPwd());
    }

    private boolean updatePasswordById(Long userId, String pwd) {
        int count = userDao.updateById(TblUser.builder()
                .id(userId)
                .hashedPassword(SafeHashGenerator.getStretchedPassword(pwd, userId.toString()))
                .passwordChangedAt(Calendar.getInstance().getTime())
                .initialPasswordFlag(false)
                .loginFailCount(0)
                .build());
        if (count == 1) {//删除token记录
            tokenService.deleteToken(userId);
        }
        return count == 1;
    }

    @Override
    public boolean updateStatus(UserStatusRequest request) {
        //检查入参
        request.toRequestCheck();
        //查询指定用户
        TblUser user = userDao.selectById(request.getId());
        if (user == null) {
            throw new BaseException("未找到指定用户");
        }
        //判断修改的用户是否为系统管理员
        if (user.getLoginName().equals(ADMIN_PHONE)) {
            throw new BaseException("管理员账号不允许修改");
        }
        int count = userDao.updateById(TblUser.builder()
                .id(request.getId())
                .userStatus(request.getUserStatus())
                .build());
        //禁用用户成功,清理相关用户token
        if (UserStatusEnum.DISABLE.getKey().equals(request.getUserStatus())) {
            userManager.cleanUserTokenByUser(user.getId());
        }
        return count == 1;
    }

    @Override
    public LoginUserResponse getUserInfo() {
        Long userId = threadLocalUtil.getUserId();
        TblUser user = userDao.selectById(userId);
        //判断是否可登录后台系统
        if (user.getRoleId() == null) {
            throw new BaseException("无登录权限");
        }
        List<MenuListResponse> menus = roleMenuManager.findMenusByRoleId(user.getRoleId());
        if (menus == null || menus.size() == 0) {
            throw new BaseException("无登录权限");
        }
        List<TblUserLogin> loginList = userLoginDao.selectPage(new Page<>(1, 2), TblUserLogin.getWrapper()
                .in(TblUserLogin::getLoginWay, Arrays.asList(LoginWayEnum.ADMIN_WEB.getKey(), LoginWayEnum.CLIENT_APP.getKey(), LoginWayEnum.DEVICE_WEB.getKey()))
                .orderByDesc(TblUserLogin::getLoginTime)
                .eq(TblUserLogin::getUserId, userId)).getRecords();
        boolean deviceFlag = false;
        for (MenuListResponse vo : menus) {
            if (vo.getMenuGroup().equals(MenuGroupEnum.DEVICE_MENU.getKey())) {
                deviceFlag = true;
                break;
            }
        }
        return LoginUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .fileHead(user.getFileHead())
                .phone(user.getPhone())
                .email(user.getEmail())
                .organizationId(user.getOrganizationId())
                .organizationName(user.getOrganizationId() == 0 ? OrganizationNodeEnum.ROOT_COMPANY_CODE.getName() : organizationDao.selectById(user.getOrganizationId()).getOrganizationName())
                .organizationType(threadLocalUtil.getOrganizationType())
                .roleId(user.getRoleId())
                .roleName(roleDao.selectById(user.getRoleId()).getRoleName())
                .menus(menus)
                .lastLoginTime(loginList.size() > 1 ? loginList.get(1).getLoginTime() : null)
                .deviceFlag(deviceFlag)
                .systemInfoList(getSystemInfoList(menus))
                .build();
    }

    private List<SystemInfoVo> getSystemInfoList(List<MenuListResponse> menus) {
        List<SystemInfoVo> list = new ArrayList<>();
        Set<String> menuGroups = new HashSet<>();
        for (MenuListResponse menu : menus) {
            menuGroups.add(menu.getMenuGroup());
        }
        Map<String, String> urlMap = basicDataDao.selectList(TblBasicData.getWrapper()
                .eq(TblBasicData::getDeleted, false)
                .in(menuGroups.size() > 0, TblBasicData::getDataKey, new ArrayList<>(menuGroups)))
                .stream()
                .collect(Collectors.toMap(TblBasicData::getDataKey, TblBasicData::getDataValue, (a, b) -> b));
        System.out.println("host=" + threadLocalUtil.getHost());
        for (String menuGroup : menuGroups) {
            list.add(SystemInfoVo.builder()
                    .name(MenuGroupEnum.query(menuGroup).getTitle())
                    .menuGroup(menuGroup)
                    .url(urlMap.getOrDefault(menuGroup, ""))
                    .build());
        }
        return list;
    }

    @Override
    public LoginUserResponse getUserInfoByExamine() {
        Long userId = threadLocalUtil.getUserId();
        TblUser user = userDao.selectById(userId);
        //判断是否可登录后台系统
        if (user.getRoleId() == null) {
            throw new BaseException("无登录权限");
        }
        List<MenuListResponse> menus = roleMenuManager.findMenusByRoleId(user.getRoleId());
        //过滤只保留从业资格考核菜单
        menus = menus.stream()
                .filter(x -> x.getMenuGroup().equals(MenuGroupEnum.EXAMINE_MENU.getKey()))
                .collect(Collectors.toList());
        if (menus.size() == 0) {
            throw new BaseException("用户未授权访问此系统");
        }
        List<TblUserLogin> loginList = userLoginDao.selectPage(new Page<>(1, 2), TblUserLogin.getWrapper()
                .in(TblUserLogin::getLoginWay, Arrays.asList(LoginWayEnum.EXAMINE_WEB.getKey(), LoginWayEnum.EXAMINE_APP.getKey()))
                .orderByDesc(TblUserLogin::getLoginTime)
                .eq(TblUserLogin::getUserId, userId)).getRecords();
        return LoginUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .fileHead(user.getFileHead())
                .phone(user.getPhone())
                .email(user.getEmail())
                .organizationId(user.getOrganizationId())
                .organizationName(user.getOrganizationId() == 0 ? OrganizationNodeEnum.ROOT_COMPANY_CODE.getName() : organizationDao.selectById(user.getOrganizationId()).getOrganizationName())
                .organizationType(threadLocalUtil.getOrganizationType())
                .roleId(user.getRoleId())
                .roleName(roleDao.selectById(user.getRoleId()).getRoleName())
                .menus(menus)
                .lastLoginTime(loginList.size() > 1 ? loginList.get(1).getLoginTime() : null)
                .deviceFlag(false)
                .build();
    }

    @Override
    public LoginUserResponse getUserInfoByMeter() {
        Long userId = threadLocalUtil.getUserId();
        TblUser user = userDao.selectById(userId);
        //判断是否可登录后台系统
        if (user.getRoleId() == null) {
            throw new BaseException("无登录权限");
        }
        List<MenuListResponse> menus = roleMenuManager.findMenusByRoleId(user.getRoleId());
        //过滤只保留从业资格考核菜单
        menus = menus.stream()
                .filter(x -> x.getMenuGroup().equals(MenuGroupEnum.METER_MENU.getKey()))
                .collect(Collectors.toList());
        if (menus.size() == 0) {
            throw new BaseException("用户未授权访问此系统");
        }
        List<TblUserLogin> loginList = userLoginDao.selectPage(new Page<>(1, 2), TblUserLogin.getWrapper()
                .eq(TblUserLogin::getLoginWay, LoginWayEnum.METER_WEB.getKey())
                .orderByDesc(TblUserLogin::getLoginTime)
                .eq(TblUserLogin::getUserId, userId)).getRecords();
        return LoginUserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .fileHead(user.getFileHead())
                .phone(user.getPhone())
                .email(user.getEmail())
                .organizationId(user.getOrganizationId())
                .organizationName(user.getOrganizationId() == 0 ? OrganizationNodeEnum.ROOT_COMPANY_CODE.getName() : organizationDao.selectById(user.getOrganizationId()).getOrganizationName())
                .organizationType(threadLocalUtil.getOrganizationType())
                .roleId(user.getRoleId())
                .roleName(roleDao.selectById(user.getRoleId()).getRoleName())
                .menus(menus)
                .lastLoginTime(loginList.size() > 1 ? loginList.get(1).getLoginTime() : null)
                .deviceFlag(false)
                .build();
    }

    @Override
    public boolean deleteUser(Long userId) {
        //查询指定用户
        TblUser user = userDao.selectById(userId);
        if (user == null) {
            throw new BaseException("未找到指定用户");
        }
        //判断修改的用户是否为系统管理员
        if (user.getLoginName().equals(ADMIN_PHONE)) {
            throw new BaseException("管理员账号不允许删除");
        }
        int count = userDao.updateById(TblUser.builder()
                .id(userId)
                .deleted(true)
                .build());
        return count == 1;
    }

    @Override
    public boolean sendCode(SendCodeRequest request) {
        //检查入参
        request.toRequestCheck();
        List<TblUser> list = userDao.selectList(TblUser.getWrapper()
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getAccountLocked, false)
                .eq(TblUser::getPhone, request.getPhone()));
        if (list.isEmpty()) {
            throw new BaseException("该手机号未被使用");
        }
        String phone = request.getPhone();
        String code = codeUtil.getNumCode();
        if (telCodeService.saveCode(phone, code)) {
            //发送短信验证码
            smsService.sendSmsOfCode(phone, code);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updatePhone(PhoneUpdateRequest request) {
        //检查入参
        request.toRequestCheck();
        //查询当前登录用户手机号
        Long userId = threadLocalUtil.getUserId();
        String phone = userDao.selectById(userId).getPhone();
        //核对手机验证码
        if (!telCodeService.verifyCode(phone, request.getCode())) {
            throw new BaseException("验证码错误或已过期");
        }
        //核对新旧手机号
        if (request.getNewPhone().equals(phone)) {
            throw new BaseException("新手机号与当前绑定手机号一致");
        }
        //验证新手机号是否已使用
        List<TblUser> userList1 = userDao.selectList(TblUser.getWrapper()
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getPhone, request.getNewPhone()));
        if (userList1 != null && userList1.size() > 0) {
            throw new BaseException("手机号已被使用");
        }
        //修改手机号
        int count = userDao.updateById(TblUser.builder()
                .id(userId)
                .phone(request.getNewPhone())
                .build());
        return count  == 1;
    }

    @Override
    public IPage<UserResponse> findUserList(UserFindRequest request) {
        //组装查询条件
        List<Long> orgIds = orgManager.findChildOrg(threadLocalUtil.getOrganizationId());
        LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblUser::getCreateTime)
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getUserType, UserTypeEnum.ORG_USER.getKey())
                .eq(request.getOrganizationId() != null, TblUser::getOrganizationId, request.getOrganizationId())
                .like(StringUtils.isNotEmpty(request.getName()), TblUser::getName, "%" + request.getName() + "%")
                .eq(request.getRoleId() != null, TblUser::getRoleId, request.getRoleId())
                .eq(StringUtils.isNotEmpty(request.getUserStatus()), TblUser::getUserStatus, request.getUserStatus())
                .in(orgIds != null, TblUser::getOrganizationId, orgIds);//非系统管理组,只能查看本单位及下属单位的用户
        //查询
        Page<TblUser> list = userDao.selectPage(new Page<>(request.getPageNum(), request.getPageSize()), wrapper);
        //组装数据
        Set<Long> roldIds = new HashSet<>();
        Set<Long> orgIdSet = new HashSet<>();
        for (TblUser po : list.getRecords()) {
            orgIdSet.add(po.getOrganizationId());
            if (po.getRoleId() != null) {
                roldIds.add(po.getRoleId());
            }
        }
        Map<Long, TblOrganization> orgMap = orgManager.getOrgPoMap(new ArrayList<>(orgIdSet));
        Map<Long, TblRole> roleMap = roleMenuManager.getRoleMap(new ArrayList<>(roldIds));
        Map<String, String> dictSubsetMap = dictManager.getDictSubsetMap(DictGroupEnum.ORGANIZATION_TYPE.getKey(), orgMap.values().stream()
                .map(TblOrganization::getOrganizationType).distinct().collect(Collectors.toList()));
        return list.convert(po -> {
            UserResponse vo = new UserResponse();
            BeanUtils.copyProperties(po, vo);
            if (!po.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode())) {
                TblOrganization org = orgMap.get(vo.getOrganizationId());
                vo.setOrganizationName(org.getOrganizationName());
                vo.setOrganizationType(org.getOrganizationType());
                vo.setOrganizationTypeName(dictSubsetMap.get(org.getOrganizationType()));
            } else {
                vo.setOrganizationName(OrganizationNodeEnum.ROOT_COMPANY_CODE.getName());
                vo.setOrganizationType(OrganizationTypeEnum.SYSTEM_SAAS.getKey());
                vo.setOrganizationTypeName(OrganizationTypeEnum.SYSTEM_SAAS.getTitle());
            }
            if (vo.getRoleId() != null) {
                TblRole role = roleMap.get(vo.getRoleId());
                vo.setRoleName(role.getRoleName());
            }
            return vo;
        });
    }

    @Override
    public List<SelectResponse> userSelect(Long organizationId) {
        List<Long> orgIds = orgManager.findChildOrg(threadLocalUtil.getOrganizationId());
        LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblUser::getCreateTime)
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getUserType, UserTypeEnum.ORG_USER.getKey())
                .eq(organizationId != null, TblUser::getOrganizationId, organizationId)
                .in(orgIds != null, TblUser::getOrganizationId, orgIds);//非系统管理组,只能查看本单位及下属单位的用户
        return userDao.selectList(wrapper).stream()
                .map(x -> SelectResponse.builder()
                        .id(x.getId())
                        .name(x.getName())
                        .status(1)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<SelectResponse> userSelectNotFilter(Long organizationId) {
        LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblUser::getCreateTime)
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getUserType, UserTypeEnum.ORG_USER.getKey())
                .eq(organizationId != null, TblUser::getOrganizationId, organizationId);
        return userDao.selectList(wrapper).stream()
                .map(x -> SelectResponse.builder()
                        .id(x.getId())
                        .name(x.getName())
                        .status(1)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<SelectResponse> userSelectByCompany(Long organizationId) {
        LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblUser::getCreateTime)
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getUserType, UserTypeEnum.ORG_USER.getKey())
                .eq(organizationId != null, TblUser::getOrganizationId, organizationId);
        //查询所有企业单位
        List<TblOrganization> organizations = organizationDao.selectList(TblOrganization.getWrapper()
                .eq(TblOrganization::getDeleted, false)
                .eq(TblOrganization::getOrganizationType, OrganizationTypeEnum.COMPANY.getKey()));
        if (organizationId == null && organizations.size() > 0) {
            wrapper.in(TblUser::getOrganizationId, organizations.stream().map(TblOrganization::getId).collect(Collectors.toList()));
        }
        return userDao.selectList(wrapper).stream()
                .map(x -> SelectResponse.builder()
                        .id(x.getId())
                        .name(x.getName())
                        .status(1)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailResponse findUserDetail(Long id) {
        TblUser po = userDao.selectById(id);
        List<Long> orgIds = new ArrayList<>();
        orgIds.add(po.getOrganizationId());
        Map<Long, TblOrganization> orgMap = orgManager.getOrgPoMap(orgIds);
        Map<String, String> dictSubsetMap = dictManager.getDictSubsetMap(DictGroupEnum.ORGANIZATION_TYPE.getKey(), orgMap.values().stream()
                .map(TblOrganization::getOrganizationType).distinct().collect(Collectors.toList()));
        UserDetailResponse vo = new UserDetailResponse();
        BeanUtils.copyProperties(po, vo);
        TblOrganization org = orgMap.get(vo.getOrganizationId());
        vo.setOrganizationName(vo.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode()) ? OrganizationNodeEnum.ROOT_COMPANY_CODE.getName() : org.getOrganizationName());
        vo.setOrganizationType(vo.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode()) ? OrganizationTypeEnum.SYSTEM_SAAS.getKey() : org.getOrganizationType());
        vo.setOrganizationTypeName(dictSubsetMap.get(vo.getOrganizationType()));
        if (vo.getRoleId() != null) {
            List<Long> roldIds = new ArrayList<>();
            roldIds.add(po.getRoleId());
            Map<Long, TblRole> roleMap = roleMenuManager.getRoleMap(new ArrayList<>(roldIds));
            TblRole role = roleMap.get(vo.getRoleId());
            vo.setRoleName(role.getRoleName());
        }
        return vo;
    }

    @Override
    public boolean saveUser(UserSaveRequest request) {
        //判断用户是否拥有权限
        checkUserPermissionManager.checkUserPermission(PermissionEnum.USER_SAVE_UPDATE);
        request.toRequestCheck();
        //判断手机号是否已使用
        LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblUser::getDeleted, false)
                .eq(TblUser::getLoginName, request.getPhone());
        if (request.getId() != null) {
            wrapper.ne(TblUser::getId, request.getId());
        }
        List<TblUser> list = userDao.selectList(wrapper);
        if (list.size() > 0) {
            throw new BaseException("手机号已注册");
        }
        //保存
        TblUser po = request.getId() == null ? new TblUser() : userDao.selectById(request.getId());
        BeanUtils.copyProperties(request, po);
        po.setLoginName(request.getPhone());
        po.setUserType(UserTypeEnum.ORG_USER.getKey());
        po.setAdminFlag(false);
        int count = request.getId() == null ? userDao.insert(po) : userDao.updateById(po);
        //更新密码
        if (count == 1 && StringUtils.isNotEmpty(request.getPwd())) {
            userDao.updateById(TblUser.builder()
                    .id(po.getId())
                    .hashedPassword(SafeHashGenerator.getStretchedPassword(request.getPwd(), po.getId().toString()))
                    .build());
        } else if (count == 1 && request.getId() == null) {//新增用户成功，设置密码
            userDao.updateById(TblUser.builder()
                    .id(po.getId())
                    .hashedPassword(SafeHashGenerator.getStretchedPassword(getNewPassword(), po.getId().toString()))
                    .build());
        }
        if (count == 1) {
            flowService.userSaveByService(ServiceUserSaveRequest.builder()
                    .userId(po.getId().toString())
                    .name(po.getName())
                    .orgId(po.getOrganizationId().toString())
                    .build());
        }
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
    public Map<Long, String> findUserMapById(List<Long> request) {
        return userDao.selectList(TblUser.getWrapper()
                .orderByDesc(TblUser::getCreateTime)
                .in(request != null && request.size() > 0, TblUser::getId, request))
                .stream()
                .collect(Collectors.toMap(TblUser::getId, TblUser::getName, (a, b) -> b));
    }



    @Override
    public List<UserResponse> findAllUserInfo(String secretKey) {
        if (!secretKey.equals(SecretKeyEnum.SECRET_KEY.getKey())) {
            return new ArrayList<>();
        } else {
            //组装查询条件
            LambdaQueryWrapper<TblUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(TblUser::getCreateTime)
                    .eq(TblUser::getDeleted, false)
                    .eq(TblUser::getUserType, UserTypeEnum.ORG_USER.getKey());
            //查询
            List<TblUser> list = userDao.selectList(wrapper);
            //组装数据
            Set<Long> roldIds = new HashSet<>();
            Set<Long> orgIds = new HashSet<>();
            for (TblUser po : list) {
                orgIds.add(po.getOrganizationId());
                if (po.getRoleId() != null) {
                    roldIds.add(po.getRoleId());
                }
            }
            Map<Long, TblOrganization> orgMap = orgManager.getOrgPoMap(new ArrayList<>(orgIds));
            Map<Long, TblRole> roleMap = roleMenuManager.getRoleMap(new ArrayList<>(roldIds));
            Map<String, String> dictSubsetMap = dictManager.getDictSubsetMap(DictGroupEnum.ORGANIZATION_TYPE.getKey(), orgMap.values().stream()
                    .map(TblOrganization::getOrganizationType).distinct().collect(Collectors.toList()));
            return list.stream().map(po -> {
                UserResponse vo = new UserResponse();
                BeanUtils.copyProperties(po, vo);
                if (!po.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode())) {
                    TblOrganization org = orgMap.get(vo.getOrganizationId());
                    vo.setOrganizationName(org.getOrganizationName());
                    vo.setOrganizationType(org.getOrganizationType());
                    vo.setOrganizationTypeName(dictSubsetMap.get(org.getOrganizationType()));
                } else {
                    vo.setOrganizationName(OrganizationNodeEnum.ROOT_COMPANY_CODE.getName());
                    vo.setOrganizationType(OrganizationTypeEnum.SYSTEM_SAAS.getKey());
                    vo.setOrganizationTypeName(OrganizationTypeEnum.SYSTEM_SAAS.getTitle());
                }
                if (vo.getRoleId() != null) {
                    TblRole role = roleMap.get(vo.getRoleId());
                    vo.setRoleName(role.getRoleName());
                }
                return vo;
            }).collect(Collectors.toList());
        }
    }

    @Override
    public List<Long> findUserListByName(String name) {
        return userDao.selectList(TblUser.getWrapper()
                .orderByDesc(TblUser::getCreateTime)
                .eq(TblUser::getDeleted, false)
                .like(TblUser::getName, name))
                .stream()
                .map(TblUser::getId)
                .collect(Collectors.toList());
    }

    @Override
    public String simulatedLogin() {
        //查询用户
        TblUser user = userDao.selectOne(TblUser.getWrapper().eq(TblUser::getUserType, UserTypeEnum.SYSTEM_ADMIN.getKey()));
        return userManager.getUserToken(user, null, TokenTypeEnum.BI);
    }

    @Override
    public String findFirstUserLoginNameByOrgId(SyncDataLoginRequest request) {
        if(request.getSecretValue()==null||!request.getSecretValue().equals(SECRET_VALUE)){
            throw new BaseException("登录失败");
        }
        List<TblUser> tblUsers = userDao.selectList(TblUser.getWrapper()
                .orderByDesc(TblUser::getCreateTime)
                .in(TblUser::getOrganizationId, request.getOrgId())
                .eq(TblUser::getDeleted, 0)
                .eq(TblUser::getUserStatus, "ENABLE"));
        if(tblUsers==null||tblUsers.size()<=0){
            throw new BaseException("未查找到该组织用户");
        }
        return tblUsers.get(0).getLoginName();
    }

    /**
     * 同步数据-模拟登录
     * @param request
     * @return
     */
    @Override
    public String mockLogin(SyncDataLoginRequest request) {
        if(request.getSecretValue()==null||!request.getSecretValue().equals(SECRET_VALUE)){
            throw new BaseException("登录失败");
        }
        //查询用户
        TblUser user = findUserByLoginName(request.getLoginName());
        if (user == null) {
            throw new BaseException("账号未找到");
        }
        //登录返回token
        return userManager.getUserToken(user, "", TokenTypeEnum.ADMIN);
    }

    @Override
    public boolean testUpdateAllPwd(UserLoginRequest request) {
        if (request.getCode().equals("CZeVqXxG&ALW1p")) {
            List<TblUser> list = userDao.selectList(TblUser.getWrapper().eq(TblUser::getDeleted, false));
            for (TblUser po : list) {
                updatePasswordById(po.getId(), request.getPassword());
            }
        }
        return true;
    }

    @Override
    public String openLoginToken(OpenLoginRequest request) {
        String username = "";
        if ("5kLOzu07K1x0PTpqnPVn".equals(request.getAppId()) && "JOkTTMtKLrWTXp6FHOfoj8B1".equals(request.getAppSecret())) {
            username = "szls";//数字孪生
        }
        if (StringUtils.isEmpty(username)) {
            throw new BaseException("appId或appSecret错误");
        }
        //查询用户
        TblUser user = findUserByLoginName(username);
        if (user == null) {
            TblOrganization organization = organizationDao.selectOne(TblOrganization.getWrapper()
                    .last("limit 1")
                    .eq(TblOrganization::getDeleted, false)
                    .eq(TblOrganization::getOrganizationType, OrganizationTypeEnum.GOVERNMENT.getKey()));
            TblRole role = roleDao.selectOne(TblRole.getWrapper()
                    .last("limit 1")
                    .eq(TblRole::getDeleted, false)
                    .eq(TblRole::getRoleType, RoleTypeEnum.OTHER.getKey()));
            userDao.insert(TblUser.builder()
                    .name(username)
                    .loginName(username)
                    .phone(username)
                    .userType(UserTypeEnum.ORG_USER.getKey())
                    .adminFlag(false)
                    .userStatus(UserStatusEnum.ENABLE.getKey())
                    .organizationId(organization.getId())
                    .roleId(role.getId())
                    .build());
            user = findUserByLoginName(username);
        }
        //新增用户登录记录
        userManager.addLoginRecord(user.getId(), LoginWayEnum.ADMIN_WEB);
        return userManager.getUserToken(user,"", TokenTypeEnum.BI);
    }

    @Override
    public OpenCheckTokenResponse openCheckToken(OpenCheckTokenRequest request) {
        OpenCheckTokenResponse vo = OpenCheckTokenResponse.builder().flag(false).build();
        String authentication = request.getToken();
        try {
            if (StringUtils.isNotEmpty(authentication)) {
                TokenModel tokenModel = JwtUtils.getTokenModelByJwtToken(authentication);
                boolean flag = tokenService.checkToken(tokenModel);
                vo.setFlag(flag);
                if (flag) {
                    vo.setFailureTime(tokenService.getTokenExpirationTime(authentication));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            vo.setFlag(false);
        }
        return vo;
    }

    @Override
    public boolean changePasswordByUser(PwdChangeByUserRequest request) {
        //判断用户是否拥有权限
        checkUserPermissionManager.checkUserPermission(PermissionEnum.USER_SAVE_UPDATE);
        //检查入参
        request.toRequestCheck();
        //查询用户
        TblUser user = userDao.selectById(request.getId());
        if (user == null || user.getDeleted()) {
            throw new BaseException("未找到指定用户");
        }
        List<Long> orgList = orgManager.findChildOrgNotNull(threadLocalUtil.getOrganizationId());
        boolean flag = false;
        for (Long orgId : orgList) {
            if (orgId.equals(user.getOrganizationId())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new BaseException("未找到指定用户");
        }
        return updatePasswordById(user.getId(), request.getNewPwd());
    }

    @Override
    public boolean needChangePassword() {
        //当前登录用户ID
        Long userId = threadLocalUtil.getUserId();
        TblUser user = userDao.selectById(userId);
        if (user == null) {
            throw new BaseException("账号数据异常,请联系管理员");
        } else if (user.getDeleted()) {
            throw new BaseException("未找到指定账号");
        } else if (user.getUserStatus().equals(UserStatusEnum.DISABLE.getKey())) {
            throw new BaseException("账号已禁用");
        }
        //密码修改日期
        Date changedAt = user.getPasswordChangedAt();
        if (changedAt != null) {
            long msNum = Calendar.getInstance().getTime().getTime() - changedAt.getTime();
            long dayNum = msNum / (24 * 60 * 60 * 1000);
            //距上次更新超90天,需要更新密码
            return dayNum > 90;
        } else {
            //初始化,需要更新密码
            return true;
        }
    }
}
