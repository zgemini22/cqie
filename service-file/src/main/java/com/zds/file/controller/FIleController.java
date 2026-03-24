package com.zds.file.controller;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.FileInfoVo;
import com.zds.biz.vo.request.file.*;
import com.zds.biz.vo.request.file.Base64FileRequest;
import com.zds.biz.vo.request.file.ByteFileRequest;
import com.zds.biz.vo.request.file.FileDownloadRequest;
import com.zds.biz.vo.request.file.FileRealNameRequest;
import com.zds.biz.vo.response.file.Base64FileResponse;
import com.zds.biz.vo.response.file.FileUploadResponse;
import com.zds.file.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "文件服务模块")
@RestController
@RequestMapping(value = "/file")
public class FIleController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public BaseResult<FileUploadResponse> fileUpload(@RequestParam(value = "file") MultipartFile file) {
        return BaseResult.success(fileService.uploadFile(file));
    }

    @ApiOperation(value = "上传文件(Base64)文件名不变")
    @RequestMapping(value = "/upload/base64/unchange", method = RequestMethod.POST)
    public BaseResult<FileUploadResponse> fileUploadUnchange(@RequestBody Base64FileRequest file) {
        return BaseResult.success(fileService.fileUploadUnchange(file));
    }

    @ApiOperation(value = "上传文件(Base64)")
    @RequestMapping(value = "/upload/base64", method = RequestMethod.POST)
    public BaseResult<FileUploadResponse> fileUpload(@RequestBody Base64FileRequest file) {
        return BaseResult.success(fileService.uploadFile(file));
    }

    @ApiOperation(value = "byte上传文件")
    @RequestMapping(value = "/byte/upload", method = RequestMethod.POST)
    public BaseResult<FileUploadResponse> byteFileUpload(@RequestBody ByteFileRequest request) {
        return BaseResult.success(fileService.uploadByte(request));
    }

    @ApiOperation(value = "获取文件下载路径")
    @RequestMapping(value = "/load/prefix", method = RequestMethod.POST)
    public BaseResult<String> getLoadPrefix() {
        return BaseResult.success("success", fileService.getFileLoadUrl());
    }

    @ApiOperation(value = "获取上传文件最大限制(MB)")
    @RequestMapping(value = "/upload/max/size", method = RequestMethod.POST)
    public BaseResult<Integer> getFileMbMaxSize() {
        return BaseResult.success("success", fileService.getFileMbMaxSize());
    }

    @ApiOperation(value = "下载文件")
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void fileDownload(@RequestBody FileDownloadRequest request, HttpServletResponse response) {
        fileService.fileDownload(request, response);
    }

    @ApiOperation(value = "下载文件(Base64)")
    @RequestMapping(value = "/download/base64", method = RequestMethod.POST)
    public BaseResult<Base64FileResponse> fileDownloadBase64(@RequestBody FileDownloadRequest request) {
        return BaseResult.success(fileService.fileDownloadBase64(request));
    }

    @ApiIgnore
    @ApiOperation(value = "上传文件(feign)")
    @RequestMapping(value = "/feign/upload", method = RequestMethod.POST)
    public BaseResult<FileUploadResponse> uploadByFeign(@RequestPart(value = "file") MultipartFile file) {
        return BaseResult.success(fileService.uploadFile(file));
    }

    @ApiIgnore
    @ApiOperation(value = "查询文件信息(feign)")
    @RequestMapping(value = "/feign/findByRealName", method = RequestMethod.POST)
    public BaseResult<FileInfoVo> findByRealName(@RequestBody FileRealNameRequest request) {
        return BaseResult.success(fileService.findByRealName(request));
    }

    @ApiOperation(value = "数据库备份")
    @RequestMapping(value = "/database/backup", method = RequestMethod.GET)
    public BaseResult<String> DatabaseBackup(@RequestParam String token) {
        return BaseResult.judgeOperate(fileService.DatabaseBackup(token));
    }

    @ApiOperation(value = "指定数据库备份")
    @RequestMapping(value = "/database/backup/byName", method = RequestMethod.POST)
    public BaseResult<String> DatabaseBackupByName(@RequestBody List<BackupTableRequest> request) {
        return BaseResult.judgeOperate(fileService.DatabaseBackupByName(request));
    }
}
