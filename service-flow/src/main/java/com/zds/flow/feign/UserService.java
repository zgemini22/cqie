package com.zds.flow.feign;

import com.zds.biz.constant.SecretKeyEnum;
import com.zds.biz.interceptor.FeignRequestInterceptor;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.flow.FlowCommentReponse;
import com.zds.biz.vo.response.user.BasicDataResponse;
import com.zds.biz.vo.response.user.OrgResponse;
import com.zds.biz.vo.response.user.UserResponse;
import com.zds.biz.vo.response.user.WorkCalendarResponse;
import com.zds.flow.feign.fallback.UserServiceFallBackFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@FeignClient(value = "service-user", configuration = FeignRequestInterceptor.class, fallbackFactory = UserServiceFallBackFactory.class, url = "#{'${feignurl.isOpen}'?'${feignurl.serviceUser:}':''}")
public interface UserService {

    /**
     * 查询所有用户信息
     * @param secretKey 服务间通信用密钥
     * {@link SecretKeyEnum#getKey()}
     */
    @RequestMapping(value = "/admin/user/info/all", method = RequestMethod.POST)
    BaseResult<List<UserResponse>> findAllUserInfo(@RequestBody String secretKey);

    /**
     * 查询所有单位信息
     * @param secretKey 服务间通信用密钥
     * {@link SecretKeyEnum#getKey()}
     */
    @RequestMapping(value = "/admin/org/info/all", method = RequestMethod.POST)
    BaseResult<List<OrgResponse>> findAllOrgInfo(@RequestBody String secretKey);

    /**
     * 用户名称模糊查询用户ID集合
     * @param name 用户名
     * @return List<Long>
     */
    @RequestMapping(value = "/admin/user/name/like/list", method = RequestMethod.POST)
    BaseResult<List<Long>> findUserListByName(@RequestBody String name);

    /**
     * 单位名称模糊查询单位ID集合
     * @param organizationName 单位名称
     * @return List<Long>
     */
    @RequestMapping(value = "/admin/org/name/like/list", method = RequestMethod.POST)
    BaseResult<List<Long>> findOrgListByName(@RequestBody String organizationName);


    /**
     *新增待办事项
     */
    @RequestMapping(value = "/admin/todoList/addToList",method = RequestMethod.POST)
    BaseResult<Long> addToList(@RequestBody AdminAddTodoListRequest request);

    /**
     * 查询指定单位范围的单位名称
     * @param orgIds 单位ID集合
     * @return Map<organizationId, organizationName>
     */
    @RequestMapping(value = "/admin/org/map", method = RequestMethod.POST)
    BaseResult<Map<Long, String>> findOrgMapById(@RequestBody List<Long> orgIds);

    /**
     * 查询指定用户范围的用户名称
     * @param userIds 用户ID集合
     * @return Map<userId, name>
     */
    @RequestMapping(value = "/admin/user/map", method = RequestMethod.POST)
    BaseResult<Map<Long, String>> findUserMapById(@RequestBody List<Long> userIds);

    /**
     * 待办id查看审核详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/todoList/user/examine/detail", method = RequestMethod.POST)
    BaseResult<FlowCommentReponse> examineDetail(@RequestBody IdRequest request);

    /**
     * 根据规定工作日计算业务完成状态
     * @param requset
     * @return
     */
    @ApiOperation("根据规定工作日计算业务完成状态")
    @RequestMapping(value = "/admin/calendar/check/work", method = RequestMethod.POST)
    BaseResult<WorkCalendarResponse> checkWork(@RequestBody WorkCalendarRequest requset);

    /**
     * 完成待办
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/todoList/processedTodoList", method = RequestMethod.POST)
    BaseResult<FlowCommentReponse> processedTodoList(@RequestBody FlowCommentRequest request);

    /**
     * 同步数据-模拟登录获取token
     */
    @RequestMapping(value = "/admin/user/syncData/mockLogin", method = RequestMethod.POST)
    BaseResult<String> mockLogin(@RequestBody SyncDataLoginRequest request);

    /**
     * 批量新增流程待办事项
     */
    @RequestMapping(value = "/admin/todoList/addToList/batch",method = RequestMethod.POST)
    BaseResult<Map<Long, String>> addToListBatch(@RequestBody AdminAddTodoListBatchRequest request);

    /**
     * 根据数据标识查询基础数据
     */
    @RequestMapping(value = "/admin/basic/select/key", method = RequestMethod.POST)
    BaseResult<BasicDataResponse> selectByKey(@RequestBody BasicDataRequest request);
}
