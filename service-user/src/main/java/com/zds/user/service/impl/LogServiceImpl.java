package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.LogResponse;
import com.zds.biz.vo.LogVo;
import com.zds.biz.vo.PageRequest;
import com.zds.user.dao.TblLogDao;
import com.zds.user.po.TblLog;
import com.zds.user.service.LogService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private TblLogDao tblLogDao;

    @Override
    public Long save(LogVo log) {
        TblLog tblLog = new TblLog();
        BeanUtils.copyProperties(log , tblLog);
        tblLogDao.insert(tblLog);
        return tblLog.getId();
    }

    @Override
    public Boolean update(LogVo log) {
        TblLog tblLog = new TblLog();
        BeanUtils.copyProperties(log , tblLog);
        int i = tblLogDao.updateById(tblLog);
        return i > 0;
    }

    @Override
    public IPage<LogResponse> page(PageRequest request) {
        //构建查询条件
        Page<TblLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<TblLog> queryWrapper = new LambdaQueryWrapper<>();
        //排除已删除的数据
        queryWrapper.eq(TblLog::getDeleted, false);
        Page<TblLog> selectPage = tblLogDao.selectPage(page , queryWrapper);
        //查询并返回
        return selectPage.convert(data -> {
            LogResponse vo = new LogResponse();
            BeanUtils.copyProperties(data, vo);
            return vo;
        });
    }
}
