package com.zds.flow.service;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.flow.FlowUserRequest;
import com.zds.biz.vo.response.flow.FlowGroupListResponse;
import com.zds.biz.vo.response.flow.FlowGroupsUserResponse;

import java.util.List;

public interface FlowUserService {
    /**
     * 查询所有部门
     * @return
     */
    List<FlowGroupListResponse> groupList();

    /**
     * 查询部门下所有员工id
     * @param id
     * @return
     */
    List<FlowGroupsUserResponse> groupDetail(String id);

    /**
     * 查询用户列表
     * @return
     */
    List<FlowGroupsUserResponse> userList();

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    FlowGroupsUserResponse userDetail(String id);

    /**
     * 添加用户
     * @param param
     * @return
     */
    Boolean create(FlowUserRequest param);

    /**
     * 修改用户信息
     * @param param
     * @return
     */
    Boolean update(FlowUserRequest param);
}
