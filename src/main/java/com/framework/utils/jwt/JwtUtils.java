package com.framework.utils.jwt;



import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.framework.constant.StatusEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import org.bouncycastle.util.encoders.Base64;



/**
 * jwt加密和解密的工具类
 */
public class JwtUtils {

    public static final String JWT_SECERT = "8677df7fc3a34e26a61c034d5ec8245d";

    /**
     * 签发JWT
     * @param id
     * @param subject 主题 尽可能少
     * @param ttlMillis 过期时间
     * @return  String
     *
     */
    public static String createJWT(String id, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)   // 主题
                .setIssuer("签发者")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm,secretKey); // 签名算法以及密匙
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate); // 过期时间
        }
        return builder.compact();
    }
    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJWT(String jwtStr) {
        CheckResult checkResult = new CheckResult();
        Claims claims;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } catch (ExpiredJwtException e) {
            checkResult.setErrCode(StatusEnum.JWT_ERRCODE_EXPIRE.getCode());
            checkResult.setSuccess(false);
        } catch (SignatureException e) {
            checkResult.setErrCode(StatusEnum.JWT_ERRCODE_FAIL.getCode());
            checkResult.setSuccess(false);
        } catch (Exception e) {
            checkResult.setErrCode(StatusEnum.JWT_ERRCODE_FAIL.getCode());
            checkResult.setSuccess(false);
        }
        return checkResult;
    }
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.decode(JWT_SECERT);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     *
     * 解析JWT字符串
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
    public static void main(String[] args) throws InterruptedException {
        //小明失效 10s
        String sc = createJWT("1","小明", 3000);
        System.out.println(sc);
        System.out.println(validateJWT(sc).getErrCode());
        System.out.println(validateJWT(sc).getClaims().getId());
//        Thread.sleep(3000);
        System.out.println(validateJWT(sc).toString());
    }
}