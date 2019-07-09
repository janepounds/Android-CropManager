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




    public CropFertilizer(JSONObject object) throws MissingValueException, JSONException{

        setGlobalId(object.getString("id"));
        setFertilizerName(object.getString("fertilizerName"));
        setType(object.getString("type"));
        setnPercentage(Float.parseFloat(object.getString("nPercentage")));
        setpPercentage(Float.parseFloat(object.getString("pPercentage")));
        setkPercentage(Float.parseFloat(object.getString("kPercentage")));
        setSyncStatus("yes");
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
        return fertilizerName +getComposition();
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getComposition(){
        return "("+getnPercentage() +": "+(int)getpPercentage()+" : "+getkPercentage()+" )";
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
            object.put("fertilizerName",fertilizerName);
            object.put("type",type);
            object.put("nPercentage",nPercentage);
            object.put("pPercentage",pPercentage);
            object.put("kPercentage",kPercentage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }


}
