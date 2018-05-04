package edu.wtamu.cis.cidm4385saru.changeexchain.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import edu.wtamu.cis.cidm4385saru.changeexchain.Classes.PriceAlarm;
import edu.wtamu.cis.cidm4385saru.changeexchain.R;
import edu.wtamu.cis.cidm4385saru.changeexchain.RESTApiCalls.CoinDeskApi;

public class NewPriceAlarmActivity extends AppCompatActivity {

    private List<String> mSupportedCurrencies;
    private SearchableSpinner mCurrencyDropDown;
    private Button mSaveButton;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    private EditText mAlarmPrice;
    private RadioGroup mThresholdGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_price_alarm);

        mAlarmPrice = findViewById(R.id.exAlarmPrice);
        mThresholdGroup = findViewById(R.id.radioGroupThreshold);

        mCurrencyDropDown = findViewById(R.id.new_alarm_currency);
        mCurrencyDropDown.setTitle("Supported Currencies");
        mCurrencyDropDown.setPositiveButton("Select");
        new fetchSupportedCurrenciesTask().execute();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("PriceAlarm").child(mUser.getUid());

        mSaveButton = findViewById(R.id.button_save_price_alarm);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PriceAlarm priceAlarm = new PriceAlarm();

                //Enabled is true
                priceAlarm.setEnabled(true);

                //Get the currency code as a string
                String currCode = mCurrencyDropDown.getSelectedItem().toString();

                //Currency code
                priceAlarm.setCurrencyCode(currCode);

                //Price
                Currency currency = Currency.getInstance(currCode);
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                double temp = Double.parseDouble(mAlarmPrice.getText().toString());
                String price = formatter.format(temp);
                String priceString = currency.getSymbol() + price;
                priceAlarm.setPrice(priceString);

                //Threshold
                RadioButton button = findViewById(mThresholdGroup.getCheckedRadioButtonId());
                priceAlarm.setThreshold(button.getText().toString());

                mRef.child(priceAlarm.getId().toString()).setValue(priceAlarm);

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
            ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, mSupportedCurrencies);
            currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mCurrencyDropDown.setAdapter(currencyAdapter);
        }
    }
}
