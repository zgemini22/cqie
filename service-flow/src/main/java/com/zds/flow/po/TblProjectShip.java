package com.zds.flow.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_project_ship")
@ApiModel(value = "TblProjectShip对象", description = "项目与流程id关联存放表")
public class TblProjectShip implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "流程实例id")
    private String processInstanceId;

    @ApiModelPropertyCheck(value = "流程定义id")
    private String processDefinitionId;

    @ApiModelPropertyCheck(value = "项目id")
    private Long projectId;

    @ApiModelPropertyCheck(value = "项目类型")
    private String projectType;

    @ApiModelPropertyCheck(value = "发起人id")
    private String initiatorId;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public static LambdaQueryWrapper<TblProjectShip> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
