package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropFertilizerApplication implements Serializable,CropActivity {
    String date;
    String operator;
    String method;
    String reason;
    String fertilizerForm;
    String fertilizerId;
    String fertilizerName;
    float rate;
    float cost;
    String id;
    String userId;
    String cropId;
    String recurrence;
    String reminders;
    String units;
    private float frequency=1;
    private String repeatUntil;
    private float daysBefore=0;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getFertilizerForm() {
        return fertilizerForm;
    }

    public void setFertilizerForm(String fertilizerForm) {
        this.fertilizerForm = fertilizerForm;
    }

    public String getFertilizerId() {
        return fertilizerId;
    }

    public void setFertilizerId(String fertilizerId) {
        this.fertilizerId = fertilizerId;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
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

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getFertilizerName() {
        return fertilizerName;
    }

    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName;
    }

    @Override
    public int getType() {
        return CropActivity.CROP_ACTIVITY_FERTILIZER_APPLICATION;
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

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("globalId",globalId);
            object.put("date",date);
            object.put("operator",operator);
            object.put("method",method);
            object.put("reason",reason);
            object.put("fertilizerForm",fertilizerForm);
            object.put("fertilizerId",fertilizerId);
            object.put("fertilizerName",fertilizerName);
            object.put("rate",rate);
            object.put("cost",cost);
            object.put("id",id);
            object.put("userId",userId);
            object.put("cropId",cropId);
            object.put("recurrence",recurrence);
            object.put("reminders",reminders);
            object.put("frequency",frequency);
            object.put("repeatUntil",repeatUntil);
            object.put("daysBefore",daysBefore);
            object.put("units",units);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }
    public CropFertilizerApplication(JSONObject object) throws JSONException {

        setGlobalId(object.getString("id"));
        setDate(object.getString("date"));
        setOperator(object.getString("operator"));
        setMethod(object.getString("method"));
        setReason(object.getString("reason"));
        setFertilizerForm(object.getString("fertilizerForm"));
        setFertilizerId(object.getString("fertilizerId"));
        setFertilizerName(object.getString("fertilizerName"));
        setRate(Float.parseFloat(object.getString("rate")));
        setCost(Float.parseFloat(object.getString("cost")));
        setUserId(object.getString("userId"));
        setCropId(object.getString("cropId"));
        setRecurrence(object.getString("recurrence"));
        setReminders(object.getString("reminders"));
        setFrequency(Float.parseFloat(object.getString("frequency")));
        setRepeatUntil(object.getString("repeatUntil"));
        setDaysBefore(Float.parseFloat(object.getString("daysBefore")));
        setUnits(object.getString("units"));
        setSyncStatus("yes");

    }

    public CropFertilizerApplication(){

    }
}
