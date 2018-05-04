package edu.wtamu.cis.cidm4385saru.changeexchain.Services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;

import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import edu.wtamu.cis.cidm4385saru.changeexchain.Activities.MainActivity;
import edu.wtamu.cis.cidm4385saru.changeexchain.R;
import edu.wtamu.cis.cidm4385saru.changeexchain.RESTApiCalls.CoinDeskApi;

public class AlarmCheckingService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            AlarmCheckingService.fetchPriceTask performBackgroundTask = new AlarmCheckingService.fetchPriceTask();
                            // PerformBackgroundTask this class is the class that extends AsynchTask
                            performBackgroundTask.execute();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 6 * 1000); //execute in every 50000 ms
    }

    private class fetchPriceTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void...params){

            SharedPreferences preferences = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);

            String test = preferences.getString("currencyPreference", "");



            return new CoinDeskApi().fetchCurrentPrice("USD");
        }

        @Override
        protected void onPostExecute(String currentPrice) {
            super.onPostExecute(currentPrice);

            SendNotification(currentPrice);
        }
    }

    private void SendNotification(String message){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, getString(R.string.channel_name))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("ChangeExChain Alarm Notification")
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        Random rand = new Random();
        notificationManager.notify(rand.nextInt(), mBuilder.build());
    }
}
