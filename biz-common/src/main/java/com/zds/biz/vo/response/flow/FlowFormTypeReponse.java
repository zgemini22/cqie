package com.zds.biz.vo.response.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "表单类型返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowFormTypeReponse {

    @ApiModelPropertyCheck("类型对应枚举值")
    private String typeEnum;

    @ApiModelPropertyCheck("类型名")
    private String typeName;
}
