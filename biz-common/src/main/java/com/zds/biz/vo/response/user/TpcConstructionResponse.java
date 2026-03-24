package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "三方施工列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TpcConstructionResponse {
    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "三方工地编号")
    private String constructionCode;

    @ApiModelProperty(value = "三方工地名称")
    private String constructionName;

    @ApiModelProperty(value = "管控级别")
    private String controlLevel;

    @ApiModelProperty(value = "施工地址")
    private String address;

    @ApiModelProperty(value = "开工时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "完工时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "状态")
    private String projectStatus;

    @ApiModelProperty(value = "说明描述")
    private String description;

    @ApiModelProperty(value = "告知函是否签订")
    private Integer isNoticeSigned;

    @ApiModelProperty(value = "安全协议是否签订")
    private Integer isSafetySigned;
}
