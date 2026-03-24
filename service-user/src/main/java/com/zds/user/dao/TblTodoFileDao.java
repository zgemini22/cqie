package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.user.po.TblTodoFile;
import com.zds.user.po.TblTodoList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblTodoFileDao extends BaseMapper<TblTodoFile> {

    void insertList(List<TblTodoFile> list);
}
