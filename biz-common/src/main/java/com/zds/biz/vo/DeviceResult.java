package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(description = "设备对外接口返回结果")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResult implements Serializable {
    @ApiModelPropertyCheck("执行结果")
    private Boolean result;

    @ApiModelPropertyCheck("错误码")
    private String code;

    @ApiModelPropertyCheck("错误消息")
    private String message;


    public static  DeviceResult newInstance() {
        return new DeviceResult();
    }

    public static  DeviceResult success() {
        DeviceResult deviceResult = DeviceResult.newInstance();
        deviceResult.setResult(true);
        deviceResult.setCode("S0000");
        deviceResult.setMessage("message");
        return deviceResult;
    }
}