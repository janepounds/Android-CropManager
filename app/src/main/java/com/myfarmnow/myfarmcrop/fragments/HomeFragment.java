package com.myfarmnow.myfarmcrop.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.agronomy.AgronomyDashboardActivity;
import com.myfarmnow.myfarmcrop.activities.farmrecords.FarmRecordsDashboardActivity;
import com.myfarmnow.myfarmcrop.activities.predictiontools.PredictionToolsDashboardActivity;
import com.myfarmnow.myfarmcrop.activities.services.ServicesDashboardActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.adapters.NotificationTabsLayoutAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.marketplace.MarketPlaceHomeFragment;
import com.myfarmnow.myfarmcrop.models.CropNotification;
import com.myfarmnow.myfarmcrop.models.weather.WeatherResponse;
import com.myfarmnow.myfarmcrop.network.APIClient;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;

public class HomeFragment extends Fragment implements LocationListener {
    private static final String TAG = "HomeFragment";
    private Context appContext;
    RelativeLayout mainlayout;



    boolean isGPSEnabled = false;// flag for network status
    boolean isNetworkEnabled = false;// flag for GPS status
    boolean canGetLocation = false;
    Location location; // location
    double latitude; // latitude
    double longitude; // longitude// The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters// The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 200 * 10; // 2 seconds// Declaring a Location Manager
    protected LocationManager locationManager;

    private TextView textCommissions, temp_min,temp_max, visibility, humidity, wind_speed, precipitation_type, precipitation, weather_day;

    LinearLayout walletLinearLayout, fieldsLinearLayout, farmRecordsLinearLayout, predictionToolsLinearLayout, marketplaceLinearLayout,
            agronomyLinearLayout, servicesLinearLayout, tasksLinearLayout, userProfileLayout, weatherForecastLinearLayout, contactsLinearLayout;

    TextView unreadNotificationsTextView;

    ImageView noticationsImageBtn;
    NotificationTabsLayoutAdapter notificationTabsLayoutAdapter;

    private TabLayout notificationsTabLayout;
    private ViewPager notificationsViewPager;
    FrameLayout notificationsFrameLayout;
    FragmentManager fm;
    MyFarmDbHandlerSingleton dbHandler;

    public HomeFragment(Context context, FragmentManager supportFragmentManager, MyFarmDbHandlerSingleton handlerInstance) {
        super();
        this.appContext = context;
        this.fm = supportFragmentManager;
        this.dbHandler = handlerInstance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for appContext fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));

        location = getLocation();

        initializeDashboard(view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appContext = context;
    }

    public void initializeDashboard(View view) {
        mainlayout = view.findViewById(R.id.mainlayout);
        walletLinearLayout = view.findViewById(R.id.layout_dashboard_wallet);
        fieldsLinearLayout = view.findViewById(R.id.layout_crop_dashboard_fields);
        farmRecordsLinearLayout = view.findViewById(R.id.layout_dashboard_farmrecords);
        marketplaceLinearLayout = view.findViewById(R.id.layout_dashboard_marketplace);
        agronomyLinearLayout = view.findViewById(R.id.layout_dashboard_agronomy);
        predictionToolsLinearLayout = view.findViewById(R.id.layout_predictiontools_dashboard);
        tasksLinearLayout = view.findViewById(R.id.layout_crop_dashboard_tasks);
        servicesLinearLayout = view.findViewById(R.id.layout_dashboard_service);
        weatherForecastLinearLayout = view.findViewById(R.id.layout_crop_dashboard_weather_forecast);
        temp_max = view.findViewById(R.id.weather_temp_max);
        temp_min = view.findViewById(R.id.weather_temp_min);
        visibility = view.findViewById(R.id.visibility_default);
        humidity = view.findViewById(R.id.humidity_max);
        wind_speed = view.findViewById(R.id.wind_default);
        precipitation_type = view.findViewById(R.id.text_view_rain);
        precipitation = view.findViewById(R.id.rain_status);
        weather_day = view.findViewById(R.id.weather_day);

        walletLinearLayout.setOnClickListener(v -> {
            if (WalletAuthActivity.WALLET_ACCESS_TOKEN == null) {
                startActivity(new Intent(appContext, WalletAuthActivity.class));
                getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            } else {
                Intent authenticate = new Intent(appContext, WalletHomeActivity.class);
                startActivity(authenticate);
                getActivity().finish();
            }
        });

        farmRecordsLinearLayout.setOnClickListener(v -> {
            Intent openFields = new Intent(appContext, FarmRecordsDashboardActivity.class);
            startActivity(openFields);
        });

        marketplaceLinearLayout.setOnClickListener(view1 ->
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_container, new MarketPlaceHomeFragment(), getString(R.string.actionMarketPlace)).commit()
        );

        agronomyLinearLayout.setOnClickListener(v -> {
            Intent openFields = new Intent(appContext, AgronomyDashboardActivity.class);
            startActivity(openFields);
        });

        predictionToolsLinearLayout.setOnClickListener(v -> {
            Intent openFields = new Intent(appContext, PredictionToolsDashboardActivity.class);
            startActivity(openFields);
        });

        servicesLinearLayout.setOnClickListener(v -> {
            Intent openFields = new Intent(appContext, ServicesDashboardActivity.class);
            startActivity(openFields);
        });

        notificationsFrameLayout = view.findViewById(R.id.frame_layout_notifications);
        noticationsImageBtn = view.findViewById(R.id.img_crop_dashboard_notifications);
        unreadNotificationsTextView = view.findViewById(R.id.text_view_crop_dashboard_notification_unread_counter);
        notificationsViewPager = view.findViewById(R.id.viewPager);
        notificationsTabLayout = view.findViewById(R.id.tabLayout);
        notificationTabsLayoutAdapter = new NotificationTabsLayoutAdapter(fm);
        notificationTabsLayoutAdapter.addFragment(new NotificationsTodayFragment(), "Today");
        notificationTabsLayoutAdapter.addFragment(new NotificationsUpcomingFragment(), "Upcoming");
        notificationTabsLayoutAdapter.addFragment(new NotificationsOverDueFragment(), "Over Due");
        notificationsViewPager.setAdapter(notificationTabsLayoutAdapter);
        notificationsTabLayout.setupWithViewPager(notificationsViewPager);
        notificationsTabLayout.setSelectedTabIndicatorColor(Color.GREEN);

        unreadNotificationsTextView.setText("" + dbHandler.getCropNotifications(DashboardActivity.getPreferences("userId", appContext), CropNotification.QUERY_KEY_TODAY).size());

        noticationsImageBtn.setOnClickListener(v -> {
            if (notificationsFrameLayout.getVisibility() == View.GONE) {
                notificationsFrameLayout.setVisibility(View.VISIBLE);
            } else {
                notificationsFrameLayout.setVisibility(View.GONE);
            }
        });

        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
        weather_day.setText(simpleDateformat.format(now));

        ArrayList<String> fieldValues = new ArrayList<>();
        fieldValues.add("temp");
        fieldValues.add("visibility");
        fieldValues.add("humidity");
        fieldValues.add("wind_speed");
        fieldValues.add("precipitation");
        fieldValues.add("precipitation_type");

        //*******************WEATHER API INTEGRATION*************************//
        String WEATHER_API_KEY = getString(R.string.WEATHER_API_KEY);
        Call<WeatherResponse> call = APIClient.getWeatherInstance()
                .getRealtimeWeather(WEATHER_API_KEY, (float) getLatitude(), (float) getLongitude(), null, "si", fieldValues);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NotNull Call<WeatherResponse> call, @NotNull Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    //set temperature
                    double temperature_value = response.body().getTemp().getValue();
                    String temperature_units = response.body().getTemp().getUnits();
//                    temp.setText(temperature_value + " " + "\u2103");

                    //set visibility
                    int visibility_value = response.body().getVisibility().getValue();
                    String visibility_units = response.body().getVisibility().getUnits();
                    visibility.setText(visibility_value + " " + visibility_units);

                    //set humidity
                    double humidity_value = response.body().getHumidity().getValue();
                    String humidity_units = response.body().getHumidity().getUnits();
                    humidity.setText(humidity_value + " " + humidity_units);

                    //set wind speed
                    double wind_speed_value = response.body().getWindSpeed().getValue();
                    String wind_speed_units = response.body().getWindSpeed().getUnits();
                    wind_speed.setText(wind_speed_value + " " + wind_speed_units);

                    //set precipitation
                    double precipitation_value = response.body().getPrecipitation().getValue();
                    String precipitation_units = response.body().getPrecipitation().getUnits();
                    if (precipitation_value == 0.0) {
                        precipitation.setText("No");
                    } else {
                        precipitation.setText(precipitation_value + " " + precipitation_units);
                    }

//                    //set precipitation type
//                    String precipitation_type_value = response.body().getPrecipitationType().getValue();
//                    String precipitation_type_capitalized = precipitation_type_value.substring(0, 1).toUpperCase() + precipitation_type_value.substring(1);
//                    precipitation_type.setText(precipitation_type_capitalized);

                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG);
                    Log.d(TAG, "onResponse:  " + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NotNull Call<WeatherResponse> call, @NotNull Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG);
            }
        });

        ArrayList<String> fieldValuess = new ArrayList<>();
        fieldValuess.add("temp");

        //******************FORECAST********//
        Call<WeatherResponse[]> call_hourlycast = APIClient.getWeatherInstance()
                .getHourlyCastTemp(WEATHER_API_KEY, (float) getLatitude(), (float) getLongitude(), null, "si","now",generateTodaysMidNightTimestamp(), fieldValuess);
        call_hourlycast.enqueue(new Callback<WeatherResponse[]>() {
            @Override
            public void onResponse(Call<WeatherResponse[]> call, Response<WeatherResponse[]> response) {
                if (response.isSuccessful()) {
                    //get maximum and minimum temperature
                    double temperature_max=0, temperature_min=100; String temperature_units="C";
                    for (WeatherResponse weather : response.body() ) {

                        if(weather.getTemp().getValue()>temperature_max)
                            temperature_max=weather.getTemp().getValue();

                        if(weather.getTemp().getValue()<temperature_min)
                            temperature_min=weather.getTemp().getValue();

                    }


                    temp_min.setText(temperature_min + " " + "\u2103");
                    temp_max.setText(temperature_max + " " + "\u2103");

                }else{
                    Log.d(TAG, "onResponse: failed"+generateTodaysMidNightTimestamp());
                }

            }

            @Override
            public void onFailure(Call<WeatherResponse[]> call, Throwable t) {

            }
        });
    }

    public static void addDatePicker(final EditText ed_, final Context context) {
        ed_.setOnClickListener(view -> {
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
        });
        ed_.setInputType(InputType.TYPE_NULL);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NotNull MenuInflater inflater) {
        // Hide Cart Icon in the Toolbar
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
        MenuItem profileItem = menu.findItem(R.id.toolbar_edit_profile);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);
        profileItem.setVisible(false);
        languageItem.setVisible(false);
        currencyItem.setVisible(false);
        searchItem.setVisible(false);
        cartItem.setVisible(false);
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) requireContext().getSystemService(LOCATION_SERVICE);// getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);// getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                // No network provider is enabled
                Log.e("Network-GPS", "Disabled");
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                        } else {
                            return null;
                        }
                    }

                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    // Log.e(“Network”, “Network”);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                } else
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            //Log.e(“GPS Enabled”, “GPS Enabled”);
                            if (locationManager != null) {
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        // Do nothing
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Do nothing
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        // Do nothing
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        // Do nothing
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                location = getLocation();
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



    //generate midnight timestamp

    public  String generateTodaysMidNightTimestamp(){
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());

        return nowAsISO+"T23:59:00.000Z";
    }
}
