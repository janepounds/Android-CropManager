package com.myfarmnow.myfarmcrop.models.livestock_models;

import org.json.JSONException;
import org.json.JSONObject;

public class Litter {
    private String id;
    private String userId;
    private String dateOfBirth;
    private int litterSize;
    private String breedingId;
    private String motherDam;
    private String fatherSire;
    private int bornAlive;
    private int bornDead;
    private int noOfMale;
    private int noOfFemale;
    private int weaning;
    private int weaningAlert;
    private String syncStatus="no";
    private String globalId;

    public String getMotherDam() {
        return motherDam;
    }

    public void setMotherDam(String motherDam) {
        this.motherDam = motherDam;
    }

    public String getFatherSire() {
        return fatherSire;
    }

    public void setFatherSire(String fatherSire) {
        this.fatherSire = fatherSire;
    }

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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getLitterSize() {
        return litterSize;
    }

    public void setLitterSize(int litterSize) {
        this.litterSize = litterSize;
    }

    public String getBreedingId() {
        return breedingId;
    }

    public void setBreedingId(String breedingId) {
        this.breedingId = breedingId;
    }

    public int getBornAlive() {
        return bornAlive;
    }

    public void setBornAlive(int bornAlive) {
        this.bornAlive = bornAlive;
    }

    public int getBornDead() {
        return bornDead;
    }

    public void setBornDead(int bornDead) {
        this.bornDead = bornDead;
    }

    public int getNoOfMale() {
        return noOfMale;
    }

    public void setNoOfMale(int noOfMale) {
        this.noOfMale = noOfMale;
    }

    public int getNoOfFemale() {
        return noOfFemale;
    }

    public void setNoOfFemale(int noOfFemale) {
        this.noOfFemale = noOfFemale;
    }

    public int getWeaning() {
        return weaning;
    }

    public void setWeaning(int weaning) {
        this.weaning = weaning;
    }

    public int getWeaningAlert() {
        return weaningAlert;
    }

    public void setWeaningAlert(int weaningAlert) {
        this.weaningAlert = weaningAlert;
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

    public Litter(){

    }


    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("userId",userId);
            object.put("dateOfBirth",dateOfBirth);
            object.put("litterSize",litterSize);
            object.put("breedingId",breedingId);
            object.put("bornAlive",bornAlive);
            object.put("bornDead",bornDead);
            object.put("motherDam",motherDam);
            object.put("fatherSire",fatherSire);
            object.put("noOfMale",noOfMale);
            object.put("noOfFemale",noOfFemale);
            object.put("weaning",weaning);
            object.put("weaningAlert",weaningAlert);
            object.put("syncStatus",syncStatus);
            object.put("globalId",globalId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    public Litter(JSONObject object) throws JSONException {
        setUserId(object.getString("userId"));
        setGlobalId(object.getString("id"));
        setDateOfBirth(object.getString("dateOfBirth"));
        setLitterSize(Integer.parseInt(object.getString("earTag")));
        setBreedingId(object.getString("breedingId"));
        setBornAlive(Integer.parseInt(object.getString("bornAlive")));
        setBornDead(Integer.parseInt(object.getString("bornDead")));
        setNoOfMale(Integer.parseInt(object.getString("noOfMale")));
        setNoOfFemale(Integer.parseInt(object.getString("noOfFemale")));
        setMotherDam(object.getString("motherDam"));
        setFatherSire(object.getString("fatherSire"));
        setWeaning(Integer.parseInt(object.getString("weaning")));
        setWeaningAlert(Integer.parseInt(object.getString("weaningAlert")));
        setSyncStatus("yes");
    }
}
