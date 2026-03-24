package com.zds.biz.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "分页信息返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    @ApiModelPropertyCheck(value="页码",position=1)
    private int pageNum;

    @ApiModelPropertyCheck(value="每页条数",position=2)
    private int pageSize;

    @ApiModelPropertyCheck(value="总条数",position=3)
    private long total;

    @ApiModelPropertyCheck(value="总页数",position=4)
    private int pages;

    @ApiModelPropertyCheck(value="分页集合数据",position=5)
    private List<T> list;

    @ApiModelPropertyCheck(value="上一页页码",position=6)
    private int prePage;

    @ApiModelPropertyCheck(value="下一页页码",position=7)
    private int nextPage;

    public static <T> PageResponse<T> getInstance(Page<T> pageInfo) {
        PageResponse response = (PageResponse<T>) PageResponse.builder()
                .pageNum((int) pageInfo.getCurrent())
                .pageSize((int) pageInfo.getSize())
                .total(pageInfo.getTotal())
                .pages((int) pageInfo.getPages())
                .build();
        response.setList(pageInfo.getRecords());
        return response;
    }

    public static <T> PageResponse<T> getInstance(Page pageInfo, List<T> list) {
        PageResponse response = (PageResponse<T>) PageResponse.builder()
                .pageNum((int) pageInfo.getCurrent())
                .pageSize((int) pageInfo.getSize())
                .total(pageInfo.getTotal())
                .pages((int) pageInfo.getPages())
                .build();
        response.setList(list);
        return response;
    }

    public static <T> PageResponse<T> getInstance(List<T> list, int pageNum, int pageSize, int total) {
        int pages = (int) Math.ceil((double) total / pageSize);
        PageResponse response = (PageResponse<T>) PageResponse.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .total(total)
                .pages(pages)
                .prePage(pages == pageNum ? pageNum : pageNum - 1)
                .nextPage(pages == pageNum ? pageNum : pageNum + 1)
                .build();
        response.setList(list);
        return response;
    }
}
