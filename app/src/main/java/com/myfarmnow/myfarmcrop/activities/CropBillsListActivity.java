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
import com.myfarmnow.myfarmcrop.adapters.CropBillsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropBillsListActivity extends AppCompatActivity {
    RecyclerView estimateListRecyclerView;
    CropBillsListRecyclerAdapter cropBillsListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_bills_list);


        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        estimateListRecyclerView = findViewById(R.id.crop_bill_recyc_view);
        cropBillsListRecyclerAdapter = new CropBillsListRecyclerAdapter(this,dbHandler.getCropBills(CropDashboardActivity.getPreferences("userId",this)));
        estimateListRecyclerView.setAdapter(cropBillsListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        estimateListRecyclerView.setLayoutManager(linearLayoutManager);

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

                Intent intent = new Intent(this, CropBillManagerActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
