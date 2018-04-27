package edu.wtamu.cis.cidm4385saru.changeexchain.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.wtamu.cis.cidm4385saru.changeexchain.Classes.PriceAlarm;
import edu.wtamu.cis.cidm4385saru.changeexchain.Labs.PriceAlarmLab;
import edu.wtamu.cis.cidm4385saru.changeexchain.R;

public class PriceAlarmListFragment extends Fragment {

    private RecyclerView mPriceAlarmRecyclerView;
    private PriceAlarmAdapter mAdapter;

    @Nullable
    @Override
    /*
    * This is the onCreateView for the Recycler view which is responsible for holding everything and moving it around.
    * */
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.price_alarm_list_layout, container, false);
        mPriceAlarmRecyclerView =  view
                .findViewById(R.id.alarm_recycler_view);
        mPriceAlarmRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI(){
        PriceAlarmLab priceAlarmLab = PriceAlarmLab.get(getActivity());
        List<PriceAlarm> priceAlarms = priceAlarmLab.getAlarms();

        mAdapter = new PriceAlarmAdapter(priceAlarms);
        mPriceAlarmRecyclerView.setAdapter(mAdapter);
    }

    /*
    * This is the holder, it will hold all of the information for an individual alarm and is each object inside of the recycler view
    * */
    private class PriceAlarmHolder extends RecyclerView.ViewHolder{

        private TextView mAlarmAmount;
        private TextView mThreshold;
        private PriceAlarm mPriceAlarm;

        public PriceAlarmHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_price_alarm_item, parent, false));

            mAlarmAmount = itemView.findViewById(R.id.alarm_amount);
            mThreshold = itemView.findViewById(R.id.alarm_threshold);
        }

        public void bind (PriceAlarm priceAlarm){
            mPriceAlarm = priceAlarm;
            mAlarmAmount.setText(Integer.toString(mPriceAlarm.getPrice()));
            mThreshold.setText(mPriceAlarm.getThreshold());
        }
    }

    /*
    * This is the adapter which is responsible for putting the stuff inside of the holders.
    * */
    private class PriceAlarmAdapter extends RecyclerView.Adapter<PriceAlarmHolder>{
        private List<PriceAlarm> mPriceAlarms;

        public PriceAlarmAdapter(List<PriceAlarm> priceAlarms){
            mPriceAlarms = priceAlarms;
        }

        @Override
        public PriceAlarmHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PriceAlarmHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PriceAlarmHolder holder, int position) {
            PriceAlarm priceAlarm = mPriceAlarms.get(position);
            holder.bind(priceAlarm);
        }

        @Override
        public int getItemCount() {
            return mPriceAlarms.size();
        }
    }

}
