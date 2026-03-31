package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.GasSupplyOperationQueryRequest;
import com.zds.biz.vo.GasSupplyOperationResponse;
import com.zds.user.service.GsSupplyOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "供气作业管理")
@RestController
@RequestMapping("/api/gas-operation")
public class GasSupplyOperationController {

    @Autowired
    private GsSupplyOperationService gsSupplyOperationService;

    @ApiOperation("分页查询供气作业列表")
    @GetMapping("/list")
    public BaseResult<Map<String, Object>> list(GasSupplyOperationQueryRequest request) {
        try {
            IPage<GasSupplyOperationResponse> page = gsSupplyOperationService.selectPage(request);
            Map<String, Object> result = new HashMap<>();
            result.put("records", page.getRecords());
            result.put("total", page.getTotal());
            result.put("current", page.getCurrent());
            result.put("size", page.getSize());
            result.put("pages", page.getPages());
            return BaseResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResult.failure("查询失败：" + e.getMessage());
        }
    }
}