package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropMachineTask implements CropSpinnerItem, Serializable {
    String userId;
    String id;
    String employeeId;
    String machineId;

    String title;

    String status;
    String description;
    String recurrence;
    String reminders;
    String employeeName;
    private String cropName;
    private String endDate;
    private String startDate;
    private float frequency;
    private String repeatUntil;
    private float cost;
    private String daysBefore;


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

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
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
            object.put("employeeId",employeeId);
            object.put("machineId",machineId);
            object.put("title",title);
            object.put("status",status);
            object.put("description",description);
            object.put("recurrence",recurrence);
            object.put("reminders",reminders);
            object.put("responsible",employeeName);
            object.put("cropName",cropName);
            object.put("endDate",endDate);
            object.put("startDate",startDate);
            object.put("frequency",frequency);
            object.put("repeatUntil",repeatUntil);
            object.put("cost",cost);
            object.put("daysBefore",daysBefore);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }
    public  CropMachineTask(JSONObject object) throws JSONException{

        setGlobalId(object.getString("id"));
        setMachineId(object.getString("machineId"));
        setStartDate(object.getString("startDate"));
        setEndDate(object.getString("endDate"));
        setTitle(object.getString("title"));
        setFrequency((float)object.getDouble("frequency"));
        setRepeatUntil(object.getString("repeatUntil"));
        setRecurrence(object.getString("recurrence"));
        setReminders(object.getString("reminders"));
        setDaysBefore(object.getString("daysBefore"));
        setEmployeeName(object.getString("responsible"));
        setEmployeeId(object.getString("employeeId"));
        setCost((float)object.getDouble("cost"));
        setDescription(object.getString("description"));
        setStatus(object.getString("status"));
        setCropName(object.getString("cropName"));
    }

    public  CropMachineTask(){

    }
}


