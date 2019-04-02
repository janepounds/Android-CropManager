package com.myfarmnow.myfarmcrop.models;

import android.content.Context;

import com.myfarmnow.myfarmcrop.R;

import java.io.Serializable;
import java.util.ArrayList;

public class CropBill implements Serializable,CropSpinnerItem {

    String  id;
    String  userId;
    String  supplierId;
    String  number;
    String  billDate;
    String  dueDate;
    float  discount;
    String  notes;
    String  terms;
    String  orderNumber;
    String supplierName;

    ArrayList<CropProductItem> items = new ArrayList<>();
    private ArrayList<CropPaymentBill> paymentBills;

    private ArrayList<String> deletedItemsIds;

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

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
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

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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
    public float computeTotalPayments(){
        float subTotal =0;
        for(CropPaymentBill x :paymentBills ){
            subTotal+=x.getAmount();
        }
        return subTotal;
    }

    public void setDeletedItemsIds(ArrayList<String> deletedItemsIds) {
        this.deletedItemsIds = deletedItemsIds;
    }

    public ArrayList<String> getDeletedItemsIds() {
        return deletedItemsIds;
    }

    public String toString(){
        return getNumber();
    }


    public void setPaymentBills(ArrayList<CropPaymentBill> paymentBills) {
        this.paymentBills = paymentBills;
    }

    public ArrayList<CropPaymentBill> getPaymentBills() {
        return paymentBills;
    }

    public float computeBalance(){
        return computeTotal()-computeTotalPayments();
    }

    public String determineStatus(Context context){
        float balance = computeBalance();
        float totalAmount = computeTotal();
        if(balance>=totalAmount){
            return context.getString(R.string.invoice_status_draft);
        }
        else if(balance<=0){
            return context.getString(R.string.invoice_status_paid);
        }
        else if(balance<totalAmount){
            return context.getString(R.string.invoice_status_partially_paid);
        }

        return  "";
    }


}
