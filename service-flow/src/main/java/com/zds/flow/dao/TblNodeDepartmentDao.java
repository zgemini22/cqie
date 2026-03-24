package com.zds.flow.dao;

import com.zds.biz.vo.request.flow.FlowNodeDepartmentRequest;
import com.zds.flow.po.TblNodeDepartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TblNodeDepartmentDao extends BaseMapper<TblNodeDepartment> {

    void batchUpdate(@Param("list") List<FlowNodeDepartmentRequest> updateParamsList);

    void insertList(@Param("list") List<FlowNodeDepartmentRequest> nodeDepartment);
}
