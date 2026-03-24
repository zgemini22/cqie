package com.zds.flow.service;

import com.zds.biz.vo.request.flow.FlowDeployRequest;
import com.zds.biz.vo.request.flow.FlowProcessDiagramRequest;
import com.zds.biz.vo.response.flow.FlowDeployResponse;
import com.zds.biz.vo.response.flow.FlowXmlDiagramResponse;

public interface FlowRepositoryService {
    /**
     * 保存并部署流程
     * @param flowDeployRequest
     * @return
     */
    FlowDeployResponse deploy(FlowDeployRequest flowDeployRequest);

    /**
     * 获取执行中流程模型XML文件内容
     * @param request
     * @return
     */
    FlowXmlDiagramResponse getProcessModeLXml(FlowProcessDiagramRequest request);

}
