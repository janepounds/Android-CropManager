package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropSupplier;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropSupplierManagerActivity extends AppCompatActivity {
    CropSupplier cropSupplier =null;
    TextView currencyTxt;
    EditText nameTxt, companyTxt, taxRegTxt,phoneTxt, mobileTxt, emailTxt, balanceTxt, invoiceStreetTxt,invoiceCityTxt;
   Spinner invoiceCountryTcxt;
 
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
        currencyTxt = findViewById(R.id.txt_crop_supplier_currency);
        invoiceStreetTxt = findViewById(R.id.txt_crop_supplier_street);
        invoiceCityTxt = findViewById(R.id.txt_crop_supplier_city_town);
        invoiceCountryTcxt = findViewById(R.id.txt_crop_supplier_country);
       



        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)invoiceCountryTcxt.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        invoiceCountryTcxt.setOnItemSelectedListener(onItemSelectedListener);

        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());

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
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }
    public void saveFields(){
        cropSupplier = new CropSupplier();
        cropSupplier.setUserId(DashboardActivity.RETRIEVED_USER_ID);
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
            DashboardActivity.selectSpinnerItemByValue(invoiceCountryTcxt,cropSupplier.getInvoiceCountry());
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
