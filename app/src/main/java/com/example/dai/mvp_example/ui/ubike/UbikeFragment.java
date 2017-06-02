package com.example.dai.mvp_example.ui.ubike;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dai.mvp_example.GoogleApiManager;
import com.example.dai.mvp_example.R;
import com.example.dai.mvp_example.contract.UbikeContract;
import com.example.dai.mvp_example.model.UbikeModel;
import com.example.dai.mvp_example.presenter.ubike.UbikePresenter;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.Unbinder;

import static android.support.v4.app.ActivityCompat.checkSelfPermission;

/**
 * Created by daijunwei on 2017/6/1.
 */

public class UbikeFragment extends Fragment implements OnMapReadyCallback, LocationListener,
        UbikeContract.ViewActions {

    private static final int REQ_GPS_PERMISSION = 0;
    private static final int REQ_GPS_SETTING_REQUEST = 1;
    private static final LatLng DEFAULT_POS = new LatLng(25.0371546, 121.5624078);
    private Unbinder unbinder;
    private UbikePresenter presenter;
    private GoogleMap googleMap;
    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter == null) {
            presenter = new UbikePresenter(this);
        }
        googleApiClient = GoogleApiManager.getInstance(getActivity().getApplicationContext()).getApiClient();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ubike, container, false);
        //unbinder = ButterKnife.bind(this, root);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.ubike_mapFragment);
        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
        if (!hasGpsPermission()) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, REQ_GPS_PERMISSION);
        }
        else {
            requestLocationUpdate();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        if (presenter != null && presenter.isViewAttached()) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQ_GPS_PERMISSION) {
            return;
        }
        if (grantResults.length == 0) {
            return;
        }
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }
        requestLocationUpdate();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (currentLocation != null) {
            moveCameraTo(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
        }
        else {
            if (hasGpsPermission()) {
                Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                if (lastLocation != null) {
                    moveCameraTo(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()));
                }
                else {
                    moveCameraTo(DEFAULT_POS);
                }
            }
            else {
                moveCameraTo(DEFAULT_POS);
            }
        }
        googleMap.setInfoWindowAdapter(new InfoWindowAdapter(getContext()));
        presenter.getNewTaipeiUbikeStations();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onUbikeStationsListReceived(List<UbikeModel> stations) {
        if (googleMap == null) {
            return;
        }
        googleMap.clear();
        for (UbikeModel model : stations) {
            googleMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.valueOf(model.getLat()),
                                                             Double.valueOf(model.getLng()))))
                    .setTag(model);
        }
    }

    private boolean hasGpsPermission() {
        String fine = Manifest.permission.ACCESS_FINE_LOCATION;
        String coarse = Manifest.permission.ACCESS_COARSE_LOCATION;
        return checkSelfPermission(getContext(), fine) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(getContext(), coarse) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationUpdate() {
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(30 * 1000)        /** app prefer rate(ms) */
                .setFastestInterval(5 * 1000);  /** set max rate(ms) */
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true);

        if (getActivity() == null) {
            return;
        }
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        /** All location settings are satisfied. The client can initialize location
                         requests here. */
                        /*if (mapManager.getMap() != null) {
                            mapManager.getMap().setMyLocationEnabled(true);
                        }*/
                        if (!googleApiClient.isConnected()) {
                            return;
                        }
                        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, UbikeFragment.this);
                        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, UbikeFragment.this);
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        /** Location settings are not satisfied. But could be fixed by showing the user
                         a dialog. */
                        try {
                            status.startResolutionForResult(getActivity(), REQ_GPS_SETTING_REQUEST);
                        }
                        catch (Exception e) {
                            Log.e("ResolutionRequired_Fail", e.toString());
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        /** Location settings are not satisfied. However, we have no way to fix the
                         settings so we won't show the dialog. */
                        // do nothing
                        break;
                }
            }
        });
    }

    private void moveCameraTo(LatLng place) {
        if (googleMap == null) {
            return;
        }
        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(place)
                        .zoom(16)
                        .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 500, null);
    }
}
