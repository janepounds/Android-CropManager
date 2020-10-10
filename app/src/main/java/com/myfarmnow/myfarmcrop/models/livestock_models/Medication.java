package com.myfarmnow.myfarmcrop.models.livestock_models;

import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Medication implements LivestockSpinnerItem, Serializable {
    private String id;
    private String userId;
    private String medicationDate;
    private String medicationType;
    private String breedingId;
    private String healthCondition;
    private String medicationsName;
    private String manufacturer;
    private float dosage;
    private int treatmentPeriod;
    private String note;
    private String animal;
    private String technicalPersonal;
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

    public String getMedicationDate() {
        return medicationDate;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public void setMedicationDate(String medicationDate) {
        this.medicationDate = medicationDate;
    }

    public String getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(String medicationType) {
        this.medicationType = medicationType;
    }

    public String getBreedingId() {
        return breedingId;
    }

    public void setBreedingId(String breedingId) {
        this.breedingId = breedingId;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getMedicationsName() {
        return medicationsName;
    }

    public void setMedicationsName(String medicationsName) {
        this.medicationsName = medicationsName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public float getDosage() {
        return dosage;
    }

    public void setDosage(float dosage) {
        this.dosage = dosage;
    }

    public int getTreatmentPeriod() {
        return treatmentPeriod;
    }

    public void setTreatmentPeriod(int treatmentPeriod) {
        this.treatmentPeriod = treatmentPeriod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTechnicalPersonal() {
        return technicalPersonal;
    }

    public void setTechnicalPersonal(String technicalPersonal) {
        this.technicalPersonal = technicalPersonal;
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

    public Medication() {
        // Empty constructor required
    }

    public JSONObject toJSON() {

        JSONObject object = new JSONObject();

        try {
            object.put("id", id);
            object.put("userId", userId);
            object.put("medicationDate", medicationDate);
            object.put("medicationType", medicationType);
            object.put("breedingId", breedingId);
            object.put("healthCondition", healthCondition);
            object.put("medicationsName", medicationsName);
            object.put("manufacturer", manufacturer);
            object.put("dosage", dosage);
            object.put("treatmentPeriod", treatmentPeriod);
            object.put("note", note);
            object.put("animal", animal);
            object.put("technicalPersonal", technicalPersonal);
            object.put("syncStatus", syncStatus);
            object.put("globalId", globalId);
            object.put("animalType", animalType);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public Medication(JSONObject object) throws JSONException {
        setUserId(object.getString("userId"));
        setGlobalId(object.getString("id"));
        setMedicationDate(object.getString("medicationDate"));
        setMedicationType(object.getString("medicationType"));
        setBreedingId(object.getString("breedingId"));
        setHealthCondition(object.getString("healthCondition"));
        setMedicationsName(object.getString("medicationsName"));
        setManufacturer(object.getString("manufacturer"));
        setDosage((float) object.getDouble("dosage"));
        setTreatmentPeriod((int) object.getInt("treatmentPeriod"));
        setNote(object.getString("note"));
        setAnimal(object.getString("animal"));
        setTechnicalPersonal(object.getString("technicalPersonal"));
        setAnimalType(object.getString("animalType"));
        setSyncStatus("yes");
    }


}
