package com.jc_lab.alarmapp;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder>
{
    private ArrayList<AlarmData> mAlarmData = null;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener
    {
        TextView mAlarmTextView;
        SwitchCompat mAlarmSwitch;
        int requestCode;

        ViewHolder(View itemView)
        {
            super(itemView);
            mAlarmTextView = itemView.findViewById(R.id.recyclerViewItemText);
            mAlarmSwitch = itemView.findViewById(R.id.recyclerViewItemSwitch);
            requestCode = 0;
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            showDeleteDialog(getAdapterPosition(), requestCode);
            return true;
        }
    }

    AlarmRecyclerViewAdapter(Context context, ArrayList<AlarmData> list)
    {
        mContext = context;
        this.mAlarmData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.alarm_recycler_view_item, parent, false);
        AlarmRecyclerViewAdapter.ViewHolder vh = new AlarmRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmRecyclerViewAdapter.ViewHolder holder, final int position) {
        String alarmText = mAlarmData.get(position).getAlarmText();
        boolean alarmToggle = mAlarmData.get(position).getSwitchOn();
        final int requestCode = mAlarmData.get(position).getRequestCode();

        holder.mAlarmTextView.setText(alarmText);
        holder.mAlarmSwitch.setChecked(alarmToggle);
        holder.mAlarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked)
                {
                    Intent intent = new Intent(mContext, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    MainActivity.alarmManager.cancel(pendingIntent);
                }
                else
                {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(System.currentTimeMillis());
                    cal.set(Calendar.HOUR_OF_DAY,mAlarmData.get(position).getHourOfDay());
                    cal.set(Calendar.MINUTE, mAlarmData.get(position).getMinute());
                    cal.set(Calendar.SECOND,0);
                    Intent intent = new Intent(mContext, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    MainActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                }
            }
        });
        holder.requestCode = requestCode;
    }

    @Override
    public int getItemCount() {
        return mAlarmData.size();
    }

    private void showDeleteDialog(final int pos, final int requestCode)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Delete");
        builder.setMessage("Delete This Alarm?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAlarmData.remove(pos);
                notifyItemRemoved(pos);
                notifyItemChanged(pos, mAlarmData.size());
                Intent intent = new Intent(mContext, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                MainActivity.alarmManager.cancel(pendingIntent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
