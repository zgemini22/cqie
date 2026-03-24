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
@TableName("tbl_organization")
@ApiModel(value = "TblOrganization对象", description = "组织信息")
public class TblOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "组织ID")
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

    @ApiModelPropertyCheck(value = "组织名称")
    private String organizationName;

    @ApiModelPropertyCheck(value = "组织状态,字典group_id=ORGANIZATION_STATUS")
    private String organizationStatus;

    @ApiModelPropertyCheck(value = "组织类别,字典group_id=ORGANIZATION_TYPE")
    private String organizationType;

    @ApiModelPropertyCheck(value = "父级ID")
    private Long parentId;

    @ApiModelPropertyCheck(value = "父级路径")
    private String parentUrl;

    public static LambdaQueryWrapper<TblOrganization> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
