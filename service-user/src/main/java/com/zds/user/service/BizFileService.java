package com.zds.user.service;

import com.zds.user.po.BizFile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BizFileService {
    // 上传
    BizFile uploadFile(BizFile bizFile);

    // + 查询列表
    List<BizFile> queryFileList(BizFile query);

    // + 删除文件
    boolean deleteFile(Long id);

}
