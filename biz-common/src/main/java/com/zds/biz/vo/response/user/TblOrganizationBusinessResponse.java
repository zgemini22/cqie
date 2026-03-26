package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "TblOrganizationBusinessResponse对象", description = "营业所管理响应")
public class TblOrganizationBusinessResponse {

    @ApiModelProperty(value = "营业所ID")
    private Long id;

    @ApiModelProperty(value = "营业所名称")
    private String name;

    @ApiModelProperty(value = "营业所类型")
    private String type;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "行政区域")
    private String code;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "负责人联系电话")
    private String principalPhone;

    @ApiModelProperty(value = "负责人")
    private String principal;

    @ApiModelProperty(value = "服务人员数量")
    private Integer staffCount;

    @ApiModelProperty(value = "所属燃气企业ID")
    private Long orgId;
}