package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "系统信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoVo {

    @ApiModelPropertyCheck(value="系统名称")
    private String name;

    @ApiModelPropertyCheck(value="菜单分组,字典group_id=MENU_GROUP")
    private String menuGroup;

    @ApiModelPropertyCheck(value="访问地址")
    private String url;
}
