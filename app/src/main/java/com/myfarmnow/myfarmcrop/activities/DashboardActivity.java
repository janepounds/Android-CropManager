package com.myfarmnow.myfarmcrop.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.WrappedDrawable;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.app.MyAppPrefsManager;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.customs.NotificationBadger;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.AccountFragment;
import com.myfarmnow.myfarmcrop.fragments.HomeFragment;
import com.myfarmnow.myfarmcrop.fragments.OffersFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Category_Products;
import com.myfarmnow.myfarmcrop.fragments.marketplace.BuyInputsHomePage;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.CurrencyFrag;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Languages;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Addresses;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Cart;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Orders;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Nearby_Merchants;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.SearchFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.SettingsFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Shipping_Address;
import com.myfarmnow.myfarmcrop.fragments.UpdateAccountFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.WishList;
import com.myfarmnow.myfarmcrop.fragments.marketplace.MarketPlaceHomeFragment;
import com.myfarmnow.myfarmcrop.fragments.marketplace.SellProduceFragment;
import com.myfarmnow.myfarmcrop.network.StartAppRequests;
import com.myfarmnow.myfarmcrop.receivers.AlarmReceiver;
import com.myfarmnow.myfarmcrop.services.BackupWorker;
import com.myfarmnow.myfarmcrop.services.CropNotificationsSendWorker;
import com.myfarmnow.myfarmcrop.services.CropSyncService;
import com.myfarmnow.myfarmcrop.utils.LocaleHelper;
import com.myfarmnow.myfarmcrop.utils.NotificationScheduler;
import com.myfarmnow.myfarmcrop.utils.Utilities;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static android.provider.SettingsSlicesContract.KEY_LOCATION;

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

    public static String mSelectedItem;
    private static final String SELECTED_ITEM_ID = "selected";
    public static ActionBar actionBar;
    public Fragment currentFragment;
    public  static BottomNavigationView bottomNavigationView;

    // Razor Pay callback is not for the fragment so we need to paas static data from main activity to sub fragmnet
    public static String paymentNonceToken;

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


        // Select SELECTED_ITEM from SavedInstanceState
        mSelectedItem = savedInstanceState == null ? ConstantValues.DEFAULT_HOME_STYLE : savedInstanceState.getString(SELECTED_ITEM_ID);
        setupDefaultHomePage(mSelectedItem);

        bottomNavigationView = findViewById(R.id.btm_navigation);
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
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {

            // Check BackStackEntryCount of FragmentManager
            if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
                // Set DrawerToggle Indicator and default ToolbarNavigationClickListener
                actionBar.setTitle(ConstantValues.APP_HEADER);
                actionBar.setHomeButtonEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(false);

            }
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            setupTitle();

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
        } else if (curruntFrag instanceof UpdateAccountFragment) {
            actionBar.setTitle(getString(R.string.actionAccount));
        } else if (curruntFrag instanceof My_Orders) {
            actionBar.setTitle(getString(R.string.actionOrders));
        } else if (curruntFrag instanceof My_Addresses) {
            actionBar.setTitle(getString(R.string.actionAddresses));
        } else if (curruntFrag instanceof WishList) {
            actionBar.setTitle(getString(R.string.actionFavourites));
        } else if (curruntFrag instanceof SettingsFragment) {
            actionBar.setTitle(getString(R.string.actionSettings));
        } else if (curruntFrag instanceof AccountFragment) {
            actionBar.setTitle(getString(R.string.actionAccount));
        } else if (curruntFrag instanceof MarketPlaceHomeFragment) {
            actionBar.setTitle(getString(R.string.actionMarketPlace));
            DashboardActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        }else if (curruntFrag instanceof BuyInputsHomePage) {
            actionBar.setTitle(getString(R.string.app_name));
            DashboardActivity.bottomNavigationView.setVisibility(View.GONE);
        }else if (curruntFrag instanceof SellProduceFragment) {
            actionBar.setTitle(getString(R.string.actionproducemarket));
        }else if (curruntFrag instanceof Category_Products) {
            DashboardActivity.bottomNavigationView.setVisibility(View.VISIBLE);
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.page_1:
                selectedFragment = new HomeFragment(DashboardActivity.this, getSupportFragmentManager(), MyFarmDbHandlerSingleton.getHandlerInstance(this));
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


    private void setupDefaultHomePage(String defaultHome) {
        defaultHomeFragment = new HomeFragment(DashboardActivity.this, getSupportFragmentManager(), MyFarmDbHandlerSingleton.getHandlerInstance(this));
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, defaultHomeFragment, getString(R.string.homeStyle9)).commit();
        currentFragment = defaultHomeFragment;
    }

    //*********** Called by the System when the Device's Configuration changes while Activity is Running ********//

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Configure ActionBarDrawerToggle with new Configuration
    }

    //*********** Invoked to Save the Instance's State when the Activity may be Temporarily Destroyed ********//

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (Shipping_Address.map != null) {
            outState.putParcelable(Shipping_Address.KEY_CAMERA_POSITION, Shipping_Address.map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, Shipping_Address.lastKnownLocation);
        }
        super.onSaveInstanceState(outState);

        super.onSaveInstanceState(outState);
        // Save the Selected NavigationDrawer Item
        outState.putString(SELECTED_ITEM_ID, mSelectedItem);
    }

    //*********** Set the Base Context for the ContextWrapper ********//

    @Override
    protected void attachBaseContext(Context newBase) {

        String languageCode = ConstantValues.LANGUAGE_CODE;
        if ("".equalsIgnoreCase(languageCode) || languageCode == null)
            languageCode = ConstantValues.LANGUAGE_CODE = "en";

        super.attachBaseContext(LocaleHelper.wrapLocale(newBase, languageCode));
    }

    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.checkout));
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate toolbar_menu Menu
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);

        // Bind Menu Items
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
        MenuItem profileItem = menu.findItem(R.id.toolbar_edit_profile);
//        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);


        currentFragment = this.getSupportFragmentManager().getPrimaryNavigationFragment();

        profileItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to My_Cart Fragment
                Fragment fragment = new UpdateAccountFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (currentFragment == null)
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

        cartItem.setActionView(R.layout.buy_inputs_animated_ic_cart);

        cartItem.getActionView().setOnClickListener(v -> {
            // Navigate to My_Cart Fragment
            Fragment fragment = new My_Cart();
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (currentFragment == null)
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
        });

        // Tint Menu Icons with the help of static method of Utilities class
        Utilities.tintMenuIcon(DashboardActivity.this, languageItem, R.color.white);
//        Utilities.tintMenuIcon(DashboardActivity.this, searchItem, R.color.white);
        Utilities.tintMenuIcon(DashboardActivity.this, cartItem, R.color.white);

        return true;
    }

    //*********** Prepares the OptionsMenu of Toolbar ********//

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);

        // Get No. of Cart Items with the static method of My_Cart Fragment
        int cartSize = My_Cart.getCartSize();


        // if Cart has some Items
        if (cartSize > 0) {

            // Animation for cart_menuItem
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_icon);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setRepeatCount(1);

            cartItem.getActionView().startAnimation(animation);
            cartItem.getActionView().setAnimation(null);


            LayerDrawable icon = null;
            Drawable drawable = cartItem.getIcon();

            if (drawable instanceof DrawableWrapper) {
                drawable = ((DrawableWrapper) drawable).getWrappedDrawable();
            } else if (drawable instanceof WrappedDrawable) {
                drawable = ((WrappedDrawable) drawable).getWrappedDrawable();
            }


            if (drawable instanceof LayerDrawable) {
                icon = (LayerDrawable) drawable;
            } else if (drawable instanceof DrawableWrapper) {
                DrawableWrapper wrapper = (DrawableWrapper) drawable;
                if (wrapper.getWrappedDrawable() instanceof LayerDrawable) {
                    icon = (LayerDrawable) wrapper.getWrappedDrawable();
                }
            }

//                icon = (LayerDrawable) drawable;


            // Set BadgeCount on Cart_Icon with the static method of NotificationBadger class
            if (icon != null)
                NotificationBadger.setBadgeCount(this, icon, String.valueOf(cartSize));


        } else {
            // Set the Icon for Empty Cart
            cartItem.setIcon(R.drawable.ic_cart_empty);
        }


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (item.getItemId()) {

            case android.R.id.home:

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
                else if (fragmentManager.getBackStackEntryCount() > 0) {
                    // Pop previous Fragment
                    fragmentManager.popBackStack();
                } else
                    showHomePage();

                break;
            case R.id.toolbar_ic_cart:

                // Navigate to My_Cart Fragment
                fragment = new My_Cart();
                fragmentManager.beginTransaction()
                        .hide(currentFragment)
                        .add(R.id.main_fragment_container, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
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
//        getSupportFragmentManager().beginTransaction().hide(currentFragment).show(defaultHomeFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_container, defaultHomeFragment).commit();
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

    public static void addDatePickerImageView(final ImageView imageView, final EditText ed_, final Context context) {
        imageView.setOnClickListener(new View.OnClickListener() {
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

        if (value == null) {
            return;
        }

        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spnr.getAdapter();
        for (int position = 1; position < adapter.getCount(); position++) {

            String item = spnr.getAdapter().getItem(position) + "";
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

    public void clearBackStackInclusive(String tag) {
        getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
