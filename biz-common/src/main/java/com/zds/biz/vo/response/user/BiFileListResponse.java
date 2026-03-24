package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "资源列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiFileListResponse {

    @ApiModelPropertyCheck(value = "文件ID")
    private Long id;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "源文件名")
    private String fileName;

    @ApiModelPropertyCheck(value = "服务器保存文件名")
    private String fileRealName;

    @ApiModelPropertyCheck(value = "文件大小,单位KB")
    private Long fileSize;

    @ApiModelPropertyCheck(value = "备注")
    private String remark;
}
