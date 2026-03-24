package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "FileInfoVo")
public class FileInfoVo {

    @ApiModelPropertyCheck(value = "文件ID")
    private Long id;

    @ApiModelPropertyCheck(value = "源文件名")
    private String fileName;

    @ApiModelPropertyCheck(value = "服务器保存文件名")
    private String realFileName;

    @ApiModelPropertyCheck(value = "文件大小,单位KB")
    private Long realFileSize;
}
