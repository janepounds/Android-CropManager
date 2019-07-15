package com.myfarmnow.myfarmcrop.singletons;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CropSettingsSingleton {
    private static final CropSettingsSingleton ourInstance = new CropSettingsSingleton();

    private String dateFormat ="dd/mm/yyyy";
    private String currency ="USD";
    private String weightUnits="Kg";
    private String areaUnits="Acres";
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

    private String syncStatus="no";
    private String globalId;
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getGlobalId() {
        return globalId;
    }

    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("globalId",globalId);
            object.put("dateFormat",dateFormat);
            object.put("currency",currency);
            object.put("weightUnits",weightUnits);
            object.put("areaUnits",areaUnits);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropSettingsSingleton(JSONObject object) throws JSONException{
        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setDateFormat(object.getString("dateFormat"));
        setCurrency(object.getString("currency"));
        setWeightUnits(object.getString("weightUnits"));
        setAreaUnits(object.getString("areaUnits"));
    }
}
