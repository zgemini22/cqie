package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "员工操作请求对象")
@NoArgsConstructor
@AllArgsConstructor
public class FlowUserRequest {
    @ApiModelPropertyCheck("ID")
    private String id;

    @ApiModelPropertyCheck("名")
    private String firstName;

    @ApiModelPropertyCheck("姓")
    private String lastName;

    @ApiModelPropertyCheck("邮箱")
    private String email;

    @ApiModelPropertyCheck("密码")
    private String password;
}
