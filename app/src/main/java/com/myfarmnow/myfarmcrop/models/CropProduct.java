package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

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
    float quantityUsed;
    float quantityAdded;
    String description;


    public float getQuantityAdded() {
        return quantityAdded;
    }

    public void setQuantityAdded(float quantityAdded) {
        this.quantityAdded = quantityAdded;
    }

    public float getQuantityUsed() {
        return quantityUsed;
    }

    public void setQuantityUsed(float remainingQuantity) {
        this.quantityUsed = remainingQuantity;
    }

    public float computeStockAtHand(){
        return (openingQuantity +quantityAdded)- quantityUsed;
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
            object.put("type",type);
            object.put("code",code);
            object.put("units",units);
            object.put("linkedAccount",linkedAccount);
            object.put("openingCost",openingCost);
            object.put("openingQuantity",openingQuantity);
            object.put("sellingPrice",sellingPrice);
            object.put("taxRate",taxRate);
            object.put("description",description);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropProduct(JSONObject object) throws JSONException{
        setGlobalId( object.getString("id"));
        setUserId( object.getString("userId"));
        setSyncStatus( "yes");
        setName( object.getString("name"));
        setType( object.getString("type"));
        setCode( object.getString("code"));
        setUnits( object.getString("units"));
        setLinkedAccount( object.getString("linkedAccount"));
        setDescription( object.getString("description"));
        setOpeningCost((float)object.getDouble("openingCost"));
        setOpeningQuantity((float)object.getDouble("openingQuantity"));
        setSellingPrice((float)object.getDouble("sellingPrice"));
        setTaxRate((float)object.getDouble("taxRate"));
    }

    public CropProduct(){

    }
}
