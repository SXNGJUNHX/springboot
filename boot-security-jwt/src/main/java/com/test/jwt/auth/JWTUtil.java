package com.test.jwt.auth;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secretKey) {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    //검증용 메서드
    public String getUsername(String token) {

        //                   발급한 토큰이 맞는지?            파싱                      본문 내용
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    //검증용 메서드
    public String getRole(String token) {

        //                   발급한 토큰이 맞는지?            파싱                      본문 내용
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    //검증용 메서드
    public Boolean isExpired(String token) {

        //                   발급한 토큰이 맞는지?            파싱                      본문 내용
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }


    //JWT 생성 메서드
    //- 로그인 성공 > JWT 발급
    //- JWT = header.payload.signature
    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username) //Payload
                .claim("role", role) //Payload
                .issuedAt(new Date(System.currentTimeMillis())) //발생 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) //유효 시간
                .signWith(secretKey) //암호화 > Signature
                .compact();

    }

}














