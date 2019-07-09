package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropTask implements CropSpinnerItem, Serializable {
    String userId="";
    String id="";
    String employeeId="";
    String cropId="";
    String date="";
    String title="";
    String type="";
    String status="";
    String description="";
    String recurrence="";
    String reminders="";
    String employeeName;
    private float frequency;
    private String repeatUntil;
    private String daysBefore;
    private String cropName;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return getEmployeeName();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getCropName() {
        return cropName;
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

    public String getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(String daysBefore) {
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
            object.put("employeeId",employeeId);
            object.put("cropId",cropId);
            object.put("date",date);
            object.put("title",title);
            object.put("type",type);
            object.put("status",status);
            object.put("description",description);
            object.put("employeeName",employeeName);
            object.put("recurrence",recurrence);
            object.put("reminders",reminders);
            object.put("frequency",frequency);
            object.put("repeatUntil",repeatUntil);
            object.put("daysBefore",daysBefore);
            object.put("cropName",cropName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropTask(JSONObject object) throws JSONException {
        setGlobalId(object.getString("id"));
        setCropId(object.getString("cropId"));
        setUserId(object.getString("userId"));
        setDate(object.getString("date"));
        setEmployeeId(object.getString("employeeId"));
        setEmployeeName(object.getString("employeeName"));
        setTitle(object.getString("title"));
        setType(object.getString("type"));
        setStatus(object.getString("status"));
        setDescription(object.getString("description"));
        setRecurrence(object.getString("recurrence"));
        setReminders(object.getString("reminders"));
        //setCost((float)object.getDouble("cost"));
        setFrequency((float)object.getDouble("frequency"));
        setRepeatUntil(object.getString("repeatUntil"));
        setDaysBefore(object.getString("daysBefore"));
        setSyncStatus( "yes");

    }
}

