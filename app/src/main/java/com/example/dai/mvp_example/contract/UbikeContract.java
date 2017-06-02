package com.example.dai.mvp_example.contract;

import com.example.dai.mvp_example.model.UbikeModel;
import com.example.dai.mvp_example.ui.BaseView;

import java.util.List;

/**
 * Created by daijunwei on 2017/6/2.
 */

public interface UbikeContract {
    interface ViewActions extends BaseView{
        void onUbikeStationsListReceived(List<UbikeModel> stations);
    }

    interface PresenterActions {
        void getNewTaipeiUbikeStations();
    }
}
