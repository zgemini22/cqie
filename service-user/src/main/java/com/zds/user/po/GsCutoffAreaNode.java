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
@TableName("gs_cutoff_area_node")
@ApiModel(value = "GsCutoffAreaNode对象", description = "保供管理-停气涉及区域明细表")
public class GsCutoffAreaNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "组织机构ID")
    private Long orgId;

    @ApiModelPropertyCheck(value = "业务来源 (PLAN:计划, OPERATION:作业)")
    private String sourceType;

    @ApiModelPropertyCheck(value = "关联主业务ID")
    private Long sourceId;

    @ApiModelPropertyCheck(value = "行政区划代码 (区县级)")
    private String districtCode;

    @ApiModelPropertyCheck(value = "街道级代码")
    private String streetCode;

    @ApiModelPropertyCheck(value = "详细区域/小区名称")
    private String detailAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Long createId;

    private Boolean deleted;

    public static LambdaQueryWrapper<GsCutoffAreaNode> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
