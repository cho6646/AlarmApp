package com.jc_lab.alarmapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

//        String state = intent.getExtras().getString("state");

        Intent serviceIntent = new Intent(context, RingtonePlayService.class);

//        serviceIntent.putExtra("state", state);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            context.startForegroundService(serviceIntent);
        }else{
            context.startService(serviceIntent);
        }
    }
}
