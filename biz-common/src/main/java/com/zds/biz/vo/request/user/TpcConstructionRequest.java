package com.zds.biz.vo.request.user;

import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "三方施工查询请求")
@Data
public class TpcConstructionRequest extends PageRequest {

    @ApiModelProperty(value = "三方工地编号")
    private String constructionCode;

    @ApiModelProperty(value = "三方工地名称")
    private String constructionName;

    @ApiModelProperty(value = "状态")
    private String projectStatus;

    @ApiModelProperty(value = "施工地址")
    private String address;

    @ApiModelProperty(value = "施工单位")
    private String constructionCompany;

    @ApiModelProperty(value = "告知函是否签订")
    private Integer isNoticeSigned;

    @ApiModelProperty(value = "安全协议是否签订")
    private Integer isSafetySigned;

}
