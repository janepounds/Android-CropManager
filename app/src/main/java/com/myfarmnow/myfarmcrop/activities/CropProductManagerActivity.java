package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.myfarmnow.myfarmcrop.models.CropProduct;

public class CropProductManagerActivity extends AppCompatActivity {

    CropProduct cropProduct =null;
    EditText nameTxt, codeTxt, priceTxt, rateTxt, balanceTxt, descriptionTxt, openingCostTxt, openingQuantity;
    Spinner typeSp,unitsSp,linkedAccountSp;

    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_product_manager);


        if(getIntent().hasExtra("cropProduct")){
            cropProduct = (CropProduct)getIntent().getSerializableExtra("cropProduct");
        }
        initializeForm();
    }

    public void initializeForm(){

        nameTxt = findViewById(R.id.txt_crop_product_name);
        priceTxt = findViewById(R.id.txt_crop_product_selling_price);
        rateTxt = findViewById(R.id.txt_crop_product_rate);
        linkedAccountSp = findViewById(R.id.sp_crop_product_linked_account);
        unitsSp = findViewById(R.id.sp_crop_product_units);
        codeTxt = findViewById(R.id.txt_crop_product_code);
        descriptionTxt = findViewById(R.id.txt_crop_product_description);
        typeSp = findViewById(R.id.sp_crop_product_type);
        openingCostTxt = findViewById(R.id.txt_crop_product_opening_cost);
        openingQuantity = findViewById(R.id.txt_crop_product_opening_quantity);

        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);

        ((ArrayAdapter)typeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)unitsSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)linkedAccountSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        fillViews();
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropProduct ==null){
                        saveFields();
                    }
                    else{
                        updateField();
                    }

                    Intent toCropProductsList = new Intent(CropProductManagerActivity.this, CropProductsListActivity.class);
                    toCropProductsList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropProductsList);
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
        typeSp.setOnItemSelectedListener(onItemSelectedListener);
        unitsSp.setOnItemSelectedListener(onItemSelectedListener);
        linkedAccountSp.setOnItemSelectedListener(onItemSelectedListener);
    }
    public void saveFields(){
        cropProduct = new CropProduct();
        cropProduct.setUserId(CropDashboardActivity.getPreferences("userId",this));
        cropProduct.setName(nameTxt.getText().toString());
        cropProduct.setCode( codeTxt.getText().toString());
        cropProduct.setSellingPrice(Float.parseFloat(priceTxt.getText().toString()));
        cropProduct.setTaxRate(Float.parseFloat(rateTxt.getText().toString()));
        cropProduct.setOpeningQuantity(Float.parseFloat(openingQuantity.getText().toString()));
        cropProduct.setOpeningCost(Float.parseFloat(openingCostTxt.getText().toString()));

        cropProduct.setType(typeSp.getSelectedItem().toString());
        if(unitsSp.getSelectedItemPosition() != 0){
            cropProduct.setUnits(unitsSp.getSelectedItem().toString());
        }
       if(linkedAccountSp.getSelectedItemPosition() != 0){
            cropProduct.setLinkedAccount(linkedAccountSp.getSelectedItem().toString());
        }
        cropProduct.setDescription(descriptionTxt.getText().toString());

        dbHandler.insertCropProduct(cropProduct);



    }
    public void updateField(){
        if(cropProduct !=null){

            cropProduct.setName(nameTxt.getText().toString());
            cropProduct.setCode( codeTxt.getText().toString());
            cropProduct.setSellingPrice(Float.parseFloat(priceTxt.getText().toString()));
            cropProduct.setTaxRate(Float.parseFloat(rateTxt.getText().toString()));
            cropProduct.setOpeningQuantity(Float.parseFloat(openingQuantity.getText().toString()));
            cropProduct.setOpeningCost(Float.parseFloat(openingCostTxt.getText().toString()));

            cropProduct.setType(typeSp.getSelectedItem().toString());
            if(unitsSp.getSelectedItemPosition() != 0){
                cropProduct.setUnits(unitsSp.getSelectedItem().toString());
            }
            if(linkedAccountSp.getSelectedItemPosition() != 0){
                cropProduct.setLinkedAccount(linkedAccountSp.getSelectedItem().toString());
            }
            cropProduct.setDescription(descriptionTxt.getText().toString());
            dbHandler.updateCropProduct(cropProduct);

        }
    }

    public void fillViews(){
        if(cropProduct !=null){
            nameTxt.setText(cropProduct.getName());
            CropDashboardActivity.selectSpinnerItemByValue(typeSp,cropProduct.getType());
            CropDashboardActivity.selectSpinnerItemByValue(unitsSp,cropProduct.getUnits());
            CropDashboardActivity.selectSpinnerItemByValue(linkedAccountSp,cropProduct.getLinkedAccount());
            codeTxt.setText(cropProduct.getCode()+"");
            openingQuantity.setText(cropProduct.getOpeningQuantity()+"");
            openingCostTxt.setText(cropProduct.getOpeningCost()+"");
            priceTxt.setText(cropProduct.getSellingPrice()+"");
            rateTxt.setText(cropProduct.getTaxRate()+"");
            descriptionTxt.setText(cropProduct.getDescription());
            saveBtn.setText(R.string.btn_update_label);
        }
    }



    public boolean validateEntries(){
        String message = null;
        if(nameTxt.getText().toString().isEmpty()){
            message = getString(R.string.name_not_entered);
            nameTxt.requestFocus();
        }
        else if(codeTxt.getText().toString().isEmpty()){
            message = getString(R.string.total_area_not_entered_message);
            codeTxt.requestFocus();
        }
        else if(priceTxt.getText().toString().isEmpty()){
            message = getString(R.string.phone_number_not_entered);
            priceTxt.requestFocus();
        }
        else if(typeSp.getSelectedItemPosition()==0){
            message =getString(R.string.type_not_selected);
            typeSp.requestFocus();
        }

       if(openingQuantity.getText().toString().isEmpty()){
           openingQuantity.setText(R.string.default_numeric_value);
        }
        if(openingCostTxt.getText().toString().isEmpty()){
            openingCostTxt.setText(R.string.default_numeric_value);
        }
        if(priceTxt.getText().toString().isEmpty()){
            priceTxt.setText(R.string.default_numeric_value);
        }
        if(rateTxt.getText().toString().isEmpty()){
            rateTxt.setText(R.string.default_numeric_value);
        }


        if(message != null){
            Toast.makeText(CropProductManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}
