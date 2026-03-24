package com.zds.biz.vo.request.flow;

import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "外部表单请求对象")
public class FlowOutsideFormRequest extends PageRequest {

    @ApiModelPropertyCheck(value = "表单名称" )
    private String formName;

    @ApiModelPropertyCheck(value = "表单类型")
    private String formType;

    @ApiModelPropertyCheck(value = "表单地址")
    private String formUrl;

}
