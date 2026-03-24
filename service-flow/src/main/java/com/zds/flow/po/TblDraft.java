package com.zds.flow.po;

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
@TableName("tbl_draft")
@ApiModel(value = "TblDraft����", description = "流程部署草稿")
public class TblDraft implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "是否删除")
    private Boolean deleted;

    @ApiModelPropertyCheck(value = "流程名称")
    private String processName;

    @ApiModelPropertyCheck(value = "流程key")
    private String processKey;

    @ApiModelPropertyCheck(value = "流程xml")
    private String processXml;

    @ApiModelPropertyCheck(value = "是否发布（1-已发布,2-未发布）")
    private Integer processStatus;

    @ApiModelPropertyCheck(value = "流程类别")
    private String processType;

    @ApiModelPropertyCheck(value = "组织id")
    private Long organizationId;

    public static LambdaQueryWrapper<TblDraft> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
