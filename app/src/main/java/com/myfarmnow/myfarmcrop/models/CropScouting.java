package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropScouting implements CropSpinnerItem, Serializable {
    String id="";
    String userId="";
    String cropId ="";
    String date="";
    String method="";
    String infested="";
    String infestationType="";
    String infestation="";
    String infestationLevel="";
    float cost=0;
    String remarks="";

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInfested() {
        return infested;
    }

    public void setInfested(String infested) {
        this.infested = infested;
    }

    public String getInfestationType() {
        return infestationType;
    }

    public void setInfestationType(String infestationType) {
        this.infestationType = infestationType;
    }

    public String getInfestation() {
        return infestation;
    }

    public void setInfestation(String infestation) {
        this.infestation = infestation;
    }

    public String getInfestationLevel() {
        return infestationLevel;
    }

    public void setInfestationLevel(String infestationLevel) {
        this.infestationLevel = infestationLevel;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
