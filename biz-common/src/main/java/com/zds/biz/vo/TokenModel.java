package com.zds.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Token的Model类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * JWT工具类生成的token
     */
    private String token;

    /**
     * 单位ID
     */
    private Long organizationId;

    /**
     * 单位类别
     */
    private String organizationType;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色类型,字典group_id=ROLE_TYPE
     */
    private String roleType;

    /**
     * 是否锁定账户
     */
    private Boolean accountLocked;

    /**
     * 用户状态,字典group_id=USER_STATUS
     */
    private String userStatus;

    /**
     * 权限集合
     */
    private List<String> powerList;

    /**
     * 用户类型,字典group_id=USER_TYPE
     */
    private String userType;

    @Override
    public String toString() {
        return new StringBuilder().append(this.userId).append('_').append(this.token).toString();
    }
}
