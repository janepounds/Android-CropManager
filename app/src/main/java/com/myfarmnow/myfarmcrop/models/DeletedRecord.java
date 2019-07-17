package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

public class DeletedRecord {
    private String id;
    private String type;
    private String date;
    private String syncStatus;


    public DeletedRecord(){

    }
    public DeletedRecord(String id, String type, String date, String syncStatus) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.syncStatus = syncStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("date",date);
            object.put("type",type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

}
