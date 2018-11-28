package com.bloodapp.Model;

public class NotificationModel {

    private String msguid, buid;
    private String buildingAddress;
    private String msgTitle, msgBody, msgDate;
    private boolean checked;

    public NotificationModel() {

    }

    //simple constructor
    public NotificationModel(String title, String msg, String date) {
        this.msgTitle = title;
        this.msgBody  = msg;
        this.msgDate = date;
    }

    //mock constructor
    public NotificationModel(String title, String msg, String date, boolean checked) {
        this.msgTitle = title;
        this.msgBody  = msg;
        this.msgDate = date;
        this.checked = checked;
    }

    //complete constructor
    public NotificationModel(String uid, String buid, String address, String title, String msg, String date, boolean checked) {
        this.msguid = uid;
        this.buid = buid;
        this.buildingAddress = address;
        this.msgTitle = title;
        this.msgBody  = msg;
        this.msgDate = date;
        this.checked = checked;
    }

    public String getMsguid() {
        return msguid;
    }

    public void setMsguid(String msguid) {
        this.msguid = msguid;
    }

    public String getBuid() {
        return buid;
    }

    public void setBuid(String buid) {
        this.buid = buid;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
