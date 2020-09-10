package com.myfarmnow.myfarmcrop.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.net.Uri;

import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropCalculatorsActivity;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropFertilizerCalculatorEntryActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.AccountFragment;
import com.myfarmnow.myfarmcrop.fragments.HomeFragment;
import com.myfarmnow.myfarmcrop.fragments.OffersFragment;
import com.myfarmnow.myfarmcrop.models.ApiPaths;
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

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = "DashboardActivity";

    public static final String PREFERENCES_FILE_NAME = "pref";

    public static final String FARM_NAME_PREFERENCES_ID = "farmname";
    public static final String STREET_PREFERENCES_ID = "addressStreet";
    public static final String CITY_PREFERENCES_ID = "addressCityOrTown";
    public static final String COUNTRY_PREFERENCES_ID = "addressCountry";
    public static final String PREFERENCES_FIRST_NAME = "firstname";
    public static final String PREFERENCES_LAST_NAME = "lastname";
    public static final String PREFERENCES_USER_ID = "userId";
    public static final String PREFERENCES_USER_EMAIL = "email";
    public static final String PREFERENCES_PHONE_NUMBER = "phoneNumber";

    public static final String PREFERENCES_FIREBASE_TOKEN_SUBMITTED = "tokenSubmitted";
    public static final String PREFERENCES_USER_BACKED_UP = "userBackedUp";
    public static final String PREFERENCES_USER_PASSWORD = "password";

    public static final String TASK_BACKUP_DATA_TAG = "SYNC_SERVICE";
    public static final String TASK_SEND_NOTIFICATIONS_TAG = "SEND_NOTIFICATIONS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        MyFarmDbHandlerSingleton.getHandlerInstance(this).initializeSettings(getPreferences("userId", this));

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.replace(R.id.fragment_crop_dashboard_graphs_section, new CropDashboardGraphsFragment()).commit();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(DashboardActivity.this, getSupportFragmentManager(),  MyFarmDbHandlerSingleton.getHandlerInstance(this) )).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_navigation);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        if (!getPreferences(PREFERENCES_FIREBASE_TOKEN_SUBMITTED, DashboardActivity.this).equals("yes")) {
            getAppToken();
        }

        startService(new Intent(this, CropSyncService.class));
        scheduleBackgroundWork();
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

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };

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

                        sendFirebaseToken(token, DashboardActivity.this);
                    }
                });

    }

    public static void sendFirebaseToken(String token, final Context context) {
        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        // client.addHeader("Authorization","Bearer "+CropWalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("email", DashboardActivity.getPreferences(DashboardActivity.PREFERENCES_USER_EMAIL, context));
        params.put("firebaseToken", token);

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
                        savePreferences(PREFERENCES_FIREBASE_TOKEN_SUBMITTED, "yes", context);
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

    public void openFertilizerCalculator(View view) {
        //CropFertilizerCalculatorEntryActivity
        Intent openList = new Intent(this, CropFertilizerCalculatorEntryActivity.class);
        startActivity(openList);
    }



    public void openCalculators(View view) {
        Intent openList = new Intent(this, CropCalculatorsActivity.class);
        startActivity(openList);
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

    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref",
                0);
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

    public void logout(View view) {

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
                SharedPreferences sharedPreferences = DashboardActivity.this.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                //remove database
                DashboardActivity.this.deleteDatabase(MyFarmDbHandlerSingleton.DATABASE_NAME);

                WorkManager.getInstance().cancelAllWorkByTag(TASK_BACKUP_DATA_TAG);
                WorkManager.getInstance().cancelAllWorkByTag(TASK_SEND_NOTIFICATIONS_TAG);
                finish();
                Intent openList = new Intent(DashboardActivity.this, CropLoginActivity.class);
                startActivity(openList);
            }
        }, 10000);


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
