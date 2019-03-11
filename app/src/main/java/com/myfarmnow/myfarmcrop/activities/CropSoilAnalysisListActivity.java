package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSoilAnalysisListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropSoilAnalysisListActivity extends AppCompatActivity {

    RecyclerView soilAnalysisListRecyclerView;
    CropSoilAnalysisListRecyclerAdapter cropsoilAnalysissListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    String cropId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_soil_analysis_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("cropId")){
            cropId =getIntent().getStringExtra("cropId");
        }
        else{
            finish();
        }
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        soilAnalysisListRecyclerView = findViewById(R.id.crop_soil_analysis_recyc_view);
        cropsoilAnalysissListRecyclerAdapter = new CropSoilAnalysisListRecyclerAdapter(this,dbHandler.getCropSoilAnalysis(cropId));
        soilAnalysisListRecyclerView.setAdapter(cropsoilAnalysissListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        soilAnalysisListRecyclerView.setLayoutManager(linearLayoutManager);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_add_new:
                Intent intent = new Intent(this, CropSoilAnalysisManagerActivity.class);
                intent.putExtra("cropId",cropId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
