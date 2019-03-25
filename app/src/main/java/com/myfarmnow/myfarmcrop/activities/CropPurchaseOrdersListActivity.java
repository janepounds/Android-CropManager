package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.adapters.CropPurchaseOrdersListRecyclerAdapter;

public class CropPurchaseOrdersListActivity extends AppCompatActivity {

    RecyclerView purchaseOrderListRecyclerView;
    CropPurchaseOrdersListRecyclerAdapter cropPurchaseOrdersListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_purchase_orders_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        purchaseOrderListRecyclerView = findViewById(R.id.crop_purchase_order_recyc_view);
        cropPurchaseOrdersListRecyclerAdapter = new CropPurchaseOrdersListRecyclerAdapter(this,dbHandler.getCropPurchaseOrders(CropDashboardActivity.getPreferences("userId",this)));
        purchaseOrderListRecyclerView.setAdapter(cropPurchaseOrdersListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        purchaseOrderListRecyclerView.setLayoutManager(linearLayoutManager);

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

                Intent intent = new Intent(this, CropPurchaseOrderManagerActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
