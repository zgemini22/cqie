package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("tpc_construction")
@ApiModel(value = "TpcConstruction对象", description = "三方施工表")
public class TpcConstruction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "三方施工编号")
    private String constructionCode;

    @ApiModelPropertyCheck(value = "三方施工名称")
    private String constructionName;

    @ApiModelPropertyCheck(value = "开工时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck(value = "完工时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelPropertyCheck(value = "tbl_area_node表的code")
    private String areaCode;

    @ApiModelPropertyCheck(value = "详细地址")
    private String address;

    @ApiModelPropertyCheck(value = "经度")
    private BigDecimal longitude;

    @ApiModelPropertyCheck(value = "纬度")
    private BigDecimal latitude;

    @ApiModelPropertyCheck(value = "施工行业")
    private String constructionIndustry;

    @ApiModelPropertyCheck(value = "施工方式")
    private String constructionMethod;

    @ApiModelPropertyCheck(value = "建设单位")
    private String buildCompany;

    @ApiModelPropertyCheck(value = "建设方联系人")
    private String buildContact;

    @ApiModelPropertyCheck(value = "建设方联系电话")
    private String buildPhone;

    @ApiModelPropertyCheck(value = "施工单位")
    private String constructionCompany;

    @ApiModelPropertyCheck(value = "施工方联系人")
    private String constructionContact;

    @ApiModelPropertyCheck(value = "施工方联系电话")
    private String constructionPhone;

    @ApiModelPropertyCheck(value = "说明描述")
    private String description;

    @ApiModelPropertyCheck(value = "敷设方式")
    private String layingMethod;

    @ApiModelPropertyCheck(value = "压力级别")
    private String pressureLevel;

    @ApiModelPropertyCheck(value = "管道材质")
    private String pipeMaterial;

    @ApiModelPropertyCheck(value = "是否管道上方施工")
    private Integer isAbovePipe;

    @ApiModelPropertyCheck(value = "管径")
    private String pipeDiameter;

    @ApiModelPropertyCheck(value = "管道埋深（m）")
    private String pipeDepth;

    @ApiModelPropertyCheck(value = "管控级别")
    private String controlLevel;

    @ApiModelPropertyCheck(value = "告知函是否签订")
    private Integer isNoticeSigned;

    @ApiModelPropertyCheck(value = "安全协议是否签订")
    private Integer isSafetySigned;

    @ApiModelPropertyCheck(value = "管道交底")
    private Integer isPipeDisclosed;

    @ApiModelPropertyCheck(value = "工程状态")
    private String projectStatus;

    @ApiModelPropertyCheck(value = "组织ID 关联 tbl_organization.id")
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
    private Boolean deleted;

    public static LambdaQueryWrapper<TpcConstruction> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
