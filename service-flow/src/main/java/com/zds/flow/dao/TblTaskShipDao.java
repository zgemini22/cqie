package com.zds.flow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.flow.po.TblTaskShip;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblTaskShipDao extends BaseMapper<TblTaskShip> {

    String getTaskId(Long todoId);

    String getProjectId(String taskId);

    String getTodoId(String projectId);

    void insertList(List<TblTaskShip> list1);
}
