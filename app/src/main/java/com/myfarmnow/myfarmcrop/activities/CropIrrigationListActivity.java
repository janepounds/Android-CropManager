package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropIrrigationListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropIrrigationListActivity extends AppCompatActivity {

    RecyclerView irrigationListRecyclerView;
    CropIrrigationListRecyclerAdapter cropIrrigationListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_irrigation_list);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        irrigationListRecyclerView = findViewById(R.id.crop_irrigation_recyc_view);
        cropIrrigationListRecyclerAdapter = new CropIrrigationListRecyclerAdapter(this,dbHandler.getCropIrrigations(CropDashboardActivity.getPreferences("userId",this)));
        irrigationListRecyclerView.setAdapter(cropIrrigationListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        irrigationListRecyclerView.setLayoutManager(linearLayoutManager);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add_new:
                openCropIrrigationManagerActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropIrrigationManagerActivity() {
        Intent intent = new Intent(this, CropIrrigationManagerActivity.class);
        startActivity(intent);
    }
}
