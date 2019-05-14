package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropProductItem implements Serializable {
    String  id;
    String  productId;
    String  parentObjectId;
    float quantity;
    float rate;
    float tax;
    String productName;

    public CropProductItem(){

    }

    public CropProductItem( String  productId, String  parentObjectId, float quantity){
        setProductId(productId);
        setParentObjectId(parentObjectId);
        setQuantity(quantity);
    }
    public CropProductItem( String id, String  productId, String  parentObjectId, float quantity){
        setId(id);
        setProductId(productId);
        setParentObjectId(parentObjectId);
        setQuantity(quantity);
    }
    public CropProductItem( String id, String  productId, String  parentObjectId, float quantity,float rate,float tax){
        setId(id);
        setProductId(productId);
        setParentObjectId(parentObjectId);
        setQuantity(quantity);
        setRate(rate);
        setTax(tax);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(String parentObjectId) {
        this.parentObjectId = parentObjectId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float computeAmount(){
        float total  = rate*quantity;
        float tax = (this.tax/100)*total;
        return total+tax;
    }


    public void setProductName(String name) {
        this.productName = name;
    }


    public String gettProductName() {
        return this.productName;
    }

    public String print(){
        return String.format("%s %s %s ",getId(),getQuantity(),getParentObjectId());
    }
}
