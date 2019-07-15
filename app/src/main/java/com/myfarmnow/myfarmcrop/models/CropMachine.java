package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropMachine implements CropSpinnerItem, Serializable {

    String id="";
    String userId="";
    String name="";
    String brand="";
    String category="";
    String manufacturer="";
    String model="";
    String registrationNumber="";
    float quantity=0;
    String date="";
    String purchasedFrom="";
    String storageLocation="";
    float purchasePrice=0;


    public CropMachine(){

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPurchasedFrom() {
        return purchasedFrom;
    }

    public void setPurchasedFrom(String purchasedFrom) {
        this.purchasedFrom = purchasedFrom;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
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
            object.put("name",name);
            object.put("brand",brand);
            object.put("category",category);
            object.put("manufacturer",manufacturer);
            object.put("model",model);
            object.put("registrationNumber",registrationNumber);
            object.put("quantity",quantity);
            object.put("date",date);
            object.put("purchasedFrom",purchasedFrom);
            object.put("storageLocation",storageLocation);
            object.put("purchasePrice",purchasePrice);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropMachine(JSONObject object) throws JSONException {
       setGlobalId( object.getString("id"));
        setUserId( object.getString("userId"));
        setName( object.getString("name"));
        setBrand( object.getString("brand"));
        setCategory( object.getString("category"));
        setManufacturer( object.getString("manufacturer"));
        setModel( object.getString("model"));
        setRegistrationNumber( object.getString("registrationNumber"));
        setQuantity( (float)object.getDouble("quantity"));
        setDate( object.getString("date"));
        setPurchasedFrom( object.getString("purchasedFrom"));
        setStorageLocation( object.getString("storageLocation"));
        setPurchasePrice( (float)object.getDouble("date"));
        setSyncStatus( "yes");
    }



}
