package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropYieldsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropYieldPerformanceActivity extends AppCompatActivity {

    RecyclerView yieldListRecyclerView;
    CropYieldsListRecyclerAdapter cropYieldsListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_yield_performance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        yieldListRecyclerView = findViewById(R.id.crop_yield_recyc_view);
        cropYieldsListRecyclerAdapter = new CropYieldsListRecyclerAdapter(this,dbHandler.getCropsYield(CropDashboardActivity.getPreferences("userId",this)));
        yieldListRecyclerView.setAdapter(cropYieldsListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        yieldListRecyclerView.setLayoutManager(linearLayoutManager);
    }

}
