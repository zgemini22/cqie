package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.user.po.TblUser;
import org.springframework.stereotype.Repository;

@Repository
public interface TblUserDao extends BaseMapper<TblUser> {

    void addLoginFailCount(String loginName);
}
