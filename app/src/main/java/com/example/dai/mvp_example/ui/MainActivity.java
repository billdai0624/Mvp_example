package com.example.dai.mvp_example.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.dai.mvp_example.GoogleApiManager;
import com.example.dai.mvp_example.R;
import com.example.dai.mvp_example.ui.ubike.UbikeFragment;

import butterknife.ButterKnife;

/**
 * Created by daijunwei on 2017/6/1.
 */

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomBar;

    private FragmentManager fm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GoogleApiManager.getInstance(getApplicationContext()).getApiClient().connect();
        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.main_container, new UbikeFragment(), UbikeFragment.class.getSimpleName())
                .commitAllowingStateLoss();
        bottomBar = ButterKnife.findById(this, R.id.main_bottomNav);
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_ubike:

                        break;
                    case R.id.navigation_guide:
                        break;
                    case R.id.navigation_universiade:
                        break;
                }
                return true;
            }
        });
    }
}
