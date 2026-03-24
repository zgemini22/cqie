package com.zds.biz.vo.response.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "外部表单返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowOutsideFormResponse {
    @ApiModelPropertyCheck("ID")
    private Long id;

    @ApiModelPropertyCheck(value = "表单名称")
    private String formName;

    @ApiModelPropertyCheck(value = "表单类型")
    private String formType;

    @ApiModelPropertyCheck(value = "表单地址")
    private String formUrl;
}
