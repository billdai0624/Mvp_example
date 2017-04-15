package com.example.dai.mvp_example.http.http_response;

/**
 * Created by Dai on 2017/4/14.
 */

public class RegisterResponse {
    private String token;

    public RegisterResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }
}
