package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "用户修改请求")
public class UserUpdateRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "id", required = true, example = "admin")
    private Long id;

    @ApiModelPropertyCheck(value = "名称", required = true, example = "admin")
    private String name;

    @ApiModelPropertyCheck(value = "电话", required = true, example = "admin")
    private String phone;
}
