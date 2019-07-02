package com.myfarmnow.myfarmcrop.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
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
import com.myfarmnow.myfarmcrop.models.CropInventory;
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
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

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
            requestObject.put("fertilizerApplication",prepareFertilizerApplication());
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
            Crop crop = dbHandler.getCrop(record.getCropId());
            if(crop !=null){
                if(crop.getGlobalId()==null){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setCropId(crop.getGlobalId());
            }
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareBills(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropBill> records = dbHandler.getCropBills(userId,false);
        for(CropBill record: records){
            CropSupplier supplier = dbHandler.getCropSupplier(record.getSupplierId());
            if(supplier ==null){
                continue; //item has no assigned supplier
            }
            if(supplier.getGlobalId()==null){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setSupplierId(supplier.getGlobalId());
            jsonArray.put(record.toJSON());
        }
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
        ArrayList<Crop> records = dbHandler.getCrops(userId,false);
        for(Crop crop: records){
            CropField field = dbHandler.getCropField(crop.getFieldId());
            if(field ==null){
                continue; //crop has no assigned field
            }
            if(field.getGlobalId()==null){
                continue; //do not back up this crop since its parent field is not backed up
            }
            crop.setFieldId(field.getGlobalId());

            CropInventorySeeds seed = dbHandler.getCropSeed(crop.getSeedId());

            if(seed != null){
                if(seed.getGlobalId()==null){
                    continue; //do not back up this crop since its parent seed inventory is not backed up
                }
                crop.setSeedId(seed.getGlobalId()); //change the seedId it to map the global Id
            }

            jsonArray.put(crop.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareCultivations(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropCultivation> records = dbHandler.getCropCultivates(userId,false);
        for(CropCultivation record: records){
            Crop crop = dbHandler.getCrop(record.getCropId());
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getGlobalId()==null){
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
        ArrayList<CropEstimate> records = dbHandler.getCropEstimates(userId,false);
        for(CropEstimate record: records){
            CropCustomer customer = dbHandler.getCropCustomer(record.getCustomerId());
            if(customer ==null){
                continue; //item has no assigned customer
            }
            if(customer.getGlobalId()==null){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setCustomerId(customer.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareHarvest(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropHarvest> records = dbHandler.getCropHarvests(userId,false);
        for(CropHarvest record: records){
            Crop crop = dbHandler.getCrop(record.getCropId());
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getGlobalId()==null){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareFertilizerApplication(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropFertilizerApplication> records = dbHandler.getCropFertilizerApplication(userId,false);
        for(CropFertilizerApplication record: records){
            Crop crop = dbHandler.getCrop(record.getCropId());
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getGlobalId()==null){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());

            CropInventoryFertilizer fertilizer = dbHandler.getCropFertilizerApplicationById(record.getFertilizerId());
            if(fertilizer ==null){
                continue; //spraying has no assigned spray
            }
            if(fertilizer.getGlobalId()==null){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setFertilizerId(fertilizer.getGlobalId());
            jsonArray.put(record.toJSON());
        }
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
        ArrayList<CropInvoice> records = dbHandler.getCropInvoices(userId,false);
        for(CropInvoice record: records){
            CropCustomer customer = dbHandler.getCropCustomer(record.getCustomerId());
            if(customer ==null){
                continue; //item has no assigned bill
            }
            if(customer.getGlobalId()==null){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setCustomerId(customer.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareIrrigations(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropIrrigation> records = dbHandler.getCropIrrigations(userId,false);
        for(CropIrrigation record: records){
            Crop crop = dbHandler.getCrop(record.getCropId());
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getGlobalId()==null){
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
            CropMachine machine = dbHandler.getCropMachine(record.getMachineId());
            if(machine ==null){
                continue; //note has no assigned machine
            }
            if(machine.getGlobalId()==null){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setMachineId(machine.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareMachineTasks(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropMachineTask> records = dbHandler.getCropMachineTasks(userId,false);
        for(CropMachineTask record: records){
            CropMachine machine = dbHandler.getCropMachine(record.getMachineId());
            if(machine ==null){
                continue; //note has no assigned machine
            }
            if(machine.getGlobalId()==null){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setMachineId(machine.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareNotes(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropNote> records = dbHandler.getCropNotes(userId,false);
        for(CropNote record: records){
            if(record.getIsFor().equals(CropNote.IS_FOR_CROP)){
                Crop crop = dbHandler.getCrop(record.getParentId());
                if(crop ==null){
                    continue; //note has no assigned crop
                }
                if(crop.getGlobalId()==null){
                    continue; //do not back up this record since its parent field is not backed up
                }
                record.setParentId(crop.getGlobalId());
            }else if(record.getIsFor().equals(CropNote.IS_FOR_MACHINE)){
                CropMachine machine = dbHandler.getCropMachine(record.getParentId());
                if(machine ==null){
                    continue; //note has no assigned machine
                }
                if(machine.getGlobalId()==null){
                    continue; //do not back up this record since its parent field is not backed up
                }
                record.setParentId(machine.getGlobalId());
            }
            else{
                continue;
            }
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray preparePayments(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropInvoicePayment> records = dbHandler.getCropPayments(userId,false);
        for(CropInvoicePayment record: records){
            CropInvoice invoice = dbHandler.getCropInvoiceById(record.getInvoiceId());
            if(invoice !=null){
                if(invoice.getGlobalId()==null){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setInvoiceId(invoice.getGlobalId());
            }
            CropCustomer customer = dbHandler.getCropCustomer(record.getCustomerId());
            if(customer ==null){
                continue; //item has no assigned customer
            }
            if(customer.getGlobalId()==null){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setCustomerId(customer.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray preparePaymentBills(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropPaymentBill> records = dbHandler.getCropPaymentBills(userId,false);
        for(CropPaymentBill record: records){
            CropBill bill = dbHandler.getCropBillById(record.getBillId());
            if(bill !=null){
                if(bill.getGlobalId()==null){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setBillId(bill.getGlobalId());
            }
            CropSupplier supplier = dbHandler.getCropSupplier(record.getSupplierId());
            if(supplier ==null){
                continue; //item has no assigned customer
            }
            if(supplier.getGlobalId()==null){
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
            if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_BILL)){
                CropBill bill = dbHandler.getCropBillById(record.getParentObjectId());
                if(bill ==null){
                    continue; //item has no assigned bill
                }
                if(bill.getGlobalId()==null){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setParentObjectId(bill.getGlobalId());
            }else  if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_INVOICE)){
                CropInvoice invoice = dbHandler.getCropInvoiceById(record.getParentObjectId());
                if(invoice ==null){
                    continue; //item has no assigned bill
                }
                if(invoice.getGlobalId()==null){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setParentObjectId(invoice.getGlobalId());
            }else  if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_ESTIMATE)){
                CropEstimate estimate = dbHandler.getCropEstimateById(record.getParentObjectId());
                if(estimate ==null){
                    continue; //item has no assigned bill
                }
                if(estimate.getGlobalId()==null){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setParentObjectId(estimate.getGlobalId());
            }else if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_SALES_ORDER)){
                CropSalesOrder cropSalesOrder = dbHandler.getCropSalesOrderById(record.getParentObjectId());
                if(cropSalesOrder ==null){
                    continue; //item has no assigned bill
                }
                if(cropSalesOrder.getGlobalId()==null){
                    continue; //do not back up this record since its parent record is not backed up
                }
                record.setParentObjectId(cropSalesOrder.getGlobalId());
            }else if(record.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER)){
                CropPurchaseOrder purchaseOrder = dbHandler.getCropPurchaseOrderById(record.getParentObjectId());
                if(purchaseOrder ==null){
                    continue; //item has no assigned bill
                }
                if(purchaseOrder.getGlobalId()==null){
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
            CropSupplier supplier = dbHandler.getCropSupplier(record.getSupplierId());
            if(supplier ==null){
                continue; //item has no assigned supplier
            }
            if(supplier.getGlobalId()==null){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setSupplierId(supplier.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareSalesOrders(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropSalesOrder> records = dbHandler.getCropSalesOrders(userId,false);
        for(CropSalesOrder record: records){
            CropCustomer customer = dbHandler.getCropCustomer(record.getCustomerId());
            if(customer ==null){
                continue; //item has no assigned bill
            }
            if(customer.getGlobalId()==null){
                continue; //do not back up this record since its parent record is not backed up
            }
            record.setCustomerId(customer.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareScoutings(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropScouting> records = dbHandler.getCropScoutings(userId,false);
        for(CropScouting record: records){
            Crop crop = dbHandler.getCrop(record.getCropId());
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getGlobalId()==null){
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
        jsonArray.put(singleton);

        return jsonArray;
    }
    private JSONArray prepareSoilAnalysis(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropSoilAnalysis> records = dbHandler.getCropSoilAnalysis(userId,false);
        for(CropSoilAnalysis record: records){
            CropField field = dbHandler.getCropField(record.getFieldId());
            if(field ==null){
                continue; //crop has no assigned field
            }
            if(field.getGlobalId()==null){
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
            Crop crop = dbHandler.getCrop(record.getCropId());
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getGlobalId()==null){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            CropInventorySpray spray = dbHandler.getCropSprayById(record.getSprayId());
            if(spray ==null){
                continue; //spraying has no assigned spray
            }
            if(spray.getGlobalId()==null){
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
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareTasks(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropTask> records = dbHandler.getCropTasks(userId,false);
        for(CropTask record: records){
            Crop crop = dbHandler.getCrop(record.getCropId());
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getGlobalId()==null){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }
    private JSONArray prepareTransplantings(){
        JSONArray jsonArray = new JSONArray();
        ArrayList<CropTransplanting> records = dbHandler.getCropTransplantings(userId,false);
        for(CropTransplanting record: records){
            Crop crop = dbHandler.getCrop(record.getCropId());
            if(crop ==null){
                continue; //crop has no assigned field
            }
            if(crop.getGlobalId()==null){
                continue; //do not back up this record since its parent field is not backed up
            }
            record.setCropId(crop.getGlobalId());
            jsonArray.put(record.toJSON());
        }
        return jsonArray;
    }

}
