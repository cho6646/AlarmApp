package com.jc_lab.alarmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    public static AlarmManager alarmManager;
    public static ArrayList<AlarmData> alarmDataArrayList;
    private int requestCode;
    private SharedPreferenceUtil sp;
    protected AlarmRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        sp = new SharedPreferenceUtil(this);
        alarmDataArrayList = sp.getAlarmList();
        requestCode = sp.getMaxRequestCode();

        RecyclerView recyclerView = findViewById(R.id.alarmRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AlarmRecyclerViewAdapter(this, alarmDataArrayList);
        recyclerView.setAdapter(adapter);


        ImageButton addAlarmButton = findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarmTime();
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void setAlarmTime()
    {
        Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND,0);
                String amOrPm = (hourOfDay>12) ? "PM" : "AM";
                requestCode = (requestCode+1)%Integer.MAX_VALUE;
                String alarmText = String.format("%02d:%02d %s", hourOfDay%12, minute, amOrPm);
                AlarmData newData = new AlarmData(alarmText, true, requestCode, hourOfDay, minute);
                alarmDataArrayList.add(newData);
                adapter.notifyDataSetChanged();

                Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
                intent.putExtra("alarmText", alarmText);
                intent.putExtra("requestCode", requestCode);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

        timePickerDialog.setMessage("Set Time For New Alarm");
        timePickerDialog.show();
    }

    protected void onStop()
    {
        super.onStop();
        sp.setSharedPreference(alarmDataArrayList, requestCode);
    }

}
