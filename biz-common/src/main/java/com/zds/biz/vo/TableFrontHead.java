package com.zds.biz.vo;

import java.util.ArrayList;
import java.util.List;

public class TableFrontHead {
    private String title;
    private List<TableFrontHead> children;

    public TableFrontHead() {
    }

    public TableFrontHead(String title) {
        this.title = title;
    }

    public TableFrontHead(String title, List<TableFrontHead> children) {
        this.title = title;
        this.children = children;
    }

    public TableFrontHead(String title, int size) {
        this.title = title;
        List<TableFrontHead> children = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            children.add(new TableFrontHead());
        }
        this.children = children;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TableFrontHead> getChildren() {
        return children;
    }

    public void setChildren(List<TableFrontHead> children) {
        this.children = children;
    }
}
