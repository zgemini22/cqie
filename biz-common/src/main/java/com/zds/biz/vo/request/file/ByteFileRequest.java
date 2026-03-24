package com.zds.biz.vo.request.file;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
public class ByteFileRequest {

    @ApiModelPropertyCheck(value = "二进制文件数据")
    private byte[] file;

    @ApiModelPropertyCheck(value = "源地址")
    private String url;
}
