package com.zds.user.controller.bi;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.TableBase;
import com.zds.biz.vo.request.user.BiKpiNormRequest;
import com.zds.biz.vo.response.user.BiKpiNormListResponse;
import com.zds.biz.vo.response.user.BiKpiNormVo;
import com.zds.user.service.KpiNormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "大屏指标模块")
@RestController
@RequestMapping(value = "/bi/kpi")
public class BiKpiNormController {

    @Autowired
    private KpiNormService kpiNormService;

    @Authorization
    @ApiOperation(value = "查询指定指标信息")
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public BaseResult<BiKpiNormVo> findByCode(@RequestBody BiKpiNormRequest request) {
        return BaseResult.success(kpiNormService.findByCode(request));
    }

    @Authorization
    @ApiOperation(value = "大屏体征指标返回")
    @RequestMapping(value = "/normList", method = RequestMethod.POST)
    public BaseResult<List<BiKpiNormListResponse>> biKpiNormList(@RequestBody IdRequest request) {
        return BaseResult.success(kpiNormService.biKpiNormList(request));
    }

    /**
     * 按月新增所有指标细项
     */
    @ApiIgnore
    @ApiOperation(value = "按月新增所有指标细项")
    @RequestMapping(value = "/allInsert", method = RequestMethod.POST)
    public BaseResult<String> allInsert(@RequestBody String time){
        return BaseResult.judgeOperate(kpiNormService.allInsert(time));
    }

    @ApiOperation(value = "导出kpi数据")
    @RequestMapping(value = "/export/kpi", method = RequestMethod.POST)
    public void openExportKpi(HttpServletResponse response) {
        TableBase data = kpiNormService.openExportKpi();
        data.export(data.getExcelName(), response);
    }
}
