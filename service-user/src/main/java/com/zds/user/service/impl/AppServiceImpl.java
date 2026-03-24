package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.AppTypeEnum;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.AppVersionAddRequest;
import com.zds.biz.vo.request.user.AppVersionFindRequest;
import com.zds.biz.vo.request.user.AppVersionRequest;
import com.zds.biz.vo.request.user.AppVersionStatusRequest;
import com.zds.user.dao.AppVersionDao;
import com.zds.user.po.AppVersion;
import com.zds.user.service.AppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppVersionDao appVersionDao;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Override
    public IPage<AppVersion> findList(AppVersionFindRequest request) {
        return appVersionDao.selectPage(new Page<>(request.getPageNum(), request.getPageSize()),
                AppVersion.getWrapper()
                        .eq(StringUtils.isNotEmpty(request.getAppType()), AppVersion::getAppType, request.getAppType())
                        .eq(request.getStatus() != null, AppVersion::getStatus, request.getStatus())
                        .eq(request.getSystemType() != null, AppVersion::getSystemType, request.getSystemType())
                        .orderByDesc(AppVersion::getCreateTime));
    }

    @Override
    public boolean addAppVersion(AppVersionAddRequest request) {
        //判断版本号是否已使用
        AppVersion version = appVersionDao.selectOne(AppVersion.getWrapper()
                .orderByDesc(AppVersion::getCreateTime)
                .eq(AppVersion::getAppType, request.getAppType())
                .eq(AppVersion::getSystemType, request.getSystemType())
                .last("limit 1"));
        if (version != null) {
            int code = compareVersion(request.getVersionCode(), version.getVersionCode());
            if (code == 0) {
                throw new BaseException("版本号已使用");
            } else if (code == -1) {
                throw new BaseException("新版本号必须大于" + version.getVersionCode());
            }
        }
        //新增
        return appVersionDao.insert(AppVersion.builder()
                .appType(request.getAppType())
                .versionCode(request.getVersionCode())
                .remarks(request.getRemarks())
                .status(request.getStatus())
                .downloadUrl(request.getDownloadUrl())
                .createTime(Calendar.getInstance().getTime())
                .createId(threadLocalUtil.getUserId())
                .systemType(request.getSystemType())
                .build()) > 0;
    }

    /**
     * 版本号比较
     * @return 0代表相等，1代表左边大，-1代表右边大
     */
    private int compareVersion(String v1, String v2) {
        if (v1.equals(v2)) {
            return 0;
        }
        String[] version1Array = v1.split("[._]");
        String[] version2Array = v2.split("[._]");
        int index = 0;
        int minLen = Math.min(version1Array.length, version2Array.length);
        long diff = 0;
        while (index < minLen && (diff = Long.parseLong(version1Array[index]) - Long.parseLong(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            for (int i = index; i < version1Array.length; i++) {
                if (Long.parseLong(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < version2Array.length; i++) {
                if (Long.parseLong(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    @Override
    public boolean updateAppVersionStatus(AppVersionStatusRequest request) {
        return appVersionDao.updateById(AppVersion.builder()
                .id(request.getId())
                .status(request.getStatus())
                .build()) > 0;
    }

    @Override
    public boolean deleteAppVersion(Long id) {
        return appVersionDao.deleteById(id) > 0;
    }

    @Override
    public AppVersion getNewAppInfo(AppVersionRequest request) {
        return appVersionDao.selectOne(AppVersion.getWrapper()
                .eq(AppVersion::getSystemType, request.getSystemType())
                .eq(AppVersion::getAppType, AppTypeEnum.SYSTEM_APP.getKey())
                .eq(AppVersion::getStatus, 1)//已发布
                .orderByDesc(AppVersion::getCreateTime)
                .last("limit 1"));
    }
}
