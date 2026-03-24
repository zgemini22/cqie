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
@TableName("kpi_benchmark_info")
@ApiModel(value = "KpiBenchmarkInfo对象", description = "kpi标杆管理-主表")
public class KpiBenchmarkInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "kpi标杆管理ID")
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

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "指标ID")
    private Long normId;

    @ApiModelPropertyCheck(value = "标杆类型（1=自动维护，2=手动维护）")
    private Integer benchmarkType;

    @ApiModelPropertyCheck(value = "标杆描述")
    private String benchmarkRemarks;

    @ApiModelPropertyCheck(value = "状态（1=启用，2=停用）")
    private Integer status;

    @ApiModelPropertyCheck(value = "指标小数位")
    private Integer decimalDigit;

    public static LambdaQueryWrapper<KpiBenchmarkInfo> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
