package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.UserStatusEnum;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "更新用户状态请求")
public class UserStatusRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "用户ID", required = true, example = "1")
    private Long id;

    @ApiModelPropertyCheck(value="用户状态,字典group_id=USER_STATUS", required = true, example = "ENABLE")
    private String userStatus;

    @Override
    public void toRequestCheck() {
        if (id == null) {
            throw new BaseException("用户ID不能为空");
        }
        if (UserStatusEnum.query(userStatus) == null) {
            throw new BaseException("用户状态值不在允许范围");
        }
    }
}
