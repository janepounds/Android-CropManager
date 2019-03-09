package com.myfarmnow.myfarmcrop.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.NavDrawerItem;

import java.util.ArrayList;
import java.util.Calendar;

public class CropDashboardActivity extends AppCompatActivity {

    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ImageView imgdrawer;
    RelativeLayout mainlayout;
    Toolbar toolbar;
    ExpandableListView expandableListView;
    ArrayList<NavDrawerItem> menuList = new ArrayList<>();
    LinearLayout inventoryLinearLayout,fieldsLinearLayout, machinesLinearLayout,cropsLinearLayout;
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
        expandableListView = findViewById(R.id.drawer_menu_list);

        mainlayout = findViewById(R.id.mainlayout);
        inventoryLinearLayout =findViewById(R.id.layout_crop_dashboard_inventory);
        fieldsLinearLayout =findViewById(R.id.layout_crop_dashboard_fields);
        machinesLinearLayout =findViewById(R.id.layout_crop_dashboard_machines);
        cropsLinearLayout =findViewById(R.id.layout_crop_dashboard_crops);

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


        SavePreferences("userId", "1");
        SavePreferences("email", "georgia@gmail.com");
        SavePreferences("Firmname", "Crop Farm");
        SavePreferences("firstname", "Georgia" );

        CropField n = new CropField();


        n.setSoilType("Sand");
        n.setSoilCategory("Shallow");
        n.setFieldName("Field1");
        n.setWatercourse("Dry Ditch");
        float z =45;
        n.setTotalArea(z);
        n.setUserId("1");
        float b = (float) 20.5;
        n.setCroppableArea(b);



       /// MyFarmDbHandlerSingleton.getHandlerInstance(this).insertCropField(n);

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
                        ed_.setText( selectedyear+ "-" + month + "-" +selectedday );
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();

            }
        });
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
        return sharedPreferences.getString(key, "0");

    }
    public static void selectSpinnerItemByValue(Spinner spnr, String value) {

        ArrayAdapter<String> adapter = (ArrayAdapter) spnr.getAdapter();
        if(value==null){
            return;
        }
        for (int position = 0; position < adapter.getCount(); position++) {
            String item =(String)adapter.getItem(position);
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
            if(item.toLowerCase().equals(id)){
                spnr.setSelection(position);
                return;
            }

        }
    }


}
