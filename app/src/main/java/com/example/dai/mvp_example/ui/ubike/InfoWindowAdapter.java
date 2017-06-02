package com.example.dai.mvp_example.ui.ubike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dai.mvp_example.R;
import com.example.dai.mvp_example.model.UbikeModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import butterknife.ButterKnife;

/**
 * Created by daijunwei on 2017/6/2.
 */

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;

    public InfoWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.info_window, null);
        UbikeModel model = (UbikeModel) marker.getTag();
        if (model == null) {
            return view;
        }
        TextView stationName = ButterKnife.findById(view, R.id.infoWindow_stationName);
        stationName.setText(model.getSna());
        TextView availableBikes = ButterKnife.findById(view, R.id.infoWindow_availableBikes);
        availableBikes.setText(model.getSbi());
        TextView availableSpaces = ButterKnife.findById(view, R.id.infoWindow_availableSpaces);
        availableSpaces.setText(model.getBemp());
        TextView availableBikes_est = ButterKnife.findById(view, R.id.infoWindow_availableBikes_est);
        //TODO
        TextView availableSpaces_est = ButterKnife.findById(view, R.id.infoWindow_availableSpaces_est);
        //TODO
        Button startPoint = ButterKnife.findById(view, R.id.infoWindow_startPoint_btn);
        startPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button endPoint = ButterKnife.findById(view, R.id.infoWindow_endPoint_btn);
        endPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
