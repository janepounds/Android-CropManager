package com.myfarmnow.myfarmcrop.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropContact;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropMachine;
import com.myfarmnow.myfarmcrop.models.CropProduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CropSyncService extends Service {
    MyFarmDbHandlerSingleton dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
    String userId = null;
    public CropSyncService() {



    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        userId = CropDashboardActivity.getPreferences(CropDashboardActivity
                .PREFERENCES_USER_ID, this);

        prepareSyncRequest();
        stopSelf();
        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    public void prepareSyncRequest(){
        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("fields",prepareFields());
            requestObject.put("incomeExpenses",prepareIncomeExpense());
            requestObject.put("bills",prepareBills());
            requestObject.put("contacts",prepareContacts());
            requestObject.put("crops",prepareCrops());
            requestObject.put("cultivations",prepareCultivations());
            requestObject.put("customers",prepareCustomers());
            requestObject.put("employees",prepareEmployees());
            requestObject.put("estimates",prepareEstimates());
            requestObject.put("harvests",prepareHarvest());
            requestObject.put("inventoryFertilizers",prepareInventoryFertilizers());
            requestObject.put("inventorySeeds",prepareInventorySeeds());
            requestObject.put("inventorySprays",prepareInventorySprays());
            requestObject.put("invoices",prepareInvoice());
            requestObject.put("irrigations",prepareIrrigations());
            requestObject.put("machines",prepareMachines());
            requestObject.put("machineServices",prepareMachineServices());
            requestObject.put("machineTasks",prepareMachineTasks());
            requestObject.put("notes",prepareNotes());
            requestObject.put("payments",preparePayments());
            requestObject.put("paymentBills",preparePaymentBills());
            requestObject.put("products",prepareProducts());
            requestObject.put("productItems",prepareProductItems());
            requestObject.put("purchaseOrders",preparePurchaseOrders());
            requestObject.put("salesOrders",prepareSalesOrders());
            requestObject.put("scoutings",prepareScoutings());
            requestObject.put("settings",prepareSettings());
            requestObject.put("soilAnalysis",prepareSoilAnalysis());
            requestObject.put("sprayings",prepareSprayings());
            requestObject.put("suppliers",prepareSuppliers());
            requestObject.put("tasks",prepareTasks());
            requestObject.put("transplantings",prepareTransplantings());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("SYNC",requestObject.toString());
    }

    private JSONArray prepareFields(){
        ArrayList<CropField> fields = dbHandler.getCropFields(userId,false);
        JSONArray fieldsJson = new JSONArray();
        for(CropField field: fields){
            fieldsJson.put(field.toJSON());
        }
        return fieldsJson;
    }

    private JSONArray prepareMachines(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropMachine> records = dbHandler.getCropMachines(userId,false);
        for(CropMachine record: records){
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }

    private JSONArray prepareProducts(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropProduct> records = dbHandler.getCropProducts(userId,false);
        for(CropProduct record: records){
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }

    private JSONArray prepareIncomeExpense(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropIncomeExpense> records = dbHandler.getCropIncomeExpenses(userId,false);
        for(CropIncomeExpense record: records){
            //get crop globalId
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareBills(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced bills records

        return jsonArray;
    }
    private JSONArray prepareContacts(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropContact> records = dbHandler.getCropContacts(userId,false);
        for(CropContact field: records){
            jsonArray.put(field.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareCrops(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced crops records

        return jsonArray;
    }
    private JSONArray prepareCultivations(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced cultivations records

        return jsonArray;
    }
    private JSONArray prepareCustomers(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropCustomer> records = dbHandler.getCropCustomers(userId,false);
        for(CropCustomer field: records){
            jsonArray.put(field.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareEmployees(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropEmployee> records = dbHandler.getCropEmployee(userId,false);
        for(CropEmployee field: records){
            jsonArray.put(field.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareEstimates(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced estimates records

        return jsonArray;
    }
    private JSONArray prepareHarvest(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced harvest records

        return jsonArray;
    }
    private JSONArray prepareInventoryFertilizers(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropInventoryFertilizer> records = dbHandler.getCropFertilizerInventorys(userId,false);
        for(CropInventoryFertilizer record: records){
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareInventorySeeds(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropInventorySeeds> records = dbHandler.getCropSeeds(userId,false);
        for(CropInventorySeeds record: records){
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareInventorySprays(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropInventorySpray> records = dbHandler.getCropSpray(userId,false);
        for(CropInventorySpray record: records){
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareInvoice(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced invoices records

        return jsonArray;
    }
    private JSONArray prepareIrrigations(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced irrigations records

        return jsonArray;
    }
    private JSONArray prepareMachineServices(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced machine services records

        return jsonArray;
    }
    private JSONArray prepareMachineTasks(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced machine services records

        return jsonArray;
    }
    private JSONArray prepareNotes(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced notes records

        return jsonArray;
    }
    private JSONArray preparePayments(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced invoice payments records

        return jsonArray;
    }
    private JSONArray preparePaymentBills(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced invoice payments records

        return jsonArray;
    }
    private JSONArray prepareProductItems(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced bill payments records

        return jsonArray;
    }

    private JSONArray preparePurchaseOrders(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced purchase orders records

        return jsonArray;
    }
    private JSONArray prepareSalesOrders(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced sales orders records

        return jsonArray;
    }
    private JSONArray prepareScoutings(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced sales orders records

        return jsonArray;
    }
    private JSONArray prepareSettings(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced settings records

        return jsonArray;
    }
    private JSONArray prepareSoilAnalysis(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced soil analysis records

        return jsonArray;
    }
    private JSONArray prepareSprayings(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced sprayings records

        return jsonArray;
    }
    private JSONArray prepareSuppliers(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced suppliers records

        return jsonArray;
    }
    private JSONArray prepareTasks(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced tasks records

        return jsonArray;
    }
    private JSONArray prepareTransplantings(){
        JSONArray jsonArray = new JSONArray();

        //TODO prepare unsynced transplanting records

        return jsonArray;
    }

}
