package com.myfarmnow.myfarmcrop.models.retrofitResponses;

import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WalletUserRegistration {
    @SerializedName("status")
    @Expose
 private String status;
    @SerializedName("message")
    @Expose
 private String message;
    @SerializedName("data")
    @Expose
 private ResponseData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    public class ResponseData {
     @SerializedName("firstname")
     @Expose
     private ArrayList<String> firstname;
     @SerializedName("lastname")
     @Expose
     private ArrayList<String> lastname;
     @SerializedName("password")
     @Expose
     private ArrayList<String>  password;
     @SerializedName("phoneNumber")
     @Expose
     private ArrayList<String>  phoneNumber;
     @SerializedName("email")
     @Expose
     private ArrayList<String> email;
     @SerializedName("addressStreet")
     @Expose
     private ArrayList<String>  addressStreet;
     @SerializedName("addressCityOrTown")
     @Expose
     private ArrayList<String> addressCityOrTown;

        public ArrayList<String> getFirstname() {
            return firstname;
        }

        public void setFirstname(ArrayList<String> firstname) {
            this.firstname = firstname;
        }

        public ArrayList<String> getLastname() {
            return lastname;
        }

        public void setLastname(ArrayList<String> lastname) {
            this.lastname = lastname;
        }

        public ArrayList<String> getPassword() {
            return password;
        }

        public ArrayList<String> getEmail() {
            return email;
        }

        public void setEmail(ArrayList<String> email) {
            this.email = email;
        }

        public void setPassword(ArrayList<String> password) {
            this.password = password;
        }

        public ArrayList<String> getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(ArrayList<String> phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public ArrayList<String> getAddressStreet() {
            return addressStreet;
        }

        public void setAddressStreet(ArrayList<String> addressStreet) {
            this.addressStreet = addressStreet;
        }

        public ArrayList<String> getAddressCityOrTown() {
            return addressCityOrTown;
        }

        public void setAddressCityOrTown(ArrayList<String> addressCityOrTown) {
            this.addressCityOrTown = addressCityOrTown;
        }
    }

}
