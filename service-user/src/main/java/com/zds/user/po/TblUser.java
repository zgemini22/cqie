package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_user")
@ApiModel(value = "TblUser对象", description = "用户信息")
public class TblUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人")
    private Long createId;

    @ApiModelPropertyCheck(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "修改人")
    private Long updateId;

    @ApiModelPropertyCheck(value = "是否删除")
    private Boolean deleted;

    @ApiModelPropertyCheck(value = "用户名称")
    private String name;

    @ApiModelPropertyCheck(value = "用户头像文件")
    private String fileHead;

    @ApiModelPropertyCheck(value = "登录名")
    private String loginName;

    @ApiModelPropertyCheck(value = "手机号")
    private String phone;

    @ApiModelPropertyCheck(value = "邮箱")
    private String email;

    @ApiModelPropertyCheck(value = "备注")
    private String remarks;

    @ApiModelPropertyCheck(value = "用户类型,字典group_id=USER_TYPE")
    private String userType;

    @ApiModelPropertyCheck(value = "是否为组织管理员")
    private Boolean adminFlag;

    @ApiModelPropertyCheck(value = "用户状态,字典group_id=USER_STATUS")
    private String userStatus;

    @ApiModelPropertyCheck(value = "密码")
    private String hashedPassword;

    @ApiModelPropertyCheck(value = "密码修改日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date passwordChangedAt;

    @ApiModelPropertyCheck(value = "登录失败次数")
    private Integer loginFailCount;

    @ApiModelPropertyCheck(value = "是否初始密码")
    private Boolean initialPasswordFlag;

    @ApiModelPropertyCheck(value = "是否锁定账户")
    private Boolean accountLocked;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "角色ID")
    private Long roleId;

    @ApiModelPropertyCheck(value = "微信openId")
    private String openId;

    public static LambdaQueryWrapper<TblUser> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
