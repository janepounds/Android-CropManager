package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropPaymentBillsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropPaymentBillsListActivity extends AppCompatActivity {
    RecyclerView paymentBillListRecyclerView;
    CropPaymentBillsListRecyclerAdapter cropPaymentBillsListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_payment_bills_list);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        paymentBillListRecyclerView = findViewById(R.id.crop_payment_bill_recyc_view);
        cropPaymentBillsListRecyclerAdapter = new CropPaymentBillsListRecyclerAdapter(this,dbHandler.getCropPaymentBills(CropDashboardActivity.getPreferences("userId",this)));
        paymentBillListRecyclerView.setAdapter(cropPaymentBillsListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        paymentBillListRecyclerView.setLayoutManager(linearLayoutManager);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_new:
                Intent intent = new Intent(this, CropPaymentBillManagerActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
