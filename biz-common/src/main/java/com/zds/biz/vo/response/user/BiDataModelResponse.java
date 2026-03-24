package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "数据类型列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiDataModelResponse {

    @ApiModelPropertyCheck(value = "信息ID")
    private Long id;

    @ApiModelPropertyCheck(value = "数据标识")
    private String biDataKey;

    @ApiModelPropertyCheck(value = "数据名称")
    private String biDataName;

    @ApiModelPropertyCheck(value = "数据模型")
    private String biDataJson;

    @ApiModelPropertyCheck(value = "数据注释")
    private String biDataComment;
}
