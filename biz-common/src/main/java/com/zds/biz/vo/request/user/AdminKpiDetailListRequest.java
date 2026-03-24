package com.zds.biz.vo.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "数据源明细分页列表请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiDetailListRequest extends PageRequest {

    @ApiModelPropertyCheck(value = "数据源ID", required = true)
    private Long kpiDataId;

    @ApiModelPropertyCheck(value = "企业或街道名称")
    private String companyName;

    @ApiModelPropertyCheck(value = "开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck(value = "结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
}
