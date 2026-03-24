package com.zds.biz.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("tpc_construction")
@ApiModel(value = "三方施工表实体")
public class TpcConstruction {

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键ID")
    private Long id;
    
    @ApiModelProperty("三方施工编号")
    private String constructionCode;
    
    @ApiModelProperty("三方施工名称")
    private String constructionName;
    
    @ApiModelProperty("开工时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    
    @ApiModelProperty("完工时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;
    
    @ApiModelProperty("区域编码")
    private String areaCode;
    
    @ApiModelProperty("详细地址")
    private String address;
    
    @ApiModelProperty("经度")
    private BigDecimal longitude;
    
    @ApiModelProperty("纬度")
    private BigDecimal latitude;
    
    @ApiModelProperty("施工行业")
    private String constructionIndustry;
    
    @ApiModelProperty("施工方式")
    private String constructionMethod;
    
    @ApiModelProperty("建设单位")
    private String buildCompany;
    
    @ApiModelProperty("建设方联系人")
    private String buildContact;
    
    @ApiModelProperty("建设方联系电话")
    private String buildPhone;
    
    @ApiModelProperty("施工单位")
    private String constructionCompany;
    
    @ApiModelProperty("施工方联系人")
    private String constructionContact;
    
    @ApiModelProperty("施工方联系电话")
    private String constructionPhone;
    
    @ApiModelProperty("说明描述")
    private String description;
    
    @ApiModelProperty("施工现场图片")
    private String constructionImages;
    
    @ApiModelProperty("施工附件")
    private String constructionAttachments;
    
    @ApiModelProperty("告知函文件")
    private String noticeFile;
    
    @ApiModelProperty("安全协议文件")
    private String safetyAgreementFile;
    
    @ApiModelProperty("管道交底文件")
    private String pipeDisclosureFile;
    
    @ApiModelProperty("敷设方式")
    private String layingMethod;
    
    @ApiModelProperty("压力级别")
    private String pressureLevel;
    
    @ApiModelProperty("管道材质")
    private String pipeMaterial;
    
    @ApiModelProperty("是否管道上方施工")
    private Integer isAbovePipe;
    
    @ApiModelProperty("管径")
    private String pipeDiameter;
    
    @ApiModelProperty("管道埋深")
    private String pipeDepth;
    
    @ApiModelProperty("管控级别")
    private String controlLevel;
    
    @ApiModelProperty("告知函是否签订")
    private Integer isNoticeSigned;
    
    @ApiModelProperty("安全协议是否签订")
    private Integer isSafetySigned;
    
    @ApiModelProperty("管道交底")
    private Integer isPipeDisclosed;
    
    @ApiModelProperty("工程状态")
    private String projectStatus;
    
    @ApiModelProperty("组织ID")
    private Long orgId;
    
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("创建人")
    private Long createId;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("修改人")
    private Long updateId;
    
    @TableLogic
    @ApiModelProperty("是否删除")
    private Integer deleted;
}