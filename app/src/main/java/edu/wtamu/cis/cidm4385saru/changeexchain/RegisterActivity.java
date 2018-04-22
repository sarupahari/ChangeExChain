package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by sarup on 4/15/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText exEmail, exPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        exEmail = (EditText) findViewById(R.id.exEmail);
        exPassword = (EditText) findViewById(R.id.exPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.exButtonRegister).setOnClickListener(this);
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

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        if(!task.isSuccessful()){
                           Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exButtonRegister:
                registerUser();
                break;

            case R.id.exTextViewLogin:
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}