package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropCustomer implements CropSpinnerItem,Serializable{
    String id;
    String userId;
    String name;
    String company;
    String taxRegNo;
    String phone;
    String mobile;
    String email;
    float openingBalance;
    String billingStreet;
    String billingCityOrTown;
    String billingCountry;
    String shippingStreet;
    String shippingCityOrTown;
    String shippingCountry;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTaxRegNo() {
        return taxRegNo;
    }

    public void setTaxRegNo(String taxRegNo) {
        this.taxRegNo = taxRegNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(float openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getBillingStreet() {
        return billingStreet;
    }

    public void setBillingStreet(String billingStreet) {
        this.billingStreet = billingStreet;
    }

    public String getBillingCityOrTown() {
        return billingCityOrTown;
    }

    public void setBillingCityOrTown(String billingCityOrTown) {
        this.billingCityOrTown = billingCityOrTown;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getShippingStreet() {
        return shippingStreet;
    }

    public void setShippingStreet(String shippingStreet) {
        this.shippingStreet = shippingStreet;
    }

    public String getShippingCityOrTown() {
        return shippingCityOrTown;
    }

    public void setShippingCityOrTown(String shippingCityOrTown) {
        this.shippingCityOrTown = shippingCityOrTown;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String toString(){
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
            object.put("userId",userId);
            object.put("name",name);
            object.put("company",company);
            object.put("taxRegNo",taxRegNo);
            object.put("phone",phone);
            object.put("mobile",mobile);
            object.put("email",email);
            object.put("openingBalance",openingBalance);
            object.put("billingStreet",billingStreet);
            object.put("billingCityOrTown",billingCityOrTown);
            object.put("billingCountry",billingCountry);
            object.put("shippingStreet",shippingStreet);
            object.put("shippingCityOrTown",shippingCityOrTown);
            object.put("shippingCountry",shippingCountry);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropCustomer(JSONObject object) throws JSONException {
        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setName(object.getString("name"));
        setCompany(object.getString("company"));
        setTaxRegNo(object.getString("taxRegNo"));
        setPhone(object.getString("phone"));
        setMobile(object.getString("mobile"));
        setEmail(object.getString("email"));
        setOpeningBalance(Float.parseFloat(object.getString("openingBalance")));
        setBillingStreet(object.getString("billingStreet"));
        setBillingCityOrTown(object.getString("billingCityOrTown"));
        setBillingCountry(object.getString("billingCountry"));
        setShippingStreet(object.getString("shippingStreet"));
        setShippingCityOrTown(object.getString("shippingCityOrTown"));
        setShippingCountry(object.getString("shippingCountry"));
        setSyncStatus("no");
    }
}
