package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropProduct implements CropSpinnerItem, Serializable {
    String id;
    String userId;
    String  name;
    String  type;
    String code;
    String  units;
    String  linkedAccount;
    float openingCost;
    float  openingQuantity;
    float  sellingPrice;
    float taxRate;
    String description;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getLinkedAccount() {
        return linkedAccount;
    }

    public void setLinkedAccount(String linkedAccount) {
        this.linkedAccount = linkedAccount;
    }

    public float getOpeningCost() {
        return openingCost;
    }

    public void setOpeningCost(float openingCost) {
        this.openingCost = openingCost;
    }

    public float getOpeningQuantity() {
        return openingQuantity;
    }

    public void setOpeningQuantity(float openingQuantity) {
        this.openingQuantity = openingQuantity;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString(){
        return name;
    }
}
