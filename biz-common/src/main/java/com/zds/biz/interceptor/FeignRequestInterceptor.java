package com.zds.biz.interceptor;

import com.zds.biz.util.ThreadLocalUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Override
    public void apply(RequestTemplate template) {
        String currentFeignClient = threadLocalUtil.getCurrentFeignClient();
        if (StringUtils.isNotEmpty(currentFeignClient) && "ai-service-dispose".equals(currentFeignClient)) {
            template.header("superkey", "8ue4YWKdKkRnvhh5axvF");
        } else {
            template.header(HttpHeaders.AUTHORIZATION, threadLocalUtil.getToken());
        }
    }
}
