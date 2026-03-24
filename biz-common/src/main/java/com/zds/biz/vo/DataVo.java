package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "加密返回类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataVo {
    private String data;
}
