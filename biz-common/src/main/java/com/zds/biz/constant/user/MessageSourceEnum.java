package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 消息来源,字典group_id=MESSAGE_SOURCE
 */
public enum MessageSourceEnum implements BaseEnum<String> {

    //设备系统
    DEVICE_UPKEEP("DEVICE_UPKEEP", "设备保养发起"),
    DEVICE_UPKEEP_RESULT("DEVICE_UPKEEP_RESULT", "设备保养结果"),
    DEVICE_TRANSFER_APPLY("DEVICE_TRANSFER_APPLY", "设备调拨申请"),
    DEVICE_TRANSFER_EXAMINE("DEVICE_TRANSFER_EXAMINE", "设备调拨审批结果"),
    DEVICE_REPAIR("DEVICE_REPAIR", "设备报修发起"),
    DEVICE_REPAIR_RESULT("DEVICE_REPAIR_RESULT", "设备报修结果"),
    DEVICE_SCRAP_APPLY("DEVICE_SCRAP_APPLY", "设备报废申请"),
    DEVICE_SCRAP_EXAMINE("DEVICE_SCRAP_EXAMINE", "设备报废审批结果"),
    //从业系统
    EXAM_START("EXAM_START", "阅卷开始通知"),//给考试创建单位推送
    EXAM_END("EXAM_END", "阅卷完成通知"),//给考试发布人推送
    SCORE_RELEASE("SCORE_RELEASE", "成绩发布通知"),//给考试的已参考的人员推送
    //燃气系统
    PROJECT_APPROVAL("PROJECT_APPROVAL", "工程立项结果"),//给发起人推送
    PROJECT_INVEST("PROJECT_INVEST", "工程投资备案结果"),//给发起人推送
    PROJECT_LICENSE("PROJECT_LICENSE", "工程规划许可结果"),//给发起人推送
    PROJECT_EXCAVATE("PROJECT_EXCAVATE", "工程开挖许可结果"),//给发起人推送
    ACCIDENT_RESULT("ACCIDENT_RESULT", "事故处理完毕"),//给研判人推送
    //抄表系统
    REMOTE_METER_READING_ERROR("REMOTE_METER_READING_ERROR", "远程抄表异常"),//给抄表方案创建人推送
    ;

    private String key;

    private String title;

    MessageSourceEnum(String key, String title) {
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

    public static MessageSourceEnum query(String key){
        if (key != null) {
            MessageSourceEnum[] values = MessageSourceEnum.values();
            for (MessageSourceEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
