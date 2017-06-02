package com.example.dai.mvp_example.model;

/**
 * Created by daijunwei on 2017/6/2.
 */

public class HttpResult<T> {
    private boolean success;
    private T result;

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }
}
