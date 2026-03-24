package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.BasicDataKeyEnum;
import com.zds.biz.vo.request.user.BasicDataRequest;
import com.zds.biz.vo.request.user.BasicSaveRequest;
import com.zds.biz.vo.response.user.BasicDataResponse;
import com.zds.user.dao.TblBasicDataDao;
import com.zds.user.po.TblBasicData;
import com.zds.user.service.BasicDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BasicDataServiceImpl implements BasicDataService {

    @Autowired
    private TblBasicDataDao basicDataDao;

    @Override
    public BasicDataResponse selectByKey(BasicDataRequest request) {
        BasicDataResponse data = BasicDataResponse.builder().build();
        if (StringUtils.isNotEmpty(request.getDataKey())) {
            LambdaQueryWrapper<TblBasicData> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TblBasicData::getDataKey, request.getDataKey());
            List<TblBasicData> list = basicDataDao.selectList(wrapper);
            if (list != null && list.size() > 0) {
                TblBasicData basicData = list.get(0);
                data = BasicDataResponse.builder()
                        .dataKey(basicData.getDataKey())
                        .dataName(basicData.getDataName())
                        .dataValue(basicData.getDataValue())
                        .remarks(basicData.getRemarks())
                        .build();
            }
        }
        return data;
    }

    @Override
    public List<BasicDataResponse> findList() {
        LambdaQueryWrapper<TblBasicData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblBasicData::getDeleted, false);
        return basicDataDao.selectList(wrapper).stream()
                .map(x -> BasicDataResponse.builder()
                        .dataKey(x.getDataKey())
                        .dataName(x.getDataName())
                        .dataValue(x.getDataValue())
                        .remarks(x.getRemarks())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(BasicSaveRequest request) {
        //查询数据标识
        BasicDataResponse data = selectByKey(BasicDataRequest.builder().dataKey(request.getDataKey()).build());
        if (data == null) {
            throw new BaseException("数据标识未找到");
        }
        if (BasicDataKeyEnum.fileMbMaxSize.getKey().equals(data.getDataKey()) && !StringUtils.isNumeric(data.getDataValue())) {
            throw new BaseException("上传文件最大限制只能填写正整数");
        }
        if (BasicDataKeyEnum.phoneCodeEffectiveTime.getKey().equals(data.getDataKey()) && !StringUtils.isNumeric(data.getDataValue())) {
            throw new BaseException("手机验证码有效期只能填写正整数");
        }
        return basicDataDao.updateById(TblBasicData.builder()
                .id(data.getId())
                .dataValue(request.getDataValue())
                .build()) == 1;
    }
}
