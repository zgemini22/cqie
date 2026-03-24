package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@ApiModel(description = "保存用户请求")
@Data
public class UserSaveRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "用户ID,传为修改,不传为新增")
    private Long id;

    @ApiModelPropertyCheck(value = "用户名称", required = true)
    private String name;

    @ApiModelPropertyCheck(value = "角色ID", required = true)
    private Long roleId;

    @ApiModelPropertyCheck(value = "手机号", required = true)
    private String phone;

    @ApiModelPropertyCheck(value = "身份证")
    private String idCard;

    @ApiModelPropertyCheck(value = "单位ID", required = true)
    private Long organizationId;

    @ApiModelPropertyCheck(value = "用户状态,字典group_id=USER_STATUS", required = true)
    private String userStatus;

    @ApiModelPropertyCheck(value = "邮箱")
    private String email;

    @ApiModelPropertyCheck(value = "备注")
    private String remark;

    @ApiModelPropertyCheck(value = "密码")
    private String pwd;

    @ApiModelPropertyCheck(value="用户头像文件")
    private String fileHead;

    @Override
    public void toRequestCheck() {
        if (name.length() > 10) {
            throw new BaseException("姓名最大长度为10位");
        }
        if (phone.length() > 11) {
            throw new BaseException("手机号最大长度为11位");
        }
        if (StringUtils.isNotEmpty(idCard) && idCard.length() > 18) {
            throw new BaseException("身份证最大长度为18位");
        }
        if (StringUtils.isNotEmpty(pwd) && pwd.length() > 20) {
            throw new BaseException("密码最大长度为20位");
        }
    }
}
