package com.zds.biz.vo;

import com.zds.biz.constant.SeriesTypeEnum;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "图表数据集元素基类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesVo {

    @ApiModelPropertyCheck(value = "类型,bar/line,默认bar")
    private String type = SeriesTypeEnum.BAR.getKey();

    @ApiModelPropertyCheck(value = "名称")
    private String name;

    @ApiModelPropertyCheck(value = "数据堆叠,同个类目轴上系列配置相同的stack值后,后一个系列的值会在前一个系列的值上相加")
    private String stack;

    @ApiModelPropertyCheck(value = "使用的x轴的index,默认0")
    private Integer xAxisIndex = 0;

    @ApiModelPropertyCheck(value = "使用的y轴的index,默认0")
    private Integer yAxisIndex = 0;

    @ApiModelPropertyCheck(value = "数据集合")
    private List<BigDecimal> data;
}
