package com.myfarmnow.myfarmcrop.models.wallet;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;


public class TokenDetails {

    @SerializedName("headers")
    @Expose
  private JSONArray headers;
    @SerializedName("headers")
    @Expose
  private JSONArray original;
    @SerializedName("headers")
    @Expose
  private String exception;

    public JSONArray getHeaders() {
        return headers;
    }

    public JSONArray getOriginal() {
        return original;
    }

    public String getException() {
        return exception;
    }

    public void setHeaders(JSONArray headers) {
        this.headers = headers;
    }

    public void setOriginal(JSONArray original) {
        this.original = original;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
