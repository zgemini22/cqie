package com.zds.file.feign;

import com.zds.biz.interceptor.FeignRequestInterceptor;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.BasicDataRequest;
import com.zds.biz.vo.response.user.BasicDataResponse;
import com.zds.file.feign.fallback.UserServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-user", configuration = FeignRequestInterceptor.class, fallbackFactory = UserServiceFallBackFactory.class, url = "#{'${feignurl.isOpen}'?'${feignurl.serviceUser:}':''}")
public interface UserService {

    @RequestMapping(value = "/admin/basic/select/key", method = RequestMethod.POST)
    BaseResult<BasicDataResponse> selectByKey(@RequestBody BasicDataRequest request);
}
