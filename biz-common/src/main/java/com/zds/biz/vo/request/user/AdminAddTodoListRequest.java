package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Builder;
import lombok.Data;



@Data
@Builder
@ApiModel(description = "新增待办事项")
public class AdminAddTodoListRequest extends BaseRequest {
    @ApiModelPropertyCheck("审批人组织ID")
    private String organizationId;

    @ApiModelPropertyCheck("发起人Id")
    private String initiatorId;

    @ApiModelPropertyCheck("审批人Id")
    private String assigneeId;

    @ApiModelPropertyCheck("名称")
    private String name;

    @ApiModelPropertyCheck("待办类型(字典group_id=TODO_LIST)")
    private String type;

    @ApiModelPropertyCheck("数据来源ID")
    private String dataId;

    @ApiModelPropertyCheck("预期时间天数")
    private Integer dueTime;



    @Override
    public void toRequestCheck() {
        if(initiatorId == null){
            throw new BaseException("发起人Id不能为空");
        }
        if(assigneeId == null){
            throw new BaseException("审批人Id不能为空");
        }
        if(name == null){
            throw new BaseException("名称不能为空");
        }
        if(type == null){
            throw new BaseException("待办类型不能为空");
        }
        if(dueTime == null){
            throw new BaseException("预期时间不能为空");
        }
    }
}
