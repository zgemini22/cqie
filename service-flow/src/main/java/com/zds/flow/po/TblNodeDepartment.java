package com.zds.flow.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_node_department")
@ApiModel(value = "TblNodeDepartment对象", description = "节点对应表")
public class TblNodeDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "流程id")
    private Long processId;

    @ApiModelPropertyCheck(value = "节点id")
    private String nodeId;

    @ApiModelPropertyCheck(value = "部门id")
    private String departmentId;

    @ApiModelPropertyCheck(value = "任务周期")
    private Integer dueDay;

    public static LambdaQueryWrapper<TblNodeDepartment> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
