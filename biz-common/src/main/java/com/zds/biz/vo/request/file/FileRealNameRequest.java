package com.zds.biz.vo.request.file;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRealNameRequest {

    @ApiModelPropertyCheck(value = "服务器保存文件名")
    private String realFileName;
}
