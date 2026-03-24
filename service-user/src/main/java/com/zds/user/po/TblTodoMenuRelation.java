package com.zds.user.po;

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
@TableName("tbl_todo_menu_relation")
@ApiModel(value = "TblTodoMenuRelation对象", description = "待办菜单关联")
public class TblTodoMenuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "业务枚举字典group_id=MESSAGE_SOURCE、TODO_LIST")
    private String workEnum;

    @ApiModelPropertyCheck(value = "菜单ID集合,以逗号分隔")
    private String menuIds;

    public static LambdaQueryWrapper<TblTodoMenuRelation> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
