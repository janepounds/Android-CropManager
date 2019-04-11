package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CropIrrigation implements CropSpinnerItem, Serializable, CropActivity {
    String id="";
    String userId="";
    String cropId ="";
    String operationDate="";
    float systemRate=0;
    String startTime="";
    String endTime="";
    float totalWaterQuantity=0;
    float areaIrrigated =0;
    String units="";
    float quantityPerUnit=0;
    String recurrence="";
    String reminders="";
    float totalCost=0;


    @Override
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

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public float getSystemRate() {
        return systemRate;
    }

    public void setSystemRate(float systemRate) {
        this.systemRate = systemRate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public float getTotalWaterQuantity() {
        return totalWaterQuantity;
    }

    public void setTotalWaterQuantity(float totalWaterQuantity) {
        this.totalWaterQuantity = totalWaterQuantity;
    }

    public float getAreaIrrigated() {
        return areaIrrigated;
    }

    public void setAreaIrrigated(float areaIrrigated) {
        this.areaIrrigated = areaIrrigated;
    }

    public float getQuantityPerUnit() {
        return computeWaterQuantity()/getAreaIrrigated();
    }



    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getReminders() {
        return reminders;
    }

    public void setReminders(String reminders) {
        this.reminders = reminders;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public int getType() {
        return CropActivity.CROP_ACTIVITY_IRRIGATION;
    }

    public float computeWaterQuantity(){
        try {
            return  getSystemRate()*calculateTime(startTime,endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getDuration(){
        try {
            return  calculateTime(startTime,endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static float calculateTime(String startTime, String endTime) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date1 = format.parse(startTime);
        Date date2 = format.parse(endTime);
        double difference = (date2.getTime() - date1.getTime())/(1000.0*60*60.0);
        return (float)difference;
    }
}
