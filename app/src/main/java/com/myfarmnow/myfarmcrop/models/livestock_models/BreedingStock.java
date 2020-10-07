package com.myfarmnow.myfarmcrop.models.livestock_models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Comparator;

public class BreedingStock implements Serializable, LivestockSpinnerItem {
    private String id;
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
    private String syncStatus = "no";
    private String globalId;
    private String animalType;

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

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public BreedingStock() {
        // Empty constructor required
    }

    public JSONObject toJSON() {

        JSONObject object = new JSONObject();

        try {
            object.put("id", id);
            object.put("globalId", globalId);
            object.put("userId", userId);
            object.put("name", name);
            object.put("earTag", earTag);
            object.put("color", color);
            object.put("sex", sex);
            object.put("breed", breed);
            object.put("dateOfBirth", dateOfBirth);
            object.put("source", source);
            object.put("weight", weight);
            object.put("father", father);
            object.put("motherDam", motherDam);
            object.put("photo", photo);
            object.put("syncStatus", syncStatus);
            object.put("globalId", globalId);
            object.put("animalType", animalType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public BreedingStock(JSONObject object) throws JSONException {
        setUserId(object.getString("userId"));
        setGlobalId(object.getString("id"));
        setName(object.getString("name"));
        setEarTag(object.getString("earTag"));
        setColor(object.getString("color"));
        setSex(object.getString("sex"));
        setBreed(object.getString("breed"));
        setDateOfBirth(object.getString("dateOfBirth"));
        setSource(object.getString("source"));
        setWeight((float) object.getDouble("weight"));
        setFather(object.getString("father"));
        setMotherDam(object.getString("motherDam"));
        setPhoto(object.getString("photo"));
        setAnimalType(object.getString("animalType"));
        setSyncStatus("yes");
    }

 public static Comparator<BreedingStock> nameComparator = (o1, o2) -> {
     String name1 = o1.getName().toLowerCase();
     String name2 = o2.getName().toLowerCase();
     return name1.compareTo(name2);
 };
    public static Comparator<BreedingStock> breedComparator = (o1, o2) -> {
        String breed1 = o1.getBreed().toLowerCase();
        String breed2 = o2.getBreed().toLowerCase();
        return breed1.compareTo(breed2);
    };
}
