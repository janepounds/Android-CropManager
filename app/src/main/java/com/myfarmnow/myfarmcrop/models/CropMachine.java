package com.myfarmnow.myfarmcrop.models;

public class CropMachine {

    String id="";
    String userId="";
    String name="";
    String brand="";
    String category="";
    String manufacturer="";
    String model="";
    float registrationNumber=0;
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

    public float getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(float registrationNumber) {
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

    public void setStorageLocation(String storageLoacation) {
        this.storageLocation = storageLoacation;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
