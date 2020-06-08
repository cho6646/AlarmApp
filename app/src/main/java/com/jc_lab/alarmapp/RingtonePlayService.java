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

//    MediaPlayer mediaPlayer;
//    Ringtone ringtone;
//    int startId;
//    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
//        String state = intent.getExtras().getString("state");
//        assert state != null;
//        switch (state)
//        {
//            case "alarm on":
//                startId = 1;
//                break;
//            case "alarm off":
//                startId = 0;
//                break;
//            default:
//                startId = 0;
//                break;
//        }
//
//        if(!this.isRunning && startId == 1)
//        {
////            mediaPlayer = MediaPlayer.create(this,R.raw.ouu); // 여기 음악 넣어야 함
//            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//            mediaPlayer = MediaPlayer.create(getApplicationContext(), notification);
//            mediaPlayer.start();
////            ringtone = RingtoneManager.getRingtone(getApplicationContext(), notification);
////            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P)
////            {
////                ringtone.setVolume(1.0f);
////                ringtone.setLooping(true);
////            }
////            ringtone.play();
//
//            this.isRunning = true;
//            this.startId = 0;
//        }
//        else if(this.isRunning && startId == 0)
//        {
////            ringtone.stop();
//            mediaPlayer.stop();
//            mediaPlayer.reset();
//            mediaPlayer.release();
//
//            this.isRunning = false;
//            this.startId = 0;
//            stopSelf();
//        }
//        return START_NOT_STICKY;
        if(Build.VERSION.SDK_INT>=26)
        {
            String CHANNEL_ID = "Alarm";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "AlarmApp",
                    NotificationManager.IMPORTANCE_NONE);
            channel.setSound(null,null);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            startForeground(1, notification);
        }

        Intent intent1 = new Intent(this, AlarmActivity.class);
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
