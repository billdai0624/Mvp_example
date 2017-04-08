package com.example.dai.mvp_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MvActivity extends AppCompatActivity implements RemoteCallback {
    private EditText password;
    private EditText confirm_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPasswordValid(password.getText().toString(), confirm_password.getText().toString())) {
                    Toast.makeText(MvActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                    return;
                }
                submit(password.getText().toString());
            }
        });
    }

    private boolean isPasswordValid(String password, String confirm_password) {
        if (password.length() < 4 || confirm_password.length() < 4) {
            return false;
        }
        if (!password.equals(confirm_password)) {
            return false;
        }
        return true;
    }

    private void submit(String password) {
        PasswordModel model = new PasswordModel(password);
        RestApi.setPassword(model, this);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Set password succeed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "Set password fail!", Toast.LENGTH_SHORT).show();
    }
}
