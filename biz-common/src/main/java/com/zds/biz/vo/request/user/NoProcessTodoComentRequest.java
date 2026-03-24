package com.zds.biz.vo.request.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Builder;
import lombok.Data;

@ApiModel(description = "非流程代办完成请求")
@Data
@Builder
public class NoProcessTodoComentRequest {

    @ApiModelPropertyCheck("待办类型")
    private String type;

    @ApiModelPropertyCheck("数据来源ID")
    private String dataId;

    @ApiModelPropertyCheck("用户或单位Id")
    private String Id;
}
