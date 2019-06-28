package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import com.myfarmnow.myfarmcrop.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        openPDF.putExtra("fileName","eggplants.pdf");
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
        openPDF.putExtra("fileName","onions.pdf");
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
        openPDF.putExtra("fileName","pinneapple.pdf");
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
    public void openSorghumBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","sorghum.pdf");
        openPDF.putExtra("pageTitle","Sorghum Best Practices");
        startActivity(openPDF);
    }
    public void openGarlicBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","garlic.pdf");
        openPDF.putExtra("pageTitle","Garlic Best Practices");
        startActivity(openPDF);
    }

    public void openBananaBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","banana.pdf");
        openPDF.putExtra("pageTitle","Banana Best Practices");
        startActivity(openPDF);
    }
    public void openCoffeeBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","coffee.pdf");
        openPDF.putExtra("pageTitle","Coffee Best Practices");
        startActivity(openPDF);
    }
    public void openRiceBestPractices(View view){
        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
        openPDF.putExtra("fileName","rice.pdf");
        openPDF.putExtra("pageTitle","Rice Best Practices");
        startActivity(openPDF);
    }

}
