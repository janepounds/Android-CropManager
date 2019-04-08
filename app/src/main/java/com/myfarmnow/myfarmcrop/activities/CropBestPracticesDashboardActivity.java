package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.myfarmnow.myfarmcrop.R;

public class CropBestPracticesDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_best_practices_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void openMaizeBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","maize.pdf");
        openPDF.putExtra("pageTitle","Maize Best Practices");
        startActivity(openPDF);
    }
    public void openBeansBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","beans.pdf");
        openPDF.putExtra("pageTitle","Beans Best Practices");
        startActivity(openPDF);
    }
    public void openCabbageBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","cabbage.pdf");
        openPDF.putExtra("pageTitle","Cabbage Best Practices");
        startActivity(openPDF);
    }
    public void openCarrotBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","carrot.pdf");
        openPDF.putExtra("pageTitle","Carrot Best Practices");
        startActivity(openPDF);
    }
    public void openEggPlantBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","egg_plants.pdf");
        openPDF.putExtra("pageTitle","Egg Plants Best Practices");
        startActivity(openPDF);
    }
    public void openGroundNutsBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","ground_nuts.pdf");
        openPDF.putExtra("pageTitle","Ground Nuts Best Practices");
        startActivity(openPDF);
    }
    public void openOnionBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","ground_nuts.pdf");
        openPDF.putExtra("pageTitle","Onions Best Practices");
        startActivity(openPDF);
    }
    public void openPassionFruitBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","passion_fruit.pdf");
        openPDF.putExtra("pageTitle","Passion Fruit Best Practices");
        startActivity(openPDF);
    }
    public void openPineappleBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","pineapple.pdf");
        openPDF.putExtra("pageTitle","Pineapple Best Practices");
        startActivity(openPDF);
    }
    public void openPumpkinBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","pumpkin.pdf");
        openPDF.putExtra("pageTitle","Pumpkin Best Practices");
        startActivity(openPDF);
    }
    public void openSoyaBeansBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","soya_bean.pdf");
        openPDF.putExtra("pageTitle","Soya Beans Best Practices");
        startActivity(openPDF);
    }
    public void openTomatoBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","tomato.pdf");
        openPDF.putExtra("pageTitle","Tomatoes Best Practices");
        startActivity(openPDF);
    }
    public void openWaterMelonBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","water_melon.pdf");
        openPDF.putExtra("pageTitle","Water Melon Best Practices");
        startActivity(openPDF);
    }

}
