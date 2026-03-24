package com.zds.user.manager.impl;

import com.zds.user.dao.TblDictSubsetDao;
import com.zds.user.manager.DictManager;
import com.zds.user.po.TblDictSubset;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictManagerImpl implements DictManager {

    @Autowired
    private TblDictSubsetDao dictSubsetDao;

    @Override
    public Map<String, String> getDictSubsetMap(String groupKey, List<String> dictNames) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isNotEmpty(groupKey) && dictNames.size() > 0) {
            map = dictSubsetDao.selectList(TblDictSubset.getWrapper()
                        .eq(TblDictSubset::getGroupKey, groupKey)
                        .in(TblDictSubset::getDictValue, dictNames))
                    .stream()
                    .collect(Collectors.toMap(TblDictSubset::getDictValue, TblDictSubset::getDictName, (a, b) -> b));
        }
        return map;
    }
}
