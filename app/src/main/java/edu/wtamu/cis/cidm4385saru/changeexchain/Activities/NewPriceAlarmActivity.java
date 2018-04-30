package edu.wtamu.cis.cidm4385saru.changeexchain.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.wtamu.cis.cidm4385saru.changeexchain.R;
import edu.wtamu.cis.cidm4385saru.changeexchain.RESTApiCalls.CoinDeskApi;

public class NewPriceAlarmActivity extends AppCompatActivity {

    private List<String> mSupportedCurrencies;
    private SearchableSpinner mCurrencyDropDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_price_alarm);
        mCurrencyDropDown = findViewById(R.id.new_alarm_currency);
        mCurrencyDropDown.setTitle("Supported Currencies");
        mCurrencyDropDown.setPositiveButton("OK");
        new fetchSupportedCurrenciesTask().execute();
    }

    private class fetchSupportedCurrenciesTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void...params){
            return new CoinDeskApi().fetchSupportedCurrencies();
        }

        @Override
        protected void onPostExecute(ArrayList<String> supportedCurrencies){
            mSupportedCurrencies = supportedCurrencies;
            ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mSupportedCurrencies);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mCurrencyDropDown.setAdapter(currencyAdapter);
        }
    }
}
