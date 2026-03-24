package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(description = "返回对比后的数据")
@Data
public class DataComparisonResponse<T> {
    @ApiModelProperty(value="新增数据")
    public List<T> insert;
    @ApiModelProperty(value="修改数据")
    public List<T> update;
    @ApiModelProperty(value="删除数据")
    public List<T> delete;
}
