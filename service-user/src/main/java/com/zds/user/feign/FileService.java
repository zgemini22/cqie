package com.zds.user.feign;

import com.zds.biz.interceptor.FeignRequestInterceptor;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.FileInfoVo;
import com.zds.biz.vo.request.file.FileRealNameRequest;
import com.zds.user.feign.fallback.FileServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-file", configuration = FeignRequestInterceptor.class, fallbackFactory = FileServiceFallBackFactory.class, url = "#{'${feignurl.isOpen}'?'${feignurl.serviceFile:}':''}")
public interface FileService {

    /**
     * 查询文件信息
     */
    @RequestMapping(value = "/file/feign/findByRealName", method = RequestMethod.POST)
    BaseResult<FileInfoVo> findByRealName(@RequestBody FileRealNameRequest request);
}

