package com.zds.biz.vo;

import lombok.Data;

import java.util.List;

@Data
public class ContrastUpVo<T> {
    private List<T> addList;
    private List<T> upList;
    private List<T> delList;
}
