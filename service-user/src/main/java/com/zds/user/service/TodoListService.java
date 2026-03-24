package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.flow.FlowGetXmlRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.flow.FlowCommentReponse;
import com.zds.biz.vo.response.flow.FlowEmergencyListReponse;
import com.zds.biz.vo.response.flow.FlowFormTypeReponse;
import com.zds.biz.vo.response.flow.FlowXmlDiagramResponse;
import com.zds.biz.vo.response.user.AdminLoadAllTodoListPageListResponse;
import com.zds.biz.vo.response.user.AdminPointStatisticsResponse;
import com.zds.biz.vo.response.user.AdminUserTodoStatisticsResponse;
import com.zds.biz.vo.response.user.ClientUserTodoDetailResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 待办事项服务
 */
public interface TodoListService {

    /**
     * 新增待办事项
     *
     * @param request
     * @return
     */
    Long addToList(AdminAddTodoListRequest request);

    /**
     * 删除待办事项
     * @param request
     * @return
     */
    boolean delToList(AdminDelTodoListRequest request);

    /**
     * 修改待办事项
     * @param request
     * @return
     */
    boolean modifyToList(AdminModifyTodoListRequest request);

    /**
     * 完成待办
     *
     * @param request
     * @return
     */
    FlowCommentReponse processedTodoList(FlowCommentRequest request);

    /**
     * 条件分页查询待办事项
     * @param request
     * @return
     */
    IPage<AdminLoadAllTodoListPageListResponse> listAllTodoList(AdminLoadAllTodoListPageListRequest request);

    /**
     * 非流程新增待办
     * @param request
     * @return
     */
    String addNoProcesList(AdminAddNoProcessListRequest request);

    /**
     * 未指定用户非流程新增待办
     */
    String addNoUserNoProcessTodo(AdminAddNoUserNoProcessTodoRequest request);

    /**
     * 新增事故研判待办事项
     */
    String addAccidentTodo(AdminAddAccidentTodoRequest request);

    /**
     * 当前用户待办完成率
     */
    BigDecimal findUserRatio();

    /**
     * 当前用户待办统计
     */
    AdminUserTodoStatisticsResponse findUserTodoStatistics();

    /**
     *  项目id查看审批意见
     */
    FlowCommentReponse examineDetail(IdRequest request);

    /**
     * 根据标识和类型删除待办事项
     */
    boolean delToListByCode(AdminDelTodoListByCodeRequest request);

    /**
     * 用户完成非流程类型待办待办
     */
    boolean userNoProcessedTodoList(NoProcessTodoComentRequest request);
    /**
     * 单位完成非流程类型待办待办
     */
    boolean unitNoProcessedTodoList(NoProcessTodoComentRequest request);

    /**
     * 政府企业可见待办类型下拉
     */
    List<FlowFormTypeReponse> getType();

    /**
     * 查询处置方案流程图
     */
    FlowXmlDiagramResponse accidentProcess(FlowGetXmlRequest request);

    /**
     * 待办id查看事故处理详情
     */
    ClientUserTodoDetailResponse accidentDetail(IdRequest request);

    /**
     * 处置方案下拉
     */
    List<FlowEmergencyListReponse> getEmergencyProcess();

    /**
     * 事故处理
     */
    boolean accidentHand(AccidentHandlingRequest request);

    /**
     * 根据标识和类型完成待办事项
     */
    boolean completeToListByCode(AdminDelTodoListByCodeRequest request);

    /**
     * 节点审批统计
     */
    List<AdminPointStatisticsResponse> findPointTodoStatistics();

    /**
     * 批量新增流程待办事项
     *
     * @param request
     * @return
     */
    Map<Long, String> addToListBatch(AdminAddTodoListBatchRequest request);
}
