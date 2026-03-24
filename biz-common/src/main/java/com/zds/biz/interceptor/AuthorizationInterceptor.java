package com.zds.biz.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.PowerEnum;
import com.zds.biz.constant.PowerLogical;
import com.zds.biz.constant.ResultValueEnum;
import com.zds.biz.constant.user.*;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.util.HttpUtil;
import com.zds.biz.util.JwtUtils;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.TokenModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 自定义拦截器，判断用户是否登录
 */
@Slf4j
@Order(1)
public class AuthorizationInterceptor implements HandlerInterceptor {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Integer TOKEN_EXPIRES_HOUR = 1;

    private boolean isopen = true;

    //redis的key以TOKEN_拼接用户ID
    private final String TOKEN_KEY = "TOKEN_";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setIp(request);
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if (!checkIsHaveToken(handler)) {
            return true;
        }
        String url = request.getRequestURL().toString();
        if (url.contains("swagger-resources") || url.contains("/v2/api-docs")) {
            return true;
        }
        System.out.println(request.getRequestURL());
        //header中superkey验证通过,则默认用户为管理员
        String superkey = request.getHeader("superkey");
        if (StringUtils.isNotEmpty(superkey) && "8ue4YWKdKkRnvhh5axvF".equals(superkey)) {
            //如果token验证成功，将token对应的用户id存在threadLocal中
            threadLocalUtil.clear();
            threadLocalUtil.setUserId(1L);
            threadLocalUtil.setOrganizationId(1L);
            threadLocalUtil.setOrganizationType(OrganizationTypeEnum.SYSTEM_SAAS.getKey());
            threadLocalUtil.setRoleId(1L);
            threadLocalUtil.setUserType(UserTypeEnum.SYSTEM_ADMIN.getKey());
            threadLocalUtil.setRoleType(RoleTypeEnum.SYSTEM_ADMIN.getKey());
            return true;
        }
        //验证token
        TokenModel model = getToken(request.getHeader("Authorization"));
        if (model != null && checkToken(model)) {
            //检查用户状态
//            if(model.getUserStatus().equals(UserStatusEnum.DISABLE.getKey())){
//                throw new BaseException("账号已被禁用");
//            }
//            //检查是否锁定账户
//            if(model.getAccountLocked()){
//                throw new BaseException("账号已被锁定");
//            }
//            //检查用户是否拥有指定API权限
//            checkApiPower(handler, model.getPowerList());
            //如果token验证成功，将token对应的用户id存在threadLocal中
            threadLocalUtil.clear();
            threadLocalUtil.setToken(model.getToken());
            threadLocalUtil.setUserId(model.getUserId());
            threadLocalUtil.setOrganizationId(model.getOrganizationId());
//            threadLocalUtil.setOrganizationType(model.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode()) ? OrganizationTypeEnum.SYSTEM_SAAS.getKey() : model.getOrganizationType());
            threadLocalUtil.setRoleId(model.getRoleId());
            threadLocalUtil.setUserType(model.getUserType());
            threadLocalUtil.setRoleType(model.getRoleType());
            return true;
        } else {
            //如果验证token失败，并且方法注明了Authorization，返回鉴权失败提示
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (method.getAnnotation(Authorization.class) != null) {
                throw new BaseException(ResultValueEnum.NO_LOGIN, ResultValueEnum.NO_LOGIN.getTitle());
            }
        }
        return true;
    }

    public boolean checkIsHaveToken(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        return method.getAnnotation(Authorization.class) != null;
    }

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

    public boolean refreshToken(TokenModel model) {
        boolean flag = false;
        if (model != null) {
            flag = redisTemplate.expire(TOKEN_KEY + model.getUserId(), TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
        }
        return flag;
    }

    public TokenModel getTokenByUserId(Long userId) {
        TokenModel model = null;
        //判断redis存在
        Boolean flag = redisTemplate.hasKey(TOKEN_KEY + userId);
        if (flag) {
            String json = redisTemplate.opsForValue().get(TOKEN_KEY + userId);
            model = JSON.parseObject(json, new TypeReference<TokenModel>() {
            });
        }
        return model;
    }

    /**
     * 检查用户是否拥有指定API权限
     */
    private void checkApiPower(Object handler, List<String> powerList) {
        if (!isopen) {
            return;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Authorization authorization = method.getAnnotation(Authorization.class);
        if (authorization != null) {
            List<PowerEnum> list = Arrays.asList(authorization.value());
            //接口只要求一个权限,并且为UNWANTED时
            if (list.size() == 1 && list.get(0) == PowerEnum.UNWANTED) {
                return;
            }
            int count = 0;
            boolean flag = false;
            for (PowerEnum apiKey : list) {
                for (String power : powerList) {
                    if (apiKey.name().equals(power)) {
                        count += 1;
                        break;
                    }
                }
                if (authorization.logical().equals(PowerLogical.AND) && count == 0) {
                    flag = true;
                    break;
                }
            }
            if (authorization.logical().equals(PowerLogical.OR) && count == 0) {
                flag = true;
            }
            if (authorization.logical().equals(PowerLogical.AND) && count != list.size()) {
                flag = true;
            }
            if (flag) {
                throw new BaseException(ResultValueEnum.NO_POWER, ResultValueEnum.NO_POWER.getTitle());
            }
        }
    }

    private void setIp(HttpServletRequest request) {
        String ip = HttpUtil.getIpAddr(request);
        threadLocalUtil.setIpAddr(ip);
        threadLocalUtil.setIpPort(String.valueOf(request.getRemotePort()));

        String host = request.getHeader("Host");
        String scheme = request.getScheme();
        log.info("ip= " + ip);
        log.info("Port= " + request.getRemotePort());
        log.info("host= " + host);
        log.info("scheme= " + scheme);
        threadLocalUtil.setHost(scheme + "://" + host);
    }
}
