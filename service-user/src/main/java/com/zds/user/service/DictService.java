package com.zds.user.service;

import com.zds.biz.vo.request.user.DictGroupSaveRequest;
import com.zds.biz.vo.request.user.DictSaveRequest;
import com.zds.biz.vo.request.user.DictSelectRequest;
import com.zds.biz.vo.response.user.DictGroupSelectResponse;
import com.zds.biz.vo.response.user.DictSelectResponse;

import java.util.List;
import java.util.Map;

public interface DictService {
    /**
     * 字典分组列表
     */
    List<DictGroupSelectResponse> findGroupList();
    /**
     * 字典分组保存
     */
    boolean saveGroup(DictGroupSaveRequest request);
    /**
     * 字典分组启用/禁用
     */
    boolean enabledGroup(Long id);
    /**
     * 字典分组删除
     */
    boolean deleteGroup(Long id);
    /**
     * 字典列表
     */
    List<DictSelectResponse> findListByGroup(DictSelectRequest request);
    /**
     * 字典保存
     */
    boolean saveByGroup(DictSaveRequest request);
    /**
     * 字典启用/禁用
     */
    boolean enabledByGroup(Long id);
    /**
     * 字典删除
     */
    boolean deleteByGroup(Long id);
    /**
     * 获取数据字典map
     */
    Map<String, String> getDictMap();
    /**
     * 获取字典名称
     */
    String getDictByKey(String groupKey, Integer dictValue);
    /**
     * 获取字典名称
     */
    String getDictByKey(String groupKey, String dictValue);
    /**
     * 查询指定字典分组范围的字典名称
     */
    Map<String, String> findDictMapByGroup(List<String> request);
}
