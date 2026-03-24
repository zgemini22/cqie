package com.zds.user.manager;

import com.zds.biz.constant.user.LoginWayEnum;
import com.zds.biz.constant.user.TokenTypeEnum;
import com.zds.biz.constant.user.UserTypeEnum;
import com.zds.user.po.TblUser;

import java.util.List;
import java.util.Map;

public interface UserManager {
    /**
     * 新增后台用户
     * @param organizationId 单位ID
     * @param roleId 角色ID
     * @param loginName 登录名
     * @param password 密码
     * @param phone 手机号
     * @param name 姓名
     * @param adminFlag 是否为单位管理员
     * @return 用户ID
     */
    Long addSystemUser(Long organizationId, Long roleId, String loginName, String password, String phone, String name, Boolean adminFlag, UserTypeEnum userTypeEnum);

    /**
     * 修改后台用户
     * @param userId 用户ID
     * @param roleId 角色ID
     * @param loginName 登录名
     * @param password 密码
     * @param phone 手机号
     * @param name 姓名
     */
    boolean updateSystemUser(Long userId, Long roleId, String loginName, String password, String phone, String name);

    /**
     * 获取用户token
     */
    String getUserToken(TblUser user, String pwd, TokenTypeEnum tokenTypeEnum);

    /**
     * 检查登录名是否已使用
     */
    void checkLoginName(String loginName, Long userId);

    /**
     * 检查手机号是否已使用
     */
    void checkPhone(String phone, Long userId);

    /**
     * 清除指定单位的用户token缓存
     */
    void cleanUserTokenByOrg(Long organizationId);

    /**
     * 清除指定单位集合的用户token缓存
     */
    void cleanUserTokenByOrg(List<Long> organizationIds);

    /**
     * 清除指定用户的用户token缓存
     */
    void cleanUserTokenByUser(Long userId);

    /**
     * 清除多个用户的用户token缓存
     */
    void cleanUserTokenByUser(List<Long> userIds);

    Map<Long, String> getUserNameMap(List<Long> ids);

    Map<Long, TblUser> getUserMap(List<Long> ids);

    /**
     * 新增用户登录记录
     */
    void addLoginRecord(Long userId, LoginWayEnum loginWayEnum);

    /**
     * 累计登录失败次数
     */
    void addLoginFailCount(String loginName);
}
