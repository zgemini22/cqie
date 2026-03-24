package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@ApiModel(description = "数字重庆返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SzCqBaseResult {
    private Integer code;
    private String data;
    private Boolean serviceSuccess;
    private List<String> errors;
    private String requestId;

}
