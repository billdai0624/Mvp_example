package com.example.dai.mvp_example.mvp_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dai.mvp_example.R;

/**
 * Created by Dai on 2017/4/8.
 */

public class Ex_RegisterAccountActivity extends AppCompatActivity implements Ex_RegisterContract.ViewActions {
    private EditText email;
    private EditText password;
    private TextView token_tv;
    private Ex_RegisterAccountPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_main);
        if (presenter == null) {
            presenter = new Ex_RegisterAccountPresenter(this);
        }
        email = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        token_tv = (TextView) findViewById(R.id.token);
        Button submit = (Button) findViewById(R.id.register);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.register(email.getText().toString(), password.getText().toString());
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
    public void onRegistered(String token) {
        token_tv.setText(token);
        showMessage("Register succeed!");
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}