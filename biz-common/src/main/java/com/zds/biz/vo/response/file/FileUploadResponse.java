package com.zds.biz.vo.response.file;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "文件上传返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {

    @ApiModelPropertyCheck(value = "源文件名")
    private String fileName;

    @ApiModelPropertyCheck(value = "服务器保存文件名")
    private String realFileName;
}
