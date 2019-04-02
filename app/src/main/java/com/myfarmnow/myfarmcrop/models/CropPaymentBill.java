package com.myfarmnow.myfarmcrop.models;

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
}
