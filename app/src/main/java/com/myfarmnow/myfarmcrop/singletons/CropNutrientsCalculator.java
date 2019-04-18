package com.myfarmnow.myfarmcrop.singletons;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.Echelon3DEquationsCalculator;
import com.myfarmnow.myfarmcrop.models.FertilizerCalculationException;

import java.util.HashMap;

public class CropNutrientsCalculator {
    CropItem crop;
    String units;
    float estimatedYield;

    CropFertilizer nitrogenSourceFertilizer,phosphorusSourceFertilizer,potassiumSourceFertilizer;

    boolean economicImpactRequired=false;

    private static final CropNutrientsCalculator ourInstance = new CropNutrientsCalculator();
    private float nitrogenSourcePrice;
    private float phosphorusSourcePrice;
    private float potassiumSourcePrice;

    public static CropNutrientsCalculator getInstance() {
        return ourInstance;
    }

    private CropNutrientsCalculator() {
    }


    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public float getEstimatedYield() {
        return estimatedYield;
    }

    public void setEstimatedYield(float estimatedYield) {
        this.estimatedYield = estimatedYield;
    }

    public CropItem getCrop() {
        return crop;
    }

    public void setCrop(CropItem crop) {
        this.crop = crop;
    }

    public float computeNitrogenLost(){
        return crop.getnRemoved()*getEstimatedYield();
    }
    public float computePotassiumLost(){
        return crop.getkRemoved()*getEstimatedYield();
    }
    public float computePhosphateLost(){
        return crop.getpRemoved()*getEstimatedYield();
    }

    public float computeNitrogenLostValue(){
        if(nitrogenSourceFertilizer != null){
            if(nitrogenSourceFertilizer.getnPercentage()!=0){
                return (computeNitrogenLost()*100*getNitrogenSourcePrice())/nitrogenSourceFertilizer.getnPercentage();
            }else{

                return 0;
            }
        }else{
            System.out.println("Nitrogen is NULL");
            return 0;
        }
    }
    public float computePhosphorusLostValue(){
        if(phosphorusSourceFertilizer != null){
            if(phosphorusSourceFertilizer.getpPercentage()!=0){
                return (computePhosphateLost()*100*getPhosphorusSourcePrice())/phosphorusSourceFertilizer.getpPercentage();
            }else{
                return 0;
            }

        }else{
            return 0;
        }
    }
    public float computePotassiumLostValue(){
        if(potassiumSourceFertilizer != null){
            if(potassiumSourceFertilizer.getkPercentage()>0){
                return (computePotassiumLost()*100*getPotassiumSourcePrice())/potassiumSourceFertilizer.getkPercentage();
            }else{
                return 0;
            }

        }else{
            return 0;
        }
    }

    public boolean isCalculationPossible(){
        if(crop == null){
            return false;
        }else{
            return true;
        }
    }

    public CropFertilizer getNitrogenSourceFertilizer() {
        return nitrogenSourceFertilizer;
    }

    public void setNitrogenSourceFertilizer(CropFertilizer nitrogenSourceFertilizer) {
        this.nitrogenSourceFertilizer = nitrogenSourceFertilizer;
    }

    public CropFertilizer getPhosphorusSourceFertilizer() {
        return phosphorusSourceFertilizer;
    }

    public void setPhosphorusSourceFertilizer(CropFertilizer phosphorusSourceFertilizer) {
        this.phosphorusSourceFertilizer = phosphorusSourceFertilizer;
    }

    public CropFertilizer getPotassiumSourceFertilizer() {
        return potassiumSourceFertilizer;
    }

    public void setPotassiumSourceFertilizer(CropFertilizer potassiumSourceFertilizer) {
        this.potassiumSourceFertilizer = potassiumSourceFertilizer;
    }

    public boolean isEconomicImpactRequired() {
        return economicImpactRequired;
    }

    public void setEconomicImpactRequired(boolean economicImpactRequired) {
        this.economicImpactRequired = economicImpactRequired;
    }

    public void setNitrogenSourcePrice(float nitrogenSourcePrice) {
        this.nitrogenSourcePrice = nitrogenSourcePrice;
    }

    public float getNitrogenSourcePrice() {
        return nitrogenSourcePrice;
    }

    public void setPhosphorusSourcePrice(float phosphorusSourcePrice) {
        this.phosphorusSourcePrice = phosphorusSourcePrice;
    }

    public float getPhosphorusSourcePrice() {
        return phosphorusSourcePrice;
    }

    public void setPotassiumSourcePrice(float potassiumSourcePrice) {
        this.potassiumSourcePrice = potassiumSourcePrice;
    }

    public float getPotassiumSourcePrice() {
        return potassiumSourcePrice;
    }
}
