package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.LogResponse;
import com.zds.biz.vo.LogVo;
import com.zds.biz.vo.PageRequest;

/**
 * 接口日志服务
 */
public interface LogService {

    /**
     * 接口调用日志保存
     */
    Long save(LogVo log);

    /**
     * 口调用日志修改
     */
    Boolean update(LogVo log);

    /**
     * 接口调用日志分页查询
     */
    IPage<LogResponse> page(PageRequest request);
}
