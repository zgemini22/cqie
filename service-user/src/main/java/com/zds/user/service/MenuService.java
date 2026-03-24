package com.zds.user.service;


import com.zds.biz.vo.request.user.MenuFindRequest;
import com.zds.biz.vo.request.user.MenuSaveRequest;
import com.zds.biz.vo.request.user.MenuStatusRequest;
import com.zds.biz.vo.response.user.MenuDetailResponse;
import com.zds.biz.vo.response.user.MenuListResponse;

import java.util.List;

/**
 * 菜单服务
 */
public interface MenuService {
    /**
     * 保存菜单
     */
    boolean saveMenu(MenuSaveRequest request);
    /**
     * 菜单详情
     */
    MenuDetailResponse findDetail(Long id);
    /**
     * 菜单删除
     */
    boolean deleteById(Long id);
    /**
     * 菜单列表
     */
    List<MenuListResponse> findList(MenuFindRequest request);
    /**
     * 更新菜单状态
     */
    boolean updateStatus(MenuStatusRequest request);
}
