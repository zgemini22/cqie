package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "停气涉及区域详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GsCutoffAreaNodeDetailResponse {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "组织机构ID")
    private Long orgId;

    @ApiModelProperty(value = "业务来源")
    private String sourceType;

    @ApiModelProperty(value = "关联主业务ID")
    private Long sourceId;

    @ApiModelProperty(value = "行政区划代码")
    private String districtCode;

    @ApiModelProperty(value = "街道级代码")
    private String streetCode;

    @ApiModelProperty(value = "详细区域/小区名称")
    private String detailAddress;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createId;

}
