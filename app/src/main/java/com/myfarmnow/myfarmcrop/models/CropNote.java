package com.myfarmnow.myfarmcrop.models;

public class CropNote {

    private String id;
    private String date;
    private String parentId;
    private String category;
    private String notes;
    private String isFor;

    private static final String IS_FOR_MACHINE ="machine";
    private static final String IS_FOR_CROP ="crop";

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
}
