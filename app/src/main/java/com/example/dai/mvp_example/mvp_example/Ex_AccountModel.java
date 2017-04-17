package com.example.dai.mvp_example.mvp_example;

import com.example.dai.mvp_example.http.ApiCallback;
import com.example.dai.mvp_example.http.ServiceFactory;
import com.example.dai.mvp_example.http.http_response.RegisterResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dai on 2017/4/8.
 */

public class Ex_AccountModel {
    @SerializedName("email")
    private String account;
    private String password;

    public Ex_AccountModel(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void register(Ex_AccountModel model, ApiCallback<RegisterResponse> callback){
        ServiceFactory.getExampleApi().register(model).enqueue(callback);
    }
}
