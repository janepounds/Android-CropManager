package com.myfarmnow.myfarmcrop.singletons;

import android.util.Log;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.Echelon3DEquationsCalculator;
import com.myfarmnow.myfarmcrop.models.FertilizerCalculationException;


import java.util.HashMap;

public class CropFertilizerCalculator {
    CropItem crop;
    String units;
    float area;
    float nitrogenComposition;
    float potassiumComposition;
    float phosphateComposition;
    float npkPrice;
    float potassicPrice;
    float nitrogenousPrice;

    CropFertilizer npkFertilizer, potassicFertilizer, nitrogenousFertilizer;
    HashMap <String,Double> solution = null;
    private static final CropFertilizerCalculator ourInstance = new CropFertilizerCalculator();

    public static CropFertilizerCalculator getInstance() {
        return ourInstance;
    }

    private CropFertilizerCalculator() {
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
        resetComputation();
    }

    public float getPotassiumComposition() {
        return potassiumComposition;
    }

    public void setPotassiumComposition(float potassiumComposition) {
        this.potassiumComposition = potassiumComposition;
        resetComputation();
    }

    public float getPhosphateComposition() {
        return phosphateComposition;
    }

    public void setPhosphateComposition(float phosphateComposition) {
        this.phosphateComposition = phosphateComposition;
        resetComputation();
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
        resetComputation();
    }

    public CropFertilizer getPotassicFertilizer() {
        return potassicFertilizer;
    }

    public void setPotassicFertilizer(CropFertilizer potassicFertilizer) {
        this.potassicFertilizer = potassicFertilizer;
        resetComputation();
    }

    public CropFertilizer getNitrogenousFertilizer() {
        return nitrogenousFertilizer;
    }

    public void setNitrogenousFertilizer(CropFertilizer nitrogenousFertilizer) {
        this.nitrogenousFertilizer = nitrogenousFertilizer;
        resetComputation();
    }

    public CropItem getCrop() {
        return crop;
    }

    public void setCrop(CropItem crop) {
        this.crop = crop;
    }

    public boolean isCalculationPossible(){
        return determineMissingNutrient() ==-1;
    }

    public int determineMissingNutrient(){

        float nitrogen =0;
        float phosphate =0;
        float potassium =0;
        if(npkFertilizer != null){
            nitrogen+=npkFertilizer.getnPercentage();
            phosphate+=npkFertilizer.getpPercentage();
            potassium+=npkFertilizer.getkPercentage();
        }
        if(nitrogenousFertilizer != null){
            nitrogen+=nitrogenousFertilizer.getnPercentage();
            phosphate+=nitrogenousFertilizer.getpPercentage();
            potassium+=nitrogenousFertilizer.getkPercentage();
        }
        if(potassicFertilizer != null){
            nitrogen+=potassicFertilizer.getnPercentage();
            phosphate+=potassicFertilizer.getpPercentage();
            potassium+=potassicFertilizer.getkPercentage();
        }
        System.out.println("Phosphate: "+phosphate+" : "+nitrogen+" : "+potassium);
        if(nitrogen ==0 && nitrogenComposition >0){
            return R.string.no_nitrgen_selected;
        }else if(phosphate ==0 && phosphateComposition >0){
            return R.string.no_phosphate_seleceted;
        }
        else if(potassium ==0 && potassiumComposition >0){
            return R.string.no_potassium_selected;
        }



        return -1;
    }

    private double[][] generateMatrix(){
        double nitrogen[] = new double[4];
        double phosphate[]= new double[4];
        double potassium[]= new double[4];

        //get  compositions
        if(getNpkFertilizer() != null){
            nitrogen[0] =getNpkFertilizer().getnPercentage()/100.0;
            phosphate[0] =getNpkFertilizer().getpPercentage()/100.0;
            potassium[0] =getNpkFertilizer().getkPercentage()/100.0;
        }
        else{
            nitrogen[0] =0;
            phosphate[0] =0;
            potassium[0] =0;
        }
        if(getNitrogenousFertilizer() != null){
            nitrogen[1] =getNitrogenousFertilizer().getnPercentage()/100.0;
            phosphate[1] =getNitrogenousFertilizer().getpPercentage()/100.0;
            potassium[1] =getNitrogenousFertilizer().getkPercentage()/100.0;
        }
        else{
            nitrogen[1] =0;
            phosphate[1] =0;
            potassium[1] =0;
        }
        if(getPotassicFertilizer() != null){
            nitrogen[2] =getPotassicFertilizer().getnPercentage()/100.0;
            phosphate[2] =getPotassicFertilizer().getpPercentage()/100.0;
            potassium[2] =getPotassicFertilizer().getkPercentage()/100.0;
        }
        else{
            nitrogen[2] =0;
            phosphate[2] =0;
            potassium[2] =0;
        }

        //total
        nitrogen[3] =nitrogenComposition;
        phosphate[3] =phosphateComposition;
        potassium[3] =potassiumComposition;

        return new double[][]{
                nitrogen,
                phosphate,
                potassium
        };
    }

    public HashMap calculateQuantities(){
        Echelon3DEquationsCalculator calculator = new Echelon3DEquationsCalculator(generateMatrix(),3,4);
        return calculator.solveEquation();
    }

    public double computeTotalCost() throws FertilizerCalculationException {
        return computeNitrogenousCost()+computePotassicCost()+computeNpkCost();
    }

    public double computeNpkCost() throws FertilizerCalculationException{
        return computeNpkQuantity()*npkPrice;
    }
    public double computePotassicCost() throws FertilizerCalculationException{
        return computePotassicQuantity()*potassicPrice;
    }
    public double computeNitrogenousCost() throws FertilizerCalculationException{
        return computeNitrogenousQuantity()*nitrogenousPrice;
    }
    public double computeNpkQuantity() throws FertilizerCalculationException{
        if (solution == null){
            solution = calculateQuantities();
            if (solution == null){
                throw new FertilizerCalculationException("Error in Calculations");
            }

        }

        return Math.round(solution.get("x")*area*100)/100;


    }
    public double computePotassicQuantity() throws FertilizerCalculationException{
        if (solution == null){
            solution = calculateQuantities();
            if (solution == null){
                throw new FertilizerCalculationException("Error in Calculations");
            }

        }

        return Math.round(solution.get("z")*area*100)/100;

    }
    public double computeNitrogenousQuantity() throws FertilizerCalculationException{
        if (solution == null){
            solution = calculateQuantities();
            if (solution == null){
                throw new FertilizerCalculationException("Error in Calculations");
            }

        }

        return Math.round(solution.get("y")*area*100)/100;

    }

    public void resetComputation(){
        solution=null;
    }
}
