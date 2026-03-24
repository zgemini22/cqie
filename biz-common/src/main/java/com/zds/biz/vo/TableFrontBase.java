package com.zds.biz.vo;

import java.util.List;

/**
 * 前端框架要求的动态表格数据格式
 */
public class TableFrontBase {
    /**
     * 表头
     */
    private List<TableFrontHead> headList;
    /**
     * 表体
     */
    private List<List<String>> bodyList;

    public List<TableFrontHead> getHeadList() {
        return headList;
    }

    public void setHeadList(List<TableFrontHead> headList) {
        this.headList = headList;
    }

    public List<List<String>> getBodyList() {
        return bodyList;
    }

    public void setBodyList(List<List<String>> bodyList) {
        this.bodyList = bodyList;
    }
}
