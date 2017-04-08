package com.example.dai.mvp_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dai on 2017/4/8.
 */

public class MvpActivity extends AppCompatActivity implements PasswordSettingContract.ViewActions {
    private EditText password;
    private EditText confirm_password;
    private CheckBox result;
    private SetPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (presenter == null) {
            presenter = new SetPasswordPresenter(this);
        }
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        result = (CheckBox) findViewById(R.id.result);
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setPassword(password.getText().toString(), confirm_password.getText().toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (presenter != null && presenter.isViewAttached()) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public void onSetPasswordSuccess() {
        result.setChecked(true);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}