package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zds.user.dao.BizFileInfoDao;
import com.zds.user.po.BizFile;
import com.zds.user.service.BizFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BizFileServiceImpl implements BizFileService {

    @Autowired
    private BizFileInfoDao bizFileInfoDao;

    // 上传（修复：返回正确数据，不返回null）
    @Override
    public BizFile uploadFile(BizFile bizFile) {
        bizFileInfoDao.insert(bizFile);
        // 修复：必须返回插入的对象
        return bizFile;
    }

    // + 查询文件列表（你之前完全没写！！）
    @Override
    public List<BizFile> queryFileList(BizFile query) {
        LambdaQueryWrapper<BizFile> wrapper = Wrappers.lambdaQuery(BizFile.class)
                .eq(BizFile::getBizId, query.getBizId())
                .eq(BizFile::getBizType, query.getBizType())
                .eq(BizFile::getDeleted, false)
                .orderByDesc(BizFile::getCreateTime);

        return bizFileInfoDao.selectList(wrapper);
    }

    // + 删除文件（逻辑删除）
    @Override
    public boolean deleteFile(Long id) {
        LambdaUpdateWrapper<BizFile> wrapper = Wrappers.lambdaUpdate(BizFile.class)
                .eq(BizFile::getId, id)
                .set(BizFile::getDeleted, true);

        return bizFileInfoDao.update(null, wrapper) > 0;
    }
}