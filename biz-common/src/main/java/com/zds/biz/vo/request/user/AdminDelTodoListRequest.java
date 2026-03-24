package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "删除待办事项")
public class AdminDelTodoListRequest extends BaseRequest {

    @ApiModelPropertyCheck("待办事项ID")
    private Long id;

    @ApiModelPropertyCheck("数据来源,1:流程引擎,2:协同监管")
    private Integer dataWay;

    @ApiModelPropertyCheck("数据来源ID")
    private String dataId;
}
