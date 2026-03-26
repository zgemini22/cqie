package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.GsCutoffAreaNodeRequest;
import com.zds.biz.vo.response.user.GsCutoffAreaNodeDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffAreaNodeResponse;
import com.zds.user.service.GsCutoffAreaNodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "保供管理-停气涉及区域")
@RestController
@RequestMapping("/cutoff/areaNode")
public class GsCutoffAreaNodeController {

    @Autowired
    private GsCutoffAreaNodeService gsCutoffAreaNodeService;

    @Authorization
    @ApiOperation("保存停气涉及区域")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody GsCutoffAreaNodeRequest request) {
        return BaseResult.judgeOperate(gsCutoffAreaNodeService.save(request));
    }

    @Authorization
    @ApiOperation("修改停气涉及区域")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResult<String> edit(@RequestBody GsCutoffAreaNodeRequest request) {
        return BaseResult.judgeOperate(gsCutoffAreaNodeService.edit(request));
    }

    @Authorization
    @ApiOperation("删除停气涉及区域")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteById(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(gsCutoffAreaNodeService.deleteById(request.getId()));
    }

    @Authorization
    @ApiOperation("停气涉及区域列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<GsCutoffAreaNodeResponse>> list(@RequestBody GsCutoffAreaNodeRequest request) {
        return BaseResult.success(gsCutoffAreaNodeService.list(request));
    }

    @Authorization
    @ApiOperation("停气涉及区域详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<GsCutoffAreaNodeDetailResponse> detail(@RequestBody IdRequest request) {
        return BaseResult.success(gsCutoffAreaNodeService.detail(request.getId()));
    }
}
