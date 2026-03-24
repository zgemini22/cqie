package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel(value = "键值对元素返回基类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyAndValueVo {

    @ApiModelPropertyCheck(value = "参数名")
    private String key;

    @ApiModelPropertyCheck(value = "参数值")
    private BigDecimal value;

    @ApiModelPropertyCheck(value = "参数占比")
    private BigDecimal ratio;

    public KeyAndValueVo(String key, BigDecimal value) {
        this.key = key;
        this.value = value;
    }
}
