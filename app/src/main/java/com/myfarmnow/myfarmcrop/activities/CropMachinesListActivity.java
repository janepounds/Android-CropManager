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

import com.myfarmnow.myfarmcrop.adapters.CropMachinesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropMachinesListActivity extends AppCompatActivity {
    RecyclerView machinesListRecyclerView;
    CropMachinesListRecyclerAdapter cropMachinesListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_machines_list);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        machinesListRecyclerView = findViewById(R.id.crop_machine_recyc_view);
        cropMachinesListRecyclerAdapter = new CropMachinesListRecyclerAdapter(this,dbHandler.getCropMachines(CropDashboardActivity.getPreferences("userId",this)));
        machinesListRecyclerView.setAdapter(cropMachinesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        machinesListRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add_new:
                openCropMachineManagerActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropMachineManagerActivity() {
        Intent intent = new Intent(this, CropMachineManagerActivity.class);
        startActivity(intent);
    }

}
