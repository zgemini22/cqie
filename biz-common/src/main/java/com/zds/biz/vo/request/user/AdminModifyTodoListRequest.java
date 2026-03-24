package com.zds.biz.vo.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

import java.util.Date;


@Data
@ApiModel(description = "修改待办事项")
public class AdminModifyTodoListRequest extends BaseRequest {

    @ApiModelPropertyCheck("消息ID")
    private Long id;

    @ApiModelPropertyCheck("组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck("标题")
    private String title;

    @ApiModelPropertyCheck("内容")
    private String content;

    @ApiModelPropertyCheck("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelPropertyCheck("数据来源,1:流程引擎,2:协同监管")
    private Integer dataWay;

    @ApiModelPropertyCheck("数据来源ID")
    private String dataId;
    @Override
    public void toRequestCheck() {
        if(id == null || id < 1){
            throw new BaseException("消息ID不能为空");
        }
        if(organizationId == null || organizationId < 1){
            throw new BaseException("组织ID不能为空");
        }
        if(title == null){
            throw new BaseException("事项标题不能为空");
        }
        if(content == null){
            throw new BaseException("事项内容不能为空");
        }
        if(startTime == null){
            throw new BaseException("开始时间不能为空");
        }
        if(endTime == null){
            throw new BaseException("结束时间不能为空");
        }
    }
}
