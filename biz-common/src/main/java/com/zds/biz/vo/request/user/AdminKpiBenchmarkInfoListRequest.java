package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "标杆管理分页列表请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiBenchmarkInfoListRequest extends PageRequest {
    @ApiModelPropertyCheck(value = "指标名称")
    private String normName;

    @ApiModelPropertyCheck(value = "标杆状态（1=启用，2=停用）")
    private Integer status;
}
