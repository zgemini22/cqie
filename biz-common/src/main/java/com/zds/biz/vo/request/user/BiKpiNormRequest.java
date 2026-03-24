package com.zds.biz.vo.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "查询指定指标信息请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BiKpiNormRequest {

    @ApiModelPropertyCheck(value = "指标编码")
    private String normCode;

    @ApiModelPropertyCheck("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelPropertyCheck(value = "是否查询往期指标结果")
    private Boolean lastSearchFlag = false;

    @ApiModelPropertyCheck(value = "是否查询标杆")
    private Boolean benchmarkSearchFlag = false;

    @ApiModelPropertyCheck(value = "是否查询企业分组")
    private Boolean companySearchFlag = false;

    @ApiModelPropertyCheck(value = "是否查询街道分组")
    private Boolean streetSearchFlag = false;
}
