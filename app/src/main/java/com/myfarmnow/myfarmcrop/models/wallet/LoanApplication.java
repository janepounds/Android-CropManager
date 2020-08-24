package com.cabral.emaisha.wallet.models;


import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.cabral.emaisha.wallet.singletons.WalletSettingsSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LoanApplication implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("amount")
    @Expose
    private float amount;
    @SerializedName("ninNumber")
    @Expose
    private String ninNumber;
    @SerializedName("loanType")
    @Expose
    private String loanType;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("interestRate")
    @Expose
    private float interestRate;
    @SerializedName("dateApproved")
    @Expose
    private String dateApproved;


    @SerializedName("amountPaid")
    @Expose
    private double amountPaid;
    @SerializedName("isDue")
    @Expose
    private String isDue;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("amountExpected")
    @Expose
    private float amountExpected;
    @SerializedName("totalFines")
    @Expose
    private float totalFines;
    @SerializedName("nationalIDFrontPic")
    @Expose
    private String nationalIDFrontPic;
    @SerializedName("nationalIDBackPic")
    @Expose
    private String nationalIDBackPic;
    @SerializedName("userPhotoPic")
    @Expose
    private String userPhotoPic;

    @SerializedName("requestDate")
    @Expose
    private String requestDate;

    @SerializedName("dueAmount")
    @Expose
    private int dueAmount;

    public LoanApplication(){

    }

    public LoanApplication(JSONObject loanObject) throws JSONException {

       setId(loanObject.getString("id"));
       setLoanType(loanObject.getString("loanType"));
       setStatus(loanObject.getString("status"));
       setDateApproved(loanObject.getString("dateApproved"));
        setRequestDate(loanObject.getString("requestDate"));
        setDueDate(loanObject.getString("dueDate"));
       setNationalIDFrontPic(loanObject.getString("nationalIDFrontPic"));
       setNationalIDBackPic(loanObject.getString("nationalIDBackPic"));
       setUserPhotoPic(loanObject.getString("userPhotoPic"));
       setDuration(loanObject.getInt("duration"));
       setAmount((float)loanObject.getDouble("amount"));
       setAmountExpected((float)loanObject.getDouble("amountExpected"));
       setTotalFines((float)loanObject.getDouble("totalFines"));
      setInterestRate((float)loanObject.getDouble("interestRate"));
        setDueAmount((int)loanObject.getDouble("dueAmount"));
        setAmountPaid((double)loanObject.getDouble("totalPayments"));


    }
    public String getId() {
        return id;
    }
    public double getDueAmount(){
        return this.dueAmount;

    }

    public void setId(String id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getNinNumber() {
        return ninNumber;
    }

    public void setNinNumber(String ninNumber) {
        this.ninNumber = ninNumber;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }

    public String getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(String dateApproved) {
        this.dateApproved = dateApproved;
    }


    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getIsDue() {
        return isDue;
    }

    public void setIsDue(String isDue) {
        this.isDue = isDue;
    }
    public void setDueAmount(int dueAmount) {
        this.dueAmount = dueAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public float getAmountExpected() {
        return amountExpected;
    }

    public void setAmountExpected(float amountExpected) {
        this.amountExpected = amountExpected;
    }

    public float getTotalFines() {
        return totalFines;
    }

    public void setTotalFines(float totalFines) {
        this.totalFines = totalFines;
    }

    public String getNationalIDFrontPic() {
        return nationalIDFrontPic;
    }

    public void setNationalIDFrontPic(String nationalIDFrontPic) {
        this.nationalIDFrontPic = nationalIDFrontPic;
    }

    public String getNationalIDBackPic() {
        return nationalIDBackPic;
    }

    public void setNationalIDBackPic(String nationalIDBackPic) {
        this.nationalIDBackPic = nationalIDBackPic;
    }

    public String getUserPhotoPic() {
        return userPhotoPic;
    }

    public void setUserPhotoPic(String userPhotoPic) {
        this.userPhotoPic = userPhotoPic;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String generateStatus(){
        if (status.equals("Rejected") || status.equals("Approved")){
            return status;
        }
        if(nationalIDFrontPic ==null || nationalIDBackPic==null | userPhotoPic==null){
            return "Photos missing";
        }

        else {
            return status;
        }
    }

    public boolean isApproved(){
        return generateStatus().equals("Approved");
    }
    public boolean isEditable(){
        return !(generateStatus().equals("Approved") || generateStatus().equals("Rejected"));
    }

    public String computeDueDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(WalletSettingsSingleton.getInstance().getDateFormat().replace("mm","MM"));
        Log.d("DATE FORMAT", WalletSettingsSingleton.getInstance().getDateFormat().replace("MM","mm"));

        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.setTime(new Date());
        if (getLoanType().toLowerCase().equals("weekly")){
            todayCalendar.add(Calendar.DAY_OF_YEAR, getDuration()*7);
        }else if (getLoanType().toLowerCase().equals("monthly")){
            todayCalendar.add(Calendar.DAY_OF_YEAR, getDuration()*30);
        }
        return dateFormat.format(todayCalendar.getTime());
    }
    public int computeDueAmount(){
        return  Math. round(this.amount*(1+this.interestRate/100));
    }
    public String getDurationLabel(){

        return getLoanType().replace("ly","")+(getDuration()==1?"":"s");
    }
}



