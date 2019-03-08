package com.myfarmnow.myfarmcrop.models;

public class CropField {
    String id="";
    String userId="";
    String fieldName="";
    String soilCategory="";
    String soilType="";
    String watercourse="";
    float totalArea=0;
    float croppableArea=0;
    String units="";

    public CropField(){

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

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getSoilCategory() {
        return soilCategory;
    }

    public void setSoilCategory(String soilCategory) {
        this.soilCategory = soilCategory;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getWatercourse() {
        return watercourse;
    }

    public void setWatercourse(String watercourse) {
        this.watercourse = watercourse;
    }

    public float getTotalArea() {
        return totalArea;
    }

    public void setTotalArea(float totalArea) {
        this.totalArea = totalArea;
    }

    public float getCroppableArea() {
        return croppableArea;
    }

    public void setCroppableArea(float croppableArea) {
        this.croppableArea = croppableArea;
    }
    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }


    @Override
    public String toString() {
        return "CropField{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", fieldName='" + fieldName + '\'' +
                ", soilCategory='" + soilCategory + '\'' +
                ", soilType='" + soilType + '\'' +
                ", watercourse='" + watercourse + '\'' +
                ", totalArea=" + totalArea +
                ", croppableArea=" + croppableArea +
                ", units=" + units +
                '}';
    }


}

