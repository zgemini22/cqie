package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "数据源分页列表请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiDataListRequest extends PageRequest {

    @ApiModelPropertyCheck(value = "数据源名称")
    private String dataName;

    @ApiModelPropertyCheck(value = "数据状态（1=启用，2=停用）")
    private Integer status;
}
