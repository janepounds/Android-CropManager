package com.myfarmnow.myfarmcrop.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class CropsTable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String crop;
    String variety;
    String field;
    int field_id;
    String season;
    String planting_date;
    int field_size;
    String units;
    int estimated_yield;
    int estimated_revenue;

    public CropsTable(String crop, String variety, String field,int field_id, String season, String planting_date, int field_size, String units, int estimated_yield, int estimated_revenue) {
        this.crop = crop;
        this.variety = variety;
        this.field = field;
        this.field_id = field_id;
        this.season = season;
        this.planting_date = planting_date;
        this.field_size = field_size;
        this.units = units;
        this.estimated_yield = estimated_yield;
        this.estimated_revenue = estimated_revenue;
    }

    public String getCrop() {
        return crop;
    }

    public void setCrop(String crop) {
        this.crop = crop;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getField_id() {
        return field_id;
    }

    public void setField_id(int field_id) {
        this.field_id = field_id;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getPlanting_date() {
        return planting_date;
    }

    public void setPlanting_date(String planting_date) {
        this.planting_date = planting_date;
    }

    public int getFiels_size() {
        return field_size;
    }

    public void setFiels_size(int field_size) {
        this.field_size = field_size;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public int getEstimated_yield() {
        return estimated_yield;
    }

    public void setEstimated_yield(int estimated_yield) {
        this.estimated_yield = estimated_yield;
    }

    public int getEstimated_revenue() {
        return estimated_revenue;
    }

    public void setEstimated_revenue(int estimated_revenue) {
        this.estimated_revenue = estimated_revenue;
    }



}
