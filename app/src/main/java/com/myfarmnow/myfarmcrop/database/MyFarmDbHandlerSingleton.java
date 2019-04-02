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
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;

import com.myfarmnow.myfarmcrop.models.CropField;

import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropMachine;
import com.myfarmnow.myfarmcrop.models.CropPayment;
import com.myfarmnow.myfarmcrop.models.CropPaymentBill;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropPurchaseOrder;
import com.myfarmnow.myfarmcrop.models.CropSalesOrder;
import com.myfarmnow.myfarmcrop.models.CropSoilAnalysis;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
import com.myfarmnow.myfarmcrop.models.CropSupplier;
import com.myfarmnow.myfarmcrop.models.CropTask;
import com.myfarmnow.myfarmcrop.singletons.CropDatabaseInitializerSingleton;

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
    public static final String CROP_PRODUCT_ITEM_TABLE_NAME ="crop_product_items";
    public static final String CROP_INVOICE_TABLE_NAME ="crop_invoice";
    public static final String CROP_INCOME_EXPENSE_TABLE_NAME ="crop_income_expense";
    public static final String CROP_PAYMENT_TABLE_NAME ="crop_payments";
    public static final String CROP_TASK_TABLE_NAME ="crop_task";
    public static final String CROP_SALES_ORDER_TABLE_NAME ="crop_sales_order";
    public static final String CROP_PURCHASE_ORDER_TABLE_NAME ="crop_purchase_order";
    public static final String CROP_PAYMENT_BILL_TABLE_NAME ="crop_payment_bill";
    public static final String CROP_ITEM_TABLE_NAME ="crop_item";
    public static final String CROP_FERTILIZER_TABLE_NAME ="crop_fertilizer";



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
    public static final String CROP_CROP_SEASON ="season";

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
    public static final String CROP_ESTIMATE_REFERENCE_NO ="referenceNo";
    public static final String CROP_ESTIMATE_DISCOUNT ="discount";
    public static final String CROP_ESTIMATE_SHIPPING_CHARGES ="shippingCharges";
    public static final String CROP_ESTIMATE_CUSTOMER_NOTES ="customerNotes";
    public static final String CROP_ESTIMATE_TERMS_AND_CONDITIONS ="termsAndConditions";
    public static final String CROP_ESTIMATE_STATUS ="status";


    public static final String CROP_PRODUCT_ITEM_ID ="id";
    public static final String CROP_PRODUCT_ITEM_PRODUCT_ID ="productId";
    public static final String CROP_PRODUCT_ITEM_ESTIMATE_ID ="estimateId";
    public static final String CROP_PRODUCT_ITEM_QUANTITY="quantity";
    public static final String CROP_PRODUCT_ITEM_RATE="rate";
    public static final String CROP_PRODUCT_ITEM_TAX="tax";
    public static final String CROP_PRODUCT_ITEM_TYPE="type";

    public static final String CROP_INVOICE_ID ="id";
    public static final String CROP_INVOICE_USER_ID ="userId";
    public static final String CROP_INVOICE_CUSTOMER_ID ="customerId";
    public static final String CROP_INVOICE_NO ="number";
    public static final String CROP_INVOICE_DATE ="date";
    public static final String CROP_INVOICE_ORDER_NUMBER ="orderNumber";
    public static final String CROP_INVOICE_TERMS ="terms";
    public static final String CROP_INVOICE_DUE_DATE ="dueDate";
    public static final String CROP_INVOICE_DISCOUNT ="discount";
    public static final String CROP_INVOICE_SHIPPING_CHARGES ="shippingCharges";
    public static final String CROP_INVOICE_CUSTOMER_NOTES ="customerNotes";
    public static final String CROP_INVOICE_TERMS_AND_CONDITIONS ="termsAndConditions";




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

    public static final String CROP_SALES_ORDER_ID ="id";
    public static final String CROP_SALES_ORDER_USER_ID ="userId";
    public static final String CROP_SALES_ORDER_CUSTOMER_ID ="customerId";
    public static final String CROP_SALES_ORDER_NO ="number";
    public static final String CROP_SALES_ORDER_REFERENCE_NO ="referenceNumber";
    public static final String CROP_SALES_ORDER_SHIPPING_METHOD ="shippingMethod";
    public static final String CROP_SALES_ORDER_DATE ="date";
    public static final String CROP_SALES_ORDER_SHIPPING_DATE ="shippingDate";
    public static final String CROP_SALES_ORDER_DISCOUNT ="discount";
    public static final String CROP_SALES_ORDER_SHIPPING_CHARGES ="shippingCharges";
    public static final String CROP_SALES_ORDER_CUSTOMER_NOTES ="customerNotes";
    public static final String CROP_SALES_ORDER_TERMS_AND_CONDITIONS ="termsAndConditions";
    public static final String CROP_SALES_ORDER_STATUS ="status";
    

    

    public static final String CROP_INCOME_EXPENSE_ID = "id";
    public static final String CROP_INCOME_EXPENSE_DATE = "date";
    public static final String CROP_INCOME_EXPENSE_TRANSACTION = "incomeExpenseTransaction";
    public static final String CROP_INCOME_EXPENSE_ITEM = "item";
    public static final String CROP_INCOME_EXPENSE_CATEGORY = "category";
    public static final String CROP_INCOME_EXPENSE_QUANTITY = "quantity";
    public static final String CROP_INCOME_EXPENSE_GROSS_AMOUNT = "grossAmount";
    public static final String CROP_INCOME_EXPENSE_UNIT_PRICE = "unitPrice";
    public static final String CROP_INCOME_EXPENSE_TAXES = "taxes";
    public static final String CROP_INCOME_EXPENSE_PAYMENT_MODE = "paymentMode";
    public static final String CROP_INCOME_EXPENSE_PAYMENT_STATUS = "paymentStatus";
    public static final String CROP_INCOME_EXPENSE_SELLING_PRICE = "sellingPrice";
    public static final String CROP_INCOME_EXPENSE_CUSTOMER_SUPPLIER = "customerSupplier";
    public static final String CROP_INCOME_EXPENSE_USER_ID = "userId";
    public static final String CROP_INCOME_EXPENSE_CROP_ID = "cropId";

    public static final String CROP_TASK_ID = "id";
    public static final String CROP_TASK_CROP_ID = "cropId";
    public static final String CROP_TASK_USER_ID = "userId";
    public static final String CROP_TASK_DATE= "date";
    public static final String CROP_TASK_TITLE = "title";
    public static final String CROP_TASK_TYPE = "type";
    public static final String CROP_TASK_EMPLOYEE_ID = "employeeId";
    public static final String CROP_TASK_STATUS = "status";
    public static final String CROP_TASK_DESCRIPTION = "description";
    public static final String CROP_TASK_RECURRENCE = "recurrence";
    public static final String CROP_TASK_REMINDERS = "reminders";

    public static final String CROP_PRODUCT_ITEM_TYPE_SALES_ORDER = "salesOrder";
    public static final String CROP_PRODUCT_ITEM_TYPE_ESTIMATE = "estimate";
    public static final String CROP_PRODUCT_ITEM_TYPE_INVOICE = "estimate";
    public static final String CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER = "purchaseOrder";

    public static final String CROP_PURCHASE_ORDER_ID ="id";
    public static final String CROP_PURCHASE_ORDER_USER_ID ="userId";
    public static final String CROP_PURCHASE_ORDER_SUPPLIER_ID ="supplierId";
    public static final String CROP_PURCHASE_ORDER_NUMBER ="number";
    public static final String CROP_PURCHASE_ORDER_REFERENCE_NUMBER ="referenceNumber";
    public static final String CROP_PURCHASE_ORDER_DELIVERY_METHOD ="deliveryMethod";
    public static final String CROP_PURCHASE_ORDER_DELIVERY_DATE ="deliveryDate";
    public static final String CROP_PURCHASE_ORDER_PURCHASE_DATE ="purchaseDate";
    public static final String CROP_PURCHASE_ORDER_DISCOUNT ="discount";
    public static final String CROP_PURCHASE_ORDER_NOTES ="notes";
    public static final String CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS ="termsAndConditions";
    public static final String CROP_PURCHASE_ORDER_STATUS ="status";

    public static final String CROP_PAYMENT_BILL_ID ="id";
    public static final String CROP_PAYMENT_BILL_USER_ID ="userId";
    public static final String CROP_PAYMENT_BILL_DATE ="date";
    public static final String CROP_PAYMENT_BILL_PAYMENT_MADE ="amount";
    public static final String CROP_PAYMENT_BILL_PAYMENT_MODE="mode";
    public static final String CROP_PAYMENT_BILL_PAID_THROUGH ="paidThrough";
    public static final String CROP_PAYMENT_BILL_REFERENCE_NUMBER ="referenceNumber";
    public static final String CROP_PAYMENT_BILL_NOTES ="notes";

    public static final String CROP_ITEM_ID ="id";
    public static final String CROP_ITEM_NAME ="name";
    public static final String CROP_ITEM_N_COMPOSITION ="nComposition";
    public static final String CROP_ITEM_P_COMPOSITION ="pComposition";
    public static final String CROP_ITEM_K_COMPOSITION ="kComposition";
    public static final String CROP_ITEM_IMAGE_RESOURCE_ID="imageResourceId";

    public static final String CROP_FERTILIZER_ID ="id";
    public static final String CROP_FERTILIZER_TYPE ="type";
    public static final String CROP_FERTILIZER_NAME ="name";
    public static final String CROP_FERTILIZER_N_PERCENTAGE ="nPercentage";
    public static final String CROP_FERTILIZER_P_PERCENTAGE ="pPercentage";
    public static final String CROP_FERTILIZER_K_PERCENTAGE ="kPercentage";





    private static MyFarmDbHandlerSingleton myFarmDbHandlerSingleton;
    SQLiteDatabase database;
    Context context;


    private MyFarmDbHandlerSingleton(Context context) {

        super(context, DATABASE_NAME, null, database_version);
        this.context = context;
    }

    public static MyFarmDbHandlerSingleton getHandlerInstance(Context context) {
        if (myFarmDbHandlerSingleton == null) {
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
                CROP_CROP_FIELD_ID+" TEXT NOT NULL," +CROP_CROP_GROWING_CYCLE+" TEXT," +CROP_CROP_SEASON +" TEXT,"+CROP_CROP_DATE_SOWN+" TEXT NOT NULL,"+
                CROP_CROP_AREA+" REAL,"+CROP_CROP_OPERATOR+" TEXT NOT NULL,"+
                CROP_CROP_COST+" REAL NOT NULL,"+CROP_CROP_SEED_ID+" TEXT ,"+CROP_CROP_RATE+" REAL ,"+CROP_CROP_PLANTING_METHOD+" TEXT NOT NULL )";

        String crop_cultivate_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_CULTIVATION_TABLE_NAME+" ( "+CROP_CULTIVATION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_CULTIVATION_USER_ID+" TEXT,"+CROP_CULTIVATION_CROP_ID+" TEXT NOT NULL,"+ CROP_CULTIVATION_DATE+" TEXT NOT NULL,"+ CROP_CULTIVATION_OPERATION+" TEXT NOT NULL,"+CROP_CULTIVATION_OPERATOR+" TEXT NOT NULL,"+
                CROP_CULTIVATION_COST+" REAL,"+CROP_CULTIVATION_NOTES+" TEXT )";

        String crop_fertilizer_application_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+" ( "+CROP_FERTILIZER_APPLICATION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
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
                CROP_MACHINE_REGISTRATION_NUMBER+" TEXT,"+ CROP_MACHINE_QUANTITY+" REAL NOT NULL,"+CROP_MACHINE_DATE_ACQUIRED+" TEXT NOT NULL,"+
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
                CROP_ESTIMATE_REFERENCE_NO+" TEXT NOT NULL,"+ CROP_ESTIMATE_EXP_DATE+" TEXT,"+CROP_ESTIMATE_DISCOUNT+" REAL DEFAULT 0,"+ CROP_ESTIMATE_SHIPPING_CHARGES+" REAL DEFAULT 0  ,"+
                CROP_ESTIMATE_CUSTOMER_NOTES+" TEXT ,"+  CROP_ESTIMATE_STATUS+" TEXT DEFAULT 'DRAFT' ,"+ CROP_ESTIMATE_TERMS_AND_CONDITIONS+" TEXT "+" )";


        String crop_estimate_item_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_PRODUCT_ITEM_TABLE_NAME+" ( "+CROP_PRODUCT_ITEM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_PRODUCT_ITEM_PRODUCT_ID+" TEXT NOT NULL,"+CROP_PRODUCT_ITEM_ESTIMATE_ID+" TEXT NOT NULL,"+ CROP_PRODUCT_ITEM_QUANTITY+" REAL NOT NULL, "+CROP_PRODUCT_ITEM_TAX+" REAL NOT NULL, "+
                CROP_PRODUCT_ITEM_RATE+" REAL NOT NULL, "+ CROP_PRODUCT_ITEM_TYPE+" TEXT NOT NULL, "+
                " FOREIGN KEY ( "+CROP_PRODUCT_ITEM_ESTIMATE_ID+") REFERENCES  "+CROP_ESTIMATE_TABLE_NAME+" ( "+CROP_ESTIMATE_ID+" ), " +
                "FOREIGN KEY( "+CROP_PRODUCT_ITEM_PRODUCT_ID+") REFERENCES  "+CROP_PRODUCT_TABLE_NAME+" ( "+CROP_PRODUCT_ID+" ) )";


        String crop_invoices_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_INVOICE_TABLE_NAME+" ( "+CROP_INVOICE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_INVOICE_USER_ID+" TEXT NOT NULL,"+CROP_INVOICE_CUSTOMER_ID+" TEXT NOT NULL,"+CROP_INVOICE_NO+" TEXT NOT NULL,"+CROP_INVOICE_TERMS+" TEXT NOT NULL,"+
                CROP_INVOICE_ORDER_NUMBER+" TEXT NOT NULL,"+CROP_INVOICE_DATE+" TEXT NOT NULL,"+
                CROP_INVOICE_DUE_DATE+" TEXT,"+CROP_INVOICE_DISCOUNT+" REAL DEFAULT 0,"+ CROP_INVOICE_SHIPPING_CHARGES+" REAL DEFAULT 0  ,"+
                CROP_INVOICE_CUSTOMER_NOTES+" TEXT ,"+ CROP_INVOICE_TERMS_AND_CONDITIONS+" TEXT "+" )";



        String crop_payment_item_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_PAYMENT_TABLE_NAME+" ( "+CROP_PAYMENT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_PAYMENT_USER_ID+" TEXT NOT NULL,"+CROP_PAYMENT_CUSTOMER_ID+" TEXT NOT NULL,"+CROP_PAYMENT_DATE+" TEXT NOT NULL,"+ CROP_PAYMENT_MODE+" TEXT NOT NULL, "+CROP_PAYMENT_AMOUNT+" REAL NOT NULL, "+
                CROP_PAYMENT_REFERENCE_NO+" TEXT , "+CROP_PAYMENT_NUMBER+" TEXT , "+CROP_PAYMENT_NOTES+" TEXT , "+CROP_PAYMENT_INVOICE_ID+" TEXT , "+" FOREIGN KEY ( "+CROP_PAYMENT_INVOICE_ID+") REFERENCES  "+CROP_INVOICE_TABLE_NAME+" ( "+CROP_INVOICE_ID+" ), " +
                "FOREIGN KEY( "+CROP_PAYMENT_CUSTOMER_ID+") REFERENCES  "+CROP_CUSTOMER_TABLE_NAME+" ( "+CROP_CUSTOMER_ID+" ) )";
        String crop_income_expense_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_INCOME_EXPENSE_TABLE_NAME + " ( " + CROP_INCOME_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CROP_INCOME_EXPENSE_DATE + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_USER_ID + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_TRANSACTION + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_ITEM +
                " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_CATEGORY + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_QUANTITY + " REAL NOT NULL, " + CROP_INCOME_EXPENSE_GROSS_AMOUNT + " REAL NOT NULL, " + CROP_INCOME_EXPENSE_UNIT_PRICE + " REAL NOT NULL, " + CROP_INCOME_EXPENSE_TAXES + " REAL, "
                + CROP_INCOME_EXPENSE_PAYMENT_MODE + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_PAYMENT_STATUS + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_SELLING_PRICE + " REAL , " + CROP_INCOME_EXPENSE_CUSTOMER_SUPPLIER + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_CROP_ID + " TEXT " + " ) ";

        String crop_task_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_TASK_TABLE_NAME + " ( " + CROP_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_TASK_CROP_ID + " TEXT NOT NULL, " + CROP_TASK_USER_ID + " TEXT NOT NULL, " + CROP_TASK_DATE + " TEXT NOT NULL, " + CROP_TASK_TITLE + " TEXT NOT NULL, " +
                CROP_TASK_EMPLOYEE_ID + " TEXT NOT NULL, " + CROP_TASK_STATUS + " TEXT NOT NULL, " +CROP_TASK_TYPE + " TEXT NOT NULL, " + CROP_TASK_DESCRIPTION + " TEXT NOT NULL, " + CROP_TASK_RECURRENCE + " TEXT NOT NULL, " + CROP_TASK_REMINDERS + " TEXT NOT NULL " + " ) ";

        String crop_sales_order_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_SALES_ORDER_TABLE_NAME+" ( "+CROP_SALES_ORDER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_SALES_ORDER_USER_ID+" TEXT NOT NULL,"+CROP_SALES_ORDER_CUSTOMER_ID+" TEXT NOT NULL,"+CROP_SALES_ORDER_NO+" TEXT NOT NULL,"+CROP_SALES_ORDER_REFERENCE_NO+" TEXT NOT NULL,"+CROP_SALES_ORDER_DATE+" TEXT NOT NULL,"+
                CROP_SALES_ORDER_SHIPPING_DATE +" TEXT,"+CROP_SALES_ORDER_SHIPPING_METHOD +" TEXT,"+CROP_SALES_ORDER_DISCOUNT+" REAL DEFAULT 0,"+ CROP_SALES_ORDER_SHIPPING_CHARGES+" REAL DEFAULT 0  ,"+
                CROP_SALES_ORDER_CUSTOMER_NOTES+" TEXT ,"+ CROP_SALES_ORDER_STATUS+" TEXT DEFAULT 'DRAFT' ,"+ CROP_SALES_ORDER_TERMS_AND_CONDITIONS+" TEXT "+" )";

        String crop_purchase_order_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_PURCHASE_ORDER_TABLE_NAME+" ( "+CROP_PURCHASE_ORDER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_PURCHASE_ORDER_USER_ID+" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_SUPPLIER_ID+" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_NUMBER+" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_REFERENCE_NUMBER+" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_PURCHASE_DATE+" TEXT NOT NULL,"+
                CROP_PURCHASE_ORDER_DELIVERY_DATE +" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_DELIVERY_METHOD +" TEXT,"+CROP_PURCHASE_ORDER_DISCOUNT+" REAL DEFAULT 0,"+
                CROP_PURCHASE_ORDER_NOTES+" TEXT ,"+ CROP_PURCHASE_ORDER_STATUS+" TEXT DEFAULT 'DRAFT' ,"+ CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS+" TEXT "+" )";

        String crop_payment_bill_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_PAYMENT_BILL_TABLE_NAME+" ( "+ CROP_PAYMENT_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + CROP_PAYMENT_BILL_USER_ID + " TEXT NOT NULL,"  + CROP_PAYMENT_BILL_DATE + " TEXT NOT NULL," +
                CROP_PAYMENT_BILL_PAYMENT_MADE + " " +
                "REAL NOT NULL," + CROP_PAYMENT_BILL_PAYMENT_MODE + " TEXT NOT NULL," + CROP_PAYMENT_BILL_PAID_THROUGH + " TEXT," + CROP_PAYMENT_BILL_REFERENCE_NUMBER + " TEXT," + CROP_PAYMENT_BILL_NOTES + " TEXT" + " )";


        String crop_item_table_query = " CREATE TABLE IF NOT EXISTS " + CROP_ITEM_TABLE_NAME + " ( " + CROP_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_ITEM_N_COMPOSITION + " REAL DEFAULT 0, "+
                CROP_ITEM_K_COMPOSITION + " REAL DEFAULT 0, "+CROP_ITEM_NAME + " TEXT NOT NULL , "  +CROP_ITEM_IMAGE_RESOURCE_ID + " TEXT  , "  + CROP_ITEM_P_COMPOSITION + " REAL DEFAULT 0 ) " ;


        String crop_fertilizer_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_FERTILIZER_TABLE_NAME+" ( "+CROP_FERTILIZER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_FERTILIZER_TYPE+" TEXT NOT NULL,"+ CROP_FERTILIZER_NAME+" TEXT NOT NULL,"+ CROP_FERTILIZER_N_PERCENTAGE+" REAL,"+
                CROP_FERTILIZER_P_PERCENTAGE+" REAL,"+ CROP_FERTILIZER_K_PERCENTAGE+" REAL )";



       /* Log.d("FERTILIZER INVENTORY",crop_inventory_fertilizer_insert_query);
        Log.d("SEEDS INVENTORY",crop_seeds_insert_query);
        Log.d("SPRAY INVENTORY",crop_inventory_spray_insert_query);
        Log.d("SPRAY INVENTORY",crop_spraying_insert_query);
        Log.d("CROP",crop_insert_query);
        Log.d("CULTIVATE",crop_cultivate_insert_query);
        Log.d("FERTILIZER",crop_fertilizer_insert_query);
        Log.d("FIELDS",crop_field_insert_query);
        Log.d("MACHINE",crop_machine_insert_query);
        Log.d("MACHINE",crop_machine_insert_query);*/

       //db.execSQL("DROP TABLE IF EXISTS "+ CROP_FERTILIZER_TABLE_NAME);
        database.execSQL(crop_inventory_fertilizer_insert_query);
        database.execSQL(crop_seeds_insert_query);
        database.execSQL(crop_inventory_spray_insert_query);
        database.execSQL(crop_insert_query);
        database.execSQL(crop_cultivate_insert_query);
        database.execSQL(crop_fertilizer_application_insert_query);
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
        database.execSQL(crop_payment_item_insert_query);

        database.execSQL(crop_income_expense_insert_query);
        database.execSQL(crop_task_insert_query);

        database.execSQL(crop_sales_order_insert_query);
        database.execSQL(crop_purchase_order_insert_query);
        database.execSQL(crop_payment_bill_insert_query);
        database.execSQL(crop_item_table_query);
        database.execSQL(crop_fertilizer_insert_query);

        System.out.println(


                ";"+crop_employee_insert_query+
                ";"+crop_customer_insert_query+
                ";"+crop_supplier_insert_query+
                ";"+crop_product_insert_query+
                ";"+crop_estimates_insert_query+
                ";"+crop_estimate_item_insert_query+
                ";"+crop_invoices_insert_query+

                ";"+crop_payment_item_insert_query+

                ";"+crop_sales_order_insert_query +
                        ";"+crop_purchase_order_insert_query);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CROP_INVENTORY_FERTILIZER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_INVENTORY_SEEDS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_INVENTORY_SPRAY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_SPRAYING_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_FIELDS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_MACHINE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_EMPLOYEE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_CUSTOMER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_SOIL_ANALYSIS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_EMPLOYEE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_CROP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_SUPPLIER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_PRODUCT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_ESTIMATE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_PRODUCT_ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_PRODUCT_ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_PAYMENT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_INCOME_EXPENSE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_TASK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_PURCHASE_ORDER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CROP_PAYMENT_BILL_TABLE_NAME);


        onCreate(db);
    }


    public MyFarmDbHandlerSingleton openDB() throws SQLException {

        database = this.getWritableDatabase();
        onCreate(database);

        return this;
    }

    public void closeDB() throws SQLException {
        this.close();
    }

    public void insertCropFertilizer(CropFertilizer fertilizer) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_FERTILIZER_TYPE, fertilizer.getType());
        contentValues.put(CROP_FERTILIZER_NAME, fertilizer.getFertilizerName());
        contentValues.put(CROP_FERTILIZER_N_PERCENTAGE, fertilizer.getnPercentage());
        contentValues.put(CROP_FERTILIZER_K_PERCENTAGE, fertilizer.getkPercentage());
        contentValues.put(CROP_FERTILIZER_P_PERCENTAGE, fertilizer.getpPercentage());


        database.insert(CROP_FERTILIZER_TABLE_NAME, null, contentValues);
        closeDB();
    }
    public void updateCropFertilizer(CropFertilizer fertilizer) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_FERTILIZER_TYPE, fertilizer.getType());
        contentValues.put(CROP_FERTILIZER_NAME, fertilizer.getFertilizerName());
        contentValues.put(CROP_FERTILIZER_N_PERCENTAGE, fertilizer.getnPercentage());
        contentValues.put(CROP_FERTILIZER_K_PERCENTAGE, fertilizer.getkPercentage());
        contentValues.put(CROP_FERTILIZER_P_PERCENTAGE, fertilizer.getpPercentage());
        database.update(CROP_FERTILIZER_TABLE_NAME, contentValues, CROP_FERTILIZER_ID + " = ?", new String[]{fertilizer.getId()});
        closeDB();
    }
    public boolean deleteCropFertilizer(String fertilizerId) {
        openDB();
        database.delete(CROP_FERTILIZER_TABLE_NAME, CROP_FERTILIZER_ID + " = ?", new String[]{fertilizerId});
        closeDB();
        return true;
    }
    public ArrayList<CropFertilizer> getCropFertilizers(String type) {
        openDB();
        ArrayList<CropFertilizer> array_list = new ArrayList();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_FERTILIZER_TABLE_NAME + " where " + CROP_FERTILIZER_TYPE + " = '" + type+"'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropFertilizer fertilizer = new CropFertilizer();
            fertilizer.setId(res.getString(res.getColumnIndex(CROP_FERTILIZER_ID)));

            fertilizer.setType(res.getString(res.getColumnIndex(CROP_FERTILIZER_TYPE)));
            fertilizer.setFertilizerName(res.getString(res.getColumnIndex(CROP_FERTILIZER_NAME)));
            fertilizer.setnPercentage(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_N_PERCENTAGE)));
            fertilizer.setpPercentage(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_P_PERCENTAGE)));
            fertilizer.setkPercentage(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_K_PERCENTAGE)));

            array_list.add(fertilizer);
            Log.d("TYPE",fertilizer.getType());
            res.moveToNext();
        }

        closeDB();

        if (array_list.size()==0){
            CropDatabaseInitializerSingleton.initializeFertilizers(this);
            //return getCropFertilizers(type);
        }

        //Log.d("HOUSES SIZE",array_list.size()+"");
        return array_list;

    }


    public void insertCropItem(CropItem crop) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_ITEM_NAME, crop.getName());
        contentValues.put(CROP_ITEM_N_COMPOSITION, crop.getnPercentage());
        contentValues.put(CROP_ITEM_P_COMPOSITION, crop.getpPercentage());
        contentValues.put(CROP_ITEM_K_COMPOSITION, crop.getkPercentage());
        contentValues.put(CROP_ITEM_IMAGE_RESOURCE_ID, crop.getImageResourceId());

        database.insert(CROP_ITEM_TABLE_NAME, null, contentValues);

        closeDB();
    }

    public void updateCropItem(CropItem crop) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_ITEM_NAME, crop.getName());
        contentValues.put(CROP_ITEM_N_COMPOSITION, crop.getnPercentage());
        contentValues.put(CROP_ITEM_P_COMPOSITION, crop.getpPercentage());
        contentValues.put(CROP_ITEM_K_COMPOSITION, crop.getkPercentage());
        contentValues.put(CROP_ITEM_IMAGE_RESOURCE_ID, crop.getImageResourceId());
        database.update(CROP_ITEM_TABLE_NAME, contentValues, CROP_ITEM_ID + " = ?", new String[]{crop.getId()});
        closeDB();
    }

    public boolean deleteCropItem(String id) {
        openDB();
        database.delete(CROP_ITEM_TABLE_NAME, CROP_ITEM_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public ArrayList<CropItem> getCropItems() {
        openDB();
        ArrayList<CropItem> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_ITEM_TABLE_NAME , null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
            CropItem cropItem = new CropItem();
            cropItem.setId(res.getString(res.getColumnIndex(CROP_ITEM_ID)));
            cropItem.setName(res.getString(res.getColumnIndex(CROP_ITEM_NAME)));
            cropItem.setnPercentage(res.getInt(res.getColumnIndex(CROP_ITEM_N_COMPOSITION)));
            cropItem.setkPercentage(res.getInt(res.getColumnIndex(CROP_ITEM_K_COMPOSITION)));
            cropItem.setpPercentage(res.getInt(res.getColumnIndex(CROP_ITEM_P_COMPOSITION)));
            cropItem.setImageResourceId(res.getString(res.getColumnIndex(CROP_ITEM_IMAGE_RESOURCE_ID)));
            array_list.add(cropItem);
            res.moveToNext();
        }

        closeDB();
        if (array_list.size()==0){
            CropDatabaseInitializerSingleton.initializeCrops(this);
           // return getCropItems();
        }
        Log.d("SIZE", array_list.size()+"");
        return array_list;

    }
    public CropItem getCropItem(String id) {
        openDB();
        ArrayList<CropItem> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_ITEM_TABLE_NAME + " where " + CROP_ITEM_ID + " = " + id, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
            CropItem cropItem = new CropItem();
            cropItem.setId(res.getString(res.getColumnIndex(CROP_ITEM_ID)));
            cropItem.setName(res.getString(res.getColumnIndex(CROP_ITEM_NAME)));
            cropItem.setnPercentage(res.getInt(res.getColumnIndex(CROP_ITEM_N_COMPOSITION)));
            cropItem.setkPercentage(res.getInt(res.getColumnIndex(CROP_ITEM_K_COMPOSITION)));
            cropItem.setpPercentage(res.getInt(res.getColumnIndex(CROP_ITEM_P_COMPOSITION)));
            cropItem.setImageResourceId(res.getString(res.getColumnIndex(CROP_ITEM_IMAGE_RESOURCE_ID)));

            return cropItem;
        }

        closeDB();

        return null;

    }
    public String getNextSalesOrderNumber(){
        openDB();
        Cursor res =  database.rawQuery( "select "+CROP_SALES_ORDER_ID+" from "+CROP_SALES_ORDER_TABLE_NAME+" ORDER BY "+CROP_SALES_ORDER_ID+" DESC LIMIT 1",null);
        int lastId = 0;
        res.moveToFirst();
        if(!res.isAfterLast()){
            lastId = res.getInt(res.getColumnIndex(CROP_SALES_ORDER_ID));
        }
        int id=lastId+1;
        closeDB();

        return "SO-"+String.format("%03d", id);
    }
    public void  insertCropSalesOrder(CropSalesOrder estimate){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SALES_ORDER_USER_ID,estimate.getUserId());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_ID,estimate.getCustomerId());
        contentValues.put(CROP_SALES_ORDER_NO,estimate.getNumber());
        contentValues.put(CROP_SALES_ORDER_DATE,estimate.getDate());
        contentValues.put(CROP_SALES_ORDER_STATUS,estimate.getStatus());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_METHOD,estimate.getMethod());
        contentValues.put(CROP_SALES_ORDER_REFERENCE_NO,estimate.getReferenceNumber());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_DATE,estimate.getShippingDate());
        contentValues.put(CROP_SALES_ORDER_STATUS,estimate.getStatus());
        contentValues.put(CROP_SALES_ORDER_DISCOUNT,estimate.getDiscount());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_CHARGES,estimate.getShippingCharges());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_NOTES,estimate.getCustomerNotes());
        contentValues.put(CROP_SALES_ORDER_TERMS_AND_CONDITIONS,estimate.getTermsAndConditions());

        database.insert(CROP_SALES_ORDER_TABLE_NAME,null,contentValues);

        Cursor res =  database.rawQuery( "select "+CROP_SALES_ORDER_ID+" from "+CROP_SALES_ORDER_TABLE_NAME+" where "+CROP_SALES_ORDER_CUSTOMER_ID+" = '"+estimate.getCustomerId()+"' AND "+CROP_SALES_ORDER_NO+" = '"+estimate.getNumber()+"'", null );
        res.moveToFirst();
        if(!res.isAfterLast()){
            String estimateId = res.getString(res.getColumnIndex(CROP_SALES_ORDER_ID));

            ArrayList<CropProductItem> items = estimate.getItems();

            for(CropProductItem x: items){
                contentValues.clear();
                contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
                contentValues.put(CROP_PRODUCT_ITEM_ESTIMATE_ID,estimateId);
                contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
                contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
                contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
                contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_SALES_ORDER);
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }
        }
        closeDB();
    }
    public void  updateCropSalesOrder(CropSalesOrder estimate){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SALES_ORDER_USER_ID,estimate.getUserId());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_ID,estimate.getCustomerId());
        contentValues.put(CROP_SALES_ORDER_NO,estimate.getNumber());
        contentValues.put(CROP_SALES_ORDER_DATE,estimate.getDate());
        contentValues.put(CROP_SALES_ORDER_STATUS,estimate.getStatus());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_METHOD,estimate.getMethod());
        contentValues.put(CROP_SALES_ORDER_STATUS,estimate.getStatus());
        contentValues.put(CROP_SALES_ORDER_REFERENCE_NO,estimate.getReferenceNumber());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_DATE,estimate.getShippingDate());
        contentValues.put(CROP_SALES_ORDER_DISCOUNT,estimate.getDiscount());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_CHARGES,estimate.getShippingCharges());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_NOTES,estimate.getCustomerNotes());
        contentValues.put(CROP_SALES_ORDER_TERMS_AND_CONDITIONS,estimate.getTermsAndConditions());

        database.update(CROP_SALES_ORDER_TABLE_NAME,contentValues,CROP_SALES_ORDER_ID+" = ?", new String[]{estimate.getId()});

        String estimateId = estimate.getId();

        ArrayList<CropProductItem> items = estimate.getItems();

        for(CropProductItem x: items){
            contentValues.clear();
            contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
            contentValues.put(CROP_PRODUCT_ITEM_ESTIMATE_ID,estimateId);
            contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
            contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
            contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
            contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_SALES_ORDER);
            if(x.getId() !=null){
                database.update(CROP_PRODUCT_ITEM_TABLE_NAME,contentValues,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{x.getId()});
            }
            else{
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }

        }

        for(String id: estimate.getDeletedItemsIds()){
            database.delete(CROP_PRODUCT_ITEM_TABLE_NAME,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{id});
        }

        closeDB();
    }
    public boolean deleteCropSalesOrder(String id){
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME,CROP_PRODUCT_ITEM_ID+" = ? AND "+CROP_PRODUCT_ITEM_TYPE+" = ?", new String[]{id,CROP_PRODUCT_ITEM_TYPE_SALES_ORDER});
        database.delete(CROP_SALES_ORDER_TABLE_NAME,CROP_SALES_ORDER_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropSalesOrder> getCropSalesOrders(String userId){
        openDB();
        ArrayList<CropSalesOrder> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+CROP_SALES_ORDER_TABLE_NAME+".*,"+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_NAME+" from "+CROP_SALES_ORDER_TABLE_NAME+" LEFT JOIN "+CROP_CUSTOMER_TABLE_NAME+" ON "+CROP_SALES_ORDER_TABLE_NAME+"."+CROP_SALES_ORDER_CUSTOMER_ID+" = "+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_ID+" where "+CROP_SALES_ORDER_TABLE_NAME+"."+CROP_SALES_ORDER_USER_ID+" = "+ userId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropSalesOrder cropSalesOrder = new CropSalesOrder();
            cropSalesOrder.setId(res.getString(res.getColumnIndex(CROP_SALES_ORDER_ID)));
            cropSalesOrder.setUserId(res.getString(res.getColumnIndex(CROP_SALES_ORDER_USER_ID)));
            cropSalesOrder.setCustomerId(res.getString(res.getColumnIndex(CROP_SALES_ORDER_CUSTOMER_ID)));
            cropSalesOrder.setNumber(res.getString(res.getColumnIndex(CROP_SALES_ORDER_NO)));
            cropSalesOrder.setMethod(res.getString(res.getColumnIndex(CROP_SALES_ORDER_SHIPPING_METHOD)));
            cropSalesOrder.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropSalesOrder.setReferenceNumber(res.getString(res.getColumnIndex(CROP_SALES_ORDER_REFERENCE_NO)));
            cropSalesOrder.setDate(res.getString(res.getColumnIndex(CROP_SALES_ORDER_DATE)));
            cropSalesOrder.setStatus(res.getString(res.getColumnIndex(CROP_SALES_ORDER_STATUS)));
            cropSalesOrder.setShippingDate(res.getString(res.getColumnIndex(CROP_SALES_ORDER_SHIPPING_DATE)));
            cropSalesOrder.setDiscount(res.getFloat(res.getColumnIndex(CROP_SALES_ORDER_DISCOUNT)));
            cropSalesOrder.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_SALES_ORDER_SHIPPING_CHARGES)));
            cropSalesOrder.setCustomerNotes(res.getString(res.getColumnIndex(CROP_SALES_ORDER_CUSTOMER_NOTES)));
            cropSalesOrder.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_SALES_ORDER_TERMS_AND_CONDITIONS)));
            array_list.add(cropSalesOrder);
            res.moveToNext();
        }


        for(CropSalesOrder cropSalesOrder: array_list){
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery( "select * from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+CROP_PRODUCT_ITEM_ESTIMATE_ID+" = "+ cropSalesOrder.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = "+CROP_PRODUCT_ITEM_TYPE_SALES_ORDER, null );
            res.moveToFirst();
            while(!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setInvoiceOrEstimateId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ESTIMATE_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                items_list.add(item);
                res.moveToNext();
            }
            cropSalesOrder.setItems(items_list);
        }

        closeDB();
        return array_list;
    }
    public void  insertCropPayment(CropPayment cropPayment){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PAYMENT_USER_ID, cropPayment.getUserId());
        contentValues.put(CROP_PAYMENT_AMOUNT, cropPayment.getAmount());
        contentValues.put(CROP_PAYMENT_DATE, cropPayment.getDate());
        contentValues.put(CROP_PAYMENT_MODE, cropPayment.getMode());
        contentValues.put(CROP_PAYMENT_NUMBER, cropPayment.getPaymentNumber());
        contentValues.put(CROP_PAYMENT_NOTES, cropPayment.getNotes());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID, cropPayment.getCustomerId());
        contentValues.put(CROP_PAYMENT_REFERENCE_NO, cropPayment.getReferenceNo());
        contentValues.put(CROP_PAYMENT_INVOICE_ID, cropPayment.getInvoiceId());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID, cropPayment.getCustomerId());
        database.insert(CROP_PAYMENT_TABLE_NAME, null, contentValues);

        closeDB();
    }

    public void updateCropPayment(CropPayment cropPayment) {
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
        contentValues.put(CROP_PAYMENT_DATE,cropPayment.getDate());
        database.update(CROP_PAYMENT_TABLE_NAME,contentValues,CROP_PAYMENT_ID+" = ?", new String[]{cropPayment.getId()});


        closeDB();

    }

    public boolean deleteCropPayment(String id) {
        openDB();
        database.delete(CROP_PAYMENT_TABLE_NAME, CROP_PAYMENT_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public ArrayList<CropPayment> getCropPaymentsByInvoice(String invoiceId){
        openDB();
        ArrayList<CropPayment> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+CROP_PAYMENT_TABLE_NAME+".*,"+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_NAME+", "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_NO+" from "+CROP_PAYMENT_TABLE_NAME+" LEFT JOIN "+CROP_CUSTOMER_TABLE_NAME+" ON "+CROP_PAYMENT_TABLE_NAME+"."+CROP_PAYMENT_CUSTOMER_ID+" = "+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_ID+
                " JOIN "+CROP_INVOICE_TABLE_NAME+" ON "+CROP_PAYMENT_TABLE_NAME+"."+CROP_PAYMENT_INVOICE_ID+" = "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_ID+
                " where "+CROP_PAYMENT_TABLE_NAME+"."+CROP_PAYMENT_INVOICE_ID+" = "+invoiceId, null );
        res.moveToFirst();


        while(!res.isAfterLast()){
            CropPayment cropPayment = new CropPayment();
            cropPayment.setId(res.getString(res.getColumnIndex(CROP_PAYMENT_ID)));
            cropPayment.setUserId(res.getString(res.getColumnIndex(CROP_PAYMENT_USER_ID)));
            cropPayment.setAmount(res.getFloat(res.getColumnIndex(CROP_PAYMENT_AMOUNT)));
            cropPayment.setMode(res.getString(res.getColumnIndex(CROP_PAYMENT_MODE)));
            cropPayment.setDate(res.getString(res.getColumnIndex(CROP_PAYMENT_DATE)));
            cropPayment.setReferenceNo(res.getString(res.getColumnIndex(CROP_PAYMENT_REFERENCE_NO)));
            cropPayment.setPaymentNumber(res.getString(res.getColumnIndex(CROP_PAYMENT_NUMBER)));
            cropPayment.setNotes(res.getString(res.getColumnIndex(CROP_PAYMENT_NOTES)));
            cropPayment.setCustomerId(res.getString(res.getColumnIndex(CROP_PAYMENT_CUSTOMER_ID)));
            cropPayment.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropPayment.setInvoiceId(res.getString(res.getColumnIndex(CROP_PAYMENT_INVOICE_ID)));
            cropPayment.setInvoiceNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            array_list.add(cropPayment);
            res.moveToNext();
        }

        closeDB();
        return array_list;

    }
    public ArrayList<CropPayment> getCropPayments(String fieldId){

        openDB();
        ArrayList<CropPayment> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select "+CROP_PAYMENT_TABLE_NAME+".*,"+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_NAME+", "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_NO+" from "+CROP_PAYMENT_TABLE_NAME+" LEFT JOIN "+CROP_CUSTOMER_TABLE_NAME+" ON "+CROP_PAYMENT_TABLE_NAME+"."+CROP_PAYMENT_CUSTOMER_ID+" = "+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_ID+
                        " JOIN "+CROP_INVOICE_TABLE_NAME+" ON "+CROP_PAYMENT_TABLE_NAME+"."+CROP_PAYMENT_INVOICE_ID+" = "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_ID+
                " where "+CROP_PAYMENT_TABLE_NAME+"."+CROP_PAYMENT_USER_ID+" = "+fieldId, null );
        res.moveToFirst();


        while (!res.isAfterLast()) {
            CropPayment cropPayment = new CropPayment();
            cropPayment.setId(res.getString(res.getColumnIndex(CROP_PAYMENT_ID)));
            cropPayment.setUserId(res.getString(res.getColumnIndex(CROP_PAYMENT_USER_ID)));
            cropPayment.setAmount(res.getFloat(res.getColumnIndex(CROP_PAYMENT_AMOUNT)));
            cropPayment.setMode(res.getString(res.getColumnIndex(CROP_PAYMENT_MODE)));
            cropPayment.setDate(res.getString(res.getColumnIndex(CROP_PAYMENT_DATE)));
            cropPayment.setReferenceNo(res.getString(res.getColumnIndex(CROP_PAYMENT_REFERENCE_NO)));
            cropPayment.setPaymentNumber(res.getString(res.getColumnIndex(CROP_PAYMENT_NUMBER)));
            cropPayment.setNotes(res.getString(res.getColumnIndex(CROP_PAYMENT_NOTES)));
            cropPayment.setCustomerId(res.getString(res.getColumnIndex(CROP_PAYMENT_CUSTOMER_ID)));
            cropPayment.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropPayment.setInvoiceId(res.getString(res.getColumnIndex(CROP_PAYMENT_INVOICE_ID)));
            cropPayment.setInvoiceNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            array_list.add(cropPayment);
            res.moveToNext();
        }

        closeDB();
        Log.d("Crop Payment", array_list.toString());
        return array_list;

    }


    public String getNextPaymentNumber(){
        openDB();
        Cursor res =  database.rawQuery( "select "+CROP_PAYMENT_ID+" from "+CROP_PAYMENT_TABLE_NAME+" ORDER BY "+CROP_PAYMENT_ID+" DESC LIMIT 1",null);
        int lastId = 0;
        res.moveToFirst();
        if(!res.isAfterLast()){
            lastId = res.getInt(res.getColumnIndex(CROP_PAYMENT_ID));
        }
        int id=lastId+1;
        closeDB();

        return String.format("%03d", id);
    }
    public void  insertCropInvoice(CropInvoice invoice){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_INVOICE_USER_ID,invoice.getUserId());
        contentValues.put(CROP_INVOICE_CUSTOMER_ID,invoice.getCustomerId());
        contentValues.put(CROP_INVOICE_NO,invoice.getNumber());
        contentValues.put(CROP_INVOICE_DATE,invoice.getDate());
        contentValues.put(CROP_INVOICE_DUE_DATE,invoice.getDueDate());
        contentValues.put(CROP_INVOICE_ORDER_NUMBER,invoice.getOrderNumber());
        contentValues.put(CROP_INVOICE_TERMS,invoice.getTerms());
        contentValues.put(CROP_INVOICE_DISCOUNT,invoice.getDiscount());
        contentValues.put(CROP_INVOICE_SHIPPING_CHARGES,invoice.getShippingCharges());
        contentValues.put(CROP_INVOICE_CUSTOMER_NOTES,invoice.getCustomerNotes());
        contentValues.put(CROP_INVOICE_CUSTOMER_NOTES,invoice.getTermsAndConditions());

        database.insert(CROP_INVOICE_TABLE_NAME, null, contentValues);


        Cursor res =  database.rawQuery( "select "+CROP_INVOICE_ID+" from "+CROP_INVOICE_TABLE_NAME+" where "+CROP_INVOICE_CUSTOMER_ID+" = '"+invoice.getCustomerId()+"' AND "+CROP_INVOICE_NO+" = '"+invoice.getNumber()+"'", null );
        res.moveToFirst();
        if(!res.isAfterLast()){
            String invoiceId = res.getString(res.getColumnIndex(CROP_INVOICE_ID));
            ArrayList<CropProductItem> items = invoice.getItems();
            for (CropProductItem x : items) {
                contentValues.clear();
                contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID, x.getProductId());
                contentValues.put(CROP_PRODUCT_ITEM_ESTIMATE_ID, invoiceId);
                contentValues.put(CROP_PRODUCT_ITEM_QUANTITY, x.getQuantity());
                contentValues.put(CROP_PRODUCT_ITEM_TAX, x.getTax());
                contentValues.put(CROP_PRODUCT_ITEM_RATE, x.getRate());
                contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_INVOICE);
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME, null, contentValues);
            }
            //save payments if there are any

            CropPayment cropPayment = invoice.getInitialPayment();
            if(cropPayment != null){
                cropPayment.setInvoiceId(invoiceId);
                this.insertCropPayment(cropPayment);

            }
        }

        closeDB();
    }

    public void updateCropInvoice(CropInvoice invoice) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVOICE_USER_ID,invoice.getUserId());
        contentValues.put(CROP_INVOICE_CUSTOMER_ID,invoice.getCustomerId());
        contentValues.put(CROP_INVOICE_NO,invoice.getNumber());
        contentValues.put(CROP_INVOICE_DATE,invoice.getDate());
        contentValues.put(CROP_INVOICE_DUE_DATE,invoice.getDueDate());
        contentValues.put(CROP_INVOICE_ORDER_NUMBER,invoice.getOrderNumber());
        contentValues.put(CROP_INVOICE_TERMS,invoice.getTerms());
        contentValues.put(CROP_INVOICE_DISCOUNT,invoice.getDiscount());
        contentValues.put(CROP_INVOICE_SHIPPING_CHARGES,invoice.getShippingCharges());
        contentValues.put(CROP_INVOICE_CUSTOMER_NOTES,invoice.getCustomerNotes());
        contentValues.put(CROP_INVOICE_TERMS_AND_CONDITIONS,invoice.getTermsAndConditions());


        database.update(CROP_INVOICE_TABLE_NAME, contentValues, CROP_INVOICE_ID + " = ?", new String[]{invoice.getId()});

        String invoiceId = invoice.getId();

        ArrayList<CropProductItem> items = invoice.getItems();

        for (CropProductItem x : items) {
            contentValues.clear();
            contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
            contentValues.put(CROP_PRODUCT_ITEM_ESTIMATE_ID,invoiceId);
            contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
            contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
            contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
            contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_INVOICE);
            if(x.getId() !=null){
                database.update(CROP_PRODUCT_ITEM_TABLE_NAME,contentValues,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{x.getId()});
            }
            else{
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }

        }
        CropPayment cropPayment = invoice.getInitialPayment();
        if(cropPayment != null){
            cropPayment.setInvoiceId(invoiceId);
            this.insertCropPayment(cropPayment);
        }

        for(String id: invoice.getDeletedItemsIds()){
            database.delete(CROP_PRODUCT_ITEM_TABLE_NAME,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{id});
        }
        closeDB();
    }

    public boolean deleteCropInvoice(String id) {
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_ESTIMATE_ID + " = ? AND "+CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id,CROP_PRODUCT_ITEM_TYPE_INVOICE});
        database.delete(CROP_INVOICE_TABLE_NAME, CROP_INVOICE_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public String getNextInvoiceNumber(){
        openDB();
        Cursor res =  database.rawQuery( "select "+CROP_INVOICE_ID+" from "+ CROP_INVOICE_TABLE_NAME+" ORDER BY "+CROP_INVOICE_ID+" DESC LIMIT 1",null);
        int lastId = 0;
        res.moveToFirst();
        Log.d("TESTING",res.getCount()+"");
        if(!res.isAfterLast()){
            Log.d("TESTING",res.getColumnCount()+" columns "+res.getColumnNames().toString());
            lastId = res.getInt(res.getColumnIndex(CROP_INVOICE_ID));
        }
        int id=lastId+1;
        closeDB();

        return "INV-"+String.format("%04d", id);
    }
    public ArrayList<CropInvoice> getCropInvoices(String userId){
        openDB();
        ArrayList<CropInvoice> array_list = new ArrayList();

        SQLiteDatabase db = database;
        Cursor res =  db.rawQuery( "select "+CROP_INVOICE_TABLE_NAME+".*,"+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_NAME+" from "+CROP_INVOICE_TABLE_NAME+" LEFT JOIN "+CROP_CUSTOMER_TABLE_NAME+" ON "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_CUSTOMER_ID+" = "+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_ID+" where "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_USER_ID+" = "+ userId, null );
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropInvoice cropInvoice = new CropInvoice();
            cropInvoice.setId(res.getString(res.getColumnIndex(CROP_INVOICE_ID)));
            cropInvoice.setUserId(res.getString(res.getColumnIndex(CROP_INVOICE_USER_ID)));
            cropInvoice.setCustomerId(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_ID)));
            cropInvoice.setNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            cropInvoice.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropInvoice.setDate(res.getString(res.getColumnIndex(CROP_INVOICE_DATE)));
            cropInvoice.setDueDate(res.getString(res.getColumnIndex(CROP_INVOICE_DUE_DATE)));
            cropInvoice.setOrderNumber(res.getString(res.getColumnIndex(CROP_INVOICE_ORDER_NUMBER)));
            cropInvoice.setTerms(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS)));
            cropInvoice.setDiscount(res.getFloat(res.getColumnIndex(CROP_INVOICE_DISCOUNT)));
            cropInvoice.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_INVOICE_SHIPPING_CHARGES)));
            cropInvoice.setCustomerNotes(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_NOTES)));
            cropInvoice.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS_AND_CONDITIONS)));
            array_list.add(cropInvoice);
            res.moveToNext();
        }


        for (CropInvoice invoice : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery( "select * from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+CROP_PRODUCT_ITEM_ESTIMATE_ID+" = "+ invoice.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = "+CROP_PRODUCT_ITEM_TYPE_INVOICE, null );
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setInvoiceOrEstimateId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ESTIMATE_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                items_list.add(item);
                res.moveToNext();
            }
            res.close();

            invoice.setItems(items_list);
        }

        for(CropInvoice invoice: array_list){
            invoice.setPayments(this.getCropPaymentsByInvoice(invoice.getId()));
        }

        closeDB();
        Log.d("Crop Product", array_list.toString());
        return array_list;
    }

    public ArrayList<CropInvoice> getCropInvoicesByCustomer(String customerId){
        openDB();
        ArrayList<CropInvoice> array_list = new ArrayList();

        SQLiteDatabase db = database;
        Cursor res =  db.rawQuery( "select "+CROP_INVOICE_TABLE_NAME+".*,"+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_NAME+
                " from "+CROP_INVOICE_TABLE_NAME+" LEFT JOIN "+CROP_CUSTOMER_TABLE_NAME+" ON "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_CUSTOMER_ID+
                " = "+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_ID+" where "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_CUSTOMER_ID+" = "+ customerId, null );
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropInvoice cropInvoice = new CropInvoice();
            cropInvoice.setId(res.getString(res.getColumnIndex(CROP_INVOICE_ID)));
            cropInvoice.setUserId(res.getString(res.getColumnIndex(CROP_INVOICE_USER_ID)));
            cropInvoice.setCustomerId(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_ID)));
            cropInvoice.setNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            cropInvoice.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropInvoice.setDate(res.getString(res.getColumnIndex(CROP_INVOICE_DATE)));
            cropInvoice.setDueDate(res.getString(res.getColumnIndex(CROP_INVOICE_DUE_DATE)));
            cropInvoice.setOrderNumber(res.getString(res.getColumnIndex(CROP_INVOICE_ORDER_NUMBER)));
            cropInvoice.setTerms(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS)));
            cropInvoice.setDiscount(res.getFloat(res.getColumnIndex(CROP_INVOICE_DISCOUNT)));
            cropInvoice.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_INVOICE_SHIPPING_CHARGES)));
            cropInvoice.setCustomerNotes(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_NOTES)));
            cropInvoice.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS_AND_CONDITIONS)));
            array_list.add(cropInvoice);
            res.moveToNext();
        }


        for (CropInvoice invoice : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery( "select * from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+CROP_PRODUCT_ITEM_ESTIMATE_ID+" = "+ invoice.getId(), null );
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setInvoiceOrEstimateId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ESTIMATE_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                items_list.add(item);
                res.moveToNext();
            }
            res.close();

            invoice.setItems(items_list);
        }

        for(CropInvoice invoice: array_list){
            invoice.setPayments(this.getCropPaymentsByInvoice(invoice.getId()));
        }

        closeDB();
        Log.d("Crop Product", array_list.toString());
        return array_list;
    }

    public void insertCropEstimate(CropEstimate estimate) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_ESTIMATE_USER_ID,estimate.getUserId());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_ID,estimate.getCustomerId());
        contentValues.put(CROP_ESTIMATE_NO,estimate.getNumber());
        contentValues.put(CROP_ESTIMATE_DATE,estimate.getDate());
        contentValues.put(CROP_ESTIMATE_STATUS,estimate.getStatus());
        contentValues.put(CROP_ESTIMATE_REFERENCE_NO,estimate.getReferenceNumber());
        contentValues.put(CROP_ESTIMATE_EXP_DATE,estimate.getExpiryDate());
        contentValues.put(CROP_ESTIMATE_DISCOUNT,estimate.getDiscount());
        contentValues.put(CROP_ESTIMATE_SHIPPING_CHARGES,estimate.getShippingCharges());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_NOTES,estimate.getCustomerNotes());
        contentValues.put(CROP_ESTIMATE_TERMS_AND_CONDITIONS,estimate.getTermsAndConditions());
        database.insert(CROP_ESTIMATE_TABLE_NAME, null, contentValues);

        Cursor res =  database.rawQuery( "select "+CROP_ESTIMATE_ID+" from "+CROP_ESTIMATE_TABLE_NAME+" where "+CROP_ESTIMATE_CUSTOMER_ID+" = '"+estimate.getCustomerId()+"' AND "+CROP_ESTIMATE_NO+" = '"+estimate.getNumber()+"'", null );
        res.moveToFirst();
        if(!res.isAfterLast()){
            String estimateId = res.getString(res.getColumnIndex(CROP_ESTIMATE_ID));

            ArrayList<CropProductItem> items = estimate.getItems();

            for (CropProductItem x : items) {
                contentValues.clear();
                contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID, x.getProductId());
                contentValues.put(CROP_PRODUCT_ITEM_ESTIMATE_ID, estimateId);
                contentValues.put(CROP_PRODUCT_ITEM_QUANTITY, x.getQuantity());
                contentValues.put(CROP_PRODUCT_ITEM_TAX, x.getTax());
                contentValues.put(CROP_PRODUCT_ITEM_RATE, x.getRate());
                contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_ESTIMATE);
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME, null, contentValues);
            }
        }
        closeDB();
    }

    public String getNextEstimateNumber() {
        openDB();
        Cursor res = database.rawQuery("select " + CROP_ESTIMATE_ID + " from " + CROP_ESTIMATE_TABLE_NAME + " ORDER BY " + CROP_ESTIMATE_ID + " DESC LIMIT 1", null);
        int lastId = 0;

        if (!res.isAfterLast()) {
            res.moveToFirst();
            Log.d("TESTING", res.getCount() + "");
            if (!res.isAfterLast()) {
                Log.d("TESTING", res.getColumnCount() + " columns " + res.getColumnNames().toString());

                lastId = res.getInt(res.getColumnIndex(CROP_ESTIMATE_ID));
            }
        }
        int id = lastId + 1;
        closeDB();



        return "EST-"+String.format("%03d", id);
    }

    public void updateCropEstimate(CropEstimate estimate) {
        openDB();
        ContentValues contentValues = new ContentValues();


        contentValues.put(CROP_ESTIMATE_USER_ID,estimate.getUserId());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_ID,estimate.getCustomerId());
        contentValues.put(CROP_ESTIMATE_NO,estimate.getNumber());
        contentValues.put(CROP_ESTIMATE_DATE,estimate.getDate());
        contentValues.put(CROP_ESTIMATE_REFERENCE_NO,estimate.getReferenceNumber());
        contentValues.put(CROP_ESTIMATE_EXP_DATE,estimate.getExpiryDate());
        contentValues.put(CROP_ESTIMATE_STATUS,estimate.getStatus());
        contentValues.put(CROP_ESTIMATE_DISCOUNT,estimate.getDiscount());
        contentValues.put(CROP_ESTIMATE_SHIPPING_CHARGES,estimate.getShippingCharges());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_NOTES,estimate.getCustomerNotes());
        contentValues.put(CROP_ESTIMATE_TERMS_AND_CONDITIONS,estimate.getTermsAndConditions());


        database.update(CROP_ESTIMATE_TABLE_NAME, contentValues, CROP_ESTIMATE_ID + " = ?", new String[]{estimate.getId()});

        String estimateId = estimate.getId();

        ArrayList<CropProductItem> items = estimate.getItems();

        for (CropProductItem x : items) {
            contentValues.clear();

            contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
            contentValues.put(CROP_PRODUCT_ITEM_ESTIMATE_ID,estimateId);
            contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
            contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
            contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
            contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_ESTIMATE);
            if(x.getId() !=null){
                database.update(CROP_PRODUCT_ITEM_TABLE_NAME,contentValues,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{x.getId()});

            }
            else{
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }

        }

        for(String id: estimate.getDeletedItemsIds()){
            database.delete(CROP_PRODUCT_ITEM_TABLE_NAME,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{id});
        }

        closeDB();
    }

    public boolean deleteCropEstimate(String id) {
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_ESTIMATE_ID + " = ? AND "+CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id,CROP_PRODUCT_ITEM_TYPE_ESTIMATE});
        database.delete(CROP_ESTIMATE_TABLE_NAME, CROP_ESTIMATE_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public ArrayList<CropEstimate> getCropEstimates(String userId) {
        openDB();
        ArrayList<CropEstimate> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select "+CROP_ESTIMATE_TABLE_NAME+".*,"+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_NAME+" from "+CROP_ESTIMATE_TABLE_NAME+" LEFT JOIN "+CROP_CUSTOMER_TABLE_NAME+" ON "+CROP_ESTIMATE_TABLE_NAME+"."+CROP_ESTIMATE_CUSTOMER_ID+" = "+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_ID+" where "+CROP_ESTIMATE_TABLE_NAME+"."+CROP_ESTIMATE_USER_ID+" = "+ userId, null );
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropEstimate cropEstimate = new CropEstimate();
            cropEstimate.setId(res.getString(res.getColumnIndex(CROP_ESTIMATE_ID)));
            cropEstimate.setUserId(res.getString(res.getColumnIndex(CROP_ESTIMATE_USER_ID)));
            cropEstimate.setCustomerId(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_ID)));
            cropEstimate.setNumber(res.getString(res.getColumnIndex(CROP_ESTIMATE_NO)));
            cropEstimate.setReferenceNumber(res.getString(res.getColumnIndex(CROP_ESTIMATE_REFERENCE_NO)));
            cropEstimate.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropEstimate.setDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_DATE)));
            cropEstimate.setExpiryDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_EXP_DATE)));
            cropEstimate.setStatus(res.getString(res.getColumnIndex(CROP_ESTIMATE_STATUS)));
            cropEstimate.setDiscount(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_DISCOUNT)));
            cropEstimate.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_SHIPPING_CHARGES)));
            cropEstimate.setCustomerNotes(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_NOTES)));
            cropEstimate.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_ESTIMATE_TERMS_AND_CONDITIONS)));
            array_list.add(cropEstimate);
            res.moveToNext();
        }


        for (CropEstimate estimate : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery( "select * from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+CROP_PRODUCT_ITEM_ESTIMATE_ID+" = "+ estimate.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = "+CROP_PRODUCT_ITEM_TYPE_ESTIMATE, null );
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setInvoiceOrEstimateId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ESTIMATE_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                items_list.add(item);
                res.moveToNext();
            }
            estimate.setItems(items_list);
        }

        closeDB();

        return array_list;
    }

    public void insertCropProduct(CropProduct cropProduct) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PRODUCT_USER_ID, cropProduct.getUserId());
        contentValues.put(CROP_PRODUCT_NAME, cropProduct.getName());
        contentValues.put(CROP_PRODUCT_TYPE, cropProduct.getType());
        contentValues.put(CROP_PRODUCT_CODE, cropProduct.getCode());
        contentValues.put(CROP_PRODUCT_UNITS, cropProduct.getUnits());
        contentValues.put(CROP_PRODUCT_LINKED_ACCOUNT, cropProduct.getLinkedAccount());
        contentValues.put(CROP_PRODUCT_OPENING_COST, cropProduct.getOpeningCost());
        contentValues.put(CROP_PRODUCT_OPENING_QUANTITY, cropProduct.getOpeningQuantity());
        contentValues.put(CROP_PRODUCT_SELLING_PRICE, cropProduct.getSellingPrice());
        contentValues.put(CROP_PRODUCT_TAX_RATE, cropProduct.getTaxRate());
        contentValues.put(CROP_PRODUCT_DESCRIPTION, cropProduct.getDescription());
        database.insert(CROP_PRODUCT_TABLE_NAME, null, contentValues);

        closeDB();
    }

    public void updateCropProduct(CropProduct cropProduct) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PRODUCT_USER_ID, cropProduct.getUserId());
        contentValues.put(CROP_PRODUCT_NAME, cropProduct.getName());
        contentValues.put(CROP_PRODUCT_TYPE, cropProduct.getType());
        contentValues.put(CROP_PRODUCT_CODE, cropProduct.getCode());
        contentValues.put(CROP_PRODUCT_UNITS, cropProduct.getUnits());
        contentValues.put(CROP_PRODUCT_LINKED_ACCOUNT, cropProduct.getLinkedAccount());
        contentValues.put(CROP_PRODUCT_OPENING_COST, cropProduct.getOpeningCost());
        contentValues.put(CROP_PRODUCT_OPENING_QUANTITY, cropProduct.getOpeningQuantity());
        contentValues.put(CROP_PRODUCT_SELLING_PRICE, cropProduct.getSellingPrice());
        contentValues.put(CROP_PRODUCT_TAX_RATE, cropProduct.getTaxRate());
        contentValues.put(CROP_PRODUCT_DESCRIPTION, cropProduct.getDescription());
        database.update(CROP_PRODUCT_TABLE_NAME, contentValues, CROP_PRODUCT_ID + " = ?", new String[]{cropProduct.getId()});

        closeDB();

    }

    public boolean deleteCropProduct(String id) {
        openDB();
        database.delete(CROP_PRODUCT_TABLE_NAME, CROP_PRODUCT_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public ArrayList<CropProduct> getCropProducts(String fieldId) {
        openDB();
        ArrayList<CropProduct> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PRODUCT_TABLE_NAME/*+" where "+CROP_PRODUCT_USER_ID+" = "+fieldId*/, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
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
        Log.d("Crop Product", array_list.toString());
        return array_list;

    }

    public void insertCropSupplier(CropSupplier spraying) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SUPPLIER_USER_ID, spraying.getUserId());
        contentValues.put(CROP_SUPPLIER_NAME, spraying.getName());
        contentValues.put(CROP_SUPPLIER_COMPANY, spraying.getCompany());
        contentValues.put(CROP_SUPPLIER_TAX_REG_NO, spraying.getTaxRegNo());
        contentValues.put(CROP_SUPPLIER_PHONE, spraying.getPhone());
        contentValues.put(CROP_SUPPLIER_MOBILE, spraying.getMobile());
        contentValues.put(CROP_SUPPLIER_EMAIL, spraying.getEmail());
        contentValues.put(CROP_SUPPLIER_OPENING_BALANCE, spraying.getOpeningBalance());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_STREET, spraying.getInvoiceStreet());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_CITY, spraying.getInvoiceCityOrTown());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_COUNTRY, spraying.getInvoiceCountry());
        database.insert(CROP_SUPPLIER_TABLE_NAME, null, contentValues);

        closeDB();
    }

    public void updateCropSupplier(CropSupplier customer) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SUPPLIER_USER_ID, customer.getUserId());
        contentValues.put(CROP_SUPPLIER_NAME, customer.getName());
        contentValues.put(CROP_SUPPLIER_COMPANY, customer.getCompany());
        contentValues.put(CROP_SUPPLIER_TAX_REG_NO, customer.getTaxRegNo());
        contentValues.put(CROP_SUPPLIER_PHONE, customer.getPhone());
        contentValues.put(CROP_SUPPLIER_MOBILE, customer.getMobile());
        contentValues.put(CROP_SUPPLIER_EMAIL, customer.getEmail());
        contentValues.put(CROP_SUPPLIER_OPENING_BALANCE, customer.getOpeningBalance());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_STREET, customer.getInvoiceStreet());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_CITY, customer.getInvoiceCityOrTown());
        contentValues.put(CROP_SUPPLIER_INVOICE_ADDRESS_COUNTRY, customer.getInvoiceCountry());
        database.update(CROP_SUPPLIER_TABLE_NAME, contentValues, CROP_SUPPLIER_ID + " = ?", new String[]{customer.getId()});
        closeDB();
    }

    public boolean deleteCropSupplier(String id) {
        openDB();
        database.delete(CROP_SUPPLIER_TABLE_NAME, CROP_SUPPLIER_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public ArrayList<CropSupplier> getCropSuppliers(String fieldId) {
        openDB();
        ArrayList<CropSupplier> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SUPPLIER_TABLE_NAME + " where " + CROP_SUPPLIER_USER_ID + " = " + fieldId, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
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
        Log.d("Crop Analysis", array_list.toString());
        return array_list;

    }
    public CropSupplier getCropSupplier(String id) {
        openDB();
        ArrayList<CropSupplier> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SUPPLIER_TABLE_NAME + " where " + CROP_SUPPLIER_USER_ID + " = " + id, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
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
            return cropSupplier;
        }

        closeDB();

        return null;

    }


    public void insertCropCustomer(CropCustomer spraying) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CUSTOMER_USER_ID, spraying.getUserId());
        contentValues.put(CROP_CUSTOMER_NAME, spraying.getName());
        contentValues.put(CROP_CUSTOMER_COMPANY, spraying.getCompany());
        contentValues.put(CROP_CUSTOMER_TAX_REG_NO, spraying.getTaxRegNo());
        contentValues.put(CROP_CUSTOMER_PHONE, spraying.getPhone());
        contentValues.put(CROP_CUSTOMER_MOBILE, spraying.getMobile());
        contentValues.put(CROP_CUSTOMER_EMAIL, spraying.getEmail());
        contentValues.put(CROP_CUSTOMER_OPENING_BALANCE, spraying.getOpeningBalance());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_STREET, spraying.getBillingStreet());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_CITY, spraying.getBillingCityOrTown());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_COUNTRY, spraying.getBillingCountry());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_STREET, spraying.getShippingStreet());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_CITY, spraying.getShippingCityOrTown());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY, spraying.getShippingCountry());
        database.insert(CROP_CUSTOMER_TABLE_NAME, null, contentValues);

        closeDB();
    }

    public void updateCropCustomer(CropCustomer customer) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CUSTOMER_USER_ID, customer.getUserId());
        contentValues.put(CROP_CUSTOMER_NAME, customer.getName());
        contentValues.put(CROP_CUSTOMER_COMPANY, customer.getCompany());
        contentValues.put(CROP_CUSTOMER_TAX_REG_NO, customer.getTaxRegNo());
        contentValues.put(CROP_CUSTOMER_PHONE, customer.getPhone());
        contentValues.put(CROP_CUSTOMER_MOBILE, customer.getMobile());
        contentValues.put(CROP_CUSTOMER_EMAIL, customer.getEmail());
        contentValues.put(CROP_CUSTOMER_OPENING_BALANCE, customer.getOpeningBalance());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_STREET, customer.getBillingStreet());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_CITY, customer.getBillingCityOrTown());
        contentValues.put(CROP_CUSTOMER_BILL_ADDRESS_COUNTRY, customer.getBillingCountry());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_STREET, customer.getShippingStreet());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_CITY, customer.getShippingCityOrTown());
        contentValues.put(CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY, customer.getShippingCountry());
        database.update(CROP_CUSTOMER_TABLE_NAME, contentValues, CROP_CUSTOMER_ID + " = ?", new String[]{customer.getId()});
        closeDB();
    }

    public boolean deleteCropCustomer(String id) {
        openDB();
        database.delete(CROP_CUSTOMER_TABLE_NAME, CROP_CUSTOMER_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public ArrayList<CropCustomer> getCropCustomers(String fieldId) {
        openDB();
        ArrayList<CropCustomer> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CUSTOMER_TABLE_NAME + " where " + CROP_CUSTOMER_USER_ID + " = " + fieldId, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
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
        Log.d("Crop Analysis", array_list.toString());
        return array_list;

    }



    public CropCustomer getCropCustomer(String customerId){
        openDB();
        ArrayList<CropCustomer> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_CUSTOMER_TABLE_NAME+" where "+CROP_CUSTOMER_ID+" = "+customerId, null );
        res.moveToFirst();
        
        if(!res.isAfterLast()){
            CropCustomer cropCustomer = new CropCustomer();
            cropCustomer.setId(res.getString(res.getColumnIndex(CROP_CUSTOMER_ID)));
            cropCustomer.setUserId(res.getString(res.getColumnIndex(CROP_CUSTOMER_USER_ID)));
            cropCustomer.setName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropCustomer.setCompany(res.getString(res.getColumnIndex(CROP_CUSTOMER_COMPANY)));
            cropCustomer.setTaxRegNo(res.getString(res.getColumnIndex(CROP_CUSTOMER_TAX_REG_NO)));
            cropCustomer.setPhone(res.getString(res.getColumnIndex(CROP_CUSTOMER_PHONE)));
            cropCustomer.setMobile(res.getString(res.getColumnIndex(CROP_CUSTOMER_MOBILE)));
            cropCustomer.setEmail(res.getString(res.getColumnIndex(CROP_CUSTOMER_EMAIL)));
            cropCustomer.setOpeningBalance(res.getFloat(res.getColumnIndex(CROP_CUSTOMER_OPENING_BALANCE)));
            cropCustomer.setBillingStreet(res.getString(res.getColumnIndex(CROP_CUSTOMER_BILL_ADDRESS_STREET)));
            cropCustomer.setBillingCityOrTown(res.getString(res.getColumnIndex(CROP_CUSTOMER_BILL_ADDRESS_CITY)));
            cropCustomer.setBillingCountry(res.getString(res.getColumnIndex(CROP_CUSTOMER_BILL_ADDRESS_COUNTRY)));
            cropCustomer.setShippingStreet(res.getString(res.getColumnIndex(CROP_CUSTOMER_SHIP_ADDRESS_STREET)));
            cropCustomer.setShippingCityOrTown(res.getString(res.getColumnIndex(CROP_CUSTOMER_SHIP_ADDRESS_CITY)));
            cropCustomer.setShippingCountry(res.getString(res.getColumnIndex(CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY)));

            return cropCustomer;
         
        }

        closeDB();
        return null;

    }
    public void  insertCropEmployee(CropEmployee spraying){

        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_EMPLOYEE_USER_ID, spraying.getUserId());
        contentValues.put(CROP_EMPLOYEE_TITLE, spraying.getTitle());
        contentValues.put(CROP_EMPLOYEE_FIRST_NAME, spraying.getFirstName());
        contentValues.put(CROP_EMPLOYEE_LAST_NAME, spraying.getLastName());
        contentValues.put(CROP_EMPLOYEE_PHONE, spraying.getPhone());
        contentValues.put(CROP_EMPLOYEE_MOBILE, spraying.getMobile());
        contentValues.put(CROP_EMPLOYEE_EMP_ID, spraying.getEmployeeId());
        contentValues.put(CROP_EMPLOYEE_GENDER, spraying.getGender());
        contentValues.put(CROP_EMPLOYEE_ADDRESS, spraying.getAddress());
        contentValues.put(CROP_EMPLOYEE_EMAIL, spraying.getEmail());
        contentValues.put(CROP_EMPLOYEE_DOB, spraying.getDateOfBirth());
        contentValues.put(CROP_EMPLOYEE_HIRE_DATE, spraying.getHireDate());
        contentValues.put(CROP_EMPLOYEE_EMPLOYMENT_STATUS, spraying.getEmploymentStatus());
        contentValues.put(CROP_EMPLOYEE_PAY_AMOUNT, spraying.getPayAmount());
        contentValues.put(CROP_EMPLOYEE_PAY_RATE, spraying.getPayRate());
        contentValues.put(CROP_EMPLOYEE_PAY_TYPE, spraying.getPayType());
        contentValues.put(CROP_EMPLOYEE_SUPERVISOR, spraying.getSupervisor());
        database.insert(CROP_EMPLOYEE_TABLE_NAME, null, contentValues);
        /*
        public static final String  ="id";

         */
        closeDB();
    }

    public void updateCropEmployee(CropEmployee s) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_EMPLOYEE_USER_ID, s.getUserId());
        contentValues.put(CROP_EMPLOYEE_TITLE, s.getTitle());
        contentValues.put(CROP_EMPLOYEE_FIRST_NAME, s.getFirstName());
        contentValues.put(CROP_EMPLOYEE_LAST_NAME, s.getLastName());
        contentValues.put(CROP_EMPLOYEE_PHONE, s.getPhone());
        contentValues.put(CROP_EMPLOYEE_MOBILE, s.getMobile());
        contentValues.put(CROP_EMPLOYEE_EMP_ID, s.getEmployeeId());
        contentValues.put(CROP_EMPLOYEE_GENDER, s.getGender());
        contentValues.put(CROP_EMPLOYEE_ADDRESS, s.getAddress());
        contentValues.put(CROP_EMPLOYEE_EMAIL, s.getEmail());
        contentValues.put(CROP_EMPLOYEE_DOB, s.getDateOfBirth());
        contentValues.put(CROP_EMPLOYEE_HIRE_DATE, s.getHireDate());
        contentValues.put(CROP_EMPLOYEE_EMPLOYMENT_STATUS, s.getEmploymentStatus());
        contentValues.put(CROP_EMPLOYEE_PAY_AMOUNT, s.getPayAmount());
        contentValues.put(CROP_EMPLOYEE_PAY_RATE, s.getPayRate());
        contentValues.put(CROP_EMPLOYEE_PAY_TYPE, s.getPayType());
        contentValues.put(CROP_EMPLOYEE_SUPERVISOR, s.getSupervisor());
        database.update(CROP_EMPLOYEE_TABLE_NAME, contentValues, CROP_EMPLOYEE_ID + " = ?", new String[]{s.getId()});
        closeDB();
    }

    public boolean deleteCropEmployee(String id) {
        openDB();
        database.delete(CROP_EMPLOYEE_TABLE_NAME, CROP_EMPLOYEE_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public ArrayList<CropEmployee> getCropEmployee(String fieldId) {
        openDB();
        ArrayList<CropEmployee> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_EMPLOYEE_TABLE_NAME + " where " + CROP_EMPLOYEE_USER_ID + " = " + fieldId, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
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
        Log.d("Crop Analysis", array_list.toString());
        return array_list;

    }

    public void insertCropSoilAnalysis(CropSoilAnalysis spraying) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SOIL_ANALYSIS_DATE, spraying.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_USER_ID, spraying.getUserId());
        contentValues.put(CROP_SOIL_ANALYSIS_FIELD_ID, spraying.getFieldId());
        contentValues.put(CROP_SOIL_ANALYSIS_DATE, spraying.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_PH, spraying.getPh());
        contentValues.put(CROP_SOIL_ANALYSIS_AGRONOMIST, spraying.getAgronomist());
        contentValues.put(CROP_SOIL_ANALYSIS_RESULTS, spraying.getResult());
        contentValues.put(CROP_SOIL_ANALYSIS_COST, spraying.getCost());
        contentValues.put(CROP_SOIL_ANALYSIS_ORGANIC_MATTER, spraying.getOrganicMatter());
        database.insert(CROP_SOIL_ANALYSIS_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropSoilAnalysis(CropSoilAnalysis spraying) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SOIL_ANALYSIS_DATE, spraying.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_USER_ID, spraying.getUserId());
        contentValues.put(CROP_SOIL_ANALYSIS_FIELD_ID, spraying.getFieldId());
        contentValues.put(CROP_SOIL_ANALYSIS_DATE, spraying.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_PH, spraying.getPh());
        contentValues.put(CROP_SOIL_ANALYSIS_AGRONOMIST, spraying.getAgronomist());
        contentValues.put(CROP_SOIL_ANALYSIS_RESULTS, spraying.getResult());
        contentValues.put(CROP_SOIL_ANALYSIS_COST, spraying.getCost());
        contentValues.put(CROP_SOIL_ANALYSIS_ORGANIC_MATTER, spraying.getOrganicMatter());
        database.update(CROP_SOIL_ANALYSIS_TABLE_NAME, contentValues, CROP_SOIL_ANALYSIS_ID + " = ?", new String[]{spraying.getId()});
        closeDB();
    }

    public boolean deleteCropSoilAnalysis(String id) {
        openDB();
        database.delete(CROP_SOIL_ANALYSIS_TABLE_NAME, CROP_SOIL_ANALYSIS_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public ArrayList<CropSoilAnalysis> getCropSoilAnalysis(String fieldId) {
        openDB();
        ArrayList<CropSoilAnalysis> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SOIL_ANALYSIS_TABLE_NAME + " where " + CROP_SOIL_ANALYSIS_FIELD_ID + " = " + fieldId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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
        Log.d("Crop Analysis", array_list.toString());
        return array_list;

    }

    public void insertCropSpraying(CropSpraying spraying) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SPRAYING_DATE, spraying.getDate());
        contentValues.put(CROP_SPRAYING_USER_ID, spraying.getUserId());
        contentValues.put(CROP_SPRAYING_CROP_ID, spraying.getCropId());
        contentValues.put(CROP_SPRAYING_DATE, spraying.getDate());
        contentValues.put(CROP_SPRAYING_START_TIME, spraying.getStartTime());
        contentValues.put(CROP_SPRAYING_END_TIME, spraying.getEndTime());
        contentValues.put(CROP_SPRAYING_OPERATOR, spraying.getOperator());
        contentValues.put(CROP_SPRAYING_WATER_CONDITION, spraying.getWaterCondition());
        contentValues.put(CROP_SPRAYING_WATER_VOLUME, spraying.getWaterVolume());
        contentValues.put(CROP_SPRAYING_WIND_DIRECTION, spraying.getWindDirection());
        contentValues.put(CROP_SPRAYING_TREATMENT_REASON, spraying.getTreatmentReason());
        contentValues.put(CROP_SPRAYING_EQUIPMENT_USED, spraying.getEquipmentUsed());
        contentValues.put(CROP_SPRAYING_SPRAY_ID, spraying.getSprayId());
        contentValues.put(CROP_SPRAYING_OPERATOR, spraying.getOperator());
        contentValues.put(CROP_SPRAYING_COST, spraying.getCost());
        contentValues.put(CROP_SPRAYING_RATE, spraying.getRate());
        database.insert(CROP_SPRAYING_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropSpraying(CropSpraying spraying) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SPRAYING_DATE, spraying.getDate());
        contentValues.put(CROP_SPRAYING_USER_ID, spraying.getUserId());
        contentValues.put(CROP_SPRAYING_CROP_ID, spraying.getCropId());
        contentValues.put(CROP_SPRAYING_DATE, spraying.getDate());
        contentValues.put(CROP_SPRAYING_START_TIME, spraying.getStartTime());
        contentValues.put(CROP_SPRAYING_END_TIME, spraying.getEndTime());
        contentValues.put(CROP_SPRAYING_OPERATOR, spraying.getOperator());
        contentValues.put(CROP_SPRAYING_WATER_CONDITION, spraying.getWaterCondition());
        contentValues.put(CROP_SPRAYING_WATER_VOLUME, spraying.getWaterVolume());
        contentValues.put(CROP_SPRAYING_WIND_DIRECTION, spraying.getWindDirection());
        contentValues.put(CROP_SPRAYING_TREATMENT_REASON, spraying.getTreatmentReason());
        contentValues.put(CROP_SPRAYING_EQUIPMENT_USED, spraying.getEquipmentUsed());
        contentValues.put(CROP_SPRAYING_SPRAY_ID, spraying.getSprayId());
        contentValues.put(CROP_SPRAYING_OPERATOR, spraying.getOperator());
        contentValues.put(CROP_SPRAYING_COST, spraying.getCost());
        contentValues.put(CROP_SPRAYING_RATE, spraying.getRate());
        database.update(CROP_SPRAYING_TABLE_NAME, contentValues, CROP_SPRAYING_ID + " = ?", new String[]{spraying.getId()});
        closeDB();
    }

    public boolean deleteCropSpraying(String fertilizerId) {
        openDB();
        database.delete(CROP_SPRAYING_TABLE_NAME, CROP_SPRAYING_ID + " = ?", new String[]{fertilizerId});
        closeDB();
        return true;
    }

    public ArrayList<CropSpraying> getCropSprayings(String cropId) {
        openDB();
        ArrayList<CropSpraying> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + CROP_SPRAYING_TABLE_NAME + ".*," + CROP_INVENTORY_SPRAY_TABLE_NAME + "." + CROP_INVENTORY_SPRAY_NAME + " from " + CROP_SPRAYING_TABLE_NAME + " LEFT JOIN " + CROP_INVENTORY_SPRAY_TABLE_NAME + " ON " + CROP_SPRAYING_TABLE_NAME + "." + CROP_SPRAYING_SPRAY_ID + " = " + CROP_INVENTORY_SPRAY_TABLE_NAME + "." + CROP_INVENTORY_SPRAY_ID + " where " + CROP_SPRAYING_CROP_ID + " = " + cropId;
        Log.d("QUERY", query);
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropSpraying crop = new CropSpraying();
            Log.d("COLUMN", res.getColumnIndex(CROP_SPRAYING_START_TIME) + "");
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

    public void insertCropFertilizerApplication(CropFertilizerApplication fertilizerApplication) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_FERTILIZER_APPLICATION_DATE, fertilizerApplication.getDate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_USER_ID, fertilizerApplication.getUserId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_CROP_ID, fertilizerApplication.getCropId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_DATE, fertilizerApplication.getDate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR, fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_METHOD, fertilizerApplication.getMethod());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REASON, fertilizerApplication.getReason());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM, fertilizerApplication.getFertilizerForm());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_ID, fertilizerApplication.getFertilizerId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR, fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_COST, fertilizerApplication.getCost());
        contentValues.put(CROP_FERTILIZER_APPLICATION_RATE, fertilizerApplication.getRate());
        database.insert(CROP_FERTILIZER_APPLICATION_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropFertilizerApplication(CropFertilizerApplication fertilizerApplication) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_FERTILIZER_APPLICATION_DATE, fertilizerApplication.getDate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_USER_ID, fertilizerApplication.getUserId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_CROP_ID, fertilizerApplication.getCropId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_DATE, fertilizerApplication.getDate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR, fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_METHOD, fertilizerApplication.getMethod());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REASON, fertilizerApplication.getReason());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM, fertilizerApplication.getFertilizerForm());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_ID, fertilizerApplication.getFertilizerId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR, fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_COST, fertilizerApplication.getCost());
        contentValues.put(CROP_FERTILIZER_APPLICATION_RATE, fertilizerApplication.getRate());
        database.update(CROP_FERTILIZER_APPLICATION_TABLE_NAME, contentValues, CROP_FERTILIZER_APPLICATION_ID + " = ?", new String[]{fertilizerApplication.getId()});
        closeDB();
    }

    public boolean deleteCropFertilizerApplication(String fertilizerId) {
        openDB();
        database.delete(CROP_FERTILIZER_APPLICATION_TABLE_NAME, CROP_FERTILIZER_APPLICATION_ID + " = ?", new String[]{fertilizerId});
        closeDB();
        return true;
    }

    public ArrayList<CropFertilizerApplication> getCropFertilizerApplication(String cropId) {
        openDB();
        ArrayList<CropFertilizerApplication> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+" where "+CROP_FERTILIZER_APPLICATION_CROP_ID+" = "+cropId, null );

        String query = "select " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + ".*," + CROP_INVENTORY_FERTILIZER_TABLE_NAME + "." + CROP_INVENTORY_FERTILIZER_NAME + " from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " LEFT JOIN " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + " ON " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + "." + CROP_FERTILIZER_APPLICATION_ID + " = " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + "." + CROP_INVENTORY_FERTILIZER_ID + " where " + CROP_FERTILIZER_APPLICATION_CROP_ID + " = " + cropId;

        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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

    public void insertCropCultivate(CropCultivation cropCultivation) {
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
        database.insert(CROP_CULTIVATION_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropCultivate(CropCultivation cropCultivation) {
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
        database.update(CROP_CULTIVATION_TABLE_NAME, contentValues, CROP_CULTIVATION_ID + " = ?", new String[]{cropCultivation.getId()});

        closeDB();
    }

    public boolean deleteCropCultivate(String cultivateId) {
        openDB();
        database.delete(CROP_CULTIVATION_TABLE_NAME, CROP_CULTIVATION_ID + " = ?", new String[]{cultivateId});
        closeDB();
        return true;
    }

    public ArrayList<CropCultivation> getCropCultivates(String cropId) {
        openDB();
        ArrayList<CropCultivation> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CULTIVATION_TABLE_NAME + " where " + CROP_CULTIVATION_CROP_ID + " = " + cropId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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

    public void insertCrop(Crop inventorySpray) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CROP_DATE_SOWN, inventorySpray.getDateSown());
        contentValues.put(CROP_CROP_USER_ID, inventorySpray.getUserId());
        contentValues.put(CROP_CROP_VARIETY, inventorySpray.getVariety());
        contentValues.put(CROP_CROP_AREA, inventorySpray.getArea());
        contentValues.put(CROP_CROP_COST, inventorySpray.getCost());
        contentValues.put(CROP_CROP_NAME, inventorySpray.getName());
        contentValues.put(CROP_CROP_YEAR, inventorySpray.getCroppingYear());
        contentValues.put(CROP_CROP_OPERATOR, inventorySpray.getOperator());
        contentValues.put(CROP_CROP_FIELD_ID, inventorySpray.getFieldId());
        contentValues.put(CROP_CROP_GROWING_CYCLE, inventorySpray.getGrowingCycle());
        contentValues.put(CROP_CROP_SEASON, inventorySpray.getSeason());
        contentValues.put(CROP_CROP_COST, inventorySpray.getCost());
        contentValues.put(CROP_CROP_SEED_ID, inventorySpray.getSeedId());
        contentValues.put(CROP_CROP_RATE, inventorySpray.getRate());
        contentValues.put(CROP_CROP_PLANTING_METHOD, inventorySpray.getPlantingMethod());
        database.insert(CROP_CROP_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCrop(Crop inventorySpray) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CROP_DATE_SOWN, inventorySpray.getDateSown());
        contentValues.put(CROP_CROP_USER_ID, inventorySpray.getUserId());
        contentValues.put(CROP_CROP_VARIETY, inventorySpray.getVariety());
        contentValues.put(CROP_CROP_AREA, inventorySpray.getArea());
        contentValues.put(CROP_CROP_COST, inventorySpray.getCost());
        contentValues.put(CROP_CROP_YEAR, inventorySpray.getCroppingYear());
        contentValues.put(CROP_CROP_OPERATOR, inventorySpray.getOperator());
        contentValues.put(CROP_CROP_FIELD_ID, inventorySpray.getFieldId());
        contentValues.put(CROP_CROP_GROWING_CYCLE, inventorySpray.getGrowingCycle());
        contentValues.put(CROP_CROP_SEASON, inventorySpray.getSeason());
        contentValues.put(CROP_CROP_COST, inventorySpray.getCost());
        contentValues.put(CROP_CROP_SEED_ID, inventorySpray.getSeedId());
        contentValues.put(CROP_CROP_RATE, inventorySpray.getRate());
        contentValues.put(CROP_CROP_PLANTING_METHOD, inventorySpray.getPlantingMethod());
        database.update(CROP_CROP_TABLE_NAME, contentValues, CROP_CROP_ID + " = ?", new String[]{inventorySpray.getId()});

        closeDB();
    }

    public boolean deleteCrop(String cropId) {
        openDB();
        database.delete(CROP_CROP_TABLE_NAME, CROP_CROP_ID + " = ?", new String[]{cropId});
        closeDB();
        return true;
    }

    public ArrayList<Crop> getCropsInField(String fieldId){
        openDB();
        ArrayList<Crop> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+CROP_CROP_TABLE_NAME+" where "+CROP_CROP_FIELD_ID+" = "+fieldId, null );
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
            crop.setGrowingCycle(res.getString(res.getColumnIndex(CROP_CROP_GROWING_CYCLE)));
            crop.setSeason(res.getString(res.getColumnIndex(CROP_CROP_SEASON)));
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_CROP_RATE)));
            crop.setPlantingMethod(res.getString(res.getColumnIndex(CROP_CROP_PLANTING_METHOD)));
            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
        Log.d("TESTING",array_list.toString());
        return array_list;

    }
    public ArrayList<Crop> getCrops(String userId){

        openDB();
        ArrayList<Crop> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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
            crop.setGrowingCycle(res.getString(res.getColumnIndex(CROP_CROP_GROWING_CYCLE)));
            crop.setSeason(res.getString(res.getColumnIndex(CROP_CROP_SEASON)));
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_CROP_RATE)));
            crop.setPlantingMethod(res.getString(res.getColumnIndex(CROP_CROP_PLANTING_METHOD)));
            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
        Log.d("TESTING", array_list.toString());
        return array_list;

    }

    public void insertCropSpray(CropInventorySpray inventorySpray) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SPRAY_DATE, inventorySpray.getDateOfPurchase());
        contentValues.put(CROP_INVENTORY_SPRAY_USER_ID, inventorySpray.getUserId());
        contentValues.put(CROP_INVENTORY_SPRAY_TYPE, inventorySpray.getType());
        contentValues.put(CROP_INVENTORY_SPRAY_QUANTITY, inventorySpray.getQuantity());
        contentValues.put(CROP_INVENTORY_SPRAY_COST, inventorySpray.getCost());
        contentValues.put(CROP_INVENTORY_SPRAY_NAME, inventorySpray.getName());
        contentValues.put(CROP_INVENTORY_SPRAY_BATCH_NUMBER, inventorySpray.getBatchNumber());
        contentValues.put(CROP_INVENTORY_SPRAY_SUPPLIER, inventorySpray.getSupplier());
        contentValues.put(CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS, inventorySpray.getActiveIngredients());
        contentValues.put(CROP_INVENTORY_SPRAY_USAGE_UNIT, inventorySpray.getUsageUnits());
        contentValues.put(CROP_INVENTORY_SPRAY_EXPIRY_DATE, inventorySpray.getExpiryDate());
        contentValues.put(CROP_INVENTORY_SPRAY_HARVEST_INTERVAL, inventorySpray.getHarvestInterval());
        database.insert(CROP_INVENTORY_SPRAY_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropSpray(CropInventorySpray inventorySpray) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SPRAY_DATE, inventorySpray.getDateOfPurchase());
        contentValues.put(CROP_INVENTORY_SPRAY_USER_ID, inventorySpray.getUserId());
        contentValues.put(CROP_INVENTORY_SPRAY_TYPE, inventorySpray.getType());
        contentValues.put(CROP_INVENTORY_SPRAY_QUANTITY, inventorySpray.getQuantity());
        contentValues.put(CROP_INVENTORY_SPRAY_COST, inventorySpray.getCost());
        contentValues.put(CROP_INVENTORY_SPRAY_NAME, inventorySpray.getName());
        contentValues.put(CROP_INVENTORY_SPRAY_BATCH_NUMBER, inventorySpray.getBatchNumber());
        contentValues.put(CROP_INVENTORY_SPRAY_SUPPLIER, inventorySpray.getSupplier());
        contentValues.put(CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS, inventorySpray.getActiveIngredients());
        contentValues.put(CROP_INVENTORY_SPRAY_USAGE_UNIT, inventorySpray.getUsageUnits());
        contentValues.put(CROP_INVENTORY_SPRAY_EXPIRY_DATE, inventorySpray.getExpiryDate());
        contentValues.put(CROP_INVENTORY_SPRAY_HARVEST_INTERVAL, inventorySpray.getHarvestInterval());
        database.update(CROP_INVENTORY_SPRAY_TABLE_NAME, contentValues, CROP_INVENTORY_SPRAY_ID + " = ?", new String[]{inventorySpray.getId()});

        closeDB();
    }

    public boolean deleteCropSpray(String sprayId) {
        openDB();
        database.delete(CROP_INVENTORY_SPRAY_TABLE_NAME, CROP_INVENTORY_SPRAY_ID + " = ?", new String[]{sprayId});
        closeDB();
        return true;
    }


    public ArrayList<CropInventorySpray> getCropSpray(String userId) {
        openDB();
        ArrayList<CropInventorySpray> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_SPRAY_TABLE_NAME + " where " + CROP_INVENTORY_SPRAY_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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

        for (CropInventorySpray spray : array_list) {
            String query = "select SUM("+CROP_SPRAYING_RATE+") as totalConsumed from " + CROP_SPRAYING_TABLE_NAME +  " where " + CROP_SPRAYING_SPRAY_ID + " = " + spray.getId();
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if(!res.isAfterLast()){
                spray.setTotalConsumed(res.getFloat(res.getColumnIndex("totalConsumed")));
            }

        }
        closeDB();
        return array_list;

    }

    public void insertCropSeeds(CropInventorySeeds inventorySeeds) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SEEDS_DATE, inventorySeeds.getDateOfPurchase());
        contentValues.put(CROP_INVENTORY_SEEDS_USER_ID, inventorySeeds.getUserId());
        contentValues.put(CROP_INVENTORY_SEEDS_VARIETY, inventorySeeds.getVariety());
        contentValues.put(CROP_INVENTORY_SEEDS_NAME, inventorySeeds.getName());
        contentValues.put(CROP_INVENTORY_SEEDS_DRESSING, inventorySeeds.getDressing());
        contentValues.put(CROP_INVENTORY_SEEDS_QUANTITY, inventorySeeds.getQuantity());
        contentValues.put(CROP_INVENTORY_SEEDS_COST, inventorySeeds.getCost());
        contentValues.put(CROP_INVENTORY_SEEDS_BATCH_NUMBER, inventorySeeds.getBatchNumber());
        contentValues.put(CROP_INVENTORY_SEEDS_SUPPLIER, inventorySeeds.getSupplier());
        contentValues.put(CROP_INVENTORY_SEEDS_TGW, inventorySeeds.getTgw());
        contentValues.put(CROP_INVENTORY_SEEDS_USAGE_UNIT, inventorySeeds.getUsageUnits());
        database.insert(CROP_INVENTORY_SEEDS_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropSeeds(CropInventorySeeds inventorySeeds) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SEEDS_DATE, inventorySeeds.getDateOfPurchase());
        contentValues.put(CROP_INVENTORY_SEEDS_USER_ID, inventorySeeds.getUserId());
        contentValues.put(CROP_INVENTORY_SEEDS_VARIETY, inventorySeeds.getVariety());
        contentValues.put(CROP_INVENTORY_SEEDS_NAME, inventorySeeds.getName());
        contentValues.put(CROP_INVENTORY_SEEDS_DRESSING, inventorySeeds.getDressing());
        contentValues.put(CROP_INVENTORY_SEEDS_QUANTITY, inventorySeeds.getQuantity());
        contentValues.put(CROP_INVENTORY_SEEDS_COST, inventorySeeds.getCost());
        contentValues.put(CROP_INVENTORY_SEEDS_BATCH_NUMBER, inventorySeeds.getBatchNumber());
        contentValues.put(CROP_INVENTORY_SEEDS_SUPPLIER, inventorySeeds.getSupplier());
        contentValues.put(CROP_INVENTORY_SEEDS_TGW, inventorySeeds.getTgw());
        contentValues.put(CROP_INVENTORY_SEEDS_USAGE_UNIT, inventorySeeds.getUsageUnits());
        database.update(CROP_INVENTORY_SEEDS_TABLE_NAME, contentValues, CROP_INVENTORY_SEEDS_ID + " = ?", new String[]{inventorySeeds.getId()});
        closeDB();
    }

    public boolean deleteCropSeeds(String seedsId) {
        openDB();
        database.delete(CROP_INVENTORY_SEEDS_TABLE_NAME, CROP_INVENTORY_SEEDS_ID + " = ?", new String[]{seedsId});
        closeDB();
        return true;
    }

    public ArrayList<CropInventorySeeds> getCropSeeds(String userId) {
        openDB();
        ArrayList<CropInventorySeeds> array_list = new ArrayList();
        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_SEEDS_TABLE_NAME + " where " + CROP_INVENTORY_SEEDS_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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
    public void insertCropField(CropField field) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_FIELD_USER_ID, field.getUserId());
        contentValues.put(CROP_FIELD_NAME, field.getFieldName());
        contentValues.put(CROP_FIELD_SOIL_CATEGORY, field.getSoilCategory());
        contentValues.put(CROP_FIELD_SOIL_TYPE, field.getSoilType());
        contentValues.put(CROP_FIELD_TOTAL_AREA, field.getTotalArea());
        contentValues.put(CROP_FIELD_CROPPABLE_AREA, field.getCroppableArea());
        contentValues.put(CROP_FIELD_UNITS, field.getUnits());

        database.insert(CROP_FIELDS_TABLE_NAME, null, contentValues);
        closeDB();
    }


    public void insertCropFertilizerInventory(CropInventoryFertilizer fertilizer) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_FERTILIZER_DATE, fertilizer.getPurchaseDate());
        contentValues.put(CROP_INVENTORY_FERTILIZER_USER_ID, fertilizer.getUserId());
        contentValues.put(CROP_INVENTORY_FERTILIZER_TYPE, fertilizer.getType());
        contentValues.put(CROP_INVENTORY_FERTILIZER_NAME, fertilizer.getName());
        contentValues.put(CROP_INVENTORY_FERTILIZER_N_PERCENTAGE, fertilizer.getnPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_K_PERCENTAGE, fertilizer.getkPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_P_PERCENTAGE, fertilizer.getpPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_QUANTITY, fertilizer.getQuantity());
        contentValues.put(CROP_INVENTORY_FERTILIZER_BATCH_NUMBER, fertilizer.getBatchNumber());
        contentValues.put(CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER, fertilizer.getSerialNumber());
        contentValues.put(CROP_INVENTORY_FERTILIZER_SUPPLIER, fertilizer.getSupplier());
        contentValues.put(CROP_INVENTORY_FERTILIZER_USAGE_UNIT, fertilizer.getUsageUnit());
        contentValues.put(CROP_INVENTORY_FERTILIZER_COST, fertilizer.getCost());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA, fertilizer.getMacroNutrientsCa());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG, fertilizer.getMacroNutrientsMg());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S, fertilizer.getMacroNutrientsS());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B, fertilizer.getMicroNutrientsB());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN, fertilizer.getMicroNutrientsMn());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO, fertilizer.getMicroNutrientsMo());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL, fertilizer.getMicroNutrientsCl());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU, fertilizer.getMicroNutrientsCu());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN, fertilizer.getMicroNutrientsZn());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE, fertilizer.getMicroNutrientsFe());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA, fertilizer.getMicroNutrientsNa());

        database.insert(CROP_INVENTORY_FERTILIZER_TABLE_NAME, null, contentValues);
        closeDB();
    }
    public void updateCropFertilizerInventory(CropInventoryFertilizer fertilizer) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_FERTILIZER_DATE, fertilizer.getPurchaseDate());
        contentValues.put(CROP_INVENTORY_FERTILIZER_USER_ID, fertilizer.getUserId());
        contentValues.put(CROP_INVENTORY_FERTILIZER_TYPE, fertilizer.getType());
        contentValues.put(CROP_INVENTORY_FERTILIZER_NAME, fertilizer.getName());
        contentValues.put(CROP_INVENTORY_FERTILIZER_N_PERCENTAGE, fertilizer.getnPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_K_PERCENTAGE, fertilizer.getkPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_P_PERCENTAGE, fertilizer.getpPercentage());
        contentValues.put(CROP_INVENTORY_FERTILIZER_QUANTITY, fertilizer.getQuantity());
        contentValues.put(CROP_INVENTORY_FERTILIZER_BATCH_NUMBER, fertilizer.getBatchNumber());
        contentValues.put(CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER, fertilizer.getSerialNumber());
        contentValues.put(CROP_INVENTORY_FERTILIZER_SUPPLIER, fertilizer.getSupplier());
        contentValues.put(CROP_INVENTORY_FERTILIZER_USAGE_UNIT, fertilizer.getUsageUnit());
        contentValues.put(CROP_INVENTORY_FERTILIZER_COST, fertilizer.getCost());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA, fertilizer.getMacroNutrientsCa());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG, fertilizer.getMacroNutrientsMg());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S, fertilizer.getMacroNutrientsS());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B, fertilizer.getMicroNutrientsB());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN, fertilizer.getMicroNutrientsMn());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO, fertilizer.getMicroNutrientsMo());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL, fertilizer.getMicroNutrientsCl());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU, fertilizer.getMicroNutrientsCu());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN, fertilizer.getMicroNutrientsZn());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE, fertilizer.getMicroNutrientsFe());
        contentValues.put(CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA, fertilizer.getMicroNutrientsNa());

        database.update(CROP_INVENTORY_FERTILIZER_TABLE_NAME, contentValues, CROP_INVENTORY_FERTILIZER_ID + " = ?", new String[]{fertilizer.getId()});
        closeDB();
    }
    public boolean deleteCropFertilizerInventory(String fertilizerId) {
        openDB();
        database.delete(CROP_INVENTORY_FERTILIZER_TABLE_NAME, CROP_INVENTORY_FERTILIZER_ID + " = ?", new String[]{fertilizerId});
        closeDB();
        return true;
    }
    public ArrayList<CropInventoryFertilizer> getCropFertilizerInventorys(String userId) {
        openDB();
        ArrayList<CropInventoryFertilizer> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + " where " + CROP_INVENTORY_FERTILIZER_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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



        for (CropInventoryFertilizer fertilizer : array_list) {
            String query = "select SUM("+CROP_FERTILIZER_APPLICATION_RATE+") as totalConsumed from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME +  " where " + CROP_FERTILIZER_APPLICATION_FERTILIZER_ID + " = " + fertilizer.getId();
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if(!res.isAfterLast()){
                fertilizer.setTotalConsumed(res.getFloat(res.getColumnIndex("totalConsumed")));
            }

        }
        closeDB();

        //Log.d("HOUSES SIZE",array_list.size()+"");
        return array_list;

    }

    public void updateCropField(CropField field) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_FIELD_USER_ID,field.getUserId());
        contentValues.put(CROP_FIELD_NAME,field.getFieldName());
        contentValues.put(CROP_FIELD_SOIL_CATEGORY,field.getSoilCategory());
        contentValues.put(CROP_FIELD_SOIL_TYPE,field.getSoilType());
        contentValues.put(CROP_FIELD_WATERCOURSE,field.getWatercourse());
        contentValues.put(CROP_FIELD_UNITS,field.getUnits());
        contentValues.put(CROP_FIELD_TOTAL_AREA,field.getTotalArea());
        contentValues.put(CROP_FIELD_CROPPABLE_AREA,field.getCroppableArea());

        database.update(CROP_FIELDS_TABLE_NAME, contentValues, CROP_FIELD_ID + " = ?", new String[]{field.getId()});
        closeDB();
    }


    public boolean deleteCropField(String fieldId) {
        openDB();
        database.delete(CROP_FIELDS_TABLE_NAME, CROP_FIELD_ID + " = ?", new String[]{fieldId});
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


    public void insertCropMachine(CropMachine machine) {
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

        database.insert(CROP_MACHINE_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropMachine(CropMachine machine) {
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

        database.update(CROP_MACHINE_TABLE_NAME, contentValues, CROP_MACHINE_ID + " = ?", new String[]{machine.getId()});

        closeDB();
    }

    public boolean deleteCropMachine(String machineId) {
        openDB();
        database.delete(CROP_MACHINE_TABLE_NAME, CROP_MACHINE_ID + " = ?", new String[]{machineId});
        closeDB();
        return true;
    }

    public ArrayList<CropMachine> getCropMachines(String userId) {
        openDB();
        ArrayList<CropMachine> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_MACHINE_TABLE_NAME + " where " + CROP_MACHINE_USER_ID + " = " + userId, null);
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

            machine.setRegistrationNumber(res.getString(res.getColumnIndex(CROP_MACHINE_REGISTRATION_NUMBER)));
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


    public void  insertCropIncomeExpense(CropIncomeExpense incomeExpense){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_INCOME_EXPENSE_USER_ID, incomeExpense.getUserId());
        contentValues.put(CROP_INCOME_EXPENSE_CROP_ID, incomeExpense.getCropId());
        contentValues.put(CROP_INCOME_EXPENSE_DATE, incomeExpense.getDate());
        contentValues.put(CROP_INCOME_EXPENSE_TRANSACTION, incomeExpense.getTransaction());
        contentValues.put(CROP_INCOME_EXPENSE_ITEM, incomeExpense.getItem());
        contentValues.put(CROP_INCOME_EXPENSE_CATEGORY, incomeExpense.getCategory());
        contentValues.put(CROP_INCOME_EXPENSE_QUANTITY, incomeExpense.getQuantity());
        contentValues.put(CROP_INCOME_EXPENSE_GROSS_AMOUNT, incomeExpense.getGrossAmount());
        contentValues.put(CROP_INCOME_EXPENSE_UNIT_PRICE, incomeExpense.getUnitPrice());
        contentValues.put(CROP_INCOME_EXPENSE_TAXES, incomeExpense.getTaxes());
        contentValues.put(CROP_INCOME_EXPENSE_PAYMENT_MODE, incomeExpense.getPaymentMode());
        contentValues.put(CROP_INCOME_EXPENSE_PAYMENT_STATUS, incomeExpense.getPaymentStatus());
        contentValues.put(CROP_INCOME_EXPENSE_SELLING_PRICE, incomeExpense.getSellingPrice());
        contentValues.put(CROP_INCOME_EXPENSE_CUSTOMER_SUPPLIER, incomeExpense.getCustomerSupplier());
        database.insert(CROP_INCOME_EXPENSE_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropIncomeExpense(CropIncomeExpense incomeExpense){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INCOME_EXPENSE_USER_ID, incomeExpense.getUserId());
        contentValues.put(CROP_INCOME_EXPENSE_CROP_ID, incomeExpense.getCropId());
        contentValues.put(CROP_INCOME_EXPENSE_DATE, incomeExpense.getDate());
        contentValues.put(CROP_INCOME_EXPENSE_TRANSACTION, incomeExpense.getTransaction());
        contentValues.put(CROP_INCOME_EXPENSE_ITEM, incomeExpense.getItem());
        contentValues.put(CROP_INCOME_EXPENSE_CATEGORY, incomeExpense.getCategory());
        contentValues.put(CROP_INCOME_EXPENSE_QUANTITY, incomeExpense.getQuantity());
        contentValues.put(CROP_INCOME_EXPENSE_GROSS_AMOUNT, incomeExpense.getGrossAmount());
        contentValues.put(CROP_INCOME_EXPENSE_UNIT_PRICE, incomeExpense.getUnitPrice());
        contentValues.put(CROP_INCOME_EXPENSE_TAXES, incomeExpense.getTaxes());
        contentValues.put(CROP_INCOME_EXPENSE_PAYMENT_MODE, incomeExpense.getPaymentMode());
        contentValues.put(CROP_INCOME_EXPENSE_PAYMENT_STATUS, incomeExpense.getPaymentStatus());
        contentValues.put(CROP_INCOME_EXPENSE_SELLING_PRICE, incomeExpense.getSellingPrice());
        contentValues.put(CROP_INCOME_EXPENSE_CUSTOMER_SUPPLIER, incomeExpense.getCustomerSupplier());

        database.update(CROP_INCOME_EXPENSE_TABLE_NAME,contentValues,CROP_INCOME_EXPENSE_ID+" = ?", new String[]{incomeExpense.getId()});

        closeDB();
    }
    public boolean deleteCropIncomeExpense(String incomeExpenseId){
        openDB();
        database.delete(CROP_INCOME_EXPENSE_TABLE_NAME,CROP_INCOME_EXPENSE_ID+" = ?", new String[]{incomeExpenseId});
        closeDB();
        return true;
    }
    public ArrayList<CropIncomeExpense> getCropIncomeExpenses(String userId){
        openDB();
        ArrayList<CropIncomeExpense> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME+ " where " + CROP_INCOME_EXPENSE_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropIncomeExpense incomeExpense = new CropIncomeExpense();
            incomeExpense.setId(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_ID)));
            incomeExpense.setUserId(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_USER_ID)));
            incomeExpense.setCropId(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_CROP_ID)));
            incomeExpense.setDate(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DATE)));
            incomeExpense.setTransaction(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_TRANSACTION)));
            incomeExpense.setItem(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_ITEM)));
            incomeExpense.setCategory(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_CATEGORY)));
            incomeExpense.setQuantity(res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_QUANTITY)));
            incomeExpense.setGrossAmount(res.getInt(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT)));
            incomeExpense.setUnitPrice(res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_UNIT_PRICE)));
            incomeExpense.setTaxes(res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_TAXES)));
            incomeExpense.setPaymentMode(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_PAYMENT_MODE)));
            incomeExpense.setPaymentStatus(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_PAYMENT_STATUS)));
            incomeExpense.setSellingPrice(res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_SELLING_PRICE)));
            incomeExpense.setCustomerSupplier(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_CUSTOMER_SUPPLIER)));


            array_list.add(incomeExpense);
            res.moveToNext();
        }

        closeDB();
        Log.d("INCOMES ",array_list.size()+"");
        return array_list;
    }


    public void  insertCropTask(CropTask task){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_TASK_USER_ID, task.getUserId());
        contentValues.put(CROP_TASK_CROP_ID, task.getCropId());
        contentValues.put(CROP_TASK_DATE, task.getDate());
        contentValues.put(CROP_TASK_EMPLOYEE_ID, task.getEmployeeId());
        contentValues.put(CROP_TASK_TITLE, task.getTitle());
        contentValues.put(CROP_TASK_TYPE, task.getType());
        contentValues.put(CROP_TASK_STATUS, task.getStatus());
        contentValues.put(CROP_TASK_DESCRIPTION, task.getDescription());
        contentValues.put(CROP_TASK_RECURRENCE, task.getRecurrence());
        contentValues.put(CROP_TASK_REMINDERS, task.getReminders());
        Log.d("INSERTED",contentValues.toString());
        database.insert(CROP_TASK_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropTask(CropTask task){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_TASK_USER_ID, task.getUserId());
        contentValues.put(CROP_TASK_CROP_ID, task.getCropId());
        contentValues.put(CROP_TASK_DATE, task.getDate());
        contentValues.put(CROP_TASK_EMPLOYEE_ID, task.getEmployeeId());
        contentValues.put(CROP_TASK_TITLE, task.getTitle());
        contentValues.put(CROP_TASK_TYPE, task.getType());
        contentValues.put(CROP_TASK_STATUS, task.getStatus());
        contentValues.put(CROP_TASK_DESCRIPTION, task.getDescription());
        contentValues.put(CROP_TASK_RECURRENCE, task.getRecurrence());
        contentValues.put(CROP_TASK_REMINDERS, task.getReminders());
        database.update(CROP_TASK_TABLE_NAME,contentValues,CROP_TASK_ID+" = ?", new String[]{task.getId()});

        closeDB();
    }
    public boolean deleteCropTask(String taskId){
        openDB();
        database.delete(CROP_TASK_TABLE_NAME,CROP_TASK_ID+" = ?", new String[]{taskId});
        closeDB();
        return true;
    }
    public ArrayList<CropTask> getCropTasks(String userId){
        openDB();
        ArrayList<CropTask> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+CROP_TASK_TABLE_NAME+".*,"+CROP_EMPLOYEE_TABLE_NAME+"."+CROP_EMPLOYEE_FIRST_NAME+","+CROP_EMPLOYEE_TABLE_NAME+"."+CROP_EMPLOYEE_LAST_NAME+","+CROP_CROP_TABLE_NAME+"."+CROP_CROP_NAME+
                        " from " + CROP_TASK_TABLE_NAME+
                        " LEFT JOIN "+CROP_EMPLOYEE_TABLE_NAME+" ON "+CROP_TASK_TABLE_NAME+"."+CROP_TASK_EMPLOYEE_ID+" = "+CROP_EMPLOYEE_TABLE_NAME+"."+CROP_EMPLOYEE_ID+
                        " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_TASK_TABLE_NAME+"."+CROP_TASK_CROP_ID+" = "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+
                        " where "+CROP_TASK_TABLE_NAME+"."+CROP_TASK_USER_ID+" = "+ userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Log.d("TASKS",array_list.size()+"");
            CropTask task = new CropTask();
            task.setId(res.getString(res.getColumnIndex(CROP_TASK_ID)));
            task.setUserId(res.getString(res.getColumnIndex(CROP_TASK_USER_ID)));
            task.setCropId(res.getString(res.getColumnIndex(CROP_TASK_CROP_ID)));
            task.setCropName(res.getString(res.getColumnIndex(CROP_CROP_NAME)));
            task.setEmployeeId(res.getString(res.getColumnIndex(CROP_TASK_EMPLOYEE_ID)));
            task.setEmployeeName(res.getString(res.getColumnIndex(CROP_EMPLOYEE_FIRST_NAME))+res.getString(res.getColumnIndex(CROP_EMPLOYEE_LAST_NAME)));
            task.setDate(res.getString(res.getColumnIndex(CROP_TASK_DATE)));
            task.setTitle(res.getString(res.getColumnIndex(CROP_TASK_TITLE)));
            task.setType(res.getString(res.getColumnIndex(CROP_TASK_TYPE)));
            task.setStatus(res.getString(res.getColumnIndex(CROP_TASK_STATUS)));
            task.setDescription(res.getString(res.getColumnIndex(CROP_TASK_DESCRIPTION)));
            task.setRecurrence(res.getString(res.getColumnIndex(CROP_TASK_RECURRENCE)));
            task.setReminders(res.getString(res.getColumnIndex(CROP_TASK_REMINDERS)));

            array_list.add(task);
            res.moveToNext();
        }

        closeDB();

        return array_list;
    }

    public String getNextPurchaseOrderNumber(){
        openDB();
        Cursor res =  database.rawQuery( "select "+CROP_PURCHASE_ORDER_ID+" from "+CROP_PURCHASE_ORDER_TABLE_NAME+" ORDER BY "+CROP_PURCHASE_ORDER_ID+" DESC LIMIT 1",null);
        int lastId = 0;
        res.moveToFirst();
        if(!res.isAfterLast()){
            lastId = res.getInt(res.getColumnIndex(CROP_PURCHASE_ORDER_ID));
        }
        int id=lastId+1;
        closeDB();

        return "PO-"+String.format("%03d", id);
    }
    public void  insertCropPurchaseOrder(CropPurchaseOrder estimate){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_PURCHASE_ORDER_USER_ID,estimate.getUserId());
        contentValues.put(CROP_PURCHASE_ORDER_SUPPLIER_ID,estimate.getSupplierId());
        contentValues.put(CROP_PURCHASE_ORDER_NUMBER,estimate.getNumber());
        contentValues.put(CROP_PURCHASE_ORDER_PURCHASE_DATE,estimate.getPurchaseDate());
        contentValues.put(CROP_PURCHASE_ORDER_DELIVERY_METHOD,estimate.getMethod());
        contentValues.put(CROP_PURCHASE_ORDER_REFERENCE_NUMBER,estimate.getReferenceNumber());
        contentValues.put(CROP_PURCHASE_ORDER_DELIVERY_DATE,estimate.getDeliveryDate());
        contentValues.put(CROP_PURCHASE_ORDER_STATUS,estimate.getStatus());
        contentValues.put(CROP_PURCHASE_ORDER_DISCOUNT,estimate.getDiscount());
        contentValues.put(CROP_PURCHASE_ORDER_NOTES,estimate.getNotes());
        contentValues.put(CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS,estimate.getTermsAndConditions());

        database.insert(CROP_PURCHASE_ORDER_TABLE_NAME,null,contentValues);

        Cursor res =  database.rawQuery( "select "+CROP_PURCHASE_ORDER_ID+" from "+CROP_PURCHASE_ORDER_TABLE_NAME+" where "+CROP_PURCHASE_ORDER_SUPPLIER_ID+" = '"+estimate.getSupplierId()+"' AND "+CROP_PURCHASE_ORDER_NUMBER+" = '"+estimate.getNumber()+"'", null );
        res.moveToFirst();
        if(!res.isAfterLast()){
            String estimateId = res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_ID));

            ArrayList<CropProductItem> items = estimate.getItems();

            for(CropProductItem x: items){
                contentValues.clear();
                contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
                contentValues.put(CROP_PRODUCT_ITEM_ESTIMATE_ID,estimateId);
                contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
                contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
                contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
                contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER);
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }
        }
        closeDB();
    }
    public void  updateCropPurchaseOrder(CropPurchaseOrder estimate){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_PURCHASE_ORDER_USER_ID,estimate.getUserId());
        contentValues.put(CROP_PURCHASE_ORDER_SUPPLIER_ID,estimate.getSupplierId());
        contentValues.put(CROP_PURCHASE_ORDER_NUMBER,estimate.getNumber());
        contentValues.put(CROP_PURCHASE_ORDER_PURCHASE_DATE,estimate.getPurchaseDate());
        contentValues.put(CROP_PURCHASE_ORDER_DELIVERY_METHOD,estimate.getMethod());
        contentValues.put(CROP_PURCHASE_ORDER_REFERENCE_NUMBER,estimate.getReferenceNumber());
        contentValues.put(CROP_PURCHASE_ORDER_DELIVERY_DATE,estimate.getDeliveryDate());
        contentValues.put(CROP_PURCHASE_ORDER_STATUS,estimate.getStatus());
        contentValues.put(CROP_PURCHASE_ORDER_DISCOUNT,estimate.getDiscount());
        contentValues.put(CROP_PURCHASE_ORDER_NOTES,estimate.getNotes());
        contentValues.put(CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS,estimate.getTermsAndConditions());

        database.update(CROP_PURCHASE_ORDER_TABLE_NAME,contentValues,CROP_PURCHASE_ORDER_ID+" = ?", new String[]{estimate.getId()});

        String estimateId = estimate.getId();

        ArrayList<CropProductItem> items = estimate.getItems();

        for(CropProductItem x: items){
            contentValues.clear();
            contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
            contentValues.put(CROP_PRODUCT_ITEM_ESTIMATE_ID,estimateId);
            contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
            contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
            contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
            contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER);
            if(x.getId() !=null){
                database.update(CROP_PRODUCT_ITEM_TABLE_NAME,contentValues,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{x.getId()});
            }
            else{
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }

        }

        for(String id: estimate.getDeletedItemsIds()){
            database.delete(CROP_PRODUCT_ITEM_TABLE_NAME,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{id});
        }

        closeDB();
    }
    public boolean deleteCropPurchaseOrder(String id){
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME,CROP_PRODUCT_ITEM_ID+" = ? AND "+CROP_PRODUCT_ITEM_TYPE+" = ?", new String[]{id,CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER});
        database.delete(CROP_PURCHASE_ORDER_TABLE_NAME,CROP_PURCHASE_ORDER_ID+" = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropPurchaseOrder> getCropPurchaseOrders(String userId){
        openDB();
        ArrayList<CropPurchaseOrder> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+CROP_PURCHASE_ORDER_TABLE_NAME+".*,"+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_NAME+" from "+CROP_PURCHASE_ORDER_TABLE_NAME+" LEFT JOIN "+CROP_SUPPLIER_TABLE_NAME+" ON "+CROP_PURCHASE_ORDER_TABLE_NAME+"."+CROP_PURCHASE_ORDER_SUPPLIER_ID+" = "+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_ID+" where "+CROP_PURCHASE_ORDER_TABLE_NAME+"."+CROP_PURCHASE_ORDER_USER_ID+" = "+ userId, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            CropPurchaseOrder cropPurchaseOrder = new CropPurchaseOrder();
            cropPurchaseOrder.setId(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_ID)));
            cropPurchaseOrder.setUserId(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_USER_ID)));
            cropPurchaseOrder.setSupplierId(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_SUPPLIER_ID)));
            cropPurchaseOrder.setNumber(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_NUMBER)));
            cropPurchaseOrder.setMethod(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_DELIVERY_METHOD)));
            cropPurchaseOrder.setSupplierName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            cropPurchaseOrder.setReferenceNumber(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_REFERENCE_NUMBER)));
            cropPurchaseOrder.setPurchaseDate(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_PURCHASE_DATE)));
            cropPurchaseOrder.setStatus(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_STATUS)));
            cropPurchaseOrder.setDeliveryDate(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_DELIVERY_DATE)));
            cropPurchaseOrder.setDiscount(res.getFloat(res.getColumnIndex(CROP_PURCHASE_ORDER_DISCOUNT)));
            cropPurchaseOrder.setNotes(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_NOTES)));
            cropPurchaseOrder.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS)));
            array_list.add(cropPurchaseOrder);
            res.moveToNext();
        }


        for(CropPurchaseOrder cropPurchaseOrder: array_list){
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery( "select * from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+CROP_PRODUCT_ITEM_ESTIMATE_ID+" = "+ cropPurchaseOrder.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = "+CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER, null );
            res.moveToFirst();
            while(!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setInvoiceOrEstimateId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ESTIMATE_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                items_list.add(item);
                res.moveToNext();
            }
            cropPurchaseOrder.setItems(items_list);
        }

        closeDB();
        return array_list;
    }

    public void  insertCropPaymentBill(CropPaymentBill cropPaymentBill){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PAYMENT_BILL_USER_ID, cropPaymentBill.getUserId());
        contentValues.put(CROP_PAYMENT_BILL_PAYMENT_MADE, cropPaymentBill.getAmount());
        contentValues.put(CROP_PAYMENT_BILL_DATE, cropPaymentBill.getDate());
        contentValues.put(CROP_PAYMENT_BILL_PAYMENT_MODE, cropPaymentBill.getMode());
        contentValues.put(CROP_PAYMENT_BILL_NOTES, cropPaymentBill.getNotes());
        contentValues.put(CROP_PAYMENT_BILL_PAID_THROUGH, cropPaymentBill.getPaidThrough());
        contentValues.put(CROP_PAYMENT_BILL_REFERENCE_NUMBER, cropPaymentBill.getReferenceNumber());
        database.insert(CROP_PAYMENT_BILL_TABLE_NAME, null, contentValues);

        closeDB();
    }

    public void updateCropPaymentBill(CropPaymentBill cropPaymentBill) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_PAYMENT_BILL_USER_ID, cropPaymentBill.getUserId());
        contentValues.put(CROP_PAYMENT_BILL_PAYMENT_MADE, cropPaymentBill.getAmount());
        contentValues.put(CROP_PAYMENT_BILL_DATE, cropPaymentBill.getDate());
        contentValues.put(CROP_PAYMENT_BILL_PAYMENT_MODE, cropPaymentBill.getMode());
        contentValues.put(CROP_PAYMENT_BILL_NOTES, cropPaymentBill.getNotes());
        contentValues.put(CROP_PAYMENT_BILL_PAID_THROUGH, cropPaymentBill.getPaidThrough());
        contentValues.put(CROP_PAYMENT_BILL_REFERENCE_NUMBER, cropPaymentBill.getReferenceNumber());
        database.update(CROP_PAYMENT_BILL_TABLE_NAME,contentValues,CROP_PAYMENT_ID+" = ?", new String[]{cropPaymentBill.getId()});


        closeDB();

    }
    public boolean deleteCropPaymentBill(String id) {
        openDB();
        database.delete(CROP_PAYMENT_BILL_TABLE_NAME, CROP_PAYMENT_BILL_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public ArrayList<CropPaymentBill> getCropPaymentBills(String userId) {
        openDB();
        ArrayList<CropPaymentBill> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PAYMENT_BILL_TABLE_NAME + " where " + CROP_PAYMENT_BILL_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropPaymentBill paymentBill = new CropPaymentBill();
            paymentBill.setId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_ID)));
            paymentBill.setUserId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_USER_ID)));
            paymentBill.setDate(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_DATE)));
            paymentBill.setAmount(Float.parseFloat(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_PAYMENT_MADE))));
            paymentBill.setMode(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_PAYMENT_MODE)));
            paymentBill.setPaidThrough(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_PAID_THROUGH)));
            paymentBill.setReferenceNumber(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_REFERENCE_NUMBER)));
            paymentBill.setNotes(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_NOTES)));


            array_list.add(paymentBill);
            res.moveToNext();
        }

        closeDB();
        return array_list;
    }


}

