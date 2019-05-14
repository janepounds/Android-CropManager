package com.myfarmnow.myfarmcrop.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropDashboardActivity;
import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropBill;
import com.myfarmnow.myfarmcrop.models.CropContact;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropEstimate;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;

import com.myfarmnow.myfarmcrop.models.CropField;

import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.models.CropIrrigation;
import com.myfarmnow.myfarmcrop.models.CropItem;
import com.myfarmnow.myfarmcrop.models.CropMachine;
import com.myfarmnow.myfarmcrop.models.CropMachineService;
import com.myfarmnow.myfarmcrop.models.CropMachineTask;
import com.myfarmnow.myfarmcrop.models.CropNote;
import com.myfarmnow.myfarmcrop.models.CropNotification;
import com.myfarmnow.myfarmcrop.models.CropPayment;
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
import com.myfarmnow.myfarmcrop.models.GraphRecord;
import com.myfarmnow.myfarmcrop.singletons.CropDatabaseInitializerSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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
    public static final String CROP_BILL_TABLE_NAME ="crop_bill";
    public static final String CROP_ITEM_TABLE_NAME ="crop_item";
    public static final String CROP_FERTILIZER_TABLE_NAME ="crop_fertilizer";
    public static final String CROP_SETTINGS_TABLE_NAME ="crop_settings";
    public static final String CROP_NOTIFICATION_TABLE_NAME ="crop_notification";
    public static final String CROP_MACHINE_TASK_TABLE_NAME ="crop_machine_task";
    public static final String CROP_NOTE_TABLE_NAME ="crop_notes";
    public static final String CROP_MACHINE_SERVICE_TABLE_NAME ="crop_machine_services";
    public static final String CROP_IRRIGATION_TABLE_NAME ="crop_irrigation";
    public static final String CROP_TRANSPLANTING_TABLE_NAME ="crop_transplanting";
    public static final String CROP_SCOUTING_TABLE_NAME ="crop_scouting";
    public static final String CROP_HARVEST_TABLE_NAME ="crop_harvest";
    public static final String CROP_CONTACT_TABLE_NAME ="crop_contact";

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
    public static final String CROP_INVENTORY_SEEDS_TYPE ="seedType";

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
    public static final String CROP_CROP_HARVEST_UNITS ="harvestUnits";
    public static final String CROP_CROP_ESTIMATED_YIELD ="estimatedYield";
    public static final String CROP_CROP_ESTIMATED_REVENUE ="estimatedRevenue";

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
    public static final String CROP_CULTIVATION_FREQUENCY ="frequency";
    public static final String CROP_CULTIVATION_REPEAT_UNTIL ="repeatUntil";
    public static final String CROP_CULTIVATION_DAYS_BEFORE ="daysBefore";
    public static final String CROP_CULTIVATION_RECURRENCE ="recurrence";
    public static final String CROP_CULTIVATION_REMINDERS ="reminders";


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
    public static final String CROP_FERTILIZER_APPLICATION_FREQUENCY ="frequency";
    public static final String CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL ="repeatUntil";
    public static final String CROP_FERTILIZER_APPLICATION_DAYS_BEFORE ="daysBefore";
    public static final String CROP_FERTILIZER_APPLICATION_RECURRENCE ="recurrence";
    public static final String CROP_FERTILIZER_APPLICATION_REMINDERS ="reminders";


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
    public static final String CROP_SPRAYING_FREQUENCY ="frequency";
    public static final String CROP_SPRAYING_REPEAT_UNTIL ="repeatUntil";
    public static final String CROP_SPRAYING_DAYS_BEFORE ="daysBefore";
    public static final String CROP_SPRAYING_RECURRENCE ="recurrence";
    public static final String CROP_SPRAYING_REMINDERS ="reminders";



    public static final String CROP_FIELD_ID ="id";
    public static final String CROP_FIELD_USER_ID ="userId";
    public static final String CROP_FIELD_NAME="fieldName";
    public static final String CROP_FIELD_SOIL_CATEGORY="soilCategory";
    public static final String CROP_FIELD_SOIL_TYPE="soilType";
    public static final String CROP_FIELD_WATERCOURSE="watercourse";
    public static final String CROP_FIELD_TOTAL_AREA="totalArea";
    public static final String CROP_FIELD_CROPPABLE_AREA="croppableArea";
    public static final String CROP_FIELD_UNITS="units";
    public static final String CROP_FIELD_FIELD_TYPE="fieldType";
    public static final String CROP_FIELD_LAYOUT_TYPE="layoutType";
    public static final String CROP_FIELD_STATUS="status";

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
    public static final String CROP_SOIL_ANALYSIS_FREQUENCY ="frequency";
    public static final String CROP_SOIL_ANALYSIS_REPEAT_UNTIL ="repeatUntil";
    public static final String CROP_SOIL_ANALYSIS_DAYS_BEFORE ="daysBefore";
    public static final String CROP_SOIL_ANALYSIS_RECURRENCE ="recurrence";
    public static final String CROP_SOIL_ANALYSIS_REMINDERS ="reminders";



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
    public static final String CROP_PRODUCT_ITEM_PARENT_OBJECT_ID ="estimateId";
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
    public static final String CROP_TASK_FREQUENCY ="frequency";
    public static final String CROP_TASK_REPEAT_UNTIL ="repeatUntil";
    public static final String CROP_TASK_DAYS_BEFORE ="daysBefore";




    public static final String CROP_PRODUCT_ITEM_TYPE_SALES_ORDER = "salesOrder";
    public static final String CROP_PRODUCT_ITEM_TYPE_ESTIMATE = "estimate";
    public static final String CROP_PRODUCT_ITEM_TYPE_INVOICE = "invoice";
    public static final String CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER = "purchaseOrder";
    public static final String CROP_PRODUCT_ITEM_TYPE_BILL = "bill";

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

    public static final String CROP_BILL_ID ="id";
    public static final String CROP_BILL_USER_ID ="userId";
    public static final String CROP_BILL_SUPPLIER_ID ="supplierId";
    public static final String CROP_BILL_ORDER_NUMBER ="orderNumber";
    public static final String CROP_BILL_NUMBER ="number";
    public static final String CROP_BILL_DATE ="billDate";
    public static final String CROP_BILL_DUE_DATE ="dueDate";
    public static final String CROP_BILL_TERMS ="terms";
    public static final String CROP_BILL_DISCOUNT ="discount";
    public static final String CROP_BILL_NOTES ="notes";
    public static final String CROP_BILL_STATUS ="status";

    public static final String CROP_PAYMENT_BILL_ID ="id";
    public static final String CROP_PAYMENT_BILL_USER_ID ="userId";
    public static final String CROP_PAYMENT_BILL_SUPPLIER_ID ="supplierId";
    public static final String CROP_PAYMENT_BILL_DATE ="date";
    public static final String CROP_PAYMENT_BILL_PAYMENT_MADE ="amount";
    public static final String CROP_PAYMENT_BILL_PAYMENT_MODE="mode";
    public static final String CROP_PAYMENT_BILL_PAID_THROUGH ="paidThrough";
    public static final String CROP_PAYMENT_BILL_REFERENCE_NUMBER ="referenceNumber";
    public static final String CROP_PAYMENT_BILL_NOTES ="notes";
    public static final String CROP_PAYMENT_BILL_BILL_ID ="billId";

    public static final String CROP_ITEM_ID ="id";
    public static final String CROP_ITEM_NAME ="name";
    public static final String CROP_ITEM_N_COMPOSITION ="nComposition";
    public static final String CROP_ITEM_P_COMPOSITION ="pComposition";
    public static final String CROP_ITEM_K_COMPOSITION ="kComposition";
    public static final String CROP_ITEM_IMAGE_RESOURCE_ID="imageResourceId";
    public static final String CROP_ITEM_N_REMOVED ="nRemoved";
    public static final String CROP_ITEM_P_REMOVED ="pRemoved";
    public static final String CROP_ITEM_K_REMOVED ="kRemoved";
    public static final String CROP_ITEM_IS_FOR ="isFor";

    public static final String CROP_FERTILIZER_ID ="id";
    public static final String CROP_FERTILIZER_TYPE ="type";
    public static final String CROP_FERTILIZER_NAME ="name";
    public static final String CROP_FERTILIZER_N_PERCENTAGE ="nPercentage";
    public static final String CROP_FERTILIZER_P_PERCENTAGE ="pPercentage";
    public static final String CROP_FERTILIZER_K_PERCENTAGE ="kPercentage";



    public static final String CROP_MACHINE_TASK_ID = "id";
    public static final String CROP_MACHINE_TASK_MACHINE_ID = "machineId";
    public static final String CROP_MACHINE_TASK_USER_ID = "userId";
    public static final String CROP_MACHINE_TASK_START_DATE= "startDate";
    public static final String CROP_MACHINE_TASK_END_DATE= "endDate";
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
    public static final String CROP_NOTE_NOTES= "notes";
    public static final String CROP_NOTE_IS_FOR= "isFor";

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

    public static final String CROP_IRRIGATION_ID ="id";
    public static final String CROP_IRRIGATION_USER_ID ="userId";
    public static final String CROP_IRRIGATION_CROP_ID ="cropId";
    public static final String CROP_IRRIGATION_DATE ="date";
    public static final String CROP_IRRIGATION_SYSTEM_RATE ="systemRate";
    public static final String CROP_IRRIGATION_START_TIME ="startTime";
    public static final String CROP_IRRIGATION_END_TIME ="endTime";
    public static final String CROP_IRRIGATION_AREA_IRRIGATED ="areaIrrigated";
    public static final String CROP_IRRIGATION_UNITS ="units";
    public static final String CROP_IRRIGATION_RECURRENCE ="recurrence";
    public static final String CROP_IRRIGATION_REMINDERS ="reminders";
    public static final String CROP_IRRIGATION_FREQUENCY ="frequency";
    public static final String CROP_IRRIGATION_REPEAT_UNTIL ="repeatUntil";
    public static final String CROP_IRRIGATION_DAYS_BEFORE ="daysBefore";
    public static final String CROP_IRRIGATION_COST ="totalCost";


    public static final String CROP_TRANSPLANTING_ID ="id";
    public static final String CROP_TRANSPLANTING_USER_ID ="userId";
    public static final String CROP_TRANSPLANTING_CROP_ID ="cropId";
    public static final String CROP_TRANSPLANTING_DATE ="operationDate";
    public static final String CROP_TRANSPLANTING_TOTAL_SEEDLING ="totalSeedling";
    public static final String CROP_TRANSPLANTING_SEEDLINGS_PER_HA ="seedlingsPerHa";
    public static final String CROP_TRANSPLANTING_VARIETY_EARLINESS ="varietyEarliness";
    public static final String CROP_TRANSPLANTING_CYCLE_LENGTH ="cycleLength";
    public static final String CROP_TRANSPLANTING_UNITS ="units";
    public static final String CROP_TRANSPLANTING_EXPECTED_YIELD ="expectedYield";
    public static final String CROP_TRANSPLANTING_EXPECTED_YIELD_PER_HA ="expectedYieldPerHa";
    public static final String CROP_TRANSPLANTING_OPERATOR ="operator";
    public static final String CROP_TRANSPLANTING_FREQUENCY ="frequency";
    public static final String CROP_TRANSPLANTING_REPEAT_UNTIL ="repeatUntil";
    public static final String CROP_TRANSPLANTING_DAYS_BEFORE ="daysBefore";
    public static final String CROP_TRANSPLANTING_RECURRENCE ="recurrence";
    public static final String CROP_TRANSPLANTING_REMINDERS ="reminders";
    public static final String CROP_TRANSPLANTING_COST ="totalCost";


    public static final String CROP_SCOUTING_ID ="id";
    public static final String CROP_SCOUTING_USER_ID ="userId";
    public static final String CROP_SCOUTING_CROP_ID ="cropId";
    public static final String CROP_SCOUTING_DATE ="date";
    public static final String CROP_SCOUTING_METHOD ="method";
    public static final String CROP_SCOUTING_INFESTED ="infested";
    public static final String CROP_SCOUTING_INFESTATION_TYPE ="infestationType";
    public static final String CROP_SCOUTING_INFESTATION="infestation";
    public static final String CROP_SCOUTING_INFESTATION_LEVEL="infestationLevel";
    public static final String CROP_SCOUTING_COST="cost";
    public static final String CROP_SCOUTING_REMARKS="remarks";
    public static final String CROP_SCOUTING_FREQUENCY ="frequency";
    public static final String CROP_SCOUTING_REPEAT_UNTIL ="repeatUntil";
    public static final String CROP_SCOUTING_DAYS_BEFORE ="daysBefore";
    public static final String CROP_SCOUTING_RECURRENCE ="recurrence";
    public static final String CROP_SCOUTING_REMINDERS ="reminders";


    public static final String CROP_HARVEST_ID ="id";
    public static final String CROP_HARVEST_USER_ID ="userId";
    public static final String CROP_HARVEST_CROP_ID ="cropId";
    public static final String CROP_HARVEST_EMPLOYEE_ID ="employeeId";
    public static final String CROP_HARVEST_DATE="date";
    public static final String CROP_HARVEST_METHOD ="method";
    public static final String CROP_HARVEST_UNITS="harvestUnits";
    public static final String CROP_HARVEST_QUANTITY ="quantity";
    public static final String CROP_HARVEST_OPERATOR ="operator";
    public static final String CROP_HARVEST_STATUS ="status";
    public static final String CROP_HARVEST_DATE_SOLD ="dateSold";
    public static final String CROP_HARVEST_CUSTOMER ="customer";
    public static final String CROP_HARVEST_PRICE ="price";
    public static final String CROP_HARVEST_QUANTITY_SOLD ="quantitySold";
    public static final String CROP_HARVEST_STORAGE_DATE ="storageDate";
    public static final String CROP_HARVEST_QUANTITY_STORED ="quantityStored";
    public static final String CROP_HARVEST_COST="cost";
    public static final String CROP_HARVEST_FREQUENCY ="frequency";
    public static final String CROP_HARVEST_REPEAT_UNTIL ="repeatUntil";
    public static final String CROP_HARVEST_DAYS_BEFORE ="daysBefore";
    public static final String CROP_HARVEST_RECURRENCE ="recurrence";
    public static final String CROP_HARVEST_REMINDERS ="reminders";

    public static final String CROP_CONTACT_ID ="id";
    public static final String CROP_CONTACT_USER_ID ="userId";
    public static final String CROP_CONTACT_TYPE ="type";
    public static final String CROP_CONTACT_NAME ="name";
    public static final String CROP_CONTACT_BUSINESS_NAME ="businessName";
    public static final String CROP_CONTACT_ADDRESS="address";
    public static final String CROP_CONTACT_PHONE_NUMBER="phoneNumber";
    public static final String CROP_CONTACT_EMAIL ="email";
    public static final String CROP_CONTACT_WEBSITE ="website";

    public static final String CROP_SETTINGS_ID ="id";
    public static final String CROP_SETTINGS_USER_ID ="userId";
    public static final String CROP_SETTINGS_DATE_FORMAT ="dateFormat";
    public static final String CROP_SETTINGS_WEIGHT_UNITS ="weightUnits";
    public static final String CROP_SETTINGS_AREA_UNITS ="areaUnits";
    public static final String CROP_SETTINGS_CURRENCY ="currency";

    public static final String CROP_NOTIFICATION_ID ="id";
    public static final String CROP_NOTIFICATION_USER_ID ="userId";
    public static final String CROP_NOTIFICATION_DATE ="date";
    public static final String CROP_NOTIFICATION_MESSAGE ="message";
    public static final String CROP_NOTIFICATION_STATUS ="status";
    public static final String CROP_NOTIFICATION_ACTION_DATE ="actionDate";
    public static final String CROP_NOTIFICATION_TYPE ="type";

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

        String crop_contact_insert_query = " CREATE TABLE IF NOT EXISTS "+ CROP_CONTACT_TABLE_NAME+ " ( "+CROP_CONTACT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                CROP_CONTACT_USER_ID+" TEXT, "+CROP_CONTACT_TYPE+" TEXT NOT NULL, "+CROP_CONTACT_NAME+" TEXT NOT NULL, "+CROP_CONTACT_BUSINESS_NAME+" TEXT, "+
                CROP_CONTACT_ADDRESS+" TEXT NOT NULL, "+CROP_CONTACT_PHONE_NUMBER+" TEXT NOT NULL, "+CROP_CONTACT_EMAIL+" TEXT, "+CROP_CONTACT_WEBSITE+" TEXT "+" ) ";


        String crop_harvest_insert_query =" CREATE TABLE IF NOT EXISTS " + CROP_HARVEST_TABLE_NAME+ " ( "+CROP_HARVEST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                CROP_HARVEST_USER_ID+" TEXT, "+CROP_HARVEST_CROP_ID+" TEXT, "+CROP_HARVEST_EMPLOYEE_ID+ " TEXT, "+CROP_HARVEST_DATE+" TEXT NOT NULL, "+CROP_HARVEST_METHOD+ " TEXT, "+
                CROP_HARVEST_UNITS+" TEXT NOT NULL, "+CROP_HARVEST_QUANTITY+" REAL NOT NULL, "+CROP_HARVEST_OPERATOR+" TEXT, "+CROP_HARVEST_STATUS+" TEXT NOT NULL, "+
                CROP_HARVEST_DATE_SOLD+" TEXT, "+CROP_HARVEST_CUSTOMER+" TEXT, "+CROP_HARVEST_PRICE+" REAL DEFAULT 0, "+CROP_HARVEST_QUANTITY_SOLD+" REAL, "+
                CROP_HARVEST_STORAGE_DATE+" TEXT, "+CROP_HARVEST_QUANTITY_STORED+" REAL, "+CROP_HARVEST_COST+ " REAL, "+CROP_HARVEST_REPEAT_UNTIL + " TEXT, " +CROP_HARVEST_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_HARVEST_RECURRENCE + " TEXT NOT NULL, " +CROP_HARVEST_FREQUENCY + " REAL DEFAULT 0, " + CROP_HARVEST_REMINDERS + " TEXT NOT NULL " +" ) ";

        String crop_scouting_insert_query = "CREATE TABLE IF NOT EXISTS "+CROP_SCOUTING_TABLE_NAME+" ( "+CROP_SCOUTING_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+CROP_SCOUTING_USER_ID+" TEXT , "+
                CROP_SCOUTING_CROP_ID+" TEXT, "+CROP_SCOUTING_DATE+" TEXT NOT NULL, "+CROP_SCOUTING_METHOD+" TEXT, "+CROP_SCOUTING_INFESTED+" TEXT NOT NULL, "+
                CROP_SCOUTING_INFESTATION_TYPE+" TEXT, "+CROP_SCOUTING_INFESTATION+" TEXT, "+CROP_SCOUTING_INFESTATION_LEVEL+" TEXT, "+
                CROP_SCOUTING_COST+" REAL NOT NULL DEFAULT 0, "+ CROP_SCOUTING_REMARKS+" TEXT, "+CROP_SCOUTING_REPEAT_UNTIL + " TEXT, " +CROP_SCOUTING_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_SCOUTING_RECURRENCE + " TEXT NOT NULL, " +CROP_SCOUTING_FREQUENCY + " REAL DEFAULT 0, " + CROP_SCOUTING_REMINDERS + " TEXT NOT NULL " +" ) ";


        String crop_transplanting_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_TRANSPLANTING_TABLE_NAME+" ( "+CROP_TRANSPLANTING_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_TRANSPLANTING_USER_ID+" TEXT, "+ CROP_TRANSPLANTING_CROP_ID +" TEXT, "+CROP_TRANSPLANTING_DATE+" TEXT NOT NULL, "+CROP_TRANSPLANTING_TOTAL_SEEDLING+" REAL, "+
                CROP_TRANSPLANTING_SEEDLINGS_PER_HA+ " REAL, "+CROP_TRANSPLANTING_VARIETY_EARLINESS+" TEXT NOT NULL, "+CROP_TRANSPLANTING_CYCLE_LENGTH+" REAL NOT NULL, "+
                CROP_TRANSPLANTING_UNITS+" TEXT, "+CROP_TRANSPLANTING_EXPECTED_YIELD+" REAL DEFAULT 0, "+CROP_TRANSPLANTING_EXPECTED_YIELD_PER_HA +" REAL DEFAULT 0, "+
                CROP_TRANSPLANTING_OPERATOR+" TEXT NOT NULL, "+CROP_TRANSPLANTING_COST+" REAL NOT NULL, "+CROP_TRANSPLANTING_REPEAT_UNTIL + " TEXT, " +CROP_TRANSPLANTING_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_TRANSPLANTING_RECURRENCE + " TEXT NOT NULL, " +CROP_TRANSPLANTING_FREQUENCY + " REAL DEFAULT 0, " + CROP_TRANSPLANTING_REMINDERS + " TEXT NOT NULL " +" ) ";


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
                CROP_INVENTORY_SEEDS_DRESSING+" TEXT,"+CROP_INVENTORY_FERTILIZER_QUANTITY+" REAL NOT NULL,"+CROP_INVENTORY_SEEDS_BATCH_NUMBER+" TEXT NOT NULL,"+ CROP_INVENTORY_SEEDS_TYPE +" TEXT NOT NULL,"+
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
                CROP_CROP_COST+" REAL NOT NULL,"+CROP_CROP_SEED_ID+" TEXT ,"+CROP_CROP_HARVEST_UNITS+" TEXT ,"+CROP_CROP_ESTIMATED_YIELD+" REAL DEFAULT 0,"+
                CROP_CROP_ESTIMATED_REVENUE+" REAL DEFAULT 0,"+CROP_CROP_RATE+" REAL DEFAULT 0,"+CROP_CROP_PLANTING_METHOD+" TEXT NOT NULL )";

        String crop_cultivate_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_CULTIVATION_TABLE_NAME+" ( "+CROP_CULTIVATION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_CULTIVATION_USER_ID+" TEXT,"+CROP_CULTIVATION_CROP_ID+" TEXT NOT NULL,"+ CROP_CULTIVATION_DATE+" TEXT NOT NULL,"+ CROP_CULTIVATION_OPERATION+" TEXT NOT NULL,"+CROP_CULTIVATION_OPERATOR+" TEXT NOT NULL,"+
                CROP_CULTIVATION_COST+" REAL,"+CROP_CULTIVATION_NOTES+" TEXT, "+CROP_CULTIVATION_REPEAT_UNTIL + " TEXT, " +CROP_CULTIVATION_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_CULTIVATION_RECURRENCE + " TEXT NOT NULL, " +CROP_CULTIVATION_FREQUENCY + " REAL DEFAULT 0, " + CROP_CULTIVATION_REMINDERS + " TEXT NOT NULL " +" ) ";

        String crop_fertilizer_application_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+" ( "+CROP_FERTILIZER_APPLICATION_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_FERTILIZER_APPLICATION_USER_ID+" TEXT,"+CROP_FERTILIZER_APPLICATION_CROP_ID+" TEXT NOT NULL,"+ CROP_FERTILIZER_APPLICATION_DATE+" TEXT NOT NULL,"+CROP_FERTILIZER_APPLICATION_OPERATOR+" TEXT,"+
                CROP_FERTILIZER_APPLICATION_METHOD+" REAL NOT NULL,"+CROP_FERTILIZER_APPLICATION_REASON+" TEXT, "+CROP_FERTILIZER_APPLICATION_FERTILIZER_FORM+" TEXT NOT NULL, "+CROP_FERTILIZER_APPLICATION_FERTILIZER_ID+" TEXT NOT NULL,"+
                 CROP_FERTILIZER_APPLICATION_RATE+" REAL NOT NULL ,"+CROP_FERTILIZER_APPLICATION_COST+" REAL, "+CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL + " TEXT, " +CROP_FERTILIZER_APPLICATION_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_FERTILIZER_APPLICATION_RECURRENCE + " TEXT NOT NULL, " +CROP_FERTILIZER_APPLICATION_FREQUENCY + " REAL DEFAULT 0, " + CROP_FERTILIZER_APPLICATION_REMINDERS + " TEXT NOT NULL " +" ) ";


        String crop_spraying_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_SPRAYING_TABLE_NAME+" ( "+CROP_SPRAYING_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_SPRAYING_USER_ID+" TEXT,"+CROP_SPRAYING_CROP_ID+" TEXT NOT NULL,"+ CROP_SPRAYING_DATE +" TEXT NOT NULL,"+CROP_SPRAYING_START_TIME+" TEXT,"+
                CROP_SPRAYING_END_TIME+" TEXT,"+CROP_SPRAYING_OPERATOR+" TEXT NOT NULL,"+
                CROP_SPRAYING_WATER_VOLUME+" REAL ,"+ CROP_SPRAYING_WATER_CONDITION+" TEXT,"+CROP_SPRAYING_WIND_DIRECTION+" TEXT, "+CROP_SPRAYING_EQUIPMENT_USED+" TEXT ,"+
                CROP_SPRAYING_SPRAY_ID +" TEXT NOT NULL,"+ CROP_SPRAYING_RATE+" REAL NOT NULL ,"+CROP_SPRAYING_TREATMENT_REASON+" TEXT ,"+CROP_SPRAYING_COST+" REAL, "+CROP_SPRAYING_REPEAT_UNTIL + " TEXT, " +CROP_SPRAYING_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_SPRAYING_RECURRENCE + " TEXT NOT NULL, " +CROP_SPRAYING_FREQUENCY + " REAL DEFAULT 0, " + CROP_SPRAYING_REMINDERS + " TEXT NOT NULL " +" ) ";

        String crop_field_insert_query ="CREATE TABLE IF NOT EXISTS "+ CROP_FIELDS_TABLE_NAME +" ( "+ CROP_FIELD_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_FIELD_USER_ID+" TEXT,"+CROP_FIELD_NAME+" TEXT NOT NULL,"+ CROP_FIELD_SOIL_CATEGORY+" TEXT,"+ CROP_FIELD_SOIL_TYPE+" TEXT,"+CROP_FIELD_WATERCOURSE+" TEXT,"+
                CROP_FIELD_TOTAL_AREA +" REAL NOT NULL ,"+ CROP_FIELD_CROPPABLE_AREA+" REAL ,"+CROP_FIELD_FIELD_TYPE+" TEXT NOT NULL ,"+
                CROP_FIELD_LAYOUT_TYPE+" TEXT ,"+CROP_FIELD_STATUS+" TEXT NOT NULL ,"+ CROP_FIELD_UNITS+" TEXT NOT NULL)";

        String crop_soil_analysis_insert_query ="CREATE TABLE IF NOT EXISTS "+ CROP_SOIL_ANALYSIS_TABLE_NAME +" ( "+ CROP_SOIL_ANALYSIS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_SOIL_ANALYSIS_USER_ID+" TEXT ,"+CROP_SOIL_ANALYSIS_FIELD_ID+" TEXT,"+CROP_SOIL_ANALYSIS_DATE+" TEXT NOT NULL,"+ CROP_SOIL_ANALYSIS_PH+" REAL,"+ CROP_SOIL_ANALYSIS_ORGANIC_MATTER+" TEXT,"+
                CROP_SOIL_ANALYSIS_AGRONOMIST +" TEXT NOT NULL ,"+ CROP_SOIL_ANALYSIS_COST+" REAL  NOT NULL  ,"+ CROP_SOIL_ANALYSIS_RESULTS+" TEXT NOT NULL, "+CROP_SOIL_ANALYSIS_REPEAT_UNTIL + " TEXT, " +CROP_SOIL_ANALYSIS_DAYS_BEFORE + " REAL DEFAULT 0, " +
                CROP_SOIL_ANALYSIS_RECURRENCE + " TEXT NOT NULL, " +CROP_SOIL_ANALYSIS_FREQUENCY + " REAL DEFAULT 0, " + CROP_SOIL_ANALYSIS_REMINDERS + " TEXT NOT NULL " + " ) ";

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
                CROP_PRODUCT_ITEM_PRODUCT_ID+" TEXT NOT NULL,"+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" TEXT NOT NULL,"+ CROP_PRODUCT_ITEM_QUANTITY+" REAL NOT NULL, "+CROP_PRODUCT_ITEM_TAX+" REAL NOT NULL, "+
                CROP_PRODUCT_ITEM_RATE+" REAL NOT NULL, "+ CROP_PRODUCT_ITEM_TYPE+" TEXT NOT NULL, "+
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
                CROP_TASK_EMPLOYEE_ID + " TEXT NOT NULL, " + CROP_TASK_STATUS + " TEXT NOT NULL, " +CROP_TASK_TYPE + " TEXT NOT NULL, " + CROP_TASK_DESCRIPTION + " TEXT NOT NULL, " + CROP_TASK_RECURRENCE + " TEXT NOT NULL, " + CROP_TASK_REMINDERS + " TEXT NOT NULL, " +CROP_TASK_REPEAT_UNTIL + " TEXT, " +CROP_TASK_DAYS_BEFORE
                + " REAL DEFAULT 0, " +CROP_TASK_FREQUENCY + " REAL DEFAULT 0 " + " ) ";

        String crop_sales_order_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_SALES_ORDER_TABLE_NAME+" ( "+CROP_SALES_ORDER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_SALES_ORDER_USER_ID+" TEXT NOT NULL,"+CROP_SALES_ORDER_CUSTOMER_ID+" TEXT NOT NULL,"+CROP_SALES_ORDER_NO+" TEXT NOT NULL,"+CROP_SALES_ORDER_REFERENCE_NO+" TEXT NOT NULL,"+CROP_SALES_ORDER_DATE+" TEXT NOT NULL,"+
                CROP_SALES_ORDER_SHIPPING_DATE +" TEXT,"+CROP_SALES_ORDER_SHIPPING_METHOD +" TEXT,"+CROP_SALES_ORDER_DISCOUNT+" REAL DEFAULT 0,"+ CROP_SALES_ORDER_SHIPPING_CHARGES+" REAL DEFAULT 0  ,"+
                CROP_SALES_ORDER_CUSTOMER_NOTES+" TEXT ,"+ CROP_SALES_ORDER_STATUS+" TEXT DEFAULT 'DRAFT' ,"+ CROP_SALES_ORDER_TERMS_AND_CONDITIONS+" TEXT "+" )";

        String crop_purchase_order_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_PURCHASE_ORDER_TABLE_NAME+" ( "+CROP_PURCHASE_ORDER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_PURCHASE_ORDER_USER_ID+" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_SUPPLIER_ID+" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_NUMBER+" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_REFERENCE_NUMBER+" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_PURCHASE_DATE+" TEXT NOT NULL,"+
                CROP_PURCHASE_ORDER_DELIVERY_DATE +" TEXT NOT NULL,"+CROP_PURCHASE_ORDER_DELIVERY_METHOD +" TEXT,"+CROP_PURCHASE_ORDER_DISCOUNT+" REAL DEFAULT 0,"+
                CROP_PURCHASE_ORDER_NOTES+" TEXT ,"+ CROP_PURCHASE_ORDER_STATUS+" TEXT DEFAULT 'DRAFT' ,"+ CROP_PURCHASE_ORDER_TERMS_AND_CONDITIONS+" TEXT "+" )";

        String crop_bill_insert_query = "CREATE TABLE IF NOT EXISTS "+ CROP_BILL_TABLE_NAME + " ( " +CROP_BILL_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_BILL_USER_ID + " TEXT NOT NULL, " + CROP_BILL_SUPPLIER_ID + " TEXT NOT NULL, " +
                CROP_BILL_ORDER_NUMBER + " TEXT NOT NULL, " + CROP_BILL_NUMBER + " TEXT NOT NULL, " +  CROP_BILL_DATE + " TEXT NOT NULL, " + CROP_BILL_DUE_DATE + " TEXT, " + CROP_BILL_TERMS + " TEXT NOT NULL, " + CROP_BILL_DISCOUNT + " REAL DEFAULT 0, "+ CROP_BILL_NOTES+ " TEXT, "+CROP_BILL_STATUS+ " TEXT DEFAULT 'DRAFT' " + " ) ";


        String crop_payment_bill_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_PAYMENT_BILL_TABLE_NAME+" ( "+ CROP_PAYMENT_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + CROP_PAYMENT_BILL_USER_ID + " TEXT NOT NULL,"  + CROP_PAYMENT_BILL_DATE + " TEXT NOT NULL," +
                CROP_PAYMENT_BILL_PAYMENT_MADE + " " +
                "REAL NOT NULL," + CROP_PAYMENT_BILL_PAYMENT_MODE + " TEXT NOT NULL," + CROP_PAYMENT_BILL_PAID_THROUGH + " TEXT," + CROP_PAYMENT_BILL_REFERENCE_NUMBER + " TEXT," + CROP_PAYMENT_BILL_NOTES + " TEXT, " +CROP_PAYMENT_BILL_SUPPLIER_ID + " TEXT NOT NULL, " + CROP_PAYMENT_BILL_BILL_ID + " TEXT " + " )";


        String crop_item_table_query = " CREATE TABLE IF NOT EXISTS " + CROP_ITEM_TABLE_NAME + " ( " + CROP_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_ITEM_N_COMPOSITION + " REAL DEFAULT 0, "+
                CROP_ITEM_K_COMPOSITION + " REAL DEFAULT 0, "+CROP_ITEM_NAME + " TEXT NOT NULL , "  +CROP_ITEM_IMAGE_RESOURCE_ID + " TEXT  , "  +CROP_ITEM_P_COMPOSITION + " REAL DEFAULT 0 , "+
        CROP_ITEM_P_REMOVED + " REAL DEFAULT 0 , "+ CROP_ITEM_N_REMOVED + " REAL DEFAULT 0 , "+CROP_ITEM_K_REMOVED + " REAL DEFAULT 0 , "+
                CROP_ITEM_IS_FOR + " TEXT DEFAULT NULL ) " ;


        String crop_fertilizer_insert_query ="CREATE TABLE IF NOT EXISTS "+CROP_FERTILIZER_TABLE_NAME+" ( "+CROP_FERTILIZER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+
                CROP_FERTILIZER_TYPE+" TEXT NOT NULL,"+ CROP_FERTILIZER_NAME+" TEXT NOT NULL,"+ CROP_FERTILIZER_N_PERCENTAGE+" REAL,"+
                CROP_FERTILIZER_P_PERCENTAGE+" REAL,"+ CROP_FERTILIZER_K_PERCENTAGE+" REAL )";

        String crop_machine_task_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_MACHINE_TASK_TABLE_NAME + " ( " + CROP_MACHINE_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_MACHINE_TASK_MACHINE_ID + " TEXT NOT NULL, " + CROP_MACHINE_TASK_START_DATE + " TEXT NOT NULL, " +
                CROP_MACHINE_TASK_END_DATE + " TEXT NOT NULL, " + CROP_MACHINE_TASK_TITLE + " TEXT NOT NULL, " + CROP_MACHINE_TASK_PERSONNEL + " TEXT NOT NULL, "
                + CROP_MACHINE_TASK_STATUS + " TEXT NOT NULL, " + CROP_MACHINE_TASK_DESCRIPTION + " TEXT , " +CROP_MACHINE_TASK_REPEAT_UNTIL + " TEXT, " +CROP_MACHINE_TASK_DAYS_BEFORE + " REAL DEFAULT 0, " +CROP_MACHINE_TASK_COST + " REAL DEFAULT 0, " +
                CROP_MACHINE_TASK_RECURRENCE + " TEXT NOT NULL, " +CROP_MACHINE_TASK_FREQUENCY + " REAL DEFAULT 0, " + CROP_MACHINE_TASK_REMINDERS + " TEXT NOT NULL " + " ) ";


        String crop_note_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_NOTE_TABLE_NAME + " ( " + CROP_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_NOTE_PARENT_ID + " TEXT NOT NULL, " + CROP_NOTE_DATE + " TEXT NOT NULL, " + CROP_NOTE_CATEGORY + " TEXT, "  + CROP_NOTE_NOTES + " TEXT NOT NULL, " +CROP_NOTE_IS_FOR + " TEXT " + " ) ";


        String crop_machine_service_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_MACHINE_SERVICE_TABLE_NAME + " ( " + CROP_MACHINE_SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_MACHINE_SERVICE_MACHINE_ID + " TEXT NOT NULL, " +CROP_MACHINE_SERVICE_DATE + " TEXT NOT NULL, " +
                CROP_MACHINE_SERVICE_CURRENT_HOURS + " REAL DEFAULT 0, " + CROP_MACHINE_SERVICE_PERSONNEL + " TEXT NOT NULL, "+CROP_MACHINE_SERVICE_TYPE + " TEXT NOT NULL, "
                + CROP_MACHINE_SERVICE_DESCRIPTION + " TEXT , " +CROP_MACHINE_SERVICE_REPEAT_UNTIL + " TEXT, " +CROP_MACHINE_SERVICE_DAYS_BEFORE + " REAL DEFAULT 0, " +CROP_MACHINE_SERVICE_COST + " REAL DEFAULT 0, " +
                CROP_MACHINE_SERVICE_RECURRENCE + " TEXT NOT NULL, " +CROP_MACHINE_SERVICE_FREQUENCY + " REAL DEFAULT 0, " + CROP_MACHINE_SERVICE_REMINDERS + " TEXT NOT NULL " + " ) ";

        String crop_irrigation_insert_query = "CREATE TABLE IF NOT EXISTS " + CROP_IRRIGATION_TABLE_NAME + " ( " +CROP_IRRIGATION_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                CROP_IRRIGATION_USER_ID+ " TEXT, "+ CROP_IRRIGATION_CROP_ID + " TEXT NOT NULL, " +CROP_IRRIGATION_DATE+ " TEXT NOT NULL, "+CROP_IRRIGATION_SYSTEM_RATE+" REAL DEFAULT 0, "+ CROP_IRRIGATION_START_TIME+ " TEXT, "+
                CROP_IRRIGATION_END_TIME + " TEXT, "+ CROP_IRRIGATION_AREA_IRRIGATED+" REAL DEFAULT 0, "+CROP_IRRIGATION_UNITS+" TEXT, "+ CROP_IRRIGATION_RECURRENCE+" TEXT NOT NULL, " +
                CROP_IRRIGATION_REMINDERS+" TEXT NOT NULL, "+CROP_IRRIGATION_REPEAT_UNTIL + " TEXT, " +CROP_IRRIGATION_DAYS_BEFORE + " REAL DEFAULT 0, " +CROP_IRRIGATION_FREQUENCY + " REAL DEFAULT 0, " +CROP_IRRIGATION_COST+" REAL DEFAULT 0 " + " ) ";




        String crop_notification_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_NOTIFICATION_TABLE_NAME + " ( " + CROP_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_NOTIFICATION_USER_ID + " TEXT NOT NULL, " +
                CROP_NOTIFICATION_DATE + " TEXT NOT NULL DEFAULT 'dd-mm-2018', " + CROP_NOTIFICATION_MESSAGE + " TEXT NOT NULL DEFAULT 'Message', "  + CROP_NOTIFICATION_STATUS + " TEXT NOT NULL DEFAULT 'Pending', " + CROP_NOTIFICATION_ACTION_DATE + " TEXT NOT NULL DEFAULT 'dd-mm-2018', " +
                CROP_NOTIFICATION_TYPE + " TEXT NOT NULL DEFAULT 'Cultivate' " +" ) ";


        String crop_settings_insert_query = " CREATE TABLE IF NOT EXISTS " + CROP_SETTINGS_TABLE_NAME + " ( " + CROP_SETTINGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + CROP_SETTINGS_USER_ID + " TEXT NOT NULL, " + CROP_SETTINGS_AREA_UNITS + " TEXT NOT NULL DEFAULT 'Acres', " + CROP_SETTINGS_DATE_FORMAT + " TEXT NOT NULL DEFAULT 'dd-mm-yyyy', " +
                CROP_SETTINGS_CURRENCY + " TEXT NOT NULL DEFAULT 'UGX', "+ CROP_SETTINGS_WEIGHT_UNITS + " TEXT NOT NULL DEFAULT 'Kg' " + " ) ";





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

        onCreate(db);
    }


    public MyFarmDbHandlerSingleton openDB() throws SQLException {

        database = this.getWritableDatabase();
       // onCreate(database);

        return this;
    }

    public void closeDB() throws SQLException {
        this.close();
    }

    public void insertCropNotification(CropNotification notification) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_NOTIFICATION_USER_ID, notification.getUserId());
        contentValues.put(CROP_NOTIFICATION_DATE, notification.getDate());
        contentValues.put(CROP_NOTIFICATION_MESSAGE, notification.getMessage());
        contentValues.put(CROP_NOTIFICATION_STATUS, notification.getStatus());
        contentValues.put(CROP_NOTIFICATION_ACTION_DATE, notification.getActionDate());
        contentValues.put(CROP_NOTIFICATION_TYPE, notification.getType());

        database.insert(CROP_NOTIFICATION_TABLE_NAME, null, contentValues);
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

        database.update(CROP_NOTIFICATION_TABLE_NAME, contentValues, CROP_NOTIFICATION_ID + " = ?", new String[]{notification.getId()});

        closeDB();
    }

    public boolean deleteCropNotification(String notificationId) {
        openDB();
        database.delete(CROP_NOTIFICATION_TABLE_NAME, CROP_NOTIFICATION_ID + " = ?", new String[]{notificationId});
        closeDB();
        return true;
    }

    public ArrayList<CropNotification> getCropNotifications(String userId, String queryKey) {
        openDB();
        ArrayList<CropNotification> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_NOTIFICATION_TABLE_NAME + " where " + CROP_NOTIFICATION_USER_ID + " = " + userId +" AND date("+CROP_NOTIFICATION_ACTION_DATE+") "+queryKey+" date('now') AND "+CROP_NOTIFICATION_STATUS+" = '"+context.getString(R.string.notification_status_pending)+"'", null);
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

            array_list.add(notification);
            res.moveToNext();
        }

        closeDB();
        return array_list;
    }
    private CropNotification createNotification(String reminderType,String starDate, int frequency, int daysBefore){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar todayCalendar = Calendar.getInstance();
        todayCalendar.set(Calendar.HOUR,0);
        todayCalendar.set(Calendar.MINUTE,0);
        todayCalendar.set(Calendar.SECOND,0);
        todayCalendar.set(Calendar.MILLISECOND,0);
        Calendar varyingCalendar = Calendar.getInstance();
        varyingCalendar.set(Calendar.HOUR,0);
        varyingCalendar.set(Calendar.MINUTE,0);
        varyingCalendar.set(Calendar.SECOND,0);
        varyingCalendar.set(Calendar.MILLISECOND,0);
        int calendarIdentifier =0;
        int repeatFrequency =1;

        Date startDate = null;
        try {
            startDate = dateFormat.parse(starDate);
            varyingCalendar.setTime(startDate);
            if (reminderType.toLowerCase().equals("weekly")){
                calendarIdentifier = Calendar.DAY_OF_MONTH;
                repeatFrequency =7*frequency;
            }else if (reminderType.toLowerCase().equals("monthly")){
                calendarIdentifier = Calendar.MONTH;
                repeatFrequency =1;
            }else if (reminderType.toLowerCase().equals("daily")){
                calendarIdentifier = Calendar.DAY_OF_MONTH;
                repeatFrequency =1;
            }else if (reminderType.toLowerCase().equals("annually")){
                calendarIdentifier = Calendar.YEAR;
                repeatFrequency =1;
            }

            todayCalendar.add(Calendar.DAY_OF_MONTH,daysBefore);
            while(!varyingCalendar.after(todayCalendar)){
                System.out.println("Running : "+reminderType+" "+dateFormat.format(varyingCalendar.getTime())+" -> "+dateFormat.format(todayCalendar.getTime()));
                if(dateFormat.format(todayCalendar.getTime()).equals(dateFormat.format(varyingCalendar.getTime()))){ //equal dates we can notify
                    CropNotification notification = new CropNotification();
                    notification.setActionDate(dateFormat.format(varyingCalendar.getTime()));
                    //get todays date
                    todayCalendar.add(Calendar.DAY_OF_MONTH,-1*daysBefore);
                    notification.setDate(dateFormat.format(todayCalendar.getTime()));
                    notification.setUserId(CropDashboardActivity.getPreferences("userId",context));
                    System.out.println("There was a match : ");
                    return notification;
                }else{
                    varyingCalendar.add(calendarIdentifier,repeatFrequency);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }


    public void generateNotifications(){
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
        //Crop Task (Activity) Spraying
        String query = "select " + CROP_SPRAYING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_SPRAYING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_SPRAYING_TABLE_NAME + "." + CROP_SPRAYING_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_SPRAYING_REMINDERS + " = 'Yes' AND " +
                CROP_SPRAYING_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_SPRAYING_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_SPRAYING_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_SPRAYING_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_SPRAYING_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_spraying)+" ("+res.getString(res.getColumnIndex(CROP_CROP_NAME))+")");
               notification.setStatus(context.getString(R.string.notification_status_pending));
               notification.setType(context.getString(R.string.notification_type_spraying));
                array_list.add(notification);
            }
            res.moveToNext();
        }

        //Crop Task (Activity) Cultivation
        query = "select " + CROP_CULTIVATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_CULTIVATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_CULTIVATION_TABLE_NAME + "." + CROP_CULTIVATION_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_CULTIVATION_REMINDERS + " = 'Yes' AND " +
                CROP_CULTIVATION_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_CULTIVATION_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_CULTIVATION_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_CULTIVATION_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_cultivation)+" ("+res.getString(res.getColumnIndex(CROP_CROP_NAME))+")");
               notification.setStatus(context.getString(R.string.notification_status_pending));
                notification.setType(context.getString(R.string.notification_type_cultivation));
                array_list.add(notification);
            }

            res.moveToNext();
        }
        //Crop Task (Activity) Harvest
        query = "select " + CROP_HARVEST_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_HARVEST_TABLE_NAME + "." + CROP_HARVEST_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_HARVEST_REMINDERS + " = 'Yes' AND " +
                CROP_HARVEST_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_HARVEST_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_HARVEST_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_HARVEST_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_HARVEST_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_harvest)+" ("+res.getString(res.getColumnIndex(CROP_CROP_NAME))+")");
                notification.setStatus(context.getString(R.string.notification_status_pending));
                notification.setType(context.getString(R.string.notification_type_harvest));
                array_list.add(notification);
            }
            res.moveToNext();
        }

        //Crop Task (Activity) Fertilizer Application
        query = "select " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + "." + CROP_FERTILIZER_APPLICATION_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_FERTILIZER_APPLICATION_REMINDERS + " = 'Yes' AND " +
                CROP_FERTILIZER_APPLICATION_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_fertilizer_application)+" ("+res.getString(res.getColumnIndex(CROP_CROP_NAME))+")");
                notification.setStatus(context.getString(R.string.notification_status_pending));
                notification.setType(context.getString(R.string.notification_type_fertilizer_application));
                array_list.add(notification);
            }
            res.moveToNext();
        }

        //Crop Task (Activity) Irrigation
        query = "select " + CROP_IRRIGATION_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_IRRIGATION_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_IRRIGATION_TABLE_NAME + "." + CROP_IRRIGATION_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_IRRIGATION_REMINDERS + " = 'Yes' AND " +
                CROP_IRRIGATION_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_IRRIGATION_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_IRRIGATION_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_IRRIGATION_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_irrigation)+" ("+res.getString(res.getColumnIndex(CROP_CROP_NAME))+")");
                notification.setStatus(context.getString(R.string.notification_status_pending));
                notification.setType(context.getString(R.string.notification_type_irrigation));
                array_list.add(notification);
            }
            res.moveToNext();
        }

        //Crop Task (Activity) Transplanting
        query = "select " + CROP_TRANSPLANTING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_TRANSPLANTING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_TRANSPLANTING_TABLE_NAME + "." + CROP_TRANSPLANTING_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_TRANSPLANTING_REMINDERS + " = 'Yes' AND " +
                CROP_TRANSPLANTING_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_TRANSPLANTING_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_TRANSPLANTING_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_TRANSPLANTING_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_transplanting)+" ("+res.getString(res.getColumnIndex(CROP_CROP_NAME))+")");
                notification.setStatus(context.getString(R.string.notification_status_pending));
                notification.setType(context.getString(R.string.notification_type_transplanting));
                array_list.add(notification);
            }
            res.moveToNext();
        }
        //Crop Task (Activity) Scouting
        query = "select " + CROP_SCOUTING_TABLE_NAME + ".*," + CROP_CROP_TABLE_NAME + "." + CROP_CROP_NAME + " from " + CROP_SCOUTING_TABLE_NAME + " LEFT JOIN " + CROP_CROP_TABLE_NAME + " ON " + CROP_SCOUTING_TABLE_NAME + "." + CROP_SCOUTING_CROP_ID + " = " + CROP_CROP_TABLE_NAME + "." + CROP_CROP_ID + " where " + CROP_SCOUTING_REMINDERS + " = 'Yes' AND " +
                CROP_SCOUTING_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_SCOUTING_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_SCOUTING_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_SCOUTING_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_SCOUTING_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_scouting)+" ("+res.getString(res.getColumnIndex(CROP_CROP_NAME))+")");
               notification.setStatus(context.getString(R.string.notification_status_pending));
                notification.setType(context.getString(R.string.notification_type_scouting));
                array_list.add(notification);
            }
            res.moveToNext();
        }

        //Machine Task (Activity) Service
        query = "select " + CROP_MACHINE_SERVICE_TABLE_NAME + ".*," + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_NAME + " from " + CROP_MACHINE_SERVICE_TABLE_NAME + " LEFT JOIN " + CROP_MACHINE_TABLE_NAME + " ON " + CROP_MACHINE_SERVICE_TABLE_NAME + "." + CROP_MACHINE_SERVICE_MACHINE_ID + " = " + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_ID + " where " + CROP_MACHINE_SERVICE_REMINDERS + " = 'Yes' AND " +
                CROP_MACHINE_SERVICE_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_MACHINE_SERVICE_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_MACHINE_SERVICE_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_service)+" ("+res.getString(res.getColumnIndex(CROP_MACHINE_NAME))+")");
                notification.setStatus(context.getString(R.string.notification_status_pending));
                notification.setType(context.getString(R.string.notification_type_service));
                array_list.add(notification);
            }
            res.moveToNext();
        }
        //Machine Task (Activity) Task
        query = "select " + CROP_MACHINE_TASK_TABLE_NAME + ".*," + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_NAME + " from " + CROP_MACHINE_TASK_TABLE_NAME + " LEFT JOIN " + CROP_MACHINE_TABLE_NAME + " ON " + CROP_MACHINE_TASK_TABLE_NAME + "." + CROP_MACHINE_TASK_MACHINE_ID + " = " + CROP_MACHINE_TABLE_NAME + "." + CROP_MACHINE_ID + " where " + CROP_MACHINE_TASK_REMINDERS + " = 'Yes' AND " +
                CROP_MACHINE_TASK_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_MACHINE_TASK_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_MACHINE_TASK_START_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_MACHINE_TASK_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_MACHINE_TASK_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_machine_task)+" ("+res.getString(res.getColumnIndex(CROP_MACHINE_NAME))+")");
                notification.setStatus(context.getString(R.string.notification_status_pending));
                notification.setType(context.getString(R.string.notification_type_machine_task));
                array_list.add(notification);
            }
            res.moveToNext();
        }
        //Field Task (Activity) Soil Analysis
        query = "select " + CROP_SOIL_ANALYSIS_TABLE_NAME + ".*," + CROP_FIELDS_TABLE_NAME + "." + CROP_FIELD_NAME + " from " + CROP_SOIL_ANALYSIS_TABLE_NAME + " LEFT JOIN " + CROP_FIELDS_TABLE_NAME + " ON " + CROP_SOIL_ANALYSIS_TABLE_NAME + "." + CROP_SOIL_ANALYSIS_FIELD_ID + " = " + CROP_FIELDS_TABLE_NAME + "." + CROP_FIELD_ID + " where " + CROP_SOIL_ANALYSIS_REMINDERS + " = 'Yes' AND " +
                CROP_SOIL_ANALYSIS_RECURRENCE + " NOT LIKE '"+context.getString(R.string.task_reminder_type)+"'";
        res = db.rawQuery(query, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            String reminderType = res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_RECURRENCE));
            String startDate = res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_DATE));
            int frequency =res.getInt(res.getColumnIndex(CROP_SOIL_ANALYSIS_FREQUENCY));;
            int daysBefore = res.getInt(res.getColumnIndex(CROP_SOIL_ANALYSIS_DAYS_BEFORE));
            CropNotification notification = createNotification(reminderType,startDate,frequency,daysBefore);

            if (notification != null){
                notification.setMessage(context.getString(R.string.notification_type_soil_analysis)+" ("+res.getString(res.getColumnIndex(CROP_FIELD_NAME))+")");
                notification.setStatus(context.getString(R.string.notification_status_pending));
                notification.setType(context.getString(R.string.notification_type_soil_analysis));
                array_list.add(notification);
            }
            res.moveToNext();
        }







        closeDB();

        for(CropNotification notification : array_list){
            insertCropNotification(notification);
        }





    }

    public void initializeSettings(String userId){
        openDB();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SETTINGS_TABLE_NAME+ " where " + CROP_SETTINGS_ID + " = " + userId , null);
        res.moveToFirst();

        if (!res.isAfterLast()) {
            CropSettingsSingleton settingsSingleton = CropSettingsSingleton.getInstance();
            settingsSingleton.setCurrency(res.getString(res.getColumnIndex(CROP_SETTINGS_CURRENCY)));
            settingsSingleton.setAreaUnits(res.getString(res.getColumnIndex(CROP_SETTINGS_AREA_UNITS)));
            settingsSingleton.setWeightUnits(res.getString(res.getColumnIndex(CROP_SETTINGS_WEIGHT_UNITS)));
            settingsSingleton.setDateFormat(res.getString(res.getColumnIndex(CROP_SETTINGS_DATE_FORMAT)));
            settingsSingleton.setId(res.getString(res.getColumnIndex(CROP_SETTINGS_ID)));
            settingsSingleton.setUserId(res.getString(res.getColumnIndex(CROP_SETTINGS_USER_ID)));
            res.moveToNext();
        }
        else{
            CropSettingsSingleton settingsSingleton = CropSettingsSingleton.getInstance();
            settingsSingleton.setUserId(userId);
            /*settingsSingleton.setCurrency("USD");
            settingsSingleton.setAreaUnits("Acres");
            settingsSingleton.setWeightUnits("Kg");
            settingsSingleton.setDateFormat("dd/mm/yyyy");*/
            insertSettings(settingsSingleton);
            initializeSettings(userId);
        }
        res.close();
        closeDB();
    }
    public void insertSettings(CropSettingsSingleton crop) {
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SETTINGS_AREA_UNITS, crop.getAreaUnits());
        contentValues.put(CROP_SETTINGS_CURRENCY, crop.getCurrency());
        contentValues.put(CROP_SETTINGS_DATE_FORMAT, crop.getDateFormat());
        contentValues.put(CROP_SETTINGS_WEIGHT_UNITS, crop.getWeightUnits());
        contentValues.put(CROP_SETTINGS_USER_ID, crop.getUserId());
        database.insert(CROP_SETTINGS_TABLE_NAME,null, contentValues);
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
        database.update(CROP_SETTINGS_TABLE_NAME, contentValues, CROP_SETTINGS_ID + " = ? ", new String[]{crop.getId()});
        closeDB();
    }
    public ArrayList<CropYieldRecord> getCropsYield(String userId){

        openDB();
        ArrayList<CropYieldRecord> array_list = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+CROP_CROP_TABLE_NAME+".*,"+CROP_FIELDS_TABLE_NAME+".* from " + CROP_CROP_TABLE_NAME +  " LEFT JOIN "+CROP_FIELDS_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_FIELD_ID+" = "+CROP_FIELDS_TABLE_NAME+"."+CROP_FIELD_ID+" where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_USER_ID + " = " + userId, null);
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

        for(CropYieldRecord yieldRecord: array_list){
            yieldRecord.setTotalCost(getCropTotalExpenses(yieldRecord.getCropId()));
            yieldRecord.setRevenue(getCropRevenue(yieldRecord.getCropId()));
        }
        return array_list;

    }
    public float getCropTotalExpenses(String cropId){
        Cursor res;
        float totalExpenses=0;
        SQLiteDatabase db = this.getReadableDatabase();

        res = db.rawQuery("select "+CROP_INCOME_EXPENSE_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_INCOME_EXPENSE_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_INCOME_EXPENSE_TABLE_NAME+"."+CROP_INCOME_EXPENSE_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId+" AND "+CROP_INCOME_EXPENSE_TRANSACTION+" = 'Expense'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            totalExpenses+= res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT));
            res.moveToNext();
        }

        //Activities
        res = db.rawQuery("select "+CROP_HARVEST_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_HARVEST_TABLE_NAME+"."+CROP_HARVEST_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId  , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses+= res.getFloat(res.getColumnIndex(CROP_HARVEST_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_TRANSPLANTING_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_TRANSPLANTING_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_TRANSPLANTING_TABLE_NAME+"."+CROP_TRANSPLANTING_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId  , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses+= res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_SCOUTING_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_SCOUTING_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_SCOUTING_TABLE_NAME+"."+CROP_SCOUTING_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId  , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses+= res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_CULTIVATION_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_CULTIVATION_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_CULTIVATION_TABLE_NAME+"."+CROP_CULTIVATION_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId  , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses+= res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_IRRIGATION_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_IRRIGATION_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_IRRIGATION_TABLE_NAME+"."+CROP_IRRIGATION_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId  , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses+= res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_SPRAYING_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_SPRAYING_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_SPRAYING_TABLE_NAME+"."+CROP_SPRAYING_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId  , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses+= res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST));
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+"."+CROP_FERTILIZER_APPLICATION_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId  , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            totalExpenses+= res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST));
            res.moveToNext();
        }

        //Crop Planting
        res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId  , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            totalExpenses+= res.getFloat(res.getColumnIndex(CROP_CROP_COST));
            res.moveToNext();
        }


        closeDB();
        return totalExpenses;

    }
    public float getCropRevenue(String cropId){
        float totalRevenue =0;
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select "+CROP_INCOME_EXPENSE_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_INCOME_EXPENSE_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_INCOME_EXPENSE_TABLE_NAME+"."+CROP_INCOME_EXPENSE_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId+" AND "+CROP_INCOME_EXPENSE_TRANSACTION+" = 'Income'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            totalRevenue+= res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT));
            res.moveToNext();
        }
        res = db.rawQuery("select "+CROP_HARVEST_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_HARVEST_TABLE_NAME+"."+CROP_HARVEST_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_ID + " = "+cropId , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            totalRevenue+=(res.getFloat(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD))*res.getFloat(res.getColumnIndex(CROP_HARVEST_PRICE)));
            res.moveToNext();
        }
        closeDB();
        return totalRevenue;

    }
    public ArrayList<GraphRecord> getGraphExpensesByActivity(String startDate, String endDate){
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select * from " + CROP_CULTIVATION_TABLE_NAME + " where " + CROP_CULTIVATION_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE)),
                    "Cultivation",
                    res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_TRANSPLANTING_TABLE_NAME + " where " + CROP_TRANSPLANTING_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE)),
                   "Transplanting",
                    res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_HARVEST_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    "Transplanting",
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " where " + CROP_FERTILIZER_APPLICATION_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE)),
                    "Fertilizer Application",
                    res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_SPRAYING_TABLE_NAME + " where " + CROP_SPRAYING_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SPRAYING_DATE)),
                    "Spraying",
                    res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select * from " + CROP_IRRIGATION_TABLE_NAME + " where " + CROP_IRRIGATION_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE)),
                    "Irrigation",
                    res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_SCOUTING_TABLE_NAME + " where " + CROP_SCOUTING_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SCOUTING_DATE)),
                    "Scouting",
                    res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        //Crop Planting
        res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_DATE_SOWN+ " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
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
    public ArrayList<GraphRecord> getGraphExpensesByCategory(String startDate, String endDate){
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME + " where " + CROP_INCOME_EXPENSE_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') AND "+CROP_INCOME_EXPENSE_TRANSACTION+" = 'Expense'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DATE)),
                    res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_CATEGORY)),
                    res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_PAYMENT_BILL_TABLE_NAME + " where " +  CROP_PAYMENT_BILL_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_PAYMENT_BILL_DATE)),
                    "Payment Made",
                    res.getFloat(res.getColumnIndex(CROP_PAYMENT_BILL_PAYMENT_MADE)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        //ACTIVITIESS
        res = db.rawQuery("select * from " + CROP_CULTIVATION_TABLE_NAME + " where " + CROP_CULTIVATION_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_TRANSPLANTING_TABLE_NAME + " where " + CROP_TRANSPLANTING_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_HARVEST_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " where " + CROP_FERTILIZER_APPLICATION_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_SPRAYING_TABLE_NAME + " where " + CROP_SPRAYING_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SPRAYING_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select * from " + CROP_IRRIGATION_TABLE_NAME + " where " + CROP_IRRIGATION_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_SCOUTING_TABLE_NAME + " where " + CROP_SCOUTING_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SCOUTING_DATE)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        //Crop Planting
        res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_DATE_SOWN+ " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)),
                    context.getString(R.string.graph_category_crop_activity),
                    res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        //FIELD ANALYSIS
        res = db.rawQuery("select * from " + CROP_SOIL_ANALYSIS_TABLE_NAME + " where " + CROP_SOIL_ANALYSIS_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
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
        res = db.rawQuery("select * from " + CROP_INVENTORY_FERTILIZER_TABLE_NAME + " where " +  CROP_INVENTORY_FERTILIZER_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_DATE)),
                    context.getString(R.string.graph_category_crop_inventory),
                    res.getFloat(res.getColumnIndex(CROP_INVENTORY_FERTILIZER_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_INVENTORY_SEEDS_TABLE_NAME + " where " +  CROP_INVENTORY_SEEDS_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_DATE)),
                    context.getString(R.string.graph_category_crop_inventory),
                    res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select * from " + CROP_MACHINE_SERVICE_TABLE_NAME + " where " + CROP_MACHINE_SERVICE_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DATE)),
                    context.getString(R.string.graph_category_machine_service),
                    res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_MACHINE_TASK_TABLE_NAME + " where " + CROP_MACHINE_TASK_START_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_START_DATE)),
                    context.getString(R.string.graph_category_machine_task),
                    res.getFloat(res.getColumnIndex(CROP_MACHINE_TASK_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        closeDB();
        return expensesList;

    }

    public ArrayList<GraphRecord> getGraphExpensesByCrop(int year, String season){
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select "+CROP_INCOME_EXPENSE_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_INCOME_EXPENSE_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_INCOME_EXPENSE_TABLE_NAME+"."+CROP_INCOME_EXPENSE_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_SEASON + " = '"+season+"' AND "+CROP_INCOME_EXPENSE_TRANSACTION+" = 'Expense'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        //Activities
        res = db.rawQuery("select "+CROP_HARVEST_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_HARVEST_TABLE_NAME+"."+CROP_HARVEST_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"'" , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_TRANSPLANTING_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_TRANSPLANTING_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_TRANSPLANTING_TABLE_NAME+"."+CROP_TRANSPLANTING_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"'" , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_SCOUTING_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_SCOUTING_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_SCOUTING_TABLE_NAME+"."+CROP_SCOUTING_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"'" , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SCOUTING_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_SCOUTING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_CULTIVATION_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_CULTIVATION_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_CULTIVATION_TABLE_NAME+"."+CROP_CULTIVATION_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"'" , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CULTIVATION_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_CULTIVATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_IRRIGATION_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_IRRIGATION_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_IRRIGATION_TABLE_NAME+"."+CROP_IRRIGATION_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"'" , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_IRRIGATION_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_SPRAYING_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_SPRAYING_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_SPRAYING_TABLE_NAME+"."+CROP_SPRAYING_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"'" , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_SPRAYING_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_SPRAYING_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        res = db.rawQuery("select "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_FERTILIZER_APPLICATION_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_FERTILIZER_APPLICATION_TABLE_NAME+"."+CROP_FERTILIZER_APPLICATION_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"'" , null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        //Crop Planting
        res = db.rawQuery("select * from " + CROP_CROP_TABLE_NAME + " where " + CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_CROP_DATE_SOWN)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_CROP_COST)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }



        closeDB();
        return expensesList;

    }
    public ArrayList<GraphRecord> getGraphIncomesByCrop(int year, String season){
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select "+CROP_INCOME_EXPENSE_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_INCOME_EXPENSE_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_INCOME_EXPENSE_TABLE_NAME+"."+CROP_INCOME_EXPENSE_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"' AND "+CROP_INCOME_EXPENSE_TRANSACTION+" = 'Income'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select "+CROP_HARVEST_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+".* from " + CROP_HARVEST_TABLE_NAME + " LEFT JOIN "+CROP_CROP_TABLE_NAME+" ON "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_ID+" = "+CROP_HARVEST_TABLE_NAME+"."+CROP_HARVEST_CROP_ID+
                " where " +CROP_CROP_TABLE_NAME+"."+ CROP_CROP_YEAR + " = "+year+" AND "+CROP_CROP_TABLE_NAME+"."+CROP_CROP_SEASON + " = '"+season+"'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    res.getString(res.getColumnIndex(CROP_CROP_NAME)),
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD))*res.getFloat(res.getColumnIndex(CROP_HARVEST_PRICE)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        closeDB();
        return expensesList;

    }
    public ArrayList<GraphRecord> getGraphIncomes(String startDate, String endDate){
        ArrayList<GraphRecord> expensesList = new ArrayList<>();
        Cursor res;
        SQLiteDatabase db = this.getReadableDatabase();
        res = db.rawQuery("select * from " + CROP_INCOME_EXPENSE_TABLE_NAME + " where " + CROP_INCOME_EXPENSE_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') AND "+CROP_INCOME_EXPENSE_TRANSACTION+" = 'Income'" , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_DATE)),
                    res.getString(res.getColumnIndex(CROP_INCOME_EXPENSE_CATEGORY)),
                    res.getFloat(res.getColumnIndex(CROP_INCOME_EXPENSE_GROSS_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }


        res = db.rawQuery("select * from " + CROP_PAYMENT_TABLE_NAME + " where " +  CROP_PAYMENT_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_PAYMENT_DATE)),
                    "Payment Received",
                    res.getFloat(res.getColumnIndex(CROP_PAYMENT_AMOUNT)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }
        res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_HARVEST_DATE + " BETWEEN date('"+startDate+"') AND date('"+endDate+"') " , null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            GraphRecord expenseRecord = new GraphRecord(res.getString(res.getColumnIndex(CROP_HARVEST_DATE)),
                    "Crop Harvest",
                    res.getFloat(res.getColumnIndex(CROP_HARVEST_QUANTITY_SOLD))*res.getFloat(res.getColumnIndex(CROP_HARVEST_PRICE)));
            expensesList.add(expenseRecord);
            res.moveToNext();
        }

        closeDB();
        return expensesList;

    }
    public void  insertCropMachineService(CropMachineService service){
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

        database.insert(CROP_MACHINE_SERVICE_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropMachineService(CropMachineService service){
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
        database.update(CROP_MACHINE_SERVICE_TABLE_NAME,contentValues,CROP_MACHINE_SERVICE_ID+" = ?", new String[]{service.getId()});

        closeDB();
    }
    public boolean deleteCropMachineService(String serviceId){
        openDB();
        database.delete(CROP_MACHINE_SERVICE_TABLE_NAME,CROP_MACHINE_SERVICE_ID+" = ?", new String[]{serviceId});
        closeDB();
        return true;
    }
    public ArrayList<CropMachineService> getCropMachineServices(String machineId){
        openDB();
        ArrayList<CropMachineService> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+CROP_MACHINE_SERVICE_TABLE_NAME+".*,"+CROP_MACHINE_TABLE_NAME+"."+CROP_MACHINE_NAME+
                " from " + CROP_MACHINE_SERVICE_TABLE_NAME+
                " LEFT JOIN "+CROP_MACHINE_TABLE_NAME+" ON "+CROP_MACHINE_SERVICE_TABLE_NAME+"."+CROP_MACHINE_SERVICE_MACHINE_ID+" = "+CROP_MACHINE_TABLE_NAME+"."+CROP_MACHINE_ID+
                " where "+CROP_MACHINE_SERVICE_TABLE_NAME+"."+CROP_MACHINE_SERVICE_MACHINE_ID+" = "+ machineId, null);

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
            service.setDaysBefore(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_DAYS_BEFORE)));
            service.setCost(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_COST)));
            service.setFrequency(res.getFloat(res.getColumnIndex(CROP_MACHINE_SERVICE_FREQUENCY)));
            service.setRepeatUntil(res.getString(res.getColumnIndex(CROP_MACHINE_SERVICE_REPEAT_UNTIL)));

            array_list.add(service);
            res.moveToNext();
        }

        closeDB();
        Log.d("SERVICES ",array_list.size()+"");
        return array_list;
    }
    public void  insertCropNote(CropNote note){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_NOTE_DATE, note.getDate());
        contentValues.put(CROP_NOTE_PARENT_ID, note.getParentId());
        contentValues.put(CROP_NOTE_CATEGORY, note.getCategory());
        contentValues.put(CROP_NOTE_NOTES, note.getNotes());
        contentValues.put(CROP_NOTE_IS_FOR, note.getIsFor());

        database.insert(CROP_NOTE_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropNote(CropNote note){
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_NOTE_DATE, note.getDate());
        contentValues.put(CROP_NOTE_PARENT_ID, note.getParentId());
        contentValues.put(CROP_NOTE_CATEGORY, note.getCategory());
        contentValues.put(CROP_NOTE_NOTES, note.getNotes());
        contentValues.put(CROP_NOTE_IS_FOR, note.getIsFor());
        database.update(CROP_NOTE_TABLE_NAME,contentValues,CROP_NOTE_ID+" = ?", new String[]{note.getId()});

        closeDB();
    }
    public boolean deleteCropNote(String noteId){
        openDB();
        database.delete(CROP_NOTE_TABLE_NAME,CROP_NOTE_ID+" = ?", new String[]{noteId});
        closeDB();
        return true;
    }
    public ArrayList<CropNote> getCropNotes(String parentId, String isFor){
        openDB();
        ArrayList<CropNote> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_NOTE_TABLE_NAME+ " where " + CROP_NOTE_PARENT_ID + " = " + parentId +" AND "+CROP_NOTE_IS_FOR+" = '"+isFor+"'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            CropNote note = new CropNote();
            note.setId(res.getString(res.getColumnIndex(CROP_NOTE_ID)));
            note.setParentId(res.getString(res.getColumnIndex(CROP_NOTE_PARENT_ID)));
            note.setDate(res.getString(res.getColumnIndex(CROP_NOTE_DATE)));
            note.setCategory(res.getString(res.getColumnIndex(CROP_NOTE_CATEGORY)));
            note.setIsFor(res.getString(res.getColumnIndex(CROP_NOTE_IS_FOR)));
            note.setNotes(res.getString(res.getColumnIndex(CROP_NOTE_NOTES)));
            array_list.add(note);
            res.moveToNext();
        }

        closeDB();
        Log.d("NOTES ",array_list.size()+"");
        return array_list;
    }

    public void  insertCropMachineTask(CropMachineTask task){
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
        database.insert(CROP_MACHINE_TASK_TABLE_NAME,null,contentValues);
        closeDB();
    }
    public void  updateCropMachineTask(CropMachineTask task){
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
        database.update(CROP_MACHINE_TASK_TABLE_NAME,contentValues,CROP_MACHINE_TASK_ID+" = ?", new String[]{task.getId()});

        closeDB();
    }
    public boolean deleteCropMachineTask(String taskId){
        openDB();
        database.delete(CROP_MACHINE_TASK_TABLE_NAME,CROP_MACHINE_TASK_ID+" = ?", new String[]{taskId});
        closeDB();
        return true;
    }
    public ArrayList<CropMachineTask> getCropMachineTasks(String machineId){
        openDB();
        ArrayList<CropMachineTask> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select "+CROP_MACHINE_TASK_TABLE_NAME+".*,"+CROP_MACHINE_TABLE_NAME+"."+CROP_MACHINE_NAME+
                " from " + CROP_MACHINE_TASK_TABLE_NAME+
                " LEFT JOIN "+CROP_MACHINE_TABLE_NAME+" ON "+CROP_MACHINE_TASK_TABLE_NAME+"."+CROP_MACHINE_TASK_MACHINE_ID+" = "+CROP_MACHINE_TABLE_NAME+"."+CROP_MACHINE_ID+
                " where "+CROP_MACHINE_TASK_TABLE_NAME+"."+CROP_MACHINE_TASK_MACHINE_ID+" = "+ machineId, null);
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
            task.setFrequency(Float.parseFloat(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_FREQUENCY))));
            task.setStatus(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_STATUS)));
            task.setDescription(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_DESCRIPTION)));
            task.setRecurrence(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_RECURRENCE)));
            task.setReminders(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_REMINDERS)));
            task.setDaysBefore(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_DAYS_BEFORE)));
            task.setCost(Float.parseFloat(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_COST))));
            task.setRepeatUntil(res.getString(res.getColumnIndex(CROP_MACHINE_TASK_REPEAT_UNTIL)));

            array_list.add(task);
            res.moveToNext();
        }

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

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_ITEM_TABLE_NAME+" WHERE "  +CROP_ITEM_IS_FOR+" IS NULL", null);
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
        return array_list;

    }

    public ArrayList<CropItem> getCropItemsForNutrientRemoval() {
        openDB();
        ArrayList<CropItem> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_ITEM_TABLE_NAME+" WHERE "  +CROP_ITEM_IS_FOR+" = '"+CropItem.IS_FOR_NUTRIENT_REMOVAL+"'", null);
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

        closeDB();
        if (array_list.size()==0){
            CropDatabaseInitializerSingleton.initializeCrops(this);
            // return getCropItems();
        }
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
    public CropSalesOrder  insertCropSalesOrder(CropSalesOrder salesOrder){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SALES_ORDER_USER_ID,salesOrder.getUserId());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_ID,salesOrder.getCustomerId());
        contentValues.put(CROP_SALES_ORDER_NO,salesOrder.getNumber());
        contentValues.put(CROP_SALES_ORDER_DATE,salesOrder.getDate());
        contentValues.put(CROP_SALES_ORDER_STATUS,salesOrder.getStatus());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_METHOD,salesOrder.getMethod());
        contentValues.put(CROP_SALES_ORDER_REFERENCE_NO,salesOrder.getReferenceNumber());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_DATE,salesOrder.getShippingDate());
        contentValues.put(CROP_SALES_ORDER_STATUS,salesOrder.getStatus());
        contentValues.put(CROP_SALES_ORDER_DISCOUNT,salesOrder.getDiscount());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_CHARGES,salesOrder.getShippingCharges());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_NOTES,salesOrder.getCustomerNotes());
        contentValues.put(CROP_SALES_ORDER_TERMS_AND_CONDITIONS,salesOrder.getTermsAndConditions());

        database.insert(CROP_SALES_ORDER_TABLE_NAME,null,contentValues);

        Cursor res =  database.rawQuery( "select "+CROP_SALES_ORDER_ID+" from "+CROP_SALES_ORDER_TABLE_NAME+" where "+CROP_SALES_ORDER_CUSTOMER_ID+" = '"+salesOrder.getCustomerId()+"' AND "+CROP_SALES_ORDER_NO+" = '"+salesOrder.getNumber()+"'", null );
        res.moveToFirst();
        if(!res.isAfterLast()){
            String estimateId = res.getString(res.getColumnIndex(CROP_SALES_ORDER_ID));

            ArrayList<CropProductItem> items = salesOrder.getItems();

            for(CropProductItem x: items){
                contentValues.clear();
                contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
                contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID,estimateId);
                contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
                contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
                contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
                contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_SALES_ORDER);
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }
        }
        closeDB();
        return getCropSalesOrder (salesOrder.getNumber());
    }
    public CropSalesOrder  updateCropSalesOrder(CropSalesOrder salesOrder){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_SALES_ORDER_USER_ID,salesOrder.getUserId());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_ID,salesOrder.getCustomerId());
        contentValues.put(CROP_SALES_ORDER_NO,salesOrder.getNumber());
        contentValues.put(CROP_SALES_ORDER_DATE,salesOrder.getDate());
        contentValues.put(CROP_SALES_ORDER_STATUS,salesOrder.getStatus());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_METHOD,salesOrder.getMethod());
        contentValues.put(CROP_SALES_ORDER_STATUS,salesOrder.getStatus());
        contentValues.put(CROP_SALES_ORDER_REFERENCE_NO,salesOrder.getReferenceNumber());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_DATE,salesOrder.getShippingDate());
        contentValues.put(CROP_SALES_ORDER_DISCOUNT,salesOrder.getDiscount());
        contentValues.put(CROP_SALES_ORDER_SHIPPING_CHARGES,salesOrder.getShippingCharges());
        contentValues.put(CROP_SALES_ORDER_CUSTOMER_NOTES,salesOrder.getCustomerNotes());
        contentValues.put(CROP_SALES_ORDER_TERMS_AND_CONDITIONS,salesOrder.getTermsAndConditions());

        database.update(CROP_SALES_ORDER_TABLE_NAME,contentValues,CROP_SALES_ORDER_ID+" = ?", new String[]{salesOrder.getId()});

        String estimateId = salesOrder.getId();

        ArrayList<CropProductItem> items = salesOrder.getItems();

        for(CropProductItem x: items){
            contentValues.clear();
            contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
            contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID,estimateId);
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

        for(String id: salesOrder.getDeletedItemsIds()){
            database.delete(CROP_PRODUCT_ITEM_TABLE_NAME,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{id});
        }

        closeDB();
        return getCropSalesOrder (salesOrder.getNumber());
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
            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ cropSalesOrder.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_SALES_ORDER+"'", null );
            res.moveToFirst();
            while(!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
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
    public CropSalesOrder getCropSalesOrder(String salesOrderNumber){
        openDB();
        CropSalesOrder cropSalesOrder=null;

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+CROP_SALES_ORDER_TABLE_NAME+".*,"+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_NAME+" from "+CROP_SALES_ORDER_TABLE_NAME+" LEFT JOIN "+CROP_CUSTOMER_TABLE_NAME+" ON "+CROP_SALES_ORDER_TABLE_NAME+"."+CROP_SALES_ORDER_CUSTOMER_ID+" = "+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_ID+" where "+CROP_SALES_ORDER_TABLE_NAME+"."+CROP_SALES_ORDER_NO+" = '"+ salesOrderNumber+"'", null );
        res.moveToFirst();

        if(!res.isAfterLast()){
            cropSalesOrder= new CropSalesOrder();
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

            res.moveToNext();
        }


        if(cropSalesOrder != null){
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ cropSalesOrder.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_SALES_ORDER+"'", null );
            res.moveToFirst();
            while(!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                items_list.add(item);
                res.moveToNext();
            }
            cropSalesOrder.setItems(items_list);
        }

        closeDB();
        return cropSalesOrder;
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
                        " LEFT JOIN "+CROP_INVOICE_TABLE_NAME+" ON "+CROP_PAYMENT_TABLE_NAME+"."+CROP_PAYMENT_INVOICE_ID+" = "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_ID+
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
    public CropInvoice  insertCropInvoice(CropInvoice invoice){
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

        database.insert(CROP_INVOICE_TABLE_NAME, null, contentValues);


        Cursor res =  database.rawQuery( "select "+CROP_INVOICE_ID+" from "+CROP_INVOICE_TABLE_NAME+" where "+CROP_INVOICE_CUSTOMER_ID+" = '"+invoice.getCustomerId()+"' AND "+CROP_INVOICE_NO+" = '"+invoice.getNumber()+"'", null );
        res.moveToFirst();
        //ensure that the invoice has been saved before any items and payments are tied to it
        if(!res.isAfterLast()){
            String invoiceId = res.getString(res.getColumnIndex(CROP_INVOICE_ID));
            ArrayList<CropProductItem> items = invoice.getItems();
            for (CropProductItem x : items) {
                contentValues.clear();
                contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID, x.getProductId());
                contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID, invoiceId);
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

        return getCropInvoice(invoice.getNumber());
    }

    public CropInvoice updateCropInvoice(CropInvoice invoice) {
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
            contentValues = new ContentValues();
            contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
            contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID,invoiceId);
            contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
            contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
            contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
            contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_INVOICE);

            if(x.getId() !=null){
                Log.d("ID",x.getId()+" UPDATE");
                database.update(CROP_PRODUCT_ITEM_TABLE_NAME,contentValues,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{x.getId()});
            }
            else{
                Log.d("ID",x.getId()+" INSERT");
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }

            Log.d("CONTENT VALUES ",contentValues.toString());

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

        return getCropInvoice(invoice.getNumber());
    }

    public boolean deleteCropInvoice(String id) {
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = ? AND "+CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id,CROP_PRODUCT_ITEM_TYPE_INVOICE});
        database.delete(CROP_INVOICE_TABLE_NAME, CROP_INVOICE_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }

    public String getNextInvoiceNumber(){
        openDB();
        Cursor res =  database.rawQuery( "select "+CROP_INVOICE_ID+" from "+ CROP_INVOICE_TABLE_NAME+" ORDER BY "+CROP_INVOICE_ID+" DESC LIMIT 1",null);
        int lastId = 0;
        res.moveToFirst();

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

            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ invoice.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_INVOICE+ "'", null );
            res.moveToFirst();
            while (!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();

                Log.d("ITEM ",res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID))+" != "+res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
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

            invoice.setItems(items_list);
        }

        for(CropInvoice invoice: array_list){
            invoice.setPayments(this.getCropPaymentsByInvoice(invoice.getId()));
        }

        closeDB();

        return array_list;
    }
    public CropInvoice getCropInvoice(String invoiceNumber){
        openDB();


        SQLiteDatabase db = database;
        Cursor res =  db.rawQuery( "select "+CROP_INVOICE_TABLE_NAME+".*,"+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_NAME+" from "+CROP_INVOICE_TABLE_NAME+" LEFT JOIN "+CROP_CUSTOMER_TABLE_NAME+" ON "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_CUSTOMER_ID+" = "+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_ID+" where "+CROP_INVOICE_TABLE_NAME+"."+CROP_INVOICE_NO+" = '"+ invoiceNumber+"'", null );
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

            res.moveToNext();
        }


        if (invoice != null) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ invoice.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_INVOICE+ "'", null );
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

            invoice.setItems(items_list);
        }


        invoice.setPayments(this.getCropPaymentsByInvoice(invoice.getId()));


        closeDB();

        return invoice;
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

            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ invoice.getId()+
                    " AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_INVOICE+ "'", null );
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

            invoice.setItems(items_list);
        }

        for(CropInvoice invoice: array_list){
            invoice.setPayments(this.getCropPaymentsByInvoice(invoice.getId()));
        }

        closeDB();
        Log.d("Crop Product", array_list.toString());
        return array_list;
    }

    public CropEstimate insertCropEstimate(CropEstimate estimate) {
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
                contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID, estimateId);
                contentValues.put(CROP_PRODUCT_ITEM_QUANTITY, x.getQuantity());
                contentValues.put(CROP_PRODUCT_ITEM_TAX, x.getTax());
                contentValues.put(CROP_PRODUCT_ITEM_RATE, x.getRate());
                contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_ESTIMATE);
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME, null, contentValues);
            }
        }
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
        closeDB();



        return "EST-"+String.format("%03d", id);
    }

    public CropEstimate updateCropEstimate(CropEstimate estimate) {
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
            contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID,estimateId);
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
        return getCropEstimate(estimate.getNumber());
    }

    public boolean deleteCropEstimate(String id) {
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = ? AND "+CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id,CROP_PRODUCT_ITEM_TYPE_ESTIMATE});
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

            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ estimate.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_ESTIMATE+ "'", null );
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
                items_list.add(item);
                res.moveToNext();
            }
            estimate.setItems(items_list);
        }

        closeDB();

        return array_list;
    }
    public CropEstimate getCropEstimate(String estimateNumber) {
        openDB();
        ArrayList<CropEstimate> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select "+CROP_ESTIMATE_TABLE_NAME+".*,"+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_NAME+" from "+CROP_ESTIMATE_TABLE_NAME+" LEFT JOIN "+CROP_CUSTOMER_TABLE_NAME+" ON "+CROP_ESTIMATE_TABLE_NAME+"."+CROP_ESTIMATE_CUSTOMER_ID+" = "+CROP_CUSTOMER_TABLE_NAME+"."+CROP_CUSTOMER_ID+" where "+CROP_ESTIMATE_TABLE_NAME+"."+CROP_ESTIMATE_NO+" = '"+ estimateNumber+"'", null );
        res.moveToFirst();
        CropEstimate estimate=null;
        if (!res.isAfterLast()) {
            estimate= new CropEstimate();
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
            array_list.add(estimate);
            res.moveToNext();
        }


        if ( estimate != null) {
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ estimate.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_ESTIMATE+ "'", null );
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
                items_list.add(item);
                res.moveToNext();
            }
            estimate.setItems(items_list);
        }

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

    public ArrayList<CropProduct> getCropProducts(String userId) {
        openDB();
        ArrayList<CropProduct> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+CROP_PRODUCT_TABLE_NAME+".*, SUM("+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_QUANTITY+") as quantityUsed from " + CROP_PRODUCT_TABLE_NAME +"  LEFT JOIN "+CROP_PRODUCT_ITEM_TABLE_NAME+" ON "+
                  CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" = "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+
                " WHERE "+CROP_PRODUCT_USER_ID+" = "+userId+" AND "+
                "( "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_INVOICE+ "' OR "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" IS NULL) GROUP BY "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID;
        Log.d("PRODUCT QUERY",query);
        Cursor res = db.rawQuery( query, null);
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
            cropProduct.setQuantityUsed(res.getFloat(res.getColumnIndex("quantityUsed")));
            cropProduct.setSellingPrice(res.getFloat(res.getColumnIndex(CROP_PRODUCT_SELLING_PRICE)));
            cropProduct.setTaxRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_TAX_RATE)));
            cropProduct.setDescription(res.getString(res.getColumnIndex(CROP_PRODUCT_DESCRIPTION)));
            array_list.add(cropProduct);
            res.moveToNext();
        }

        closeDB();
        Log.d("Crop Product", array_list.toString());
        return array_list;

    }
    public CropProduct getCropProductById(String productId) {
        openDB();
       

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select "+CROP_PRODUCT_TABLE_NAME+".*, SUM("+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_QUANTITY+") as quantityUsed from " + CROP_PRODUCT_TABLE_NAME +"  LEFT JOIN "+CROP_PRODUCT_ITEM_TABLE_NAME+" ON "+
                CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" = "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+
                " WHERE "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" = "+ productId +" AND "+
                "( "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_INVOICE+ "' OR "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" IS NULL) GROUP BY "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID;
     
        Cursor res = db.rawQuery( query, null);
        res.moveToFirst();

        CropProduct cropProduct=null;


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
            cropProduct.setQuantityUsed(res.getFloat(res.getColumnIndex("quantityUsed")));
            cropProduct.setSellingPrice(res.getFloat(res.getColumnIndex(CROP_PRODUCT_SELLING_PRICE)));
            cropProduct.setTaxRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_TAX_RATE)));
            cropProduct.setDescription(res.getString(res.getColumnIndex(CROP_PRODUCT_DESCRIPTION)));
           
            res.moveToNext();
        }

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
        Cursor res = db.rawQuery("select * from " + CROP_SUPPLIER_TABLE_NAME + " where " + CROP_SUPPLIER_ID + " = " + id, null);
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
        contentValues.put(CROP_SOIL_ANALYSIS_RECURRENCE, spraying.getRecurrence());
        contentValues.put(CROP_SOIL_ANALYSIS_REMINDERS, spraying.getReminders());
        contentValues.put(CROP_SOIL_ANALYSIS_FREQUENCY, spraying.getFrequency());
        contentValues.put(CROP_SOIL_ANALYSIS_REPEAT_UNTIL, spraying.getRepeatUntil());
        contentValues.put(CROP_SOIL_ANALYSIS_DAYS_BEFORE, spraying.getDaysBefore());

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
        contentValues.put(CROP_SOIL_ANALYSIS_RECURRENCE, spraying.getRecurrence());
        contentValues.put(CROP_SOIL_ANALYSIS_REMINDERS, spraying.getReminders());
        contentValues.put(CROP_SOIL_ANALYSIS_FREQUENCY, spraying.getFrequency());
        contentValues.put(CROP_SOIL_ANALYSIS_REPEAT_UNTIL, spraying.getRepeatUntil());
        contentValues.put(CROP_SOIL_ANALYSIS_DAYS_BEFORE, spraying.getDaysBefore());

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
            crop.setFrequency(res.getFloat(res.getColumnIndex(CROP_SOIL_ANALYSIS_FREQUENCY)));
            crop.setRecurrence(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_RECURRENCE)));
            crop.setReminders(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_REMINDERS)));
            crop.setDaysBefore(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SOIL_ANALYSIS_REPEAT_UNTIL)));

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
        contentValues.put(CROP_SPRAYING_RECURRENCE, spraying.getRecurrence());
        contentValues.put(CROP_SPRAYING_REMINDERS, spraying.getReminders());
        contentValues.put(CROP_SPRAYING_FREQUENCY, spraying.getFrequency());
        contentValues.put(CROP_SPRAYING_REPEAT_UNTIL, spraying.getRepeatUntil());
        contentValues.put(CROP_SPRAYING_DAYS_BEFORE, spraying.getDaysBefore());

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
        contentValues.put(CROP_SPRAYING_RECURRENCE, spraying.getRecurrence());
        contentValues.put(CROP_SPRAYING_REMINDERS, spraying.getReminders());
        contentValues.put(CROP_SPRAYING_FREQUENCY, spraying.getFrequency());
        contentValues.put(CROP_SPRAYING_REPEAT_UNTIL, spraying.getRepeatUntil());
        contentValues.put(CROP_SPRAYING_DAYS_BEFORE, spraying.getDaysBefore());

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
            crop.setFrequency(res.getFloat(res.getColumnIndex(CROP_SPRAYING_FREQUENCY)));
            crop.setRecurrence(res.getString(res.getColumnIndex(CROP_SPRAYING_RECURRENCE)));
            crop.setReminders(res.getString(res.getColumnIndex(CROP_SPRAYING_REMINDERS)));
            crop.setDaysBefore(res.getString(res.getColumnIndex(CROP_SPRAYING_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SPRAYING_REPEAT_UNTIL)));

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
        contentValues.put(CROP_FERTILIZER_APPLICATION_RECURRENCE, fertilizerApplication.getRecurrence());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REMINDERS, fertilizerApplication.getReminders());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FREQUENCY, fertilizerApplication.getFrequency());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL, fertilizerApplication.getRepeatUntil());
        contentValues.put(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE, fertilizerApplication.getDaysBefore());

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
        contentValues.put(CROP_FERTILIZER_APPLICATION_RECURRENCE, fertilizerApplication.getRecurrence());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REMINDERS, fertilizerApplication.getReminders());
        contentValues.put(CROP_FERTILIZER_APPLICATION_FREQUENCY, fertilizerApplication.getFrequency());
        contentValues.put(CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL, fertilizerApplication.getRepeatUntil());
        contentValues.put(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE, fertilizerApplication.getDaysBefore());

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
            crop.setFrequency(res.getFloat(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_FREQUENCY)));
            crop.setRecurrence(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_RECURRENCE)));
            crop.setReminders(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REMINDERS)));
            crop.setDaysBefore(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_FERTILIZER_APPLICATION_REPEAT_UNTIL)));

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
        contentValues.put(CROP_CULTIVATION_RECURRENCE, cropCultivation.getRecurrence());
        contentValues.put(CROP_CULTIVATION_REMINDERS, cropCultivation.getReminders());
        contentValues.put(CROP_CULTIVATION_FREQUENCY, cropCultivation.getFrequency());
        contentValues.put(CROP_CULTIVATION_REPEAT_UNTIL, cropCultivation.getRepeatUntil());
        contentValues.put(CROP_CULTIVATION_DAYS_BEFORE, cropCultivation.getDaysBefore());

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
        contentValues.put(CROP_CULTIVATION_RECURRENCE, cropCultivation.getRecurrence());
        contentValues.put(CROP_CULTIVATION_REMINDERS, cropCultivation.getReminders());
        contentValues.put(CROP_CULTIVATION_FREQUENCY, cropCultivation.getFrequency());
        contentValues.put(CROP_CULTIVATION_REPEAT_UNTIL, cropCultivation.getRepeatUntil());
        contentValues.put(CROP_CULTIVATION_DAYS_BEFORE, cropCultivation.getDaysBefore());

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
            crop.setDaysBefore(res.getString(res.getColumnIndex(CROP_CULTIVATION_DAYS_BEFORE)));
            crop.setRepeatUntil(res.getString(res.getColumnIndex(CROP_CULTIVATION_REPEAT_UNTIL)));


            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
        return array_list;

    }

    public void insertCrop(Crop crop) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CROP_DATE_SOWN, crop.getDateSown());
        contentValues.put(CROP_CROP_USER_ID, crop.getUserId());
        contentValues.put(CROP_CROP_VARIETY, crop.getVariety());
        contentValues.put(CROP_CROP_AREA, crop.getArea());
        contentValues.put(CROP_CROP_COST, crop.getCost());
        contentValues.put(CROP_CROP_NAME, crop.getName());
        contentValues.put(CROP_CROP_YEAR, crop.getCroppingYear());
        contentValues.put(CROP_CROP_OPERATOR, crop.getOperator());
        contentValues.put(CROP_CROP_FIELD_ID, crop.getFieldId());
        contentValues.put(CROP_CROP_GROWING_CYCLE, crop.getGrowingCycle());
        contentValues.put(CROP_CROP_SEASON, crop.getSeason());
        contentValues.put(CROP_CROP_COST, crop.getCost());
        contentValues.put(CROP_CROP_SEED_ID, crop.getSeedId());
        contentValues.put(CROP_CROP_RATE, crop.getRate());
        contentValues.put(CROP_CROP_HARVEST_UNITS, crop.getHarvestUnits());
        contentValues.put(CROP_CROP_ESTIMATED_REVENUE, crop.getEstimatedRevenue());
        contentValues.put(CROP_CROP_ESTIMATED_YIELD, crop.getEstimatedYield());
        contentValues.put(CROP_CROP_PLANTING_METHOD, crop.getPlantingMethod());
        database.insert(CROP_CROP_TABLE_NAME, null, contentValues);
        closeDB();
    }

    public void updateCrop(Crop crop) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_CROP_DATE_SOWN, crop.getDateSown());
        contentValues.put(CROP_CROP_USER_ID, crop.getUserId());
        contentValues.put(CROP_CROP_VARIETY, crop.getVariety());
        contentValues.put(CROP_CROP_AREA, crop.getArea());
        contentValues.put(CROP_CROP_COST, crop.getCost());
        contentValues.put(CROP_CROP_YEAR, crop.getCroppingYear());
        contentValues.put(CROP_CROP_OPERATOR, crop.getOperator());
        contentValues.put(CROP_CROP_FIELD_ID, crop.getFieldId());
        contentValues.put(CROP_CROP_GROWING_CYCLE, crop.getGrowingCycle());
        contentValues.put(CROP_CROP_SEASON, crop.getSeason());
        contentValues.put(CROP_CROP_COST, crop.getCost());
        contentValues.put(CROP_CROP_SEED_ID, crop.getSeedId());
        contentValues.put(CROP_CROP_RATE, crop.getRate());
        contentValues.put(CROP_CROP_HARVEST_UNITS, crop.getHarvestUnits());
        contentValues.put(CROP_CROP_ESTIMATED_REVENUE, crop.getEstimatedRevenue());
        contentValues.put(CROP_CROP_ESTIMATED_YIELD, crop.getEstimatedYield());
        contentValues.put(CROP_CROP_PLANTING_METHOD, crop.getPlantingMethod());
        database.update(CROP_CROP_TABLE_NAME, contentValues, CROP_CROP_ID + " = ?", new String[]{crop.getId()});

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

            crop.setHarvestUnits(res.getString(res.getColumnIndex(CROP_CROP_HARVEST_UNITS)));
            crop.setEstimatedRevenue(res.getFloat(res.getColumnIndex(CROP_CROP_ESTIMATED_REVENUE)));
            crop.setEstimatedYield(res.getFloat(res.getColumnIndex(CROP_CROP_ESTIMATED_YIELD)));

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
            crop.setHarvestUnits(res.getString(res.getColumnIndex(CROP_CROP_HARVEST_UNITS)));
            crop.setEstimatedRevenue(res.getFloat(res.getColumnIndex(CROP_CROP_ESTIMATED_REVENUE)));
            crop.setEstimatedYield(res.getFloat(res.getColumnIndex(CROP_CROP_ESTIMATED_YIELD)));

            array_list.add(crop);
            res.moveToNext();
        }

        closeDB();
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
            spray.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SPRAY_COST)));
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
        contentValues.put(CROP_INVENTORY_SEEDS_TYPE, inventorySeeds.getType());
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
        contentValues.put(CROP_INVENTORY_SEEDS_TYPE, inventorySeeds.getType());

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
            inventorySeeds.setCost(res.getFloat(res.getColumnIndex(CROP_INVENTORY_SEEDS_COST)));
            inventorySeeds.setBatchNumber(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_BATCH_NUMBER)));
            inventorySeeds.setSupplier(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_SUPPLIER)));
            inventorySeeds.setTgw(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TGW)));
            inventorySeeds.setUsageUnits(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_USAGE_UNIT)));
            inventorySeeds.setType(res.getString(res.getColumnIndex(CROP_INVENTORY_SEEDS_TYPE)));
            array_list.add(inventorySeeds);
            res.moveToNext();
        }
        for (CropInventorySeeds seed : array_list) {
            String query = "select SUM("+CROP_CROP_RATE+") as totalConsumed from " + CROP_CROP_TABLE_NAME +  " where " + CROP_CROP_SEED_ID + " = " + seed.getId();
            res = db.rawQuery(query, null);
            res.moveToFirst();
            if(!res.isAfterLast()){
                seed.setTotalConsumed(res.getFloat(res.getColumnIndex("totalConsumed")));
            }

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
        contentValues.put(CROP_FIELD_FIELD_TYPE,field.getFieldType());
        contentValues.put(CROP_FIELD_LAYOUT_TYPE,field.getLayoutType());
        contentValues.put(CROP_FIELD_STATUS,field.getStatus());
        contentValues.put(CROP_FIELD_WATERCOURSE,field.getWatercourse());

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
        contentValues.put(CROP_FIELD_FIELD_TYPE,field.getFieldType());
        contentValues.put(CROP_FIELD_LAYOUT_TYPE,field.getLayoutType());
        contentValues.put(CROP_FIELD_STATUS,field.getStatus());

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
            field.setFieldType(res.getString(res.getColumnIndex(CROP_FIELD_FIELD_TYPE)));
            field.setLayoutType(res.getString(res.getColumnIndex(CROP_FIELD_LAYOUT_TYPE)));
            field.setStatus(res.getString(res.getColumnIndex(CROP_FIELD_STATUS)));

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
        contentValues.put(CROP_TASK_FREQUENCY, task.getFrequency());
        contentValues.put(CROP_TASK_REPEAT_UNTIL, task.getRepeatUntil());
        contentValues.put(CROP_TASK_DAYS_BEFORE, task.getDaysBefore());
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
        contentValues.put(CROP_TASK_FREQUENCY, task.getFrequency());
        contentValues.put(CROP_TASK_REPEAT_UNTIL, task.getRepeatUntil());
        contentValues.put(CROP_TASK_DAYS_BEFORE, task.getDaysBefore());

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
        Cursor res = db.rawQuery("select "+CROP_TASK_TABLE_NAME+".*,"+CROP_CROP_TABLE_NAME+"."+CROP_CROP_NAME+
                        " from " + CROP_TASK_TABLE_NAME+
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
            task.setDaysBefore(res.getString(res.getColumnIndex(CROP_TASK_DAYS_BEFORE)));
            task.setRepeatUntil(res.getString(res.getColumnIndex(CROP_TASK_REPEAT_UNTIL)));

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
    public CropPurchaseOrder  insertCropPurchaseOrder(CropPurchaseOrder estimate){
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
        Log.d("PURCHASE ORDER INSERTED",contentValues.toString());

        database.insert(CROP_PURCHASE_ORDER_TABLE_NAME,null,contentValues);

        Cursor res =  database.rawQuery( "select "+CROP_PURCHASE_ORDER_ID+" from "+CROP_PURCHASE_ORDER_TABLE_NAME+" where "+CROP_PURCHASE_ORDER_SUPPLIER_ID+" = '"+estimate.getSupplierId()+"' AND "+CROP_PURCHASE_ORDER_NUMBER+" = '"+estimate.getNumber()+"'", null );
        res.moveToFirst();
        if(!res.isAfterLast()){
            String estimateId = res.getString(res.getColumnIndex(CROP_PURCHASE_ORDER_ID));

            ArrayList<CropProductItem> items = estimate.getItems();

            for(CropProductItem x: items){
                contentValues.clear();
                contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
                contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID,estimateId);
                contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
                contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
                contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
                contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER);
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }
        }
        closeDB();
        return getCropPurchaseOrder(estimate.getNumber());
    }
    public CropPurchaseOrder  updateCropPurchaseOrder(CropPurchaseOrder estimate){
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
            contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID,estimateId);
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
        return getCropPurchaseOrder(estimate.getNumber());
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
            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ cropPurchaseOrder.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER+"'", null );
            res.moveToFirst();
            while(!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
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
    public CropPurchaseOrder getCropPurchaseOrder(String purchaseOrderNumber){
        openDB();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select "+CROP_PURCHASE_ORDER_TABLE_NAME+".*,"+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_NAME+" from "+CROP_PURCHASE_ORDER_TABLE_NAME+" LEFT JOIN "+CROP_SUPPLIER_TABLE_NAME+" ON "+CROP_PURCHASE_ORDER_TABLE_NAME+"."+CROP_PURCHASE_ORDER_SUPPLIER_ID+" = "+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_ID+" where "+CROP_PURCHASE_ORDER_TABLE_NAME+"."+CROP_PURCHASE_ORDER_NUMBER+" = '"+ purchaseOrderNumber+"'", null );
        res.moveToFirst();
        CropPurchaseOrder cropPurchaseOrder=null;
        if(!res.isAfterLast()){
            cropPurchaseOrder= new CropPurchaseOrder();
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
            res.moveToNext();
        }


        if(cropPurchaseOrder != null){
            ArrayList<CropProductItem> items_list = new ArrayList();
            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ cropPurchaseOrder.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_PURCHASE_ORDER+"'", null );
            res.moveToFirst();
            while(!res.isAfterLast()) {
                CropProductItem item = new CropProductItem();
                item.setId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_ID)));
                item.setProductId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PRODUCT_ID)));
                item.setProductName(res.getString(res.getColumnIndex(CROP_PRODUCT_NAME)));
                item.setParentObjectId(res.getString(res.getColumnIndex(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID)));
                item.setQuantity(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_QUANTITY)));
                item.setTax(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_TAX)));
                item.setRate(res.getFloat(res.getColumnIndex(CROP_PRODUCT_ITEM_RATE)));
                items_list.add(item);
                res.moveToNext();
            }
            cropPurchaseOrder.setItems(items_list);
        }

        closeDB();
        return cropPurchaseOrder;
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
        contentValues.put(CROP_PAYMENT_BILL_BILL_ID, cropPaymentBill.getBillId());
        contentValues.put(CROP_PAYMENT_BILL_SUPPLIER_ID, cropPaymentBill.getSupplierId());

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

        Cursor res =  db.rawQuery( "select "+CROP_PAYMENT_BILL_TABLE_NAME+".*,"+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_NAME+", "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_NUMBER+" from "+CROP_PAYMENT_BILL_TABLE_NAME+" LEFT JOIN "+CROP_SUPPLIER_TABLE_NAME+" ON "+CROP_PAYMENT_BILL_TABLE_NAME+"."+CROP_PAYMENT_BILL_SUPPLIER_ID+" = "+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_ID+
                " LEFT JOIN "+CROP_BILL_TABLE_NAME+" ON "+CROP_PAYMENT_BILL_TABLE_NAME+"."+CROP_PAYMENT_BILL_BILL_ID+" = "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_ID+
                " where "+CROP_PAYMENT_BILL_TABLE_NAME+"."+CROP_PAYMENT_BILL_USER_ID+" = "+userId, null );
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
            array_list.add(paymentBill);
            res.moveToNext();
        }

        closeDB();
        return array_list;
    }
    public ArrayList<CropPaymentBill> getCropPaymentBillsByBill(String billId) {
        openDB();
        ArrayList<CropPaymentBill> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select "+CROP_PAYMENT_BILL_TABLE_NAME+".*,"+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_NAME+", "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_NUMBER+" from "+CROP_PAYMENT_BILL_TABLE_NAME+" LEFT JOIN "+CROP_SUPPLIER_TABLE_NAME+" ON "+CROP_PAYMENT_BILL_TABLE_NAME+"."+CROP_PAYMENT_BILL_SUPPLIER_ID+" = "+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_ID+
                " LEFT JOIN "+CROP_BILL_TABLE_NAME+" ON "+CROP_PAYMENT_BILL_TABLE_NAME+"."+CROP_PAYMENT_BILL_BILL_ID+" = "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_ID+
                " where "+CROP_PAYMENT_BILL_TABLE_NAME+"."+CROP_PAYMENT_BILL_BILL_ID+" = "+billId, null );
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
            array_list.add(paymentBill);
            res.moveToNext();
        }

        closeDB();
        return array_list;
    }
    public CropBill  insertCropBill(CropBill bill){
        openDB();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CROP_BILL_USER_ID,bill.getUserId());
        contentValues.put(CROP_BILL_SUPPLIER_ID,bill.getSupplierId());
        contentValues.put(CROP_BILL_NUMBER,bill.getNumber());
        contentValues.put(CROP_BILL_DATE,bill.getBillDate());
        contentValues.put(CROP_BILL_DUE_DATE,bill.getDueDate());
        contentValues.put(CROP_BILL_ORDER_NUMBER,bill.getOrderNumber());
        contentValues.put(CROP_BILL_TERMS,bill.getTerms());
        contentValues.put(CROP_BILL_DISCOUNT,bill.getDiscount());
        contentValues.put(CROP_BILL_NOTES,bill.getNotes());

        database.insert(CROP_BILL_TABLE_NAME, null, contentValues);

        Cursor res =  database.rawQuery( "select "+CROP_BILL_ID+" from "+CROP_BILL_TABLE_NAME+" where "+CROP_BILL_SUPPLIER_ID+" = '"+bill.getSupplierId()+"' AND "+CROP_BILL_NUMBER+" = '"+bill.getNumber()+"'", null );
        res.moveToFirst();
        if(!res.isAfterLast()){
            String estimateId = res.getString(res.getColumnIndex(CROP_BILL_ID));
            ArrayList<CropProductItem> items = bill.getItems();
            for (CropProductItem x : items) {
                contentValues.clear();
                contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID, x.getProductId());
                contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID, estimateId);
                contentValues.put(CROP_PRODUCT_ITEM_QUANTITY, x.getQuantity());
                contentValues.put(CROP_PRODUCT_ITEM_TAX, x.getTax());
                contentValues.put(CROP_PRODUCT_ITEM_RATE, x.getRate());
                contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_BILL);
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME, null, contentValues);
            }
            

        }

        closeDB();
        return getCropBill(bill.getNumber());
    }

    public CropBill updateCropBill(CropBill bill) {
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CROP_BILL_USER_ID,bill.getUserId());
        contentValues.put(CROP_BILL_SUPPLIER_ID,bill.getSupplierId());
        contentValues.put(CROP_BILL_NUMBER,bill.getNumber());
        contentValues.put(CROP_BILL_DATE,bill.getBillDate());
        contentValues.put(CROP_BILL_DUE_DATE,bill.getDueDate());
        contentValues.put(CROP_BILL_ORDER_NUMBER,bill.getOrderNumber());
        contentValues.put(CROP_BILL_TERMS,bill.getTerms());
        contentValues.put(CROP_BILL_DISCOUNT,bill.getDiscount());
        contentValues.put(CROP_BILL_NOTES,bill.getNotes());


        database.update(CROP_BILL_TABLE_NAME, contentValues, CROP_BILL_ID + " = ?", new String[]{bill.getId()});

        String estimateId = bill.getId();

        ArrayList<CropProductItem> items = bill.getItems();

        for (CropProductItem x : items) {
            contentValues.clear();
            contentValues.put(CROP_PRODUCT_ITEM_PRODUCT_ID,x.getProductId());
            contentValues.put(CROP_PRODUCT_ITEM_PARENT_OBJECT_ID,estimateId);
            contentValues.put(CROP_PRODUCT_ITEM_QUANTITY,x.getQuantity());
            contentValues.put(CROP_PRODUCT_ITEM_TAX,x.getTax());
            contentValues.put(CROP_PRODUCT_ITEM_RATE,x.getRate());
            contentValues.put(CROP_PRODUCT_ITEM_TYPE,CROP_PRODUCT_ITEM_TYPE_BILL);
            if(x.getId() !=null){
                database.update(CROP_PRODUCT_ITEM_TABLE_NAME,contentValues,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{x.getId()});
            }
            else{
                database.insert(CROP_PRODUCT_ITEM_TABLE_NAME,null,contentValues);
            }

        }


        for(String id: bill.getDeletedItemsIds()){
            database.delete(CROP_PRODUCT_ITEM_TABLE_NAME,CROP_PRODUCT_ITEM_ID+" = ?", new String[]{id});
        }
        closeDB();
        return getCropBill(bill.getNumber());
    }
    public String getNextBillNumber(){
        openDB();
        Cursor res =  database.rawQuery( "select "+CROP_BILL_ID+" from "+ CROP_BILL_TABLE_NAME+" ORDER BY "+CROP_BILL_ID+" DESC LIMIT 1",null);
        int lastId = 0;
        res.moveToFirst();
        Log.d("TESTING",res.getCount()+"");
        if(!res.isAfterLast()){
            Log.d("TESTING",res.getColumnCount()+" columns "+res.getColumnNames().toString());
            lastId = res.getInt(res.getColumnIndex(CROP_BILL_ID));
        }
        int id=lastId+1;
        closeDB();

        return "BL-"+String.format("%04d", id);
    }
    public boolean deleteCropBill(String id) {
        openDB();
        database.delete(CROP_PRODUCT_ITEM_TABLE_NAME, CROP_PRODUCT_ITEM_PARENT_OBJECT_ID + " = ? AND "+CROP_PRODUCT_ITEM_TYPE + " = ?", new String[]{id,CROP_PRODUCT_ITEM_TYPE_BILL});
        database.delete(CROP_BILL_TABLE_NAME, CROP_BILL_ID + " = ?", new String[]{id});
        closeDB();
        return true;
    }
    public ArrayList<CropBill> getCropBills(String userId){
        openDB();
        ArrayList<CropBill> array_list = new ArrayList();

        SQLiteDatabase db = database;
        Cursor res =  db.rawQuery( "select "+CROP_BILL_TABLE_NAME+".*,"+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_NAME+" from "+CROP_BILL_TABLE_NAME+" LEFT JOIN "+CROP_SUPPLIER_TABLE_NAME+" ON "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_SUPPLIER_ID+" = "+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_ID+" where "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_USER_ID+" = "+ userId, null );
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
            array_list.add(cropBill);
            res.moveToNext();
        }


        for (CropBill bill : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ bill.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_BILL +"'", null );
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

        for(CropBill bill: array_list){
            bill.setPaymentBills(this.getCropPaymentBills(bill.getId()));
        }

        closeDB();
        Log.d("Crop Product", array_list.toString());
        return array_list;
    }
    public CropBill getCropBill(String billNumber){
        openDB();
        CropBill bill=null;

        SQLiteDatabase db = database;
        Cursor res =  db.rawQuery( "select "+CROP_BILL_TABLE_NAME+".*,"+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_NAME+" from "+CROP_BILL_TABLE_NAME+" LEFT JOIN "+CROP_SUPPLIER_TABLE_NAME+" ON "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_SUPPLIER_ID+" = "+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_ID+" where "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_NUMBER+" = '"+ billNumber+"'", null );
        res.moveToFirst();

        if (!res.isAfterLast()) {
            bill= new CropBill();
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

            res.moveToNext();
        }


       if (bill != null) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ bill.getId()+" AND "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_TYPE+" = '"+CROP_PRODUCT_ITEM_TYPE_BILL +"'", null );
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


        bill.setPaymentBills(this.getCropPaymentBills(bill.getId()));


        closeDB();

        return bill;
    }

    public ArrayList<CropBill> getCropBillsBySupplier(String supplierId){
        openDB();
        ArrayList<CropBill> array_list = new ArrayList();

        SQLiteDatabase db = database;
        Cursor res =  db.rawQuery( "select "+CROP_BILL_TABLE_NAME+".*,"+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_NAME+
                " from "+CROP_BILL_TABLE_NAME+" LEFT JOIN "+CROP_SUPPLIER_TABLE_NAME+" ON "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_SUPPLIER_ID+
                " = "+CROP_SUPPLIER_TABLE_NAME+"."+CROP_SUPPLIER_ID+" where "+CROP_BILL_TABLE_NAME+"."+CROP_BILL_SUPPLIER_ID+" = "+ supplierId, null );
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
            array_list.add(cropBill);
            res.moveToNext();
        }


        for (CropBill bill : array_list) {
            ArrayList<CropProductItem> items_list = new ArrayList();

            res = db.rawQuery( "select "+CROP_PRODUCT_ITEM_TABLE_NAME+".*,"+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_NAME+" from "+CROP_PRODUCT_ITEM_TABLE_NAME+" LEFT JOIN "+CROP_PRODUCT_TABLE_NAME+" ON "+CROP_PRODUCT_ITEM_TABLE_NAME+"."+CROP_PRODUCT_ITEM_PRODUCT_ID+" = "+CROP_PRODUCT_TABLE_NAME+"."+CROP_PRODUCT_ID+" where "+ CROP_PRODUCT_ITEM_PARENT_OBJECT_ID +" = "+ bill.getId(), null );
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

        for(CropBill bill: array_list){
            bill.setPaymentBills(this.getCropPaymentBills(bill.getId()));
        }

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

Log.d("CROP IRRIGATION","IRRIGATION IS INSERTED");
        database.insert(CROP_IRRIGATION_TABLE_NAME, null, contentValues);
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


        database.update(CROP_IRRIGATION_TABLE_NAME, contentValues, CROP_IRRIGATION_ID + " = ?", new String[]{irrigation.getId()});

        closeDB();
    }

    public boolean deleteCropIrrigation(String irrigationId) {
        openDB();
        database.delete(CROP_IRRIGATION_TABLE_NAME, CROP_IRRIGATION_ID + " = ?", new String[]{irrigationId});
        closeDB();
        return true;
    }

    public ArrayList<CropIrrigation> getCropIrrigations(String cropId) {
        openDB();
        ArrayList<CropIrrigation> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_IRRIGATION_TABLE_NAME + " where " + CROP_IRRIGATION_CROP_ID + " = '" + cropId+"'", null);
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
           irrigation.setDaysBefore(res.getString(res.getColumnIndex(CROP_IRRIGATION_DAYS_BEFORE)));
            irrigation.setRepeatUntil(res.getString(res.getColumnIndex(CROP_IRRIGATION_REPEAT_UNTIL)));
            irrigation.setTotalCost(res.getFloat(res.getColumnIndex(CROP_IRRIGATION_COST)));

            array_list.add(irrigation);
            res.moveToNext();
        }

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
        contentValues.put(CROP_TRANSPLANTING_OPERATOR, transplanting.getOperator());;
        contentValues.put(CROP_TRANSPLANTING_RECURRENCE, transplanting.getRecurrence());
        contentValues.put(CROP_TRANSPLANTING_REMINDERS, transplanting.getReminders());
        contentValues.put(CROP_TRANSPLANTING_FREQUENCY, transplanting.getFrequency());
        contentValues.put(CROP_TRANSPLANTING_REPEAT_UNTIL, transplanting.getRepeatUntil());
        contentValues.put(CROP_TRANSPLANTING_DAYS_BEFORE, transplanting.getDaysBefore());
        contentValues.put(CROP_TRANSPLANTING_COST, transplanting.getTotalCost());


        database.insert(CROP_TRANSPLANTING_TABLE_NAME, null, contentValues);
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

        database.update(CROP_TRANSPLANTING_TABLE_NAME, contentValues, CROP_TRANSPLANTING_ID + " = ?", new String[]{transplanting.getId()});

        closeDB();
    }

    public boolean deleteCropTransplanting(String transplantingId) {
        openDB();
        database.delete(CROP_TRANSPLANTING_TABLE_NAME, CROP_TRANSPLANTING_ID + " = ?", new String[]{transplantingId});
        closeDB();
        return true;
    }
    public ArrayList<CropTransplanting> getCropTransplantings(String cropId) {
        openDB();
        ArrayList<CropTransplanting> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_TRANSPLANTING_TABLE_NAME + " where " + CROP_TRANSPLANTING_CROP_ID + " = '" + cropId+"'", null);
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
            transplanting.setDaysBefore(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_DAYS_BEFORE)));
            transplanting.setRepeatUntil(res.getString(res.getColumnIndex(CROP_TRANSPLANTING_REPEAT_UNTIL)));
            transplanting.setTotalCost(res.getFloat(res.getColumnIndex(CROP_TRANSPLANTING_COST)));


            array_list.add(transplanting);
            res.moveToNext();
        }

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

        database.insert(CROP_SCOUTING_TABLE_NAME, null, contentValues);
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

        database.update(CROP_SCOUTING_TABLE_NAME, contentValues, CROP_SCOUTING_ID + " = ?", new String[]{scouting.getId()});

        closeDB();
    }

    public boolean deleteCropScouting(String scoutingId) {
        openDB();
        database.delete(CROP_SCOUTING_TABLE_NAME, CROP_SCOUTING_ID + " = ?", new String[]{scoutingId});
        closeDB();
        return true;
    }

    public ArrayList<CropScouting> getCropScoutings(String cropId) {
        openDB();
        ArrayList<CropScouting> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_SCOUTING_TABLE_NAME + " where " + CROP_SCOUTING_CROP_ID + " = '" + cropId+"'", null);
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
            scouting.setDaysBefore(res.getString(res.getColumnIndex(CROP_SCOUTING_DAYS_BEFORE)));
            scouting.setRepeatUntil(res.getString(res.getColumnIndex(CROP_SCOUTING_REPEAT_UNTIL)));

            array_list.add(scouting);
            res.moveToNext();
        }

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

        database.insert(CROP_HARVEST_TABLE_NAME, null, contentValues);
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

        database.update(CROP_HARVEST_TABLE_NAME, contentValues, CROP_HARVEST_ID + " = ?", new String[]{harvest.getId()});

        closeDB();
    }

    public boolean deleteCropHarvest(String harvestId) {
        openDB();
        database.delete(CROP_HARVEST_TABLE_NAME, CROP_HARVEST_ID + " = ?", new String[]{harvestId});
        closeDB();
        return true;
    }
    public ArrayList<CropHarvest> getCropHarvests(String cropId) {
        openDB();
        ArrayList<CropHarvest> array_list = new ArrayList();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CROP_HARVEST_TABLE_NAME + " where " + CROP_HARVEST_CROP_ID + " = '" + cropId+"'", null);
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
            harvest.setDaysBefore(res.getString(res.getColumnIndex(CROP_HARVEST_DAYS_BEFORE)));
            harvest.setRepeatUntil(res.getString(res.getColumnIndex(CROP_HARVEST_REPEAT_UNTIL)));

            array_list.add(harvest);
            res.moveToNext();
        }

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

        database.update(CROP_CONTACT_TABLE_NAME, contentValues, CROP_CONTACT_ID + " = ?", new String[]{contact.getId()});

        closeDB();
    }

    public boolean deleteCropContact(String contactId) {
        openDB();
        database.delete(CROP_CONTACT_TABLE_NAME, CROP_CONTACT_ID + " = ?", new String[]{contactId});
        closeDB();
        return true;
    }

    public ArrayList<CropContact> getCropContacts(String userId) {
        openDB();
        ArrayList<CropContact> array_list = new ArrayList();

        //hp = new HashMap();
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

            array_list.add(contact);
            res.moveToNext();
        }

        closeDB();
        return array_list;
    }




}

