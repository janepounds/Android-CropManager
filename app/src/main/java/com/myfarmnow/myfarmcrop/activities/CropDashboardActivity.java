package com.myfarmnow.myfarmcrop.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.adapters.NavigationAdapterExpand;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.CropSalesOrder;
import com.myfarmnow.myfarmcrop.models.NavDrawerItem;
import com.myfarmnow.myfarmcrop.models.NavDrawerItemchild;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CropDashboardActivity extends AppCompatActivity {

    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ImageView imgdrawer;
    RelativeLayout mainlayout;
    Toolbar toolbar;
    ArrayList<NavDrawerItem> menuList = new ArrayList<>();
    LinearLayout inventoryLinearLayout,fieldsLinearLayout, machinesLinearLayout,cropsLinearLayout, incomeExpenseLinearLayout, tasksLinearLayout,userProfileLayout;
    TextView textViewUserEmail, textViewUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_dashboard);
        toolbar=  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initializeDashboard();

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
        userProfileLayout =findViewById(R.id.layout_user_profile);
        textViewUserName =findViewById(R.id.text_view_crop_dashboard_name);
        textViewUserEmail =findViewById(R.id.text_view_crop_dashboard_email);


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
        //

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
                // TODO Auto-generated method stub
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

    public void showHideFinancialManager(View view){
        LinearLayout financialsSubMenu = findViewById(R.id.layout_crop_dashboard_financial_submenus);

        toggleVisibility(financialsSubMenu);
    }
    public void showSalesManager(View view){
        LinearLayout slesSubMenu = findViewById(R.id.layout_crop_dashboard_financial_sales_submenus);

        toggleVisibility(slesSubMenu);
    }
    public void showHideFieldManager(View view){
        LinearLayout fieldsSubMenu = findViewById(R.id.layout_crop_dashboard_field_submenus);

        toggleVisibility(fieldsSubMenu);
    }
    public void showHideCropManager(View view){
        LinearLayout cropsSubMenu = findViewById(R.id.layout_crop_dashboard_crops_submenus);

        toggleVisibility(cropsSubMenu);
    }
    public void showHideInventoryManager(View view){
        LinearLayout inventorySubMenu = findViewById(R.id.layout_crop_dashboard_inventory_submenus);

        toggleVisibility(inventorySubMenu);
    }
    public void showHideContactsManager(View view){
        LinearLayout contactsSubMenu = findViewById(R.id.layout_crop_dashboard_contact_submenus);

        toggleVisibility(contactsSubMenu);
    }


    public void toggleVisibility(View view){
        if(view.getVisibility() == View.GONE){
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
                Log.d("ITEMS ",item+" "+id);
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
        CropDashboardActivity.savePreferences("farmname", user.getString("farmname"), context);
        CropDashboardActivity.savePreferences("firstname", user.getString("firstname"), context);
        CropDashboardActivity.savePreferences("email", user.getString("email"), context);
        CropDashboardActivity.savePreferences("userId", user.getString("id"), context);
        CropDashboardActivity.savePreferences("lastname", user.getString("lastname"), context);
        CropDashboardActivity.savePreferences("country", user.getString("country"), context);
        CropDashboardActivity.savePreferences("countryCode",  user.getString("countryCode"), context);
        CropDashboardActivity.savePreferences("email", user.getString("email"), context);
        CropDashboardActivity.savePreferences("addressStreet", user.getString("addressStreet"), context);
        CropDashboardActivity.savePreferences("addressCityOrTown", user.getString("addressCityOrTown"), context);
        CropDashboardActivity.savePreferences("addressCountry", user.getString("addressCountry"), context);
        CropDashboardActivity.savePreferences("phoneNumber", user.getString("phoneNumber"), context);
        CropDashboardActivity.savePreferences("latitude", user.getString("latitude"), context);
        CropDashboardActivity.savePreferences("longitude", user.getString("longitude"), context);
       // CropDashboardActivity.savePreferences("userimage", user.getString("userimage"), context);

    }


}
