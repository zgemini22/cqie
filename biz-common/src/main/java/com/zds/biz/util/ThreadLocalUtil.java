package com.zds.biz.util;

import com.zds.biz.constant.ThreadLocalKeyEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程内部数据工具类
 */
@Component
public class ThreadLocalUtil {

    private final ThreadLocal<Map<String, Object>> store = ThreadLocal.withInitial(HashMap::new);

    public Object get(String key) {
        Map<String, Object> map = store.get();
        return map.getOrDefault(key, null);
    }

    public void put(String key, Object obj) {
        store.get().put(key, obj);
    }

    public void clear() {
        Map<String, Object> map = store.get();
        Object ipaddr = map.get(ThreadLocalKeyEnum.IPADDR.getKey());
        Object ipport = map.get(ThreadLocalKeyEnum.IPPORT.getKey());
        Object host = map.get(ThreadLocalKeyEnum.HOST.getKey());
        map.clear();
        map.put(ThreadLocalKeyEnum.IPADDR.getKey(), ipaddr);
        map.put(ThreadLocalKeyEnum.IPPORT.getKey(), ipport);
        map.put(ThreadLocalKeyEnum.HOST.getKey(), host);
    }

    public void setUserId(Long userId) {
        put(ThreadLocalKeyEnum.USERID.getKey(), userId);
    }

    /**
     * 获取当前线程用户的userId
     */
    public Long getUserId() {
        Object obj = get(ThreadLocalKeyEnum.USERID.getKey());
        return obj != null ? Long.parseLong(obj.toString()) : null;
    }

    public void setOrganizationId(Long organizationId) {
        put(ThreadLocalKeyEnum.ORGANIZATIONID.getKey(), organizationId);
    }

    /**
     * 获取当前线程用户的organizationId
     */
    public Long getOrganizationId() {
        Object obj = get(ThreadLocalKeyEnum.ORGANIZATIONID.getKey());
        return obj != null ? Long.parseLong(obj.toString()) : null;
    }

    public void setOrganizationType(String organizationType) {
        put(ThreadLocalKeyEnum.ORGANIZATIONTYPE.getKey(), organizationType);
    }

    /**
     * 获取当前线程用户的organizationType
     */
    public String getOrganizationType() {
        Object obj = get(ThreadLocalKeyEnum.ORGANIZATIONTYPE.getKey());
        return obj != null ? obj.toString() : null;
    }

    public void setRoleId(Long roleId) {
        put(ThreadLocalKeyEnum.ROLEID.getKey(), roleId);
    }

    /**
     * 获取当前线程用户的roleId
     */
    public Long getRoleId() {
        Object obj = get(ThreadLocalKeyEnum.ROLEID.getKey());
        return obj != null ? Long.parseLong(obj.toString()) : null;
    }

    public void setIpAddr(String ipAddr) {
        put(ThreadLocalKeyEnum.IPADDR.getKey(), ipAddr);
    }

    /**
     * 获取当前线程用户的IP
     */
    public String getIpAddr() {
        Object obj = get(ThreadLocalKeyEnum.IPADDR.getKey());
        return obj != null ? obj.toString() : "";
    }

    public void setIpPort(String ipPort) {
        put(ThreadLocalKeyEnum.IPPORT.getKey(), ipPort);
    }

    /**
     * 获取当前线程用户的IP出网端口
     */
    public String getIpPortr() {
        Object obj = get(ThreadLocalKeyEnum.IPPORT.getKey());
        return obj != null ? obj.toString() : "";
    }

    public void setToken(String token) {
        put(ThreadLocalKeyEnum.TOKEN.getKey(), token);
    }

    /**
     * 获取当前线程用户的token
     */
    public String getToken() {
        Object obj = get(ThreadLocalKeyEnum.TOKEN.getKey());
        return obj != null ? obj.toString() : "";
    }

    public void setUserType(String userType) {
        put(ThreadLocalKeyEnum.USERTYPE.getKey(), userType);
    }

    /**
     * 获取当前线程用户的用户类型,字典group_id=USER_TYPE
     */
    public String getUserType() {
        Object obj = get(ThreadLocalKeyEnum.USERTYPE.getKey());
        return obj != null ? obj.toString() : "";
    }

    public void setRoleType(String roleType) {
        put(ThreadLocalKeyEnum.ROLETYPE.getKey(), roleType);
    }

    /**
     * 获取当前线程用户的角色类型,字典group_id=ROLE_TYPE
     */
    public String getRoleType() {
        Object obj = get(ThreadLocalKeyEnum.ROLETYPE.getKey());
        return obj != null ? obj.toString() : "";
    }

    public void setHost(String host) {
        put(ThreadLocalKeyEnum.HOST.getKey(), host);
    }

    /**
     * 获取当前请求host
     */
    public String getHost() {
        Object obj = get(ThreadLocalKeyEnum.HOST.getKey());
        return obj != null ? obj.toString() : "";
    }

    public void setCurrentFeignClient(String currentFeignClient) {
        put(ThreadLocalKeyEnum.FeignClient.getKey(), currentFeignClient);
    }

    /**
     * 获取当前远程调用名称
     */
    public String getCurrentFeignClient() {
        Object obj = get(ThreadLocalKeyEnum.FeignClient.getKey());
        return obj != null ? obj.toString() : null;
    }

    public void clearCurrentFeignClient() {
        Map<String, Object> map = store.get();
        map.remove(ThreadLocalKeyEnum.FeignClient.getKey());
    }
}
