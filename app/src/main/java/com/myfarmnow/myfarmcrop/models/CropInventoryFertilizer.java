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
    private String syncStatus="no";
    private String globalId;
    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getGlobalId() {
        return globalId;
    }

    public JSONObject toJSON(){

        JSONObject object = new JSONObject();

        try {
            object.put("id",id);
            object.put("globalId",globalId);
            object.put("userId",userId);
            object.put("purchaseDate",purchaseDate);
            object.put("fertilizerName",fertilizerName);
            object.put("batchNumber",batchNumber);
            object.put("quantity",quantity);
            object.put("totalConsumed",totalConsumed);
            object.put("usageUnit",usageUnits);
            object.put("fertilizerType",type);
            object.put("nPercentage",nPercentage);
            object.put("kPercentage",kPercentage);
            object.put("pPercentage",pPercentage);
            object.put("serialNumber",serialNumber);
            object.put("supplier",supplier);
            object.put("macroNutrientsCa",macroNutrientsCa);
            object.put("macroNutrientsMg",macroNutrientsMg);
            object.put("macroNutrientsS",macroNutrientsS);
            object.put("microNutrientsB",microNutrientsB);
            object.put("microNutrientsMn",microNutrientsMn);
            object.put("microNutrientsCl",microNutrientsCl);
            object.put("microNutrientsZn",microNutrientsZn);
            object.put("microNutrientsFe",microNutrientsFe);
            object.put("microNutrientsNa",microNutrientsNa);
        } catch (JSONException e) { e.printStackTrace();        }
        return object;
    }
    public CropInventoryFertilizer(JSONObject object) throws JSONException {
        setGlobalId(object.getString("id"));
        setUserId(object.getString("userId"));
        setPurchaseDate(object.getString("purchaseDate"));
        setFertilizerName(object.getString("fertilizerName"));
        setBatchNumber(object.getString("batchNumber"));
        setQuantity(Float.parseFloat(object.getString("quantity")));
        setUsageUnits(object.getString("usageUnit"));
        setType(object.getString("fertilizerType"));
        setnPercentage(Float.parseFloat(object.getString("nPercentage")));
        setkPercentage(Float.parseFloat(object.getString("kPercentage")));
        setpPercentage(Float.parseFloat(object.getString("pPercentage")));
        setSerialNumber(object.getString("serialNumber"));
        setSupplier(object.getString("supplier"));
        setMacroNutrientsCa(Float.parseFloat(object.getString("macroNutrientsCa")));
        setMacroNutrientsMg(Float.parseFloat(object.getString("macroNutrientsMg")));
        setMacroNutrientsS(Float.parseFloat(object.getString("macroNutrientsS")));
        setMicroNutrientsB(Float.parseFloat(object.getString("microNutrientsB")));
        setMicroNutrientsMn(Float.parseFloat(object.getString("microNutrientsMn")));
        setMicroNutrientsCl(Float.parseFloat(object.getString("microNutrientsCl")));
        setMicroNutrientsZn(Float.parseFloat(object.getString("microNutrientsZn")));
        setMicroNutrientsFe(Float.parseFloat(object.getString("microNutrientsFe")));
        setMicroNutrientsNa(Float.parseFloat(object.getString("microNutrientsNa")));
        setSyncStatus("yes");
    }

    public CropInventoryFertilizer(){

    }



}
