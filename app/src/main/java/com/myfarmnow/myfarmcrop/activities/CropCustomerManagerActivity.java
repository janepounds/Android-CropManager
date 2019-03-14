package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.myfarmnow.myfarmcrop.models.CropCustomer;

public class CropCustomerManagerActivity extends AppCompatActivity {
    CropCustomer cropCustomer =null;
    EditText nameTxt, companyTxt, taxRegTxt,phoneTxt, mobileTxt, emailTxt, balanceTxt, billingStreetTxt,billingCityTxt,shippingStretTxt,shippingCityTxt;
   Spinner billingCountryTcxt,shippingCountryTxt;
   CheckBox billingAsShipping;
    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_customer_manager);
        if(getIntent().hasExtra("cropCustomer")){
            cropCustomer = (CropCustomer)getIntent().getSerializableExtra("cropCustomer");
        }
        initializeForm();
    }

    public void initializeForm(){

        nameTxt = findViewById(R.id.txt_crop_customer_name);
        phoneTxt = findViewById(R.id.txt_crop_customer_phone);
        mobileTxt = findViewById(R.id.txt_crop_customer_mobile);
        emailTxt = findViewById(R.id.txt_crop_customer_email);
        companyTxt = findViewById(R.id.txt_crop_customer_company);
        taxRegTxt = findViewById(R.id.txt_crop_customer_tax_reg_number);
        balanceTxt = findViewById(R.id.txt_crop_customer_opening_balance);
        billingStreetTxt = findViewById(R.id.txt_crop_customer_street);
        billingCityTxt = findViewById(R.id.txt_crop_customer_city_town);
        billingCountryTcxt = findViewById(R.id.txt_crop_customer_country);
        shippingStretTxt = findViewById(R.id.txt_crop_customer_shipping_add_street);
        shippingCityTxt = findViewById(R.id.txt_crop_customer_shipping_add_city_town);
        shippingCountryTxt = findViewById(R.id.txt_crop_customer_shipping_add_country);
        billingAsShipping = findViewById(R.id.check_crop_customer_shipping_as_billing);

        billingAsShipping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String message=null;
                    if(billingCountryTcxt.getSelectedItemPosition()==0){
                        message = getString(R.string.billing_address_not_complete);
                        balanceTxt.requestFocus();
                    }
                    else if(billingCityTxt.getText().toString().isEmpty()){
                        message = getString(R.string.billing_address_not_complete);
                        billingCityTxt.requestFocus();
                    }
                    else if(billingStreetTxt.getText().toString().isEmpty()){
                        message = getString(R.string.billing_address_not_complete);
                        billingStreetTxt.requestFocus();
                    }

                    if(message != null){
                        shippingCityTxt.setText(billingCityTxt.getText().toString());
                        shippingStretTxt.setText(billingStreetTxt.getText().toString());
                        CropDashboardActivity.selectSpinnerItemByValue(shippingCountryTxt,billingCountryTcxt.getSelectedItem().toString());
                    }
                    else{
                        billingAsShipping.setChecked(false);
                        Toast.makeText(CropCustomerManagerActivity.this, message, Toast.LENGTH_LONG).show();
                    }

                }
            }
        });


        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        fillViews();
        saveBtn.setOnClickListener(new View.OnClickListener() {
           
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropCustomer ==null){
                        saveFields();
                    }
                    else{
                        updateField();
                    }

                    Intent toCropCustomersList = new Intent(CropCustomerManagerActivity.this, CropCustomersListActivity.class);
                    toCropCustomersList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropCustomersList);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }
    public void saveFields(){
        cropCustomer = new CropCustomer();
        cropCustomer.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropCustomer.setName(nameTxt.getText().toString());
        cropCustomer.setPhone( phoneTxt.getText().toString());
        cropCustomer.setMobile( mobileTxt.getText().toString());
        cropCustomer.setEmail( emailTxt.getText().toString());
        cropCustomer.setCompany(companyTxt.getText().toString());
        cropCustomer.setTaxRegNo(taxRegTxt.getText().toString());
        cropCustomer.setOpeningBalance(Float.parseFloat(balanceTxt.getText().toString()));
        cropCustomer.setBillingCountry(billingCountryTcxt.getSelectedItem().toString());
        cropCustomer.setBillingCityOrTown(billingCityTxt.getText().toString());
        cropCustomer.setBillingStreet(billingStreetTxt.getText().toString());
        cropCustomer.setShippingCountry(shippingCountryTxt.getSelectedItem().toString());
        cropCustomer.setShippingCityOrTown(shippingCityTxt.getText().toString());
        cropCustomer.setShippingStreet(shippingStretTxt.getText().toString());


        dbHandler.insertCropCustomer(cropCustomer);



    }
    public void updateField(){
        if(cropCustomer !=null){

            cropCustomer.setName(nameTxt.getText().toString());
            cropCustomer.setPhone( phoneTxt.getText().toString());
            cropCustomer.setMobile( mobileTxt.getText().toString());
            cropCustomer.setEmail( emailTxt.getText().toString());
            cropCustomer.setCompany(companyTxt.getText().toString());
            cropCustomer.setTaxRegNo(taxRegTxt.getText().toString());
            cropCustomer.setOpeningBalance(Float.parseFloat(balanceTxt.getText().toString()));
            cropCustomer.setBillingCountry(billingCountryTcxt.getSelectedItem().toString());
            cropCustomer.setBillingCityOrTown(billingCityTxt.getText().toString());
            cropCustomer.setBillingStreet(billingStreetTxt.getText().toString());
            cropCustomer.setShippingCountry(shippingCountryTxt.getSelectedItem().toString());
            cropCustomer.setShippingCityOrTown(shippingCityTxt.getText().toString());
            cropCustomer.setShippingStreet(shippingStretTxt.getText().toString());
            dbHandler.updateCropCustomer(cropCustomer);

        }
    }

    public void fillViews(){
        if(cropCustomer !=null){
            nameTxt.setText(cropCustomer.getName());
            CropDashboardActivity.selectSpinnerItemByValue(billingCountryTcxt,cropCustomer.getBillingCountry());
            CropDashboardActivity.selectSpinnerItemByValue(shippingCountryTxt,cropCustomer.getShippingCountry());
            companyTxt.setText(cropCustomer.getCompany()+"");
            taxRegTxt.setText(cropCustomer.getTaxRegNo()+"");
            phoneTxt.setText(cropCustomer.getPhone());
            mobileTxt.setText(cropCustomer.getMobile());
            emailTxt.setText(cropCustomer.getEmail());
            balanceTxt.setText(cropCustomer.getOpeningBalance()+"");
            billingCityTxt.setText(cropCustomer.getBillingCityOrTown());
            billingStreetTxt.setText(cropCustomer.getBillingStreet());
            shippingCityTxt.setText(cropCustomer.getShippingCityOrTown());
            shippingStretTxt.setText(cropCustomer.getShippingStreet());

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
        else if(shippingCountryTxt.getSelectedItemPosition()==0){
            message =getString(R.string.shipping_address_not_complete);
            shippingCountryTxt.requestFocus();
        }
        else if(shippingCityTxt.getText().toString().isEmpty()){
            message =getString(R.string.shipping_address_not_complete);
            shippingCityTxt.requestFocus();
        }
        else if(shippingStretTxt.getText().toString().isEmpty()){
            message =getString(R.string.shipping_address_not_complete);
            shippingStretTxt.requestFocus();
        }
        else if(billingCountryTcxt.getSelectedItemPosition()==0){
            message = getString(R.string.billing_address_not_complete);
            balanceTxt.requestFocus();
        }
        else if(billingCityTxt.getText().toString().isEmpty()){
            message = getString(R.string.billing_address_not_complete);
            billingCityTxt.requestFocus();
        }
        else if(billingStreetTxt.getText().toString().isEmpty()){
            message = getString(R.string.billing_address_not_complete);
            billingStreetTxt.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropCustomerManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


}
