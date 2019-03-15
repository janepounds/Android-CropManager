package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropSupplier;

public class CropSupplierManagerActivity extends AppCompatActivity {
    CropSupplier cropSupplier =null;
    EditText nameTxt, companyTxt, taxRegTxt,phoneTxt, mobileTxt, emailTxt, balanceTxt, invoiceStreetTxt,invoiceCityTxt;
   Spinner invoiceCountryTcxt,shippingCountryTxt;
 
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_supplier_manager);


        if(getIntent().hasExtra("cropSupplier")){
            cropSupplier = (CropSupplier)getIntent().getSerializableExtra("cropSupplier");
        }
        initializeForm();
    }

    public void initializeForm(){

        nameTxt = findViewById(R.id.txt_crop_supplier_name);
        phoneTxt = findViewById(R.id.txt_crop_supplier_phone);
        mobileTxt = findViewById(R.id.txt_crop_supplier_mobile);
        emailTxt = findViewById(R.id.txt_crop_supplier_email);
        companyTxt = findViewById(R.id.txt_crop_supplier_company);
        taxRegTxt = findViewById(R.id.txt_crop_supplier_tax_reg_number);
        balanceTxt = findViewById(R.id.txt_crop_supplier_opening_balance);
        invoiceStreetTxt = findViewById(R.id.txt_crop_supplier_street);
        invoiceCityTxt = findViewById(R.id.txt_crop_supplier_city_town);
        invoiceCountryTcxt = findViewById(R.id.txt_crop_supplier_country);
       



        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        fillViews();
        saveBtn.setOnClickListener(new View.OnClickListener() {
           
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropSupplier ==null){
                        saveFields();
                    }
                    else{
                        updateField();
                    }

                    Intent toCropSuppliersList = new Intent(CropSupplierManagerActivity.this, CropSuppliersListActivity.class);
                    toCropSuppliersList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropSuppliersList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }
    public void saveFields(){
        cropSupplier = new CropSupplier();
        cropSupplier.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropSupplier.setName(nameTxt.getText().toString());
        cropSupplier.setPhone( phoneTxt.getText().toString());
        cropSupplier.setMobile( mobileTxt.getText().toString());
        cropSupplier.setEmail( emailTxt.getText().toString());
        cropSupplier.setCompany(companyTxt.getText().toString());
        cropSupplier.setTaxRegNo(taxRegTxt.getText().toString());
        cropSupplier.setOpeningBalance(Float.parseFloat(balanceTxt.getText().toString()));
        cropSupplier.setInvoiceCountry(invoiceCountryTcxt.getSelectedItem().toString());
        cropSupplier.setInvoiceCityOrTown(invoiceCityTxt.getText().toString());
        cropSupplier.setInvoiceStreet(invoiceStreetTxt.getText().toString());
        


        dbHandler.insertCropSupplier(cropSupplier);



    }
    public void updateField(){
        if(cropSupplier !=null){

            cropSupplier.setName(nameTxt.getText().toString());
            cropSupplier.setPhone( phoneTxt.getText().toString());
            cropSupplier.setMobile( mobileTxt.getText().toString());
            cropSupplier.setEmail( emailTxt.getText().toString());
            cropSupplier.setCompany(companyTxt.getText().toString());
            cropSupplier.setTaxRegNo(taxRegTxt.getText().toString());
            cropSupplier.setOpeningBalance(Float.parseFloat(balanceTxt.getText().toString()));
            cropSupplier.setInvoiceCountry(invoiceCountryTcxt.getSelectedItem().toString());
            cropSupplier.setInvoiceCityOrTown(invoiceCityTxt.getText().toString());
            cropSupplier.setInvoiceStreet(invoiceStreetTxt.getText().toString());
            dbHandler.updateCropSupplier(cropSupplier);

        }
    }

    public void fillViews(){
        if(cropSupplier !=null){
            nameTxt.setText(cropSupplier.getName());
            CropDashboardActivity.selectSpinnerItemByValue(invoiceCountryTcxt,cropSupplier.getInvoiceCountry());
            companyTxt.setText(cropSupplier.getCompany()+"");
            taxRegTxt.setText(cropSupplier.getTaxRegNo()+"");
            phoneTxt.setText(cropSupplier.getPhone());
            mobileTxt.setText(cropSupplier.getMobile());
            emailTxt.setText(cropSupplier.getEmail());
            balanceTxt.setText(cropSupplier.getOpeningBalance()+"");
            invoiceCityTxt.setText(cropSupplier.getInvoiceCityOrTown());
            invoiceStreetTxt.setText(cropSupplier.getInvoiceStreet());
            saveBtn.setText(R.string.btn_update_label);
        }
    }


   
    public boolean validateEntries(){
        String message = null;
        if(nameTxt.getText().toString().isEmpty()){
            message = getString(R.string.field_name_not_entered_message);
            nameTxt.requestFocus();
        }
        else if(companyTxt.getText().toString().isEmpty()){
            message = getString(R.string.total_area_not_entered_message);
            companyTxt.requestFocus();
        }
        else if(phoneTxt.getText().toString().isEmpty()){
            message = getString(R.string.phone_number_not_entered);
            phoneTxt.requestFocus();
        }

        else if(invoiceCountryTcxt.getSelectedItemPosition()==0){
            message = getString(R.string.invoice_address_not_complete);
            balanceTxt.requestFocus();
        }
        else if(invoiceCityTxt.getText().toString().isEmpty()){
            message = getString(R.string.invoice_address_not_complete);
            invoiceCityTxt.requestFocus();
        }
        else if(invoiceStreetTxt.getText().toString().isEmpty()){
            message = getString(R.string.invoice_address_not_complete);
            invoiceStreetTxt.requestFocus();
        }
        
        if(message != null){
            Toast.makeText(CropSupplierManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
