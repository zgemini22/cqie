package com.zds.biz.vo.request.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "字典列表请求")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictSelectRequest {

    @ApiModelPropertyCheck(value="字典分组key")
    private String groupKey;
}
