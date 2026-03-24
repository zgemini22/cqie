package com.zds.flow.controller;


import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.flow.FlowUserRequest;
import com.zds.biz.vo.request.flow.ServiceOrgSaveRequest;
import com.zds.biz.vo.request.flow.ServiceUserSaveRequest;
import com.zds.biz.vo.response.flow.FlowGroupListResponse;
import com.zds.biz.vo.response.flow.FlowGroupsUserResponse;
import com.zds.flow.service.FlowUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RequestMapping(value = "/flow")
@RestController
@Api(tags = "用户服务")
public class UserController {
    @Autowired
    private IdentityService identityService;
    @Autowired
    private FlowUserService flowUserService;

    // 用户列表
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/user/list", method = RequestMethod.POST)
    @ApiOperation("查询用户列表")
    public BaseResult<List<FlowGroupsUserResponse>> userList() {
        return BaseResult.success(flowUserService.userList());
    }

    // 查询用户
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/user/detail/{id}", method = RequestMethod.POST)
    @ApiOperation("根据id查询用户")
    public BaseResult<FlowGroupsUserResponse> userDetail(@PathVariable("id") String id) {
        return BaseResult.success(flowUserService.userDetail(id));
    }

    // 添加用户
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    @ApiOperation("添加用户")
    public BaseResult<Boolean> create(@RequestBody FlowUserRequest param) {
        return BaseResult.success(flowUserService.create(param));
    }

    // 修改用户信息
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    @ApiOperation("修改用户信息")
    public BaseResult<Boolean> update(@RequestBody FlowUserRequest param) {
        return  BaseResult.success(flowUserService.update(param));
    }


    // 删除用户
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/user/delete/{id}", method = RequestMethod.POST)
    @ApiOperation("根据id删除用户")
    public String delete(@PathVariable("id") String id) {
        identityService.deleteUser(id);
        return id;
    }

    // 组列表
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/group/list", method = RequestMethod.POST)
    @ApiOperation("查询所有组")
    public BaseResult<List<FlowGroupListResponse>> groupList() {
        List<FlowGroupListResponse> responses = flowUserService.groupList();
        return BaseResult.success(responses);
    }


    // 查询组下所有用户
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/group/detail/{id}", method = RequestMethod.POST)
    @ApiOperation("查询组下所有用户")
    public BaseResult<List<FlowGroupsUserResponse>> groupDetail(@PathVariable("id") String id) {
        List<FlowGroupsUserResponse> responses = flowUserService.groupDetail(id);
        return BaseResult.success(responses);
    }

    // 添加组
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/group/create", method = RequestMethod.POST)
    @ApiOperation("添加组")
    public String groupCreate(@RequestBody Group param) {
        final Group exist = identityService.createGroupQuery().groupId(param.getId()).singleResult();
        if (exist != null) {
            return "该组已存在：" + param.getId();
        }

        GroupEntity entity = new GroupEntity();
        entity.setId(param.getId());
        entity.setName(param.getName());
        entity.setType(param.getType());
        identityService.saveGroup(entity);

        return param.getId();
    }

    // 修改组信息
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/group/update", method = RequestMethod.POST)
    @ApiOperation("修改组信息")
    public String groupUpdate(@RequestBody Group param) {
        final Group group = identityService.createGroupQuery().groupId(param.getId()).singleResult();
        if (group == null) {
            return "该组不存在：" + param.getId();
        }
        group.setName(param.getName());
        group.setType(param.getType());
        identityService.saveGroup(group);
        return group.getId();
    }

    // 删除组
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/group/delete/{id}", method = RequestMethod.POST)
    @ApiOperation("删除组")
    public String groupDelete(@PathVariable("id") String id) {
        identityService.deleteGroup(id);
        return id;
    }

    // 将用户添加到组中
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/user/group/relation/{userId}/{groupId}", method = RequestMethod.POST)
    @ApiOperation("将用户添加到组中")
    public String userGroupRelation(@PathVariable("userId") String userId,
                                    @PathVariable("groupId") String groupId) {
        String error = checkUserGroupExist(userId, groupId);
        if (error != null) {
            return error;
        }

        final User exist = identityService.createUserQuery().memberOfGroup(groupId).userId(userId).singleResult();
        if (exist != null) {
            return "该用户与组已关联";
        }

        identityService.createMembership(userId, groupId);
        return "请求成功";
    }

    // 从组中删除用户
    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/user/group/delete/{userId}/{groupId}", method = RequestMethod.POST)
    @ApiOperation("从组中删除用户")
    public String userGroupDelete(@PathVariable("userId") String userId,
                                  @PathVariable("groupId") String groupId) {
        String error = checkUserGroupExist(userId, groupId);
        if (error != null) {
            return error;
        }
        identityService.deleteMembership(userId, groupId);
        return "请求成功";
    }

    /**
     * 检查用户组
     */
    private String checkUserGroupExist(String userId, String groupId) {
        final User user = identityService.createUserQuery().userId(userId).singleResult();
        final Group group = identityService.createGroupQuery().groupId(groupId).singleResult();

        if (userId != null && user == null) {
            return "该用户不存在：" + userId;
        }

        if (groupId != null && group == null) {
            return "该组不存在：" + groupId;
        }

        return null;
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("同步用户保存")
    @RequestMapping(value = "/service/user/save", method = RequestMethod.POST)
    public BaseResult<String> userSaveByService(@RequestBody ServiceUserSaveRequest request) {
        // 检查用户是否存在
        User user = identityService.createUserQuery().userId(request.getUserId()).singleResult();
        if(ObjectUtils.isEmpty(user)){
            //保存用户
            UserEntity entity = new UserEntity();
            entity.setId(request.getUserId());
            entity.setFirstName(request.getName());
            identityService.saveUser(entity);
            //保存用户与组关联
            final User exist = identityService.createUserQuery().memberOfGroup(request.getOrgId()).userId(request.getUserId()).singleResult();
            if (exist == null) {
                identityService.createMembership(request.getUserId(), request.getOrgId());
            }
        }else {
            user.setFirstName(request.getName());
            identityService.saveUser(user);

            final User exist = identityService.createUserQuery().memberOfGroup(request.getOrgId()).userId(request.getUserId()).singleResult();
            // 如果用户已经关联到该组织，且组织ID发生变化，更新关联
            if (!exist.getId().equals(request.getOrgId())) {
                // 先删除现有的关联
                List<Group> groups = identityService.createGroupQuery().groupMember(request.getUserId()).list();
                for (Group group : groups) {
                    identityService.deleteMembership(request.getUserId(), group.getId());
                }
                // 创建新的关联
                identityService.createMembership(request.getUserId(), request.getOrgId());
            }
        }

        return BaseResult.judgeOperate(true);
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("同步单位保存")
    @RequestMapping(value = "/service/org/save", method = RequestMethod.POST)
    public BaseResult<String> orgSaveByService(@RequestBody ServiceOrgSaveRequest request) {
        Group group = identityService.createGroupQuery().groupId(request.getOrgId()).singleResult();
        //保存单位
        if(ObjectUtils.isEmpty(group)){
            GroupEntity entity = new GroupEntity();
            entity.setId(request.getOrgId());
            entity.setName(request.getName());
            entity.setType(request.getParentId());
            identityService.saveGroup(entity);
        }else {
            group.setName(request.getName());
            identityService.saveGroup(group);
        }

        return BaseResult.judgeOperate(true);
    }
}
