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
@TableName("tbl_task_ship")
@ApiModel(value = "TblTaskShip对象", description = "待办id关联存放表")
public class TblTaskShip implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "流程引擎任务Id")
    private String taskId;

    @ApiModelPropertyCheck(value = "待办任务Id")
    private String todoId;

    @ApiModelPropertyCheck(value = "工程id")
    private String projectId;

    public static LambdaQueryWrapper<TblTaskShip> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
