package com.zds.file.interceptor;

import com.zds.biz.util.DaoAopUtil;
import com.zds.biz.util.ThreadLocalUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 插件生成的dao
 * 添加方法修改方法
 * 自动添加创建人修改人创建时间修改
 * 时间拦截处理类
 */
@Aspect
@Component
@Configuration
public class TblDaoAspect {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Pointcut("execution(* com.zds.file.dao.*.update*(..))")
    public void daoUpdate() {
    }

    @Pointcut("execution(* com.zds.file.dao.*.insert*(..))")
    public void daoCreate() {
    }

    /**
     * 修改方法自动补全
     * 创建人修改人创建时间修改时间
     */
    @Around("daoUpdate()")
    public Object doDaoUpdate(ProceedingJoinPoint pjp) throws Throwable {
        return DaoAopUtil.daoAopByUpdate(pjp, threadLocalUtil.getUserId());
    }

    /**
     * 添加方法自动补全
     * 创建人修改人创建时间修改时间
     */
    @Around("daoCreate()")
    public Object doDaoCreate(ProceedingJoinPoint pjp) throws Throwable {
        return DaoAopUtil.daoAopByCreate(pjp, threadLocalUtil.getUserId());
    }
}
