package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "字典保存请求")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictSaveRequest extends BaseRequest {

    @ApiModelPropertyCheck(value="字典子集ID", notes = "不存在则新增,存在则修改")
    private Long id;

    @ApiModelPropertyCheck(value="字典分组key", required = true)
    private String groupKey;

    @ApiModelPropertyCheck(value="字典名称", required = true)
    private String dictName;

    @ApiModelPropertyCheck(value="字典值", notes = "不传则自动生成")
    private String dictValue;

    @ApiModelPropertyCheck(value="排序号")
    private Integer sort;

    @ApiModelPropertyCheck(value="备注")
    private String remark;
}
