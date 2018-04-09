package edu.wtamu.cis.cidm4385saru.changeexchain.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.wtamu.cis.cidm4385saru.changeexchain.database.ChangeExChDbSchema.*;
/**
 * Created by sarup on 4/1/2018.
 */


public class ChangeExChainBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "changeExChain.db";

    public ChangeExChainBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserTable.Cols.USERNAME + ", " +
                UserTable.Cols.PASSWORD+ ", " +
                UserTable.Cols.CURRENCYPREFERENCE +
                ")"
        );

        db.execSQL("create table " + UserSettingsTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserSettingsTable.Cols.TIMEFORMAT + ", " +
                UserSettingsTable.Cols.PRIORITYCURRENCYPREFERENCE + ", " +
                UserSettingsTable.Cols.COLORMODE + ", " +
                "FOREIGN KEY ("+ UserSettingsTable.Cols.USERNAME +") REFERENCES "+ UserTable.NAME +"(username)" +
                ")"
        );

        db.execSQL("create table " + PriceAlarmTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                PriceAlarmTable.Cols.CURRENCYCODE + ", " +
                PriceAlarmTable.Cols.PRICE + ", " +
                PriceAlarmTable.Cols.THRESHOLD + ", " +
                "FOREIGN KEY ("+ PriceAlarmTable.Cols.USERNAME +") REFERENCES "+ UserTable.NAME +"(username)" +
                ")"
        );


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
