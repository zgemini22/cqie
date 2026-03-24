package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "字典分组列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictGroupSelectResponse {

    @ApiModelPropertyCheck(value="字典分组ID")
    private Long id;

    @ApiModelPropertyCheck(value="字典分组key")
    private String groupKey;

    @ApiModelPropertyCheck(value="字典分组名称")
    private String groupName;

    @ApiModelPropertyCheck(value="是否可修改")
    private Boolean modifiable;

    @ApiModelPropertyCheck(value="是否启用")
    private Boolean enabled;
}
