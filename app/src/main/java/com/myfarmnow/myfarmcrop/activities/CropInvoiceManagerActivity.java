package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropItemListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.fragments.CropPaymentDialog;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.models.CropInvoiceItem;
import com.myfarmnow.myfarmcrop.models.CropPayment;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.PaymentDialogActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CropInvoiceManagerActivity extends AppCompatActivity implements PaymentDialogActivity {

    CropItemListRecyclerAdapter itemListRecyclerAdapter;
    RecyclerView itemListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    LinearLayout paymentMadeLayout;
    Button addItemBtn,addPaymentBtn;
    MyFarmDbHandlerSingleton dbHandler;
    CropInvoice cropInvoice;
    TextView subTotalTextView, discountAmountTxt,shippingChargesTxtView,totalAmountTxt,amountPaidTxt;
    EditText discountPercentageTxt,shippingChargesTxt,termsAndConditionsTxt,notesTxt,invoiceDateTxt, dueDateTxt,orderNumberTxt;
    TextView invoiceNumberTextView;
    Spinner customersSp,termsSp;
    Button saveBtn;
    ArrayList <CropProduct> list = new ArrayList<>();
    ArrayList <CropInvoiceItem> invoiceItems = new ArrayList<>();
    CropSpinnerAdapter customersSpinnerAdapter;
    CropPayment cropPayment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_invoice_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("cropInvoice")){
            cropInvoice = (CropInvoice)getIntent().getSerializableExtra("cropInvoice");
        }
        initializeForm();


    }

    public void initializeForm(){
        subTotalTextView = findViewById(R.id.txt_view_crop_invoice_sub_total);
        discountAmountTxt = findViewById(R.id.txt_crop_invoice_discount);
        shippingChargesTxtView = findViewById(R.id.txt_view_crop_invoice_shipping_charges);
        totalAmountTxt = findViewById(R.id.txt_crop_invoice_total);
        discountPercentageTxt = findViewById(R.id.txt_crop_invoice_discount_percentage);
        shippingChargesTxt = findViewById(R.id.txt_crop_invoice_shipping_charge);
        invoiceNumberTextView = findViewById(R.id.txt_crop_invoice_number);
        invoiceDateTxt = findViewById(R.id.txt_crop_invoice_date);
        dueDateTxt = findViewById(R.id.txt_view_crop_invoice_due_date);
        notesTxt = findViewById(R.id.txt_crop_invoice_customer_notes);
        termsAndConditionsTxt = findViewById(R.id.txt_crop_invoice_terms_and_conditions);
        customersSp = findViewById(R.id.spinner_crop_invoice_customer_name);
        termsSp = findViewById(R.id.spinner_crop_invoice_terms);
        paymentMadeLayout = findViewById(R.id.layout_crop_invoice_payment_made);
        amountPaidTxt = findViewById(R.id.txt_crop_invoice_payment_made);
        orderNumberTxt = findViewById(R.id.txt_crop_invoice_order_number);
        saveBtn = findViewById(R.id.btn_save);
        CropDashboardActivity.addDatePicker(invoiceDateTxt,this);
        CropDashboardActivity.addDatePicker(dueDateTxt,this);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        itemListRecyclerView = findViewById(R.id.recyc_view_crop_invoice_item_list);
        itemListRecyclerAdapter = new CropItemListRecyclerAdapter(this,new ArrayList<CropProductItem>(),dbHandler.getCropProducts(CropDashboardActivity.getPreferences("userId",this)),subTotalTextView);
        itemListRecyclerView.setAdapter(itemListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        itemListRecyclerView.setLayoutManager(linearLayoutManager);
        addItemBtn = findViewById(R.id.btn_add_item);
        addPaymentBtn = findViewById(R.id.btn_add_payment);

        ArrayList<CropSpinnerItem> customersList = new ArrayList<>();
        for(CropCustomer x: dbHandler.getCropCustomers(CropDashboardActivity.getPreferences("userId",this))){
            customersList.add(x);
        }
        customersSpinnerAdapter = new CropSpinnerAdapter(customersList,"Customer",this);
        customersSp.setAdapter(customersSpinnerAdapter);
        invoiceNumberTextView.setText(dbHandler.getNextInvoiceNumber());
        //  invoiceNumberTextView.setEnabled(false);


        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropInvoiceItem item = new CropInvoiceItem();
                itemListRecyclerAdapter.addCropProductItem(item);
            }
        });
        addPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropPaymentDialog dialog = new CropPaymentDialog();
                dialog.setShowsDialog(true);
                dialog.show(getSupportFragmentManager(),"Payment");
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

        invoiceDateTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                computeDueDate();
            }
        });

        termsSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position !=0 ){
                    computeDueDate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                    if(cropInvoice ==null){
                        savePayment();
                    }
                    else{
                        updateInvoice();
                    }

                    Intent toCropEmployeesList = new Intent(CropInvoiceManagerActivity.this, CropInvoicesListActivity.class);
                    toCropEmployeesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropEmployeesList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        fillViews();


    }

    public void savePayment(){
        cropInvoice = new CropInvoice();
        cropInvoice.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropInvoice.setTermsAndConditions(termsAndConditionsTxt.getText().toString());
        cropInvoice.setTerms(termsSp.getSelectedItem().toString());
        cropInvoice.setCustomerNotes(notesTxt.getText().toString());
        cropInvoice.setDueDate(dueDateTxt.getText().toString());
        cropInvoice.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
        cropInvoice.setShippingCharges(Float.parseFloat(shippingChargesTxt.getText().toString()));
        cropInvoice.setDate(invoiceDateTxt.getText().toString());
        cropInvoice.setNumber(invoiceNumberTextView.getText().toString());
        cropInvoice.setOrderNumber(orderNumberTxt.getText().toString());
        cropInvoice.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());

        invoiceItems.clear();

        for(CropProductItem x:itemListRecyclerAdapter.getItems()){
            invoiceItems.add((CropInvoiceItem)x);
        }
        cropInvoice.setItems(invoiceItems);

        if(cropPayment != null){
            cropPayment.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());
            cropInvoice.setInitialPayment(cropPayment);
        }

        dbHandler.insertCropInvoice(cropInvoice);

    }

    public void updateInvoice(){
        if(cropInvoice != null){
            cropInvoice.setCustomerNotes(notesTxt.getText().toString());
            cropInvoice.setTermsAndConditions(termsAndConditionsTxt.getText().toString());
            cropInvoice.setDueDate(dueDateTxt.getText().toString());
            cropInvoice.setTerms(termsSp.getSelectedItem().toString());
            cropInvoice.setDiscount(Float.parseFloat(discountPercentageTxt.getText().toString()));
            cropInvoice.setShippingCharges(Float.parseFloat(shippingChargesTxt.getText().toString()));
            cropInvoice.setDate(invoiceDateTxt.getText().toString());
            cropInvoice.setNumber(invoiceNumberTextView.getText().toString());
            cropInvoice.setOrderNumber(orderNumberTxt.getText().toString());
            cropInvoice.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());
            invoiceItems.clear();
            for(CropProductItem x:itemListRecyclerAdapter.getItems()){
                invoiceItems.add((CropInvoiceItem)x);
            }
            cropInvoice.setDeletedItemsIds(itemListRecyclerAdapter.getDeleteItemsId());
            cropInvoice.setItems(invoiceItems);
            if(cropPayment != null){
                cropPayment.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());
                cropInvoice.setInitialPayment(cropPayment);
            }
            dbHandler.updateCropInvoice(cropInvoice);
        }
    }

    public void fillViews(){
        if(cropInvoice != null){
            CropDashboardActivity.selectSpinnerItemById(customersSp,cropInvoice.getCustomerId());
            CropDashboardActivity.selectSpinnerItemByValue(termsSp,cropInvoice.getTerms());
            ArrayList <CropProductItem> items = new ArrayList<>();
            for(CropProductItem x: cropInvoice.getItems()){
                items.add(x);
            }
            itemListRecyclerAdapter.appendList(items);
            termsAndConditionsTxt.setText(cropInvoice.getTermsAndConditions());
            notesTxt.setText(cropInvoice.getCustomerNotes());
            dueDateTxt.setText(cropInvoice.getDueDate());
            shippingChargesTxt.setText(cropInvoice.getShippingCharges()+"");
            discountPercentageTxt.setText(cropInvoice.getDiscount()+"");
            invoiceNumberTextView.setText(cropInvoice.getNumber());
            orderNumberTxt.setText(cropInvoice.getOrderNumber());
            invoiceDateTxt.setText(cropInvoice.getDate());

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

        if(invoiceDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            invoiceDateTxt.requestFocus();
        }
        if(dueDateTxt.getText().toString().isEmpty()){
            message =getString(R.string.due_date_not_entered);
            dueDateTxt.requestFocus();
        }
        if(orderNumberTxt.getText().toString().isEmpty()){
            message =getString(R.string.order_number_not_entered);
            orderNumberTxt.requestFocus();
        }
        if(itemListRecyclerAdapter.getItems().size()==0){
            message =getString(R.string.invoice_no_items_added);

        }
        if(discountPercentageTxt.getText().toString().isEmpty()){
            discountPercentageTxt.setText(getString(R.string.default_numeric_value));
        }
        if(shippingChargesTxt.getText().toString().isEmpty()){
            shippingChargesTxt.setText(getString(R.string.default_numeric_value));
        }

        if(customersSp.getSelectedItemPosition()==0){
            message = getString(R.string.customer_not_selected);
            invoiceDateTxt.requestFocus();
        }
        if(termsSp.getSelectedItemPosition()==0){
            message = getString(R.string.terms_not_selected);
            invoiceDateTxt.requestFocus();
        }
        if(message != null){
            Toast.makeText(CropInvoiceManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void savePayment(CropPayment payment) {
        cropPayment = payment;
        amountPaidTxt.setText(cropPayment.getAmount()+"");
        addPaymentBtn.setVisibility(View.GONE);
        paymentMadeLayout.setVisibility(View.VISIBLE);

    }


    public void computeDueDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            String date = invoiceDateTxt.getText().toString();
            if (!date.isEmpty() && termsSp.getSelectedItemPosition()!=0){
                cal.setTime( dateFormat.parse(date));
                int days =0;
                String terms = termsSp.getSelectedItem().toString().toLowerCase();

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
                dueDateTxt.setText(cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DAY_OF_MONTH));
            }
        } catch (ParseException e) {

        }
    }

}
