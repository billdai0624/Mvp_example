package com.example.dai.mvp_example;

import com.example.dai.mvp_example.http.ApiCallback;
import com.example.dai.mvp_example.http.HttpClient;
import com.example.dai.mvp_example.http.http_response.RegisterResponse;

import retrofit2.Response;

/**
 * Created by Dai on 2017/4/8.
 */

public class RegisterAccountPresenter implements RegisterContract.PresenterActions {
    private AccountModel model;
    private RegisterContract.ViewActions view;

    public RegisterAccountPresenter(RegisterContract.ViewActions view) {
        this.view = view;
    }

    @Override
    public boolean isViewAttached() {
        return view != null;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void register(final String account, final String password) {
        if (!isDataValid(account, password)) {
            return;
        }
        if (model == null) {
            model = new AccountModel(account, password);
        }
        else{
            model.setAccount(account);
            model.setPassword(password);
        }
        model.register(model, new ApiCallback<RegisterResponse>() {
            @Override
            public void onApiSuccess(Response<RegisterResponse> response) {
                if (!isViewAttached()) {
                    return;
                }
                //What view should do when success
                RegisterResponse result = response.body();
                String token = result.getToken();
                HttpClient.setToken(token);
                view.onRegistered(token);
            }

            @Override
            public void onApiFailure(Response<RegisterResponse> response) {
                if (!isViewAttached()) {
                    return;
                }
                //What view should do when success
                view.showMessage("Register failed!!");
            }

            @Override
            public void onNoResponse(Throwable throwable) {
                if (!isViewAttached()) {
                    return;
                }
                view.showMessage("Network unavailable?");
            }

            @Override
            public void onTokenExpired() {
                if (!isViewAttached()) {
                    return;
                }
                //Current token is expired, call updateToken Api
                /*
                ...
                ...
                ...
                 */
                //Now the token is updated, call register() again
                register(account, password);
            }
        });
    }

    private boolean isDataValid(String account, String password) {
        if (password.length() < 4) {
            view.showMessage("Password is too short!");
            return false;
        }
        if (account.equals(password)) {
            view.showMessage("Account is the same as password!");
            return false;
        }
        return true;
    }
}
