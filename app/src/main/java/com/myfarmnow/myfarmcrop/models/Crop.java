package com.myfarmnow.myfarmcrop.models;

public class Crop {
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
 private String rate;
 private String plantingMethod;

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

 public String getRate() {
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

 public void setRate(String rate) {
  this.rate = rate;
 }

 public void setPlantingMethod(String plantingMethod) {
  this.plantingMethod = plantingMethod;
 }
}