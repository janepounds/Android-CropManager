package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropSoilAnalysis implements Serializable {
    String userId;
    String id;
    String fieldId;
    String date;
    float ph;
    float organicMatter;
    String agronomist;
    float cost;
    String result;
    String recurrence;
    String reminders;
    private float frequency;
    private String repeatUntil;
    private String daysBefore;

    public CropSoilAnalysis(){
    }

    public CropSoilAnalysis( String fieldId,String date,String agronomist, float cost, String result){
        setFieldId(fieldId);
        setDate(date);
        setAgronomist(agronomist);
        setCost(cost);
        setResult(result);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public float getOrganicMatter() {
        return organicMatter;
    }

    public void setOrganicMatter(float organicMatter) {
        this.organicMatter = organicMatter;
    }

    public String getAgronomist() {
        return agronomist;
    }

    public void setAgronomist(String agronomist) {
        this.agronomist = agronomist;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CropSoilAnalysis{" +
                "userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", cropId='" + fieldId + '\'' +
                ", date='" + date + '\'' +
                ", ph=" + ph +
                ", organicMatter=" + organicMatter +
                ", agronomist='" + agronomist + '\'' +
                ", cost=" + cost +
                ", result='" + result + '\'' +
                '}';
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
            object.put("fieldId",fieldId);
            object.put("date",date);
            object.put("ph",ph);
            object.put("organicMatter",organicMatter);
            object.put("agronomist",agronomist);
            object.put("cost",cost);
            object.put("result",result);
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
}
