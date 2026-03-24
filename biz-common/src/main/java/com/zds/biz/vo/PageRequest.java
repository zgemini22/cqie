package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "分页通用请求对象")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "页码", required = true, example = "1")
    private int pageNum = 1;

    @ApiModelPropertyCheck(value = "每页条数", required = true, example = "10")
    private int pageSize = 10;

    @Override
    public void toRequestCheck() {
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 1;
        } else if (pageSize > 200) {
            pageSize = 200;
        }
    }
}
