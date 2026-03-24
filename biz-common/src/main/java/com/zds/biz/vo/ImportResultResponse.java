package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "导入基础信息公共返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportResultResponse {
    @ApiModelPropertyCheck(value="导入成功数据条数")
    private Integer successNum;

    @ApiModelPropertyCheck(value="导入失败数据条数")
    private Integer failNum;

    @ApiModelPropertyCheck(value="导入失败数据明细")
    private List<ImportResultDetailResponse> failList;
}
