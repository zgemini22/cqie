package com.zds.biz.util;

import com.zds.biz.targetcheck.ApiModelRequestCheck;
import com.zds.biz.vo.DataComparisonResponse;
import com.zds.biz.vo.DataComparisonResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

@Component
public class DataComparisonUtil<T> {


    public DataComparisonResponse<T> dataComparison(List<T> myList, List<T> originalList) throws Exception {
        //删除集合
        List<T> deleteList = new ArrayList<>();
        //修改集合
        List<T> updateList = new ArrayList<>();
        //新增集合
        List<T> insertList = new ArrayList<>();
        //集合转map
        Map<String, T> orginalMap = new HashMap<>();
        Map<String, T> myMap = new HashMap<>();
        Set<String> key = new HashSet<>();
        for (T myt : myList) {
            Class<? extends Object> tClass = myt.getClass();
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName() == "deviceNo") {
                    field.setAccessible(true);
                    String keys = "" + field.get(myt);
                    myMap.put(keys, myt);
                    key.add(keys);
                    break;
                }
            }
        }
        for (T originalt : originalList) {
            Class<? extends Object> tClass = originalt.getClass();
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName() == "deviceNo") {
                    field.setAccessible(true);
                    String keys = "" + field.get(originalt);
                    orginalMap.put(keys, originalt);
                    key.add(keys);
                    break;
                }
            }
        }

        for (String str : key) {

            //在正式数据库数据判断是否为NULL是为添加数据
            if (!myMap.containsKey(str)) {
                insertList.add(orginalMap.get(str));
                continue;
            }
            //在来源数据库数据判断是否为NULL是为删除数据
            if (!orginalMap.containsKey(str)) {
                deleteList.add(myMap.get(str));
                continue;
            }
            //在正式数据库数据与来源数据库数据判断不等于NULL，进行对比
            if (orginalMap.containsKey(str) && myMap.containsKey(str)) {
                T orginal = orginalMap.get(str);
                T my = myMap.get(str);
                Class<? extends Object> tClass = orginal.getClass();
                Field[] fields=tClass.getDeclaredFields();
                for (Field field:fields){
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(ApiModelRequestCheck.class)) {
                            String orginalStr=""+field.get(orginal);
                            String myStr=""+field.get(my);
                            if (!myStr.equals(orginalStr)) {
                                updateList.add(orginalMap.get(str));
                                break;
                            }
                    }
                }
            }
        }
        DataComparisonResponse<T> dataComparisonResponse = new DataComparisonResponse();
        dataComparisonResponse.setDelete(deleteList);
        dataComparisonResponse.setInsert(insertList);
        dataComparisonResponse.setUpdate(updateList);
        return dataComparisonResponse;
    }


    public boolean dataComparison(T my,T orginal){
        try {
            Class<? extends Object> tClass = orginal.getClass();
            Field[] fields=tClass.getDeclaredFields();
            for (Field field:fields){
                field.setAccessible(true);
                if (field.isAnnotationPresent(ApiModelRequestCheck.class)) {
                    String orginalStr=""+field.get(orginal);
                    String myStr=""+field.get(my);
                    if (!myStr.equals(orginalStr)) {
                        return true;
                    }
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public DataComparisonResponse<T> rqaqComparison(List<T> myList, List<T> originalList , String keyName) throws Exception {
        //删除集合
        List<T> deleteList = new ArrayList<>();
        //修改集合
        List<T> updateList = new ArrayList<>();
        //新增集合
        List<T> insertList = new ArrayList<>();
        //集合转map
        Map<String, T> orginalMap = new HashMap<>();
        Map<String, T> myMap = new HashMap<>();
        Set<String> key = new HashSet<>();
        for (T myt : myList) {
            Class<? extends Object> tClass = myt.getClass();
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName() == keyName) {
                    field.setAccessible(true);
                    String keys = "" + field.get(myt);
                    myMap.put(keys, myt);
                    key.add(keys);
                    break;
                }
            }
        }
        for (T originalt : originalList) {
            Class<? extends Object> tClass = originalt.getClass();
            Field[] fields = tClass.getDeclaredFields();
            for (Field field : fields) {
                if (field.getName() == keyName) {
                    field.setAccessible(true);
                    String keys = "" + field.get(originalt);
                    orginalMap.put(keys, originalt);
                    key.add(keys);
                    break;
                }
            }
        }

        for (String str : key) {

            //在正式数据库数据判断是否为NULL是为添加数据
            if (!myMap.containsKey(str)) {
                insertList.add(orginalMap.get(str));
                continue;
            }
            //在来源数据库数据判断是否为NULL是为删除数据
            if (!orginalMap.containsKey(str)) {
                deleteList.add(myMap.get(str));
                continue;
            }
            //在正式数据库数据与来源数据库数据判断不等于NULL，进行对比
            if (orginalMap.containsKey(str) && myMap.containsKey(str)) {
                T orginal = orginalMap.get(str);
                T my = myMap.get(str);
                Class<? extends Object> tClass = orginal.getClass();
                Field[] fields=tClass.getDeclaredFields();
                for (Field field:fields){
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(ApiModelRequestCheck.class)) {
                        String orginalStr=""+field.get(orginal);
                        String myStr=""+field.get(my);
                        if (!myStr.equals(orginalStr)) {
                            updateList.add(orginalMap.get(str));
                            break;
                        }
                    }
                }
            }
        }
        DataComparisonResponse<T> dataComparisonResponse = new DataComparisonResponse();
        dataComparisonResponse.setDelete(deleteList);
        dataComparisonResponse.setInsert(insertList);
        dataComparisonResponse.setUpdate(updateList);
        return dataComparisonResponse;
    }
}
