package com.myfarmnow.myfarmcrop.singletons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CropSettingsSingleton {
    private static final CropSettingsSingleton ourInstance = new CropSettingsSingleton();

    private String dateFormat ="dd/mm/yyyy";
    private String currency ="USD ";
    private String weightUnits;
    private String areaUnits;
    private String id;
    private String userId;

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



    public  String convertToUserFormat(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat) ;
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-mm-dd") ;
        try {
            return formatter.format(defaultFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
           return "";
        }
    }

    public String getWeightUnits() {
        return weightUnits;
    }

    public void setWeightUnits(String weightUnits) {
        this.weightUnits = weightUnits;
    }

    public String getAreaUnits() {
        return areaUnits;
    }

    public void setAreaUnits(String areaUnits) {
        this.areaUnits = areaUnits;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
