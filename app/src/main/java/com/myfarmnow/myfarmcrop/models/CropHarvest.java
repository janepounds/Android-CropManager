package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropHarvest implements CropSpinnerItem, Serializable,CropActivity {
    String id;
    String userId;
    String cropId ;
    String employeeId;
    String date;
    String method;
    String units;
    float quantity=0;
    String status;
    String dateSold;
    String customer;
    float price=0;
    float quantitySold=0;
    String storageDate;
    float quantityStored=0;
    float cost=0;
    String recurrence;
    String reminders;
    private float frequency;
    private String repeatUntil;
    private String daysBefore;
    String operator;


    public float getIncomeGenerated() {
        return incomeGenerated;
    }

    public void setIncomeGenerated(float incomeGenerated) {
        this.incomeGenerated = incomeGenerated;
    }

    public float incomeGenerated=0;

    public float computeIncomeGenerated(){

        float incomeGenerated = (price * quantitySold);

        return  incomeGenerated;
    }

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

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateSold() {
        return dateSold;
    }

    public void setDateSold(String dateSold) {
        this.dateSold = dateSold;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(float quantitySold) {
        this.quantitySold = quantitySold;
    }

    public String getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }

    public float getQuantityStored() {
        return quantityStored;
    }

    public void setQuantityStored(float quantityStored) {
        this.quantityStored = quantityStored;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }


    @Override
    public int getType() {
        return CropActivity.CROP_ACTIVITY_HARVESTING;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getReminders() {
        return reminders;
    }

    public void setReminders(String reminders) {
        this.reminders = reminders;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public String getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(String repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public String getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(String daysBefore) {
        this.daysBefore = daysBefore;
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
            object.put("cropId",cropId);
            object.put("employeeId",employeeId);
            object.put("date",date);
            object.put("method",method);
            object.put("harvestUnits",units);
            object.put("quantity",quantity);
            object.put("status",status);
            object.put("dateSold",dateSold);
            object.put("customer",customer);
            object.put("price",price);
            object.put("quantitySold",quantitySold);
            object.put("storageDate",storageDate);
            object.put("quantityStored",quantityStored);
            object.put("cost",cost);
            object.put("recurrence",recurrence);
            object.put("reminders",reminders);
            object.put("frequency",frequency);
            object.put("repeatUntil",repeatUntil);
            object.put("daysBefore",daysBefore);
            object.put("operator",operator);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    public CropHarvest(JSONObject object) throws JSONException {
        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setCropId(object.getString("cropId"));
        setEmployeeId(object.getString("operator"));
        setDate(object.getString("date"));
        setMethod(object.getString("method"));
        setUnits(object.getString("harvestUnits"));
        setQuantity(Float.parseFloat(object.getString("quantity")));
        setStatus(object.getString("status"));
        setDateSold(object.getString("dateSold"));
        setCustomer(object.getString("customer"));
        setPrice(Float.parseFloat(object.getString("price")));
        setQuantitySold(Float.parseFloat(object.getString("quantitySold")));
        setStorageDate(object.getString("storageDate"));
        setQuantityStored(Float.parseFloat(object.getString("quantityStored")));
        setCost(Float.parseFloat(object.getString("cost")));
        setRecurrence(object.getString("recurrence"));
        setReminders(object.getString("reminders"));
        setFrequency(Float.parseFloat(object.getString("frequency")));
        setRepeatUntil(object.getString("repeatUntil"));
        setDaysBefore(object.getString("daysBefore"));
        setOperator(object.getString("operator"));
        setSyncStatus("yes");

    }
    public CropHarvest(){

    }
}
