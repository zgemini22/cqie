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
@TableName("tbl_organization_business")
@ApiModel(value = "TblOrganizationBusiness对象", description = "营业所信息表")
public class TblOrganizationBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "营业所名称")
    private String name;

    @ApiModelPropertyCheck(value = "营业所类型")
    private String type;

    @ApiModelPropertyCheck(value = "状态")
    private Integer status;

    @ApiModelPropertyCheck(value = "行政区域")
    private String code;

    @ApiModelPropertyCheck(value = "详细地址")
    private String address;

    @ApiModelPropertyCheck(value = "联系人")
    private String contactPerson;

    @ApiModelPropertyCheck(value = "联系电话")
    private String contactPhone;

    @ApiModelPropertyCheck(value = "负责人联系电话")
    private String principalPhone;

    @ApiModelPropertyCheck(value = "负责人")
    private String principal;

    @ApiModelPropertyCheck(value = "服务人员数量")
    private Integer staffCount;

    @ApiModelPropertyCheck(value = "所属燃气企业ID")
    private Long orgId;

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
    private Integer deleted;

    public static LambdaQueryWrapper<TblOrganizationBusiness> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}