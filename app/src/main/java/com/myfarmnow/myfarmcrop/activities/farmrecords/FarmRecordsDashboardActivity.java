package com.myfarmnow.myfarmcrop.activities.farmrecords;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.BuildConfig;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropBestPracticesDashboardActivity;
import com.myfarmnow.myfarmcrop.activities.CropBillsListActivity;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropCalculatorsActivity;
import com.myfarmnow.myfarmcrop.activities.CropContactManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropContactsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropCustomersListActivity;
import com.myfarmnow.myfarmcrop.activities.CropEmployeesListActivity;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropEstimatesListActivity;
import com.myfarmnow.myfarmcrop.activities.CropFarmReportsActivity;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropFertilizerCalculatorEntryActivity;
import com.myfarmnow.myfarmcrop.activities.CropInventoryListActivity;
import com.myfarmnow.myfarmcrop.activities.CropInvoicesListActivity;
import com.myfarmnow.myfarmcrop.activities.CropLoginActivity;
import com.myfarmnow.myfarmcrop.activities.CropPaymentBillsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropPaymentsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropProductsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropPurchaseOrdersListActivity;
import com.myfarmnow.myfarmcrop.activities.CropRegisterActivity;
import com.myfarmnow.myfarmcrop.activities.CropSalesOrdersListActivity;
import com.myfarmnow.myfarmcrop.activities.CropSettingsActivity;
import com.myfarmnow.myfarmcrop.activities.CropSuppliersListActivity;
import com.myfarmnow.myfarmcrop.activities.CropTasksListActivity;
import com.myfarmnow.myfarmcrop.activities.agronomy.CropsListActivity;
import com.myfarmnow.myfarmcrop.activities.agronomy.CropsManagerActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.adapters.NotificationTabsLayoutAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.NotificationsOverDueFragment;
import com.myfarmnow.myfarmcrop.fragments.NotificationsTodayFragment;
import com.myfarmnow.myfarmcrop.fragments.NotificationsUpcomingFragment;
import com.myfarmnow.myfarmcrop.models.ApiPaths;
import com.myfarmnow.myfarmcrop.models.CropNotification;
import com.myfarmnow.myfarmcrop.services.BackupWorker;
import com.myfarmnow.myfarmcrop.services.CropNotificationsSendWorker;
import com.myfarmnow.myfarmcrop.services.CropSyncService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import cz.msebera.android.httpclient.Header;

public class FarmRecordsDashboardActivity extends AppCompatActivity  {


    ImageView noticationsImageBtn,imgBack;
    LinearLayout contactsSubMenu,helpSubMenu,inventorySubMenu,cropsSubMenu,financialsSubMenu,slesSubMenu,purchasesSubMenu;
    Toolbar toolbar;


    LinearLayout inventoryLinearLayout,fieldsLinearLayout,cropsLinearLayout,
            incomeExpenseLinearLayout, tasksLinearLayout, contactsLinearLayout;

    TextView textViewUserEmail, textViewUserName,unreadNotificationsTextView,textViewVersion;


    FrameLayout notificationsFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_records_dashboard);
        toolbar=  findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        MyFarmDbHandlerSingleton.getHandlerInstance(this).initializeSettings(getPreferences("userId",this));
        initializeDashboard();

    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void initializeDashboard(){

        inventoryLinearLayout =findViewById(R.id.layout_inventory_store);

        fieldsLinearLayout =findViewById(R.id.layout_crop_dashboard_fields);

        incomeExpenseLinearLayout =findViewById(R.id.layout_dashboard_financial_records);
        cropsLinearLayout =findViewById(R.id.layout_crop_dashboard_crops);
        tasksLinearLayout =findViewById(R.id.layout_crop_dashboard_tasks);

        notificationsFrameLayout =findViewById(R.id.frame_layout_notifications);
        noticationsImageBtn =findViewById(R.id.img_crop_dashboard_notifications);
        unreadNotificationsTextView =findViewById(R.id.text_view_crop_dashboard_notification_unread_counter);

        textViewUserName =findViewById(R.id.text_view_crop_dashboard_name);
        textViewUserEmail =findViewById(R.id.text_view_crop_dashboard_email);



        inventoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openInventory = new Intent(FarmRecordsDashboardActivity.this, CropInventoryListActivity.class);
                startActivity(openInventory);
            }
        });
//       to be called in "crop records"
//        fieldsLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openFields = new Intent(FarmRecordsDashboardActivity.this, CropFieldsListActivity.class);
//                startActivity(openFields);
//            }
//        });

        cropsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCrops = new Intent(FarmRecordsDashboardActivity.this, CropRecordsDashboardActivity.class);
                startActivity(openCrops);
            }
        });
        incomeExpenseLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIncomeExpense = new Intent(FarmRecordsDashboardActivity.this, CropIncomeExpensesListActivity.class);
                startActivity(openIncomeExpense);
            }
        });

//        imgBack.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                Intent imgback = new Intent(FarmRecordsDashboardActivity.this, DashboardActivity.class);
//                startActivity(imgback);
//            }
//        });


    }
    public static  void addDatePicker(final EditText ed_, final Context context){
        ed_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        int month = selectedmonth + 1;
                        NumberFormat formatter = new DecimalFormat("00");
                        ed_.setText( selectedyear+ "-" + formatter.format(month) + "-" +formatter.format(selectedday) );
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();

            }
        });
        ed_.setInputType(InputType.TYPE_NULL);
    }


    public void openSettings(View view){
        Intent openSettings = new Intent(this, CropSettingsActivity.class);
        startActivity(openSettings);
        finish();
    }
    public void openEmployeeList(View view){
        Intent openList = new Intent(this, CropEmployeesListActivity.class);
        startActivity(openList);
    }
    public void openFieldList(View view){
        Intent openList = new Intent(this, CropFieldsListActivity.class);
        startActivity(openList);
    }

    public void openFieldManager(View view){
        Intent openList = new Intent(this, CropFieldManagerActivity.class);
        startActivity(openList);
    }
    public void openCropList(View view){
        Intent openList = new Intent(this, CropsListActivity.class);
        startActivity(openList);
    }
    public void openCropManager(View view){
        Intent openList = new Intent(this, CropsManagerActivity.class);
        startActivity(openList);
    }
    public void openInventoryList(View view){
        Intent openList = new Intent(this, CropInventoryListActivity.class);
        startActivity(openList);
    }
    public void openInventoryManager(View view){
        Intent openList = new Intent(this, CropInventoryListActivity.class);
        startActivity(openList);
    }

    public void openContactManager(View view){
        Intent openList = new Intent(this, CropContactManagerActivity.class);
        startActivity(openList);
    }

    public void openCustomerList(View view){
        Intent openList = new Intent(this, CropCustomersListActivity.class);
        startActivity(openList);
    }
    public void openSupplierList(View view){
        Intent openList = new Intent(this, CropSuppliersListActivity.class);
        startActivity(openList);
    }

    public void openFertilizerCalculator(View view){
        //CropFertilizerCalculatorEntryActivity
        Intent openList = new Intent(this, CropFertilizerCalculatorEntryActivity.class);
        startActivity(openList);
    }
    public void openSalesOrderList(View view){
        Intent openList = new Intent(this, CropSalesOrdersListActivity.class);
        startActivity(openList);
    }
    public void openProductsList(View view){
        Intent openList = new Intent(this, CropProductsListActivity.class);
        startActivity(openList);
    }
    public void openEstimatesList(View view){
        Intent openList = new Intent(this, CropEstimatesListActivity.class);
        startActivity(openList);
    }
    public void openInvoicesList(View view){
        Intent openList = new Intent(this, CropInvoicesListActivity.class);
        startActivity(openList);
    }
    public void openPaymentsList(View view){
        Intent openList = new Intent(this, CropPaymentsListActivity.class);
        startActivity(openList);
    }
    public void openPurchaseOrdersList(View view){
        Intent openList = new Intent(this, CropPurchaseOrdersListActivity.class);
        startActivity(openList);
    }
    public void openBillsList(View view){
        Intent openList = new Intent(this, CropBillsListActivity.class);
        startActivity(openList);
    }
    public void openBillPaymentsList(View view){
        Intent openList = new Intent(this, CropPaymentBillsListActivity.class);
        startActivity(openList);
    }



    public void showHideFinancialManager(View view){


        toggleVisibility(financialsSubMenu);
    }
    public void showSalesManager(View view){

        toggleSubMenuVisibility(slesSubMenu);
    }
    public void showPurchasesManager(View view){

        toggleSubMenuVisibility(purchasesSubMenu);
    }
    public void showHelpOptions(View view){

        toggleVisibility(helpSubMenu);
    }

    public void showHideCropManager(View view){


        toggleVisibility(cropsSubMenu);
    }


    public void toggleVisibility(View view){
        LinearLayout [] layouts = new LinearLayout[]{contactsSubMenu,helpSubMenu,inventorySubMenu,cropsSubMenu,financialsSubMenu};
        if(view.getVisibility() == View.GONE){
            for(LinearLayout layout: layouts){
                if(layout!=view){
                    layout.setVisibility(View.GONE);
                }
            }
            view.setVisibility(View.VISIBLE);

        }else{
            view.setVisibility(View.GONE);
        }
    }

    public void toggleSubMenuVisibility(View view){
        LinearLayout [] layouts = new LinearLayout[]{slesSubMenu,purchasesSubMenu};
        if(view.getVisibility() == View.GONE){
            for(LinearLayout layout: layouts){
                if(layout!=view){
                    layout.setVisibility(View.GONE);
                }
            }
            view.setVisibility(View.VISIBLE);

        }else{
            view.setVisibility(View.GONE);
        }
    }



    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref",
                0);
        return sharedPreferences.getString(key, "");

    }
    public static void selectSpinnerItemByValue(Spinner spnr, String value) {

        ArrayAdapter<String> adapter = (ArrayAdapter) spnr.getAdapter();
        //((TextView) spnr.getChildAt(0)).setTextColor(Color.BLUE);
        if(value==null){
            return;
        }

        for (int position = 0; position < adapter.getCount(); position++) {

            String item = adapter.getItem(position);
            if(item.toLowerCase().equals(value.toLowerCase())){
                spnr.setSelection(position);
                return;
            }

        }
    }
    public static void selectSpinnerItemById(Spinner spnr, String id) {

        CropSpinnerAdapter adapter = (CropSpinnerAdapter) spnr.getAdapter();
        if(id==null || adapter==null){
            return;
        }

        for (int position = 0; position < adapter.getCount(); position++) {
            String item =adapter.getItem(position).getId();
            if(item==null){
                continue;//this occurs for the first element
            }

            if(item.toLowerCase().equals(id.toLowerCase())){

                spnr.setSelection(position);
                return;
            }

        }
    }



}
