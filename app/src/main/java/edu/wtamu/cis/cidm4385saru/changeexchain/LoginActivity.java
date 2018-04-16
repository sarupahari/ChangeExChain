package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sarup on 4/15/2018.
 */

public class LoginActivity  extends AppCompatActivity implements View.OnClickListener{

    Button exLogin;
    EditText exEmail, exPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        exEmail = (EditText) findViewById(R.id.exEmail);
        exPassword = (EditText) findViewById(R.id.exEmail);
    }

    @Override
    public void onClick(View view) {

    }
}
