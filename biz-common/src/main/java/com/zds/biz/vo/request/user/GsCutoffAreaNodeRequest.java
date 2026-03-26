package com.zds.biz.vo.request.user;

import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "停气涉及区域请求")
@Data
public class GsCutoffAreaNodeRequest extends PageRequest {

    @ApiModelProperty(value = "主键ID")
    private Long id;

}
