package com.example.dai.mvp_example.http.Api;

import com.example.dai.mvp_example.mvp_example.Ex_AccountModel;
import com.example.dai.mvp_example.http.http_response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Dai on 2017/4/14.
 */

public interface ExampleApi {
    String prefix = "api";

    @Headers("Content-Type: application/json")
    @POST(prefix +"/register")
    Call<RegisterResponse> register(@Body Ex_AccountModel body);
}
