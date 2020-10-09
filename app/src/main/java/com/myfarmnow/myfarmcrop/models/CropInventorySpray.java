package com.myfarmnow.myfarmcrop.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class CropInventorySpray implements CropInventory, Serializable,CropSpinnerItem{
    String id;
    String purchaseDate;
    String name;
    String batchNumber;
    float quantity;
    float totalConsumed;
    String usageUnits ="Ltr";
    String type;
    private String userId;
    private float cost;
    private String supplier;
    String expiryDate;
    int harvestInterval;
    String activeIngredients;


    public String getId() {
        return id;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getTotalConsumed() {
        return totalConsumed;
    }






    public void setUsageUnits(String usageUnits) {
        if(usageUnits != null){
            this.usageUnits = usageUnits;
        }else{
            this.usageUnits ="Ltr";
        }

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setTotalConsumed(float totalConsumed) {
        this.totalConsumed = totalConsumed;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getInventoryType() {
        return CropInventory.CONST_SPRAY_INVENTORY;
    }



    @Override
    public String getBatchNumber() {
        return batchNumber;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getInitialQuantity() {
        return quantity;
    }

    @Override
    public float getAmountConsumed() {
        return totalConsumed;
    }

    @Override
    public float calculateAmountLeft() {
        return quantity-totalConsumed;
    }

    @Override
    public String getUsageUnits() {
        return usageUnits;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getHarvestInterval() {
        return harvestInterval;
    }

    public void setHarvestInterval(int harvestInterval) {
        this.harvestInterval = harvestInterval;
    }

    public String getActiveIngredients() {
        return activeIngredients;
    }

    public void setActiveIngredients(String activeIngredients) {
        this.activeIngredients = activeIngredients;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getUnits() {
        return usageUnits;
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

//            Log.d("GLOBAL ID",globalId);

            object.put("globalId",globalId);
            object.put("purchaseDate", purchaseDate);
            object.put("sprayName",name);
            object.put("batchNumber",batchNumber);
            object.put("quantity",quantity);
            object.put("totalConsumed",totalConsumed);
            object.put("usageUnits",usageUnits);
            object.put("sprayType",type);
            object.put("userId",userId);
            object.put("cost",cost);
            object.put("supplier",supplier);
            object.put("expiryDate",expiryDate);
            object.put("harvestInterval",harvestInterval);
            object.put("activeIngredients",activeIngredients);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropInventorySpray(JSONObject object) throws JSONException {

        setUserId(object.getString("userId"));
        setGlobalId(object.getString("id"));
        setPurchaseDate(object.getString("purchaseDate"));
        setName(object.getString("sprayName"));
        setBatchNumber(object.getString("batchNumber"));
        setQuantity(Float.parseFloat(object.getString("quantity")));
//        setTotalConsumed(Float.parseFloat(object.getString("totalConsumed")));
        setUsageUnits(object.getString("usageUnits"));
        setType(object.getString("sprayType"));
        setCost(Float.parseFloat(object.getString("cost")));
        setSupplier(object.getString("supplier"));
        setExpiryDate(object.getString("expiryDate"));
        setHarvestInterval(Integer.parseInt(object.getString("harvestInterval")));
        setActiveIngredients(object.getString("activeIngredients"));
        setSyncStatus("yes");
    }

    public CropInventorySpray(){

    }
}
