package com.example.dai.mvp_example.http;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dai on 2017/4/14.
 */

public class ApiClient {
    private static final String BASE_URL = "https://reqres.in/";

    private static ApiClient mInstance;
    private final OkHttpClient client;
    private Retrofit retrofit;

    private ApiClient() {
        client = HttpClient.getOkHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static ApiClient getInstance() {
        if (mInstance == null){
            synchronized (ApiClient.class) {
                if (mInstance == null)
                    mInstance = new ApiClient();
            }
        }
        return (mInstance);
    }

    public <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }
}
