package com.zds.user.dao;

import com.zds.user.po.TblTodoList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TblTodoListDao extends BaseMapper<TblTodoList> {

    Long insertGetId(TblTodoList tblTodoList);

    int insertList(@Param("list") List<TblTodoList> list);

    void updateList(List<TblTodoList> list);

    List<String> selectUnit();

}
