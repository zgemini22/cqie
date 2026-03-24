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
public class BiFileSaveRequest {

    @ApiModelPropertyCheck(value = "文件ID")
    private Long id;

    @ApiModelPropertyCheck(value = "源文件名", required = true)
    private String fileName;

    @ApiModelPropertyCheck(value = "服务器保存文件名", required = true)
    private String fileRealName;

    @ApiModelPropertyCheck(value = "备注")
    private String remark;
}
