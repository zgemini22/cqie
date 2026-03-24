package com.zds.file.service;

import com.zds.biz.vo.FileInfoVo;
import com.zds.biz.vo.request.file.*;
import com.zds.biz.vo.request.file.Base64FileRequest;
import com.zds.biz.vo.request.file.ByteFileRequest;
import com.zds.biz.vo.request.file.FileDownloadRequest;
import com.zds.biz.vo.request.file.FileRealNameRequest;
import com.zds.biz.vo.response.file.Base64FileResponse;
import com.zds.biz.vo.response.file.FileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件资源服务
 */
public interface FileService {

    /**
     * 上传文件
     */
    FileUploadResponse uploadFile(MultipartFile uploadfile);

    /**
     * 上传文件(Base64)文件名不变
     */
    FileUploadResponse fileUploadUnchange(Base64FileRequest uploadfile);

    /**
     * 上传文件(Base64)
     */
    FileUploadResponse uploadFile(Base64FileRequest uploadfile);

    /**
     * 二进制保存文件
     */
    FileUploadResponse uploadByte(ByteFileRequest request);

    /**
     * 获取文件下载路径
     */
    String getFileLoadUrl();

    /**
     * 获取上传文件最大限制(MB)
     */
    Integer getFileMbMaxSize();

    /**
     * 保存文件(base64)
     */
    FileUploadResponse saveBase64File(String base64, String contentType, String fileName);

    /**
     * 下载文件
     */
    void fileDownload(FileDownloadRequest request, HttpServletResponse response);

    /**
     * 下载文件Base64
     */
    Base64FileResponse fileDownloadBase64(FileDownloadRequest request);

    /**
     * 查询文件信息
     */
    FileInfoVo findByRealName(FileRealNameRequest request);

    /**
     * 数据库备份
     */
    boolean DatabaseBackup(String token);

    /**
     * 指定数据库备份
     */
    boolean DatabaseBackupByName(List<BackupTableRequest> request);
}
