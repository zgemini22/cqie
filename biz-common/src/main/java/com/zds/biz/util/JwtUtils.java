package com.zds.biz.util;

import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.ResultValueEnum;
import com.zds.biz.vo.TokenModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * token-jwt加密解密工具类
 */
public class JwtUtils {

    // token时效：24小时
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    // 签名哈希的密钥
    public static final String APP_SECRET = "1qaz!QAZ";

    /**
     * 根据用户userId生成token
     * @param userId 用户信息
     * @return JWT规则生成的token
     */
    public static String createToken(Long userId) {
        return Jwts.builder()
                //发行日期
                .setIssuedAt(new Date())
                //过期时间
                .setExpiration(null)
                //参数
                .claim("userId", userId)
                // HS256算法实际上就是MD5加盐值，此时APP_SECRET就代表盐值
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();
    }

    /**
     * 判断token是否存在与有效
     * @param token token字符串
     * @return 如果token有效返回true，否则返回false
     */
    public static boolean checkToken(String token) {
        if (StringUtils.isEmpty(token)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断token是否存在与有效
     * @param request Http请求对象
     * @return 如果token有效返回true，否则返回false
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            // 从http请求头中获取token字符串
            String jwtToken = request.getHeader("Authorization");

            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据token获取TokenModel
     * @param token
     * @return 解析token后获得的用户id
     */
    public static TokenModel getTokenModelByJwtToken(String token) {
        TokenModel tokenModel = new TokenModel();
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            Object object1 = claims.get("userId");
            Long userId = Long.parseLong(String.valueOf(object1));
            tokenModel.setUserId(userId);
        } catch (Exception e) {
            throw new BaseException(ResultValueEnum.NO_LOGIN);
        }
        return tokenModel;
    }
}
