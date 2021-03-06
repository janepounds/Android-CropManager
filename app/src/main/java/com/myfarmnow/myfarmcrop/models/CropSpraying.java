package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropSpraying implements Serializable,CropActivity {
    String date;
    String startTime;
    String endTime;
    String operator;
    float waterVolume;
    String waterCondition;
    String windDirection;
    String equipmentUsed;
    String sprayType;
    String sprayId;
    float rate;
    String treatmentReason;
    float cost;
    String id="";
    String userId="";
    String cropId;
    String sprayName;
    String recurrence;
    String reminders;
    String usageUnits;
    private float frequency = 1;
    private String repeatUntil;
    private float daysBefore=0;

    public String getSprayName() {
        return sprayName;
    }

    public void setSprayName(String sprayName) {
        this.sprayName = sprayName;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public float getWaterVolume() {
        return waterVolume;
    }

    public void setWaterVolume(float waterVolume) {
        this.waterVolume = waterVolume;
    }

    public String getWaterCondition() {
        return waterCondition;
    }

    public void setWaterCondition(String waterCondition) {
        this.waterCondition = waterCondition;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getEquipmentUsed() {
        return equipmentUsed;
    }

    public void setEquipmentUsed(String equipmentUsed) {
        this.equipmentUsed = equipmentUsed;
    }

    public String getSprayId() {
        return sprayId;
    }

    public void setSprayId(String sprayId) {
        this.sprayId = sprayId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getTreatmentReason() {
        return treatmentReason;
    }

    public void setTreatmentReason(String treatmentReason) {
        this.treatmentReason = treatmentReason;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

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

    @Override
    public int getType() {
        return CropActivity.CROP_ACTIVITY_SPRAYING;
    }
    public String getSprayType() {
        return this.sprayType;
    }
    public void setSprayType(String sprayType) {
        this.sprayType=sprayType;
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
            object.put("date",date);
            object.put("startTime",startTime);
            object.put("endTime",endTime);
            object.put("operator",operator);
            object.put("waterVolume",waterVolume);
            object.put("waterCondition",waterCondition);
            object.put("windDirection",windDirection);
            object.put("equipmentUsed",equipmentUsed);
            object.put("sprayId",sprayId);
            object.put("rate",rate);
            object.put("treatmentReason",treatmentReason);
            object.put("cost",cost);
            object.put("userId",userId);
            object.put("cropId",cropId);
            object.put("sprayName",sprayName);
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

    public CropSpraying(JSONObject object) throws JSONException {
        setGlobalId(object.getString("id"));
        setCropId(object.getString("cropId"));
        setUserId(object.getString("userId"));
        setDate(object.getString("date"));
        setStartTime(object.getString("startTime"));
        setEndTime(object.getString("endTime"));
        setOperator(object.getString("operator"));
        setWaterCondition(object.getString("waterCondition"));
        setWaterVolume((float)object.getDouble("waterVolume"));
        setWindDirection(object.getString("windDirection"));
        setEquipmentUsed(object.getString("equipmentUsed"));
        setSprayId(object.getString("sprayId"));
        setRate((float)object.getDouble("rate"));
        setTreatmentReason(object.getString("treatmentReason"));
//        setSprayName(object.getString("sprayName"));
        setRecurrence(object.getString("recurrence"));
        setReminders(object.getString("reminders"));
        setCost((float)object.getDouble("cost"));
        setFrequency((float)object.getDouble("frequency"));
        setRepeatUntil(object.getString("repeatUntil"));
        setDaysBefore((float)object.getDouble("daysBefore"));
        setSyncStatus( "yes");

    }

    public CropSpraying(){

    }

    public void setUnits(String units) {
       this.usageUnits=units;
    }

    public String getUsageUnits() {
        return usageUnits;
    }
}
