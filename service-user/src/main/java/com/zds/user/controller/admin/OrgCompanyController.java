package com.zds.user.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.OrgCompanySaveRequest;
import com.zds.biz.vo.response.user.OrgCompanyDetailResponse;
import com.zds.biz.vo.response.user.OrgCompanyResponse;
import com.zds.user.service.OrgCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "企业管理模块")
@RestController
@RequestMapping("/org/company")
public class OrgCompanyController {

    @Autowired
    private OrgCompanyService orgCompanyService;
    @Authorization
    @ApiOperation("企业列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<OrgCompanyResponse>> list(@RequestBody String body) {
        System.out.println("=== Controller 接收到的原始字符串: " + body);

        // 手动解析 JSON
        Map<String, Object> params = new HashMap<>();
        if (body != null && !body.isEmpty()) {
            try {
                params = JSON.parseObject(body, new TypeReference<Map<String, Object>>() {});
            } catch (Exception e) {
                System.out.println("解析失败: " + e.getMessage());
            }
        }

        String name = (String) params.get("name");
        System.out.println("=== 解析后的 name: " + name);

        return BaseResult.success(orgCompanyService.list(name));
    }

    @Authorization
    @ApiOperation("企业详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<OrgCompanyDetailResponse> detail(@RequestBody IdRequest request) {
        return BaseResult.success(orgCompanyService.detail(request.getId()));
    }

    @Authorization
    @ApiOperation("保存企业")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody OrgCompanySaveRequest request) {
        return BaseResult.judgeOperate(orgCompanyService.save(request));
    }

    @Authorization
    @ApiOperation("编辑企业")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResult<String> edit(@RequestBody OrgCompanySaveRequest request) {
        return BaseResult.judgeOperate(orgCompanyService.edit(request));
    }

    @Authorization
    @ApiOperation("删除企业")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> delete(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(orgCompanyService.delete(request.getId()));
    }
}