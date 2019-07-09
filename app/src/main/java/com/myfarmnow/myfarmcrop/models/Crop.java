package com.myfarmnow.myfarmcrop.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Crop implements Serializable, CropSpinnerItem {
 private String id;
 private String userId;
 private int croppingYear;
 private String fieldId;
 private String dateSown;
 private String variety;
 private String growingCycle;
 private String season;
 public  float area;
 private float cost;
 private float estimatedYield;
 private float estimatedRevenue;
 private String harvestUnits;
 private String operator;
 private String seedId;
 private float rate;
 private String plantingMethod;
 private String name;
 private String age;



 public float getRateR() {
  return rateR;
 }

 public void setRateR(float rateR) {
  this.rateR = rateR;
 }

 public float rateR=0;

 public float computeRateR(){

  float rateR = (rate / area);

  return  rateR;
 }

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

 public int getCroppingYear() {
  return croppingYear;
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

 public String getGrowingCycle() {
  return growingCycle;
 }

 public String getSeason() {  return season; }

 public float getArea() {
  return area;
 }

 public float getCost() {
  return cost;
 }

 public String getOperator() {
  return operator;
 }

 public String getSeedId() {
  return seedId;
 }

 public float getRate() {
  return rate;
 }

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

 public void setCroppingYear(int croppingYear) {
  this.croppingYear = croppingYear;
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

 public void setGrowingCycle(String growingCycle) {
  this.growingCycle = growingCycle;
 }

 public void setSeason(String season) {  this.season = season; }

 public void setArea(float area) {
  this.area = area;
 }

 public void setCost(float cost) {
  this.cost = cost;
 }

 public void setOperator(String operator) {
  this.operator = operator;
 }

 public void setSeedId(String seedId) {
  this.seedId = seedId;
 }

 public void setRate(float rate) {
  this.rate = rate;
 }

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

   long daysBetween = daysBetween(convertedDate,today.getTime());

   int months = (int)(daysBetween/30);
   int days =(int)(daysBetween%30);
   Log.d("DATES",getDateSown()+" "+convertedDate.toString()+" - "+today.getTime().toString()+" days = "+daysBetween);
   return months+"m "+days+"d";

  } catch (ParseException e) {
   Log.d("DATe",getDateSown());
    e.printStackTrace();
    return "0m 0d";

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

 @Override
 public String toString() {
  return getName()+"( "+getSeason()+" - "+getCroppingYear()+" )";
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
   object.put("croppingYear",croppingYear);
   object.put("fieldId",fieldId);
   object.put("dateSown",dateSown);
   object.put("variety",variety);
   object.put("growingCycle",growingCycle);
   object.put("season",season);
   object.put("area",area);
   object.put("cost",cost);
   object.put("estimatedYield",estimatedYield);
   object.put("estimatedRevenue",estimatedRevenue);
   object.put("harvestUnits",harvestUnits);
   object.put("operator",operator);
   object.put("seedId",seedId);
   object.put("rate",rate);
   object.put("plantingMethod",plantingMethod);
   object.put("name",name);

  } catch (JSONException e) {
   e.printStackTrace();
  }
  return object;
 }
 public Crop(JSONObject object) throws JSONException {
  setUserId(object.getString("userId"));
  setGlobalId(object.getString("id"));
  setCroppingYear(Integer.parseInt(object.getString("croppingYear")));
  setFieldId(object.getString("fieldId"));
  setDateSown(object.getString("dateSown"));
  setVariety(object.getString("variety"));
  setGrowingCycle(object.getString("growingCycle"));
  setSeason(object.getString("season"));
  setArea(Float.parseFloat(object.getString("area")));
  setCost(Float.parseFloat(object.getString("cost")));
  setEstimatedYield(Float.parseFloat(object.getString("estimatedYield")));
  setEstimatedRevenue(Float.parseFloat(object.getString("estimatedRevenue")));
  setHarvestUnits(object.getString("harvestUnits"));
  setOperator(object.getString("operator"));
  setSeedId(object.getString("seedId"));
  setRate(Float.parseFloat(object.getString("rate")));
  setPlantingMethod(object.getString("plantingMethod"));
  setName(object.getString("name"));
  setSyncStatus("yes");
 }
}