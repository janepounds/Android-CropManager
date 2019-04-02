package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropItemListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropBill;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSupplier;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CropBillManagerActivity extends AppCompatActivity {
    CropItemListRecyclerAdapter itemListRecyclerAdapter;
    RecyclerView itemListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    Button addItemBtn;
    MyFarmDbHandlerSingleton dbHandler;
    TextView subTotalTextView, discountAmountTxt,totalAmountTxt;
    EditText discountPercentageTxt,notesTxt, billDateTxt,dueDateTxt;
    TextView billOrderNumberTextView, billNumberTxt;
    Spinner termsTxt,suppliersSp;
    Button saveBtn;
    ArrayList <CropProduct> list = new ArrayList<>();
    ArrayList <CropProductItem> bill_orderItems = new ArrayList<>();
    CropSpinnerAdapter suppliersSpinnerAdapter;

    CropBill cropBill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_bill_manager);

        if (getIntent().hasExtra("cropBill")) {
            cropBill = (CropBill) getIntent().getSerializableExtra("cropBill");
        }
        initializeForm();

    }

    public void initializeForm() {
        subTotalTextView = findViewById(R.id.txt_view_crop_bill_sub_total);
        discountAmountTxt = findViewById(R.id.txt_crop_bill_discount);
        totalAmountTxt = findViewById(R.id.txt_crop_bill_total);
        discountPercentageTxt = findViewById(R.id.txt_crop_bill_discount_percentage);
        billOrderNumberTextView = findViewById(R.id.txt_crop_bill_order_number);
        billDateTxt = findViewById(R.id.txt_crop_bill_date);
        billNumberTxt = findViewById(R.id.txt_crop_bill_number);
        dueDateTxt = findViewById(R.id.txt_crop_bill_due_date);
        notesTxt = findViewById(R.id.txt_crop_bill_notes);
        termsTxt = findViewById(R.id.txt_crop_bill_terms);
        suppliersSp = findViewById(R.id.spinner_crop_bill_supplier);
        saveBtn = findViewById(R.id.btn_save);
        CropDashboardActivity.addDatePicker(billDateTxt, this);
        CropDashboardActivity.addDatePicker(dueDateTxt, this);
        ((ArrayAdapter)termsTxt.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        itemListRecyclerView = findViewById(R.id.recyc_view_crop_invoice_item_list);
        itemListRecyclerAdapter = new CropItemListRecyclerAdapter(this, new ArrayList<CropProductItem>(), dbHandler.getCropProducts(CropDashboardActivity.getPreferences("userId", this)), subTotalTextView);
        itemListRecyclerView.setAdapter(itemListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        itemListRecyclerView.setLayoutManager(linearLayoutManager);
        addItemBtn = findViewById(R.id.btn_add_item);

        ArrayList<CropSpinnerItem> suppliersList = new ArrayList<>();
        for (CropSupplier x : dbHandler.getCropSuppliers(CropDashboardActivity.getPreferences("userId", this))) {
            suppliersList.add(x);
        }
        suppliersSpinnerAdapter = new CropSpinnerAdapter(suppliersList, "Supplier", this);
        suppliersSp.setAdapter(suppliersSpinnerAdapter);
        billNumberTxt.setText(dbHandler.getNextBillNumber());
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
                    if (cropBill == null) {
                        saveBill();
                    } else {
                        updateBill();
                    }

                    Intent toCropEmployeesList = new Intent(CropBillManagerActivity.this, CropBillsListActivity.class);
                    toCropEmployeesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropEmployeesList);
                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });
        fillViews();


    }

    public void saveBill(){
        cropBill = new CropBill();
        cropBill.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropBill.setTerms(termsTxt.getSelectedItem().toString());
        cropBill.setNotes(notesTxt.getText().toString());
        cropBill.setDueDate(dueDateTxt.getText().toString());
        cropBill.setOrderNumber(billOrderNumberTextView.getText().toString());
        cropBill.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
        cropBill.setBillDate(billDateTxt.getText().toString());
        cropBill.setNumber(billNumberTxt.getText().toString());
        cropBill.setSupplierId(((CropSpinnerItem)suppliersSp.getSelectedItem()).getId());

        bill_orderItems.clear();

        for(CropProductItem x:itemListRecyclerAdapter.getItems()){
            bill_orderItems.add(x);
        }
        cropBill.setItems(bill_orderItems);

        dbHandler.insertCropBill(cropBill);

    }

    public void updateBill(){
        if(cropBill != null){
            cropBill.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropBill.setTerms(termsTxt.getSelectedItem().toString());
            cropBill.setNotes(notesTxt.getText().toString());
            cropBill.setDueDate(dueDateTxt.getText().toString());
            cropBill.setOrderNumber(billOrderNumberTextView.getText().toString());
            cropBill.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
            cropBill.setBillDate(billDateTxt.getText().toString());
            cropBill.setNumber(billNumberTxt.getText().toString());
            cropBill.setSupplierId(((CropSpinnerItem)suppliersSp.getSelectedItem()).getId());
            bill_orderItems.clear();

            for(CropProductItem x:itemListRecyclerAdapter.getItems()){
                bill_orderItems.add(x);
            }
            cropBill.setItems(bill_orderItems);
            dbHandler.updateCropBill(cropBill);
        }
    }
    public void fillViews(){
        if(cropBill != null){
            CropDashboardActivity.selectSpinnerItemById(suppliersSp,cropBill.getSupplierId());
            CropDashboardActivity.selectSpinnerItemByValue(termsTxt,cropBill.getTerms());
            ArrayList <CropProductItem> items = new ArrayList<>();
            for(CropProductItem x: cropBill.getItems()){
                items.add(x);
            }
            itemListRecyclerAdapter.appendList(items);

            notesTxt.setText(cropBill.getNotes());
            dueDateTxt.setText(cropBill.getDueDate());
            discountPercentageTxt.setText(cropBill.getDiscount()+"");
            billNumberTxt.setText(cropBill.getNumber());
            billOrderNumberTextView.setText(cropBill.getOrderNumber());
            billDateTxt.setText(cropBill.getBillDate());

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

        if(billDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            billDateTxt.requestFocus();
        }
        if(dueDateTxt.getText().toString().isEmpty()){
            message =getString(R.string.due_date_not_entered);
            dueDateTxt.requestFocus();
        }
        if(billOrderNumberTextView.getText().toString().isEmpty()){
            message =getString(R.string.order_number_not_entered);
            billOrderNumberTextView.requestFocus();
        }
        if(itemListRecyclerAdapter.getItems().size()==0){
            message =getString(R.string.invoice_no_items_added);

        }
        if(discountPercentageTxt.getText().toString().isEmpty()){
            discountPercentageTxt.setText(getString(R.string.default_numeric_value));
        }


        if(suppliersSp.getSelectedItemPosition()==0){
            message = getString(R.string.supplier_not_selected);
            suppliersSp.requestFocus();
        }
        if(termsTxt.getSelectedItemPosition()==0){
            message = getString(R.string.terms_not_selected);
            termsTxt.requestFocus();
        }
        if(message != null){
            Toast.makeText(CropBillManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void computeDueDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            String date = billDateTxt.getText().toString();
            if (!date.isEmpty() && termsTxt.getSelectedItemPosition()!=0){
                cal.setTime( dateFormat.parse(date));
                int days =0;
                String terms = termsTxt.getSelectedItem().toString().toLowerCase();

                dueDateTxt.setEnabled(false);
                if(terms.equals("due on receipt")){
                    days=0;
                }else if(terms.equals("net 7")){
                    days =7;
                }
                else if(terms.equals("net 15")){
                    days =15;
                }
                else if(terms.equals("net 30")){
                    days =60;
                }
                else if(terms.equals("net 60")){
                    days =60;
                }
                else if(terms.equals("none")){
                    dueDateTxt.setEnabled(true);
                }
                cal.add(Calendar.DAY_OF_MONTH,days);
                NumberFormat formatter = new DecimalFormat("00");
                dueDateTxt.setText(cal.get(Calendar.YEAR)+"-"+formatter.format(cal.get(Calendar.MONTH)+1)+"-"+formatter.format(cal.get(Calendar.DAY_OF_MONTH)));
            }
        } catch (ParseException e) {

        }
    }


}
