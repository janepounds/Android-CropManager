package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropContact implements CropSpinnerItem, Serializable {
    String id;
    String userId;
    String type;
    String name;
    String businessName;
    String address;
    String phoneNumber;
    String email;
    String website;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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
            object.put("globalId",globalId);
            object.put("userId",userId);
            object.put("type",type);
            object.put("name",name);
            object.put("businessName",businessName);
            object.put("address",address);
            object.put("phoneNumber",phoneNumber);
            object.put("email",email);
            object.put("website",website);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropContact(JSONObject object) throws JSONException {
        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setType(object.getString("type"));
        setName(object.getString("name"));
        setBusinessName(object.getString("businessName"));
        setAddress(object.getString("address"));
        setPhoneNumber(object.getString("phoneNumber"));
        setEmail(object.getString("email"));
        setWebsite(object.getString("website"));
        setSyncStatus("yes");
    }
    public CropContact(){

    }
}
