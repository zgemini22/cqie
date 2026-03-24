package com.zds.biz.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class RequestTypeChecker {


    private static String SWAGGER_KEYWORDS;

    @Value("${parameter.key.refererFilter:swagger_ui}")
    public void setSwaggerKeywordsStatic(String value) {
        SWAGGER_KEYWORDS = value;
    }

    private static String FEIGN_HEADERS;

    @Value("${parameter.key.userAgentFilter:java}")
    public void setFEIGN_HEADERSStatic(String value) {
        FEIGN_HEADERS = value;
    }

    private static String Open;

    @Value("${parameter.key.urlHeadFilter:open}")
    public void setOpenStatic(String value) {
        Open = value;
    }

    private static String url;

    @Value("${parameter.key.urlFilter:service-file/file/upload, service-file/file/upload/base64, service-file/file/byte/upload, service-user/admin/user/login/code}")
    public void setUrlStatic(String value) {
        url = value;
    }

    private static String content;

    @Value("${parameter.key.contentTypeFilter:multipart/}")
    public void setContentStatic(String value) {
        content = value;
    }

    private static Set<String> filter(String type) {
        Set<String> stringSet = new HashSet<>();
        String[] Filter = null;
        switch (type) {
            case "urlFilter":
                Filter = url.split(",");
                break;
            case "userAgentFilter":
                Filter = FEIGN_HEADERS.split(",");
                break;
            case "refererFilter":
                Filter = SWAGGER_KEYWORDS.split(",");
                break;
            case "contentTypeFilter":
                Filter = content.split(",");
                break;
            case "urlHeadFilter":
                Filter = Open.split(",");
                break;
        }
        for (String str : Filter) {
            if (StringUtils.isNotEmpty(str)) {
                stringSet.add(str);
            }
        }
        return stringSet;
    }


    public static boolean isMultipartRequest() {
        HttpServletRequest request = getCurrentRequest();
        return request != null && isMultipartRequest(request);
    }

    public static boolean isMultipartRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        // 检查Referer
        if (contentType != null && containsAnyKeyword(contentType.toLowerCase(), filter("contentTypeFilter"))) {
            return true;
        }

        return false;
    }

    /**
     * 检查当前请求是否是特定url请求
     */
    public static boolean isUrlRequest() {
        HttpServletRequest request = getCurrentRequest();
        return request != null && isUrlRequest(request);
    }

    /**
     * 检查是否是特定url请求
     */
    public static boolean isUrlRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        // 检查Referer
        if (uri != null && containsAnyKeyword(uri.toLowerCase(),  filter("urlFilter"))) {
            return true;
        }

        return false;
    }

    /**
     * 检查当前请求是否是open请求
     */
    public static boolean isOpenRequest() {
        HttpServletRequest request = getCurrentRequest();
        return request != null && isOpenRequest(request);
    }

    /**
     * 检查是否是open请求
     */
    public static boolean isOpenRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (uri != null) {
            String[] strings = uri.split("/");
            String item = "";
            for (String str : strings) {
                if (!str.equals("") && !str.equals("gasapi") && !str.startsWith("service-")) {
                    item = str;
                    break;
                }
            }
            return containsAnyKeyword(item.toLowerCase(), filter("urlHeadFilter"));
        }

        return false;
    }

    /**
     * 检查当前请求是否是Swagger请求
     */
    public static boolean isSwaggerRequest() {
        HttpServletRequest request = getCurrentRequest();
        return request != null && isSwaggerRequest(request);
    }

    /**
     * 检查是否是Swagger请求
     */
    public static boolean isSwaggerRequest(HttpServletRequest request) {
        String referer = request.getHeader("X-Request-Source");
        // 检查Referer
        if (referer != null && containsAnyKeyword(referer.toLowerCase(), filter("refererFilter"))) {
            return true;
        }

        return false;
    }

    /**
     * 检查是否是Feign客户端请求
     */
    public static boolean isFeignRequest() {
        HttpServletRequest request = getCurrentRequest();
        return request != null && isFeignRequest(request);
    }

    /**
     * Feign默认Header
     */
    public static boolean isFeignRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        // 检查Referer
        if (userAgent != null && containsAnyKeyword(userAgent.toLowerCase(),  filter("userAgentFilter"))) {
            return true;
        }
        return false;
    }

    /**
     * 检查是否是需要排除加解密的请求
     */
    public static boolean isExcludedRequest() {
        return isSwaggerRequest() || isFeignRequest() || isUrlRequest() || isOpenRequest() || isMultipartRequest();
    }

    private static boolean containsAnyKeyword(String text, Set<String> keywords) {
        return keywords.stream().anyMatch(text::contains);
    }

    private static HttpServletRequest getCurrentRequest() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes instanceof ServletRequestAttributes) {
                return ((ServletRequestAttributes) requestAttributes).getRequest();
            }
        } catch (Exception e) {
            // 忽略异常
        }
        return null;
    }
}
