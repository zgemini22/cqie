package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "导入失败数据明细返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportResultDetailResponse {

    @ApiModelPropertyCheck(value="数据行数")
    private Integer rowNum;

    @ApiModelPropertyCheck(value="失败原因")
    private String message;
}
