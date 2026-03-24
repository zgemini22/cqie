package com.zds.biz.vo.request.flow;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(description = "根据流程key流程查询xml请求对象")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowGetXmlRequest {
    @ApiModelPropertyCheck("流程key")
    private String processId;
}
