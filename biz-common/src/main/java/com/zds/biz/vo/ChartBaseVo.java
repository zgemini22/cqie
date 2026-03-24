package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "图表数据基类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartBaseVo {

    @ApiModelPropertyCheck(value = "标题")
    private String title;

    @ApiModelPropertyCheck(value = "内容")
    private String context;

    @ApiModelPropertyCheck(value = "项集合")
    private List<String> legend;

    @ApiModelPropertyCheck(value = "x轴数据集合")
    private List<AxisVo> xAxis;

    @ApiModelPropertyCheck(value = "y轴数据集合")
    private List<AxisVo> yAxis;

    @ApiModelPropertyCheck(value = "数据集合")
    private List<SeriesVo> series;

    public static ChartBaseVo buildVo(String title, String context, List<String> legend, List<String> xAxis, List<BigDecimal> data) {
        List<SeriesVo> series = new ArrayList<>();
        SeriesVo seriesVo = new SeriesVo();
        seriesVo.setData(data);
        series.add(seriesVo);
        return ChartBaseVo.builder()
                .title(title)
                .context(context)
                .legend(legend)
                .xAxis(AxisVo.xAxisListBase(xAxis))
                .yAxis(AxisVo.yAxisListBase())
                .series(series)
                .build();
    }
}
