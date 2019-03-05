package com.myfarmnow.myfarmcrop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;

import java.util.ArrayList;


public class MyFarmDbHandlerSingleton extends SQLiteOpenHelper {


    private static final String DATABASE_NAME ="myfarmdb";
    private static int database_version=1;
    public static final String CROP_INVENTORY_FERTILIZER_TABLE_NAME ="crop_inventory_fertilizer";
    public static final String CROP_INVENTORY_SEEDS_TABLE_NAME ="crop_inventory_seeds";
    public static final String CROP_FIELDS_TABLE_NAME ="crop_fields";
    public static final String CROP_MACHINES_TABLE_NAME ="crop_machines";

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


    public static final String CROP_FIELD_ID ="id";
    public static final String CROP_FIELD_USER_ID ="userId";
    public static final String CROP_FIELD_NAME="fieldName";
    public static final String CROP_FIELD_SOIL_CATEGORY="soilCategory";
    public static final String CROP_FIELD_SOIL_TYPE="soilType";
    public static final String CROP_FIELD_WATERCOURSE="watercourse";
    public static final String CROP_FIELD_TOTAL_AREA="totalArea";
    public static final String CROP_FIELD_CROPPABLE_AREA="croppableArea";
    public static final String CROP_FIELD_UNITS="units";


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
        String crop_fertilizer_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_INVENTORY_FERTILIZER_TABLE_NAME+" ( "+CROP_INVENTORY_FERTILIZER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_INVENTORY_FERTILIZER_USER_ID+" TEXT,"+CROP_INVENTORY_FERTILIZER_DATE+" TEXT NOT NULL,"+ CROP_INVENTORY_FERTILIZER_TYPE+" TEXT NOT NULL,"+ CROP_INVENTORY_FERTILIZER_NAME+" TEXT NOT NULL,"+ CROP_INVENTORY_FERTILIZER_N_PERCENTAGE+" REAL,"+
                CROP_INVENTORY_FERTILIZER_P_PERCENTAGE+" REAL,"+ CROP_INVENTORY_FERTILIZER_K_PERCENTAGE+" REAL,"+ CROP_INVENTORY_FERTILIZER_QUANTITY+" REAL NOT NULL,"+CROP_INVENTORY_FERTILIZER_BATCH_NUMBER+" TEXT NOT NULL,"+
                CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER+" TEXT,"+CROP_INVENTORY_FERTILIZER_SUPPLIER+" TEXT,"+CROP_INVENTORY_FERTILIZER_USAGE_UNIT+" TEXT ,"+CROP_INVENTORY_FERTILIZER_COST+" REAL ,"+CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA+" REAL ,"+
                CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG+" REAL ,"+CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S+" REAL ,"+CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B+" REAL ,"+
                CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU+" REAL ,"+
                CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE+" REAL ,"+ CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA+" REAL )";

        Log.d("FERTILIZER QUERY",crop_fertilizer_insert_query); //displays this generated SQL query on the log cat

        database.execSQL(crop_fertilizer_insert_query);

        database = db;
        String crop_field_insert_query ="CREATE TABLE IF NOT EXISTS "+ CROP_FIELDS_TABLE_NAME +" ( "+ CROP_FIELD_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_FIELD_USER_ID+" TEXT,"+CROP_FIELD_NAME+" TEXT NOT NULL,"+ CROP_FIELD_SOIL_CATEGORY+" TEXT,"+ CROP_FIELD_SOIL_TYPE+" TEXT,"+CROP_FIELD_WATERCOURSE+" TEXT,"+
                CROP_FIELD_TOTAL_AREA +" REAL NOT NULL ,"+ CROP_FIELD_CROPPABLE_AREA+" REAL ,"+ CROP_FIELD_UNITS+" REAL NOT NULL)";

        Log.d("FIELDS QUERY",crop_field_insert_query); //displays this generated SQL query on the log cat

        database.execSQL(crop_field_insert_query);


    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CROP_INVENTORY_FERTILIZER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CROP_FIELDS_TABLE_NAME);

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
        Log.d("HOUSES SIZE",array_list.size()+"");
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

        while(!res.isAfterLast()){
            CropField field = new CropField();
            field.setId(res.getString(res.getColumnIndex(CROP_FIELD_ID)));
            field.setUserId(res.getString(res.getColumnIndex(CROP_FIELD_USER_ID)));
            field.setFieldName(res.getString(res.getColumnIndex(CROP_FIELD_NAME)));
            field.setSoilCategory(res.getString(res.getColumnIndex(CROP_FIELD_SOIL_CATEGORY)));
            field.setSoilType(res.getString(res.getColumnIndex(CROP_FIELD_SOIL_TYPE)));
            field.setWatercourse(res.getString(res.getColumnIndex(CROP_FIELD_WATERCOURSE)));
            field.setTotalArea(res.getFloat(res.getColumnIndex(CROP_FIELD_TOTAL_AREA)));
            field.setCroppableArea(res.getFloat(res.getColumnIndex(CROP_FIELD_CROPPABLE_AREA)));
            field.setUnits(res.getFloat(res.getColumnIndex(CROP_FIELD_UNITS)));
            array_list.add(field);

            res.moveToNext();
        }
        closeDB();
        Log.d("FIELDS SIZE",array_list.size()+"");
        return array_list;

}}
