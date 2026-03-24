package com.zds.biz.targetcheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelPropertyCheck {
    /**
     * 最小位数
     */
    int min() default 0;
    /**
     * 最大位数
     */
    int max() default 0;
    /**
     * 固定位数
     */
    int length() default 0;
    /**
     * 参数名称
     */
    String value() default "";
    /**
     * 是否必填
     */
    boolean required() default false;
    /**
     * 是否隐藏
     */
    boolean hidden() default false;
    /**
     * 示例值
     */
    String example() default "";

    /**
     * 注释
     */
    String notes() default "";

    /**
     * 排序
     */
    int position() default 0;
}
