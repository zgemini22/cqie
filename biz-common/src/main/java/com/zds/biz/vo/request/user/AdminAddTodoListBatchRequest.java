package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@ApiModel(description = "新增待办事项")
public class AdminAddTodoListBatchRequest{

    @ApiModelPropertyCheck("新增list")
    private List<AdminAddTodoListRequest> list;

}
