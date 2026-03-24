package com.zds.biz.vo;

import lombok.Data;

import java.util.List;

@Data
public class ContrastVo<T> {
    private List<T> addList;
    private List<T> delList;
}
