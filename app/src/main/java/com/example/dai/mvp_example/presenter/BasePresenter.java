package com.example.dai.mvp_example.presenter;

import com.example.dai.mvp_example.ui.BaseView;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by daijunwei on 2017/6/2.
 */

public class BasePresenter<V extends BaseView> {
    protected V view;
    protected CompositeDisposable compositeDisposable;

    public BasePresenter(V view) {
        this.view = view;
        compositeDisposable = new CompositeDisposable();
    }

    public void detachView() {
        view = null;
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public boolean isViewAttached() {
        return view != null;
    }
}
