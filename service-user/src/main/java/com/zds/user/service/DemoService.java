package com.zds.user.service;

import com.zds.biz.vo.request.user.DemoAddRequest;
import com.zds.user.po.Demo;

/**
 * @author WuJianQiang
 * @date : 2026/3/12
 */
public interface DemoService {
    boolean add(DemoAddRequest request);
}
