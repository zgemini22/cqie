package com.zds.user.feign;

import com.zds.biz.interceptor.FeignRequestInterceptor;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.flow.FlowGetXmlRequest;
import com.zds.biz.vo.request.flow.ServiceOrgSaveRequest;
import com.zds.biz.vo.request.flow.ServiceUserSaveRequest;
import com.zds.biz.vo.response.flow.FlowEmergencyListReponse;
import com.zds.biz.vo.response.flow.FlowXmlDiagramResponse;
import com.zds.user.feign.fallback.FlowServiceFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(value = "service-flow", configuration = FeignRequestInterceptor.class, fallbackFactory = FlowServiceFallBackFactory.class, url = "#{'${feignurl.isOpen}'?'${feignurl.serviceFlow:}':''}")
public interface FlowService {

    @RequestMapping(value = "/flow/task/completeAndComment", method = RequestMethod.POST)
    BaseResult<String> completeAndComment(@RequestBody FlowCommentRequest flowCommentRequest);

    @RequestMapping(value = "/flow/task/deleteTask", method = RequestMethod.POST)
    BaseResult<String> deleteTask(@RequestBody Long todoId);

    @RequestMapping(value = "/flow/service/user/save", method = RequestMethod.POST)
    BaseResult<String> userSaveByService(@RequestBody ServiceUserSaveRequest request);

    @RequestMapping(value = "/flow/service/org/save", method = RequestMethod.POST)
    BaseResult<String> orgSaveByService(@RequestBody ServiceOrgSaveRequest request);

    @RequestMapping(value = "/flow/task/getProjectId", method = RequestMethod.POST)
    Map<String , String> getProjectIdBytodoId(@RequestBody List<String> taskId);

    @RequestMapping(value = "/flow/repository/getXml/key", method = RequestMethod.POST)
    BaseResult<FlowXmlDiagramResponse> getXmlByKey(@RequestBody FlowGetXmlRequest request);

    @RequestMapping(value = "/flow/repository/getEmergencyProcess", method = RequestMethod.POST)
    BaseResult<List<FlowEmergencyListReponse>> getEmergencyProcess();
}
