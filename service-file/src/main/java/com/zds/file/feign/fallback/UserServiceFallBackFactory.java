package com.zds.file.feign.fallback;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.BasicDataRequest;
import com.zds.biz.vo.response.user.BasicDataResponse;
import com.zds.file.feign.UserService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserServiceFallBackFactory implements FallbackFactory<UserService> {

    private Logger log = LoggerFactory.getLogger(UserServiceFallBackFactory.class);

    @Override
    public UserService create(Throwable throwable) {
        log.error("用户服务异常:" + throwable.getMessage());
        return new UserService() {
            @Override
            public BaseResult<BasicDataResponse> selectByKey(BasicDataRequest request) {
                return BaseResult.success(BasicDataResponse.builder().build());
            }
        };
    }
}
