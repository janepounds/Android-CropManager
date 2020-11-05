package com.myfarmnow.myfarmcrop.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private Context appContext;
    RelativeLayout mainlayout;
    LinearLayout contactsSubMenu, helpSubMenu, inventorySubMenu, cropsSubMenu, financialsSubMenu, slesSubMenu, purchasesSubMenu, digitalWalletLayout;


    LinearLayout walletLinearLayout, fieldsLinearLayout, farmRecordsLinearLayout, predictionToolsLinearLayout, marketplaceLinearLayout,
            agronomyLinearLayout, servicesLinearLayout, tasksLinearLayout, userProfileLayout, weatherForecastLinearLayout, contactsLinearLayout;

    TextView textViewUserEmail, textViewUserName, textViewVersion;

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

        initializeDashboard(view);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        appContext = context;
    }


    public void initializeDashboard(View view) {
        //expandableListView = view.findViewById(R.id.drawer_menu_list);
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

//        textViewVersion = view.findViewById(R.id.text_view_crop_dashboard_android_version);
//        textViewVersion.setText("version " + BuildConfig.VERSION_NAME);

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


//        tasksLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openTasks = new Intent(appContext, CropTasksListActivity.class);
//                startActivity(openTasks);
//            }
//        });
//        contactsLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openContacts = new Intent(appContext, CropContactsListActivity.class);
//                startActivity(openContacts);
//            }
//        });

//        textViewUserName.setText(DashboardActivity.getPreferences("firstname", appContext) + " " + DashboardActivity.getPreferences("lastname", appContext));
//        textViewUserEmail.setText(DashboardActivity.getPreferences("email", appContext));
//
//        userProfileLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent editUser = new Intent(appContext, CropRegisterActivity.class);
//                editUser.putExtra("editUser", "yes");
//                startActivity(editUser);
//            }
//        });

//        //relavant for general version but this update is available for UG
//        if (DashboardActivity.getPreferences(DashboardActivity.COUNTRY_PREFERENCES_ID, appContext).toLowerCase().equals("uganda")) {
//            //digitalWalletLayout.setVisibility(View.VISIBLE);
//        }


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


        noticationsImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notificationsFrameLayout.getVisibility() == View.GONE) {
                    notificationsFrameLayout.setVisibility(View.VISIBLE);
                } else {
                    notificationsFrameLayout.setVisibility(View.GONE);
                }
            }
        });


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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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

}
