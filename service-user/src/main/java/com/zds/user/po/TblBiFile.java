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
@TableName("tbl_bi_file")
@ApiModel(value = "TblBiFile对象", description = "大屏资源记录")
public class TblBiFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "文件ID")
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

    @ApiModelPropertyCheck(value = "源文件名")
    private String fileName;

    @ApiModelPropertyCheck(value = "服务器保存文件名")
    private String fileRealName;

    @ApiModelPropertyCheck(value = "文件大小,单位KB")
    private Long fileSize;

    @ApiModelPropertyCheck(value = "备注")
    private String remark;

    public static LambdaQueryWrapper<TblBiFile> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
