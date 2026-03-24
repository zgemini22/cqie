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
@TableName("tbl_todo_list")
@ApiModel(value = "TblTodoList对象", description = "待办事项")
public class TblTodoList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "待办事项ID")
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

    @ApiModelPropertyCheck(value = "待办事项编码")
    private String todoCode;

    @ApiModelPropertyCheck(value = "名称")
    private String name;

    @ApiModelPropertyCheck(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelPropertyCheck(value = "期限时间(预计工作日天数)")
    private Integer dueTime;

    @ApiModelPropertyCheck(value = "实际工作日天数")
    private Integer workTime;

    @ApiModelPropertyCheck(value = "事务时间状态,1:正常完成,2:提前完成,3:超期完成")
    private Integer timeOut;

    @ApiModelPropertyCheck(value = "待办状态,1:未处理,2:已处理,3:无需处理")
    private Integer status;

    @ApiModelPropertyCheck(value = "数据来源ID")
    private String dataId;

    @ApiModelPropertyCheck(value = "待办类型,字典group_id=TODO_LIST")
    private String type;

    @ApiModelPropertyCheck(value = "发起人id")
    private String initiatorId;

    @ApiModelPropertyCheck(value = "待办人id")
    private String assigneeId;

    @ApiModelPropertyCheck(value = "待办人单位id")
    private String assigneeUnit;

    @ApiModelPropertyCheck(value = "表单地址")
    private String formUrl;

    @ApiModelPropertyCheck(value = "审批意见")
    private String commentText;

    @ApiModelPropertyCheck(value = "是否通过")
    private String pass;

    @ApiModelPropertyCheck(value = "是否为流程(1=流程,2=非流程)")
    private Integer process;

    public static LambdaQueryWrapper<TblTodoList> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
