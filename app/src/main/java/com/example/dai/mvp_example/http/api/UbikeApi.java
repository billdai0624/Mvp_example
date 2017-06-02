package com.example.dai.mvp_example.http.api;

import com.example.dai.mvp_example.model.DataSetModel;
import com.example.dai.mvp_example.model.HttpResult;
import com.example.dai.mvp_example.model.UbikeModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

/**
 * Created by daijunwei on 2017/6/2.
 */

public interface UbikeApi {
    @Headers("Content-Type: application/json")
    @GET
    Observable<HttpResult<DataSetModel<UbikeModel>>> getNewTaipeiUbikeStations(@Url String url);
}
