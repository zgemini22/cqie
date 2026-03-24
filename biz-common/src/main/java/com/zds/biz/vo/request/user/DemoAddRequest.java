package com.zds.biz.vo.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author WuJianQiang
 * @date : 2026/3/12
 */
@Data
@ApiModel(description = "Demo表新增请求")
public class DemoAddRequest {
    @ApiModelPropertyCheck(value = "字符测试")
    private String name;

    @ApiModelPropertyCheck(value = "数字测试")
    private Integer age;

    @ApiModelPropertyCheck(value = "日期测试")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birth;

}
