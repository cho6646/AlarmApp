package com.jc_lab.alarmapp;

public class AlarmData {
    String alarmText;
    boolean switchOn;

    public AlarmData(String alarmText, boolean switchOn)
    {
        this.alarmText = alarmText;
        this.switchOn = switchOn;
    }

    public String getAlarmText() {
        return alarmText;
    }

    public boolean getSwitchOn(){
        return switchOn;
    }
}
