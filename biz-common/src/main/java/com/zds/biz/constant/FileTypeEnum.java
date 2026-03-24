package com.zds.biz.constant;

/**
 * 文件类型枚举
 */
public enum FileTypeEnum implements BaseEnum<Integer> {

    FILE(1, "附件"),
    PICTURE(2, "图片"),
    VIDEO(3, "视频"),
    PROTOCOL(4, "协议"),;

    private int key;

    private String msg;

    FileTypeEnum(int key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static FileTypeEnum query(Integer key) {
        if (key != null) {
            FileTypeEnum[] values = FileTypeEnum.values();
            for (FileTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
