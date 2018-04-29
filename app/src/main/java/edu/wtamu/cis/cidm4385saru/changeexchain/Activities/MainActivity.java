package edu.wtamu.cis.cidm4385saru.changeexchain.Activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import edu.wtamu.cis.cidm4385saru.changeexchain.R;

public class MainActivity extends AppCompatActivity{

    private ImageView mAddAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddAlarm = findViewById(R.id.add_alarm);

        mAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Add to where it takes you to the add an alarm view
            }
        });
    }
}
