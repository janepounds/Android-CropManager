package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropItem implements Serializable, CropSpinnerItem{
    String id;
    String name;
    int nPercentage;
    int pPercentage;
    int kPercentage;
    String imageResourceId;

    float nRemoved;
    float pRemoved;
    float kRemoved;

    String isFor; //this model will hold data meant for nutrient removal or fertilizer calculation.

    public static final String IS_FOR_NUTRIENT_REMOVAL ="nutrientRemoval";

    public CropItem(String name, int nPercentage, int pPercentage, int kPercentage) {
        this.name = name;
        this.nPercentage = nPercentage;
        this.pPercentage = pPercentage;
        this.kPercentage = kPercentage;
    }

    public CropItem(String name, int nPercentage, int pPercentage, int kPercentage, String imageResourceId) {
        this.name = name;
        this.nPercentage = nPercentage;
        this.pPercentage = pPercentage;
        this.kPercentage = kPercentage;
        this.imageResourceId = imageResourceId;
    }

    public CropItem(String isFor,String name, double nRemoved, double pRemoved, double kRemoved) {
        this.name = name;
        this.nRemoved = (float) nRemoved;
        this.pRemoved = (float) pRemoved;
        this.kRemoved = (float) kRemoved;
        this.isFor = isFor;
    }

    public CropItem(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getnPercentage() {
        return nPercentage;
    }

    public void setnPercentage(int nPercentage) {
        this.nPercentage = nPercentage;
    }

    public int getpPercentage() {
        return pPercentage;
    }

    public void setpPercentage(int pPercentage) {
        this.pPercentage = pPercentage;
    }

    public int getkPercentage() {
        return kPercentage;
    }

    public void setkPercentage(int kPercentage) {
        this.kPercentage = kPercentage;
    }

    public String getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(String imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public float getnRemoved() {
        return nRemoved;
    }

    public void setnRemoved(float nRemoved) {
        this.nRemoved = nRemoved;
    }

    public float getpRemoved() {
        return pRemoved;
    }

    public void setpRemoved(float pRemoved) {
        this.pRemoved = pRemoved;
    }

    public float getkRemoved() {
        return kRemoved;
    }

    public void setkRemoved(float kRemoved) {
        this.kRemoved = kRemoved;
    }

    public String getIsFor() {
        return isFor;
    }

    public void setIsFor(String isFor) {
        this.isFor = isFor;
    }

    public String toString(){
        return name;
    }

    @Override
    public String getUnits() {
        return null;
    }
}
