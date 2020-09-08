package com.myfarmnow.myfarmcrop.models.farmrecords;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Entity
public class CropField implements Serializable, CropSpinnerItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    String field_name;
    String field_type;
    String status;
    String unit;
    String field_size;
    String croppable_area;
    String globalId;
    int userId;


    public CropField(String field_name, String field_type, String status, String unit, String field_size, String croppable_area, int userId) {
        this.field_name = field_name;
        this.field_type = field_type;
        this.status = status;
        this.unit = unit;
        this.field_size = field_size;
        this.croppable_area = croppable_area;
        this.userId=userId;
    }
    public CropField(JSONObject object) throws JSONException {

        setGlobalId(object.getString("id"));
        setUserId(Integer.parseInt(object.getString("userId")));
        setField_name(object.getString("fieldName"));
        setField_type(object.getString("fieldType"));
//        setSoilCategory(object.getString("soilCategory"));
//        setLayoutType(object.getString("layoutType"));
//        setWatercourse(object.getString("watercourse"));
//        setSoilType(object.getString("soilType"));
        setStatus(object.getString("status"));
        setField_size( object.getString("totalArea") );
        setCroppable_area( object.getString("croppableArea") );
        setUnit(object.getString("units"));
        setSyncStatus("yes");

    }

    public CropField(){

    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getGlobalId() {
        return globalId;
    }

    public int getUserId() {
        return userId;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getField_size() {
        return field_size;
    }

    public void setField_size(String field_size) {
        this.field_size = field_size;
    }

    public String getCroppable_area() {
        return croppable_area;
    }

    public void setCroppable_area(String croppable_area) {
        this.croppable_area = croppable_area;
    }

    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("fieldName",field_name);
            //object.put("soilCategory",soilCategory);
            object.put("fieldType",field_type);
            //object.put("layoutType",layoutType);
            object.put("status",status);
            //object.put("soilType",soilType);
            //object.put("watercourse",watercourse);
            object.put("totalArea",field_size);
            object.put("croppableArea",croppable_area);
            object.put("globalId",globalId);
            object.put("units",unit);
            object.put("syncStatus",syncStatus);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    private String syncStatus="no";
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }


}
