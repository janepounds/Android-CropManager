package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropMachineServiceListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class
CropMachineServicesListActivity extends AppCompatActivity {
    RecyclerView machineServiceListRecyclerView;
    CropMachineServiceListRecyclerAdapter cropMachineServiceListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    String machineId=null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_machine_services_list);

        if(getIntent().hasExtra("machineId")){
            machineId = getIntent().getStringExtra("machineId");
        }else{
            finish();
        }
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        machineServiceListRecyclerView = findViewById(R.id.crop_machine_service_recyc_view);
        cropMachineServiceListRecyclerAdapter = new CropMachineServiceListRecyclerAdapter(this,dbHandler.getCropMachineServices(machineId));
        machineServiceListRecyclerView.setAdapter(cropMachineServiceListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        machineServiceListRecyclerView.setLayoutManager(linearLayoutManager);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);

        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add_new:
                item.setTitle("Add New");
                openCropMachineTaskManagerActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropMachineTaskManagerActivity() {
        Intent intent = new Intent(this, CropMachineServiceManagerActivity.class);
        intent.putExtra("machineId",machineId);
        startActivity(intent);
    }


}
