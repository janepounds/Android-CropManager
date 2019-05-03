package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropBill;
import com.myfarmnow.myfarmcrop.models.CropInvoice;
import com.myfarmnow.myfarmcrop.models.CropPaymentBill;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSupplier;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;


public class CropBillPaymentManagerActivity extends AppCompatActivity {

    CropPaymentBill cropPaymentBill=null;
    MyFarmDbHandlerSingleton dbHandler;
    CropSpinnerAdapter billsSpinnerAdapter;
    Spinner paymentModeSp, billSp, supplierSp;
    EditText paymentDateTxt,paymentMadeTxt, referenceNumberTxt,notesTxt;
    TextView currencyLabelTxt;
    Button saveBtn;
    String billId;
    CropSpinnerAdapter supplierSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_payment_bill_manager);


        if(getIntent().hasExtra("cropPaymentBill")){
            cropPaymentBill = (CropPaymentBill) getIntent().getSerializableExtra("cropPaymentBill");
        }
        initializeForm();

    }

    public void initializeForm(){
        paymentMadeTxt = findViewById(R.id.txt_crop_payment_bill_payment_made);
        paymentDateTxt = findViewById(R.id.txt_crop_payment_bill_payment_date);
        billSp = findViewById(R.id.txt_crop_payment_bill_select_bill);
        paymentModeSp = findViewById(R.id.sp_crop_payment_bill_payment_mode);
        supplierSp = findViewById(R.id.sp_crop_payment_bill_supplier);
        referenceNumberTxt = findViewById(R.id.txt_crop_payment_bill_reference_number);
        notesTxt = findViewById(R.id.txt_crop_payment_bill_notes);
        currencyLabelTxt = findViewById(R.id.txt_crop_payment_bill_currency);
        saveBtn = findViewById(R.id.btn_save);

        CropDashboardActivity.addDatePicker(paymentDateTxt,this);

        currencyLabelTxt.setText(CropSettingsSingleton.getInstance().getCurrency());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropPaymentBill==null){
                        savePayment();
                    }
                    else{
                        updatePayment();
                    }
                    Intent CropPaymentsList = new Intent(CropBillPaymentManagerActivity.this, CropPaymentBillsListActivity.class);
                    CropPaymentsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(CropPaymentsList);
                    finish();
                }
            }
        });
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)paymentModeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayList<CropSpinnerItem> billsList = new ArrayList<>();
        for(CropBill x: dbHandler.getCropBills(CropDashboardActivity.getPreferences("userId",this))){
            billsList.add(x);
        }
        billsSpinnerAdapter = new CropSpinnerAdapter(billsList,"Bill",this);
        billSp.setAdapter(billsSpinnerAdapter);

        ArrayList<CropSpinnerItem> suppliersList = new ArrayList<>();
        for(CropSupplier x: dbHandler.getCropSuppliers(CropDashboardActivity.getPreferences("userId",this))){
            suppliersList.add(x);
        }
        supplierSpinnerAdapter = new CropSpinnerAdapter(suppliersList,"Supplier",this);
        supplierSp.setAdapter(supplierSpinnerAdapter);

        billSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0){
                    CropBill invoice = (CropBill)((CropSpinnerItem)billSp.getSelectedItem());
                    CropDashboardActivity.selectSpinnerItemById(supplierSp,invoice.getSupplierId());
                    supplierSp.setEnabled(false);

                }
                else{
                    supplierSp.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fillViews();
    }

    public void savePayment(){
        cropPaymentBill = new CropPaymentBill();
        cropPaymentBill.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropPaymentBill.setReferenceNumber(referenceNumberTxt.getText().toString());
        cropPaymentBill.setDate(paymentDateTxt.getText().toString());
        cropPaymentBill.setMode(paymentModeSp.getSelectedItem().toString());
        cropPaymentBill.setAmount(Float.parseFloat(paymentMadeTxt.getText().toString()));
        cropPaymentBill.setNotes(notesTxt.getText().toString());
        cropPaymentBill.setBillId(((CropSpinnerItem) billSp.getSelectedItem()).getId());
        cropPaymentBill.setSupplierId(((CropSpinnerItem) supplierSp.getSelectedItem()).getId());
        dbHandler.insertCropPaymentBill(cropPaymentBill);
    }
    public void updatePayment(){
        if(cropPaymentBill !=null){
            cropPaymentBill.setUserId(CropDashboardActivity.getPreferences("userId",this));
            cropPaymentBill.setReferenceNumber(referenceNumberTxt.getText().toString());
            cropPaymentBill.setDate(paymentDateTxt.getText().toString());
            cropPaymentBill.setMode(paymentModeSp.getSelectedItem().toString());
            cropPaymentBill.setAmount(Float.parseFloat(paymentMadeTxt.getText().toString()));
            cropPaymentBill.setNotes(notesTxt.getText().toString());
            cropPaymentBill.setBillId(((CropSpinnerItem) billSp.getSelectedItem()).getId());
            cropPaymentBill.setSupplierId(((CropSpinnerItem) supplierSp.getSelectedItem()).getId());

            dbHandler.updateCropPaymentBill(cropPaymentBill);

        }
    }
    public void fillViews(){

        if(getIntent().hasExtra("billId")){
            CropDashboardActivity.selectSpinnerItemById(billSp,getIntent().getStringExtra("billId"));
        }
        if(cropPaymentBill !=null){
            paymentDateTxt.setText(cropPaymentBill.getDate());
            CropDashboardActivity.selectSpinnerItemByValue(paymentModeSp,cropPaymentBill.getMode());
            CropDashboardActivity.selectSpinnerItemById(billSp,cropPaymentBill.getBillId());
            paymentMadeTxt.setText(cropPaymentBill.getAmount()+"");
            referenceNumberTxt.setText(cropPaymentBill.getReferenceNumber());
            notesTxt.setText(cropPaymentBill.getNotes());
            saveBtn.setText(R.string.btn_update_label);
        }
    }
    public boolean validateEntries(){
        String message = null;
       if(supplierSp.getSelectedItemPosition()==0){
            message ="Supplier not selected";
            paymentModeSp.requestFocus();
        }else if(paymentDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            paymentDateTxt.requestFocus();
        }
        else if(paymentModeSp.getSelectedItemPosition()==0){
            message =getString(R.string.payment_mode_not_selected);
            paymentModeSp.requestFocus();
        }
        else if(paymentMadeTxt.getText().toString().isEmpty()){
           message = getString(R.string.payment_made_not_entered_message);
           paymentMadeTxt.requestFocus();
       }



        if(message != null){
            Toast.makeText(CropBillPaymentManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }



}
