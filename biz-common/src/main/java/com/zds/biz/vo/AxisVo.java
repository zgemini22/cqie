package com.zds.biz.vo;

import com.zds.biz.constant.AxisPositionEnum;
import com.zds.biz.constant.AxisTypeEnum;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "图表轴设置基类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AxisVo {

    @ApiModelPropertyCheck(value = "类型,category/value")
    private String type;

    @ApiModelPropertyCheck(value = "名称")
    private String name;

    @ApiModelPropertyCheck(value = "位置,left/right")
    private String position;

    @ApiModelPropertyCheck(value = "轴单位标签格式化,不包含{value}字符串")
    private String axisLabelFormatter;

    @ApiModelPropertyCheck(value = "数据集合")
    private List<String> data;

    /**
     * 生成普通Y轴元素
     */
    public static AxisVo yAxisBase() {
        return AxisVo.builder()
                .type(AxisTypeEnum.VALUE.getKey())
                .position(AxisPositionEnum.LEFT.getKey())
                .build();
    }

    /**
     * 生成普通X轴元素
     * @param list 数据集合
     */
    public static AxisVo xAxisBase(List<String> list) {
        return AxisVo.builder()
                .type(AxisTypeEnum.CATEGORY.getKey())
                .data(list)
                .build();
    }

    /**
     * 生成普通Y轴元素集合
     */
    public static List<AxisVo> yAxisListBase() {
        List<AxisVo> yAxis = new ArrayList<>();
        yAxis.add(yAxisBase());
        return yAxis;
    }

    /**
     * 生成普通X轴元素集合
     * @param list 数据集合
     */
    public static List<AxisVo> xAxisListBase(List<String> list) {
        List<AxisVo> xAxis = new ArrayList<>();
        xAxis.add(xAxisBase(list));
        return xAxis;
    }
}
