package com.zds.user.service;

import com.zds.biz.constant.user.TokenTypeEnum;
import com.zds.user.po.TblUser;
import com.zds.biz.vo.TokenModel;

import java.util.Date;
import java.util.List;

/**
 * token服务
 */
public interface TokenService {
    /**
     * 创建token
     * @param user 用户
     */
    TokenModel createToken(TblUser user, TokenTypeEnum tokenTypeEnum);
    /**
     * 刷新token
     * @param model token
     */
    boolean refreshToken(TokenModel model);
    /**
     * 检查token是否有效
     * @param model token
     */
    boolean checkToken(TokenModel model);
    /**
     * 从字符串中解析token
     * @param authentication 加密后的字符串
     */
    TokenModel getToken(String authentication);
    /**
     * 清除token
     * @param userId 用户id
     */
    boolean deleteToken(Long userId);
    /**
     * 清除token
     * @param userIds 用户id集合
     */
    boolean deleteToken(List<Long> userIds);
    /**
     * 获取用户token
     * @param user
     */
    TokenModel getTokenByUserId(TblUser user);
    /**
     * 获取token失效时间
     */
    Date getTokenExpirationTime(String token);
}
