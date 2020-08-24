package com.cabral.emaisha.wallet.models;

import java.util.ArrayList;

public class WalletPurchase {
    static WalletPurchase purchase = new WalletPurchase();
    String mechantId, coupon;
    float amount;

    public static WalletPurchase getInstance(){
        return purchase;
    }

    public void setMechantId(String mechantId) {
        this.mechantId = mechantId;
    }



    public String getMechantId() {
        return mechantId;
    }



    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getCoupon() { return coupon; }
}
