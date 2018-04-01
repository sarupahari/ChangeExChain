package edu.wtamu.cis.cidm4385saru.changeexchain.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.PriceAlarmTable;
/**
 * Created by sarup on 4/1/2018.
 */


public class PriceAlarmBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "priceAlarmBase.db";

    public PriceAlarmBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + PriceAlarmTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                PriceAlarmTable.Cols.CURRENCYCODE + ", " +
                PriceAlarmTable.Cols.PRICE + ", " +
                PriceAlarmTable.Cols.THRESHOLD +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
