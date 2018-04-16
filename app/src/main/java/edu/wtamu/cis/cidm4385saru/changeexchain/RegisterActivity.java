package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

/**
 * Created by sarup on 4/15/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText exEmail, exPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        exEmail = (EditText) findViewById(R.id.exEmail);
        exPassword = (EditText) findViewById(R.id.exPassword);
    }

    private void registerUser() {
        String email = exEmail.getText().toString().trim();
        String password = exPassword.getText().toString().trim();

        if (email.isEmpty()) {
            exEmail.setError("Email is required");
            exPassword.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            exEmail.setError("Please enter a valid email");
            exEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            exPassword.setError("Password is required");
            exPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            exPassword.setError("Minimum length of password is 6");
            exPassword.requestFocus();
            return;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exRegister:
                registerUser();
                break;

            case R.id.exLogin:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}