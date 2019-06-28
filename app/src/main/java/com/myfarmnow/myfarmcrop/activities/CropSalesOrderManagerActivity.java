package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropItemListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropSalesOrder;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropSalesOrderManagerActivity extends AppCompatActivity {

    CropItemListRecyclerAdapter itemListRecyclerAdapter;
    RecyclerView itemListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    Button addItemBtn;
    MyFarmDbHandlerSingleton dbHandler;

    CropSalesOrder cropSalesOrder;
    TextView subTotalTextView, discountAmountTxt,shippingChargesTxtView,totalAmountTxt;
    EditText discountPercentageTxt,shippingChargesTxt,termsAndConditionsTxt,notesTxt, salesOrderDateTxt,expiryDateTxt,referenceNoTxt,methodTxt;
    TextView salesOrderNumberTextView;
    Spinner customersSp;
    Button saveBtn,saveAndSendBtn;
    ArrayList <CropProduct> list = new ArrayList<>();
    ArrayList <CropProductItem> sales_orderItems = new ArrayList<>();
    CropSpinnerAdapter customersSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_sales_order_manager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getIntent().hasExtra("cropSalesOrder")){
            cropSalesOrder = (CropSalesOrder)getIntent().getSerializableExtra("cropSalesOrder");
        }
        initializeForm();

    }


    public void initializeForm(){
        subTotalTextView = findViewById(R.id.txt_view_crop_sales_order_sub_total);
        discountAmountTxt = findViewById(R.id.txt_crop_sales_order_discount);
        shippingChargesTxtView = findViewById(R.id.txt_view_crop_sales_order_shipping_charges);
        totalAmountTxt = findViewById(R.id.txt_crop_sales_order_total);
        discountPercentageTxt = findViewById(R.id.txt_crop_sales_order_discount_percentage);
        shippingChargesTxt = findViewById(R.id.txt_crop_sales_order_shipping_charge);
        salesOrderNumberTextView = findViewById(R.id.txt_crop_sales_order_number);
        salesOrderDateTxt = findViewById(R.id.txt_crop_sales_order_date);
        expiryDateTxt = findViewById(R.id.txt_crop_sales_order_shipping_date);
        notesTxt = findViewById(R.id.txt_crop_sales_order_customer_notes);
        methodTxt = findViewById(R.id.txt_crop_sales_order_method);
        termsAndConditionsTxt = findViewById(R.id.txt_crop_sales_order_terms_and_conditions);
        customersSp = findViewById(R.id.spinner_crop_sales_order_customer_name);
        referenceNoTxt = findViewById(R.id.txt_crop_sales_order_reference_number);
        saveBtn = findViewById(R.id.btn_save);
        saveAndSendBtn = findViewById(R.id.btn_save_send);
        CropDashboardActivity.addDatePicker(salesOrderDateTxt,this);
        CropDashboardActivity.addDatePicker(expiryDateTxt,this);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        itemListRecyclerView = findViewById(R.id.recyc_view_crop_invoice_item_list);
        itemListRecyclerAdapter = new CropItemListRecyclerAdapter(this,new ArrayList<CropProductItem>(),dbHandler.getCropProducts(CropDashboardActivity.getPreferences("userId",this)),subTotalTextView);
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
        salesOrderNumberTextView.setText(dbHandler.getNextSalesOrderNumber());
      //  salesOrderNumberTextView.setEnabled(false);


        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropProductItem item = new CropProductItem();
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
        discountPercentageTxt.addTextChangedListener(new TextWatcher() {
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
        });
        shippingChargesTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                shippingChargesTxtView.setText(shippingChargesTxt.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                shippingChargesTxtView.setText(shippingChargesTxt.getText().toString());
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropSalesOrder ==null){
                        saveSalesOrder();
                    }
                    else{
                        updateSalesOrder();
                    }

                    Intent toCropEmployeesList = new Intent(CropSalesOrderManagerActivity.this, CropSalesOrdersListActivity.class);
                    toCropEmployeesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropEmployeesList);
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        saveAndSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(validateEntries()){
                        CropSalesOrder salesOrder=null;
                        if(cropSalesOrder ==null){
                            salesOrder=saveSalesOrder();
                        }
                        else{
                            salesOrder=updateSalesOrder();
                        }
                        if(salesOrder!=null){
                            Intent toCropEmployeesList = new Intent(CropSalesOrderManagerActivity.this, CropSalesOrderPreviewActivity.class);
                            toCropEmployeesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            toCropEmployeesList.putExtra("cropSalesOrder",salesOrder);
                            toCropEmployeesList.putExtra("action",CropSalesOrderPreviewActivity.SALES_ORDER_ACTION_EMAIL);
                            startActivity(toCropEmployeesList);
                            finish();
                        }else{
                            Toast.makeText(CropSalesOrderManagerActivity.this,"Sales Order cant be Saved",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Log.d("ERROR","Testing");
                    }
            }
        });
        fillViews();


    }

    public CropSalesOrder saveSalesOrder(){
        cropSalesOrder = new CropSalesOrder();
        cropSalesOrder.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropSalesOrder.setTermsAndConditions(termsAndConditionsTxt.getText().toString());
        cropSalesOrder.setCustomerNotes(notesTxt.getText().toString());
        cropSalesOrder.setShippingDate(expiryDateTxt.getText().toString());
        cropSalesOrder.setStatus("DRAFT");
        cropSalesOrder.setMethod(methodTxt.getText().toString());
        cropSalesOrder.setReferenceNumber(referenceNoTxt.getText().toString());
        cropSalesOrder.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
        cropSalesOrder.setShippingCharges(Float.parseFloat(shippingChargesTxt.getText().toString()));
        cropSalesOrder.setDate(salesOrderDateTxt.getText().toString());
        cropSalesOrder.setNumber(salesOrderNumberTextView.getText().toString());
        cropSalesOrder.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());

        sales_orderItems.clear();

        for(CropProductItem x:itemListRecyclerAdapter.getItems()){
            sales_orderItems.add((CropProductItem)x);
        }
        cropSalesOrder.setItems(sales_orderItems);

        return dbHandler.insertCropSalesOrder(cropSalesOrder);

    }

    public CropSalesOrder updateSalesOrder(){
        if(cropSalesOrder != null){
            cropSalesOrder.setCustomerNotes(notesTxt.getText().toString());
            cropSalesOrder.setTermsAndConditions(termsAndConditionsTxt.getText().toString());
            cropSalesOrder.setShippingDate(expiryDateTxt.getText().toString());
            cropSalesOrder.setReferenceNumber(referenceNoTxt.getText().toString());
            cropSalesOrder.setMethod(methodTxt.getText().toString());
            cropSalesOrder.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
            cropSalesOrder.setShippingCharges(Float.parseFloat(shippingChargesTxt.getText().toString()));
            cropSalesOrder.setDate(salesOrderDateTxt.getText().toString());
            cropSalesOrder.setNumber(salesOrderNumberTextView.getText().toString());
            cropSalesOrder.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());
            sales_orderItems.clear();
            for(CropProductItem x:itemListRecyclerAdapter.getItems()){
                sales_orderItems.add((CropProductItem)x);
            }
            cropSalesOrder.setDeletedItemsIds(itemListRecyclerAdapter.getDeleteItemsId());
            Log.d("TEST",cropSalesOrder.getDeletedItemsIds().toString());
            cropSalesOrder.setItems(sales_orderItems);
            return dbHandler.updateCropSalesOrder(cropSalesOrder);
        }
        else{
            return null;
        }
    }

    public void fillViews(){
        if(cropSalesOrder != null){
            CropDashboardActivity.selectSpinnerItemById(customersSp,cropSalesOrder.getCustomerId());
            ArrayList <CropProductItem> items = new ArrayList<>();
            for(CropProductItem x: cropSalesOrder.getItems()){
                items.add(x);
            }
            itemListRecyclerAdapter.appendList(items);
            termsAndConditionsTxt.setText(cropSalesOrder.getTermsAndConditions());
            notesTxt.setText(cropSalesOrder.getCustomerNotes());
            expiryDateTxt.setText(cropSalesOrder.getShippingDate());
            referenceNoTxt.setText(cropSalesOrder.getReferenceNumber());
            methodTxt.setText(cropSalesOrder.getMethod());
            shippingChargesTxt.setText(cropSalesOrder.getShippingCharges()+"");
            discountPercentageTxt.setText(cropSalesOrder.getDiscount()+"");
            salesOrderNumberTextView.setText(cropSalesOrder.getNumber());
            salesOrderDateTxt.setText(cropSalesOrder.getDate());
        }
    }

    public float computeDiscount(){
        try{
            float subTotal = Float.parseFloat(subTotalTextView.getText().toString());
            float discountPercentage = Float.parseFloat(discountPercentageTxt.getText().toString());
            float amount = (discountPercentage/100)*subTotal;

            return amount;
        }catch (Exception e){

        }
        return 0;
    }
    public void updateTotalAmount(){
        try{
            float subTotal = Float.parseFloat(subTotalTextView.getText().toString());
            float discount = computeDiscount();
            float shippingCharges = Float.parseFloat(shippingChargesTxtView.getText().toString());
            float total = subTotal-discount+shippingCharges;
            discountAmountTxt.setText("-"+discount);
            totalAmountTxt.setText(total+"");

        }catch (Exception e){

        }
    }

    public boolean validateEntries() {
        String message = null;

        if(salesOrderDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            salesOrderDateTxt.requestFocus();
        }
        if(itemListRecyclerAdapter.getItems().size()==0){
            message =getString(R.string.sales_order_no_items_added);

        }
        if(discountPercentageTxt.getText().toString().isEmpty()){
            discountPercentageTxt.setText(getString(R.string.default_numeric_value));
        }
        if(shippingChargesTxt.getText().toString().isEmpty()){
            shippingChargesTxt.setText(getString(R.string.default_numeric_value));
        }

        if(customersSp.getSelectedItemPosition()==0){
            message = "Customer not selected";
            salesOrderDateTxt.requestFocus();
        }
        if(message != null){
            Toast.makeText(CropSalesOrderManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
