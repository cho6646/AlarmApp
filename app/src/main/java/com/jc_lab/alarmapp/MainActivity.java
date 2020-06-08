package com.jc_lab.alarmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public AlarmFragment alarmFragment;
    public AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmFragment = new AlarmFragment();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        ArrayList<AlarmData> alarmDataArrayList = new ArrayList<>();

        for(int i=0; i<10; i++)
        {
            alarmDataArrayList.add(new AlarmData("12:00 AM", true));
        }

        RecyclerView recyclerView = findViewById(R.id.alarmRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(alarmDataArrayList);
        recyclerView.setAdapter(adapter);


        ImageButton addAlarmButton = findViewById(R.id.addAlarmButton);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().add(R.id.alarmSetterFragment, alarmFragment).commit();
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    public void closeFragment()
    {
        getSupportFragmentManager().beginTransaction().remove(alarmFragment).commit();
    }

    public void cancelAlarm()
    {
        Intent intent = new Intent(this, AlarmReceiver.class);
//        intent.putExtra("state", "alarm off");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
//        sendBroadcast(intent);
    }
}
