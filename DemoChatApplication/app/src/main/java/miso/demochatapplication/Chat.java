package miso.demochatapplication;

import android.content.SharedPreferences;

/**
 * Created by Sonu on 30-Jul-17.
 */

public class Chat {
//    public String sender;
//    public String receiver;
    public static String currentuser;
    private  String sednerid;
    private String receiverid;
    private String message;
    private long timestamp;
    private int layoutid;



    public Chat()
    {

    }

    public String getSednerid() {
        return sednerid;
    }

    public void setSednerid(String sednerid) {
        this.sednerid = sednerid;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getLayoutid() {
        return layoutid;
    }

    public void setLayoutid(int layoutid) {
        this.layoutid = layoutid;
    }
}
