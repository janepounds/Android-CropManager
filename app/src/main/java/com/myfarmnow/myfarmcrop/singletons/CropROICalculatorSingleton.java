package com.myfarmnow.myfarmcrop.singletons;

public class CropROICalculatorSingleton {

    private static CropROICalculatorSingleton currentCalculator;

    String step1YieldUnits="Units";
    float step1Price =0;
    float step1YieldPerAcre =0;
    float step1OtherIncome =0;
    float step1GrossRevenue=0;

    float step2SeedAndTreatment =0;
    float step2Fertilizer =0;
    float step2Herbicide =0;
    float step2Insecticide =0;
    float step2Fuel =0;
    float step2MachineryOperating =0;
    float step2MachineryLease =0;
    float step2LandRental =0;
    float step2CropInsurancePremium =0;
    float step2DryingCosts =0;
    float step2OtherCosts =0;

    float step3BuildingRepair =0;
    float step3machineDepreciation =0;
    float step3PropertyTaxes=0;
    float step3BusinessOverhead=0;
    float step3BuildingDepreciation=0;
    float step3TotalOtherExpenses=0;

    float step4OwnerAllowanceQty=0;
    float step4OwnerAllowanceCost=0;
    float step4SalariedEmployeeCost=0;
    float step4SalariedEmployeeQty=0;
    float step4CasualEmployeeCost=0;
    float step4CasualEmployeeNumber=0;
    float step4CasualEmployeeHours=0;
    float step4CasualEmployeeWeeks=0;
    float step4CasualEmployeeTotal=0;

    float step5MachineryInvestment=0;
    float step5BuildingInvestment=0;
    float step5LandInvestment=0;




    private CropROICalculatorSingleton(){

    }
    public static CropROICalculatorSingleton getInstance(){
        if(currentCalculator == null){
            currentCalculator = new CropROICalculatorSingleton();
        }

        return currentCalculator;
    }

    public String getStep1YieldUnits() {
        return step1YieldUnits;
    }

    public void setStep1YieldUnits(String step1YieldUnits) {
        this.step1YieldUnits = step1YieldUnits;
    }

    public float getStep1Price() {
        return step1Price;
    }

    public void setStep1Price(float step1Price) {
        this.step1Price = step1Price;
    }

    public float getStep1YieldPerAcre() {
        return step1YieldPerAcre;
    }

    public void setStep1YieldPerAcre(float step1YieldPerAcre) {
        this.step1YieldPerAcre = step1YieldPerAcre;
    }

    public float getStep1OtherIncome() {
        return step1OtherIncome;
    }

    public void setStep1OtherIncome(float step1OtherIncome) {
        this.step1OtherIncome = step1OtherIncome;
    }

    public float getStep1GrossRevenue() {
        return step1GrossRevenue;
    }

    public void setStep1GrossRevenue(float step1GrossRevenue) {
        this.step1GrossRevenue = step1GrossRevenue;
    }

    public float getStep2SeedAndTreatment() {
        return step2SeedAndTreatment;
    }

    public void setStep2SeedAndTreatment(float step2SeedAndTreatment) {
        this.step2SeedAndTreatment = step2SeedAndTreatment;
    }

    public float getStep2Fertilizer() {
        return step2Fertilizer;
    }

    public void setStep2Fertilizer(float step2Fertilizer) {
        this.step2Fertilizer = step2Fertilizer;
    }

    public float getStep2Herbicide() {
        return step2Herbicide;
    }

    public void setStep2Herbicide(float step2Herbicide) {
        this.step2Herbicide = step2Herbicide;
    }

    public float getStep2Insecticide() {
        return step2Insecticide;
    }

    public void setStep2Insecticide(float step2Insecticide) {
        this.step2Insecticide = step2Insecticide;
    }

    public float getStep2Fuel() {
        return step2Fuel;
    }

    public void setStep2Fuel(float step2Fuel) {
        this.step2Fuel = step2Fuel;
    }

    public float getStep2MachineryOperating() {
        return step2MachineryOperating;
    }

    public void setStep2MachineryOperating(float step2MachineryOperating) {
        this.step2MachineryOperating = step2MachineryOperating;
    }

    public float getStep2MachineryLease() {
        return step2MachineryLease;
    }

    public void setStep2MachineryLease(float step2MachineryLease) {
        this.step2MachineryLease = step2MachineryLease;
    }

    public float getStep2LandRental() {
        return step2LandRental;
    }

    public void setStep2LandRental(float step2LandRental) {
        this.step2LandRental = step2LandRental;
    }

    public float getStep2CropInsurancePremium() {
        return step2CropInsurancePremium;
    }

    public void setStep2CropInsurancePremium(float step2CropInsurancePremium) {
        this.step2CropInsurancePremium = step2CropInsurancePremium;
    }

    public float getStep2DryingCosts() {
        return step2DryingCosts;
    }

    public void setStep2DryingCosts(float step2DryingCosts) {
        this.step2DryingCosts = step2DryingCosts;
    }

    public float getStep2OtherCosts() {
        return step2OtherCosts;
    }

    public void setStep2OtherCosts(float step2OtherCosts) {
        this.step2OtherCosts = step2OtherCosts;
    }

    public float getStep3BuildingRepair() {
        return step3BuildingRepair;
    }

    public void setStep3BuildingRepair(float step3BuildingRepair) {
        this.step3BuildingRepair = step3BuildingRepair;
    }

    public float getStep3machineDepreciation() {
        return step3machineDepreciation;
    }

    public void setStep3machineDepreciation(float step3machineDepreciation) {
        this.step3machineDepreciation = step3machineDepreciation;
    }

    public float getStep3PropertyTaxes() {
        return step3PropertyTaxes;
    }

    public void setStep3PropertyTaxes(float step3PropertyTaxes) {
        this.step3PropertyTaxes = step3PropertyTaxes;
    }

    public float getStep3BusinessOverhead() {
        return step3BusinessOverhead;
    }

    public void setStep3BusinessOverhead(float step3BusinessOverhead) {
        this.step3BusinessOverhead = step3BusinessOverhead;
    }

    public float getStep3BuildingDepreciation() {
        return step3BuildingDepreciation;
    }

    public void setStep3BuildingDepreciation(float step3BuildingDepreciation) {
        this.step3BuildingDepreciation = step3BuildingDepreciation;
    }

    public float getStep3TotalOtherExpenses() {
        return step3TotalOtherExpenses;
    }

    public void setStep3TotalOtherExpenses(float step3TotalOtherExpenses) {
        this.step3TotalOtherExpenses = step3TotalOtherExpenses;
    }

    public float getStep4OwnerAllowanceQty() {
        return step4OwnerAllowanceQty;
    }

    public void setStep4OwnerAllowanceQty(float step4OwnerAllowanceQty) {
        this.step4OwnerAllowanceQty = step4OwnerAllowanceQty;
    }

    public float getStep4OwnerAllowanceCost() {
        return step4OwnerAllowanceCost;
    }

    public void setStep4OwnerAllowanceCost(float step4OwnerAllowanceCost) {
        this.step4OwnerAllowanceCost = step4OwnerAllowanceCost;
    }

    public float getStep4SalariedEmployeeCost() {
        return step4SalariedEmployeeCost;
    }

    public void setStep4SalariedEmployeeCost(float step4SalariedEmployeeCost) {
        this.step4SalariedEmployeeCost = step4SalariedEmployeeCost;
    }

    public float getStep4SalariedEmployeeQty() {
        return step4SalariedEmployeeQty;
    }

    public void setStep4SalariedEmployeeQty(float step4SalariedEmployeeQty) {
        this.step4SalariedEmployeeQty = step4SalariedEmployeeQty;
    }

    public float getStep4CasualEmployeeCost() {
        return step4CasualEmployeeCost;
    }

    public void setStep4CasualEmployeeCost(float step4CasualEmployeeCost) {
        this.step4CasualEmployeeCost = step4CasualEmployeeCost;
    }

    public float getStep4CasualEmployeeHours() {
        return step4CasualEmployeeHours;
    }

    public void setStep4CasualEmployeeHours(float step4CasualEmployeeHours) {
        this.step4CasualEmployeeHours = step4CasualEmployeeHours;
    }

    public float getStep4CasualEmployeeWeeks() {
        return step4CasualEmployeeWeeks;
    }

    public void setStep4CasualEmployeeWeeks(float step4CasualEmployeeWeeks) {
        this.step4CasualEmployeeWeeks = step4CasualEmployeeWeeks;
    }

    public float getStep4CasualEmployeeTotal() {
        return step4CasualEmployeeTotal;
    }

    public void setStep4CasualEmployeeTotal(float step4CasualEmployeeTotal) {
        this.step4CasualEmployeeTotal = step4CasualEmployeeTotal;
    }

    public float step4TotalExpenses(){
        return  computeStep4TotalLabourCosts() + computeStep3TotalOverheadCosts()+computeStep2TotalVariableCosts();
    }

    public float getStep5MachineryInvestment() {
        return step5MachineryInvestment;
    }

    public void setStep5MachineryInvestment(float step5MachineryInvestment) {
        this.step5MachineryInvestment = step5MachineryInvestment;
    }

    public float getStep5BuildingInvestment() {
        return step5BuildingInvestment;
    }

    public void setStep5BuildingInvestment(float step5BuildingInvestment) {
        this.step5BuildingInvestment = step5BuildingInvestment;
    }

    public float getStep5LandInvestment() {
        return step5LandInvestment;
    }

    public void setStep5LandInvestment(float step5LandInvestment) {
        this.step5LandInvestment = step5LandInvestment;
    }

    public float computeStep4TotalExpenses(){
        return computeStep2TotalVariableCosts()+computeStep3TotalOverheadCosts()+computeStep4TotalLabourCosts();
    }
    public float computeStep4TotalCasualEmployeeCost(){
        return  step4CasualEmployeeNumber*(step4CasualEmployeeCost *step4CasualEmployeeHours*step4CasualEmployeeWeeks);
    }

    public float computeStep4TotalLabourCosts(){

        return (step4OwnerAllowanceQty* step4OwnerAllowanceCost)+
                (step4SalariedEmployeeCost* step4SalariedEmployeeQty)+
                computeStep4TotalCasualEmployeeCost();
    }

    public float computeStep3TotalOverheadCosts(){
        return  step3BuildingRepair +
                step3machineDepreciation +
                step3PropertyTaxes+
                step3BusinessOverhead+
                step3BuildingDepreciation+
                step3TotalOtherExpenses;
    }
    public float computeStep2TotalVariableCosts(){
        return  step2SeedAndTreatment +
                step2Fertilizer +
                step2Herbicide +
                step2Insecticide +
                step2Fuel +
                step2MachineryOperating +
                step2MachineryLease+
                step2LandRental +
                step2CropInsurancePremium +
                step2DryingCosts +
                step2OtherCosts ;
    }

    public float computeStep5TotalCapitalInvestment(){
        return step5MachineryInvestment+step5BuildingInvestment+step5LandInvestment;
    }

    public float computeStep1GrossRevenue(){ //C
        return step1OtherIncome +(step1Price*step1YieldPerAcre);
    }

    public float computeResultReturnOverVariableCosts(){
        return computeStep1GrossRevenue()-computeStep2TotalVariableCosts();
    }
    public float computeResultReturnOverTotalExpenses(){
        return computeStep1GrossRevenue()-computeStep4TotalExpenses();
    }

    public float computeResultBreakevenYieldToCoverVariableExpenses(){
        try {
            return computeStep2TotalVariableCosts()/getStep1Price();
        }catch (Exception e){
            return 0;
        }
    }
    public float computeResultBreakevenYieldToCoverTotalExpenses(){
        try {
            return computeStep4TotalExpenses()/getStep1Price();
        }catch (Exception e){
            return 0;
        }
    }

    public float computeResultBreakevenPriceToCoverVariableExpenses(){
        try {
            return computeStep2TotalVariableCosts()/getStep1YieldPerAcre();
        }catch (Exception e){
            return 0;
        }
    }
    public float computeResultBreakevenPriceToCoverTotalExpenses(){
        try{
            return computeStep4TotalExpenses()/getStep1YieldPerAcre();
        }catch (Exception e){
            return 0;
        }

    }

    public float computeReturnOnCapital(){
        try {
            return ((computeResultReturnOverTotalExpenses()/computeStep5TotalCapitalInvestment()))*100;
        }catch (Exception e){
            return 0;
        }
    }
    public float computeMargin(){

        return computeStep1GrossRevenue()-computeStep2TotalVariableCosts();

    }

    public float getStep4CasualEmployeeNumber() {
        return step4CasualEmployeeNumber;
    }

    public void setStep4CasualEmployeeNumber(float step4CasualEmployeeNumber) {
        this.step4CasualEmployeeNumber = step4CasualEmployeeNumber;
    }

    @Override
    public String toString() {
        return "CropROICalculatorSingleton{" +
                "step1YieldUnits='" + step1YieldUnits + '\'' +
                ", step1Price=" + step1Price +
                ", step1YieldPerAcre=" + step1YieldPerAcre +
                ", step1OtherIncome=" + step1OtherIncome +
                ", step1GrossRevenue=" + step1GrossRevenue +
                ", step2SeedAndTreatment=" + step2SeedAndTreatment +
                ", step2Fertilizer=" + step2Fertilizer +
                ", step2Herbicide=" + step2Herbicide +
                ", step2Insecticide=" + step2Insecticide +
                ", step2Fuel=" + step2Fuel +
                ", step2MachineryOperating=" + step2MachineryOperating +
                ", step2MachineryLease=" + step2MachineryLease +
                ", step2LandRental=" + step2LandRental +
                ", step2CropInsurancePremium=" + step2CropInsurancePremium +
                ", step2DryingCosts=" + step2DryingCosts +
                ", step2OtherCosts=" + step2OtherCosts +
                ", step3BuildingRepair=" + step3BuildingRepair +
                ", step3machineDepreciation=" + step3machineDepreciation +
                ", step3PropertyTaxes=" + step3PropertyTaxes +
                ", step3BusinessOverhead=" + step3BusinessOverhead +
                ", step3BuildingDepreciation=" + step3BuildingDepreciation +
                ", step3TotalOtherExpenses=" + step3TotalOtherExpenses +
                ", step4OwnerAllowanceQty=" + step4OwnerAllowanceQty +
                ", step4OwnerAllowanceCost=" + step4OwnerAllowanceCost +
                ", step4SalariedEmployeeCost=" + step4SalariedEmployeeCost +
                ", step4SalariedEmployeeQty=" + step4SalariedEmployeeQty +
                ", step4CasualEmployeeCost=" + step4CasualEmployeeCost +
                ", step4CasualEmployeeHours=" + step4CasualEmployeeHours +
                ", step4CasualEmployeeWeeks=" + step4CasualEmployeeWeeks +
                ", step4CasualEmployeeTotal=" + step4CasualEmployeeTotal +
                ", step5MachineryInvestment=" + step5MachineryInvestment +
                ", step5BuildingInvestment=" + step5BuildingInvestment +
                ", step5LandInvestment=" + step5LandInvestment +
                '}';
    }
}
