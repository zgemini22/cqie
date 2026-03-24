package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 动态基础数据key枚举
 */
public enum BasicDataKeyEnum implements BaseEnum<String> {

    fileMbMaxSize("fileMbMaxSize", "1200", "上传文件最大限制(MB)"),
    phoneCodeEffectiveTime("phoneCodeEffectiveTime","5", "手机验证码有效期(分钟)"),
    isSynchroTemplate("isSynchroTemplate","1", "是否开启角色模板同步"),

    accident_mock("accident_mock","0","是否查询事故模拟处置流程"),
    ;

    private String key;

    private String value;

    private String name;

    BasicDataKeyEnum(String key, String value, String name) {
        this.key = key;
        this.value = value;
        this.name = name;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static BasicDataKeyEnum query(String key){
        if (key != null) {
            BasicDataKeyEnum[] values = BasicDataKeyEnum.values();
            for (BasicDataKeyEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
