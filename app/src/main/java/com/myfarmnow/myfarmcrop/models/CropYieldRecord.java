package com.myfarmnow.myfarmcrop.models;

public class CropYieldRecord {
    String cropName;
    String fieldName;
    String variety;
    String croppingYear;
    String season;
    float totalCost;
    float revenue;
    float margin;
    float returnOnInvestment;
    private String cropId;

    public String getCropName() {
        return cropName;
    }

    public void setCropName(String cropName) {
        this.cropName = cropName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getCroppingYear() {
        return croppingYear;
    }

    public void setCroppingYear(String croppingYear) {
        this.croppingYear = croppingYear;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public float getMargin() {
        return revenue-totalCost;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    public float getReturnOnInvestment() {
        return (getMargin()/getTotalCost())*100;
    }

    public void setReturnOnInvestment(float returnOnInvestment) {
        this.returnOnInvestment = returnOnInvestment;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getCropId() {
        return cropId;
    }
}
