package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropEmployee implements CropSpinnerItem, Serializable {
    String id;
    String userId;
    String title;
    String firstName;
    String lastName;
    String phone;
    String mobile;
    String employeeId;
    String gender;
    String address;
    String email;
    String dateOfBirth;
    String hireDate;
    String employmentStatus;
    float payAmount;
    String payRate;
    String payType;
    String supervisor;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public float getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(float payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayRate() {
        return payRate;
    }

    public void setPayRate(String payRate) {
        this.payRate = payRate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getFullName(){
        return getFirstName()+" "+getLastName();
    }
    @Override
    public String toString() {
        return getFullName();
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
            object.put("title",title);
            object.put("firstName",firstName);
            object.put("lastName",lastName);
            object.put("phone",phone);
            object.put("mobile",mobile);
            object.put("employeeId",employeeId);
            object.put("gender",gender);
            object.put("address",address);
            object.put("email",email);
            object.put("dateOfBirth",dateOfBirth);
            object.put("hireDate",hireDate);
            object.put("employmentStatus",employmentStatus);
            object.put("payAmount",payAmount);
            object.put("payRate",payRate);
            object.put("payType",payType);
            object.put("supervisor",supervisor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public CropEmployee(JSONObject object) throws JSONException {

        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setTitle(object.getString("title"));
        setFirstName(object.getString("firstName"));
        setLastName(object.getString("lastName"));
        setPhone(object.getString("phone"));
        setMobile(object.getString("mobile"));
        setEmployeeId(object.getString("employeeId"));
        setGender(object.getString("gender"));
        setAddress(object.getString("address"));
        setEmail(object.getString("email"));
        setDateOfBirth(object.getString("dateOfBirth"));
        setHireDate(object.getString("hireDate"));
        setEmploymentStatus(object.getString("employmentStatus"));
        setPayAmount(Float.parseFloat(object.getString("payAmount")));
        setPayRate(object.getString("payRate"));
        setPayType(object.getString("payType"));
        setSupervisor(object.getString("supervisor"));
        setSyncStatus("no");
    }
}
