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
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.CropBill;
import com.myfarmnow.myfarmcrop.models.CropContact;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropEstimate;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;

import com.myfarmnow.myfarmcrop.models.farmrecords.CropField;

import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.models.CropInvoicePayment;
import com.myfarmnow.myfarmcrop.models.CropIrrigation;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropMachine;
import com.myfarmnow.myfarmcrop.models.CropMachineService;
import com.myfarmnow.myfarmcrop.models.CropMachineTask;
import com.myfarmnow.myfarmcrop.models.CropNote;
import com.myfarmnow.myfarmcrop.models.CropNotification;
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
import com.myfarmnow.myfarmcrop.models.CropYieldRecord;
import com.myfarmnow.myfarmcrop.models.DeletedRecord;
import com.myfarmnow.myfarmcrop.models.GraphRecord;
import com.myfarmnow.myfarmcrop.models.marketplace.MarketPrice;
import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;
import com.myfarmnow.myfarmcrop.singletons.CropDatabaseInitializerSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MyFarmDbHandlerSingleton extends SQLiteOpenHelper {
    private static final String TAG = "MyFarmDbHandler";

    public static final String DATABASE_NAME = "myFarm.db";
    private static int database_version = 1;
    private static int database_version_2 = 2;

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

    private static MyFarmDbHandlerSingleton myFarmDbHandlerSingleton;
    SQLiteDatabase database;
    Context context;

    private MyFarmDbHandlerSingleton(Context context) {

        super(context, DATABASE_NAME, null, database_version_2);
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
                CROP_FERTILIZER_APPLICATION_METHOD + " TEXT NOT NULL," + CROP_FERTILIZER_APPLICATION_REASON + " TEXT, " + CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM + " TEXT NOT NULL, " + CROP_FERTILIZER_APPLICATION_FERTILIZER_ID + " TEXT NOT NULL," +
                CROP_FERTILIZER_APPLICATION_RATE + " REAL NOT NULL ," + CROP_FERTILIZER_APPLICATION_COST + " REAL, " + CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL + " TEXT, " + CROP_FERTILIZER_APPLICATION_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_FERTILIZER_APPLICATION_RECURRENCE + " TEXT NOT NULL, " + CROP_FERTILIZER_APPLICATION_FREQUENCY + " REAL DEFAULT 1, " + CROP_FERTILIZER_APPLICATION_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";


        String crop_spraying_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_SPRAYING_TABLE_NAME + " ( " + CROP_SPRAYING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                CROP_SPRAYING_USER_ID + " TEXT," + CROP_SPRAYING_CROP_ID + " TEXT NOT NULL," + CROP_SPRAYING_DATE + " TEXT NOT NULL," + CROP_SPRAYING_START_TIME + " TEXT," +
                CROP_SPRAYING_END_TIME + " TEXT," + CROP_SPRAYING_OPERATOR + " TEXT NOT NULL," +
                CROP_SPRAYING_WATER_VOLUME + " REAL ," + CROP_SPRAYING_WATER_CONDITION + " TEXT," + CROP_SPRAYING_WIND_DIRECTION + " TEXT, " + CROP_SPRAYING_EQUIPMENT_USED + " TEXT ," +
                CROP_SPRAYING_SPRAY_ID + " TEXT NOT NULL," + CROP_SPRAYING_RATE + " REAL NOT NULL ," + CROP_SPRAYING_TREATMENT_REASON + " TEXT ," + CROP_SPRAYING_COST + " REAL, " + CROP_SPRAYING_REPEAT_UNTIL + " TEXT, " + CROP_SPRAYING_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_SPRAYING_RECURRENCE + " TEXT NOT NULL, " + CROP_SPRAYING_FREQUENCY + " REAL DEFAULT 1, " + CROP_SPRAYING_REMINDERS + " TEXT NOT NULL, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

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
                + CROP_INCOME_EXPENSE_PAYMENT_MODE + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_PAYMENT_STATUS + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_SELLING_PRICE + " REAL , " + CROP_INCOME_EXPENSE_CUSTOMER_SUPPLIER + " TEXT NOT NULL, " + CROP_INCOME_EXPENSE_CROP_ID + " TEXT, " + CROP_GLOBAL_ID + " TEXT DEFAULT NULL UNIQUE ," + CROP_SYNC_STATUS + " TEXT DEFAULT 'no' " + " ) ";

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

       /* Log.d("FERTILIZER INVENTORY",crop_inventory_fertilizer_insert_query);
        Log.d("SEEDS INVENTORY",crop_seeds_insert_query);
        Log.d("SPRAY INVENTORY",crop_inventory_spray_insert_query);
        Log.d("SPRAY INVENTORY",crop_spraying_insert_query);
        Log.d("CROP",crop_insert_query);
        Log.d("CULTIVATE",crop_cultivate_insert_query);
        Log.d("FERTILIZER",crop_fertilizer_insert_query);
        Log.d("FIELDS",crop_field_insert_query);
        Log.d("MACHINE",crop_machine_insert_query);
        Log.d("CROP PURCHASE ORDER",crop_purchase_order_insert_query);*/
        //db.execSQL("DROP TABLE IF EXISTS "+ CROP_PAYMENT_BILL_TABLE_NAME);
        // db.execSQL("DROP TABLE IF EXISTS "+ CROP_MACHINE_SERVICE_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS "+ CROP_FIELDS_TABLE_NAME);


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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        if (oldVersion == 1 && newVersion == 2) {
            upGradingTablesFromVersion1ToVersion2(db);

        }
        onCreate(db);

    }

    public void upGradingTablesFromVersion1ToVersion2(SQLiteDatabase db) {

        database = db;

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
                notification.setUserId(DashboardActivity.PREFERENCES_USER_ID);

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
        res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME + " where " + CROP_INCOME_EXPENSE_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') AND " + CROP_INCOME_EXPENSE_TRANSACTION + " = 'Expense'", null);
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
        res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME + " where " + CROP_INCOME_EXPENSE_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') AND " + CROP_INCOME_EXPENSE_TRANSACTION + " = 'Income'", null);
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
        res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_HARVEST_DATE + " BETWEEN date('" + startDate + "') AND date('" + endDate + "') ", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    "Crop Harvest",
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD)) * res.getFloat(res.getColumnIndex(CROP_HARVEST_PRICE)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return expensesList;

    }

    public void insertCropMachineService(CropMachineService service) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_MACHINE_SERVICE_MACHINE_ID, service.getMachineId());
        contentValues.put(CROP_MACHINE_SERVICE_DATE, service.getDate());
        contentValues.put(CROP_MACHINE_SERVICE_PERSONNEL, service.getEmployeeName());
        contentValues.put(CROP_MACHINE_SERVICE_TYPE, service.getType());
        contentValues.put(CROP_MACHINE_SERVICE_DESCRIPTION, service.getDescription());
        contentValues.put(CROP_MACHINE_SERVICE_RECURRENCE, service.getRecurrence());
        contentValues.put(CROP_MACHINE_SERVICE_REMINDERS, service.getReminders());
        contentValues.put(CROP_MACHINE_SERVICE_FREQUENCY, service.getFrequency());
        contentValues.put(CROP_MACHINE_SERVICE_COST, service.getCost());
        contentValues.put(CROP_MACHINE_SERVICE_CURRENT_HOURS, service.getCurrentHours());
        contentValues.put(CROP_MACHINE_SERVICE_REPEAT_UNTIL, service.getRepeatUntil());
        contentValues.put(CROP_MACHINE_SERVICE_DAYS_BEFORE, service.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, service.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, service.getGlobalId());
        database.insert(CROP_MACHINE_SERVICE_TABLE_NAME, null, contentValues);
        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_MACHINE_SERVICE_TABLE_NAME + " where " + CROP_MACHINE_SERVICE_MACHINE_ID + " = " + service.getMachineId() + " AND " + CROP_MACHINE_SERVICE_DATE + " = '" + service.getDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_ID));
        }
        generateNotifications(context.getString(R.string.notification_type_service), id);
        res.close();
        closeDB();
    }

    public void updateCropMachineService(CropMachineService service) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_MACHINE_SERVICE_MACHINE_ID, service.getMachineId());
        contentValues.put(CROP_MACHINE_SERVICE_DATE, service.getDate());
        contentValues.put(CROP_MACHINE_SERVICE_PERSONNEL, service.getEmployeeName());
        contentValues.put(CROP_MACHINE_SERVICE_CURRENT_HOURS, service.getCurrentHours());
        contentValues.put(CROP_MACHINE_SERVICE_TYPE, service.getType());
        contentValues.put(CROP_MACHINE_SERVICE_DESCRIPTION, service.getDescription());
        contentValues.put(CROP_MACHINE_SERVICE_RECURRENCE, service.getRecurrence());
        contentValues.put(CROP_MACHINE_SERVICE_REMINDERS, service.getReminders());
        contentValues.put(CROP_MACHINE_SERVICE_FREQUENCY, service.getFrequency());
        contentValues.put(CROP_MACHINE_SERVICE_COST, service.getCost());
        contentValues.put(CROP_MACHINE_SERVICE_REPEAT_UNTIL, service.getRepeatUntil());
        contentValues.put(CROP_MACHINE_SERVICE_DAYS_BEFORE, service.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, service.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, service.getGlobalId());
        database.update(CROP_MACHINE_SERVICE_TABLE_NAME, contentValues, CROP_MACHINE_SERVICE_ID + " = ?", new String[]{service.getId()});

        deleteCropNotification(service.getId(), context.getString(R.string.notification_type_service));
        generateNotifications(context.getString(R.string.notification_type_service), service.getId());

        closeDB();
    }

    public boolean deleteCropMachineService(String serviceId) {
        CropMachineService service = getCropMachineService(serviceId, false);
        openDB();
        deleteCropNotification(serviceId, context.getString(R.string.notification_type_service));
        database.delete(CROP_MACHINE_SERVICE_TABLE_NAME, CROP_MACHINE_SERVICE_ID + " = ?", new String[]{serviceId});
        closeDB();
        if (service != null) {
            recordDeletedRecord("machineService", service.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropMachineService> getCropMachineServices(String machineId) {
        openDB();
        ArrayList<CropMachineService> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_MACHINE_SERVICE_TABLE_NAME + ".*," + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_NAME +
                " from " + CROP_MACHINE_SERVICE_TABLE_NAME +
                " LEFT JOIN " + CROP_MACHINE_TABLE_NAME + " ON " + CROP_MACHINE_SERVICE_TABLE_NAME + "." + CROP_MACHINE_SERVICE_MACHINE_ID + " = " + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_ID +
                " where " + CROP_MACHINE_SERVICE_TABLE_NAME + "." + CROP_MACHINE_SERVICE_MACHINE_ID + " = " + machineId + " ORDER BY date(" + CROP_MACHINE_SERVICE_TABLE_NAME + "." + CROP_MACHINE_SERVICE_DATE + ") DESC", null);

        res.moveToFirst();

        while (!res.isAfterLast()) {

            CropMachineService service = new CropMachineService();
            service.setId(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_ID)));
            service.setMachineId(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_MACHINE_ID)));
            service.setEmployeeName(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_PERSONNEL)));
            service.setCurrentHours(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_CURRENT_HOURS)));
            service.setServiceType(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_TYPE)));
            service.setType(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_TYPE)));
            service.setDate(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DATE)));
            service.setDescription(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DESCRIPTION)));
            service.setRecurrence(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_RECURRENCE)));
            service.setReminders(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_REMINDERS)));
            service.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_DAYS_BEFORE)));
            service.setCost(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_COST)));
            service.setFrequency(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_FREQUENCY)));
            service.setRepeatUntil(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_REPEAT_UNTIL)));
            service.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(service);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;
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

    public void insertCropMachineTask(CropMachineTask task) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_MACHINE_TASK_MACHINE_ID, task.getMachineId());
        contentValues.put(CROP_MACHINE_TASK_START_DATE, task.getStartDate());
        contentValues.put(CROP_MACHINE_TASK_END_DATE, task.getEndDate());
        contentValues.put(CROP_MACHINE_TASK_PERSONNEL, task.getEmployeeName());
        contentValues.put(CROP_MACHINE_TASK_TITLE, task.getTitle());
        contentValues.put(CROP_MACHINE_TASK_STATUS, task.getStatus());
        contentValues.put(CROP_MACHINE_TASK_DESCRIPTION, task.getDescription());
        contentValues.put(CROP_MACHINE_TASK_RECURRENCE, task.getRecurrence());
        contentValues.put(CROP_MACHINE_TASK_REMINDERS, task.getReminders());
        contentValues.put(CROP_MACHINE_TASK_FREQUENCY, task.getFrequency());
        contentValues.put(CROP_MACHINE_TASK_COST, task.getCost());
        contentValues.put(CROP_MACHINE_TASK_REPEAT_UNTIL, task.getRepeatUntil());
        contentValues.put(CROP_MACHINE_TASK_DAYS_BEFORE, task.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, task.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, task.getGlobalId());
        database.insert(CROP_MACHINE_TASK_TABLE_NAME, null, contentValues);

        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_MACHINE_TASK_TABLE_NAME + " where " + CROP_MACHINE_TASK_MACHINE_ID + " = " + task.getMachineId() + " AND " + CROP_MACHINE_TASK_START_DATE + " = '" + task.getStartDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_MACHINE_TASK_ID));
        }
        generateNotifications(context.getString(R.string.notification_type_machine_task), id);

        res.close();
        closeDB();
    }

    public void updateCropMachineTask(CropMachineTask task) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_MACHINE_TASK_MACHINE_ID, task.getMachineId());
        contentValues.put(CROP_MACHINE_TASK_START_DATE, task.getStartDate());
        contentValues.put(CROP_MACHINE_TASK_END_DATE, task.getEndDate());
        contentValues.put(CROP_MACHINE_TASK_PERSONNEL, task.getEmployeeName());
        contentValues.put(CROP_MACHINE_TASK_TITLE, task.getTitle());
        contentValues.put(CROP_MACHINE_TASK_STATUS, task.getStatus());
        contentValues.put(CROP_MACHINE_TASK_DESCRIPTION, task.getDescription());
        contentValues.put(CROP_MACHINE_TASK_RECURRENCE, task.getRecurrence());
        contentValues.put(CROP_MACHINE_TASK_REMINDERS, task.getReminders());
        contentValues.put(CROP_MACHINE_TASK_FREQUENCY, task.getFrequency());
        contentValues.put(CROP_MACHINE_TASK_COST, task.getCost());
        contentValues.put(CROP_MACHINE_TASK_REPEAT_UNTIL, task.getRepeatUntil());
        contentValues.put(CROP_MACHINE_TASK_DAYS_BEFORE, task.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, task.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, task.getGlobalId());
        database.update(CROP_MACHINE_TASK_TABLE_NAME, contentValues, CROP_MACHINE_TASK_ID + " = ?", new String[]{task.getId()});

        deleteCropNotification(task.getId(), context.getString(R.string.notification_type_machine_task));
        generateNotifications(context.getString(R.string.notification_type_machine_task), task.getId());

        closeDB();
    }

    public boolean deleteCropMachineTask(String taskId) {
        CropMachineTask task = getCropMachineTask(taskId, false);
        openDB();
        deleteCropNotification(taskId, context.getString(R.string.notification_type_machine_task));
        database.delete(CROP_MACHINE_TASK_TABLE_NAME, CROP_MACHINE_TASK_ID + " = ?", new String[]{taskId});
        closeDB();
        if (task != null) {
            recordDeletedRecord("machineTask", task.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropMachineTask> getCropMachineTasks(String machineId) {
        openDB();
        ArrayList<CropMachineTask> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_MACHINE_TASK_TABLE_NAME + ".*," + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_NAME +
                " from " + CROP_MACHINE_TASK_TABLE_NAME +
                " LEFT JOIN " + CROP_MACHINE_TABLE_NAME + " ON " + CROP_MACHINE_TASK_TABLE_NAME + "." + CROP_MACHINE_TASK_MACHINE_ID + " = " + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_ID +
                " where " + CROP_MACHINE_TASK_TABLE_NAME + "." + CROP_MACHINE_TASK_MACHINE_ID + " = " + machineId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {

            CropMachineTask task = new CropMachineTask();
            task.setId(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_ID)));
            //   task.setUserId(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_USER_ID)));
            task.setMachineId(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_MACHINE_ID)));
            task.setCropName(res.getString(res.getColumnIndex(CROP_MACHINE_NAME)));
            task.setEmployeeName(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_PERSONNEL)));
            task.setEndDate(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_START_DATE)));
            task.setStartDate(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_END_DATE)));
            task.setTitle(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_TITLE)));
            task.setFrequency(res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_FREQUENCY)));
            task.setStatus(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_STATUS)));
            task.setDescription(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_DESCRIPTION)));
            task.setRecurrence(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_RECURRENCE)));
            task.setReminders(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_REMINDERS)));
            task.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_DAYS_BEFORE)));
            task.setCost(res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_COST)));
            task.setRepeatUntil(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_REPEAT_UNTIL)));
            task.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(task);
            res.moveToNext();
        }

        res.close();

        closeDB();

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

    public CropSalesOrder insertCropSalesOrder(CropSalesOrder salesOrder) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SALES_ORDER_USER_ID, salesOrder.getUserId());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_ID, salesOrder.getCustomerId());
        contentValues.put(CROP_SALES_ORDER_NO, salesOrder.getNumber());
        contentValues.put(CROP_SALES_ORDER_DATE, salesOrder.getDate());
        contentValues.put(CROP_SALES_ORDER_STATUS, salesOrder.getStatus());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_METHOD, salesOrder.getMethod());
        contentValues.put(CROP_SALES_ORDER_REFERENCE_NO, salesOrder.getReferenceNumber());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_DATE, salesOrder.getShippingDate());
        contentValues.put(CROP_SALES_ORDER_STATUS, salesOrder.getStatus());
        contentValues.put(CROP_SALES_ORDER_DISCOUNT, salesOrder.getDiscount());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_CHARGES, salesOrder.getShippingCharges());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_NOTES, salesOrder.getCustomerNotes());
        contentValues.put(CROP_SYNC_STATUS, salesOrder.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, salesOrder.getGlobalId());
        contentValues.put(CROP_SALES_ORDER_TERMS_AND_CONDITIONS, salesOrder.getTermsAndConditions());

        database.insert(CROP_SALES_ORDER_TABLE_NAME, null, contentValues);

        Cursor res = database.rawQuery("select " + CROP_SALES_ORDER_ID + " from " + CROP_SALES_ORDER_TABLE_NAME + " where " + CROP_SALES_ORDER_CUSTOMER_ID + " = '" + salesOrder.getCustomerId() + "' AND " + CROP_SALES_ORDER_NO + " = '" + salesOrder.getNumber() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            String estimateId = res.getString(res.getColumnIndex(CROP_SALES_ORDER_ID));

            ArrayList<CropProductItem> items = salesOrder.getItems();
            for (CropProductItem x : items) {
                x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_SALES_ORDER);
                x.setParentObjectId(estimateId);
                insertCropProductItem(x);
            }

        }
        res.close();
        closeDB();
        return getCropSalesOrder(salesOrder.getNumber());
    }

    public CropSalesOrder updateCropSalesOrder(CropSalesOrder salesOrder) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SALES_ORDER_USER_ID, salesOrder.getUserId());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_ID, salesOrder.getCustomerId());
        contentValues.put(CROP_SALES_ORDER_NO, salesOrder.getNumber());
        contentValues.put(CROP_SALES_ORDER_DATE, salesOrder.getDate());
        contentValues.put(CROP_SALES_ORDER_STATUS, salesOrder.getStatus());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_METHOD, salesOrder.getMethod());
        contentValues.put(CROP_SALES_ORDER_STATUS, salesOrder.getStatus());
        contentValues.put(CROP_SALES_ORDER_REFERENCE_NO, salesOrder.getReferenceNumber());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_DATE, salesOrder.getShippingDate());
        contentValues.put(CROP_SALES_ORDER_DISCOUNT, salesOrder.getDiscount());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_CHARGES, salesOrder.getShippingCharges());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_NOTES, salesOrder.getCustomerNotes());
        contentValues.put(CROP_SALES_ORDER_TERMS_AND_CONDITIONS, salesOrder.getTermsAndConditions());
        contentValues.put(CROP_SYNC_STATUS, salesOrder.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, salesOrder.getGlobalId());
        database.update(CROP_SALES_ORDER_TABLE_NAME, contentValues, CROP_SALES_ORDER_ID + " = ?", new String[]{salesOrder.getId()});

        String estimateId = salesOrder.getId();

        ArrayList<CropProductItem> items = salesOrder.getItems();

        for (CropProductItem x : items) {
            x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_SALES_ORDER);
            x.setParentObjectId(estimateId);
            if (x.getId() != null) {
                updateCropProductItem(x);
            } else {
                insertCropProductItem(x);
            }
        }
        closeDB();
        deleteCropProductItems(salesOrder.getDeletedItemsIds());
        return getCropSalesOrder(salesOrder.getNumber());
    }

    public boolean deleteCropSalesOrder(String id) {
        CropSalesOrder cropSalesOrder = getCropSalesOrderById(id, false);
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_ID + " = ? AND " + CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id, CROP_PRODUCT_ITEM_TYPE_SALES_ORDER});
        database.delete(CROP_SALES_ORDER_TABLE_NAME, CROP_SALES_ORDER_ID + " = ?", new String[]{id});
        closeDB();
        if (cropSalesOrder != null) {
            recordDeletedRecord("salesOrder", cropSalesOrder.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropSalesOrder> getCropSalesOrders(String userId) {
        openDB();
        ArrayList<CropSalesOrder> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_SALES_ORDER_TABLE_NAME + ".*," + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_NAME + " from " + CROP_SALES_ORDER_TABLE_NAME + " LEFT JOIN " + CROP_CUSTOMER_TABLE_NAME + " ON " + CROP_SALES_ORDER_TABLE_NAME + "." + CROP_SALES_ORDER_CUSTOMER_ID + " = " + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_ID + " where " + CROP_SALES_ORDER_TABLE_NAME + "." + CROP_SALES_ORDER_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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
            cropSalesOrder.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropSalesOrder);
            res.moveToNext();
        }


        for (CropSalesOrder cropSalesOrder : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + cropSalesOrder.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_SALES_ORDER + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            cropSalesOrder.setItems(items_list);
        }

        res.close();
        closeDB();
        return array_list;
    }

    public CropSalesOrder getCropSalesOrder(String salesOrderNumber) {
        openDB();
        CropSalesOrder cropSalesOrder = null;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_SALES_ORDER_TABLE_NAME + ".*," + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_NAME + " from " + CROP_SALES_ORDER_TABLE_NAME + " LEFT JOIN " + CROP_CUSTOMER_TABLE_NAME + " ON " + CROP_SALES_ORDER_TABLE_NAME + "." + CROP_SALES_ORDER_CUSTOMER_ID + " = " + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_ID + " where " + CROP_SALES_ORDER_TABLE_NAME + "." + CROP_SALES_ORDER_NO + " = '" + salesOrderNumber + "'", null);
        res.moveToFirst();

        if (!res.isAfterLast()) {
            cropSalesOrder = new CropSalesOrder();
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
            cropSalesOrder.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }


        if (cropSalesOrder != null) {
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + cropSalesOrder.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_SALES_ORDER + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            cropSalesOrder.setItems(items_list);
        }

        res.close();
        closeDB();
        return cropSalesOrder;
    }

    public void insertCropPayment(CropInvoicePayment cropInvoicePayment) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PAYMENT_USER_ID, cropInvoicePayment.getUserId());
        contentValues.put(CROP_PAYMENT_AMOUNT, cropInvoicePayment.getAmount());
        contentValues.put(CROP_PAYMENT_DATE, cropInvoicePayment.getDate());
        contentValues.put(CROP_PAYMENT_MODE, cropInvoicePayment.getMode());
        contentValues.put(CROP_PAYMENT_NUMBER, cropInvoicePayment.getPaymentNumber());
        contentValues.put(CROP_PAYMENT_NOTES, cropInvoicePayment.getNotes());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID, cropInvoicePayment.getCustomerId());
        contentValues.put(CROP_PAYMENT_REFERENCE_NO, cropInvoicePayment.getReferenceNo());
        contentValues.put(CROP_PAYMENT_INVOICE_ID, cropInvoicePayment.getInvoiceId());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID, cropInvoicePayment.getCustomerId());
        contentValues.put(CROP_SYNC_STATUS, cropInvoicePayment.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, cropInvoicePayment.getGlobalId());
        database.insert(CROP_PAYMENT_TABLE_NAME, null, contentValues);

        closeDB();
    }

    public void updateCropPayment(CropInvoicePayment cropInvoicePayment) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_PAYMENT_USER_ID, cropInvoicePayment.getUserId());
        contentValues.put(CROP_PAYMENT_AMOUNT, cropInvoicePayment.getAmount());
        contentValues.put(CROP_PAYMENT_DATE, cropInvoicePayment.getDate());
        contentValues.put(CROP_PAYMENT_MODE, cropInvoicePayment.getMode());
        contentValues.put(CROP_PAYMENT_NUMBER, cropInvoicePayment.getPaymentNumber());
        contentValues.put(CROP_PAYMENT_NOTES, cropInvoicePayment.getNotes());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID, cropInvoicePayment.getCustomerId());
        contentValues.put(CROP_PAYMENT_REFERENCE_NO, cropInvoicePayment.getReferenceNo());
        contentValues.put(CROP_PAYMENT_INVOICE_ID, cropInvoicePayment.getInvoiceId());
        contentValues.put(CROP_PAYMENT_CUSTOMER_ID, cropInvoicePayment.getCustomerId());
        contentValues.put(CROP_PAYMENT_DATE, cropInvoicePayment.getDate());
        contentValues.put(CROP_SYNC_STATUS, cropInvoicePayment.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, cropInvoicePayment.getGlobalId());
        database.update(CROP_PAYMENT_TABLE_NAME, contentValues, CROP_PAYMENT_ID + " = ?", new String[]{cropInvoicePayment.getId()});


        closeDB();

    }

    public boolean deleteCropPayment(String id) {
        CropInvoicePayment cropInvoicePayment = getCropPayment(id, false);
        openDB();
        database.delete(CROP_PAYMENT_TABLE_NAME, CROP_PAYMENT_ID + " = ?", new String[]{id});
        closeDB();
        if (cropInvoicePayment != null) {
            recordDeletedRecord("invoicePayment", cropInvoicePayment.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropInvoicePayment> getCropPaymentsByInvoice(String invoiceId) {
        openDB();
        ArrayList<CropInvoicePayment> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_PAYMENT_TABLE_NAME + ".*," + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_NAME + ", " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_NO + " from " + CROP_PAYMENT_TABLE_NAME + " LEFT JOIN " + CROP_CUSTOMER_TABLE_NAME + " ON " + CROP_PAYMENT_TABLE_NAME + "." + CROP_PAYMENT_CUSTOMER_ID + " = " + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_ID +
                " JOIN " + CROP_INVOICE_TABLE_NAME + " ON " + CROP_PAYMENT_TABLE_NAME + "." + CROP_PAYMENT_INVOICE_ID + " = " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_ID +
                " where " + CROP_PAYMENT_TABLE_NAME + "." + CROP_PAYMENT_INVOICE_ID + " = " + invoiceId, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
            CropInvoicePayment cropInvoicePayment = new CropInvoicePayment();
            cropInvoicePayment.setId(res.getString(res.getColumnIndex(CROP_PAYMENT_ID)));
            cropInvoicePayment.setUserId(res.getString(res.getColumnIndex(CROP_PAYMENT_USER_ID)));
            cropInvoicePayment.setAmount(res.getFloat(res.getColumnIndex(CROP_PAYMENT_AMOUNT)));
            cropInvoicePayment.setMode(res.getString(res.getColumnIndex(CROP_PAYMENT_MODE)));
            cropInvoicePayment.setDate(res.getString(res.getColumnIndex(CROP_PAYMENT_DATE)));
            cropInvoicePayment.setReferenceNo(res.getString(res.getColumnIndex(CROP_PAYMENT_REFERENCE_NO)));
            cropInvoicePayment.setPaymentNumber(res.getString(res.getColumnIndex(CROP_PAYMENT_NUMBER)));
            cropInvoicePayment.setNotes(res.getString(res.getColumnIndex(CROP_PAYMENT_NOTES)));
            cropInvoicePayment.setCustomerId(res.getString(res.getColumnIndex(CROP_PAYMENT_CUSTOMER_ID)));
            cropInvoicePayment.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropInvoicePayment.setInvoiceId(res.getString(res.getColumnIndex(CROP_PAYMENT_INVOICE_ID)));
            cropInvoicePayment.setInvoiceNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            cropInvoicePayment.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropInvoicePayment);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;

    }

    public ArrayList<CropInvoicePayment> getCropPayments(String fieldId) {

        openDB();
        ArrayList<CropInvoicePayment> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select " + CROP_PAYMENT_TABLE_NAME + ".*," + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_NAME + ", " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_NO + " from " + CROP_PAYMENT_TABLE_NAME + " LEFT JOIN " + CROP_CUSTOMER_TABLE_NAME + " ON " + CROP_PAYMENT_TABLE_NAME + "." + CROP_PAYMENT_CUSTOMER_ID + " = " + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_ID +
                " LEFT JOIN " + CROP_INVOICE_TABLE_NAME + " ON " + CROP_PAYMENT_TABLE_NAME + "." + CROP_PAYMENT_INVOICE_ID + " = " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_ID +
                " where " + CROP_PAYMENT_TABLE_NAME + "." + CROP_PAYMENT_USER_ID + " = " + fieldId, null);

        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropInvoicePayment cropInvoicePayment = new CropInvoicePayment();
            cropInvoicePayment.setId(res.getString(res.getColumnIndex(CROP_PAYMENT_ID)));
            cropInvoicePayment.setUserId(res.getString(res.getColumnIndex(CROP_PAYMENT_USER_ID)));
            cropInvoicePayment.setAmount(res.getFloat(res.getColumnIndex(CROP_PAYMENT_AMOUNT)));
            cropInvoicePayment.setMode(res.getString(res.getColumnIndex(CROP_PAYMENT_MODE)));
            cropInvoicePayment.setDate(res.getString(res.getColumnIndex(CROP_PAYMENT_DATE)));
            cropInvoicePayment.setReferenceNo(res.getString(res.getColumnIndex(CROP_PAYMENT_REFERENCE_NO)));
            cropInvoicePayment.setPaymentNumber(res.getString(res.getColumnIndex(CROP_PAYMENT_NUMBER)));
            cropInvoicePayment.setNotes(res.getString(res.getColumnIndex(CROP_PAYMENT_NOTES)));
            cropInvoicePayment.setCustomerId(res.getString(res.getColumnIndex(CROP_PAYMENT_CUSTOMER_ID)));
            cropInvoicePayment.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            cropInvoicePayment.setInvoiceId(res.getString(res.getColumnIndex(CROP_PAYMENT_INVOICE_ID)));
            cropInvoicePayment.setInvoiceNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            cropInvoicePayment.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropInvoicePayment);
            res.moveToNext();
        }

        res.close();
        closeDB();
        Log.d("Crop Payment", array_list.toString());
        return array_list;
    }

    public String getNextPaymentNumber() {
        openDB();
        Cursor res = database.rawQuery("select " + CROP_PAYMENT_ID + " from " + CROP_PAYMENT_TABLE_NAME + " ORDER BY " + CROP_PAYMENT_ID + " DESC LIMIT 1", null);
        int lastId = 0;
        res.moveToFirst();
        if (!res.isAfterLast()) {
            lastId = res.getInt(res.getColumnIndex(CROP_PAYMENT_ID));
        }
        int id = lastId + 1;
        res.close();
        closeDB();

        return String.format("%04d", id);
    }

    public CropInvoice insertCropInvoice(CropInvoice invoice) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_INVOICE_USER_ID, invoice.getUserId());
        contentValues.put(CROP_INVOICE_CUSTOMER_ID, invoice.getCustomerId());
        contentValues.put(CROP_INVOICE_NO, invoice.getNumber());
        contentValues.put(CROP_INVOICE_DATE, invoice.getDate());
        contentValues.put(CROP_INVOICE_DUE_DATE, invoice.getDueDate());
        contentValues.put(CROP_INVOICE_ORDER_NUMBER, invoice.getOrderNumber());
        contentValues.put(CROP_INVOICE_TERMS, invoice.getTerms());
        contentValues.put(CROP_INVOICE_DISCOUNT, invoice.getDiscount());
        contentValues.put(CROP_INVOICE_SHIPPING_CHARGES, invoice.getShippingCharges());
        contentValues.put(CROP_INVOICE_CUSTOMER_NOTES, invoice.getCustomerNotes());
        contentValues.put(CROP_INVOICE_TERMS_AND_CONDITIONS, invoice.getTermsAndConditions());
        contentValues.put(CROP_SYNC_STATUS, invoice.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, invoice.getGlobalId());
        database.insert(CROP_INVOICE_TABLE_NAME, null, contentValues);


        Cursor res = database.rawQuery("select " + CROP_INVOICE_ID + " from " + CROP_INVOICE_TABLE_NAME + " where " + CROP_INVOICE_CUSTOMER_ID + " = '" + invoice.getCustomerId() + "' AND " + CROP_INVOICE_NO + " = '" + invoice.getNumber() + "'", null);
        res.moveToFirst();
        //ensure that the invoice has been saved before any items and payments are tied to it
        if (!res.isAfterLast()) {
            String invoiceId = res.getString(res.getColumnIndex(CROP_INVOICE_ID));
            ArrayList<CropProductItem> items = invoice.getItems();

            for (CropProductItem x : items) {
                x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_INVOICE);
                x.setParentObjectId(invoiceId);
                insertCropProductItem(x);
            }


            CropInvoicePayment cropInvoicePayment = invoice.getInitialPayment();
            if (cropInvoicePayment != null) {
                cropInvoicePayment.setInvoiceId(invoiceId);
                this.insertCropPayment(cropInvoicePayment);

            }
        }

        res.close();
        closeDB();

        return getCropInvoice(invoice.getNumber());
    }

    public CropInvoice updateCropInvoice(CropInvoice invoice) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_INVOICE_USER_ID, invoice.getUserId());
        contentValues.put(CROP_INVOICE_CUSTOMER_ID, invoice.getCustomerId());
        contentValues.put(CROP_INVOICE_NO, invoice.getNumber());
        contentValues.put(CROP_INVOICE_DATE, invoice.getDate());
        contentValues.put(CROP_INVOICE_DUE_DATE, invoice.getDueDate());
        contentValues.put(CROP_INVOICE_ORDER_NUMBER, invoice.getOrderNumber());
        contentValues.put(CROP_INVOICE_TERMS, invoice.getTerms());
        contentValues.put(CROP_INVOICE_DISCOUNT, invoice.getDiscount());
        contentValues.put(CROP_INVOICE_SHIPPING_CHARGES, invoice.getShippingCharges());
        contentValues.put(CROP_INVOICE_CUSTOMER_NOTES, invoice.getCustomerNotes());
        contentValues.put(CROP_INVOICE_TERMS_AND_CONDITIONS, invoice.getTermsAndConditions());
        contentValues.put(CROP_SYNC_STATUS, invoice.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, invoice.getGlobalId());
        database.update(CROP_INVOICE_TABLE_NAME, contentValues, CROP_INVOICE_ID + " = ?", new String[]{invoice.getId()});

        String invoiceId = invoice.getId();

        ArrayList<CropProductItem> items = invoice.getItems();

        for (CropProductItem x : items) {
            x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_INVOICE);
            x.setParentObjectId(invoiceId);
            if (x.getId() != null) {
                updateCropProductItem(x);
            } else {
                insertCropProductItem(x);
            }
        }
        CropInvoicePayment cropInvoicePayment = invoice.getInitialPayment();
        if (cropInvoicePayment != null) {
            cropInvoicePayment.setInvoiceId(invoiceId);
            this.insertCropPayment(cropInvoicePayment);
        }
        closeDB();
        deleteCropProductItems(invoice.getDeletedItemsIds());

        return getCropInvoice(invoice.getNumber());
    }

    public boolean deleteCropInvoice(String id) {
        CropInvoice invoice = getCropInvoiceById(id, false);
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = ? AND " + CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id, CROP_PRODUCT_ITEM_TYPE_INVOICE});
        database.delete(CROP_INVOICE_TABLE_NAME, CROP_INVOICE_ID + " = ?", new String[]{id});
        closeDB();
        if (invoice != null) {
            recordDeletedRecord("invoice", invoice.getGlobalId());
        }
        return true;
    }

    public String getNextInvoiceNumber() {
        openDB();
        Cursor res = database.rawQuery("select " + CROP_INVOICE_ID + " from " + CROP_INVOICE_TABLE_NAME + " ORDER BY " + CROP_INVOICE_ID + " DESC LIMIT 1", null);
        int lastId = 0;
        res.moveToFirst();

        if (!res.isAfterLast()) {
            Log.d("TESTING", res.getColumnCount() + " columns " + res.getColumnNames().toString());
            lastId = res.getInt(res.getColumnIndex(CROP_INVOICE_ID));
        }
        int id = lastId + 1;
        res.close();
        closeDB();

        return "INV-" + String.format("%04d", id);
    }

    public ArrayList<CropInvoice> getCropInvoices(String userId) {
        openDB();
        ArrayList<CropInvoice> array_list = new ArrayList();

        SQLiteDatabase db = database;
        Cursor res = db.rawQuery("select " + CROP_INVOICE_TABLE_NAME + ".*," + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_NAME + " from " + CROP_INVOICE_TABLE_NAME + " LEFT JOIN " + CROP_CUSTOMER_TABLE_NAME + " ON " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_CUSTOMER_ID + " = " + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_ID + " where " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_USER_ID + " = " + userId, null);
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
            cropInvoice.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropInvoice);
            res.moveToNext();
        }


        for (CropInvoice invoice : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + invoice.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_INVOICE + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();

                Log.d("ITEM ", res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)) + " != " + res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            res.close();

            invoice.setItems(items_list);
        }

        for (CropInvoice invoice : array_list) {
            invoice.setPayments(this.getCropPaymentsByInvoice(invoice.getId()));
        }
        res.close();

        closeDB();

        return array_list;
    }

    public CropInvoice getCropInvoice(String invoiceNumber) {
        openDB();
        SQLiteDatabase db = database;
        Cursor res = db.rawQuery("select " + CROP_INVOICE_TABLE_NAME + ".*," + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_NAME + " from " + CROP_INVOICE_TABLE_NAME + " LEFT JOIN " + CROP_CUSTOMER_TABLE_NAME + " ON " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_CUSTOMER_ID + " = " + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_ID + " where " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_NO + " = '" + invoiceNumber + "'", null);
        res.moveToFirst();
        CropInvoice invoice = null;
        if (!res.isAfterLast()) {
            invoice = new CropInvoice();
            invoice.setId(res.getString(res.getColumnIndex(CROP_INVOICE_ID)));
            invoice.setUserId(res.getString(res.getColumnIndex(CROP_INVOICE_USER_ID)));
            invoice.setCustomerId(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_ID)));
            invoice.setNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            invoice.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            invoice.setDate(res.getString(res.getColumnIndex(CROP_INVOICE_DATE)));
            invoice.setDueDate(res.getString(res.getColumnIndex(CROP_INVOICE_DUE_DATE)));
            invoice.setOrderNumber(res.getString(res.getColumnIndex(CROP_INVOICE_ORDER_NUMBER)));
            invoice.setTerms(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS)));
            invoice.setDiscount(res.getFloat(res.getColumnIndex(CROP_INVOICE_DISCOUNT)));
            invoice.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_INVOICE_SHIPPING_CHARGES)));
            invoice.setCustomerNotes(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_NOTES)));
            invoice.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS_AND_CONDITIONS)));
            invoice.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }


        if (invoice != null) {
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + invoice.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_INVOICE + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            res.close();

            invoice.setItems(items_list);
        }


        invoice.setPayments(this.getCropPaymentsByInvoice(invoice.getId()));


        res.close();
        closeDB();

        return invoice;
    }

    public ArrayList<CropInvoice> getCropInvoicesByCustomer(String customerId) {
        openDB();
        ArrayList<CropInvoice> array_list = new ArrayList();

        SQLiteDatabase db = database;
        Cursor res = db.rawQuery("select " + CROP_INVOICE_TABLE_NAME + ".*," + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_NAME +
                " from " + CROP_INVOICE_TABLE_NAME + " LEFT JOIN " + CROP_CUSTOMER_TABLE_NAME + " ON " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_CUSTOMER_ID +
                " = " + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_ID + " where " + CROP_INVOICE_TABLE_NAME + "." + CROP_INVOICE_CUSTOMER_ID + " = " + customerId, null);
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
            cropInvoice.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropInvoice);
            res.moveToNext();
        }


        for (CropInvoice invoice : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + invoice.getId() +
                    " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_INVOICE + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            res.close();

            invoice.setItems(items_list);
        }

        for (CropInvoice invoice : array_list) {
            invoice.setPayments(this.getCropPaymentsByInvoice(invoice.getId()));
        }

        res.close();
        closeDB();

        return array_list;
    }

    public CropEstimate insertCropEstimate(CropEstimate estimate) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_ESTIMATE_USER_ID, estimate.getUserId());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_ID, estimate.getCustomerId());
        contentValues.put(CROP_ESTIMATE_NO, estimate.getNumber());
        contentValues.put(CROP_ESTIMATE_DATE, estimate.getDate());
        contentValues.put(CROP_ESTIMATE_STATUS, estimate.getStatus());
        contentValues.put(CROP_ESTIMATE_REFERENCE_NO, estimate.getReferenceNumber());
        contentValues.put(CROP_ESTIMATE_EXP_DATE, estimate.getExpiryDate());
        contentValues.put(CROP_ESTIMATE_DISCOUNT, estimate.getDiscount());
        contentValues.put(CROP_ESTIMATE_SHIPPING_CHARGES, estimate.getShippingCharges());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_NOTES, estimate.getCustomerNotes());
        contentValues.put(CROP_ESTIMATE_TERMS_AND_CONDITIONS, estimate.getTermsAndConditions());
        contentValues.put(CROP_SYNC_STATUS, estimate.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, estimate.getGlobalId());
        database.insert(CROP_ESTIMATE_TABLE_NAME, null, contentValues);

        Cursor res = database.rawQuery("select " + CROP_ESTIMATE_ID + " from " + CROP_ESTIMATE_TABLE_NAME + " where " + CROP_ESTIMATE_CUSTOMER_ID + " = '" + estimate.getCustomerId() + "' AND " + CROP_ESTIMATE_NO + " = '" + estimate.getNumber() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            String estimateId = res.getString(res.getColumnIndex(CROP_ESTIMATE_ID));

            ArrayList<CropProductItem> items = estimate.getItems();

            for (CropProductItem x : items) {
                x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_ESTIMATE);
                x.setParentObjectId(estimateId);
                insertCropProductItem(x);
            }

        }
        res.close();
        closeDB();


        return getCropEstimate(estimate.getNumber());

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
        res.close();
        closeDB();


        return "EST-" + String.format("%04d", id);
    }

    public CropEstimate updateCropEstimate(CropEstimate estimate) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_ESTIMATE_USER_ID, estimate.getUserId());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_ID, estimate.getCustomerId());
        contentValues.put(CROP_ESTIMATE_NO, estimate.getNumber());
        contentValues.put(CROP_ESTIMATE_DATE, estimate.getDate());
        contentValues.put(CROP_ESTIMATE_REFERENCE_NO, estimate.getReferenceNumber());
        contentValues.put(CROP_ESTIMATE_EXP_DATE, estimate.getExpiryDate());
        contentValues.put(CROP_ESTIMATE_STATUS, estimate.getStatus());
        contentValues.put(CROP_ESTIMATE_DISCOUNT, estimate.getDiscount());
        contentValues.put(CROP_ESTIMATE_SHIPPING_CHARGES, estimate.getShippingCharges());
        contentValues.put(CROP_ESTIMATE_CUSTOMER_NOTES, estimate.getCustomerNotes());
        contentValues.put(CROP_ESTIMATE_TERMS_AND_CONDITIONS, estimate.getTermsAndConditions());
        contentValues.put(CROP_SYNC_STATUS, estimate.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, estimate.getGlobalId());

        database.update(CROP_ESTIMATE_TABLE_NAME, contentValues, CROP_ESTIMATE_ID + " = ?", new String[]{estimate.getId()});

        String estimateId = estimate.getId();

        ArrayList<CropProductItem> items = estimate.getItems();
        for (CropProductItem x : items) {
            x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_ESTIMATE);
            x.setParentObjectId(estimateId);
            if (x.getId() != null) {
                updateCropProductItem(x);
            } else {
                insertCropProductItem(x);
            }
        }
        closeDB();
        deleteCropProductItems(estimate.getDeletedItemsIds());
        return getCropEstimate(estimate.getNumber());
    }

    public boolean deleteCropEstimate(String id) {
        CropEstimate estimate = getCropEstimateById(id, false);
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = ? AND " + CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id, CROP_PRODUCT_ITEM_TYPE_ESTIMATE});
        database.delete(CROP_ESTIMATE_TABLE_NAME, CROP_ESTIMATE_ID + " = ?", new String[]{id});
        closeDB();
        if (estimate != null) {
            recordDeletedRecord("estimate", estimate.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropEstimate> getCropEstimates(String userId) {
        openDB();
        ArrayList<CropEstimate> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select " + CROP_ESTIMATE_TABLE_NAME + ".*," + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_NAME + " from " + CROP_ESTIMATE_TABLE_NAME + " LEFT JOIN " + CROP_CUSTOMER_TABLE_NAME + " ON " + CROP_ESTIMATE_TABLE_NAME + "." + CROP_ESTIMATE_CUSTOMER_ID + " = " + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_ID + " where " + CROP_ESTIMATE_TABLE_NAME + "." + CROP_ESTIMATE_USER_ID + " = " + userId, null);
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
            cropEstimate.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropEstimate);
            res.moveToNext();
        }


        for (CropEstimate estimate : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + estimate.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_ESTIMATE + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            estimate.setItems(items_list);
        }

        res.close();
        closeDB();

        return array_list;
    }

    public CropEstimate getCropEstimate(String estimateNumber) {
        openDB();
        ArrayList<CropEstimate> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select " + CROP_ESTIMATE_TABLE_NAME + ".*," + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_NAME + " from " + CROP_ESTIMATE_TABLE_NAME + " LEFT JOIN " + CROP_CUSTOMER_TABLE_NAME + " ON " + CROP_ESTIMATE_TABLE_NAME + "." + CROP_ESTIMATE_CUSTOMER_ID + " = " + CROP_CUSTOMER_TABLE_NAME + "." + CROP_CUSTOMER_ID + " where " + CROP_ESTIMATE_TABLE_NAME + "." + CROP_ESTIMATE_NO + " = '" + estimateNumber + "'", null);
        res.moveToFirst();
        CropEstimate estimate = null;
        if (!res.isAfterLast()) {
            estimate = new CropEstimate();
            estimate.setId(res.getString(res.getColumnIndex(CROP_ESTIMATE_ID)));
            estimate.setUserId(res.getString(res.getColumnIndex(CROP_ESTIMATE_USER_ID)));
            estimate.setCustomerId(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_ID)));
            estimate.setNumber(res.getString(res.getColumnIndex(CROP_ESTIMATE_NO)));
            estimate.setReferenceNumber(res.getString(res.getColumnIndex(CROP_ESTIMATE_REFERENCE_NO)));
            estimate.setCustomerName(res.getString(res.getColumnIndex(CROP_CUSTOMER_NAME)));
            estimate.setDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_DATE)));
            estimate.setExpiryDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_EXP_DATE)));
            estimate.setStatus(res.getString(res.getColumnIndex(CROP_ESTIMATE_STATUS)));
            estimate.setDiscount(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_DISCOUNT)));
            estimate.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_SHIPPING_CHARGES)));
            estimate.setCustomerNotes(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_NOTES)));
            estimate.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_ESTIMATE_TERMS_AND_CONDITIONS)));
            estimate.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(estimate);
            res.moveToNext();
        }


        if (estimate != null) {
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + estimate.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_ESTIMATE + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            estimate.setItems(items_list);
        }

        res.close();
        closeDB();

        return estimate;
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
        contentValues.put(CROP_SYNC_STATUS, cropProduct.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, cropProduct.getGlobalId());
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
        contentValues.put(CROP_SYNC_STATUS, cropProduct.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, cropProduct.getGlobalId());
        database.update(CROP_PRODUCT_TABLE_NAME, contentValues, CROP_PRODUCT_ID + " = ?", new String[]{cropProduct.getId()});

        closeDB();

    }

    public boolean deleteCropProduct(String id) {
        CropProduct cropProduct = getCropProductById(id, false);
        openDB();
        database.delete(CROP_PRODUCT_TABLE_NAME, CROP_PRODUCT_ID + " = ?", new String[]{id});
        closeDB();
        if (cropProduct != null) {
            recordDeletedRecord("product", cropProduct.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropProduct> getCropProducts(String userId) {
        openDB();
        ArrayList<CropProduct> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + CROP_PRODUCT_TABLE_NAME + ".*  from " + CROP_PRODUCT_TABLE_NAME +
                " WHERE " + CROP_PRODUCT_USER_ID + " = " + userId;

//SUM("+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_QUANTITY+") as quantityUsed
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
            CropProduct cropProduct = new CropProduct();
            cropProduct.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ID)));
            cropProduct.setUserId(res.getString(res.getColumnIndex(CROP_PRODUCT_USER_ID)));
            cropProduct.setName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            cropProduct.setType(res.getString(res.getColumnIndex(CROP_PRODUCT_TYPE)));
            cropProduct.setCode(res.getString(res.getColumnIndex(CROP_PRODUCT_CODE)));
            cropProduct.setUnits(res.getString(res.getColumnIndex(CROP_PRODUCT_UNITS)));
            cropProduct.setLinkedAccount(res.getString(res.getColumnIndex(CROP_PRODUCT_LINKED_ACCOUNT)));
            cropProduct.setOpeningCost(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_COST)));
            cropProduct.setOpeningQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_QUANTITY)));
            cropProduct.setQuantityUsed(computeProductQuantityUsed(res.getString(res.getColumnIndex(CROP_PRODUCT_ID))));
            cropProduct.setQuantityAdded(computeProductQuantityAdded(res.getString(res.getColumnIndex(CROP_PRODUCT_ID))));
            cropProduct.setSellingPrice(res.getFloat(res.getColumnIndex(CROP_PRODUCT_SELLING_PRICE)));
            cropProduct.setTaxRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_TAX_RATE)));
            cropProduct.setDescription(res.getString(res.getColumnIndex(CROP_PRODUCT_DESCRIPTION)));
            cropProduct.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropProduct);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;

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

    public CropProduct getCropProductById(String productId) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select " + CROP_PRODUCT_TABLE_NAME + ".*  from " + CROP_PRODUCT_TABLE_NAME +
                " WHERE " + CROP_PRODUCT_ID + " = " + productId;
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        CropProduct cropProduct = null;


        if (!res.isAfterLast()) {
            cropProduct = new CropProduct();
            cropProduct.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ID)));
            cropProduct.setUserId(res.getString(res.getColumnIndex(CROP_PRODUCT_USER_ID)));
            cropProduct.setName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            cropProduct.setType(res.getString(res.getColumnIndex(CROP_PRODUCT_TYPE)));
            cropProduct.setCode(res.getString(res.getColumnIndex(CROP_PRODUCT_CODE)));
            cropProduct.setUnits(res.getString(res.getColumnIndex(CROP_PRODUCT_UNITS)));
            cropProduct.setLinkedAccount(res.getString(res.getColumnIndex(CROP_PRODUCT_LINKED_ACCOUNT)));
            cropProduct.setOpeningCost(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_COST)));
            cropProduct.setOpeningQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_QUANTITY)));
            cropProduct.setQuantityUsed(computeProductQuantityUsed(res.getString(res.getColumnIndex(CROP_PRODUCT_ID))));
            cropProduct.setQuantityAdded(computeProductQuantityAdded(res.getString(res.getColumnIndex(CROP_PRODUCT_ID))));
            cropProduct.setSellingPrice(res.getFloat(res.getColumnIndex(CROP_PRODUCT_SELLING_PRICE)));
            cropProduct.setTaxRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_TAX_RATE)));
            cropProduct.setDescription(res.getString(res.getColumnIndex(CROP_PRODUCT_DESCRIPTION)));
            cropProduct.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();

        return cropProduct;

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
        contentValues.put(CROP_SYNC_STATUS, spraying.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, spraying.getGlobalId());
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
        contentValues.put(CROP_SYNC_STATUS, customer.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, customer.getGlobalId());
        database.update(CROP_SUPPLIER_TABLE_NAME, contentValues, CROP_SUPPLIER_ID + " = ?", new String[]{customer.getId()});
        closeDB();
    }

    public boolean deleteCropSupplier(String id) {
        CropSupplier cropSupplier = getCropSupplier(id, false);
        openDB();
        database.delete(CROP_SUPPLIER_TABLE_NAME, CROP_SUPPLIER_ID + " = ?", new String[]{id});
        closeDB();
        if (cropSupplier != null) {
            recordDeletedRecord("supplier", cropSupplier.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropSupplier> getCropSuppliers(String fieldId) {
        openDB();
        ArrayList<CropSupplier> array_list = new ArrayList();


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
            //  cropSupplier.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropSupplier.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropSupplier);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;

    }

    public CropSupplier getCropSupplier(String id, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_SUPPLIER_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SUPPLIER_TABLE_NAME + " where " + key + " = '" + id + "'", null);
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
            cropSupplier.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropSupplier.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            return cropSupplier;
        }
        res.close();

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
        contentValues.put(CROP_SYNC_STATUS, spraying.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, spraying.getGlobalId());
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
        contentValues.put(CROP_SYNC_STATUS, customer.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, customer.getGlobalId());
        database.update(CROP_CUSTOMER_TABLE_NAME, contentValues, CROP_CUSTOMER_ID + " = ?", new String[]{customer.getId()});
        closeDB();
    }

    public boolean deleteCropCustomer(String id) {
        CropCustomer customer = getCropCustomer(id, false);
        openDB();
        database.delete(CROP_CUSTOMER_TABLE_NAME, CROP_CUSTOMER_ID + " = ?", new String[]{id});
        closeDB();
        if (customer != null) {
            recordDeletedRecord("customer", customer.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropCustomer> getCropCustomers(String fieldId) {
        openDB();
        ArrayList<CropCustomer> array_list = new ArrayList();


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
            cropEmployee.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropEmployee);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return array_list;

    }

    public CropCustomer getCropCustomer(String customerId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_CUSTOMER_ID;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CUSTOMER_TABLE_NAME + " where " + key + " = '" + customerId + "'", null);
        res.moveToFirst();

        if (!res.isAfterLast()) {
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
            cropCustomer.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropCustomer.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            return cropCustomer;

        }

        closeDB();
        return null;

    }

    public void insertCropEmployee(CropEmployee spraying) {

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
        contentValues.put(CROP_SYNC_STATUS, spraying.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, spraying.getGlobalId());
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
        contentValues.put(CROP_SYNC_STATUS, s.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, s.getGlobalId());
        database.update(CROP_EMPLOYEE_TABLE_NAME, contentValues, CROP_EMPLOYEE_ID + " = ?", new String[]{s.getId()});
        closeDB();
    }

    public boolean deleteCropEmployee(String id) {
        CropEmployee employee = getCropEmployee(id, false);
        openDB();
        database.delete(CROP_EMPLOYEE_TABLE_NAME, CROP_EMPLOYEE_ID + " = ?", new String[]{id});
        closeDB();
        if (employee != null) {
            recordDeletedRecord("employee", employee.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropEmployee> getCropEmployees(String fieldId) {
        openDB();
        ArrayList<CropEmployee> array_list = new ArrayList();


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
            cropEmployee.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropEmployee);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return array_list;

    }

    public void insertCropSoilAnalysis(CropSoilAnalysis soilAnalysis) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SOIL_ANALYSIS_DATE, soilAnalysis.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_USER_ID, soilAnalysis.getUserId());
        contentValues.put(CROP_SOIL_ANALYSIS_FIELD_ID, soilAnalysis.getFieldId());
        contentValues.put(CROP_SOIL_ANALYSIS_DATE, soilAnalysis.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_PH, soilAnalysis.getPh());
        contentValues.put(CROP_SOIL_ANALYSIS_AGRONOMIST, soilAnalysis.getAgronomist());
        contentValues.put(CROP_SOIL_ANALYSIS_RESULTS, soilAnalysis.getResult());
        contentValues.put(CROP_SOIL_ANALYSIS_COST, soilAnalysis.getCost());
        contentValues.put(CROP_SOIL_ANALYSIS_ORGANIC_MATTER, soilAnalysis.getOrganicMatter());
        contentValues.put(CROP_SOIL_ANALYSIS_RECURRENCE, soilAnalysis.getRecurrence());
        contentValues.put(CROP_SOIL_ANALYSIS_REMINDERS, soilAnalysis.getReminders());
        contentValues.put(CROP_SOIL_ANALYSIS_FREQUENCY, soilAnalysis.getFrequency());
        contentValues.put(CROP_SOIL_ANALYSIS_REPEAT_UNTIL, soilAnalysis.getRepeatUntil());
        contentValues.put(CROP_SOIL_ANALYSIS_DAYS_BEFORE, soilAnalysis.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, soilAnalysis.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, soilAnalysis.getGlobalId());
        database.insert(CROP_SOIL_ANALYSIS_TABLE_NAME, null, contentValues);
        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_SOIL_ANALYSIS_TABLE_NAME + " where " + CROP_SOIL_ANALYSIS_FIELD_ID + " = " + soilAnalysis.getFieldId() + " AND " + CROP_SOIL_ANALYSIS_DATE + " = '" + soilAnalysis.getDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_ID));
        }
        generateNotifications(context.getString(R.string.notification_type_soil_analysis), id);
        res.close();
        closeDB();
    }

    public void updateCropSoilAnalysis(CropSoilAnalysis soilAnalysis) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SOIL_ANALYSIS_DATE, soilAnalysis.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_USER_ID, soilAnalysis.getUserId());
        contentValues.put(CROP_SOIL_ANALYSIS_FIELD_ID, soilAnalysis.getFieldId());
        contentValues.put(CROP_SOIL_ANALYSIS_DATE, soilAnalysis.getDate());
        contentValues.put(CROP_SOIL_ANALYSIS_PH, soilAnalysis.getPh());
        contentValues.put(CROP_SOIL_ANALYSIS_AGRONOMIST, soilAnalysis.getAgronomist());
        contentValues.put(CROP_SOIL_ANALYSIS_RESULTS, soilAnalysis.getResult());
        contentValues.put(CROP_SOIL_ANALYSIS_COST, soilAnalysis.getCost());
        contentValues.put(CROP_SOIL_ANALYSIS_ORGANIC_MATTER, soilAnalysis.getOrganicMatter());
        contentValues.put(CROP_SOIL_ANALYSIS_RECURRENCE, soilAnalysis.getRecurrence());
        contentValues.put(CROP_SOIL_ANALYSIS_REMINDERS, soilAnalysis.getReminders());
        contentValues.put(CROP_SOIL_ANALYSIS_FREQUENCY, soilAnalysis.getFrequency());
        contentValues.put(CROP_SOIL_ANALYSIS_REPEAT_UNTIL, soilAnalysis.getRepeatUntil());
        contentValues.put(CROP_SOIL_ANALYSIS_DAYS_BEFORE, soilAnalysis.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, soilAnalysis.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, soilAnalysis.getGlobalId());
        database.update(CROP_SOIL_ANALYSIS_TABLE_NAME, contentValues, CROP_SOIL_ANALYSIS_ID + " = ?", new String[]{soilAnalysis.getId()});
        deleteCropNotification(soilAnalysis.getId(), context.getString(R.string.notification_type_soil_analysis));
        generateNotifications(context.getString(R.string.notification_type_soil_analysis), soilAnalysis.getId());
        closeDB();
    }

    public boolean deleteCropSoilAnalysis(String id) {
        CropSoilAnalysis cropSoilAnalysis = getCropSoilAnalysisById(id, false);
        openDB();
        deleteCropNotification(id, context.getString(R.string.notification_type_soil_analysis));
        database.delete(CROP_SOIL_ANALYSIS_TABLE_NAME, CROP_SOIL_ANALYSIS_ID + " = ?", new String[]{id});
        closeDB();
        if (cropSoilAnalysis != null) {
            recordDeletedRecord("soilAnalysis", cropSoilAnalysis.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropSoilAnalysis> getCropSoilAnalysis(String fieldId) {
        openDB();
        ArrayList<CropSoilAnalysis> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SOIL_ANALYSIS_TABLE_NAME + " where " + CROP_SOIL_ANALYSIS_FIELD_ID + " = " + fieldId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropSoilAnalysis cropSoilAnalysis = new CropSoilAnalysis();
            cropSoilAnalysis.setId(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_ID)));
            cropSoilAnalysis.setUserId(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_USER_ID)));
            cropSoilAnalysis.setDate(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_DATE)));
            cropSoilAnalysis.setAgronomist(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_AGRONOMIST)));
            cropSoilAnalysis.setResult(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_RESULTS)));
            cropSoilAnalysis.setOrganicMatter(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_ORGANIC_MATTER)));
            cropSoilAnalysis.setPh(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_PH)));
            cropSoilAnalysis.setCost(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_COST)));
            cropSoilAnalysis.setFieldId(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_FIELD_ID)));
            cropSoilAnalysis.setFrequency(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_FREQUENCY)));
            cropSoilAnalysis.setRecurrence(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_RECURRENCE)));
            cropSoilAnalysis.setReminders(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_REMINDERS)));
            cropSoilAnalysis.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_DAYS_BEFORE)));
            cropSoilAnalysis.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_REPEAT_UNTIL)));
            cropSoilAnalysis.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropSoilAnalysis);
            res.moveToNext();
        }

        res.close();
        closeDB();

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
        contentValues.put(CROP_SPRAYING_RECURRENCE, spraying.getRecurrence());
        contentValues.put(CROP_SPRAYING_REMINDERS, spraying.getReminders());
        contentValues.put(CROP_SPRAYING_FREQUENCY, spraying.getFrequency());
        contentValues.put(CROP_SPRAYING_REPEAT_UNTIL, spraying.getRepeatUntil());
        contentValues.put(CROP_SPRAYING_DAYS_BEFORE, spraying.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, spraying.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, spraying.getGlobalId());
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
        contentValues.put(CROP_SYNC_STATUS, spraying.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, spraying.getGlobalId());
        database.update(CROP_SPRAYING_TABLE_NAME, contentValues, CROP_SPRAYING_ID + " = ?", new String[]{spraying.getId()});
        deleteCropNotification(spraying.getId(), context.getString(R.string.notification_type_spraying));
        generateNotifications(context.getString(R.string.notification_type_spraying), spraying.getId());
        closeDB();
    }

    public boolean deleteCropSpraying(String id) {
        CropSpraying spraying = getCropSpraying(id, false);
        openDB();
        deleteCropNotification(id, context.getString(R.string.notification_type_fertilizer_application));
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
            spraying.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
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
        contentValues.put(CROP_FERTILIZER_APPLICATION_METHOD, fertilizerApplication.getMethod());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REASON, fertilizerApplication.getReason());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM, fertilizerApplication.getFertilizerForm());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FERTILIZER_ID, fertilizerApplication.getFertilizerId());
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
            crop.setRate(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RATE)));
            crop.setFertilizerName(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_NAME)));
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
        contentValues.put(CROP_CULTIVATION_RECURRENCE, cropCultivation.getRecurrence());
        contentValues.put(CROP_CULTIVATION_REMINDERS, cropCultivation.getReminders());
        contentValues.put(CROP_CULTIVATION_FREQUENCY, cropCultivation.getFrequency());
        contentValues.put(CROP_CULTIVATION_REPEAT_UNTIL, cropCultivation.getRepeatUntil());
        contentValues.put(CROP_CULTIVATION_DAYS_BEFORE, cropCultivation.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, cropCultivation.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, cropCultivation.getGlobalId());
        database.insert(CROP_CULTIVATION_TABLE_NAME, null, contentValues);

        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_CULTIVATION_TABLE_NAME + " where " + CROP_CULTIVATION_CROP_ID + " = " + cropCultivation.getCropId() + " AND " + CROP_CULTIVATION_DATE + " = '" + cropCultivation.getDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_CULTIVATION_ID));
        }
        generateNotifications(context.getString(R.string.notification_type_cultivation), id);
        res.close();
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
        contentValues.put(CROP_CULTIVATION_RECURRENCE, cropCultivation.getRecurrence());
        contentValues.put(CROP_CULTIVATION_REMINDERS, cropCultivation.getReminders());
        contentValues.put(CROP_CULTIVATION_FREQUENCY, cropCultivation.getFrequency());
        contentValues.put(CROP_CULTIVATION_REPEAT_UNTIL, cropCultivation.getRepeatUntil());
        contentValues.put(CROP_CULTIVATION_DAYS_BEFORE, cropCultivation.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, cropCultivation.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, cropCultivation.getGlobalId());
        database.update(CROP_CULTIVATION_TABLE_NAME, contentValues, CROP_CULTIVATION_ID + " = ?", new String[]{cropCultivation.getId()});

        deleteCropNotification(cropCultivation.getId(), context.getString(R.string.notification_type_cultivation));
        generateNotifications(context.getString(R.string.notification_type_cultivation), cropCultivation.getId());
        closeDB();
    }

    public boolean deleteCropCultivate(String cultivateId) {
        CropCultivation cultivate = getCropCultivate(cultivateId, false);
        openDB();
        deleteCropNotification(cultivateId, context.getString(R.string.notification_type_cultivation));
        database.delete(CROP_CULTIVATION_TABLE_NAME, CROP_CULTIVATION_ID + " = ?", new String[]{cultivateId});
        closeDB();
        if (cultivate != null) {
            recordDeletedRecord("cultivation", cultivate.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropCultivation> getCropCultivates(String cropId) {
        openDB();
        ArrayList<CropCultivation> array_list = new ArrayList();

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
            crop.setFrequency(res.getFloat(res.getColumnIndex(CROP_CULTIVATION_FREQUENCY)));
            crop.setRecurrence(res.getString(res.getColumnIndex(CROP_CULTIVATION_RECURRENCE)));
            crop.setReminders(res.getString(res.getColumnIndex(CROP_CULTIVATION_REMINDERS)));
            crop.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_CULTIVATION_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_CULTIVATION_REPEAT_UNTIL)));
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
//        contentValues.put(CROP_CROP_COST, crop.getCost());
//        contentValues.put(CROP_CROP_YEAR, crop.getCroppingYear());
//        contentValues.put(CROP_CROP_OPERATOR, crop.getOperator());
//        contentValues.put(CROP_CROP_GROWING_CYCLE, crop.getGrowingCycle());
        contentValues.put(CROP_CROP_SEASON, crop.getSeason());
//        contentValues.put(CROP_CROP_COST, crop.getCost());
//        contentValues.put(CROP_CROP_SEED_ID, crop.getSeedId());
//        contentValues.put(CROP_CROP_RATE, crop.getRate());
//        contentValues.put(CROP_CROP_PLANTING_METHOD, crop.getPlantingMethod());
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
//        contentValues.put(CROP_CROP_COST, crop.getCost());
//        contentValues.put(CROP_CROP_YEAR, crop.getCroppingYear());
//        contentValues.put(CROP_CROP_OPERATOR, crop.getOperator());
//        contentValues.put(CROP_CROP_GROWING_CYCLE, crop.getGrowingCycle());
        contentValues.put(CROP_CROP_SEASON, crop.getSeason());
//        contentValues.put(CROP_CROP_COST, crop.getCost());
//        contentValues.put(CROP_CROP_SEED_ID, crop.getSeedId());
//        contentValues.put(CROP_CROP_RATE, crop.getRate());
//        contentValues.put(CROP_CROP_PLANTING_METHOD, crop.getPlantingMethod());
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
//            crop.setCost(res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
//            crop.setCroppingYear(res.getInt(res.getColumnIndex(CROP_CROP_YEAR)));
//            crop.setOperator(res.getString(res.getColumnIndex(CROP_CROP_OPERATOR)));
//            crop.setSeedId(res.getString(res.getColumnIndex(CROP_CROP_SEED_ID)));
//            crop.setGrowingCycle(res.getString(res.getColumnIndex(CROP_CROP_GROWING_CYCLE)));
//            crop.setRate(res.getFloat(res.getColumnIndex(CROP_CROP_RATE)));
//            crop.setPlantingMethod(res.getString(res.getColumnIndex(CROP_CROP_PLANTING_METHOD)));
            crop.setFieldId(res.getString(res.getColumnIndex(CROP_CROP_FIELD_ID)));
//            crop.setFieldName(res.getString(res.getColumnIndex(CROP_FIELD_NAME)));
            crop.setName(res.getString(res.getColumnIndex(CROP_CROP_NAME)));
//            crop.setSeres.getString(res.getColumnIndex(CROP_CROP_SEASON)));
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
//
//        contentValues.put(CROP_FIELD_USER_ID,field.getUserId());
//        contentValues.put(CROP_FIELD_NAME,field.getFieldName());
//        contentValues.put(CROP_FIELD_SOIL_CATEGORY,field.getSoilCategory());
//        contentValues.put(CROP_FIELD_SOIL_TYPE,field.getSoilType());
//        contentValues.put(CROP_FIELD_WATERCOURSE,field.getWatercourse());
//        contentValues.put(CROP_FIELD_UNITS,field.getUnits());
//        contentValues.put(CROP_FIELD_TOTAL_AREA,field.getTotalArea());
//        contentValues.put(CROP_FIELD_CROPPABLE_AREA,field.getCroppableArea());
//        contentValues.put(CROP_FIELD_FIELD_TYPE,field.getFieldType());
//        contentValues.put(CROP_FIELD_LAYOUT_TYPE,field.getLayoutType());
//        contentValues.put(CROP_FIELD_STATUS,field.getStatus());
//        contentValues.put(CROP_SYNC_STATUS,field.getSyncStatus());
//        contentValues.put(CROP_GLOBAL_ID,field.getGlobalId());

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
        contentValues.put(CROP_SYNC_STATUS, machine.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, machine.getGlobalId());
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
        contentValues.put(CROP_SYNC_STATUS, machine.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, machine.getGlobalId());
        database.update(CROP_MACHINE_TABLE_NAME, contentValues, CROP_MACHINE_ID + " = ?", new String[]{machine.getId()});

        closeDB();
    }

    public boolean deleteCropMachine(String machineId) {
        CropMachine machine = getCropMachine(machineId, false);
        openDB();
        database.delete(CROP_MACHINE_TABLE_NAME, CROP_MACHINE_ID + " = ?", new String[]{machineId});
        closeDB();
        if (machine != null) {
            recordDeletedRecord("machine", machine.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropMachine> getCropMachines(String userId) {
        openDB();
        ArrayList<CropMachine> array_list = new ArrayList();


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
            machine.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            array_list.add(machine);
            res.moveToNext();
        }

        res.close();
        closeDB();
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
            incomeExpense.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(incomeExpense);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public void insertCropTask(CropTask task) {
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
        contentValues.put(CROP_TASK_FREQUENCY, task.getFrequency());
        contentValues.put(CROP_TASK_REPEAT_UNTIL, task.getRepeatUntil());
        contentValues.put(CROP_TASK_DAYS_BEFORE, task.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, task.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, task.getGlobalId());
        database.insert(CROP_TASK_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropTask(CropTask task) {
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
        contentValues.put(CROP_TASK_FREQUENCY, task.getFrequency());
        contentValues.put(CROP_TASK_REPEAT_UNTIL, task.getRepeatUntil());
        contentValues.put(CROP_TASK_DAYS_BEFORE, task.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, task.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, task.getGlobalId());
        database.update(CROP_TASK_TABLE_NAME, contentValues, CROP_TASK_ID + " = ?", new String[]{task.getId()});

        closeDB();
    }

    public boolean deleteCropTask(String taskId) {
        CropTask task = getCropTask(taskId, false);
        openDB();
        database.delete(CROP_TASK_TABLE_NAME, CROP_TASK_ID + " = ?", new String[]{taskId});
        closeDB();
        if (task != null) {
            recordDeletedRecord("task", task.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropTask> getCropTasks(String userId) {
        openDB();
        ArrayList<CropTask> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_TASK_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME +
                " from " + CROP_TASK_TABLE_NAME +
                " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_TASK_TABLE_NAME + "." + CROP_TASK_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID +
                " where " + CROP_TASK_TABLE_NAME + "." + CROP_TASK_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropTask task = new CropTask();
            task.setId(res.getString(res.getColumnIndex(CROP_TASK_ID)));
            task.setUserId(res.getString(res.getColumnIndex(CROP_TASK_USER_ID)));
            task.setCropId(res.getString(res.getColumnIndex(CROP_TASK_CROP_ID)));
            task.setCropName(res.getString(res.getColumnIndex(CROP_CROP_NAME)));
            task.setEmployeeName(res.getString(res.getColumnIndex(CROP_TASK_EMPLOYEE_ID)));
            //  task.setEmployeeName(res.getString(res.getColumnIndex(CROP_EMPLOYEE_FIRST_NAME))+res.getString(res.getColumnIndex(CROP_EMPLOYEE_LAST_NAME)));
            task.setDate(res.getString(res.getColumnIndex(CROP_TASK_DATE)));
            task.setTitle(res.getString(res.getColumnIndex(CROP_TASK_TITLE)));
            task.setType(res.getString(res.getColumnIndex(CROP_TASK_TYPE)));
            task.setStatus(res.getString(res.getColumnIndex(CROP_TASK_STATUS)));
            task.setDescription(res.getString(res.getColumnIndex(CROP_TASK_DESCRIPTION)));
            task.setFrequency(Float.parseFloat(res.getString(res.getColumnIndex(CROP_TASK_FREQUENCY))));
            task.setRecurrence(res.getString(res.getColumnIndex(CROP_TASK_RECURRENCE)));
            task.setReminders(res.getString(res.getColumnIndex(CROP_TASK_REMINDERS)));
            task.setDaysBefore(Float.parseFloat(res.getString(res.getColumnIndex(CROP_TASK_DAYS_BEFORE))));
            task.setRepeatUntil(res.getString(res.getColumnIndex(CROP_TASK_REPEAT_UNTIL)));
            task.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            array_list.add(task);
            res.moveToNext();
        }
        res.close();
        closeDB();

        return array_list;
    }

    public String getNextPurchaseOrderNumber() {
        openDB();
        Cursor res = database.rawQuery("select " + CROP_PURCHASE_ORDER_ID + " from " + CROP_PURCHASE_ORDER_TABLE_NAME + " ORDER BY " + CROP_PURCHASE_ORDER_ID + " DESC LIMIT 1", null);
        int lastId = 0;
        res.moveToFirst();
        if (!res.isAfterLast()) {
            lastId = res.getInt(res.getColumnIndex(CROP_PURCHASE_ORDER_ID));
        }
        int id = lastId + 1;

        res.close();
        closeDB();

        return "PO-" + String.format("%04d", id);
    }

    public CropPurchaseOrder insertCropPurchaseOrder(CropPurchaseOrder estimate) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_PURCHASE_ORDER_USER_ID, estimate.getUserId());
        contentValues.put(CROP_PURCHASE_ORDER_SUPPLIER_ID, estimate.getSupplierId());
        contentValues.put(CROP_PURCHASE_ORDER_NUMBER, estimate.getNumber());
        contentValues.put(CROP_PURCHASE_ORDER_PURCHASE_DATE, estimate.getPurchaseDate());
        contentValues.put(CROP_PURCHASE_ORDER_DELIVERY_METHOD, estimate.getMethod());
        contentValues.put(CROP_PURCHASE_ORDER_REFERENCE_NUMBER, estimate.getReferenceNumber());
        contentValues.put(CROP_PURCHASE_ORDER_DELIVERY_DATE, estimate.getDeliveryDate());
        contentValues.put(CROP_PURCHASE_ORDER_STATUS, estimate.getStatus());
        contentValues.put(CROP_PURCHASE_ORDER_DISCOUNT, estimate.getDiscount());
        contentValues.put(CROP_PURCHASE_ORDER_NOTES, estimate.getNotes());
        contentValues.put(CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS, estimate.getTermsAndConditions());
        contentValues.put(CROP_SYNC_STATUS, estimate.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, estimate.getGlobalId());
        Log.d("PURCHASE ORDER INSERTED", contentValues.toString());

        database.insert(CROP_PURCHASE_ORDER_TABLE_NAME, null, contentValues);

        Cursor res = database.rawQuery("select " + CROP_PURCHASE_ORDER_ID + " from " + CROP_PURCHASE_ORDER_TABLE_NAME + " where " + CROP_PURCHASE_ORDER_SUPPLIER_ID + " = '" + estimate.getSupplierId() + "' AND " + CROP_PURCHASE_ORDER_NUMBER + " = '" + estimate.getNumber() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            String estimateId = res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_ID));

            ArrayList<CropProductItem> items = estimate.getItems();

            for (CropProductItem x : items) {
                x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER);
                x.setParentObjectId(estimateId);
                insertCropProductItem(x);
            }

        }
        res.close();
        closeDB();
        return getCropPurchaseOrder(estimate.getNumber());
    }

    public CropPurchaseOrder updateCropPurchaseOrder(CropPurchaseOrder estimate) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_PURCHASE_ORDER_USER_ID, estimate.getUserId());
        contentValues.put(CROP_PURCHASE_ORDER_SUPPLIER_ID, estimate.getSupplierId());
        contentValues.put(CROP_PURCHASE_ORDER_NUMBER, estimate.getNumber());
        contentValues.put(CROP_PURCHASE_ORDER_PURCHASE_DATE, estimate.getPurchaseDate());
        contentValues.put(CROP_PURCHASE_ORDER_DELIVERY_METHOD, estimate.getMethod());
        contentValues.put(CROP_PURCHASE_ORDER_REFERENCE_NUMBER, estimate.getReferenceNumber());
        contentValues.put(CROP_PURCHASE_ORDER_DELIVERY_DATE, estimate.getDeliveryDate());
        contentValues.put(CROP_PURCHASE_ORDER_STATUS, estimate.getStatus());
        contentValues.put(CROP_PURCHASE_ORDER_DISCOUNT, estimate.getDiscount());
        contentValues.put(CROP_PURCHASE_ORDER_NOTES, estimate.getNotes());
        contentValues.put(CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS, estimate.getTermsAndConditions());
        contentValues.put(CROP_SYNC_STATUS, estimate.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, estimate.getGlobalId());
        database.update(CROP_PURCHASE_ORDER_TABLE_NAME, contentValues, CROP_PURCHASE_ORDER_ID + " = ?", new String[]{estimate.getId()});

        String estimateId = estimate.getId();

        ArrayList<CropProductItem> items = estimate.getItems();

        for (CropProductItem x : items) {
            x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER);
            x.setParentObjectId(estimateId);
            if (x.getId() != null) {
                updateCropProductItem(x);
            } else {
                insertCropProductItem(x);
            }
        }

        closeDB();
        deleteCropProductItems(estimate.getDeletedItemsIds());
        return getCropPurchaseOrder(estimate.getNumber());
    }

    public boolean deleteCropPurchaseOrder(String id) {
        CropPurchaseOrder cropPurchaseOrder = getCropPurchaseOrderById(id, false);
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_ID + " = ? AND " + CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id, CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER});
        database.delete(CROP_PURCHASE_ORDER_TABLE_NAME, CROP_PURCHASE_ORDER_ID + " = ?", new String[]{id});
        closeDB();
        if (cropPurchaseOrder != null) {
            recordDeletedRecord("purchaseOrder", cropPurchaseOrder.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropPurchaseOrder> getCropPurchaseOrders(String userId) {
        openDB();
        ArrayList<CropPurchaseOrder> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_PURCHASE_ORDER_TABLE_NAME + ".*," + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_NAME + " from " + CROP_PURCHASE_ORDER_TABLE_NAME + " LEFT JOIN " + CROP_SUPPLIER_TABLE_NAME + " ON " + CROP_PURCHASE_ORDER_TABLE_NAME + "." + CROP_PURCHASE_ORDER_SUPPLIER_ID + " = " + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_ID + " where " + CROP_PURCHASE_ORDER_TABLE_NAME + "." + CROP_PURCHASE_ORDER_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
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
            cropPurchaseOrder.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropPurchaseOrder);
            res.moveToNext();
        }


        for (CropPurchaseOrder cropPurchaseOrder : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + cropPurchaseOrder.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

                items_list.add(item);
                res.moveToNext();
            }
            cropPurchaseOrder.setItems(items_list);
        }

        res.close();
        closeDB();
        return array_list;
    }

    public CropPurchaseOrder getCropPurchaseOrder(String purchaseOrderNumber) {
        openDB();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select " + CROP_PURCHASE_ORDER_TABLE_NAME + ".*," + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_NAME + " from " + CROP_PURCHASE_ORDER_TABLE_NAME + " LEFT JOIN " + CROP_SUPPLIER_TABLE_NAME + " ON " + CROP_PURCHASE_ORDER_TABLE_NAME + "." + CROP_PURCHASE_ORDER_SUPPLIER_ID + " = " + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_ID + " where " + CROP_PURCHASE_ORDER_TABLE_NAME + "." + CROP_PURCHASE_ORDER_NUMBER + " = '" + purchaseOrderNumber + "'", null);
        res.moveToFirst();
        CropPurchaseOrder cropPurchaseOrder = null;
        if (!res.isAfterLast()) {
            cropPurchaseOrder = new CropPurchaseOrder();
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
            cropPurchaseOrder.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }


        if (cropPurchaseOrder != null) {
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + cropPurchaseOrder.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            cropPurchaseOrder.setItems(items_list);
        }
        res.close();
        closeDB();
        return cropPurchaseOrder;
    }

    public void insertCropPaymentBill(CropPaymentBill cropPaymentBill) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PAYMENT_BILL_USER_ID, cropPaymentBill.getUserId());
        contentValues.put(CROP_PAYMENT_BILL_PAYMENT_MADE, cropPaymentBill.getAmount());
        contentValues.put(CROP_PAYMENT_BILL_DATE, cropPaymentBill.getDate());
        contentValues.put(CROP_PAYMENT_BILL_PAYMENT_MODE, cropPaymentBill.getMode());
        contentValues.put(CROP_PAYMENT_BILL_NOTES, cropPaymentBill.getNotes());
        contentValues.put(CROP_PAYMENT_BILL_PAID_THROUGH, cropPaymentBill.getPaidThrough());
        contentValues.put(CROP_PAYMENT_BILL_REFERENCE_NUMBER, cropPaymentBill.getReferenceNumber());
        contentValues.put(CROP_PAYMENT_BILL_BILL_ID, cropPaymentBill.getBillId());
        contentValues.put(CROP_PAYMENT_BILL_SUPPLIER_ID, cropPaymentBill.getSupplierId());
        contentValues.put(CROP_SYNC_STATUS, cropPaymentBill.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, cropPaymentBill.getGlobalId());
        database.insert(CROP_PAYMENT_BILL_TABLE_NAME, null, contentValues);

//        Log.d("ANNOYING",cropPaymentBill.getBillId());

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
        contentValues.put(CROP_PAYMENT_BILL_BILL_ID, cropPaymentBill.getBillId());
        contentValues.put(CROP_PAYMENT_BILL_SUPPLIER_ID, cropPaymentBill.getSupplierId());
        contentValues.put(CROP_SYNC_STATUS, cropPaymentBill.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, cropPaymentBill.getGlobalId());
        database.update(CROP_PAYMENT_BILL_TABLE_NAME, contentValues, CROP_PAYMENT_ID + " = ?", new String[]{cropPaymentBill.getId()});


        closeDB();

    }

    public boolean deleteCropPaymentBill(String id) {
        CropPaymentBill paymentBill = getCropPaymentBill(id, false);
        openDB();
        database.delete(CROP_PAYMENT_BILL_TABLE_NAME, CROP_PAYMENT_BILL_ID + " = ?", new String[]{id});
        closeDB();
        if (paymentBill != null) {
            recordDeletedRecord("billPayment", paymentBill.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropPaymentBill> getCropPaymentBills(String userId) {
        openDB();
        ArrayList<CropPaymentBill> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select " + CROP_PAYMENT_BILL_TABLE_NAME + ".*," + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_NAME + ", " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_NUMBER + " from " + CROP_PAYMENT_BILL_TABLE_NAME + " LEFT JOIN " + CROP_SUPPLIER_TABLE_NAME + " ON " + CROP_PAYMENT_BILL_TABLE_NAME + "." + CROP_PAYMENT_BILL_SUPPLIER_ID + " = " + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_ID +
                " LEFT JOIN " + CROP_BILL_TABLE_NAME + " ON " + CROP_PAYMENT_BILL_TABLE_NAME + "." + CROP_PAYMENT_BILL_BILL_ID + " = " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_ID +
                " where " + CROP_PAYMENT_BILL_TABLE_NAME + "." + CROP_PAYMENT_BILL_USER_ID + " = " + userId, null);
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
            paymentBill.setBillId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_BILL_ID)));
            paymentBill.setBillNumber(res.getString(res.getColumnIndex(CROP_BILL_NUMBER)));
            paymentBill.setSupplierId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_SUPPLIER_ID)));
            paymentBill.setSupplierName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            paymentBill.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(paymentBill);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropPaymentBill> getCropPaymentBillsByBill(String billId) {
        openDB();
        ArrayList<CropPaymentBill> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery("select " + CROP_PAYMENT_BILL_TABLE_NAME + ".*," + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_NAME + ", " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_NUMBER + " from " + CROP_PAYMENT_BILL_TABLE_NAME + " LEFT JOIN " + CROP_SUPPLIER_TABLE_NAME + " ON " + CROP_PAYMENT_BILL_TABLE_NAME + "." + CROP_PAYMENT_BILL_SUPPLIER_ID + " = " + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_ID +
                " LEFT JOIN " + CROP_BILL_TABLE_NAME + " ON " + CROP_PAYMENT_BILL_TABLE_NAME + "." + CROP_PAYMENT_BILL_BILL_ID + " = " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_ID +
                " where " + CROP_PAYMENT_BILL_TABLE_NAME + "." + CROP_PAYMENT_BILL_BILL_ID + " = " + billId, null);
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
            paymentBill.setBillId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_BILL_ID)));
            paymentBill.setBillNumber(res.getString(res.getColumnIndex(CROP_BILL_NUMBER)));
            paymentBill.setSupplierId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_SUPPLIER_ID)));
            paymentBill.setSupplierName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            paymentBill.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(paymentBill);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public CropBill insertCropBill(CropBill bill) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_BILL_USER_ID, bill.getUserId());
        contentValues.put(CROP_BILL_SUPPLIER_ID, bill.getSupplierId());
        contentValues.put(CROP_BILL_NUMBER, bill.getNumber());
        contentValues.put(CROP_BILL_DATE, bill.getBillDate());
        contentValues.put(CROP_BILL_DUE_DATE, bill.getDueDate());
        contentValues.put(CROP_BILL_ORDER_NUMBER, bill.getOrderNumber());
        contentValues.put(CROP_BILL_TERMS, bill.getTerms());
        contentValues.put(CROP_BILL_DISCOUNT, bill.getDiscount());
        contentValues.put(CROP_BILL_NOTES, bill.getNotes());
        contentValues.put(CROP_SYNC_STATUS, bill.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, bill.getGlobalId());
        database.insert(CROP_BILL_TABLE_NAME, null, contentValues);

        Cursor res = database.rawQuery("select " + CROP_BILL_ID + " from " + CROP_BILL_TABLE_NAME + " where " + CROP_BILL_SUPPLIER_ID + " = '" + bill.getSupplierId() + "' AND " + CROP_BILL_NUMBER + " = '" + bill.getNumber() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            String estimateId = res.getString(res.getColumnIndex(CROP_BILL_ID));
            ArrayList<CropProductItem> items = bill.getItems();
            for (CropProductItem x : items) {
                x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_BILL);
                x.setParentObjectId(estimateId);
                insertCropProductItem(x);
            }
        }
        res.close();
        closeDB();
        return getCropBill(bill.getNumber());
    }

    public CropBill updateCropBill(CropBill bill) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_BILL_USER_ID, bill.getUserId());
        contentValues.put(CROP_BILL_SUPPLIER_ID, bill.getSupplierId());
        contentValues.put(CROP_BILL_NUMBER, bill.getNumber());
        contentValues.put(CROP_BILL_DATE, bill.getBillDate());
        contentValues.put(CROP_BILL_DUE_DATE, bill.getDueDate());
        contentValues.put(CROP_BILL_ORDER_NUMBER, bill.getOrderNumber());
        contentValues.put(CROP_BILL_TERMS, bill.getTerms());
        contentValues.put(CROP_BILL_DISCOUNT, bill.getDiscount());
        contentValues.put(CROP_BILL_NOTES, bill.getNotes());
        contentValues.put(CROP_SYNC_STATUS, bill.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, bill.getGlobalId());

        database.update(CROP_BILL_TABLE_NAME, contentValues, CROP_BILL_ID + " = ?", new String[]{bill.getId()});

        String estimateId = bill.getId();

        ArrayList<CropProductItem> items = bill.getItems();
        for (CropProductItem x : items) {
            x.setParentObjectType(CROP_PRODUCT_ITEM_TYPE_BILL);
            x.setParentObjectId(estimateId);
            if (x.getId() != null) {
                updateCropProductItem(x);
            } else {
                insertCropProductItem(x);
            }
        }
        closeDB();
        deleteCropProductItems(bill.getDeletedItemsIds());


        return getCropBill(bill.getNumber());
    }

    public String getNextBillNumber() {
        openDB();
        Cursor res = database.rawQuery("select " + CROP_BILL_ID + " from " + CROP_BILL_TABLE_NAME + " ORDER BY " + CROP_BILL_ID + " DESC LIMIT 1", null);
        int lastId = 0;
        res.moveToFirst();
        Log.d("TESTING", res.getCount() + "");
        if (!res.isAfterLast()) {
            Log.d("TESTING", res.getColumnCount() + " columns " + res.getColumnNames().toString());
            lastId = res.getInt(res.getColumnIndex(CROP_BILL_ID));
        }
        int id = lastId + 1;
        res.close();
        closeDB();

        return "BL-" + String.format("%04d", id);
    }

    public boolean deleteCropBill(String id) {
        CropBill bill = getCropBillById(id, false);
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = ? AND " + CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id, CROP_PRODUCT_ITEM_TYPE_BILL});
        database.delete(CROP_BILL_TABLE_NAME, CROP_BILL_ID + " = ?", new String[]{id});
        closeDB();
        if (bill != null) {
            recordDeletedRecord("bill", bill.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropBill> getCropBills(String userId) {
        openDB();
        ArrayList<CropBill> array_list = new ArrayList();

        SQLiteDatabase db = database;
        Cursor res = db.rawQuery("select " + CROP_BILL_TABLE_NAME + ".*," + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_NAME + " from " + CROP_BILL_TABLE_NAME + " LEFT JOIN " + CROP_SUPPLIER_TABLE_NAME + " ON " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_SUPPLIER_ID + " = " + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_ID + " where " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropBill cropBill = new CropBill();
            cropBill.setId(res.getString(res.getColumnIndex(CROP_BILL_ID)));
            cropBill.setUserId(res.getString(res.getColumnIndex(CROP_BILL_USER_ID)));
            cropBill.setSupplierId(res.getString(res.getColumnIndex(CROP_BILL_SUPPLIER_ID)));
            cropBill.setNumber(res.getString(res.getColumnIndex(CROP_BILL_NUMBER)));
            cropBill.setSupplierName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            cropBill.setBillDate(res.getString(res.getColumnIndex(CROP_BILL_DATE)));
            cropBill.setDueDate(res.getString(res.getColumnIndex(CROP_BILL_DUE_DATE)));
            cropBill.setOrderNumber(res.getString(res.getColumnIndex(CROP_BILL_ORDER_NUMBER)));
            cropBill.setTerms(res.getString(res.getColumnIndex(CROP_BILL_TERMS)));
            cropBill.setDiscount(res.getFloat(res.getColumnIndex(CROP_BILL_DISCOUNT)));
            cropBill.setNotes(res.getString(res.getColumnIndex(CROP_BILL_NOTES)));
            cropBill.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropBill);
            res.moveToNext();
        }


        for (CropBill bill : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + bill.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_BILL + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            res.close();

            bill.setItems(items_list);
        }

        for (CropBill bill : array_list) {
            bill.setPaymentBills(this.getCropPaymentBills(bill.getId()));
        }

        res.close();
        closeDB();

        return array_list;
    }

    public CropBill getCropBill(String billNumber) {
        openDB();
        CropBill bill = null;

        SQLiteDatabase db = database;
        Cursor res = db.rawQuery("select " + CROP_BILL_TABLE_NAME + ".*," + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_NAME + " from " + CROP_BILL_TABLE_NAME + " LEFT JOIN " + CROP_SUPPLIER_TABLE_NAME + " ON " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_SUPPLIER_ID + " = " + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_ID + " where " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_NUMBER + " = '" + billNumber + "'", null);
        res.moveToFirst();

        if (!res.isAfterLast()) {
            bill = new CropBill();
            bill.setId(res.getString(res.getColumnIndex(CROP_BILL_ID)));
            bill.setUserId(res.getString(res.getColumnIndex(CROP_BILL_USER_ID)));
            bill.setSupplierId(res.getString(res.getColumnIndex(CROP_BILL_SUPPLIER_ID)));
            bill.setNumber(res.getString(res.getColumnIndex(CROP_BILL_NUMBER)));
            bill.setSupplierName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            bill.setBillDate(res.getString(res.getColumnIndex(CROP_BILL_DATE)));
            bill.setDueDate(res.getString(res.getColumnIndex(CROP_BILL_DUE_DATE)));
            bill.setOrderNumber(res.getString(res.getColumnIndex(CROP_BILL_ORDER_NUMBER)));
            bill.setTerms(res.getString(res.getColumnIndex(CROP_BILL_TERMS)));
            bill.setDiscount(res.getFloat(res.getColumnIndex(CROP_BILL_DISCOUNT)));
            bill.setNotes(res.getString(res.getColumnIndex(CROP_BILL_NOTES)));
            bill.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }


        if (bill != null) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + bill.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_BILL + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
                items_list.add(item);
                res.moveToNext();
            }
            res.close();

            bill.setItems(items_list);
        }


        bill.setPaymentBills(this.getCropPaymentBills(bill.getId()));

        res.close();
        closeDB();

        return bill;
    }

    public ArrayList<CropBill> getCropBillsBySupplier(String supplierId) {
        openDB();
        ArrayList<CropBill> array_list = new ArrayList();

        SQLiteDatabase db = database;
        Cursor res = db.rawQuery("select " + CROP_BILL_TABLE_NAME + ".*," + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_NAME +
                " from " + CROP_BILL_TABLE_NAME + " LEFT JOIN " + CROP_SUPPLIER_TABLE_NAME + " ON " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_SUPPLIER_ID +
                " = " + CROP_SUPPLIER_TABLE_NAME + "." + CROP_SUPPLIER_ID + " where " + CROP_BILL_TABLE_NAME + "." + CROP_BILL_SUPPLIER_ID + " = " + supplierId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropBill cropBill = new CropBill();
            cropBill.setId(res.getString(res.getColumnIndex(CROP_BILL_ID)));
            cropBill.setUserId(res.getString(res.getColumnIndex(CROP_BILL_USER_ID)));
            cropBill.setSupplierId(res.getString(res.getColumnIndex(CROP_BILL_SUPPLIER_ID)));
            cropBill.setNumber(res.getString(res.getColumnIndex(CROP_BILL_NUMBER)));
            cropBill.setSupplierName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            cropBill.setBillDate(res.getString(res.getColumnIndex(CROP_BILL_DATE)));
            cropBill.setDueDate(res.getString(res.getColumnIndex(CROP_BILL_DUE_DATE)));
            cropBill.setOrderNumber(res.getString(res.getColumnIndex(CROP_BILL_ORDER_NUMBER)));
            cropBill.setTerms(res.getString(res.getColumnIndex(CROP_BILL_TERMS)));
            cropBill.setDiscount(res.getFloat(res.getColumnIndex(CROP_BILL_DISCOUNT)));
            cropBill.setNotes(res.getString(res.getColumnIndex(CROP_BILL_NOTES)));
            cropBill.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropBill);
            res.moveToNext();
        }


        for (CropBill bill : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery("select " + CROP_PRODUCT_ITEM_TABLE_NAME + ".*," + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_NAME + " from " + CROP_PRODUCT_ITEM_TABLE_NAME + " LEFT JOIN " + CROP_PRODUCT_TABLE_NAME + " ON " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_PRODUCT_ID + " = " + CROP_PRODUCT_TABLE_NAME + "." + CROP_PRODUCT_ID + " where " + CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = " + bill.getId() + " AND " + CROP_PRODUCT_ITEM_TABLE_NAME + "." + CROP_PRODUCT_ITEM_TYPE + " = '" + CROP_PRODUCT_ITEM_TYPE_BILL + "'", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                items_list.add(item);
                res.moveToNext();
            }
            res.close();

            bill.setItems(items_list);
        }

        for (CropBill bill : array_list) {
            bill.setPaymentBills(this.getCropPaymentBills(bill.getId()));
        }
        res.close();
        closeDB();
        Log.d("Crop Product", array_list.toString());
        return array_list;
    }

    public void insertCropIrrigation(CropIrrigation irrigation) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_IRRIGATION_USER_ID, irrigation.getUserId());
        contentValues.put(CROP_IRRIGATION_CROP_ID, irrigation.getCropId());
        contentValues.put(CROP_IRRIGATION_DATE, irrigation.getOperationDate());
        contentValues.put(CROP_IRRIGATION_SYSTEM_RATE, irrigation.getSystemRate());
        contentValues.put(CROP_IRRIGATION_START_TIME, irrigation.getStartTime());
        contentValues.put(CROP_IRRIGATION_END_TIME, irrigation.getEndTime());
        contentValues.put(CROP_IRRIGATION_AREA_IRRIGATED, irrigation.getAreaIrrigated());
        contentValues.put(CROP_IRRIGATION_UNITS, irrigation.getUnits());
        contentValues.put(CROP_IRRIGATION_RECURRENCE, irrigation.getRecurrence());
        contentValues.put(CROP_IRRIGATION_REMINDERS, irrigation.getReminders());
        contentValues.put(CROP_IRRIGATION_FREQUENCY, irrigation.getFrequency());
        contentValues.put(CROP_IRRIGATION_REPEAT_UNTIL, irrigation.getRepeatUntil());
        contentValues.put(CROP_IRRIGATION_DAYS_BEFORE, irrigation.getDaysBefore());
        contentValues.put(CROP_IRRIGATION_COST, irrigation.getTotalCost());
        contentValues.put(CROP_SYNC_STATUS, irrigation.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, irrigation.getGlobalId());
        database.insert(CROP_IRRIGATION_TABLE_NAME, null, contentValues);
        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_IRRIGATION_TABLE_NAME + " where " + CROP_IRRIGATION_CROP_ID + " = " + irrigation.getCropId() + " AND " + CROP_IRRIGATION_DATE + " = '" + irrigation.getOperationDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_IRRIGATION_ID));
        }
        generateNotifications(context.getString(R.string.notification_type_irrigation), id);
        res.close();
        closeDB();
    }

    public void updateCropIrrigation(CropIrrigation irrigation) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_IRRIGATION_USER_ID, irrigation.getUserId());
        contentValues.put(CROP_IRRIGATION_CROP_ID, irrigation.getCropId());
        contentValues.put(CROP_IRRIGATION_DATE, irrigation.getOperationDate());
        contentValues.put(CROP_IRRIGATION_SYSTEM_RATE, irrigation.getSystemRate());
        contentValues.put(CROP_IRRIGATION_START_TIME, irrigation.getStartTime());
        contentValues.put(CROP_IRRIGATION_END_TIME, irrigation.getEndTime());
        contentValues.put(CROP_IRRIGATION_AREA_IRRIGATED, irrigation.getAreaIrrigated());
        contentValues.put(CROP_IRRIGATION_UNITS, irrigation.getUnits());
        contentValues.put(CROP_IRRIGATION_RECURRENCE, irrigation.getRecurrence());
        contentValues.put(CROP_IRRIGATION_REMINDERS, irrigation.getReminders());
        contentValues.put(CROP_IRRIGATION_FREQUENCY, irrigation.getFrequency());
        contentValues.put(CROP_IRRIGATION_REPEAT_UNTIL, irrigation.getRepeatUntil());
        contentValues.put(CROP_IRRIGATION_DAYS_BEFORE, irrigation.getDaysBefore());
        contentValues.put(CROP_IRRIGATION_COST, irrigation.getTotalCost());
        contentValues.put(CROP_SYNC_STATUS, irrigation.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, irrigation.getGlobalId());
        database.update(CROP_IRRIGATION_TABLE_NAME, contentValues, CROP_IRRIGATION_ID + " = ?", new String[]{irrigation.getId()});

        deleteCropNotification(irrigation.getId(), context.getString(R.string.notification_type_irrigation));
        generateNotifications(context.getString(R.string.notification_type_irrigation), irrigation.getId());
        closeDB();
    }

    public boolean deleteCropIrrigation(String irrigationId) {
        CropIrrigation irrigation = getCropIrrigation(irrigationId, false);
        openDB();
        deleteCropNotification(irrigationId, context.getString(R.string.notification_type_irrigation));
        database.delete(CROP_IRRIGATION_TABLE_NAME, CROP_IRRIGATION_ID + " = ?", new String[]{irrigationId});
        closeDB();
        if (irrigation != null) {
            recordDeletedRecord("irrigation", irrigation.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropIrrigation> getCropIrrigations(String cropId) {
        openDB();
        ArrayList<CropIrrigation> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_IRRIGATION_TABLE_NAME + " where " + CROP_IRRIGATION_CROP_ID + " = '" + cropId + "'", null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            CropIrrigation irrigation = new CropIrrigation();
            irrigation.setId(res.getString(res.getColumnIndex(CROP_IRRIGATION_ID)));
            irrigation.setUserId(res.getString(res.getColumnIndex(CROP_IRRIGATION_USER_ID)));
            irrigation.setCropId(res.getString(res.getColumnIndex(CROP_IRRIGATION_CROP_ID)));
            irrigation.setOperationDate(res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE)));
            irrigation.setSystemRate(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_SYSTEM_RATE)));
            irrigation.setStartTime(res.getString(res.getColumnIndex(CROP_IRRIGATION_START_TIME)));
            irrigation.setEndTime(res.getString(res.getColumnIndex(CROP_IRRIGATION_END_TIME)));
            irrigation.setUnits(res.getString(res.getColumnIndex(CROP_IRRIGATION_UNITS)));
            irrigation.setAreaIrrigated(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_AREA_IRRIGATED)));
            irrigation.setFrequency(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_FREQUENCY)));
            irrigation.setRecurrence(res.getString(res.getColumnIndex(CROP_IRRIGATION_RECURRENCE)));
            irrigation.setReminders(res.getString(res.getColumnIndex(CROP_IRRIGATION_REMINDERS)));
            irrigation.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_DAYS_BEFORE)));
            irrigation.setRepeatUntil(res.getString(res.getColumnIndex(CROP_IRRIGATION_REPEAT_UNTIL)));
            irrigation.setTotalCost(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));
            irrigation.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(irrigation);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public void insertCropTransplanting(CropTransplanting transplanting) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_TRANSPLANTING_USER_ID, transplanting.getUserId());
        contentValues.put(CROP_TRANSPLANTING_CROP_ID, transplanting.getCropId());
        contentValues.put(CROP_TRANSPLANTING_DATE, transplanting.getOperationDate());
        contentValues.put(CROP_TRANSPLANTING_TOTAL_SEEDLING, transplanting.getTotalSeedling());
        contentValues.put(CROP_TRANSPLANTING_SEEDLINGS_PER_HA, transplanting.getSeedlingPerHa());
        contentValues.put(CROP_TRANSPLANTING_VARIETY_EARLINESS, transplanting.getVarietyEarliness());
        contentValues.put(CROP_TRANSPLANTING_CYCLE_LENGTH, transplanting.getCycleLength());
        contentValues.put(CROP_TRANSPLANTING_UNITS, transplanting.getUnits());
        contentValues.put(CROP_TRANSPLANTING_EXPECTED_YIELD, transplanting.getExpectedYield());
        contentValues.put(CROP_TRANSPLANTING_EXPECTED_YIELD_PER_HA, transplanting.getExpectedYieldPerHa());
        contentValues.put(CROP_TRANSPLANTING_OPERATOR, transplanting.getOperator());
        ;
        contentValues.put(CROP_TRANSPLANTING_RECURRENCE, transplanting.getRecurrence());
        contentValues.put(CROP_TRANSPLANTING_REMINDERS, transplanting.getReminders());
        contentValues.put(CROP_TRANSPLANTING_FREQUENCY, transplanting.getFrequency());
        contentValues.put(CROP_TRANSPLANTING_REPEAT_UNTIL, transplanting.getRepeatUntil());
        contentValues.put(CROP_TRANSPLANTING_DAYS_BEFORE, transplanting.getDaysBefore());
        contentValues.put(CROP_TRANSPLANTING_COST, transplanting.getTotalCost());
        contentValues.put(CROP_SYNC_STATUS, transplanting.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, transplanting.getGlobalId());
        database.insert(CROP_TRANSPLANTING_TABLE_NAME, null, contentValues);
        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_TRANSPLANTING_TABLE_NAME + " where " + CROP_TRANSPLANTING_CROP_ID + " = " + transplanting.getCropId() + " AND " + CROP_TRANSPLANTING_DATE + " = '" + transplanting.getOperationDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_TRANSPLANTING_ID));
        }
        generateNotifications(context.getString(R.string.notification_type_transplanting), id);
        res.close();
        closeDB();
    }

    public void updateCropTransplanting(CropTransplanting transplanting) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_TRANSPLANTING_USER_ID, transplanting.getUserId());
        contentValues.put(CROP_TRANSPLANTING_CROP_ID, transplanting.getCropId());
        contentValues.put(CROP_TRANSPLANTING_DATE, transplanting.getOperationDate());
        contentValues.put(CROP_TRANSPLANTING_TOTAL_SEEDLING, transplanting.getTotalSeedling());
        contentValues.put(CROP_TRANSPLANTING_SEEDLINGS_PER_HA, transplanting.getSeedlingPerHa());
        contentValues.put(CROP_TRANSPLANTING_VARIETY_EARLINESS, transplanting.getVarietyEarliness());
        contentValues.put(CROP_TRANSPLANTING_CYCLE_LENGTH, transplanting.getCycleLength());
        contentValues.put(CROP_TRANSPLANTING_UNITS, transplanting.getUnits());
        contentValues.put(CROP_TRANSPLANTING_EXPECTED_YIELD, transplanting.getExpectedYield());
        contentValues.put(CROP_TRANSPLANTING_EXPECTED_YIELD_PER_HA, transplanting.getExpectedYieldPerHa());
        contentValues.put(CROP_TRANSPLANTING_OPERATOR, transplanting.getOperator());
        contentValues.put(CROP_TRANSPLANTING_RECURRENCE, transplanting.getRecurrence());
        contentValues.put(CROP_TRANSPLANTING_REMINDERS, transplanting.getReminders());
        contentValues.put(CROP_TRANSPLANTING_FREQUENCY, transplanting.getFrequency());
        contentValues.put(CROP_TRANSPLANTING_REPEAT_UNTIL, transplanting.getRepeatUntil());
        contentValues.put(CROP_TRANSPLANTING_DAYS_BEFORE, transplanting.getDaysBefore());
        contentValues.put(CROP_TRANSPLANTING_COST, transplanting.getTotalCost());
        contentValues.put(CROP_SYNC_STATUS, transplanting.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, transplanting.getGlobalId());
        database.update(CROP_TRANSPLANTING_TABLE_NAME, contentValues, CROP_TRANSPLANTING_ID + " = ?", new String[]{transplanting.getId()});

        deleteCropNotification(transplanting.getId(), context.getString(R.string.notification_type_transplanting));
        generateNotifications(context.getString(R.string.notification_type_transplanting), transplanting.getId());

        closeDB();
    }

    public boolean deleteCropTransplanting(String transplantingId) {
        CropTransplanting transplanting = getCropTransplanting(transplantingId, false);
        openDB();
        deleteCropNotification(transplantingId, context.getString(R.string.notification_type_transplanting));
        database.delete(CROP_TRANSPLANTING_TABLE_NAME, CROP_TRANSPLANTING_ID + " = ?", new String[]{transplantingId});
        closeDB();
        if (transplanting != null) {
            recordDeletedRecord("transplanting", transplanting.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropTransplanting> getCropTransplantings(String cropId) {
        openDB();
        ArrayList<CropTransplanting> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_TRANSPLANTING_TABLE_NAME + " where " + CROP_TRANSPLANTING_CROP_ID + " = '" + cropId + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropTransplanting transplanting = new CropTransplanting();
            transplanting.setId(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_ID)));
            transplanting.setUserId(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_USER_ID)));
            transplanting.setCropId(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_CROP_ID)));
            transplanting.setOperationDate(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE)));
            transplanting.setTotalSeedling(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_TOTAL_SEEDLING)));
            transplanting.setSeedlingPerHa(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_SEEDLINGS_PER_HA)));
            transplanting.setVarietyEarliness(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_VARIETY_EARLINESS)));
            transplanting.setCycleLength(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_CYCLE_LENGTH)));
            transplanting.setUnits(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_UNITS)));
            transplanting.setExpectedYield(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_EXPECTED_YIELD)));
            transplanting.setExpectedYieldPerHa(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_EXPECTED_YIELD_PER_HA)));
            transplanting.setFrequency(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_FREQUENCY)));
            transplanting.setOperator(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_OPERATOR)));
            transplanting.setRecurrence(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_RECURRENCE)));
            transplanting.setReminders(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_REMINDERS)));
            transplanting.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_DAYS_BEFORE)));
            transplanting.setRepeatUntil(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_REPEAT_UNTIL)));
            transplanting.setTotalCost(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));
            transplanting.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            array_list.add(transplanting);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public void insertCropScouting(CropScouting scouting) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_SCOUTING_USER_ID, scouting.getUserId());
        contentValues.put(CROP_SCOUTING_CROP_ID, scouting.getCropId());
        contentValues.put(CROP_SCOUTING_DATE, scouting.getDate());
        contentValues.put(CROP_SCOUTING_METHOD, scouting.getMethod());
        contentValues.put(CROP_SCOUTING_INFESTED, scouting.getInfested());
        contentValues.put(CROP_SCOUTING_INFESTATION_TYPE, scouting.getInfestationType());
        contentValues.put(CROP_SCOUTING_INFESTATION, scouting.getInfestation());
        contentValues.put(CROP_SCOUTING_INFESTATION_LEVEL, scouting.getInfestationLevel());
        contentValues.put(CROP_SCOUTING_COST, scouting.getCost());
        contentValues.put(CROP_SCOUTING_REMARKS, scouting.getRemarks());
        contentValues.put(CROP_SCOUTING_RECURRENCE, scouting.getRecurrence());
        contentValues.put(CROP_SCOUTING_REMINDERS, scouting.getReminders());
        contentValues.put(CROP_SCOUTING_FREQUENCY, scouting.getFrequency());
        contentValues.put(CROP_SCOUTING_REPEAT_UNTIL, scouting.getRepeatUntil());
        contentValues.put(CROP_SCOUTING_DAYS_BEFORE, scouting.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, scouting.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, scouting.getGlobalId());
        database.insert(CROP_SCOUTING_TABLE_NAME, null, contentValues);

        //generate Notifications
        String id = "";
        Cursor res = database.rawQuery("select * from " + CROP_SCOUTING_TABLE_NAME + " where " + CROP_SCOUTING_CROP_ID + " = " + scouting.getCropId() + " AND " + CROP_SCOUTING_DATE + " = '" + scouting.getDate() + "'", null);
        res.moveToFirst();
        if (!res.isAfterLast()) {
            id = res.getString(res.getColumnIndex(CROP_SCOUTING_ID));
        }
        generateNotifications(context.getString(R.string.notification_type_scouting), id);
        res.close();
        closeDB();
    }

    public void updateCropScouting(CropScouting scouting) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SCOUTING_USER_ID, scouting.getUserId());
        contentValues.put(CROP_SCOUTING_CROP_ID, scouting.getCropId());
        contentValues.put(CROP_SCOUTING_DATE, scouting.getDate());
        contentValues.put(CROP_SCOUTING_METHOD, scouting.getMethod());
        contentValues.put(CROP_SCOUTING_INFESTED, scouting.getInfested());
        contentValues.put(CROP_SCOUTING_INFESTATION_TYPE, scouting.getInfestationType());
        contentValues.put(CROP_SCOUTING_INFESTATION, scouting.getInfestation());
        contentValues.put(CROP_SCOUTING_INFESTATION_LEVEL, scouting.getInfestationLevel());
        contentValues.put(CROP_SCOUTING_COST, scouting.getCost());
        contentValues.put(CROP_SCOUTING_REMARKS, scouting.getRemarks());
        contentValues.put(CROP_SCOUTING_RECURRENCE, scouting.getRecurrence());
        contentValues.put(CROP_SCOUTING_REMINDERS, scouting.getReminders());
        contentValues.put(CROP_SCOUTING_FREQUENCY, scouting.getFrequency());
        contentValues.put(CROP_SCOUTING_REPEAT_UNTIL, scouting.getRepeatUntil());
        contentValues.put(CROP_SCOUTING_DAYS_BEFORE, scouting.getDaysBefore());
        contentValues.put(CROP_SYNC_STATUS, scouting.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, scouting.getGlobalId());
        database.update(CROP_SCOUTING_TABLE_NAME, contentValues, CROP_SCOUTING_ID + " = ?", new String[]{scouting.getId()});

        deleteCropNotification(scouting.getId(), context.getString(R.string.notification_type_scouting));
        generateNotifications(context.getString(R.string.notification_type_scouting), scouting.getId());
        closeDB();
    }

    public boolean deleteCropScouting(String scoutingId) {
        CropScouting scouting = getCropScouting(scoutingId, false);
        openDB();
        deleteCropNotification(scoutingId, context.getString(R.string.notification_type_scouting));
        database.delete(CROP_SCOUTING_TABLE_NAME, CROP_SCOUTING_ID + " = ?", new String[]{scoutingId});
        closeDB();
        if (scouting != null) {
            recordDeletedRecord("scouting", scouting.getGlobalId());
        }

        return true;
    }

    public ArrayList<CropScouting> getCropScoutings(String cropId) {
        openDB();
        ArrayList<CropScouting> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SCOUTING_TABLE_NAME + " where " + CROP_SCOUTING_CROP_ID + " = '" + cropId + "'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropScouting scouting = new CropScouting();
            scouting.setId(res.getString(res.getColumnIndex(CROP_SCOUTING_ID)));
            scouting.setUserId(res.getString(res.getColumnIndex(CROP_SCOUTING_USER_ID)));
            scouting.setCropId(res.getString(res.getColumnIndex(CROP_SCOUTING_CROP_ID)));
            scouting.setDate(res.getString(res.getColumnIndex(CROP_SCOUTING_DATE)));
            scouting.setMethod(res.getString(res.getColumnIndex(CROP_SCOUTING_METHOD)));
            scouting.setInfested(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTED)));
            scouting.setInfestationType(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTATION_TYPE)));
            scouting.setInfestation(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTATION)));
            scouting.setInfestationLevel(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTATION_LEVEL)));
            scouting.setCost(res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST)));
            scouting.setRemarks(res.getString(res.getColumnIndex(CROP_SCOUTING_REMARKS)));
            scouting.setFrequency(res.getFloat(res.getColumnIndex(CROP_SCOUTING_FREQUENCY)));
            scouting.setRecurrence(res.getString(res.getColumnIndex(CROP_SCOUTING_RECURRENCE)));
            scouting.setReminders(res.getString(res.getColumnIndex(CROP_SCOUTING_REMINDERS)));
            scouting.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_SCOUTING_DAYS_BEFORE)));
            scouting.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SCOUTING_REPEAT_UNTIL)));
            scouting.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(scouting);
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

    public void insertCropContact(CropContact contact) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_CONTACT_USER_ID, contact.getUserId());
        contentValues.put(CROP_CONTACT_TYPE, contact.getType());
        contentValues.put(CROP_CONTACT_NAME, contact.getName());
        contentValues.put(CROP_CONTACT_BUSINESS_NAME, contact.getBusinessName());
        contentValues.put(CROP_CONTACT_ADDRESS, contact.getAddress());
        contentValues.put(CROP_CONTACT_PHONE_NUMBER, contact.getPhoneNumber());
        contentValues.put(CROP_CONTACT_EMAIL, contact.getEmail());
        contentValues.put(CROP_CONTACT_WEBSITE, contact.getWebsite());
        contentValues.put(CROP_SYNC_STATUS, contact.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, contact.getGlobalId());
        database.insert(CROP_CONTACT_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCropContact(CropContact contact) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CONTACT_USER_ID, contact.getUserId());
        contentValues.put(CROP_CONTACT_TYPE, contact.getType());
        contentValues.put(CROP_CONTACT_NAME, contact.getName());
        contentValues.put(CROP_CONTACT_BUSINESS_NAME, contact.getBusinessName());
        contentValues.put(CROP_CONTACT_ADDRESS, contact.getAddress());
        contentValues.put(CROP_CONTACT_PHONE_NUMBER, contact.getPhoneNumber());
        contentValues.put(CROP_CONTACT_EMAIL, contact.getEmail());
        contentValues.put(CROP_CONTACT_WEBSITE, contact.getWebsite());
        contentValues.put(CROP_SYNC_STATUS, contact.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, contact.getGlobalId());

        database.update(CROP_CONTACT_TABLE_NAME, contentValues, CROP_CONTACT_ID + " = ?", new String[]{contact.getId()});

        closeDB();
    }

    public boolean deleteCropContact(String contactId) {
        CropContact contact = getCropContact(contactId, false);
        openDB();
        database.delete(CROP_CONTACT_TABLE_NAME, CROP_CONTACT_ID + " = ?", new String[]{contactId});
        closeDB();
        if (contact != null) {
            recordDeletedRecord("contact", contact.getGlobalId());
        }
        return true;
    }

    public ArrayList<CropContact> getCropContacts(String userId) {
        openDB();
        ArrayList<CropContact> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CONTACT_TABLE_NAME + " where " + CROP_CONTACT_USER_ID + " = " + userId, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropContact contact = new CropContact();
            contact.setId(res.getString(res.getColumnIndex(CROP_CONTACT_ID)));
            contact.setUserId(res.getString(res.getColumnIndex(CROP_CONTACT_USER_ID)));
            contact.setType(res.getString(res.getColumnIndex(CROP_CONTACT_TYPE)));
            contact.setName(res.getString(res.getColumnIndex(CROP_CONTACT_NAME)));
            contact.setBusinessName(res.getString(res.getColumnIndex(CROP_CONTACT_BUSINESS_NAME)));
            contact.setAddress(res.getString(res.getColumnIndex(CROP_CONTACT_ADDRESS)));
            contact.setPhoneNumber(res.getString(res.getColumnIndex(CROP_CONTACT_PHONE_NUMBER)));
            contact.setEmail(res.getString(res.getColumnIndex(CROP_CONTACT_EMAIL)));
            contact.setWebsite(res.getString(res.getColumnIndex(CROP_CONTACT_WEBSITE)));
            contact.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            array_list.add(contact);
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

    public ArrayList<CropCustomer> getCropCustomers(String userId, boolean synced) {
        openDB();
        ArrayList<CropCustomer> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CUSTOMER_TABLE_NAME + " where " + CROP_CUSTOMER_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();


        while (!res.isAfterLast()) {
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
            cropCustomer.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropCustomer.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropCustomer);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return array_list;
    }

    public ArrayList<CropEmployee> getCropEmployees(String userId, boolean synced) {
        openDB();
        ArrayList<CropEmployee> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_EMPLOYEE_TABLE_NAME + " where " + CROP_EMPLOYEE_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
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
            cropEmployee.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropEmployee.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropEmployee);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return array_list;
    }

    public ArrayList<CropProduct> getCropProducts(String userId, boolean synced) {
        openDB();
        ArrayList<CropProduct> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * FROM " + CROP_PRODUCT_TABLE_NAME + " WHERE " + CROP_PRODUCT_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?";
        Cursor res = db.rawQuery(query, new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();


        while (!res.isAfterLast()) {
            CropProduct cropProduct = new CropProduct();
            cropProduct.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ID)));
            cropProduct.setUserId(res.getString(res.getColumnIndex(CROP_PRODUCT_USER_ID)));
            cropProduct.setName(res.getString(res.getColumnIndex(CROP_SUPPLIER_NAME)));
            cropProduct.setType(res.getString(res.getColumnIndex(CROP_PRODUCT_TYPE)));
            cropProduct.setCode(res.getString(res.getColumnIndex(CROP_PRODUCT_CODE)));
            cropProduct.setUnits(res.getString(res.getColumnIndex(CROP_PRODUCT_UNITS)));
            cropProduct.setLinkedAccount(res.getString(res.getColumnIndex(CROP_PRODUCT_LINKED_ACCOUNT)));
            cropProduct.setOpeningCost(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_COST)));
            cropProduct.setOpeningQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_QUANTITY)));
            cropProduct.setSellingPrice(res.getFloat(res.getColumnIndex(CROP_PRODUCT_SELLING_PRICE)));
            cropProduct.setTaxRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_TAX_RATE)));
            cropProduct.setDescription(res.getString(res.getColumnIndex(CROP_PRODUCT_DESCRIPTION)));
            cropProduct.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropProduct.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropProduct);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropMachine> getCropMachines(String userId, boolean synced) {
        openDB();
        ArrayList<CropMachine> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_MACHINE_TABLE_NAME + " where " + CROP_MACHINE_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
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
            machine.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            machine.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(machine);
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

    public ArrayList<CropContact> getCropContacts(String userId, boolean synced) {
        openDB();
        ArrayList<CropContact> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CONTACT_TABLE_NAME + " where " + CROP_CONTACT_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropContact contact = new CropContact();
            contact.setId(res.getString(res.getColumnIndex(CROP_CONTACT_ID)));
            contact.setUserId(res.getString(res.getColumnIndex(CROP_CONTACT_USER_ID)));
            contact.setType(res.getString(res.getColumnIndex(CROP_CONTACT_TYPE)));
            contact.setName(res.getString(res.getColumnIndex(CROP_CONTACT_NAME)));
            contact.setBusinessName(res.getString(res.getColumnIndex(CROP_CONTACT_BUSINESS_NAME)));
            contact.setAddress(res.getString(res.getColumnIndex(CROP_CONTACT_ADDRESS)));
            contact.setPhoneNumber(res.getString(res.getColumnIndex(CROP_CONTACT_PHONE_NUMBER)));
            contact.setEmail(res.getString(res.getColumnIndex(CROP_CONTACT_EMAIL)));
            contact.setWebsite(res.getString(res.getColumnIndex(CROP_CONTACT_WEBSITE)));
            contact.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            contact.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(contact);
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

    public ArrayList<CropInvoice> getCropInvoices(String userId, boolean synced) {
        openDB();
        ArrayList<CropInvoice> array_list = new ArrayList();

        SQLiteDatabase db = database;

        Cursor res = db.rawQuery("select * from " + CROP_INVOICE_TABLE_NAME + " WHERE " + CROP_INVOICE_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropInvoice cropInvoice = new CropInvoice();
            cropInvoice.setId(res.getString(res.getColumnIndex(CROP_INVOICE_ID)));
            cropInvoice.setUserId(res.getString(res.getColumnIndex(CROP_INVOICE_USER_ID)));
            cropInvoice.setCustomerId(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_ID)));
            cropInvoice.setNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            cropInvoice.setDate(res.getString(res.getColumnIndex(CROP_INVOICE_DATE)));
            cropInvoice.setDueDate(res.getString(res.getColumnIndex(CROP_INVOICE_DUE_DATE)));
            cropInvoice.setOrderNumber(res.getString(res.getColumnIndex(CROP_INVOICE_ORDER_NUMBER)));
            cropInvoice.setTerms(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS)));
            cropInvoice.setDiscount(res.getFloat(res.getColumnIndex(CROP_INVOICE_DISCOUNT)));
            cropInvoice.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_INVOICE_SHIPPING_CHARGES)));
            cropInvoice.setCustomerNotes(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_NOTES)));
            cropInvoice.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS_AND_CONDITIONS)));
            cropInvoice.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropInvoice.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropInvoice);
            res.moveToNext();
        }


        res.close();

        closeDB();

        return array_list;
    }

    public ArrayList<CropEstimate> getCropEstimates(String userId, boolean synced) {
        openDB();
        ArrayList<CropEstimate> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_ESTIMATE_TABLE_NAME + " WHERE " + CROP_ESTIMATE_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropEstimate cropEstimate = new CropEstimate();
            cropEstimate.setId(res.getString(res.getColumnIndex(CROP_ESTIMATE_ID)));
            cropEstimate.setUserId(res.getString(res.getColumnIndex(CROP_ESTIMATE_USER_ID)));
            cropEstimate.setCustomerId(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_ID)));
            cropEstimate.setNumber(res.getString(res.getColumnIndex(CROP_ESTIMATE_NO)));
            cropEstimate.setReferenceNumber(res.getString(res.getColumnIndex(CROP_ESTIMATE_REFERENCE_NO)));
            cropEstimate.setDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_DATE)));
            cropEstimate.setExpiryDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_EXP_DATE)));
            cropEstimate.setStatus(res.getString(res.getColumnIndex(CROP_ESTIMATE_STATUS)));
            cropEstimate.setDiscount(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_DISCOUNT)));
            cropEstimate.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_SHIPPING_CHARGES)));
            cropEstimate.setCustomerNotes(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_NOTES)));
            cropEstimate.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_ESTIMATE_TERMS_AND_CONDITIONS)));
            cropEstimate.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropEstimate.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropEstimate);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return array_list;
    }

    public ArrayList<CropBill> getCropBills(String userId, boolean synced) {
        openDB();
        ArrayList<CropBill> array_list = new ArrayList();

        SQLiteDatabase db = database;
        Cursor res = db.rawQuery("select * from " + CROP_BILL_TABLE_NAME + " WHERE " + CROP_BILL_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropBill cropBill = new CropBill();
            cropBill.setId(res.getString(res.getColumnIndex(CROP_BILL_ID)));
            cropBill.setUserId(res.getString(res.getColumnIndex(CROP_BILL_USER_ID)));
            cropBill.setSupplierId(res.getString(res.getColumnIndex(CROP_BILL_SUPPLIER_ID)));
            cropBill.setNumber(res.getString(res.getColumnIndex(CROP_BILL_NUMBER)));
            cropBill.setBillDate(res.getString(res.getColumnIndex(CROP_BILL_DATE)));
            cropBill.setDueDate(res.getString(res.getColumnIndex(CROP_BILL_DUE_DATE)));
            cropBill.setOrderNumber(res.getString(res.getColumnIndex(CROP_BILL_ORDER_NUMBER)));
            cropBill.setTerms(res.getString(res.getColumnIndex(CROP_BILL_TERMS)));
            cropBill.setDiscount(res.getFloat(res.getColumnIndex(CROP_BILL_DISCOUNT)));
            cropBill.setNotes(res.getString(res.getColumnIndex(CROP_BILL_NOTES)));
            cropBill.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropBill.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropBill);
            res.moveToNext();
        }

        res.close();
        closeDB();

        return array_list;
    }

    public ArrayList<CropSalesOrder> getCropSalesOrders(String userId, boolean synced) {
        openDB();
        ArrayList<CropSalesOrder> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SALES_ORDER_TABLE_NAME + " WHERE " + CROP_SALES_ORDER_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropSalesOrder cropSalesOrder = new CropSalesOrder();
            cropSalesOrder.setId(res.getString(res.getColumnIndex(CROP_SALES_ORDER_ID)));
            cropSalesOrder.setUserId(res.getString(res.getColumnIndex(CROP_SALES_ORDER_USER_ID)));
            cropSalesOrder.setCustomerId(res.getString(res.getColumnIndex(CROP_SALES_ORDER_CUSTOMER_ID)));
            cropSalesOrder.setNumber(res.getString(res.getColumnIndex(CROP_SALES_ORDER_NO)));
            cropSalesOrder.setMethod(res.getString(res.getColumnIndex(CROP_SALES_ORDER_SHIPPING_METHOD)));
            cropSalesOrder.setReferenceNumber(res.getString(res.getColumnIndex(CROP_SALES_ORDER_REFERENCE_NO)));
            cropSalesOrder.setDate(res.getString(res.getColumnIndex(CROP_SALES_ORDER_DATE)));
            cropSalesOrder.setStatus(res.getString(res.getColumnIndex(CROP_SALES_ORDER_STATUS)));
            cropSalesOrder.setShippingDate(res.getString(res.getColumnIndex(CROP_SALES_ORDER_SHIPPING_DATE)));
            cropSalesOrder.setDiscount(res.getFloat(res.getColumnIndex(CROP_SALES_ORDER_DISCOUNT)));
            cropSalesOrder.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_SALES_ORDER_SHIPPING_CHARGES)));
            cropSalesOrder.setCustomerNotes(res.getString(res.getColumnIndex(CROP_SALES_ORDER_CUSTOMER_NOTES)));
            cropSalesOrder.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_SALES_ORDER_TERMS_AND_CONDITIONS)));
            cropSalesOrder.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropSalesOrder.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropSalesOrder);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropScouting> getCropScoutings(String userId, boolean synced) {
        openDB();
        ArrayList<CropScouting> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SCOUTING_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropScouting scouting = new CropScouting();
            scouting.setId(res.getString(res.getColumnIndex(CROP_SCOUTING_ID)));
            scouting.setUserId(res.getString(res.getColumnIndex(CROP_SCOUTING_USER_ID)));
            scouting.setCropId(res.getString(res.getColumnIndex(CROP_SCOUTING_CROP_ID)));
            scouting.setDate(res.getString(res.getColumnIndex(CROP_SCOUTING_DATE)));
            scouting.setMethod(res.getString(res.getColumnIndex(CROP_SCOUTING_METHOD)));
            scouting.setInfested(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTED)));
            scouting.setInfestationType(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTATION_TYPE)));
            scouting.setInfestation(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTATION)));
            scouting.setInfestationLevel(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTATION_LEVEL)));
            scouting.setCost(res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST)));
            scouting.setRemarks(res.getString(res.getColumnIndex(CROP_SCOUTING_REMARKS)));
            scouting.setFrequency(res.getFloat(res.getColumnIndex(CROP_SCOUTING_FREQUENCY)));
            scouting.setRecurrence(res.getString(res.getColumnIndex(CROP_SCOUTING_RECURRENCE)));
            scouting.setReminders(res.getString(res.getColumnIndex(CROP_SCOUTING_REMINDERS)));
            scouting.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_SCOUTING_DAYS_BEFORE)));
            scouting.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SCOUTING_REPEAT_UNTIL)));
            scouting.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            scouting.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(scouting);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropPurchaseOrder> getCropPurchaseOrders(String userId, boolean synced) {
        openDB();
        ArrayList<CropPurchaseOrder> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PURCHASE_ORDER_TABLE_NAME + " WHERE " + CROP_PURCHASE_ORDER_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();
        while (!res.isAfterLast()) {
            CropPurchaseOrder cropPurchaseOrder = new CropPurchaseOrder();
            cropPurchaseOrder.setId(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_ID)));
            cropPurchaseOrder.setUserId(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_USER_ID)));
            cropPurchaseOrder.setSupplierId(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_SUPPLIER_ID)));
            cropPurchaseOrder.setNumber(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_NUMBER)));
            cropPurchaseOrder.setMethod(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_DELIVERY_METHOD)));
            cropPurchaseOrder.setReferenceNumber(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_REFERENCE_NUMBER)));
            cropPurchaseOrder.setPurchaseDate(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_PURCHASE_DATE)));
            cropPurchaseOrder.setStatus(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_STATUS)));
            cropPurchaseOrder.setDeliveryDate(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_DELIVERY_DATE)));
            cropPurchaseOrder.setDiscount(res.getFloat(res.getColumnIndex(CROP_PURCHASE_ORDER_DISCOUNT)));
            cropPurchaseOrder.setNotes(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_NOTES)));
            cropPurchaseOrder.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS)));
            cropPurchaseOrder.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropPurchaseOrder.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropPurchaseOrder);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropSoilAnalysis> getCropSoilAnalysis(String userId, boolean synced) {
        openDB();
        ArrayList<CropSoilAnalysis> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SOIL_ANALYSIS_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
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
            crop.setFrequency(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_FREQUENCY)));
            crop.setRecurrence(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_RECURRENCE)));
            crop.setReminders(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_REMINDERS)));
            crop.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_REPEAT_UNTIL)));
            crop.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            crop.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(crop);
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

    public ArrayList<CropSupplier> getCropSuppliers(String userId, boolean synced) {
        openDB();
        ArrayList<CropSupplier> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SUPPLIER_TABLE_NAME + " where " + CROP_SUPPLIER_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
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
            cropSupplier.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropSupplier.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropSupplier);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropTask> getCropTasks(String userId, boolean synced) {
        openDB();
        ArrayList<CropTask> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_TASK_TABLE_NAME + " where " + CROP_TASK_USER_ID + " = ? AND " + CROP_SYNC_STATUS + " = ?", new String[]{userId, synced ? "yes" : "no"});
        res.moveToFirst();

        res.moveToFirst();

        while (!res.isAfterLast()) {

            CropTask task = new CropTask();
            task.setId(res.getString(res.getColumnIndex(CROP_TASK_ID)));
            task.setUserId(res.getString(res.getColumnIndex(CROP_TASK_USER_ID)));
            task.setCropId(res.getString(res.getColumnIndex(CROP_TASK_CROP_ID)));
            task.setEmployeeName(res.getString(res.getColumnIndex(CROP_TASK_EMPLOYEE_ID)));
            task.setDate(res.getString(res.getColumnIndex(CROP_TASK_DATE)));
            task.setTitle(res.getString(res.getColumnIndex(CROP_TASK_TITLE)));
            task.setType(res.getString(res.getColumnIndex(CROP_TASK_TYPE)));
            task.setStatus(res.getString(res.getColumnIndex(CROP_TASK_STATUS)));
            task.setDescription(res.getString(res.getColumnIndex(CROP_TASK_DESCRIPTION)));
            task.setFrequency(Float.parseFloat(res.getString(res.getColumnIndex(CROP_TASK_FREQUENCY))));
            task.setRecurrence(res.getString(res.getColumnIndex(CROP_TASK_RECURRENCE)));
            task.setReminders(res.getString(res.getColumnIndex(CROP_TASK_REMINDERS)));
            task.setDaysBefore(Float.parseFloat(res.getString(res.getColumnIndex(CROP_TASK_DAYS_BEFORE))));
            task.setRepeatUntil(res.getString(res.getColumnIndex(CROP_TASK_REPEAT_UNTIL)));
            task.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            task.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(task);
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

    public ArrayList<CropTransplanting> getCropTransplantings(String userId, boolean synced) {
        openDB();
        ArrayList<CropTransplanting> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_TRANSPLANTING_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropTransplanting transplanting = new CropTransplanting();
            transplanting.setId(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_ID)));
            transplanting.setUserId(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_USER_ID)));
            transplanting.setCropId(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_CROP_ID)));
            transplanting.setOperationDate(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE)));
            transplanting.setTotalSeedling(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_TOTAL_SEEDLING)));
            transplanting.setSeedlingPerHa(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_SEEDLINGS_PER_HA)));
            transplanting.setVarietyEarliness(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_VARIETY_EARLINESS)));
            transplanting.setCycleLength(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_CYCLE_LENGTH)));
            transplanting.setUnits(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_UNITS)));
            transplanting.setExpectedYield(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_EXPECTED_YIELD)));
            transplanting.setExpectedYieldPerHa(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_EXPECTED_YIELD_PER_HA)));
            transplanting.setFrequency(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_FREQUENCY)));
            transplanting.setOperator(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_OPERATOR)));
            transplanting.setRecurrence(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_RECURRENCE)));
            transplanting.setReminders(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_REMINDERS)));
            transplanting.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_DAYS_BEFORE)));
            transplanting.setRepeatUntil(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_REPEAT_UNTIL)));
            transplanting.setTotalCost(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));
            transplanting.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            transplanting.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(transplanting);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
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

    public ArrayList<CropCultivation> getCropCultivates(String userId, boolean synced) {
        openDB();
        ArrayList<CropCultivation> array_list = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CULTIVATION_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
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
            crop.setFrequency(res.getFloat(res.getColumnIndex(CROP_CULTIVATION_FREQUENCY)));
            crop.setRecurrence(res.getString(res.getColumnIndex(CROP_CULTIVATION_RECURRENCE)));
            crop.setReminders(res.getString(res.getColumnIndex(CROP_CULTIVATION_REMINDERS)));
            crop.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_CULTIVATION_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_CULTIVATION_REPEAT_UNTIL)));
            crop.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
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

    public ArrayList<CropIrrigation> getCropIrrigations(String userId, boolean synced) {
        openDB();
        ArrayList<CropIrrigation> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_IRRIGATION_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropIrrigation irrigation = new CropIrrigation();
            irrigation.setId(res.getString(res.getColumnIndex(CROP_IRRIGATION_ID)));
            irrigation.setUserId(res.getString(res.getColumnIndex(CROP_IRRIGATION_USER_ID)));
            irrigation.setCropId(res.getString(res.getColumnIndex(CROP_IRRIGATION_CROP_ID)));
            irrigation.setOperationDate(res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE)));
            irrigation.setSystemRate(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_SYSTEM_RATE)));
            irrigation.setStartTime(res.getString(res.getColumnIndex(CROP_IRRIGATION_START_TIME)));
            irrigation.setEndTime(res.getString(res.getColumnIndex(CROP_IRRIGATION_END_TIME)));
            irrigation.setUnits(res.getString(res.getColumnIndex(CROP_IRRIGATION_UNITS)));
            irrigation.setAreaIrrigated(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_AREA_IRRIGATED)));
            irrigation.setFrequency(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_FREQUENCY)));
            irrigation.setRecurrence(res.getString(res.getColumnIndex(CROP_IRRIGATION_RECURRENCE)));
            irrigation.setReminders(res.getString(res.getColumnIndex(CROP_IRRIGATION_REMINDERS)));
            irrigation.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_DAYS_BEFORE)));
            irrigation.setRepeatUntil(res.getString(res.getColumnIndex(CROP_IRRIGATION_REPEAT_UNTIL)));
            irrigation.setTotalCost(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));
            irrigation.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            irrigation.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(irrigation);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropMachineService> getCropMachineServices(String userId, boolean synced) {
        openDB();
        ArrayList<CropMachineService> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_MACHINE_SERVICE_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {

            CropMachineService service = new CropMachineService();
            service.setId(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_ID)));
            service.setMachineId(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_MACHINE_ID)));
            service.setEmployeeName(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_PERSONNEL)));
            service.setCurrentHours(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_CURRENT_HOURS)));
            service.setServiceType(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_TYPE)));
            service.setType(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_TYPE)));
            service.setDate(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DATE)));
            service.setDescription(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DESCRIPTION)));
            service.setRecurrence(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_RECURRENCE)));
            service.setReminders(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_REMINDERS)));
            service.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_DAYS_BEFORE)));
            service.setCost(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_COST)));
            service.setFrequency(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_FREQUENCY)));
            service.setRepeatUntil(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_REPEAT_UNTIL)));
            service.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            service.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(service);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropMachineTask> getCropMachineTasks(String userId, boolean synced) {
        openDB();
        ArrayList<CropMachineTask> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_MACHINE_TASK_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();
        while (!res.isAfterLast()) {

            CropMachineTask task = new CropMachineTask();
            task.setId(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_ID)));
            //   task.setUserId(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_USER_ID)));
            task.setMachineId(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_MACHINE_ID)));
//            task.setCropName(res.getString(res.getColumnIndex(CROP_MACHINE_NAME)));
            task.setEmployeeName(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_PERSONNEL)));
            task.setEndDate(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_START_DATE)));
            task.setStartDate(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_END_DATE)));
            task.setTitle(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_TITLE)));
            task.setFrequency(res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_FREQUENCY)));
            task.setStatus(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_STATUS)));
            task.setDescription(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_DESCRIPTION)));
            task.setRecurrence(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_RECURRENCE)));
            task.setReminders(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_REMINDERS)));
            task.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_DAYS_BEFORE)));
            task.setCost(res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_COST)));
            task.setRepeatUntil(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_REPEAT_UNTIL)));
            task.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            task.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(task);
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

    public ArrayList<CropInvoicePayment> getCropPayments(String userId, boolean synced) {
        openDB();
        ArrayList<CropInvoicePayment> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PAYMENT_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropInvoicePayment cropInvoicePayment = new CropInvoicePayment();
            cropInvoicePayment.setId(res.getString(res.getColumnIndex(CROP_PAYMENT_ID)));
            cropInvoicePayment.setUserId(res.getString(res.getColumnIndex(CROP_PAYMENT_USER_ID)));
            cropInvoicePayment.setAmount(res.getFloat(res.getColumnIndex(CROP_PAYMENT_AMOUNT)));
            cropInvoicePayment.setMode(res.getString(res.getColumnIndex(CROP_PAYMENT_MODE)));
            cropInvoicePayment.setDate(res.getString(res.getColumnIndex(CROP_PAYMENT_DATE)));
            cropInvoicePayment.setReferenceNo(res.getString(res.getColumnIndex(CROP_PAYMENT_REFERENCE_NO)));
            cropInvoicePayment.setPaymentNumber(res.getString(res.getColumnIndex(CROP_PAYMENT_NUMBER)));
            cropInvoicePayment.setNotes(res.getString(res.getColumnIndex(CROP_PAYMENT_NOTES)));
            cropInvoicePayment.setCustomerId(res.getString(res.getColumnIndex(CROP_PAYMENT_CUSTOMER_ID)));
            cropInvoicePayment.setInvoiceId(res.getString(res.getColumnIndex(CROP_PAYMENT_INVOICE_ID)));
            cropInvoicePayment.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropInvoicePayment.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(cropInvoicePayment);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropPaymentBill> getCropPaymentBills(String userId, boolean synced) {
        openDB();
        ArrayList<CropPaymentBill> array_list = new ArrayList();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PAYMENT_BILL_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
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
            paymentBill.setBillId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_BILL_ID)));
            paymentBill.setSupplierId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_SUPPLIER_ID)));
            paymentBill.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            paymentBill.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            array_list.add(paymentBill);
            res.moveToNext();
        }
        res.close();
        closeDB();
        return array_list;
    }

    public ArrayList<CropProductItem> getCropProductItems(String userId, boolean synced) {
        ArrayList<CropProductItem> items_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PRODUCT_ITEM_TABLE_NAME + " where " + CROP_SYNC_STATUS + " = ?", new String[]{synced ? "yes" : "no"});
        res.moveToFirst();
        while (!res.isAfterLast()) {
            CropProductItem item = new CropProductItem();
            item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
            item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
            ;
            item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
            item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
            item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
            item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
            item.setParentObjectType(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_TYPE)));
            item.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            items_list.add(item);
            res.moveToNext();
        }

        res.close();
        closeDB();
        return items_list;
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

    public CropMachine getCropMachine(String machineId, boolean isGlobal) {
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_MACHINE_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_MACHINE_TABLE_NAME + " where " + key + " = ? ", new String[]{machineId});
        res.moveToFirst();
        CropMachine machine = null;
        if (!res.isAfterLast()) {
            machine = new CropMachine();
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
            machine.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            machine.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return machine;
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
//            fertilizerApplication.setFertilizerName(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_NAME)));
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

    public CropBill getCropBillById(String billId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_BILL_ID;
        SQLiteDatabase db = database;
        Cursor res = db.rawQuery("select * from " + CROP_BILL_TABLE_NAME + " WHERE " + key + " = ?", new String[]{billId});
        res.moveToFirst();
        CropBill cropBill = null;

        if (!res.isAfterLast()) {
            cropBill = new CropBill();
            cropBill.setId(res.getString(res.getColumnIndex(CROP_BILL_ID)));
            cropBill.setUserId(res.getString(res.getColumnIndex(CROP_BILL_USER_ID)));
            cropBill.setSupplierId(res.getString(res.getColumnIndex(CROP_BILL_SUPPLIER_ID)));
            cropBill.setNumber(res.getString(res.getColumnIndex(CROP_BILL_NUMBER)));
            cropBill.setBillDate(res.getString(res.getColumnIndex(CROP_BILL_DATE)));
            cropBill.setDueDate(res.getString(res.getColumnIndex(CROP_BILL_DUE_DATE)));
            cropBill.setOrderNumber(res.getString(res.getColumnIndex(CROP_BILL_ORDER_NUMBER)));
            cropBill.setTerms(res.getString(res.getColumnIndex(CROP_BILL_TERMS)));
            cropBill.setDiscount(res.getFloat(res.getColumnIndex(CROP_BILL_DISCOUNT)));
            cropBill.setNotes(res.getString(res.getColumnIndex(CROP_BILL_NOTES)));
            cropBill.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropBill.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }

        res.close();
        closeDB();

        return cropBill;
    }

    public CropInvoice getCropInvoiceById(String invoiceId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_INVOICE_ID;
        SQLiteDatabase db = database;
        Cursor res = db.rawQuery("select * from " + CROP_INVOICE_TABLE_NAME + " WHERE " + key + " = ?", new String[]{invoiceId});
        res.moveToFirst();
        CropInvoice cropInvoice = null;

        if (!res.isAfterLast()) {
            cropInvoice = new CropInvoice();
            cropInvoice.setId(res.getString(res.getColumnIndex(CROP_INVOICE_ID)));
            cropInvoice.setUserId(res.getString(res.getColumnIndex(CROP_INVOICE_USER_ID)));
            cropInvoice.setCustomerId(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_ID)));
            cropInvoice.setNumber(res.getString(res.getColumnIndex(CROP_INVOICE_NO)));
            cropInvoice.setDate(res.getString(res.getColumnIndex(CROP_INVOICE_DATE)));
            cropInvoice.setDueDate(res.getString(res.getColumnIndex(CROP_INVOICE_DUE_DATE)));
            cropInvoice.setOrderNumber(res.getString(res.getColumnIndex(CROP_INVOICE_ORDER_NUMBER)));
            cropInvoice.setTerms(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS)));
            cropInvoice.setDiscount(res.getFloat(res.getColumnIndex(CROP_INVOICE_DISCOUNT)));
            cropInvoice.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_INVOICE_SHIPPING_CHARGES)));
            cropInvoice.setCustomerNotes(res.getString(res.getColumnIndex(CROP_INVOICE_CUSTOMER_NOTES)));
            cropInvoice.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_INVOICE_TERMS_AND_CONDITIONS)));
            cropInvoice.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropInvoice.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }


        res.close();

        closeDB();

        return cropInvoice;
    }

    public CropEstimate getCropEstimateById(String estimateId, boolean isGlobal) {
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_ESTIMATE_ID;
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_ESTIMATE_TABLE_NAME + " WHERE " + key + " = ? ", new String[]{estimateId});
        res.moveToFirst();
        CropEstimate cropEstimate = null;
        if (!res.isAfterLast()) {
            cropEstimate = new CropEstimate();
            cropEstimate.setId(res.getString(res.getColumnIndex(CROP_ESTIMATE_ID)));
            cropEstimate.setUserId(res.getString(res.getColumnIndex(CROP_ESTIMATE_USER_ID)));
            cropEstimate.setCustomerId(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_ID)));
            cropEstimate.setNumber(res.getString(res.getColumnIndex(CROP_ESTIMATE_NO)));
            cropEstimate.setReferenceNumber(res.getString(res.getColumnIndex(CROP_ESTIMATE_REFERENCE_NO)));
            cropEstimate.setDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_DATE)));
            cropEstimate.setExpiryDate(res.getString(res.getColumnIndex(CROP_ESTIMATE_EXP_DATE)));
            cropEstimate.setStatus(res.getString(res.getColumnIndex(CROP_ESTIMATE_STATUS)));
            cropEstimate.setDiscount(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_DISCOUNT)));
            cropEstimate.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_ESTIMATE_SHIPPING_CHARGES)));
            cropEstimate.setCustomerNotes(res.getString(res.getColumnIndex(CROP_ESTIMATE_CUSTOMER_NOTES)));
            cropEstimate.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_ESTIMATE_TERMS_AND_CONDITIONS)));
            cropEstimate.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropEstimate.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }

        res.close();
        closeDB();

        return cropEstimate;
    }

    public CropSalesOrder getCropSalesOrderById(String salesOrderId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_SALES_ORDER_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SALES_ORDER_TABLE_NAME + " WHERE " + key + " = ?", new String[]{salesOrderId});
        res.moveToFirst();
        CropSalesOrder cropSalesOrder = null;
        if (!res.isAfterLast()) {
            cropSalesOrder = new CropSalesOrder();
            cropSalesOrder.setId(res.getString(res.getColumnIndex(CROP_SALES_ORDER_ID)));
            cropSalesOrder.setUserId(res.getString(res.getColumnIndex(CROP_SALES_ORDER_USER_ID)));
            cropSalesOrder.setCustomerId(res.getString(res.getColumnIndex(CROP_SALES_ORDER_CUSTOMER_ID)));
            cropSalesOrder.setNumber(res.getString(res.getColumnIndex(CROP_SALES_ORDER_NO)));
            cropSalesOrder.setMethod(res.getString(res.getColumnIndex(CROP_SALES_ORDER_SHIPPING_METHOD)));
            cropSalesOrder.setReferenceNumber(res.getString(res.getColumnIndex(CROP_SALES_ORDER_REFERENCE_NO)));
            cropSalesOrder.setDate(res.getString(res.getColumnIndex(CROP_SALES_ORDER_DATE)));
            cropSalesOrder.setStatus(res.getString(res.getColumnIndex(CROP_SALES_ORDER_STATUS)));
            cropSalesOrder.setShippingDate(res.getString(res.getColumnIndex(CROP_SALES_ORDER_SHIPPING_DATE)));
            cropSalesOrder.setDiscount(res.getFloat(res.getColumnIndex(CROP_SALES_ORDER_DISCOUNT)));
            cropSalesOrder.setShippingCharges(res.getFloat(res.getColumnIndex(CROP_SALES_ORDER_SHIPPING_CHARGES)));
            cropSalesOrder.setCustomerNotes(res.getString(res.getColumnIndex(CROP_SALES_ORDER_CUSTOMER_NOTES)));
            cropSalesOrder.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_SALES_ORDER_TERMS_AND_CONDITIONS)));
            cropSalesOrder.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropSalesOrder.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }
        res.close();
        closeDB();
        return cropSalesOrder;
    }

    public CropPurchaseOrder getCropPurchaseOrderById(String orderId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_PURCHASE_ORDER_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PURCHASE_ORDER_TABLE_NAME + " WHERE " + key + " = ?", new String[]{orderId});
        res.moveToFirst();
        CropPurchaseOrder cropPurchaseOrder = null;
        if (!res.isAfterLast()) {
            cropPurchaseOrder = new CropPurchaseOrder();
            cropPurchaseOrder.setId(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_ID)));
            cropPurchaseOrder.setUserId(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_USER_ID)));
            cropPurchaseOrder.setSupplierId(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_SUPPLIER_ID)));
            cropPurchaseOrder.setNumber(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_NUMBER)));
            cropPurchaseOrder.setMethod(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_DELIVERY_METHOD)));
            cropPurchaseOrder.setReferenceNumber(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_REFERENCE_NUMBER)));
            cropPurchaseOrder.setPurchaseDate(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_PURCHASE_DATE)));
            cropPurchaseOrder.setStatus(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_STATUS)));
            cropPurchaseOrder.setDeliveryDate(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_DELIVERY_DATE)));
            cropPurchaseOrder.setDiscount(res.getFloat(res.getColumnIndex(CROP_PURCHASE_ORDER_DISCOUNT)));
            cropPurchaseOrder.setNotes(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_NOTES)));
            cropPurchaseOrder.setTermsAndConditions(res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS)));
            cropPurchaseOrder.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropPurchaseOrder.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }
        res.close();
        closeDB();
        return cropPurchaseOrder;
    }

    public CropContact getCropContact(String contactId, boolean isGlobal) {
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_CONTACT_ID;
        openDB();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CONTACT_TABLE_NAME + " where " + key + " = ?", new String[]{contactId});
        res.moveToFirst();
        CropContact contact = null;
        if (!res.isAfterLast()) {
            contact = new CropContact();
            contact.setId(res.getString(res.getColumnIndex(CROP_CONTACT_ID)));
            contact.setUserId(res.getString(res.getColumnIndex(CROP_CONTACT_USER_ID)));
            contact.setType(res.getString(res.getColumnIndex(CROP_CONTACT_TYPE)));
            contact.setName(res.getString(res.getColumnIndex(CROP_CONTACT_NAME)));
            contact.setBusinessName(res.getString(res.getColumnIndex(CROP_CONTACT_BUSINESS_NAME)));
            contact.setAddress(res.getString(res.getColumnIndex(CROP_CONTACT_ADDRESS)));
            contact.setPhoneNumber(res.getString(res.getColumnIndex(CROP_CONTACT_PHONE_NUMBER)));
            contact.setEmail(res.getString(res.getColumnIndex(CROP_CONTACT_EMAIL)));
            contact.setWebsite(res.getString(res.getColumnIndex(CROP_CONTACT_WEBSITE)));
            contact.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            contact.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return contact;
    }

    public CropEmployee getCropEmployee(String employeeId, boolean isGlobal) {
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_EMPLOYEE_ID;
        Cursor res = db.rawQuery("select * from " + CROP_EMPLOYEE_TABLE_NAME + " where " + key + " = ? ", new String[]{employeeId});
        res.moveToFirst();
        CropEmployee cropEmployee = null;
        if (!res.isAfterLast()) {
            cropEmployee = new CropEmployee();
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
            cropEmployee.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropEmployee.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }

        res.close();
        closeDB();

        return cropEmployee;
    }

    public CropSoilAnalysis getCropSoilAnalysisById(String analysisId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_SOIL_ANALYSIS_ID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SOIL_ANALYSIS_TABLE_NAME + " where " + key + " = ? ", new String[]{analysisId});
        res.moveToFirst();
        CropSoilAnalysis soilAnalysis = null;

        if (!res.isAfterLast()) {
            soilAnalysis = new CropSoilAnalysis();
            soilAnalysis.setId(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_ID)));
            soilAnalysis.setUserId(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_USER_ID)));
            soilAnalysis.setDate(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_DATE)));
            soilAnalysis.setAgronomist(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_AGRONOMIST)));
            soilAnalysis.setResult(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_RESULTS)));
            soilAnalysis.setOrganicMatter(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_ORGANIC_MATTER)));
            soilAnalysis.setPh(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_PH)));
            soilAnalysis.setCost(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_COST)));
            soilAnalysis.setFieldId(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_FIELD_ID)));
            soilAnalysis.setFrequency(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_FREQUENCY)));
            soilAnalysis.setRecurrence(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_RECURRENCE)));
            soilAnalysis.setReminders(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_REMINDERS)));
            soilAnalysis.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_DAYS_BEFORE)));
            soilAnalysis.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_REPEAT_UNTIL)));
            soilAnalysis.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            soilAnalysis.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return soilAnalysis;

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

    public CropIrrigation getCropIrrigation(String irrigationId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_IRRIGATION_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_IRRIGATION_TABLE_NAME + " where " + key + " = ? ", new String[]{irrigationId});
        res.moveToFirst();
        CropIrrigation irrigation = null;
        if (!res.isAfterLast()) {
            irrigation = new CropIrrigation();
            irrigation.setId(res.getString(res.getColumnIndex(CROP_IRRIGATION_ID)));
            irrigation.setUserId(res.getString(res.getColumnIndex(CROP_IRRIGATION_USER_ID)));
            irrigation.setCropId(res.getString(res.getColumnIndex(CROP_IRRIGATION_CROP_ID)));
            irrigation.setOperationDate(res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE)));
            irrigation.setSystemRate(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_SYSTEM_RATE)));
            irrigation.setStartTime(res.getString(res.getColumnIndex(CROP_IRRIGATION_START_TIME)));
            irrigation.setEndTime(res.getString(res.getColumnIndex(CROP_IRRIGATION_END_TIME)));
            irrigation.setUnits(res.getString(res.getColumnIndex(CROP_IRRIGATION_UNITS)));
            irrigation.setAreaIrrigated(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_AREA_IRRIGATED)));
            irrigation.setFrequency(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_FREQUENCY)));
            irrigation.setRecurrence(res.getString(res.getColumnIndex(CROP_IRRIGATION_RECURRENCE)));
            irrigation.setReminders(res.getString(res.getColumnIndex(CROP_IRRIGATION_REMINDERS)));
            irrigation.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_DAYS_BEFORE)));
            irrigation.setRepeatUntil(res.getString(res.getColumnIndex(CROP_IRRIGATION_REPEAT_UNTIL)));
            irrigation.setTotalCost(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));
            irrigation.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return irrigation;
    }

    public CropScouting getCropScouting(String scoutingId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_SCOUTING_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SCOUTING_TABLE_NAME + " where " + key + " = ? ", new String[]{scoutingId});
        res.moveToFirst();
        CropScouting scouting = null;

        if (!res.isAfterLast()) {
            scouting = new CropScouting();
            scouting.setId(res.getString(res.getColumnIndex(CROP_SCOUTING_ID)));
            scouting.setUserId(res.getString(res.getColumnIndex(CROP_SCOUTING_USER_ID)));
            scouting.setCropId(res.getString(res.getColumnIndex(CROP_SCOUTING_CROP_ID)));
            scouting.setDate(res.getString(res.getColumnIndex(CROP_SCOUTING_DATE)));
            scouting.setMethod(res.getString(res.getColumnIndex(CROP_SCOUTING_METHOD)));
            scouting.setInfested(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTED)));
            scouting.setInfestationType(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTATION_TYPE)));
            scouting.setInfestation(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTATION)));
            scouting.setInfestationLevel(res.getString(res.getColumnIndex(CROP_SCOUTING_INFESTATION_LEVEL)));
            scouting.setCost(res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST)));
            scouting.setRemarks(res.getString(res.getColumnIndex(CROP_SCOUTING_REMARKS)));
            scouting.setFrequency(res.getFloat(res.getColumnIndex(CROP_SCOUTING_FREQUENCY)));
            scouting.setRecurrence(res.getString(res.getColumnIndex(CROP_SCOUTING_RECURRENCE)));
            scouting.setReminders(res.getString(res.getColumnIndex(CROP_SCOUTING_REMINDERS)));
            scouting.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_SCOUTING_DAYS_BEFORE)));
            scouting.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SCOUTING_REPEAT_UNTIL)));
            scouting.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            scouting.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return scouting;
    }

    public CropCultivation getCropCultivate(String cultivationId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_CULTIVATION_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_CULTIVATION_TABLE_NAME + " where " + key + " = ? ", new String[]{cultivationId});
        res.moveToFirst();
        CropCultivation cultivation = null;

        if (!res.isAfterLast()) {
            cultivation = new CropCultivation();
            cultivation.setId(res.getString(res.getColumnIndex(CROP_CULTIVATION_ID)));
            cultivation.setUserId(res.getString(res.getColumnIndex(CROP_CULTIVATION_USER_ID)));
            cultivation.setDate(res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE)));
            cultivation.setCropId(res.getString(res.getColumnIndex(CROP_CULTIVATION_CROP_ID)));
            cultivation.setOperation(res.getString(res.getColumnIndex(CROP_CULTIVATION_OPERATION)));
            cultivation.setCost(res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST)));
            cultivation.setOperator(res.getString(res.getColumnIndex(CROP_CULTIVATION_OPERATOR)));
            cultivation.setNotes(res.getString(res.getColumnIndex(CROP_CULTIVATION_NOTES)));
            cultivation.setFrequency(res.getFloat(res.getColumnIndex(CROP_CULTIVATION_FREQUENCY)));
            cultivation.setRecurrence(res.getString(res.getColumnIndex(CROP_CULTIVATION_RECURRENCE)));
            cultivation.setReminders(res.getString(res.getColumnIndex(CROP_CULTIVATION_REMINDERS)));
            cultivation.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_CULTIVATION_DAYS_BEFORE)));
            cultivation.setRepeatUntil(res.getString(res.getColumnIndex(CROP_CULTIVATION_REPEAT_UNTIL)));
            cultivation.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cultivation.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));

            res.moveToNext();
        }

        res.close();
        closeDB();
        return cultivation;
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
//            fertilizerApplication.setFertilizerName(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_NAME)));
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

    public CropMachineTask getCropMachineTask(String taskId, boolean isGlobal) {
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_MACHINE_TASK_ID;
        Cursor res = db.rawQuery("select * from " + CROP_MACHINE_TASK_TABLE_NAME + " where " + key + " = ?", new String[]{taskId});
        res.moveToFirst();
        CropMachineTask task = null;
        if (!res.isAfterLast()) {
            task = new CropMachineTask();
            task.setId(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_ID)));
            task.setMachineId(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_MACHINE_ID)));
            // task.setCropName(res.getString(res.getColumnIndex(CROP_MACHINE_NAME)));
            task.setEmployeeName(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_PERSONNEL)));
            task.setEndDate(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_START_DATE)));
            task.setStartDate(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_END_DATE)));
            task.setTitle(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_TITLE)));
            task.setFrequency(res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_FREQUENCY)));
            task.setStatus(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_STATUS)));
            task.setDescription(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_DESCRIPTION)));
            task.setRecurrence(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_RECURRENCE)));
            task.setReminders(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_REMINDERS)));
            task.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_DAYS_BEFORE)));
            task.setCost(res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_COST)));
            task.setRepeatUntil(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_REPEAT_UNTIL)));
            task.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            task.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return task;
    }

    public CropMachineService getCropMachineService(String serviceId, boolean isGlobal) {
        openDB();

        String key = isGlobal ? CROP_GLOBAL_ID : CROP_MACHINE_SERVICE_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_MACHINE_SERVICE_TABLE_NAME + " where " + key + " = ?", new String[]{serviceId});
        res.moveToFirst();
        CropMachineService service = null;
        if (!res.isAfterLast()) {
            service = new CropMachineService();
            service.setId(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_ID)));
            service.setMachineId(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_MACHINE_ID)));
            service.setEmployeeName(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_PERSONNEL)));
            service.setCurrentHours(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_CURRENT_HOURS)));
            service.setServiceType(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_TYPE)));
            service.setType(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_TYPE)));
            service.setDate(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DATE)));
            service.setDescription(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DESCRIPTION)));
            service.setRecurrence(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_RECURRENCE)));
            service.setReminders(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_REMINDERS)));
            service.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_DAYS_BEFORE)));
            service.setCost(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_COST)));
            service.setFrequency(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_FREQUENCY)));
            service.setRepeatUntil(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_REPEAT_UNTIL)));
            service.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            service.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return service;
    }

    public CropTask getCropTask(String taskId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_TASK_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_TASK_TABLE_NAME + " where " + key + " = ?", new String[]{taskId});
        res.moveToFirst();
        CropTask task = null;
        if (!res.isAfterLast()) {
            task = new CropTask();
            task.setId(res.getString(res.getColumnIndex(CROP_TASK_ID)));
            task.setUserId(res.getString(res.getColumnIndex(CROP_TASK_USER_ID)));
            task.setCropId(res.getString(res.getColumnIndex(CROP_TASK_CROP_ID)));
            task.setEmployeeName(res.getString(res.getColumnIndex(CROP_TASK_EMPLOYEE_ID)));
            task.setDate(res.getString(res.getColumnIndex(CROP_TASK_DATE)));
            task.setTitle(res.getString(res.getColumnIndex(CROP_TASK_TITLE)));
            task.setType(res.getString(res.getColumnIndex(CROP_TASK_TYPE)));
            task.setStatus(res.getString(res.getColumnIndex(CROP_TASK_STATUS)));
            task.setDescription(res.getString(res.getColumnIndex(CROP_TASK_DESCRIPTION)));
            task.setFrequency(Float.parseFloat(res.getString(res.getColumnIndex(CROP_TASK_FREQUENCY))));
            task.setRecurrence(res.getString(res.getColumnIndex(CROP_TASK_RECURRENCE)));
            task.setReminders(res.getString(res.getColumnIndex(CROP_TASK_REMINDERS)));
            task.setDaysBefore(Float.parseFloat(res.getString(res.getColumnIndex(CROP_TASK_DAYS_BEFORE))));
            task.setRepeatUntil(res.getString(res.getColumnIndex(CROP_TASK_REPEAT_UNTIL)));
            task.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            task.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();

        return task;
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

    public CropTransplanting getCropTransplanting(String transplantingId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_TRANSPLANTING_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_TRANSPLANTING_TABLE_NAME + " where " + key + " = ?", new String[]{transplantingId});
        res.moveToFirst();
        CropTransplanting transplanting = null;
        if (!res.isAfterLast()) {
            transplanting = new CropTransplanting();
            transplanting.setId(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_ID)));
            transplanting.setUserId(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_USER_ID)));
            transplanting.setCropId(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_CROP_ID)));
            transplanting.setOperationDate(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE)));
            transplanting.setTotalSeedling(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_TOTAL_SEEDLING)));
            transplanting.setSeedlingPerHa(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_SEEDLINGS_PER_HA)));
            transplanting.setVarietyEarliness(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_VARIETY_EARLINESS)));
            transplanting.setCycleLength(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_CYCLE_LENGTH)));
            transplanting.setUnits(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_UNITS)));
            transplanting.setExpectedYield(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_EXPECTED_YIELD)));
            transplanting.setExpectedYieldPerHa(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_EXPECTED_YIELD_PER_HA)));
            transplanting.setFrequency(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_FREQUENCY)));
            transplanting.setOperator(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_OPERATOR)));
            transplanting.setRecurrence(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_RECURRENCE)));
            transplanting.setReminders(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_REMINDERS)));
            transplanting.setDaysBefore(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_DAYS_BEFORE)));
            transplanting.setRepeatUntil(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_REPEAT_UNTIL)));
            transplanting.setTotalCost(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));
            transplanting.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            transplanting.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return transplanting;
    }

    public CropProduct getCropProductById(String productId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_PRODUCT_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + CROP_PRODUCT_TABLE_NAME + " where ";
        Cursor res = db.rawQuery(query + key + " = ?", new String[]{productId});
        res.moveToFirst();
        CropProduct cropProduct = null;

        if (!res.isAfterLast()) {
            cropProduct = new CropProduct();
            cropProduct.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ID)));
            cropProduct.setUserId(res.getString(res.getColumnIndex(CROP_PRODUCT_USER_ID)));
            cropProduct.setType(res.getString(res.getColumnIndex(CROP_PRODUCT_TYPE)));
            cropProduct.setName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
            cropProduct.setCode(res.getString(res.getColumnIndex(CROP_PRODUCT_CODE)));
            cropProduct.setUnits(res.getString(res.getColumnIndex(CROP_PRODUCT_UNITS)));
            cropProduct.setLinkedAccount(res.getString(res.getColumnIndex(CROP_PRODUCT_LINKED_ACCOUNT)));
            cropProduct.setOpeningCost(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_COST)));
            cropProduct.setOpeningQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_OPENING_QUANTITY)));
            cropProduct.setSellingPrice(res.getFloat(res.getColumnIndex(CROP_PRODUCT_SELLING_PRICE)));
            cropProduct.setTaxRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_TAX_RATE)));
            cropProduct.setDescription(res.getString(res.getColumnIndex(CROP_PRODUCT_DESCRIPTION)));
            cropProduct.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropProduct.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();

        return cropProduct;

    }

    public CropInvoicePayment getCropPayment(String paymentId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_PAYMENT_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PAYMENT_TABLE_NAME + " where " + key + " = ?", new String[]{paymentId});
        res.moveToFirst();
        CropInvoicePayment cropInvoicePayment = null;

        while (!res.isAfterLast()) {
            cropInvoicePayment = new CropInvoicePayment();
            cropInvoicePayment.setId(res.getString(res.getColumnIndex(CROP_PAYMENT_ID)));
            cropInvoicePayment.setUserId(res.getString(res.getColumnIndex(CROP_PAYMENT_USER_ID)));
            cropInvoicePayment.setAmount(res.getFloat(res.getColumnIndex(CROP_PAYMENT_AMOUNT)));
            cropInvoicePayment.setMode(res.getString(res.getColumnIndex(CROP_PAYMENT_MODE)));
            cropInvoicePayment.setDate(res.getString(res.getColumnIndex(CROP_PAYMENT_DATE)));
            cropInvoicePayment.setReferenceNo(res.getString(res.getColumnIndex(CROP_PAYMENT_REFERENCE_NO)));
            cropInvoicePayment.setPaymentNumber(res.getString(res.getColumnIndex(CROP_PAYMENT_NUMBER)));
            cropInvoicePayment.setNotes(res.getString(res.getColumnIndex(CROP_PAYMENT_NOTES)));
            cropInvoicePayment.setCustomerId(res.getString(res.getColumnIndex(CROP_PAYMENT_CUSTOMER_ID)));
            cropInvoicePayment.setInvoiceId(res.getString(res.getColumnIndex(CROP_PAYMENT_INVOICE_ID)));
            cropInvoicePayment.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            cropInvoicePayment.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            cropInvoicePayment.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return cropInvoicePayment;
    }

    public CropPaymentBill getCropPaymentBill(String paymentId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_PAYMENT_BILL_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PAYMENT_BILL_TABLE_NAME + " where " + key + " = ?", new String[]{paymentId});
        res.moveToFirst();
        CropPaymentBill paymentBill = null;
        if (!res.isAfterLast()) {
            paymentBill = new CropPaymentBill();
            paymentBill.setId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_ID)));
            paymentBill.setUserId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_USER_ID)));
            paymentBill.setDate(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_DATE)));
            paymentBill.setAmount(Float.parseFloat(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_PAYMENT_MADE))));
            paymentBill.setMode(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_PAYMENT_MODE)));
            paymentBill.setPaidThrough(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_PAID_THROUGH)));
            paymentBill.setReferenceNumber(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_REFERENCE_NUMBER)));
            paymentBill.setNotes(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_NOTES)));
            paymentBill.setBillId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_BILL_ID)));
            paymentBill.setSupplierId(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_SUPPLIER_ID)));
            paymentBill.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            paymentBill.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }
        res.close();
        closeDB();
        return paymentBill;
    }

    public void updateCropProductItem(CropProductItem x) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID, x.getProductId());
        contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID, x.getParentObjectId());
        contentValues.put(CROP_PRODUCT_ITEM_QUANTITY, x.getQuantity());
        contentValues.put(CROP_PRODUCT_ITEM_TAX, x.getTax());
        contentValues.put(CROP_PRODUCT_ITEM_RATE, x.getRate());
        contentValues.put(CROP_PRODUCT_ITEM_TYPE, x.getParentObjectType());
        contentValues.put(CROP_SYNC_STATUS, x.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, x.getGlobalId());
        database.update(CROP_PRODUCT_ITEM_TABLE_NAME, contentValues, CROP_PRODUCT_ITEM_ID + " = ?", new String[]{x.getId()});
        closeDB();
    }

    public void deleteCropProductItems(ArrayList<String> ids) {

        for (String id : ids) {
            CropProductItem item = getCropProductItem(id, false);
            openDB();
            database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_ID + " = ?", new String[]{id});
            if (item != null) {
                recordDeletedRecord("productItem", item.getGlobalId());
            }
            closeDB();
        }

    }

    public void insertCropProductItem(CropProductItem x) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID, x.getProductId());
        contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID, x.getParentObjectId());
        contentValues.put(CROP_PRODUCT_ITEM_QUANTITY, x.getQuantity());
        contentValues.put(CROP_PRODUCT_ITEM_TAX, x.getTax());
        contentValues.put(CROP_PRODUCT_ITEM_RATE, x.getRate());
        contentValues.put(CROP_PRODUCT_ITEM_TYPE, x.getParentObjectType());
        contentValues.put(CROP_SYNC_STATUS, x.getSyncStatus());
        contentValues.put(CROP_GLOBAL_ID, x.getGlobalId());
        database.insert(CROP_PRODUCT_ITEM_TABLE_NAME, null, contentValues);
        closeDB();
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

    public CropProductItem getCropProductItem(String itemId, boolean isGlobal) {
        openDB();
        String key = isGlobal ? CROP_GLOBAL_ID : CROP_PRODUCT_ITEM_ID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_PRODUCT_ITEM_TABLE_NAME + " where " + key + " = '" + itemId + "'", null);
        res.moveToFirst();
        CropProductItem item = null;
        if (!res.isAfterLast()) {
            item = new CropProductItem();
            item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
            item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
            ;
            item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
            item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
            item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
            item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
            item.setParentObjectType(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_TYPE)));
            item.setSyncStatus(res.getString(res.getColumnIndex(CROP_SYNC_STATUS)));
            item.setGlobalId(res.getString(res.getColumnIndex(CROP_GLOBAL_ID)));
            res.moveToNext();
        }

        res.close();
        closeDB();
        return item;
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
}


