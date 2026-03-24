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
@TableName("tbl_bi_data")
@ApiModel(value = "TblBiData对象", description = "大屏填报信息")
public class TblBiData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "信息ID")
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

    @ApiModelPropertyCheck(value = "父ID")
    private Long parentId;

    @ApiModelPropertyCheck(value = "数据标识")
    private String biDataKey;

    @ApiModelPropertyCheck(value = "数据名称")
    private String biDataName;

    @ApiModelPropertyCheck(value = "数据模型")
    private String biDataJson;

    @ApiModelPropertyCheck(value = "数据注释")
    private String biDataComment;

    @ApiModelPropertyCheck(value = "数据实例")
    private String biDataExample;

    public static LambdaQueryWrapper<TblBiData> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
