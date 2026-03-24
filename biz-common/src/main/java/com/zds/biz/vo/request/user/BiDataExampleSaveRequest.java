package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "修改基础数据请求")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiDataExampleSaveRequest {

    @ApiModelPropertyCheck(value = "信息ID", required = true)
    private Long id;

    @ApiModelPropertyCheck(value = "数据实例", required = true)
    private String biDataExample;
}
