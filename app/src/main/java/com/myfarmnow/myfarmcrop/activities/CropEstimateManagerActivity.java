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
import com.myfarmnow.myfarmcrop.models.CropEstimate;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class CropEstimateManagerActivity extends AppCompatActivity {

    CropItemListRecyclerAdapter itemListRecyclerAdapter;
    RecyclerView itemListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    Button addItemBtn;
    MyFarmDbHandlerSingleton dbHandler;

    CropEstimate cropEstimate;
    TextView subTotalTextView, discountAmountTxt,shippingChargesTxtView,totalAmountTxt;
    EditText discountPercentageTxt,shippingChargesTxt,termsAndConditionsTxt,notesTxt,estimateDateTxt,expiryDateTxt,referenceNumberTxt;
    TextView estimateNumberTextView;
    Spinner customersSp;
    Button saveBtn,saveAndSendBtn;
    ArrayList <CropProduct> list = new ArrayList<>();
    ArrayList <CropProductItem> estimateItems = new ArrayList<>();
    CropSpinnerAdapter customersSpinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_estimate_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getIntent().hasExtra("cropEstimate")){
            cropEstimate = (CropEstimate)getIntent().getSerializableExtra("cropEstimate");
        }
        initializeForm();

    }


    public void initializeForm(){
        subTotalTextView = findViewById(R.id.txt_view_crop_estimate_sub_total);
        discountAmountTxt = findViewById(R.id.txt_crop_estimate_discount);
        shippingChargesTxtView = findViewById(R.id.txt_view_crop_estimate_shipping_charges);
        totalAmountTxt = findViewById(R.id.txt_crop_estimate_total);
        discountPercentageTxt = findViewById(R.id.txt_crop_estimate_discount_percentage);
        shippingChargesTxt = findViewById(R.id.txt_crop_estimate_shipping_charge);
        estimateNumberTextView = findViewById(R.id.txt_crop_estimate_number);
        estimateDateTxt = findViewById(R.id.txt_crop_estimate_date);
        expiryDateTxt = findViewById(R.id.txt_crop_estimate_expiry_date);
        referenceNumberTxt = findViewById(R.id.txt_crop_estimate_reference_number);
        notesTxt = findViewById(R.id.txt_crop_estimate_customer_notes);
        termsAndConditionsTxt = findViewById(R.id.txt_crop_estimate_terms_and_conditions);
        customersSp = findViewById(R.id.spinner_crop_estimate_customer_name);
        saveBtn = findViewById(R.id.btn_save);
        saveAndSendBtn = findViewById(R.id.btn_save_send);
        CropDashboardActivity.addDatePicker(estimateDateTxt,this);
        CropDashboardActivity.addDatePicker(expiryDateTxt,this);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        itemListRecyclerView = findViewById(R.id.recyc_view_crop_invoice_item_list);
        itemListRecyclerAdapter = new CropItemListRecyclerAdapter(this,new ArrayList<CropProductItem>(),dbHandler.getCropProducts(CropDashboardActivity.getPreferences("userId",this)),subTotalTextView);
        itemListRecyclerView.setAdapter(itemListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        itemListRecyclerView.setLayoutManager(linearLayoutManager);
        addItemBtn = findViewById(R.id.btn_add_item);

        ((TextView)findViewById(R.id.txt_view_crop_estimate_total_label)).setText("Total ("+ CropSettingsSingleton.getInstance().getCurrency()+")");

        ArrayList<CropSpinnerItem> customersList = new ArrayList<>();
        for(CropCustomer x: dbHandler.getCropCustomers(CropDashboardActivity.getPreferences("userId",this))){
            customersList.add(x);
        }
        customersSpinnerAdapter = new CropSpinnerAdapter(customersList,"Customer",this);
        customersSp.setAdapter(customersSpinnerAdapter);
        estimateNumberTextView.setText(dbHandler.getNextEstimateNumber());
      //  estimateNumberTextView.setEnabled(false);


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
                    if(cropEstimate ==null){
                        saveEstimate();
                    }
                    else{
                        updateEstimate();
                    }

                    Intent toCropEmployeesList = new Intent(CropEstimateManagerActivity.this, CropEstimatesListActivity.class);
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
                    CropEstimate estimate ;
                    if(cropEstimate ==null){
                        estimate =saveEstimate();
                    }
                    else{
                        estimate =updateEstimate();
                    }

                    if(estimate !=null){
                        Intent toCropEmployeesList = new Intent(CropEstimateManagerActivity.this, CropEstimatePreviewActivity.class);
                        toCropEmployeesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        toCropEmployeesList.putExtra("cropEstimate",estimate);
                        toCropEmployeesList.putExtra("action",CropEstimatePreviewActivity.INVOICE_ACTION_EMAIL);
                        startActivity(toCropEmployeesList);
                    }
                    else{
                        Toast.makeText(CropEstimateManagerActivity.this,"Estimate cant be Saved",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        fillViews();


    }

    public CropEstimate saveEstimate(){
        cropEstimate = new CropEstimate();
        cropEstimate.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropEstimate.setTermsAndConditions(termsAndConditionsTxt.getText().toString());
        cropEstimate.setCustomerNotes(notesTxt.getText().toString());
        cropEstimate.setExpiryDate(expiryDateTxt.getText().toString());
        cropEstimate.setReferenceNumber(referenceNumberTxt.getText().toString());
        cropEstimate.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
        cropEstimate.setShippingCharges(Float.parseFloat(shippingChargesTxt.getText().toString()));
        cropEstimate.setDate(estimateDateTxt.getText().toString());
        cropEstimate.setNumber(estimateNumberTextView.getText().toString());
        cropEstimate.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());

        estimateItems.clear();

        for(CropProductItem x:itemListRecyclerAdapter.getItems()){
            estimateItems.add((CropProductItem)x);
        }
        cropEstimate.setItems(estimateItems);

        return dbHandler.insertCropEstimate(cropEstimate);

    }

    public CropEstimate updateEstimate(){
        if(cropEstimate != null){
            cropEstimate.setCustomerNotes(notesTxt.getText().toString());
            cropEstimate.setTermsAndConditions(termsAndConditionsTxt.getText().toString());
            cropEstimate.setExpiryDate(expiryDateTxt.getText().toString());
            cropEstimate.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
            cropEstimate.setShippingCharges(Float.parseFloat(shippingChargesTxt.getText().toString()));
            cropEstimate.setDate(estimateDateTxt.getText().toString());
            cropEstimate.setReferenceNumber(referenceNumberTxt.getText().toString());
            cropEstimate.setNumber(estimateNumberTextView.getText().toString());
            cropEstimate.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());
            estimateItems.clear();
            for(CropProductItem x:itemListRecyclerAdapter.getItems()){
                estimateItems.add((CropProductItem)x);
            }
            cropEstimate.setDeletedItemsIds(itemListRecyclerAdapter.getDeleteItemsId());
            Log.d("TEST",cropEstimate.getDeletedItemsIds().toString());
            cropEstimate.setItems(estimateItems);
           return dbHandler.updateCropEstimate(cropEstimate);
        }
        else{
            return null;
        }
    }

    public void fillViews(){
        if(cropEstimate != null){
            CropDashboardActivity.selectSpinnerItemById(customersSp,cropEstimate.getCustomerId());
            ArrayList <CropProductItem> items = new ArrayList<>();
            for(CropProductItem x: cropEstimate.getItems()){
                items.add(x);
            }
            itemListRecyclerAdapter.appendList(items);
            termsAndConditionsTxt.setText(cropEstimate.getTermsAndConditions());
            notesTxt.setText(cropEstimate.getCustomerNotes());
            referenceNumberTxt.setText(cropEstimate.getReferenceNumber());
            expiryDateTxt.setText(cropEstimate.getExpiryDate());
            shippingChargesTxt.setText(cropEstimate.getShippingCharges()+"");
            discountPercentageTxt.setText(cropEstimate.getDiscount()+"");
            estimateNumberTextView.setText(cropEstimate.getNumber());
            estimateDateTxt.setText(cropEstimate.getDate());
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

        if(estimateDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            estimateDateTxt.requestFocus();
        }
        if(referenceNumberTxt.getText().toString().isEmpty()){
            message = getString(R.string.reference_number_not_entered);
            referenceNumberTxt.requestFocus();
        }
        if(itemListRecyclerAdapter.getItems().size()==0){
            message =getString(R.string.estimate_no_items_added);

        }
        if(discountPercentageTxt.getText().toString().isEmpty()){
            discountPercentageTxt.setText(getString(R.string.default_numeric_value));
        }
        if(shippingChargesTxt.getText().toString().isEmpty()){
            shippingChargesTxt.setText(getString(R.string.default_numeric_value));
        }

        if(customersSp.getSelectedItemPosition()==0){
            message = "Customer not selected";
            estimateDateTxt.requestFocus();
        }
        if(message != null){
            Toast.makeText(CropEstimateManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}
