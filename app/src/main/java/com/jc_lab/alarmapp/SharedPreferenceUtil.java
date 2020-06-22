package com.jc_lab.alarmapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class SharedPreferenceUtil {
//    private SharedPreferences sp;
    private Context mContext;
    private static final String XML_FILE_NAME = "alarm_list";

    public SharedPreferenceUtil(Context mContext)
    {
        this.mContext = mContext;
//        sp = mContext.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
    }

    public ArrayList<AlarmData> getAlarmList()
    {
        SharedPreferences sp = mContext.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        ArrayList<AlarmData> returnList = new ArrayList<>();
        try {
            String json = sp.getString("alarmData", "");
            if(json.equals("")) return returnList;
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject b = jsonArray.getJSONObject(i);
                if(b==null) continue;
                String mAlarmText = b.getString("alarmText");
                boolean mSwitchOn = b.getBoolean("switchOn");
                int mRequestCode = b.getInt("requestCode");
                int hourOfDay = b.getInt("hour");
                int minute = b.getInt("minute");
                returnList.add(new AlarmData(mAlarmText, mSwitchOn, mRequestCode, hourOfDay, minute));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return returnList;
    }

    public int getMaxRequestCode()
    {
        SharedPreferences sp = mContext.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        return sp.getInt("maxRequestCode", 0);
    }

    public void setSharedPreference(ArrayList<AlarmData> ls, int maxRequestCode)
    {
        SharedPreferences sp = mContext.getSharedPreferences(XML_FILE_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if(ls == null || ls.size() == 0)
        {
            editor.putString("alarmData", "");
            editor.putInt("maxRequestCode", 0);
        }
        JSONArray jsonArray = new JSONArray();
        Iterator iter = ls.iterator();
        try {
            while (iter.hasNext()) {
                AlarmData data = (AlarmData) iter.next();
                JSONObject json = new JSONObject();
                json.put("alarmText", data.getAlarmText());
                json.put("switchOn", data.getSwitchOn());
                json.put("requestCode", data.getRequestCode());
                json.put("hour", data.getHourOfDay());
                json.put("minute", data.getMinute());
                jsonArray.put(json);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        editor.putString("alarmData", jsonArray.toString());
        editor.putInt("maxRequestCode", maxRequestCode);
        editor.commit();
    }
}
