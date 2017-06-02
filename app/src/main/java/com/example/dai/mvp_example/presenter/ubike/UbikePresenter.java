package com.example.dai.mvp_example.presenter.ubike;

import com.example.dai.mvp_example.contract.UbikeContract;
import com.example.dai.mvp_example.presenter.BasePresenter;

/**
 * Created by daijunwei on 2017/6/2.
 */

public class UbikePresenter extends BasePresenter<UbikeContract.ViewActions>
        implements UbikeContract.PresenterActions{

    public UbikePresenter(UbikeContract.ViewActions view) {
        super(view);
    }
}
