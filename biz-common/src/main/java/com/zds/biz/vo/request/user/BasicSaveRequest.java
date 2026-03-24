package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "修改基础数据请求")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicSaveRequest extends BaseRequest {

    @ApiModelPropertyCheck(value="数据标识", required = true)
    private String dataKey;

    @ApiModelPropertyCheck(value="数据内容", required = true)
    private String dataValue;
}
