package com.myfarmnow.myfarmcrop.models;

public interface CropProductItem {
    float getTax();
    void setTax(float tax);
    float getRate();
    void setRate(float rate);
    String getId();
    void setId(String id);
    String getProductId();
    void setProductId(String productId);
    String getInvoiceOrEstimateId();
    void setInvoiceOrEstimateId(String id);
    float getQuantity();
    void setQuantity(float quantity);
    float computeAmount();

    void setProductName(String name);
    String gettProductName();
}
