package com.zds.biz.vo.request.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@ApiModel(description = "省市区编码请求")
@Data
public class AreaRequest {

    @ApiModelPropertyCheck(value="父级编码,默认为0")
    private String parentCode;

    @ApiModelPropertyCheck("层级,1:省,2:市,3:区,4:街道")
    private Integer level;
}
