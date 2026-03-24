package com.zds.user.dao;

import com.zds.user.po.AppVersion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface AppVersionDao extends BaseMapper<AppVersion> {

   void flushHosts();
}
