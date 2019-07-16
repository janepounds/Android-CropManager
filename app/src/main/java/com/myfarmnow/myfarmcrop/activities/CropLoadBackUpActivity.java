package com.myfarmnow.myfarmcrop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
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
import com.myfarmnow.myfarmcrop.models.CropInvoicePayment;
import com.myfarmnow.myfarmcrop.models.CropIrrigation;
import com.myfarmnow.myfarmcrop.models.CropMachine;
import com.myfarmnow.myfarmcrop.models.CropMachineService;
import com.myfarmnow.myfarmcrop.models.CropMachineTask;
import com.myfarmnow.myfarmcrop.models.CropNote;
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
import com.myfarmnow.myfarmcrop.services.CropSyncService;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CropLoadBackUpActivity extends AppCompatActivity {

    MyFarmDbHandlerSingleton dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
    String userId = null;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_load_back_up);
        userId = CropDashboardActivity.getPreferences(CropDashboardActivity.PREFERENCES_USER_ID, this);
        dialog = new ProgressDialog(CropLoadBackUpActivity.this);
        dialog.setIndeterminate(true);

        loadBlock1TablesData();
        loadBlock2TablesData();
    }

    public void continueToDashboard(View view){
        finish();
        startActivity(new Intent(this, CropDashboardActivity.class));
    }

    /**
     * 1. send fields, contacts, employees as block 1a
     * 2. after 1a send Seed, Spray and fertilizer Inventory as block 1b
     * 3. after 1b send crops, soil analysis, machines as block 1c
     * 4. after 1c send (harvest, spraying, fertilizer application) as block 1d, (cultivation, scoutings, irrigation) as block 1e,
          (task, transplantings, income/expense) as block 1f, (machine service, notes, tasks) as 1g
     *
     */
    private void loadBlock1TablesData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();

        client.get(ApiPaths.DATA_BACK_UP+"/1a/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    //logic to save the insertd fields



                    try {
                        JSONArray fields = response.getJSONArray("fields");
                        for(int i=0; i<fields.length(); i++){

                            try{
                                CropField field =new CropField(fields.getJSONObject(i));
                                field.setGlobalId(fields.getJSONObject(i).getString("id"));
                                field.setSyncStatus("yes");
                                dbHandler.insertCropField(field);

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
                                CropContact contact = new CropContact(contacts.getJSONObject(i));
                                contact.setGlobalId(contacts.getJSONObject(i).getString("id"));
                                contact.setSyncStatus("yes");
                                dbHandler.insertCropContact(contact);
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
                                CropEmployee employee = new CropEmployee(employees.getJSONObject(i));
                                employee.setGlobalId(employees.getJSONObject(i).getString("id"));
                                employee.setSyncStatus("yes");
                                dbHandler.insertCropEmployee(employee);
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
                                CropSettingsSingleton settingsSingleton =new CropSettingsSingleton(settings.getJSONObject(i));
                                settingsSingleton.setGlobalId(settings.getJSONObject(i).getString("id"));
                                settingsSingleton.setSyncStatus("yes");
                                dbHandler.insertSettings(settingsSingleton);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadBlock1bData();
                    dialog.dismiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                  
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }
                    dialog.dismiss();

                }
            });
    }

    private void loadBlock1bData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);

        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/1b/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    //logic to  insertd the synced tables
                    try {
                        JSONArray inventorySeeds = response.getJSONArray("inventorySeeds");
                        for(int i=0; i<inventorySeeds.length(); i++){


                            try{
                                CropInventorySeeds inventorySeed =  new CropInventorySeeds(inventorySeeds.getJSONObject(i));
                                inventorySeed.setGlobalId(inventorySeeds.getJSONObject(i).getString("id"));
                                inventorySeed.setSyncStatus("yes");
                                dbHandler.insertCropSeeds(inventorySeed);
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
                        JSONArray inventoryFertilizers = response.getJSONArray("inventoryFertilizers");
                        Log.d("Fertilizers LIST",inventoryFertilizers.toString());
                        for(int i=0; i<inventoryFertilizers.length(); i++){


                            try{
                                CropInventoryFertilizer cropFertilizer = new CropInventoryFertilizer(inventoryFertilizers.getJSONObject(i));
                                cropFertilizer.setGlobalId(inventoryFertilizers.getJSONObject(i).getString("id"));
                                cropFertilizer.setSyncStatus("yes");
                                dbHandler.insertCropFertilizerInventory(cropFertilizer);
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
                    try {
                        JSONArray inventorySprays = response.getJSONArray("inventorySprays");
                        Log.d("Fertilizers LIST",inventorySprays.toString());
                        for(int i=0; i<inventorySprays.length(); i++){
                            try{
                                CropInventorySpray inventorySeed = new CropInventorySpray(inventorySprays.getJSONObject(i));
                                inventorySeed.setGlobalId(inventorySprays.getJSONObject(i).getString("id"));
                                inventorySeed.setSyncStatus("yes");
                                dbHandler.insertCropSpray(inventorySeed);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    loadBlock1cData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                  
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }
    private void loadBlock1cData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/1c/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();

                    try {
                        JSONArray crops = response.getJSONArray("crops");
                        for(int i=0; i<crops.length(); i++){
                            try{
                                Crop crop =  new Crop(crops.getJSONObject(i));
                                crop.setGlobalId(crops.getJSONObject(i).getString("id"));
                                crop.setSyncStatus("yes");
                                CropField field = dbHandler.getCropField(crop.getFieldId(),true);
                                if(field != null){
                                    Log.d("FIELD FOR CROP",field.toJSON().toString());
                                    crop.setFieldId(field.getId());
                                    CropInventorySeeds inventorySeed = dbHandler.getCropSeed(crop.getSeedId(),true);
                                    if(inventorySeed != null){
                                        crop.setSeedId(inventorySeed.getId());
                                    }
                                    dbHandler.insertCrop(crop);
                                }

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

                    try {
                        JSONArray crops = response.getJSONArray("soilAnalysis");
                        for(int i=0; i<crops.length(); i++){
                            try{
                                CropSoilAnalysis analysis = new CropSoilAnalysis(crops.getJSONObject(i));
                                analysis.setGlobalId(crops.getJSONObject(i).getString("id"));
                                analysis.setSyncStatus("yes");
                                CropField field = dbHandler.getCropField(analysis.getFieldId(),true);
                                if(field != null){
                                    analysis.setFieldId(field.getId());
                                    dbHandler.insertCropSoilAnalysis(analysis);
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray machines = response.getJSONArray("machines");
                        for(int i=0; i<machines.length(); i++){
                            try{
                                CropMachine machine = new CropMachine(machines.getJSONObject(i));
                                machine.setGlobalId(machines.getJSONObject(i).getString("id"));
                                machine.setSyncStatus("yes");
                                dbHandler.insertCropMachine(machine);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    loadBlock1dData();
                    loadBlock1eData();
                    loadBlock1fData();
                    loadBlock1gData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }
    private void loadBlock1dData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/1d/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    //logic to save the insertd fields
                    try {
                        JSONArray crops = response.getJSONArray("harvests");
                        for(int i=0; i<crops.length(); i++){
                            try{
                                CropHarvest harvest = new CropHarvest(crops.getJSONObject(i));
                                harvest.setGlobalId(crops.getJSONObject(i).getString("id"));
                                harvest.setSyncStatus("yes");
                                Crop crop = dbHandler.getCrop(harvest.getCropId(),true);
                                if(crop != null){
                                    harvest.setCropId(crop.getId());
                                    dbHandler.insertCropHarvest(harvest);
                                }

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
                                CropSpraying harvest = new CropSpraying(application.getJSONObject(i));
                                harvest.setGlobalId(application.getJSONObject(i).getString("id"));
                                harvest.setSyncStatus("yes");
                                Crop crop = dbHandler.getCrop(harvest.getCropId(),true);
                                if(crop != null){
                                    harvest.setCropId(crop.getId());
                                    dbHandler.insertCropSpraying(harvest);
                                }
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
                                CropFertilizerApplication harvest = new CropFertilizerApplication(application.getJSONObject(i));
                                harvest.setGlobalId(application.getJSONObject(i).getString("id"));
                                harvest.setSyncStatus("yes");
                                Crop crop = dbHandler.getCrop(harvest.getCropId(),true);
                                if(crop != null){
                                    harvest.setCropId(crop.getId());
                                    dbHandler.insertCropFertilizerApplication(harvest);
                                }

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
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }
    private void loadBlock1eData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/1e/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    try {
                        JSONArray application = response.getJSONArray("cultivations");
                        for(int i=0; i<application.length(); i++){
                            try{
                                CropCultivation harvest =  new CropCultivation(application.getJSONObject(i));
                                harvest.setGlobalId(application.getJSONObject(i).getString("id"));
                                harvest.setSyncStatus("yes");
                                Crop crop = dbHandler.getCrop(harvest.getCropId(),true);
                                if(crop != null){
                                    harvest.setCropId(crop.getId());
                                    dbHandler.insertCropCultivate(harvest);
                                }
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
                                CropScouting harvest = new CropScouting(application.getJSONObject(i));
                                harvest.setGlobalId(application.getJSONObject(i).getString("id"));
                                harvest.setSyncStatus("yes");
                                Crop crop = dbHandler.getCrop(harvest.getCropId(),true);
                                if(crop != null){
                                    harvest.setCropId(crop.getId());
                                    dbHandler.insertCropScouting(harvest);
                                }
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
                                CropIrrigation harvest = new CropIrrigation(application.getJSONObject(i));
                                harvest.setGlobalId(application.getJSONObject(i).getString("id"));
                                harvest.setSyncStatus("yes");
                                Crop crop = dbHandler.getCrop(harvest.getCropId(),true);
                                if(crop != null){
                                    harvest.setCropId(crop.getId());
                                    dbHandler.insertCropIrrigation(harvest);
                                }
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
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }
    private void loadBlock1fData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/1f/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();

                    try {
                        JSONArray transplantings = response.getJSONArray("transplantings");
                        for(int i=0; i<transplantings.length(); i++){
                            try{
                                CropTransplanting harvest =  new CropTransplanting(transplantings.getJSONObject(i));
                                harvest.setGlobalId(transplantings.getJSONObject(i).getString("id"));
                                harvest.setSyncStatus("yes");
                                Crop crop = dbHandler.getCrop(harvest.getCropId(),true);
                                if(crop != null){
                                    harvest.setCropId(crop.getId());
                                    dbHandler.insertCropTransplanting(harvest);
                                }
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
                                CropIncomeExpense incomeExpense = new CropIncomeExpense(incomeExpenses.getJSONObject(i));
                                incomeExpense.setGlobalId(incomeExpenses.getJSONObject(i).getString("id"));
                                incomeExpense.setSyncStatus("yes");
                                Crop crop = dbHandler.getCrop(incomeExpense.getCropId(),true);
                                if(crop != null){
                                    incomeExpense.setCropId(crop.getId());
                                }
                                dbHandler.insertCropIncomeExpense(incomeExpense);
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
                                CropTask cropTask = new CropTask(tasks.getJSONObject(i));
                                cropTask.setGlobalId(tasks.getJSONObject(i).getString("id"));
                                cropTask.setSyncStatus("yes");
                                Crop crop = dbHandler.getCrop(cropTask.getCropId(),true);
                                if(crop != null){
                                    cropTask.setCropId(crop.getId());
                                    dbHandler.insertCropTask(cropTask);
                                }
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
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }
    private void loadBlock1gData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/1g/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    //logic to save the insertd fields
                    try {
                        JSONArray services = response.getJSONArray("machineServices");
                        for(int i=0; i<services.length(); i++){
                            try{
                                CropMachineService service =new CropMachineService(services.getJSONObject(i));
                                service.setGlobalId(services.getJSONObject(i).getString("id"));
                                service.setSyncStatus("yes");
                                CropMachine machine = dbHandler.getCropMachine(service.getMachineId(),true);
                                if(machine != null){
                                    service.setMachineId(machine.getId());
                                    dbHandler.insertCropMachineService(service);
                                }
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
                                cropTask.setGlobalId(tasks.getJSONObject(i).getString("id"));
                                cropTask.setSyncStatus("yes");
                                CropMachine machine = dbHandler.getCropMachine(cropTask.getMachineId(),true);
                                if(machine != null){
                                    cropTask.setMachineId(machine.getId());
                                    dbHandler.insertCropMachineTask(cropTask);
                                }
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
                                CropNote note = new CropNote(tasks.getJSONObject(i));
                                note.setGlobalId(tasks.getJSONObject(i).getString("id"));
                                note.setSyncStatus("yes");

                                if(note.getIsFor().equals(CropNote.IS_FOR_MACHINE)){
                                    CropMachine machine = dbHandler.getCropMachine(note.getParentId(),true);
                                    if(machine != null){
                                        note.setParentId(machine.getId());
                                        dbHandler.insertCropNote(note);
                                    }
                                }
                                else if(note.getIsFor().equals(CropNote.IS_FOR_CROP)){
                                    Crop crop = dbHandler.getCrop(note.getParentId(),true);
                                    if(crop != null){
                                        note.setParentId(crop.getId());
                                        dbHandler.insertCropNote(note);
                                    }
                                }

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
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }

    /**
     * COVERS FINANCIAL MANAGER
     * 1. send customers, suppliers, products as block 2a
     * 2. after 2a send Estimates, Invoice, Sales Order as block 2b
     * 3. after 2b send Purchase Order, Bills as block 2c
     * 4. after 2c send Invoice Payments and Bill payments as 2d
     * 5. After 2d send product items as 2e
     */
    private void loadBlock2TablesData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/2a/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    //logic to save the insertd fields

                    try {
                        JSONArray customers = response.getJSONArray("customers");
                        for(int i=0; i<customers.length(); i++){


                            try{
                                CropCustomer field = new CropCustomer(customers.getJSONObject(i));
                                field.setGlobalId(customers.getJSONObject(i).getString("id"));
                                field.setSyncStatus("yes");
                                dbHandler.insertCropCustomer(field);
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
                                CropSupplier field = new CropSupplier(suppliers.getJSONObject(i));
                                field.setGlobalId(suppliers.getJSONObject(i).getString("id"));
                                field.setSyncStatus("yes");
                                dbHandler.insertCropSupplier(field);
                            }catch (JSONException e) {
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
                                CropProduct field =  new CropProduct(fields.getJSONObject(i));
                                field.setGlobalId(fields.getJSONObject(i).getString("id"));
                                field.setSyncStatus("yes");
                                dbHandler.insertCropProduct(field);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    loadBlock2bData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }

    private void loadBlock2bData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/2b/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    //logic to save the insertd fields
                    try {
                        JSONArray tasks = response.getJSONArray("estimates");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropEstimate estimate = new CropEstimate(tasks.getJSONObject(i));
                                estimate.setGlobalId(tasks.getJSONObject(i).getString("id"));
                                estimate.setSyncStatus("yes");
                                CropCustomer customer = dbHandler.getCropCustomer(estimate.getCustomerId(),true);
                                if(customer != null){
                                    estimate.setCustomerId(customer.getId());
                                    dbHandler.insertCropEstimate(estimate);
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray invoices = response.getJSONArray("invoices");
                        for(int i=0; i<invoices.length(); i++){
                            try{
                                CropInvoice invoice = new CropInvoice(invoices.getJSONObject(i));
                                invoice.setGlobalId(invoices.getJSONObject(i).getString("id"));
                                invoice.setSyncStatus("yes");
                                CropCustomer customer = dbHandler.getCropCustomer(invoice.getCustomerId(),true);
                                if(customer != null){
                                    invoice.setCustomerId(customer.getId());
                                    dbHandler.insertCropInvoice(invoice);
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONArray salesOrders = response.getJSONArray("salesOrders");
                        for(int i=0; i<salesOrders.length(); i++){
                            try{
                                CropSalesOrder salesOrder = new CropSalesOrder(salesOrders.getJSONObject(i));
                                salesOrder.setGlobalId(salesOrders.getJSONObject(i).getString("id"));
                                salesOrder.setSyncStatus("yes");
                                CropCustomer customer = dbHandler.getCropCustomer(salesOrder.getCustomerId(),true);
                                if(customer != null){
                                    salesOrder.setCustomerId(customer.getId());
                                    dbHandler.insertCropSalesOrder(salesOrder);
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadBlock2cData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }
    private void loadBlock2cData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/2c/"+userId, params, new JsonHttpResponseHandler() {
                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    //logic to save the insertd fields

                    try {
                        JSONArray bills = response.getJSONArray("bills");
                        for(int i=0; i<bills.length(); i++){
                            try{
                                CropBill bill = new CropBill(bills.getJSONObject(i));
                                bill.setGlobalId(bills.getJSONObject(i).getString("id"));
                                bill.setSyncStatus("yes");
                                CropCustomer customer = dbHandler.getCropCustomer(bill.getSupplierId(),true);
                                if(customer != null){
                                    bill.setSupplierId(customer.getId());
                                    dbHandler.insertCropBill(bill);
                                }
                            }catch (JSONException e) {
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
                                CropPurchaseOrder purchaseOrder = new CropPurchaseOrder(purchaseOrders.getJSONObject(i));
                                purchaseOrder.setGlobalId(purchaseOrders.getJSONObject(i).getString("id"));
                                purchaseOrder.setSyncStatus("yes");
                                CropCustomer customer = dbHandler.getCropCustomer(purchaseOrder.getSupplierId(),true);
                                if(customer != null){
                                    purchaseOrder.setSupplierId(customer.getId());
                                    dbHandler.insertCropPurchaseOrder(purchaseOrder);
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadBlock2dData();
                    loadBlock2eData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }
    private void loadBlock2dData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/2d/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    //logic to save the insertd fields

                    try {
                        JSONArray tasks = response.getJSONArray("invoicePayments");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropInvoicePayment cropPayment =    new CropInvoicePayment(tasks.getJSONObject(i));
                                cropPayment.setGlobalId(tasks.getJSONObject(i).getString("id"));
                                cropPayment.setSyncStatus("yes");
                                CropCustomer machine = dbHandler.getCropCustomer(cropPayment.getCustomerId(),true);
                                if(machine != null){
                                    cropPayment.setCustomerId(machine.getId());
                                    CropInvoice invoice = dbHandler.getCropInvoiceById(cropPayment.getInvoiceId(),true);
                                    if(invoice != null){
                                        cropPayment.setInvoiceId(invoice.getId());
                                        dbHandler.insertCropPayment(cropPayment);
                                    }
                                }
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
                        JSONArray paymentBills = response.getJSONArray("paymentBills");
                        for(int i=0; i<paymentBills.length(); i++){
                            try{
                                CropPaymentBill cropPayment =    new CropPaymentBill(paymentBills.getJSONObject(i));
                                cropPayment.setGlobalId(paymentBills.getJSONObject(i).getString("id"));
                                cropPayment.setSyncStatus("yes");
                                CropSupplier machine = dbHandler.getCropSupplier(cropPayment.getSupplierId(),true);
                                if(machine != null){
                                    cropPayment.setSupplierId(machine.getId());
                                    CropBill invoice = dbHandler.getCropBillById(cropPayment.getBillId(),true);
                                    if(invoice != null){
                                        cropPayment.setBillId(invoice.getId());
                                        dbHandler.insertCropPaymentBill(cropPayment);
                                    }
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadBlock1eData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }
    private void loadBlock2eData(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/2e/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {
                    dialog.setMessage("Please Wait..");
                    dialog.setCancelable(false);
                    dialog.show();


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    dialog.dismiss();
                    try {
                        JSONArray tasks = response.getJSONArray("productItems");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropProductItem productItem =  new CropProductItem(tasks.getJSONObject(i));
                                productItem.setGlobalId(tasks.getJSONObject(i).getString("id"));
                                productItem.setSyncStatus("yes");
                                if(productItem.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_BILL)){
                                    CropBill bill = dbHandler.getCropBillById(productItem.getParentObjectId(),true);
                                    if(bill ==null){
                                        continue; //item has no assigned bill
                                    }
                                    productItem.setParentObjectId(bill.getId());
                                }else  if(productItem.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_INVOICE)){
                                    CropInvoice invoice = dbHandler.getCropInvoiceById(productItem.getParentObjectId(),true);
                                    if(invoice ==null){
                                        continue; //item has no assigned bill
                                    }
                                    productItem.setParentObjectId(invoice.getId());
                                }else  if(productItem.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_ESTIMATE)){
                                    CropEstimate estimate = dbHandler.getCropEstimateById(productItem.getParentObjectId(),true);
                                    if(estimate ==null){
                                        continue; //item has no assigned bill
                                    }
                                    productItem.setParentObjectId(estimate.getId());
                                }else if(productItem.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_SALES_ORDER)){
                                    CropSalesOrder cropSalesOrder = dbHandler.getCropSalesOrderById(productItem.getParentObjectId(),true);
                                    if(cropSalesOrder ==null){
                                        continue; //item has no assigned bill
                                    }
                                    productItem.setParentObjectId(cropSalesOrder.getId());
                                }else if(productItem.getParentObjectType().equals(MyFarmDbHandlerSingleton.CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER)){
                                    CropPurchaseOrder purchaseOrder = dbHandler.getCropPurchaseOrderById(productItem.getParentObjectId(),true);
                                    if(purchaseOrder ==null){
                                        continue; //item has no assigned bill
                                    }
                                    productItem.setParentObjectId(purchaseOrder.getId());
                                }
                                else{
                                    continue;//ignore unallocated items
                                }
                                dbHandler.insertCropProductItem(productItem);
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
                    Log.e("RESPONSE", "failed ");
                    dialog.dismiss();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     dialog.dismiss();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }
                    dialog.dismiss();

                }
            });
    }
}
