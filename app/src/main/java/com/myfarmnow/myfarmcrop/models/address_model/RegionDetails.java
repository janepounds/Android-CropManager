package com.myfarmnow.myfarmcrop.models.address_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionDetails {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("regionType")
    @Expose
    private String regionType;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("belongs_to")
    @Expose
    private String belongs_to;

    public int getId() {
        return id;
    }

    public String getRegionType() {
        return regionType;
    }

    public String getRegion() {
        return region;
    }

    public String getBelongs_to() {
        return belongs_to;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setBelongs_to(String belongs_to) {
        this.belongs_to = belongs_to;
    }
}
