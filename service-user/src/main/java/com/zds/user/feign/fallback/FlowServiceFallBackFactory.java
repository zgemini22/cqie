package com.zds.user.feign.fallback;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.flow.*;
import com.zds.biz.vo.response.flow.FlowEmergencyListReponse;
import com.zds.biz.vo.response.flow.FlowXmlDiagramResponse;
import com.zds.user.feign.FlowService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FlowServiceFallBackFactory implements FallbackFactory<FlowService> {

    private Logger log = LoggerFactory.getLogger(FlowServiceFallBackFactory.class);

    @Override
    public FlowService create(Throwable throwable) {
        log.error("流程引擎服务异常:" + throwable.getMessage());
        return new FlowService() {

            @Override
            public BaseResult<String> completeAndComment(FlowCommentRequest flowCommentRequest) {
                return null;
            }

            @Override
            public BaseResult<String> deleteTask(Long todoId) {
                return null;
            }

            @Override
            public BaseResult<String> userSaveByService(ServiceUserSaveRequest request) {
                return BaseResult.judgeOperate(true);
            }

            @Override
            public BaseResult<String> orgSaveByService(ServiceOrgSaveRequest request) {
                return BaseResult.judgeOperate(true);
            }

            @Override
            public Map<String, String> getProjectIdBytodoId(List<String> taskId) {
                return new HashMap<>();
            }

            @Override
            public BaseResult<FlowXmlDiagramResponse> getXmlByKey(FlowGetXmlRequest request) {
                return null;
            }


            @Override
            public BaseResult<List<FlowEmergencyListReponse>> getEmergencyProcess() {
                return null;
            }
        };
    }
}
