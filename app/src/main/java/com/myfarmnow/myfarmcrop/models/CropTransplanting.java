package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropTransplanting implements CropSpinnerItem, Serializable,CropActivity {
    String id="";
    String userId="";
    String cropId ="";
    String operationDate="";
    float totalSeedling=0;
    float seedlingPerHa=0;
    String varietyEarliness="";
    float cycleLength=0;
    String expectedHarvestingDate="";
    String units="";
    float expectedYield=0;
    float  expectedYieldPerHa=0;
    String operator="";
    float totalCost=0;
    String recurrence;
    String reminders;
    private float frequency;
    private String repeatUntil;
    private String daysBefore;

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

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public float getTotalSeedling() {
        return totalSeedling;
    }

    public void setTotalSeedling(float totalSeedling) {
        this.totalSeedling = totalSeedling;
    }

    public float getSeedlingPerHa() {
        return seedlingPerHa;
    }

    public void setSeedlingPerHa(float seedlingPerHa) {
        this.seedlingPerHa = seedlingPerHa;
    }

    public String getVarietyEarliness() {
        return varietyEarliness;
    }

    public void setVarietyEarliness(String varietyEarliness) {
        this.varietyEarliness = varietyEarliness;
    }

    public float getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(float cycleLength) {
        this.cycleLength = cycleLength;
    }

    public String getExpectedHarvestingDate() {
        return expectedHarvestingDate;
    }

    public void setExpectedHarvestingDate(String expectedHarvestingDate) {
        this.expectedHarvestingDate = expectedHarvestingDate;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public float getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(float expectedYield) {
        this.expectedYield = expectedYield;
    }

    public float getExpectedYieldPerHa() {
        return expectedYieldPerHa;
    }

    public void setExpectedYieldPerHa(float expectedYieldPerHa) {
        this.expectedYieldPerHa = expectedYieldPerHa;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public int getType() {
        return CropActivity.CROP_ACTIVITY_TRANSPLANTING;
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
