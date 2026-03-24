package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.request.user.DictGroupSaveRequest;
import com.zds.biz.vo.request.user.DictSaveRequest;
import com.zds.biz.vo.request.user.DictSelectRequest;
import com.zds.biz.vo.response.user.DictGroupSelectResponse;
import com.zds.biz.vo.response.user.DictSelectResponse;
import com.zds.user.dao.TblDictDao;
import com.zds.user.dao.TblDictSubsetDao;
import com.zds.user.po.TblDict;
import com.zds.user.po.TblDictSubset;
import com.zds.user.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private TblDictDao dictDao;

    @Autowired
    private TblDictSubsetDao dictSubsetDao;

    @Override
    public List<DictGroupSelectResponse> findGroupList() {
        LambdaQueryWrapper<TblDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblDict::getModifiable)
                .orderByDesc(TblDict::getCreateTime)
                .eq(TblDict::getDeleted, false);
        List<TblDict> list = dictDao.selectList(wrapper);
        List<DictGroupSelectResponse> dataList = new ArrayList<>();
        for (TblDict po : list) {
            DictGroupSelectResponse vo = new DictGroupSelectResponse();
            BeanUtils.copyProperties(po, vo);
            dataList.add(vo);
        }
        return dataList;
    }

    @Override
    public boolean saveGroup(DictGroupSaveRequest request) {
        return request.getId() == null ? addGroup(request) : updateGroup(request);
    }

    private boolean addGroup(DictGroupSaveRequest request) {
        LambdaQueryWrapper<TblDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblDict::getDeleted, false)
                .eq(TblDict::getGroupKey, request.getGroupKey());
        List<TblDict> list = dictDao.selectList(wrapper);
        if (list.size() > 0) {
            throw new BaseException("字典分组key已被使用");
        }
        return dictDao.insert(TblDict.builder()
                .groupKey(request.getGroupKey())
                .groupName(request.getGroupName())
                .modifiable(true)
                .enabled(true)
                .build()) == 1;
    }

    private boolean updateGroup(DictGroupSaveRequest request) {
        LambdaQueryWrapper<TblDict> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblDict::getDeleted, false)
                .eq(TblDict::getGroupKey, request.getGroupKey())
                .ne(TblDict::getId, request.getId());
        List<TblDict> list = dictDao.selectList(wrapper);
        if (list.size() > 0) {
            throw new BaseException("字典分组key已被使用");
        }
        return dictDao.updateById(TblDict.builder()
                .id(request.getId())
                .groupKey(request.getGroupKey())
                .groupName(request.getGroupName())
                .build()) == 1;
    }

    @Override
    public boolean enabledGroup(Long id) {
        TblDict dict = dictDao.selectById(id);
        if (!dict.getModifiable()) {
            throw new BaseException("指定字典分组不允许修改");
        }
        return dictDao.updateById(TblDict.builder()
                .id(dict.getId())
                .enabled(!dict.getEnabled())
                .build()) == 1;
    }

    @Override
    public boolean deleteGroup(Long id) {
        TblDict dict = dictDao.selectById(id);
        if (!dict.getModifiable()) {
            throw new BaseException("指定字典分组不允许删除");
        } else if (dict.getDeleted()) {
            return true;
        }
        boolean flag = dictDao.updateById(TblDict.builder()
                .id(dict.getId())
                .deleted(true)
                .build()) == 1;
        if (flag) {
            LambdaQueryWrapper<TblDictSubset> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TblDictSubset::getGroupKey, dict.getGroupKey());
            dictSubsetDao.update(TblDictSubset.builder().deleted(true).build(), wrapper);
        }
        return flag;
    }

    @Override
    public List<DictSelectResponse> findListByGroup(DictSelectRequest request) {
        LambdaQueryWrapper<TblDictSubset> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(TblDictSubset::getSort)
                .orderByDesc(TblDictSubset::getModifiable)
                .orderByDesc(TblDictSubset::getCreateTime)
                .orderByDesc(TblDictSubset::getUpdateTime)
                .eq(TblDictSubset::getDeleted, false);
        if (StringUtils.isNotEmpty(request.getGroupKey())) {
            wrapper.eq(TblDictSubset::getGroupKey, request.getGroupKey());
        }
        List<TblDictSubset> list = dictSubsetDao.selectList(wrapper);
        List<DictSelectResponse> dataList = new ArrayList<>();
        for (TblDictSubset po : list) {
            DictSelectResponse vo = new DictSelectResponse();
            BeanUtils.copyProperties(po, vo);
            dataList.add(vo);
        }
        return dataList;
    }

    @Override
    public boolean saveByGroup(DictSaveRequest request) {
        List<TblDictSubset> list1 = dictSubsetDao.selectList(TblDictSubset.getWrapper()
                .eq(TblDictSubset::getDeleted, false)
                .ne(request.getId() != null, TblDictSubset::getId, request.getId())
                .eq(TblDictSubset::getGroupKey, request.getGroupKey())
                .eq(TblDictSubset::getDictName, request.getDictName()));
        if (list1.size() > 0) {
            throw new BaseException("字典名称已被使用");
        }
        if (StringUtils.isNotEmpty(request.getDictValue())) {
            List<TblDictSubset> list = dictSubsetDao.selectList(TblDictSubset.getWrapper()
                    .eq(TblDictSubset::getDeleted, false)
                    .ne(request.getId() != null, TblDictSubset::getId, request.getId())
                    .eq(TblDictSubset::getGroupKey, request.getGroupKey())
                    .eq(TblDictSubset::getDictValue, request.getDictValue()));
            if (list.size() > 0) {
                throw new BaseException("字典值已被使用");
            }
        }
        return request.getId() == null ? addByGroup(request) : updateByGroup(request);
    }

    private boolean addByGroup(DictSaveRequest request) {
        TblDictSubset po = TblDictSubset.builder()
                .groupKey(request.getGroupKey())
                .dictName(request.getDictName())
                .dictValue(StringUtils.isNotEmpty(request.getDictValue()) ? request.getDictValue() : "")
                .sort(request.getSort())
                .remark(request.getRemark())
                .modifiable(true)
                .enabled(true)
                .build();
        boolean flag = dictSubsetDao.insert(po) == 1;
        if (flag) {
            //字典值不传则自动生成
            if (StringUtils.isEmpty(request.getDictValue())) {
                dictSubsetDao.updateById(TblDictSubset.builder()
                        .id(po.getId())
                        .dictValue(po.getId().toString())
                        .build());
            }
        }
        return flag;
    }

    private boolean updateByGroup(DictSaveRequest request) {
        boolean flag = dictSubsetDao.updateById(TblDictSubset.builder()
                .id(request.getId())
                .groupKey(request.getGroupKey())
                .dictName(request.getDictName())
                .dictValue(request.getDictValue())
                .sort(request.getSort())
                .remark(request.getRemark())
                .build()) == 1;
        if (flag) {
            //字典值不传则自动生成
            if (StringUtils.isEmpty(request.getDictValue())) {
                dictSubsetDao.updateById(TblDictSubset.builder()
                        .id(request.getId())
                        .dictValue(request.getId().toString())
                        .build());
            }
        }
        return flag;
    }

    @Override
    public boolean enabledByGroup(Long id) {
        TblDictSubset dictSubset = dictSubsetDao.selectById(id);
        if (!dictSubset.getModifiable()) {
            throw new BaseException("指定字典不允许修改");
        }
        return dictSubsetDao.updateById(TblDictSubset.builder()
                .id(dictSubset.getId())
                .enabled(!dictSubset.getEnabled())
                .build()) == 1;
    }

    @Override
    public boolean deleteByGroup(Long id) {
        TblDictSubset dictSubset = dictSubsetDao.selectById(id);
        if (!dictSubset.getModifiable()) {
            throw new BaseException("指定字典不允许删除");
        } else if (dictSubset.getDeleted()) {
            return true;
        }
        return dictSubsetDao.updateById(TblDictSubset.builder()
                .id(dictSubset.getId())
                .deleted(true)
                .build()) == 1;
    }

    @Override
    public Map<String, String> getDictMap() {
        LambdaQueryWrapper<TblDictSubset> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(TblDictSubset::getSort)
                .orderByDesc(TblDictSubset::getModifiable)
                .orderByDesc(TblDictSubset::getCreateTime)
                .eq(TblDictSubset::getDeleted, false);
        List<TblDictSubset> list = dictSubsetDao.selectList(wrapper);
        Map<String, String> map = new HashMap<>();
        for (TblDictSubset po : list) {
            map.put(po.getGroupKey() + po.getDictValue(), po.getDictName());
        }
        return map;
    }

    @Override
    public String getDictByKey(String groupKey, Integer dictValue) {
        return getDictByKey(groupKey, dictValue.toString());
    }

    @Override
    public String getDictByKey(String groupKey, String dictValue) {
        try {
            LambdaQueryWrapper<TblDictSubset> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TblDictSubset::getGroupKey, groupKey)
                    .eq(TblDictSubset::getDictValue, dictValue);
            List<TblDictSubset> list = dictSubsetDao.selectList(wrapper);
            if (list.size() > 0) {
                return list.get(0).getDictName();
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public Map<String, String> findDictMapByGroup(List<String> request) {
        return dictSubsetDao.selectList(TblDictSubset.getWrapper()
                .orderByAsc(TblDictSubset::getSort)
                .orderByDesc(TblDictSubset::getModifiable)
                .orderByDesc(TblDictSubset::getCreateTime)
                .in(request != null && request.size() > 0, TblDictSubset::getGroupKey, request))
                .stream()
                .collect(Collectors.toMap(x -> x.getGroupKey() + "_" + x.getDictValue(), TblDictSubset::getDictName, (a, b) -> b));
    }
}
