package com.myfarmnow.myfarmcrop.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.myfarmnow.myfarmcrop.BuildConfig;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropContactsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropRegisterActivity;
import com.myfarmnow.myfarmcrop.activities.CropTasksListActivity;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.farmrecords.CropIncomeExpensesListActivity;
import com.myfarmnow.myfarmcrop.activities.farmrecords.FarmRecordsDashboardActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.adapters.NotificationTabsLayoutAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNotification;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private Context appContext;
    RelativeLayout mainlayout;
    LinearLayout contactsSubMenu, helpSubMenu, inventorySubMenu, cropsSubMenu, financialsSubMenu, slesSubMenu, purchasesSubMenu, digitalWalletLayout;
    Toolbar toolbar;

    LinearLayout walletLinearLayout,fieldsLinearLayout, machinesLinearLayout,cropsLinearLayout,
            incomeExpenseLinearLayout, tasksLinearLayout,userProfileLayout, weatherForecastLinearLayout, contactsLinearLayout;

    TextView textViewUserEmail, textViewUserName, textViewVersion;

    private BottomNavigationView bottomNavigationView;
    TextView unreadNotificationsTextView;

    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ImageView imgdrawer, noticationsImageBtn;
    NotificationTabsLayoutAdapter notificationTabsLayoutAdapter;

    private TabLayout notificationsTabLayout;
    private ViewPager notificationsViewPager;
    FrameLayout notificationsFrameLayout;
    FragmentManager fm;
    MyFarmDbHandlerSingleton dbHandler;

    public HomeFragment(Context context, FragmentManager supportFragmentManager, MyFarmDbHandlerSingleton handlerInstance){
        this.appContext = context;
        this.fm=supportFragmentManager;
        this.dbHandler =handlerInstance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for appContext fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        
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
        walletLinearLayout =view.findViewById(R.id.layout_dashboard_wallet);
        fieldsLinearLayout =view.findViewById(R.id.layout_crop_dashboard_fields);
        machinesLinearLayout =view.findViewById(R.id.layout_crop_dashboard_machines);
        incomeExpenseLinearLayout =view.findViewById(R.id.layout_crop_dashboard_income_expense);
        cropsLinearLayout =view.findViewById(R.id.layout_crop_dashboard_crops);
        tasksLinearLayout =view.findViewById(R.id.layout_crop_dashboard_tasks);
        weatherForecastLinearLayout =view.findViewById(R.id.layout_crop_dashboard_weather_forecast);
        contactsLinearLayout =view.findViewById(R.id.layout_crop_dashboard_contacts);
       

        userProfileLayout = view.findViewById(R.id.layout_user_profile);
        textViewUserName = view.findViewById(R.id.text_view_crop_dashboard_name);
        textViewUserEmail = view.findViewById(R.id.text_view_crop_dashboard_email);

        textViewVersion = view.findViewById(R.id.text_view_crop_dashboard_android_version);
        textViewVersion.setText("version " + BuildConfig.VERSION_NAME);

        contactsSubMenu = view.findViewById(R.id.layout_crop_dashboard_contact_submenus);
        helpSubMenu = view.findViewById(R.id.layout_crop_dashboard_help_submenus);
        inventorySubMenu = view.findViewById(R.id.layout_crop_dashboard_inventory_submenus);
        cropsSubMenu = view.findViewById(R.id.layout_crop_dashboard_crops_submenus);
        financialsSubMenu = view.findViewById(R.id.layout_crop_dashboard_financial_submenus);
        slesSubMenu = view.findViewById(R.id.layout_crop_dashboard_financial_sales_submenus);
        purchasesSubMenu = view.findViewById(R.id.layout_crop_dashboard_financial_purchases_submenus);


        digitalWalletLayout = view.findViewById(R.id.layout_crop_dashboard_digital_wallet);



        walletLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(WalletAuthActivity.WALLET_ACCESS_TOKEN==null){
                    startActivity(new Intent(appContext, WalletAuthActivity.class));
                    //finish();
                    getActivity().overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
                }
                else {
                    //WalletAuthActivity.startAuth(context, true);
                    Intent authenticate = new Intent(appContext, WalletHomeActivity.class);
                    startActivity(authenticate);
                    getActivity().finish();
                }
            }
        });

//        fieldsLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openFields = new Intent(appContext, CropFieldsListActivity.class);
//                startActivity(openFields);
//            }
//        });
        machinesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent openMachines = new Intent(appContext, CropMachinesListActivity.class);
                //startActivity(openMachines);
            }
        });
        cropsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCrops = new Intent(appContext, FarmRecordsDashboardActivity.class);
                startActivity(openCrops);
            }
        });
        incomeExpenseLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openIncomeExpense = new Intent(appContext, CropIncomeExpensesListActivity.class);
                startActivity(openIncomeExpense);
            }
        });
        tasksLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openTasks = new Intent(appContext, CropTasksListActivity.class);
                startActivity(openTasks);
            }
        });
        contactsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openContacts = new Intent(appContext, CropContactsListActivity.class);
                startActivity(openContacts);
            }
        });

        textViewUserName.setText(DashboardActivity.getPreferences("firstname", appContext) + " " + DashboardActivity.getPreferences("lastname", appContext));
        textViewUserEmail.setText(DashboardActivity.getPreferences("email", appContext));

        userProfileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editUser = new Intent(appContext, CropRegisterActivity.class);
                editUser.putExtra("editUser", "yes");
                startActivity(editUser);
            }
        });

//        //relavant for general version but this update is available for UG
//        if (DashboardActivity.getPreferences(DashboardActivity.COUNTRY_PREFERENCES_ID, appContext).toLowerCase().equals("uganda")) {
//            //digitalWalletLayout.setVisibility(View.VISIBLE);
//        }

        mDrawerLayout = view.findViewById(R.id.drawer_layout);
        //expandableListView = findViewById(R.id.drawer_menu_list);

        notificationsFrameLayout =view.findViewById(R.id.frame_layout_notifications);
        noticationsImageBtn =view.findViewById(R.id.img_crop_dashboard_notifications);
        unreadNotificationsTextView =view.findViewById(R.id.text_view_crop_dashboard_notification_unread_counter);
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

        mDrawerToggle = new ActionBarDrawerToggle( (AppCompatActivity) appContext, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                getActivity().supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainlayout.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };
        imgdrawer = view.findViewById(R.id.imgdrawer);
        imgdrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
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
}
