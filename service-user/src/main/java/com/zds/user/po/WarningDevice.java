package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;

@Data
@TableName("warning_device")
public class WarningDevice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long orgId;
    private Integer relatedSubjectType;
    private Integer relatedSubjectId;
    private String warningNo;
    private String warningSource;
    private String warningDesc;
    private Long deviceId;
    private String verifyResult;
    private Date warningTime;
    private Long assigneeId;
    private String assigneeName;
    private Date deadlineStart;
    private Date deadlineEnd;
    private Long operatorId;
    private String operatorName;
    private Long currentAssigneeId;
    private String currentAssigneeName;
    private String processDesc;
    private Date falseHandleTime;
    private String falseHandleDesc;
    private Date hiddenHandleTime;
    private String hiddenLevel;
    private Date hiddenRectifyStart;
    private Date hiddenRectifyEnd;
    private String hiddenDesc;
    private Long createId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Long updateId;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic
    private Integer deleted;
    private String monitorType;
}