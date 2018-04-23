package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by sarup on 4/15/2018.
 */

public class PriceAlarmFragment extends Fragment {
    private PriceAlarm mPriceAlarm;
    private Switch TimeFormatSwitch;
    private TextView stateOnOff;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mPriceAlarm = new PriceAlarm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pricealarm, container, false);

        TimeFormatSwitch = (Switch) v.findViewById(R.id.exchain_timeformat);

        return v;
    }
}

