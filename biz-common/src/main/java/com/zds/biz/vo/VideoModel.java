package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "Video基础类")
@Data
public class VideoModel<T> {
    @ApiModelPropertyCheck(value = "结果编号")
    private String resultCode;

    @ApiModelPropertyCheck(value = "结果描述")
    private String resultMsg;
    @ApiModelPropertyCheck(value = "结果数据")
    private T data;
}
