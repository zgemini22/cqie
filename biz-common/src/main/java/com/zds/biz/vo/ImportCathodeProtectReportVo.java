package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportCathodeProtectReportVo {

    @ApiModelPropertyCheck(value = "设备编号")
    private String deviceNo;

    @ApiModelPropertyCheck(value = "通电电位值")
    private String potentialValue;

    @ApiModelPropertyCheck(value = "采集时间")
    private String installTime;

    @ApiModelProperty(value="行数")
    private Integer row;
}

