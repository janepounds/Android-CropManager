package com.myfarmnow.myfarmcrop.models.farmrecords;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropGallery implements Serializable {
    private String id;
    private String parentId;
    private String userId;
    private String photo;
    private String caption;
    private String syncStatus="no";
    private String globalId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public CropGallery(){}


    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("globalId",globalId);
            object.put("caption",caption);
            object.put("parentId",parentId);
            object.put("userId",userId);
            object.put("photo",photo);
            object.put("syncStatus",syncStatus);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public CropGallery(JSONObject object) throws JSONException{
        setGlobalId(object.getString("id"));
        setParentId(object.getString("parentId"));
        setParentId(object.getString("userId"));
        setCaption(object.getString("caption"));
        setPhoto(object.getString("photo"));
        setSyncStatus( "yes");
    }
}
