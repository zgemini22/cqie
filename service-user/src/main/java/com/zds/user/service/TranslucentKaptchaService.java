package com.zds.user.service;

import javax.servlet.http.HttpServletResponse;

public interface TranslucentKaptchaService {

    void getLoginCode(HttpServletResponse response);

    boolean isHavLoginCode(String rightCode);

    boolean deleteLoginCode(String rightCode);
}
