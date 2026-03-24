package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "外部表单请求对象")
public class FlowAddOutsideFormRequest {

    @ApiModelPropertyCheck(value = "表单类型",required = true)
    private String formType;

    @ApiModelPropertyCheck(value = "表单地址",required = true)
    private String formUrl;
}
