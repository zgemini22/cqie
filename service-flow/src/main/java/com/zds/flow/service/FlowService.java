package com.zds.flow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.constant.flow.ProcessTypeEnum;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.*;
import com.zds.biz.vo.response.flow.FlowDeployListReponse;
import com.zds.biz.vo.response.flow.FlowEmergencyListReponse;
import com.zds.biz.vo.response.flow.FlowProcessTypeReponse;
import com.zds.biz.vo.response.flow.FlowXmlDiagramResponse;

import java.util.List;
import java.util.Map;

public interface FlowService {
   /**
    * 流程部署列表分页查询
    */
   IPage<FlowDeployListReponse> deployList(FlowDeployListRequest request);

   /**
    * 部署草稿列表（企业）
    */
   IPage<FlowDeployListReponse> deployListEnterprise(FlowDeployListRequest request);

   /**
    * 流程草稿保存
    */
   Boolean deploySave(FlowDeployAddRequest request);

   /**
    * 流程保存并部署
    */
   void deployAndSave(FlowDeployRequest flowDeployRequest);

   /**
    * 流程草稿删除
    */
   Boolean deployDelete(IdRequest request);

   /**
    * 查询所有流程类型
    */
   List<FlowProcessTypeReponse> getProcessType();

   /**
    * 企业部署流程类型下拉
    */
   List<FlowProcessTypeReponse> getProcessTypeEnterprise();

   /**
    * 查询流程草稿xml
    */
   FlowXmlDiagramResponse getXml(IdRequest request);

   /**
    * 流程草稿启动流程
    */
   Boolean startProcessInstanceByDraft(IdRequest request);

   /**
    * 获取工程XML文件内容
    */
   FlowXmlDiagramResponse getProjectProcessDiagram(FlowProcessDetailRequest request);

   /**
    * 查询待办Id
    */
   String getTaskId(Long todoId);

   /**
    * 应急流程下拉
    */
    List<FlowEmergencyListReponse> getEmergencyProcess();

   /**
    * 查询停气供气企业流程
    */
   String getGasProcess(String request);

   /**
    * 通过待办id查询项目id
    */
   Map<String , String> getProjectIdBytodoId(List<String> taskId);
   /**
    * 项目id通过查询待办id
    */
   List<Long> getTodoId(String projectId);

   /**
    * 根据流程key查询xml
    */
   FlowXmlDiagramResponse getXmlByKey(FlowGetXmlRequest request);

}
