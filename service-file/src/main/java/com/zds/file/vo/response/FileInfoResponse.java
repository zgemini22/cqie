package com.zds.file.vo.response;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@ApiModel(description = "文件信息返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoResponse {

    @ApiModelPropertyCheck(value = "文件ID")
    private Long id;

    @ApiModelPropertyCheck(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelPropertyCheck(value = "创建人")
    private Long createId;

    @ApiModelPropertyCheck(value = "创建人姓名")
    private String createName;

    @ApiModelPropertyCheck(value = "源文件名")
    private String fileName;

    @ApiModelPropertyCheck(value = "服务器保存文件名")
    private String realFileName;
}
