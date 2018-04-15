package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmetManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmenById(R.id.fragmnet_container);

        if (fragment == null)
        {
            fragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}
