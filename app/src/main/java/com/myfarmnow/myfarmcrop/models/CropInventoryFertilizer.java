package com.myfarmnow.myfarmcrop.models;

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CropInventoryFertilizer implements CropInventory,Serializable,CropSpinnerItem {
    String id="";
    String userId="";

    String purchaseDate="";
    String fertilizerName="";
    String batchNumber="";
    float quantity=0;
    float totalConsumed=0;
    String usageUnits ="";
    String type="";
    float nPercentage=0;
    float kPercentage=0;
    float pPercentage=0;
    String serialNumber="";
    String supplier="";
    float cost=0;
    float macroNutrientsCa=0;
    float macroNutrientsMg=0;
    float macroNutrientsS=0;
    float microNutrientsB=0;
    float microNutrientsMn=0;
    float microNutrientsCl=0;
    float microNutrientsMo=0;
    float microNutrientsCu=0;
    float microNutrientsZn=0;
    float microNutrientsFe=0;
    float microNutrientsNa=0;

    public CropInventoryFertilizer(){

    }






    public CropInventoryFertilizer(JSONObject fertilizerJson) throws MissingValueException{
        try {
            this.setFertilizerName(fertilizerJson.getString(MyFarmDbHandlerSingleton.CROP_INVENTORY_FERTILIZER_NAME));
            this.setBatchNumber(fertilizerJson.getString(MyFarmDbHandlerSingleton.CROP_INVENTORY_FERTILIZER_BATCH_NUMBER));
            this.setPurchaseDate(fertilizerJson.getString(MyFarmDbHandlerSingleton.CROP_INVENTORY_FERTILIZER_DATE));
            this.setQuantity((float)fertilizerJson.getDouble(MyFarmDbHandlerSingleton.CROP_INVENTORY_FERTILIZER_QUANTITY));
            //this.setTotalConsumed((float)sheepJson.getDouble("totalConsumed"));
        }catch (JSONException e){
            throw new MissingValueException("Missing Key Variables in the JSON Object "+e.getMessage());
        }
        try {
            this.setId(fertilizerJson.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.setUsageUnits(fertilizerJson.getString("usageUnits"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        //
    }
    public void setUsageUnits(String usageUnits) {

            this.usageUnits = usageUnits;


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

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setFertilizerName(String fertilizerName) {
        this.fertilizerName = fertilizerName;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setTotalConsumed(float totalConsumed) {
        this.totalConsumed = totalConsumed;
    }



    @Override
    public String getInventoryType() {
        return CropInventory.CONST_FERTILIZER_INVENTORY;
    }



    @Override
    public String getBatchNumber() {
        return batchNumber;
    }

    @Override
    public String getName() {
        return this.fertilizerName;
    }

    @Override
    public float getInitialQuantity() {
        return quantity;
    }

    @Override
    public float getAmountConsumed() {
        return totalConsumed;
    }

    @Override
    public float calculateAmountLeft() {
        return quantity-totalConsumed;
    }

    @Override
    public String getUsageUnits() {
        return usageUnits;
    }

    public String getId() {
        return id;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getFertilizerName() {
        return fertilizerName;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getTotalConsumed() {
        return totalConsumed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getnPercentage() {
        return nPercentage;
    }

    public void setnPercentage(float nPercentage) {
        this.nPercentage = nPercentage;
    }

    public float getkPercentage() {
        return kPercentage;
    }

    public void setkPercentage(float kPercentage) {
        this.kPercentage = kPercentage;
    }

    public float getpPercentage() {
        return pPercentage;
    }

    public void setpPercentage(float pPercentage) {
        this.pPercentage = pPercentage;
    }

    public String  getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }



    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public float getMacroNutrientsCa() {
        return macroNutrientsCa;
    }

    public void setMacroNutrientsCa(float macroNutrientsCa) {
        this.macroNutrientsCa = macroNutrientsCa;
    }

    public float getMacroNutrientsMg() {
        return macroNutrientsMg;
    }

    public void setMacroNutrientsMg(float macroNutrientsMg) {
        this.macroNutrientsMg = macroNutrientsMg;
    }

    public float getMacroNutrientsS() {
        return macroNutrientsS;
    }

    public void setMacroNutrientsS(float macroNutrientsS) {
        this.macroNutrientsS = macroNutrientsS;
    }

    public float getMicroNutrientsB() {
        return microNutrientsB;
    }

    public void setMicroNutrientsB(float microNutrientsB) {
        this.microNutrientsB = microNutrientsB;
    }

    public float getMicroNutrientsMn() {
        return microNutrientsMn;
    }

    public void setMicroNutrientsMn(float microNutrientsMn) {
        this.microNutrientsMn = microNutrientsMn;
    }

    public float getMicroNutrientsCl() {
        return microNutrientsCl;
    }

    public void setMicroNutrientsCl(float microNutrientsCl) {
        this.microNutrientsCl = microNutrientsCl;
    }

    public float getMicroNutrientsMo() {
        return microNutrientsMo;
    }

    public void setMicroNutrientsMo(float microNutrientsMo) {
        this.microNutrientsMo = microNutrientsMo;
    }

    public float getMicroNutrientsCu() {
        return microNutrientsCu;
    }

    public void setMicroNutrientsCu(float microNutrientsCu) {
        this.microNutrientsCu = microNutrientsCu;
    }

    public float getMicroNutrientsZn() {
        return microNutrientsZn;
    }

    public void setMicroNutrientsZn(float microNutrientsZn) {
        this.microNutrientsZn = microNutrientsZn;
    }

    public float getMicroNutrientsFe() {
        return microNutrientsFe;
    }

    public void setMicroNutrientsFe(float microNutrientsFe) {
        this.microNutrientsFe = microNutrientsFe;
    }

    public float getMicroNutrientsNa() {
        return microNutrientsNa;
    }

    public void setMicroNutrientsNa(float microNutrientsNa) {
        this.microNutrientsNa = microNutrientsNa;
    }

    @Override
    public String toString() {
        return fertilizerName;
    }
}
