package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.user.po.risk.RiskDTORequest;
import com.zds.user.po.risk.RiskPointDetail;
import com.zds.user.po.risk.RiskPointQueryDTO;
import com.zds.user.service.RiskPointService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Api(tags = "风险点管理")
@RestController
@RequestMapping(value = "/admin/riskPoint")
public class RiskPointController {

    @Resource
    private RiskPointService riskPointService;

    @Authorization
    @ApiOperation("分页查询风险点列表")
    @PostMapping("/list")
    public BaseResult<IPage<RiskPointDetail>> list(@RequestBody RiskPointQueryDTO queryDTO) {
        try {
            Integer page = queryDTO.getPageNum();
            Integer size = queryDTO.getPageSize();
            String riskName = queryDTO.getRiskName();
            String riskCode = queryDTO.getRiskCode();
            String organizationName = queryDTO.getOrganizationName();

            // ✅ 修复：日期为空时不调用 toString()，避免空指针
            String planInstallStartDate = queryDTO.getPlanInstallStartDate() == null
                    ? null
                    : queryDTO.getPlanInstallStartDate().toString();

            String planInstallEndDate = queryDTO.getPlanInstallEndDate() == null
                    ? null
                    : queryDTO.getPlanInstallEndDate().toString();

            Page<RiskPointDetail> pageParam = new Page<>(page, size);
            IPage<RiskPointDetail> result = riskPointService.selectRiskPointPage(
                    pageParam,
                    riskName,
                    riskCode,
                    organizationName,
                    planInstallStartDate, // ✅ 安全传值
                    planInstallEndDate
            );
            return BaseResult.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Authorization
    @ApiOperation("查询风险点详情")
    @PostMapping("/detail")
    public BaseResult<List<RiskPointDetail>> detail(@RequestBody RiskDTORequest request) {
        return BaseResult.success(riskPointService.getDetail(request.getId()));
    }
}