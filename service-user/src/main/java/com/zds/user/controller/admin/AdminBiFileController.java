package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.PageRequest;
import com.zds.biz.vo.request.user.BiFileSaveRequest;
import com.zds.biz.vo.response.user.BiFileListResponse;
import com.zds.user.service.BiFileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "大屏资源模块")
@RestController
@RequestMapping(value = "/admin/file/bi")
public class AdminBiFileController {

    @Autowired
    private BiFileService biFileService;

    @Authorization
    @ApiOperation("资源列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<BiFileListResponse>> findList(@RequestBody PageRequest request) {
        return BaseResult.success(biFileService.findList(request));
    }

    @Authorization
    @ApiOperation("资源详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<BiFileListResponse> findDetail(@RequestBody IdRequest request) {
        return BaseResult.success(biFileService.findDetail(request));
    }

    @Authorization
    @ApiOperation("资源保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody BiFileSaveRequest request) {
        return BaseResult.judgeOperate(biFileService.save(request));
    }

    @Authorization
    @ApiOperation("资源删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> delete(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(biFileService.delete(request));
    }
}
