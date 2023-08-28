package com.example.test.api.login.vo;

import lombok.Data;

@Data
public class LoginReq {

    private String empPhn;
    private String empPwd;
    private String empAuthCd;
    private String jwtToken; //kakao jwtToken
}
