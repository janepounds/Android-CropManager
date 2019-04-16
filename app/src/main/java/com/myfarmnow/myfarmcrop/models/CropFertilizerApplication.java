package com.myfarmnow.myfarmcrop.models;

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
    private float frequency;
    private String repeatUntil;
    private String daysBefore;

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

    public String getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(String daysBefore) {
        this.daysBefore = daysBefore;
    }
}
