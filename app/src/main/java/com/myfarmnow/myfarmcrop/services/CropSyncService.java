package com.myfarmnow.myfarmcrop.services;

/*
contentValues.put(CROP_SYNC_STATUS,field.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID,field.getGlobalId());
 */


import android.app.Service;
import android.content.Intent;

import android.os.IBinder;

import android.util.Log;

import androidx.annotation.Nullable;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.ApiPaths;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropNote;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
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
    Boolean block1Completed = false;
    Boolean block2Completed = false;
    Boolean deletesCompleted = false;
    public CropSyncService() {
        //super("Sync Service");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        userId = DashboardActivity.getPreferences(DashboardActivity.RETRIEVED_USER_ID, this);
        if(!userId.equals("")){
            prepareSyncRequest();

        }else{
            Log.d("STOPPING SERVICE", "NO USER LOGGED IN");
            stopSelf();
        }

        return  super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public  void attemptToStopService(){
        if(block1Completed && block2Completed && deletesCompleted){
            Log.d("STOPPING SERVICE", "SYNC SERVICE FINISHED");
            stopSelf();

        }
    }



    public void prepareSyncRequest(){
        try{
            startBlock1TablesBackup();
            //startBlock2TablesBackup();
            backupDeletedRecords();
            if(!DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_BACKED_UP,CropSyncService.this).equals("yes")){
                userBackup();
            }
        }catch (Exception e){
            e.printStackTrace();
            stopSelf();
        }

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

            CropInventorySeeds seed = dbHandler.getCropSeed(record.getName(),false);

            if(seed != null){

                if(seed.getSyncStatus().equals("no")){
                    continue; //do not back up this crop since its parent seed inventory is not backed up
                }
                record.setName(seed.getGlobalId()); //change the seedId it to map the global Id
            }
            else{
//                Log.d("CROPS SEED ",record.getSeedId());
            }

            jsonArray.put(record.toJSON());
        }
        Log.d("CROPS LIST",jsonArray.toString());
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
            }
            else{
                continue;
            }
            jsonArray.put(record.toJSON());
        }
        Log.d("NOTES LIST",jsonArray.toString());
        return jsonArray;
    }

    private JSONArray prepareSettings(){
        JSONArray jsonArray = new JSONArray();
        CropSettingsSingleton  singleton= dbHandler.getSettings(userId,false);
        if(singleton == null){
            return  jsonArray;
        }
        if(singleton.getGlobalId()==null){
            singleton.setGlobalId(generateUUID());
            dbHandler.updateSettings(singleton);
        }
        jsonArray.put(singleton.toJSON());
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

        Log.d("SPRAYINGS LIST",jsonArray.toString());
        return jsonArray;
    }


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
//            requestObject.put("contacts",prepareContacts());
//            requestObject.put("employees",prepareEmployees());
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
                        JSONArray settings = response.getJSONArray("settings");
                        for(int i=0; i<settings.length(); i++){
                            try{
                                CropSettingsSingleton settingsSingleton = dbHandler.getSettings(userId,false);
                                settingsSingleton.setGlobalId(settings.getJSONObject(i).getString("globalId"));
                                settingsSingleton.setSyncStatus("yes");
                                dbHandler.updateSettings(settingsSingleton);
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
                    startBlock1bBackup();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                   if(errorResponse != null){
                       Log.e("BACKUP RESPONSE 1A"+statusCode,errorResponse.toString());
                   }
                    block1Completed = true;
                    attemptToStopService();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1A: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1A: "+statusCode, "Something got very very wrong");
                    }
                    block1Completed = true;
                    attemptToStopService();
                }
            });


        }
        catch (JSONException e) {
            block1Completed = true;
            attemptToStopService();
            e.printStackTrace();
        }catch (Exception e) {
            block1Completed = true;
            attemptToStopService();
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
                    block1Completed = true;
                    attemptToStopService();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1B: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1B: "+statusCode, "Something got very very wrong");
                    }
                    block1Completed = true;
                    attemptToStopService();
                }
            });


        }  catch (JSONException e) {
            block1Completed = true;
            e.printStackTrace();
        }catch (Exception e) {
            block1Completed = true;
            e.printStackTrace();
        }
    }
    private void startBlock1cBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);


        JSONObject requestObject = new JSONObject();

        try {
            requestObject.put("crops",prepareCrops());
//            requestObject.put("soilAnalysis",prepareSoilAnalysis());
//            requestObject.put("machines",prepareMachines());
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
                                    CropInventorySeeds inventorySeed = dbHandler.getCropSeed(crop.getName(),false);
                                    if(inventorySeed != null){
                                        crop.setName(inventorySeed.getId());
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
                    block1Completed = true;
                    attemptToStopService();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1C: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1C: "+statusCode, "Something got very very wrong");
                    }
                    block1Completed = true;
                    attemptToStopService();

                }

            });


        }
        catch (JSONException e) {
            block1Completed = true;
            e.printStackTrace();
        }catch (Exception e) {
            block1Completed = true;
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
                    block1Completed = true;
                    attemptToStopService();

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE 1D", errorResponse.toString());
                    }
                    block1Completed = true;
                    attemptToStopService();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1D: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1D: "+statusCode, "Something got very very wrong");
                    }
                    block1Completed = true;
                    attemptToStopService();
                }
            });


        }
        catch (JSONException e) {
            block1Completed = true;
            e.printStackTrace();
        }catch (Exception e) {
            block1Completed = true;
            e.printStackTrace();
        }
    }
    private void startBlock1eBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);
        JSONObject requestObject = new JSONObject();

        try {
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

                    block1Completed = true;
                    attemptToStopService();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse!=null){
                        Log.e("RESPONSE 1E", errorResponse.toString());
                    }
                    block1Completed = true;
                    attemptToStopService();


                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1E: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1E: "+statusCode, "Something got very very wrong");
                    }
                    block1Completed = true;
                    attemptToStopService();
                }
            });


        }
        catch (JSONException e) {
            block1Completed = true;
            e.printStackTrace();
            attemptToStopService();
        }catch (Exception e) {
            block1Completed = true;
            e.printStackTrace();
            attemptToStopService();
        }
    }
    private void startBlock1fBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);


        JSONObject requestObject = new JSONObject();

        try {
//            requestObject.put("transplantings",prepareTransplantings());
//            requestObject.put("tasks",prepareTasks());
            requestObject.put("incomeExpenses",prepareIncomeExpense());
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


                    block1Completed = true;
                    attemptToStopService();
                }


                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE", errorResponse.toString());
                    }
                    block1Completed = true;
                    attemptToStopService();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1F: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1F: "+statusCode, "Something got very very wrong");
                    }
                    block1Completed = true;
                    attemptToStopService();

                }
            });


        }
        catch (JSONException e) {
            block1Completed = true;
            e.printStackTrace();
            attemptToStopService();
        }catch (Exception e) {
            block1Completed = true;
            e.printStackTrace();
            attemptToStopService();
        }
    }
    private void startBlock1gBackup(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(20000);


        JSONObject requestObject = new JSONObject();

        try {
//            requestObject.put("machineServices",prepareMachineServices());
//            requestObject.put("machineTasks",prepareMachineTasks());
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
                    block1Completed = true;
                    attemptToStopService();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE 1G", errorResponse.toString());
                    }
                    block1Completed = true;
                    attemptToStopService();

                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 1G: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 1G: "+statusCode, "Something got very very wrong");
                    }
                    block1Completed = true;
                    attemptToStopService();

                }
            });


        }
        catch (JSONException e) {
            block1Completed = true;
            e.printStackTrace();
            attemptToStopService();
        }catch (Exception e) {
            block1Completed = true;
            e.printStackTrace();
            attemptToStopService();
        }
    }

    /**
     * COVERS FINANCIAL MANAGER
     * 1. send customers, suppliers, buy_inputs_products as block 2a
     * 2. after 2a send Estimates, Invoice, Sales Order as block 2b
     * 3. after 2b send Purchase Order, Bills as block 2c
     * 4. after 2c send Invoice Payments and Bill payments as 2d
     * 5. After 2d send product items as 2e
     */


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
                    deletesCompleted = true;
                    attemptToStopService();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if(errorResponse != null){
                        Log.e("RESPONSE DELETES", errorResponse.toString());
                    }
                    deletesCompleted = true;
                    attemptToStopService();
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                    if (errorResponse != null) {
                        Log.e("info 2E: "+statusCode, new String(String.valueOf(errorResponse)));
                    } else {
                        Log.e("info 2E: "+statusCode, "Something got very very wrong");
                    }
                    deletesCompleted = true;
                    attemptToStopService();
                }
            });


        }
        catch (JSONException e) {
            deletesCompleted = true;
            e.printStackTrace();
            attemptToStopService();
        }catch (Exception e) {
            deletesCompleted = true;
            e.printStackTrace();
            attemptToStopService();
        }


    }

    public  void userBackup() {

        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        params.put("userId", DashboardActivity.getPreferences(DashboardActivity.RETRIEVED_USER_ID,this));
        params.put("firstName",DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_FIRST_NAME,this));
        params.put("lastName",DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_LAST_NAME,this));
        params.put("country",DashboardActivity.getPreferences("country",this));
        params.put("countryCode",DashboardActivity.getPreferences("countryCode",this));
        params.put("email",DashboardActivity.getPreferences("email",this));
        params.put("farmName",DashboardActivity.getPreferences(DashboardActivity.FARM_NAME_PREFERENCES_ID,this));
        params.put("addressStreet",DashboardActivity.getPreferences(DashboardActivity.STREET_PREFERENCES_ID,this));
        params.put("addressCityOrTown",DashboardActivity.getPreferences(DashboardActivity.CITY_PREFERENCES_ID,this));
        params.put("addressCountry",DashboardActivity.getPreferences(DashboardActivity.COUNTRY_PREFERENCES_ID,this));
        params.put("phoneNumber" ,DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_PHONE_NUMBER,this));
        params.put("latitude",DashboardActivity.getPreferences("latitude",this));
        params.put("longitude",DashboardActivity.getPreferences("longitude",this));

        client.post(ApiPaths.CROP_USER_BACKUP, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                Log.e("USER BACKUP","Started");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                DashboardActivity.savePreferences(DashboardActivity.PREFERENCES_USER_BACKED_UP,"yes",CropSyncService.this);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                if(statusCode==400){
                    DashboardActivity.savePreferences(DashboardActivity.PREFERENCES_USER_BACKED_UP,"yes",CropSyncService.this);
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {

                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }

            }
        });

    }



}
