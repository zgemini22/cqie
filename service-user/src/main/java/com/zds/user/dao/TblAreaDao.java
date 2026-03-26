package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.response.user.AreaRangeInfo;
import com.zds.biz.vo.response.user.TblAreaResponse;
import java.util.List;

public interface TblAreaDao extends BaseMapper<TblAreaResponse> {
    // 联合查询所有区域数据（带分页）
    IPage<TblAreaResponse> selectAllAreaWithRange(Page<TblAreaResponse> page);

    // 联合查询所有区域数据（不需要分页）
    List<TblAreaResponse> selectAllAreaWithRangeWithoutPage();

    // 查询所有区域的基本信息（不含经纬度和排序）
    List<TblAreaResponse> selectAllAreaBasicInfo();

    // 根据code查询区域的经纬度和排序信息
    List<AreaRangeInfo> selectAreaRangeInfoByCode(String code);
}