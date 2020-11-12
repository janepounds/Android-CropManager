package com.myfarmnow.myfarmcrop.models.livestock_models.backup;

import com.myfarmnow.myfarmcrop.models.livestock_models.LivestockSpinnerItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Medications {
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
    private String globalId;
    private String animalType;

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

    public Medications() {
        // Empty constructor required
    }
}
