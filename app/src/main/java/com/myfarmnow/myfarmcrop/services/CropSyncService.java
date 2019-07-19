package com.myfarmnow.myfarmcrop.services;

/*
contentValues.put(CROP_SYNC_STATUS,field.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID,field.getGlobalId());
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.ApiPaths;
import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropBill;
import com.myfarmnow.myfarmcrop.models.CropContact;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropEstimate;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.models.CropIrrigation;
import com.myfarmnow.myfarmcrop.models.CropMachine;
import com.myfarmnow.myfarmcrop.models.CropMachineService;
import com.myfarmnow.myfarmcrop.models.CropMachineTask;
import com.myfarmnow.myfarmcrop.models.CropNote;
import com.myfarmnow.myfarmcrop.models.CropInvoicePayment;
import com.myfarmnow.myfarmcrop.models.CropPaymentBill;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropPurchaseOrder;
import com.myfarmnow.myfarmcrop.models.CropSalesOrder;
import com.myfarmnow.myfarmcrop.models.CropScouting;
import com.myfarmnow.myfarmcrop.models.CropSoilAnalysis;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
import com.myfarmnow.myfarmcrop.models.CropSupplier;
import com.myfarmnow.myfarmcrop.models.CropTask;
import com.myfarmnow.myfarmcrop.models.CropTransplanting;
import com.myfarmnow.myfarmcrop.models.DeletedRecord;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;


public class CropSyncService extends Service {

    public String generateUUID(){
        return UUID.randomUUID().toString();
    }
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
        startBlock1TablesBackup();
        startBlock2TablesBackup();
        backupDeletedRecords();
    }

    private JSONArray prepareDeleteRecords(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<DeletedRecord> records = dbHandler.getDeletedRecords(userId,false);
        for(DeletedRecord record: records){

            jsonArray.put(record.toJSON());
        }

        return jsonArray;
    }
    private JSONArray prepareFields(){
        ArrayList<CropField> fields = dbHandler.getCropFields(userId,false);
        Log.d("FIELDS COUNT",fields.size()+"");
        JSONArray fieldsJson = new JSONArray();
        for(CropField field: fields){
            if(field.getGlobalId()==null){
                field.setGlobalId(generateUUID());
                dbHandler.updateCropField(field);
            }
            fieldsJson.put(field.toJSON());
        }

        Log.d("FIELDS",fieldsJson.toString());
        return fieldsJson;
    }

    private JSONArray prepareMachines(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropMachine> records = dbHandler.getCropMachines(userId,false);
        for(CropMachine record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropMachine(record);
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("MACHINE LIST",jsonArray.toString());
        return jsonArray;
    }

    private JSONArray prepareProducts(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropProduct> records = dbHandler.getCropProducts(userId,false);
        for(CropProduct record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropProduct(record);
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("PRODUCT LIST",jsonArray.toString());
        return jsonArray;
    }

    private JSONArray prepareIncomeExpense(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropIncomeExpense> records = dbHandler.getCropIncomeExpenses(userId,false);
        for(CropIncomeExpense record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropIncomeExpense(record);
            }
            Crop crop = dbHandler.getCrop(record.getCropId(), false);
            if(crop !=null){
                if(crop.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setCropId(crop.getGlobalId());
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("INCOME EXPENSE LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareBills(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropBill> records = dbHandler.getCropBills(userId,false);
        for(CropBill record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropBill(record);
            }
            CropSupplier supplier = dbHandler.getCropSupplier(record.getSupplierId(),false);
            if(supplier ==null){
                continue; //item has no assigned supplier
            }
            if(supplier.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setSupplierId(supplier.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        Log.d("BILLS LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareContacts(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropContact> records = dbHandler.getCropContacts(userId,false);
        for(CropContact record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropContact(record);
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("CONTACTS LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareCrops(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<Crop> records = dbHandler.getCrops(userId,false);
        Log.d("CROPS ",records.size()+"");
        for(Crop record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCrop(record);
            }
            CropField field = dbHandler.getCropField(record.getFieldId(),false);

            if(field ==null){
                continue; //crop has no assigned field
            }
            Log.d("CROPS FIELD",field.toJSON().toString());
            if(field.getSyncStatus().equals("no")){
                continue; //do not back up this crop since its parent field is not backed up
            }
            record.setFieldId(field.getGlobalId());

            CropInventorySeeds seed = dbHandler.getCropSeed(record.getSeedId(),false);

            if(seed != null){
                Log.d("CROPS SEED ",seed.toJSON().toString());
                if(seed.getSyncStatus().equals("no")){
                    continue; //do not back up this crop since its parent seed inventory is not backed up
                }
                record.setSeedId(seed.getGlobalId()); //change the seedId it to map the global Id
            }
            else{
//                Log.d("CROPS SEED ",record.getSeedId());
            }

            jsonArray.put(record.toJSON());
        }
        Log.d("CROPS LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareCultivations(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropCultivation> records = dbHandler.getCropCultivates(userId,false);
        for(CropCultivation record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropCultivate(record);
            }
            Crop crop = dbHandler.getCrop(record.getCropId(), false);
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareCustomers(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropCustomer> records = dbHandler.getCropCustomers(userId,false);
        for(CropCustomer record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropCustomer(record);
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("CUSTOMERS LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareEmployees(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropEmployee> records = dbHandler.getCropEmployees(userId,false);
        for(CropEmployee record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropEmployee(record);
            }
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropEmployee(record);
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("EMPLOYEES LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareEstimates(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropEstimate> records = dbHandler.getCropEstimates(userId,false);
        for(CropEstimate record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropEstimate(record);
            }
            CropCustomer customer = dbHandler.getCropCustomer(record.getCustomerId(),false);
            if(customer ==null){
                continue; //item has no assigned customer
            }
            if(customer.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setCustomerId(customer.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        Log.d("ESTIMATES LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareHarvest(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropHarvest> records = dbHandler.getCropHarvests(userId,false);
        for(CropHarvest record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropHarvest(record);
            }
            Crop crop = dbHandler.getCrop(record.getCropId(), false);

            if(crop ==null){
                continue; //crop has no assigned field
            }
            Log.d("HARVEST LIST", crop.getId());
            if(crop.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        Log.d("HARVEST LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareFertilizerApplication(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropFertilizerApplication> records = dbHandler.getCropFertilizerApplications(userId,false);
        for(CropFertilizerApplication record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropFertilizerApplication(record);
            }
            Crop crop = dbHandler.getCrop(record.getCropId(), false);
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());

            CropInventoryFertilizer fertilizer = dbHandler.getCropFertilizer(record.getFertilizerId(),false);
            if(fertilizer ==null){
                continue; //spraying has no assigned spray
            }
            if(fertilizer.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setFertilizerId(fertilizer.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        Log.d("FERTILIZER APPLICATION",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareInventoryFertilizers(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropInventoryFertilizer> records = dbHandler.getCropFertilizerInventorys(userId,false);
        for(CropInventoryFertilizer record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropFertilizerInventory(record);
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("FERTILIZERS",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareInventorySeeds(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropInventorySeeds> records = dbHandler.getCropSeeds(userId,false);
        for(CropInventorySeeds record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropSeeds(record);
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("SEEDS",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareInventorySprays(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropInventorySpray> records = dbHandler.getCropSpray(userId,false);
        for(CropInventorySpray record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropSpray(record);
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("SPRAYS",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareInvoice(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropInvoice> records = dbHandler.getCropInvoices(userId,false);
        for(CropInvoice record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropInvoice(record);
            }
            CropCustomer customer = dbHandler.getCropCustomer(record.getCustomerId(),false);
            if(customer ==null){
                continue; //item has no assigned bill
            }
            if(customer.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setCustomerId(customer.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        Log.d("INVOICE LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareIrrigations(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropIrrigation> records = dbHandler.getCropIrrigations(userId,false);
        for(CropIrrigation record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropIrrigation(record);
            }
            Crop crop = dbHandler.getCrop(record.getCropId(), false);
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareMachineServices(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropMachineService> records = dbHandler.getCropMachineServices(userId,false);
        for(CropMachineService record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropMachineService(record);
            }
            CropMachine machine = dbHandler.getCropMachine(record.getMachineId(),false);
            if(machine ==null){
                continue; //machine has no assigned machine
            }
            if(machine.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setMachineId(machine.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        Log.d("SERVICES LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareMachineTasks(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropMachineTask> records = dbHandler.getCropMachineTasks(userId,false);
        for(CropMachineTask record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropMachineTask(record);
            }
            CropMachine machine = dbHandler.getCropMachine(record.getMachineId(),false);
            if(machine ==null){
                continue; //note has no assigned machine
            }
            if(machine.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setMachineId(machine.getGlobalId());

            jsonArray.put(record.toJSON());
        }
        Log.d("MACHINE TASKS LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareNotes(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropNote> records = dbHandler.getCropNotes(userId,false);
        Log.d("NOTES "," "+records.size());
        for(CropNote record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropNote(record);
            }
            if(record.getIsFor().equals(CropNote.IS_FOR_CROP)){
                Crop crop = dbHandler.getCrop(record.getParentId(),false);
                if(crop ==null){
                    continue; //note has no assigned crop
                }
                if(crop.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent field is not backed up
                }
                record.setParentId(crop.getGlobalId());
            }else if(record.getIsFor().equals(CropNote.IS_FOR_MACHINE)){
                CropMachine machine = dbHandler.getCropMachine(record.getParentId(),false);
                if(machine ==null){
                    continue; //note has no assigned machine
                }
                if(machine.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent field is not backed up
                }
                record.setParentId(machine.getGlobalId());
            }
            else{
                continue;
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("NOTES LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray preparePayments(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropInvoicePayment> records = dbHandler.getCropPayments(userId,false);
        for(CropInvoicePayment record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropPayment(record);
            }
            CropInvoice invoice = dbHandler.getCropInvoiceById(record.getInvoiceId(),false);
            if(invoice !=null){
                if(invoice.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setInvoiceId(invoice.getGlobalId());
            }
            CropCustomer customer = dbHandler.getCropCustomer(record.getCustomerId(),false);
            if(customer ==null){
                continue; //item has no assigned customer
            }
            if(customer.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setCustomerId(customer.getGlobalId());
            Log.d("PAYMENTS LIST",jsonArray.toString());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray preparePaymentBills(){

        JSONArray jsonArray = new JSONArray();
        ArrayList<CropPaymentBill> records = dbHandler.getCropPaymentBills(userId,false);
        for(CropPaymentBill record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropPaymentBill(record);
            }
            CropBill bill = dbHandler.getCropBillById(record.getBillId(),false);
            if(bill !=null){
                if(bill.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setBillId(bill.getGlobalId());
            }
            CropSupplier supplier = dbHandler.getCropSupplier(record.getSupplierId(),false);
            if(supplier ==null){
                continue; //item has no assigned customer
            }
            if(supplier.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setSupplierId(supplier.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareProductItems(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropProductItem> records = dbHandler.getCropProductItems(userId,false);
        for(CropProductItem record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropProductItem(record);
            }
            if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_BILL)){
                CropBill bill = dbHandler.getCropBillById(record.getParentObjectId(),false);
                if(bill ==null){
                    continue; //item has no assigned bill
                }
                if(bill.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setParentObjectId(bill.getGlobalId());
            }else  if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_INVOICE)){
                CropInvoice invoice = dbHandler.getCropInvoiceById(record.getParentObjectId(),false);
                if(invoice ==null){
                    continue; //item has no assigned bill
                }
                if(invoice.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setParentObjectId(invoice.getGlobalId());
            }else  if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_ESTIMATE)){
                CropEstimate estimate = dbHandler.getCropEstimateById(record.getParentObjectId(),false);
                if(estimate ==null){
                    continue; //item has no assigned bill
                }
                if(estimate.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setParentObjectId(estimate.getGlobalId());
            }else if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_SALES_ORDER)){
                CropSalesOrder cropSalesOrder = dbHandler.getCropSalesOrderById(record.getParentObjectId(),false);
                if(cropSalesOrder ==null){
                    continue; //item has no assigned bill
                }
                if(cropSalesOrder.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setParentObjectId(cropSalesOrder.getGlobalId());
            }else if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER)){
                CropPurchaseOrder purchaseOrder = dbHandler.getCropPurchaseOrderById(record.getParentObjectId(),false);
                if(purchaseOrder ==null){
                    continue; //item has no assigned bill
                }
                if(purchaseOrder.getSyncStatus().equals("no")){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setParentObjectId(purchaseOrder.getGlobalId());
            }
            else{
                continue;//ignore unallocated items
            }
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray preparePurchaseOrders(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropPurchaseOrder> records = dbHandler.getCropPurchaseOrders(userId,false);
        for(CropPurchaseOrder record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropPurchaseOrder(record);
            }
            CropSupplier supplier = dbHandler.getCropSupplier(record.getSupplierId(),false);
            if(supplier ==null){
                continue; //item has no assigned supplier
            }
            if(supplier.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setSupplierId(supplier.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        Log.d("PURCHASE ORDER LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareSalesOrders(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropSalesOrder> records = dbHandler.getCropSalesOrders(userId,false);
        for(CropSalesOrder record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropSalesOrder(record);
            }
            CropCustomer customer = dbHandler.getCropCustomer(record.getCustomerId(),false);
            if(customer ==null){
                continue; //item has no assigned bill
            }
            if(customer.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setCustomerId(customer.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        Log.d("SALES ORDER LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareScoutings(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropScouting> records = dbHandler.getCropScoutings(userId,false);
        for(CropScouting record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropScouting(record);
            }
            Crop crop = dbHandler.getCrop(record.getCropId(), false);
            Log.d("SCOUTING LIST",crop.toJSON().toString());
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }

            record.setCropId(crop.getGlobalId());
            jsonArray.put(record.toJSON());
        }

        return jsonArray;
    }
    private JSONArray prepareSettings(){
        JSONArray jsonArray = new JSONArray();
        CropSettingsSingleton  singleton= dbHandler.getSettings(userId,false);
        if(singleton.getGlobalId()==null){
            singleton.setGlobalId(generateUUID());
            dbHandler.updateSettings(singleton);
        }
        jsonArray.put(singleton.toJSON());
        return jsonArray;
    }
    private JSONArray prepareSoilAnalysis(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropSoilAnalysis> records = dbHandler.getCropSoilAnalysis(userId,false);
        for(CropSoilAnalysis record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropSoilAnalysis(record);
            }
            CropField field = dbHandler.getCropField(record.getFieldId(),false);
            if(field ==null){
                continue; //crop has no assigned field
            }
            if(field.getSyncStatus().equals("no")){
                continue; //do not back up this analysis since its parent field is not backed up
            }
            record.setFieldId(field.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareSprayings(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropSpraying> records = dbHandler.getCropSprayings(userId,false);
        for(CropSpraying record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropSpraying(record);
            }
            Crop crop = dbHandler.getCrop(record.getCropId(), false);
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            CropInventorySpray spray = dbHandler.getCropSprayById(record.getSprayId(),false);
            if(spray ==null){
                continue; //spraying has no assigned spray
            }
            if(spray.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setSprayId(spray.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareSuppliers(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropSupplier> records = dbHandler.getCropSuppliers(userId,false);
        for(CropSupplier record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropSupplier(record);
            }
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareTasks(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropTask> records = dbHandler.getCropTasks(userId,false);
        for(CropTask record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropTask(record);
            }
            Crop crop = dbHandler.getCrop(record.getCropId(), false);
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        Log.d("TASKS LIST",jsonArray.toString());
        return jsonArray;
    }
    private JSONArray prepareTransplantings(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropTransplanting> records = dbHandler.getCropTransplantings(userId,false);
        for(CropTransplanting record: records){
            if(record.getGlobalId()==null){
                record.setGlobalId(generateUUID());
                dbHandler.updateCropTransplanting(record);
            }
            Crop crop = dbHandler.getCrop(record.getCropId(), false);
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getSyncStatus().equals("no")){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    //Ordered Back up sequence

    /**
     * 1. send fields, contacts, employees as block 1a
     * 2. after 1a send Seed, Spray and fertilizer Inventory as block 1b
     * 3. after 1b send crops, soil analysis, machines as block 1c
     * 4. after 1c send (harvest, spraying, fertilizer application) as block 1d, (cultivation, scoutings, irrigation) as block 1e,
     (task, transplantings, income/expense) as block 1f, (machine service, notes, tasks) as 1g
     *
     */
    private void startBlock1TablesBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("fields",prepareFields());
            requestObject.put("contacts",prepareContacts());
            requestObject.put("employees",prepareEmployees());
            requestObject.put("settings",prepareSettings());
            requestObject.put("userId",userId);
            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                return;
            }
            Log.d("PATH 1A",params.toString());

            client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/1a",params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields

                    try {
                        JSONArray fields = response.getJSONArray("fields");
                        Log.d("FIELDS RESPONSE",fields.toString());
                        for(int i=0; i<fields.length(); i++){
                          

                            try{
                                CropField field = dbHandler.getCropField( fields.getJSONObject(i).getString("localId"),false);
                                field.setGlobalId(fields.getJSONObject(i).getString("globalId"));
                                field.setSyncStatus("yes");
                                dbHandler.updateCropField(field);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray contacts = response.getJSONArray("contacts");
                        for(int i=0; i<contacts.length(); i++){
                          

                            try{
                                CropContact contact = dbHandler.getCropContact( contacts.getJSONObject(i).getString("localId"),false);
                                contact.setGlobalId(contacts.getJSONObject(i).getString("globalId"));
                                contact.setSyncStatus("yes");
                                dbHandler.updateCropContact(contact);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray employees = response.getJSONArray("employees");
                        for(int i=0; i<employees.length(); i++){
                            try{
                                CropEmployee employee = dbHandler.getCropEmployee( employees.getJSONObject(i).getString("localId"),false);
                                employee.setGlobalId(employees.getJSONObject(i).getString("globalId"));
                                employee.setSyncStatus("yes");
                                dbHandler.updateCropEmployee(employee);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray settings = response.getJSONArray("settings");
                        for(int i=0; i<settings.length(); i++){
                            try{
                                CropSettingsSingleton settingsSingleton = dbHandler.getSettings(userId,false);
                                settingsSingleton.setGlobalId(settings.getJSONObject(i).getString("globalId"));
                                settingsSingleton.setSyncStatus("yes");
                                dbHandler.updateSettings(settingsSingleton);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startBlock1bBackup();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                   if(errorResponse != null){
                       Log.e("BACKUP RESPONSE 1A"+statusCode,errorResponse.toString());
                   }

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1A: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1A: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void startBlock1bBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("inventoryFertilizers",prepareInventoryFertilizers());
            requestObject.put("inventorySeeds",prepareInventorySeeds());
            requestObject.put("inventorySprays",prepareInventorySprays());
            requestObject.put("userId",userId);
            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
              ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {
                
                e.printStackTrace();
                return;
            }


          //  client.post()
            client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/1b",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to  updated the synced tables
                    try {
                        JSONArray inventorySeeds = response.getJSONArray("inventorySeeds");
                        for(int i=0; i<inventorySeeds.length(); i++){
                          

                            try{
                                CropInventorySeeds inventorySeed = dbHandler.getCropSeed( inventorySeeds.getJSONObject(i).getString("localId"),false);
                                inventorySeed.setGlobalId(inventorySeeds.getJSONObject(i).getString("globalId"));
                                inventorySeed.setSyncStatus("yes");
                                dbHandler.updateCropSeeds(inventorySeed);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray inventoryFertilizers = response.getJSONArray("inventoryFertilizers");
                        for(int i=0; i<inventoryFertilizers.length(); i++){
                          

                            try{
                                CropInventoryFertilizer cropFertilizer = dbHandler.getCropFertilizer( inventoryFertilizers.getJSONObject(i).getString("localId"),false);
                                cropFertilizer.setGlobalId(inventoryFertilizers.getJSONObject(i).getString("globalId"));
                                cropFertilizer.setSyncStatus("yes");
                                dbHandler.updateCropFertilizerInventory(cropFertilizer);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray inventorySprays = response.getJSONArray("inventorySprays");
                        for(int i=0; i<inventorySprays.length(); i++){
                            try{
                                CropInventorySpray inventorySeed = dbHandler.getCropSprayById( inventorySprays.getJSONObject(i).getString("localId"),false);
                                inventorySeed.setGlobalId(inventorySprays.getJSONObject(i).getString("globalId"));
                                inventorySeed.setSyncStatus("yes");
                                dbHandler.updateCropSpray(inventorySeed);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startBlock1cBackup();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("BACCKUP RESPONSE 1B"+statusCode,errorResponse.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1B: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1B: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void startBlock1cBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("crops",prepareCrops());
            requestObject.put("soilAnalysis",prepareSoilAnalysis());
            requestObject.put("machines",prepareMachines());
            requestObject.put("userId",userId);
            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                return;
            }
           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/1c",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        JSONArray crops = response.getJSONArray("crops");
                        for(int i=0; i<crops.length(); i++){
                            try{
                                Crop crop = dbHandler.getCrop( crops.getJSONObject(i).getString("localId"),false);
                                crop.setGlobalId(crops.getJSONObject(i).getString("globalId"));
                                crop.setSyncStatus("yes");
                                CropField field = dbHandler.getCropField(crop.getFieldId(),true);
                                if(field != null){
                                    crop.setFieldId(field.getId());
                                    CropInventorySeeds inventorySeed = dbHandler.getCropSeed(crop.getSeedId(),false);
                                    if(inventorySeed != null){
                                        crop.setSeedId(inventorySeed.getId());
                                    }
                                    dbHandler.updateCrop(crop);
                                }

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray crops = response.getJSONArray("soilAnalysis");
                        for(int i=0; i<crops.length(); i++){
                            try{
                                CropSoilAnalysis analysis = dbHandler.getCropSoilAnalysisById( crops.getJSONObject(i).getString("localId"),false);
                                analysis.setGlobalId(crops.getJSONObject(i).getString("globalId"));
                                analysis.setSyncStatus("yes");
                                CropField field = dbHandler.getCropField(analysis.getFieldId(),false);
                                if(field != null){
                                    analysis.setFieldId(field.getId());
                                    dbHandler.updateCropSoilAnalysis(analysis);
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray machines = response.getJSONArray("machines");
                        for(int i=0; i<machines.length(); i++){


                            try{
                                CropMachine machine = dbHandler.getCropMachine( machines.getJSONObject(i).getString("localId"),false);
                                machine.setGlobalId(machines.getJSONObject(i).getString("globalId"));
                                machine.setSyncStatus("yes");
                                dbHandler.updateCropMachine(machine);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startBlock1dBackup();
                    startBlock1eBackup();
                    startBlock1fBackup();
                    startBlock1gBackup();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("BACCKUP RESPONSE 1C"+statusCode,errorResponse.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1C: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1C: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void startBlock1dBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("harvests",prepareHarvest());
            requestObject.put("fertilizerApplication",prepareFertilizerApplication());
            requestObject.put("sprayings",prepareSprayings());
            requestObject.put("userId",userId);
            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                return;
            }

           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/1d",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields
                    try {
                        JSONArray crops = response.getJSONArray("harvests");
                        for(int i=0; i<crops.length(); i++){
                            try{
                                CropHarvest harvest = dbHandler.getCropHarvest( crops.getJSONObject(i).getString("localId"),false);
                                harvest.setGlobalId(crops.getJSONObject(i).getString("globalId"));
                                harvest.setSyncStatus("yes");
                                dbHandler.updateCropHarvest(harvest);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray application = response.getJSONArray("sprayings");
                        for(int i=0; i<application.length(); i++){
                            try{
                                CropSpraying harvest = dbHandler.getCropSpraying( application.getJSONObject(i).getString("localId"),false);
                                harvest.setGlobalId(application.getJSONObject(i).getString("globalId"));
                                harvest.setSyncStatus("yes");
                                dbHandler.updateCropSpraying(harvest);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray application = response.getJSONArray("fertilizerApplication");
                        for(int i=0; i<application.length(); i++){
                            try{
                                CropFertilizerApplication harvest = dbHandler.getCropFertilizerApplication( application.getJSONObject(i).getString("localId"),false);
                                harvest.setGlobalId(application.getJSONObject(i).getString("globalId"));
                                harvest.setSyncStatus("yes");
                                dbHandler.updateCropFertilizerApplication(harvest);

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE 1D", errorResponse.toString());
                    }

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1D: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1D: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void startBlock1eBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("cultivations",prepareCultivations());
            requestObject.put("scoutings",prepareScoutings());
            requestObject.put("irrigations",prepareIrrigations());
            requestObject.put("userId",userId);
            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                return;
            }

           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/1e",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray application = response.getJSONArray("cultivations");
                        for(int i=0; i<application.length(); i++){
                            try{
                                CropCultivation harvest = dbHandler.getCropCultivate( application.getJSONObject(i).getString("localId"),false);
                                harvest.setGlobalId(application.getJSONObject(i).getString("globalId"));
                                harvest.setSyncStatus("yes");
                                dbHandler.updateCropCultivate(harvest);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray application = response.getJSONArray("scoutings");
                        for(int i=0; i<application.length(); i++){
                            try{
                                CropScouting harvest = dbHandler.getCropScouting( application.getJSONObject(i).getString("localId"),false);
                                harvest.setGlobalId(application.getJSONObject(i).getString("globalId"));
                                harvest.setSyncStatus("yes");
                                dbHandler.updateCropScouting(harvest);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray application = response.getJSONArray("irrigations");
                        for(int i=0; i<application.length(); i++){
                            try{
                                CropIrrigation harvest = dbHandler.getCropIrrigation( application.getJSONObject(i).getString("localId"),false);
                                harvest.setGlobalId(application.getJSONObject(i).getString("globalId"));
                                harvest.setSyncStatus("yes");
                                dbHandler.updateCropIrrigation(harvest);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse!=null){
                        Log.e("RESPONSE 1E", errorResponse.toString());
                    }


                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1E: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1E: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void startBlock1fBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("transplantings",prepareTransplantings());
            requestObject.put("incomeExpenses",prepareIncomeExpense());
            requestObject.put("tasks",prepareTasks());
           requestObject.put("userId",userId);
            StringEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
            } catch (UnsupportedEncodingException e) {
                
                e.printStackTrace();
                return;
            }


           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/1f",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        JSONArray transplantings = response.getJSONArray("transplantings");
                        for(int i=0; i<transplantings.length(); i++){
                            try{
                                CropTransplanting harvest = dbHandler.getCropTransplanting( transplantings.getJSONObject(i).getString("localId"),false);
                                harvest.setGlobalId(transplantings.getJSONObject(i).getString("globalId"));
                                harvest.setSyncStatus("yes");
                                dbHandler.updateCropTransplanting(harvest);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray incomeExpenses = response.getJSONArray("incomeExpenses");
                        for(int i=0; i<incomeExpenses.length(); i++){
                            try{
                                CropIncomeExpense incomeExpense = dbHandler.getCropIncomeExpense( incomeExpenses.getJSONObject(i).getString("localId"),false);
                                incomeExpense.setGlobalId(incomeExpenses.getJSONObject(i).getString("globalId"));
                                incomeExpense.setSyncStatus("yes");
                                dbHandler.updateCropIncomeExpense(incomeExpense);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray tasks = response.getJSONArray("tasks");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropTask cropTask = dbHandler.getCropTask( tasks.getJSONObject(i).getString("localId"),false);
                                cropTask.setGlobalId(tasks.getJSONObject(i).getString("globalId"));
                                cropTask.setSyncStatus("yes");
                                dbHandler.updateCropTask(cropTask);

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE", errorResponse.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1F: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1F: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void startBlock1gBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("machineServices",prepareMachineServices());
            requestObject.put("machineTasks",prepareMachineTasks());
            requestObject.put("notes",prepareNotes());
            requestObject.put("userId",userId);
            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                return;
            }

           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/1g",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields
                    try {
                        JSONArray services = response.getJSONArray("machineServices");
                        for(int i=0; i<services.length(); i++){
                            try{
                                CropMachineService service = dbHandler.getCropMachineService( services.getJSONObject(i).getString("localId"),false);
                                service.setGlobalId(services.getJSONObject(i).getString("globalId"));
                                service.setSyncStatus("yes");
                                dbHandler.updateCropMachineService(service);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray tasks = response.getJSONArray("machineTasks");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropMachineTask cropTask = dbHandler.getCropMachineTask( tasks.getJSONObject(i).getString("localId"),false);
                                cropTask.setGlobalId(tasks.getJSONObject(i).getString("globalId"));
                                cropTask.setSyncStatus("yes");
                                dbHandler.updateCropMachineTask(cropTask);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray tasks = response.getJSONArray("notes");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropNote note = dbHandler.getCropNote( tasks.getJSONObject(i).getString("localId"),false);
                                note.setGlobalId(tasks.getJSONObject(i).getString("globalId"));
                                note.setSyncStatus("yes");
                                dbHandler.updateCropNote(note);

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE 1G", errorResponse.toString());
                    }

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1G: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1G: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * COVERS FINANCIAL MANAGER
     * 1. send customers, suppliers, products as block 2a
     * 2. after 2a send Estimates, Invoice, Sales Order as block 2b
     * 3. after 2b send Purchase Order, Bills as block 2c
     * 4. after 2c send Invoice Payments and Bill payments as 2d
     * 5. After 2d send product items as 2e
     */
    private void startBlock2TablesBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("customers",prepareCustomers());
            requestObject.put("products",prepareProducts());
            requestObject.put("suppliers",prepareSuppliers());
            requestObject.put("userId",userId);
            StringEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
            } catch (UnsupportedEncodingException e) {
                
                e.printStackTrace();
                return;
            }


            Log.e("URL +2A", ApiPaths.DATA_BACK_UP+"/2a");
           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/2a",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                    
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields

                    try {
                        JSONArray customers = response.getJSONArray("customers");
                        for(int i=0; i<customers.length(); i++){


                            try{
                                CropCustomer field = dbHandler.getCropCustomer( customers.getJSONObject(i).getString("localId"),false);
                                field.setGlobalId(customers.getJSONObject(i).getString("globalId"));
                                field.setSyncStatus("yes");
                                dbHandler.updateCropCustomer(field);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray suppliers = response.getJSONArray("suppliers");
                        for(int i=0; i<suppliers.length(); i++){
                            try{
                                CropSupplier field = dbHandler.getCropSupplier( suppliers.getJSONObject(i).getString("localId"),false);
                                field.setGlobalId(suppliers.getJSONObject(i).getString("globalId"));
                                field.setSyncStatus("yes");
                                dbHandler.updateCropSupplier(field);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray fields = response.getJSONArray("products");
                        for(int i=0; i<fields.length(); i++){


                            try{
                                CropProduct field = dbHandler.getCropProductById( fields.getJSONObject(i).getString("localId"),false);
                                field.setGlobalId(fields.getJSONObject(i).getString("globalId"));
                                field.setSyncStatus("yes");
                                dbHandler.updateCropProduct(field);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    startBlock2bBackup();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE 2A", errorResponse.toString());
                    }

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 2A: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 2A: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void startBlock2bBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        Log.e("URL +2B", ApiPaths.DATA_BACK_UP+"/2a");
        

        JSONObject requestObject = new JSONObject();
        try {
            requestObject.put("estimates",prepareEstimates());
            requestObject.put("invoices",prepareInvoice());
            requestObject.put("salesOrders",prepareSalesOrders());
            requestObject.put("userId",userId);
            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                return;
            }
           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/2b",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields
                    try {

                        JSONArray tasks = response.getJSONArray("estimates");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropEstimate estimate = dbHandler.getCropEstimateById( tasks.getJSONObject(i).getString("localId"),false);
                                estimate.setGlobalId(tasks.getJSONObject(i).getString("globalId"));
                                estimate.setSyncStatus("yes");
                                dbHandler.updateCropEstimate(estimate);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray tasks = response.getJSONArray("invoices");
                        Log.d("INVOICES",tasks.toString());

                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropInvoice invoice = dbHandler.getCropInvoiceById( tasks.getJSONObject(i).getString("localId"),false);
                                invoice.setGlobalId(tasks.getJSONObject(i).getString("globalId"));
                                invoice.setSyncStatus("yes");
                                dbHandler.updateCropInvoice(invoice);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray tasks = response.getJSONArray("salesOrders");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropSalesOrder salesOrder = dbHandler.getCropSalesOrderById( tasks.getJSONObject(i).getString("localId"),false);
                                salesOrder.setGlobalId(tasks.getJSONObject(i).getString("globalId"));
                                salesOrder.setSyncStatus("yes");
                                dbHandler.updateCropSalesOrder(salesOrder);

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startBlock2cBackup();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE 2B", errorResponse.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 2B : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 2B : "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void startBlock2cBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("bills",prepareBills());
            requestObject.put("purchaseOrders",preparePurchaseOrders());
            requestObject.put("userId",userId);
            HttpEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
                ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                return;
            }

           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/2c",           params,"application/json", new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields

                    try {
                        JSONArray bills = response.getJSONArray("bills");
                        for(int i=0; i<bills.length(); i++){
                            try{
                                CropBill bill = dbHandler.getCropBillById( bills.getJSONObject(i).getString("localId"),false);
                                bill.setGlobalId(bills.getJSONObject(i).getString("globalId"));
                                bill.setSyncStatus("yes");
                                dbHandler.updateCropBill(bill);

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONArray purchaseOrders = response.getJSONArray("purchaseOrders");
                        for(int i=0; i<purchaseOrders.length(); i++){
                            try{
                                CropPurchaseOrder purchaseOrder = dbHandler.getCropPurchaseOrderById( purchaseOrders.getJSONObject(i).getString("localId"),false);
                                purchaseOrder.setGlobalId(purchaseOrders.getJSONObject(i).getString("globalId"));
                                purchaseOrder.setSyncStatus("yes");
                                dbHandler.updateCropPurchaseOrder(purchaseOrder);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startBlock2dBackup();
                    startBlock2eBackup();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE 2B", errorResponse.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 2C: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 2C: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void startBlock2dBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("invoicePayments",preparePayments());
            requestObject.put("paymentBills",preparePaymentBills());
           requestObject.put("userId",userId);
            StringEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
            } catch (UnsupportedEncodingException e) {
                
                e.printStackTrace();
                return;
            }

            Log.e("URL +2D", ApiPaths.DATA_BACK_UP+"/2d");
           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/2d",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //logic to save the updated fields

                    try {
                        JSONArray tasks = response.getJSONArray("invoicePayments");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropInvoicePayment cropPayment = dbHandler.getCropPayment( tasks.getJSONObject(i).getString("localId"),false);
                                cropPayment.setGlobalId(tasks.getJSONObject(i).getString("globalId"));
                                cropPayment.setSyncStatus("yes");
                                dbHandler.updateCropPayment(cropPayment);

                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray tasks = response.getJSONArray("paymentBills");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropPaymentBill cropPayment = dbHandler.getCropPaymentBill( tasks.getJSONObject(i).getString("localId"),false);
                                cropPayment.setGlobalId(tasks.getJSONObject(i).getString("globalId"));
                                cropPayment.setSyncStatus("yes");
                                dbHandler.updateCropPaymentBill(cropPayment);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startBlock1eBackup();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE 2D", errorResponse.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 2D: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 2D: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void startBlock2eBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);

        

        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("productItems",prepareProductItems());
           requestObject.put("userId",userId);
            StringEntity params = null;
            try {
                params = new StringEntity(requestObject.toString());
            } catch (UnsupportedEncodingException e) {
                
                e.printStackTrace();
                return;
            }

            Log.e("URL +2E", ApiPaths.DATA_BACK_UP+"/2e");

           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP+"/2e",           params,"application/json", new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray tasks = response.getJSONArray("productItems");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropProductItem productItem = dbHandler.getCropProductItem( tasks.getJSONObject(i).getString("localId"),false);
                                productItem.setGlobalId(tasks.getJSONObject(i).getString("globalId"));
                                productItem.setSyncStatus("yes");
                                dbHandler.updateCropProductItem(productItem);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE 2E", errorResponse.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 2E: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 2E: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    private void backupDeletedRecords(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);



        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("delete",prepareDeleteRecords());
            requestObject.put("userId",userId);
            StringEntity params = null;
            Log.d("Deleted this", requestObject.toString());
            try {
                params = new StringEntity(requestObject.toString());
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
                return;
            }


           client.post(CropSyncService.this,ApiPaths.DATA_BACK_UP_DELETED_RECORDS,           params,"application/json", new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray tasks = response.getJSONArray("deleted");
                        Log.d("Deleted stuff", tasks.toString());
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                DeletedRecord productItem = dbHandler.getDeletedRecord( tasks.getJSONObject(i).getString("id"));
                                productItem.setSyncStatus("yes");
                                dbHandler.updateDeletedRecord(productItem);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE DELETES", errorResponse.toString());
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 2E: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 2E: "+statusCode, "Something got very very wrong");
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }



}
