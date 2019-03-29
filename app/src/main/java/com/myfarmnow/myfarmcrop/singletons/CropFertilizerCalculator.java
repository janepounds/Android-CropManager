package com.myfarmnow.myfarmcrop.singletons;

import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropItem;

public class CropFertilizerCalculator {
    CropItem crop;
    String cropId;
    String units;
    float area;
    float nitrogenComposition;
    float potassiumComposition;
    float phosphateComposition;

    float npkPrice;
    float potassicPrice;
    float nitrogenousPrice;

    CropFertilizer npkFertilizer, potassicFertilizer, nitrogenousFertilizer;
    private static final CropFertilizerCalculator ourInstance = new CropFertilizerCalculator();

    public static CropFertilizerCalculator getInstance() {
        return ourInstance;
    }

    private CropFertilizerCalculator() {
    }

    public String getCropId() {
        return cropId;
    }

    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getNitrogenComposition() {
        return nitrogenComposition;
    }

    public void setNitrogenComposition(float nitrogenComposition) {
        this.nitrogenComposition = nitrogenComposition;
    }

    public float getPotassiumComposition() {
        return potassiumComposition;
    }

    public void setPotassiumComposition(float potassiumComposition) {
        this.potassiumComposition = potassiumComposition;
    }

    public float getPhosphateComposition() {
        return phosphateComposition;
    }

    public void setPhosphateComposition(float phosphateComposition) {
        this.phosphateComposition = phosphateComposition;
    }

    public float getNpkPrice() {
        return npkPrice;
    }

    public void setNpkPrice(float npkPrice) {
        this.npkPrice = npkPrice;
    }

    public float getPotassicPrice() {
        return potassicPrice;
    }

    public void setPotassicPrice(float potassicPrice) {
        this.potassicPrice = potassicPrice;
    }

    public float getNitrogenousPrice() {
        return nitrogenousPrice;
    }

    public void setNitrogenousPrice(float nitrogenousPrice) {
        this.nitrogenousPrice = nitrogenousPrice;
    }

    public CropFertilizer getNpkFertilizer() {
        return npkFertilizer;
    }

    public void setNpkFertilizer(CropFertilizer npkFertilizer) {
        this.npkFertilizer = npkFertilizer;
    }

    public CropFertilizer getPotassicFertilizer() {
        return potassicFertilizer;
    }

    public void setPotassicFertilizer(CropFertilizer potassicFertilizer) {
        this.potassicFertilizer = potassicFertilizer;
    }

    public CropFertilizer getNitrogenousFertilizer() {
        return nitrogenousFertilizer;
    }

    public void setNitrogenousFertilizer(CropFertilizer nitrogenousFertilizer) {
        this.nitrogenousFertilizer = nitrogenousFertilizer;
    }

    public CropItem getCrop() {
        return crop;
    }

    public void setCrop(CropItem crop) {
        this.crop = crop;
    }
}
