package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by sarup on 4/15/2018.
 */

public class LoginActivity  extends AppCompatActivity implements View.OnClickListener{

    private EditText exEmail, exPassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        exEmail = (EditText) findViewById(R.id.exEmail);
        exPassword = (EditText) findViewById(R.id.exPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.exTextViewRegister).setOnClickListener(this);
        findViewById(R.id.exButtonLogin).setOnClickListener(this);
    }

    private void userLogin(){
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

        mAuth.signInWithEmailAndPassword(email, password). addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.exTextViewRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.exButtonLogin:
                userLogin();
                break;
        }
    }
}
