package com.zds.biz.vo;

/**
 * 表格-单元格实体
 */
public class TableTdBase {
    /**
     * 文本内容
     */
    private String value;
    /**
     * 跨行
     */
    private Integer rowspan = 1;
    /**
     * 跨列
     */
    private Integer colspan = 1;

    public TableTdBase() {
        this.value = "";
    }

    public TableTdBase(String value) {
        this.value = value;
    }

    public TableTdBase(String value, Integer rowspan, Integer colspan) {
        this.value = value;
        this.rowspan = rowspan;
        this.colspan = colspan;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getRowspan() {
        return rowspan;
    }

    public void setRowspan(Integer rowspan) {
        this.rowspan = rowspan;
    }

    public Integer getColspan() {
        return colspan;
    }

    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }
}
