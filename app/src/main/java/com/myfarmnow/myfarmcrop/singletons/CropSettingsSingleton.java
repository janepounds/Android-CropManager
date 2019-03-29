package com.myfarmnow.myfarmcrop.singletons;

public class CropSettingsSingleton {
    private static final CropSettingsSingleton ourInstance = new CropSettingsSingleton();

    private String dateFormat ="dd-mm-yyyy";
    private String currency ="UGX";
    public static CropSettingsSingleton getInstance() {
        return ourInstance;
    }

    private CropSettingsSingleton() {
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
