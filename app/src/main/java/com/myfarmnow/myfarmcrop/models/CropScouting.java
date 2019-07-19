package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropScouting implements CropSpinnerItem, Serializable, CropActivity {
    String id;
    String userId;
    String cropId ;
    String date;
    String method;
    String infested;
    String infestationType="";
    String infestation="";
    String infestationLevel="";
    float cost=0;
    String remarks;
    String recurrence;
    String reminders;
    private float frequency;
    private String repeatUntil;
    private float daysBefore;


    @Override
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

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInfested() {
        return infested;
    }

    public void setInfested(String infested) {
        this.infested = infested;
    }

    public String getInfestationType() {
        return infestationType;
    }

    public void setInfestationType(String infestationType) {
        this.infestationType = infestationType;
    }

    public String getInfestation() {
        return infestation;
    }

    public void setInfestation(String infestation) {
        this.infestation = infestation;
    }

    public String getInfestationLevel() {
        return infestationLevel;
    }

    public void setInfestationLevel(String infestationLevel) {
        this.infestationLevel = infestationLevel;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public int getType() {
        return CropActivity.CROP_ACTIVITY_SCOUTING;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getReminders() {
        return reminders;
    }

    public void setReminders(String reminders) {
        this.reminders = reminders;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public String getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(String repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public float getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(float daysBefore) {
        this.daysBefore = daysBefore;
    }
    private String syncStatus="no";
    private String globalId;
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getGlobalId() {
        return globalId;
    }

    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("globalId",globalId);
            object.put("userId",userId);
            object.put("cropId",cropId);
            object.put("date",date);
            object.put("method",method);
            object.put("infested",infested);
            object.put("infestationType",infestationType);
            object.put("infestation",infestation);
            object.put("infestationLevel",infestationLevel);
            object.put("cost",cost);
            object.put("remarks",remarks);
            object.put("recurrence",recurrence);
            object.put("reminders",reminders);
            object.put("frequency",frequency);
            object.put("repeatUntil",repeatUntil);
            object.put("daysBefore",daysBefore);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropScouting(JSONObject object) throws JSONException {
        setGlobalId(object.getString("id"));
        setCropId(object.getString("cropId"));
        setUserId(object.getString("userId"));
        setDate(object.getString("date"));
        setMethod(object.getString("method"));
        setInfested(object.getString("infested"));
        setInfestationType(object.getString("infestationType"));
        setInfestation(object.getString("infestation"));
        setInfestationLevel(object.getString("infestationLevel"));
        setRemarks(object.getString("remarks"));
        setRecurrence(object.getString("recurrence"));
        setReminders(object.getString("reminders"));
        setCost((float)object.getDouble("cost"));
        setFrequency((float)object.getDouble("frequency"));
        setRepeatUntil(object.getString("repeatUntil"));
        setDaysBefore(Float.parseFloat(object.getString("daysBefore")));
        setSyncStatus( "yes");

    }
    public CropScouting(){

    }
}
