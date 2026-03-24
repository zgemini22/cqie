package com.zds.flow.service.impl;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.request.flow.FlowUserRequest;
import com.zds.biz.vo.response.flow.FlowGroupListResponse;
import com.zds.biz.vo.response.flow.FlowGroupsUserResponse;
import com.zds.flow.service.FlowUserService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FlowUserServiceImpl implements FlowUserService {

    @Autowired
    private IdentityService identityService;

    @Override
    public List<FlowGroupListResponse> groupList() {
        List<Group> list = identityService.createGroupQuery().list();
        List<FlowGroupListResponse> reponses = new ArrayList<>();
        for (Group group : list) {
            reponses.add(FlowGroupListResponse.builder()
                    .name(group.getName())
                    .Id(group.getId())
                    //type存放父级id
                    .parentId(group.getType())
                    .build());
        }
        return reponses;
    }

    @Override
    public List<FlowGroupsUserResponse> groupDetail(String id) {

        Group group = identityService.createGroupQuery().groupId(id).singleResult();
        List<User> userList = identityService.createUserQuery().memberOfGroup(group.getId()).list();

        List<FlowGroupsUserResponse> responses = new ArrayList<>();
        if (!userList.isEmpty()) {
            for (User user : userList) {
                FlowGroupsUserResponse userResponse = new FlowGroupsUserResponse();
                userResponse.setId(user.getId());
                userResponse.setName(user.getLastName() + user.getFirstName());
                responses.add(userResponse);
            }
        }
        return responses;
    }

    @Override
    public List<FlowGroupsUserResponse> userList() {
        List<User> userList = identityService.createUserQuery().list();
        List<FlowGroupsUserResponse> responses = new ArrayList<>();
        if (!userList.isEmpty()) {
            for (User user : userList) {
                FlowGroupsUserResponse userResponse = new FlowGroupsUserResponse();
                userResponse.setId(user.getId());
                userResponse.setName(user.getLastName() + user.getFirstName());
                responses.add(userResponse);
            }
        }
        return responses;
    }

    @Override
    public FlowGroupsUserResponse userDetail(String id) {
        User user = identityService.createUserQuery().userId(id).singleResult();
        FlowGroupsUserResponse userResponse = new FlowGroupsUserResponse();

        if (user != null) {
            userResponse.setId(user.getId());
            userResponse.setName(user.getLastName() + user.getFirstName());
        }
        return userResponse;
    }

    @Override
    public Boolean create(FlowUserRequest param) {
        final User exist = identityService.createUserQuery().userId(param.getId()).singleResult();
        if (exist != null) {
            throw new BaseException("用户已存在");
        }

        UserEntity entity = new UserEntity();
        entity.setId(param.getId());
        entity.setFirstName(param.getFirstName());
        entity.setLastName(param.getLastName());
        entity.setEmail(param.getEmail());
        entity.setPassword(param.getPassword());
        identityService.saveUser(entity);
        return true;
    }

    @Override
    public Boolean update(FlowUserRequest param) {

        final User user = identityService.createUserQuery().userId(param.getId()).singleResult();
        if (user == null) {
            throw new BaseException("该用户不存在");
        }
        user.setFirstName(param.getFirstName());
        user.setLastName(param.getLastName());
        user.setEmail(param.getEmail());
        identityService.saveUser(user);
        return true;
    }
}
