package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropPaymentBill implements Serializable {
    String  id;
    String  userId;
    float  amount;
    String  date;
    String  mode;
    String  referenceNumber;
    String  paidThrough;
    String  notes;
    private String supplierId;
    private String supplierName;
    private String billNumber;

    @Override
    public String toString() {
        return "CropPaymentBill{" +
                "billId='" + billId + '\'' +
                '}';
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    String  billId;


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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getPaidThrough() {
        return paidThrough;
    }

    public void setPaidThrough(String paidThrough) {
        this.paidThrough = paidThrough;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
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
            object.put("amount",amount);
            object.put("date",date);
            object.put("mode",mode);
            object.put("billId",billId);
            object.put("referenceNumber",referenceNumber);
            object.put("paidThrough",mode);
            object.put("notes",notes);
            object.put("supplierId",supplierId);
            object.put("supplierName",supplierName);
            object.put("billNumber",billNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    public  CropPaymentBill(JSONObject object) throws JSONException{
        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setDate(object.getString("date"));
        setMode(object.getString("mode"));
        setReferenceNumber(object.getString("referenceNumber"));
        setPaidThrough(object.getString("paidThrough"));
        setNotes(object.getString("notes"));
        setSupplierId(object.getString("supplierId"));
        //setSupplierName(object.getString("supplierName"));
//        setBillNumber(object.getString("billNumber"));
        setBillId( object.getString("billId"));
        setAmount((float)object.getDouble("amount"));
        setSyncStatus( "yes");
    }
    public  CropPaymentBill(){

    }


}
