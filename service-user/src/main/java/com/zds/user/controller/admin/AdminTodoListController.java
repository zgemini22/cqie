package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.flow.FlowCommentReponse;
import com.zds.biz.vo.response.flow.FlowFormTypeReponse;
import com.zds.biz.vo.response.user.AdminLoadAllTodoListPageListResponse;
import com.zds.biz.vo.response.user.AdminPointStatisticsResponse;
import com.zds.biz.vo.response.user.AdminUserTodoStatisticsResponse;
import com.zds.user.service.TodoListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Api(tags = "后台待办事项模块")
@RestController
@RequestMapping(value = "/admin/todoList")
public class AdminTodoListController {

    @Autowired
    private TodoListService todoListService;

    @Authorization
    @ApiOperation("新增流程待办事项")
    @RequestMapping(value = "/addToList",method = RequestMethod.POST)
    public BaseResult<Long> addToList(@RequestBody AdminAddTodoListRequest request){
        return BaseResult.success(todoListService.addToList(request));
    }

//    @Authorization
    @ApiOperation("批量新增流程待办事项")
    @RequestMapping(value = "/addToList/batch",method = RequestMethod.POST)
    public BaseResult<Map<Long, String>> addToListBatch(@RequestBody AdminAddTodoListBatchRequest request){
        return BaseResult.success(todoListService.addToListBatch(request));
    }

    @Authorization
    @ApiOperation("新增指定用户的非流程待办事项")
    @RequestMapping(value = "/addNoProcessList",method = RequestMethod.POST)
    public BaseResult<String> addNoProcessList(@RequestBody AdminAddNoProcessListRequest request){
        return BaseResult.success("success",todoListService.addNoProcesList(request));
    }

    @Authorization
    @ApiOperation("新增未指定用户的非流程待办事项")
    @RequestMapping(value = "/noUser/noProcess/add",method = RequestMethod.POST)
    public BaseResult<String> addNoUserNoProcessTodo(@RequestBody AdminAddNoUserNoProcessTodoRequest request){
        return BaseResult.success("success",todoListService.addNoUserNoProcessTodo(request));
    }

    @Authorization
    @ApiOperation("新增事故研判待办事项")
    @RequestMapping(value = "/accident/add",method = RequestMethod.POST)
    public BaseResult<String> addAccidentTodo(@RequestBody AdminAddAccidentTodoRequest request){
        return BaseResult.success(todoListService.addAccidentTodo(request));
    }

    @Authorization
    @ApiOperation("删除待办事项")
    @RequestMapping(value = "/delToList", method = RequestMethod.POST)
    public BaseResult<String> delToList(@RequestBody AdminDelTodoListRequest request){
        return BaseResult.judgeOperate(todoListService.delToList(request));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("根据标识和类型删除待办事项")
    @RequestMapping(value = "/delToListByCode", method = RequestMethod.POST)
    public BaseResult<Boolean> delToListByCode(@RequestBody AdminDelTodoListByCodeRequest request){
        return BaseResult.judgeOperate(todoListService.delToListByCode(request));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("根据标识和类型完成待办事项")
    @RequestMapping(value = "/completeToListByCode", method = RequestMethod.POST)
    public BaseResult<Boolean> completeToListByCode(@RequestBody AdminDelTodoListByCodeRequest request){
        return BaseResult.judgeOperate(todoListService.completeToListByCode(request));
    }

    @Authorization
    @ApiOperation("修改待办事项")
    @RequestMapping(value = "/modifyToList",method = RequestMethod.POST)
    public BaseResult<String> modifyToList(@RequestBody AdminModifyTodoListRequest request){
        return BaseResult.judgeOperate(todoListService.modifyToList(request));
    }

    @Authorization
    @ApiOperation("完成流程类型待办")
    @RequestMapping(value = "/processedTodoList", method = RequestMethod.POST)
    public BaseResult<FlowCommentReponse> processedTodoList(@RequestBody FlowCommentRequest request){
        return BaseResult.success(todoListService.processedTodoList(request));
    }

    @Authorization
    @ApiOperation("用户完成非流程类型待办")
    @RequestMapping(value = "/userNoProcessedTodoList", method = RequestMethod.POST)
    public BaseResult<String> userNoProcessedTodoList(@RequestBody NoProcessTodoComentRequest request){
        return BaseResult.judgeOperate(todoListService.userNoProcessedTodoList(request));
    }

    @Authorization
    @ApiOperation("单位完成非流程类型待办")
    @RequestMapping(value = "/unitNoProcessedTodoList", method = RequestMethod.POST)
    public BaseResult<String> unitNoProcessedTodoList(@RequestBody NoProcessTodoComentRequest request){
        return BaseResult.judgeOperate(todoListService.unitNoProcessedTodoList(request));
    }

    @Authorization
    @ApiOperation("我的待办")
    @RequestMapping(value = "/listAllTodoList", method = RequestMethod.POST)
    public BaseResult<IPage<AdminLoadAllTodoListPageListResponse>> listAllTodoList(@RequestBody AdminLoadAllTodoListPageListRequest request){
        return BaseResult.success(todoListService.listAllTodoList(request));
    }

    @Authorization
    @ApiOperation("当前用户待办完成率")
    @RequestMapping(value = "/user/ratio", method = RequestMethod.POST)
    public BaseResult<BigDecimal> findUserRatio(){
        return BaseResult.success(todoListService.findUserRatio());
    }

    @Authorization
    @ApiOperation("当前用户待办统计")
    @RequestMapping(value = "/user/statistics", method = RequestMethod.POST)
    public BaseResult<AdminUserTodoStatisticsResponse> findUserTodoStatistics(){
        return BaseResult.success(todoListService.findUserTodoStatistics());
    }

    @Authorization
    @ApiOperation("待办id查看审核详情")
    @RequestMapping(value = "/user/examine/detail", method = RequestMethod.POST)
    public BaseResult<FlowCommentReponse> examineDetail(@RequestBody IdRequest request){
        return BaseResult.success(todoListService.examineDetail(request));
    }


    @Authorization
    @ApiOperation("节点审批统计")
    @RequestMapping(value = "/point/statistics", method = RequestMethod.POST)
    public BaseResult<List<AdminPointStatisticsResponse>> findPointTodoStatistics(){
        return BaseResult.success(todoListService.findPointTodoStatistics());
    }

    @Authorization
    @ApiOperation("政府企业可见待办类型下拉")
    @RequestMapping(value = "/type/select", method = RequestMethod.POST)
    public BaseResult<List<FlowFormTypeReponse>> getType(){
        return BaseResult.success(todoListService.getType());
    }
}
