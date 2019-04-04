package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropTransplanting implements CropSpinnerItem, Serializable {
    String id="";
    String userId="";
    String fieldId="";
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

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
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
}
