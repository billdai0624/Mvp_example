package com.example.dai.mvp_example.http;

import com.example.dai.mvp_example.http.Api.ExampleApi;

/**
 * Created by Dai on 2017/4/8.
 */

public class ServiceFactory {
    private static ExampleApi exampleApi;

    public static ExampleApi getExampleApi() {
        if (exampleApi == null) {
            exampleApi = ApiClient.getInstance().createService(ExampleApi.class);
        }
        return exampleApi;
    }
}
