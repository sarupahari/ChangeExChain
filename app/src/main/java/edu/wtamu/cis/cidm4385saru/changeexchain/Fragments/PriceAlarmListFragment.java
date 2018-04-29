package edu.wtamu.cis.cidm4385saru.changeexchain.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.wtamu.cis.cidm4385saru.changeexchain.Classes.PriceAlarm;
import edu.wtamu.cis.cidm4385saru.changeexchain.Labs.PriceAlarmLab;
import edu.wtamu.cis.cidm4385saru.changeexchain.R;

public class PriceAlarmListFragment extends Fragment {

    private RecyclerView mPriceAlarmRecyclerView;
    private PriceAlarmAdapter mAdapter;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    private ArrayList<PriceAlarm> mPriceAlarms = new ArrayList<>();

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

        FirebaseAuth auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();

        if(mUser != null){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            mRef = ref.child("PriceAlarm").child(mUser.getUid());
        }

        getAlarms();
        updateUI();

        return view;
    }

    private void getAlarms(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    PriceAlarm pA = new PriceAlarm(UUID.fromString(snapshot.getKey()));
                    PriceAlarm temp = snapshot.getValue(PriceAlarm.class);

                    pA.setThreshold(temp.getThreshold());
                    pA.setPrice(temp.getPrice());
                    pA.setCurrencyCode(temp.getCurrencyCode());
                    pA.setEnabled(temp.isEnabled());

                    mAdapter.mPriceAlarms.add(pA);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateUI(){
        mAdapter = new PriceAlarmAdapter(mPriceAlarms);
        mPriceAlarmRecyclerView.setAdapter(mAdapter);
    }

    /*
    * This is the holder, it will hold all of the information for an individual alarm and is each object inside of the recycler view
    * */
    private class PriceAlarmHolder extends RecyclerView.ViewHolder{

        private TextView mAlarmThreshold;
        private TextView mAlarmPrice;
        private TextView mCurrencyCode;
        private Button mEnabledButton;
        private PriceAlarm mPriceAlarm;

        public PriceAlarmHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_price_alarm_item, parent, false));

            mAlarmThreshold = itemView.findViewById(R.id.alarm_threshold);
            mAlarmPrice = itemView.findViewById(R.id.alarm_price);
            mCurrencyCode = itemView.findViewById(R.id.alarm_currency_code);
            mEnabledButton = itemView.findViewById(R.id.enabled_button);

        }

        public void bind (final PriceAlarm priceAlarm){
            mPriceAlarm = priceAlarm;

            mAlarmThreshold.setText(mPriceAlarm.getThreshold());
            mAlarmPrice.setText(mPriceAlarm.getPrice());
            mCurrencyCode.setText(mPriceAlarm.getCurrencyCode());
            if(mPriceAlarm.isEnabled()){
                mEnabledButton.setText("Enabled");
            }else{
                mEnabledButton.setText("Disabled");
            }

            mEnabledButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPriceAlarm.isEnabled()) {
                        mEnabledButton.setText("Disabled");
                        mPriceAlarm.setEnabled(false);
                    } else {
                        mEnabledButton.setText("Enabled");
                        mPriceAlarm.setEnabled(true);
                    }

                    mRef.child(mPriceAlarm.getId().toString()).child("enabled").setValue(mPriceAlarm.isEnabled());
                }

            });


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
