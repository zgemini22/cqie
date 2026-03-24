package com.zds.biz.interceptor;

import com.zds.biz.util.RequestTypeChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class RequestWrapperFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(RequestTypeChecker.class);

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        if (RequestTypeChecker.isMultipartRequest(httpRequest)) {
            chain.doFilter(request, response);
        } else {
            // 使用自定义包装类包装请求（提前读取并缓存请求体）
            ReusableHttpServletRequestWrapper wrappedRequest = new ReusableHttpServletRequestWrapper(httpRequest);
            //判断@RequestBody是否是数组
            if(!RequestTypeChecker.isFeignRequest(wrappedRequest)){
                handler(wrappedRequest);
            }
            // 传递包装后的请求到下一个过滤器/控制器
            chain.doFilter(wrappedRequest, response);
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }


    private void handler(ReusableHttpServletRequestWrapper request) {
        try {
            // 1. 获取处理器方法
            HandlerExecutionChain handler = requestMappingHandlerMapping.getHandler(request);
            if (handler != null && handler.getHandler() instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler.getHandler();
                // 2. 查找 @RequestBody 参数
                MethodParameter requestBodyParam = findRequestBodyParameter(handlerMethod);
                if (requestBodyParam != null) {
                    // 判断是否为数组类型
                    if (requestBodyParam.getParameterType().equals(List.class)) {
                        //获取入参
                        String body = request.getBody();
                        //编辑入参
                        request.setBody("["+body+"]");
                    }
                }

            }
        } catch (Exception e) {
            log.debug("无法分析请求体类型", e);
        }

    }

    /**
     * 查找 @RequestBody 注解的参数
     */
    private MethodParameter findRequestBodyParameter(HandlerMethod handlerMethod) {
        for (int i = 0; i < handlerMethod.getMethodParameters().length; i++) {
            MethodParameter parameter = handlerMethod.getMethodParameters()[i];
            if (parameter.hasParameterAnnotation(RequestBody.class)) {
                return parameter;
            }
        }
        return null;
    }
}
