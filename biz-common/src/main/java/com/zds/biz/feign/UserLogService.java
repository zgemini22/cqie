package com.zds.biz.feign;

import com.zds.biz.feign.fallback.UserLogServiceFallBackFactory;
import com.zds.biz.interceptor.FeignRequestInterceptor;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.LogVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-user", contextId = "userLogFeign",configuration = FeignRequestInterceptor.class, fallbackFactory = UserLogServiceFallBackFactory.class, url = "#{'${feignurl.isOpen}'?'${feignurl.serviceUser:}':''}")
public interface UserLogService {
    /**
     * 接口调用日志保存
     */
    @RequestMapping(value = "/inner/log/save", method = RequestMethod.POST)
    BaseResult<Long> save(@RequestBody LogVo log);


    /**
     * 接口调用日志修改
     */
    @RequestMapping(value = "/inner/log/update", method = RequestMethod.POST)
    BaseResult<String> update(@RequestBody LogVo log);
}

