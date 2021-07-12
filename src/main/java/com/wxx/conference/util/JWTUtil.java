package com.wxx.conference.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.taobao.api.internal.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2020-9-16.
 */
public class JWTUtil {

    public static final String SECRET = "123456";
    /** token 过期时间: 5分钟 */
    public static final int calendarField = Calendar.MINUTE;
    public static final int calendarInterval = 30;

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);
    /**
     * JWT生成Token.<br/>
     * JWT构成: header, payload, signature
     * @param userId 登录成功后用户userId, 参数userId不可传空
     */
    public static String createToken(String userId){
        Date date = new Date();

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();
        String token = "";
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("alg","HS256");
            map.put("typ","JWT");

            token = JWT.create().withHeader(map) // header
                    .withClaim("iss", "Service") // payload
                    .withClaim("aud", "APP").withClaim("userId", null == userId ? null : userId.toString())
                    .withIssuedAt(date) // sign time
                    .withExpiresAt(expiresDate) // expire time
                    .sign(Algorithm.HMAC256(SECRET)); // signature
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }
    /**
     * JWT生成Token.<br/>
     * JWT构成: header, payload, signature
     * @param userId 登录成功后用户userId, 参数userId不可传空
     */
    public static String createTokenNew(String userId,String userName){
        Date date = new Date();

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();
        String token = "";
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("alg","HS256");
            map.put("typ","JWT");

            token = JWT.create().withHeader(map) // header
                    .withClaim("iss", "Service") // payload
                    .withClaim("aud", "APP")
                    .withClaim("userId", null == userId ? null : userId.toString())
                    .withClaim("userName",null == userName ? null : userName.toString())
                    .withIssuedAt(date) // sign time
                    .withExpiresAt(expiresDate) // expire time
                    .sign(Algorithm.HMAC256(SECRET)); // signature
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }
    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jwt.getClaims();
    }
    public static DecodedJWT verifyTokenNew(String token){
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            logger.error(e.toString());
//            e.printStackTrace();
        }
        return jwt;
    }
    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static String getAppUID(String token) {
//        Map<String, Claim> claims = verifyToken(token);
        DecodedJWT jwt = verifyTokenNew(token);
        if(jwt!=null){
            Map<String, Claim> claims = jwt.getClaims();
            Claim user_id_claim = claims.get("userId");
            return String.valueOf(user_id_claim.asString());
        }else{
            return null;
        }
//        Map<String, Claim> claims = verifyTokenNew(token);
//        if (null == user_id_claim || StringUtils.isEmpty(user_id_claim.asString())) {
//            // token 校验失败, 抛出Token验证非法异常
//        }
//        return Long.valueOf(user_id_claim.asString());

    }
}
