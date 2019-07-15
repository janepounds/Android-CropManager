package com.myfarmnow.myfarmcrop.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropNote implements Serializable {

    private String id;
    private String date;
    private String parentId;
    private String category;
    private String notes;
    private String isFor;

    public static final String IS_FOR_MACHINE ="machine";
    public static final String IS_FOR_CROP ="crop";

    public CropNote(){

    }

    public CropNote(String id, String date, String parentId, String category, String notes, String isFor) {
        this.id = id;
        this.date = date;
        this.parentId = parentId;
        this.category = category;
        this.notes = notes;
        this.isFor = isFor;
    }

    public CropNote(String date, String parentId, String category, String notes, String isFor) {
        this.date = date;
        this.parentId = parentId;
        this.category = category;
        this.notes = notes;
        this.isFor = isFor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getIsFor() {
        return isFor;
    }

    public void setIsFor(String isFor) {
        this.isFor = isFor;
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
            object.put("date",date);
            object.put("parentId",parentId);
            object.put("category",category);
            object.put("notes",notes);
            object.put("isFor",isFor);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropNote(JSONObject object) throws JSONException{
        setGlobalId(object.getString("id"));
        setParentId(object.getString("parentId"));
        setDate(object.getString("date"));
        setNotes(object.getString("notes"));
        setIsFor(object.getString("isFor"));
        setCategory(object.getString("category"));
        setSyncStatus( "yes");
    }


}
