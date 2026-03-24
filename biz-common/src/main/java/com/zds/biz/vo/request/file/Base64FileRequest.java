package com.zds.biz.vo.request.file;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "Base64上传文件请求对象")
public class Base64FileRequest {

    @ApiModelPropertyCheck(value = "源文件名", example = "xxx.jpg")
    private String fileName;

    @ApiModelPropertyCheck(value = "文件Base64数据")
    private String fielBase64;
}
