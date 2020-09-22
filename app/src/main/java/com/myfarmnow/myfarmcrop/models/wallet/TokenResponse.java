package com.myfarmnow.myfarmcrop.models.wallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.myfarmnow.myfarmcrop.models.user_model.UserDetails;

import java.util.List;

public class TokenResponse {
    @SerializedName("statusCode")
    @Expose
    private int statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private TokenDetails data = null;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TokenDetails getData() {
        return data;
    }

    public void setData(TokenDetails data) {
        this.data = data;
    }


}
