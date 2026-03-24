package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "文件保存请求")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminFileInfoResponse {

    @ApiModelPropertyCheck(value = "文件ID")
    private Long id;

    @ApiModelPropertyCheck(value = "文件类型(1:附件,2:图片,3:视频,4,协议)")
    private Integer fileType = 1;

    @ApiModelPropertyCheck(value = "源文件名")
    private String fileName;

    @ApiModelPropertyCheck(value = "服务器保存文件名")
    private String realFileName;
}
