package com.myfarmnow.myfarmcrop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.models.address_model.RegionDetails;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;

import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;

import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropNote;
import com.myfarmnow.myfarmcrop.models.CropNotification;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
import com.myfarmnow.myfarmcrop.models.CropYieldRecord;
import com.myfarmnow.myfarmcrop.models.DeletedRecord;
import com.myfarmnow.myfarmcrop.models.GraphRecord;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropGallery;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Litter;
import com.myfarmnow.myfarmcrop.models.livestock_models.Mating;
import com.myfarmnow.myfarmcrop.models.livestock_models.Medication;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPrice;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPriceSubItem;
import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;
import com.myfarmnow.myfarmcrop.singletons.CropDatabaseInitializerSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyFarmDbHandlerSingleton extends SQLiteOpenHelper {
    private static final String TAG = "MyFarmDbHandler";

    public static final String DATABASE_NAME = "myFarm.db";
    private static int database_version = 7;

    public static final String CROP_INVENTORY_FERTILIZER_TABLE_NAME = "crop_inventory_fertilizer";
    public static final String CROP_INVENTORY_SEEDS_TABLE_NAME = "crop_inventory_seeds";
    public static final String CROP_INVENTORY_SPRAY_TABLE_NAME = "crop_inventory_spray";
    public static final String CROP_CROP_TABLE_NAME = "crop";
    public static final String CROP_CULTIVATION_TABLE_NAME = "crop_cultivate";
    public static final String CROP_FERTILIZER_APPLICATION_TABLE_NAME = "crop_fertilizer_application";
    public static final String CROP_SPRAYING_TABLE_NAME = "crop_spraying";
    public static final String CROP_FIELDS_TABLE_NAME = "crop_fields";
    public static final String CROP_MACHINE_TABLE_NAME = "crop_machine";
    public static final String CROP_SOIL_ANALYSIS_TABLE_NAME = "crop_soil_analysis";
    public static final String CROP_EMPLOYEE_TABLE_NAME = "crop_employee";
    public static final String CROP_CUSTOMER_TABLE_NAME = "crop_customer";
    public static final String CROP_SUPPLIER_TABLE_NAME = "crop_supplier";
    public static final String CROP_PRODUCT_TABLE_NAME = "crop_product";
    public static final String CROP_ESTIMATE_TABLE_NAME = "crop_estimate";
    public static final String CROP_PRODUCT_ITEM_TABLE_NAME = "crop_product_items";
    public static final String CROP_INVOICE_TABLE_NAME = "crop_invoice";
    public static final String CROP_INCOME_EXPENSE_TABLE_NAME = "crop_income_expense";
    public static final String CROP_PAYMENT_TABLE_NAME = "crop_payments";
    public static final String CROP_TASK_TABLE_NAME = "crop_task";
    public static final String CROP_SALES_ORDER_TABLE_NAME = "crop_sales_order";
    public static final String CROP_PURCHASE_ORDER_TABLE_NAME = "crop_purchase_order";
    public static final String CROP_PAYMENT_BILL_TABLE_NAME = "crop_payment_bill";
    public static final String CROP_BILL_TABLE_NAME = "crop_bill";
    public static final String CROP_ITEM_TABLE_NAME = "crop_item";
    public static final String CROP_FERTILIZER_TABLE_NAME = "crop_fertilizer";
    public static final String CROP_SETTINGS_TABLE_NAME = "crop_settings";
    public static final String CROP_NOTIFICATION_TABLE_NAME = "crop_notification";
    public static final String CROP_MACHINE_TASK_TABLE_NAME = "crop_machine_task";
    public static final String CROP_NOTE_TABLE_NAME = "crop_notes";
    public static final String CROP_MACHINE_SERVICE_TABLE_NAME = "crop_machine_services";
    public static final String CROP_IRRIGATION_TABLE_NAME = "crop_irrigation";
    public static final String CROP_TRANSPLANTING_TABLE_NAME = "crop_transplanting";
    public static final String CROP_SCOUTING_TABLE_NAME = "crop_scouting";
    public static final String CROP_HARVEST_TABLE_NAME = "crop_harvest";
    public static final String CROP_CONTACT_TABLE_NAME = "crop_contact";
    public static final String CROP_DELETED_RECORDS_TABLE_NAME = "crop_deleted_records";


    public static final String CROP_INVENTORY_FERTILIZER_ID = "id";
    public static final String CROP_INVENTORY_FERTILIZER_USER_ID = "userId";
    public static final String CROP_INVENTORY_FERTILIZER_DATE = "date";
    public static final String CROP_INVENTORY_FERTILIZER_TYPE = "type";
    public static final String CROP_INVENTORY_FERTILIZER_NAME = "name";
    public static final String CROP_INVENTORY_FERTILIZER_N_PERCENTAGE = "nPercentage";
    public static final String CROP_INVENTORY_FERTILIZER_P_PERCENTAGE = "pPercentage";
    public static final String CROP_INVENTORY_FERTILIZER_K_PERCENTAGE = "kPercentage";
    public static final String CROP_INVENTORY_FERTILIZER_QUANTITY = "quantity";
    public static final String CROP_INVENTORY_FERTILIZER_BATCH_NUMBER = "batchNumber";
    public static final String CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER = "serialNumber";
    public static final String CROP_INVENTORY_FERTILIZER_SUPPLIER = "supplier";
    public static final String CROP_INVENTORY_FERTILIZER_USAGE_UNIT = "usageUnit";
    public static final String CROP_INVENTORY_FERTILIZER_COST = "cost";
    public static final String CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA = "macronutrientsCa";
    public static final String CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG = "macronutrientsMg";
    public static final String CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S = "macronutrientsS";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B = "micronutrientsB";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN = "micronutrientsMn";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL = "micronutrientsCl";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO = "micronutrientsMo";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU = "micronutrientsCu";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN = "micronutrientsZn";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE = "micronutrientsFe";
    public static final String CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA = "micronutrientsNa";

    public static final String CROP_INVENTORY_SEEDS_ID = "id";
    public static final String CROP_INVENTORY_SEEDS_USER_ID = "userId";
    public static final String CROP_INVENTORY_SEEDS_DATE = "date";

    public static final String CROP_INVENTORY_SEEDS_NAME = "name";
    public static final String CROP_INVENTORY_SEEDS_VARIETY = "variety";
    public static final String CROP_INVENTORY_SEEDS_DRESSING = "dressing";
    public static final String CROP_INVENTORY_SEEDS_QUANTITY = "quantity";
    public static final String CROP_INVENTORY_SEEDS_COST = "cost";
    public static final String CROP_INVENTORY_SEEDS_BATCH_NUMBER = "batchNumber";
    public static final String CROP_INVENTORY_SEEDS_SUPPLIER = "supplier";
    public static final String CROP_INVENTORY_SEEDS_TGW = "tgw";
    public static final String CROP_INVENTORY_SEEDS_USAGE_UNIT = "usageUnit";
    public static final String CROP_INVENTORY_SEEDS_TYPE = "seedType";
    public static final String CROP_INVENTORY_SEEDS_MANUFACTURER = "manufacturer";

    public static final String CROP_INVENTORY_SPRAY_ID = "id";
    public static final String CROP_INVENTORY_SPRAY_USER_ID = "userId";
    public static final String CROP_INVENTORY_SPRAY_DATE = "date";
    public static final String CROP_INVENTORY_SPRAY_TYPE = "type";
    public static final String CROP_INVENTORY_SPRAY_NAME = "name";
    public static final String CROP_INVENTORY_SPRAY_VARIETY = "variety";
    public static final String CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS = "dressing";
    public static final String CROP_INVENTORY_SPRAY_QUANTITY = "quantity";
    public static final String CROP_INVENTORY_SPRAY_COST = "cost";
    public static final String CROP_INVENTORY_SPRAY_BATCH_NUMBER = "batchNumber";
    public static final String CROP_INVENTORY_SPRAY_SUPPLIER = "supplier";
    public static final String CROP_INVENTORY_SPRAY_EXPIRY_DATE = "tgw";
    public static final String CROP_INVENTORY_SPRAY_USAGE_UNIT = "usageUnit";
    public static final String CROP_INVENTORY_SPRAY_HARVEST_INTERVAL = "harvestInterval";


    public static final String CROP_CROP_ID = "id";
    public static final String CROP_CROP_USER_ID = "userId";
    public static final String CROP_CROP_NAME = "crop";
    public static final String CROP_CROP_YEAR = "croppingYear";
    public static final String CROP_CROP_FIELD_ID = "fieldId";
    public static final String CROP_CROP_DATE_SOWN = "dateSown";
    public static final String CROP_CROP_VARIETY = "variety";
    public static final String CROP_CROP_GROWING_CYCLE = "growingCycle";
    public static final String CROP_CROP_SEASON = "season";
    public static final String CROP_CROP_HARVEST_UNITS = "harvestUnits";
    public static final String CROP_CROP_ESTIMATED_YIELD = "estimatedYield";
    public static final String CROP_CROP_ESTIMATED_REVENUE = "estimatedRevenue";
    public static final String CROP_CROP_AREA = "area";
    public static final String CROP_CROP_COST = "cost";
    public static final String CROP_CROP_OPERATOR = "operator";
    public static final String CROP_CROP_SEED_ID = "seedId";
    public static final String CROP_CROP_RATE = "rate";
    public static final String CROP_CROP_PLANTING_METHOD = "plantingMethod";

    public static final String CROP_CULTIVATION_ID = "id";
    public static final String CROP_CULTIVATION_USER_ID = "userId";
    public static final String CROP_CULTIVATION_CROP_ID = "cropId";
    public static final String CROP_CULTIVATION_DATE = "date";
    public static final String CROP_CULTIVATION_OPERATION = "operation";
    public static final String CROP_CULTIVATION_OPERATOR = "operator";
    public static final String CROP_CULTIVATION_COST = "cost";
    public static final String CROP_CULTIVATION_NOTES = "notes";
    public static final String CROP_CULTIVATION_FREQUENCY = "frequency";
    public static final String CROP_CULTIVATION_REPEAT_UNTIL = "repeatUntil";
    public static final String CROP_CULTIVATION_DAYS_BEFORE = "daysBefore";
    public static final String CROP_CULTIVATION_RECURRENCE = "recurrence";
    public static final String CROP_CULTIVATION_REMINDERS = "reminders";


    public static final String CROP_FERTILIZER_APPLICATION_ID = "id";
    public static final String CROP_FERTILIZER_APPLICATION_USER_ID = "userId";
    public static final String CROP_FERTILIZER_APPLICATION_DATE = "date";
    public static final String CROP_FERTILIZER_APPLICATION_OPERATOR = "operator";
    public static final String CROP_FERTILIZER_APPLICATION_METHOD = "method";
    public static final String CROP_FERTILIZER_APPLICATION_REASON = "reason";
    public static final String CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM = "fertilizerForm";
    public static final String CROP_FERTILIZER_APPLICATION_FERTILIZER_ID = "fertilizerId";
    public static final String CROP_FERTILIZER_APPLICATION_RATE = "rate";
    public static final String CROP_FERTILIZER_APPLICATION_COST = "cost";
    public static final String CROP_FERTILIZER_APPLICATION_CROP_ID = "cropId";
    public static final String CROP_FERTILIZER_APPLICATION_FREQUENCY = "frequency";
    public static final String CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL = "repeatUntil";
    public static final String CROP_FERTILIZER_APPLICATION_DAYS_BEFORE = "daysBefore";
    public static final String CROP_FERTILIZER_APPLICATION_RECURRENCE = "recurrence";
    public static final String CROP_FERTILIZER_APPLICATION_REMINDERS = "reminders";
    public static final String CROP_FERTILIZER_APPLICATION_FERTILIZER_NAME = "fertilizerName";
    public static final String CROP_FERTILIZER_APPLICATION_FERTILIZER_UNITS = "units";


    public static final String CROP_SPRAYING_ID = "id";
    public static final String CROP_SPRAYING_USER_ID = "userId";
    public static final String CROP_SPRAYING_CROP_ID = "cropId";
    public static final String CROP_SPRAYING_DATE = "date";
    public static final String CROP_SPRAYING_START_TIME = "startTime";
    public static final String CROP_SPRAYING_END_TIME = "endTime";
    public static final String CROP_SPRAYING_OPERATOR = "operator";
    public static final String CROP_SPRAYING_WATER_VOLUME = "waterVolume";
    public static final String CROP_SPRAYING_WATER_CONDITION = "waterCondition";
    public static final String CROP_SPRAYING_WIND_DIRECTION = "windDirection";
    public static final String CROP_SPRAYING_EQUIPMENT_USED = "equipmentUsed";
    public static final String CROP_SPRAYING_SPRAY_ID = "sprayId";
    public static final String CROP_SPRAYING_RATE = "rate";
    public static final String CROP_SPRAYING_TREATMENT_REASON = "treatmentReason";
    public static final String CROP_SPRAYING_COST = "cost";
    public static final String CROP_SPRAYING_FREQUENCY = "frequency";
    public static final String CROP_SPRAYING_REPEAT_UNTIL = "repeatUntil";
    public static final String CROP_SPRAYING_DAYS_BEFORE = "daysBefore";
    public static final String CROP_SPRAYING_RECURRENCE = "recurrence";
    public static final String CROP_SPRAYING_REMINDERS = "reminders";
    public static final String CROP_SPRAYING_UNITS = "usageUnits";
    public static final String CROP_SPRAYING_SPRAY_NAME = "sprayName";


    public static final String CROP_FIELD_ID = "id";
    public static final String CROP_FIELD_USER_ID = "userId";
    public static final String CROP_FIELD_NAME = "fieldName";
    public static final String CROP_FIELD_SOIL_CATEGORY = "soilCategory";
    public static final String CROP_FIELD_SOIL_TYPE = "soilType";
    public static final String CROP_FIELD_WATERCOURSE = "watercourse";
    public static final String CROP_FIELD_TOTAL_AREA = "totalArea";
    public static final String CROP_FIELD_CROPPABLE_AREA = "croppableArea";
    public static final String CROP_FIELD_UNITS = "units";
    public static final String CROP_FIELD_FIELD_TYPE = "fieldType";
    public static final String CROP_FIELD_LAYOUT_TYPE = "layoutType";
    public static final String CROP_FIELD_STATUS = "status";

    public static final String CROP_MACHINE_ID = "id";
    public static final String CROP_MACHINE_USER_ID = "userId";
    public static final String CROP_MACHINE_NAME = "name";
    public static final String CROP_MACHINE_BRAND = "brand";
    public static final String CROP_MACHINE_CATEGORY = "category";
    public static final String CROP_MACHINE_MANUFACTURER = "manufacturer";
    public static final String CROP_MACHINE_MODEL = "model";
    public static final String CROP_MACHINE_REGISTRATION_NUMBER = "registrationNumber";
    public static final String CROP_MACHINE_QUANTITY = "quantity";
    public static final String CROP_MACHINE_DATE_ACQUIRED = "date";
    public static final String CROP_MACHINE_PURCHASED_FROM = "purchasedFrom";
    public static final String CROP_MACHINE_STORAGE_LOCATION = "storageLocation";
    public static final String CROP_MACHINE_PURCHASE_PRICE = "purchasePrice";

    public static final String CROP_SOIL_ANALYSIS_ID = "id";
    public static final String CROP_SOIL_ANALYSIS_USER_ID = "userId";
    public static final String CROP_SOIL_ANALYSIS_DATE = "date";
    public static final String CROP_SOIL_ANALYSIS_PH = "ph";
    public static final String CROP_SOIL_ANALYSIS_ORGANIC_MATTER = "organicMatter";
    public static final String CROP_SOIL_ANALYSIS_AGRONOMIST = "agronomist";
    public static final String CROP_SOIL_ANALYSIS_COST = "cost";
    public static final String CROP_SOIL_ANALYSIS_RESULTS = "results";
    public static final String CROP_SOIL_ANALYSIS_FIELD_ID = "fieldId";
    public static final String CROP_SOIL_ANALYSIS_FREQUENCY = "frequency";
    public static final String CROP_SOIL_ANALYSIS_REPEAT_UNTIL = "repeatUntil";
    public static final String CROP_SOIL_ANALYSIS_DAYS_BEFORE = "daysBefore";
    public static final String CROP_SOIL_ANALYSIS_RECURRENCE = "recurrence";
    public static final String CROP_SOIL_ANALYSIS_REMINDERS = "reminders";


    public static final String CROP_EMPLOYEE_ID = "id";
    public static final String CROP_EMPLOYEE_USER_ID = "userId";
    public static final String CROP_EMPLOYEE_TITLE = "title";
    public static final String CROP_EMPLOYEE_FIRST_NAME = "firstName";
    public static final String CROP_EMPLOYEE_LAST_NAME = "lastName";
    public static final String CROP_EMPLOYEE_PHONE = "phone";
    public static final String CROP_EMPLOYEE_MOBILE = "mobile";
    public static final String CROP_EMPLOYEE_EMP_ID = "employeeId";
    public static final String CROP_EMPLOYEE_GENDER = "gender";
    public static final String CROP_EMPLOYEE_ADDRESS = "address";
    public static final String CROP_EMPLOYEE_EMAIL = "email";
    public static final String CROP_EMPLOYEE_DOB = "dateOfBirth";
    public static final String CROP_EMPLOYEE_HIRE_DATE = "hireDate";
    public static final String CROP_EMPLOYEE_EMPLOYMENT_STATUS = "employmentStatus";
    public static final String CROP_EMPLOYEE_PAY_AMOUNT = "payAmount";
    public static final String CROP_EMPLOYEE_PAY_RATE = "payRate";
    public static final String CROP_EMPLOYEE_PAY_TYPE = "payType";
    public static final String CROP_EMPLOYEE_SUPERVISOR = "supervisor";

    public static final String CROP_CUSTOMER_ID = "id";
    public static final String CROP_CUSTOMER_USER_ID = "userId";
    public static final String CROP_CUSTOMER_NAME = "name";
    public static final String CROP_CUSTOMER_COMPANY = "company";
    public static final String CROP_CUSTOMER_TAX_REG_NO = "taxRegNo";
    public static final String CROP_CUSTOMER_PHONE = "phone";
    public static final String CROP_CUSTOMER_MOBILE = "mobile";
    public static final String CROP_CUSTOMER_EMAIL = "email";
    public static final String CROP_CUSTOMER_OPENING_BALANCE = "openingBalance";
    public static final String CROP_CUSTOMER_BILL_ADDRESS_STREET = "billingStreet";
    public static final String CROP_CUSTOMER_BILL_ADDRESS_CITY = "billingCityOrTown";
    public static final String CROP_CUSTOMER_BILL_ADDRESS_COUNTRY = "billingCountry";
    public static final String CROP_CUSTOMER_SHIP_ADDRESS_STREET = "shippingStreet";
    public static final String CROP_CUSTOMER_SHIP_ADDRESS_CITY = "shippingCityOrTown";
    public static final String CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY = "shippingCountry";


    public static final String CROP_SUPPLIER_ID = "id";
    public static final String CROP_SUPPLIER_USER_ID = "userId";
    public static final String CROP_SUPPLIER_NAME = "name";
    public static final String CROP_SUPPLIER_COMPANY = "company";
    public static final String CROP_SUPPLIER_TAX_REG_NO = "taxRegNo";
    public static final String CROP_SUPPLIER_PHONE = "phone";
    public static final String CROP_SUPPLIER_MOBILE = "mobile";
    public static final String CROP_SUPPLIER_EMAIL = "email";
    public static final String CROP_SUPPLIER_OPENING_BALANCE = "openingBalance";
    public static final String CROP_SUPPLIER_INVOICE_ADDRESS_STREET = "invoiceStreet";
    public static final String CROP_SUPPLIER_INVOICE_ADDRESS_CITY = "invoiceCityOrTown";
    public static final String CROP_SUPPLIER_INVOICE_ADDRESS_COUNTRY = "invoiceCountry";

    public static final String CROP_PRODUCT_ID = "id";
    public static final String CROP_PRODUCT_USER_ID = "userId";
    public static final String CROP_PRODUCT_NAME = "name";
    public static final String CROP_PRODUCT_TYPE = "type";
    public static final String CROP_PRODUCT_CODE = "code";
    public static final String CROP_PRODUCT_UNITS = "units";
    public static final String CROP_PRODUCT_LINKED_ACCOUNT = "linkedAccount";
    public static final String CROP_PRODUCT_OPENING_COST = "openingCost";
    public static final String CROP_PRODUCT_OPENING_QUANTITY = "openingQuantity";
    public static final String CROP_PRODUCT_SELLING_PRICE = "sellingPrice";
    public static final String CROP_PRODUCT_TAX_RATE = "taxRate";
    public static final String CROP_PRODUCT_DESCRIPTION = "description";

    public static final String CROP_ESTIMATE_ID = "id";
    public static final String CROP_ESTIMATE_USER_ID = "userId";
    public static final String CROP_ESTIMATE_CUSTOMER_ID = "customerId";
    public static final String CROP_ESTIMATE_NO = "number";
    public static final String CROP_ESTIMATE_DATE = "date";
    public static final String CROP_ESTIMATE_EXP_DATE = "expiryDate";
    public static final String CROP_ESTIMATE_REFERENCE_NO = "referenceNo";
    public static final String CROP_ESTIMATE_DISCOUNT = "discount";
    public static final String CROP_ESTIMATE_SHIPPING_CHARGES = "shippingCharges";
    public static final String CROP_ESTIMATE_CUSTOMER_NOTES = "customerNotes";
    public static final String CROP_ESTIMATE_TERMS_AND_CONDITIONS = "termsAndConditions";
    public static final String CROP_ESTIMATE_STATUS = "status";


    public static final String CROP_PRODUCT_ITEM_ID = "id";
    public static final String CROP_PRODUCT_ITEM_PRODUCT_ID = "productId";
    public static final String CROP_PRODUCT_ITEM_PARENT_OBJECT_ID = "estimateId";
    public static final String CROP_PRODUCT_ITEM_QUANTITY = "quantity";
    public static final String CROP_PRODUCT_ITEM_RATE = "rate";
    public static final String CROP_PRODUCT_ITEM_TAX = "tax";
    public static final String CROP_PRODUCT_ITEM_TYPE = "type";

    public static final String CROP_INVOICE_ID = "id";
    public static final String CROP_INVOICE_USER_ID = "userId";
    public static final String CROP_INVOICE_CUSTOMER_ID = "customerId";
    public static final String CROP_INVOICE_NO = "number";
    public static final String CROP_INVOICE_DATE = "date";
    public static final String CROP_INVOICE_ORDER_NUMBER = "orderNumber";
    public static final String CROP_INVOICE_TERMS = "terms";
    public static final String CROP_INVOICE_DUE_DATE = "dueDate";
    public static final String CROP_INVOICE_DISCOUNT = "discount";
    public static final String CROP_INVOICE_SHIPPING_CHARGES = "shippingCharges";
    public static final String CROP_INVOICE_CUSTOMER_NOTES = "customerNotes";
    public static final String CROP_INVOICE_TERMS_AND_CONDITIONS = "termsAndConditions";

    public static final String CROP_PAYMENT_ID = "id";
    public static final String CROP_PAYMENT_USER_ID = "userId";
    public static final String CROP_PAYMENT_CUSTOMER_ID = "customerId";
    public static final String CROP_PAYMENT_AMOUNT = "amount";
    public static final String CROP_PAYMENT_DATE = "date";
    public static final String CROP_PAYMENT_MODE = "mode";
    public static final String CROP_PAYMENT_REFERENCE_NO = "referenceNo";
    public static final String CROP_PAYMENT_NUMBER = "paymentNumber";
    public static final String CROP_PAYMENT_NOTES = "notes";
    public static final String CROP_PAYMENT_INVOICE_ID = "invoiceId";

    public static final String CROP_SALES_ORDER_ID = "id";
    public static final String CROP_SALES_ORDER_USER_ID = "userId";
    public static final String CROP_SALES_ORDER_CUSTOMER_ID = "customerId";
    public static final String CROP_SALES_ORDER_NO = "number";
    public static final String CROP_SALES_ORDER_REFERENCE_NO = "referenceNumber";
    public static final String CROP_SALES_ORDER_SHIPPING_METHOD = "shippingMethod";
    public static final String CROP_SALES_ORDER_DATE = "date";
    public static final String CROP_SALES_ORDER_SHIPPING_DATE = "shippingDate";
    public static final String CROP_SALES_ORDER_DISCOUNT = "discount";
    public static final String CROP_SALES_ORDER_SHIPPING_CHARGES = "shippingCharges";
    public static final String CROP_SALES_ORDER_CUSTOMER_NOTES = "customerNotes";
    public static final String CROP_SALES_ORDER_TERMS_AND_CONDITIONS = "termsAndConditions";
    public static final String CROP_SALES_ORDER_STATUS = "status";

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
    public static final String CROP_INCOME_EXPENSE_DEPARTMENT = "department";
    public static final String CROP_INCOME_EXPENSE_USER_ID = "userId";
    public static final String CROP_INCOME_EXPENSE_CROP_ID = "cropId";

    public static final String CROP_TASK_ID = "id";
    public static final String CROP_TASK_CROP_ID = "cropId";
    public static final String CROP_TASK_USER_ID = "userId";
    public static final String CROP_TASK_DATE = "date";
    public static final String CROP_TASK_TITLE = "title";
    public static final String CROP_TASK_TYPE = "type";
    public static final String CROP_TASK_EMPLOYEE_ID = "employeeId";
    public static final String CROP_TASK_STATUS = "status";
    public static final String CROP_TASK_DESCRIPTION = "description";
    public static final String CROP_TASK_RECURRENCE = "recurrence";
    public static final String CROP_TASK_REMINDERS = "reminders";
    public static final String CROP_TASK_FREQUENCY = "frequency";
    public static final String CROP_TASK_REPEAT_UNTIL = "repeatUntil";
    public static final String CROP_TASK_DAYS_BEFORE = "daysBefore";

    public static final String CROP_PRODUCT_ITEM_TYPE_SALES_ORDER = "salesOrder";
    public static final String CROP_PRODUCT_ITEM_TYPE_ESTIMATE = "estimate";
    public static final String CROP_PRODUCT_ITEM_TYPE_INVOICE = "invoice";
    public static final String CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER = "purchaseOrder";
    public static final String CROP_PRODUCT_ITEM_TYPE_BILL = "bill";

    public static final String CROP_PURCHASE_ORDER_ID = "id";
    public static final String CROP_PURCHASE_ORDER_USER_ID = "userId";
    public static final String CROP_PURCHASE_ORDER_SUPPLIER_ID = "supplierId";
    public static final String CROP_PURCHASE_ORDER_NUMBER = "number";
    public static final String CROP_PURCHASE_ORDER_REFERENCE_NUMBER = "referenceNumber";
    public static final String CROP_PURCHASE_ORDER_DELIVERY_METHOD = "deliveryMethod";
    public static final String CROP_PURCHASE_ORDER_DELIVERY_DATE = "deliveryDate";
    public static final String CROP_PURCHASE_ORDER_PURCHASE_DATE = "purchaseDate";
    public static final String CROP_PURCHASE_ORDER_DISCOUNT = "discount";
    public static final String CROP_PURCHASE_ORDER_NOTES = "notes";
    public static final String CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS = "termsAndConditions";
    public static final String CROP_PURCHASE_ORDER_STATUS = "status";

    public static final String CROP_BILL_ID = "id";
    public static final String CROP_BILL_USER_ID = "userId";
    public static final String CROP_BILL_SUPPLIER_ID = "supplierId";
    public static final String CROP_BILL_ORDER_NUMBER = "orderNumber";
    public static final String CROP_BILL_NUMBER = "number";
    public static final String CROP_BILL_DATE = "billDate";
    public static final String CROP_BILL_DUE_DATE = "dueDate";
    public static final String CROP_BILL_TERMS = "terms";
    public static final String CROP_BILL_DISCOUNT = "discount";
    public static final String CROP_BILL_NOTES = "notes";
    public static final String CROP_BILL_STATUS = "status";

    public static final String CROP_PAYMENT_BILL_ID = "id";
    public static final String CROP_PAYMENT_BILL_USER_ID = "userId";
    public static final String CROP_PAYMENT_BILL_SUPPLIER_ID = "supplierId";
    public static final String CROP_PAYMENT_BILL_DATE = "date";
    public static final String CROP_PAYMENT_BILL_PAYMENT_MADE = "amount";
    public static final String CROP_PAYMENT_BILL_PAYMENT_MODE = "mode";
    public static final String CROP_PAYMENT_BILL_PAID_THROUGH = "paidThrough";
    public static final String CROP_PAYMENT_BILL_REFERENCE_NUMBER = "referenceNumber";
    public static final String CROP_PAYMENT_BILL_NOTES = "notes";
    public static final String CROP_PAYMENT_BILL_BILL_ID = "billId";

    public static final String CROP_ITEM_ID = "id";
    public static final String CROP_ITEM_NAME = "name";
    public static final String CROP_ITEM_N_COMPOSITION = "nComposition";
    public static final String CROP_ITEM_P_COMPOSITION = "pComposition";
    public static final String CROP_ITEM_K_COMPOSITION = "kComposition";
    public static final String CROP_ITEM_IMAGE_RESOURCE_ID = "imageResourceId";
    public static final String CROP_ITEM_N_REMOVED = "nRemoved";
    public static final String CROP_ITEM_P_REMOVED = "pRemoved";
    public static final String CROP_ITEM_K_REMOVED = "kRemoved";
    public static final String CROP_ITEM_IS_FOR = "isFor";

    public static final String CROP_FERTILIZER_ID = "id";
    public static final String CROP_FERTILIZER_TYPE = "type";
    public static final String CROP_FERTILIZER_NAME = "name";
    public static final String CROP_FERTILIZER_N_PERCENTAGE = "nPercentage";
    public static final String CROP_FERTILIZER_P_PERCENTAGE = "pPercentage";
    public static final String CROP_FERTILIZER_K_PERCENTAGE = "kPercentage";

    public static final String CROP_MACHINE_TASK_ID = "id";
    public static final String CROP_MACHINE_TASK_MACHINE_ID = "machineId";
    //public static final String CROP_MACHINE_TASK_USER_ID = "userId";
    public static final String CROP_MACHINE_TASK_START_DATE = "startDate";
    public static final String CROP_MACHINE_TASK_END_DATE = "endDate";
    public static final String CROP_MACHINE_TASK_TITLE = "title";
    public static final String CROP_MACHINE_TASK_PERSONNEL = "responsible";
    public static final String CROP_MACHINE_TASK_STATUS = "status";
    public static final String CROP_MACHINE_TASK_DESCRIPTION = "description";
    public static final String CROP_MACHINE_TASK_RECURRENCE = "recurrence";
    public static final String CROP_MACHINE_TASK_FREQUENCY = "frequency";
    public static final String CROP_MACHINE_TASK_REMINDERS = "reminders";
    public static final String CROP_MACHINE_TASK_COST = "cost";
    public static final String CROP_MACHINE_TASK_DAYS_BEFORE = "daysBefore";
    public static final String CROP_MACHINE_TASK_REPEAT_UNTIL = "repeatUntil";

    public static final String CROP_NOTE_ID = "id";
    public static final String CROP_NOTE_DATE = "date";
    public static final String CROP_NOTE_PARENT_ID = "parentId";
    public static final String CROP_NOTE_CATEGORY = "category";
    public static final String CROP_NOTE_NOTES = "notes";
    public static final String CROP_NOTE_IS_FOR = "isFor";

    public static final String CROP_MACHINE_SERVICE_ID = "id";
    public static final String CROP_MACHINE_SERVICE_MACHINE_ID = "machineId";
    public static final String CROP_MACHINE_SERVICE_DATE = "date";
    public static final String CROP_MACHINE_SERVICE_CURRENT_HOURS = "currentHours";
    public static final String CROP_MACHINE_SERVICE_TYPE = "type";
    public static final String CROP_MACHINE_SERVICE_PERSONNEL = "responsible";
    public static final String CROP_MACHINE_SERVICE_DESCRIPTION = "description";
    public static final String CROP_MACHINE_SERVICE_RECURRENCE = "recurrence";
    public static final String CROP_MACHINE_SERVICE_FREQUENCY = "frequency";
    public static final String CROP_MACHINE_SERVICE_REMINDERS = "reminders";
    public static final String CROP_MACHINE_SERVICE_COST = "cost";
    public static final String CROP_MACHINE_SERVICE_DAYS_BEFORE = "daysBefore";
    public static final String CROP_MACHINE_SERVICE_REPEAT_UNTIL = "repeatUntil";

    public static final String CROP_IRRIGATION_ID = "id";
    public static final String CROP_IRRIGATION_USER_ID = "userId";
    public static final String CROP_IRRIGATION_CROP_ID = "cropId";
    public static final String CROP_IRRIGATION_DATE = "date";
    public static final String CROP_IRRIGATION_SYSTEM_RATE = "systemRate";
    public static final String CROP_IRRIGATION_START_TIME = "startTime";
    public static final String CROP_IRRIGATION_END_TIME = "endTime";
    public static final String CROP_IRRIGATION_AREA_IRRIGATED = "areaIrrigated";
    public static final String CROP_IRRIGATION_UNITS = "units";
    public static final String CROP_IRRIGATION_RECURRENCE = "recurrence";
    public static final String CROP_IRRIGATION_REMINDERS = "reminders";
    public static final String CROP_IRRIGATION_FREQUENCY = "frequency";
    public static final String CROP_IRRIGATION_REPEAT_UNTIL = "repeatUntil";
    public static final String CROP_IRRIGATION_DAYS_BEFORE = "daysBefore";
    public static final String CROP_IRRIGATION_COST = "totalCost";

    public static final String CROP_TRANSPLANTING_ID = "id";
    public static final String CROP_TRANSPLANTING_USER_ID = "userId";
    public static final String CROP_TRANSPLANTING_CROP_ID = "cropId";
    public static final String CROP_TRANSPLANTING_DATE = "operationDate";
    public static final String CROP_TRANSPLANTING_TOTAL_SEEDLING = "totalSeedling";
    public static final String CROP_TRANSPLANTING_SEEDLINGS_PER_HA = "seedlingsPerHa";
    public static final String CROP_TRANSPLANTING_VARIETY_EARLINESS = "varietyEarliness";
    public static final String CROP_TRANSPLANTING_CYCLE_LENGTH = "cycleLength";
    public static final String CROP_TRANSPLANTING_UNITS = "units";
    public static final String CROP_TRANSPLANTING_EXPECTED_YIELD = "expectedYield";
    public static final String CROP_TRANSPLANTING_EXPECTED_YIELD_PER_HA = "expectedYieldPerHa";
    public static final String CROP_TRANSPLANTING_OPERATOR = "operator";
    public static final String CROP_TRANSPLANTING_FREQUENCY = "frequency";
    public static final String CROP_TRANSPLANTING_REPEAT_UNTIL = "repeatUntil";
    public static final String CROP_TRANSPLANTING_DAYS_BEFORE = "daysBefore";
    public static final String CROP_TRANSPLANTING_RECURRENCE = "recurrence";
    public static final String CROP_TRANSPLANTING_REMINDERS = "reminders";
    public static final String CROP_TRANSPLANTING_COST = "totalCost";

    public static final String CROP_SCOUTING_ID = "id";
    public static final String CROP_SCOUTING_USER_ID = "userId";
    public static final String CROP_SCOUTING_CROP_ID = "cropId";
    public static final String CROP_SCOUTING_DATE = "date";
    public static final String CROP_SCOUTING_METHOD = "method";
    public static final String CROP_SCOUTING_INFESTED = "infested";
    public static final String CROP_SCOUTING_INFESTATION_TYPE = "infestationType";
    public static final String CROP_SCOUTING_INFESTATION = "infestation";
    public static final String CROP_SCOUTING_INFESTATION_LEVEL = "infestationLevel";
    public static final String CROP_SCOUTING_COST = "cost";
    public static final String CROP_SCOUTING_REMARKS = "remarks";
    public static final String CROP_SCOUTING_FREQUENCY = "frequency";
    public static final String CROP_SCOUTING_REPEAT_UNTIL = "repeatUntil";
    public static final String CROP_SCOUTING_DAYS_BEFORE = "daysBefore";
    public static final String CROP_SCOUTING_RECURRENCE = "recurrence";
    public static final String CROP_SCOUTING_REMINDERS = "reminders";

    public static final String CROP_HARVEST_ID = "id";
    public static final String CROP_HARVEST_USER_ID = "userId";
    public static final String CROP_HARVEST_CROP_ID = "cropId";
    public static final String CROP_HARVEST_EMPLOYEE_ID = "employeeId";
    public static final String CROP_HARVEST_DATE = "date";
    public static final String CROP_HARVEST_METHOD = "method";
    public static final String CROP_HARVEST_UNITS = "harvestUnits";
    public static final String CROP_HARVEST_QUANTITY = "quantity";
    public static final String CROP_HARVEST_OPERATOR = "operator";
    public static final String CROP_HARVEST_STATUS = "status";
    public static final String CROP_HARVEST_DATE_SOLD = "dateSold";
    public static final String CROP_HARVEST_CUSTOMER = "customer";
    public static final String CROP_HARVEST_PRICE = "price";
    public static final String CROP_HARVEST_QUANTITY_SOLD = "quantitySold";
    public static final String CROP_HARVEST_STORAGE_DATE = "storageDate";
    public static final String CROP_HARVEST_QUANTITY_STORED = "quantityStored";
    public static final String CROP_HARVEST_COST = "cost";
    public static final String CROP_HARVEST_FREQUENCY = "frequency";
    public static final String CROP_HARVEST_REPEAT_UNTIL = "repeatUntil";
    public static final String CROP_HARVEST_DAYS_BEFORE = "daysBefore";
    public static final String CROP_HARVEST_RECURRENCE = "recurrence";
    public static final String CROP_HARVEST_REMINDERS = "reminders";

    public static final String CROP_CONTACT_ID = "id";
    public static final String CROP_CONTACT_USER_ID = "userId";
    public static final String CROP_CONTACT_TYPE = "type";
    public static final String CROP_CONTACT_NAME = "name";
    public static final String CROP_CONTACT_BUSINESS_NAME = "businessName";
    public static final String CROP_CONTACT_ADDRESS = "address";
    public static final String CROP_CONTACT_PHONE_NUMBER = "phoneNumber";
    public static final String CROP_CONTACT_EMAIL = "email";
    public static final String CROP_CONTACT_WEBSITE = "website";

    public static final String CROP_SETTINGS_ID = "id";
    public static final String CROP_SETTINGS_USER_ID = "userId";
    public static final String CROP_SETTINGS_DATE_FORMAT = "dateFormat";
    public static final String CROP_SETTINGS_WEIGHT_UNITS = "weightUnits";
    public static final String CROP_SETTINGS_AREA_UNITS = "areaUnits";
    public static final String CROP_SETTINGS_CURRENCY = "currency";

    public static final String CROP_NOTIFICATION_ID = "id";
    public static final String CROP_NOTIFICATION_USER_ID = "userId";
    public static final String CROP_NOTIFICATION_DATE = "date";
    public static final String CROP_NOTIFICATION_MESSAGE = "message";
    public static final String CROP_NOTIFICATION_STATUS = "status";
    public static final String CROP_NOTIFICATION_ACTION_DATE = "actionDate";
    public static final String CROP_NOTIFICATION_TYPE = "type";
    public static final String CROP_NOTIFICATION_REPORT_FROM = "reportFrom";
    public static final String CROP_NOTIFICATION_SOURCE_ID = "sourceId"; //cropId, taskId, machineId ....they can be identified when combined with the type

    public static final String CROP_GLOBAL_ID = "globalId";
    public static final String CROP_SYNC_STATUS = "syncStatus";

    public static final String CROP_DELETED_DATE = "date";
    public static final String CROP_DELETED_TYPE = "type";
    public static final String CROP_DELETED_ID = "id";

    public static final String ADD_PRODUCE_TABLE_NAME = "produce";
    public static final String ADD_PRODUCE_ID = "id";
    public static final String ADD_PRODUCE_NAME = "name";
    public static final String ADD_PRODUCE_VARIETY = "variety";
    public static final String ADD_PRODUCE_QUANTITY = "quantity";
    public static final String ADD_PRODUCE_PRICE = "price";
    public static final String ADD_PRODUCE_DATE = "date";
    public static final String ADD_PRODUCE_IMAGE = "image";

    public static final String MARKET_PRICE_TABLE_NAME = "market_price";
    public static final String MARKET_PRICE_ID = "id";
    public static final String MARKET_PRICE_CROP = "crop";
    public static final String MARKET_PRICE_TABLE_MARKET = "market";
    public static final String MARKET_PRICE_RETAIL = "retail";
    public static final String MARKET_PRICE_WHOLESALE = "wholesale";

    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME = "breedingStock";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_ID = "id";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID = "userId";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_NAME = "name";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_EAR_TAG = "earTag";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_COLOR = "color";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_SEX = "sex";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_BREED = "breed";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_DATE_OF_BIRTH = "dateOfBirth";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_SOURCE = "source";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_WEIGHT = "weight";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_FATHER = "father";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_MOTHER_DAM = "motherDam";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_PHOTO = "photo";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_SYNC_STATUS = "syncStatus";
    public static final String LIVESTOCK_RECORDS_BREEDING_STOCK_GLOBAL_ID = "globalId";
    public static final String LIVESTOCK_RECORDS_ANIMAL_TYPE = "animalType";


    public static final String LIVESTOCK_RECORDS_MATING_TABLE_NAME = "mating";
    public static final String LIVESTOCK_RECORDS_MATING_ID = "id";
    public static final String LIVESTOCK_RECORDS_MATING_USER_ID = "userId";
    public static final String LIVESTOCK_RECORDS_MATING_DATE = "matingDate";
    public static final String LIVESTOCK_RECORDS_MATING_MALE_NAME = "maleName";
    public static final String LIVESTOCK_RECORDS_MATING_FEMALE_NAME = "femaleName";
    public static final String LIVESTOCK_RECORDS_MATING_METHOD = "method";
    public static final String LIVESTOCK_RECORDS_MATING_GESTATION_PERIOD = "gestationPeriod";
    public static final String LIVESTOCK_RECORDS_MATING_DELIVERY_ALERT_DAYS_BEFORE = "deliveryAlertDaysBefore";
    public static final String LIVESTOCK_RECORDS_MATING_NOTES = "notes";
    public static final String LIVESTOCK_RECORDS_MATING_SYNC_STATUS = "syncStatus";
    public static final String LIVESTOCK_RECORDS_MATING_GLOBAL_ID = "globalId";


    public static final String LIVESTOCK_RECORDS_LITTERS_TABLE_NAME = "litters";
    public static final String LIVESTOCK_RECORDS_LITTERS_ID = "id";
    public static final String LIVESTOCK_RECORDS_LITTERS_USER_ID = "userId";
    public static final String LIVESTOCK_RECORDS_LITTERS_DATE_OF_BIRTH = "dateOfBirth";
    public static final String LIVESTOCK_RECORDS_LITTERS_LITTER_SIZE = "litterSize";
    public static final String LIVESTOCK_RECORDS_LITTERS_BREEDING_ID = "breedingId";
    public static final String LIVESTOCK_RECORDS_LITTERS_BORN_ALIVE = "bornAlive";
    public static final String LIVESTOCK_RECORDS_LITTERS_BORN_DEAD = "bornDead";
    public static final String LIVESTOCK_RECORDS_LITTERS_NO_OF_MALE = "noOfMale";
    public static final String LIVESTOCK_RECORDS_LITTERS_NO_OF_FEMALE = "noOfFemale";
    public static final String LIVESTOCK_RECORDS_LITTERS_WEANING = "weaning";
    public static final String LIVESTOCK_RECORDS_LITTERS_WEANING_ALERT = "weaningAlert";
    public static final String LIVESTOCK_RECORDS_LITTERS_MOTHER_DAM = "motherDam";
    public static final String LIVESTOCK_RECORDS_LITTERS_FATHER_SIRE = "fatherSire";
    public static final String LIVESTOCK_RECORDS_LITTERS_SIRE_ID = "sire_id";
    public static final String LIVESTOCK_RECORDS_LITTERS_DAM_ID = "dam_id";
    public static final String LIVESTOCK_RECORDS_LITTERS_SYNC_STATUS = "syncStatus";
    public static final String LIVESTOCK_RECORDS_LITTERS_GLOBAL_ID = "globalId";


    public static final String LIVESTOCK_RECORDS_MEDICATIONS_TABLE_NAME = "medications";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_ID = "id";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_USER_ID = "userId";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_DATE = "medicationDate";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_TYPE = "medicationType";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_BREEDING_ID = "breedingId";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_HEALTH_CONDITION = "healthCondition";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_NAME = "medicationName";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_MANUFACTURER = "manufacturer";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_DOSAGE = "dosage";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_TREATMENT_PERIOD = "treatmentPeriod";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_NOTES = "note";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_ANIMAL = "animal";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_TECHNICAL_PERSONAL = "technicalPersonal";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_SYNC_STATUS = "syncStatus";
    public static final String LIVESTOCK_RECORDS_MEDICATIONS_GLOBAL_ID = "globalId";

    public static final String CROP_RECORDS_CROP_GALLERY_TABLE_NAME = "cropGallery";
    public static final String CROP_RECORDS_CROP_GALLERY_ID = "id";
    public static final String CROP_RECORDS_CROP_GALLERY_USER_ID = "userId";
    public static final String CROP_RECORDS_CROP_GALLERY_PARENT_ID = "parentId";
    public static final String CROP_RECORDS_CROP_GALLERY_PHOTO = "photo";
    public static final String CROP_RECORDS_CROP_GALLERY_CAPTION = "caption";
    public static final String CROP_RECORDS_CROP_GALLERY_SYNC_STATUS = "syncStatus";
    public static final String CROP_RECORDS_CROP_GALLERY_GLOBAL_ID = "globalId";


    public static final String REGIONS_DETAILS_TABLE_NAME ="regionDetails";
    public static final String REGIONS_DETAILS_TABLE_ID ="tableId";
    public static final String REGIONS_DETAILS_ID ="id";
    public static final String REGIONS_DETAILS_REGION_TYPE ="regionType";
    public static final String REGIONS_DETAILS_REGION ="region";
    public static final String REGIONS_DETAILS_BELONGS_TO ="belongs_to";



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

        String crop_contact_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_CONTACT_TABLE_NAME + " ( " + CROP_CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                CROP_CONTACT_USER_ID + " TEXT, " + CROP_CONTACT_TYPE + " TEXT NOT NULL, " + CROP_CONTACT_NAME + " TEXT NOT NULL, " + CROP_CONTACT_BUSINESS_NAME + " TEXT, " +
                CROP_CONTACT_ADDRESS + " TEXT NOT NULL, " + CROP_CONTACT_PHONE_NUMBER + " TEXT NOT NULL, " + CROP_CONTACT_EMAIL + " TEXT, " + CROP_CONTACT_WEBSITE + " TEXT , " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";


        String crop_harvest_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_HARVEST_TABLE_NAME + " ( " + CROP_HARVEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                CROP_HARVEST_USER_ID + " TEXT, " + CROP_HARVEST_CROP_ID + " TEXT, " + CROP_HARVEST_EMPLOYEE_ID + " TEXT, " + CROP_HARVEST_DATE + " TEXT NOT NULL, " + CROP_HARVEST_METHOD + " TEXT, " +
                CROP_HARVEST_UNITS + " TEXT NOT NULL, " + CROP_HARVEST_QUANTITY + " REAL NOT NULL, " + CROP_HARVEST_OPERATOR + " TEXT, " + CROP_HARVEST_STATUS + " TEXT NOT NULL, " +
                CROP_HARVEST_DATE_SOLD + " TEXT DEFAULT '', " + CROP_HARVEST_CUSTOMER + " TEXT DEFAULT '', " + CROP_HARVEST_PRICE + " REAL DEFAULT 0, " + CROP_HARVEST_QUANTITY_SOLD + " REAL DEFAULT 0, " +
                CROP_HARVEST_STORAGE_DATE + " TEXT DEFAULT '', " + CROP_HARVEST_QUANTITY_STORED + " REAL DEFAULT 0, " + CROP_HARVEST_COST + " REAL DEFAULT 0, " + CROP_HARVEST_REPEAT_UNTIL + " TEXT, " + CROP_HARVEST_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_HARVEST_RECURRENCE + " TEXT NOT NULL, " + CROP_HARVEST_FREQUENCY + " REAL DEFAULT 1, " + CROP_HARVEST_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_scouting_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_SCOUTING_TABLE_NAME + " ( " + CROP_SCOUTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_SCOUTING_USER_ID + " TEXT , " +
                CROP_SCOUTING_CROP_ID + " TEXT, " + CROP_SCOUTING_DATE + " TEXT NOT NULL, " + CROP_SCOUTING_METHOD + " TEXT, " + CROP_SCOUTING_INFESTED + " TEXT NOT NULL, " +
                CROP_SCOUTING_INFESTATION_TYPE + " TEXT, " + CROP_SCOUTING_INFESTATION + " TEXT, " + CROP_SCOUTING_INFESTATION_LEVEL + " TEXT, " +
                CROP_SCOUTING_COST + " REAL NOT NULL DEFAULT 0, " + CROP_SCOUTING_REMARKS + " TEXT, " + CROP_SCOUTING_REPEAT_UNTIL + " TEXT, " + CROP_SCOUTING_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_SCOUTING_RECURRENCE + " TEXT NOT NULL, " + CROP_SCOUTING_FREQUENCY + " REAL DEFAULT 1, " + CROP_SCOUTING_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";


        String crop_transplanting_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_TRANSPLANTING_TABLE_NAME + " ( " + CROP_TRANSPLANTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_TRANSPLANTING_USER_ID + " TEXT, " + CROP_TRANSPLANTING_CROP_ID + " TEXT, " + CROP_TRANSPLANTING_DATE + " TEXT NOT NULL, " + CROP_TRANSPLANTING_TOTAL_SEEDLING + " REAL, " +
                CROP_TRANSPLANTING_SEEDLINGS_PER_HA + " REAL, " + CROP_TRANSPLANTING_VARIETY_EARLINESS + " TEXT NOT NULL, " + CROP_TRANSPLANTING_CYCLE_LENGTH + " REAL NOT NULL, " +
                CROP_TRANSPLANTING_UNITS + " TEXT, " + CROP_TRANSPLANTING_EXPECTED_YIELD + " REAL DEFAULT 0, " + CROP_TRANSPLANTING_EXPECTED_YIELD_PER_HA + " REAL DEFAULT 0, " +
                CROP_TRANSPLANTING_OPERATOR + " TEXT NOT NULL, " + CROP_TRANSPLANTING_COST + " REAL NOT NULL, " + CROP_TRANSPLANTING_REPEAT_UNTIL + " TEXT, " + CROP_TRANSPLANTING_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_TRANSPLANTING_RECURRENCE + " TEXT NOT NULL, " + CROP_TRANSPLANTING_FREQUENCY + " REAL DEFAULT 1, " + CROP_TRANSPLANTING_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";


        String crop_inventory_fertilizer_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + " ( " + CROP_INVENTORY_FERTILIZER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_INVENTORY_FERTILIZER_USER_ID + " TEXT," + CROP_INVENTORY_FERTILIZER_DATE + " TEXT NOT NULL," + CROP_INVENTORY_FERTILIZER_TYPE + " TEXT NOT NULL," + CROP_INVENTORY_FERTILIZER_NAME + " TEXT NOT NULL," + CROP_INVENTORY_FERTILIZER_N_PERCENTAGE + " REAL," +
                CROP_INVENTORY_FERTILIZER_P_PERCENTAGE + " REAL," + CROP_INVENTORY_FERTILIZER_K_PERCENTAGE + " REAL," + CROP_INVENTORY_FERTILIZER_QUANTITY + " REAL NOT NULL," + CROP_INVENTORY_FERTILIZER_BATCH_NUMBER + " TEXT NOT NULL," +
                CROP_INVENTORY_FERTILIZER_SERIAL_NUMBER + " TEXT," + CROP_INVENTORY_FERTILIZER_SUPPLIER + " TEXT," + CROP_INVENTORY_FERTILIZER_USAGE_UNIT + " TEXT ," + CROP_INVENTORY_FERTILIZER_COST + " REAL ," + CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_CA + " REAL ," +
                CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_MG + " REAL ," + CROP_INVENTORY_FERTILIZER_MACRO_NUTRIENTS_S + " REAL ," + CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_B + " REAL ," +
                CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MN + " REAL ," + CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CL + " REAL ," + CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_MO + " REAL ," + CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_CU + " REAL ," +
                CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_ZN + " REAL ," + CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_FE + " REAL ," + CROP_INVENTORY_FERTILIZER_MICRO_NUTRIENTS_NA + " REAL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_seeds_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_INVENTORY_SEEDS_TABLE_NAME + " ( " + CROP_INVENTORY_SEEDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_INVENTORY_SEEDS_USER_ID + " TEXT," + CROP_INVENTORY_SEEDS_DATE + " TEXT NOT NULL," + CROP_INVENTORY_SEEDS_NAME
                + " TEXT NOT NULL," + CROP_INVENTORY_SEEDS_VARIETY + " TEXT," +
                CROP_INVENTORY_SEEDS_DRESSING + " TEXT," + CROP_INVENTORY_FERTILIZER_QUANTITY + " REAL NOT NULL," + CROP_INVENTORY_SEEDS_BATCH_NUMBER + " TEXT NOT NULL," + CROP_INVENTORY_SEEDS_TYPE + " TEXT NOT NULL," +
                CROP_INVENTORY_SEEDS_COST + " REAL ," + CROP_INVENTORY_SEEDS_SUPPLIER + " TEXT ," + CROP_INVENTORY_SEEDS_TGW + " TEXT ," + CROP_INVENTORY_FERTILIZER_USAGE_UNIT + " TEXT, " + CROP_INVENTORY_SEEDS_MANUFACTURER + " TEXT," + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";


        String crop_inventory_spray_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_INVENTORY_SPRAY_TABLE_NAME + " ( " + CROP_INVENTORY_SPRAY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_INVENTORY_SPRAY_USER_ID + " TEXT," + CROP_INVENTORY_SPRAY_DATE + " TEXT NOT NULL," + CROP_INVENTORY_SPRAY_TYPE + " TEXT NOT NULL," + CROP_INVENTORY_SPRAY_NAME
                + " TEXT NOT NULL," + CROP_INVENTORY_SPRAY_VARIETY + " TEXT," +
                CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS + " TEXT," + CROP_INVENTORY_SPRAY_QUANTITY + " REAL NOT NULL," + CROP_INVENTORY_SPRAY_BATCH_NUMBER + " TEXT NOT NULL," +
                CROP_INVENTORY_SPRAY_COST + " REAL ," + CROP_INVENTORY_SPRAY_SUPPLIER + " TEXT ," + CROP_INVENTORY_SPRAY_EXPIRY_DATE + " TEXT ," + CROP_INVENTORY_SPRAY_HARVEST_INTERVAL + " TEXT ," + CROP_INVENTORY_SPRAY_USAGE_UNIT + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_CROP_TABLE_NAME + " ( " + CROP_CROP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_CROP_USER_ID + " TEXT," + CROP_CROP_VARIETY + " TEXT ," + CROP_CROP_YEAR + " INTEGER," + CROP_CROP_NAME + " TEXT NOT NULL," +
                CROP_CROP_FIELD_ID + " TEXT NOT NULL," + CROP_CROP_GROWING_CYCLE + " TEXT," + CROP_CROP_SEASON + " TEXT," + CROP_CROP_DATE_SOWN + " TEXT NOT NULL," +
                CROP_CROP_AREA + " REAL," + CROP_CROP_OPERATOR + " TEXT ," +
                CROP_CROP_COST + " REAL ," + CROP_CROP_SEED_ID + " TEXT ," + CROP_CROP_HARVEST_UNITS + " TEXT ," + CROP_CROP_ESTIMATED_YIELD + " REAL DEFAULT 0," +
                CROP_CROP_ESTIMATED_REVENUE + " REAL DEFAULT 0," + CROP_CROP_RATE + " REAL DEFAULT 0," + CROP_CROP_PLANTING_METHOD + " TEXT  ," + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_cultivate_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_CULTIVATION_TABLE_NAME + " ( " + CROP_CULTIVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_CULTIVATION_USER_ID + " TEXT," + CROP_CULTIVATION_CROP_ID + " TEXT NOT NULL," + CROP_CULTIVATION_DATE + " TEXT NOT NULL," + CROP_CULTIVATION_OPERATION + " TEXT NOT NULL," + CROP_CULTIVATION_OPERATOR + " TEXT NOT NULL," +
                CROP_CULTIVATION_COST + " REAL," + CROP_CULTIVATION_NOTES + " TEXT, " + CROP_CULTIVATION_REPEAT_UNTIL + " TEXT, " + CROP_CULTIVATION_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_CULTIVATION_RECURRENCE + " TEXT NOT NULL, " + CROP_CULTIVATION_FREQUENCY + " REAL DEFAULT 1, " + CROP_CULTIVATION_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_fertilizer_application_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " ( " + CROP_FERTILIZER_APPLICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_FERTILIZER_APPLICATION_USER_ID + " TEXT," + CROP_FERTILIZER_APPLICATION_CROP_ID + " TEXT NOT NULL," + CROP_FERTILIZER_APPLICATION_DATE + " TEXT NOT NULL," + CROP_FERTILIZER_APPLICATION_OPERATOR + " TEXT," +
                CROP_FERTILIZER_APPLICATION_METHOD + " TEXT," + CROP_FERTILIZER_APPLICATION_REASON + " TEXT, " + CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM + " TEXT NOT NULL, " + CROP_FERTILIZER_APPLICATION_FERTILIZER_ID + " TEXT," +
                CROP_FERTILIZER_APPLICATION_RATE + " REAL ," + CROP_FERTILIZER_APPLICATION_COST + " REAL, " + CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL + " TEXT, " + CROP_FERTILIZER_APPLICATION_DAYS_BEFORE + " REAL DEFAULT 0, " + CROP_FERTILIZER_APPLICATION_FERTILIZER_NAME + " TEXT, " + CROP_FERTILIZER_APPLICATION_FERTILIZER_UNITS + " TEXT," +
                CROP_FERTILIZER_APPLICATION_RECURRENCE + " TEXT NOT NULL, " + CROP_FERTILIZER_APPLICATION_FREQUENCY + " REAL DEFAULT 1, " + CROP_FERTILIZER_APPLICATION_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";


        String crop_spraying_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_SPRAYING_TABLE_NAME + " ( " + CROP_SPRAYING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_SPRAYING_USER_ID + " TEXT," + CROP_SPRAYING_CROP_ID + " TEXT NOT NULL," + CROP_SPRAYING_DATE + " TEXT NOT NULL," + CROP_SPRAYING_START_TIME + " TEXT," +
                CROP_SPRAYING_END_TIME + " TEXT," + CROP_SPRAYING_OPERATOR + " TEXT," +
                CROP_SPRAYING_WATER_VOLUME + " REAL ," + CROP_SPRAYING_WATER_CONDITION + " TEXT," + CROP_SPRAYING_WIND_DIRECTION + " TEXT, " + CROP_SPRAYING_EQUIPMENT_USED + " TEXT ," + CROP_SPRAYING_UNITS + " TEXT ," +
                CROP_SPRAYING_SPRAY_ID + " TEXT," + CROP_SPRAYING_RATE + " REAL NOT NULL ," + CROP_SPRAYING_TREATMENT_REASON + " TEXT ," + CROP_SPRAYING_COST + " REAL, " + CROP_SPRAYING_REPEAT_UNTIL + " TEXT, " + CROP_SPRAYING_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_SPRAYING_RECURRENCE + " TEXT NOT NULL, " + CROP_SPRAYING_FREQUENCY + " REAL DEFAULT 1, " + CROP_SPRAYING_REMINDERS + " TEXT NOT NULL, " + CROP_SPRAYING_SPRAY_NAME + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_field_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_FIELDS_TABLE_NAME + " ( " + CROP_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_FIELD_USER_ID + " TEXT," + CROP_FIELD_NAME + " TEXT NOT NULL," + CROP_FIELD_SOIL_CATEGORY + " TEXT," + CROP_FIELD_SOIL_TYPE + " TEXT," + CROP_FIELD_WATERCOURSE + " TEXT," +
                CROP_FIELD_TOTAL_AREA + " REAL NOT NULL ," + CROP_FIELD_CROPPABLE_AREA + " REAL ," + CROP_FIELD_FIELD_TYPE + " TEXT NOT NULL ," +
                CROP_FIELD_LAYOUT_TYPE + " TEXT ," + CROP_FIELD_STATUS + " TEXT NOT NULL ," + CROP_FIELD_UNITS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_soil_analysis_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_SOIL_ANALYSIS_TABLE_NAME + " ( " + CROP_SOIL_ANALYSIS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_SOIL_ANALYSIS_USER_ID + " TEXT ," + CROP_SOIL_ANALYSIS_FIELD_ID + " TEXT," + CROP_SOIL_ANALYSIS_DATE + " TEXT NOT NULL," + CROP_SOIL_ANALYSIS_PH + " REAL," + CROP_SOIL_ANALYSIS_ORGANIC_MATTER + " TEXT," +
                CROP_SOIL_ANALYSIS_AGRONOMIST + " TEXT NOT NULL ," + CROP_SOIL_ANALYSIS_COST + " REAL  NOT NULL  ," + CROP_SOIL_ANALYSIS_RESULTS + " TEXT NOT NULL, " + CROP_SOIL_ANALYSIS_REPEAT_UNTIL + " TEXT, " + CROP_SOIL_ANALYSIS_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_SOIL_ANALYSIS_RECURRENCE + " TEXT NOT NULL, " + CROP_SOIL_ANALYSIS_FREQUENCY + " REAL DEFAULT 1, " + CROP_SOIL_ANALYSIS_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_machine_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_MACHINE_TABLE_NAME + " ( " + CROP_MACHINE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_MACHINE_USER_ID + " TEXT," + CROP_MACHINE_NAME + " TEXT NOT NULL," + CROP_MACHINE_BRAND + " TEXT ," + CROP_MACHINE_CATEGORY + " TEXT NOT NULL," + CROP_MACHINE_MANUFACTURER + " TEXT ," + CROP_MACHINE_MODEL + " TEXT ," +
                CROP_MACHINE_REGISTRATION_NUMBER + " TEXT," + CROP_MACHINE_QUANTITY + " REAL NOT NULL," + CROP_MACHINE_DATE_ACQUIRED + " TEXT NOT NULL," +
                CROP_MACHINE_PURCHASED_FROM + " TEXT NOT NULL," + CROP_MACHINE_STORAGE_LOCATION + " TEXT," + CROP_MACHINE_PURCHASE_PRICE + " REAL NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_employee_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_EMPLOYEE_TABLE_NAME + " ( " + CROP_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_EMPLOYEE_USER_ID + " TEXT," + CROP_EMPLOYEE_FIRST_NAME + " TEXT NOT NULL," + CROP_EMPLOYEE_LAST_NAME + " TEXT NOT NULL," + CROP_EMPLOYEE_TITLE + " TEXT NOT NULL ," +
                CROP_EMPLOYEE_PHONE + " TEXT NOT NULL," + CROP_EMPLOYEE_MOBILE + " TEXT ," + CROP_EMPLOYEE_EMP_ID + " TEXT ," +
                CROP_EMPLOYEE_GENDER + " TEXT NOT NULL," + CROP_EMPLOYEE_ADDRESS + " TEXT ," + CROP_EMPLOYEE_EMAIL + " TEXT ," +
                CROP_EMPLOYEE_DOB + " TEXT," + CROP_EMPLOYEE_HIRE_DATE + " TEXT," + CROP_EMPLOYEE_EMPLOYMENT_STATUS + " TEXT NOT NULL," +
                CROP_EMPLOYEE_PAY_AMOUNT + " REAL NOT NULL," + CROP_EMPLOYEE_PAY_RATE + " TEXT ," + CROP_EMPLOYEE_PAY_TYPE + " TEXT NOT NULL," + CROP_EMPLOYEE_SUPERVISOR + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_customer_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_CUSTOMER_TABLE_NAME + " ( " + CROP_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_CUSTOMER_USER_ID + " TEXT," + CROP_CUSTOMER_NAME + " TEXT NOT NULL," + CROP_CUSTOMER_COMPANY + " TEXT NOT NULL," + CROP_CUSTOMER_TAX_REG_NO + " TEXT ," +
                CROP_CUSTOMER_PHONE + " TEXT NOT NULL," + CROP_CUSTOMER_MOBILE + " TEXT ," + CROP_CUSTOMER_EMAIL + " TEXT ," +
                CROP_CUSTOMER_OPENING_BALANCE + " REAL NOT NULL," + CROP_CUSTOMER_BILL_ADDRESS_STREET + " TEXT NOT NULL ," + CROP_CUSTOMER_BILL_ADDRESS_CITY + " TEXT NOT NULL ," +
                CROP_CUSTOMER_BILL_ADDRESS_COUNTRY + " TEXT NOT NULL," + CROP_CUSTOMER_SHIP_ADDRESS_STREET + " TEXT NOT NULL," + CROP_CUSTOMER_SHIP_ADDRESS_CITY + " TEXT NOT NULL," +
                CROP_CUSTOMER_SHIP_ADDRESS_COUNTRY + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";
        String crop_supplier_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_SUPPLIER_TABLE_NAME + " ( " + CROP_SUPPLIER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_SUPPLIER_USER_ID + " TEXT," + CROP_SUPPLIER_NAME + " TEXT NOT NULL," + CROP_SUPPLIER_COMPANY + " TEXT NOT NULL," + CROP_SUPPLIER_TAX_REG_NO + " TEXT ," +
                CROP_SUPPLIER_PHONE + " TEXT NOT NULL," + CROP_SUPPLIER_MOBILE + " TEXT ," + CROP_SUPPLIER_EMAIL + " TEXT ," + CROP_SUPPLIER_OPENING_BALANCE + " REAL ," + CROP_SUPPLIER_INVOICE_ADDRESS_STREET + " TEXT NOT NULL," + CROP_SUPPLIER_INVOICE_ADDRESS_CITY + " TEXT NOT NULL," +
                CROP_SUPPLIER_INVOICE_ADDRESS_COUNTRY + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";
        String crop_product_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_PRODUCT_TABLE_NAME + " ( " + CROP_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_PRODUCT_USER_ID + " TEXT NOT NULL," + CROP_PRODUCT_NAME + " TEXT NOT NULL," + CROP_PRODUCT_TYPE + " TEXT NOT NULL," + CROP_PRODUCT_CODE + " TEXT ," +
                CROP_PRODUCT_UNITS + " TEXT," + CROP_PRODUCT_LINKED_ACCOUNT + " TEXT ," + CROP_PRODUCT_OPENING_COST + " REAL  ," + CROP_PRODUCT_OPENING_QUANTITY + " REAL ," +
                CROP_PRODUCT_SELLING_PRICE + " REAL ," + CROP_PRODUCT_TAX_RATE + " REAL ," +
                CROP_PRODUCT_DESCRIPTION + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_estimates_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_ESTIMATE_TABLE_NAME + " ( " + CROP_ESTIMATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_ESTIMATE_USER_ID + " TEXT NOT NULL," + CROP_ESTIMATE_CUSTOMER_ID + " TEXT NOT NULL," + CROP_ESTIMATE_NO + " TEXT NOT NULL," + CROP_ESTIMATE_DATE + " TEXT NOT NULL," +
                CROP_ESTIMATE_REFERENCE_NO + " TEXT NOT NULL," + CROP_ESTIMATE_EXP_DATE + " TEXT," + CROP_ESTIMATE_DISCOUNT + " REAL DEFAULT 0," + CROP_ESTIMATE_SHIPPING_CHARGES + " REAL DEFAULT 0  ," +
                CROP_ESTIMATE_CUSTOMER_NOTES + " TEXT ," + CROP_ESTIMATE_STATUS + " TEXT DEFAULT 'DRAFT' ," + CROP_ESTIMATE_TERMS_AND_CONDITIONS + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";


        String crop_estimate_item_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_PRODUCT_ITEM_TABLE_NAME + " ( " + CROP_PRODUCT_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_PRODUCT_ITEM_PRODUCT_ID + " TEXT NOT NULL," + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " TEXT NOT NULL," + CROP_PRODUCT_ITEM_QUANTITY + " REAL NOT NULL, " + CROP_PRODUCT_ITEM_TAX + " REAL NOT NULL, " +
                CROP_PRODUCT_ITEM_RATE + " REAL NOT NULL, " + CROP_PRODUCT_ITEM_TYPE + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no', " +
                "FOREIGN KEY( " + CROP_PRODUCT_ITEM_PRODUCT_ID + ") REFERENCES  " + CROP_PRODUCT_TABLE_NAME + " ( " + CROP_PRODUCT_ID + " ) )";


        String crop_invoices_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_INVOICE_TABLE_NAME + " ( " + CROP_INVOICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_INVOICE_USER_ID + " TEXT NOT NULL," + CROP_INVOICE_CUSTOMER_ID + " TEXT NOT NULL," + CROP_INVOICE_NO + " TEXT NOT NULL," + CROP_INVOICE_TERMS + " TEXT NOT NULL," +
                CROP_INVOICE_ORDER_NUMBER + " TEXT NOT NULL," + CROP_INVOICE_DATE + " TEXT NOT NULL," +
                CROP_INVOICE_DUE_DATE + " TEXT," + CROP_INVOICE_DISCOUNT + " REAL DEFAULT 0," + CROP_INVOICE_SHIPPING_CHARGES + " REAL DEFAULT 0  ," +
                CROP_INVOICE_CUSTOMER_NOTES + " TEXT ," + CROP_INVOICE_TERMS_AND_CONDITIONS + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";


        String crop_payment_item_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_PAYMENT_TABLE_NAME + " ( " + CROP_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_PAYMENT_USER_ID + " TEXT NOT NULL," + CROP_PAYMENT_CUSTOMER_ID + " TEXT NOT NULL," + CROP_PAYMENT_DATE + " TEXT NOT NULL," + CROP_PAYMENT_MODE + " TEXT NOT NULL, " + CROP_PAYMENT_AMOUNT + " REAL NOT NULL, " +
                CROP_PAYMENT_REFERENCE_NO + " TEXT , " + CROP_PAYMENT_NUMBER + " TEXT , " + CROP_PAYMENT_NOTES + " TEXT , " + CROP_PAYMENT_INVOICE_ID + " TEXT , " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no', " + " FOREIGN KEY ( " + CROP_PAYMENT_INVOICE_ID + ") REFERENCES  " + CROP_INVOICE_TABLE_NAME + " ( " + CROP_INVOICE_ID + " ), " +
                "FOREIGN KEY( " + CROP_PAYMENT_CUSTOMER_ID + ") REFERENCES  " + CROP_CUSTOMER_TABLE_NAME + " ( " + CROP_CUSTOMER_ID + " ) )";
        String crop_income_expense_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_INCOME_EXPENSE_TABLE_NAME + " ( " + CROP_INCOME_EXPENSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CROP_INCOME_EXPENSE_DATE + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_USER_ID + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_TRANSACTION + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_ITEM +
                " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_CATEGORY + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_QUANTITY + " REAL NOT NULL, " + CROP_INCOME_EXPENSE_GROSS_AMOUNT + " REAL NOT NULL, " + CROP_INCOME_EXPENSE_UNIT_PRICE + " REAL NOT NULL, " + CROP_INCOME_EXPENSE_TAXES + " REAL, "
                + CROP_INCOME_EXPENSE_PAYMENT_MODE + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_PAYMENT_STATUS + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_SELLING_PRICE + " REAL , " + CROP_INCOME_EXPENSE_CUSTOMER_SUPPLIER + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_DEPARTMENT + " TEXT, " + CROP_INCOME_EXPENSE_CROP_ID + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_task_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_TASK_TABLE_NAME + " ( " + CROP_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_TASK_CROP_ID + " TEXT NOT NULL, " + CROP_TASK_USER_ID + " TEXT NOT NULL, " + CROP_TASK_DATE + " TEXT NOT NULL, " + CROP_TASK_TITLE + " TEXT NOT NULL, " +
                CROP_TASK_EMPLOYEE_ID + " TEXT NOT NULL, " + CROP_TASK_STATUS + " TEXT NOT NULL, " + CROP_TASK_TYPE + " TEXT NOT NULL, " + CROP_TASK_DESCRIPTION + " TEXT NOT NULL, " + CROP_TASK_RECURRENCE + " TEXT NOT NULL, " + CROP_TASK_REMINDERS + " TEXT NOT NULL, " + CROP_TASK_REPEAT_UNTIL + " TEXT, " + CROP_TASK_DAYS_BEFORE
                + " REAL DEFAULT 0, " + CROP_TASK_FREQUENCY + " REAL DEFAULT 1, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_sales_order_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_SALES_ORDER_TABLE_NAME + " ( " + CROP_SALES_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_SALES_ORDER_USER_ID + " TEXT NOT NULL," + CROP_SALES_ORDER_CUSTOMER_ID + " TEXT NOT NULL," + CROP_SALES_ORDER_NO + " TEXT NOT NULL," + CROP_SALES_ORDER_REFERENCE_NO + " TEXT NOT NULL," + CROP_SALES_ORDER_DATE + " TEXT NOT NULL," +
                CROP_SALES_ORDER_SHIPPING_DATE + " TEXT," + CROP_SALES_ORDER_SHIPPING_METHOD + " TEXT," + CROP_SALES_ORDER_DISCOUNT + " REAL DEFAULT 0," + CROP_SALES_ORDER_SHIPPING_CHARGES + " REAL DEFAULT 0  ," +
                CROP_SALES_ORDER_CUSTOMER_NOTES + " TEXT ," + CROP_SALES_ORDER_STATUS + " TEXT DEFAULT 'DRAFT' ," + CROP_SALES_ORDER_TERMS_AND_CONDITIONS + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_purchase_order_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_PURCHASE_ORDER_TABLE_NAME + " ( " + CROP_PURCHASE_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_PURCHASE_ORDER_USER_ID + " TEXT NOT NULL," + CROP_PURCHASE_ORDER_SUPPLIER_ID + " TEXT NOT NULL," + CROP_PURCHASE_ORDER_NUMBER + " TEXT NOT NULL," + CROP_PURCHASE_ORDER_REFERENCE_NUMBER + " TEXT NOT NULL," + CROP_PURCHASE_ORDER_PURCHASE_DATE + " TEXT NOT NULL," +
                CROP_PURCHASE_ORDER_DELIVERY_DATE + " TEXT NOT NULL," + CROP_PURCHASE_ORDER_DELIVERY_METHOD + " TEXT," + CROP_PURCHASE_ORDER_DISCOUNT + " REAL DEFAULT 0," +
                CROP_PURCHASE_ORDER_NOTES + " TEXT ," + CROP_PURCHASE_ORDER_STATUS + " TEXT DEFAULT 'DRAFT' ," + CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_bill_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_BILL_TABLE_NAME + " ( " + CROP_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_BILL_USER_ID + " TEXT NOT NULL, " + CROP_BILL_SUPPLIER_ID + " TEXT NOT NULL, " +
                CROP_BILL_ORDER_NUMBER + " TEXT NOT NULL, " + CROP_BILL_NUMBER + " TEXT NOT NULL, " + CROP_BILL_DATE + " TEXT NOT NULL, " + CROP_BILL_DUE_DATE + " TEXT, " + CROP_BILL_TERMS + " TEXT NOT NULL, " + CROP_BILL_DISCOUNT + " REAL DEFAULT 0, " + CROP_BILL_NOTES + " TEXT, " + CROP_BILL_STATUS + " TEXT DEFAULT 'DRAFT', " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_payment_bill_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_PAYMENT_BILL_TABLE_NAME + " ( " + CROP_PAYMENT_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + CROP_PAYMENT_BILL_USER_ID + " TEXT NOT NULL," + CROP_PAYMENT_BILL_DATE + " TEXT NOT NULL," +
                CROP_PAYMENT_BILL_PAYMENT_MADE + " " +
                "REAL NOT NULL," + CROP_PAYMENT_BILL_PAYMENT_MODE + " TEXT NOT NULL," + CROP_PAYMENT_BILL_PAID_THROUGH + " TEXT," + CROP_PAYMENT_BILL_REFERENCE_NUMBER + " TEXT," + CROP_PAYMENT_BILL_NOTES + " TEXT, " + CROP_PAYMENT_BILL_SUPPLIER_ID + " TEXT NOT NULL, " + CROP_PAYMENT_BILL_BILL_ID + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_item_table_query = " CREATE TABLE IF NOT EXISTS " + CROP_ITEM_TABLE_NAME + " ( " + CROP_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_ITEM_N_COMPOSITION + " REAL DEFAULT 0, " +
                CROP_ITEM_K_COMPOSITION + " REAL DEFAULT 0, " + CROP_ITEM_NAME + " TEXT NOT NULL , " + CROP_ITEM_IMAGE_RESOURCE_ID + " TEXT  , " + CROP_ITEM_P_COMPOSITION + " REAL DEFAULT 0 , " +
                CROP_ITEM_P_REMOVED + " REAL DEFAULT 0 , " + CROP_ITEM_N_REMOVED + " REAL DEFAULT 0 , " + CROP_ITEM_K_REMOVED + " REAL DEFAULT 0 , " +
                CROP_ITEM_IS_FOR + " TEXT DEFAULT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_fertilizer_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_FERTILIZER_TABLE_NAME + " ( " + CROP_FERTILIZER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_FERTILIZER_TYPE + " TEXT NOT NULL," + CROP_FERTILIZER_NAME + " TEXT NOT NULL," + CROP_FERTILIZER_N_PERCENTAGE + " REAL," +
                CROP_FERTILIZER_P_PERCENTAGE + " REAL," + CROP_FERTILIZER_K_PERCENTAGE + " REAL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " )";

        String crop_machine_task_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_MACHINE_TASK_TABLE_NAME + " ( " + CROP_MACHINE_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_MACHINE_TASK_MACHINE_ID + " TEXT NOT NULL, " + CROP_MACHINE_TASK_START_DATE + " TEXT NOT NULL, " +
                CROP_MACHINE_TASK_END_DATE + " TEXT NOT NULL, " + CROP_MACHINE_TASK_TITLE + " TEXT NOT NULL, " + CROP_MACHINE_TASK_PERSONNEL + " TEXT NOT NULL, "
                + CROP_MACHINE_TASK_STATUS + " TEXT NOT NULL, " + CROP_MACHINE_TASK_DESCRIPTION + " TEXT , " + CROP_MACHINE_TASK_REPEAT_UNTIL + " TEXT, " + CROP_MACHINE_TASK_DAYS_BEFORE + " REAL DEFAULT 0, " + CROP_MACHINE_TASK_COST + " REAL DEFAULT 0, " +
                CROP_MACHINE_TASK_RECURRENCE + " TEXT NOT NULL, " + CROP_MACHINE_TASK_FREQUENCY + " REAL DEFAULT 1, " + CROP_MACHINE_TASK_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_note_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_NOTE_TABLE_NAME + " ( " + CROP_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_NOTE_PARENT_ID + " TEXT NOT NULL, " + CROP_NOTE_DATE + " TEXT NOT NULL, " + CROP_NOTE_CATEGORY + " TEXT, " + CROP_NOTE_NOTES + " TEXT NOT NULL, " + CROP_NOTE_IS_FOR + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_machine_service_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_MACHINE_SERVICE_TABLE_NAME + " ( " + CROP_MACHINE_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_MACHINE_SERVICE_MACHINE_ID + " TEXT NOT NULL, " + CROP_MACHINE_SERVICE_DATE + " TEXT NOT NULL, " +
                CROP_MACHINE_SERVICE_CURRENT_HOURS + " REAL DEFAULT 0, " + CROP_MACHINE_SERVICE_PERSONNEL + " TEXT NOT NULL, " + CROP_MACHINE_SERVICE_TYPE + " TEXT NOT NULL, "
                + CROP_MACHINE_SERVICE_DESCRIPTION + " TEXT , " + CROP_MACHINE_SERVICE_REPEAT_UNTIL + " TEXT, " + CROP_MACHINE_SERVICE_DAYS_BEFORE + " REAL DEFAULT 0, " + CROP_MACHINE_SERVICE_COST + " REAL DEFAULT 0, " +
                CROP_MACHINE_SERVICE_RECURRENCE + " TEXT NOT NULL, " + CROP_MACHINE_SERVICE_FREQUENCY + " REAL DEFAULT 1, " + CROP_MACHINE_SERVICE_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_irrigation_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_IRRIGATION_TABLE_NAME + " ( " + CROP_IRRIGATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                CROP_IRRIGATION_USER_ID + " TEXT, " + CROP_IRRIGATION_CROP_ID + " TEXT NOT NULL, " + CROP_IRRIGATION_DATE + " TEXT NOT NULL, " + CROP_IRRIGATION_SYSTEM_RATE + " REAL DEFAULT 0, " + CROP_IRRIGATION_START_TIME + " TEXT, " +
                CROP_IRRIGATION_END_TIME + " TEXT, " + CROP_IRRIGATION_AREA_IRRIGATED + " REAL DEFAULT 0, " + CROP_IRRIGATION_UNITS + " TEXT, " + CROP_IRRIGATION_RECURRENCE + " TEXT NOT NULL, " +
                CROP_IRRIGATION_REMINDERS + " TEXT NOT NULL, " + CROP_IRRIGATION_REPEAT_UNTIL + " TEXT, " + CROP_IRRIGATION_DAYS_BEFORE + " REAL DEFAULT 0, " + CROP_IRRIGATION_FREQUENCY + " REAL DEFAULT 1, " + CROP_IRRIGATION_COST + " REAL DEFAULT 0, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_notification_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_NOTIFICATION_TABLE_NAME + " ( " + CROP_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_NOTIFICATION_USER_ID + " TEXT NOT NULL, " +
                CROP_NOTIFICATION_DATE + " TEXT NOT NULL, " + CROP_NOTIFICATION_MESSAGE + " TEXT NOT NULL, " + CROP_NOTIFICATION_STATUS + " TEXT NOT NULL DEFAULT 'Pending', " + CROP_NOTIFICATION_ACTION_DATE + " TEXT NOT NULL, " +
                CROP_NOTIFICATION_REPORT_FROM + " INTEGER NOT NULL DEFAULT 0, " + CROP_NOTIFICATION_SOURCE_ID + " INTEGER NOT NULL, " +
                CROP_NOTIFICATION_TYPE + " TEXT NOT NULL DEFAULT 'Cultivate', " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_settings_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_SETTINGS_TABLE_NAME + " ( " + CROP_SETTINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_SETTINGS_USER_ID + " TEXT NOT NULL, " + CROP_SETTINGS_AREA_UNITS + " TEXT NOT NULL DEFAULT 'Acres', " + CROP_SETTINGS_DATE_FORMAT + " TEXT NOT NULL DEFAULT 'dd-mm-yyyy', " +
                CROP_SETTINGS_CURRENCY + " TEXT NOT NULL DEFAULT 'UGX', " + CROP_SETTINGS_WEIGHT_UNITS + " TEXT NOT NULL DEFAULT 'Kg', " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String crop_deleted_records_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_DELETED_RECORDS_TABLE_NAME + " ( " + CROP_DELETED_ID + " TEXT PRIMARY KEY , " + CROP_DELETED_TYPE + " TEXT NOT NULL, " + CROP_DELETED_DATE + " TEXT NOT NULL, " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String add_produce_insert_query = " CREATE TABLE IF NOT EXISTS " + ADD_PRODUCE_TABLE_NAME + " ( " + ADD_PRODUCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                ADD_PRODUCE_NAME + " TEXT, " + ADD_PRODUCE_VARIETY + " TEXT NOT NULL, " + ADD_PRODUCE_QUANTITY + " TEXT NOT NULL, " + ADD_PRODUCE_PRICE + " TEXT, " +
                ADD_PRODUCE_DATE + " TEXT NOT NULL, " + ADD_PRODUCE_IMAGE + " TEXT NOT NULL " + " ) ";

        String market_price_insert_query = " CREATE TABLE IF NOT EXISTS " + MARKET_PRICE_TABLE_NAME + " ( " + MARKET_PRICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                MARKET_PRICE_CROP + " TEXT NOT NULL, " + MARKET_PRICE_TABLE_MARKET + " TEXT NOT NULL, " + MARKET_PRICE_RETAIL + " TEXT NOT NULL, " + MARKET_PRICE_WHOLESALE + " TEXT NOT NULL " + " ) ";


        String livestock_records_breeding_stock_insert_query = " CREATE TABLE IF NOT EXISTS " + LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME + " ( " + LIVESTOCK_RECORDS_BREEDING_STOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_BREEDING_STOCK_NAME + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_BREEDING_STOCK_EAR_TAG + " TEXT, " + LIVESTOCK_RECORDS_BREEDING_STOCK_COLOR
                + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_BREEDING_STOCK_SEX + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_BREEDING_STOCK_BREED + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_BREEDING_STOCK_DATE_OF_BIRTH + " TEXT, " + LIVESTOCK_RECORDS_BREEDING_STOCK_SOURCE + " TEXT, " +
                LIVESTOCK_RECORDS_BREEDING_STOCK_WEIGHT + " REAL DEFAULT 0, " + LIVESTOCK_RECORDS_BREEDING_STOCK_FATHER + " TEXT, " + LIVESTOCK_RECORDS_BREEDING_STOCK_MOTHER_DAM + " TEXT, " + LIVESTOCK_RECORDS_BREEDING_STOCK_PHOTO + " TEXT, " + LIVESTOCK_RECORDS_BREEDING_STOCK_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + LIVESTOCK_RECORDS_BREEDING_STOCK_SYNC_STATUS + " TEXT DEFAULT 'no', " + LIVESTOCK_RECORDS_ANIMAL_TYPE + " TEXT NOT NULL " + " )";
        ;

        String livestock_records_mating_insert_query = " CREATE TABLE IF NOT EXISTS " + LIVESTOCK_RECORDS_MATING_TABLE_NAME + " ( " + LIVESTOCK_RECORDS_MATING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + LIVESTOCK_RECORDS_MATING_USER_ID + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_MATING_DATE + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_MATING_MALE_NAME + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_MATING_FEMALE_NAME + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_MATING_METHOD
                + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_MATING_GESTATION_PERIOD + " REAL, " + LIVESTOCK_RECORDS_MATING_DELIVERY_ALERT_DAYS_BEFORE + " REAL, " + LIVESTOCK_RECORDS_MATING_NOTES + " TEXT, "
                + LIVESTOCK_RECORDS_MATING_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + LIVESTOCK_RECORDS_MATING_SYNC_STATUS + " TEXT DEFAULT 'no', " + LIVESTOCK_RECORDS_ANIMAL_TYPE + " TEXT NOT NULL " + " )";

        String livestock_records_litter_insert_query = " CREATE TABLE IF NOT EXISTS " + LIVESTOCK_RECORDS_LITTERS_TABLE_NAME + " ( " + LIVESTOCK_RECORDS_LITTERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + LIVESTOCK_RECORDS_LITTERS_USER_ID + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_LITTERS_DATE_OF_BIRTH + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_LITTERS_LITTER_SIZE + " REAL, " + LIVESTOCK_RECORDS_LITTERS_BREEDING_ID + " TEXT, " + LIVESTOCK_RECORDS_LITTERS_BORN_ALIVE + " REAL DEFAULT 0, " +
                LIVESTOCK_RECORDS_LITTERS_BORN_DEAD + " REAL DEFAULT 0, " + LIVESTOCK_RECORDS_LITTERS_NO_OF_FEMALE + " REAL DEFAULT 0, " + LIVESTOCK_RECORDS_LITTERS_NO_OF_MALE + " REAL DEFAULT 0, " + LIVESTOCK_RECORDS_LITTERS_FATHER_SIRE + " TEXT, " + LIVESTOCK_RECORDS_LITTERS_MOTHER_DAM + " TEXT, " + LIVESTOCK_RECORDS_LITTERS_WEANING + " REAL, " + LIVESTOCK_RECORDS_LITTERS_WEANING_ALERT + " REAL, " + LIVESTOCK_RECORDS_LITTERS_SIRE_ID + " TEXT," + LIVESTOCK_RECORDS_LITTERS_DAM_ID + " TEXT, " + LIVESTOCK_RECORDS_LITTERS_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + LIVESTOCK_RECORDS_LITTERS_SYNC_STATUS + " TEXT DEFAULT 'no', " + LIVESTOCK_RECORDS_ANIMAL_TYPE + " TEXT NOT NULL " + " )";

        String livestock_records_medications_insert_query = " CREATE TABLE IF NOT EXISTS " + LIVESTOCK_RECORDS_MEDICATIONS_TABLE_NAME + " ( " + LIVESTOCK_RECORDS_MEDICATIONS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + LIVESTOCK_RECORDS_MEDICATIONS_USER_ID + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_DATE + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_TYPE + " TEXT, " + LIVESTOCK_RECORDS_MEDICATIONS_BREEDING_ID + " TEXT, " + LIVESTOCK_RECORDS_MEDICATIONS_HEALTH_CONDITION + " TEXT, " +
                LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_NAME + " TEXT, " + LIVESTOCK_RECORDS_MEDICATIONS_MANUFACTURER + " TEXT, " + LIVESTOCK_RECORDS_MEDICATIONS_DOSAGE + " REAL DEFAULT 0, " + LIVESTOCK_RECORDS_MEDICATIONS_TREATMENT_PERIOD + " REAL, " + LIVESTOCK_RECORDS_MEDICATIONS_NOTES + " TEXT, " + LIVESTOCK_RECORDS_MEDICATIONS_ANIMAL + " TEXT NOT NULL, " + LIVESTOCK_RECORDS_MEDICATIONS_TECHNICAL_PERSONAL + " TEXT, " + LIVESTOCK_RECORDS_MEDICATIONS_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + LIVESTOCK_RECORDS_MEDICATIONS_SYNC_STATUS + " TEXT DEFAULT 'no', " + LIVESTOCK_RECORDS_ANIMAL_TYPE + " TEXT NOT NULL " + " )";

        String crop_records_crop_gallery_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_RECORDS_CROP_GALLERY_TABLE_NAME + " ( " + CROP_RECORDS_CROP_GALLERY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_RECORDS_CROP_GALLERY_PARENT_ID + " TEXT NOT NULL, " + CROP_RECORDS_CROP_GALLERY_USER_ID + " TEXT NOT NULL, " + CROP_RECORDS_CROP_GALLERY_PHOTO + " TEXT NOT NULL, " + CROP_RECORDS_CROP_GALLERY_CAPTION + " TEXT, " + CROP_RECORDS_CROP_GALLERY_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_RECORDS_CROP_GALLERY_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

        String regions_details_insert_query = " CREATE TABLE IF NOT EXISTS " + REGIONS_DETAILS_TABLE_NAME + " ( " + REGIONS_DETAILS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + REGIONS_DETAILS_ID + " INTEGER , " + REGIONS_DETAILS_REGION_TYPE + " TEXT, " + REGIONS_DETAILS_REGION + " TEXT, " + REGIONS_DETAILS_BELONGS_TO + " TEXT " + " ) ";

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
        database.execSQL(crop_bill_insert_query);
        database.execSQL(crop_item_table_query);
        database.execSQL(crop_fertilizer_insert_query);
        database.execSQL(crop_machine_task_insert_query);
        database.execSQL(crop_note_insert_query);
        database.execSQL(crop_machine_service_insert_query);
        database.execSQL(crop_irrigation_insert_query);
        database.execSQL(crop_transplanting_insert_query);
        database.execSQL(crop_scouting_insert_query);
        database.execSQL(crop_harvest_insert_query);
        database.execSQL(crop_contact_insert_query);
        database.execSQL(crop_settings_insert_query);
        database.execSQL(crop_notification_insert_query);
        database.execSQL(crop_deleted_records_insert_query);
        database.execSQL(add_produce_insert_query);
        database.execSQL(market_price_insert_query);
        database.execSQL(livestock_records_breeding_stock_insert_query);
        database.execSQL(livestock_records_mating_insert_query);
        database.execSQL(livestock_records_litter_insert_query);
        database.execSQL(livestock_records_medications_insert_query);
        database.execSQL(crop_records_crop_gallery_insert_query);
        database.execSQL(regions_details_insert_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion == 1 && newVersion == 2) {
            upGradingTablesFromVersion1ToVersion2(db);
        } else if (oldVersion == 1 && newVersion == 3) {
            upGradingTablesFromVersion1ToVersion2(db);
            upGradingTablesFromVersion2ToVersion3(db);
        } else if (oldVersion == 2 && newVersion == 3) {
            upGradingTablesFromVersion2ToVersion3(db);
        } else if (oldVersion == 3 && newVersion == 4) {
            upGradingTablesFromVersion3ToVersion4(db);
        } else if (oldVersion == 5 && newVersion == 6) {
            upGradingTablesFromVersion5ToVersion6(db);
        }else if(oldVersion == 6 && newVersion == 7){
            upGradingTablesFromVersion6ToVersion7(db);
        }
        onCreate(db);

    }

    public void upGradingTablesFromVersion2ToVersion3(SQLiteDatabase db) {
        database = db;
        db.execSQL("ALTER TABLE " + CROP_INCOME_EXPENSE_TABLE_NAME + " ADD COLUMN " + CROP_INCOME_EXPENSE_DEPARTMENT + " TEXT");
        db.execSQL(" ALTER TABLE " + LIVESTOCK_RECORDS_MEDICATIONS_TABLE_NAME + " ADD COLUMN " + LIVESTOCK_RECORDS_MEDICATIONS_ANIMAL + " TEXT NOT NULL");
    }

    public void upGradingTablesFromVersion3ToVersion4(SQLiteDatabase db) {
        database = db;
        db.execSQL("ALTER TABLE " + CROP_SPRAYING_TABLE_NAME + " ADD COLUMN " + CROP_SPRAYING_UNITS + " TEXT");
        db.execSQL("ALTER TABLE " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " ADD COLUMN " + CROP_FERTILIZER_APPLICATION_FERTILIZER_NAME + " TEXT ");
    }

    public void upGradingTablesFromVersion5ToVersion6(SQLiteDatabase db) {
        database = db;
        db.execSQL("ALTER TABLE " + LIVESTOCK_RECORDS_LITTERS_TABLE_NAME + " ADD COLUMN " + LIVESTOCK_RECORDS_LITTERS_SIRE_ID + " TEXT ");
        db.execSQL("ALTER TABLE " + LIVESTOCK_RECORDS_LITTERS_TABLE_NAME + " ADD COLUMN " + LIVESTOCK_RECORDS_LITTERS_DAM_ID + " TEXT ");
        db.execSQL("ALTER TABLE " + CROP_SPRAYING_TABLE_NAME + " ADD COLUMN " + CROP_SPRAYING_SPRAY_NAME + " TEXT");

    }

    public void upGradingTablesFromVersion6ToVersion7(SQLiteDatabase db) {
        String regions_details_insert_query = " CREATE TABLE IF NOT EXISTS " + REGIONS_DETAILS_TABLE_NAME + " ( " + REGIONS_DETAILS_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + REGIONS_DETAILS_ID + " INTEGER , " + REGIONS_DETAILS_REGION_TYPE + " TEXT, " + REGIONS_DETAILS_REGION + " TEXT, " + REGIONS_DETAILS_BELONGS_TO + " TEXT " + " ) ";
        db.execSQL(regions_details_insert_query);

    }

    public void upGradingTablesFromVersion1ToVersion2(SQLiteDatabase db) {

        database = db;

        db.execSQL("ALTER TABLE " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " ADD COLUMN " + CROP_FERTILIZER_APPLICATION_FERTILIZER_UNITS + " TEXT ");
        db.execSQL("DROP TABLE IF EXISTS " + CROP_NOTIFICATION_TABLE_NAME);
        db.execSQL("ALTER TABLE " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_INVENTORY_SEEDS_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_INVENTORY_SEEDS_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");
        db.execSQL("ALTER TABLE " + CROP_INVENTORY_SEEDS_TABLE_NAME + " ADD COLUMN " + CROP_INVENTORY_SEEDS_MANUFACTURER + " TEXT DEFAULT NULL");

        db.execSQL("ALTER TABLE " + CROP_INVENTORY_SPRAY_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_INVENTORY_SPRAY_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_CROP_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_CROP_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_CULTIVATION_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_CULTIVATION_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_SPRAYING_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_SPRAYING_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_FIELDS_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_FIELDS_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_MACHINE_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_MACHINE_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_SOIL_ANALYSIS_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_SOIL_ANALYSIS_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_EMPLOYEE_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_EMPLOYEE_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_CUSTOMER_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_CUSTOMER_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_SUPPLIER_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_SUPPLIER_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_PRODUCT_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_PRODUCT_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_ESTIMATE_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_ESTIMATE_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_PRODUCT_ITEM_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_PRODUCT_ITEM_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_INVOICE_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_INVOICE_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_INCOME_EXPENSE_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_INCOME_EXPENSE_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");
        db.execSQL("ALTER TABLE " + CROP_INCOME_EXPENSE_TABLE_NAME + " ADD COLUMN " + CROP_INCOME_EXPENSE_DEPARTMENT + " TEXT DEFAULT NULL");

        db.execSQL("ALTER TABLE " + CROP_PAYMENT_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_PAYMENT_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_TASK_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_TASK_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_SALES_ORDER_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_SALES_ORDER_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_PURCHASE_ORDER_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_PURCHASE_ORDER_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_PAYMENT_BILL_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_PAYMENT_BILL_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_BILL_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_BILL_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_ITEM_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_ITEM_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_FERTILIZER_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_FERTILIZER_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_SETTINGS_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_SETTINGS_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        //db.execSQL("ALTER TABLE " + CROP_NOTIFICATION_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID +" TEXT DEFAULT NULL ");
        //db.execSQL("ALTER TABLE " + CROP_NOTIFICATION_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_MACHINE_TASK_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_MACHINE_TASK_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_NOTE_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_NOTE_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_MACHINE_SERVICE_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_MACHINE_SERVICE_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_IRRIGATION_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_IRRIGATION_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_TRANSPLANTING_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_TRANSPLANTING_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_SCOUTING_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_SCOUTING_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_HARVEST_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_HARVEST_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        db.execSQL("ALTER TABLE " + CROP_CONTACT_TABLE_NAME + " ADD COLUMN " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL ");
        db.execSQL("ALTER TABLE " + CROP_CONTACT_TABLE_NAME + " ADD COLUMN " + CROP_SYNC_STATUS + " TEXT DEFAULT 'no'");

        //delete all settings items except the first one
        db.delete(CROP_SETTINGS_TABLE_NAME, CROP_SETTINGS_ID + " > 1", null);
    }

    public MyFarmDbHandlerSingleton openDB() throws SQLException {

        database = this.getWritableDatabase();
//        onCreate(database);

        return this;
    }

    public void closeDB() throws SQLException {
        this.close();
    }

    public void recordDeletedRecord(String type, String id) {
        if (id == null || type == null) {
            Log.d("ID:", "NULL FOR" + type);
            return;
        }
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_DELETED_DATE, new SimpleDateFormat("yyyy-mm-dd").format(new Date()));
        contentValues.put(CROP_DELETED_TYPE, type);
        contentValues.put(CROP_DELETED_ID, id);
        database.insert(CROP_DELETED_RECORDS_TABLE_NAME, null, contentValues);
        Log.d("INSERTED", contentValues.toString());
        closeDB();
    }

    public void updateDeletedRecord(DeletedRecord record) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_DELETED_DATE, record.getDate());
        contentValues.put(CROP_DELETED_TYPE, record.getType());
        contentValues.put(CROP_SYNC_STATUS, record.getSyncStatus());
        database.update(CROP_DELETED_RECORDS_TABLE_NAME, contentValues, CROP_TRANSPLANTING_ID + " = ?", new String[]{record.getId()});
        closeDB();
    }

    public void insertCropNotification(CropNotification notification) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_NOTIFICATION_TABLE_NAME + " where " + CROP_NOTIFICATION_USER_ID + " = " + notification.getUserId() + " AND " + CROP_NOTIFICATION_ACTION_DATE + " = '" + notification.getActionDate() + "' AND " + CROP_NOTIFICATION_TYPE + " = '" + notification.getType() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_NOTIFICATION_USER_ID, notification.getUserId());
        contentValues.put(CROP_NOTIFICATION_DATE, notification.getDate());
        contentValues.put(CROP_NOTIFICATION_MESSAGE, notification.getMessage());
        contentValues.put(CROP_NOTIFICATION_STATUS, notification.getStatus());
        contentValues.put(CROP_NOTIFICATION_ACTION_DATE, notification.getActionDate());
        contentValues.put(CROP_NOTIFICATION_TYPE, notification.getType());
        contentValues.put(CROP_NOTIFICATION_SOURCE_ID, notification.getSourceId());
        contentValues.put(CROP_NOTIFICATION_REPORT_FROM, notification.getReportFrom());

        database.insert(CROP_NOTIFICATION_TABLE_NAME, null, contentValues);
        res.close();
        closeDB();
    }

    public void updateCropNotification(CropNotification notification) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_NOTIFICATION_USER_ID, notification.getUserId());
        contentValues.put(CROP_NOTIFICATION_DATE, notification.getDate());
        contentValues.put(CROP_NOTIFICATION_MESSAGE, notification.getMessage());
        contentValues.put(CROP_NOTIFICATION_STATUS, notification.getStatus());
        contentValues.put(CROP_NOTIFICATION_ACTION_DATE, notification.getActionDate());
        contentValues.put(CROP_NOTIFICATION_TYPE, notification.getType());
        contentValues.put(CROP_NOTIFICATION_SOURCE_ID, notification.getSourceId());
        contentValues.put(CROP_NOTIFICATION_REPORT_FROM, notification.getReportFrom());
        database.update(CROP_NOTIFICATION_TABLE_NAME, contentValues, CROP_NOTIFICATION_ID + " = ?", new String[]{notification.getId()});

        closeDB();
    }

    public boolean deleteCropNotification(String sourceId, String type) {
        openDB();
        database.delete(CROP_NOTIFICATION_TABLE_NAME, CROP_NOTIFICATION_SOURCE_ID + " = ? AND " + CROP_NOTIFICATION_TYPE + " = ? AND " + CROP_NOTIFICATION_STATUS + " = ?", new String[]{sourceId, type, context.getString(R.string.notification_status_pending)});
        closeDB();
        return true;
    }

    public ArrayList<CropNotification> getCropNotifications(String userId, String queryKey) {
        openDB();
        ArrayList<CropNotification> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res;
        if (queryKey.equals(CropNotification.QUERY_KEY_REPORT_FROM_TODAY)) {
            res = db.rawQuery("select * from " + CROP_NOTIFICATION_TABLE_NAME + " where " + CROP_NOTIFICATION_USER_ID + " = '" + userId + "' AND date(" + CROP_NOTIFICATION_REPORT_FROM + ") = date('now') AND " + CROP_NOTIFICATION_STATUS + " = '" + context.getString(R.string.notification_status_pending) + "' ORDER BY date(" + CROP_NOTIFICATION_ACTION_DATE + ") ASC", null);
        } else {
            res = db.rawQuery("select * from " + CROP_NOTIFICATION_TABLE_NAME + " where " + CROP_NOTIFICATION_USER_ID + " = '" + userId + "' AND date(" + CROP_NOTIFICATION_ACTION_DATE + ") " + queryKey + " date('now') AND " + CROP_NOTIFICATION_STATUS + " = '" + context.getString(R.string.notification_status_pending) + "' ORDER BY date(" + CROP_NOTIFICATION_ACTION_DATE + ") ASC", null);
        }
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropNotification notification = new CropNotification();
            notification.setId(res.getString(res.getColumnIndex(CROP_NOTIFICATION_ID)));
            notification.setUserId(res.getString(res.getColumnIndex(CROP_NOTIFICATION_USER_ID)));
            notification.setDate(res.getString(res.getColumnIndex(CROP_NOTIFICATION_DATE)));
            notification.setMessage(res.getString(res.getColumnIndex(CROP_NOTIFICATION_MESSAGE)));
            notification.setStatus(res.getString(res.getColumnIndex(CROP_NOTIFICATION_STATUS)));
            notification.setActionDate(res.getString(res.getColumnIndex(CROP_NOTIFICATION_ACTION_DATE)));
            notification.setType(res.getString(res.getColumnIndex(CROP_NOTIFICATION_TYPE)));
            notification.setSourceId(res.getString(res.getColumnIndex(CROP_NOTIFICATION_SOURCE_ID)));
            notification.setReportFrom(res.getString(res.getColumnIndex(CROP_NOTIFICATION_REPORT_FROM)));
            array_list.add(notification);
            res.moveToNext();
        }
        closeDB();
        return array_list;
    }

    private ArrayList<CropNotification> createNotification(String reminderType, String starDate, int frequency, int daysBefore, String message, String type, String endDate, String sourceId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);
        Calendar varyingCalendar = Calendar.getInstance();
        varyingCalendar.set(Calendar.HOUR, 0);
        varyingCalendar.set(Calendar.MINUTE, 0);
        varyingCalendar.set(Calendar.SECOND, 0);
        varyingCalendar.set(Calendar.MILLISECOND, 0);
        Calendar endDateCalendar = Calendar.getInstance();
        varyingCalendar.set(Calendar.HOUR, 0);
        varyingCalendar.set(Calendar.MINUTE, 0);
        varyingCalendar.set(Calendar.SECOND, 0);
        varyingCalendar.set(Calendar.MILLISECOND, 0);
        int calendarIdentifier = 0;
        int repeatFrequency = 1;

        Date startDate = null;
        ArrayList<CropNotification> notificationsList = new ArrayList<>();

        try {
            startDate = dateFormat.parse(starDate);
            varyingCalendar.setTime(startDate);

            if (reminderType.toLowerCase().equals("weekly")) {
                calendarIdentifier = Calendar.DAY_OF_MONTH;
                repeatFrequency = 7 * frequency;
                if (endDate != null)
                    endDateCalendar.setTime(dateFormat.parse(endDate));
            } else if (reminderType.toLowerCase().equals("monthly")) {
                calendarIdentifier = Calendar.MONTH;
                repeatFrequency = 1;
                endDateCalendar.add(Calendar.MONTH, 5);
            } else if (reminderType.toLowerCase().equals("daily")) {
                return notificationsList;
            } else if (reminderType.toLowerCase().equals("annually")) {
                calendarIdentifier = Calendar.YEAR;
                repeatFrequency = 1;
                endDateCalendar.add(Calendar.YEAR, 1);
            }

            //return an array of tasks
            //get all task dates from the start date to the end date (repeat until date)
            //for each date calculate the report from date using the
            varyingCalendar.add(calendarIdentifier, repeatFrequency);
            if (repeatFrequency < 1) {
                return notificationsList;
            }
            while (!varyingCalendar.after(endDateCalendar)) {
                System.out.println("Running : " + reminderType + " " + dateFormat.format(varyingCalendar.getTime()) + " -> " + dateFormat.format(endDateCalendar.getTime()));
                CropNotification notification = new CropNotification();
                notification.setActionDate(dateFormat.format(varyingCalendar.getTime()));
                notification.setDate(dateFormat.format(todayCalendar.getTime()));
                varyingCalendar.add(Calendar.DAY_OF_MONTH, -1 * daysBefore);//get first reporting date
                notification.setReportFrom(dateFormat.format(varyingCalendar.getTime()));
                varyingCalendar.add(Calendar.DAY_OF_MONTH, daysBefore);//get first reporting date
                notification.setSourceId(sourceId);
                notification.setMessage(message);
                notification.setType(type);
                notification.setStatus(context.getString(R.string.notification_status_pending));
                ;
                notification.setUserId(DashboardActivity.RETRIEVED_USER_ID);

                notificationsList.add(notification);
                varyingCalendar.add(calendarIdentifier, repeatFrequency);//increase the varying calendar

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return notificationsList;

    }

    public void generateNotifications(String type, String sourceId) {
        //load tasks with reminders yes
        //get reminder type (either monthly, daily, weekly
        //add 1 quantity
        //check if the new date is greater or equal to the current date then consider this as the action date
        //if the action date is greater than repeat until then break
        //if the today + days before is equal to the action date
        //generate the message
        if (sourceId == "" || sourceId == null)
            return;

        openDB();
        ArrayList<CropNotification> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "";
        Cursor res;
        if (type.equals(context.getString(R.string.notification_type_spraying))) {
            //Crop Task (Activity) Spraying
            query = "select " + CROP_SPRAYING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_SPRAYING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_SPRAYING_TABLE_NAME + "." + CROP_SPRAYING_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_SPRAYING_REMINDERS + " = 'Yes' AND " +
                    CROP_SPRAYING_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_SPRAYING_TABLE_NAME + "." + CROP_SPRAYING_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();

            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_SPRAYING_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_SPRAYING_DATE));
                String repeatUntil = res.getString(res.getColumnIndex(CROP_SPRAYING_REPEAT_UNTIL));
                int frequency = res.getInt(res.getColumnIndex(CROP_SPRAYING_FREQUENCY));
                ;
                int daysBefore = res.getInt(res.getColumnIndex(CROP_SPRAYING_DAYS_BEFORE));
                String message = context.getString(R.string.notification_type_spraying) + " (" + res.getString(res.getColumnIndex(CROP_CROP_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));
            }
        } else if (type.equals(context.getString(R.string.notification_type_cultivation))) {
            //Crop Task (Activity) Cultivation
            query = "select " + CROP_CULTIVATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_CULTIVATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CULTIVATION_TABLE_NAME + "." + CROP_CULTIVATION_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_CULTIVATION_REMINDERS + " = 'Yes' AND " +
                    CROP_CULTIVATION_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_CULTIVATION_TABLE_NAME + "." + CROP_CULTIVATION_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();

            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_CULTIVATION_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE));
                int frequency = res.getInt(res.getColumnIndex(CROP_CULTIVATION_FREQUENCY));
                ;
                String repeatUntil = res.getString(res.getColumnIndex(CROP_CULTIVATION_REPEAT_UNTIL));
                int daysBefore = res.getInt(res.getColumnIndex(CROP_CULTIVATION_DAYS_BEFORE));
                String message = context.getString(R.string.notification_type_cultivation) + " (" + res.getString(res.getColumnIndex(CROP_CROP_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));

            }
        } else if (type.equals(context.getString(R.string.notification_type_harvest))) {
            //Crop Task (Activity) Harvest
            query = "select " + CROP_HARVEST_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_HARVEST_TABLE_NAME + "." + CROP_HARVEST_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_HARVEST_REMINDERS + " = 'Yes' AND " +
                    CROP_HARVEST_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_HARVEST_TABLE_NAME + "." + CROP_HARVEST_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_HARVEST_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_HARVEST_DATE));
                String repeatUntil = res.getString(res.getColumnIndex(CROP_HARVEST_REPEAT_UNTIL));
                int frequency = res.getInt(res.getColumnIndex(CROP_HARVEST_FREQUENCY));
                ;
                int daysBefore = res.getInt(res.getColumnIndex(CROP_HARVEST_DAYS_BEFORE));
                String message = context.getString(R.string.notification_type_harvest) + " (" + res.getString(res.getColumnIndex(CROP_CROP_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));
            }
        } else if (type.equals(context.getString(R.string.notification_type_fertilizer_application))) {
            //Crop Task (Activity) Fertilizer Application
            query = "select " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + "." + CROP_FERTILIZER_APPLICATION_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_FERTILIZER_APPLICATION_REMINDERS + " = 'Yes' AND " +
                    CROP_FERTILIZER_APPLICATION_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + "." + CROP_FERTILIZER_APPLICATION_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();

            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE));
                int frequency = res.getInt(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FREQUENCY));
                int daysBefore = res.getInt(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE));
                String repeatUntil = res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL));
                String message = context.getString(R.string.notification_type_fertilizer_application) + " (" + res.getString(res.getColumnIndex(CROP_CROP_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));
            }

        } else if (type.equals(context.getString(R.string.notification_type_irrigation))) {

            //Crop Task (Activity) Irrigation
            query = "select " + CROP_IRRIGATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_IRRIGATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_IRRIGATION_TABLE_NAME + "." + CROP_IRRIGATION_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_IRRIGATION_REMINDERS + " = 'Yes' AND " +
                    CROP_IRRIGATION_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_IRRIGATION_TABLE_NAME + "." + CROP_IRRIGATION_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_IRRIGATION_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE));
                int frequency = res.getInt(res.getColumnIndex(CROP_IRRIGATION_FREQUENCY));
                ;
                int daysBefore = res.getInt(res.getColumnIndex(CROP_IRRIGATION_DAYS_BEFORE));
                String repeatUntil = res.getString(res.getColumnIndex(CROP_IRRIGATION_REPEAT_UNTIL));
                String message = context.getString(R.string.notification_type_irrigation) + " (" + res.getString(res.getColumnIndex(CROP_CROP_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));
            }
        } else if (type.equals(context.getString(R.string.notification_type_transplanting))) {

            //Crop Task (Activity) Transplanting
            query = "select " + CROP_TRANSPLANTING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_TRANSPLANTING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_TRANSPLANTING_TABLE_NAME + "." + CROP_TRANSPLANTING_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_TRANSPLANTING_REMINDERS + " = 'Yes' AND " +
                    CROP_TRANSPLANTING_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_TRANSPLANTING_TABLE_NAME + "." + CROP_TRANSPLANTING_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();

            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_TRANSPLANTING_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE));
                int frequency = res.getInt(res.getColumnIndex(CROP_TRANSPLANTING_FREQUENCY));
                ;
                int daysBefore = res.getInt(res.getColumnIndex(CROP_TRANSPLANTING_DAYS_BEFORE));
                String repeatUntil = res.getString(res.getColumnIndex(CROP_TRANSPLANTING_REPEAT_UNTIL));
                String message = context.getString(R.string.notification_type_transplanting) + " (" + res.getString(res.getColumnIndex(CROP_CROP_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));
            }
        } else if (type.equals(context.getString(R.string.notification_type_scouting))) {
            //Crop Task (Activity) Scouting
            query = "select " + CROP_SCOUTING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_SCOUTING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_SCOUTING_TABLE_NAME + "." + CROP_SCOUTING_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_SCOUTING_REMINDERS + " = 'Yes' AND " +
                    CROP_SCOUTING_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_SCOUTING_TABLE_NAME + "." + CROP_SCOUTING_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_SCOUTING_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_SCOUTING_DATE));
                int frequency = res.getInt(res.getColumnIndex(CROP_SCOUTING_FREQUENCY));
                ;
                int daysBefore = res.getInt(res.getColumnIndex(CROP_SCOUTING_DAYS_BEFORE));
                String repeatUntil = res.getString(res.getColumnIndex(CROP_SCOUTING_REPEAT_UNTIL));
                String message = context.getString(R.string.notification_type_scouting) + " (" + res.getString(res.getColumnIndex(CROP_CROP_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));
            }

        } else if (type.equals(context.getString(R.string.notification_type_service))) {
            //Machine Task (Activity) Service
            query = "select " + CROP_MACHINE_SERVICE_TABLE_NAME + ".*," + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_NAME + " from " + CROP_MACHINE_SERVICE_TABLE_NAME + " LEFT JOIN " + CROP_MACHINE_TABLE_NAME + " ON " + CROP_MACHINE_SERVICE_TABLE_NAME + "." + CROP_MACHINE_SERVICE_MACHINE_ID + " = " + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_ID + " where " + CROP_MACHINE_SERVICE_REMINDERS + " = 'Yes' AND " +
                    CROP_MACHINE_SERVICE_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_MACHINE_SERVICE_TABLE_NAME + "." + CROP_MACHINE_SERVICE_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();

            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DATE));
                int frequency = res.getInt(res.getColumnIndex(CROP_MACHINE_SERVICE_FREQUENCY));
                ;
                int daysBefore = res.getInt(res.getColumnIndex(CROP_MACHINE_SERVICE_DAYS_BEFORE));
                String repeatUntil = res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_REPEAT_UNTIL));
                String message = context.getString(R.string.notification_type_service) + " (" + res.getString(res.getColumnIndex(CROP_MACHINE_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));
            }
        } else if (type.equals(context.getString(R.string.notification_type_machine_task))) {
            //Machine Task (Activity) Task
            query = "select " + CROP_MACHINE_TASK_TABLE_NAME + ".*," + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_NAME + " from " + CROP_MACHINE_TASK_TABLE_NAME + " LEFT JOIN " + CROP_MACHINE_TABLE_NAME + " ON " + CROP_MACHINE_TASK_TABLE_NAME + "." + CROP_MACHINE_TASK_MACHINE_ID + " = " + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_ID + " where " + CROP_MACHINE_TASK_REMINDERS + " = 'Yes' AND " +
                    CROP_MACHINE_TASK_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_MACHINE_TASK_TABLE_NAME + "." + CROP_MACHINE_TASK_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_MACHINE_TASK_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_MACHINE_TASK_START_DATE));
                int frequency = res.getInt(res.getColumnIndex(CROP_MACHINE_TASK_FREQUENCY));
                ;
                int daysBefore = res.getInt(res.getColumnIndex(CROP_MACHINE_TASK_DAYS_BEFORE));
                String repeatUntil = res.getString(res.getColumnIndex(CROP_MACHINE_TASK_REPEAT_UNTIL));
                String message = context.getString(R.string.notification_type_machine_task) + " (" + res.getString(res.getColumnIndex(CROP_MACHINE_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));
            }
        } else if (type.equals(context.getString(R.string.notification_type_soil_analysis))) {
            //Field Task (Activity) Soil Analysis
            query = "select " + CROP_SOIL_ANALYSIS_TABLE_NAME + ".*," + CROP_FIELDS_TABLE_NAME + "." + CROP_FIELD_NAME + " from " + CROP_SOIL_ANALYSIS_TABLE_NAME + " LEFT JOIN " + CROP_FIELDS_TABLE_NAME + " ON " + CROP_SOIL_ANALYSIS_TABLE_NAME + "." + CROP_SOIL_ANALYSIS_FIELD_ID + " = " + CROP_FIELDS_TABLE_NAME + "." + CROP_FIELD_ID + " where " + CROP_SOIL_ANALYSIS_REMINDERS + " = 'Yes' AND " +
                    CROP_SOIL_ANALYSIS_RECURRENCE + " NOT LIKE '" + context.getString(R.string.task_reminder_type) + "' AND " + CROP_SOIL_ANALYSIS_TABLE_NAME + "." + CROP_SOIL_ANALYSIS_ID + " = " + sourceId;
            res = db.rawQuery(query, null);
            res.moveToFirst();

            if (!res.isAfterLast()) {
                String reminderType = res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_RECURRENCE));
                String startDate = res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_DATE));
                int frequency = res.getInt(res.getColumnIndex(CROP_SOIL_ANALYSIS_FREQUENCY));
                ;
                int daysBefore = res.getInt(res.getColumnIndex(CROP_SOIL_ANALYSIS_DAYS_BEFORE));
                String repeatUntil = res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_REPEAT_UNTIL));
                String message = context.getString(R.string.notification_type_soil_analysis) + " (" + res.getString(res.getColumnIndex(CROP_FIELD_NAME)) + ")";
                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));

            }
        }
//       else if (type.equals(context.getString(R.string.notification_delivery_alert))) {
//           //Delivery alert
//                query = "select " + LIVESTOCK_RECORDS_MATING_TABLE_NAME + ".*," +" from " + LIVESTOCK_RECORDS_MATING_TABLE_NAME + " where " + LIVESTOCK_RECORDS_MATING_ID + " = " + sourceId;
//
//            res = db.rawQuery(query, null);
//            res.moveToFirst();
//
//            if (!res.isAfterLast()) {
//
//            }
//                String reminderType = res.getString(res.getColumnIndex(CROP_SPRAYING_RECURRENCE));
//                String startDate = res.getString(res.getColumnIndex(CROP_SPRAYING_DATE));
//                String repeatUntil = res.getString(res.getColumnIndex(CROP_SPRAYING_REPEAT_UNTIL));
//                int frequency = res.getInt(res.getColumnIndex(CROP_SPRAYING_FREQUENCY));
//                ;
//                int daysBefore = res.getInt(res.getColumnIndex(CROP_SPRAYING_DAYS_BEFORE));
//                String message = context.getString(R.string.notification_type_spraying) + " (" + res.getString(res.getColumnIndex(CROP_CROP_NAME)) + ")";
//                array_list.addAll(createNotification(reminderType, startDate, frequency, daysBefore, message, type, repeatUntil, sourceId));
//            }


        closeDB();


        for (CropNotification notification : array_list) {
            insertCropNotification(notification);
        }

    }

    public void initializeSettings(String userId) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SETTINGS_TABLE_NAME + " where " + CROP_SETTINGS_USER_ID + " = " + userId, null);
        res.moveToFirst();

        if (!res.isAfterLast()) {
            CropSettingsSingleton settingsSingleton = CropSettingsSingleton.getInstance();
            settingsSingleton.setCurrency(res.getString(res.getColumnIndex(CROP_SETTINGS_CURRENCY)));
            settingsSingleton.setAreaUnits(res.getString(res.getColumnIndex(CROP_SETTINGS_AREA_UNITS)));
            settingsSingleton.setWeightUnits(res.getString(res.getColumnIndex(CROP_SETTINGS_WEIGHT_UNITS)));
            settingsSingleton.setDateFormat(res.getString(res.getColumnIndex(CROP_SETTINGS_DATE_FORMAT)));
            settingsSingleton.setId(res.getString(res.getColumnIndex(CROP_SETTINGS_ID)));
            settingsSingleton.setUserId(res.getString(res.getColumnIndex(CROP_SETTINGS_USER_ID)));
            settingsSingleton.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
            res.close();
            closeDB();
        } else {
            CropSettingsSingleton settingsSingleton = CropSettingsSingleton.getInstance();
            settingsSingleton.setUserId(userId);

            res.close();
            closeDB();
            insertSettings(settingsSingleton);
            initializeSettings(userId);
        }

    }

    public void insertSettings(CropSettingsSingleton crop) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SETTINGS_AREA_UNITS, crop.getAreaUnits());
        contentValues.put(CROP_SETTINGS_CURRENCY, crop.getCurrency());
        contentValues.put(CROP_SETTINGS_DATE_FORMAT, crop.getDateFormat());
        contentValues.put(CROP_SETTINGS_WEIGHT_UNITS, crop.getWeightUnits());
        contentValues.put(CROP_SETTINGS_USER_ID, crop.getUserId());
        contentValues.put(CROP_SYNC_STATUS, crop.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, crop.getGlobalId());
        database.insert(CROP_SETTINGS_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateSettings(CropSettingsSingleton crop) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SETTINGS_AREA_UNITS, crop.getAreaUnits());
        contentValues.put(CROP_SETTINGS_CURRENCY, crop.getCurrency());
        contentValues.put(CROP_SETTINGS_DATE_FORMAT, crop.getDateFormat());
        contentValues.put(CROP_SETTINGS_WEIGHT_UNITS, crop.getWeightUnits());
        contentValues.put(CROP_SETTINGS_USER_ID, crop.getUserId());
        contentValues.put(CROP_SYNC_STATUS, crop.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, crop.getGlobalId());
        database.update(CROP_SETTINGS_TABLE_NAME, contentValues, CROP_SETTINGS_ID + " = ? ", new String[]{crop.getId()});
        closeDB();
    }

    public ArrayList<CropYieldRecord> getCropsYield(String userId) {

        openDB();
        ArrayList<CropYieldRecord> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_CROP_TABLE_NAME + ".*," + CROP_FIELDS_TABLE_NAME + ".* from " + CROP_CROP_TABLE_NAME + " LEFT JOIN " + CROP_FIELDS_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_FIELD_ID + " = " + CROP_FIELDS_TABLE_NAME + "." + CROP_FIELD_ID + " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropYieldRecord crop = new CropYieldRecord();
            crop.setVariety(res.getString(res.getColumnIndex(CROP_CROP_VARIETY)));
            crop.setCropId(res.getString(res.getColumnIndex(CROP_CROP_ID)));
            crop.setCroppingYear(res.getString(res.getColumnIndex(CROP_CROP_YEAR)));
            crop.setCropName(res.getString(res.getColumnIndex(CROP_CROP_NAME)));
            crop.setSeason(res.getString(res.getColumnIndex(CROP_CROP_SEASON)));
            crop.setFieldName(res.getString(res.getColumnIndex(CROP_FIELD_NAME)));
            array_list.add(crop);
            res.moveToNext();
        }

        for (CropYieldRecord yieldRecord : array_list) {
            yieldRecord.setTotalCost(getCropTotalExpenses(yieldRecord.getCropId()));
            yieldRecord.setRevenue(getCropRevenue(yieldRecord.getCropId()));
        }
        return array_list;

    }

    public float getCropTotalExpenses(String cropId) {
        Cursor res;
        float totalExpenses = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        res = db.rawQuery("select " + CROP_INCOME_EXPENSE_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_INCOME_EXPENSE_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_INCOME_EXPENSE_TABLE_NAME + "." + CROP_INCOME_EXPENSE_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId + " AND " + CROP_INCOME_EXPENSE_TRANSACTION + " = 'Expense'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            totalExpenses += res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT));
            res.moveToNext();
        }

        //Activities
        res = db.rawQuery("select " + CROP_HARVEST_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_HARVEST_TABLE_NAME + "." + CROP_HARVEST_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses += res.getFloat(res.getColumnIndex(CROP_HARVEST_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_TRANSPLANTING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_TRANSPLANTING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_TRANSPLANTING_TABLE_NAME + "." + CROP_TRANSPLANTING_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses += res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_SCOUTING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_SCOUTING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_SCOUTING_TABLE_NAME + "." + CROP_SCOUTING_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses += res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_CULTIVATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_CULTIVATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_CULTIVATION_TABLE_NAME + "." + CROP_CULTIVATION_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses += res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_IRRIGATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_IRRIGATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_IRRIGATION_TABLE_NAME + "." + CROP_IRRIGATION_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses += res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_SPRAYING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_SPRAYING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_SPRAYING_TABLE_NAME + "." + CROP_SPRAYING_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses += res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + "." + CROP_FERTILIZER_APPLICATION_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses += res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST));
            res.moveToNext();
        }

        //Crop Planting
        res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            totalExpenses += res.getFloat(res.getColumnIndex(CROP_CROP_COST));
            res.moveToNext();
        }


        closeDB();
        return totalExpenses;

    }

    public float getCropRevenue(String cropId) {
        float totalRevenue = 0;
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select " + CROP_INCOME_EXPENSE_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_INCOME_EXPENSE_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_INCOME_EXPENSE_TABLE_NAME + "." + CROP_INCOME_EXPENSE_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId + " AND " + CROP_INCOME_EXPENSE_TRANSACTION + " = 'Income'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            totalRevenue += res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT));
            res.moveToNext();
        }
        res = db.rawQuery("select " + CROP_HARVEST_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_HARVEST_TABLE_NAME + "." + CROP_HARVEST_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + cropId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            totalRevenue += (res.getFloat(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD)) * res.getFloat(res.getColumnIndex(CROP_HARVEST_PRICE)));
            res.moveToNext();
        }
        closeDB();
        return totalRevenue;

    }

    public ArrayList<GraphRecord> getGraphExpensesByActivity(String startDate, String endDate) {
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select * from " + CROP_CULTIVATION_TABLE_NAME + " where " + CROP_CULTIVATION_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE)),
                    "Cultivation",
                    res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_TRANSPLANTING_TABLE_NAME + " where " + CROP_TRANSPLANTING_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE)),
                    "Transplanting",
                    res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_HARVEST_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    "Transplanting",
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " where " + CROP_FERTILIZER_APPLICATION_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE)),
                    "Fertilizer Application",
                    res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_SPRAYING_TABLE_NAME + " where " + CROP_SPRAYING_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SPRAYING_DATE)),
                    "Spraying",
                    res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select * from " + CROP_IRRIGATION_TABLE_NAME + " where " + CROP_IRRIGATION_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE)),
                    "Irrigation",
                    res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_SCOUTING_TABLE_NAME + " where " + CROP_SCOUTING_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SCOUTING_DATE)),
                    "Scouting",
                    res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        //Crop Planting
        res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_DATE_SOWN + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)),
                    "Planting",
                    res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        closeDB();
        return expensesList;

    }

    public ArrayList<GraphRecord> getGraphExpensesByCategory(String startDate, String endDate) {
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME + " where " + CROP_INCOME_EXPENSE_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') AND " + CROP_INCOME_EXPENSE_TRANSACTION + " = 'Expense' AND " + CROP_INCOME_EXPENSE_DEPARTMENT + " IN ('All', 'Crops')", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DATE)),
                    res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_CATEGORY)),
                    res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_PAYMENT_BILL_TABLE_NAME + " where " + CROP_PAYMENT_BILL_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_DATE)),
                    "Payment Made",
                    res.getFloat(res.getColumnIndex(CROP_PAYMENT_BILL_PAYMENT_MADE)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        //ACTIVITIESS
        res = db.rawQuery("select * from " + CROP_CULTIVATION_TABLE_NAME + " where " + CROP_CULTIVATION_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_TRANSPLANTING_TABLE_NAME + " where " + CROP_TRANSPLANTING_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_HARVEST_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " where " + CROP_FERTILIZER_APPLICATION_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_SPRAYING_TABLE_NAME + " where " + CROP_SPRAYING_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SPRAYING_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select * from " + CROP_IRRIGATION_TABLE_NAME + " where " + CROP_IRRIGATION_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_SCOUTING_TABLE_NAME + " where " + CROP_SCOUTING_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SCOUTING_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        //Crop Planting
        res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_DATE_SOWN + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        //FIELD ANALYSIS
        res = db.rawQuery("select * from " + CROP_SOIL_ANALYSIS_TABLE_NAME + " where " + CROP_SOIL_ANALYSIS_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_DATE)),
                    "Field Activity",
                    res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        //INVENTORY
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_DATE)),
                    context.getString(R.string.graph_category_crop_inventory),
                    res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + " where " + CROP_INVENTORY_FERTILIZER_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_DATE)),
                    context.getString(R.string.graph_category_crop_inventory),
                    res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_INVENTORY_SEEDS_TABLE_NAME + " where " + CROP_INVENTORY_SEEDS_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DATE)),
                    context.getString(R.string.graph_category_crop_inventory),
                    res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select * from " + CROP_MACHINE_SERVICE_TABLE_NAME + " where " + CROP_MACHINE_SERVICE_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DATE)),
                    context.getString(R.string.graph_category_machine_service),
                    res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_MACHINE_TASK_TABLE_NAME + " where " + CROP_MACHINE_TASK_START_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_START_DATE)),
                    context.getString(R.string.graph_category_machine_task),
                    res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return expensesList;

    }

    public ArrayList<GraphRecord> getGraphExpensesByCrop(int year, String season) {
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select " + CROP_INCOME_EXPENSE_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_INCOME_EXPENSE_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_INCOME_EXPENSE_TABLE_NAME + "." + CROP_INCOME_EXPENSE_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_SEASON + " = '" + season + "' AND " + CROP_INCOME_EXPENSE_TRANSACTION + " = 'Expense'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        //Activities
        res = db.rawQuery("select " + CROP_HARVEST_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_HARVEST_TABLE_NAME + "." + CROP_HARVEST_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_TRANSPLANTING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_TRANSPLANTING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_TRANSPLANTING_TABLE_NAME + "." + CROP_TRANSPLANTING_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_SCOUTING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_SCOUTING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_SCOUTING_TABLE_NAME + "." + CROP_SCOUTING_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SCOUTING_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_CULTIVATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_CULTIVATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_CULTIVATION_TABLE_NAME + "." + CROP_CULTIVATION_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_IRRIGATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_IRRIGATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_IRRIGATION_TABLE_NAME + "." + CROP_IRRIGATION_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_SPRAYING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_SPRAYING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_SPRAYING_TABLE_NAME + "." + CROP_SPRAYING_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SPRAYING_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + "." + CROP_FERTILIZER_APPLICATION_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        //Crop Planting
        res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }


        res.close();
        closeDB();
        return expensesList;

    }

    public ArrayList<GraphRecord> getGraphIncomesByCrop(int year, String season) {
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select " + CROP_INCOME_EXPENSE_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_INCOME_EXPENSE_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_INCOME_EXPENSE_TABLE_NAME + "." + CROP_INCOME_EXPENSE_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "' AND " + CROP_INCOME_EXPENSE_TRANSACTION + " = 'Income'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select " + CROP_HARVEST_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + ".* from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " = " + CROP_HARVEST_TABLE_NAME + "." + CROP_HARVEST_CROP_ID +
                " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_YEAR + " = " + year + " AND " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_SEASON + " = '" + season + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD)) * res.getFloat(res.getColumnIndex(CROP_HARVEST_PRICE)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res.close();

        closeDB();
        Log.d("INCOMES BY CROP", expensesList.size() + " => " + expensesList.toString());
        return expensesList;

    }

    public ArrayList<GraphRecord> getGraphIncomes(String startDate, String endDate) {
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME + " where " + CROP_INCOME_EXPENSE_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') AND " + CROP_INCOME_EXPENSE_TRANSACTION + " = 'Income' AND " + CROP_INCOME_EXPENSE_DEPARTMENT + " IN ('All', 'Crops')", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DATE)),
                    res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_CATEGORY)),
                    res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }


        res = db.rawQuery("select * from " + CROP_PAYMENT_TABLE_NAME + " where " + CROP_PAYMENT_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_PAYMENT_DATE)),
                    "Payment Received",
                    res.getFloat(res.getColumnIndex(CROP_PAYMENT_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select " + CROP_HARVEST_TABLE_NAME + ".*, " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_HARVEST_TABLE_NAME + "." + CROP_HARVEST_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID +
                " where " + CROP_HARVEST_TABLE_NAME + "." + CROP_HARVEST_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);

        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)) + " Harvest",
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD)) * res.getFloat(res.getColumnIndex(CROP_HARVEST_PRICE)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return expensesList;

    }

    public void insertCropNote(CropNote note) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_NOTE_DATE, note.getDate());
        contentValues.put(CROP_NOTE_PARENT_ID, note.getParentId());
        contentValues.put(CROP_NOTE_CATEGORY, note.getCategory());
        contentValues.put(CROP_NOTE_NOTES, note.getNotes());
        contentValues.put(CROP_NOTE_IS_FOR, note.getIsFor());
        contentValues.put(CROP_SYNC_STATUS, note.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, note.getGlobalId());
        database.insert(CROP_NOTE_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropNote(CropNote note) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_NOTE_DATE, note.getDate());
        contentValues.put(CROP_NOTE_PARENT_ID, note.getParentId());
        contentValues.put(CROP_NOTE_CATEGORY, note.getCategory());
        contentValues.put(CROP_NOTE_NOTES, note.getNotes());
        contentValues.put(CROP_NOTE_IS_FOR, note.getIsFor());
        contentValues.put(CROP_SYNC_STATUS, note.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, note.getGlobalId());
        database.update(CROP_NOTE_TABLE_NAME, contentValues, CROP_NOTE_ID + " = ?", new String[]{note.getId()});

        closeDB();
    }

    public boolean deleteCropNote(String noteId) {
        CropNote note = getCropNote(noteId, false);
        openDB();
        database.delete(CROP_NOTE_TABLE_NAME, CROP_NOTE_ID + " = ?", new String[]{noteId});
        closeDB();
        if (note != null) {
            recordDeletedRecord("note", note.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropNote> getCropNotes(String parentId, String isFor) {
        openDB();
        ArrayList<CropNote> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_NOTE_TABLE_NAME + " where " + CROP_NOTE_PARENT_ID + " = " + parentId + " AND " + CROP_NOTE_IS_FOR + " = '" + isFor + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropNote note = new CropNote();
            note.setId(res.getString(res.getColumnIndex(CROP_NOTE_ID)));
            note.setParentId(res.getString(res.getColumnIndex(CROP_NOTE_PARENT_ID)));
            note.setDate(res.getString(res.getColumnIndex(CROP_NOTE_DATE)));
            note.setCategory(res.getString(res.getColumnIndex(CROP_NOTE_CATEGORY)));
            note.setIsFor(res.getString(res.getColumnIndex(CROP_NOTE_IS_FOR)));
            note.setNotes(res.getString(res.getColumnIndex(CROP_NOTE_NOTES)));
            note.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(note);
            res.moveToNext();
        }

        res.close();
        closeDB();
        Log.d("NOTES ", array_list.size() + "");
        return array_list;
    }

    public void insertCropFertilizer(CropFertilizer fertilizer) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_FERTILIZER_TYPE, fertilizer.getType());
        contentValues.put(CROP_FERTILIZER_NAME, fertilizer.getFertilizerName());
        contentValues.put(CROP_FERTILIZER_N_PERCENTAGE, fertilizer.getnPercentage());
        contentValues.put(CROP_FERTILIZER_K_PERCENTAGE, fertilizer.getkPercentage());
        contentValues.put(CROP_FERTILIZER_P_PERCENTAGE, fertilizer.getpPercentage());
        contentValues.put(CROP_SYNC_STATUS, fertilizer.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, fertilizer.getGlobalId());

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
        contentValues.put(CROP_SYNC_STATUS, fertilizer.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, fertilizer.getGlobalId());
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

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_FERTILIZER_TABLE_NAME + " where " + CROP_FERTILIZER_TYPE + " = '" + type + "'", null);
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
            Log.d("TYPE", fertilizer.getType());
            res.moveToNext();
        }

        res.close();
        closeDB();

        if (array_list.size() == 0) {
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
        contentValues.put(CROP_ITEM_P_REMOVED, crop.getpRemoved());
        contentValues.put(CROP_ITEM_N_REMOVED, crop.getnRemoved());
        contentValues.put(CROP_ITEM_K_REMOVED, crop.getkRemoved());
        contentValues.put(CROP_ITEM_IS_FOR, crop.getIsFor());

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
        contentValues.put(CROP_ITEM_P_REMOVED, crop.getpRemoved());
        contentValues.put(CROP_ITEM_N_REMOVED, crop.getnRemoved());
        contentValues.put(CROP_ITEM_K_REMOVED, crop.getkRemoved());
        contentValues.put(CROP_ITEM_IS_FOR, crop.getIsFor());
        database.update(CROP_ITEM_TABLE_NAME, contentValues, CROP_ITEM_ID + " = ? ", new String[]{crop.getId()});
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


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_ITEM_TABLE_NAME + " WHERE " + CROP_ITEM_IS_FOR + " IS NULL", null);
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

        res.close();
        closeDB();
        if (array_list.size() == 0) {
            CropDatabaseInitializerSingleton.initializeCrops(this);
            // return getCropItems();
        }
        return array_list;

    }

    public ArrayList<CropItem> getCropItemsForNutrientRemoval() {
        openDB();
        ArrayList<CropItem> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_ITEM_TABLE_NAME + " WHERE " + CROP_ITEM_IS_FOR + " = '" + CropItem.IS_FOR_NUTRIENT_REMOVAL + "'", null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
            CropItem cropItem = new CropItem();
            cropItem.setId(res.getString(res.getColumnIndex(CROP_ITEM_ID)));
            cropItem.setName(res.getString(res.getColumnIndex(CROP_ITEM_NAME)));
            cropItem.setnRemoved(res.getFloat(res.getColumnIndex(CROP_ITEM_N_REMOVED)));
            cropItem.setpRemoved(res.getFloat(res.getColumnIndex(CROP_ITEM_P_REMOVED)));
            cropItem.setkRemoved(res.getFloat(res.getColumnIndex(CROP_ITEM_K_REMOVED)));
            cropItem.setIsFor(res.getString(res.getColumnIndex(CROP_ITEM_IS_FOR)));
            array_list.add(cropItem);
            res.moveToNext();
        }

        res.close();
        closeDB();
        if (array_list.size() == 0) {
            CropDatabaseInitializerSingleton.initializeCrops(this);
            // return getCropItems();
        }
        return array_list;

    }

    public CropItem getCropItem(String id) {
        openDB();
        ArrayList<CropItem> array_list = new ArrayList();


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

        res.close();
        closeDB();

        return null;

    }

    public String getNextSalesOrderNumber() {
        openDB();
        Cursor res = database.rawQuery("select " + CROP_SALES_ORDER_ID + " from " + CROP_SALES_ORDER_TABLE_NAME + " ORDER BY " + CROP_SALES_ORDER_ID + " DESC LIMIT 1", null);
        int lastId = 0;
        res.moveToFirst();
        if (!res.isAfterLast()) {
            lastId = res.getInt(res.getColumnIndex(CROP_SALES_ORDER_ID));
        }
        int id = lastId + 1;

        res.close();
        closeDB();

        return "SO-" + String.format("%03d", id);
    }


    public float computeProductQuantityUsed(String productId) {
        openDB();
        float quantity = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select  SUM(" + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_QUANTITY + ") as quantityUsed from " + CROP_PRODUCT_ITEM_TABLE_NAME +
                " WHERE " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + productId + " AND " +
                CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_INVOICE + "'";

        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        if (!res.isAfterLast()) {
            quantity = res.getFloat(res.getColumnIndex("quantityUsed"));
        }
        res.close();
        closeDB();
        return quantity;
    }

    public float computeProductQuantityAdded(String productId) {
        openDB();
        float quantity = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select  SUM(" + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_QUANTITY + ") as quantityBought from " + CROP_PRODUCT_ITEM_TABLE_NAME +
                " WHERE " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + productId + " AND " +
                CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_BILL + "'";

        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        if (!res.isAfterLast()) {
            quantity = res.getFloat(res.getColumnIndex("quantityBought"));
        }
        res.close();
        closeDB();
        return quantity;
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
        contentValues.put(CROP_SPRAYING_RECURRENCE, spraying.getRecurrence());
        contentValues.put(CROP_SPRAYING_REMINDERS, spraying.getReminders());
        contentValues.put(CROP_SPRAYING_FREQUENCY, spraying.getFrequency());
        contentValues.put(CROP_SPRAYING_REPEAT_UNTIL, spraying.getRepeatUntil());
        contentValues.put(CROP_SPRAYING_DAYS_BEFORE, spraying.getDaysBefore());
        contentValues.put(CROP_SPRAYING_SPRAY_NAME, spraying.getSprayName());
        contentValues.put(CROP_SYNC_STATUS, spraying.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, spraying.getGlobalId());
        contentValues.put(CROP_SPRAYING_UNITS, spraying.getUsageUnits());
        database.insert(CROP_SPRAYING_TABLE_NAME, null, contentValues);

        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_SPRAYING_TABLE_NAME + " where " + CROP_SPRAYING_CROP_ID + " = " + spraying.getCropId() + " AND " + CROP_SPRAYING_DATE + " = '" + spraying.getDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_SPRAYING_ID));
        }
        generateNotifications(context.getString(R.string.notification_type_spraying), id);
        res.close();
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
        contentValues.put(CROP_SPRAYING_RECURRENCE, spraying.getRecurrence());
        contentValues.put(CROP_SPRAYING_REMINDERS, spraying.getReminders());
        contentValues.put(CROP_SPRAYING_FREQUENCY, spraying.getFrequency());
        contentValues.put(CROP_SPRAYING_REPEAT_UNTIL, spraying.getRepeatUntil());
        contentValues.put(CROP_SPRAYING_DAYS_BEFORE, spraying.getDaysBefore());
        contentValues.put(CROP_SPRAYING_SPRAY_NAME, spraying.getSprayName());
        contentValues.put(CROP_SYNC_STATUS, spraying.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, spraying.getGlobalId());
        contentValues.put(CROP_SPRAYING_UNITS, spraying.getUsageUnits());
        database.update(CROP_SPRAYING_TABLE_NAME, contentValues, CROP_SPRAYING_ID + " = ?", new String[]{spraying.getId()});
        deleteCropNotification(spraying.getId(), context.getString(R.string.notification_type_spraying));
        generateNotifications(context.getString(R.string.notification_type_spraying), spraying.getId());
        closeDB();
    }

    public boolean deleteCropSpraying(String id) {
        CropSpraying spraying = getCropSpraying(id, false);
        openDB();
        deleteCropNotification(id, context.getString(R.string.notification_type_fertilizer_application));
        openDB();
        database.delete(CROP_SPRAYING_TABLE_NAME, CROP_SPRAYING_ID + " = ?", new String[]{id});
        closeDB();
        if (spraying != null) {
            recordDeletedRecord("spraying", spraying.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropSpraying> getCropSprayings(String cropId) {

        openDB();
        ArrayList<CropSpraying> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + CROP_SPRAYING_TABLE_NAME + ".*," + CROP_INVENTORY_SPRAY_TABLE_NAME + "." + CROP_INVENTORY_SPRAY_NAME + " from " + CROP_SPRAYING_TABLE_NAME + " LEFT JOIN " + CROP_INVENTORY_SPRAY_TABLE_NAME + " ON " + CROP_SPRAYING_TABLE_NAME + "." + CROP_SPRAYING_SPRAY_ID + " = " + CROP_INVENTORY_SPRAY_TABLE_NAME + "." + CROP_INVENTORY_SPRAY_ID + " where " + CROP_SPRAYING_CROP_ID + " = " + cropId;

        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropSpraying spraying = new CropSpraying();

            spraying.setId(res.getString(res.getColumnIndex(CROP_SPRAYING_ID)));
            spraying.setUserId(res.getString(res.getColumnIndex(CROP_SPRAYING_USER_ID)));
            spraying.setDate(res.getString(res.getColumnIndex(CROP_SPRAYING_DATE)));
            spraying.setCropId(res.getString(res.getColumnIndex(CROP_SPRAYING_CROP_ID)));
            spraying.setStartTime(res.getString(res.getColumnIndex(CROP_SPRAYING_START_TIME)));
            spraying.setEndTime(res.getString(res.getColumnIndex(CROP_SPRAYING_END_TIME)));
            spraying.setCost(res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            spraying.setOperator(res.getString(res.getColumnIndex(CROP_SPRAYING_OPERATOR)));
            spraying.setWaterCondition(res.getString(res.getColumnIndex(CROP_SPRAYING_WATER_CONDITION)));
            spraying.setWaterVolume(res.getFloat(res.getColumnIndex(CROP_SPRAYING_WATER_VOLUME)));
            spraying.setWindDirection(res.getString(res.getColumnIndex(CROP_SPRAYING_WIND_DIRECTION)));
            spraying.setTreatmentReason(res.getString(res.getColumnIndex(CROP_SPRAYING_TREATMENT_REASON)));
            spraying.setEquipmentUsed(res.getString(res.getColumnIndex(CROP_SPRAYING_EQUIPMENT_USED)));
            spraying.setSprayId(res.getString(res.getColumnIndex(CROP_SPRAYING_SPRAY_ID)));
            spraying.setSprayName(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_NAME)));
            spraying.setRate(res.getFloat(res.getColumnIndex(CROP_SPRAYING_RATE)));
            spraying.setFrequency(res.getFloat(res.getColumnIndex(CROP_SPRAYING_FREQUENCY)));
            spraying.setRecurrence(res.getString(res.getColumnIndex(CROP_SPRAYING_RECURRENCE)));
            spraying.setReminders(res.getString(res.getColumnIndex(CROP_SPRAYING_REMINDERS)));
            spraying.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_SPRAYING_DAYS_BEFORE)));
            spraying.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SPRAYING_REPEAT_UNTIL)));
            spraying.setSprayName(res.getString(res.getColumnIndex(CROP_SPRAYING_SPRAY_NAME)));
            spraying.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            spraying.setUnits(res.getString(res.getColumnIndex(CROP_SPRAYING_UNITS)));
            array_list.add(spraying);
            res.moveToNext();
        }

        res.close();
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
        contentValues.put(CROP_FERTILIZER_APPLICATION_METHOD, "None");
        contentValues.put(CROP_FERTILIZER_APPLICATION_REASON, fertilizerApplication.getReason());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM, fertilizerApplication.getFertilizerForm());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_ID, fertilizerApplication.getFertilizerId());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_NAME, fertilizerApplication.getFertilizerName());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_UNITS, fertilizerApplication.getUnits());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR, fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_COST, fertilizerApplication.getCost());
        contentValues.put(CROP_FERTILIZER_APPLICATION_RATE, fertilizerApplication.getRate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_RECURRENCE, fertilizerApplication.getRecurrence());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REMINDERS, fertilizerApplication.getReminders());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FREQUENCY, fertilizerApplication.getFrequency());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL, fertilizerApplication.getRepeatUntil());
        contentValues.put(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE, fertilizerApplication.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, fertilizerApplication.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, fertilizerApplication.getGlobalId());
        database.insert(CROP_FERTILIZER_APPLICATION_TABLE_NAME, null, contentValues);

        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " where " + CROP_FERTILIZER_APPLICATION_CROP_ID + " = " + fertilizerApplication.getCropId() + " AND " + CROP_FERTILIZER_APPLICATION_DATE + " = '" + fertilizerApplication.getDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_ID));
        }
        if (id != "")
            generateNotifications(context.getString(R.string.notification_type_fertilizer_application), id);

        res.close();
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
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_NAME, fertilizerApplication.getFertilizerName());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_UNITS, fertilizerApplication.getUnits());
        contentValues.put(CROP_FERTILIZER_APPLICATION_OPERATOR, fertilizerApplication.getOperator());
        contentValues.put(CROP_FERTILIZER_APPLICATION_COST, fertilizerApplication.getCost());
        contentValues.put(CROP_FERTILIZER_APPLICATION_RATE, fertilizerApplication.getRate());
        contentValues.put(CROP_FERTILIZER_APPLICATION_RECURRENCE, fertilizerApplication.getRecurrence());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REMINDERS, fertilizerApplication.getReminders());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FREQUENCY, fertilizerApplication.getFrequency());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL, fertilizerApplication.getRepeatUntil());
        contentValues.put(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE, fertilizerApplication.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, fertilizerApplication.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, fertilizerApplication.getGlobalId());
        database.update(CROP_FERTILIZER_APPLICATION_TABLE_NAME, contentValues, CROP_FERTILIZER_APPLICATION_ID + " = ?", new String[]{fertilizerApplication.getId()});
        deleteCropNotification(fertilizerApplication.getId(), context.getString(R.string.notification_type_fertilizer_application));
        generateNotifications(context.getString(R.string.notification_type_fertilizer_application), fertilizerApplication.getId());
        closeDB();
    }

    public boolean deleteCropFertilizerApplication(String fertilizerId) {
        CropFertilizerApplication application = getCropFertilizerApplication(fertilizerId, false);
        openDB();
        deleteCropNotification(fertilizerId, context.getString(R.string.notification_type_fertilizer_application));
        database.delete(CROP_FERTILIZER_APPLICATION_TABLE_NAME, CROP_FERTILIZER_APPLICATION_ID + " = ?", new String[]{fertilizerId});
        closeDB();
        if (application != null) {
            recordDeletedRecord("fertilizerApplication", application.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropFertilizerApplication> getCropFertilizerApplications(String cropId) {
        openDB();
        ArrayList<CropFertilizerApplication> array_list = new ArrayList();


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
            crop.setUnits(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_UNITS)));
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RATE)));
            crop.setFertilizerName(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_NAME)));
            crop.setFrequency(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FREQUENCY)));
            crop.setRecurrence(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RECURRENCE)));
            crop.setReminders(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REMINDERS)));
            crop.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL)));
            crop.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(crop);
            res.moveToNext();
        }


        res.close();
        closeDB();

        return array_list;

    }


    public void insertCrop(Crop crop) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CROP_DATE_SOWN, crop.getDateSown());
        contentValues.put(CROP_CROP_USER_ID, crop.getUserId());
        contentValues.put(CROP_CROP_VARIETY, crop.getVariety());
        contentValues.put(CROP_CROP_NAME, crop.getName());
        contentValues.put(CROP_CROP_AREA, crop.getArea());
        contentValues.put(CROP_CROP_FIELD_ID, crop.getFieldId());
        contentValues.put(CROP_CROP_SEASON, crop.getSeason());
        contentValues.put(CROP_CROP_HARVEST_UNITS, crop.getHarvestUnits());
        contentValues.put(CROP_CROP_ESTIMATED_REVENUE, crop.getEstimatedRevenue());
        contentValues.put(CROP_CROP_ESTIMATED_YIELD, crop.getEstimatedYield());
        contentValues.put(CROP_SYNC_STATUS, crop.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, crop.getGlobalId());
        database.insert(CROP_CROP_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCrop(Crop crop) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CROP_DATE_SOWN, crop.getDateSown());
        contentValues.put(CROP_CROP_USER_ID, crop.getUserId());
        contentValues.put(CROP_CROP_VARIETY, crop.getVariety());
        contentValues.put(CROP_CROP_NAME, crop.getName());
        contentValues.put(CROP_CROP_AREA, crop.getArea());
        contentValues.put(CROP_CROP_FIELD_ID, crop.getFieldId());
        contentValues.put(CROP_CROP_SEASON, crop.getSeason());
        contentValues.put(CROP_CROP_HARVEST_UNITS, crop.getHarvestUnits());
        contentValues.put(CROP_CROP_ESTIMATED_REVENUE, crop.getEstimatedRevenue());
        contentValues.put(CROP_CROP_ESTIMATED_YIELD, crop.getEstimatedYield());
        contentValues.put(CROP_SYNC_STATUS, crop.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, crop.getGlobalId());
        database.update(CROP_CROP_TABLE_NAME, contentValues, CROP_CROP_ID + " = ?", new String[]{crop.getId()});

        closeDB();
    }

    public boolean deleteCrop(String cropId) {
        Crop crop = getCrop(cropId, false);
        openDB();
        database.delete(CROP_CROP_TABLE_NAME, CROP_CROP_ID + " = ?", new String[]{cropId});
        closeDB();
        if (crop != null) {
            recordDeletedRecord("crop", crop.getGlobalId());
        }
        return true;
    }

    public ArrayList<Crop> getCropsInField(String fieldId) {
        openDB();
        ArrayList<Crop> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_FIELD_ID + " = '" + fieldId + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Crop crop = new Crop();
            crop.setId(res.getString(res.getColumnIndex(CROP_CROP_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_CROP_USER_ID)));
            crop.setDateSown(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)));
            crop.setVariety(res.getString(res.getColumnIndex(CROP_CROP_VARIETY)));
            crop.setArea(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_AREA))));
            crop.setFieldId(res.getString(res.getColumnIndex(CROP_CROP_FIELD_ID)));
            crop.setName(res.getString(res.getColumnIndex(CROP_CROP_NAME)));
            crop.setHarvestUnits(res.getString(res.getColumnIndex(CROP_CROP_HARVEST_UNITS)));
            crop.setEstimatedRevenue(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_ESTIMATED_REVENUE))));
            crop.setEstimatedYield(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_ESTIMATED_YIELD))));
            crop.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(crop);
            res.moveToNext();
        }

        res.close();
        closeDB();
        Log.d("TESTING", array_list.toString());
        return array_list;

    }

    public ArrayList<Crop> getCrops(String userId) {

        openDB();
        ArrayList<Crop> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_CROP_TABLE_NAME + ".*," + CROP_FIELDS_TABLE_NAME + "." + CROP_FIELD_NAME + " from " + CROP_CROP_TABLE_NAME + " LEFT JOIN " + CROP_FIELDS_TABLE_NAME + " ON " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_FIELD_ID + "=" + CROP_FIELDS_TABLE_NAME + "." + CROP_FIELD_ID + " where " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Crop crop = new Crop();
            crop.setId(res.getString(res.getColumnIndex(CROP_CROP_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_CROP_USER_ID)));
            crop.setDateSown(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)));
            crop.setVariety(res.getString(res.getColumnIndex(CROP_CROP_VARIETY)));
            crop.setArea(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_AREA))));
//            crop.setCost(res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
//            crop.setCroppingYear(res.getInt(res.getColumnIndex(CROP_CROP_YEAR)));
//            crop.setOperator(res.getString(res.getColumnIndex(CROP_CROP_OPERATOR)));
//            crop.setSeedId(res.getString(res.getColumnIndex(CROP_CROP_SEED_ID)));
//            crop.setGrowingCycle(res.getString(res.getColumnIndex(CROP_CROP_GROWING_CYCLE)));
//            crop.setRate(res.getFloat(res.getColumnIndex(CROP_CROP_RATE)));
//            crop.setPlantingMethod(res.getString(res.getColumnIndex(CROP_CROP_PLANTING_METHOD)));
            crop.setFieldId(res.getString(res.getColumnIndex(CROP_CROP_FIELD_ID)));
            crop.setFieldName(res.getString(res.getColumnIndex(CROP_FIELD_NAME)));
            crop.setName(res.getString(res.getColumnIndex(CROP_CROP_NAME)));
//            crop.setSeason(res.getString(res.getColumnIndex(CROP_CROP_SEASON)));
            crop.setHarvestUnits(res.getString(res.getColumnIndex(CROP_CROP_HARVEST_UNITS)));
            crop.setEstimatedRevenue(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_ESTIMATED_REVENUE))));
            crop.setEstimatedYield(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_ESTIMATED_YIELD))));
            crop.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(crop);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;

    }

    public void insertCropSpray(CropInventorySpray inventorySpray) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SPRAY_DATE, inventorySpray.getPurchaseDate());
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
        contentValues.put(CROP_SYNC_STATUS, inventorySpray.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, inventorySpray.getGlobalId());
        database.insert(CROP_INVENTORY_SPRAY_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropSpray(CropInventorySpray inventorySpray) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SPRAY_DATE, inventorySpray.getPurchaseDate());
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
        contentValues.put(CROP_SYNC_STATUS, inventorySpray.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, inventorySpray.getGlobalId());
        database.update(CROP_INVENTORY_SPRAY_TABLE_NAME, contentValues, CROP_INVENTORY_SPRAY_ID + " = ?", new String[]{inventorySpray.getId()});

        closeDB();
    }

    public boolean deleteCropSpray(String sprayId) {
        CropInventorySpray spray = getCropSprayById(sprayId, false);
        openDB();
        database.delete(CROP_INVENTORY_SPRAY_TABLE_NAME, CROP_INVENTORY_SPRAY_ID + " = ?", new String[]{sprayId});
        closeDB();
        if (spray != null) {
            recordDeletedRecord("sprayInventory", spray.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropInventorySpray> getCropSpray(String userId) {
        openDB();
        ArrayList<CropInventorySpray> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_SPRAY_TABLE_NAME + " where " + CROP_INVENTORY_SPRAY_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropInventorySpray spray = new CropInventorySpray();
            spray.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_ID)));
            spray.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_USER_ID)));
            spray.setPurchaseDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_DATE)));
            spray.setName(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_NAME)));
            spray.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_TYPE)));
            spray.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_QUANTITY)));
            spray.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_COST)));
            spray.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_BATCH_NUMBER)));
            spray.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_SUPPLIER)));
            spray.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_USAGE_UNIT)));
            spray.setActiveIngredients(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS)));
            spray.setExpiryDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_EXPIRY_DATE)));
            spray.setHarvestInterval(res.getInt(res.getColumnIndex(CROP_INVENTORY_SPRAY_HARVEST_INTERVAL)));
            spray.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(spray);
            res.moveToNext();
        }

        for (CropInventorySpray spray : array_list) {
            String query = "select SUM(" + CROP_SPRAYING_RATE + ") as totalConsumed from " + CROP_SPRAYING_TABLE_NAME + " where " + CROP_SPRAYING_SPRAY_ID + " = " + spray.getId();
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if (!res.isAfterLast()) {
                spray.setTotalConsumed(res.getFloat(res.getColumnIndex("totalConsumed")));
            }

        }
        res.close();
        closeDB();
        return array_list;

    }

    public void insertCropSeeds(CropInventorySeeds inventorySeeds) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SEEDS_DATE, inventorySeeds.getPurchaseDate());
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
        contentValues.put(CROP_INVENTORY_SEEDS_TYPE, inventorySeeds.getType());
        contentValues.put(CROP_INVENTORY_SEEDS_MANUFACTURER, inventorySeeds.getManufacturer());
        contentValues.put(CROP_SYNC_STATUS, inventorySeeds.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, inventorySeeds.getGlobalId());
        database.insert(CROP_INVENTORY_SEEDS_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropSeeds(CropInventorySeeds inventorySeeds) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVENTORY_SEEDS_DATE, inventorySeeds.getPurchaseDate());
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
        contentValues.put(CROP_INVENTORY_SEEDS_TYPE, inventorySeeds.getType());
        contentValues.put(CROP_INVENTORY_SEEDS_MANUFACTURER, inventorySeeds.getManufacturer());
        contentValues.put(CROP_SYNC_STATUS, inventorySeeds.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, inventorySeeds.getGlobalId());
        database.update(CROP_INVENTORY_SEEDS_TABLE_NAME, contentValues, CROP_INVENTORY_SEEDS_ID + " = ?", new String[]{inventorySeeds.getId()});
        closeDB();
    }

    public boolean deleteCropSeeds(String seedsId) {
        CropInventorySeeds inventorySeeds = getCropSeed(seedsId, false);
        openDB();
        database.delete(CROP_INVENTORY_SEEDS_TABLE_NAME, CROP_INVENTORY_SEEDS_ID + " = ?", new String[]{seedsId});
        closeDB();
        if (inventorySeeds != null) {
            recordDeletedRecord("seedInventory", inventorySeeds.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropInventorySeeds> getCropSeeds(String userId) {
        openDB();
        ArrayList<CropInventorySeeds> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_SEEDS_TABLE_NAME + " where " + CROP_INVENTORY_SEEDS_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropInventorySeeds inventorySeeds = new CropInventorySeeds();
            inventorySeeds.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_ID)));
            inventorySeeds.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USER_ID)));
            inventorySeeds.setName(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_NAME)));
            inventorySeeds.setPurchaseDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DATE)));
            inventorySeeds.setVariety(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_VARIETY)));
            inventorySeeds.setDressing(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DRESSING)));
            inventorySeeds.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_QUANTITY)));
            inventorySeeds.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_COST)));
            inventorySeeds.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_BATCH_NUMBER)));
            inventorySeeds.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_SUPPLIER)));
            inventorySeeds.setTgw(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TGW)));
            inventorySeeds.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USAGE_UNIT)));
            inventorySeeds.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TYPE)));
            inventorySeeds.setManufacturer(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_MANUFACTURER)));
            inventorySeeds.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(inventorySeeds);
            res.moveToNext();
        }
        for (CropInventorySeeds seed : array_list) {
            String query = "select SUM(" + CROP_CROP_RATE + ") as totalConsumed from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_SEED_ID + " = " + seed.getId();
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if (!res.isAfterLast()) {
                seed.setTotalConsumed(res.getFloat(res.getColumnIndex("totalConsumed")));
            }

        }
        res.close();
        closeDB();

        return array_list;

    }

    public CropInventorySeeds getCropInventorySeed(String seedId) {
        openDB();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_SEEDS_TABLE_NAME + " where " + CROP_INVENTORY_SEEDS_ID + " = " + seedId, null);
        res.moveToFirst();
        CropInventorySeeds inventorySeeds = null;

        if (!res.isAfterLast()) {
            inventorySeeds = new CropInventorySeeds();
            inventorySeeds.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_ID)));
            inventorySeeds.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USER_ID)));
            inventorySeeds.setName(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_NAME)));
            inventorySeeds.setPurchaseDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DATE)));
            inventorySeeds.setVariety(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_VARIETY)));
            inventorySeeds.setDressing(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DRESSING)));
            inventorySeeds.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_QUANTITY)));
            inventorySeeds.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_COST)));
            inventorySeeds.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_BATCH_NUMBER)));
            inventorySeeds.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_SUPPLIER)));
            inventorySeeds.setTgw(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TGW)));
            inventorySeeds.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USAGE_UNIT)));
            inventorySeeds.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TYPE)));
            inventorySeeds.setManufacturer(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_MANUFACTURER)));
            inventorySeeds.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        if (inventorySeeds != null) {
            String query = "select SUM(" + CROP_CROP_RATE + ") as totalConsumed from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_SEED_ID + " = " + inventorySeeds.getId();
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if (!res.isAfterLast()) {
                inventorySeeds.setTotalConsumed(res.getFloat(res.getColumnIndex("totalConsumed")));
            }
        }


        res.close();
        closeDB();

        return inventorySeeds;

    }

    public void insertCropField(CropField field) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_FIELD_USER_ID, field.getUserId());
        contentValues.put(CROP_FIELD_NAME, field.getFieldName());
        contentValues.put(CROP_FIELD_TOTAL_AREA, field.getTotalArea());
        contentValues.put(CROP_FIELD_CROPPABLE_AREA, field.getCroppableArea());
        contentValues.put(CROP_FIELD_UNITS, field.getUnits());
        contentValues.put(CROP_FIELD_FIELD_TYPE, field.getFieldType());
        contentValues.put(CROP_FIELD_STATUS, field.getStatus());
        contentValues.put(CROP_SYNC_STATUS, field.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, field.getGlobalId());
        database.insert(CROP_FIELDS_TABLE_NAME, null, contentValues);
        Log.d("FIELDS LIST", contentValues.toString());
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
        contentValues.put(CROP_INVENTORY_FERTILIZER_USAGE_UNIT, fertilizer.getUsageUnits());
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
        contentValues.put(CROP_SYNC_STATUS, fertilizer.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, fertilizer.getGlobalId());
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
        contentValues.put(CROP_INVENTORY_FERTILIZER_USAGE_UNIT, fertilizer.getUsageUnits());
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
        contentValues.put(CROP_SYNC_STATUS, fertilizer.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, fertilizer.getGlobalId());
        database.update(CROP_INVENTORY_FERTILIZER_TABLE_NAME, contentValues, CROP_INVENTORY_FERTILIZER_ID + " = ?", new String[]{fertilizer.getId()});
        closeDB();
    }

    public boolean deleteCropFertilizerInventory(String fertilizerId) {
        CropInventoryFertilizer fertilizer = getCropFertilizer(fertilizerId, false);
        openDB();
        database.delete(CROP_INVENTORY_FERTILIZER_TABLE_NAME, CROP_INVENTORY_FERTILIZER_ID + " = ?", new String[]{fertilizerId});
        closeDB();
        if (fertilizer != null) {
            recordDeletedRecord("fertilizerInventory", fertilizer.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropInventoryFertilizer> getCropFertilizerInventorys(String userId) {
        openDB();
        ArrayList<CropInventoryFertilizer> array_list = new ArrayList();


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
            fertilizer.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_USAGE_UNIT)));
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
            fertilizer.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(fertilizer);

            res.moveToNext();
        }


        for (CropInventoryFertilizer fertilizer : array_list) {
            String query = "select SUM(" + CROP_FERTILIZER_APPLICATION_RATE + ") as totalConsumed from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " where " + CROP_FERTILIZER_APPLICATION_FERTILIZER_ID + " = " + fertilizer.getId();
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if (!res.isAfterLast()) {
                fertilizer.setTotalConsumed(res.getFloat(res.getColumnIndex("totalConsumed")));
            }

        }

        res.close();
        closeDB();

        //Log.d("HOUSES SIZE",array_list.size()+"");
        return array_list;

    }

    public void updateCropField(CropField field) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_FIELD_USER_ID, field.getUserId());
        contentValues.put(CROP_FIELD_NAME, field.getFieldName());
        contentValues.put(CROP_FIELD_TOTAL_AREA, field.getTotalArea());
        contentValues.put(CROP_FIELD_CROPPABLE_AREA, field.getCroppableArea());
        contentValues.put(CROP_FIELD_UNITS, field.getUnits());
        contentValues.put(CROP_FIELD_FIELD_TYPE, field.getFieldType());
        contentValues.put(CROP_FIELD_STATUS, field.getStatus());
        contentValues.put(CROP_SYNC_STATUS, field.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, field.getGlobalId());

        Log.d("UPDATE FIELD CALLED", contentValues.toString());
        database.update(CROP_FIELDS_TABLE_NAME, contentValues, CROP_FIELD_ID + " = ?", new String[]{field.getId()});
        closeDB();
    }

    public boolean deleteCropField(String fieldId) {
        CropField field = getCropField(fieldId, false);
        openDB();
        database.delete(CROP_FIELDS_TABLE_NAME, CROP_FIELD_ID + " = ?", new String[]{fieldId});
        closeDB();
//        if(field != null){
//            recordDeletedRecord("field",field.getGlobalId());
//        }
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
            field.setTotalArea(res.getFloat(res.getColumnIndex(CROP_FIELD_TOTAL_AREA)));
            field.setCroppableArea(res.getFloat(res.getColumnIndex(CROP_FIELD_CROPPABLE_AREA)));
            field.setUnits(res.getString(res.getColumnIndex(CROP_FIELD_UNITS)));
            field.setFieldType(res.getString(res.getColumnIndex(CROP_FIELD_FIELD_TYPE)));
            field.setStatus(res.getString(res.getColumnIndex(CROP_FIELD_STATUS)));
            field.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(field);
            res.moveToNext();
        }

        res.close();
        closeDB();

        Log.d("FIELDS LIST", array_list.toString());
        return array_list;
    }

    public void insertCropIncomeExpense(CropIncomeExpense incomeExpense) {
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
        contentValues.put(CROP_INCOME_EXPENSE_DEPARTMENT, incomeExpense.getDepartment());
        contentValues.put(CROP_SYNC_STATUS, incomeExpense.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, incomeExpense.getGlobalId());
        database.insert(CROP_INCOME_EXPENSE_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropIncomeExpense(CropIncomeExpense incomeExpense) {
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
        contentValues.put(CROP_INCOME_EXPENSE_DEPARTMENT, incomeExpense.getDepartment());
        contentValues.put(CROP_SYNC_STATUS, incomeExpense.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, incomeExpense.getGlobalId());
        database.update(CROP_INCOME_EXPENSE_TABLE_NAME, contentValues, CROP_INCOME_EXPENSE_ID + " = ?", new String[]{incomeExpense.getId()});

        closeDB();
    }

    public boolean deleteCropIncomeExpense(String incomeExpenseId) {
        CropIncomeExpense incomeExpense = getCropIncomeExpense(incomeExpenseId, false);
        openDB();
        database.delete(CROP_INCOME_EXPENSE_TABLE_NAME, CROP_INCOME_EXPENSE_ID + " = ?", new String[]{incomeExpenseId});
        closeDB();
        if (incomeExpense != null) {
            recordDeletedRecord("incomeExpense", incomeExpense.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropIncomeExpense> getCropIncomeExpenses(String userId) {
        openDB();
        ArrayList<CropIncomeExpense> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME + " where " + CROP_INCOME_EXPENSE_USER_ID + " = " + userId, null);
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
            incomeExpense.setDepartment(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DEPARTMENT)));
            incomeExpense.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(incomeExpense);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public void insertCropHarvest(CropHarvest harvest) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_HARVEST_USER_ID, harvest.getUserId());
        contentValues.put(CROP_HARVEST_CROP_ID, harvest.getCropId());
        contentValues.put(CROP_HARVEST_EMPLOYEE_ID, harvest.getEmployeeId());
        contentValues.put(CROP_HARVEST_DATE, harvest.getDate());
        contentValues.put(CROP_HARVEST_METHOD, harvest.getMethod());
        contentValues.put(CROP_HARVEST_UNITS, harvest.getUnits());
        contentValues.put(CROP_HARVEST_QUANTITY, harvest.getQuantity());
        contentValues.put(CROP_HARVEST_OPERATOR, harvest.getOperator());
        contentValues.put(CROP_HARVEST_STATUS, harvest.getStatus());
        contentValues.put(CROP_HARVEST_DATE_SOLD, harvest.getDateSold());
        contentValues.put(CROP_HARVEST_CUSTOMER, harvest.getCustomer());
        contentValues.put(CROP_HARVEST_PRICE, harvest.getPrice());
        contentValues.put(CROP_HARVEST_QUANTITY_SOLD, harvest.getQuantitySold());
        contentValues.put(CROP_HARVEST_STORAGE_DATE, harvest.getStorageDate());
        contentValues.put(CROP_HARVEST_QUANTITY_STORED, harvest.getQuantityStored());
        contentValues.put(CROP_HARVEST_COST, harvest.getCost());
        contentValues.put(CROP_HARVEST_RECURRENCE, harvest.getRecurrence());
        contentValues.put(CROP_HARVEST_REMINDERS, harvest.getReminders());
        contentValues.put(CROP_HARVEST_FREQUENCY, harvest.getFrequency());
        contentValues.put(CROP_HARVEST_REPEAT_UNTIL, harvest.getRepeatUntil());
        contentValues.put(CROP_HARVEST_DAYS_BEFORE, harvest.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, harvest.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, harvest.getGlobalId());
        database.insert(CROP_HARVEST_TABLE_NAME, null, contentValues);
        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_HARVEST_CROP_ID + " = " + harvest.getCropId() + " AND " + CROP_HARVEST_DATE + " = '" + harvest.getDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_HARVEST_ID));
        }
        generateNotifications(context.getString(R.string.notification_type_harvest), id);
        res.close();
        closeDB();
    }

    public void updateCropHarvest(CropHarvest harvest) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_HARVEST_USER_ID, harvest.getUserId());
        contentValues.put(CROP_HARVEST_CROP_ID, harvest.getCropId());
        contentValues.put(CROP_HARVEST_EMPLOYEE_ID, harvest.getEmployeeId());
        contentValues.put(CROP_HARVEST_DATE, harvest.getDate());
        contentValues.put(CROP_HARVEST_METHOD, harvest.getMethod());
        contentValues.put(CROP_HARVEST_UNITS, harvest.getUnits());
        contentValues.put(CROP_HARVEST_QUANTITY, harvest.getQuantity());
        contentValues.put(CROP_HARVEST_OPERATOR, harvest.getOperator());
        contentValues.put(CROP_HARVEST_STATUS, harvest.getStatus());
        contentValues.put(CROP_HARVEST_DATE_SOLD, harvest.getDateSold());
        contentValues.put(CROP_HARVEST_CUSTOMER, harvest.getCustomer());
        contentValues.put(CROP_HARVEST_PRICE, harvest.getPrice());
        contentValues.put(CROP_HARVEST_QUANTITY_SOLD, harvest.getQuantitySold());
        contentValues.put(CROP_HARVEST_STORAGE_DATE, harvest.getStorageDate());
        contentValues.put(CROP_HARVEST_QUANTITY_STORED, harvest.getQuantityStored());
        contentValues.put(CROP_HARVEST_COST, harvest.getCost());
        contentValues.put(CROP_HARVEST_RECURRENCE, harvest.getRecurrence());
        contentValues.put(CROP_HARVEST_REMINDERS, harvest.getReminders());
        contentValues.put(CROP_HARVEST_FREQUENCY, harvest.getFrequency());
        contentValues.put(CROP_HARVEST_REPEAT_UNTIL, harvest.getRepeatUntil());
        contentValues.put(CROP_HARVEST_DAYS_BEFORE, harvest.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, harvest.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, harvest.getGlobalId());
        database.update(CROP_HARVEST_TABLE_NAME, contentValues, CROP_HARVEST_ID + " = ?", new String[]{harvest.getId()});

        deleteCropNotification(harvest.getId(), context.getString(R.string.notification_type_harvest));
        generateNotifications(context.getString(R.string.notification_type_harvest), harvest.getId());
        closeDB();
    }

    public boolean deleteCropHarvest(String harvestId) {
        CropHarvest harvest = getCropHarvest(harvestId, false);
        openDB();
        database.delete(CROP_HARVEST_TABLE_NAME, CROP_HARVEST_ID + " = ?", new String[]{harvestId});
        closeDB();
        if (harvest != null) {
            recordDeletedRecord("harvest", harvest.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropHarvest> getCropHarvests(String cropId) {
        openDB();
        ArrayList<CropHarvest> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_HARVEST_CROP_ID + " = '" + cropId + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropHarvest harvest = new CropHarvest();
            harvest.setId(res.getString(res.getColumnIndex(CROP_HARVEST_ID)));
            harvest.setUserId(res.getString(res.getColumnIndex(CROP_HARVEST_USER_ID)));
            harvest.setCropId(res.getString(res.getColumnIndex(CROP_HARVEST_CROP_ID)));
            harvest.setEmployeeId(res.getString(res.getColumnIndex(CROP_HARVEST_EMPLOYEE_ID)));
            harvest.setDate(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)));
            harvest.setMethod(res.getString(res.getColumnIndex(CROP_HARVEST_METHOD)));
            harvest.setUnits(res.getString(res.getColumnIndex(CROP_HARVEST_UNITS)));
            harvest.setQuantity(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_QUANTITY))));
            harvest.setOperator(res.getString(res.getColumnIndex(CROP_HARVEST_OPERATOR)));
            harvest.setStatus(res.getString(res.getColumnIndex(CROP_HARVEST_STATUS)));
            harvest.setDateSold(res.getString(res.getColumnIndex(CROP_HARVEST_DATE_SOLD)));
            harvest.setCustomer(res.getString(res.getColumnIndex(CROP_HARVEST_CUSTOMER)));
            harvest.setPrice(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_PRICE))));
            harvest.setQuantitySold(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD))));
            harvest.setStorageDate(res.getString(res.getColumnIndex(CROP_HARVEST_STORAGE_DATE)));
            harvest.setQuantityStored(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_QUANTITY_STORED))));
            harvest.setCost(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_COST))));
            harvest.setFrequency(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_FREQUENCY))));
            harvest.setRecurrence(res.getString(res.getColumnIndex(CROP_HARVEST_RECURRENCE)));
            harvest.setReminders(res.getString(res.getColumnIndex(CROP_HARVEST_REMINDERS)));
            harvest.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_HARVEST_DAYS_BEFORE)));
            harvest.setRepeatUntil(res.getString(res.getColumnIndex(CROP_HARVEST_REPEAT_UNTIL)));
            harvest.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            array_list.add(harvest);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropField> getCropFields(String userId, boolean synced) {
        openDB();
        ArrayList<CropField> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_FIELDS_TABLE_NAME + " where " + CROP_FIELD_USER_ID + " = '" + userId + "' AND " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropField field = new CropField();
            field.setId(res.getString(res.getColumnIndex(CROP_FIELD_ID)));
            field.setUserId(res.getString(res.getColumnIndex(CROP_FIELD_USER_ID)));
            field.setFieldName(res.getString(res.getColumnIndex(CROP_FIELD_NAME)));
            field.setTotalArea(res.getFloat(res.getColumnIndex(CROP_FIELD_TOTAL_AREA)));
            field.setCroppableArea(res.getFloat(res.getColumnIndex(CROP_FIELD_CROPPABLE_AREA)));
            field.setUnits(res.getString(res.getColumnIndex(CROP_FIELD_UNITS)));
            field.setFieldType(res.getString(res.getColumnIndex(CROP_FIELD_FIELD_TYPE)));
            field.setStatus(res.getString(res.getColumnIndex(CROP_FIELD_STATUS)));
            field.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            field.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            array_list.add(field);

            res.moveToNext();
        }

        res.close();
        closeDB();


        return array_list;
    }


    public ArrayList<CropIncomeExpense> getCropIncomeExpenses(String userId, boolean synced) {
        openDB();
        ArrayList<CropIncomeExpense> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME + " where " + CROP_INCOME_EXPENSE_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
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
            incomeExpense.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            incomeExpense.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(incomeExpense);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropInventorySeeds> getCropSeeds(String userId, boolean synced) {
        openDB();
        ArrayList<CropInventorySeeds> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_SEEDS_TABLE_NAME + " where " + CROP_INVENTORY_SEEDS_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropInventorySeeds inventorySeeds = new CropInventorySeeds();
            inventorySeeds.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_ID)));
            inventorySeeds.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USER_ID)));
            inventorySeeds.setName(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_NAME)));
            inventorySeeds.setPurchaseDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DATE)));
            inventorySeeds.setVariety(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_VARIETY)));
            inventorySeeds.setDressing(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DRESSING)));
            inventorySeeds.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_QUANTITY)));
            inventorySeeds.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_COST)));
            inventorySeeds.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_BATCH_NUMBER)));
            inventorySeeds.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_SUPPLIER)));
            inventorySeeds.setTgw(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TGW)));
            inventorySeeds.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USAGE_UNIT)));
            inventorySeeds.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TYPE)));
            inventorySeeds.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            inventorySeeds.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(inventorySeeds);
            res.moveToNext();
        }
        res.close();
        closeDB();

        return array_list;
    }

    public ArrayList<CropInventorySpray> getCropSpray(String userId, boolean synced) {
        openDB();
        ArrayList<CropInventorySpray> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_SPRAY_TABLE_NAME + " where " + CROP_INVENTORY_SPRAY_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropInventorySpray spray = new CropInventorySpray();
            spray.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_ID)));
            spray.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_USER_ID)));
            spray.setPurchaseDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_DATE)));
            spray.setName(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_NAME)));
            spray.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_TYPE)));
            spray.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_QUANTITY)));
            spray.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_COST)));
            spray.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_BATCH_NUMBER)));
            spray.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_SUPPLIER)));
            spray.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_USAGE_UNIT)));
            spray.setActiveIngredients(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS)));
            spray.setExpiryDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_EXPIRY_DATE)));
            spray.setHarvestInterval(res.getInt(res.getColumnIndex(CROP_INVENTORY_SPRAY_HARVEST_INTERVAL)));
            spray.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            spray.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(spray);
            res.moveToNext();
        }


        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropInventoryFertilizer> getCropFertilizerInventorys(String userId, boolean synced) {
        openDB();
        ArrayList<CropInventoryFertilizer> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + " where " + CROP_INVENTORY_FERTILIZER_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
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
            fertilizer.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_USAGE_UNIT)));
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
            fertilizer.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            fertilizer.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(fertilizer);

            res.moveToNext();
        }

        res.close();
        closeDB();


        return array_list;

    }

    public ArrayList<CropSpraying> getCropSprayings(String userId, boolean synced) {
        openDB();
        ArrayList<CropSpraying> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + CROP_SPRAYING_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?";
        Cursor res = db.rawQuery(query, new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropSpraying crop = new CropSpraying();
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
//            crop.setSprayName(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_NAME)));
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_SPRAYING_RATE)));
            crop.setFrequency(res.getFloat(res.getColumnIndex(CROP_SPRAYING_FREQUENCY)));
            crop.setRecurrence(res.getString(res.getColumnIndex(CROP_SPRAYING_RECURRENCE)));
            crop.setReminders(res.getString(res.getColumnIndex(CROP_SPRAYING_REMINDERS)));
            crop.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_SPRAYING_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SPRAYING_REPEAT_UNTIL)));
            crop.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            crop.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(crop);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;
    }

    public CropSettingsSingleton getSettings(String userId, boolean synced) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SETTINGS_TABLE_NAME + " where " + CROP_SETTINGS_ID + " = '" + userId + "'", null);
        res.moveToFirst();
        CropSettingsSingleton settingsSingleton = null;
        if (!res.isAfterLast()) {
            settingsSingleton = CropSettingsSingleton.getInstance();
            settingsSingleton.setCurrency(res.getString(res.getColumnIndex(CROP_SETTINGS_CURRENCY)));
            settingsSingleton.setAreaUnits(res.getString(res.getColumnIndex(CROP_SETTINGS_AREA_UNITS)));
            settingsSingleton.setWeightUnits(res.getString(res.getColumnIndex(CROP_SETTINGS_WEIGHT_UNITS)));
            settingsSingleton.setDateFormat(res.getString(res.getColumnIndex(CROP_SETTINGS_DATE_FORMAT)));
            settingsSingleton.setId(res.getString(res.getColumnIndex(CROP_SETTINGS_ID)));
            settingsSingleton.setUserId(res.getString(res.getColumnIndex(CROP_SETTINGS_USER_ID)));
            settingsSingleton.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            settingsSingleton.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();

        return settingsSingleton;
    }

    public ArrayList<Crop> getCrops(String userId, boolean synced) {

        openDB();
        ArrayList<Crop> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Crop crop = new Crop();
            crop.setId(res.getString(res.getColumnIndex(CROP_CROP_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_CROP_USER_ID)));
            crop.setDateSown(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)));
            crop.setVariety(res.getString(res.getColumnIndex(CROP_CROP_VARIETY)));
            crop.setArea(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_AREA))));
//            crop.setCost(res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
//            crop.setCroppingYear(res.getInt(res.getColumnIndex(CROP_CROP_YEAR)));
//            crop.setOperator(res.getString(res.getColumnIndex(CROP_CROP_OPERATOR)));
//            crop.setSeedId(res.getString(res.getColumnIndex(CROP_CROP_SEED_ID)));
//            crop.setGrowingCycle(res.getString(res.getColumnIndex(CROP_CROP_GROWING_CYCLE)));
//            crop.setRate(res.getFloat(res.getColumnIndex(CROP_CROP_RATE)));
//            crop.setPlantingMethod(res.getString(res.getColumnIndex(CROP_CROP_PLANTING_METHOD)));
            crop.setFieldId(res.getString(res.getColumnIndex(CROP_CROP_FIELD_ID)));
            crop.setFieldName(res.getString(res.getColumnIndex(CROP_FIELD_NAME)));
            crop.setName(res.getString(res.getColumnIndex(CROP_CROP_NAME)));
//            crop.setSeason(res.getString(res.getColumnIndex(CROP_CROP_SEASON)));
            crop.setHarvestUnits(res.getString(res.getColumnIndex(CROP_CROP_HARVEST_UNITS)));
            crop.setEstimatedRevenue(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_ESTIMATED_REVENUE))));
            crop.setEstimatedYield(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_ESTIMATED_YIELD))));
            crop.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(crop);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropHarvest> getCropHarvests(String userId, boolean synced) {
        openDB();
        ArrayList<CropHarvest> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropHarvest harvest = new CropHarvest();
            harvest.setId(res.getString(res.getColumnIndex(CROP_HARVEST_ID)));
            harvest.setUserId(res.getString(res.getColumnIndex(CROP_HARVEST_USER_ID)));
            harvest.setCropId(res.getString(res.getColumnIndex(CROP_HARVEST_CROP_ID)));
            harvest.setEmployeeId(res.getString(res.getColumnIndex(CROP_HARVEST_EMPLOYEE_ID)));
            harvest.setDate(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)));
            harvest.setMethod(res.getString(res.getColumnIndex(CROP_HARVEST_METHOD)));
            harvest.setUnits(res.getString(res.getColumnIndex(CROP_HARVEST_UNITS)));
            harvest.setQuantity(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_QUANTITY))));
            harvest.setOperator(res.getString(res.getColumnIndex(CROP_HARVEST_OPERATOR)));
            harvest.setStatus(res.getString(res.getColumnIndex(CROP_HARVEST_STATUS)));
            harvest.setDateSold(res.getString(res.getColumnIndex(CROP_HARVEST_DATE_SOLD)));
            harvest.setCustomer(res.getString(res.getColumnIndex(CROP_HARVEST_CUSTOMER)));
            harvest.setPrice(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_PRICE))));
            harvest.setQuantitySold(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD))));
            harvest.setStorageDate(res.getString(res.getColumnIndex(CROP_HARVEST_STORAGE_DATE)));
            harvest.setQuantityStored(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_QUANTITY_STORED))));
            harvest.setCost(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_COST))));
            harvest.setFrequency(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_FREQUENCY))));
            harvest.setRecurrence(res.getString(res.getColumnIndex(CROP_HARVEST_RECURRENCE)));
            harvest.setReminders(res.getString(res.getColumnIndex(CROP_HARVEST_REMINDERS)));
            harvest.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_HARVEST_DAYS_BEFORE)));
            harvest.setRepeatUntil(res.getString(res.getColumnIndex(CROP_HARVEST_REPEAT_UNTIL)));
            harvest.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            harvest.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(harvest);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropNote> getCropNotes(String userId, boolean synced) {
        openDB();
        ArrayList<CropNote> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_NOTE_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropNote note = new CropNote();
            note.setId(res.getString(res.getColumnIndex(CROP_NOTE_ID)));
            note.setParentId(res.getString(res.getColumnIndex(CROP_NOTE_PARENT_ID)));
            note.setDate(res.getString(res.getColumnIndex(CROP_NOTE_DATE)));
            note.setCategory(res.getString(res.getColumnIndex(CROP_NOTE_CATEGORY)));
            note.setIsFor(res.getString(res.getColumnIndex(CROP_NOTE_IS_FOR)));
            note.setNotes(res.getString(res.getColumnIndex(CROP_NOTE_NOTES)));
            note.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            note.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(note);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return array_list;
    }

    public CropField getCropField(String fieldId, boolean isGlobal) {
        openDB();
        CropField field = null;
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_FIELD_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_FIELDS_TABLE_NAME + " where " + key + " = '" + fieldId + "'", null);
        res.moveToFirst();
//
//        if (!res.isAfterLast()) {
//            field = new CropField();
//            field.setId(res.getString(res.getColumnIndex(CROP_FIELD_ID)));
//            field.setUserId(res.getString(res.getColumnIndex(CROP_FIELD_USER_ID)));
//            field.setFieldName(res.getString(res.getColumnIndex(CROP_FIELD_NAME)));
//            field.setSoilCategory(res.getString(res.getColumnIndex(CROP_FIELD_SOIL_CATEGORY)));
//            field.setSoilType(res.getString(res.getColumnIndex(CROP_FIELD_SOIL_TYPE)));
//            field.setWatercourse(res.getString(res.getColumnIndex(CROP_FIELD_WATERCOURSE)));
//            field.setTotalArea(res.getFloat(res.getColumnIndex(CROP_FIELD_TOTAL_AREA)));
//            field.setCroppableArea(res.getFloat(res.getColumnIndex(CROP_FIELD_CROPPABLE_AREA)));
//            field.setUnits(res.getString(res.getColumnIndex(CROP_FIELD_UNITS)));
//            field.setFieldType(res.getString(res.getColumnIndex(CROP_FIELD_FIELD_TYPE)));
//            field.setLayoutType(res.getString(res.getColumnIndex(CROP_FIELD_LAYOUT_TYPE)));
//            field.setStatus(res.getString(res.getColumnIndex(CROP_FIELD_STATUS)));
//            field.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
//            field.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
//            res.moveToNext();
//        }

        res.close();
        closeDB();


        return field;
    }

    public CropInventorySeeds getCropSeed(String seedId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_INVENTORY_SEEDS_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_SEEDS_TABLE_NAME + " where " + key + " = '" + seedId + "'", null);
        res.moveToFirst();
        CropInventorySeeds inventorySeeds = null;
        if (!res.isAfterLast()) {
            inventorySeeds = new CropInventorySeeds();
            inventorySeeds.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_ID)));
            inventorySeeds.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USER_ID)));
            inventorySeeds.setName(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_NAME)));
            inventorySeeds.setPurchaseDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DATE)));
            inventorySeeds.setVariety(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_VARIETY)));
            inventorySeeds.setDressing(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DRESSING)));
            inventorySeeds.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_QUANTITY)));
            inventorySeeds.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_COST)));
            inventorySeeds.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_BATCH_NUMBER)));
            inventorySeeds.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_SUPPLIER)));
            inventorySeeds.setTgw(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TGW)));
            inventorySeeds.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USAGE_UNIT)));
            inventorySeeds.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TYPE)));
            inventorySeeds.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            inventorySeeds.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }
        res.close();
        closeDB();

        return inventorySeeds;

    }

    public Crop getCrop(String cropId, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_CROP_ID;

        Cursor res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + key + " = '" + cropId + "'", null);
        res.moveToFirst();
        Crop crop = null;
        if (!res.isAfterLast()) {
            crop = new Crop();
            crop.setId(res.getString(res.getColumnIndex(CROP_CROP_ID)));
            crop.setUserId(res.getString(res.getColumnIndex(CROP_CROP_USER_ID)));
            crop.setDateSown(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)));
            crop.setVariety(res.getString(res.getColumnIndex(CROP_CROP_VARIETY)));
            crop.setArea(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_AREA))));
//            crop.setCost(res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
//            crop.setCroppingYear(res.getInt(res.getColumnIndex(CROP_CROP_YEAR)));
//            crop.setOperator(res.getString(res.getColumnIndex(CROP_CROP_OPERATOR)));
//            crop.setSeedId(res.getString(res.getColumnIndex(CROP_CROP_SEED_ID)));
//            crop.setGrowingCycle(res.getString(res.getColumnIndex(CROP_CROP_GROWING_CYCLE)));
//            crop.setRate(res.getFloat(res.getColumnIndex(CROP_CROP_RATE)));
//            crop.setPlantingMethod(res.getString(res.getColumnIndex(CROP_CROP_PLANTING_METHOD)));
            crop.setFieldId(res.getString(res.getColumnIndex(CROP_CROP_FIELD_ID)));
            crop.setFieldName(res.getString(res.getColumnIndex(CROP_FIELD_NAME)));
            crop.setName(res.getString(res.getColumnIndex(CROP_CROP_NAME)));
            crop.setSeason(res.getString(res.getColumnIndex(CROP_CROP_SEASON)));
            crop.setHarvestUnits(res.getString(res.getColumnIndex(CROP_CROP_HARVEST_UNITS)));
            crop.setEstimatedRevenue(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_ESTIMATED_REVENUE))));
            crop.setEstimatedYield(Float.parseFloat(res.getString(res.getColumnIndex(CROP_CROP_ESTIMATED_YIELD))));
            crop.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return crop;
    }

    public CropInventorySpray getCropSprayById(String sprayId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_INVENTORY_SPRAY_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_SPRAY_TABLE_NAME + " where " + key + " = ?", new String[]{sprayId});
        res.moveToFirst();
        CropInventorySpray spray = null;
        if (!res.isAfterLast()) {
            spray = new CropInventorySpray();
            spray.setId(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_ID)));
            spray.setUserId(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_USER_ID)));
            spray.setPurchaseDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_DATE)));
            spray.setName(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_NAME)));
            spray.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_TYPE)));
            spray.setQuantity(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_QUANTITY)));
            spray.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_COST)));
            spray.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_BATCH_NUMBER)));
            spray.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_SUPPLIER)));
            spray.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_USAGE_UNIT)));
            spray.setActiveIngredients(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_ACTIVE_INGREDIENTS)));
            spray.setExpiryDate(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_EXPIRY_DATE)));
            spray.setHarvestInterval(res.getInt(res.getColumnIndex(CROP_INVENTORY_SPRAY_HARVEST_INTERVAL)));
            spray.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            spray.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return spray;
    }

    public ArrayList<CropFertilizerApplication> getCropFertilizerApplications(String userId, boolean synced) {
        openDB();
        ArrayList<CropFertilizerApplication> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropFertilizerApplication fertilizerApplication = new CropFertilizerApplication();
            fertilizerApplication.setId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_ID)));
            fertilizerApplication.setUserId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_USER_ID)));
            fertilizerApplication.setDate(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE)));
            fertilizerApplication.setCropId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_CROP_ID)));
            fertilizerApplication.setMethod(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_METHOD)));
            fertilizerApplication.setCost(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST)));
            fertilizerApplication.setOperator(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_OPERATOR)));
            fertilizerApplication.setReason(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REASON)));
            fertilizerApplication.setFertilizerForm(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM)));
            fertilizerApplication.setFertilizerId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_ID)));
            fertilizerApplication.setRate(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RATE)));
            fertilizerApplication.setFertilizerName(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_NAME)));
            fertilizerApplication.setUnits(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_UNITS)));
            fertilizerApplication.setFrequency(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FREQUENCY)));
            fertilizerApplication.setRecurrence(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RECURRENCE)));
            fertilizerApplication.setReminders(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REMINDERS)));
            fertilizerApplication.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE)));
            fertilizerApplication.setRepeatUntil(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL)));
            fertilizerApplication.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            fertilizerApplication.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(fertilizerApplication);
            res.moveToNext();
        }


        res.close();
        closeDB();

        return array_list;

    }

    public CropInventoryFertilizer getCropFertilizer(String fertilizerId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_INVENTORY_FERTILIZER_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + " where " + key + " = ?", new String[]{fertilizerId});
        res.moveToFirst();
        CropInventoryFertilizer fertilizer = null;

        if (!res.isAfterLast()) {
            fertilizer = new CropInventoryFertilizer();
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
            fertilizer.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_USAGE_UNIT)));
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
            fertilizer.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            fertilizer.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return fertilizer;
    }

    public CropHarvest getCropHarvest(String harvestId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_HARVEST_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + key + " = ? ", new String[]{harvestId});
        res.moveToFirst();
        CropHarvest harvest = null;
        if (!res.isAfterLast()) {
            harvest = new CropHarvest();
            harvest.setId(res.getString(res.getColumnIndex(CROP_HARVEST_ID)));
            harvest.setUserId(res.getString(res.getColumnIndex(CROP_HARVEST_USER_ID)));
            harvest.setCropId(res.getString(res.getColumnIndex(CROP_HARVEST_CROP_ID)));
            harvest.setEmployeeId(res.getString(res.getColumnIndex(CROP_HARVEST_EMPLOYEE_ID)));
            harvest.setDate(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)));
            harvest.setMethod(res.getString(res.getColumnIndex(CROP_HARVEST_METHOD)));
            harvest.setUnits(res.getString(res.getColumnIndex(CROP_HARVEST_UNITS)));
            harvest.setQuantity(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_QUANTITY))));
            harvest.setOperator(res.getString(res.getColumnIndex(CROP_HARVEST_OPERATOR)));
            harvest.setStatus(res.getString(res.getColumnIndex(CROP_HARVEST_STATUS)));
            harvest.setDateSold(res.getString(res.getColumnIndex(CROP_HARVEST_DATE_SOLD)));
            harvest.setCustomer(res.getString(res.getColumnIndex(CROP_HARVEST_CUSTOMER)));
            harvest.setPrice(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_PRICE))));
            harvest.setQuantitySold(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD))));
            harvest.setStorageDate(res.getString(res.getColumnIndex(CROP_HARVEST_STORAGE_DATE)));
            harvest.setQuantityStored(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_QUANTITY_STORED))));
            harvest.setCost(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_COST))));
            harvest.setFrequency(Float.parseFloat(res.getString(res.getColumnIndex(CROP_HARVEST_FREQUENCY))));
            harvest.setRecurrence(res.getString(res.getColumnIndex(CROP_HARVEST_RECURRENCE)));
            harvest.setReminders(res.getString(res.getColumnIndex(CROP_HARVEST_REMINDERS)));
            harvest.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_HARVEST_DAYS_BEFORE)));
            harvest.setRepeatUntil(res.getString(res.getColumnIndex(CROP_HARVEST_REPEAT_UNTIL)));
            harvest.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            harvest.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return harvest;
    }

    public CropFertilizerApplication getCropFertilizerApplication(String applicationId, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_FERTILIZER_APPLICATION_ID;
        Cursor res = db.rawQuery("select * from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " where " + key + " = ? ", new String[]{applicationId});
        res.moveToFirst();
        CropFertilizerApplication fertilizerApplication = null;

        if (!res.isAfterLast()) {
            fertilizerApplication = new CropFertilizerApplication();
            fertilizerApplication.setId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_ID)));
            fertilizerApplication.setUserId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_USER_ID)));
            fertilizerApplication.setDate(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE)));
            fertilizerApplication.setCropId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_CROP_ID)));
            fertilizerApplication.setMethod(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_METHOD)));
            fertilizerApplication.setCost(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST)));
            fertilizerApplication.setOperator(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_OPERATOR)));
            fertilizerApplication.setReason(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REASON)));
            fertilizerApplication.setFertilizerForm(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM)));
            fertilizerApplication.setFertilizerId(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_ID)));
            fertilizerApplication.setRate(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RATE)));
            fertilizerApplication.setFertilizerName(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_NAME)));
            fertilizerApplication.setUnits(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FERTILIZER_UNITS)));
            fertilizerApplication.setFrequency(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FREQUENCY)));
            fertilizerApplication.setRecurrence(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RECURRENCE)));
            fertilizerApplication.setReminders(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REMINDERS)));
            fertilizerApplication.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE)));
            fertilizerApplication.setRepeatUntil(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL)));
            fertilizerApplication.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            fertilizerApplication.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }

        res.close();
        closeDB();

        return fertilizerApplication;

    }

    public CropSpraying getCropSpraying(String sprayingId, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_SPRAYING_ID;
        String query = "select * from " + CROP_SPRAYING_TABLE_NAME + " where " + key + " = ?";
        Cursor res = db.rawQuery(query, new String[]{sprayingId});
        res.moveToFirst();
        CropSpraying crop = null;

        if (!res.isAfterLast()) {
            crop = new CropSpraying();
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
//            crop.setSprayName(res.getString(res.getColumnIndex(CROP_INVENTORY_SPRAY_NAME)));
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_SPRAYING_RATE)));
            crop.setFrequency(res.getFloat(res.getColumnIndex(CROP_SPRAYING_FREQUENCY)));
            crop.setRecurrence(res.getString(res.getColumnIndex(CROP_SPRAYING_RECURRENCE)));
            crop.setReminders(res.getString(res.getColumnIndex(CROP_SPRAYING_REMINDERS)));
            crop.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_SPRAYING_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SPRAYING_REPEAT_UNTIL)));
            crop.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            crop.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }

        res.close();
        closeDB();
        return crop;
    }

    public CropNote getCropNote(String noteId, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_NOTE_ID;
        Cursor res = db.rawQuery("select * from " + CROP_NOTE_TABLE_NAME + " where " + key + " = ?", new String[]{noteId});
        res.moveToFirst();
        CropNote note = null;
        if (!res.isAfterLast()) {
            note = new CropNote();
            note.setId(res.getString(res.getColumnIndex(CROP_NOTE_ID)));
            note.setParentId(res.getString(res.getColumnIndex(CROP_NOTE_PARENT_ID)));
            note.setDate(res.getString(res.getColumnIndex(CROP_NOTE_DATE)));
            note.setCategory(res.getString(res.getColumnIndex(CROP_NOTE_CATEGORY)));
            note.setIsFor(res.getString(res.getColumnIndex(CROP_NOTE_IS_FOR)));
            note.setNotes(res.getString(res.getColumnIndex(CROP_NOTE_NOTES)));
            note.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            note.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return note;

    }

    public CropIncomeExpense getCropIncomeExpense(String incomeExpenseId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_INCOME_EXPENSE_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME + " where " + key + " = ?", new String[]{incomeExpenseId});
        res.moveToFirst();
        CropIncomeExpense incomeExpense = null;
        if (!res.isAfterLast()) {
            incomeExpense = new CropIncomeExpense();
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
            incomeExpense.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            incomeExpense.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return incomeExpense;
    }

    public void insertProduce(MyProduce produce) {
        openDB();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ADD_PRODUCE_NAME, produce.getName());
        contentValues.put(ADD_PRODUCE_VARIETY, produce.getVariety());
        contentValues.put(ADD_PRODUCE_QUANTITY, produce.getQuantity());
        contentValues.put(ADD_PRODUCE_PRICE, produce.getPrice());
        contentValues.put(ADD_PRODUCE_DATE, produce.getDate());
        contentValues.put(ADD_PRODUCE_IMAGE, produce.getImage());
        database.insert(ADD_PRODUCE_TABLE_NAME, null, contentValues);

        closeDB();
    }

    public ArrayList<MyProduce> getAllProduce() {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ADD_PRODUCE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<MyProduce> produceArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                MyProduce model = new MyProduce();

                model.setName(cursor.getString(cursor.getColumnIndex(ADD_PRODUCE_NAME)));
                model.setVariety(cursor.getString(cursor.getColumnIndex(ADD_PRODUCE_VARIETY)));
                model.setQuantity(cursor.getString(cursor.getColumnIndex(ADD_PRODUCE_QUANTITY)));
                model.setPrice(cursor.getString(cursor.getColumnIndex(ADD_PRODUCE_PRICE)));
                model.setDate(cursor.getString(cursor.getColumnIndex(ADD_PRODUCE_DATE)));
                model.setImage(cursor.getString(cursor.getColumnIndex(ADD_PRODUCE_IMAGE)));

                produceArrayList.add(model);

            } while (cursor.moveToNext());
        }

        return produceArrayList;
    }

    public ArrayList<DeletedRecord> getDeletedRecords(String userId, boolean synced) {
        ArrayList<DeletedRecord> items_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_DELETED_RECORDS_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            DeletedRecord deletedRecord = new DeletedRecord();
            deletedRecord.setId(res.getString(res.getColumnIndex(CROP_DELETED_ID)));
            deletedRecord.setType(res.getString(res.getColumnIndex(CROP_DELETED_TYPE)));
            deletedRecord.setDate(res.getString(res.getColumnIndex(CROP_DELETED_DATE)));
            deletedRecord.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            items_list.add(deletedRecord);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return items_list;

    }

    public DeletedRecord getDeletedRecord(String id) {
        ArrayList<DeletedRecord> items_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_DELETED_RECORDS_TABLE_NAME + " where " + CROP_DELETED_ID + " = ?", new String[]{id});
        res.moveToFirst();
        DeletedRecord deletedRecord = null;

        if (!res.isAfterLast()) {
            deletedRecord = new DeletedRecord();
            deletedRecord.setId(res.getString(res.getColumnIndex(CROP_DELETED_ID)));
            deletedRecord.setType(res.getString(res.getColumnIndex(CROP_DELETED_TYPE)));
            deletedRecord.setDate(res.getString(res.getColumnIndex(CROP_DELETED_DATE)));
            deletedRecord.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            res.moveToNext();
        }

        res.close();
        closeDB();

        return deletedRecord;

    }

    public void insertMarketPrice(MarketPrice marketPrice) {
        openDB();

        ContentValues contentValues = new ContentValues();
        contentValues.put(MARKET_PRICE_CROP, marketPrice.getCrop());
        contentValues.put(MARKET_PRICE_TABLE_MARKET, marketPrice.getMarket());
        contentValues.put(MARKET_PRICE_RETAIL, marketPrice.getRetail());
        contentValues.put(MARKET_PRICE_WHOLESALE, marketPrice.getWholesale());
        database.insert(MARKET_PRICE_TABLE_NAME, null, contentValues);

        closeDB();
    }

    public ArrayList<MarketPrice> getAllMarketPrices() {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MARKET_PRICE_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<MarketPrice> marketPriceArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                MarketPrice model = new MarketPrice();

                model.setCrop(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_CROP)));
                model.setMarket(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_TABLE_MARKET)));
                model.setRetail(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_RETAIL)));
                model.setWholesale(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_WHOLESALE)));

                marketPriceArrayList.add(model);

            } while (cursor.moveToNext());
        }

        return marketPriceArrayList;
    }

    public ArrayList<MarketPrice> filterMarketPrices(String crop) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MARKET_PRICE_TABLE_NAME + " WHERE " + MARKET_PRICE_CROP + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{crop});

        ArrayList<MarketPrice> marketPriceArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                MarketPrice model = new MarketPrice();

                model.setCrop(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_CROP)));
                model.setMarket(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_TABLE_MARKET)));
                model.setRetail(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_RETAIL)));
                model.setWholesale(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_WHOLESALE)));

                marketPriceArrayList.add(model);

            } while (cursor.moveToNext());
        }

        return marketPriceArrayList;
    }


    public ArrayList<MarketPriceSubItem> filterMarketPriceSubItem(String crop) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + MARKET_PRICE_TABLE_NAME + " WHERE " + MARKET_PRICE_CROP + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{crop});

        ArrayList<MarketPriceSubItem> marketPriceArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                MarketPriceSubItem model = new MarketPriceSubItem();

                model.setMarket(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_TABLE_MARKET)));
                model.setRetail(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_RETAIL)));
                model.setWholesale(cursor.getString(cursor.getColumnIndex(MARKET_PRICE_WHOLESALE)));

                marketPriceArrayList.add(model);

            } while (cursor.moveToNext());
        }

        return marketPriceArrayList;
    }
    /********CRUD OPERATIONS FOR LIVESTOCK RECORDS***************/

    public BreedingStock getBreedingStock(String id, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? LIVESTOCK_RECORDS_BREEDING_STOCK_GLOBAL_ID : LIVESTOCK_RECORDS_BREEDING_STOCK_ID;

        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME + " where " + key + " = '" + id + "'", null);
        res.moveToFirst();
        BreedingStock breedingStock = null;
        if (!res.isAfterLast()) {
            breedingStock = new BreedingStock();
            breedingStock.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_ID)));
            breedingStock.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID)));
            breedingStock.setName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_NAME)));
            breedingStock.setEarTag(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_EAR_TAG)));
            breedingStock.setColor(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_COLOR)));
            breedingStock.setSex(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SEX)));
            breedingStock.setBreed(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_BREED)));
            breedingStock.setDateOfBirth(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_DATE_OF_BIRTH)));
            breedingStock.setSource(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SOURCE)));
            breedingStock.setWeight(Float.parseFloat(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_WEIGHT))));
            breedingStock.setFather(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_FATHER)));
            breedingStock.setMotherDam(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_MOTHER_DAM)));
            breedingStock.setPhoto(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_PHOTO)));
            breedingStock.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SYNC_STATUS)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return breedingStock;
    }

    public void insertBreedingStock(BreedingStock breedingStock) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID, breedingStock.getUserId());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_NAME, breedingStock.getName());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_EAR_TAG, breedingStock.getEarTag());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_COLOR, breedingStock.getColor());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_SEX, breedingStock.getSex());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_BREED, breedingStock.getBreed());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_DATE_OF_BIRTH, breedingStock.getDateOfBirth());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_SOURCE, breedingStock.getSource());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_WEIGHT, breedingStock.getWeight());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_FATHER, breedingStock.getFather());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_MOTHER_DAM, breedingStock.getMotherDam());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_PHOTO, breedingStock.getPhoto());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_SYNC_STATUS, breedingStock.getSyncStatus());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_GLOBAL_ID, breedingStock.getGlobalId());
        contentValues.put(LIVESTOCK_RECORDS_ANIMAL_TYPE, breedingStock.getAnimalType());
        database.insert(LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME, null, contentValues);
        closeDB();

    }

    public void updateBreedingStock(BreedingStock breedingStock) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID, breedingStock.getUserId());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_NAME, breedingStock.getName());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_EAR_TAG, breedingStock.getEarTag());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_COLOR, breedingStock.getColor());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_SEX, breedingStock.getSex());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_BREED, breedingStock.getBreed());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_DATE_OF_BIRTH, breedingStock.getDateOfBirth());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_SOURCE, breedingStock.getSource());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_WEIGHT, breedingStock.getWeight());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_FATHER, breedingStock.getFather());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_MOTHER_DAM, breedingStock.getMotherDam());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_PHOTO, breedingStock.getPhoto());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_SYNC_STATUS, breedingStock.getSyncStatus());
        contentValues.put(LIVESTOCK_RECORDS_BREEDING_STOCK_GLOBAL_ID, breedingStock.getGlobalId());
        contentValues.put(LIVESTOCK_RECORDS_ANIMAL_TYPE, breedingStock.getAnimalType());
        database.update(LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME, contentValues, LIVESTOCK_RECORDS_BREEDING_STOCK_ID + " = ?", new String[]{breedingStock.getId()});

        closeDB();
    }

    public boolean deleteBreedingStock(String id) {
        BreedingStock breedingStock = getBreedingStock(id, false);
        openDB();
        database.delete(LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME, LIVESTOCK_RECORDS_BREEDING_STOCK_ID + " = ?", new String[]{id});
        closeDB();
        if (breedingStock != null) {
            recordDeletedRecord("breedingStock", breedingStock.getGlobalId());
        }
        return true;
    }

    public ArrayList<BreedingStock> getBreedingStocks(String userId, String animal) {

        openDB();
        ArrayList<BreedingStock> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME + " where " + LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID + " = " + userId + " AND " + LIVESTOCK_RECORDS_ANIMAL_TYPE + " = '" + animal + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            BreedingStock breedingStock = new BreedingStock();
            breedingStock.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_ID)));
            breedingStock.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID)));
            breedingStock.setName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_NAME)));
            breedingStock.setEarTag(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_EAR_TAG)));
            breedingStock.setColor(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_COLOR)));
            breedingStock.setSex(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SEX)));
            breedingStock.setBreed(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_BREED)));
            breedingStock.setDateOfBirth(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_DATE_OF_BIRTH)));
            breedingStock.setSource(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SOURCE)));
            breedingStock.setWeight(Float.parseFloat(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_WEIGHT))));
            breedingStock.setFather(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_FATHER)));
            breedingStock.setMotherDam(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_MOTHER_DAM)));
            breedingStock.setPhoto(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_PHOTO)));
            breedingStock.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SYNC_STATUS)));
            breedingStock.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_GLOBAL_ID)));
            breedingStock.setAnimalType(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_ANIMAL_TYPE)));
            array_list.add(breedingStock);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;

    }

    public ArrayList<BreedingStock> getBreedingStockBySex(String userId, String animal, String sex) {

        openDB();
        ArrayList<BreedingStock> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME + " where " + LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID + " = " + userId + " AND " + LIVESTOCK_RECORDS_ANIMAL_TYPE + " = '" + animal + "' AND " + LIVESTOCK_RECORDS_BREEDING_STOCK_SEX + " = '" + sex + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            BreedingStock breedingStock = new BreedingStock();
            breedingStock.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_ID)));
            breedingStock.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID)));
            breedingStock.setName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_NAME)));
            breedingStock.setEarTag(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_EAR_TAG)));
            breedingStock.setColor(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_COLOR)));
            breedingStock.setSex(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SEX)));
            breedingStock.setBreed(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_BREED)));
            breedingStock.setDateOfBirth(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_DATE_OF_BIRTH)));
            breedingStock.setSource(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SOURCE)));
            breedingStock.setWeight(Float.parseFloat(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_WEIGHT))));
            breedingStock.setFather(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_FATHER)));
            breedingStock.setMotherDam(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_MOTHER_DAM)));
            breedingStock.setPhoto(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_PHOTO)));
            breedingStock.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SYNC_STATUS)));
            breedingStock.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_GLOBAL_ID)));
            breedingStock.setAnimalType(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_ANIMAL_TYPE)));
            array_list.add(breedingStock);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;

    }

    /**************MATING OPERATIONS*****************/

    public Mating getMating(String id, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? LIVESTOCK_RECORDS_MATING_GLOBAL_ID : LIVESTOCK_RECORDS_MATING_ID;

        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_MATING_TABLE_NAME + " where " + key + " = '" + id + "'", null);
        res.moveToFirst();
        Mating mating = null;
        if (!res.isAfterLast()) {
            mating = new Mating();
            mating.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_ID)));
            mating.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_USER_ID)));
            mating.setMatingDate(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_DATE)));
            mating.setMaleName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_MALE_NAME)));
            mating.setFemaleName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_FEMALE_NAME)));
            mating.setMethod(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_METHOD)));
            mating.setGestationPeriod(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_GESTATION_PERIOD))));
            mating.setDeliveryAlertDaysBefore(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_DELIVERY_ALERT_DAYS_BEFORE))));
            mating.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_GLOBAL_ID)));
            mating.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_SYNC_STATUS)));
            mating.setAnimalType(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_ANIMAL_TYPE)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return mating;
    }

    public void insertMating(Mating mating) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIVESTOCK_RECORDS_MATING_USER_ID, mating.getUserId());
        contentValues.put(LIVESTOCK_RECORDS_MATING_DATE, mating.getMatingDate());
        contentValues.put(LIVESTOCK_RECORDS_MATING_MALE_NAME, mating.getMaleName());
        contentValues.put(LIVESTOCK_RECORDS_MATING_FEMALE_NAME, mating.getFemaleName());
        contentValues.put(LIVESTOCK_RECORDS_MATING_METHOD, mating.getMethod());
        contentValues.put(LIVESTOCK_RECORDS_MATING_GESTATION_PERIOD, mating.getGestationPeriod());
        contentValues.put(LIVESTOCK_RECORDS_MATING_DELIVERY_ALERT_DAYS_BEFORE, mating.getDeliveryAlertDaysBefore());
        contentValues.put(LIVESTOCK_RECORDS_MATING_NOTES, mating.getNotes());
        contentValues.put(LIVESTOCK_RECORDS_MATING_SYNC_STATUS, mating.getSyncStatus());
        contentValues.put(LIVESTOCK_RECORDS_MATING_GLOBAL_ID, mating.getGlobalId());
        contentValues.put(LIVESTOCK_RECORDS_ANIMAL_TYPE, mating.getAnimalType());
        database.insert(LIVESTOCK_RECORDS_MATING_TABLE_NAME, null, contentValues);

//        //generate Notifications
//        String id = "";
//        Cursor res = database.rawQuery("select * from " + LIVESTOCK_RECORDS_MATING_TABLE_NAME + " where " + LIVESTOCK_RECORDS_MATING_ID + " = " + mating.getId() + " AND " + LIVESTOCK_RECORDS_MATING_DATE + " = '" + mating.getMatingDate() + "'", null);
//        res.moveToFirst();
//        if (!res.isAfterLast()) {
//            id = res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_ID));
//        }
//        generateNotifications(context.getString(R.string.notification_delivery_alert), id);
        closeDB();

    }

    public void updateMating(Mating mating) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIVESTOCK_RECORDS_MATING_USER_ID, mating.getUserId());
        contentValues.put(LIVESTOCK_RECORDS_MATING_DATE, mating.getMatingDate());
        contentValues.put(LIVESTOCK_RECORDS_MATING_MALE_NAME, mating.getMaleName());
        contentValues.put(LIVESTOCK_RECORDS_MATING_FEMALE_NAME, mating.getFemaleName());
        contentValues.put(LIVESTOCK_RECORDS_MATING_METHOD, mating.getMethod());
        contentValues.put(LIVESTOCK_RECORDS_MATING_GESTATION_PERIOD, mating.getGestationPeriod());
        contentValues.put(LIVESTOCK_RECORDS_MATING_DELIVERY_ALERT_DAYS_BEFORE, mating.getDeliveryAlertDaysBefore());
        contentValues.put(LIVESTOCK_RECORDS_MATING_NOTES, mating.getNotes());
        contentValues.put(LIVESTOCK_RECORDS_MATING_SYNC_STATUS, mating.getSyncStatus());
        contentValues.put(LIVESTOCK_RECORDS_MATING_GLOBAL_ID, mating.getGlobalId());
        contentValues.put(LIVESTOCK_RECORDS_ANIMAL_TYPE, mating.getAnimalType());
        database.update(LIVESTOCK_RECORDS_MATING_TABLE_NAME, contentValues, LIVESTOCK_RECORDS_MATING_ID + " = ?", new String[]{mating.getId()});

        closeDB();
    }

    public boolean deleteMating(String id) {
        Mating mating = getMating(id, false);
        openDB();
        database.delete(LIVESTOCK_RECORDS_MATING_TABLE_NAME, LIVESTOCK_RECORDS_MATING_ID + " = ?", new String[]{id});
        closeDB();
        if (mating != null) {
            recordDeletedRecord("mating", mating.getGlobalId());
        }
        return true;
    }

    public ArrayList<Mating> getMatings(String userId, String animal) {

        openDB();
        ArrayList<Mating> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_MATING_TABLE_NAME + " where " + LIVESTOCK_RECORDS_MATING_USER_ID + " = " + userId + " AND " + LIVESTOCK_RECORDS_ANIMAL_TYPE + " = '" + animal + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Mating mating = new Mating();
            mating.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_ID)));
            mating.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_USER_ID)));
            mating.setMatingDate(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_DATE)));
            mating.setMaleName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_MALE_NAME)));
            mating.setFemaleName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_FEMALE_NAME)));
            mating.setMethod(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_METHOD)));
            mating.setGestationPeriod(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_GESTATION_PERIOD))));
            mating.setDeliveryAlertDaysBefore(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_DELIVERY_ALERT_DAYS_BEFORE))));
            mating.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_GLOBAL_ID)));
            mating.setNotes(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_NOTES)));
            mating.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MATING_SYNC_STATUS)));
            array_list.add(mating);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;

    }

    /************LITTERS OPERATIONS*************************/

    public Litter getLitter(String id, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? LIVESTOCK_RECORDS_LITTERS_GLOBAL_ID : LIVESTOCK_RECORDS_LITTERS_ID;

        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_LITTERS_TABLE_NAME + " where " + key + " = '" + id + "'", null);
        res.moveToFirst();
        Litter litter = null;
        if (!res.isAfterLast()) {
            try {
                litter = new Litter();
            } catch (Exception e) {
                e.printStackTrace();
            }
            litter.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_ID)));
            litter.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_USER_ID)));
            litter.setDateOfBirth(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_DATE_OF_BIRTH)));
            litter.setLitterSize(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_LITTER_SIZE))));
            litter.setBreedingId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BREEDING_ID)));
            litter.setBornAlive(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BORN_ALIVE))));
            litter.setBornDead(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BORN_DEAD))));
            litter.setNoOfMale(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_NO_OF_MALE))));
            litter.setNoOfFemale(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_NO_OF_FEMALE))));
            litter.setWeaning(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_WEANING))));
            litter.setMotherDam(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_MOTHER_DAM)));
            litter.setFatherSire(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_FATHER_SIRE)));
            litter.setSireId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_SIRE_ID)));
            litter.setDamId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_DAM_ID)));
            litter.setWeaningAlert(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_WEANING_ALERT))));
            litter.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_SYNC_STATUS)));
            litter.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return litter;
    }

    public void insertLitter(Litter litter) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_DATE_OF_BIRTH, litter.getDateOfBirth());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_USER_ID, litter.getUserId());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_LITTER_SIZE, litter.getLitterSize());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_BREEDING_ID, litter.getBreedingId());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_BORN_ALIVE, litter.getBornAlive());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_BORN_DEAD, litter.getBornDead());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_NO_OF_MALE, litter.getNoOfMale());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_NO_OF_FEMALE, litter.getNoOfFemale());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_WEANING, litter.getWeaning());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_WEANING_ALERT, litter.getWeaningAlert());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_MOTHER_DAM, litter.getMotherDam());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_FATHER_SIRE, litter.getFatherSire());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_SYNC_STATUS, litter.getSyncStatus());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_SIRE_ID, litter.getSireId());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_DAM_ID, litter.getDamId());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_GLOBAL_ID, litter.getGlobalId());
        contentValues.put(LIVESTOCK_RECORDS_ANIMAL_TYPE, litter.getAnimalType());
        database.insert(LIVESTOCK_RECORDS_LITTERS_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateLitter(Litter litter) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_DATE_OF_BIRTH, litter.getDateOfBirth());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_USER_ID, litter.getUserId());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_LITTER_SIZE, litter.getLitterSize());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_BREEDING_ID, litter.getBreedingId());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_BORN_ALIVE, litter.getBornAlive());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_BORN_DEAD, litter.getBornDead());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_NO_OF_MALE, litter.getNoOfMale());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_NO_OF_FEMALE, litter.getNoOfFemale());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_WEANING, litter.getWeaning());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_WEANING_ALERT, litter.getWeaningAlert());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_MOTHER_DAM, litter.getMotherDam());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_FATHER_SIRE, litter.getFatherSire());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_SIRE_ID, litter.getSireId());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_DAM_ID, litter.getDamId());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_SYNC_STATUS, litter.getSyncStatus());
        contentValues.put(LIVESTOCK_RECORDS_LITTERS_GLOBAL_ID, litter.getGlobalId());
        contentValues.put(LIVESTOCK_RECORDS_ANIMAL_TYPE, litter.getAnimalType());
        database.update(LIVESTOCK_RECORDS_LITTERS_TABLE_NAME, contentValues, LIVESTOCK_RECORDS_LITTERS_ID + " = ?", new String[]{litter.getId()});

        closeDB();
    }

    public boolean deleteLitter(String id) {
        Litter litter = getLitter(id, false);
        openDB();
        database.delete(LIVESTOCK_RECORDS_LITTERS_TABLE_NAME, LIVESTOCK_RECORDS_LITTERS_ID + " = ?", new String[]{id});
        closeDB();
        if (litter != null) {
            recordDeletedRecord("litter", litter.getGlobalId());
        }
        return true;
    }

    //return litters
    public ArrayList<Litter> getLittersInFemaleBreeds(String damId) {
        openDB();
        ArrayList<Litter> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_LITTERS_TABLE_NAME + " where " + LIVESTOCK_RECORDS_LITTERS_DAM_ID + " = '" + damId + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Litter litter = new Litter();
            litter.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_ID)));
            litter.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_USER_ID)));
            litter.setDateOfBirth(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_DATE_OF_BIRTH)));
            litter.setLitterSize(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_LITTER_SIZE))));
            litter.setBreedingId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BREEDING_ID)));
            litter.setBornAlive(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BORN_ALIVE))));
            litter.setBornDead(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BORN_DEAD))));
            litter.setNoOfMale(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_NO_OF_MALE))));
            litter.setNoOfFemale(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_NO_OF_FEMALE))));
            litter.setWeaning(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_WEANING))));
            litter.setMotherDam(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_MOTHER_DAM)));
            litter.setFatherSire(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_FATHER_SIRE)));
            litter.setSireId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_SIRE_ID)));
            litter.setDamId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_DAM_ID)));
            litter.setWeaningAlert(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_WEANING_ALERT))));
            litter.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_SYNC_STATUS)));
            litter.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_GLOBAL_ID)));
            array_list.add(litter);
            res.moveToNext();
        }

        res.close();
        closeDB();
        Log.d("TESTING", array_list.toString());
        return array_list;
    }
    //returns litters in male breeds
    //return litters
    public ArrayList<Litter> getLittersInMaleBreeds(String sireId) {
        openDB();
        ArrayList<Litter> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_LITTERS_TABLE_NAME + " where " + LIVESTOCK_RECORDS_LITTERS_SIRE_ID + " = '" + sireId + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Litter litter = new Litter();
            litter.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_ID)));
            litter.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_USER_ID)));
            litter.setDateOfBirth(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_DATE_OF_BIRTH)));
            litter.setLitterSize(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_LITTER_SIZE))));
            litter.setBreedingId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BREEDING_ID)));
            litter.setBornAlive(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BORN_ALIVE))));
            litter.setBornDead(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BORN_DEAD))));
            litter.setNoOfMale(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_NO_OF_MALE))));
            litter.setNoOfFemale(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_NO_OF_FEMALE))));
            litter.setWeaning(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_WEANING))));
            litter.setMotherDam(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_MOTHER_DAM)));
            litter.setFatherSire(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_FATHER_SIRE)));
            litter.setSireId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_SIRE_ID)));
            litter.setDamId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_DAM_ID)));
            litter.setWeaningAlert(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_WEANING_ALERT))));
            litter.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_SYNC_STATUS)));
            litter.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_GLOBAL_ID)));
            array_list.add(litter);
            res.moveToNext();
        }

        res.close();
        closeDB();
        Log.d("TESTING", array_list.toString());
        return array_list;
    }

    public ArrayList<Litter> getLitters(String userId, String animal) {

        openDB();
        ArrayList<Litter> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_LITTERS_TABLE_NAME + " where " + LIVESTOCK_RECORDS_LITTERS_USER_ID + " = " + userId + " AND " + LIVESTOCK_RECORDS_ANIMAL_TYPE + " = '" + animal + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            Litter litter = new Litter();
            litter.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_ID)));
            litter.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_USER_ID)));
            litter.setDateOfBirth(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_DATE_OF_BIRTH)));
            litter.setLitterSize(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_LITTER_SIZE))));
            litter.setBreedingId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BREEDING_ID)));
            litter.setBornAlive(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BORN_ALIVE))));
            litter.setBornDead(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_BORN_DEAD))));
            litter.setNoOfMale(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_NO_OF_MALE))));
            litter.setNoOfFemale(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_NO_OF_FEMALE))));
            litter.setWeaning(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_WEANING))));
            litter.setMotherDam(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_MOTHER_DAM)));
            litter.setFatherSire(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_FATHER_SIRE)));
            litter.setSireId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_SIRE_ID)));
            litter.setDamId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_DAM_ID)));
            litter.setWeaningAlert(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_WEANING_ALERT))));
            litter.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_SYNC_STATUS)));
            litter.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_LITTERS_GLOBAL_ID)));
            array_list.add(litter);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;

    }

    public ArrayList<BreedingStock> getFemaleBreeds(String userId, String gender) {
        openDB();
        ArrayList<BreedingStock> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME + " where " + LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID + " = " + userId + " AND " + LIVESTOCK_RECORDS_BREEDING_STOCK_SEX + " = '" + gender + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            BreedingStock breedingStock = new BreedingStock();
            breedingStock.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_ID)));
            breedingStock.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID)));
            breedingStock.setName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_NAME)));
            breedingStock.setEarTag(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_EAR_TAG)));
            breedingStock.setColor(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_COLOR)));
            breedingStock.setSex(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SEX)));
            breedingStock.setBreed(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_BREED)));
            breedingStock.setDateOfBirth(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_DATE_OF_BIRTH)));
            breedingStock.setSource(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SOURCE)));
            breedingStock.setWeight(Float.parseFloat(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_WEIGHT))));
            breedingStock.setFather(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_FATHER)));
            breedingStock.setMotherDam(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_MOTHER_DAM)));
            breedingStock.setPhoto(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_PHOTO)));
            breedingStock.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SYNC_STATUS)));
            breedingStock.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_GLOBAL_ID)));
            array_list.add(breedingStock);
            res.moveToNext();
            res.moveToNext();
        }

        res.close();
        closeDB();

        return array_list;
    }

    public ArrayList<BreedingStock> getMaleBreeds(String userId, String gender) {
        openDB();
        ArrayList<BreedingStock> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_BREEDING_STOCK_TABLE_NAME + " where " + LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID + " = " + userId + " AND " + LIVESTOCK_RECORDS_BREEDING_STOCK_SEX + " = '" + gender + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            BreedingStock breedingStock = new BreedingStock();
            breedingStock.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_ID)));
            breedingStock.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_USER_ID)));
            breedingStock.setName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_NAME)));
            breedingStock.setEarTag(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_EAR_TAG)));
            breedingStock.setColor(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_COLOR)));
            breedingStock.setSex(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SEX)));
            breedingStock.setBreed(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_BREED)));
            breedingStock.setDateOfBirth(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_DATE_OF_BIRTH)));
            breedingStock.setSource(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SOURCE)));
            breedingStock.setWeight(Float.parseFloat(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_WEIGHT))));
            breedingStock.setFather(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_FATHER)));
            breedingStock.setMotherDam(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_MOTHER_DAM)));
            breedingStock.setPhoto(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_PHOTO)));
            breedingStock.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_SYNC_STATUS)));
            breedingStock.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_BREEDING_STOCK_GLOBAL_ID)));
            array_list.add(breedingStock);
            res.moveToNext();
            res.moveToNext();
        }

        res.close();
        closeDB();

        return array_list;
    }

    /************MEDICATION OPERATIONS*****************************************/

    public Medication getMedication(String id, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? LIVESTOCK_RECORDS_MEDICATIONS_GLOBAL_ID : LIVESTOCK_RECORDS_MEDICATIONS_ID;

        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_MEDICATIONS_TABLE_NAME + " where " + key + " = '" + id + "'", null);
        res.moveToFirst();
        Medication medication = null;
        if (!res.isAfterLast()) {
            try {
                medication = new Medication();
            } catch (Exception e) {
                e.printStackTrace();
            }
            medication.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_ID)));
            medication.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_USER_ID)));
            medication.setMedicationDate(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_DATE)));
            medication.setMedicationType(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_TYPE)));
            medication.setBreedingId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_BREEDING_ID)));
            medication.setHealthCondition(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_HEALTH_CONDITION)));
            medication.setMedicationsName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_NAME)));
            medication.setManufacturer(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_MANUFACTURER)));
            medication.setDosage(Float.parseFloat(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_DOSAGE))));
            medication.setTreatmentPeriod(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_TREATMENT_PERIOD))));
            medication.setNote(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_NOTES)));
            medication.setAnimal(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_ANIMAL)));
            medication.setTechnicalPersonal(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_TECHNICAL_PERSONAL)));
            medication.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_SYNC_STATUS)));
            medication.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_GLOBAL_ID)));
            medication.setAnimalType(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_ANIMAL_TYPE)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return medication;
    }

    public void insertMedication(Medication medication) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_USER_ID, medication.getUserId());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_DATE, medication.getMedicationDate());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_TYPE, medication.getMedicationType());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_BREEDING_ID, medication.getBreedingId());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_HEALTH_CONDITION, medication.getHealthCondition());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_NAME, medication.getMedicationsName());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_MANUFACTURER, medication.getManufacturer());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_DOSAGE, medication.getDosage());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_TREATMENT_PERIOD, medication.getTreatmentPeriod());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_NOTES, medication.getNote());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_ANIMAL, medication.getAnimal());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_TECHNICAL_PERSONAL, medication.getTechnicalPersonal());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_SYNC_STATUS, medication.getSyncStatus());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_GLOBAL_ID, medication.getGlobalId());
        contentValues.put(LIVESTOCK_RECORDS_ANIMAL_TYPE, medication.getAnimalType());
        Log.w("AnimalMedDb", medication.getAnimal());
        database.insert(LIVESTOCK_RECORDS_MEDICATIONS_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateMedication(Medication medication) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_USER_ID, medication.getUserId());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_DATE, medication.getMedicationDate());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_TYPE, medication.getMedicationType());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_BREEDING_ID, medication.getBreedingId());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_HEALTH_CONDITION, medication.getHealthCondition());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_NAME, medication.getMedicationsName());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_MANUFACTURER, medication.getManufacturer());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_DOSAGE, medication.getDosage());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_TREATMENT_PERIOD, medication.getTreatmentPeriod());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_NOTES, medication.getNote());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_ANIMAL, medication.getAnimal());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_TECHNICAL_PERSONAL, medication.getTechnicalPersonal());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_SYNC_STATUS, medication.getSyncStatus());
        contentValues.put(LIVESTOCK_RECORDS_MEDICATIONS_GLOBAL_ID, medication.getGlobalId());
        contentValues.put(LIVESTOCK_RECORDS_ANIMAL_TYPE, medication.getAnimalType());
        database.update(LIVESTOCK_RECORDS_MEDICATIONS_TABLE_NAME, contentValues, LIVESTOCK_RECORDS_MEDICATIONS_ID + " = ?", new String[]{medication.getId()});

        closeDB();
    }

    public boolean deleteMedication(String id) {
        Medication medication = getMedication(id, false);
        openDB();
        database.delete(LIVESTOCK_RECORDS_MEDICATIONS_TABLE_NAME, LIVESTOCK_RECORDS_MEDICATIONS_ID + " = ?", new String[]{id});
        closeDB();
        if (medication != null) {
            recordDeletedRecord("medication", medication.getGlobalId());
        }
        return true;
    }


    public ArrayList<Medication> getMedications(String userId, String animal) {

        openDB();
        ArrayList<Medication> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + LIVESTOCK_RECORDS_MEDICATIONS_TABLE_NAME + " where " + LIVESTOCK_RECORDS_MEDICATIONS_USER_ID + " = " + userId + " AND " + LIVESTOCK_RECORDS_ANIMAL_TYPE + " = '" + animal + "'", null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
            Medication medication = new Medication();
            medication.setId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_ID)));
            medication.setUserId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_USER_ID)));
            medication.setMedicationDate(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_DATE)));
            medication.setMedicationType(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_TYPE)));
            medication.setBreedingId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_BREEDING_ID)));
            medication.setHealthCondition(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_HEALTH_CONDITION)));
            medication.setMedicationsName(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_MEDICATION_NAME)));
            medication.setManufacturer(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_MANUFACTURER)));
            medication.setDosage(Float.parseFloat(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_DOSAGE))));
            medication.setTreatmentPeriod(Integer.parseInt(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_TREATMENT_PERIOD))));
            medication.setNote(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_NOTES)));
            medication.setAnimal(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_ANIMAL)));
            medication.setTechnicalPersonal(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_TECHNICAL_PERSONAL)));
            medication.setSyncStatus(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_SYNC_STATUS)));
            medication.setGlobalId(res.getString(res.getColumnIndex(LIVESTOCK_RECORDS_MEDICATIONS_GLOBAL_ID)));
            array_list.add(medication);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;

    }

    /*************CROP GALLERY IMPLEMENTATION*******************************/

    public CropGallery getCropGallery(String galleryId, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? CROP_RECORDS_CROP_GALLERY_GLOBAL_ID : CROP_RECORDS_CROP_GALLERY_ID;
        Cursor res = db.rawQuery("select * from " + CROP_RECORDS_CROP_GALLERY_TABLE_NAME + " where " + key + " = ?", new String[]{galleryId});
        res.moveToFirst();
        CropGallery cropGallery = null;
        if (!res.isAfterLast()) {
            cropGallery = new CropGallery();
            cropGallery.setId(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_ID)));
            cropGallery.setParentId(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_PARENT_ID)));
            cropGallery.setUserId(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_USER_ID)));
            cropGallery.setPhoto(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_PHOTO)));
            cropGallery.setCaption(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_CAPTION)));
            cropGallery.setSyncStatus(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_SYNC_STATUS)));
            cropGallery.setGlobalId(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return cropGallery;

    }

    public void insertCropGallery(CropGallery cropGallery) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_RECORDS_CROP_GALLERY_PARENT_ID, cropGallery.getParentId());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_PHOTO, cropGallery.getPhoto());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_CAPTION, cropGallery.getCaption());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_USER_ID, cropGallery.getUserId());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_SYNC_STATUS, cropGallery.getSyncStatus());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_GLOBAL_ID, cropGallery.getGlobalId());
        database.insert(CROP_RECORDS_CROP_GALLERY_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropGallery(CropGallery cropGallery) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_RECORDS_CROP_GALLERY_PARENT_ID, cropGallery.getParentId());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_PHOTO, cropGallery.getPhoto());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_CAPTION, cropGallery.getCaption());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_USER_ID, cropGallery.getUserId());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_SYNC_STATUS, cropGallery.getSyncStatus());
        contentValues.put(CROP_RECORDS_CROP_GALLERY_GLOBAL_ID, cropGallery.getGlobalId());
        database.update(CROP_RECORDS_CROP_GALLERY_TABLE_NAME, contentValues, CROP_RECORDS_CROP_GALLERY_ID + " = ?", new String[]{cropGallery.getId()});

        closeDB();
    }

    public boolean deleteCropGallery(String galleryId) {
        CropGallery cropGallery = getCropGallery(galleryId, false);
        openDB();
        database.delete(CROP_RECORDS_CROP_GALLERY_TABLE_NAME, CROP_RECORDS_CROP_GALLERY_ID + " = ?", new String[]{galleryId});
        closeDB();
        if (cropGallery != null) {
            recordDeletedRecord("cropGallery", cropGallery.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropGallery> getCropgalleries(String parentId, String userId) {
        openDB();
        ArrayList<CropGallery> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_RECORDS_CROP_GALLERY_TABLE_NAME + " where " + CROP_RECORDS_CROP_GALLERY_PARENT_ID + " = " + parentId + " AND " + CROP_RECORDS_CROP_GALLERY_USER_ID + " = '" + userId + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropGallery cropGallery = new CropGallery();
            cropGallery.setId(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_ID)));
            cropGallery.setUserId(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_USER_ID)));
            cropGallery.setParentId(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_PARENT_ID)));
            cropGallery.setPhoto(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_PHOTO)));
            cropGallery.setCaption(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_CAPTION)));
            cropGallery.setSyncStatus(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_SYNC_STATUS)));
            cropGallery.setGlobalId(res.getString(res.getColumnIndex(CROP_RECORDS_CROP_GALLERY_GLOBAL_ID)));
            array_list.add(cropGallery);
            res.moveToNext();
        }

        res.close();
        closeDB();
        Log.d("Gallery ", array_list.size() + "");
        return array_list;
    }


    //******INSERT REGIONS************//
    public void insertRegionDetails(List<RegionDetails> regionDetails) {
        openDB();
        ContentValues contentValues = new ContentValues();
        for(RegionDetails regionDetail : regionDetails) {
            contentValues.put(REGIONS_DETAILS_ID, regionDetail.getId());
            contentValues.put(REGIONS_DETAILS_REGION_TYPE, regionDetail.getRegionType());
            contentValues.put(REGIONS_DETAILS_REGION, regionDetail.getRegion());
            contentValues.put(REGIONS_DETAILS_BELONGS_TO, regionDetail.getBelongs_to());
            database.insert(REGIONS_DETAILS_TABLE_NAME, null, contentValues);
        }
        closeDB();
    }

    //*********GET LATEST ID FROM REGIONS DETAILS*************//
    public int getMaxRegionId() {
          openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        final String getRegionId = "SELECT MAX("+ REGIONS_DETAILS_ID +") FROM " + REGIONS_DETAILS_TABLE_NAME;

        Cursor cur = db.rawQuery(getRegionId, null);
        cur.moveToFirst();

        int regionId = cur.getInt(0);

        // close cursor and DB
        cur.close();
        closeDB();

        return regionId;

    }

    //******GET DISTRICTS*****//

    public ArrayList<RegionDetails> getRegionDetails( String district) throws JSONException {
        openDB();
        ArrayList<RegionDetails> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + REGIONS_DETAILS_TABLE_NAME + " where "  + REGIONS_DETAILS_REGION_TYPE + " = '" + district + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            RegionDetails regionDetails = new RegionDetails();
            regionDetails.setTableId(Integer.parseInt(res.getString(res.getColumnIndex(REGIONS_DETAILS_TABLE_ID))));
            regionDetails.setId(Integer.parseInt(res.getString(res.getColumnIndex(REGIONS_DETAILS_ID))));
            regionDetails.setRegionType(res.getString(res.getColumnIndex(REGIONS_DETAILS_REGION_TYPE)));
            regionDetails.setRegion(res.getString(res.getColumnIndex(REGIONS_DETAILS_REGION)));
            regionDetails.setBelongs_to(res.getString(res.getColumnIndex(REGIONS_DETAILS_BELONGS_TO)));
            array_list.add(regionDetails);
            res.moveToNext();
        }

        res.close();
        closeDB();
        Log.d("RegionDetails ", array_list.size() + "");
        return array_list;
    }
    //******GET DISTRICT ID****//
    public int getDistrictId( String region) throws JSONException {
        openDB();
       int regionId = 0;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + REGIONS_DETAILS_ID +" from " + REGIONS_DETAILS_TABLE_NAME + " where "  + REGIONS_DETAILS_REGION + " = '" + region + "'", null);
        res.moveToFirst();


        while(res.moveToNext())
        {
            if(res.isFirst())
            {
                //Your code goes here in your case
                return res.getInt(res.getColumnIndex(REGIONS_DETAILS_ID));
            }
        }




        res.close();
        closeDB();
        Log.d("RegionDetails ", String.valueOf(regionId));
        return regionId;
    }

    //******GET SUB COUNTIES**********//
    public ArrayList<RegionDetails> getSubcountyDetails(String belongs_to, String subcounty) throws JSONException {
        openDB();
        ArrayList<RegionDetails> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + REGIONS_DETAILS_TABLE_NAME + " where " + REGIONS_DETAILS_BELONGS_TO + " = '" + belongs_to + "'" + " AND " + REGIONS_DETAILS_REGION_TYPE + " = '" + subcounty + "'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            RegionDetails regionDetails = new RegionDetails();
            regionDetails.setTableId(Integer.parseInt(res.getString(res.getColumnIndex(REGIONS_DETAILS_TABLE_ID))));
            regionDetails.setId(Integer.parseInt(res.getString(res.getColumnIndex(REGIONS_DETAILS_ID))));
            regionDetails.setRegionType(res.getString(res.getColumnIndex(REGIONS_DETAILS_REGION_TYPE)));
            regionDetails.setRegion(res.getString(res.getColumnIndex(REGIONS_DETAILS_REGION)));
            regionDetails.setBelongs_to(res.getString(res.getColumnIndex(REGIONS_DETAILS_BELONGS_TO)));
            array_list.add(regionDetails);
            res.moveToNext();
        }

        res.close();
        closeDB();
        Log.d("RegionDetails ", array_list.size() + "");
        return array_list;
    }

    //*********GET VILLAGES*************//
    public ArrayList<RegionDetails> getVillageDetails(String belongs_to, String subcounty) throws JSONException {
        openDB();
        ArrayList<RegionDetails> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + REGIONS_DETAILS_TABLE_NAME + " where " + REGIONS_DETAILS_BELONGS_TO + " = '" + belongs_to + "'" + " AND " + REGIONS_DETAILS_REGION_TYPE + " = '" + subcounty + "'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            RegionDetails regionDetails = new RegionDetails();
            regionDetails.setTableId(Integer.parseInt(res.getString(res.getColumnIndex(REGIONS_DETAILS_TABLE_ID))));
            regionDetails.setId(Integer.parseInt(res.getString(res.getColumnIndex(REGIONS_DETAILS_ID))));
            regionDetails.setRegionType(res.getString(res.getColumnIndex(REGIONS_DETAILS_REGION_TYPE)));
            regionDetails.setRegion(res.getString(res.getColumnIndex(REGIONS_DETAILS_REGION)));
            regionDetails.setBelongs_to(res.getString(res.getColumnIndex(REGIONS_DETAILS_BELONGS_TO)));
            array_list.add(regionDetails);
            res.moveToNext();
        }

        res.close();
        closeDB();
        Log.d("VillageDetails ", array_list.size() + "");
        return array_list;
    }
}


