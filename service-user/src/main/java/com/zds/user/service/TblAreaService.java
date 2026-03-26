package com.zds.user.service;

import com.zds.biz.vo.response.user.TblAreaTreeResponse;
import java.util.List;

public interface TblAreaService {
    // 获取树形结构的区域数据
    List<TblAreaTreeResponse> getAreaTree();
}