package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropSupplier implements Serializable,CropSpinnerItem{
    String id;
    String userId;
    String name;
    String company;
    String taxRegNo;
    String phone;
    String mobile;
    String email;
    float openingBalance;
    String invoiceStreet;
    String invoiceCityOrTown;
    String invoiceCountry;

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

    public String getInvoiceStreet() {
        return invoiceStreet;
    }

    public void setInvoiceStreet(String invoiceStreet) {
        this.invoiceStreet = invoiceStreet;
    }

    public String getInvoiceCityOrTown() {
        return invoiceCityOrTown;
    }

    public void setInvoiceCityOrTown(String invoiceCityOrTown) {
        this.invoiceCityOrTown = invoiceCityOrTown;
    }

    public String getInvoiceCountry() {
        return invoiceCountry;
    }

    public void setInvoiceCountry(String invoiceCountry) {
        this.invoiceCountry = invoiceCountry;
    }
    @Override
    public String toString() {
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
            object.put("invoiceStreet",invoiceStreet);
            object.put("invoiceCityOrTown",invoiceCityOrTown);
            object.put("invoiceCountry",invoiceCountry);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropSupplier(JSONObject object) throws JSONException{
        setGlobalId( object.getString("id"));
        setUserId( object.getString("userId"));
        setSyncStatus( "yes");
        setName( object.getString("name"));
        setCompany( object.getString("company"));
        setTaxRegNo( object.getString("taxRegNo"));
        setPhone( object.getString("phone"));
        setMobile( object.getString("mobile"));
        setEmail( object.getString("email"));
        setOpeningBalance((float)object.getDouble("openingBalance"));
        setInvoiceStreet( object.getString("invoiceStreet"));
        setInvoiceCityOrTown( object.getString("invoiceCityOrTown"));
        setInvoiceCountry( object.getString("invoiceCityOrTown"));
    }
}
