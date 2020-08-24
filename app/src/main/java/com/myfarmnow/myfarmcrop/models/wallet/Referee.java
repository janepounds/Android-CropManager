package com.myfarmnow.myfarmcrop.models.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Referee implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("nationalIDPic")
    @Expose
    private String nationalIDPic;
    public Referee(JSONObject loanObject) throws JSONException {

        setId(loanObject.getString("id"));
        setName(loanObject.getString("name"));
        setStatus(loanObject.getString("status"));
        setPhoneNumber(loanObject.getString("phoneNumber"));
        setNationalIDPic(loanObject.getString("nationalIDPic"));


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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNationalIDPic() {
        return nationalIDPic;
    }

    public void setNationalIDPic(String nationalIDPic) {
        this.nationalIDPic = nationalIDPic;
    }

}