package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     findViewById(R.id.bRegister).setOnClickListener(this);

    }

    @Override
    public void onClick (View view)
    {
        switch(view.getId()){
            case R.id.bRegister:
                startActivity(new Intent(this, RegisterActivity.class));
        }
    }
}
