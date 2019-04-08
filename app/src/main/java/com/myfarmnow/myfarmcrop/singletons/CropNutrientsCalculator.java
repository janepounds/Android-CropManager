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
    float area;

    private static final CropNutrientsCalculator ourInstance = new CropNutrientsCalculator();

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

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public CropItem getCrop() {
        return crop;
    }

    public void setCrop(CropItem crop) {
        this.crop = crop;
    }

    public float computeNitrogenLost(){
        return crop.getnRemoved();
    }
    public float computePotassiumLost(){
        return crop.getkRemoved();
    }
    public float computePhosphateLost(){
        return crop.getpRemoved();
    }

    public boolean isCalculationPossible(){
        if(crop == null){
            return false;
        }else{
            return true;
        }
    }
}
