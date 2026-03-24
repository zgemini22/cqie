package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportLecturerVo {

    @ApiModelPropertyCheck(value = "讲师姓名")
    private String lecturerName;

    @ApiModelPropertyCheck(value = "性别(1:男,2女)")
    private String gender;

    @ApiModelPropertyCheck(value = "联系电话")
    private String telephone;

    @ApiModelPropertyCheck(value = "擅长领域,多个以逗号分割")
    private String field;

    @ApiModelPropertyCheck(value = "讲师介绍")
    private String lecturerIntroduce;

    @ApiModelProperty(value="行数")
    private Integer row;

}

