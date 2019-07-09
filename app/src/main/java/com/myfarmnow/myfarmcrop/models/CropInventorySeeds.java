package com.myfarmnow.myfarmcrop.models;

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


public class CropInventorySeeds implements CropInventory,Serializable,CropSpinnerItem{
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
    private float cost;
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

    @Override
    public String toString() {
        return getName();
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
            object.put("dateOfPurchase",dateOfPurchase);
            object.put("name",name);
            object.put("batchNumber",batchNumber);
            object.put("quantity",quantity);
            object.put("totalConsumed",totalConsumed);
            object.put("usageUnits",usageUnits);
            object.put("type",type);
            object.put("variety",variety);
            object.put("dressing",dressing);
            object.put("tgw",tgw);
            object.put("userId",userId);
            object.put("cost",cost);
            object.put("supplier",supplier);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public  CropInventorySeeds(JSONObject object) throws JSONException {

        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setDateOfPurchase(object.getString("dateOfPurchase"));
        setName(object.getString("name"));
        setBatchNumber(object.getString("batchNumber"));
        setQuantity(Float.parseFloat(object.getString("quantity")));
        setTotalConsumed(Float.parseFloat(object.getString("totalConsumed")));
        setUsageUnits(object.getString("usageUnits"));
        setType(object.getString("type"));
        setVariety(object.getString("variety"));
        setDressing(object.getString("dressing"));
        setTgw(object.getString("tgw"));
        setCost(Float.parseFloat(object.getString("cost")));
        setSupplier(object.getString("supplier"));
        setSyncStatus("no");
    }



}
