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
import java.util.Timer;
import java.util.TimerTask;

import edu.wtamu.cis.cidm4385saru.changeexchain.R;
import edu.wtamu.cis.cidm4385saru.changeexchain.RESTApiCalls.CoinDeskApi;

public class CurrentPriceFragment extends Fragment {

    View mMainView;
    private String mCurrentCurrencyCode;
    private String mCurrentPrice;
    private TextView mCurrentPriceView;
    private TextView mCurrentCurrencyCodeView;
    private static final String TAG = "CurrentPriceFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mCurrentCurrencyCode = "USD";

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            fetchPriceTask performBackgroundTask = new fetchPriceTask();
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            performBackgroundTask.execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 6 * 1000); //execute in every 50000 ms
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.current_price_fragment_layout, container, false);
        mCurrentCurrencyCodeView = mMainView.findViewById(R.id.current_currency_code);
        mCurrentPriceView = mMainView.findViewById(R.id.current_price);
        mCurrentPrice = "";

        mCurrentPriceView.setText(mCurrentPrice);
        mCurrentCurrencyCodeView.setText(mCurrentCurrencyCode);
        return mMainView;
    }

    private class fetchPriceTask extends AsyncTask<Void, Void, String>{
        @Override
        protected String doInBackground(Void...params){
            return new CoinDeskApi().fetchCurrentPrice(mCurrentCurrencyCode);
        }

        @Override
        protected void onPostExecute(String currentPrice){
            mCurrentPrice = currentPrice;
            mCurrentPriceView.setText(mCurrentPrice);
        }
    }
}
