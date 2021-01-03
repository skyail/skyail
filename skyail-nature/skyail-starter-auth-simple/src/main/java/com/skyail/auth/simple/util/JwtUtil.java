package com.skyail.auth.simple.util;


import com.skyail.auth.simple.entity.User;
import com.skyail.common.constant.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    private static final Long DEFAULT_EXPIRE = 2*3600*1000L; // 默认两个小时

    /**
     * 密钥加密token
     *
     * @param
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(User user , Long expire) throws Exception {
        String compactJws = Jwts.builder()
                .setSubject(CommonConstants.SUBJECT_NAME)
                .claim(CommonConstants.USERNAME,user.getUsername())
                .claim(CommonConstants.PASSWORD , user.getPassword())
                .setExpiration(new Date(new Date().getTime() + (expire==null?DEFAULT_EXPIRE:expire)))
                .signWith(SignatureAlgorithm.HS512, CommonConstants.SIGN_KEY)
                .compact();
        return compactJws;
    }


    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Jws<Claims> parserToken(String token) throws Exception {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(CommonConstants.SIGN_KEY).parseClaimsJws(token);
        return claimsJws;
    }
    /**
     * 获取token中的用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static User getUserInfoFromToken(String token) throws Exception {
        Jws<Claims> claimsJws = parserToken(token);
        Claims body = claimsJws.getBody();
        String username = body.get(CommonConstants.USERNAME,String.class);
        String password = body.get(CommonConstants.PASSWORD,String.class);
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    public static void main(String[] args) throws Exception{
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        String token = generateToken(user,null);
        System.out.println(token);
        Jws<Claims> claims = parserToken(token);
        System.out.println(claims.getSignature());
    }

}
