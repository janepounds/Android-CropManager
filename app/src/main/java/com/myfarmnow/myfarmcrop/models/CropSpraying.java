package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropSpraying implements Serializable {
    String date;
    String startTime;
    String endTime;
    String operator;
    float waterVolume;
    String waterCondition;
    String windDirection;
    String equipmentUsed;
    String sprayId;
    float rate;
    String treatmentReason;
    float cost;
    String id="";
    String userId="";
    String cropId;
    String sprayName;

    public String getSprayName() {
        return sprayName; //TODO change this to spray name after updating the db handler to send it
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
}
