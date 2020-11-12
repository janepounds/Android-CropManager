package com.myfarmnow.myfarmcrop.models.livestock_models.backup;

import com.myfarmnow.myfarmcrop.models.livestock_models.LivestockSpinnerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Litters {
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
    private String globalId;
    private String animalType;
    private String sireId;
    private String damId;

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

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public Litters() {
        // Empty constructor required
    }

    public String getSireId() {
        return sireId;
    }

    public void setSireId(String sireId) {
        this.sireId = sireId;
    }

    public String getDamId() {
        return damId;
    }

    public void setDamId(String damId) {
        this.damId = damId;
    }
}
