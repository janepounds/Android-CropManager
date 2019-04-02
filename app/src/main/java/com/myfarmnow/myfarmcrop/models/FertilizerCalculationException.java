package com.myfarmnow.myfarmcrop.models;

public class FertilizerCalculationException extends Exception {
    public FertilizerCalculationException(String error_in_calculations) {
        super(error_in_calculations);
    }
}
