package com.myfarmnow.myfarmcrop.models;

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class CropInventorySpray implements CropInventory, Serializable{
    String id;
    String dateOfPurchase;
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

    public String getDateOfPurchase() {
        return dateOfPurchase;
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


    public CropInventorySpray(){

    }
    public CropInventorySpray(JSONObject seedsJson) throws MissingValueException{
        try {
            this.setName(seedsJson.getString("name"));
            this.setType(seedsJson.getString("type"));
            this.setBatchNumber(seedsJson.getString("batchNumber"));
            this.setDateOfPurchase(seedsJson.getString(MyFarmDbHandlerSingleton.CROP_INVENTORY_SEEDS_DATE));
            this.setQuantity((float)seedsJson.getDouble("quantity"));
            this.setTotalConsumed((float)seedsJson.getDouble("totalConsumed"));
        }catch (JSONException e){
            throw new MissingValueException("Missing Key Variables in the JSON Object "+e.getMessage());
        }
        try {
            this.setId(seedsJson.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.setUsageUnits(seedsJson.getString("usageUnits"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
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
}
