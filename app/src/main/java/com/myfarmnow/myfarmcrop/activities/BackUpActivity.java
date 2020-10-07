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
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropNote;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class BackUpActivity extends AppCompatActivity {

    MyFarmDbHandlerSingleton dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
    String userId = null;
    ProgressDialog dialog;
    Boolean block1Completed = false;
    Boolean block2Completed = false;
    Boolean deletesCompleted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_load_back_up);
        userId = DashboardActivity.getPreferences(DashboardActivity.RETRIEVED_USER_ID, this);
        dialog = new ProgressDialog(BackUpActivity.this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Synchronizing! Please wait..");
        dialog.setCancelable(false);
        dialog.show();
        loadBlock1TablesData();
        loadBlock2TablesData();
    }

    public  void attemptToStopProgress(){
        if(block1Completed && block2Completed ){
            Log.d("STOPPING SERVICE", "SYNC SERVICE FINISHED");
            dialog.dismiss();
            continueToDashboard(null);
        }
    }

    public void continueToDashboard(View view){
        finish();
        startActivity(new Intent(this, DashboardActivity.class));
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
                   
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   
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
                        JSONObject settings = response.getJSONObject("settings");

                        try{
                            CropSettingsSingleton settingsSingleton =new CropSettingsSingleton(settings);
                            settingsSingleton.setGlobalId(settings.getString("id"));
                            settingsSingleton.setSyncStatus("yes");
                            dbHandler.insertSettings(settingsSingleton);
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadBlock1bData();
                   
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block1Completed = true;
                    attemptToStopProgress();
              
                  
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                   
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }
                     block1Completed = true;
                    attemptToStopProgress();

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
                   

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   
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
                     block1Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {

                     block1Completed = true;
                    attemptToStopProgress();
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



                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


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
                                    CropInventorySeeds inventorySeed = dbHandler.getCropSeed(crop.getName(),true);
                                    if(inventorySeed != null){
                                        crop.setName(inventorySeed.getId());
                                    }
                                    Log.d("RETURNED CROP:",crop.toJSON().toString());
                                    Log.d("CORRESPONDING FIELD:",field.toJSON().toString());
                                    dbHandler.insertCrop(crop);
                                }
                                Log.d("RETURNED CROP:",crop.toJSON().toString());
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

                    loadBlock1dData();
//                    loadBlock1eData();
                    loadBlock1fData();
                    loadBlock1gData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block1Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     block1Completed = true;
                    attemptToStopProgress();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }

                }
            });
    }
    private void loadBlock1dData(){
        Log.d("BLOCK-1D DATA", "block 1d data");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        final RequestParams params = new RequestParams();
        client.get(ApiPaths.DATA_BACK_UP+"/1d/"+userId, params, new JsonHttpResponseHandler() {

                @Override
                             public void onStart() {



                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    //logic to save the insertd fields
                    Log.d("RESPONSE FA:",response.toString());
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
                        Log.d("FERTILIZER-APPLICATION",application.toString());
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
                    block1Completed = true;
                    attemptToStopProgress();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block1Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     block1Completed = true;
                    attemptToStopProgress();
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



                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {


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

                     block1Completed = true;
                    attemptToStopProgress();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block1Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     block1Completed = true;
                    attemptToStopProgress();
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



                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        JSONArray tasks = response.getJSONArray("notes");
                        for(int i=0; i<tasks.length(); i++){
                            try{
                                CropNote note = new CropNote(tasks.getJSONObject(i));
                                note.setGlobalId(tasks.getJSONObject(i).getString("id"));
                                note.setSyncStatus("yes");

                                if(note.getIsFor().equals(CropNote.IS_FOR_CROP)){
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
                     block1Completed = true;
                    attemptToStopProgress();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block1Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     block1Completed = true;
                    attemptToStopProgress();
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
     * 1. send customers, suppliers, buy_inputs_products as block 2a
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
                    


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   
                    //logic to save the insertd fields
                    Log.d("BLOCK 2A",response.toString());
                    loadBlock2bData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block2Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     block2Completed = true;
                    attemptToStopProgress();
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
                    


                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   

                    Log.d("BLOCK 2B",response.toString());


                    loadBlock2cData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block2Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     block2Completed = true;
                    attemptToStopProgress();
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
                   ;
                }
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   
                    Log.d("BLOCK 2C",response.toString());
                    loadBlock2dData();
                    loadBlock2eData();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block2Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     block2Completed = true;
                    attemptToStopProgress();
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

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                   
                    //logic to save the insertd fields

                     block2Completed = true;
                    attemptToStopProgress();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block2Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     block2Completed = true;
                    attemptToStopProgress();
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

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                     block2Completed = true;
                    attemptToStopProgress();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("RESPONSE", "failed ");
                     block2Completed = true;
                    attemptToStopProgress();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                     block2Completed = true;
                    attemptToStopProgress();
                    if (errorResponse != null) {
                        Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info : "+statusCode, "Something got very very wrong");
                    }
                   

                }
            });
    }
}
