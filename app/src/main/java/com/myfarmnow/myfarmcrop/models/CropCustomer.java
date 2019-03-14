package com.myfarmnow.myfarmcrop.models;

public class CropCustomer implements CropSpinnerItem{
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
}
