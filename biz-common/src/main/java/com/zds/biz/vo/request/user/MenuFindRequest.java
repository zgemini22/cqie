package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.MenuGroupEnum;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(description = "菜单查询请求")
public class MenuFindRequest extends BaseRequest {

    @ApiModelPropertyCheck(value="菜单分组,字典group_id=MENU_GROUP")
    private String menuGroup;

    @Override
    public void toRequestCheck() {
        if (StringUtils.isNotEmpty(menuGroup) && MenuGroupEnum.query(menuGroup) == null) {
            throw new BaseException("菜单分组值不在允许范围");
        }
    }
}
