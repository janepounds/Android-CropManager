package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropActivitiesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropActivity;
import com.myfarmnow.myfarmcrop.models.CropCultivation;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropIrrigation;
import com.myfarmnow.myfarmcrop.models.CropScouting;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSpraying;
import com.myfarmnow.myfarmcrop.models.CropTransplanting;

import java.util.ArrayList;

public class CropActivitiesListActivity extends AppCompatActivity {

    RecyclerView activitiesListRecyclerView;
    CropActivitiesListRecyclerAdapter activitiesListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    String cropId;
    ArrayList<CropActivity> cropActivities = new ArrayList<>();
    ArrayList<CropActivity> cropInventoryList = new ArrayList();
    ArrayList<CropActivity> cropListBackUp = new ArrayList();
    Spinner selectActivitySpinner;
    CropSpinnerAdapter cropSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_activities_list);

        if(getIntent().hasExtra("cropId")){
            cropId =getIntent().getStringExtra("cropId");
        }
        else{
            finish();
        }
        selectActivitySpinner = findViewById(R.id.select_activity_spinner);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        activitiesListRecyclerView = findViewById(R.id.crop_activities_recyc_view);

        ArrayList<CropFertilizerApplication> cropFertilizerApplications =dbHandler.getCropFertilizerApplications(cropId);

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
        ArrayList<CropScouting> cropScoutings =dbHandler.getCropScoutings(cropId);

        for(CropScouting scouting :cropScoutings ){
            cropActivities.add(scouting);
        }
        ArrayList<CropIrrigation> cropIrrigations =dbHandler.getCropIrrigations(cropId);

        for(CropIrrigation irrigation :cropIrrigations ){
            cropActivities.add(irrigation);
        }
        ArrayList<CropTransplanting> cropTransplantings =dbHandler.getCropTransplantings(cropId);

        for(CropTransplanting transplanting :cropTransplantings ){
            cropActivities.add(transplanting);
        }
        ArrayList<CropHarvest> cropHarvests =dbHandler.getCropHarvests(cropId);

        for(CropHarvest harvest :cropHarvests ){
            cropActivities.add(harvest);
        }

        activitiesListRecyclerAdapter = new CropActivitiesListRecyclerAdapter(cropActivities,this);
        activitiesListRecyclerView.setAdapter(activitiesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        activitiesListRecyclerView.setLayoutManager(linearLayoutManager);

        cropSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Activity",this);
        selectActivitySpinner.setAdapter(cropSpinnerAdapter);
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Cultivate",CropActivity.CROP_ACTIVITY_CULTIVATE));
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Fertilizer Application",CropActivity.CROP_ACTIVITY_FERTILIZER_APPLICATION));
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Spraying",CropActivity.CROP_ACTIVITY_SPRAYING));
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Scouting",CropActivity.CROP_ACTIVITY_SCOUTING));
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Harvesting",CropActivity.CROP_ACTIVITY_HARVESTING));
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Irrigation",CropActivity.CROP_ACTIVITY_IRRIGATION));
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Transplanting",CropActivity.CROP_ACTIVITY_TRANSPLANTING));
        selectActivitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<CropActivity> filteredList = new ArrayList<>();
                if(cropListBackUp.size() ==0 ){
                    // cropArrayList.clear();
                    for(CropActivity x :activitiesListRecyclerAdapter.getCropsList()){
                        cropListBackUp.add(x);
                    }
                }
                if(position !=0){
                    int selection = ((CropActivitySpinnerItem)selectActivitySpinner.getSelectedItem()).getIdentifier();
                    //filteredList.clear();
                    for(CropActivity x :cropListBackUp){

                        if(selection==x.getType()){
                            filteredList.add(x);
                        }

                    }
                    activitiesListRecyclerAdapter.changeList(filteredList);

                }
                else{
                    for(CropActivity x :cropListBackUp){
                        filteredList.add(x);
                    }
                    activitiesListRecyclerAdapter.changeList(filteredList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private class CropActivitySpinnerItem implements CropSpinnerItem{

        int identifier=0;
        String label;
        public CropActivitySpinnerItem(String label, int identifier){
            this.label =label;
            this.identifier =identifier;
        }
        @Override
        public String getId() {
            return null;
        }

        public int getIdentifier() {
            return identifier;
        }

        @Override
        public String toString() {
            return label;
        }
    }

}
