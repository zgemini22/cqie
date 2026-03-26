package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;

public class TblAreaTreeResponse extends TblAreaResponse {
    @ApiModelProperty("子节点")
    private List<TblAreaTreeResponse> children;

    public List<TblAreaTreeResponse> getChildren() {
        return children;
    }

    public void setChildren(List<TblAreaTreeResponse> children) {
        this.children = children;
    }
}