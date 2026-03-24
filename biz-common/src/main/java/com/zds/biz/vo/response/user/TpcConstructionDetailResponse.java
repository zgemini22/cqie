package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "三方施工详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TpcConstructionDetailResponse {

    @ApiModelProperty(value = "三方施工编号")
    private String constructionCode;

    @ApiModelProperty(value = "三方施工名称")
    private String constructionName;

    @ApiModelProperty(value = "开工时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "完工时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "行政区域编码")
    private String areaCode;

    @ApiModelProperty(value = "施工地址（详细地址）")
    private String address;

    @ApiModelProperty(value = "施工行业")
    private String constructionIndustry;

    @ApiModelProperty(value = "施工方式")
    private String constructionMethod;

    @ApiModelProperty(value = "建设单位")
    private String buildCompany;

    @ApiModelProperty(value = "建设方联系人")
    private String buildContact;

    @ApiModelProperty(value = "建设方联系电话")
    private String buildPhone;

    @ApiModelProperty(value = "施工单位")
    private String constructionCompany;

    @ApiModelProperty(value = "施工方联系人")
    private String constructionContact;

    @ApiModelProperty(value = "施工方联系电话")
    private String constructionPhone;

    @ApiModelProperty(value = "说明描述")
    private String description;

    @ApiModelProperty(value = "敷设方式")
    private String layingMethod;

    @ApiModelProperty(value = "压力级别")
    private String pressureLevel;

    @ApiModelProperty(value = "管道材质")
    private String pipeMaterial;

    @ApiModelProperty(value = "是否管道上方施工")
    private Integer isAbovePipe;

    @ApiModelProperty(value = "管径")
    private String pipeDiameter;

    @ApiModelProperty(value = "管道埋深（m）")
    private String pipeDepth;

    @ApiModelProperty(value = "管控级别")
    private String controlLevel;

    @ApiModelProperty(value = "告知函是否签订")
    private Integer isNoticeSigned;

    @ApiModelProperty(value = "安全协议是否签订")
    private Integer isSafetySigned;

    @ApiModelProperty(value = "管道交底")
    private Integer isPipeDisclosed;

    // todo： 添加附件字段
    //  attachment_type 附件类型（description/notice/protocol/disclosure）
    //			name 附件显示文件名
    //			url 附件下载地址
}
