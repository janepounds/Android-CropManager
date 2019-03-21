package com.myfarmnow.myfarmcrop.models;

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

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    float sellingPrice=0;
    String customerSupplier="";


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
    @Override
    public String toString() {
        return getCustomerSupplier();
    }

}

