package com.example.dai.mvp_example;

/**
 * Created by Dai on 2017/4/8.
 */

public interface PasswordSettingContract {
    interface ViewActions{
        void onSetPasswordSuccess();

        void showMessage(String message);
    }

    interface PresenterActions{
        boolean isViewAttached();

        void detachView();

        void setPassword(String password, String confirmed_password);
    }
}
