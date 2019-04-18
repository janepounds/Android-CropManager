package com.myfarmnow.myfarmcrop.models;

import java.io.Serializable;

public class CropMachineService implements CropSpinnerItem, Serializable {

    String id;
    String machineId;
    String date;
    String title;
    String type;

    String description;
    String recurrence;
    String reminders;
    String employeeName;
    String serviceType;
    private float frequency;
    private String repeatUntil;
    private float cost;
    public float currentHours;
    private String daysBefore;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(String recurrence) {
        this.recurrence = recurrence;
    }

    public String getReminders() {
        return reminders;
    }

    public void setReminders(String reminders) {
        this.reminders = reminders;
    }

    @Override
    public String toString() {
        return getEmployeeName();
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }

    public String getRepeatUntil() {
        return repeatUntil;
    }

    public void setRepeatUntil(String repeatUntil) {
        this.repeatUntil = repeatUntil;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getDaysBefore() {
        return daysBefore;
    }

    public void setDaysBefore(String daysBefore) {
        this.daysBefore = daysBefore;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public float getCurrentHours() {
        return currentHours;
    }

    public void setCurrentHours(float currentHours) {
        this.currentHours = currentHours;
    }
}

