package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.PageRequest;
import com.zds.biz.vo.request.user.BiFileSaveRequest;
import com.zds.biz.vo.response.user.BiFileListResponse;

/**
 * 大屏文件资源模块
 */
public interface BiFileService {

    IPage<BiFileListResponse> findList(PageRequest request);

    BiFileListResponse findDetail(IdRequest request);

    boolean save(BiFileSaveRequest request);

    boolean delete(IdRequest request);
}
