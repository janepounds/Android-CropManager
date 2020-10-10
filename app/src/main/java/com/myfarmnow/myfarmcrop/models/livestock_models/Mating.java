package com.myfarmnow.myfarmcrop.models.livestock_models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Mating implements LivestockSpinnerItem, Serializable {
    private String id;
    private String userId;
    private String matingDate;
    private String maleName;
    private String femaleName;
    private String method;
    private int gestationPeriod = 0;
    private int deliveryAlertDaysBefore = 0;
    private String notes;
    private String syncStatus = "no";
    private String globalId;
    private String animalType;

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

    public int getGestationPeriod() {
        return gestationPeriod;
    }

    public void setGestationPeriod(int gestationPeriod) {
        this.gestationPeriod = gestationPeriod;
    }

    public int getDeliveryAlertDaysBefore() {
        return deliveryAlertDaysBefore;
    }

    public void setDeliveryAlertDaysBefore(int deliveryAlertDaysBefore) {
        this.deliveryAlertDaysBefore = deliveryAlertDaysBefore;
    }

    public String getNotes() {
        return this.notes;
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

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public Mating() {
        // Empty constructor required
    }

    public Mating(JSONObject object) throws JSONException {
        setUserId(object.getString("userId"));
        setGlobalId(object.getString("id"));
        setMatingDate(object.getString("matingDate"));
        setMaleName(object.getString("maleName"));
        setFemaleName(object.getString("femaleName"));
        setMethod(object.getString("method"));
        setGestationPeriod((int) object.getInt("gestationPeriod"));
        setDeliveryAlertDaysBefore((int) object.getInt("deliveryAlertDaysBefore"));
        setNotes(object.getString("notes"));
        setAnimalType(object.getString("animalType"));
        setSyncStatus("yes");
    }

    public JSONObject toJson() {
        JSONObject object = new JSONObject();

        try {
            object.put("id", id);
            object.put("userId", userId);
            object.put("matingDate", matingDate);
            object.put("maleName", maleName);
            object.put("femaleName", femaleName);
            object.put("method", method);
            object.put("gestationPeriod", gestationPeriod);
            object.put("deliveryAlertDaysBefore", deliveryAlertDaysBefore);
            object.put("notes", notes);
            object.put("syncStatus", syncStatus);
            object.put("globalId", globalId);
            object.put("animalType", animalType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
