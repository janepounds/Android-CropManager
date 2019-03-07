package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;


import com.loopj.android.http.AsyncHttpClient;
import com.myfarmnow.myfarmcrop.adapters.CropInventoryListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventory;

import java.util.ArrayList;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;

public class CropInventoryListActivity extends AppCompatActivity {

    RecyclerView cropInventoryListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    CropInventoryListRecyclerAdapter cropListRecyclerAdapter;
    Spinner selectInventorySpinner;
    ArrayList<CropInventory> cropInventoryList = new ArrayList();
    ArrayList<CropInventory> cropListBackUp = new ArrayList();
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_inventory_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        cropInventoryListRecyclerView = findViewById(R.id.inventory_recyc_view);
        selectInventorySpinner = findViewById(R.id.select_inventory_spinner);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        cropInventoryListRecyclerView.setLayoutManager(linearLayoutManager);
        cropListRecyclerAdapter = new CropInventoryListRecyclerAdapter(CropInventoryListActivity.this.cropInventoryList,CropInventoryListActivity.this);
        cropInventoryListRecyclerView.setAdapter(cropListRecyclerAdapter);
        loadCropInventories();

        selectInventorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position !=0){
                    String selection = parent.getSelectedItem().toString();
                    ArrayList<CropInventory> filteredList = new ArrayList<>();
                    if(cropListBackUp.size() ==0 ){
                       // cropInventoryList.clear();
                        for(CropInventory x :cropListRecyclerAdapter.getInventoryList()){
                            cropListBackUp.add(x);
                        }
                    }

                    //filteredList.clear();
                    for(CropInventory x :cropListBackUp){
                        if(selection.toLowerCase().contains(x.getInventoryType().toLowerCase())||position==1){
                            filteredList.add(x);
                        }

                    }

                    cropListRecyclerAdapter.changeList(filteredList);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void loadCropInventories(){
        AsyncHttpClient client = new AsyncHttpClient();
        for(CropInventorySeeds seedsInventory:dbHandler.getCropSeeds(CropDashboardActivity.getPreferences("userId",this))){
            cropListRecyclerAdapter.addInventory(seedsInventory);
        }
        for(CropInventoryFertilizer fertilizerInventory:dbHandler.getCropFertilizers(CropDashboardActivity.getPreferences("userId",this))){
            cropListRecyclerAdapter.addInventory(fertilizerInventory);
        }
        for(CropInventorySpray spraysInventory:dbHandler.getCropSpray(CropDashboardActivity.getPreferences("userId",this))){
            cropListRecyclerAdapter.addInventory(spraysInventory);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_menu_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_add_fertilizer:
                Intent openFertilizerForm = new Intent(this, CropInventorySeedsManagerActivity.class);
                startActivity(openFertilizerForm);
                return true;

            case R.id.action_add_seed:
                Intent openSeedForm = new Intent(this, CropInventorySeedsManagerActivity.class);
                startActivity(openSeedForm);
                return true;
            case R.id.action_add_spray:
                Intent openSprayForm = new Intent(this, CropInventorySeedsManagerActivity.class);
                startActivity(openSprayForm);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
