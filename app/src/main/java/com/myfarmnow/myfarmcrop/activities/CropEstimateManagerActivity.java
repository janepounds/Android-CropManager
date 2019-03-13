package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropEstimateItemListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.CropFertilizerApplicationsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropEstimateItem;
import com.myfarmnow.myfarmcrop.models.CropProduct;

import java.util.ArrayList;

public class CropEstimateManagerActivity extends AppCompatActivity {

    CropEstimateItemListRecyclerAdapter itemListRecyclerAdapter;
    RecyclerView itemListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    Button addItemBtn;
    MyFarmDbHandlerSingleton dbHandler;
    String cropId;
    TextView subTotalTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_estimate_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList <CropProduct> list = new ArrayList<>();
        CropProduct x = new CropProduct();
        x.setId("1");
        x.setTaxRate(10);
        x.setName("Water Pipes");
        x.setSellingPrice(2000);
        CropProduct y = new CropProduct();
        y.setId("1");
        y.setName("Water Pipes");
        y.setTaxRate(17);
        y.setSellingPrice(2600);

        list.add(x);
        list.add(y);

        subTotalTextView = findViewById(R.id.txt_view_crop_estimate_sub_total);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        itemListRecyclerView = findViewById(R.id.recyc_view_crop_invoice_item_list);
        itemListRecyclerAdapter = new CropEstimateItemListRecyclerAdapter(this,new ArrayList<CropEstimateItem>(),list,subTotalTextView);
        itemListRecyclerView.setAdapter(itemListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        itemListRecyclerView.setLayoutManager(linearLayoutManager);
        addItemBtn = findViewById(R.id.btn_add_item);

        //itemListRecyclerAdapter
        CropEstimateItem item = new CropEstimateItem();
        itemListRecyclerAdapter.addCropEstimateItem(item);


        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropEstimateItem item = new CropEstimateItem();
                itemListRecyclerAdapter.addCropEstimateItem(item);
            }
        });


    }

}
