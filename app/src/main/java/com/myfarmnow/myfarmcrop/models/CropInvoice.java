package com.myfarmnow.myfarmcrop.models;

import android.content.Context;

import com.myfarmnow.myfarmcrop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CropInvoice implements Serializable,CropSpinnerItem {
     String  id;
     String  userId;
     String  customerId;;
     String  number;
     String  date;
     String  dueDate;
     float  discount;
     float  shippingCharges;
     String  customerNotes;
     String  termsAndConditions;
     String  terms;
     String  orderNumber;
     String customerName;

    CropPayment initialPayment;
     ArrayList<CropProductItem> items = new ArrayList<>();
    private ArrayList<CropPayment> payments;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    private ArrayList<String> deletedItemsIds;

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
    public float computeTotalPayments(){
        float subTotal =0;
        for(CropPayment x :payments ){
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

    public CropPayment getInitialPayment() {
        return initialPayment;
    }

    public void setInitialPayment(CropPayment initialPayment) {
        this.initialPayment = initialPayment;
    }

    public void setPayments(ArrayList<CropPayment> payments) {
        this.payments = payments;
    }

    public ArrayList<CropPayment> getPayments() {
        return payments;
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


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

}
