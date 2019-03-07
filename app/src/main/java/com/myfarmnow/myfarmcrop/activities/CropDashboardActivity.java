package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.NavDrawerItem;

import java.util.ArrayList;

public class CropDashboardActivity extends AppCompatActivity {

    public static DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    ImageView imgdrawer;
    RelativeLayout mainlayout;
    Toolbar toolbar;
    ExpandableListView expandableListView;
    ArrayList<NavDrawerItem> menuList = new ArrayList<>();
    LinearLayout inventoryLinearLayout;
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

    }

}
