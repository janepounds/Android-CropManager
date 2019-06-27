package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;

import com.myfarmnow.myfarmcrop.adapters.CropsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;

import java.util.ArrayList;

public class CropsListActivity extends AppCompatActivity {

    RecyclerView cropInventoryListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CropsListRecyclerAdapter cropListRecyclerAdapter;
    MyFarmDbHandlerSingleton dbHandler;
    ArrayList<Crop> cropArrayList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_list);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        cropInventoryListRecyclerView = findViewById(R.id.crops_recyc_view);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        cropInventoryListRecyclerView.setLayoutManager(linearLayoutManager);
        cropListRecyclerAdapter = new CropsListRecyclerAdapter(CropsListActivity.this.cropArrayList,CropsListActivity.this);
        cropInventoryListRecyclerView.setAdapter(cropListRecyclerAdapter);
        loadCropInventories();
    }
    private void loadCropInventories(){

        cropListRecyclerAdapter.addList(dbHandler.getCrops(CropDashboardActivity.getPreferences("userId",this)));

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        // Log.d("ADD NEW SELECTED",item.getItemId()+" "+R.id.action_add_new);

        switch (item.getItemId()) {

            case R.id.action_add_new:
                openCropsManagerActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropsManagerActivity() {
        Intent intent = new Intent(this, CropsManagerActivity.class);
        startActivity(intent);
    }
}
