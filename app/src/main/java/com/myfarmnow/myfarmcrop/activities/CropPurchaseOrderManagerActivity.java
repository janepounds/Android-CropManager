package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropPurchaseOrder;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSupplier;

import java.util.ArrayList;

public class CropPurchaseOrderManagerActivity extends AppCompatActivity {
    CropItemListRecyclerAdapter itemListRecyclerAdapter;
    RecyclerView itemListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    Button addItemBtn;
    MyFarmDbHandlerSingleton dbHandler;

    CropPurchaseOrder cropPurchaseOrder;
    TextView subTotalTextView, discountAmountTxt,totalAmountTxt;
    EditText discountPercentageTxt,termsAndConditionsTxt,notesTxt, purchaseOrderDateTxt,deliveryDateTxt,referenceNoTxt,methodTxt;
    TextView purchaseOrderNumberTextView;
    Spinner suppliersSp;
    Button saveBtn,saveAndSendBtn;
    ArrayList <CropProduct> list = new ArrayList<>();
    ArrayList <CropProductItem> purchase_orderItems = new ArrayList<>();
    CropSpinnerAdapter suppliersSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_purchase_order_manager);

        if (getIntent().hasExtra("cropPurchaseOrder")) {
            cropPurchaseOrder = (CropPurchaseOrder) getIntent().getSerializableExtra("cropPurchaseOrder");
        }
        initializeForm();
    }

    public void initializeForm() {
        subTotalTextView = findViewById(R.id.txt_view_crop_purchase_order_sub_total);
        discountAmountTxt = findViewById(R.id.txt_crop_purchase_order_discount);
        totalAmountTxt = findViewById(R.id.txt_crop_purchase_order_total);
        discountPercentageTxt = findViewById(R.id.txt_crop_purchase_order_discount_percentage);
        purchaseOrderNumberTextView = findViewById(R.id.txt_crop_purchase_order_number);
        purchaseOrderDateTxt = findViewById(R.id.txt_crop_purchase_order_purchase_date);
        deliveryDateTxt = findViewById(R.id.txt_crop_purchase_order_delivery_date);
        notesTxt = findViewById(R.id.txt_crop_purchase_order_notes);
        methodTxt = findViewById(R.id.txt_crop_purchase_order_delivery_method);
        termsAndConditionsTxt = findViewById(R.id.txt_crop_purchase_order_terms_and_conditions);
        suppliersSp = findViewById(R.id.spinner_crop_purchase_order_supplier);
        referenceNoTxt = findViewById(R.id.txt_crop_purchase_order_reference_number);
        saveBtn = findViewById(R.id.btn_save);
        saveAndSendBtn = findViewById(R.id.btn_save_send);
        DashboardActivity.addDatePicker(purchaseOrderDateTxt, this);
        DashboardActivity.addDatePicker(deliveryDateTxt, this);

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        itemListRecyclerView = findViewById(R.id.recyc_view_crop_invoice_item_list);
        itemListRecyclerAdapter = new CropItemListRecyclerAdapter(this, new ArrayList<CropProductItem>(), dbHandler.getCropProducts(DashboardActivity.getPreferences("userId", this)), subTotalTextView);
        itemListRecyclerView.setAdapter(itemListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        itemListRecyclerView.setLayoutManager(linearLayoutManager);
        addItemBtn = findViewById(R.id.btn_add_item);

        ArrayList<CropSpinnerItem> suppliersList = new ArrayList<>();
        for (CropSupplier x : dbHandler.getCropSuppliers(DashboardActivity.getPreferences("userId", this))) {
            suppliersList.add(x);
        }
        suppliersSpinnerAdapter = new CropSpinnerAdapter(suppliersList, "Supplier", this);
        suppliersSp.setAdapter(suppliersSpinnerAdapter);
        purchaseOrderNumberTextView.setText(dbHandler.getNextPurchaseOrderNumber());
        //  salesOrderNumberTextView.setEnabled(false);


        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropProductItem item = new CropProductItem();
                itemListRecyclerAdapter.addCropProductItem(item);
            }
        });
        TextWatcher watcher = new TextWatcher() {
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
        discountPercentageTxt.addTextChangedListener(watcher);


        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validateEntries()) {
                    if (cropPurchaseOrder == null) {
                        savePurchaseOrder();
                    } else {
                        updatePurchaseOrder();
                    }

                    Intent toCropEmployeesList = new Intent(CropPurchaseOrderManagerActivity.this, CropPurchaseOrdersListActivity.class);
                    toCropEmployeesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropEmployeesList);
                    finish();
                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });

        saveAndSendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    CropPurchaseOrder purchaseOrder=null;
                    if (cropPurchaseOrder == null) {
                        savePurchaseOrder();
                    } else {
                        updatePurchaseOrder();
                    }

                    if(purchaseOrder!=null){
                        Intent toCropEmployeesList = new Intent(CropPurchaseOrderManagerActivity.this, CropPurchaseOrderPreviewActivity.class);
                        toCropEmployeesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        toCropEmployeesList.putExtra("cropPurchaseOrder",purchaseOrder);
                        toCropEmployeesList.putExtra("action",CropInvoicePreviewActivity.INVOICE_ACTION_EMAIL);
                        startActivity(toCropEmployeesList);
                        finish();
                    }else{
                        Toast.makeText(CropPurchaseOrderManagerActivity.this,"Sales Order cant be Saved",Toast.LENGTH_LONG).show();
                    }


                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        fillViews();


    }
    public CropPurchaseOrder savePurchaseOrder(){
        cropPurchaseOrder = new CropPurchaseOrder();
        cropPurchaseOrder.setUserId(DashboardActivity.PREFERENCES_USER_ID);
        cropPurchaseOrder.setTermsAndConditions(termsAndConditionsTxt.getText().toString());
        cropPurchaseOrder.setNotes(notesTxt.getText().toString());
        cropPurchaseOrder.setMethod(methodTxt.getText().toString());
        cropPurchaseOrder.setDeliveryDate(deliveryDateTxt.getText().toString());
        cropPurchaseOrder.setStatus("DRAFT");
        cropPurchaseOrder.setReferenceNumber(referenceNoTxt.getText().toString());
        cropPurchaseOrder.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
        cropPurchaseOrder.setPurchaseDate(purchaseOrderDateTxt.getText().toString());
        cropPurchaseOrder.setNumber(purchaseOrderNumberTextView.getText().toString());
        cropPurchaseOrder.setSupplierId(((CropSpinnerItem)suppliersSp.getSelectedItem()).getId());

        purchase_orderItems.clear();

        for(CropProductItem x:itemListRecyclerAdapter.getItems()){
            purchase_orderItems.add((CropProductItem)x);
        }
        cropPurchaseOrder.setItems(purchase_orderItems);

       return dbHandler.insertCropPurchaseOrder(cropPurchaseOrder);

    }

    public CropPurchaseOrder updatePurchaseOrder(){
        if(cropPurchaseOrder != null){
            cropPurchaseOrder.setTermsAndConditions(termsAndConditionsTxt.getText().toString());
            cropPurchaseOrder.setNotes(notesTxt.getText().toString());
            cropPurchaseOrder.setMethod(methodTxt.getText().toString());
            cropPurchaseOrder.setDeliveryDate(deliveryDateTxt.getText().toString());
            cropPurchaseOrder.setReferenceNumber(referenceNoTxt.getText().toString());
            cropPurchaseOrder.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
            cropPurchaseOrder.setPurchaseDate(purchaseOrderDateTxt.getText().toString());
            cropPurchaseOrder.setNumber(purchaseOrderNumberTextView.getText().toString());
            cropPurchaseOrder.setSupplierId(((CropSpinnerItem)suppliersSp.getSelectedItem()).getId());

            purchase_orderItems.clear();

            for(CropProductItem x:itemListRecyclerAdapter.getItems()){
                purchase_orderItems.add(x);
            }
            cropPurchaseOrder.setDeletedItemsIds(itemListRecyclerAdapter.getDeleteItemsId());

            cropPurchaseOrder.setItems(purchase_orderItems);
            return dbHandler.updateCropPurchaseOrder(cropPurchaseOrder);
        }
        else{
            return null;
        }
    }

    public void fillViews(){
        if(cropPurchaseOrder != null){
            DashboardActivity.selectSpinnerItemById(suppliersSp,cropPurchaseOrder.getSupplierId());
            ArrayList <CropProductItem> items = new ArrayList<>();
            for(CropProductItem x: cropPurchaseOrder.getItems()){
                items.add(x);
            }
            itemListRecyclerAdapter.appendList(items);
            termsAndConditionsTxt.setText(cropPurchaseOrder.getTermsAndConditions());
            notesTxt.setText(cropPurchaseOrder.getNotes());
            deliveryDateTxt.setText(cropPurchaseOrder.getDeliveryDate());
            referenceNoTxt.setText(cropPurchaseOrder.getReferenceNumber());
            methodTxt.setText(cropPurchaseOrder.getMethod());
            discountPercentageTxt.setText(cropPurchaseOrder.getDiscount()+"");
            purchaseOrderNumberTextView.setText(cropPurchaseOrder.getNumber());
            purchaseOrderDateTxt.setText(cropPurchaseOrder.getPurchaseDate());
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
            float total = subTotal-discount;
           discountAmountTxt.setText("-"+discount);
            totalAmountTxt.setText(total+"");

        }catch (Exception e){

        }
    }

    public boolean validateEntries() {
        String message = null;

        if(purchaseOrderDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            purchaseOrderDateTxt.requestFocus();
        }
        if(itemListRecyclerAdapter.getItems().size()==0){
            message =getString(R.string.purchase_order_no_items_added);

        }
        if(discountPercentageTxt.getText().toString().isEmpty()){
            discountPercentageTxt.setText(getString(R.string.default_numeric_value));
        }

        if(suppliersSp.getSelectedItemPosition()==0){
            message = "Supplier not selected";
            purchaseOrderDateTxt.requestFocus();
        }
        if(message != null){
            Toast.makeText(CropPurchaseOrderManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}