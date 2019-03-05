package com.myfarmnow.myfarmcrop.models;

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

import org.json.JSONException;
import org.json.JSONObject;



public class CropInventorySeeds implements CropInventory{
    String id;
    String dateOfPurchase;
    String name;
    String batchNumber;
    float quantity;
    float totalConsumed;
    String usageUnits ="Ltr";
    String type;
    String variety;
    String dressing;
    String tgw;
    private String userId;
    private String cost;
    private String supplier;

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

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getDressing() {
        return dressing;
    }

    public void setDressing(String dressing) {
        this.dressing = dressing;
    }

    public String getTgw() {
        return tgw;
    }

    public void setTgw(String tgw) {
        this.tgw = tgw;
    }



    public void setUsageUnits(String usageUnits) {
        if(usageUnits != null){
            this.usageUnits = usageUnits;
        }else{
            this.usageUnits ="Ltr";
        }

    }


    public  CropInventorySeeds(){

    }
    public CropInventorySeeds(JSONObject seedsJson) throws MissingValueException{
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
        return CropInventory.CONST_SEEDS_INVENTORY;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
