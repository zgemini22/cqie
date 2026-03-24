package com.zds.flow.listiner;

import com.zds.flow.dao.TblNodeDepartmentDao;
import com.zds.flow.dao.TblProjectShipDao;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EndProcessListener implements ExecutionListener {

    @Autowired
    private TblProjectShipDao projectShipDao;
    @Autowired
    private TblNodeDepartmentDao nodeDepartmentDao;
    @Autowired
    private HistoryService historyService;

    @Override
    @Transactional
    public void notify(DelegateExecution execution) {
        // 由于InfoService和DisPoseService已被删除，此方法现在为空
    }
}
