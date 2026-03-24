package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@ApiModel(description = "文件保存请求")
@Data
public class AdminFileSaveRequest {

    @ApiModelPropertyCheck(value = "文件ID(新增时不传,修改时传入)")
    private Long id;

    @ApiModelPropertyCheck(value = "附件类型(1:附件,2:图片,3:视频,4,协议)")
    private Integer fileType = 1;

    @ApiModelPropertyCheck(value = "源文件名",required = true)
    private String fileName;

    @ApiModelPropertyCheck(value = "服务器保存文件名",required = true)
    private String realFileName;
}
