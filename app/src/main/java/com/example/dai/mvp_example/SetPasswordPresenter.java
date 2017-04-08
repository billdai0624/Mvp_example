package com.example.dai.mvp_example;

/**
 * Created by Dai on 2017/4/8.
 */

public class SetPasswordPresenter implements PasswordSettingContract.PresenterActions, RemoteCallback {
    private PasswordModel model;
    private PasswordSettingContract.ViewActions view;

    public SetPasswordPresenter(PasswordSettingContract.ViewActions view) {
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
    public void setPassword(String password, String confirmed_password) {
        if (!isPasswordValid(password, confirmed_password)) {
            view.showMessage("Invalid password!");
            return;
        }
        if (model == null) {
            model = new PasswordModel(password);
        }
        RestApi.setPassword(model, this);
    }

    @Override
    public void onSuccess() {
        if (view == null) {
            return;
        }
        view.showMessage("Set password success!");
        view.onSetPasswordSuccess();
    }

    @Override
    public void onFailure() {
        if (view == null) {
            return;
        }
        view.showMessage("Set password failure!");
    }

    private boolean isPasswordValid(String password, String confirm_password) {
        if (password.length() < 4 || confirm_password.length() < 4) {
            return false;
        }
        if (!password.equals(confirm_password)) {
            return false;
        }
        return true;
    }
}
