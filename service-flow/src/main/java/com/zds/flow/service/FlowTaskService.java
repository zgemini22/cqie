package com.zds.flow.service;

import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.flow.FlowProcessDetailRequest;
import com.zds.biz.vo.request.flow.FlowProcessUnitRequest;
import com.zds.biz.vo.response.flow.FlowCommentResponse;
import com.zds.biz.vo.response.flow.FlowProcessCompletionDegreeResponse;
import com.zds.biz.vo.response.flow.FlowProcessDetailResponse;
import com.zds.biz.vo.response.flow.GasReviewTaskResponse;

import java.util.List;
import java.util.Map;

public interface FlowTaskService {
    /**
     *  根据ID获取审批意见
     */
    FlowCommentResponse getTaskComments(String taskId);

    /**
     * 流程详情查询
     */
    List<FlowProcessDetailResponse> details(FlowProcessDetailRequest request);

    /**
     *根据ID完成待办并添加审批意见
     */
    void completeAndComment(FlowCommentRequest flowCommentRequest);


    /**
     * 查询流程完成度
     * @param request
     * @return
     */
    FlowProcessCompletionDegreeResponse completionDegree(FlowProcessDetailRequest request);

    /**
     * 后端查询当前流程审核部门
     *
     * @param request
     * @return
     */
    Map<Long,List<String>> getProcessUnit(List<FlowProcessUnitRequest> request);

    /**
     * 查询当前用户是否为管理员账号权限或流程指定人
     *
     * @param request
     * @return
     */
    Map<Long , GasReviewTaskResponse> auditPermission(List<FlowProcessDetailRequest> request);

}
