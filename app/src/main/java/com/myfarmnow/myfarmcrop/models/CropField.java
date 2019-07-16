package com.myfarmnow.myfarmcrop.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropField  implements CropSpinnerItem,Serializable{
    String id="";
    String userId="";
    String fieldName="";
    String soilCategory="";
    String fieldType;
    String layoutType;
    String status;
    String soilType="";
    String watercourse="";
    float totalArea=0;
    float croppableArea=0;
    String units="";




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
        return getFieldName();
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String syncStatus="no";
    private String globalId;
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;

    }

    public String getGlobalId() {
        return globalId;
    }

    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("fieldName",fieldName);
            object.put("soilCategory",soilCategory);
            object.put("fieldType",fieldType);
            object.put("layoutType",layoutType);
            object.put("status",status);
            object.put("soilType",soilType);
            object.put("watercourse",watercourse);
            object.put("totalArea",totalArea);
            object.put("croppableArea",croppableArea);
            object.put("globalId",globalId);
            object.put("units",units);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropField(JSONObject object) throws JSONException {

        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setFieldName(object.getString("fieldName"));
        setSoilCategory(object.getString("soilCategory"));
        setFieldType(object.getString("fieldType"));
        setLayoutType(object.getString("layoutType"));
        setStatus(object.getString("status"));
        setSoilType(object.getString("soilType"));
        setWatercourse(object.getString("watercourse"));
        setTotalArea(Float.parseFloat(object.getString("totalArea")));
        setCroppableArea(Float.parseFloat(object.getString("croppableArea")));
        setUnits(object.getString("units"));
        setSyncStatus("yes");

    }
    public CropField(){

    }
}

