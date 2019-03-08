package com.myfarmnow.myfarmcrop.models;

import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Crop implements Serializable {
 private String id;
 private String userId;
 private int croppingYear;
 private String fieldId;
 private String dateSown;
 private String variety;
 private String growingCycle;
 private float area;
 private float cost;
 private String operator;
 private String seedId;
 private float rate;
 private String plantingMethod;
 private String name;
 private String age;

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
  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
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
   sDate.add(Calendar.DAY_OF_MONTH, 1);
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
}