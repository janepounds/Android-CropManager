package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropActivitiesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropActivity;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropSpraying;

import java.util.ArrayList;

public class CropActivitiesListActivity extends AppCompatActivity {

    RecyclerView activitiesListRecyclerView;
    CropActivitiesListRecyclerAdapter activitiesListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    String cropId;
    ArrayList<CropActivity> cropActivities = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_activities_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("cropId")){
            cropId =getIntent().getStringExtra("cropId");
        }
        else{
            finish();
        }
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        activitiesListRecyclerView = findViewById(R.id.crop_activities_recyc_view);

        ArrayList<CropFertilizerApplication> cropFertilizerApplications =dbHandler.getCropFertilizerApplication(cropId);

        for(CropFertilizerApplication application :cropFertilizerApplications ){
            cropActivities.add(application);
        }
        ArrayList<CropCultivation> cropCultivations =dbHandler.getCropCultivates(cropId);

        for(CropCultivation cultivation :cropCultivations ){
            cropActivities.add(cultivation);
        }
        ArrayList<CropSpraying> cropSprayings =dbHandler.getCropSprayings(cropId);

        for(CropSpraying spraying :cropSprayings ){
            cropActivities.add(spraying);
        }

        activitiesListRecyclerAdapter = new CropActivitiesListRecyclerAdapter(cropActivities,this);
        activitiesListRecyclerView.setAdapter(activitiesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        activitiesListRecyclerView.setLayoutManager(linearLayoutManager);
    }

}
