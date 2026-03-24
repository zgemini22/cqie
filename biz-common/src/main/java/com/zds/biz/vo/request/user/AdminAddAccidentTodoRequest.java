package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(description = "新增事故研判待办事项")
public class AdminAddAccidentTodoRequest extends BaseRequest {

    @ApiModelPropertyCheck("名称")
    private String name;

    @ApiModelPropertyCheck(value = "待办类型", required = true)
    private String type;

    @ApiModelPropertyCheck(value = "发起人Id", required = true)
    private String initiatorId;

    @ApiModelPropertyCheck(value = "数据来源ID", required = true)
    private String dataId;

    @ApiModelPropertyCheck("期限时间(预计工作日天数)")
    private Integer dueTime;

    @Override
    public void toRequestCheck() {
        if (initiatorId == null) {
            throw new BaseException("发起人Id不能为空");
        }
        if (type == null) {
            throw new BaseException("待办类型不能为空");
        }
        if(dueTime == null){
            throw new BaseException("预期时间不能为空");
        }
    }
}
