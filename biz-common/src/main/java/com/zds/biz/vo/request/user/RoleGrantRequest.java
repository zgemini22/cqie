package com.zds.biz.vo.request.user;


import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "角色授权请求")
public class RoleGrantRequest extends BaseRequest {

    @ApiModelPropertyCheck(value="角色ID", required = true, example = "1")
    private Long id;

    @ApiModelPropertyCheck(value="菜单ID集合")
    private List<Long> menuIds;

    @Override
    public void toRequestCheck() {
        if (id == null || id < 1L) {
            throw new BaseException("角色ID不能为空");
        }
    }
}
