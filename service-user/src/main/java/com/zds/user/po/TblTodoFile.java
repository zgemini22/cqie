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
@TableName("tbl_todo_file")
@ApiModel(value = "TblTodoFile对象", description = "待办事项文件")
public class TblTodoFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "待办id")
    private Long todoId;

    @ApiModelPropertyCheck(value = "附件原始文件名")
    private String realFileName;

    @ApiModelPropertyCheck(value = "附件保存文件名")
    private String fileName;

    @ApiModelPropertyCheck(value = "文件类型")
    private Integer fileType;

    public static LambdaQueryWrapper<TblTodoFile> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
