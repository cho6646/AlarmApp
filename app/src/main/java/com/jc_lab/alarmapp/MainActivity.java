package com.jc_lab.alarmapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<AlarmData> alarmDataArrayList = new ArrayList<>();
        for(int i=0; i<10; i++)
        {
            alarmDataArrayList.add(new AlarmData("12:00 AM", true));
        }
        RecyclerView recyclerView = findViewById(R.id.alarmRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(alarmDataArrayList);
        recyclerView.setAdapter(adapter);
    }
}
