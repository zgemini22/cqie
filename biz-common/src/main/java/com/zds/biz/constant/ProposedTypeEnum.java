package com.zds.biz.constant;

public enum ProposedTypeEnum implements BaseEnum<String> {
    PIPING("PIPING", "管道"),
    REFUELING_STATION("REFUELING_STATION", "加气站"),
    TRANSMISSION("TRANSMISSION", "输配站"),
    FACILITIES("FACILITIES", "设施设备"),
    ;

    private String key;

    private String title;

    ProposedTypeEnum(String key, String title) {
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

    public static ProposedTypeEnum query(String key){
        if (key != null) {
            ProposedTypeEnum[] values = ProposedTypeEnum.values();
            for (ProposedTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
