package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CropTransplanting implements CropSpinnerItem, Serializable,CropActivity {
    String id;
    String userId;
    String cropId;
    String operationDate;
    float totalSeedling = 0;
    float seedlingPerHa = 0;
    String varietyEarliness;
    float cycleLength = 0;
    String units;
    float expectedYield = 0;
    float expectedYieldPerHa = 0;
    String operator;
    float totalCost = 0;
    String recurrence;
    String reminders;
    private float frequency;
    private String repeatUntil;
    private String daysBefore;
    float area;

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

    public float getTotalSeedling() {
        return totalSeedling;
    }

    public void setTotalSeedling(float totalSeedling) {
        this.totalSeedling = totalSeedling;
    }

    public float getSeedlingPerHa() {
        return seedlingPerHa;
    }

    public void setSeedlingPerHa(float seedlingPerHa) {
        this.seedlingPerHa = seedlingPerHa;
    }

    public String getVarietyEarliness() {
        return varietyEarliness;
    }

    public void setVarietyEarliness(String varietyEarliness) {
        this.varietyEarliness = varietyEarliness;
    }

    public float getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(float cycleLength) {
        this.cycleLength = cycleLength;
    }

    public String getExpectedHarvestingDate() {

        try {
            return determineHarvestDate(operationDate, (int) cycleLength);
        } catch (ParseException e) {
            e.printStackTrace();

            return "";
        }
    }


    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public float getExpectedYield() {
        return expectedYield;
    }

    public void setExpectedYield(float expectedYield) {
        this.expectedYield = expectedYield;
    }

    public float getExpectedYieldPerHa() {
        return expectedYieldPerHa;
    }

    public void setExpectedYieldPerHa(float expectedYieldPerHa) {
        this.expectedYieldPerHa = expectedYieldPerHa;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public int getType() {
        return CropActivity.CROP_ACTIVITY_TRANSPLANTING;
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

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public String getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(String repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public String getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(String daysBefore) {
        this.daysBefore = daysBefore;
    }
    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

        public static String determineHarvestDate (String date,int cycleLength) throws ParseException {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date curDate = dateFormat.parse(date);
            Calendar currentCalendar = Calendar.getInstance();
            currentCalendar.setTime(curDate);
            currentCalendar.add(Calendar.DAY_OF_MONTH, cycleLength);
            NumberFormat formatter = new DecimalFormat("00");
            return currentCalendar.get(Calendar.YEAR) + "-" + formatter.format(currentCalendar.get(Calendar.MONTH) + 1) + "-" + formatter.format(currentCalendar.get(Calendar.DAY_OF_MONTH));


        }
   /* public float computeExpectedYieldPerUnit(){

        float expectedYieldPerUnit = (expectedYield / Crop.area);

        return  expectedYieldPerUnit;
    }

    public float computeSeedlingPerUnit(){

        float seedlingPerUnit = (totalSeedling / Crop.area);

        return  seedlingPerUnit;
    }*/

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
            object.put("cropId",cropId);
            object.put("operationDate",operationDate);
            object.put("totalSeedling",totalSeedling);
            object.put("seedlingPerHa",seedlingPerHa);
            object.put("varietyEarliness",varietyEarliness);
            object.put("cycleLength",cycleLength);
            object.put("units",units);
            object.put("expectedYield",expectedYield);
            object.put("expectedYieldPerHa",expectedYieldPerHa);
            object.put("operator",operator);
            object.put("totalCost",totalCost);
            object.put("area",area);
            object.put("recurrence",recurrence);
            object.put("reminders",reminders);
            object.put("frequency",frequency);
            object.put("repeatUntil",repeatUntil);
            object.put("daysBefore",daysBefore);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropTransplanting(JSONObject object) throws JSONException {
        setGlobalId(object.getString("id"));
        setCropId(object.getString("cropId"));
        setUserId(object.getString("userId"));
        setOperationDate(object.getString("operationDate"));
        setTotalSeedling((float)object.getDouble("totalSeedling"));
        setSeedlingPerHa((float)object.getDouble("seedlingPerHa"));
        setCycleLength((float)object.getDouble("cycleLength"));
        setVarietyEarliness(object.getString("varietyEarliness"));
        setUnits(object.getString("units"));
        setExpectedYield((float)object.getDouble("expectedYield"));
        setExpectedYieldPerHa((float)object.getDouble("expectedYieldPerHa"));
        setOperator(object.getString("operator"));
        setArea((float)object.getDouble("area"));
        setRecurrence(object.getString("recurrence"));
        setReminders(object.getString("totalCost"));
        setTotalCost((float)object.getDouble("cost"));
        setFrequency((float)object.getDouble("frequency"));
        setRepeatUntil(object.getString("repeatUntil"));
        setDaysBefore(object.getString("daysBefore"));
        setSyncStatus( "yes");

    }

}