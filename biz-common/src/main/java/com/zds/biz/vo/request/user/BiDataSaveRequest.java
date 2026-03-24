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
public class BiDataSaveRequest {

    @ApiModelPropertyCheck(value = "信息ID")
    private Long id;

    @ApiModelPropertyCheck(value = "父ID", required = true)
    private Long parentId;

    @ApiModelPropertyCheck(value = "数据标识", required = true)
    private String biDataKey;

    @ApiModelPropertyCheck(value = "数据名称", required = true)
    private String biDataName;
}
