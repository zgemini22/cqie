package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.constant.user.MenuStatusEnum;
import com.zds.biz.vo.request.user.MenuFindRequest;
import com.zds.biz.vo.request.user.MenuSaveRequest;
import com.zds.biz.vo.request.user.MenuStatusRequest;
import com.zds.biz.vo.response.user.MenuDetailResponse;
import com.zds.biz.vo.response.user.MenuListResponse;
import com.zds.user.dao.TblMenuDao;
import com.zds.user.po.TblMenu;
import com.zds.user.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private TblMenuDao menuDao;

    @Override
    public boolean saveMenu(MenuSaveRequest request) {
        //检查入参
        request.toRequestCheck();
        //保存
        boolean addFlag = request.getId() == null;
        TblMenu po = new TblMenu();
        BeanUtils.copyProperties(request, po);
        if (addFlag) {
            po.setMenuStatus(MenuStatusEnum.ENABLE.getKey());
        }
        int count = addFlag ? menuDao.insert(po) : menuDao.updateById(po);
        return count == 1;
    }

    @Override
    public MenuDetailResponse findDetail(Long id) {
        LambdaQueryWrapper<TblMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblMenu::getId,id);
        TblMenu po = menuDao.selectOne(wrapper);
        MenuDetailResponse vo = new MenuDetailResponse();
        if (po != null) {
            BeanUtils.copyProperties(po, vo);
        }
        return vo;
    }

    @Override
    public boolean deleteById(Long id) {
        return menuDao.updateById(TblMenu.builder()
                .id(id)
                .deleted(true)
                .build()) == 1;
    }

    @Override
    public List<MenuListResponse> findList(MenuFindRequest request) {
        //检查入参
        request.toRequestCheck();
        //查询
        LambdaQueryWrapper<TblMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblMenu::getSort)
                .eq(TblMenu::getDeleted,false)
                .eq(StringUtils.isNotEmpty(request.getMenuGroup()), TblMenu::getMenuGroup,request.getMenuGroup());
        return menuDao.selectList(wrapper).stream()
                .map(po -> {
                    MenuListResponse vo = new MenuListResponse();
                    BeanUtils.copyProperties(po, vo);
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean updateStatus(MenuStatusRequest request) {
        //检查入参
        request.toRequestCheck();
        //更新
        int count = menuDao.updateById(TblMenu.builder()
                .id(request.getId())
                .menuStatus(request.getMenuStatus())
                .build());
        return count == 1;
    }
}
