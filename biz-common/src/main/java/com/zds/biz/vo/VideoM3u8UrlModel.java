package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "VideoM3u8UrlModel返回直播地址")
@Data
public class VideoM3u8UrlModel {
    @ApiModelPropertyCheck(value = "直播地址")
    private String m3u8Url;

    @ApiModelPropertyCheck(value = "播放器直播地址")
    private String url;
}
