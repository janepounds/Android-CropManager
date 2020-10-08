package com.myfarmnow.myfarmcrop.models.farmrecords;

import android.util.Log;


import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Crop implements Serializable, CropSpinnerItem {
    private String id;
    private String userId;
    private String fieldId;
    private String fieldName;
    private String dateSown;
    private String variety;
    public  float area;
    private float estimatedYield;
    private float estimatedRevenue;
    private String harvestUnits;
    private String plantingMethod;
    private String name;
    private String season;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public float getRateR() {
        return rateR;
    }

    public void setRateR(float rateR) {
        this.rateR = rateR;
    }

    public float rateR=0;



    public float computeEstimatedRevenueC(){
        float estimatedRevenueC= (estimatedYield*estimatedRevenue);
        return  estimatedRevenueC;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Crop(){

    }
    // Getter Methods
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }


    public String getFieldId() {
        return fieldId;
    }

    public String getDateSown() {
        return dateSown;
    }

    public String getVariety() {
        return variety;
    }



    public float getArea() {
        return area;
    }


    public String getSeason(){return  season;}

    public String getPlantingMethod() {
        return plantingMethod;
    }

    // Setter Methods
    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public void setDateSown(String dateSown) {
        this.dateSown = dateSown;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }



    public void setArea(float area) {
        this.area = area;
    }

    public void setSeason(String season){this.season = season;}



    public void setPlantingMethod(String plantingMethod) {
        this.plantingMethod = plantingMethod;
    }

    public String computeAge(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {

            convertedDate = dateFormat.parse(getDateSown());
            Calendar today = Calendar.getInstance();       // get calendar instance
            today.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            today.set(Calendar.MINUTE, 0);                 // set minute in hour
            today.set(Calendar.SECOND, 0);                 // set second in minute
            today.set(Calendar.MILLISECOND, 0);

            long daysBetween = daysBetween(convertedDate, today.getTime());
            int years = (int) (daysBetween / 365);
            int months = (int) ((daysBetween - years * 365) / 30);
            int days = (int) (daysBetween % 30);
            Log.d("DATES", getDateSown() + " " + convertedDate.toString() + " - " + today.getTime().toString() + " days = " + daysBetween);
            String age = "";
            if (years > 0) {
                age += years + "Y ";
            }
            if (months > 0)
                age += months + "M ";
            age += days + "D";

            return age;


        } catch (ParseException e) {
            Log.d("DATe", getDateSown());
            e.printStackTrace();
            String age = "--";
            return age;
        }

    }
    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH,1);
            //Log.d("Day "+daysBetween,sDate.getTime().toString());
            daysBetween++;
        }
        return daysBetween;
    }
    public static Calendar getDatePart(Date date){
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }

    public int getYear() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date = dateFormat.parse(getDateSown());
        int year= date.getYear();
        int  currentYear = year + 1900;
        return currentYear;


    }


    @Override
    public String toString() {
        return getName()+"( "+getSeason()+"  )";
    }

    public float getEstimatedYield() {
        return estimatedYield;
    }

    public void setEstimatedYield(float estimatedYield) {
        this.estimatedYield = estimatedYield;
    }

    public float getEstimatedRevenue() {
        return estimatedRevenue;
    }

    public void setEstimatedRevenue(float estimatedRevenue) {
        this.estimatedRevenue = estimatedRevenue;
    }

    public String getHarvestUnits() {
        return harvestUnits;
    }

    public void setHarvestUnits(String harvestUnits) {
        this.harvestUnits = harvestUnits;
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
            object.put("userId",userId);
            object.put("fieldId",fieldId);
            object.put("dateSown",dateSown);
            object.put("variety",variety);
            object.put("area",area);
            object.put("season",season);
            object.put("estimatedYield",estimatedYield);
            object.put("estimatedRevenue",estimatedRevenue);
            object.put("harvestUnits",harvestUnits);
            object.put("plantingMethod",plantingMethod);
            object.put("name",name);
            object.put("syncStatus",syncStatus);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    public Crop(JSONObject object) throws JSONException {
        setUserId(object.getString("userId"));
        setGlobalId(object.getString("id"));
        setFieldId(object.getString("fieldId"));
        setDateSown(object.getString("dateSown"));
        setVariety(object.getString("variety"));
        setArea((float)object.getDouble("area"));
        setSeason(object.getString("season"));
        setEstimatedYield((float)object.getDouble("estimatedYield"));
        setEstimatedRevenue((float)object.getDouble("estimatedRevenue"));
        setHarvestUnits(object.getString("harvestUnits"));
        setPlantingMethod(object.getString("plantingMethod"));
        setName(object.getString("name"));
        setSyncStatus("yes");
    }


}
