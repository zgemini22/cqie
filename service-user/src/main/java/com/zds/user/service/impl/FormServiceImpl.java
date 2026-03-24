package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.flow.ProcessTypeEnum;
import com.zds.biz.constant.user.OrganizationTypeEnum;
import com.zds.biz.constant.user.UserTodoListEnum;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowAddOutsideFormRequest;
import com.zds.biz.vo.request.flow.FlowOutsideFormRequest;
import com.zds.biz.vo.response.flow.FlowFormTypeReponse;
import com.zds.biz.vo.response.flow.FlowOutsideFormResponse;
import com.zds.biz.vo.response.flow.FlowProcessTypeReponse;
import com.zds.user.dao.TblFormOutsideDao;
import com.zds.user.po.TblFormOutside;
import com.zds.user.service.FormService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FormServiceImpl implements FormService {

    @Autowired
    private TblFormOutsideDao formOutsideDao;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Override
    public boolean saveForm(FlowAddOutsideFormRequest request) {
        UserTodoListEnum todoEnum = UserTodoListEnum.query(request.getFormType());
        if (todoEnum == null) {
            throw new BaseException("表单类型不存在");
        }
        TblFormOutside po = formOutsideDao.selectOne(TblFormOutside.getWrapper().eq(TblFormOutside::getFormType, request.getFormType()));
        if (ObjectUtils.isNotEmpty(po)) {
            po.setFormUrl(request.getFormUrl());
            formOutsideDao.updateById(po);
        } else {
            formOutsideDao.insert(TblFormOutside.builder()
                    .formType(request.getFormType())
                    .formName(todoEnum.getTitle())
                    .formUrl(request.getFormUrl())
                    .build());
        }
        return true;
    }

    @Override
    public List<FlowFormTypeReponse> findFormType() {
        return Arrays.stream(UserTodoListEnum.values())
                .map(x -> FlowFormTypeReponse.builder()
                        .typeEnum(x.getKey())
                        .typeName(x.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowFormTypeReponse> findFlowFormType() {
        String userType = threadLocalUtil.getOrganizationType();
        if (userType.equals(OrganizationTypeEnum.COMPANY.getKey())){
            List<FlowFormTypeReponse> reponses = new ArrayList<>();
            reponses.add(FlowFormTypeReponse.builder()
                    .typeEnum(ProcessTypeEnum.GAS_SUPPLY.getKey())
                    .typeName(ProcessTypeEnum.GAS_SUPPLY.getTitle())
                    .build());
            reponses.add(FlowFormTypeReponse.builder()
                    .typeEnum(ProcessTypeEnum.GAS_STOP.getKey())
                    .typeName(ProcessTypeEnum.GAS_STOP.getTitle())
                    .build());
            reponses.add(FlowFormTypeReponse.builder()
                    .typeEnum(ProcessTypeEnum.GAS_SUPPLY_PLAN.getKey())
                    .typeName(ProcessTypeEnum.GAS_SUPPLY_PLAN.getTitle())
                    .build());
            reponses.add(FlowFormTypeReponse.builder()
                    .typeEnum(ProcessTypeEnum.GAS_STOP_PLAN.getKey())
                    .typeName(ProcessTypeEnum.GAS_STOP_PLAN.getTitle())
                    .build());
            return reponses;
        }else {
            Set<String> excludedKeys = Stream.of(
                    ProcessTypeEnum.GAS_STOP.getKey(),
                    ProcessTypeEnum.GAS_SUPPLY.getKey(),
                    ProcessTypeEnum.GAS_SUPPLY_PLAN.getKey(),
                    ProcessTypeEnum.GAS_STOP_PLAN.getKey()
            ).collect(Collectors.toSet());

            return Stream.of(ProcessTypeEnum.values())
                    .filter(value -> !excludedKeys.contains(value.getKey()))  // 排除停气供气类型
                    .map(value -> FlowFormTypeReponse.builder()
                            .typeEnum(value.getKey())
                            .typeName(value.getTitle())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<FlowFormTypeReponse> findNoFlowFormType() {
        return Arrays.stream(UserTodoListEnum.values())
                .filter(x -> x.getType() == 2)
                .map(x -> FlowFormTypeReponse.builder()
                        .typeEnum(x.getKey())
                        .typeName(x.getTitle())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public String getOutsideForm(String type) {
        TblFormOutside po = formOutsideDao.selectOne(TblFormOutside.getWrapper().eq(TblFormOutside::getFormType, type));
        return po != null ? po.getFormUrl() : null;
    }

    @Override
    public IPage<FlowOutsideFormResponse> getOutsideFormList(FlowOutsideFormRequest request) {
        LambdaQueryWrapper<TblFormOutside> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getFormName())) {
            wrapper.like(TblFormOutside::getFormName, request.getFormName());
        }
        if (StringUtils.isNotEmpty(request.getFormType())) {
            wrapper.like(TblFormOutside::getFormType, request.getFormType());
        }
        if (StringUtils.isNotEmpty(request.getFormUrl())) {
            wrapper.like(TblFormOutside::getFormUrl, request.getFormUrl());
        }
        Page<TblFormOutside> page = new Page<>(request.getPageNum(), request.getPageSize());
        Page<TblFormOutside> page1 = formOutsideDao.selectPage(page, wrapper);
        return page1.convert(x -> FlowOutsideFormResponse.builder()
                .id(x.getId())
                .formName(x.getFormName())
                .formType(x.getFormType())
                .formUrl(x.getFormUrl())
                .build());
    }

    @Override
    public boolean outsideFormDelete(IdRequest request) {
        return formOutsideDao.deleteById(request.getId()) > 0;
    }
}
