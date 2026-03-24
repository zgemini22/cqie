package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.LoginUserResponse;
import com.zds.biz.vo.response.user.OpenCheckTokenResponse;
import com.zds.biz.vo.response.user.UserDetailResponse;
import com.zds.biz.vo.response.user.UserResponse;
import com.zds.user.po.TblUser;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**
     * 系统用户登录
     */
    String adminUserLogin(UserLoginRequest request);

    /**
     * 系统用户登录无需验证码
     */
    String adminUserLoginUnCode(UserLoginUncodeRequest request);

    /**
     * 系统用户登录(从业资格考核)
     */
    String adminUserLoginByExamine(UserClientLoginRequest request);

    /**
     * 系统用户登录(抄表信息化)
     */
    String adminUserLoginByMeter(UserClientLoginRequest request);

    /**
     * 前台用户登录
     */
    String clientUserLogin(UserClientLoginRequest request);

    /**
     * 前台用户登录(从业资格考核)
     */
    String clientUserLoginByExamine(UserClientLoginRequest request);

    /**
     * 根据手机号查询用户信息
     */
    TblUser findUserByPhone(String phone);

    /**
     * 根据登录名查询用户信息
     */
    TblUser findUserByLoginName(String loginName);

    /**
     * 系统用户退出登录
     */
    boolean adminUserLogout();

    /**
     * 修改密码(旧密码)
     */
    boolean changeOldPassword(PwdChangeOldRequest request);

    /**
     * 修改密码(手机验证码)
     */
    boolean changePassword(PwdChangeRequest request);

    /**
     * 忘记密码
     */
    boolean renewPassword(PwdRenewRequest request);

    /**
     * 更新用户状态
     */
    boolean updateStatus(UserStatusRequest request);

    /**
     * 查询当前登录用户信息
     */
    LoginUserResponse getUserInfo();

    /**
     * 查询当前登录用户信息(从业资格考核)
     */
    LoginUserResponse getUserInfoByExamine();

    /**
     * 查询当前登录用户信息(抄表信息化)
     */
    LoginUserResponse getUserInfoByMeter();

    /**
     * 删除用户
     */
    boolean deleteUser(Long userId);

    /**
     * 发送验证码
     */
    boolean sendCode(SendCodeRequest request);

    /**
     * 修改绑定手机号
     */
    boolean updatePhone(PhoneUpdateRequest request);

    /**
     * 用户列表
     */
    IPage<UserResponse> findUserList(UserFindRequest request);

    /**
     * 用户下拉
     */
    List<SelectResponse> userSelect(Long organizationId);

    /**
     * 用户下拉(不过滤权限)
     */
    List<SelectResponse> userSelectNotFilter(Long organizationId);

    /**
     * 用户下拉(企业)
     */
    List<SelectResponse> userSelectByCompany(Long organizationId);

    /**
     * 用户详情
     */
    UserDetailResponse findUserDetail(Long id);

    /**
     * 保存用户
     */
    boolean saveUser(UserSaveRequest request);

    /**
     * 查询指定用户范围的用户名称
     */
    Map<Long, String> findUserMapById(List<Long> request);

    /**
     * 查询所有用户信息
     */
    List<UserResponse> findAllUserInfo(String secretKey);

    /**
     * 用户名称模糊查询用户ID集合
     */
    List<Long> findUserListByName(String name);

    /**
     * 大屏模拟登录
     */
    String simulatedLogin();

    /**
     * 组织ID查询该组织第一个用户
     * @param request
     * @return
     */
    String findFirstUserLoginNameByOrgId(SyncDataLoginRequest request);

    /**
     * 同步数据-模拟登录获取token
     * @param request
     * @return
     */
    String mockLogin(SyncDataLoginRequest request);

    boolean testUpdateAllPwd(UserLoginRequest request);

    /**
     * 获取认证token
     */
    String openLoginToken(OpenLoginRequest request);

    /**
     * 校验token有效性
     */
    OpenCheckTokenResponse openCheckToken(OpenCheckTokenRequest request);

    /**
     * 重置指定用户密码
     */
    boolean changePasswordByUser(PwdChangeByUserRequest request);

    /**
     * 判断用户密码是否需要更新
     */
    boolean needChangePassword();
}
