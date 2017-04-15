package com.example.dai.mvp_example.http;

import android.util.Log;

import com.example.dai.mvp_example.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dai on 2017/4/14.
 */

public class RequestTokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String token = HttpClient.getToken() == null ? "" : HttpClient.getToken();
        Request newRequest = request.newBuilder().addHeader("AI-Bike-Token", token).build();
        Response response = chain.proceed(newRequest);
        //Peek the http response for debugging
        if (BuildConfig.DEBUG) {
            Log.d("HttpResponse", response.peekBody(1024).string());
        }
        return response;
    }
}
