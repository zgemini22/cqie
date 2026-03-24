package com.zds.biz.util;

import java.util.UUID;

/**
 * UUID生产工具类
 */
public final class IDGenerator {
    /**
     * 生成UUID
     */
    public final static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
