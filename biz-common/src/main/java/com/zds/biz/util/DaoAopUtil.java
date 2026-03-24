package com.zds.biz.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * dao层AOP通用清洗工具
 */
public class DaoAopUtil {

    private static final String createId = "createId";

    private static final String createTime = "createTime";

    private static final String updateId = "updateId";

    private static final String updateTime = "updateTime";

    private static final String deleted = "deleted";

    /**
     * 添加方法自动补全
     * 创建人修改人创建时间修改时间
     */
    public static Object daoAopByCreate(ProceedingJoinPoint pjp, Long UserID) throws Throwable {
        String[] fieldNames = {"createId","createTime","updateId","updateTime","deleted"};
        Object[] objects = pjp.getArgs();
        if (objects != null && objects.length > 0) {
            for (Object arg : objects) {
                if (arg instanceof Collection) {
                    Collection obs = (Collection) arg;
                    for (Object ob : obs) {
                        setData(fieldNames, ob, UserID);
                    }
                } else {
                    setData(fieldNames, arg, UserID);
                }
            }
        }
        Object object = pjp.proceed();
        return object;
    }

    /**
     * 修改方法自动补全
     * 创建人修改人创建时间修改时间
     */
    public static Object daoAopByUpdate(ProceedingJoinPoint pjp, Long UserID) throws Throwable {
        String[] fieldNames= {"updateId","updateTime"};
        if (UserID != null) {
            Object[] objects = pjp.getArgs();
            if (objects != null && objects.length > 0) {
                for (Object arg : objects) {
                    Map<String, Boolean> map= isExistFieldName(fieldNames,arg);
                    if(map.containsKey("updateId")){
                        if (StringUtils.isBlank(BeanUtils.getProperty(arg, updateId))) {
                            BeanUtils.setProperty(arg, updateId, UserID);
                        }
                    }
                    if(map.containsKey("updateTime")) {
                        if (StringUtils.isBlank(BeanUtils.getProperty(arg, updateTime))) {
                            BeanUtils.setProperty(arg, updateTime, new Date());
                        }
                    }
                }
            }
        }
        Object object = pjp.proceed();
        return object;
    }

    public static void setData(String[] fieldNames, Object arg, Long UserID) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, Boolean> map= isExistFieldName(fieldNames,arg);
        if(map.containsKey("createId")) {
            if (StringUtils.isBlank(BeanUtils.getProperty(arg, createId))) {
                BeanUtils.setProperty(arg, createId, UserID);
            }
        }
        if(map.containsKey("createTime")) {
            if (StringUtils.isBlank(BeanUtils.getProperty(arg, createTime))) {
                BeanUtils.setProperty(arg, createTime, new Date());
            }
        }
        if(map.containsKey("updateId")) {
            if (StringUtils.isBlank(BeanUtils.getProperty(arg, updateId))) {
                BeanUtils.setProperty(arg, updateId, UserID);
            }
        }
        if(map.containsKey("updateTime")) {
            if (StringUtils.isBlank(BeanUtils.getProperty(arg, updateTime))) {
                BeanUtils.setProperty(arg, updateTime, new Date());
            }
        }
        if(map.containsKey("deleted")) {
            if (StringUtils.isBlank(BeanUtils.getProperty(arg, deleted))) {
                BeanUtils.setProperty(arg, deleted, 0);
            }
        }
    }

    /**
     * 判断你一个类是否存在某个属性（字段）
     */
    public static Map<String, Boolean> isExistFieldName(String[] fieldNames, Object obj) {
        Map<String, Boolean> map=new HashMap<>();
        if (obj == null || fieldNames.length<=0) {
            return map;
        }
        //获取这个类的所有属性
        Field[] fields = obj.getClass().getDeclaredFields();
        //循环遍历所有的fields
        for (int i = 0; i < fields.length; i++) {
            for (String fieldname:fieldNames){
                if (fields[i].getName().equals(fieldname)) {
                    map.put(fieldname,true);
                    break;
                }
            }

        }
        return map;
    }
}
