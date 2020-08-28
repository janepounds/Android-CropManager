package com.myfarmnow.myfarmcrop.activities.farmrecords;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Menu;


import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropFieldsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropFieldsListActivity extends AppCompatActivity {

    RecyclerView fieldListRecyclerView;
    CropFieldsListRecyclerAdapter cropFieldsListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_fields_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        fieldListRecyclerView = findViewById(R.id.crop_field_recyc_view);
        cropFieldsListRecyclerAdapter = new CropFieldsListRecyclerAdapter(this,dbHandler.getCropFields(DashboardActivity.getPreferences("userId",this)));
        fieldListRecyclerView.setAdapter(cropFieldsListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        fieldListRecyclerView.setLayoutManager(linearLayoutManager);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new:
                openCropFieldManagerActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropFieldManagerActivity() {
        Intent intent = new Intent(this, CropFieldManagerActivity.class);
        startActivity(intent);
    }

}
