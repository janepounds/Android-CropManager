package com.myfarmnow.myfarmcrop.models.livestock_models;

import org.json.JSONException;
import org.json.JSONObject;

public class Mating {
    private String id;
    private String userId;
    private String matingDate;
    private String maleName;
    private String femaleName;
    private String method;
    private float gestationPeriod = 0;
    private float deliveryAlertDaysBefore =0;
    private String notes;
    private String syncStatus="no";
    private String globalId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMatingDate() {
        return matingDate;
    }

    public void setMatingDate(String matingDate) {
        this.matingDate = matingDate;
    }

    public String getMaleName() {
        return maleName;
    }

    public void setMaleName(String maleName) {
        this.maleName = maleName;
    }

    public String getFemaleName() {
        return femaleName;
    }

    public void setFemaleName(String femaleName) {
        this.femaleName = femaleName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public float getGestationPeriod() {
        return gestationPeriod;
    }

    public void setGestationPeriod(float gestationPeriod) {
        this.gestationPeriod = gestationPeriod;
    }

    public float getDeliveryAlertDaysBefore() {
        return deliveryAlertDaysBefore;
    }

    public void setDeliveryAlertDaysBefore(float deliveryAlertDaysBefore) {
        this.deliveryAlertDaysBefore = deliveryAlertDaysBefore;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public Mating(){

    }

    public void computeDeliveryDate(){


    }

    public Mating(JSONObject object) throws JSONException{
        setUserId(object.getString("userId"));
        setGlobalId(object.getString("id"));
        setMatingDate(object.getString("matingDate"));
        setMaleName(object.getString("maleName"));
        setFemaleName(object.getString("femaleName"));
        setMethod(object.getString("method"));
        setGestationPeriod((float)object.getDouble("gestationPeriod"));
        setDeliveryAlertDaysBefore((float)object.getDouble("deliveryAlertDaysBefore"));
        setNotes(object.getString("notes"));
        setSyncStatus("yes");

    }

    public JSONObject toJson(){
        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("userId",userId);
            object.put("matingDate",matingDate);
            object.put("maleName",maleName);
            object.put("femaleName",femaleName);
            object.put("method",method);
            object.put("gestationPeriod",gestationPeriod);
            object.put("deliveryAlertDaysBefore",deliveryAlertDaysBefore);
            object.put("notes",notes);
            object.put("syncStatus",syncStatus);
            object.put("globalId",globalId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }






}
