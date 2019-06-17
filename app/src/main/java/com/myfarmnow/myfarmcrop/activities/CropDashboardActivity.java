package com.myfarmnow.myfarmcrop.activities;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;

import android.net.Uri;
import android.os.Build;

import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.adapters.NotificationTabsLayoutAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.CropDashboardGraphsFragment;
import com.myfarmnow.myfarmcrop.fragments.NotificationsOverDueFragment;
import com.myfarmnow.myfarmcrop.fragments.NotificationsTodayFragment;
import com.myfarmnow.myfarmcrop.fragments.NotificationsUpcomingFragment;
import com.myfarmnow.myfarmcrop.models.CropNotification;
import com.myfarmnow.myfarmcrop.services.CropNotificationsCreatorService;
import com.myfarmnow.myfarmcrop.services.CropNotificationsFireService;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    TextView textViewUserEmail, textViewUserName,unreadNotificationsTextView;


    private TabLayout notificationsTabLayout;
    private ViewPager notificationsViewPager;

    FrameLayout notificationsFrameLayout;
    MyFarmDbHandlerSingleton dbHandler;

    public static final String FARM_NAME_PREFERENCES_ID ="farmname";
    public static final String STREET_PREFERENCES_ID ="addressStreet";
    public static final String CITY_PREFERENCES_ID ="addressCityOrTown";
    public static final String COUNTRY_PREFERENCES_ID ="addressCountry";


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

        //start the notifications services
       /* startService(new Intent(this, CropNotificationsCreatorService.class));
        startService(new Intent(this, CropNotificationsFireService.class));*/

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
                mDrawerLayout.openDrawer(Gravity.START);
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


    public void openDigitalWallet(View view){
        Intent openDW = new Intent(this, CropDigitalWalletAuthenticationManagerActivity.class);
        startActivity(openDW);
    }
    public void openFarmReports(View view){
        Intent openReports = new Intent(this, CropFarmReportsActivity.class);
        startActivity(openReports);
    }
    public void openSettings(View view){
        Intent openSettings = new Intent(this, CropSettingsActivity.class);
        startActivity(openSettings);
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
        Intent openList = new Intent(this, CropsListActivity.class);
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
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref",
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveUser(JSONObject user, Context context) throws JSONException{

        CropDashboardActivity.savePreferences(FARM_NAME_PREFERENCES_ID, user.getString("farmname"), context);
        CropDashboardActivity.savePreferences("firstname", user.getString("firstname"), context);
        CropDashboardActivity.savePreferences("email", user.getString("email"), context);
        CropDashboardActivity.savePreferences("userId", user.getString("id"), context);
        CropDashboardActivity.savePreferences("lastname", user.getString("lastname"), context);
        CropDashboardActivity.savePreferences("country", user.getString("country"), context);
        CropDashboardActivity.savePreferences("countryCode",  user.getString("countryCode"), context);
        CropDashboardActivity.savePreferences("email", user.getString("email"), context);
        CropDashboardActivity.savePreferences(STREET_PREFERENCES_ID, user.getString("addressStreet"), context);
        CropDashboardActivity.savePreferences(CITY_PREFERENCES_ID, user.getString("addressCityOrTown"), context);
        CropDashboardActivity.savePreferences(COUNTRY_PREFERENCES_ID, user.getString("addressCountry"), context);
        CropDashboardActivity.savePreferences("phoneNumber", user.getString("phoneNumber"), context);
        CropDashboardActivity.savePreferences("latitude", user.getString("latitude"), context);
        CropDashboardActivity.savePreferences("longitude", user.getString("longitude"), context);
        // CropDashboardActivity.savePreferences("userimage", user.getString("userimage"), this);

    }





}
