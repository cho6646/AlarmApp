package com.jc_lab.alarmapp;

public class AlarmData {
    private String alarmText;
    private int hourOfDay;
    private int minute;
    private boolean switchOn;
    private int requestCode;

    public AlarmData(String alarmText, boolean switchOn, int requestCode, int hourOfDay, int minute)
    {
        this.alarmText = alarmText;
        this.switchOn = switchOn;
        this.requestCode = requestCode;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }

    public String getAlarmText() { return alarmText; }

    public boolean getSwitchOn(){
        return switchOn;
    }

    public int getRequestCode() { return requestCode; }

    public int getHourOfDay() { return hourOfDay; }

    public int getMinute() { return minute; }

    public void setSwitchOn(boolean switchOn) { this.switchOn = switchOn; }
}
