package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

public class CropCustomerManagerActivity extends AppCompatActivity {
    CropCustomer cropCustomer =null;
    TextView currencyTxt;
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
        currencyTxt = findViewById(R.id.txt_crop_customer_currency);
        billingStreetTxt = findViewById(R.id.txt_crop_customer_street);
        billingCityTxt = findViewById(R.id.txt_crop_customer_city_town);
        billingCountryTcxt = findViewById(R.id.txt_crop_customer_country);
        shippingStretTxt = findViewById(R.id.txt_crop_customer_shipping_add_street);
        shippingCityTxt = findViewById(R.id.txt_crop_customer_shipping_add_city_town);
        shippingCountryTxt = findViewById(R.id.txt_crop_customer_shipping_add_country);
        billingAsShipping = findViewById(R.id.check_crop_customer_shipping_as_billing);

        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());

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

                    if(message == null){
                        shippingCityTxt.setText(billingCityTxt.getText().toString());
                        shippingStretTxt.setText(billingStreetTxt.getText().toString());
                        DashboardActivity.selectSpinnerItemByValue(shippingCountryTxt,billingCountryTcxt.getSelectedItem().toString());
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
        ((ArrayAdapter)billingCountryTcxt.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)shippingCountryTxt.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


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
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
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
        shippingCountryTxt.setOnItemSelectedListener(onItemSelectedListener);
        shippingCountryTxt.setOnItemSelectedListener(onItemSelectedListener);
    }
    public void saveFields(){
        cropCustomer = new CropCustomer();
        cropCustomer.setUserId(DashboardActivity.getPreferences("userId",this));
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
            DashboardActivity.selectSpinnerItemByValue(billingCountryTcxt,cropCustomer.getBillingCountry());
            DashboardActivity.selectSpinnerItemByValue(shippingCountryTxt,cropCustomer.getShippingCountry());
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
