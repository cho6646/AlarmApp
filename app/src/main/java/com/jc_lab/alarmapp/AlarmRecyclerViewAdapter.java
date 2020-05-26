package com.jc_lab.alarmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder>
{
    private ArrayList<String> mAlarmData = null;

    public class ViewHolder extends  RecyclerView.ViewHolder
    {
        TextView mAlarmTextView;
        SwitchCompat mAlarmSwitch;
        ViewHolder(View itemView)
        {
            super(itemView);
            mAlarmTextView.findViewById(R.id.recyclerViewItemText);
            mAlarmSwitch.findViewById(R.id.recyclerViewItemSwitch);
        }
    }

    AlarmRecyclerViewAdapter(ArrayList<String> list)
    {
        this.mAlarmData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alarm_recycler_view_item, parent, false);
        AlarmRecyclerViewAdapter.ViewHolder vh = new AlarmRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmRecyclerViewAdapter.ViewHolder holder, int position) {
        String str = mAlarmData.get(position);
        holder.mAlarmTextView.setText(str);
    }

    @Override
    public int getItemCount() {
        return mAlarmData.size();
    }
}
