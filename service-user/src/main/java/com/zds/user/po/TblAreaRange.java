package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("tbl_area_range")
@ApiModel(value = "TblAreaRange对象", description = "区域镇街边界表")
public class TblAreaRange implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "区域编码")
    private String code;

    @ApiModelPropertyCheck(value = "经度")
    private BigDecimal longitude;

    @ApiModelPropertyCheck(value = "纬度")
    private BigDecimal latitude;

    @ApiModelPropertyCheck(value = "排序")
    private Integer sort;

    public static LambdaQueryWrapper<TblAreaRange> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
