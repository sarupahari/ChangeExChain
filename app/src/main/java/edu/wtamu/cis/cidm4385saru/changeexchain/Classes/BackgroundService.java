package edu.wtamu.cis.cidm4385saru.changeexchain.Classes;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

public class BackgroundService extends IntentService {
    private static final String TAG = "BackgroundService";

    public static Intent newIntent (Context context){
        return new Intent(context, BackgroundService.class);
    }

    public BackgroundService(){
        super("TAG");
    }
    @Override
    protected void onHandleIntent (Intent serviceIntent){

    }
}
