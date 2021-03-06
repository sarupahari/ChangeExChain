package edu.wtamu.cis.cidm4385saru.changeexchain.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.wtamu.cis.cidm4385saru.changeexchain.Classes.PriceAlarm;
import edu.wtamu.cis.cidm4385saru.changeexchain.Classes.SharedPreferencesManager;
import edu.wtamu.cis.cidm4385saru.changeexchain.Classes.UserSetting;
import edu.wtamu.cis.cidm4385saru.changeexchain.R;

/**
 * Created by sarup on 4/15/2018.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String KEY = "RegisterActivity";

    private EditText exEmail, exPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView text = findViewById(R.id.exCreditCoinDesk);
        text.setMovementMethod(LinkMovementMethod.getInstance());

        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance().getReference();

        exEmail = findViewById(R.id.exEmail);
        exPassword = findViewById(R.id.exPassword);
        progressBar = findViewById(R.id.progressbar);

        findViewById(R.id.exButtonRegister).setOnClickListener(this);
        findViewById(R.id.exTextViewLogin).setOnClickListener(this);
    }

    private void registerUser() {
        String email = exEmail.getText().toString().trim();
        final String password = exPassword.getText().toString().trim();

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
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            final String uuid = user.getUid();
                            mDB.child("UserSettings").child(uuid).setValue(UserSetting.createDefault());

                            /*PriceAlarm pA = PriceAlarm.createDefault();
                            mDB.child("PriceAlarms").child(pA.getId().toString()).setValue(pA);*/

                            DatabaseReference ref = mDB.child("UserSettings").child(uuid);
                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    SharedPreferencesManager.setUserSettingsPreferences(dataSnapshot, getApplicationContext());
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else {
                          if(task.getException() instanceof FirebaseAuthException){
                              Toast.makeText(getApplicationContext(),"Email already exist", Toast.LENGTH_SHORT).show();
                          }else{
                             Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                          }
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