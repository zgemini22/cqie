package com.zds.biz.vo.response.file;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "Base64上传文件请求对象")
public class Base64FileResponse {

    @ApiModelPropertyCheck(value = "源文件名", example = "xxx.jpg")
    private String fileName;

    @ApiModelPropertyCheck(value = "文件Base64数据")
    private String fielBase64;
}
