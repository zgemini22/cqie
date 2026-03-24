package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "新增非流程待办事项")
public class AdminAddNoProcessListRequest extends BaseRequest {
    @ApiModelPropertyCheck("审批人组织ID")
    private String organizationId;

    @ApiModelPropertyCheck("名称")
    private String name;

    @ApiModelPropertyCheck("待办类型")
    private String type;

    @ApiModelPropertyCheck("发起人Id")
    private String initiatorId;

    @ApiModelPropertyCheck("审批人Id")
    private String assigneeId;

    @ApiModelPropertyCheck("数据来源ID")
    private String dataId;

    @ApiModelPropertyCheck("期限时间(预计工作日天数)")
    private Integer dueTime;

    @Override
    public void toRequestCheck() {
        if(initiatorId == null){
            throw new BaseException("发起人Id不能为空");
        }
        if(type == null){
            throw new BaseException("待办类型不能为空");
        }
    }
}
