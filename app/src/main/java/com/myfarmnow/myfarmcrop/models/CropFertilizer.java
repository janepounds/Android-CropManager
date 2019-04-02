package com.myfarmnow.myfarmcrop.models;

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropFertilizer implements Serializable,CropSpinnerItem {
    String id="";

    String fertilizerName="";

    String type="";
    float nPercentage=0;
    float kPercentage=0;
    float pPercentage=0;


    public CropFertilizer(String fertilizerName, String type, double nPercentage, double pPercentage, double kPercentage) {
        this.fertilizerName = fertilizerName;
        this.type = type;
        this.nPercentage = (float)nPercentage;
        this.kPercentage = (float)kPercentage;
        this.pPercentage = (float)pPercentage;
    }

    public CropFertilizer(){

    }


    public CropFertilizer(JSONObject fertilizerJson) throws MissingValueException{

    }


    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName;
    }








    public String getFertilizerName() {
        return fertilizerName;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getnPercentage() {
        return nPercentage;
    }

    public void setnPercentage(float nPercentage) {
        this.nPercentage = nPercentage;
    }

    public float getkPercentage() {
        return kPercentage;
    }

    public void setkPercentage(float kPercentage) {
        this.kPercentage = kPercentage;
    }

    public float getpPercentage() {
        return pPercentage;
    }

    public void setpPercentage(float pPercentage) {
        this.pPercentage = pPercentage;
    }




    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return fertilizerName;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getComposition(){
        return "("+getnPercentage() +": "+getpPercentage()+" : "+getkPercentage()+" )";
    }
}
