package com.zds.biz.vo.request.construction;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Date;

// ### 2. 请求参数类 (Request)
// 需要做分页查询，需要继承分页通用请求对象，获取分页参数
public class TpcConstructionRequest extends PageRequest {
    @ApiModelProperty("三方施工编号")
    private String constructionCode;

    @ApiModelProperty("三方施工名称")
    private String constructionName;

    @ApiModelProperty("开工时间开始")
    private LocalDateTime startTimeBegin;

    @ApiModelProperty("开工时间结束")
    private LocalDateTime startTimeEnd;

    @ApiModelProperty("完工时间开始")
    private Date endTimeBegin;

    @ApiModelProperty("完工时间结束")
    private Date endTimeEnd;

    @ApiModelProperty("区域编码")
    private String areaCode;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("建设单位")
    private String buildCompany;

    @ApiModelProperty("施工单位")
    private String constructionCompany;

    @ApiModelProperty("工程状态")
    private String projectStatus;

    @ApiModelProperty("管控级别")
    private String controlLevel;
}
