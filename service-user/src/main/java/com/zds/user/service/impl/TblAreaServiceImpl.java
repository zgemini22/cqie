package com.zds.user.service.impl;

import com.zds.biz.vo.response.user.AreaRangeInfo;
import com.zds.biz.vo.response.user.TblAreaResponse;
import com.zds.biz.vo.response.user.TblAreaTreeResponse;
import com.zds.user.dao.TblAreaDao;
import com.zds.user.service.TblAreaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TblAreaServiceImpl implements TblAreaService {

    @Autowired
    private TblAreaDao tblAreaDao;

    @Override
    public List<TblAreaTreeResponse> getAreaTree() {
        // 获取所有区域的基本信息
        List<TblAreaResponse> areas = tblAreaDao.selectAllAreaBasicInfo();

        // 为每个区域添加经纬度和排序信息
        for (TblAreaResponse area : areas) {
            List<AreaRangeInfo> rangeInfoList = tblAreaDao.selectAreaRangeInfoByCode(area.getCode());
            area.setRangeInfoList(rangeInfoList);
        }

        // 转换为树形结构
        return buildAreaTree(areas);
    }

    /**
     * 构建区域树形结构
     */
    private List<TblAreaTreeResponse> buildAreaTree(List<TblAreaResponse> allAreas) {
        // 1. 转换所有数据为树形节点
        List<TblAreaTreeResponse> treeNodes = new ArrayList<>();
        for (TblAreaResponse area : allAreas) {
            TblAreaTreeResponse treeNode = new TblAreaTreeResponse();
            // 复制属性
            BeanUtils.copyProperties(area, treeNode);
            treeNodes.add(treeNode);
        }

        // 2. 构建树形结构
        Map<String, TblAreaTreeResponse> nodeMap = new HashMap<>();
        List<TblAreaTreeResponse> rootNodes = new ArrayList<>();

        // 先将所有节点放入map
        for (TblAreaTreeResponse node : treeNodes) {
            nodeMap.put(node.getCode(), node);
        }

        // 构建父子关系
        for (TblAreaTreeResponse node : treeNodes) {
            String parentCode = node.getParentCode();
            // 调整根节点判断逻辑，确保 parentCode 为 "0" 的节点被当作根节点
            if (parentCode == null || parentCode.isEmpty() || "0".equals(parentCode) || !nodeMap.containsKey(parentCode)) {
                // 根节点：parentCode为空、为"0"或不存在对应的父节点
                rootNodes.add(node);
            } else {
                // 子节点，添加到父节点的children中
                TblAreaTreeResponse parentNode = nodeMap.get(parentCode);
                if (parentNode != null) {
                    if (parentNode.getChildren() == null) {
                        parentNode.setChildren(new ArrayList<>());
                    }
                    parentNode.getChildren().add(node);
                }
            }
        }

        return rootNodes;
    }
}