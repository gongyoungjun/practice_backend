package com.example.test.api.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Component
public class PasswordUtil {
    /**
     * MessageDigest
     * digest - 바이트배열로 반환
     */
    public String sha256Encrypt(String empPwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); // sha-256사용
            byte[] hash = digest.digest(empPwd.getBytes(StandardCharsets.UTF_8)); //digest 계산

            StringBuilder hexString = new StringBuilder(); //hex(16진수)
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b); // 0xff = 16진수로 표현되는 숫자
                if (hex.length() == 1) hexString.append('0'); //append - 문자열 추가
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
