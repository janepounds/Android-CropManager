package com.myfarmnow.myfarmcrop.models.livestock_models.backup;

public class Matings {
    private String userId;
    private String matingDate;
    private String maleName;
    private String femaleName;
    private String method;
    private int gestationPeriod;
    private int deliveryAlertDaysBefore;
    private String notes;
    private String syncStatus;
    private String globalId;
    private String animalType;

    public Matings(String userId, String matingDate, String maleName, String femaleName, String method, int gestationPeriod, int deliveryAlertDaysBefore, String notes, String syncStatus, String globalId, String animalType) {
        this.userId = userId;
        this.matingDate = matingDate;
        this.maleName = maleName;
        this.femaleName = femaleName;
        this.method = method;
        this.gestationPeriod = gestationPeriod;
        this.deliveryAlertDaysBefore = deliveryAlertDaysBefore;
        this.notes = notes;
        this.syncStatus = syncStatus;
        this.globalId = globalId;
        this.animalType = animalType;
    }

    public Matings() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMatingDate() {
        return matingDate;
    }

    public void setMatingDate(String matingDate) {
        this.matingDate = matingDate;
    }

    public String getMaleName() {
        return maleName;
    }

    public void setMaleName(String maleName) {
        this.maleName = maleName;
    }

    public String getFemaleName() {
        return femaleName;
    }

    public void setFemaleName(String femaleName) {
        this.femaleName = femaleName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getGestationPeriod() {
        return gestationPeriod;
    }

    public void setGestationPeriod(int gestationPeriod) {
        this.gestationPeriod = gestationPeriod;
    }

    public int getDeliveryAlertDaysBefore() {
        return deliveryAlertDaysBefore;
    }

    public void setDeliveryAlertDaysBefore(int deliveryAlertDaysBefore) {
        this.deliveryAlertDaysBefore = deliveryAlertDaysBefore;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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
}
