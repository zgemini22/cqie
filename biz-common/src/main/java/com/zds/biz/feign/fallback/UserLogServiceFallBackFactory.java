package com.zds.biz.feign.fallback;

import com.zds.biz.feign.UserLogService;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.LogVo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserLogServiceFallBackFactory implements FallbackFactory<UserLogService> {

    private Logger log = LoggerFactory.getLogger(UserLogServiceFallBackFactory.class);

    @Override
    public UserLogService create(Throwable throwable) {
        log.error("用户服务异常:" + throwable.getMessage());
        return new UserLogService() {

            @Override
            public BaseResult<Long> save(LogVo log) {
                return BaseResult.failure();
            }

            @Override
            public BaseResult<String> update(LogVo log) {
                return BaseResult.failure();
            }
        };
    }
}
