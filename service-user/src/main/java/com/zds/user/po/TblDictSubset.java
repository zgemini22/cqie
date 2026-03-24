package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
@TableName("tbl_dict_subset")
@ApiModel(value = "TblDictSubset对象", description = "字典子集")
public class TblDictSubset implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "字典子集ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人")
    private Long createId;

    @ApiModelPropertyCheck(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "修改人")
    private Long updateId;

    @ApiModelPropertyCheck(value = "是否删除")
    private Boolean deleted;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "字典分组key")
    private String groupKey;

    @ApiModelPropertyCheck(value = "字典名称")
    private String dictName;

    @ApiModelPropertyCheck(value = "字典值")
    private String dictValue;

    @ApiModelPropertyCheck(value = "排序号")
    private Integer sort;

    @ApiModelPropertyCheck(value = "是否可修改")
    private Boolean modifiable;

    @ApiModelPropertyCheck(value = "是否启用")
    private Boolean enabled;

    @ApiModelPropertyCheck(value = "备注")
    private String remark;

    public static LambdaQueryWrapper<TblDictSubset> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
