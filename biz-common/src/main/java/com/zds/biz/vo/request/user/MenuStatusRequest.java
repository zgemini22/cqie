package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.MenuStatusEnum;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "更新菜单状态请求")
public class MenuStatusRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "菜单ID", required = true, example = "1")
    private Long id;

    @ApiModelPropertyCheck(value="菜单状态,字典group_id=MENU_STATUS", required = true, example = "ENABLE")
    private String menuStatus;

    @Override
    public void toRequestCheck() {
        if (id == null || id < 1) {
            throw new BaseException("菜单ID不能为空");
        }
        if (MenuStatusEnum.query(menuStatus) == null) {
            throw new BaseException("菜单状态值不在允许范围");
        }
    }
}
