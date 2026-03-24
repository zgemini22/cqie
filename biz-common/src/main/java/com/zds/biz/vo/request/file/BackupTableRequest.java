package com.zds.biz.vo.request.file;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "后台数据库指定备份请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackupTableRequest {
    @ApiModelPropertyCheck(value = "数据库名称")
    private String databaseName;

    @ApiModelPropertyCheck(value = "表名")
    private String tableName;
}
