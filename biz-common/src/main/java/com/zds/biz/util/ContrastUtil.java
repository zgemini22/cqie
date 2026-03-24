package com.zds.biz.util;

import com.zds.biz.config.SerializableFunction;
import com.zds.biz.targetcheck.ModelCheck;
import com.zds.biz.vo.ContrastUpVo;
import com.zds.biz.vo.ContrastVo;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 集合对比工具类
 */
public class ContrastUtil<T,R> {

    /**
     * 对比两个集合,根据指定属性,计算出新增、删除的
     */
    public static <T,R> ContrastVo<T> contrastList(List<T> oldList, List<T> newList, SerializableFunction<T,R> function) {
        //删除集合
        List<T> deleteList = new ArrayList<>();
        //新增集合
        List<T> insertList = new ArrayList<>();
        Field field_id = ReflectionUtil.getField(function);
        String originalId = field_id.getName();
        try {
            //集合转map
            Map<String, T> newMap = new HashMap<>();
            Map<String, T> oldMap = new HashMap<>();
            Set<String> key = new HashSet<>();
            for (T myt : oldList) {
                Class<? extends Object> tClass = myt.getClass();
                Field[] fields = tClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equals(originalId)) {
                        field.setAccessible(true);
                        String keys = "" + field.get(myt);
                        oldMap.put(keys, myt);
                        key.add(keys);
                        break;
                    }
                }
            }
            for (T originalt : newList) {
                Class<? extends Object> tClass = originalt.getClass();
                Field[] fields = tClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equals(originalId)) {
                        field.setAccessible(true);
                        String keys = "" + field.get(originalt);
                        newMap.put(keys, originalt);
                        key.add(keys);
                        break;
                    }
                }
            }
            for (String str : key) {
                if (!oldMap.containsKey(str)) {
                    insertList.add(newMap.get(str));
                } else if (!newMap.containsKey(str)) {
                    deleteList.add(oldMap.get(str));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ContrastVo<T> vo = new ContrastVo<>();
        vo.setAddList(insertList);
        vo.setDelList(deleteList);
        return vo;
    }

    /**
     * 对比两个集合,根据指定属性,计算出新增、修改、删除的
     * function为指定主键字段
     * 需要对比的字段加注解@ModelCheck
     */
    public static <T,R> ContrastUpVo<T> dataComparison(List<T> oldList, List<T> newList, SerializableFunction<T,R> function) {
        //删除集合
        List<T> deleteList = new ArrayList<>();
        //新增集合
        List<T> insertList = new ArrayList<>();
        //修改集合
        List<T> updateList = new ArrayList<>();
        Field field_id = ReflectionUtil.getField(function);
        String originalId = field_id.getName();
        try {
            //集合转map
            Map<String, T> newMap = new HashMap<>();
            Map<String, T> oldMap = new HashMap<>();
            Set<String> key = new HashSet<>();
            for (T myt : oldList) {
                Class<? extends Object> tClass = myt.getClass();
                Field[] fields = tClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equals(originalId)) {
                        field.setAccessible(true);
                        String keys = "" + field.get(myt);
                        oldMap.put(keys, myt);
                        key.add(keys);
                        break;
                    }
                }
            }
            for (T originalt : newList) {
                Class<? extends Object> tClass = originalt.getClass();
                Field[] fields = tClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equals(originalId)) {
                        field.setAccessible(true);
                        String keys = "" + field.get(originalt);
                        newMap.put(keys, originalt);
                        key.add(keys);
                        break;
                    }
                }
            }
            for (String str : key) {
                if (!oldMap.containsKey(str)) {
                    insertList.add(newMap.get(str));
                } else if (!newMap.containsKey(str)) {
                    deleteList.add(oldMap.get(str));
                } else {//对比是否修改
                    T newObj = newMap.get(str);
                    T oldObj = oldMap.get(str);
                    try {
                        Class<? extends Object> tClass = newObj.getClass();
                        Field[] fields = tClass.getDeclaredFields();
                        for (Field field : fields) {
                            field.setAccessible(true);
                            if (field.isAnnotationPresent(ModelCheck.class)) {
                                String newStr = "" + field.get(newObj);
                                String oldStr = "" + field.get(oldObj);
                                if (!oldStr.equals(newStr)) {
                                    updateList.add(newObj);
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ContrastUpVo<T> vo = new ContrastUpVo<>();
        vo.setAddList(insertList);
        vo.setDelList(deleteList);
        vo.setUpList(updateList);
        return vo;
    }
}
