package com.zds.biz.vo.request.user;

import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("所有事项分页请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoadAllTodoListPageListRequest extends PageRequest {

    @ApiModelPropertyCheck("事务名称")
    private String name;

    @ApiModelPropertyCheck("待办状态,1:未处理,2:已处理")
    private Integer status;

    @ApiModelPropertyCheck("待办类型(字典:group_id=TODO_LIST)")
    private String type;
}
