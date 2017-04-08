package com.example.dai.mvp_example;

/**
 * Created by Dai on 2017/4/8.
 */

public class RestApi {
    public static void setPassword(PasswordModel model, RemoteCallback callback){
        double random = Math.random();
        if (random < 0.5) {
            callback.onSuccess();
        }
        else{
            callback.onFailure();
        }
    }
}
