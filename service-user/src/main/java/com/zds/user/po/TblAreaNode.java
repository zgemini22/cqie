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
@TableName("tbl_area_node")
@ApiModel(value = "TblAreaNode对象", description = "省市区编码")
public class TblAreaNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "省市区编码")
    private String code;

    @ApiModelPropertyCheck(value = "省市区名称")
    private String areaName;

    @ApiModelPropertyCheck(value = "父级编码")
    private String parentCode;

    @ApiModelPropertyCheck(value = "层级")
    private Integer level;

    public static LambdaQueryWrapper<TblAreaNode> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
