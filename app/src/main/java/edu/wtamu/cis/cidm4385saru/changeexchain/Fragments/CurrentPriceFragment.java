package edu.wtamu.cis.cidm4385saru.changeexchain.Fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

import edu.wtamu.cis.cidm4385saru.changeexchain.R;
import edu.wtamu.cis.cidm4385saru.changeexchain.RESTApiCalls.CoinDeskApi;

public class CurrentPriceFragment extends Fragment {

    View mMainView;
    private String mCurrentCurrencyCode;
    private TextView mCurrentPriceView;
    private TextView mCurrentCurrencyCodeView;
    private static final String TAG = "CurrentPriceFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        new FetchPriceTask().execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.current_price_fragment_layout, container, false);
        mCurrentCurrencyCodeView = mMainView.findViewById(R.id.current_currency_code);
        mCurrentPriceView = mMainView.findViewById(R.id.current_price);

        mCurrentCurrencyCode = "USD";
        mCurrentPriceView.setText("");
        mCurrentCurrencyCodeView.setText(mCurrentCurrencyCode);
        return mMainView;
    }

    private class FetchPriceTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void...params){
            try{
                String result = CoinDeskApi.getCurrentPrice(mCurrentCurrencyCode);
                Log.i(TAG, "Fetched current price " + result);
            }catch(IOException e){
                Log.e(TAG, "Failed to fetch current price");
            }
            return null;
        }
    }
}
