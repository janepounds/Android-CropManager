package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropItem implements Serializable, CropSpinnerItem{
    String id;
    String name;
    int nPercentage;
    int pPercentage;
    int kPercentage;
    String imageResourceId;

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
}
