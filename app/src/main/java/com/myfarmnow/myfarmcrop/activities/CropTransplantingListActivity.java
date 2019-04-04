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
import com.myfarmnow.myfarmcrop.adapters.CropTransplantingListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropTransplantingListActivity extends AppCompatActivity {

    RecyclerView transplantingListRecyclerView;
    CropTransplantingListRecyclerAdapter cropTransplantingListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_transplanting_list);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        transplantingListRecyclerView = findViewById(R.id.crop_transplanting_recyc_view);
        cropTransplantingListRecyclerAdapter = new CropTransplantingListRecyclerAdapter(this,dbHandler.getCropTransplantings(CropDashboardActivity.getPreferences("userId",this)));
        transplantingListRecyclerView.setAdapter(cropTransplantingListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        transplantingListRecyclerView.setLayoutManager(linearLayoutManager);

    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add_new:
                openCropTransplantingManagerActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropTransplantingManagerActivity() {
        Intent intent = new Intent(this, CropTransplantingManagerActivity.class);
        startActivity(intent);
    }


}
