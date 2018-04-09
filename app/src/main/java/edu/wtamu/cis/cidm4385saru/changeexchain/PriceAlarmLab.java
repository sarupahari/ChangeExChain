package edu.wtamu.cis.cidm4385saru.changeexchain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.PriceAlarmBaseHelper;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.PriceAlarmCursorWrapper;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.PriceAlarmTable;

import static edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.PriceAlarmTable.Cols.*;

/**
 * Created by sarup on 4/1/2018.
 */

public class PriceAlarmLab {
    private static PriceAlarmLab sPriceAlarmLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static PriceAlarmLab get(Context context) {
        if (sPriceAlarmLab == null) {
            sPriceAlarmLab = new PriceAlarmLab(context);
        }

        return sPriceAlarmLab;
    }

    private PriceAlarmLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new PriceAlarmBaseHelper(mContext)
                .getWritableDatabase();

    }

    public void addPriceAlarm(PriceAlarm pA) {
        ContentValues values = getContentValues(pA);
        mDatabase.insert(ChangeExChDbSchema.PriceAlarmTable.NAME, null, values);
    }

    public List<PriceAlarm> getAlarms() {
        List<PriceAlarm> priceAlarms = new ArrayList<>();
        PriceAlarmCursorWrapper cursor = queryPriceAlarm(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                priceAlarms.add(cursor.getAlarm());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return priceAlarms;
    }

    public PriceAlarm getPriceAlarm(UUID id) {
        PriceAlarmCursorWrapper cursor = queryPriceAlarm(
                PriceAlarmTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getAlarm();
        } finally {
            cursor.close();
        }
    }

    public void updateAlarm(PriceAlarm priceAlarm) {
        String uuidString = priceAlarm.getId().toString();
        ContentValues values = getContentValues(priceAlarm);
        mDatabase.update(PriceAlarmTable.NAME, values,
                PriceAlarmTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private PriceAlarmCursorWrapper queryPriceAlarm(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                PriceAlarmTable.NAME,
                null, // Columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new PriceAlarmCursorWrapper(cursor);
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
