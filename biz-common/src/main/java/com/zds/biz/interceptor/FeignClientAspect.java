package com.zds.biz.interceptor;

import com.zds.biz.util.ThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

// 通过AOP切面获取 FeignClient的value值，以便动态的去注入token值
@Aspect
@Component
public class FeignClientAspect {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;
    
    @Around("@within(feignClient)")
    public Object aroundFeignInterface(ProceedingJoinPoint joinPoint, 
                                       FeignClient feignClient) throws Throwable {
        // 获取FeignClient注解信息
        String info = extractFeignClientInfo(feignClient, joinPoint);
        
        try {
            threadLocalUtil.setCurrentFeignClient(info);
            return joinPoint.proceed();
        } finally {
            threadLocalUtil.clearCurrentFeignClient();
        }
    }
    
    @Around("@annotation(feignClient)")
    public Object aroundFeignMethod(ProceedingJoinPoint joinPoint,
                                    org.springframework.cloud.openfeign.FeignClient feignClient) 
                                    throws Throwable {
        // 如果方法上也有@FeignClient注解（较少见）
        String info = extractFeignClientInfo(feignClient, joinPoint);
        
        try {
            threadLocalUtil.setCurrentFeignClient(info);
            return joinPoint.proceed();
        } finally {
            threadLocalUtil.clearCurrentFeignClient();
        }
    }
    //获取远程调用的value值
    private String extractFeignClientInfo(FeignClient annotation,
                                                   ProceedingJoinPoint joinPoint) {
        return annotation.value();
    }
}