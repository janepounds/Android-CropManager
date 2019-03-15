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
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropEstimate;
import com.myfarmnow.myfarmcrop.models.CropEstimateItem;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;

import com.myfarmnow.myfarmcrop.models.CropField;

import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.models.CropInvoiceItem;
import com.myfarmnow.myfarmcrop.models.CropMachine;
import com.myfarmnow.myfarmcrop.models.CropPayment;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropSoilAnalysis;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
import com.myfarmnow.myfarmcrop.models.CropSupplier;

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
    public static final String CROP_SPRAYING_TABLE_NAME ="crop_spraying";
    public static final String CROP_FIELDS_TABLE_NAME ="crop_fields";
    public static final String CROP_MACHINE_TABLE_NAME ="crop_machine";
    public static final String CROP_SOIL_ANALYSIS_TABLE_NAME ="crop_soil_analysis";
    public static final String CROP_EMPLOYEE_TABLE_NAME ="crop_employee";
    public static final String CROP_CUSTOMER_TABLE_NAME ="crop_customer";
    public static final String CROP_SUPPLIER_TABLE_NAME ="crop_supplier";
    public static final String CROP_PRODUCT_TABLE_NAME ="crop_product";
    public static final String CROP_ESTIMATE_TABLE_NAME ="crop_estimate";
    public static final String CROP_ESTIMATE_ITEM_TABLE_NAME ="crop_estimate_items";
    public static final String CROP_INVOICE_TABLE_NAME ="crop_invoice";
    public static final String CROP_INVOICE_ITEM_TABLE_NAME ="crop_invoice_items";
    public static final String CROP_PAYMENT_TABLE_NAME ="crop_payments";


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

    public static final String CROP_EMPLOYEE_ID ="id";
    public static final String CROP_EMPLOYEE_USER_ID ="userId";
    public static final String CROP_EMPLOYEE_TITLE ="title";
    public static final String CROP_EMPLOYEE_FIRST_NAME ="firstName";
    public static final String CROP_EMPLOYEE_LAST_NAME ="lastName";
    public static final String CROP_EMPLOYEE_PHONE ="phone";
    public static final String CROP_EMPLOYEE_MOBILE ="mobile";
    public static final String CROP_EMPLOYEE_EMP_ID ="employeeId";
    public static final String CROP_EMPLOYEE_GENDER ="gender";
    public static final String CROP_EMPLOYEE_ADDRESS ="address";
    public static final String CROP_EMPLOYEE_EMAIL ="email";
    public static final String CROP_EMPLOYEE_DOB ="dateOfBirth";
    public static final String CROP_EMPLOYEE_HIRE_DATE ="hireDate";
    public static final String CROP_EMPLOYEE_EMPLOYMENT_STATUS ="employmentStatus";
    public static final String CROP_EMPLOYEE_PAY_AMOUNT ="payAmount";
    public static final String CROP_EMPLOYEE_PAY_RATE ="payRate";
    public static final String CROP_EMPLOYEE_PAY_TYPE ="payType";
    public static final String CROP_EMPLOYEE_SUPERVISOR ="supervisor";

    public static final String CROP_CUSTOMER_ID ="id";
    public static final String CROP_CUSTOMER_USER_ID ="userId";
    public static final String CROP_CUSTOMER_NAME ="name";
    public static final String CROP_CUSTOMER_COMPANY ="company";
    public static final String CROP_CUSTOMER_TAX_REG_NO ="taxRegNo";
    public static final String CROP_CUSTOMER_PHONE ="phone";
    public static final String CROP_CUSTOMER_MOBILE ="mobile";
    public static final String CROP_CUSTOMER_EMAIL ="email";
    public static final String CROP_CUSTOMER_OPENING_BALANCE ="openingBalance";
    public static final String CROP_CUSTOMER_BILL_ADDRESS_STREET ="billingStreet";
    public static final String CROP_CUSTOMER_BILL_ADDRESS_CITY ="billingCityOrTown";
    public static final String CROP_CUSTOMER_BILL_ADDRESS_COUNTRY ="billingCountry";
    public static final String CROP_CUSTOMER_SHIP_ADDRESS_STREET ="shippingStreet";
    public static final String CROP_CUSTOMER_SHIP_ADDRESS_CITY ="shippingCityOrTown";
    public static final String CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY ="shippingCountry";


    public static final String CROP_SUPPLIER_ID ="id";
    public static final String CROP_SUPPLIER_USER_ID ="userId";
    public static final String CROP_SUPPLIER_NAME ="name";
    public static final String CROP_SUPPLIER_COMPANY ="company";
    public static final String CROP_SUPPLIER_TAX_REG_NO ="taxRegNo";
    public static final String CROP_SUPPLIER_PHONE ="phone";
    public static final String CROP_SUPPLIER_MOBILE ="mobile";
    public static final String CROP_SUPPLIER_EMAIL ="email";
    public static final String CROP_SUPPLIER_OPENING_BALANCE ="openingBalance";
    public static final String CROP_SUPPLIER_INVOICE_ADDRESS_STREET ="invoiceStreet";
    public static final String CROP_SUPPLIER_INVOICE_ADDRESS_CITY ="invoiceCityOrTown";
    public static final String CROP_SUPPLIER_INVOICE_ADDRESS_COUNTRY ="invoiceCountry";

    public static final String CROP_PRODUCT_ID ="id";
    public static final String CROP_PRODUCT_USER_ID ="userId";
    public static final String CROP_PRODUCT_NAME ="name";
    public static final String CROP_PRODUCT_TYPE ="type";
    public static final String CROP_PRODUCT_CODE ="code";
    public static final String CROP_PRODUCT_UNITS ="units";
    public static final String CROP_PRODUCT_LINKED_ACCOUNT ="linkedAccount";
    public static final String CROP_PRODUCT_OPENING_COST ="openingCost";
    public static final String CROP_PRODUCT_OPENING_QUANTITY ="openingQuantity";
    public static final String CROP_PRODUCT_SELLING_PRICE ="sellingPrice";
    public static final String CROP_PRODUCT_TAX_RATE="taxRate";
    public static final String CROP_PRODUCT_DESCRIPTION ="description";

    public static final String CROP_ESTIMATE_ID ="id";
    public static final String CROP_ESTIMATE_USER_ID ="userId";
    public static final String CROP_ESTIMATE_CUSTOMER_ID ="customerId";
    public static final String CROP_ESTIMATE_NO ="number";
    public static final String CROP_ESTIMATE_DATE ="date";
    public static final String CROP_ESTIMATE_EXP_DATE ="expiryDate";
    public static final String CROP_ESTIMATE_DISCOUNT ="discount";
    public static final String CROP_ESTIMATE_SHIPPING_CHARGES ="shippingCharges";
    public static final String CROP_ESTIMATE_CUSTOMER_NOTES ="customerNotes";
    public static final String CROP_ESTIMATE_TERMS_AND_CONDITIONS ="termsAndConditions";


    public static final String CROP_ESTIMATE_ITEM_ID ="id";
    public static final String CROP_ESTIMATE_ITEM_PRODUCT_ID ="productId";
    public static final String CROP_ESTIMATE_ITEM_ESTIMATE_ID ="estimateId";
    public static final String CROP_ESTIMATE_ITEM_QUANTITY="quantity";
    public static final String CROP_ESTIMATE_ITEM_RATE="rate";
    public static final String CROP_ESTIMATE_ITEM_TAX="tax";

    public static final String CROP_INVOICE_ID ="id";
    public static final String CROP_INVOICE_USER_ID ="userId";
    public static final String CROP_INVOICE_CUSTOMER_ID ="customerId";
    public static final String CROP_INVOICE_NO ="number";
    public static final String CROP_INVOICE_DATE ="date";
    public static final String CROP_INVOICE_EXP_DATE ="expiryDate";
    public static final String CROP_INVOICE_DISCOUNT ="discount";
    public static final String CROP_INVOICE_SHIPPING_CHARGES ="shippingCharges";
    public static final String CROP_INVOICE_CUSTOMER_NOTES ="customerNotes";
    public static final String CROP_INVOICE_TERMS_AND_CONDITIONS ="termsAndConditions";


    public static final String CROP_INVOICE_ITEM_ID ="id";
    public static final String CROP_INVOICE_ITEM_PRODUCT_ID ="productId";
    public static final String CROP_INVOICE_ITEM_INVOICE_ID ="estimateId";
    public static final String CROP_INVOICE_ITEM_QUANTITY="quantity";
    public static final String CROP_INVOICE_ITEM_RATE="rate";
    public static final String CROP_INVOICE_ITEM_TAX="tax";

    public static final String CROP_PAYMENT_ID ="id";
    public static final String CROP_PAYMENT_USER_ID ="userId";
    public static final String CROP_PAYMENT_CUSTOMER_ID ="customerId";
    public static final String CROP_PAYMENT_AMOUNT ="amount";
    public static final String CROP_PAYMENT_DATE ="date";
    public static final String CROP_PAYMENT_MODE ="mode";
    public static final String CROP_PAYMENT_REFERENCE_NO ="referenceNo";
    public static final String CROP_PAYMENT_NUMBER ="paymentNumber";
    public static final String CROP_PAYMENT_NOTES ="notes";
    public static final String CROP_PAYMENT_INVOICE_ID ="invoiceId";

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

        String crop_employee_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_EMPLOYEE_TABLE_NAME+" ( "+CROP_EMPLOYEE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_EMPLOYEE_USER_ID+" TEXT,"+CROP_EMPLOYEE_FIRST_NAME+" TEXT NOT NULL,"+CROP_EMPLOYEE_LAST_NAME+" TEXT NOT NULL,"+CROP_EMPLOYEE_TITLE+" TEXT NOT NULL ,"+
                CROP_EMPLOYEE_PHONE+" TEXT NOT NULL,"+CROP_EMPLOYEE_MOBILE+" TEXT ,"+ CROP_EMPLOYEE_EMP_ID+" TEXT ,"+
                CROP_EMPLOYEE_GENDER+" TEXT NOT NULL,"+ CROP_EMPLOYEE_ADDRESS+" TEXT ,"+CROP_EMPLOYEE_EMAIL+" TEXT ,"+
                CROP_EMPLOYEE_DOB+" TEXT,"+CROP_EMPLOYEE_HIRE_DATE+" TEXT,"+CROP_EMPLOYEE_EMPLOYMENT_STATUS+" TEXT NOT NULL,"+
                CROP_EMPLOYEE_PAY_AMOUNT+" REAL NOT NULL,"+ CROP_EMPLOYEE_PAY_RATE+" TEXT ,"+CROP_EMPLOYEE_PAY_TYPE+" TEXT NOT NULL,"+CROP_EMPLOYEE_SUPERVISOR+" TEXT "+" )";

        String crop_customer_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_CUSTOMER_TABLE_NAME+" ( "+CROP_CUSTOMER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_CUSTOMER_USER_ID+" TEXT,"+CROP_CUSTOMER_NAME+" TEXT NOT NULL,"+CROP_CUSTOMER_COMPANY+" TEXT NOT NULL,"+CROP_CUSTOMER_TAX_REG_NO+" TEXT ,"+
                CROP_CUSTOMER_PHONE+" TEXT NOT NULL,"+CROP_CUSTOMER_MOBILE+" TEXT ,"+ CROP_CUSTOMER_EMAIL+" TEXT ,"+
                CROP_CUSTOMER_OPENING_BALANCE+" REAL NOT NULL,"+ CROP_CUSTOMER_BILL_ADDRESS_STREET+" TEXT NOT NULL ,"+CROP_CUSTOMER_BILL_ADDRESS_CITY+" TEXT NOT NULL ,"+
                CROP_CUSTOMER_BILL_ADDRESS_COUNTRY+" TEXT NOT NULL,"+CROP_CUSTOMER_SHIP_ADDRESS_STREET+" TEXT NOT NULL,"+CROP_CUSTOMER_SHIP_ADDRESS_CITY+" TEXT NOT NULL,"+
                CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY+" TEXT NOT NULL "+" )";
        String crop_supplier_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_SUPPLIER_TABLE_NAME+" ( "+CROP_SUPPLIER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_SUPPLIER_USER_ID+" TEXT,"+CROP_SUPPLIER_NAME+" TEXT NOT NULL,"+CROP_SUPPLIER_COMPANY+" TEXT NOT NULL,"+CROP_SUPPLIER_TAX_REG_NO+" TEXT ,"+
                CROP_SUPPLIER_PHONE+" TEXT NOT NULL,"+CROP_SUPPLIER_MOBILE+" TEXT ,"+ CROP_SUPPLIER_EMAIL+" TEXT ,"+CROP_SUPPLIER_OPENING_BALANCE+" REAL ,"+CROP_SUPPLIER_INVOICE_ADDRESS_STREET+" TEXT NOT NULL,"+CROP_SUPPLIER_INVOICE_ADDRESS_CITY+" TEXT NOT NULL,"+
                CROP_SUPPLIER_INVOICE_ADDRESS_COUNTRY+" TEXT NOT NULL "+" )";
        String crop_product_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_PRODUCT_TABLE_NAME+" ( "+CROP_PRODUCT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_PRODUCT_USER_ID+" TEXT NOT NULL,"+CROP_PRODUCT_NAME+" TEXT NOT NULL,"+CROP_PRODUCT_TYPE+" TEXT NOT NULL,"+CROP_PRODUCT_CODE+" TEXT ,"+
                CROP_PRODUCT_UNITS+" TEXT,"+CROP_PRODUCT_LINKED_ACCOUNT+" TEXT ,"+ CROP_PRODUCT_OPENING_COST+" REAL  ,"+CROP_PRODUCT_OPENING_QUANTITY+" REAL ,"+
                CROP_PRODUCT_SELLING_PRICE+" REAL ,"+CROP_PRODUCT_TAX_RATE+" REAL ,"+
                CROP_PRODUCT_DESCRIPTION+" TEXT "+" )";

        String crop_estimates_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_ESTIMATE_TABLE_NAME+" ( "+CROP_ESTIMATE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_ESTIMATE_USER_ID+" TEXT NOT NULL,"+CROP_ESTIMATE_CUSTOMER_ID+" TEXT NOT NULL,"+CROP_ESTIMATE_NO+" TEXT NOT NULL,"+CROP_ESTIMATE_DATE+" TEXT NOT NULL,"+
                CROP_ESTIMATE_EXP_DATE+" TEXT,"+CROP_ESTIMATE_DISCOUNT+" REAL DEFAULT 0,"+ CROP_ESTIMATE_SHIPPING_CHARGES+" REAL DEFAULT 0  ,"+
                CROP_ESTIMATE_CUSTOMER_NOTES+" TEXT ,"+ CROP_ESTIMATE_TERMS_AND_CONDITIONS+" TEXT "+" )";


        String crop_estimate_item_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_ESTIMATE_ITEM_TABLE_NAME+" ( "+CROP_ESTIMATE_ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_ESTIMATE_ITEM_PRODUCT_ID+" TEXT NOT NULL,"+CROP_ESTIMATE_ITEM_ESTIMATE_ID+" TEXT NOT NULL,"+ CROP_ESTIMATE_ITEM_QUANTITY+" REAL NOT NULL, "+CROP_ESTIMATE_ITEM_TAX+" REAL NOT NULL, "+
                CROP_ESTIMATE_ITEM_RATE+" REAL NOT NULL, "+" FOREIGN KEY ( "+CROP_ESTIMATE_ITEM_ESTIMATE_ID+") REFERENCES  "+CROP_ESTIMATE_TABLE_NAME+" ( "+CROP_ESTIMATE_ID+" ), " +
                "FOREIGN KEY( "+CROP_ESTIMATE_ITEM_PRODUCT_ID+") REFERENCES  "+CROP_PRODUCT_TABLE_NAME+" ( "+CROP_PRODUCT_ID+" ) )";


        String crop_invoices_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_INVOICE_TABLE_NAME+" ( "+CROP_INVOICE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_INVOICE_USER_ID+" TEXT NOT NULL,"+CROP_INVOICE_CUSTOMER_ID+" TEXT NOT NULL,"+CROP_INVOICE_NO+" TEXT NOT NULL,"+CROP_INVOICE_DATE+" TEXT NOT NULL,"+
                CROP_INVOICE_EXP_DATE+" TEXT,"+CROP_INVOICE_DISCOUNT+" REAL DEFAULT 0,"+ CROP_INVOICE_SHIPPING_CHARGES+" REAL DEFAULT 0  ,"+
                CROP_INVOICE_CUSTOMER_NOTES+" TEXT ,"+ CROP_INVOICE_TERMS_AND_CONDITIONS+" TEXT "+" )";


        String crop_invoice_item_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_INVOICE_ITEM_TABLE_NAME+" ( "+CROP_INVOICE_ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_INVOICE_ITEM_PRODUCT_ID+" TEXT NOT NULL,"+CROP_INVOICE_ITEM_INVOICE_ID+" TEXT NOT NULL,"+ CROP_INVOICE_ITEM_QUANTITY+" REAL NOT NULL, "+CROP_INVOICE_ITEM_TAX+" REAL NOT NULL, "+
                CROP_INVOICE_ITEM_RATE+" REAL NOT NULL, "+" FOREIGN KEY ( "+CROP_INVOICE_ITEM_INVOICE_ID+") REFERENCES  "+CROP_INVOICE_TABLE_NAME+" ( "+CROP_INVOICE_ID+" ), " +
                "FOREIGN KEY( "+CROP_INVOICE_ITEM_PRODUCT_ID+") REFERENCES  "+CROP_PRODUCT_TABLE_NAME+" ( "+CROP_PRODUCT_ID+" ) )";

        String crop_payment_item_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_PAYMENT_TABLE_NAME+" ( "+CROP_PAYMENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_PAYMENT_CUSTOMER_ID+" TEXT NOT NULL,"+CROP_PAYMENT_DATE+" TEXT NOT NULL,"+ CROP_PAYMENT_MODE+" TEXT NOT NULL, "+CROP_PAYMENT_AMOUNT+" REAL NOT NULL, "+
                CROP_PAYMENT_REFERENCE_NO+" TEXT , "+CROP_PAYMENT_NUMBER+" TEXT , "+CROP_PAYMENT_NOTES+" TEXT , "+CROP_PAYMENT_INVOICE_ID+" TEXT , "+" FOREIGN KEY ( "+CROP_PAYMENT_INVOICE_ID+") REFERENCES  "+CROP_INVOICE_TABLE_NAME+" ( "+CROP_INVOICE_ID+" ), " +
                "FOREIGN KEY( "+CROP_PAYMENT_CUSTOMER_ID+") REFERENCES  "+CROP_CUSTOMER_TABLE_NAME+" ( "+CROP_CUSTOMER_ID+" ) )";

        /*Log.d("FERTILIZER INVENTORY",crop_inventory_fertilizer_insert_query);
        Log.d("SEEDS INVENTORY",crop_seeds_insert_query);
        Log.d("SPRAY INVENTORY",crop_inventory_spray_insert_query);
        Log.d("SPRAY INVENTORY",crop_spraying_insert_query);
        Log.d("CROP",crop_insert_query);
        Log.d("CULTIVATE",crop_cultivate_insert_query);
        Log.d("FERTILIZER",crop_fertilizer_insert_query);
        Log.d("FIELDS",crop_field_insert_query);
        Log.d("MACHINE",crop_machine_insert_query);*/

        //db.execSQL("DROP TABLE IF EXISTS "+ CROP_SUPPLIER_TABLE_NAME);
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
        database.execSQL(crop_employee_insert_query);
        database.execSQL(crop_customer_insert_query);
        database.execSQL(crop_supplier_insert_query);
        database.execSQL(crop_product_insert_query);
        database.execSQL(crop_estimates_insert_query);
        database.execSQL(crop_estimate_item_insert_query);
        database.execSQL(crop_invoices_insert_query);
        database.execSQL(crop_invoice_item_insert_query);
        database.execSQL(crop_payment_item_insert_query);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+CROP_INVENTORY_FERTILIZER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CROP_INVENTORY_SEEDS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CROP_INVENTORY_SPRAY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CROP_SPRAYING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CROP_FIELDS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CROP_MACHINE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ CROP_EMPLOYEE_TABLE_NAME);
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

    public void  insertCropPayment(CropPayment cropPayment){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PAYMENT_USER_ID,cropPayment.getUserId());
        contentValues.put(CROP_PAYMENT_AMOUNT,cropPayment.getAmount());
        contentValues.put(CROP_PAYMENT_DATE,cropPayment.getDate());
        contentValues.put(CROP_PAYMENT_MODE,cropPayment.getMode());
        contentValues.put(CROP_PAYMENT_NUMBER,cropPayment.getPaymentNumber());
        contentValues.put(CROP_PAYMENT_NOTES,cropPayment.getNotes());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID,cropPayment.getCustomerId());
        contentValues.put(CROP_PAYMENT_REFERENCE_NO,cropPayment.getReferenceNo());
        contentValues.put(CROP_PAYMENT_INVOICE_ID,cropPayment.getInvoiceId());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID,cropPayment.getCustomerId());
        database.insert(CROP_PAYMENT_TABLE_NAME,null,contentValues);

        closeDB();
    }
    public void  updateCropPayment(CropPayment cropPayment){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PAYMENT_USER_ID,cropPayment.getUserId());
        contentValues.put(CROP_PAYMENT_AMOUNT,cropPayment.getAmount());
        contentValues.put(CROP_PAYMENT_DATE,cropPayment.getDate());
        contentValues.put(CROP_PAYMENT_MODE,cropPayment.getMode());
        contentValues.put(CROP_PAYMENT_NUMBER,cropPayment.getPaymentNumber());
        contentValues.put(CROP_PAYMENT_NOTES,cropPayment.getNotes());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID,cropPayment.getCustomerId());
        contentValues.put(CROP_PAYMENT_REFERENCE_NO,cropPayment.getReferenceNo());
        contentValues.put(CROP_PAYMENT_INVOICE_ID,cropPayment.getInvoiceId());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID,cropPayment.getCustomerId());
        database.update(CROP_PAYMENT_TABLE_NAME,contentValues,CROP_PAYMENT_ID+" = ?", new String[]{cropPayment.getId()});

        closeDB();

    }
    public boolean deleteCropPayment(String id){
        openDB();
        database.delete(CROP_PAYMENT_TABLE_NAME,CROP_PAYMENT_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropPayment> getCropPayments(String fieldId){
        openDB();
        ArrayList<CropPayment> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_EMPLOYEE_TABLE_NAME+" where "+CROP_EMPLOYEE_USER_ID+" = "+fieldId, null );
        res.moveToFirst();


        while(!res.isAfterLast()){
            CropPayment cropPayment = new CropPayment();
            cropPayment.setId(res.getString(res.getColumnIndex(CROP_PAYMENT_ID)));
            cropPayment.setUserId(res.getString(res.getColumnIndex(CROP_PAYMENT_USER_ID)));
            cropPayment.setAmount(res.getString(res.getColumnIndex(CROP_PAYMENT_AMOUNT)));
            cropPayment.setMode(res.getString(res.getColumnIndex(CROP_PAYMENT_MODE)));
            cropPayment.setDate(res.getString(res.getColumnIndex(CROP_PAYMENT_DATE)));
            cropPayment.setReferenceNo(res.getString(res.getColumnIndex(CROP_PAYMENT_REFERENCE_NO)));
            cropPayment.setPaymentNumber(res.getString(res.getColumnIndex(CROP_PAYMENT_NUMBER)));
            cropPayment.setNotes(res.getString(res.getColumnIndex(CROP_PAYMENT_NOTES)));
            cropPayment.setCustomerId(res.getString(res.getColumnIndex(CROP_PAYMENT_CUSTOMER_ID)));
            cropPayment.setInvoiceId(res.getString(res.getColumnIndex(CROP_PAYMENT_INVOICE_ID)));
            array_list.add(cropPayment);
            res.moveToNext();
        }

        closeDB();
        Log.d("Crop Payment",array_list.toString());
        return array_list;

    }
    public void  insertCropInvoice(CropInvoice invoice){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_INVOICE_USER_ID,invoice.getUserId());
        contentValues.put(CROP_INVOICE_CUSTOMER_ID,invoice.getCustomerId());
        contentValues.put(CROP_INVOICE_NO,invoice.getNumber());
        contentValues.put(CROP_INVOICE_DATE,invoice.getDate());
        contentValues.put(CROP_INVOICE_EXP_DATE,invoice.getExpiryDate());
        contentValues.put(CROP_INVOICE_DISCOUNT,invoice.getDiscount());
        contentValues.put(CROP_INVOICE_SHIPPING_CHARGES,invoice.getShippingCharges());
        contentValues.put(CROP_INVOICE_CUSTOMER_NOTES,invoice.getCustomerNotes());
        contentValues.put(CROP_INVOICE_CUSTOMER_NOTES,invoice.getTermsAndConditions());

        database.insert(CROP_INVOICE_TABLE_NAME,null,contentValues);

        Cursor res =  database.rawQuery( "select "+CROP_INVOICE_ID+" from "+CROP_INVOICE_TABLE_NAME+" where "+CROP_INVOICE_CUSTOMER_ID+" = '"+invoice.getCustomerId()+"' AND "+CROP_INVOICE_NO+" = '"+invoice.getNumber()+"'", null );
        if(!res.isAfterLast()){
            String invoiceId = res.getString(res.getColumnIndex(CROP_INVOICE_ID));

            ArrayList<CropInvoiceItem> items = invoice.getItems();

            for(CropInvoiceItem x: items){
                contentValues.clear();
                contentValues.put(CROP_INVOICE_ITEM_PRODUCT_ID,x.getProductId());
                contentValues.put(CROP_INVOICE_ITEM_INVOICE_ID,invoiceId);
                contentValues.put(CROP_INVOICE_ITEM_QUANTITY,x.getQuantity());
                contentValues.put(CROP_INVOICE_ITEM_TAX,x.getTax());
                contentValues.put(CROP_INVOICE_ITEM_RATE,x.getRate());
                database.insert(CROP_INVOICE_ITEM_TABLE_NAME,null,contentValues);
            }
        }
        closeDB();
    }
    public void  updateCropInvoice(CropInvoice invoice){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_INVOICE_USER_ID,invoice.getUserId());
        contentValues.put(CROP_INVOICE_CUSTOMER_ID,invoice.getCustomerId());
        contentValues.put(CROP_INVOICE_NO,invoice.getNumber());
        contentValues.put(CROP_INVOICE_DATE,invoice.getDate());
        contentValues.put(CROP_INVOICE_EXP_DATE,invoice.getExpiryDate());
        contentValues.put(CROP_INVOICE_DISCOUNT,invoice.getDiscount());
        contentValues.put(CROP_INVOICE_SHIPPING_CHARGES,invoice.getShippingCharges());
        contentValues.put(CROP_INVOICE_CUSTOMER_NOTES,invoice.getCustomerNotes());
        contentValues.put(CROP_INVOICE_CUSTOMER_NOTES,invoice.getTermsAndConditions());

        database.update(CROP_INVOICE_TABLE_NAME,contentValues,CROP_INVOICE_ID+" = ?", new String[]{invoice.getId()});

        String invoiceId = invoice.getId();

        ArrayList<CropInvoiceItem> items = invoice.getItems();

        for(CropInvoiceItem x: items){
            contentValues.clear();
            if(x.getId()==null){
                contentValues.put(CROP_INVOICE_ITEM_PRODUCT_ID,x.getProductId());
                contentValues.put(CROP_INVOICE_ITEM_INVOICE_ID,invoiceId);
                contentValues.put(CROP_INVOICE_ITEM_QUANTITY,x.getQuantity());
                contentValues.put(CROP_INVOICE_ITEM_TAX,x.getTax());
                contentValues.put(CROP_INVOICE_ITEM_RATE,x.getRate());
                database.update(CROP_INVOICE_ITEM_TABLE_NAME,contentValues,CROP_INVOICE_ITEM_ID+" = ?", new String[]{x.getId()});
            }

        }
        closeDB();
    }
    public boolean deleteCropInvoice(String id){
        openDB();
        database.delete(CROP_INVOICE_ITEM_TABLE_NAME,CROP_INVOICE_ITEM_INVOICE_ID+" = ?", new String[]{id});
        database.delete(CROP_INVOICE_TABLE_NAME,CROP_INVOICE_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropInvoice> getCropInvoices(String userId){
        openDB();
        ArrayList<CropInvoice> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_INVOICE_TABLE_NAME+" where "+CROP_INVOICE_USER_ID+" = "+ userId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropInvoice cropInvoice = new CropInvoice();
            cropInvoice.setId(res.getString(res.getColumnIndex(CROP_INVOICE_ID)));
            cropInvoice.setUserId(res.getString(res.getColumnIndex(CROP_INVOICE_USER_ID)));
            cropInvoice.setCustomerId(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_ID)));
            cropInvoice.setNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            cropInvoice.setDate(res.getString(res.getColumnIndex(CROP_INVOICE_DATE)));
            cropInvoice.setExpiryDate(res.getString(res.getColumnIndex(CROP_INVOICE_EXP_DATE)));
            cropInvoice.setDiscount(res.getFloat(res.getColumnIndex(CROP_INVOICE_DISCOUNT)));
            cropInvoice.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_INVOICE_SHIPPING_CHARGES)));
            cropInvoice.setCustomerNotes(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_NOTES)));
            cropInvoice.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS_AND_CONDITIONS)));
            array_list.add(cropInvoice);
            res.moveToNext();
        }


        for(CropInvoice invoice: array_list){
            ArrayList<CropInvoiceItem> items_list = new ArrayList();
            res = db.rawQuery( "select * from "+CROP_INVOICE_ITEM_TABLE_NAME+" where "+CROP_INVOICE_ITEM_INVOICE_ID+" = "+ invoice.getId(), null );
            res.moveToFirst();
            while(!res.isAfterLast()) {
                CropInvoiceItem item = new CropInvoiceItem();
                item.setId(res.getString(res.getColumnIndex(CROP_INVOICE_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_INVOICE_ITEM_PRODUCT_ID)));
                item.setInvoiceOrEstimateId(res.getString(res.getColumnIndex(CROP_INVOICE_ITEM_INVOICE_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVOICE_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_INVOICE_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_INVOICE_ITEM_RATE)));
                items_list.add(item);
            }
            invoice.setItems(items_list);
        }

        closeDB();
        Log.d("Crop Product",array_list.toString());
        return array_list;
    }
    public void  insertCropEstimate(CropEstimate estimate){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_ESTIMATE_USER_ID,estimate.getUserId());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_ID,estimate.getCustomerId());
        contentValues.put(CROP_ESTIMATE_NO,estimate.getNumber());
        contentValues.put(CROP_ESTIMATE_DATE,estimate.getDate());
        contentValues.put(CROP_ESTIMATE_EXP_DATE,estimate.getExpiryDate());
        contentValues.put(CROP_ESTIMATE_DISCOUNT,estimate.getDiscount());
        contentValues.put(CROP_ESTIMATE_SHIPPING_CHARGES,estimate.getShippingCharges());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_NOTES,estimate.getCustomerNotes());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_NOTES,estimate.getTermsAndConditions());

        database.insert(CROP_ESTIMATE_TABLE_NAME,null,contentValues);

        Cursor res =  database.rawQuery( "select "+CROP_ESTIMATE_ID+" from "+CROP_ESTIMATE_TABLE_NAME+" where "+CROP_ESTIMATE_CUSTOMER_ID+" = '"+estimate.getCustomerId()+"' AND "+CROP_ESTIMATE_NO+" = '"+estimate.getNumber()+"'", null );
        if(!res.isAfterLast()){
            String estimateId = res.getString(res.getColumnIndex(CROP_ESTIMATE_ID));

            ArrayList<CropEstimateItem> items = estimate.getItems();

            for(CropEstimateItem x: items){
                contentValues.clear();
                contentValues.put(CROP_ESTIMATE_ITEM_PRODUCT_ID,x.getProductId());
                contentValues.put(CROP_ESTIMATE_ITEM_ESTIMATE_ID,estimateId);
                contentValues.put(CROP_ESTIMATE_ITEM_QUANTITY,x.getQuantity());
                contentValues.put(CROP_ESTIMATE_ITEM_TAX,x.getTax());
                contentValues.put(CROP_ESTIMATE_ITEM_RATE,x.getRate());
                database.insert(CROP_ESTIMATE_ITEM_TABLE_NAME,null,contentValues);
            }
        }
        closeDB();
    }
    public String getNextEstimateNumber(){
        openDB();
        Cursor res =  database.rawQuery( "select "+CROP_ESTIMATE_ID+" from "+CROP_ESTIMATE_TABLE_NAME+" ORDER BY "+CROP_ESTIMATE_ID+" DESC LIMIT 1",null);
        int lastId = 0;
        if(!res.isAfterLast()){
            lastId = res.getInt(res.getColumnIndex(CROP_ESTIMATE_ID));
        }
        int id=lastId+1;
        closeDB();
        return "EST-"+id;
    }
    public void  updateCropEstimate(CropEstimate estimate){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_ESTIMATE_USER_ID,estimate.getUserId());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_ID,estimate.getCustomerId());
        contentValues.put(CROP_ESTIMATE_NO,estimate.getNumber());
        contentValues.put(CROP_ESTIMATE_DATE,estimate.getDate());
        contentValues.put(CROP_ESTIMATE_EXP_DATE,estimate.getExpiryDate());
        contentValues.put(CROP_ESTIMATE_DISCOUNT,estimate.getDiscount());
        contentValues.put(CROP_ESTIMATE_SHIPPING_CHARGES,estimate.getShippingCharges());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_NOTES,estimate.getCustomerNotes());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_NOTES,estimate.getTermsAndConditions());

        database.update(CROP_ESTIMATE_TABLE_NAME,contentValues,CROP_ESTIMATE_ID+" = ?", new String[]{estimate.getId()});

        String estimateId = estimate.getId();

        ArrayList<CropEstimateItem> items = estimate.getItems();

        for(CropEstimateItem x: items){
            contentValues.clear();
            if(x.getId()==null){
                contentValues.put(CROP_ESTIMATE_ITEM_PRODUCT_ID,x.getProductId());
                contentValues.put(CROP_ESTIMATE_ITEM_ESTIMATE_ID,estimateId);
                contentValues.put(CROP_ESTIMATE_ITEM_QUANTITY,x.getQuantity());
                contentValues.put(CROP_ESTIMATE_ITEM_TAX,x.getTax());
                contentValues.put(CROP_ESTIMATE_ITEM_RATE,x.getRate());
                database.update(CROP_ESTIMATE_ITEM_TABLE_NAME,contentValues,CROP_ESTIMATE_ITEM_ID+" = ?", new String[]{x.getId()});
            }

        }
        closeDB();
    }
    public boolean deleteCropEstimate(String id){
        openDB();
        database.delete(CROP_ESTIMATE_ITEM_TABLE_NAME,CROP_ESTIMATE_ITEM_ESTIMATE_ID+" = ?", new String[]{id});
        database.delete(CROP_ESTIMATE_TABLE_NAME,CROP_ESTIMATE_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropEstimate> getCropEstimates(String userId){
        openDB();
        ArrayList<CropEstimate> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_ESTIMATE_TABLE_NAME+" where "+CROP_ESTIMATE_USER_ID+" = "+ userId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropEstimate cropEstimate = new CropEstimate();
            cropEstimate.setId(res.getString(res.getColumnIndex(CROP_ESTIMATE_ID)));
            cropEstimate.setUserId(res.getString(res.getColumnIndex(CROP_ESTIMATE_USER_ID)));
            cropEstimate.setCustomerId(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_ID)));
            cropEstimate.setNumber(res.getString(res.getColumnIndex(CROP_ESTIMATE_NO)));
            cropEstimate.setDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_DATE)));
            cropEstimate.setExpiryDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_EXP_DATE)));
            cropEstimate.setDiscount(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_DISCOUNT)));
            cropEstimate.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_SHIPPING_CHARGES)));
            cropEstimate.setCustomerNotes(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_NOTES)));
            cropEstimate.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_ESTIMATE_TERMS_AND_CONDITIONS)));
            array_list.add(cropEstimate);
            res.moveToNext();
        }


        for(CropEstimate estimate: array_list){
            ArrayList<CropEstimateItem> items_list = new ArrayList();
            res = db.rawQuery( "select * from "+CROP_ESTIMATE_ITEM_TABLE_NAME+" where "+CROP_ESTIMATE_ITEM_ESTIMATE_ID+" = "+ estimate.getId(), null );
            res.moveToFirst();
            while(!res.isAfterLast()) {
                CropEstimateItem item = new CropEstimateItem();
                item.setId(res.getString(res.getColumnIndex(CROP_ESTIMATE_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_ESTIMATE_ITEM_PRODUCT_ID)));
                item.setInvoiceOrEstimateId(res.getString(res.getColumnIndex(CROP_ESTIMATE_ITEM_ESTIMATE_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_ITEM_RATE)));
                items_list.add(item);
            }
            estimate.setItems(items_list);
        }

        closeDB();
        Log.d("Crop Product",array_list.toString());
        return array_list;
    }
    public void  insertCropProduct(CropProduct cropProduct){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PRODUCT_USER_ID,cropProduct.getUserId());
        contentValues.put(CROP_PRODUCT_NAME,cropProduct.getName());
        contentValues.put(CROP_PRODUCT_TYPE,cropProduct.getType());
        contentValues.put(CROP_PRODUCT_CODE,cropProduct.getCode());
        contentValues.put(CROP_PRODUCT_UNITS,cropProduct.getUnits());
        contentValues.put(CROP_PRODUCT_LINKED_ACCOUNT,cropProduct.getLinkedAccount());
        contentValues.put(CROP_PRODUCT_OPENING_COST,cropProduct.getOpeningCost());
        contentValues.put(CROP_PRODUCT_OPENING_QUANTITY,cropProduct.getOpeningQuantity());
        contentValues.put(CROP_PRODUCT_SELLING_PRICE,cropProduct.getSellingPrice());
        contentValues.put(CROP_PRODUCT_TAX_RATE,cropProduct.getTaxRate());
        contentValues.put(CROP_PRODUCT_DESCRIPTION,cropProduct.getDescription());
        database.insert(CROP_PRODUCT_TABLE_NAME,null,contentValues);

        closeDB();
    }
    public void  updateCropProduct(CropProduct cropProduct){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PRODUCT_USER_ID,cropProduct.getUserId());
        contentValues.put(CROP_PRODUCT_NAME,cropProduct.getName());
        contentValues.put(CROP_PRODUCT_TYPE,cropProduct.getType());
        contentValues.put(CROP_PRODUCT_CODE,cropProduct.getCode());
        contentValues.put(CROP_PRODUCT_UNITS,cropProduct.getUnits());
        contentValues.put(CROP_PRODUCT_LINKED_ACCOUNT,cropProduct.getLinkedAccount());
        contentValues.put(CROP_PRODUCT_OPENING_COST,cropProduct.getOpeningCost());
        contentValues.put(CROP_PRODUCT_OPENING_QUANTITY,cropProduct.getOpeningQuantity());
        contentValues.put(CROP_PRODUCT_SELLING_PRICE,cropProduct.getSellingPrice());
        contentValues.put(CROP_PRODUCT_TAX_RATE,cropProduct.getTaxRate());
        contentValues.put(CROP_PRODUCT_DESCRIPTION,cropProduct.getDescription());
        database.update(CROP_PRODUCT_TABLE_NAME,contentValues,CROP_PRODUCT_ID+" = ?", new String[]{cropProduct.getId()});

        closeDB();

    }
    public boolean deleteCropProduct(String id){
        openDB();
        database.delete(CROP_PRODUCT_TABLE_NAME,CROP_PRODUCT_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropProduct> getCropProducts(String fieldId){
        openDB();
        ArrayList<CropProduct> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_PRODUCT_TABLE_NAME/*+" where "+CROP_PRODUCT_USER_ID+" = "+fieldId*/, null );
        res.moveToFirst();


        while(!res.isAfterLast()){
            CropProduct cropSupplier = new CropProduct();
            cropSupplier.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ID)));
            cropSupplier.setUserId(res.getString(res.getColumnIndex(CROP_PRODUCT_USER_ID)));
            cropSupplier.setName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            cropSupplier.setType(res.getString(res.getColumnIndex(CROP_PRODUCT_TYPE)));
            cropSupplier.setCode(res.getString(res.getColumnIndex(CROP_PRODUCT_CODE)));
            cropSupplier.setUnits(res.getString(res.getColumnIndex(CROP_PRODUCT_UNITS)));
            cropSupplier.setLinkedAccount(res.getString(res.getColumnIndex(CROP_PRODUCT_LINKED_ACCOUNT)));
            cropSupplier.setOpeningCost(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_COST)));
            cropSupplier.setOpeningQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_QUANTITY)));
            cropSupplier.setSellingPrice(res.getFloat(res.getColumnIndex(CROP_PRODUCT_SELLING_PRICE)));
            cropSupplier.setTaxRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_TAX_RATE)));
            cropSupplier.setDescription(res.getString(res.getColumnIndex(CROP_PRODUCT_DESCRIPTION)));
            array_list.add(cropSupplier);
            res.moveToNext();
        }

        closeDB();
        Log.d("Crop Product",array_list.toString());
        return array_list;

    }
    public void  insertCropSupplier(CropSupplier spraying){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SUPPLIER_USER_ID,spraying.getUserId());
        contentValues.put(CROP_SUPPLIER_NAME,spraying.getName());
        contentValues.put(CROP_SUPPLIER_COMPANY,spraying.getCompany());
        contentValues.put(CROP_SUPPLIER_TAX_REG_NO,spraying.getTaxRegNo());
        contentValues.put(CROP_SUPPLIER_PHONE,spraying.getPhone());
        contentValues.put(CROP_SUPPLIER_MOBILE,spraying.getMobile());
        contentValues.put(CROP_SUPPLIER_EMAIL,spraying.getEmail());
        contentValues.put(CROP_SUPPLIER_OPENING_BALANCE,spraying.getOpeningBalance());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_STREET,spraying.getInvoiceStreet());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_CITY,spraying.getInvoiceCityOrTown());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_COUNTRY,spraying.getInvoiceCountry());
        database.insert(CROP_SUPPLIER_TABLE_NAME,null,contentValues);

        closeDB();
    }
    public void  updateCropSupplier(CropSupplier customer){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SUPPLIER_USER_ID,customer.getUserId());
        contentValues.put(CROP_SUPPLIER_NAME,customer.getName());
        contentValues.put(CROP_SUPPLIER_COMPANY,customer.getCompany());
        contentValues.put(CROP_SUPPLIER_TAX_REG_NO,customer.getTaxRegNo());
        contentValues.put(CROP_SUPPLIER_PHONE,customer.getPhone());
        contentValues.put(CROP_SUPPLIER_MOBILE,customer.getMobile());
        contentValues.put(CROP_SUPPLIER_EMAIL,customer.getEmail());
        contentValues.put(CROP_SUPPLIER_OPENING_BALANCE,customer.getOpeningBalance());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_STREET,customer.getInvoiceStreet());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_CITY,customer.getInvoiceCityOrTown());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_COUNTRY,customer.getInvoiceCountry());
        database.update(CROP_SUPPLIER_TABLE_NAME,contentValues,CROP_SUPPLIER_ID+" = ?", new String[]{customer.getId()});
        closeDB();
    }
    public boolean deleteCropSupplier(String id){
        openDB();
        database.delete(CROP_SUPPLIER_TABLE_NAME,CROP_SUPPLIER_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropSupplier> getCropSuppliers(String fieldId){
        openDB();
        ArrayList<CropSupplier> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_SUPPLIER_TABLE_NAME+" where "+CROP_SUPPLIER_USER_ID+" = "+fieldId, null );
        res.moveToFirst();


        while(!res.isAfterLast()){
            CropSupplier cropSupplier = new CropSupplier();
            cropSupplier.setId(res.getString(res.getColumnIndex(CROP_SUPPLIER_ID)));
            cropSupplier.setUserId(res.getString(res.getColumnIndex(CROP_SUPPLIER_USER_ID)));
            cropSupplier.setName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            cropSupplier.setCompany(res.getString(res.getColumnIndex(CROP_SUPPLIER_COMPANY)));
            cropSupplier.setTaxRegNo(res.getString(res.getColumnIndex(CROP_SUPPLIER_TAX_REG_NO)));
            cropSupplier.setPhone(res.getString(res.getColumnIndex(CROP_SUPPLIER_PHONE)));
            cropSupplier.setMobile(res.getString(res.getColumnIndex(CROP_SUPPLIER_MOBILE)));
            cropSupplier.setEmail(res.getString(res.getColumnIndex(CROP_SUPPLIER_EMAIL)));
            cropSupplier.setOpeningBalance(res.getFloat(res.getColumnIndex(CROP_SUPPLIER_OPENING_BALANCE)));
            cropSupplier.setInvoiceStreet(res.getString(res.getColumnIndex(CROP_SUPPLIER_INVOICE_ADDRESS_STREET)));
            cropSupplier.setInvoiceCityOrTown(res.getString(res.getColumnIndex(CROP_SUPPLIER_INVOICE_ADDRESS_CITY)));
            cropSupplier.setInvoiceCountry(res.getString(res.getColumnIndex(CROP_SUPPLIER_INVOICE_ADDRESS_COUNTRY)));
            array_list.add(cropSupplier);
            res.moveToNext();
        }

        closeDB();
        Log.d("Crop Analysis",array_list.toString());
        return array_list;

    }
    public void  insertCropCustomer(CropCustomer spraying){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CUSTOMER_USER_ID,spraying.getUserId());
        contentValues.put(CROP_CUSTOMER_NAME,spraying.getName());
        contentValues.put(CROP_CUSTOMER_COMPANY,spraying.getCompany());
        contentValues.put(CROP_CUSTOMER_TAX_REG_NO,spraying.getTaxRegNo());
        contentValues.put(CROP_CUSTOMER_PHONE,spraying.getPhone());
        contentValues.put(CROP_CUSTOMER_MOBILE,spraying.getMobile());
        contentValues.put(CROP_CUSTOMER_EMAIL,spraying.getEmail());
        contentValues.put(CROP_CUSTOMER_OPENING_BALANCE,spraying.getOpeningBalance());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_STREET,spraying.getBillingStreet());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_CITY,spraying.getBillingCityOrTown());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_COUNTRY,spraying.getBillingCountry());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_STREET,spraying.getShippingStreet());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_CITY,spraying.getShippingCityOrTown());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY,spraying.getShippingCountry());
        database.insert(CROP_CUSTOMER_TABLE_NAME,null,contentValues);

        closeDB();
    }
    public void  updateCropCustomer(CropCustomer customer){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CUSTOMER_USER_ID,customer.getUserId());
        contentValues.put(CROP_CUSTOMER_NAME,customer.getName());
        contentValues.put(CROP_CUSTOMER_COMPANY,customer.getCompany());
        contentValues.put(CROP_CUSTOMER_TAX_REG_NO,customer.getTaxRegNo());
        contentValues.put(CROP_CUSTOMER_PHONE,customer.getPhone());
        contentValues.put(CROP_CUSTOMER_MOBILE,customer.getMobile());
        contentValues.put(CROP_CUSTOMER_EMAIL,customer.getEmail());
        contentValues.put(CROP_CUSTOMER_OPENING_BALANCE,customer.getOpeningBalance());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_STREET,customer.getBillingStreet());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_CITY,customer.getBillingCityOrTown());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_COUNTRY,customer.getBillingCountry());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_STREET,customer.getShippingStreet());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_CITY,customer.getShippingCityOrTown());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY,customer.getShippingCountry());
        database.update(CROP_CUSTOMER_TABLE_NAME,contentValues,CROP_CUSTOMER_ID+" = ?", new String[]{customer.getId()});
        closeDB();
    }
    public boolean deleteCropCustomer(String id){
        openDB();
        database.delete(CROP_CUSTOMER_TABLE_NAME,CROP_CUSTOMER_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropCustomer> getCropCustomers(String fieldId){
        openDB();
        ArrayList<CropCustomer> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_CUSTOMER_TABLE_NAME+" where "+CROP_CUSTOMER_USER_ID+" = "+fieldId, null );
        res.moveToFirst();


        while(!res.isAfterLast()){
            CropCustomer cropEmployee = new CropCustomer();
            cropEmployee.setId(res.getString(res.getColumnIndex(CROP_CUSTOMER_ID)));
            cropEmployee.setUserId(res.getString(res.getColumnIndex(CROP_CUSTOMER_USER_ID)));
            cropEmployee.setName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropEmployee.setCompany(res.getString(res.getColumnIndex(CROP_CUSTOMER_COMPANY)));
            cropEmployee.setTaxRegNo(res.getString(res.getColumnIndex(CROP_CUSTOMER_TAX_REG_NO)));
            cropEmployee.setPhone(res.getString(res.getColumnIndex(CROP_CUSTOMER_PHONE)));
            cropEmployee.setMobile(res.getString(res.getColumnIndex(CROP_CUSTOMER_MOBILE)));
            cropEmployee.setEmail(res.getString(res.getColumnIndex(CROP_CUSTOMER_EMAIL)));
            cropEmployee.setOpeningBalance(res.getFloat(res.getColumnIndex(CROP_CUSTOMER_OPENING_BALANCE)));
            cropEmployee.setBillingStreet(res.getString(res.getColumnIndex(CROP_CUSTOMER_BILL_ADDRESS_STREET)));
            cropEmployee.setBillingCityOrTown(res.getString(res.getColumnIndex(CROP_CUSTOMER_BILL_ADDRESS_CITY)));
            cropEmployee.setBillingCountry(res.getString(res.getColumnIndex(CROP_CUSTOMER_BILL_ADDRESS_COUNTRY)));
            cropEmployee.setShippingStreet(res.getString(res.getColumnIndex(CROP_CUSTOMER_SHIP_ADDRESS_STREET)));
            cropEmployee.setShippingCityOrTown(res.getString(res.getColumnIndex(CROP_CUSTOMER_SHIP_ADDRESS_CITY)));
            cropEmployee.setShippingCountry(res.getString(res.getColumnIndex(CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY)));
            array_list.add(cropEmployee);
            res.moveToNext();
        }

        closeDB();
        Log.d("Crop Analysis",array_list.toString());
        return array_list;

    }
    public void  insertCropEmployee(CropEmployee spraying){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_EMPLOYEE_USER_ID,spraying.getUserId());
        contentValues.put(CROP_EMPLOYEE_TITLE,spraying.getTitle());
        contentValues.put(CROP_EMPLOYEE_FIRST_NAME,spraying.getFirstName());
        contentValues.put(CROP_EMPLOYEE_LAST_NAME,spraying.getLastName());
        contentValues.put(CROP_EMPLOYEE_PHONE,spraying.getPhone());
        contentValues.put(CROP_EMPLOYEE_MOBILE,spraying.getMobile());
        contentValues.put(CROP_EMPLOYEE_EMP_ID,spraying.getEmployeeId());
        contentValues.put(CROP_EMPLOYEE_GENDER,spraying.getGender());
        contentValues.put(CROP_EMPLOYEE_ADDRESS,spraying.getAddress());
        contentValues.put(CROP_EMPLOYEE_EMAIL,spraying.getEmail());
        contentValues.put(CROP_EMPLOYEE_DOB,spraying.getDateOfBirth());
        contentValues.put(CROP_EMPLOYEE_HIRE_DATE,spraying.getHireDate());
        contentValues.put(CROP_EMPLOYEE_EMPLOYMENT_STATUS,spraying.getEmploymentStatus());
        contentValues.put(CROP_EMPLOYEE_PAY_AMOUNT,spraying.getPayAmount());
        contentValues.put(CROP_EMPLOYEE_PAY_RATE,spraying.getPayRate());
        contentValues.put(CROP_EMPLOYEE_PAY_TYPE,spraying.getPayType());
        contentValues.put(CROP_EMPLOYEE_SUPERVISOR,spraying.getSupervisor());
        database.insert(CROP_EMPLOYEE_TABLE_NAME,null,contentValues);
        /*
        public static final String  ="id";

         */
        closeDB();
    }
    public void  updateCropEmployee(CropEmployee s){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_EMPLOYEE_USER_ID,s.getUserId());
        contentValues.put(CROP_EMPLOYEE_TITLE,s.getTitle());
        contentValues.put(CROP_EMPLOYEE_FIRST_NAME,s.getFirstName());
        contentValues.put(CROP_EMPLOYEE_LAST_NAME,s.getLastName());
        contentValues.put(CROP_EMPLOYEE_PHONE,s.getPhone());
        contentValues.put(CROP_EMPLOYEE_MOBILE,s.getMobile());
        contentValues.put(CROP_EMPLOYEE_EMP_ID,s.getEmployeeId());
        contentValues.put(CROP_EMPLOYEE_GENDER,s.getGender());
        contentValues.put(CROP_EMPLOYEE_ADDRESS,s.getAddress());
        contentValues.put(CROP_EMPLOYEE_EMAIL,s.getEmail());
        contentValues.put(CROP_EMPLOYEE_DOB,s.getDateOfBirth());
        contentValues.put(CROP_EMPLOYEE_HIRE_DATE,s.getHireDate());
        contentValues.put(CROP_EMPLOYEE_EMPLOYMENT_STATUS,s.getEmploymentStatus());
        contentValues.put(CROP_EMPLOYEE_PAY_AMOUNT,s.getPayAmount());
        contentValues.put(CROP_EMPLOYEE_PAY_RATE,s.getPayRate());
        contentValues.put(CROP_EMPLOYEE_PAY_TYPE,s.getPayType());
        contentValues.put(CROP_EMPLOYEE_SUPERVISOR,s.getSupervisor());
        database.update(CROP_EMPLOYEE_TABLE_NAME,contentValues,CROP_EMPLOYEE_ID+" = ?", new String[]{s.getId()});
        closeDB();
    }
    public boolean deleteCropEmployee(String id){
        openDB();
        database.delete(CROP_EMPLOYEE_TABLE_NAME,CROP_EMPLOYEE_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropEmployee> getCropEmployee(String fieldId){
        openDB();
        ArrayList<CropEmployee> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_EMPLOYEE_TABLE_NAME+" where "+CROP_EMPLOYEE_USER_ID+" = "+fieldId, null );
        res.moveToFirst();


        while(!res.isAfterLast()){
            CropEmployee cropEmployee = new CropEmployee();
            cropEmployee.setId(res.getString(res.getColumnIndex(CROP_EMPLOYEE_ID)));
            cropEmployee.setUserId(res.getString(res.getColumnIndex(CROP_EMPLOYEE_USER_ID)));
            cropEmployee.setTitle(res.getString(res.getColumnIndex(CROP_EMPLOYEE_TITLE)));
            cropEmployee.setFirstName(res.getString(res.getColumnIndex(CROP_EMPLOYEE_FIRST_NAME)));
            cropEmployee.setLastName(res.getString(res.getColumnIndex(CROP_EMPLOYEE_LAST_NAME)));
            cropEmployee.setPhone(res.getString(res.getColumnIndex(CROP_EMPLOYEE_PHONE)));
            cropEmployee.setMobile(res.getString(res.getColumnIndex(CROP_EMPLOYEE_MOBILE)));
            cropEmployee.setEmployeeId(res.getString(res.getColumnIndex(CROP_EMPLOYEE_EMP_ID)));
            cropEmployee.setGender(res.getString(res.getColumnIndex(CROP_EMPLOYEE_GENDER)));
            cropEmployee.setAddress(res.getString(res.getColumnIndex(CROP_EMPLOYEE_ADDRESS)));
            cropEmployee.setEmail(res.getString(res.getColumnIndex(CROP_EMPLOYEE_EMAIL)));
            cropEmployee.setDateOfBirth(res.getString(res.getColumnIndex(CROP_EMPLOYEE_DOB)));
            cropEmployee.setHireDate(res.getString(res.getColumnIndex(CROP_EMPLOYEE_HIRE_DATE)));
            cropEmployee.setEmploymentStatus(res.getString(res.getColumnIndex(CROP_EMPLOYEE_EMPLOYMENT_STATUS)));
            cropEmployee.setPayAmount(res.getFloat(res.getColumnIndex(CROP_EMPLOYEE_PAY_AMOUNT)));
            cropEmployee.setPayRate(res.getString(res.getColumnIndex(CROP_EMPLOYEE_PAY_RATE)));
            cropEmployee.setPayType(res.getString(res.getColumnIndex(CROP_EMPLOYEE_PAY_TYPE)));
            cropEmployee.setSupervisor(res.getString(res.getColumnIndex(CROP_EMPLOYEE_SUPERVISOR)));
            array_list.add(cropEmployee);
            res.moveToNext();
        }

        closeDB();
        Log.d("Crop Analysis",array_list.toString());
        return array_list;

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
            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
        Log.d("Crop Analysis",array_list.toString());
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
        database.delete(CROP_SPRAYING_TABLE_NAME,CROP_SPRAYING_ID+" = ?", new String[]{fertilizerId});
        closeDB();
        return true;
    }
    public ArrayList<CropSpraying> getCropSprayings(String cropId){
        openDB();
        ArrayList<CropSpraying> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String query ="select "+CROP_SPRAYING_TABLE_NAME+".*,"+CROP_INVENTORY_SPRAY_TABLE_NAME+"."+CROP_INVENTORY_SPRAY_NAME+" from "+CROP_SPRAYING_TABLE_NAME+" LEFT JOIN "+CROP_INVENTORY_SPRAY_TABLE_NAME+" ON "+CROP_SPRAYING_TABLE_NAME+"."+CROP_SPRAYING_SPRAY_ID+" = "+CROP_INVENTORY_SPRAY_TABLE_NAME+"."+CROP_INVENTORY_SPRAY_ID+" where "+CROP_SPRAYING_CROP_ID+" = "+cropId ;
        Log.d("QUERY",query);
        Cursor res =  db.rawQuery( query,null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropSpraying crop = new CropSpraying();
            Log.d("COLUMN",res.getColumnIndex(CROP_SPRAYING_START_TIME)+"");
            crop.setId(res.getString(res.getColumnIndex(CROP_SPRAYING_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_SPRAYING_USER_ID)));
            crop.setDate(res.getString(res.getColumnIndex(CROP_SPRAYING_DATE)));
            crop.setCropId(res.getString(res.getColumnIndex(CROP_SPRAYING_CROP_ID)));
            crop.setStartTime(res.getString(res.getColumnIndex(CROP_SPRAYING_START_TIME)));
            crop.setEndTime(res.getString(res.getColumnIndex(CROP_SPRAYING_END_TIME)));
            crop.setCost(res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            crop.setOperator(res.getString(res.getColumnIndex(CROP_SPRAYING_OPERATOR)));
            crop.setWaterCondition(res.getString(res.getColumnIndex(CROP_SPRAYING_WATER_CONDITION)));
            crop.setWaterVolume(res.getFloat(res.getColumnIndex(CROP_SPRAYING_WATER_VOLUME)));
            crop.setWindDirection(res.getString(res.getColumnIndex(CROP_SPRAYING_WIND_DIRECTION)));
            crop.setTreatmentReason(res.getString(res.getColumnIndex(CROP_SPRAYING_TREATMENT_REASON)));
            crop.setEquipmentUsed(res.getString(res.getColumnIndex(CROP_SPRAYING_EQUIPMENT_USED)));
            crop.setSprayId(res.getString(res.getColumnIndex(CROP_SPRAYING_SPRAY_ID)));
            crop.setSprayName(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_NAME)));
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
        contentValues.put(CROP_FERTILIZER_APPLICATION_RATE,fertilizerApplication.getRate());
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
        contentValues.put(CROP_FERTILIZER_APPLICATION_RATE,fertilizerApplication.getRate());
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
        //Cursor res =  db.rawQuery( "select * from "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+" where "+CROP_FERTILIZER_APPLICATION_CROP_ID+" = "+cropId, null );

        String query ="select "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+".*,"+CROP_INVENTORY_FERTILIZER_TABLE_NAME+"."+CROP_INVENTORY_FERTILIZER_NAME+" from "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+" LEFT JOIN "+CROP_INVENTORY_FERTILIZER_TABLE_NAME+" ON "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+"."+CROP_FERTILIZER_APPLICATION_ID+" = "+CROP_INVENTORY_FERTILIZER_TABLE_NAME+"."+CROP_INVENTORY_FERTILIZER_ID+" where "+CROP_FERTILIZER_APPLICATION_CROP_ID+" = "+cropId ;

        Cursor res =  db.rawQuery(query,null);
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
            crop.setFertilizerName(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_NAME)));

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
            crop.setNotes(res.getString(res.getColumnIndex(CROP_CULTIVATION_NOTES)));

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
