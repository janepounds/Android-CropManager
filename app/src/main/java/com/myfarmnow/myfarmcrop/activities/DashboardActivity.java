package com.myfarmnow.myfarmcrop.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.net.Uri;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.marketplace.BuyInputsActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.app.MyAppPrefsManager;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.AccountFragment;
import com.myfarmnow.myfarmcrop.fragments.HomeFragment;
import com.myfarmnow.myfarmcrop.fragments.OffersFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.CurrencyFrag;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Languages;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Addresses;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Cart;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Orders;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Nearby_Merchants;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.SettingsFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Shipping_Address;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Update_Account;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.WishList;
import com.myfarmnow.myfarmcrop.network.StartAppRequests;
import com.myfarmnow.myfarmcrop.receivers.AlarmReceiver;
import com.myfarmnow.myfarmcrop.services.BackupWorker;
import com.myfarmnow.myfarmcrop.services.CropNotificationsSendWorker;
import com.myfarmnow.myfarmcrop.services.CropSyncService;
import com.myfarmnow.myfarmcrop.utils.NotificationScheduler;
import com.myfarmnow.myfarmcrop.utils.Utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";

    public static final String PREFERENCES_FILE_NAME = "UserInfo";

    public static final String FARM_NAME_PREFERENCES_ID = "farmname";
    public static final String STREET_PREFERENCES_ID = "addressStreet";
    public static final String CITY_PREFERENCES_ID = "addressCityOrTown";
    public static final String USER_DEFAULT_ADDRESS_PREFERENCES_ID = "userDefaultAddressID";
    public static final String COUNTRY_PREFERENCES_ID = "addressCountry";
    public static final String PREFERENCES_FIRST_NAME = "firstname";
    public static final String PREFERENCES_LAST_NAME = "lastname";
    public static String RETRIEVED_USER_ID = "";
    public static String PREFERENCES_USER_ID = "userId";
    public static final String PREFERENCES_USER_EMAIL = "email";
    public static final String PREFERENCES_PHONE_NUMBER = "phoneNumber";

    public static final String PREFERENCES_FIREBASE_TOKEN_SUBMITTED = "tokenSubmitted";
    public static final String PREFERENCES_USER_BACKED_UP = "userBackedUp";
    public static final String PREFERENCES_USER_PASSWORD = "password";

    public static final String TASK_BACKUP_DATA_TAG = "SYNC_SERVICE";
    public static final String TASK_SEND_NOTIFICATIONS_TAG = "SEND_NOTIFICATIONS";


    SharedPreferences sharedPreferences;
    MyAppPrefsManager myAppPrefsManager;
    Toolbar toolbar;
    Fragment defaultHomeFragment;

    public Fragment currentFragment;
    public  static ActionBar actionBar;
    public static  boolean FLAG_PROFILE;
    @Override
    protected void onStart() {
        super.onStart();

        if (myAppPrefsManager.isFirstTimeLaunch()) {
            NotificationScheduler.setReminder(DashboardActivity.this, AlarmReceiver.class);

            if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("fcm")) {
                StartAppRequests.RegisterDeviceForFCM(DashboardActivity.this);
            }

        }

        myAppPrefsManager.setFirstTimeLaunch(false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        // Get MyAppPrefsManager
        myAppPrefsManager = new MyAppPrefsManager(DashboardActivity.this);
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);

        DashboardActivity.RETRIEVED_USER_ID = sharedPreferences.getString(DashboardActivity.PREFERENCES_USER_ID, "");
        MyFarmDbHandlerSingleton.getHandlerInstance(this).initializeSettings(DashboardActivity.RETRIEVED_USER_ID);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.replace(R.id.fragment_crop_dashboard_graphs_section, new CropDashboardGraphsFragment()).commit();
        defaultHomeFragment=new HomeFragment(DashboardActivity.this, getSupportFragmentManager(),  MyFarmDbHandlerSingleton.getHandlerInstance(this) );
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, defaultHomeFragment ).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        toolbar = findViewById(R.id.main_Toolbar);

        // Get ActionBar and Set the Title and HomeButton of Toolbar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(ConstantValues.APP_HEADER);

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        // Handle ToolbarNavigationClickListener with OnBackStackChangedListener
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                // Check BackStackEntryCount of FragmentManager
                if (getSupportFragmentManager().getBackStackEntryCount() <= 0)  {
                    // Set DrawerToggle Indicator and default ToolbarNavigationClickListener
                    actionBar.setTitle(ConstantValues.APP_HEADER);
                    actionBar.setHomeButtonEnabled(false);
                    actionBar.setDisplayHomeAsUpEnabled(false);

                }
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                setupTitle();

            }
        });

        if (!getPreferences(PREFERENCES_FIREBASE_TOKEN_SUBMITTED, DashboardActivity.this).equals("yes")) {
            getAppToken();
        }

        startService(new Intent(this, CropSyncService.class));
        scheduleBackgroundWork();
    }


    private void setupTitle() {

        Fragment curruntFrag = getSupportFragmentManager().findFragmentById(R.id.main_fragment_container);
        if (curruntFrag instanceof My_Cart) {
            actionBar.setTitle(getString(R.string.actionCart));
        } else if (curruntFrag instanceof Shipping_Address) {
            actionBar.setTitle(getString(R.string.shipping_address));
        } else if (curruntFrag instanceof Nearby_Merchants) {
            actionBar.setTitle(getString(R.string.nearby_merchants));
        } else if (curruntFrag instanceof Update_Account) {
            actionBar.setTitle(getString(R.string.actionAccount));
        } else if (curruntFrag instanceof My_Orders) {
            actionBar.setTitle(getString(R.string.actionOrders));
        } else if (curruntFrag instanceof My_Addresses) {
            actionBar.setTitle(getString(R.string.actionAddresses));
        } else if (curruntFrag instanceof WishList) {
            actionBar.setTitle(getString(R.string.actionFavourites));
        } else if (curruntFrag instanceof SettingsFragment) {
            actionBar.setTitle(getString(R.string.actionSettings));
        }else if (curruntFrag instanceof AccountFragment) {
            actionBar.setTitle(getString(R.string.actionAccount));
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.page_1:
                selectedFragment = new HomeFragment(DashboardActivity.this, getSupportFragmentManager(),  MyFarmDbHandlerSingleton.getHandlerInstance(this));
                break;
            case R.id.page_2:
                selectedFragment = new OffersFragment();
                break;
            case R.id.page_3:
                selectedFragment = new AccountFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, selectedFragment).commit();
        return true;
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate toolbar_menu Menu
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);

        // Bind Menu Items
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
        MenuItem profileItem = menu.findItem(R.id.toolbar_edit_profile);

        languageItem.setVisible(false);
        currencyItem.setVisible(false);

        currentFragment=this.getSupportFragmentManager().getPrimaryNavigationFragment();
//        profileItem.setActionView(R.layout.layout_animated_ic_cart);
        profileItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to My_Cart Fragment
                Fragment fragment = new Update_Account();
                FragmentManager fragmentManager = getSupportFragmentManager();
                if(currentFragment==null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment_container, fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                else
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment_container, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
        });


        // Tint Menu Icons with the help of static method of Utilities class
        Utilities.tintMenuIcon(DashboardActivity.this, languageItem, R.color.white);

        return true;
    }

    //*********** Prepares the OptionsMenu of Toolbar ********//

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (item.getItemId()) {

            case android.R.id.home:
                DashboardActivity.super.onBackPressed();

                break;

            case R.id.toolbar_ic_language:
                // Navigate to Languages Fragment
                fragment = new Languages();
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment_container, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
                break;

            case R.id.toolbar_ic_currency:



                // Navigate to Currency Fragment
                fragment = new CurrencyFrag();
                fragmentManager.beginTransaction()
                        .add(R.id.main_fragment_container, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();

                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // Get FragmentManager
        FragmentManager fm = getSupportFragmentManager();


        if (fm.getBackStackEntryCount() > 0) {

            // Pop previous Fragment
            fm.popBackStack();

        } else {
            if (currentFragment == defaultHomeFragment)
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DashboardActivity.super.onBackPressed();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            else
                showHomePage();

        }
    }


    private void showHomePage() {
        getSupportFragmentManager().beginTransaction().hide(currentFragment).show(defaultHomeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        currentFragment = defaultHomeFragment;

        actionBar.setTitle(getString(R.string.app_name));
    }

    /**
     * Schedules the background tasks such as synchronisation and notification
     * It uses the WorkerManager library
     */
    public static void scheduleBackgroundWork() {
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

    public static boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }


    public static void addDatePicker(final EditText ed_, final Context context) {
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
                        ed_.setText(selectedyear + "-" + formatter.format(month) + "-" + formatter.format(selectedday));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();

            }
        });
        ed_.setInputType(InputType.TYPE_NULL);
    }

    public static void addTimePicker(final EditText ed_, final Context context) {
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
                        ed_.setText(formatter.format(selectedHour) + ":" + formatter.format(selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        ed_.setInputType(InputType.TYPE_NULL);
    }

    public void getAppToken() {
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
                        StartAppRequests.RegisterDeviceForFCM(getApplicationContext());
//                        sendFirebaseToken(token, DashboardActivity.this);
                    }
                });

    }

//    public static void sendFirebaseToken(String token, final Context context) {
//        final AsyncHttpClient client = new AsyncHttpClient();
//        final RequestParams params = new RequestParams();
//        // client.addHeader("Authorization","Bearer "+CropWalletAuthActivity.WALLET_ACCESS_TOKEN);
//        params.put("email", DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, context));
//        params.put("firebaseToken", token);
//
//        Handler mainHandler = new Handler(Looper.getMainLooper());
//        Runnable myRunnable = new Runnable() {
//            @Override
//            public void run() {
//                client.post(ApiPaths.CROP_SEND_FIREBASE_TOKEN, params, new AsyncHttpResponseHandler() {
//
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                        savePreferences(PREFERENCES_FIREBASE_TOKEN_SUBMITTED, "yes", context);
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                        if (responseBody != null) {
//                            Log.e("info", new String(String.valueOf(responseBody)));
//                        } else {
//                            Log.e("info", "Something got very very wrong");
//                        }
//                    }
//
//
//                });
//            }
//        };
//        mainHandler.post(myRunnable);
//
//    }



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

    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME,
                MODE_PRIVATE);
        return sharedPreferences.getString(key, "");

    }

    public static void selectSpinnerItemByValue(Spinner spnr, String value) {

        ArrayAdapter<String> adapter = (ArrayAdapter) spnr.getAdapter();
        //((TextView) spnr.getChildAt(0)).setTextColor(Color.BLUE);
        if (value == null) {
            return;
        }

        for (int position = 0; position < adapter.getCount(); position++) {

            String item = adapter.getItem(position);
            if (item.toLowerCase().equals(value.toLowerCase())) {
                spnr.setSelection(position);
                return;
            }

        }
    }

    public static void selectSpinnerItemById(Spinner spnr, String id) {

        CropSpinnerAdapter adapter = (CropSpinnerAdapter) spnr.getAdapter();
        if (id == null || adapter == null) {
            return;
        }

        for (int position = 0; position < adapter.getCount(); position++) {
            String item = adapter.getItem(position).getId();
            if (item == null) {
                continue;//this occurs for the first element
            }

            if (item.toLowerCase().equals(id.toLowerCase())) {

                spnr.setSelection(position);
                return;
            }

        }
    }

    public static void savePreferences(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME,
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveUser(JSONObject user, Context context) throws JSONException {

        DashboardActivity.savePreferences(FARM_NAME_PREFERENCES_ID, user.getString("farmname"), context);
        DashboardActivity.savePreferences(PREFERENCES_FIRST_NAME, user.getString("firstname"), context);
        DashboardActivity.savePreferences("email", user.getString("email"), context);
        DashboardActivity.savePreferences(PREFERENCES_USER_ID, user.getString("id"), context);
        DashboardActivity.savePreferences(PREFERENCES_LAST_NAME, user.getString("lastname"), context);
        DashboardActivity.savePreferences("country", user.getString("country"), context);
        DashboardActivity.savePreferences("countryCode", user.getString("countryCode"), context);
        DashboardActivity.savePreferences(PREFERENCES_USER_EMAIL, user.getString("email"), context);
        DashboardActivity.savePreferences(STREET_PREFERENCES_ID, user.getString("addressStreet"), context);
        DashboardActivity.savePreferences(CITY_PREFERENCES_ID, user.getString("addressCityOrTown"), context);
        DashboardActivity.savePreferences(COUNTRY_PREFERENCES_ID, user.getString("addressCountry"), context);
        DashboardActivity.savePreferences("phoneNumber", user.getString("phoneNumber"), context);
        DashboardActivity.savePreferences("latitude", user.getString("latitude"), context);
        DashboardActivity.savePreferences("longitude", user.getString("longitude"), context);
//        DashboardActivity.savePreferences(PREFERENCES_USER_PASSWORD, user.getString("password"), context);
        // DashboardActivity.savePreferences("userimage", user.getString("userimage"), this);

    }


    public void shareApp(View view) {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
        String shareMessage = "\nLet me recommend you this application\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.myfarmnow.cropmanager";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "choose one"));


    }

    public void rateApp(View view) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.myfarmnow.cropmanager")));

        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=com.myfarmnow.cropmanager")));
        }
    }

}
