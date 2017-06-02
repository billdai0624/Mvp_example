package com.example.dai.mvp_example.presenter;

import com.example.dai.mvp_example.ui.BaseView;

/**
 * Created by daijunwei on 2017/6/2.
 */

public class BasePresenter<V extends BaseView> {
    protected V view;

    public BasePresenter(V view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    public boolean isViewAttached() {
        return view != null;
    }
}
