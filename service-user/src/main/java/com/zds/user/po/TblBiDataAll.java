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
@TableName("tbl_bi_data_all")
@ApiModel(value = "TblBiDataAll对象", description = "大屏填报信息(全部指标的数据(按年月季）)")
public class TblBiDataAll implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "数据ID")
    private String a;

    @ApiModelPropertyCheck(value = "指标ID")
    private String b;

    @ApiModelPropertyCheck(value = "指标名称")
    private String c;

    @ApiModelPropertyCheck(value = "指标类型")
    private String d;

    @ApiModelPropertyCheck(value = "指标方向")
    private String e;

    @ApiModelPropertyCheck(value = "父指标名称")
    private String f;

    @ApiModelPropertyCheck(value = "父指标标准值显示")
    private String g;

    @ApiModelPropertyCheck(value = "父指标标准值")
    private String h;

    @ApiModelPropertyCheck(value = "指标标准值显示")
    private String i;

    @ApiModelPropertyCheck(value = "指标标准值")
    private String j;

    @ApiModelPropertyCheck(value = "标准要求（大于还是小于）")
    private String k;

    @ApiModelPropertyCheck(value = "标准值单位")
    private String l;

    @ApiModelPropertyCheck(value = "细化方式")
    private String m;

    @ApiModelPropertyCheck(value = "业务领域ID")
    private String n;

    @ApiModelPropertyCheck(value = "业务领域")
    private String o;

    @ApiModelPropertyCheck(value = "业务环节ID")
    private String p;

    @ApiModelPropertyCheck(value = "业务环节")
    private String q;

    @ApiModelPropertyCheck(value = "年度数据值")
    private String r;

    @ApiModelPropertyCheck(value = "增幅")
    private String s;

    @ApiModelPropertyCheck(value = "变化方向")
    private String t;

    @ApiModelPropertyCheck(value = "变化文字")
    private String u;

    @ApiModelPropertyCheck(value = "时间条件")
    private String v;

    @ApiModelPropertyCheck(value = "当前指标状态")
    private String w;

    public static LambdaQueryWrapper<TblBiDataAll> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
