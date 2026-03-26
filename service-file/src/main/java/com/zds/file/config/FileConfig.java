package com.zds.file.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 上传文件配置参数
 */
@Data
@Configuration
public class FileConfig {

    /**
     * 文件上传大小限制
     */
    @Value("${file-setting.file-mb-max-size}")
    private Integer fileMbMaxSize;

    /**
     * 文件保存路径-windows
     */
    @Value("${file-setting.file-upload-path-windows}")
    private String fileUploadPathOfWindows;

    /**
     * 文件保存路径-linux
     */
    @Value("${file-setting.file-upload-path-linux}")
    private String fileUploadPathOfLinux;

    /**
     * 文件读取路径-windows
     */
    @Value("${file-setting.file-load-path-windows}")
    private String fileLoadPathOfWindows;

    /**
     * 文件读取路径-linux
     */
    @Value("${file-setting.file-load-path-linux}")
    private String fileLoadPathOfLinux;

    /**
     * 文件读取路径-外网
     */
    @Value("${file-setting.file-load-path-extranet}")
    private String fileLoadPathOfExtranet;
}
