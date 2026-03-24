package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("tbl_bi_data_qy")
@ApiModel(value = "TblBiDataQy对象", description = "大屏填报信息企业")
public class TblBiDataQy implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "数据ID")
    private String a;

    @ApiModelPropertyCheck(value = "指标ID")
    private String b;

    @ApiModelPropertyCheck(value = "指标名称")
    private String c;

    @ApiModelPropertyCheck(value = "变化文字")
    private String d;

    @ApiModelPropertyCheck(value = "时间条件")
    private String e;

    @ApiModelPropertyCheck(value = "燃气公司")
    private String f;

    @ApiModelPropertyCheck(value = "数据值")
    private String g;

    @ApiModelPropertyCheck(value = "排名")
    private String h;

    @ApiModelPropertyCheck(value = "较上期变化")
    private String i;

    @ApiModelPropertyCheck(value = "变化方向")
    private String j;

    public static LambdaQueryWrapper<TblBiDataQy> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
