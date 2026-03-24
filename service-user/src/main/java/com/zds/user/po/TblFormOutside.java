package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_form_outside")
@ApiModel(value = "TblFormOutside对象", description = "外部表单存放表")
public class TblFormOutside implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "表单类型")
    private String formType;

    @ApiModelPropertyCheck(value = "表单地址")
    private String formUrl;

    @ApiModelPropertyCheck(value = "表单名称")
    private String formName;

    public static LambdaQueryWrapper<TblFormOutside> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
