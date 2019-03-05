package com.myfarmnow.myfarmcrop.models;

public interface CropInventory {
    final String CONST_FERTILIZER_INVENTORY = "fertilizer";
    final String CONST_SEEDS_INVENTORY = "seeds";

    String getBatchNumber();
    String getInventoryType();
    String getName();
    float getInitialQuantity();
    float getAmountConsumed();
    float calculateAmountLeft();
    String getUsageUnits();

}
