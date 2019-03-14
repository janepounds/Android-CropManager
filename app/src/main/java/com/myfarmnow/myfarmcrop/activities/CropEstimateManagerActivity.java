package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropItemListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropEstimateItem;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropEstimateManagerActivity extends AppCompatActivity {

    CropItemListRecyclerAdapter itemListRecyclerAdapter;
    RecyclerView itemListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    Button addItemBtn;
    MyFarmDbHandlerSingleton dbHandler;
    String cropId;
    TextView subTotalTextView, discountAmountTxt,shippingChargesTxt,totalAmountTxt;
    EditText discountPercentageTxt;
    Spinner customersSp;
    ArrayList <CropProduct> list = new ArrayList<>();
    CropSpinnerAdapter customersSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_estimate_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        initializeForm();

    }


    public void initializeForm(){
        subTotalTextView = findViewById(R.id.txt_view_crop_estimate_sub_total);
        discountAmountTxt = findViewById(R.id.txt_crop_estimate_discount);
        shippingChargesTxt = findViewById(R.id.txt_view_crop_estimate_shipping_charges);
        totalAmountTxt = findViewById(R.id.txt_crop_estimate_total);
        discountPercentageTxt = findViewById(R.id.txt_crop_estimate_discount_percentage);
        customersSp = findViewById(R.id.spinner_crop_estimate_customer_name);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        itemListRecyclerView = findViewById(R.id.recyc_view_crop_invoice_item_list);
        itemListRecyclerAdapter = new CropItemListRecyclerAdapter(this,new ArrayList<CropProductItem>(),list,subTotalTextView);
        itemListRecyclerView.setAdapter(itemListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        itemListRecyclerView.setLayoutManager(linearLayoutManager);
        addItemBtn = findViewById(R.id.btn_add_item);

        ArrayList<CropSpinnerItem> customersList = new ArrayList<>();
        for(CropCustomer x: dbHandler.getCropCustomers(CropDashboardActivity.getPreferences("userId",this))){
            customersList.add(x);
        }
        customersSpinnerAdapter = new CropSpinnerAdapter(customersList,"Customer",this);
        customersSp.setAdapter(customersSpinnerAdapter);
        //itemListRecyclerAdapter
        CropEstimateItem item = new CropEstimateItem();
        itemListRecyclerAdapter.addCropProductItem(item);


        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropEstimateItem item = new CropEstimateItem();
                itemListRecyclerAdapter.addCropProductItem(item);
            }
        });
        TextWatcher watcher =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTotalAmount();
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTotalAmount();
            }
        };

        subTotalTextView.addTextChangedListener(watcher);
        shippingChargesTxt.addTextChangedListener(watcher);
        discountAmountTxt.addTextChangedListener(watcher);
        discountPercentageTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                computeDiscount();
            }

            @Override
            public void afterTextChanged(Editable s) {
                computeDiscount();
            }
        });

    }

    public void computeDiscount(){
        try{
            float subTotal = Float.parseFloat(subTotalTextView.getText().toString());
            float discountPercentage = Float.parseFloat(discountPercentageTxt.getText().toString());
            float amount = (discountPercentage/100)*subTotal;
            discountAmountTxt.setText(amount+"");
        }catch (Exception e){

        }
    }
    public void updateTotalAmount(){
        try{
            float subTotal = Float.parseFloat(subTotalTextView.getText().toString());
            float discount = Float.parseFloat(discountAmountTxt.getText().toString());
            float shippingCharges = Float.parseFloat(shippingChargesTxt.getText().toString());
            float total = subTotal+discount+shippingCharges;
            totalAmountTxt.setText(total+"");
        }catch (Exception e){

        }
    }

}
