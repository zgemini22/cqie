package com.zds.biz.vo.request.file;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@ApiModel(description = "下载请求")
@Data
public class FileDownloadRequest {

    @ApiModelPropertyCheck(value = "源文件名")
    private String fileName;

    @ApiModelPropertyCheck(value = "服务器保存文件名")
    private String realFileName;
}
