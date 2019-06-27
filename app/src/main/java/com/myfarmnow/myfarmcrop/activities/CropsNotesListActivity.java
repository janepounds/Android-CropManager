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
import com.myfarmnow.myfarmcrop.adapters.CropsNotesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;

public class CropsNotesListActivity extends AppCompatActivity {
    RecyclerView cropsNotesListRecyclerView;
    CropsNotesListRecyclerAdapter cropsNotesListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;


    String cropId =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crops_notes_list);
        if(getIntent().hasExtra("cropId")){
            cropId = getIntent().getStringExtra("cropId");
        }else{
            finish();
        }
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        cropsNotesListRecyclerView = findViewById(R.id.crops_notes_recyc_view);
        cropsNotesListRecyclerAdapter = new CropsNotesListRecyclerAdapter(this,dbHandler.getCropNotes(cropId, CropNote.IS_FOR_CROP));
        cropsNotesListRecyclerView.setAdapter(cropsNotesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        cropsNotesListRecyclerView.setLayoutManager(linearLayoutManager);

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
        Intent intent = new Intent(this, CropsNotesManagerActivity.class);
        intent.putExtra("cropId",cropId);
        startActivity(intent);
        finish();
    }

}
