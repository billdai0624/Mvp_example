package com.example.dai.mvp_example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

/**
 * Created by daijunwei on 2017/6/2.
 */

public class GoogleApiManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static GoogleApiManager apiManager;
    private GoogleApiClient apiClient;
    private boolean isApiClientConnected;

    private GoogleApiManager(Context context) {
        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .build();
        }
    }

    public static GoogleApiManager getInstance(Context context) {
        if (apiManager == null) {
            synchronized (GoogleApiManager.class) {
                if (apiManager == null) {
                    apiManager = new GoogleApiManager(context);
                }
            }
        }
        return apiManager;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        isApiClientConnected = true;
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        isApiClientConnected = false;
    }

    public GoogleApiClient getApiClient() {
        return apiClient;
    }

    public boolean isApiClientConnected() {
        return isApiClientConnected;
    }
}
