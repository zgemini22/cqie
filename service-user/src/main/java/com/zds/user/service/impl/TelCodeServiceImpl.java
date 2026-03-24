package com.zds.user.service.impl;

import com.zds.biz.constant.user.BasicDataKeyEnum;
import com.zds.user.dao.TblBasicDataDao;
import com.zds.user.dao.TblTelCodeDao;
import com.zds.user.po.TblBasicData;
import com.zds.user.po.TblTelCode;
import com.zds.user.service.TelCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TelCodeServiceImpl implements TelCodeService {

    @Autowired
    private TblTelCodeDao telCodeDao;

    @Autowired
    private TblBasicDataDao basicDataDao;

    @Value("${phone-code.isDefaultCode}")
    private boolean isDefaultCode;

    @Value("${phone-code.defaultCode}")
    private String defaultCode;

    @Override
    public boolean saveCode(String phone, String code) {
        return telCodeDao.insert(TblTelCode.builder()
                .phone(phone)
                .code(code)
                .createTime(Calendar.getInstance().getTime())
                .build()) == 1;
    }

    @Override
    public boolean verifyCode(String phone, String code) {
        boolean flag = false;
        List<TblTelCode> list = telCodeDao.selectList(TblTelCode.getWrapper()
                .orderByDesc(TblTelCode::getCreateTime)
                .eq(TblTelCode::getPhone, phone));
        if (list != null && list.size() > 0) {
            int minute = getCodeEffectiveTime();
            Date createTime = list.get(0).getCreateTime();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -minute);
            if (createTime.compareTo(cal.getTime()) > -1) {
                flag = list.get(0).getCode().equals(code);
            }
        }
        if (isDefaultCode && code.equals(defaultCode)) {
            flag = true;
        }
        return flag;
    }

    private Integer getCodeEffectiveTime() {
        //手机验证码有效期参数,优先级：数据库配置>java枚举
        int time = Integer.parseInt(BasicDataKeyEnum.phoneCodeEffectiveTime.getTitle());
        TblBasicData basicData = basicDataDao.findByDataKey(BasicDataKeyEnum.phoneCodeEffectiveTime.getKey());
        if (basicData != null
                && org.apache.commons.lang3.StringUtils.isNotEmpty(basicData.getDataValue())
                && org.apache.commons.lang3.StringUtils.isNumeric(basicData.getDataValue())) {
            time = Integer.parseInt(basicData.getDataValue());
        }
        return time;
    }

    @Override
    public String findCode(String phone) {
        List<TblTelCode> list = telCodeDao.selectList(TblTelCode.getWrapper()
                .orderByDesc(TblTelCode::getCreateTime)
                .eq(TblTelCode::getPhone, phone));
        return list != null && list.size() > 0 ? list.get(0).getCode() : "";
    }
}
