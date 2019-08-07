package com.myfarmnow.myfarmcrop.activities;

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

import androidx.annotation.NonNull;

import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

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
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.adapters.NotificationTabsLayoutAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.CropDashboardGraphsFragment;
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

public class CropDashboardActivity extends AppCompatActivity  {


    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ImageView imgdrawer, noticationsImageBtn;
    RelativeLayout mainlayout;
    LinearLayout contactsSubMenu,helpSubMenu,inventorySubMenu,cropsSubMenu,financialsSubMenu,slesSubMenu,purchasesSubMenu, digitalWalletLayout;
    Toolbar toolbar;
    NotificationTabsLayoutAdapter notificationTabsLayoutAdapter;

    LinearLayout inventoryLinearLayout,fieldsLinearLayout, machinesLinearLayout,cropsLinearLayout,
            incomeExpenseLinearLayout, tasksLinearLayout,userProfileLayout, weatherForecastLinearLayout, contactsLinearLayout;

    TextView textViewUserEmail, textViewUserName,unreadNotificationsTextView,textViewVersion;


    private TabLayout notificationsTabLayout;
    private ViewPager notificationsViewPager;

    FrameLayout notificationsFrameLayout;
    MyFarmDbHandlerSingleton dbHandler;

    public static final String PREFERENCES_FILE_NAME ="pref";

    public static final String FARM_NAME_PREFERENCES_ID ="farmname";
    public static final String STREET_PREFERENCES_ID ="addressStreet";
    public static final String CITY_PREFERENCES_ID ="addressCityOrTown";
    public static final String COUNTRY_PREFERENCES_ID ="addressCountry";
    public static final String PREFERENCES_FIRST_NAME ="firstname";
    public static final String PREFERENCES_LAST_NAME ="lastname";
    public static final String PREFERENCES_USER_ID ="userId";
    public static final String PREFERENCES_USER_EMAIL ="email";
    public static final String PREFERENCES_PHONE_NUMBER = "phoneNumber";

    public static final String PREFERENCES_FIREBASE_TOKEN_SUBMITTED ="tokenSubmitted";
    public static final String PREFERENCES_USER_BACKED_UP ="userBackedUp";

    public static final String TASK_BACKUP_DATA_TAG ="SYNC_SERVICE";
    public static final String TASK_SEND_NOTIFICATIONS_TAG ="SEND_NOTIFICATIONS";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_dashboard);
        toolbar=  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        MyFarmDbHandlerSingleton.getHandlerInstance(this).initializeSettings(getPreferences("userId",this));
        initializeDashboard();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_crop_dashboard_graphs_section, new CropDashboardGraphsFragment()).commit();

        if(!getPreferences(PREFERENCES_FIREBASE_TOKEN_SUBMITTED,CropDashboardActivity.this).equals("yes")){
            getAppToken();
        }

        startService(new Intent(this, CropSyncService.class));
        scheduleBackgroundWork();





    }

    /**
     * Schedules the background tasks such as synchronisation and notification
     * It uses the WorkerManager library
     */
    public static void scheduleBackgroundWork(){
        Constraints backupConstraints = new Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(false)

                .build();

        PeriodicWorkRequest backupData =
                new PeriodicWorkRequest.Builder(BackupWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(backupConstraints)
                        // .setInitialRunAttemptCount(10)
                        //    .setPeriodStartTime(1,TimeUnit.MINUTES)
                        .build();

        WorkManager.getInstance().enqueueUniquePeriodicWork(TASK_BACKUP_DATA_TAG, ExistingPeriodicWorkPolicy.KEEP, backupData);
        //SChedule notification
        Constraints sendNotificationsConstraints = new Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiresBatteryNotLow(true)
                .setRequiresStorageNotLow(false)
                .build();

        PeriodicWorkRequest sendNotifications =
                new PeriodicWorkRequest.Builder(CropNotificationsSendWorker.class, 6, TimeUnit.HOURS)
                        .setConstraints(sendNotificationsConstraints)

                        .build();


        WorkManager.getInstance().enqueueUniquePeriodicWork(TASK_SEND_NOTIFICATIONS_TAG, ExistingPeriodicWorkPolicy.KEEP, sendNotifications);



    }
    public static boolean isGooglePlayServicesAvailable(Context context){
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }

    public void initializeDashboard(){

        mDrawerLayout = findViewById(R.id.drawer_layout);
        //expandableListView = findViewById(R.id.drawer_menu_list);
        mainlayout = findViewById(R.id.mainlayout);
        inventoryLinearLayout =findViewById(R.id.layout_crop_dashboard_inventory);
        fieldsLinearLayout =findViewById(R.id.layout_crop_dashboard_fields);
        machinesLinearLayout =findViewById(R.id.layout_crop_dashboard_machines);
        incomeExpenseLinearLayout =findViewById(R.id.layout_crop_dashboard_income_expense);
        cropsLinearLayout =findViewById(R.id.layout_crop_dashboard_crops);
        tasksLinearLayout =findViewById(R.id.layout_crop_dashboard_tasks);
        weatherForecastLinearLayout =findViewById(R.id.layout_crop_dashboard_weather_forecast);
        contactsLinearLayout =findViewById(R.id.layout_crop_dashboard_contacts);
        notificationsFrameLayout =findViewById(R.id.frame_layout_notifications);
        noticationsImageBtn =findViewById(R.id.img_crop_dashboard_notifications);
        unreadNotificationsTextView =findViewById(R.id.text_view_crop_dashboard_notification_unread_counter);
        notificationsViewPager = findViewById(R.id.viewPager);
        notificationsTabLayout = findViewById(R.id.tabLayout);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);

        userProfileLayout =findViewById(R.id.layout_user_profile);
        textViewUserName =findViewById(R.id.text_view_crop_dashboard_name);
        textViewUserEmail =findViewById(R.id.text_view_crop_dashboard_email);


        notificationTabsLayoutAdapter = new NotificationTabsLayoutAdapter(getSupportFragmentManager());
        notificationTabsLayoutAdapter.addFragment(new NotificationsTodayFragment(),"Today");
        notificationTabsLayoutAdapter.addFragment(new NotificationsUpcomingFragment(),"Upcoming");
        notificationTabsLayoutAdapter.addFragment(new NotificationsOverDueFragment(),"Over Due");
        notificationsViewPager.setAdapter(notificationTabsLayoutAdapter);
        notificationsTabLayout.setupWithViewPager(notificationsViewPager);
        notificationsTabLayout.setSelectedTabIndicatorColor(Color.GREEN);

        unreadNotificationsTextView.setText(""+dbHandler.getCropNotifications(CropDashboardActivity.getPreferences("userId",this), CropNotification.QUERY_KEY_TODAY).size());


        textViewVersion =  findViewById(R.id.text_view_crop_dashboard_android_version);
        textViewVersion.setText("version " + BuildConfig.VERSION_NAME);

        noticationsImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationsFrameLayout.getVisibility()==View.GONE){
                    notificationsFrameLayout.setVisibility(View.VISIBLE);
                }
                else{
                    notificationsFrameLayout.setVisibility(View.GONE);
                }
            }
        });

        contactsSubMenu = findViewById(R.id.layout_crop_dashboard_contact_submenus);
        helpSubMenu = findViewById(R.id.layout_crop_dashboard_help_submenus);
        inventorySubMenu = findViewById(R.id.layout_crop_dashboard_inventory_submenus);
        cropsSubMenu = findViewById(R.id.layout_crop_dashboard_crops_submenus);
        financialsSubMenu = findViewById(R.id.layout_crop_dashboard_financial_submenus);
        slesSubMenu = findViewById(R.id.layout_crop_dashboard_financial_sales_submenus);
        purchasesSubMenu = findViewById(R.id.layout_crop_dashboard_financial_purchases_submenus);


        digitalWalletLayout = findViewById(R.id.layout_crop_dashboard_digital_wallet);



        mDrawerToggle = new ActionBarDrawerToggle(CropDashboardActivity.this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainlayout.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        imgdrawer = findViewById(R.id.imgdrawer);
        imgdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        inventoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openInventory = new Intent(CropDashboardActivity.this, CropInventoryListActivity.class);
                startActivity(openInventory);
            }
        });

        fieldsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openFields = new Intent(CropDashboardActivity.this, CropFieldsListActivity.class);
                startActivity(openFields);
            }
        });
        machinesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openMachines = new Intent(CropDashboardActivity.this, CropMachinesListActivity.class);
                startActivity(openMachines);
            }
        });
        cropsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCrops = new Intent(CropDashboardActivity.this, CropsListActivity.class);
                startActivity(openCrops);
            }
        });
        incomeExpenseLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIncomeExpense = new Intent(CropDashboardActivity.this, CropIncomeExpensesListActivity.class);
                startActivity(openIncomeExpense);
            }
        });
        tasksLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openTasks = new Intent(CropDashboardActivity.this, CropTasksListActivity.class);
                startActivity(openTasks);
            }
        });
        contactsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openContacts = new Intent(CropDashboardActivity.this, CropContactsListActivity.class);
                startActivity(openContacts);
            }
        });

        textViewUserName.setText(getPreferences("firstname",this)+" "+getPreferences("lastname",this));
        textViewUserEmail.setText(getPreferences("email",this));

        userProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editUser = new Intent(CropDashboardActivity.this, CropRegisterActivity.class);
                editUser.putExtra("editUser","yes");
                startActivity(editUser);
            }
        });
        if (getPreferences(COUNTRY_PREFERENCES_ID,this).toLowerCase().equals("uganda")) {
            //digitalWalletLayout.setVisibility(View.VISIBLE);
        }

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

    public static  void addTimePicker(final EditText ed_, final Context context){
        ed_.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        NumberFormat formatter = new DecimalFormat("00");
                        ed_.setText( formatter.format(selectedHour) + ":" + formatter.format(selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        ed_.setInputType(InputType.TYPE_NULL);
    }


    public void getAppToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                           // Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        sendFirebaseToken(token,CropDashboardActivity.this);
                    }
                });

    }

    public static void sendFirebaseToken(String token, final Context context){
        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
       // client.addHeader("Authorization","Bearer "+CropWalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("email",CropDashboardActivity.getPreferences(CropDashboardActivity.PREFERENCES_USER_EMAIL,context));
        params.put("firebaseToken",token);

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                client.post(ApiPaths.CROP_SEND_FIREBASE_TOKEN, params, new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        savePreferences(PREFERENCES_FIREBASE_TOKEN_SUBMITTED,"yes",context);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        if (responseBody != null) {
                            Log.e("info", new String(String.valueOf(responseBody)));
                        } else {
                            Log.e("info", "Something got very very wrong");
                        }
                    }



                });
            }
        };
        mainHandler.post(myRunnable);

    }
    public void openDigitalWallet(View view){
        if(CropWalletAuthActivity.WALLET_ACCESS_TOKEN==null){
            Intent openDW = new Intent(this, CropWalletAuthActivity.class);
            startActivity(openDW);
        }
        else{
            Intent openDW = new Intent(this, CropWalletActivity.class);
            startActivity(openDW);
        }

    }
    public void openFarmReports(View view){
        Intent openReports = new Intent(this, CropFarmReportsActivity.class);
        startActivity(openReports);
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
    public void openContactList(View view){
        Intent openList = new Intent(this, CropContactsListActivity.class);
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

    public void openCalculators(View view){
        Intent openList = new Intent(this, CropCalculatorsActivity.class);
        startActivity(openList);
    }

    public void openBestPractices(View view){
        Intent openBestPractices = new Intent(this, CropBestPracticesDashboardActivity.class);
        startActivity(openBestPractices);

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
    public void showHideFieldManager(View view){
        LinearLayout fieldsSubMenu = findViewById(R.id.layout_crop_dashboard_field_submenus);

        toggleVisibility(fieldsSubMenu);
    }
    public void showHideCropManager(View view){


        toggleVisibility(cropsSubMenu);
    }
    public void showHideInventoryManager(View view){


        toggleVisibility(inventorySubMenu);
    }
    public void showHideContactsManager(View view){


        toggleVisibility(contactsSubMenu);
    }


    public void helpSendEmail(View view){

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","cabraltechltd@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    public void helpMakeCall(View view){
        Uri number = Uri.parse("tel:+256700353769");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }
    public void helpFacebook(View view){
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(this);
        facebookIntent.setData(Uri.parse(facebookUrl));
        startActivity(facebookIntent);
    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + "https://www.facebook.com/cropmanager/";
            } else { //older versions of fb app
               return "fb://page/" + "1552608241538614";//FACEBOOK_ID

            }
        } catch (PackageManager.NameNotFoundException e) {
            return "https://www.facebook.com/cropmanager/"; //normal web url
        }
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
    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("pref",
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
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
    public static void  savePreferences(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME ,
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveUser(JSONObject user, Context context) throws JSONException{

        CropDashboardActivity.savePreferences(FARM_NAME_PREFERENCES_ID, user.getString("farmname"), context);
        CropDashboardActivity.savePreferences(PREFERENCES_FIRST_NAME, user.getString("firstname"), context);
        CropDashboardActivity.savePreferences("email", user.getString("email"), context);
        CropDashboardActivity.savePreferences(PREFERENCES_USER_ID, user.getString("id"), context);
        CropDashboardActivity.savePreferences(PREFERENCES_LAST_NAME, user.getString("lastname"), context);
        CropDashboardActivity.savePreferences("country", user.getString("country"), context);
        CropDashboardActivity.savePreferences("countryCode",  user.getString("countryCode"), context);
        CropDashboardActivity.savePreferences(PREFERENCES_USER_EMAIL, user.getString("email"), context);
        CropDashboardActivity.savePreferences(STREET_PREFERENCES_ID, user.getString("addressStreet"), context);
        CropDashboardActivity.savePreferences(CITY_PREFERENCES_ID, user.getString("addressCityOrTown"), context);
        CropDashboardActivity.savePreferences(COUNTRY_PREFERENCES_ID, user.getString("addressCountry"), context);
        CropDashboardActivity.savePreferences("phoneNumber", user.getString("phoneNumber"), context);
        CropDashboardActivity.savePreferences("latitude", user.getString("latitude"), context);
        CropDashboardActivity.savePreferences("longitude", user.getString("longitude"), context);
        // CropDashboardActivity.savePreferences("userimage", user.getString("userimage"), this);

    }




    public void logout(View view){

        startService(new Intent(this, CropSyncService.class));
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Logging out..");
        dialog.setCancelable(false);
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dialog.dismiss();
                //remove shared preferences
                SharedPreferences sharedPreferences = CropDashboardActivity.this.getSharedPreferences(PREFERENCES_FILE_NAME , 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                //remove database
                CropDashboardActivity.this.deleteDatabase(MyFarmDbHandlerSingleton.DATABASE_NAME);

                WorkManager.getInstance().cancelAllWorkByTag(TASK_BACKUP_DATA_TAG);
                WorkManager.getInstance().cancelAllWorkByTag(TASK_SEND_NOTIFICATIONS_TAG);
                finish();
                Intent openList = new Intent(CropDashboardActivity.this, CropLoginActivity.class);
                startActivity(openList);
            }
        }, 10000);





    }

    public void shareApp(View view){

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
        String shareMessage= "\nLet me recommend you this application\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.myfarmnow.cropmanager";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "choose one"));


    }
    public void rateApp(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.myfarmnow.cropmanager" )));

        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.myfarmnow.cropmanager" )));
        }
    }

}
