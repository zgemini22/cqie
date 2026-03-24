package com.zds.user.service.impl;

import com.zds.biz.vo.request.user.DemoAddRequest;
import com.zds.user.dao.DemoDao;
import com.zds.user.po.AppVersion;
import com.zds.user.po.Demo;
import com.zds.user.service.DemoService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * @author WuJianQiang
 * @date : 2026/3/12
 */
@Service
public class DemoServiceImpl implements DemoService {
    @Resource
    private DemoDao demoDao;

    @Override
    public boolean add(DemoAddRequest request) {
//        Demo demo = Demo.builder()
//                .name("测试")
//                .age(20)
//                .build();
//        Demo demo = new Demo();
//        demo.setName("测试");
//        demo.setAge(20);
//        BeanUtils.copyProperties(request, demo);
//        return demoDao.insert(demo) > 0;
//        AppVersion appVersion = AppVersion.builder()
//                .createTime(Calendar.getInstance().getTime())
//                .build();
//        appVersion.setAppType("ios");
//        Demo demo = Demo.builder()
//                .name("测试")
//                .age(20)
//                .build();
        Demo demo = new Demo();
        BeanUtils.copyProperties(request, demo);
        return demoDao.insert(demo) > 0;
    }
}
