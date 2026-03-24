package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "后台我的待办返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoadAllTodoListPageListResponse {

    @ApiModelPropertyCheck("待办事项ID")
    private Long id;

    @ApiModelPropertyCheck("名称")
    private String name;

    @ApiModelPropertyCheck("待办类型")
    private String type;

    @ApiModelPropertyCheck("待办类型枚举(字典group_id=TODO_LIST)")
    private String typeEnum;

    @ApiModelPropertyCheck("是否为流程(1=流程,2=非流程)")
    private Integer process;

    @ApiModelPropertyCheck("发起人Id")
    private String initiatorId;

    @ApiModelPropertyCheck("发起人姓名")
    private String initiatorName;

    @ApiModelPropertyCheck(value = "待办人id")
    private String assigneeId;

    @ApiModelPropertyCheck(value = "待办人单位id")
    private String assigneeUnit;

    @ApiModelPropertyCheck("待办状态,1:未处理,2:已处理")
    private Integer status;

    @ApiModelPropertyCheck("表单地址")
    private String formUrl;

    @ApiModelPropertyCheck("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck("预期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dueTime;

    @ApiModelPropertyCheck("项目ID")
    private String projectId;

    @ApiModelPropertyCheck("隐患对应工程、三方施工、输配站业务ID")
    private Long businessId;
}
