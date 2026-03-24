package com.zds.biz.targetcheck;

import com.zds.biz.constant.PowerEnum;
import com.zds.biz.constant.PowerLogical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在Controller的方法上使用此注解，该方法在映射时会检查用户是否登录
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {

    PowerEnum[] value() default PowerEnum.UNWANTED;

    PowerLogical logical() default PowerLogical.AND;
}
