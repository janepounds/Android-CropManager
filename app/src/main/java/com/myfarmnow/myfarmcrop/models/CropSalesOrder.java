package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CropSalesOrder implements Serializable{
     String  id;
     String  userId;
     String  customerId;
     String  number;
     String  date;
     String  shippingDate;
     float  discount;
     float  shippingCharges;
     String  customerNotes;
     String  termsAndConditions;
     String  method;
     String  referenceNumber;

     String customerName;
     ArrayList<CropProductItem> items = new ArrayList<>();
     ArrayList<String> deletedItemsId = new ArrayList<>();
    private String status="DRAFT";

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getShippingCharges() {
        return shippingCharges;
    }

    public void setShippingCharges(float shippingCharges) {
        this.shippingCharges = shippingCharges;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public ArrayList<CropProductItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CropProductItem> items) {
        this.items = items;
    }

    public float computeSubTotal(){
        float subTotal =0;
        for(CropProductItem x :items ){
            subTotal+=x.computeAmount();
        }
        return subTotal;
    }

    public float computeDiscount(float discount){
        float discountAmount =(discount/100)*computeSubTotal();
        return discountAmount;
    }
    public float computeDiscount(){

        return computeDiscount(this.discount);
    }
    public float computeTotal(){

        float subTotal=computeSubTotal();
        float total = (subTotal-computeDiscount())+shippingCharges;
        return total;

    }

    public void setDeletedItemsIds(ArrayList<String> items) {
        this.deletedItemsId =items;
    }

    public ArrayList<String>getDeletedItemsIds() {
        return this.deletedItemsId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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
            object.put("customerId",customerId);
            object.put("number",number);
            object.put("date",date);
            object.put("shippingDate",shippingDate);
            object.put("discount",discount);
            object.put("shippingCharges",shippingCharges);
            object.put("customerNotes",customerNotes);
            object.put("termsAndConditions",termsAndConditions);
            object.put("method",method);
            object.put("referenceNumber",referenceNumber);
            object.put("customerName",customerName);
            object.put("status",status);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

}