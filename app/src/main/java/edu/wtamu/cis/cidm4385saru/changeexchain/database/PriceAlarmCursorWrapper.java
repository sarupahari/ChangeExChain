package edu.wtamu.cis.cidm4385saru.changeexchain.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;
import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.PriceAlarmTable;
import edu.wtamu.cis.cidm4385saru.changeexchain.Classes.PriceAlarm;

public class PriceAlarmCursorWrapper extends CursorWrapper {

    public PriceAlarmCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public PriceAlarm getAlarm() {
        String uuidString = getString(getColumnIndex(PriceAlarmTable.Cols.UUID));
        String currencyCode = getString(getColumnIndex(PriceAlarmTable.Cols.CURRENCYCODE));
        int price = getInt(getColumnIndex(PriceAlarmTable.Cols.PRICE));
        String threshold = getString(getColumnIndex(PriceAlarmTable.Cols.THRESHOLD));

        PriceAlarm priceAlarm = new PriceAlarm(UUID.fromString(uuidString));
        priceAlarm.setCurrencyCode(currencyCode);
        priceAlarm.setPrice(price);
        priceAlarm.setThreshold(threshold);

        return priceAlarm;
    }
}
