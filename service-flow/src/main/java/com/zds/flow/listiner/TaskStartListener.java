package com.zds.flow.listiner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zds.biz.vo.request.user.AdminAddTodoListRequest;
import com.zds.flow.dao.TblNodeDepartmentDao;
import com.zds.flow.dao.TblProjectShipDao;
import com.zds.flow.dao.TblTaskShipDao;
import com.zds.flow.feign.UserService;
import com.zds.flow.po.TblNodeDepartment;
import com.zds.flow.po.TblProjectShip;
import com.zds.flow.po.TblTaskShip;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class TaskStartListener implements TaskListener {
    @Autowired
    private TblTaskShipDao taskShipDao;
    @Autowired
    private UserService userService;
    @Autowired
    private TblProjectShipDao projectShipDao;
    @Autowired
    private TblNodeDepartmentDao nodeDepartmentDao;
    @Autowired
    private IdentityService identityService;

    @Override
    @Transactional
    public void notify(DelegateTask delegateTask) {
        LambdaQueryWrapper<TblNodeDepartment> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(TblNodeDepartment::getNodeId, delegateTask.getTaskDefinitionKey());
        TblNodeDepartment flowNodeDepartment = nodeDepartmentDao.selectOne(queryWrapper1);

        LambdaQueryWrapper<TblProjectShip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblProjectShip::getProcessInstanceId, delegateTask.getProcessInstanceId());
        TblProjectShip flowProjectShip = projectShipDao.selectOne(queryWrapper);
        if (ObjectUtils.isNotEmpty(flowProjectShip)){
            Group group = identityService.createGroupQuery().groupMember(delegateTask.getAssignee()).singleResult();
            //添加待办
            Long data = userService.addToList(AdminAddTodoListRequest.builder()
                    .initiatorId(flowProjectShip.getInitiatorId())
                    .assigneeId(delegateTask.getAssignee())
                    .name(delegateTask.getName())
                    .dueTime(flowNodeDepartment.getDueDay())
                    .type(flowProjectShip.getProjectType())
                    .organizationId(group.getId())
                    .dataId(delegateTask.getId()).build()).getData();
            //添加待办与流程关联
            LambdaQueryWrapper<TblTaskShip> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TblTaskShip::getTaskId, delegateTask.getId());
            List<TblTaskShip> flowTaskShips = taskShipDao.selectList(wrapper);
            if (flowTaskShips.isEmpty()) {
                taskShipDao.insert(TblTaskShip.builder()
                        .taskId(delegateTask.getId())
                        .todoId(String.valueOf(data))
                        .projectId(String.valueOf(flowProjectShip.getProjectId())).build());
            }
        }
    }
}
