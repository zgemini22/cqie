package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zds.biz.util.JwtUtils;
import com.zds.biz.vo.AuthKeyRes;
import com.zds.biz.vo.TokenModel;
import com.zds.user.dao.DtGeoserverAuthDao;
import com.zds.user.po.DtGeoserverAuth;
import com.zds.user.service.DtGeoserverAuthService;
import com.zds.user.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class DtGeoserverAuthServiceImpl implements DtGeoserverAuthService {

    private final Map<String, AuthKeyRes> tokenAuthKeyMap = new HashMap<>();


    @Autowired
    private DtGeoserverAuthDao dtGeoserverAuthDao;

    @Autowired
    private TokenService tokenService;

    @Override
    public AuthKeyRes getAuthKey(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)){
            throw new RuntimeException("无token无法验证");
        }
        AuthKeyRes res = tokenAuthKeyMap.get(token);
        if(res == null || LocalDateTime.now().plusMinutes(5).isAfter(res.getExpireTime())){
            if(res != null && LocalDateTime.now().plusMinutes(5).isAfter(res.getExpireTime())) {
                // 注销旧的key
                cleanExpireMap();
            }
            res = new AuthKeyRes();
            DtGeoserverAuth oldAuthEntity = dtGeoserverAuthDao.selectOne(
                    new LambdaQueryWrapper<DtGeoserverAuth>()
                            .eq(DtGeoserverAuth::getToken, token)
                            .ge(DtGeoserverAuth::getExpiredTime, LocalDateTime.now().plusMinutes(5))
                            .eq(DtGeoserverAuth::getValidState, 1)
                            .last("limit 0, 1")
            );
            if( oldAuthEntity == null ){
                String authKey = IdWorker.get32UUID();
                LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(15);

                DtGeoserverAuth dtGeoserverAuthEntity = new DtGeoserverAuth();
                dtGeoserverAuthEntity.setAuthKey(authKey);
                dtGeoserverAuthEntity.setExpiredTime(expiredTime);
                dtGeoserverAuthEntity.setValidState(1L);
                dtGeoserverAuthEntity.setToken(token);
                dtGeoserverAuthDao.insert(dtGeoserverAuthEntity);
                res.setAuthKey(authKey);
                res.setExpireTime(expiredTime);
            }
            else {
                res.setAuthKey(oldAuthEntity.getAuthKey());
                res.setExpireTime(oldAuthEntity.getExpiredTime());
            }
            tokenAuthKeyMap.put(token, res);
        }
        return res;
    }
    private void cleanExpireMap() {
        List<String> delKeyList = new ArrayList<>();
        tokenAuthKeyMap.forEach((key, value) -> {
            if(LocalDateTime.now().plusMinutes(5).isAfter(value.getExpireTime())) {
                delKeyList.add(key);
            }
        });
        for(String delKey : delKeyList){
            tokenAuthKeyMap.remove(delKey);
        }
        // 清理数据库里面的已过期key
        List<String> willDelKeyList = new ArrayList<>();
        List<DtGeoserverAuth> willDelEntityList = dtGeoserverAuthDao.selectList(
                new LambdaQueryWrapper<DtGeoserverAuth>()
                        .lt(DtGeoserverAuth::getExpiredTime, LocalDateTime.now().plusMinutes(-15))
                        .eq(DtGeoserverAuth::getValidState, 1)
        );
        for(DtGeoserverAuth dtGeoserverAuthEntity: willDelEntityList){
            willDelKeyList.add(dtGeoserverAuthEntity.getAuthKey());
        }
        if(!willDelKeyList.isEmpty()){
            dtGeoserverAuthDao.delete(DtGeoserverAuth.getWrapper().in(DtGeoserverAuth::getAuthKey,willDelKeyList));
        }
    }

    @Override
    public boolean checkAuthKey(String authKey)throws Exception {
        if(StringUtils.isBlank(authKey)){
            log.info("authKey is null");
            return false;
        }
        if(authKey.length() != 32) {
            log.info("is not authKey!");
            return false;
        }
        DtGeoserverAuth dtGeoserverAuthEntity = dtGeoserverAuthDao.selectOne(DtGeoserverAuth.getWrapper().eq(DtGeoserverAuth::getAuthKey,authKey).last("limit 1"));
        if(dtGeoserverAuthEntity == null){
            log.info("authKey is not exist!");
            return false;
        }
        if(Objects.equals(dtGeoserverAuthEntity.getValidState(), 2)){
            log.info("authKey is not valid!");
            return false;
        }
        TokenModel tokenModel = JwtUtils.getTokenModelByJwtToken(dtGeoserverAuthEntity.getToken());
        boolean flag = tokenService.checkToken(tokenModel);
        if(!flag) {
            log.info("authKey is logout!");
            tokenAuthKeyMap.remove(authKey);
            dtGeoserverAuthDao.delete(DtGeoserverAuth.getWrapper().eq(DtGeoserverAuth::getAuthKey,authKey));
            return false;
        }
        return LocalDateTime.now().isBefore(dtGeoserverAuthEntity.getExpiredTime());

    }



}
