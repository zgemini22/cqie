package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowAddOutsideFormRequest;
import com.zds.biz.vo.request.flow.FlowOutsideFormRequest;
import com.zds.biz.vo.response.flow.FlowFormTypeReponse;
import com.zds.biz.vo.response.flow.FlowOutsideFormResponse;
import com.zds.user.service.FormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "表单服务")
@RestController
@RequestMapping(value = "/admin/form")
public class AdminFormController {

    @Autowired
    private FormService flowFormService;

    @ApiOperation("保存表单")
    @RequestMapping(value = "/addOutsideFormData", method = RequestMethod.POST)
    public BaseResult<String> outsideFormData(@RequestBody FlowAddOutsideFormRequest request) {
        return BaseResult.judgeOperate(flowFormService.saveForm(request));
    }

    @ApiOperation("表单类型下拉")
    @RequestMapping(value = "/type/select", method = RequestMethod.POST)
    public BaseResult<List<FlowFormTypeReponse>> findFormType() {
        return BaseResult.success(flowFormService.findFormType());
    }

    @ApiOperation("流程表单类型下拉")
    @RequestMapping(value = "/flow/type/select", method = RequestMethod.POST)
    public BaseResult<List<FlowFormTypeReponse>> findFlowFormType() {
        return BaseResult.success(flowFormService.findFlowFormType());
    }

    @ApiOperation("非流程表单类型下拉")
    @RequestMapping(value = "/noflow/type/select", method = RequestMethod.POST)
    public BaseResult<List<FlowFormTypeReponse>> findNoFlowFormType() {
        return BaseResult.success(flowFormService.findNoFlowFormType());
    }

    @ApiOperation("表单类型查询表单")
    @RequestMapping(value = "/getOutsideForm/{type}", method = RequestMethod.POST)
    public BaseResult<String> getOutsideForm(@PathVariable("type") String type) {
        return BaseResult.success("",flowFormService.getOutsideForm(type));
    }

    @ApiOperation("表单分页查询")
    @RequestMapping(value = "/getOutsideForm/list", method = RequestMethod.POST)
    public BaseResult<IPage<FlowOutsideFormResponse>> getOutsideFormList(@RequestBody FlowOutsideFormRequest request) {
        return BaseResult.success(flowFormService.getOutsideFormList(request));
    }

    @ApiIgnore
    @ApiOperation("表单删除")
    @RequestMapping(value = "/getOutsideForm/delete", method = RequestMethod.POST)
    public BaseResult<String> outsideFormDelete(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(flowFormService.outsideFormDelete(request));
    }
}
