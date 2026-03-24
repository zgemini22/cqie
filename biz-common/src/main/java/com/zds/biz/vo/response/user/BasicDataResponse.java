package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "文件上传返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicDataResponse {

    @ApiModelPropertyCheck("配置ID")
    private Long id;

    @ApiModelPropertyCheck("数据标识")
    private String dataKey;

    @ApiModelPropertyCheck("数据名称")
    private String dataName;

    @ApiModelPropertyCheck("数据内容")
    private String dataValue;

    @ApiModelPropertyCheck("备注")
    private String remarks;
}
