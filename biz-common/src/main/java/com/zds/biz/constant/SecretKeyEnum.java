package com.zds.biz.constant;

/**
 * 服务间通信用密钥
 */
public enum SecretKeyEnum implements BaseEnum<String>  {
    SECRET_KEY("GAS_SERVICE_API_SECRET_KEY", "SECRET_KEY"),
    ;

    private String key;

    private String title;

    SecretKeyEnum(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
