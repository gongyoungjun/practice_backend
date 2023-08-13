package com.example.test.api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtil {
    private static final String SECRET_KEY = "123456789abcdefg123456789abcdefg123456789abcdefg123456789abcdefg123456789abcdefg123456789abcdefg";

//    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMiIsImlhdCI6MTY5MTU0NzA2NywiZXhwIjoxNjkxNTUwNjY3fQ.uO8WI7ihXNVzXEbIGeL5_tZCZsMwve-lzvhPyCg2N2M

    public static String generateToken(String empPhn, int empNo) { // 토큰 생성
        return Jwts.builder()
/*                // user 객체의 id 지정
                .setSubject(empNm)*/
                // 유저 전화번호
                .setSubject(empPhn)
                // empNo
                .claim("empNo", empNo)
                //JWT 발급한 시간
                .setIssuedAt(new Date(System.currentTimeMillis()))
                // 만료시간 지정
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(1)))  // 1시간 유효
                // 개인키 -> Signature 생성
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 토큰의 유효성 검사 (만료 여부 및 서명 확인)
     *
     * @param token 확인할 토큰 문자열
     * @return 토큰이 유효하면 true, 아니면 false
     */
    public static boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public static int extractEmpNo(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("empNo", Integer.class);
    }


}