package com.example.test.api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtil {
    private static final String SECRET_KEY = "1234567890abcdEFGH1234567890abcdEFGH123456789123456789123456789";

    public static String generateToken(String empNm) { // 토큰 생성
        return Jwts.builder()
                // user 객체의 id 지정
                .setSubject(empNm)
                //JWT 발급한 시간
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 만료시간 지정
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))  // 1시간 유효
                // 개인키 -> Signature 생성
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String validateToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}