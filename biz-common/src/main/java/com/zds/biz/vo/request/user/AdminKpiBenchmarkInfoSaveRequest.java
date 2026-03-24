package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "标杆管理保存请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiBenchmarkInfoSaveRequest {

    @ApiModelPropertyCheck(value = "标杆ID,传为修改,不传为新增")
    private Long id;

    @ApiModelPropertyCheck(value = "指标ID", required = true)
    private Long normId;

    @ApiModelPropertyCheck(value = "标杆类型（1=自动维护，2=手动维护）", required = true)
    private Integer benchmarkType;

    @ApiModelPropertyCheck(value = "标杆描述", max = 200)
    private String benchmarkRemarks;

    @ApiModelPropertyCheck(value = "状态（1=启用，2=停用）")
    private Integer status;

    @ApiModelPropertyCheck(value = "标杆值/指标告警阈值集合", required = true)
    List<AdminKpiBenchmarkAlarmRequest> benchmarkAlarm;


}
