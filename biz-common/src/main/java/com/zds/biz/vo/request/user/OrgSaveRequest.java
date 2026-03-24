package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "保存单位请求")
public class OrgSaveRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "单位ID,无则新增,有则修改")
    private Long id;

    @ApiModelPropertyCheck(value = "单位名称", required = true, max = 100)
    private String organizationName;

    @ApiModelPropertyCheck(value = "单位类别,字典group_id=ORGANIZATION_TYPE")
    private String organizationType;

    @ApiModelPropertyCheck(value = "单位状态,字典group_id=ORGANIZATION_STATUS")
    private String organizationStatus;

    @ApiModelPropertyCheck(value = "父级ID")
    private Long parentId;

    @Override
    public void toRequestCheck() {
        if (id != null && id.equals(parentId)) {
            throw new BaseException("上级单位不能选择自己");
        }
    }
}
