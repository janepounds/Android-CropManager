package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropBill;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropPaymentBill;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;


public class CropPaymentBillManagerActivity extends AppCompatActivity {

    CropPaymentBill cropPaymentBill=null;
    MyFarmDbHandlerSingleton dbHandler;
    CropSpinnerAdapter billsSpinnerAdapter;
    Spinner paymentModeSp,selectBillTxt;
    EditText paymentDateTxt,paymentMadeTxt, referenceNumberTxt,notesTxt;
    Button saveBtn;
    String billId;

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
        selectBillTxt = findViewById(R.id.txt_crop_payment_bill_select_bill);
        paymentModeSp = findViewById(R.id.sp_crop_payment_bill_payment_mode);
        referenceNumberTxt = findViewById(R.id.txt_crop_payment_bill_reference_number);
        notesTxt = findViewById(R.id.txt_crop_payment_bill_notes);
        saveBtn = findViewById(R.id.btn_save);

        CropDashboardActivity.addDatePicker(paymentDateTxt,this);

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
                    Intent CropPaymentsList = new Intent(CropPaymentBillManagerActivity.this, CropPaymentBillsListActivity.class);
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
        selectBillTxt.setAdapter(billsSpinnerAdapter);
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
        cropPaymentBill.setBillId(((CropSpinnerItem)selectBillTxt.getSelectedItem()).getId());
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
            cropPaymentBill.setBillId(((CropSpinnerItem)selectBillTxt.getSelectedItem()).getId());

            dbHandler.updateCropPaymentBill(cropPaymentBill);

        }
    }
    public void fillViews(){

        if(billId != null){
            CropDashboardActivity.selectSpinnerItemById(selectBillTxt,billId);
        }
        if(cropPaymentBill !=null){
            paymentDateTxt.setText(cropPaymentBill.getDate());
            CropDashboardActivity.selectSpinnerItemByValue(paymentModeSp,cropPaymentBill.getMode());
            CropDashboardActivity.selectSpinnerItemById(selectBillTxt,cropPaymentBill.getBillId());
            paymentMadeTxt.setText(cropPaymentBill.getAmount()+"");
            referenceNumberTxt.setText(cropPaymentBill.getReferenceNumber());
            notesTxt.setText(cropPaymentBill.getNotes());
            saveBtn.setText(R.string.btn_update_label);
        }
    }
    public boolean validateEntries(){
        String message = null;
       if(paymentDateTxt.getText().toString().isEmpty()){
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
            Toast.makeText(CropPaymentBillManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }



}
