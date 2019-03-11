package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropFertilizerApplication implements Serializable {
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
        return fertilizerId; //TODO change this to fertilizer name after updating the db handler to send it
    }

    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName;
    }
}
