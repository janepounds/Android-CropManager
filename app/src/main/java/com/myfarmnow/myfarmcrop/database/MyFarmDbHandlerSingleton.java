package com.myfarmnow.myfarmcrop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;

import com.myfarmnow.myfarmcrop.models.CropField;

import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropMachine;
import com.myfarmnow.myfarmcrop.models.CropSoilAnalysis;
import com.myfarmnow.myfarmcrop.models.CropSpraying;

import java.util.ArrayList;


public class MyFarmDbHandlerSingleton extends SQLiteOpenHelper {


    private static final String DATABASE_NAME ="myfarmdb";
    private static int database_version=1;
    public static final String CROP_INVENTORY_FERTILIZER_TABLE_NAME ="crop_inventory_fertilizer";
    public static final String CROP_INVENTORY_SEEDS_TABLE_NAME ="crop_inventory_seeds";
    public static final String CROP_INVENTORY_SPRAY_TABLE_NAME ="crop_inventory_spray";
    public static final String CROP_CROP_TABLE_NAME ="crop";
    public static final String CROP_CULTIVATION_TABLE_NAME ="crop_cultivate";
    public static final String CROP_FERTILIZER_APPLICATION_TABLE_NAME ="crop_fertilizer_application";
    public static final String CROP_SPRAYING_TABLE_NAME ="crop_fertilizer_application";
    public static final String CROP_FIELDS_TABLE_NAME ="crop_fields";
    public static final String CROP_MACHINE_TABLE_NAME ="crop_machine";
    public static final String CROP_SOIL_ANALYSIS_TABLE_NAME ="crop_soil_analysis";

    public static final String CROP_INVENTORY_FERTILIZER_ID ="id";
    public static final String CROP_INVENTORY_FERTILIZER_USER_ID ="userId";
    public static final String CROP_INVENTORY_FERTILIZER_DATE ="date";
    public static final String CROP_INVENTORY_FERTILIZER_TYPE ="type";
    public static final String CROP_INVENTORY_FERTILIZER_NAME ="name";
    public static final String CROP_INVENTORY_FERTILIZER_N_PERCENTAGE ="nPercentage";
    public static final String CROP_INVENTORY_FERTILIZER_P_PERCENTAGE ="pPercentage";
    public static final String CROP_INVENTORY_FERTILIZER_K_PERCENTAGE ="kPercentage";
    public static final String CROP_INVENTORY_FERTILIZER_QUANTITY ="quantity";
    public static final String CROP_INVENTORY_FERTILIZER_BATCH_NUMBER ="batchNumber";
    public static final String CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER ="serialNumber";
    public static final String CROP_INVENTORY_FERTILIZER_SUPPLIER ="supplier";
    public static final String CROP_INVENTORY_FERTILIZER_USAGE_UNIT ="usageUnit";
    public static final String CROP_INVENTORY_FERTILIZER_COST ="cost";
    public static final String CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA ="macronutrientsCa";
    public static final String CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG ="macronutrientsMg";
    public static final String CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S ="macronutrientsS";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B ="micronutrientsB";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN ="micronutrientsMn";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL ="micronutrientsCl";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO ="micronutrientsMo";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU ="micronutrientsCu";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN ="micronutrientsZn";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE ="micronutrientsFe";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA ="micronutrientsNa";

    public static final String CROP_INVENTORY_SEEDS_ID ="id";
    public static final String CROP_INVENTORY_SEEDS_USER_ID ="userId";
    public static final String CROP_INVENTORY_SEEDS_DATE ="date";

    public static final String CROP_INVENTORY_SEEDS_NAME ="name";
    public static final String CROP_INVENTORY_SEEDS_VARIETY ="variety";
    public static final String CROP_INVENTORY_SEEDS_DRESSING ="dressing";
    public static final String CROP_INVENTORY_SEEDS_QUANTITY ="quantity";
    public static final String CROP_INVENTORY_SEEDS_COST ="cost";
    public static final String CROP_INVENTORY_SEEDS_BATCH_NUMBER ="batchNumber";
    public static final String CROP_INVENTORY_SEEDS_SUPPLIER ="supplier";
    public static final String CROP_INVENTORY_SEEDS_TGW ="tgw";
    public static final String CROP_INVENTORY_SEEDS_USAGE_UNIT ="usageUnit";

    public static final String CROP_INVENTORY_SPRAY_ID ="id";
    public static final String CROP_INVENTORY_SPRAY_USER_ID ="userId";
    public static final String CROP_INVENTORY_SPRAY_DATE ="date";
    public static final String CROP_INVENTORY_SPRAY_TYPE ="type";
    public static final String CROP_INVENTORY_SPRAY_NAME ="name";
    public static final String CROP_INVENTORY_SPRAY_VARIETY ="variety";
    public static final String CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS ="dressing";
    public static final String CROP_INVENTORY_SPRAY_QUANTITY ="quantity";
    public static final String CROP_INVENTORY_SPRAY_COST ="cost";
    public static final String CROP_INVENTORY_SPRAY_BATCH_NUMBER ="batchNumber";
    public static final String CROP_INVENTORY_SPRAY_SUPPLIER ="supplier";
    public static final String CROP_INVENTORY_SPRAY_EXPIRY_DATE ="tgw";
    public static final String CROP_INVENTORY_SPRAY_USAGE_UNIT ="usageUnit";
    public static final String CROP_INVENTORY_SPRAY_HARVEST_INTERVAL ="harvestInterval";


    public static final String CROP_CROP_ID ="id";
    public static final String CROP_CROP_USER_ID ="userId";
    public static final String CROP_CROP_NAME ="crop";
    public static final String CROP_CROP_YEAR ="croppingYear";
    public static final String CROP_CROP_FIELD_ID ="fieldId";
    public static final String CROP_CROP_DATE_SOWN ="dateSown";
    public static final String CROP_CROP_VARIETY ="variety";
    public static final String CROP_CROP_GROWING_CYCLE ="growingCycle";
    public static final String CROP_CROP_AREA ="area";
    public static final String CROP_CROP_COST ="cost";
    public static final String CROP_CROP_OPERATOR ="operator";
    public static final String CROP_CROP_SEED_ID ="seedId";
    public static final String CROP_CROP_RATE ="rate";
    public static final String CROP_CROP_PLANTING_METHOD ="plantingMethod";

    public static final String CROP_CULTIVATION_ID ="id";
    public static final String CROP_CULTIVATION_USER_ID ="userId";
    public static final String CROP_CULTIVATION_CROP_ID ="cropId";
    public static final String CROP_CULTIVATION_DATE ="date";
    public static final String CROP_CULTIVATION_OPERATION ="operation";
    public static final String CROP_CULTIVATION_OPERATOR ="operator";
    public static final String CROP_CULTIVATION_COST ="cost";
    public static final String CROP_CULTIVATION_NOTES ="notes";

    public static final String CROP_FERTILIZER_APPLICATION_ID ="id";
    public static final String CROP_FERTILIZER_APPLICATION_USER_ID ="userId";
    public static final String CROP_FERTILIZER_APPLICATION_DATE ="date";
    public static final String CROP_FERTILIZER_APPLICATION_OPERATOR ="operator";
    public static final String CROP_FERTILIZER_APPLICATION_METHOD ="method";
    public static final String CROP_FERTILIZER_APPLICATION_REASON ="reason";
    public static final String CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM ="fertilizerForm";
    public static final String CROP_FERTILIZER_APPLICATION_FERTILIZER_ID ="fertilizerId";
    public static final String CROP_FERTILIZER_APPLICATION_RATE ="rate";
    public static final String CROP_FERTILIZER_APPLICATION_COST ="cost";
    public static final String CROP_FERTILIZER_APPLICATION_CROP_ID ="cropId";

    public static final String CROP_SPRAYING_ID ="id";
    public static final String CROP_SPRAYING_USER_ID ="userId";
    public static final String CROP_SPRAYING_CROP_ID ="cropId";
    public static final String CROP_SPRAYING_DATE ="date";
    public static final String CROP_SPRAYING_START_TIME ="startTime";
    public static final String CROP_SPRAYING_END_TIME ="endTime";
    public static final String CROP_SPRAYING_OPERATOR ="operator";
    public static final String CROP_SPRAYING_WATER_VOLUME ="waterVolume";
    public static final String CROP_SPRAYING_WATER_CONDITION ="waterCondition";
    public static final String CROP_SPRAYING_WIND_DIRECTION="windDirection";
    public static final String CROP_SPRAYING_EQUIPMENT_USED="equipmentUsed";
    public static final String CROP_SPRAYING_SPRAY_ID ="sprayId";
    public static final String CROP_SPRAYING_RATE="rate";
    public static final String CROP_SPRAYING_TREATMENT_REASON="treatmentReason";
    public static final String CROP_SPRAYING_COST="cost";



    public static final String CROP_FIELD_ID ="id";
    public static final String CROP_FIELD_USER_ID ="userId";
    public static final String CROP_FIELD_NAME="fieldName";
    public static final String CROP_FIELD_SOIL_CATEGORY="soilCategory";
    public static final String CROP_FIELD_SOIL_TYPE="soilType";
    public static final String CROP_FIELD_WATERCOURSE="watercourse";
    public static final String CROP_FIELD_TOTAL_AREA="totalArea";
    public static final String CROP_FIELD_CROPPABLE_AREA="croppableArea";
    public static final String CROP_FIELD_UNITS="units";

    public static final String CROP_MACHINE_ID ="id";
    public static final String CROP_MACHINE_USER_ID ="userId";
    public static final String CROP_MACHINE_NAME="name";
    public static final String CROP_MACHINE_BRAND="brand";
    public static final String CROP_MACHINE_CATEGORY="category";
    public static final String CROP_MACHINE_MANUFACTURER="manufacturer";
    public static final String CROP_MACHINE_MODEL="model";
    public static final String CROP_MACHINE_REGISTRATION_NUMBER="registrationNumber";
    public static final String CROP_MACHINE_QUANTITY="quantity";
    public static final String CROP_MACHINE_DATE_ACQUIRED="date";
    public static final String CROP_MACHINE_PURCHASED_FROM="purchasedFrom";
    public static final String CROP_MACHINE_STORAGE_LOCATION="storageLocation";
    public static final String CROP_MACHINE_PURCHASE_PRICE="purchasePrice";

    public static final String CROP_SOIL_ANALYSIS_ID ="id";
    public static final String CROP_SOIL_ANALYSIS_USER_ID ="userId";
    public static final String CROP_SOIL_ANALYSIS_DATE="date";
    public static final String CROP_SOIL_ANALYSIS_PH="ph";
    public static final String CROP_SOIL_ANALYSIS_ORGANIC_MATTER="organicMatter";
    public static final String CROP_SOIL_ANALYSIS_AGRONOMIST="agronomist";
    public static final String CROP_SOIL_ANALYSIS_COST="cost";
    public static final String CROP_SOIL_ANALYSIS_RESULTS="results";
    public static final String CROP_SOIL_ANALYSIS_FIELD_ID="fieldId";





    private static MyFarmDbHandlerSingleton myFarmDbHandlerSingleton;
    SQLiteDatabase database;
    Context context;


    private MyFarmDbHandlerSingleton(Context context) {

        super(context,DATABASE_NAME, null, database_version);
        this.context = context;
    }
    public static MyFarmDbHandlerSingleton getHandlerInstance(Context context){
        if (myFarmDbHandlerSingleton ==null){
            myFarmDbHandlerSingleton = new MyFarmDbHandlerSingleton(context);
        }
        return myFarmDbHandlerSingleton;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        database = db;
        String crop_inventory_fertilizer_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_INVENTORY_FERTILIZER_TABLE_NAME+" ( "+CROP_INVENTORY_FERTILIZER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_INVENTORY_FERTILIZER_USER_ID+" TEXT,"+CROP_INVENTORY_FERTILIZER_DATE+" TEXT NOT NULL,"+ CROP_INVENTORY_FERTILIZER_TYPE+" TEXT NOT NULL,"+ CROP_INVENTORY_FERTILIZER_NAME+" TEXT NOT NULL,"+ CROP_INVENTORY_FERTILIZER_N_PERCENTAGE+" REAL,"+
                CROP_INVENTORY_FERTILIZER_P_PERCENTAGE+" REAL,"+ CROP_INVENTORY_FERTILIZER_K_PERCENTAGE+" REAL,"+ CROP_INVENTORY_FERTILIZER_QUANTITY+" REAL NOT NULL,"+CROP_INVENTORY_FERTILIZER_BATCH_NUMBER+" TEXT NOT NULL,"+
                CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER+" TEXT,"+CROP_INVENTORY_FERTILIZER_SUPPLIER+" TEXT,"+CROP_INVENTORY_FERTILIZER_USAGE_UNIT+" TEXT ,"+CROP_INVENTORY_FERTILIZER_COST+" REAL ,"+CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA+" REAL ,"+
                CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG+" REAL ,"+CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S+" REAL ,"+CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B+" REAL ,"+
                CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU+" REAL ,"+
                CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA+" REAL )";

        String crop_seeds_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_INVENTORY_SEEDS_TABLE_NAME+" ( "+CROP_INVENTORY_SEEDS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_INVENTORY_SEEDS_USER_ID+" TEXT,"+CROP_INVENTORY_SEEDS_DATE+" TEXT NOT NULL,"+  CROP_INVENTORY_SEEDS_NAME
                +" TEXT NOT NULL,"+ CROP_INVENTORY_SEEDS_VARIETY+" TEXT,"+
                CROP_INVENTORY_SEEDS_DRESSING+" TEXT,"+CROP_INVENTORY_FERTILIZER_QUANTITY+" REAL NOT NULL,"+CROP_INVENTORY_SEEDS_BATCH_NUMBER+" TEXT NOT NULL,"+
               CROP_INVENTORY_SEEDS_COST+" REAL ,"+CROP_INVENTORY_SEEDS_SUPPLIER+" TEXT ,"+CROP_INVENTORY_SEEDS_TGW+" TEXT ,"+CROP_INVENTORY_FERTILIZER_USAGE_UNIT+" TEXT )";


        String crop_inventory_spray_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_INVENTORY_SPRAY_TABLE_NAME+" ( "+CROP_INVENTORY_SPRAY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_INVENTORY_SPRAY_USER_ID+" TEXT,"+CROP_INVENTORY_SPRAY_DATE+" TEXT NOT NULL,"+ CROP_INVENTORY_SPRAY_TYPE+" TEXT NOT NULL,"+ CROP_INVENTORY_SPRAY_NAME
                +" TEXT NOT NULL,"+ CROP_INVENTORY_SPRAY_VARIETY+" TEXT,"+
                CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS+" TEXT,"+CROP_INVENTORY_SPRAY_QUANTITY+" REAL NOT NULL,"+CROP_INVENTORY_SPRAY_BATCH_NUMBER+" TEXT NOT NULL,"+
                CROP_INVENTORY_SPRAY_COST+" REAL ,"+CROP_INVENTORY_SPRAY_SUPPLIER+" TEXT ,"+CROP_INVENTORY_SPRAY_EXPIRY_DATE+" TEXT ,"+CROP_INVENTORY_SPRAY_HARVEST_INTERVAL+" TEXT ,"+CROP_INVENTORY_SPRAY_USAGE_UNIT+" TEXT )";

        String crop_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_CROP_TABLE_NAME+" ( "+CROP_CROP_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_CROP_USER_ID+" TEXT,"+ CROP_CROP_VARIETY+" TEXT ,"+ CROP_CROP_YEAR +" INTEGER,"+ CROP_CROP_NAME +" TEXT NOT NULL,"+
                CROP_CROP_FIELD_ID+" TEXT NOT NULL," +CROP_CROP_GROWING_CYCLE+" TEXT,"+CROP_CROP_DATE_SOWN+" TEXT NOT NULL,"+
                CROP_CROP_AREA+" REAL,"+CROP_CROP_OPERATOR+" TEXT NOT NULL,"+
                CROP_CROP_COST+" REAL NOT NULL,"+CROP_CROP_SEED_ID+" TEXT ,"+CROP_CROP_RATE+" REAL ,"+CROP_CROP_PLANTING_METHOD+" TEXT NOT NULL )";

        String crop_cultivate_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_CULTIVATION_TABLE_NAME+" ( "+CROP_CULTIVATION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_CULTIVATION_USER_ID+" TEXT,"+CROP_CULTIVATION_CROP_ID+" TEXT NOT NULL,"+ CROP_CULTIVATION_DATE+" TEXT NOT NULL,"+ CROP_CULTIVATION_OPERATION+" TEXT NOT NULL,"+CROP_CULTIVATION_OPERATOR+" TEXT NOT NULL,"+
                CROP_CULTIVATION_COST+" REAL,"+CROP_CULTIVATION_NOTES+" TEXT )";

        String crop_fertilizer_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+" ( "+CROP_FERTILIZER_APPLICATION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_FERTILIZER_APPLICATION_USER_ID+" TEXT,"+CROP_FERTILIZER_APPLICATION_CROP_ID+" TEXT NOT NULL,"+ CROP_FERTILIZER_APPLICATION_DATE+" TEXT NOT NULL,"+CROP_FERTILIZER_APPLICATION_OPERATOR+" TEXT,"+
                CROP_FERTILIZER_APPLICATION_METHOD+" REAL NOT NULL,"+CROP_FERTILIZER_APPLICATION_REASON+" TEXT, "+CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM+" TEXT NOT NULL, "+CROP_FERTILIZER_APPLICATION_FERTILIZER_ID+" TEXT NOT NULL,"+
                 CROP_FERTILIZER_APPLICATION_RATE+" REAL NOT NULL ,"+CROP_FERTILIZER_APPLICATION_COST+" REAL )";


        String crop_spraying_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_SPRAYING_TABLE_NAME+" ( "+CROP_SPRAYING_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_SPRAYING_USER_ID+" TEXT,"+CROP_SPRAYING_CROP_ID+" TEXT NOT NULL,"+ CROP_SPRAYING_DATE +" TEXT NOT NULL,"+CROP_SPRAYING_START_TIME+" TEXT,"+
                CROP_SPRAYING_END_TIME+" TEXT,"+CROP_SPRAYING_OPERATOR+" TEXT NOT NULL,"+
                CROP_SPRAYING_WATER_VOLUME+" REAL ,"+ CROP_SPRAYING_WATER_CONDITION+" TEXT,"+CROP_SPRAYING_WIND_DIRECTION+" TEXT, "+CROP_SPRAYING_EQUIPMENT_USED+" TEXT ,"+
                CROP_SPRAYING_SPRAY_ID +" TEXT NOT NULL,"+ CROP_SPRAYING_RATE+" REAL NOT NULL ,"+CROP_SPRAYING_TREATMENT_REASON+" TEXT ,"+CROP_SPRAYING_COST+" REAL )";

        String crop_field_insert_query ="CREATE TABLE IF NOT EXISTS "+ CROP_FIELDS_TABLE_NAME +" ( "+ CROP_FIELD_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_FIELD_USER_ID+" TEXT,"+CROP_FIELD_NAME+" TEXT NOT NULL,"+ CROP_FIELD_SOIL_CATEGORY+" TEXT,"+ CROP_FIELD_SOIL_TYPE+" TEXT,"+CROP_FIELD_WATERCOURSE+" TEXT,"+
                CROP_FIELD_TOTAL_AREA +" REAL NOT NULL ,"+ CROP_FIELD_CROPPABLE_AREA+" REAL ,"+ CROP_FIELD_UNITS+" TEXT NOT NULL)";

        String crop_soil_analysis_insert_query ="CREATE TABLE IF NOT EXISTS "+ CROP_SOIL_ANALYSIS_TABLE_NAME +" ( "+ CROP_SOIL_ANALYSIS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_SOIL_ANALYSIS_USER_ID+" TEXT ,"+CROP_SOIL_ANALYSIS_FIELD_ID+" TEXT,"+CROP_SOIL_ANALYSIS_DATE+" TEXT NOT NULL,"+ CROP_SOIL_ANALYSIS_PH+" REAL,"+ CROP_SOIL_ANALYSIS_ORGANIC_MATTER+" TEXT,"+
                CROP_SOIL_ANALYSIS_AGRONOMIST +" TEXT NOT NULL ,"+ CROP_SOIL_ANALYSIS_COST+" REAL  NOT NULL  ,"+ CROP_SOIL_ANALYSIS_RESULTS+" TEXT NOT NULL)";

        String crop_machine_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_MACHINE_TABLE_NAME+" ( "+CROP_MACHINE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_MACHINE_USER_ID+" TEXT,"+CROP_MACHINE_NAME+" TEXT NOT NULL,"+CROP_MACHINE_BRAND+" TEXT ,"+CROP_MACHINE_CATEGORY+" TEXT NOT NULL,"+CROP_MACHINE_MANUFACTURER+" TEXT ,"+ CROP_MACHINE_MODEL+" TEXT ,"+
                CROP_MACHINE_REGISTRATION_NUMBER+" REAL,"+ CROP_MACHINE_QUANTITY+" REAL NOT NULL,"+CROP_MACHINE_DATE_ACQUIRED+" TEXT NOT NULL,"+
                CROP_MACHINE_PURCHASED_FROM+" TEXT NOT NULL,"+CROP_MACHINE_STORAGE_LOCATION+" TEXT,"+CROP_MACHINE_PURCHASE_PRICE+" REAL NOT NULL )";


        Log.d("FERTILIZER INVENTORY",crop_inventory_fertilizer_insert_query);
        Log.d("SEEDS INVENTORY",crop_seeds_insert_query);
        Log.d("SPRAY INVENTORY",crop_inventory_spray_insert_query);
        Log.d("SPRAY INVENTORY",crop_spraying_insert_query);
        Log.d("CROP",crop_insert_query);
        Log.d("CULTIVATE",crop_cultivate_insert_query);
        Log.d("FERTILIZER",crop_fertilizer_insert_query);
        Log.d("FIELDS",crop_field_insert_query);
        Log.d("MACHINE",crop_machine_insert_query);

        database.execSQL(crop_inventory_fertilizer_insert_query);
        database.execSQL(crop_seeds_insert_query);
        database.execSQL(crop_inventory_spray_insert_query);
        database.execSQL(crop_insert_query);
        database.execSQL(crop_cultivate_insert_query);
        database.execSQL(crop_fertilizer_insert_query);
        database.execSQL(crop_spraying_insert_query);
        database.execSQL(crop_field_insert_query);
        database.execSQL(crop_machine_insert_query);
        database.execSQL(crop_soil_analysis_insert_query);







    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CROP_INVENTORY_FERTILIZER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CROP_INVENTORY_SEEDS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CROP_INVENTORY_SPRAY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CROP_FIELDS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CROP_MACHINE_TABLE_NAME);
        onCreate(db);
    }



    public MyFarmDbHandlerSingleton openDB() throws SQLException {

        database=this.getWritableDatabase();
        onCreate(database);

        return this;
    }

    public void closeDB() throws SQLException{
        this.close();
    }
    public void  insertCropSoilAnalysis(CropSoilAnalysis spraying){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SOIL_ANALYSIS_DATE,spraying.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_USER_ID,spraying.getUserId());
        contentValues.put(CROP_SOIL_ANALYSIS_FIELD_ID,spraying.getFieldId());
        contentValues.put(CROP_SOIL_ANALYSIS_DATE,spraying.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_PH,spraying.getPh());
        contentValues.put(CROP_SOIL_ANALYSIS_AGRONOMIST,spraying.getAgronomist());
        contentValues.put(CROP_SOIL_ANALYSIS_RESULTS,spraying.getResult());
        contentValues.put(CROP_SOIL_ANALYSIS_COST,spraying.getCost());
        contentValues.put(CROP_SOIL_ANALYSIS_ORGANIC_MATTER,spraying.getOrganicMatter());
        database.insert(CROP_SOIL_ANALYSIS_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropSoilAnalysis(CropSoilAnalysis spraying){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SOIL_ANALYSIS_DATE,spraying.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_USER_ID,spraying.getUserId());
        contentValues.put(CROP_SOIL_ANALYSIS_FIELD_ID,spraying.getFieldId());
        contentValues.put(CROP_SOIL_ANALYSIS_DATE,spraying.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_PH,spraying.getPh());
        contentValues.put(CROP_SOIL_ANALYSIS_AGRONOMIST,spraying.getAgronomist());
        contentValues.put(CROP_SOIL_ANALYSIS_RESULTS,spraying.getResult());
        contentValues.put(CROP_SOIL_ANALYSIS_COST,spraying.getCost());
        contentValues.put(CROP_SOIL_ANALYSIS_ORGANIC_MATTER,spraying.getOrganicMatter());
        database.update(CROP_SOIL_ANALYSIS_TABLE_NAME,contentValues,CROP_SOIL_ANALYSIS_ID+" = ?", new String[]{spraying.getId()});
        closeDB();
    }
    public boolean deleteCropSoilAnalysis(String id){
        openDB();
        database.delete(CROP_SOIL_ANALYSIS_TABLE_NAME,CROP_SOIL_ANALYSIS_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropSoilAnalysis> getCropSoilAnalysis(String fieldId){
        openDB();
        ArrayList<CropSoilAnalysis> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_SOIL_ANALYSIS_TABLE_NAME+" where "+CROP_SOIL_ANALYSIS_FIELD_ID+" = "+fieldId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropSoilAnalysis crop = new CropSoilAnalysis();
            crop.setId(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_USER_ID)));
            crop.setDate(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_DATE)));
            crop.setAgronomist(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_AGRONOMIST)));
            crop.setResult(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_RESULTS)));
            crop.setOrganicMatter(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_ORGANIC_MATTER)));
            crop.setPh(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_PH)));
            crop.setCost(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_COST)));
            crop.setFieldId(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_FIELD_ID)));
           /* crop.setCropId(res.getString(res.getColumnIndex(CROP_SPRAYING_CROP_ID)));
            crop.setStartTime(res.getString(res.getColumnIndex(CROP_SPRAYING_START_TIME)));
            crop.setStartTime(res.getString(res.getColumnIndex(CROP_SPRAYING_END_TIME)));
            crop.setCost(res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            crop.setOperator(res.getString(res.getColumnIndex(CROP_SPRAYING_OPERATOR)));
            crop.setWaterCondition(res.getString(res.getColumnIndex(CROP_SPRAYING_WATER_CONDITION)));
            crop.setWaterVolume(res.getFloat(res.getColumnIndex(CROP_SPRAYING_WATER_VOLUME)));
            crop.setWindDirection(res.getString(res.getColumnIndex(CROP_SPRAYING_WIND_DIRECTION)));
            crop.setTreatmentReason(res.getString(res.getColumnIndex(CROP_SPRAYING_TREATMENT_REASON)));
            crop.setEquipmentUsed(res.getString(res.getColumnIndex(CROP_SPRAYING_EQUIPMENT_USED)));
            crop.setSprayId(res.getString(res.getColumnIndex(CROP_SPRAYING_SPRAY_ID)));
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_SPRAYING_RATE)));*/

            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
        return array_list;

    }

    public void  insertCropSpraying(CropSpraying spraying){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SPRAYING_DATE,spraying.getDate());
        contentValues.put(CROP_SPRAYING_USER_ID,spraying.getUserId());
        contentValues.put(CROP_SPRAYING_CROP_ID,spraying.getCropId());
        contentValues.put(CROP_SPRAYING_DATE,spraying.getDate());
        contentValues.put(CROP_SPRAYING_START_TIME,spraying.getStartTime());
        contentValues.put(CROP_SPRAYING_END_TIME,spraying.getEndTime());
        contentValues.put(CROP_SPRAYING_OPERATOR,spraying.getOperator());
        contentValues.put(CROP_SPRAYING_WATER_CONDITION,spraying.getWaterCondition());
        contentValues.put(CROP_SPRAYING_WATER_VOLUME,spraying.getWaterVolume());
        contentValues.put(CROP_SPRAYING_WIND_DIRECTION,spraying.getWindDirection());
        contentValues.put(CROP_SPRAYING_TREATMENT_REASON,spraying.getTreatmentReason());
        contentValues.put(CROP_SPRAYING_EQUIPMENT_USED,spraying.getEquipmentUsed());
        contentValues.put(CROP_SPRAYING_SPRAY_ID,spraying.getSprayId());
        contentValues.put(CROP_SPRAYING_OPERATOR,spraying.getOperator());
        contentValues.put(CROP_SPRAYING_COST,spraying.getCost());
        contentValues.put(CROP_SPRAYING_RATE,spraying.getRate());
        database.insert(CROP_SPRAYING_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropSpraying(CropSpraying spraying){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SPRAYING_DATE,spraying.getDate());
        contentValues.put(CROP_SPRAYING_USER_ID,spraying.getUserId());
        contentValues.put(CROP_SPRAYING_CROP_ID,spraying.getCropId());
        contentValues.put(CROP_SPRAYING_DATE,spraying.getDate());
        contentValues.put(CROP_SPRAYING_START_TIME,spraying.getStartTime());
        contentValues.put(CROP_SPRAYING_END_TIME,spraying.getEndTime());
        contentValues.put(CROP_SPRAYING_OPERATOR,spraying.getOperator());
        contentValues.put(CROP_SPRAYING_WATER_CONDITION,spraying.getWaterCondition());
        contentValues.put(CROP_SPRAYING_WATER_VOLUME,spraying.getWaterVolume());
        contentValues.put(CROP_SPRAYING_WIND_DIRECTION,spraying.getWindDirection());
        contentValues.put(CROP_SPRAYING_TREATMENT_REASON,spraying.getTreatmentReason());
        contentValues.put(CROP_SPRAYING_EQUIPMENT_USED,spraying.getEquipmentUsed());
        contentValues.put(CROP_SPRAYING_SPRAY_ID,spraying.getSprayId());
        contentValues.put(CROP_SPRAYING_OPERATOR,spraying.getOperator());
        contentValues.put(CROP_SPRAYING_COST,spraying.getCost());
        contentValues.put(CROP_SPRAYING_RATE,spraying.getRate());
        database.update(CROP_SPRAYING_TABLE_NAME,contentValues,CROP_SPRAYING_ID+" = ?", new String[]{spraying.getId()});
        closeDB();
    }
    public boolean deleteCropSpraying(String fertilizerId){
        openDB();
        database.delete(CROP_FERTILIZER_APPLICATION_TABLE_NAME,CROP_FERTILIZER_APPLICATION_ID+" = ?", new String[]{fertilizerId});
        closeDB();
        return true;
    }
    public ArrayList<CropSpraying> getCropSprayings(String cropId){
        openDB();
        ArrayList<CropSpraying> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+" where "+CROP_FERTILIZER_APPLICATION_CROP_ID+" = "+cropId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropSpraying crop = new CropSpraying();
            crop.setId(res.getString(res.getColumnIndex(CROP_SPRAYING_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_SPRAYING_USER_ID)));
            crop.setDate(res.getString(res.getColumnIndex(CROP_SPRAYING_DATE)));
            crop.setCropId(res.getString(res.getColumnIndex(CROP_SPRAYING_CROP_ID)));
            crop.setStartTime(res.getString(res.getColumnIndex(CROP_SPRAYING_START_TIME)));
            crop.setStartTime(res.getString(res.getColumnIndex(CROP_SPRAYING_END_TIME)));
            crop.setCost(res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            crop.setOperator(res.getString(res.getColumnIndex(CROP_SPRAYING_OPERATOR)));
            crop.setWaterCondition(res.getString(res.getColumnIndex(CROP_SPRAYING_WATER_CONDITION)));
            crop.setWaterVolume(res.getFloat(res.getColumnIndex(CROP_SPRAYING_WATER_VOLUME)));
            crop.setWindDirection(res.getString(res.getColumnIndex(CROP_SPRAYING_WIND_DIRECTION)));
            crop.setTreatmentReason(res.getString(res.getColumnIndex(CROP_SPRAYING_TREATMENT_REASON)));
            crop.setEquipmentUsed(res.getString(res.getColumnIndex(CROP_SPRAYING_EQUIPMENT_USED)));
            crop.setSprayId(res.getString(res.getColumnIndex(CROP_SPRAYING_SPRAY_ID)));
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_SPRAYING_RATE)));

            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
        return array_list;

    }
    public void  insertCropFertilizerApplication(CropFertilizerApplication fertilizerApplication){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_FERTILIZER_APPLICATION_DATE,fertilizerApplication.getDate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_USER_ID,fertilizerApplication.getUserId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_CROP_ID,fertilizerApplication.getCropId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_DATE,fertilizerApplication.getDate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR,fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_METHOD,fertilizerApplication.getMethod());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REASON,fertilizerApplication.getReason());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM,fertilizerApplication.getFertilizerForm());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_ID,fertilizerApplication.getFertilizerId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR,fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_COST,fertilizerApplication.getCost());
        database.insert(CROP_FERTILIZER_APPLICATION_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropFertilizerApplication(CropFertilizerApplication fertilizerApplication){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_FERTILIZER_APPLICATION_DATE,fertilizerApplication.getDate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_USER_ID,fertilizerApplication.getUserId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_CROP_ID,fertilizerApplication.getCropId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_DATE,fertilizerApplication.getDate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR,fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_METHOD,fertilizerApplication.getMethod());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REASON,fertilizerApplication.getReason());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM,fertilizerApplication.getFertilizerForm());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_ID,fertilizerApplication.getFertilizerId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR,fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_COST,fertilizerApplication.getCost());
        database.update(CROP_FERTILIZER_APPLICATION_TABLE_NAME,contentValues,CROP_FERTILIZER_APPLICATION_ID+" = ?", new String[]{fertilizerApplication.getId()});
        closeDB();
    }
    public boolean deleteCropFertilizerApplication(String fertilizerId){
        openDB();
        database.delete(CROP_FERTILIZER_APPLICATION_TABLE_NAME,CROP_FERTILIZER_APPLICATION_ID+" = ?", new String[]{fertilizerId});
        closeDB();
        return true;
    }
    public ArrayList<CropFertilizerApplication> getCropFertilizerApplication(String cropId){
        openDB();
        ArrayList<CropFertilizerApplication> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+" where "+CROP_FERTILIZER_APPLICATION_CROP_ID+" = "+cropId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropFertilizerApplication crop = new CropFertilizerApplication();
            crop.setId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_USER_ID)));
            crop.setDate(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE)));
            crop.setCropId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_CROP_ID)));
            crop.setMethod(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_METHOD)));
            crop.setCost(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST)));
            crop.setOperator(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_OPERATOR)));
            crop.setReason(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REASON)));
            crop.setFertilizerForm(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM)));
            crop.setFertilizerId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_ID)));
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RATE)));

            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
        return array_list;

    }
    public void  insertCropCultivate(CropCultivation cropCultivation){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CULTIVATION_DATE, cropCultivation.getDate());
        contentValues.put(CROP_CULTIVATION_USER_ID, cropCultivation.getUserId());
        contentValues.put(CROP_CULTIVATION_OPERATION, cropCultivation.getOperation());
        contentValues.put(CROP_CULTIVATION_CROP_ID, cropCultivation.getCropId());
        contentValues.put(CROP_CULTIVATION_OPERATOR, cropCultivation.getOperator());
        contentValues.put(CROP_CULTIVATION_COST, cropCultivation.getCost());
        contentValues.put(CROP_CULTIVATION_NOTES, cropCultivation.getNotes());
        contentValues.put(CROP_CULTIVATION_OPERATOR, cropCultivation.getOperator());
        database.insert(CROP_CULTIVATION_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropCultivate(CropCultivation cropCultivation){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CULTIVATION_DATE, cropCultivation.getDate());
        contentValues.put(CROP_CULTIVATION_USER_ID, cropCultivation.getUserId());
        contentValues.put(CROP_CULTIVATION_OPERATION, cropCultivation.getOperation());
        contentValues.put(CROP_CULTIVATION_CROP_ID, cropCultivation.getCropId());
        contentValues.put(CROP_CULTIVATION_OPERATOR, cropCultivation.getOperator());
        contentValues.put(CROP_CULTIVATION_COST, cropCultivation.getCost());
        contentValues.put(CROP_CULTIVATION_NOTES, cropCultivation.getNotes());
        contentValues.put(CROP_CULTIVATION_OPERATOR, cropCultivation.getOperator());
        database.update(CROP_CULTIVATION_TABLE_NAME,contentValues,CROP_CULTIVATION_ID+" = ?", new String[]{cropCultivation.getId()});

        closeDB();
    }
    public boolean deleteCropCultivate(String cultivateId){
        openDB();
        database.delete(CROP_CULTIVATION_TABLE_NAME,CROP_CULTIVATION_ID+" = ?", new String[]{cultivateId});
        closeDB();
        return true;
    }
    public ArrayList<CropCultivation> getCropCultivates(String cropId){
        openDB();
        ArrayList<CropCultivation> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_CULTIVATION_TABLE_NAME+" where "+CROP_CULTIVATION_CROP_ID+" = "+cropId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropCultivation crop = new CropCultivation();
            crop.setId(res.getString(res.getColumnIndex(CROP_CULTIVATION_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_CULTIVATION_USER_ID)));
            crop.setDate(res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE)));
            crop.setCropId(res.getString(res.getColumnIndex(CROP_CULTIVATION_CROP_ID)));
            crop.setOperation(res.getString(res.getColumnIndex(CROP_CULTIVATION_OPERATION)));
            crop.setCost(res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST)));
            crop.setOperator(res.getString(res.getColumnIndex(CROP_CULTIVATION_OPERATOR)));

            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
        return array_list;

    }
    public void  insertCrop(Crop inventorySpray){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CROP_DATE_SOWN,inventorySpray.getDateSown());
        contentValues.put(CROP_CROP_USER_ID,inventorySpray.getUserId());
        contentValues.put(CROP_CROP_VARIETY,inventorySpray.getVariety());
        contentValues.put(CROP_CROP_AREA,inventorySpray.getArea());
        contentValues.put(CROP_CROP_COST,inventorySpray.getCost());
        contentValues.put(CROP_CROP_NAME,inventorySpray.getName());
        contentValues.put(CROP_CROP_YEAR,inventorySpray.getCroppingYear());
        contentValues.put(CROP_CROP_OPERATOR,inventorySpray.getOperator());
        contentValues.put(CROP_CROP_FIELD_ID,inventorySpray.getFieldId());
        contentValues.put(CROP_CROP_GROWING_CYCLE,inventorySpray.getGrowingCycle());
        contentValues.put(CROP_CROP_COST,inventorySpray.getCost());
        contentValues.put(CROP_CROP_SEED_ID,inventorySpray.getSeedId());
        contentValues.put(CROP_CROP_RATE,inventorySpray.getRate());
        contentValues.put(CROP_CROP_PLANTING_METHOD,inventorySpray.getPlantingMethod());
        database.insert(CROP_CROP_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCrop(Crop inventorySpray){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CROP_DATE_SOWN,inventorySpray.getDateSown());
        contentValues.put(CROP_CROP_USER_ID,inventorySpray.getUserId());
        contentValues.put(CROP_CROP_VARIETY,inventorySpray.getVariety());
        contentValues.put(CROP_CROP_AREA,inventorySpray.getArea());
        contentValues.put(CROP_CROP_COST,inventorySpray.getCost());
        contentValues.put(CROP_CROP_YEAR,inventorySpray.getCroppingYear());
        contentValues.put(CROP_CROP_OPERATOR,inventorySpray.getOperator());
        contentValues.put(CROP_CROP_FIELD_ID,inventorySpray.getFieldId());
        contentValues.put(CROP_CROP_GROWING_CYCLE,inventorySpray.getGrowingCycle());
        contentValues.put(CROP_CROP_COST,inventorySpray.getCost());
        contentValues.put(CROP_CROP_SEED_ID,inventorySpray.getSeedId());
        contentValues.put(CROP_CROP_RATE,inventorySpray.getRate());
        contentValues.put(CROP_CROP_PLANTING_METHOD,inventorySpray.getPlantingMethod());
        database.update(CROP_CROP_TABLE_NAME,contentValues,CROP_CROP_ID+" = ?", new String[]{inventorySpray.getId()});

        closeDB();
    }
    public boolean deleteCrop(String cropId){
        openDB();
        database.delete(CROP_CROP_TABLE_NAME,CROP_CROP_ID+" = ?", new String[]{cropId});
        closeDB();
        return true;
    }
    public ArrayList<Crop> getCrops(String userId){
        openDB();
        ArrayList<Crop> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_CROP_TABLE_NAME+" where "+CROP_CROP_USER_ID+" = "+userId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            Crop crop = new Crop();
            crop.setId(res.getString(res.getColumnIndex(CROP_CROP_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_CROP_USER_ID)));
            crop.setDateSown(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)));
            crop.setVariety(res.getString(res.getColumnIndex(CROP_CROP_VARIETY)));
            crop.setArea(res.getFloat(res.getColumnIndex(CROP_CROP_AREA)));
            crop.setCost(res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
            crop.setCroppingYear(res.getInt(res.getColumnIndex(CROP_CROP_YEAR)));
            crop.setOperator(res.getString(res.getColumnIndex(CROP_CROP_OPERATOR)));
            crop.setSeedId(res.getString(res.getColumnIndex(CROP_CROP_SEED_ID)));
            crop.setFieldId(res.getString(res.getColumnIndex(CROP_CROP_FIELD_ID)));
            crop.setName(res.getString(res.getColumnIndex(CROP_CROP_NAME)));
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_CROP_RATE)));
            crop.setPlantingMethod(res.getString(res.getColumnIndex(CROP_CROP_PLANTING_METHOD)));
            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
        Log.d("TESTING",array_list.toString());
        return array_list;

    }
    public void  insertCropSpray(CropInventorySpray inventorySpray){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SPRAY_DATE,inventorySpray.getDateOfPurchase());
        contentValues.put(CROP_INVENTORY_SPRAY_USER_ID,inventorySpray.getUserId());
        contentValues.put(CROP_INVENTORY_SPRAY_TYPE,inventorySpray.getType());
        contentValues.put(CROP_INVENTORY_SPRAY_QUANTITY,inventorySpray.getQuantity());
        contentValues.put(CROP_INVENTORY_SPRAY_COST,inventorySpray.getCost());
        contentValues.put(CROP_INVENTORY_SPRAY_NAME,inventorySpray.getName());
        contentValues.put(CROP_INVENTORY_SPRAY_BATCH_NUMBER,inventorySpray.getBatchNumber());
        contentValues.put(CROP_INVENTORY_SPRAY_SUPPLIER,inventorySpray.getSupplier());
        contentValues.put(CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS,inventorySpray.getActiveIngredients());
        contentValues.put(CROP_INVENTORY_SPRAY_USAGE_UNIT,inventorySpray.getUsageUnits());
        contentValues.put(CROP_INVENTORY_SPRAY_EXPIRY_DATE,inventorySpray.getExpiryDate());
        contentValues.put(CROP_INVENTORY_SPRAY_HARVEST_INTERVAL,inventorySpray.getHarvestInterval());
        database.insert(CROP_INVENTORY_SPRAY_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropSpray(CropInventorySpray inventorySpray){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SPRAY_DATE,inventorySpray.getDateOfPurchase());
        contentValues.put(CROP_INVENTORY_SPRAY_USER_ID,inventorySpray.getUserId());
        contentValues.put(CROP_INVENTORY_SPRAY_TYPE,inventorySpray.getType());
        contentValues.put(CROP_INVENTORY_SPRAY_QUANTITY,inventorySpray.getQuantity());
        contentValues.put(CROP_INVENTORY_SPRAY_COST,inventorySpray.getCost());
        contentValues.put(CROP_INVENTORY_SPRAY_NAME,inventorySpray.getName());
        contentValues.put(CROP_INVENTORY_SPRAY_BATCH_NUMBER,inventorySpray.getBatchNumber());
        contentValues.put(CROP_INVENTORY_SPRAY_SUPPLIER,inventorySpray.getSupplier());
        contentValues.put(CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS,inventorySpray.getActiveIngredients());
        contentValues.put(CROP_INVENTORY_SPRAY_USAGE_UNIT,inventorySpray.getUsageUnits());
        contentValues.put(CROP_INVENTORY_SPRAY_EXPIRY_DATE,inventorySpray.getExpiryDate());
        contentValues.put(CROP_INVENTORY_SPRAY_HARVEST_INTERVAL,inventorySpray.getHarvestInterval());
        database.update(CROP_INVENTORY_SPRAY_TABLE_NAME,contentValues,CROP_INVENTORY_SPRAY_ID+" = ?", new String[]{inventorySpray.getId()});

        closeDB();
    }
    public boolean deleteCropSpray(String sprayId){
        openDB();
        database.delete(CROP_INVENTORY_SPRAY_TABLE_NAME,CROP_INVENTORY_SPRAY_ID+" = ?", new String[]{sprayId});
        closeDB();
        return true;
    }
    public ArrayList<CropInventorySpray> getCropSpray(String userId){
        openDB();
        ArrayList<CropInventorySpray> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_INVENTORY_SPRAY_TABLE_NAME+" where "+CROP_INVENTORY_SPRAY_USER_ID+" = "+userId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropInventorySpray spray = new CropInventorySpray();
            spray.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_ID)));
            spray.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_USER_ID)));
            spray.setDateOfPurchase(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_DATE)));
            spray.setName(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_NAME)));
            spray.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_TYPE)));
            spray.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_QUANTITY)));
            spray.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_QUANTITY)));
            spray.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_BATCH_NUMBER)));
            spray.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_SUPPLIER)));
            spray.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_USAGE_UNIT)));
            spray.setActiveIngredients(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS)));
            spray.setExpiryDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_EXPIRY_DATE)));
            spray.setHarvestInterval(res.getInt(res.getColumnIndex(CROP_INVENTORY_SPRAY_HARVEST_INTERVAL)));
            array_list.add(spray);
            res.moveToNext();
        }

        closeDB();
        return array_list;

    }
    public void  insertCropSeeds(CropInventorySeeds inventorySeeds){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SEEDS_DATE,inventorySeeds.getDateOfPurchase());
        contentValues.put(CROP_INVENTORY_SEEDS_USER_ID,inventorySeeds.getUserId());
        contentValues.put(CROP_INVENTORY_SEEDS_VARIETY,inventorySeeds.getVariety());
        contentValues.put(CROP_INVENTORY_SEEDS_NAME,inventorySeeds.getName());
        contentValues.put(CROP_INVENTORY_SEEDS_DRESSING,inventorySeeds.getDressing());
        contentValues.put(CROP_INVENTORY_SEEDS_QUANTITY,inventorySeeds.getQuantity());
        contentValues.put(CROP_INVENTORY_SEEDS_COST,inventorySeeds.getCost());
        contentValues.put(CROP_INVENTORY_SEEDS_BATCH_NUMBER,inventorySeeds.getBatchNumber());
        contentValues.put(CROP_INVENTORY_SEEDS_SUPPLIER,inventorySeeds.getSupplier());
        contentValues.put(CROP_INVENTORY_SEEDS_TGW,inventorySeeds.getTgw());
        contentValues.put(CROP_INVENTORY_SEEDS_USAGE_UNIT,inventorySeeds.getUsageUnits());
        database.insert(CROP_INVENTORY_SEEDS_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropSeeds(CropInventorySeeds inventorySeeds){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SEEDS_DATE,inventorySeeds.getDateOfPurchase());
        contentValues.put(CROP_INVENTORY_SEEDS_USER_ID,inventorySeeds.getUserId());
        contentValues.put(CROP_INVENTORY_SEEDS_VARIETY,inventorySeeds.getVariety());
        contentValues.put(CROP_INVENTORY_SEEDS_NAME,inventorySeeds.getName());
        contentValues.put(CROP_INVENTORY_SEEDS_DRESSING,inventorySeeds.getDressing());
        contentValues.put(CROP_INVENTORY_SEEDS_QUANTITY,inventorySeeds.getQuantity());
        contentValues.put(CROP_INVENTORY_SEEDS_COST,inventorySeeds.getCost());
        contentValues.put(CROP_INVENTORY_SEEDS_BATCH_NUMBER,inventorySeeds.getBatchNumber());
        contentValues.put(CROP_INVENTORY_SEEDS_SUPPLIER,inventorySeeds.getSupplier());
        contentValues.put(CROP_INVENTORY_SEEDS_TGW,inventorySeeds.getTgw());
        contentValues.put(CROP_INVENTORY_SEEDS_USAGE_UNIT,inventorySeeds.getUsageUnits());
        database.update(CROP_INVENTORY_SEEDS_TABLE_NAME,contentValues,CROP_INVENTORY_SEEDS_ID+" = ?", new String[]{inventorySeeds.getId()});
        closeDB();
    }
    public boolean deleteCropSeeds(String seedsId){
        openDB();
        database.delete(CROP_INVENTORY_SEEDS_TABLE_NAME,CROP_INVENTORY_SEEDS_ID+" = ?", new String[]{seedsId});
        closeDB();
        return true;
    }
    public ArrayList<CropInventorySeeds> getCropSeeds(String userId){
        openDB();
        ArrayList<CropInventorySeeds> array_list = new ArrayList();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_INVENTORY_SEEDS_TABLE_NAME+" where "+CROP_INVENTORY_SEEDS_USER_ID+" = "+userId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropInventorySeeds inventorySeeds = new CropInventorySeeds();
            inventorySeeds.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_ID)));
            inventorySeeds.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USER_ID)));
            inventorySeeds.setName(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_NAME)));
            inventorySeeds.setDateOfPurchase(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DATE)));
            inventorySeeds.setVariety(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_VARIETY)));
            inventorySeeds.setDressing(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DRESSING)));
            inventorySeeds.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_QUANTITY)));
            inventorySeeds.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_BATCH_NUMBER)));
            inventorySeeds.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_SUPPLIER)));
            inventorySeeds.setTgw(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TGW)));
            inventorySeeds.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USAGE_UNIT)));
            array_list.add(inventorySeeds);

            res.moveToNext();
        }

        closeDB();
        return array_list;

    }



    public void  insertCropFertilizer(CropInventoryFertilizer fertilizer){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_FERTILIZER_DATE,fertilizer.getPurchaseDate());
        contentValues.put(CROP_INVENTORY_FERTILIZER_USER_ID,fertilizer.getUserId());
        contentValues.put(CROP_INVENTORY_FERTILIZER_TYPE,fertilizer.getType());
        contentValues.put(CROP_INVENTORY_FERTILIZER_NAME,fertilizer.getName());
        contentValues.put(CROP_INVENTORY_FERTILIZER_N_PERCENTAGE,fertilizer.getnPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_K_PERCENTAGE,fertilizer.getkPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_P_PERCENTAGE,fertilizer.getpPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_QUANTITY,fertilizer.getQuantity());
        contentValues.put(CROP_INVENTORY_FERTILIZER_BATCH_NUMBER,fertilizer.getBatchNumber());
        contentValues.put(CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER,fertilizer.getSerialNumber());
        contentValues.put(CROP_INVENTORY_FERTILIZER_SUPPLIER,fertilizer.getSupplier());
        contentValues.put(CROP_INVENTORY_FERTILIZER_USAGE_UNIT,fertilizer.getUsageUnit());
        contentValues.put(CROP_INVENTORY_FERTILIZER_COST,fertilizer.getCost());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA,fertilizer.getMacroNutrientsCa());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG,fertilizer.getMacroNutrientsMg());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S,fertilizer.getMacroNutrientsS());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B,fertilizer.getMicroNutrientsB());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN,fertilizer.getMicroNutrientsMn());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO,fertilizer.getMicroNutrientsMo());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL,fertilizer.getMicroNutrientsCl());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU,fertilizer.getMicroNutrientsCu());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN,fertilizer.getMicroNutrientsZn());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE,fertilizer.getMicroNutrientsFe());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA,fertilizer.getMicroNutrientsNa());

        database.insert(CROP_INVENTORY_FERTILIZER_TABLE_NAME,null,contentValues);
        closeDB();
    }

    public void  insertCropField(CropField field){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_FIELD_USER_ID,field.getUserId());
        contentValues.put(CROP_FIELD_NAME,field.getFieldName());
        contentValues.put(CROP_FIELD_SOIL_CATEGORY,field.getSoilCategory());
        contentValues.put(CROP_FIELD_SOIL_TYPE,field.getSoilType());
        contentValues.put(CROP_FIELD_TOTAL_AREA,field.getTotalArea());
        contentValues.put(CROP_FIELD_CROPPABLE_AREA,field.getCroppableArea());
        contentValues.put(CROP_FIELD_UNITS,field.getUnits());

        database.insert(CROP_FIELDS_TABLE_NAME,null,contentValues);
        closeDB();
    }



    public void  updateCropFertilizer(CropInventoryFertilizer fertilizer){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_FERTILIZER_DATE,fertilizer.getPurchaseDate());
        contentValues.put(CROP_INVENTORY_FERTILIZER_USER_ID,fertilizer.getUserId());
        contentValues.put(CROP_INVENTORY_FERTILIZER_TYPE,fertilizer.getType());
        contentValues.put(CROP_INVENTORY_FERTILIZER_NAME,fertilizer.getName());
        contentValues.put(CROP_INVENTORY_FERTILIZER_N_PERCENTAGE,fertilizer.getnPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_K_PERCENTAGE,fertilizer.getkPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_P_PERCENTAGE,fertilizer.getpPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_QUANTITY,fertilizer.getQuantity());
        contentValues.put(CROP_INVENTORY_FERTILIZER_BATCH_NUMBER,fertilizer.getBatchNumber());
        contentValues.put(CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER,fertilizer.getSerialNumber());
        contentValues.put(CROP_INVENTORY_FERTILIZER_SUPPLIER,fertilizer.getSupplier());
        contentValues.put(CROP_INVENTORY_FERTILIZER_USAGE_UNIT,fertilizer.getUsageUnit());
        contentValues.put(CROP_INVENTORY_FERTILIZER_COST,fertilizer.getCost());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA,fertilizer.getMacroNutrientsCa());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG,fertilizer.getMacroNutrientsMg());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S,fertilizer.getMacroNutrientsS());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B,fertilizer.getMicroNutrientsB());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN,fertilizer.getMicroNutrientsMn());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO,fertilizer.getMicroNutrientsMo());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL,fertilizer.getMicroNutrientsCl());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU,fertilizer.getMicroNutrientsCu());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN,fertilizer.getMicroNutrientsZn());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE,fertilizer.getMicroNutrientsFe());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA,fertilizer.getMicroNutrientsNa());

        database.update(CROP_INVENTORY_FERTILIZER_TABLE_NAME,contentValues,CROP_INVENTORY_FERTILIZER_ID+" = ?", new String[]{fertilizer.getId()});
        closeDB();
    }
    public boolean deleteCropFertilizer(String fertilizerId){
        openDB();
        database.delete(CROP_INVENTORY_FERTILIZER_TABLE_NAME,CROP_INVENTORY_FERTILIZER_ID+" = ?", new String[]{fertilizerId});
        closeDB();
        return true;
    }


    public ArrayList<CropInventoryFertilizer> getCropFertilizers(String userId){
        openDB();
        ArrayList<CropInventoryFertilizer> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_INVENTORY_FERTILIZER_TABLE_NAME+" where "+CROP_INVENTORY_FERTILIZER_USER_ID+" = "+userId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropInventoryFertilizer fertilizer = new CropInventoryFertilizer();
            fertilizer.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_ID)));
            fertilizer.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_USER_ID)));
            fertilizer.setPurchaseDate(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_DATE)));
            fertilizer.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_TYPE)));
            fertilizer.setFertilizerName(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_NAME)));
            fertilizer.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_QUANTITY)));
            fertilizer.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_BATCH_NUMBER)));
            fertilizer.setSerialNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER)));
            fertilizer.setnPercentage(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_N_PERCENTAGE)));
            fertilizer.setpPercentage(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_P_PERCENTAGE)));
            fertilizer.setkPercentage(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_K_PERCENTAGE)));
            fertilizer.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_SUPPLIER)));
            fertilizer.setUsageUnit(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_USAGE_UNIT)));
            fertilizer.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_COST)));
            fertilizer.setMacroNutrientsCa(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA)));
            fertilizer.setMacroNutrientsMg(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG)));
            fertilizer.setMacroNutrientsS(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S)));
            fertilizer.setMicroNutrientsB(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B)));
            fertilizer.setMicroNutrientsMn(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN)));
            fertilizer.setMicroNutrientsCl(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL)));
            fertilizer.setMicroNutrientsMo(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO)));
            fertilizer.setMicroNutrientsCu(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU)));
            fertilizer.setMicroNutrientsZn(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN)));
            fertilizer.setMicroNutrientsFe(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE)));
            fertilizer.setMicroNutrientsNa(res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA)));
            array_list.add(fertilizer);

            res.moveToNext();
        }

        closeDB();
        //Log.d("HOUSES SIZE",array_list.size()+"");
        return array_list;

    }

    public void  updateCropField(CropField field){
        openDB();
        ContentValues contentValues = new ContentValues();


        contentValues.put(CROP_FIELD_USER_ID,field.getUserId());
        contentValues.put(CROP_FIELD_NAME,field.getFieldName());
        contentValues.put(CROP_FIELD_SOIL_CATEGORY,field.getSoilCategory());
        contentValues.put(CROP_FIELD_SOIL_TYPE,field.getSoilType());
        contentValues.put(CROP_FIELD_WATERCOURSE,field.getWatercourse());
        contentValues.put(CROP_FIELD_TOTAL_AREA,field.getTotalArea());
        contentValues.put(CROP_FIELD_CROPPABLE_AREA,field.getCroppableArea());


        database.update(CROP_FIELDS_TABLE_NAME,contentValues, CROP_FIELD_ID +" = ?", new String[]{field.getId()});
        closeDB();
    }


    public boolean deleteCropField(String fieldId){
        openDB();
        database.delete(CROP_FIELDS_TABLE_NAME, CROP_FIELD_ID +" = ?", new String[]{fieldId});
        closeDB();
        return true;
    }

    public ArrayList<CropField> getCropFields(String userId) {
        openDB();
        ArrayList<CropField> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_FIELDS_TABLE_NAME + " where " + CROP_FIELD_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropField field = new CropField();
            field.setId(res.getString(res.getColumnIndex(CROP_FIELD_ID)));
            field.setUserId(res.getString(res.getColumnIndex(CROP_FIELD_USER_ID)));
            field.setFieldName(res.getString(res.getColumnIndex(CROP_FIELD_NAME)));
            field.setSoilCategory(res.getString(res.getColumnIndex(CROP_FIELD_SOIL_CATEGORY)));
            field.setSoilType(res.getString(res.getColumnIndex(CROP_FIELD_SOIL_TYPE)));
            field.setWatercourse(res.getString(res.getColumnIndex(CROP_FIELD_WATERCOURSE)));
            field.setTotalArea(res.getFloat(res.getColumnIndex(CROP_FIELD_TOTAL_AREA)));
            field.setCroppableArea(res.getFloat(res.getColumnIndex(CROP_FIELD_CROPPABLE_AREA)));
            field.setUnits(res.getString(res.getColumnIndex(CROP_FIELD_UNITS)));
            array_list.add(field);

            res.moveToNext();
        }
        closeDB();
        Log.d("FIELDS SIZE", array_list.size() + "");
        return array_list;
    }
        public void  insertCropMachine(CropMachine machine){
            openDB();
            ContentValues contentValues = new ContentValues();

            contentValues.put(CROP_MACHINE_USER_ID, machine.getUserId());
            contentValues.put(CROP_MACHINE_NAME, machine.getName());
            contentValues.put(CROP_MACHINE_BRAND, machine.getBrand());
            contentValues.put(CROP_MACHINE_CATEGORY, machine.getCategory());
            contentValues.put(CROP_MACHINE_MANUFACTURER, machine.getManufacturer());
            contentValues.put(CROP_MACHINE_MODEL, machine.getModel());
            contentValues.put(CROP_MACHINE_REGISTRATION_NUMBER, machine.getRegistrationNumber());
            contentValues.put(CROP_MACHINE_QUANTITY, machine.getQuantity());
            contentValues.put(CROP_MACHINE_DATE_ACQUIRED, machine.getDate());
            contentValues.put(CROP_MACHINE_PURCHASED_FROM, machine.getPurchasedFrom());
            contentValues.put(CROP_MACHINE_STORAGE_LOCATION, machine.getStorageLocation());
            contentValues.put(CROP_MACHINE_PURCHASE_PRICE, machine.getPurchasePrice());

            database.insert(CROP_MACHINE_TABLE_NAME,null,contentValues);
            closeDB();
        }
        public void  updateCropMachine(CropMachine machine){
            openDB();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CROP_MACHINE_USER_ID, machine.getUserId());
            contentValues.put(CROP_MACHINE_NAME, machine.getName());
            contentValues.put(CROP_MACHINE_BRAND, machine.getBrand());
            contentValues.put(CROP_MACHINE_CATEGORY, machine.getCategory());
            contentValues.put(CROP_MACHINE_MANUFACTURER, machine.getManufacturer());
            contentValues.put(CROP_MACHINE_MODEL, machine.getModel());
            contentValues.put(CROP_MACHINE_REGISTRATION_NUMBER, machine.getRegistrationNumber());
            contentValues.put(CROP_MACHINE_QUANTITY, machine.getQuantity());
            contentValues.put(CROP_MACHINE_DATE_ACQUIRED, machine.getDate());
            contentValues.put(CROP_MACHINE_PURCHASED_FROM, machine.getPurchasedFrom());
            contentValues.put(CROP_MACHINE_STORAGE_LOCATION, machine.getStorageLocation());
            contentValues.put(CROP_MACHINE_PURCHASE_PRICE, machine.getPurchasePrice());

            database.update(CROP_MACHINE_TABLE_NAME,contentValues,CROP_MACHINE_ID+" = ?", new String[]{machine.getId()});

            closeDB();
        }
        public boolean deleteCropMachine(String machineId){
            openDB();
            database.delete(CROP_MACHINE_TABLE_NAME,CROP_MACHINE_ID+" = ?", new String[]{machineId});
            closeDB();
            return true;
        }
        public ArrayList<CropMachine> getCropMachines(String userId){
            openDB();
            ArrayList<CropMachine> array_list = new ArrayList();

            //hp = new HashMap();
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + CROP_MACHINE_TABLE_NAME+ " where " + CROP_MACHINE_USER_ID + " = " + userId, null);
            res.moveToFirst();

            while (!res.isAfterLast()) {
                CropMachine machine = new CropMachine();
                machine.setId(res.getString(res.getColumnIndex(CROP_MACHINE_ID)));
                machine.setUserId(res.getString(res.getColumnIndex(CROP_MACHINE_USER_ID)));
                machine.setDate(res.getString(res.getColumnIndex(CROP_MACHINE_DATE_ACQUIRED)));
                machine.setName(res.getString(res.getColumnIndex(CROP_MACHINE_NAME)));
                machine.setBrand(res.getString(res.getColumnIndex(CROP_MACHINE_BRAND)));
                machine.setCategory(res.getString(res.getColumnIndex(CROP_MACHINE_CATEGORY)));
                machine.setManufacturer(res.getString(res.getColumnIndex(CROP_MACHINE_MANUFACTURER)));
                machine.setModel(res.getString(res.getColumnIndex(CROP_MACHINE_MODEL)));

                machine.setRegistrationNumber(res.getInt(res.getColumnIndex(CROP_MACHINE_REGISTRATION_NUMBER)));
                machine.setQuantity(res.getFloat(res.getColumnIndex(CROP_MACHINE_QUANTITY)));
                machine.setPurchasedFrom(res.getString(res.getColumnIndex(CROP_MACHINE_PURCHASED_FROM)));
                machine.setStorageLocation(res.getString(res.getColumnIndex(CROP_MACHINE_STORAGE_LOCATION)));
                machine.setPurchasePrice(res.getFloat(res.getColumnIndex(CROP_MACHINE_PURCHASE_PRICE)));


                array_list.add(machine);
                res.moveToNext();
            }

            closeDB();
            return array_list;
        }

}
