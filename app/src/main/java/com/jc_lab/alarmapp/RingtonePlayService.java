package com.jc_lab.alarmapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class RingtonePlayService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        String alarmText = intent.getExtras().getString("alarmText");
        int requestCode = intent.getExtras().getInt("requestCode");
        if(Build.VERSION.SDK_INT>=26)
        {
            String CHANNEL_ID = "Alarm";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "AlarmApp",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(null,null);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            startForeground(1, notification);
        }

        Intent intent1 = new Intent(this, AlarmActivity.class);
        intent1.putExtra("alarmText", alarmText);
        for(int i=0; i<MainActivity.alarmDataArrayList.size(); i++)
        {
            if(MainActivity.alarmDataArrayList.get(i).getRequestCode()==requestCode)
            {
                MainActivity.alarmDataArrayList.get(i).setSwitchOn(false);
            }
        }
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent1);
        Log.d("AlarmService", "Alarm");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) stopForeground(true);
        stopSelf();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("onDestroy activated", "Destroying Service");
    }
}
