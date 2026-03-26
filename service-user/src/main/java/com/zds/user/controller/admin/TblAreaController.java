package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.response.user.TblAreaTreeResponse;
import com.zds.user.service.TblAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api(tags = "城市树")
@RestController
@RequestMapping("/area")
public class TblAreaController {

    @Autowired
    private TblAreaService tblAreaService;

    @Authorization
    @ApiOperation("获取区域树形结构数据")
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    public BaseResult<List<TblAreaTreeResponse>> getAreaTree() {
        return BaseResult.success(tblAreaService.getAreaTree());
    }
}