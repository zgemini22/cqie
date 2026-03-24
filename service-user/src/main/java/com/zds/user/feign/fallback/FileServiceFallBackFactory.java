package com.zds.user.feign.fallback;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.FileInfoVo;
import com.zds.biz.vo.request.file.FileRealNameRequest;
import com.zds.user.feign.FileService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class FileServiceFallBackFactory implements FallbackFactory<FileService> {

    private Logger log = LoggerFactory.getLogger(FileServiceFallBackFactory.class);

    @Override
    public FileService create(Throwable throwable) {
        log.error("文件服务异常:" + throwable.getMessage());
        return new FileService() {

            @Override
            public BaseResult<FileInfoVo> findByRealName(@RequestBody FileRealNameRequest request) {
                return BaseResult.success("", null);
            }
        };
    }
}
