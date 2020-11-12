package com.myfarmnow.myfarmcrop.models.livestock_models.backup;

import com.myfarmnow.myfarmcrop.models.livestock_models.LivestockSpinnerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Comparator;

public class BreedingStocks {
    private String userId;
    private String name;
    private String earTag;
    private String color;
    private String sex;
    private String breed;
    private String dateOfBirth;
    private String source;
    private float weight;
    private String father;
    private String motherDam;
    private String photo;
    private String globalId;
    private String animalType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEarTag() {
        return earTag;
    }

    public void setEarTag(String earTag) {
        this.earTag = earTag;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMotherDam() {
        return motherDam;
    }

    public void setMotherDam(String motherDam) {
        this.motherDam = motherDam;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public BreedingStocks() {
        // Empty constructor required
    }
}
