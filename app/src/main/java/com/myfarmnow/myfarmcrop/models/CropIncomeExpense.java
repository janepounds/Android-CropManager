package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropIncomeExpense implements CropSpinnerItem, Serializable {

    String id="";
    String userId="";

    String cropId="";
    String date="";
    String transaction="";
    String item="";
    String category="";
    float quantity=0;
    Integer grossAmount=0;
    float unitPrice=0;
    float taxes=0;
    String paymentMode="";
    String paymentStatus="";
    String department = "";

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    float sellingPrice=0;
    String customerSupplier="";
    float amount=0;


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

    public String getCropId() { return cropId;   }

    public void setCropId(String cropId) { this.cropId = cropId;  }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Integer getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Integer grossAmount) {
        this.grossAmount = grossAmount;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getTaxes() {
        return taxes;
    }

    public void setTaxes(float taxes) {
        this.taxes = taxes;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public String getCustomerSupplier() {
        return customerSupplier;
    }

    public void setCustomerSupplier(String customerSupplier) {
        this.customerSupplier = customerSupplier;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public  float computeAmount(){
        float amount= ( grossAmount-(grossAmount *(taxes/100)));
        return  amount;

    }
    public  float computeUnitPrice(){
        float unitPrice =( grossAmount / quantity);
        return  unitPrice;

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
            object.put("date",date);
            object.put("transaction",transaction);
            object.put("item",item);
            object.put("category",category);
            object.put("quantity",quantity);
            object.put("grossAmount",grossAmount);
            object.put("unitPrice",unitPrice);
            object.put("taxes",taxes);
            object.put("paymentMode",paymentMode);
            object.put("paymentStatus",paymentStatus);
            object.put("sellingPrice",sellingPrice);
            object.put("customerSupplier",customerSupplier);
            object.put("amount",amount);
            object.put("department",department);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropIncomeExpense(JSONObject object) throws JSONException {

        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setCropId(object.getString("cropId"));
        setDate(object.getString("date"));
        setTransaction(object.getString("transaction"));
        setItem(object.getString("item"));
        setCategory(object.getString("category"));
        setQuantity(Float.valueOf(object.getString("quantity")));
        setGrossAmount(Integer.valueOf(object.getString("grossAmount")));
        setUnitPrice(Float.parseFloat(object.getString("unitPrice")));
        setTaxes(Float.parseFloat(object.getString("taxes")));
        setPaymentMode(object.getString("paymentMode"));
        setPaymentStatus(object.getString("paymentStatus"));
        setSellingPrice(Float.parseFloat(object.getString("sellingPrice")));
        setCustomerSupplier(object.getString("customerSupplier"));
        setDepartment(object.getString("department"));
        setSyncStatus("yes");
    }
    public CropIncomeExpense(){

    }
}

