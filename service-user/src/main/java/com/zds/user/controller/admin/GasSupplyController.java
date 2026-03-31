package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.BaseResult;
import com.zds.user.service.GasSupplyService;
import com.zds.biz.vo.GasSupplyQueryRequest;
import com.zds.biz.vo.GasSupplyRequest;
import com.zds.biz.vo.GasSupplyResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "供气计划管理")
@RestController
@RequestMapping("/api/gas-supply")
public class GasSupplyController {

    @Autowired
    private GasSupplyService gasSupplyService;

    @ApiOperation("分页查询供气计划列表")
    @GetMapping("/list")
    public BaseResult<Map<String, Object>> list(GasSupplyQueryRequest request) {
        try {
            IPage<GasSupplyResponse> page = gasSupplyService.selectPage(request);
            Map<String, Object> result = new HashMap<>();
            result.put("list", page.getRecords());
            result.put("total", page.getTotal());
            return BaseResult.success(result);
        } catch (Exception e) {
            return BaseResult.failure("查询失败：" + e.getMessage());
        }
    }


    @ApiOperation("查询供气计划详情")
    @GetMapping("/detail/{id}")
    public BaseResult<GasSupplyResponse> detail(@ApiParam(value = "供气计划ID", required = true) @PathVariable Integer id) {
        GasSupplyResponse detail = gasSupplyService.getDetail(id);
        if (detail == null) {
            return BaseResult.failure("供气计划不存在");
        }
        return BaseResult.success(detail);
    }
}