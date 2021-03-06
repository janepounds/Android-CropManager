package com.myfarmnow.myfarmcrop.models.wallet;

public class WalletTransaction {

    String recepient, sender;
    String type;
    double amount;
    String date;
    String referenceNumber;

    boolean isPurchase;
    static WalletTransaction transaction = new WalletTransaction();

    public static WalletTransaction getInstance(){
        return transaction;
    }

    public String getRecepient() {
        return recepient;
    }

    public void setRecepient(String recepient) {
        this.recepient = recepient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public boolean isPurchase() {
        return isPurchase;
    }

    public void setIsPurchase(boolean purchase) {
        isPurchase = purchase;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    Integer time;
    public WalletTransaction() {
    }


    public WalletTransaction(String date, String recepient, String type, double debit, String referenceNumber) {
        this.date = date;
        this.recepient = recepient;
        this.type = type;
        this.amount = debit;
        this.referenceNumber = referenceNumber;
    }
}
