package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;

public class CropInventorySeedsManagerActivity extends AppCompatActivity {

    CropInventorySeeds seedsInventoryToEdit=null;
    EditText purchaseDatTxt, seedNameTxt,varietyTxt,dressingTxt,tgwTxt,quantityTxt,costTxt;
    Spinner usageUnitSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_inventory_seeds_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       //txt_crop_purchase_date
       //
       //txt_crop_variety
       //txt_crop_dressing
       //txt_crop_tgw
       //txt_crop_usage_unit
       //txt_crop_quantity
       //txt_crop_cost


    }

    public void initializeForm(){
        purchaseDatTxt = findViewById(R.id.txt_crop_purchase_date);
        seedNameTxt = findViewById(R.id.txt_crop_seed_name);
    }

}
