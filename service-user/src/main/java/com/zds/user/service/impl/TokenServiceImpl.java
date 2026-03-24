package com.zds.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zds.biz.constant.PowerEnum;
import com.zds.biz.constant.user.OrganizationTypeEnum;
import com.zds.biz.constant.user.TokenTypeEnum;
import com.zds.user.dao.TblMenuPermissionDao;
import com.zds.user.dao.TblOrganizationDao;
import com.zds.user.dao.TblRoleMenuRelationDao;
import com.zds.user.po.TblMenuPermission;
import com.zds.user.po.TblOrganization;
import com.zds.user.po.TblRoleMenuRelation;
import com.zds.user.po.TblUser;
import com.zds.user.service.TokenService;
import com.zds.user.util.JwtUtils;
import com.zds.biz.vo.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${token.expiry-time}")
    private Integer TOKEN_EXPIRES_HOUR;

    @Value("${power.isopen}")
    private boolean isopen;

    @Autowired
    private TblRoleMenuRelationDao roleMenuRelationDao;

    @Autowired
    private TblMenuPermissionDao menuPermissionDao;

    @Autowired
    private TblOrganizationDao organizationDao;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //redis的key以TOKEN_拼接用户ID
    private final String TOKEN_KEY = "TOKEN_";

    @Override
    public TokenModel createToken(TblUser user, TokenTypeEnum tokenTypeEnum) {
        //JWT工具类生成token
        String token = JwtUtils.createToken(user);
        TblOrganization organization = organizationDao.selectById(user.getOrganizationId());
        //生成model
        TokenModel model = TokenModel.builder()
                .token(token)
                .userId(user.getId())
                .organizationId(user.getOrganizationId())
                .organizationType(organization != null ? organization.getOrganizationType() : OrganizationTypeEnum.SYSTEM_SAAS.getKey())
                .roleId(user.getRoleId())
                .accountLocked(user.getAccountLocked())
                .userStatus(user.getUserStatus())
                .powerList(getPowerList(user.getRoleId()))
                .userType(user.getUserType())
                .build();
        //把model转json写入redis
        String json = JSON.toJSONString(model);
        redisTemplate.opsForValue().set(TOKEN_KEY + user.getId(), json, TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        return model;
    }

    private List<String> getPowerList(Long roleId) {
        List<String> powerList;
        if (isopen) {
            //查询角色对应的菜单集合
            QueryWrapper<TblRoleMenuRelation> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("role_id", roleId);
            List<Long> menuIds = roleMenuRelationDao.selectList(wrapper1).stream().map(TblRoleMenuRelation::getMenuId).collect(Collectors.toList());
            //查询菜单对应的权限集合
            QueryWrapper<TblMenuPermission> wrapper2 = new QueryWrapper<>();
            wrapper2.in("menu_id", menuIds);
            powerList = menuPermissionDao.selectList(wrapper2).stream().map(TblMenuPermission::getPermissionEnum).collect(Collectors.toList());
        } else {
            powerList = Arrays.stream(PowerEnum.class.getFields()).map(Field::getName).collect(Collectors.toList());
        }
        return powerList;
    }

    @Override
    public boolean refreshToken(TokenModel model) {
        boolean flag = false;
        if (model != null) {
            flag = redisTemplate.expire(TOKEN_KEY + model.getUserId(), TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        }
        return flag;
    }

    @Override
    public boolean checkToken(TokenModel model) {
        boolean flag;
        if (model == null) {
            return false;
        }
        flag = redisTemplate.hasKey(TOKEN_KEY + model.getUserId());
        if (flag) {
            //如果验证成功，说明此用户进行了一次有效操作，延长token的过期时间
            refreshToken(model);
        }
        return flag;
    }

    @Override
    public TokenModel getToken(String authentication) {
        try {
            if (authentication == null || authentication.length() == 0) {
                return null;
            }
            TokenModel tokenModel = JwtUtils.getTokenModelByJwtToken(authentication);
            //存在redis就返回
            Boolean flag = redisTemplate.hasKey(TOKEN_KEY + tokenModel.getUserId());
            if (flag) {
                TokenModel tokenModelRedis = getTokenByUserId(tokenModel.getUserId());
                if (!tokenModelRedis.getToken().equals(authentication)) {
                    return null;
                }
                return tokenModelRedis;
            } else {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }

    }

    @Override
    public boolean deleteToken(Long userId) {
        return redisTemplate.delete(TOKEN_KEY + userId);
    }

    @Override
    public boolean deleteToken(List<Long> userIds) {
        Set<String> keys = new HashSet<>();
        for (Long userId : userIds) {
            keys.add(TOKEN_KEY + userId);
        }
        redisTemplate.delete(keys);
        return true;
    }

    @Override
    public TokenModel getTokenByUserId(TblUser user) {
        return getTokenByUserId(user.getId());
    }

    public TokenModel getTokenByUserId(Long userId) {
        TokenModel model = null;
        //判断redis存在
        Boolean flag = redisTemplate.hasKey(TOKEN_KEY + userId);
        if (flag) {
            String json = redisTemplate.opsForValue().get(TOKEN_KEY + userId);
            model = JSON.parseObject(json, new TypeReference<TokenModel>(){});
        }
        return model;
    }

    @Override
    public Date getTokenExpirationTime(String token) {
        TokenModel tokenModel = JwtUtils.getTokenModelByJwtToken(token);
        if (tokenModel == null) {
            return null;
        }
        // 获取剩余生存时间（秒）
        Long ttl = redisTemplate.getExpire(TOKEN_KEY + tokenModel.getUserId(), TimeUnit.SECONDS);
        // 处理无效情况（key不存在/无过期时间/已过期）
        if (ttl == null || ttl < 0) {
            return null;
        }
        // 计算过期时间 = 当前时间 + 剩余生存时间
        return new Date(System.currentTimeMillis() + ttl * 1000);
    }
}
