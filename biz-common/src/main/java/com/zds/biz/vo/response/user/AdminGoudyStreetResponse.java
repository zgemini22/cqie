package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(description = "街道分组返回")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGoudyStreetResponse {
    @ApiModelPropertyCheck(value = "街道")
    private String street;

    @ApiModelPropertyCheck("数量")
    private Integer total;

    @ApiModelPropertyCheck(value = "经度")
    private BigDecimal longitude;

    @ApiModelPropertyCheck(value = "纬度")
    private BigDecimal latitude;

    @ApiModelPropertyCheck("建设审批详情")
    private List<AdminGoudyStreetDetailResponse> detail;
}
