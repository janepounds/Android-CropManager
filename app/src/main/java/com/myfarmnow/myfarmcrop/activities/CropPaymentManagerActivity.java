package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.models.CropPayment;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class CropPaymentManagerActivity extends AppCompatActivity {

    CropSpinnerAdapter customersSpinnerAdapter,invoicesSpinnerAdapter;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner customersSp,invoiceSp, paymentModeSp;
    EditText dateTxt,amountTxt, referenceNumberTxt,notesTxt;
    TextView paymentNumberTxt;
    CropPayment cropPayment;
    Button saveBtn;
    String submittedInvoiceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_payment_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("invoiceId")){
            submittedInvoiceId = getIntent().getStringExtra("invoiceId");
        }
        initializeForm();
    }

    public void initializeForm(){
        customersSp = findViewById(R.id.sp_crop_record_payment_received_customer_name);
        invoiceSp = findViewById(R.id.sp_crop_record_payment_received_select_operation);
        amountTxt = findViewById(R.id.txt_crop_record_payment_received_amount);
        dateTxt = findViewById(R.id.txt_crop_record_payment_received_date);
        paymentNumberTxt = findViewById(R.id.txt_crop_record_payment_received_payment_number);
        paymentModeSp = findViewById(R.id.txt_crop_record_payment_received_payment_mode);
        referenceNumberTxt = findViewById(R.id.txt_crop_record_payment_received_reference_number);
        notesTxt = findViewById(R.id.txt_crop_record_payment_received_notes);
        saveBtn = findViewById(R.id.btn_save);

        CropDashboardActivity.addDatePicker(dateTxt,this);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropPayment==null){
                        savePayment();
                    }
                    else{
                        updatePayment();
                    }
                    Intent toCropPaymentsList = new Intent(CropPaymentManagerActivity.this, CropPaymentsListActivity.class);
                    toCropPaymentsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropPaymentsList);
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        paymentNumberTxt.setText(dbHandler.getNextPaymentNumber());
        ArrayList<CropSpinnerItem> customersList = new ArrayList<>();
        for(CropCustomer x: dbHandler.getCropCustomers(CropDashboardActivity.getPreferences("userId",this))){
            customersList.add(x);
        }
        customersSpinnerAdapter = new CropSpinnerAdapter(customersList,"Customer",this);
        customersSp.setAdapter(customersSpinnerAdapter);
        ArrayList<CropSpinnerItem> invoicesList = new ArrayList<>();
        for(CropInvoice x: dbHandler.getCropInvoices(CropDashboardActivity.getPreferences("userId",this))){
            invoicesList.add(x);
        }
        invoicesSpinnerAdapter = new CropSpinnerAdapter(invoicesList,"Invoice",this);
        invoiceSp.setAdapter(invoicesSpinnerAdapter);
        fillViews();
    }

    public void savePayment(){
        cropPayment = new CropPayment();
            cropPayment.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropPayment.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());
            cropPayment.setInvoiceId(((CropSpinnerItem)invoiceSp.getSelectedItem()).getId());
            cropPayment.setPaymentNumber(paymentNumberTxt.getText().toString());
            cropPayment.setReferenceNo(referenceNumberTxt.getText().toString());
            cropPayment.setDate(dateTxt.getText().toString());
            cropPayment.setMode(paymentModeSp.getSelectedItem().toString());
            cropPayment.setAmount(Float.parseFloat(amountTxt.getText().toString()));
            cropPayment.setNotes(notesTxt.getText().toString());
            dbHandler.insertCropPayment(cropPayment);
    }
    public void updatePayment(){
        if(cropPayment !=null){
            cropPayment.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropPayment.setCustomerId(((CropSpinnerItem)customersSp.getSelectedItem()).getId());
            cropPayment.setInvoiceId(((CropSpinnerItem)invoiceSp.getSelectedItem()).getId());
            cropPayment.setPaymentNumber(paymentNumberTxt.getText().toString());
            cropPayment.setReferenceNo(referenceNumberTxt.getText().toString());
            cropPayment.setDate(dateTxt.getText().toString());
            cropPayment.setMode(paymentModeSp.getSelectedItem().toString());
            cropPayment.setAmount(Float.parseFloat(amountTxt.getText().toString()));
            cropPayment.setNotes(notesTxt.getText().toString());
            dbHandler.updateCropPayment(cropPayment);

        }
    }
    public void fillViews(){
        if(submittedInvoiceId != null){
            CropDashboardActivity.selectSpinnerItemById(invoiceSp,submittedInvoiceId);
        }
        if(cropPayment !=null){
            dateTxt.setText(cropPayment.getDate());
            CropDashboardActivity.selectSpinnerItemById(customersSp,cropPayment.getCustomerId());
            CropDashboardActivity.selectSpinnerItemById(invoiceSp,cropPayment.getInvoiceId());
            CropDashboardActivity.selectSpinnerItemByValue(paymentModeSp,cropPayment.getMode());
            paymentNumberTxt.setText(cropPayment.getPaymentNumber());
            referenceNumberTxt.setText(cropPayment.getReferenceNo());
            notesTxt.setText(cropPayment.getNotes());
            amountTxt.setText(cropPayment.getAmount()+"");
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(customersSp.getSelectedItemPosition()==0){
            message =  getString(R.string.customer_not_selected);
            customersSp.requestFocus();
        }
        else if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(paymentModeSp.getSelectedItemPosition()==0){
            message =getString(R.string.payment_mode_not_entered);
            paymentModeSp.requestFocus();
        }



        if(message != null){
            Toast.makeText(CropPaymentManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }



}
