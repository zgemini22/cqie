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
@TableName("hd_demo")
@ApiModel(value = "HdDemo对象", description = "处置节点信息表")
public class HdDemo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "处置时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date initiateDate;

    @ApiModelPropertyCheck(value = "节点id")
    private String taskId;

    @ApiModelPropertyCheck(value = "节点名称")
    private String name;

    @ApiModelPropertyCheck(value = "节点级别（1=市级，2=区级，3=镇街）")
    private Integer taskLevel;

    @ApiModelPropertyCheck(value = "节点类型（1=先期处置，2=事故处理，3=事故上报）")
    private Integer type;

    @ApiModelPropertyCheck(value = "操作者")
    private String handler;

    @ApiModelPropertyCheck(value = "联系方式")
    private String handlerPhone;

    @ApiModelPropertyCheck(value = "处置周期")
    private Integer dueDay;

    @ApiModelPropertyCheck(value = "处置状态,1=已处理,2=未处理")
    private Integer status;

    @ApiModelPropertyCheck(value = "发起时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startDate;

    @ApiModelPropertyCheck(value = "处置状态,1=已完成(未超时),2=进行中,3=超期")
    private Integer completeStatus;

    @ApiModelPropertyCheck(value = "该处置环节的动作")
    private String execuNode;

    @ApiModelPropertyCheck(value = "处置部门")
    private String groupName;

    @ApiModelPropertyCheck(value = "处理意见")
    private String commentConet;

    @ApiModelPropertyCheck(value = "事故id")
    private Long accidentId;

    public static LambdaQueryWrapper<HdDemo> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
