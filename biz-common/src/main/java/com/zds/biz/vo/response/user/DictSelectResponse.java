package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "字典列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictSelectResponse {

    @ApiModelPropertyCheck(value="字典子集ID")
    private Long id;

    @ApiModelPropertyCheck(value="字典分组key")
    private String groupKey;

    @ApiModelPropertyCheck(value="字典名称")
    private String dictName;

    @ApiModelPropertyCheck(value="字典值")
    private String dictValue;

    @ApiModelPropertyCheck(value="排序号")
    private Integer sort;

    @ApiModelPropertyCheck(value="是否可修改")
    private Boolean modifiable;

    @ApiModelPropertyCheck(value="是否启用")
    private Boolean enabled;

    @ApiModelPropertyCheck(value="备注")
    private String remark;
}
