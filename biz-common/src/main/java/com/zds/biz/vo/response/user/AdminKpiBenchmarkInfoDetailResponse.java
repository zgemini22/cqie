package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(description = "标杆值维护管理查看返回")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiBenchmarkInfoDetailResponse {
    @ApiModelPropertyCheck(value = "标杆ID,传为修改,不传为新增")
    private Long id;

    @ApiModelPropertyCheck(value = "指标ID")
    private Long normId;

    @ApiModelPropertyCheck(value = "标杆类型（1=自动维护，2=手动维护）")
    private Integer benchmarkType;

    @ApiModelPropertyCheck(value = "标杆描述")
    private String benchmarkRemarks;

    @ApiModelPropertyCheck(value = "状态（1=启用，2=停用）")
    private Integer status;

    @ApiModelPropertyCheck(value = "标杆值/指标告警阈值集合")
    List<AdminKpiBenchmarkAlarmResponse> benchmarkAlarm;
}
