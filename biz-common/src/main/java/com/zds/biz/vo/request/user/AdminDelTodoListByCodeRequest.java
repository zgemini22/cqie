package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "根据待办表示删除待办事项")
public class AdminDelTodoListByCodeRequest extends BaseRequest {

    @ApiModelPropertyCheck("待办事项编码")
    private String todoCode;

    @ApiModelPropertyCheck("待办事项类型")
    private String type;
}
