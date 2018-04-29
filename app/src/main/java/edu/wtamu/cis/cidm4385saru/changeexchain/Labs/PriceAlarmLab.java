package edu.wtamu.cis.cidm4385saru.changeexchain.Labs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChainBaseHelper;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.PriceAlarmCursorWrapper;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.PriceAlarmTable;

import static edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.PriceAlarmTable.Cols.*;

/**
 * Created by sarup on 4/1/2018.
 */

public class PriceAlarmLab {
    private static PriceAlarmLab sPriceAlarmLab;
    private Context mContext;
    private FirebaseUser mUser;
    private DatabaseReference mRef;

    public static PriceAlarmLab get(Context context) {
        if (sPriceAlarmLab == null) {
            sPriceAlarmLab = new PriceAlarmLab(context);
        }

        return sPriceAlarmLab;
    }

    private PriceAlarmLab(Context context) {
        mContext = context.getApplicationContext();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser mUser = auth.getCurrentUser();

        if(mUser != null){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            mRef = ref.child("PriceAlarm").child(mUser.getUid());
        }
    }

    public void addPriceAlarm(PriceAlarm pA) {
        mRef.child(pA.getId().toString()).setValue(pA);
    }

    public PriceAlarm getPriceAlarm(UUID id) {

        final PriceAlarm priceAlarm = new PriceAlarm(id);

        mRef.child(id.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PriceAlarm temp = dataSnapshot.getValue(PriceAlarm.class);

                priceAlarm.setCurrencyCode(temp.getCurrencyCode());
                priceAlarm.setPrice(temp.getPrice());
                priceAlarm.setThreshold(temp.getThreshold());
                priceAlarm.setEnabled(temp.isEnabled());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return priceAlarm;
    }

    public void updateAlarm(PriceAlarm priceAlarm) {
        String uuidString = priceAlarm.getId().toString();

        mRef.child(uuidString).setValue(priceAlarm);
    }

    private static ContentValues getContentValues(PriceAlarm priceAlarm) {
        ContentValues values = new ContentValues();
        values.put(UUID, priceAlarm.getId().toString());
        values.put(PRICE, priceAlarm.getPrice());
        values.put(CURRENCYCODE, priceAlarm.getCurrencyCode());
        values.put(THRESHOLD, priceAlarm.getThreshold());
        return values;
    }
}
