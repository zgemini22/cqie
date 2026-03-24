package com.zds.flow.listiner;


import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 超时提醒监听类
 */
@Slf4j
@Component
public class TimeoutRemindDelegateListener implements JavaDelegate {

    @Autowired
    private TaskService taskService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        log.info("TimeoutRemindDelegate.execute start, execution: {}", execution);


        // 获取当前节点的所有待办任务
        List<Task> taskList = taskService.createTaskQuery()
                .processInstanceId(execution.getProcessInstanceId())
                .list();
        for (Task task: taskList) {
            String assignee = task.getAssignee();
            log.info("assignee:"+assignee);
            // 实现保存记录表，发送语音提醒或短信提醒
        }

        log.info("TimeoutRemindDelegate.execute end");
    }
}
