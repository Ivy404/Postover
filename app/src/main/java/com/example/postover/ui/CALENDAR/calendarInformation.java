package com.example.postover.ui.CALENDAR;

public class calendarInformation {
    String mainInfo,subInfo;

    public calendarInformation(String mainInfo, String subInfo) {
        this.mainInfo = mainInfo;
        this.subInfo = subInfo;
    }
    public calendarInformation(){}

    public String getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(String mainInfo) {
        this.mainInfo = mainInfo;
    }

    public String getSubInfo() {
        return subInfo;
    }

    public void setSubInfo(String subInfo) {
        this.subInfo = subInfo;
    }
}
