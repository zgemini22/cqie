package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "地图配置返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapConfigResponse {

    @ApiModelPropertyCheck(value = "地图key")
    private String dataKey;

    @ApiModelPropertyCheck(value = "地图名称")
    private String dataName;

    @ApiModelPropertyCheck(value = "地图ID")
    private String dataValue;

    @ApiModelPropertyCheck(value = "备注")
    private String remarks;
}
