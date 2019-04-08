package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.myfarmnow.myfarmcrop.R;

public class CropCalculatorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_calculators);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }
    public void openFertilizerCalculator(View view){
        Intent openList = new Intent(this, CropFertilizerCalculatorEntryActivity.class);
        startActivity(openList);
    }
    public void openRevenueCalculator(View view){
        Intent openList = new Intent(this, CropROIStep1Activity.class);
        startActivity(openList);
    }

    public void openNutrientsCalculator(View view){
        Intent openList = new Intent(this, CropNutrientsCalculatorEntryActivity.class);
        startActivity(openList);
    }

}
