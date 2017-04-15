package com.example.dai.mvp_example;

/**
 * Created by Dai on 2017/4/8.
 */

public interface RegisterContract {
    interface ViewActions{
        void onRegistered(String token);

        void showMessage(String message);
    }

    interface PresenterActions{
        boolean isViewAttached();

        void detachView();

        void register(String account, String password);
    }
}
