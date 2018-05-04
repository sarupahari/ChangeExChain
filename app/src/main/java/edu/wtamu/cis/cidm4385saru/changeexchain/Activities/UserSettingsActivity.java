package edu.wtamu.cis.cidm4385saru.changeexchain.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import edu.wtamu.cis.cidm4385saru.changeexchain.R;
import edu.wtamu.cis.cidm4385saru.changeexchain.RESTApiCalls.CoinDeskApi;

public class UserSettingsActivity extends AppCompatActivity{

    private RadioGroup mThemeGroup;
    private List<String> mSupportedCurrencies;
    private SearchableSpinner mCurrencyPreference;
    private Spinner mTimeFormat;
    private Button mSaveButton;
    private DatabaseReference mRef;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("UserSettings").child(mUser.getUid());

        mThemeGroup = findViewById(R.id.radioGroup_theme);

        //Assign all of the Spinners their insides.
        //Currency Preferences spinner
        mCurrencyPreference = findViewById(R.id.currency_preference);
        mCurrencyPreference.setTitle("Supported Currencies");
        mCurrencyPreference.setPositiveButton("Select");
        new UserSettingsActivity.fetchSupportedCurrenciesTask().execute();

        //Time format Spinner
        mTimeFormat = findViewById(R.id.time_format);
        String[] timeFormatArray = new String[] {
                "12", "24"
        };
        ArrayAdapter<String> timeFormatAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, timeFormatArray);
        timeFormatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTimeFormat.setAdapter(timeFormatAdapter);

        mSaveButton = findViewById(R.id.save_settings);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton themeButton = findViewById(mThemeGroup.getCheckedRadioButtonId());
                mRef.child("colorMode").setValue(themeButton.getText());
                mRef.child("currencyPreference").setValue(mCurrencyPreference.getSelectedItem().toString());
                mRef.child("timeFormat").setValue(mTimeFormat.getSelectedItem().toString());

                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private class fetchSupportedCurrenciesTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void...params){
            return new CoinDeskApi().fetchSupportedCurrencies();
        }

        @Override
        protected void onPostExecute(ArrayList<String> supportedCurrencies){
            mSupportedCurrencies = supportedCurrencies;
            ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.spinner_item, mSupportedCurrencies);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mCurrencyPreference.setAdapter(currencyAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_action_back, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_back_button:
                Log.i("MainActivity", Integer.toString(item.getItemId()));
                Intent back = new Intent(UserSettingsActivity.this, MainActivity.class);
                startActivity(back);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
}
