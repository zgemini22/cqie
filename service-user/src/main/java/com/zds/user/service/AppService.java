package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.AppVersionAddRequest;
import com.zds.biz.vo.request.user.AppVersionFindRequest;
import com.zds.biz.vo.request.user.AppVersionRequest;
import com.zds.biz.vo.request.user.AppVersionStatusRequest;
import com.zds.user.po.AppVersion;

/**
 * APP信息服务
 */
public interface AppService {

    /**
     * APP版本信息列表
     */
    IPage<AppVersion> findList(AppVersionFindRequest request);

    /**
     * 新增APP版本信息
     */
    boolean addAppVersion(AppVersionAddRequest request);

    /**
     * 更新APP版本信息状态
     */
    boolean updateAppVersionStatus(AppVersionStatusRequest request);

    /**
     * 删除APP版本信息
     */
    boolean deleteAppVersion(Long id);

    /**
     * 获取APP最新版本信息(燃气系统)
     */
    AppVersion getNewAppInfo(AppVersionRequest request);
}
