package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropSoilAnalysis implements Serializable {
    String userId;
    String id;
    String fieldId;
    String date;
    float ph;
    float organicMatter;
    String agronomist;
    float cost;
    String result;

    public CropSoilAnalysis(){
    }

    public CropSoilAnalysis( String fieldId,String date,String agronomist, float cost, String result){
        setFieldId(fieldId);
        setDate(date);
        setAgronomist(agronomist);
        setCost(cost);
        setResult(result);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public float getOrganicMatter() {
        return organicMatter;
    }

    public void setOrganicMatter(float organicMatter) {
        this.organicMatter = organicMatter;
    }

    public String getAgronomist() {
        return agronomist;
    }

    public void setAgronomist(String agronomist) {
        this.agronomist = agronomist;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "CropSoilAnalysis{" +
                "userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", cropId='" + fieldId + '\'' +
                ", date='" + date + '\'' +
                ", ph=" + ph +
                ", organicMatter=" + organicMatter +
                ", agronomist='" + agronomist + '\'' +
                ", cost=" + cost +
                ", result='" + result + '\'' +
                '}';
    }
}
