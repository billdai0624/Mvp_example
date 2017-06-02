package com.example.dai.mvp_example.presenter.ubike;

import android.util.Log;

import com.example.dai.mvp_example.contract.UbikeContract;
import com.example.dai.mvp_example.http.ApiClient;
import com.example.dai.mvp_example.http.api.UbikeApi;
import com.example.dai.mvp_example.model.DataSetModel;
import com.example.dai.mvp_example.model.HttpResult;
import com.example.dai.mvp_example.model.UbikeModel;
import com.example.dai.mvp_example.presenter.BasePresenter;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by daijunwei on 2017/6/2.
 */

public class UbikePresenter extends BasePresenter<UbikeContract.ViewActions>
        implements UbikeContract.PresenterActions {

    public UbikePresenter(UbikeContract.ViewActions view) {
        super(view);
    }

    @Override
    public void getNewTaipeiUbikeStations() {
        Observable.interval(0, 30, TimeUnit.SECONDS, Schedulers.io())
                .concatMap(new Function<Long, ObservableSource<HttpResult<DataSetModel<UbikeModel>>>>() {
                    @Override
                    public ObservableSource<HttpResult<DataSetModel<UbikeModel>>> apply(@NonNull Long aLong) throws Exception {
                        return ApiClient.getInstance().createService(UbikeApi.class)
                                .getNewTaipeiUbikeStations("http://data.ntpc.gov.tw/api/v1/rest/datastore/382000000A-000352-001");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HttpResult<DataSetModel<UbikeModel>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(HttpResult<DataSetModel<UbikeModel>> result) {
                        Log.d("test", "test");
                        if (!isViewAttached()) {
                            return;
                        }
                        view.onUbikeStationsListReceived(result.getResult().getRecords());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error", "error");
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
