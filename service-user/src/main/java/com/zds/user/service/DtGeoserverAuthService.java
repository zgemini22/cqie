package com.zds.user.service;

import com.zds.biz.vo.AuthKeyRes;

import javax.servlet.http.HttpServletRequest;

public interface DtGeoserverAuthService {

    /**
     * 获取授权码
     * @return 授权码
     */
    AuthKeyRes getAuthKey(HttpServletRequest request);

    /**
     * 检查授权码
     * @param authKey 授权码
     * @return true：通过校验， false：未通过校验
     */
    boolean checkAuthKey(String authKey)throws Exception;
}
