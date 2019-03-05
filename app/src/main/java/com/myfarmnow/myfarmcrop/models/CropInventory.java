package com.myfarmnow.myfarmcrop.models;

public interface CropInventory {
    String CONST_FERTILIZER_INVENTORY = "fertilizer";

    String getBatchNumber();
    String getInventoryType();
    String getName();
    float getInitialQuantity();
    float getAmountConsumed();
    float calculateAmountLeft();
    String getUsageUnits();
}
