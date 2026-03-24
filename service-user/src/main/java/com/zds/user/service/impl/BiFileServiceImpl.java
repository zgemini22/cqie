package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.FileInfoVo;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.PageRequest;
import com.zds.biz.vo.request.file.FileRealNameRequest;
import com.zds.biz.vo.request.user.BiFileSaveRequest;
import com.zds.biz.vo.response.user.BiFileListResponse;
import com.zds.user.dao.TblBiFileDao;
import com.zds.user.feign.FileService;
import com.zds.user.po.TblBiFile;
import com.zds.user.service.BiFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BiFileServiceImpl implements BiFileService {

    @Autowired
    private TblBiFileDao biFileDao;

    @Autowired
    private FileService fileService;

    @Override
    public IPage<BiFileListResponse> findList(PageRequest request) {
        return biFileDao.selectPage(new Page<>(request.getPageNum(), request.getPageSize()), TblBiFile.getWrapper().eq(TblBiFile::getDeleted, false))
                .convert(po -> {
                    BiFileListResponse vo = new BiFileListResponse();
                    BeanUtils.copyProperties(po, vo);
                    return vo;
                });
    }

    @Override
    public BiFileListResponse findDetail(IdRequest request) {
        TblBiFile po = biFileDao.selectById(request.getId());
        BiFileListResponse vo = new BiFileListResponse();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    @Override
    public boolean save(BiFileSaveRequest request) {
        TblBiFile po = request.getId() == null ? new TblBiFile() : biFileDao.selectById(request.getId());
        BeanUtils.copyProperties(request, po);
        FileInfoVo fileInfo = fileService.findByRealName(FileRealNameRequest.builder().realFileName(request.getFileRealName()).build()).getData();
        if (fileInfo != null) {
            po.setFileSize(fileInfo.getRealFileSize());
        }
        int count = request.getId() == null ? biFileDao.insert(po) : biFileDao.updateById(po);
        return count == 1;
    }

    @Override
    public boolean delete(IdRequest request) {
        return biFileDao.updateById(TblBiFile.builder()
                .id(request.getId())
                .deleted(true)
                .build()) == 1;
    }
}
