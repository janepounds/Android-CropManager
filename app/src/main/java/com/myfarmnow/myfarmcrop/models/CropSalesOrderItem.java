package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropSalesOrderItem implements CropProductItem,Serializable {
     String  id;
     String  productId;
     String  salesOrderId;
     float quantity;
     float rate;
     float tax;
     String productName;

    public CropSalesOrderItem(){

    }

     public CropSalesOrderItem(String  productId, String  salesOrderId, float quantity){
         setProductId(productId);
         setInvoiceOrEstimateId(salesOrderId);
         setQuantity(quantity);
     }
    public CropSalesOrderItem(String id, String  productId, String  salesOrderId, float quantity){
        setId(id);
        setProductId(productId);
        setInvoiceOrEstimateId(salesOrderId);
        setQuantity(quantity);
    }
    public CropSalesOrderItem(String id, String  productId, String  salesOrderId, float quantity, float rate, float tax){
        setId(id);
        setProductId(productId);
        setInvoiceOrEstimateId(salesOrderId);
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

    public String getInvoiceOrEstimateId() {
        return salesOrderId;
    }

    public void setInvoiceOrEstimateId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
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

    @Override
    public void setProductName(String name) {
        this.productName = name;
    }

    @Override
    public String gettProductName() {
        return this.productName;
    }
}
