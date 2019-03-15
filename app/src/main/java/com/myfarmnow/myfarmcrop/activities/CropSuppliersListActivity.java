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

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSuppliersListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropSuppliersListActivity extends AppCompatActivity {

    RecyclerView customerListRecyclerView;
    CropSuppliersListRecyclerAdapter cropSuppliersListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_suppliers_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        customerListRecyclerView = findViewById(R.id.crop_customer_recyc_view);
        cropSuppliersListRecyclerAdapter = new CropSuppliersListRecyclerAdapter(this,dbHandler.getCropSuppliers(CropDashboardActivity.getPreferences("userId",this)));
        customerListRecyclerView.setAdapter(cropSuppliersListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        customerListRecyclerView.setLayoutManager(linearLayoutManager);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_add_new:
                Intent intent = new Intent(this, CropSupplierManagerActivity.class);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
