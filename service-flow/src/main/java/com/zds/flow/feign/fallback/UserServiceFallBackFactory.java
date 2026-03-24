package com.zds.flow.feign.fallback;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.flow.FlowCommentReponse;
import com.zds.biz.vo.response.user.BasicDataResponse;
import com.zds.biz.vo.response.user.OrgResponse;
import com.zds.biz.vo.response.user.UserResponse;
import com.zds.biz.vo.response.user.WorkCalendarResponse;
import com.zds.flow.feign.UserService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class UserServiceFallBackFactory implements FallbackFactory<UserService> {

    private Logger log = LoggerFactory.getLogger(UserServiceFallBackFactory.class);

    @Override
    public UserService create(Throwable throwable) {
        log.error("用户服务异常:" + throwable.getMessage());
        return new UserService() {

            @Override
            public BaseResult<List<UserResponse>> findAllUserInfo(String secretKey) {
                return BaseResult.success(new ArrayList<>());
            }

            @Override
            public BaseResult<List<OrgResponse>> findAllOrgInfo(String secretKey) {
                return BaseResult.success(new ArrayList<>());
            }

            @Override
            public BaseResult<List<Long>> findUserListByName(String name) {
                return BaseResult.success(new ArrayList<>());
            }

            @Override
            public BaseResult<List<Long>> findOrgListByName(String organizationName) {
                return BaseResult.success(new ArrayList<>());
            }

            @Override
            public BaseResult<Long> addToList(AdminAddTodoListRequest request) {
                return null;
            }

            @Override
            public BaseResult<Map<Long, String>> findOrgMapById(List<Long> orgIds) {
                return null;
            }

            @Override
            public BaseResult<Map<Long, String>> findUserMapById(List<Long> userIds) {
                return null;
            }

            @Override
            public BaseResult<FlowCommentReponse> examineDetail(IdRequest request) {
                return null;
            }

            @Override
            public BaseResult<WorkCalendarResponse> checkWork(WorkCalendarRequest requset) {
                return null;
            }

            @Override
            public BaseResult<FlowCommentReponse> processedTodoList(FlowCommentRequest request) {
                return BaseResult.success(new FlowCommentReponse());
            }

            @Override
            public BaseResult<String> mockLogin(SyncDataLoginRequest request) {
                return null;
            }

            @Override
            public BaseResult<Map<Long, String>> addToListBatch(AdminAddTodoListBatchRequest request) {
                return null;
            }

            @Override
            public BaseResult<BasicDataResponse> selectByKey(BasicDataRequest request) {
                return BaseResult.failure();
            }
        };
    }
}
