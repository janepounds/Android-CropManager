package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.myfarmnow.myfarmcrop.fragments.CropInvoicePaymentDialog;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropEstimate;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.models.CropInvoicePayment;
import com.myfarmnow.myfarmcrop.models.CropProduct;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.models.CropSalesOrder;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.PaymentDialogActivity;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
    Button saveBtn,saveAndSendBtn;
    ArrayList <CropProduct> list = new ArrayList<>();
    ArrayList <CropProductItem> invoiceItems = new ArrayList<>();
    CropEstimate sourceCropEstimate = null;
    CropSalesOrder sourceCropSalesOrder;
    CropSpinnerAdapter customersSpinnerAdapter;
    CropInvoicePayment cropInvoicePayment;
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

        if(getIntent().hasExtra("cropEstimate")){
            loadEstimateAsInvoice((CropEstimate) getIntent().getSerializableExtra("cropEstimate"));
            cropInvoice=null; //just incase an invoice was submitted as well. ignore it
        }
        if(getIntent().hasExtra("cropSalesOrder")){
            loadSalesOrderAsInvoice((CropSalesOrder) getIntent().getSerializableExtra("cropSalesOrder"));
            cropInvoice=null; //just incase an invoice was submitted as well. ignore it
        }


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
        saveAndSendBtn = findViewById(R.id.btn_save_send);
        ((TextView)findViewById(R.id.txt_view_crop_estimate_total_label)).setText("Total ("+ CropSettingsSingleton.getInstance().getCurrency()+")");

        CropDashboardActivity.addDatePicker(invoiceDateTxt,this);
        CropDashboardActivity.addDatePicker(dueDateTxt,this);
        ((ArrayAdapter)termsSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

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
                CropProductItem item = new CropProductItem();
                itemListRecyclerAdapter.addCropProductItem(item);
            }
        });
        addPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropInvoicePaymentDialog dialog = new CropInvoicePaymentDialog();
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
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
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
                        saveInvoice();
                    }
                    else{
                        updateInvoice();
                    }

                    if(sourceCropEstimate != null){
                        sourceCropEstimate.setStatus(getString(R.string.estimate_status_invoiced));
                        dbHandler.updateCropEstimate(sourceCropEstimate);
                    }
                    if(sourceCropSalesOrder != null){
                        sourceCropSalesOrder.setStatus(getString(R.string.estimate_status_invoiced));
                        dbHandler.updateCropSalesOrder(sourceCropSalesOrder);
                    }


                    Intent toCropEmployeesList = new Intent(CropInvoiceManagerActivity.this, CropInvoicesListActivity.class);
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
                    CropInvoice invoice=null;
                    if(cropInvoice ==null){
                        invoice=saveInvoice();
                    }
                    else{
                        invoice=updateInvoice();
                    }

                    if(sourceCropEstimate != null){
                        sourceCropEstimate.setStatus(getString(R.string.estimate_status_invoiced));
                        dbHandler.updateCropEstimate(sourceCropEstimate);
                    }
                    if(sourceCropSalesOrder != null){
                        sourceCropSalesOrder.setStatus(getString(R.string.estimate_status_invoiced));
                        dbHandler.updateCropSalesOrder(sourceCropSalesOrder);
                    }

                    if(invoice != null){
                        Intent toEmailInvoice = new Intent(CropInvoiceManagerActivity.this, CropInvoicePreviewActivity.class);
                        toEmailInvoice.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        toEmailInvoice.putExtra("cropInvoice",invoice);
                        toEmailInvoice.putExtra("action",CropInvoicePreviewActivity.INVOICE_ACTION_EMAIL);
                        startActivity(toEmailInvoice);
                        finish();
                    }else{
                        Toast.makeText(CropInvoiceManagerActivity.this,"Invoice cant be Saved",Toast.LENGTH_LONG).show();
                    }


                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        fillViews();


    }



    public void loadEstimateAsInvoice(CropEstimate estimate){
        if(estimate != null){
            sourceCropEstimate = estimate;
            CropDashboardActivity.selectSpinnerItemById(customersSp,estimate.getCustomerId());
            ArrayList <CropProductItem> items = new ArrayList<>();
            for(CropProductItem x: estimate.getItems()){
                x.setParentObjectId(null); //remove any associated Estimate
               
                items.add(x);
            }
            itemListRecyclerAdapter.appendList(items);
            termsAndConditionsTxt.setText(estimate.getTermsAndConditions());
            notesTxt.setText(estimate.getCustomerNotes());
            //dueDateTxt.setText(cropInvoice.getDueDate());
            shippingChargesTxt.setText(estimate.getShippingCharges()+"");
            discountPercentageTxt.setText(estimate.getDiscount()+"");
            invoiceNumberTextView.setText(estimate.getNumber());
            //orderNumberTxt.setText(cropInvoice.getOrderNumber());
            //invoiceDateTxt.setText(cropInvoice.getDate());
        }
    }

    public void loadSalesOrderAsInvoice(CropSalesOrder estimate){
        if(estimate != null){
            sourceCropSalesOrder = estimate;
            CropDashboardActivity.selectSpinnerItemById(customersSp,estimate.getCustomerId());
            ArrayList <CropProductItem> items = new ArrayList<>();
            for(CropProductItem x: estimate.getItems()){
                x.setParentObjectId(null); //remove any associated Estimate

                items.add(x);
            }
            itemListRecyclerAdapter.appendList(items);
            termsAndConditionsTxt.setText(estimate.getTermsAndConditions());
            notesTxt.setText(estimate.getCustomerNotes());
            //dueDateTxt.setText(cropInvoice.getDueDate());
            shippingChargesTxt.setText(estimate.getShippingCharges()+"");
            discountPercentageTxt.setText(estimate.getDiscount()+"");
            invoiceNumberTextView.setText(estimate.getNumber());
            //orderNumberTxt.setText(estimate.getReferenceNumber());
            //invoiceDateTxt.setText(cropInvoice.getDate());
        }
    }

    public CropInvoice saveInvoice(){
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
            
                invoiceItems.add(x);
            
           
        }
        cropInvoice.setItems(invoiceItems);

        if(cropInvoicePayment != null){
            cropInvoicePayment.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());
            cropInvoice.setInitialPayment(cropInvoicePayment);
        }

       return dbHandler.insertCropInvoice(cropInvoice);

    }

    public CropInvoice updateInvoice(){
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
                invoiceItems.add((CropProductItem)x);
            }
            cropInvoice.setDeletedItemsIds(itemListRecyclerAdapter.getDeleteItemsId());
            Log.d("Invoice Items",invoiceItems.size()+" ITEMS");
            Log.d("Item : ",invoiceItems.get(0).print());

            cropInvoice.setItems(invoiceItems);
            if(cropInvoicePayment != null){
                cropInvoicePayment.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());
                cropInvoice.setInitialPayment(cropInvoicePayment);
            }
           return dbHandler.updateCropInvoice(cropInvoice);
        }
        return null;
    }

    public void fillViews(){
        if(cropInvoice != null){
            CropDashboardActivity.selectSpinnerItemById(customersSp,cropInvoice.getCustomerId());
            CropDashboardActivity.selectSpinnerItemByValue(termsSp,cropInvoice.getTerms());
            ArrayList <CropProductItem> items = new ArrayList<>();
            for(CropProductItem x: cropInvoice.getItems()){
                Log.d("IDS ",x.getId());
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
    public void savePayment(CropInvoicePayment payment) {
        cropInvoicePayment = payment;
        amountPaidTxt.setText(cropInvoicePayment.getAmount()+"");
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
                NumberFormat formatter = new DecimalFormat("00");
                dueDateTxt.setText(cal.get(Calendar.YEAR)+"-"+formatter.format(cal.get(Calendar.MONTH)+1)+"-"+formatter.format(cal.get(Calendar.DAY_OF_MONTH)));
            }
        } catch (ParseException e) {

        }
    }

}
