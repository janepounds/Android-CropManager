package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CropPurchaseOrder implements Serializable {

    String  id;
    String  userId;
    String  supplierId;
    String  number;
    String  purchaseDate;
    String  deliveryDate;
    float  discount;
    String  notes;
    String  termsAndConditions;
    String  method;
    String  referenceNumber;
    String supplierName;
    private String status="DRAFT";
    ArrayList<CropProductItem> items = new ArrayList<>();
    ArrayList<String> deletedItemsId = new ArrayList<>();


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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setDeletedItemsIds(ArrayList<String> items) {
        this.deletedItemsId =items;
    }

    public ArrayList<String>getDeletedItemsIds() {
        return this.deletedItemsId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
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
        float total = (subTotal-computeDiscount());
        return total;
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
            object.put("supplierId",supplierId);
            object.put("number",number);
            object.put("purchaseDate",purchaseDate);
            object.put("deliveryDate",deliveryDate);
            object.put("discount",discount);
            object.put("notes",notes);
            object.put("termsAndConditions",termsAndConditions);
            object.put("deliveryMethod",method);
            object.put("referenceNumber",referenceNumber);
            object.put("supplierName",supplierName);
            object.put("status",status);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public  CropPurchaseOrder(JSONObject object) throws JSONException{
        setGlobalId( object.getString("id"));
        setUserId( object.getString("userId"));
        setSyncStatus( "yes");
        setNumber( object.getString("number"));
        setSupplierId( object.getString("supplierId"));
//        setSupplierName( object.getString("supplierName"));
        setReferenceNumber( object.getString("referenceNumber"));
        setMethod( object.getString("deliveryMethod"));
        setTermsAndConditions( object.getString("termsAndConditions"));
        setNotes( object.getString("notes"));
        setDeliveryDate( object.getString("deliveryDate"));
        setPurchaseDate( object.getString("purchaseDate"));
        setDiscount( (float)object.getDouble("discount"));
    }

    public  CropPurchaseOrder(){

    }
}
