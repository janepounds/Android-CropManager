package com.myfarmnow.myfarmcrop.database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.myfarmnow.myfarmcrop.fragments.farmrecords.AddFieldFragment;

@Entity
public class FieldsTable {
    @PrimaryKey(autoGenerate = true)
    int id;

    String field_name;
    String field_type;
    String status;
    String unit;
    int field_size;
    int croppable_area;

    public FieldsTable( String field_name, String field_type, String status, String unit, int field_size, int croppable_area) {
        this.field_name = field_name;
        this.field_type = field_type;
        this.status = status;
        this.unit = unit;
        this.field_size = field_size;
        this.croppable_area = croppable_area;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getField_name() {
        return field_name;
    }

    public void setField_name(String field_name) {
        this.field_name = field_name;
    }

    public String getField_type() {
        return field_type;
    }

    public void setField_type(String field_type) {
        this.field_type = field_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getField_size() {
        return field_size;
    }

    public void setField_size(int field_size) {
        this.field_size = field_size;
    }

    public int getCroppable_area() {
        return croppable_area;
    }

    public void setCroppable_area(int croppable_area) {
        this.croppable_area = croppable_area;
    }

}
