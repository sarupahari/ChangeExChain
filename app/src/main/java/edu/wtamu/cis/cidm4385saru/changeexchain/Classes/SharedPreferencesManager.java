package edu.wtamu.cis.cidm4385saru.changeexchain.Classes;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class SharedPreferencesManager {
    SharedPreferences mPref;
    SharedPreferences.Editor mEditor;
    Context mContext;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "change_exchain_preferences";

    //Preference Keys
    public static final String TIME_FORMAT = "timeFormat";
    public static final String CURRENCY_PREFERENCE = "currencyPreference";
    public static final String COLOR_MODE = "colorMode";

    public SharedPreferencesManager(Context context){
        this.mContext = context;
        mPref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPref.edit();
    }

    //Store Value
    public void storeValue(String key, String value){
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public String getStringValue(String key){
        return mPref.getString(key, null);
    }

    public void clearAllValues(){
        mEditor.clear();
        mEditor.commit();
    }

    public static void setUserSettingsPreferences(DataSnapshot ref, Context context){
        SharedPreferencesManager pm = new SharedPreferencesManager(context);
        pm.clearAllValues();
        pm.storeValue(SharedPreferencesManager.CURRENCY_PREFERENCE, ref.child("currencyPreference").getValue().toString());
        pm.storeValue(SharedPreferencesManager.TIME_FORMAT, ref.child("timeFormat").getValue().toString());
        pm.storeValue(SharedPreferencesManager.COLOR_MODE, ref.child("colorMode").getValue().toString());
    }

}
