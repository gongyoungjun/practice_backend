package com.example.test.api.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtil {
    private static final String SECRET_KEY = "123456789abcdefg123456789abcdefg123456789abcdefg123456789abcdefg123456789abcdefg123456789abcdefg";

//    eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMiIsImlhdCI6MTY5MTU0NzA2NywiZXhwIjoxNjkxNTUwNjY3fQ.uO8WI7ihXNVzXEbIGeL5_tZCZsMwve-lzvhPyCg2N2M
private static final Algorithm algorithmHS = Algorithm.HMAC256(SECRET_KEY);

    public static String generateToken(int empNo) {
        return JWT.create()
/*                .withSubject(empPhn)  // user 객체의 id 지정*/
                .withClaim("empNo", empNo)
                //JWT 발급한 시간
                .withIssuedAt(new Date(System.currentTimeMillis()))
                // 만료시간 지정
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(10)))
                .sign(algorithmHS); // 개인키 -> Signature 생성
    }

    /**
     * 토큰의 유효성 검사 (만료 여부 및 서명 확인)
     *
     * @param token 확인할 토큰 문자열
     * @return 토큰이 유효하면 true, 아니면 false
     */
    public static boolean isTokenValid(String token) {
        try {
            DecodedJWT jwt = JWT.require(algorithmHS).build().verify(token);
            return jwt.getExpiresAt().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
    public static int extractEmpNo(String token) {
        DecodedJWT jwt = JWT.require(algorithmHS).build().verify(token);
        Claim empNoClaim = jwt.getClaim("empNo");
        return empNoClaim.asInt();
    }


}