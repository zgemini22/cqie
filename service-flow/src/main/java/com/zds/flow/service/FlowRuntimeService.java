package com.zds.flow.service;

import com.zds.biz.vo.request.flow.FlowAddSignRequest;
import com.zds.biz.vo.request.flow.FlowEnumStartProcessRequest;
import com.zds.biz.vo.request.flow.FlowStartProcessRequest;
import com.zds.biz.vo.response.flow.FlowStartResponse;

public interface FlowRuntimeService {
    /**
     * 后端调用通过流程定义KEY启动流程
     */
    FlowStartResponse startProcessInstanceByKey(FlowStartProcessRequest flowStartProcessRequest);

    /**
     * 后端调用通过项目枚举启动流程
     */
    Boolean startProcessInstanceByEnum(FlowEnumStartProcessRequest request);

    /**
     * 终止流程
     * @param processInstanceId
     */
    void stopProcessInstance(String processInstanceId);

    /**
     * 节点加签
     * @param request
     */
    void addSign(FlowAddSignRequest request);


    /**
     * 自动审批
     */
    Boolean autoEnd() throws InterruptedException;


}
