package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropMachineNotesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;

public class CropMachineNotesListActivity extends AppCompatActivity {
    RecyclerView machineNotesListRecyclerView;
    CropMachineNotesListRecyclerAdapter cropMachineNotesListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;


    String machineId =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_machine_notes_list);


        if(getIntent().hasExtra("machineId")){
            machineId = getIntent().getStringExtra("machineId");
        }else{
            finish();
        }
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        machineNotesListRecyclerView = findViewById(R.id.crop_machine_notes_recyc_view);
        cropMachineNotesListRecyclerAdapter = new CropMachineNotesListRecyclerAdapter(this,dbHandler.getCropNotes(machineId, CropNote.IS_FOR_MACHINE));
        machineNotesListRecyclerView.setAdapter(cropMachineNotesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        machineNotesListRecyclerView.setLayoutManager(linearLayoutManager);

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
        Intent intent = new Intent(this, CropMachineNotesManagerActivity.class);
        intent.putExtra("machineId",machineId);
        startActivity(intent);
        finish();
    }

}
