package com.zds.flow.listiner;

import com.zds.flow.dao.TblNodeDepartmentDao;
import com.zds.flow.dao.TblProjectShipDao;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TaskDeleteListener implements TaskListener {
    @Autowired
    private TblProjectShipDao projectShipDao;
    @Autowired
    private TblNodeDepartmentDao nodeDepartmentDao;
    @Autowired
    private HistoryService historyService;

    @Override
    @Transactional
    public void notify(DelegateTask delegateTask) {
        // 由于InfoService和DisPoseService已被删除，此方法现在为空
    }
}
