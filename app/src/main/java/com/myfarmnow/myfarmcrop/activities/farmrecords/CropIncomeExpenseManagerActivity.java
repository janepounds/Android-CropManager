package com.myfarmnow.myfarmcrop.activities.farmrecords;

import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.Crop;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSupplier;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class CropIncomeExpenseManagerActivity extends AppCompatActivity {

    CropIncomeExpense cropIncomeExpense =null;

    EditText  dateTxt, sellingPriceTxt,itemTxt,quantityTxt,grossAmountTxt,unitPriceTxt,taxesTxt;
    Spinner categorySpinner, cropSpinner, paymentModeSpinner, paymentStatusSpinner, transactionSpinner;
    TextView currencyA,currencyB,currencyC,amountTxt;
    AutoCompleteTextView customerSupplierSp;
    Button saveBtn;
    CropSpinnerAdapter cropsSpinnerAdapter, categoryAdapter;
    ArrayAdapter<String>  customerSupplierAdapter;
    MyFarmDbHandlerSingleton dbHandler;
    Toolbar toolbar;

    ArrayList<CropSpinnerItem> incomeArrayList= new ArrayList<>();
    ArrayList<CropSpinnerItem> expensesArrayList= new ArrayList<>();

    ArrayList<String>  customersList = new ArrayList<>();
    ArrayList<String>  suppliersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_income__expense_manager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (getIntent().hasExtra("cropIncomeExpense")) {
            cropIncomeExpense = (CropIncomeExpense) getIntent().getSerializableExtra("cropIncomeExpense");
        }
        initializeForm();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void initializeForm() {

        dateTxt = findViewById(R.id.txt_crop_income_expense_date);
        categorySpinner = findViewById(R.id.sp_crop_income_expense_category);
        cropSpinner = findViewById(R.id.sp_crop_income_expense_crop);
        paymentModeSpinner = findViewById(R.id.sp_crop_income_expense_payment_mode);
        itemTxt = findViewById(R.id.txt_crop_income_expense_item);
        quantityTxt = findViewById(R.id.txt_crop_income_expense_quantity);
        grossAmountTxt = findViewById(R.id.txt_crop_income_expense_gross_amount);
        unitPriceTxt = findViewById(R.id.txt_crop_income_expense_unit_price);
        taxesTxt = findViewById(R.id.txt_crop_income_expense_taxes);
        currencyA = findViewById(R.id.txt_crop_income_expense_currencyA);
        currencyB = findViewById(R.id.txt_crop_income_expense_currencyB);
        currencyC = findViewById(R.id.txt_crop_income_expense_currencyC);


        customerSupplierSp = findViewById(R.id.spinner_crop_income_expense_customer_supplier);

        sellingPriceTxt = findViewById(R.id.txt_crop_income_expense_selling_price);

        paymentStatusSpinner = findViewById(R.id.sp_crop_income_expense_payment_status);
        transactionSpinner = findViewById(R.id.sp_crop_income_expense_transaction);
        categoryAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Category",this);
        categorySpinner.setAdapter(categoryAdapter);
        //transactionSpinner.setEnabled(false);
        unitPriceTxt.setEnabled(false);


        saveBtn = findViewById(R.id.btn_save);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        ((ArrayAdapter)paymentModeSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)paymentStatusSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)transactionSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)categorySpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

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

        paymentStatusSpinner.setOnItemSelectedListener(onItemSelectedListener);
        paymentModeSpinner.setOnItemSelectedListener(onItemSelectedListener);



        currencyA.setText(CropSettingsSingleton.getInstance().getCurrency());
        currencyB.setText(CropSettingsSingleton.getInstance().getCurrency());
        currencyC.setText(CropSettingsSingleton.getInstance().getCurrency());




        DashboardActivity.addDatePicker(dateTxt,this);

        String income [] = getResources().getStringArray(R.array.sp_crop_income_category);
        String expenses [] = getResources().getStringArray(R.array.sp_crop_expenses_category);

        for(String x: income){
            incomeArrayList.add(new TransactionSpinnerItem(x));
        }
        for(String x: expenses){
            expensesArrayList.add(new TransactionSpinnerItem(x));
        }

        transactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
                String selection = parent.getItemAtPosition(position).toString();

                if(selection.toLowerCase().equals("income")){
                    categorySpinner.setEnabled(true);
                    categoryAdapter.changeItems(incomeArrayList);
                    customerSupplierSp.setAdapter( new ArrayAdapter<String>(CropIncomeExpenseManagerActivity.this,
                            android.R.layout.simple_dropdown_item_1line, customersList));;

                    customerSupplierSp.setEnabled(true);
                }
                else if(selection.toLowerCase().equals("expense")){
                    categorySpinner.setEnabled(true);
                    categoryAdapter.changeItems(expensesArrayList);
                    customerSupplierSp.setAdapter( new ArrayAdapter<String>(CropIncomeExpenseManagerActivity.this,
                            android.R.layout.simple_dropdown_item_1line, suppliersList));
                    customerSupplierAdapter.addAll(suppliersList);
                    customerSupplierSp.setEnabled(true);

                }
                else{
                    categorySpinner.setEnabled(false);
                   // customerSupplierSp.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if (validateEntries()) {
                    if (cropIncomeExpense == null) {
                        saveFields();
                    } else {
                        updateField();
                    }

                    Intent toCropIncomeExpensesList = new Intent(CropIncomeExpenseManagerActivity.this, CropIncomeExpensesListActivity.class);
                    toCropIncomeExpensesList.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(toCropIncomeExpensesList);
                    finish();
                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });

        ArrayList<CropSpinnerItem> cropsItems = new ArrayList<>();
        for(Crop x: dbHandler.getCrops(DashboardActivity.getPreferences("userId",this))){
            cropsItems.add(x);
        }
        cropsSpinnerAdapter = new CropSpinnerAdapter(cropsItems,"Crops",this);
        cropSpinner.setAdapter(cropsSpinnerAdapter);

        cropsSpinnerAdapter.changeDefaultItem(new CropSpinnerItem() {
            @Override
            public String getId() {
                return null;
            }
            public String toString(){
                return "All Crops";
            }
        });

        TextWatcher watcher =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                computeUnitPrice();
            }

            @Override
            public void afterTextChanged(Editable s) {
                computeUnitPrice();
            }
        };

        grossAmountTxt.addTextChangedListener(watcher);
        quantityTxt.addTextChangedListener(watcher);


        customersList = new ArrayList<>();
        for(CropCustomer x: dbHandler.getCropCustomers(DashboardActivity.getPreferences("userId",this))){
            customersList.add(x.getName());
        }
        suppliersList = new ArrayList<>();
        for(CropSupplier x: dbHandler.getCropSuppliers(DashboardActivity.getPreferences("userId",this))){
            suppliersList.add(x.getName());
        }
        customerSupplierAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, customersList);

        Log.d("Supplier",suppliersList.toString());

        customerSupplierSp.setAdapter(customerSupplierAdapter);
        customerSupplierSp.setEnabled(false);

        fillViews();

    }

    public void saveFields() {
        cropIncomeExpense = new CropIncomeExpense();
        cropIncomeExpense.setUserId(DashboardActivity.getPreferences("userId", this));
        cropIncomeExpense.setDate(dateTxt.getText().toString());
        cropIncomeExpense.setCategory(categorySpinner.getSelectedItem().toString());
        cropIncomeExpense.setTransaction(transactionSpinner.getSelectedItem().toString());
        cropIncomeExpense.setCropId(((CropSpinnerItem)cropSpinner.getSelectedItem()).getId());
        cropIncomeExpense.setTaxes(Float.parseFloat(taxesTxt.getText().toString()));
        cropIncomeExpense.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
        cropIncomeExpense.setGrossAmount(Integer.parseInt(grossAmountTxt.getText().toString()));
        cropIncomeExpense.setPaymentMode(paymentModeSpinner.getSelectedItem().toString());
        cropIncomeExpense.setPaymentStatus(paymentStatusSpinner.getSelectedItem().toString());

        cropIncomeExpense.setCustomerSupplier(customerSupplierSp.getText().toString());


        cropIncomeExpense.setSellingPrice(Float.parseFloat(sellingPriceTxt.getText().toString()));
        cropIncomeExpense.setItem(itemTxt.getText().toString());
        dbHandler.insertCropIncomeExpense(cropIncomeExpense);


    }
    public void updateField(){
        if(cropIncomeExpense !=null){

            cropIncomeExpense.setUserId(DashboardActivity.getPreferences("userId", this));
            cropIncomeExpense.setDate(dateTxt.getText().toString());
            cropIncomeExpense.setCategory(categorySpinner.getSelectedItem().toString());
            cropIncomeExpense.setTransaction(transactionSpinner.getSelectedItem().toString());
            cropIncomeExpense.setCropId(((CropSpinnerItem)cropSpinner.getSelectedItem()).getId());
            cropIncomeExpense.setTaxes(Float.parseFloat(taxesTxt.getText().toString()));
            cropIncomeExpense.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
            cropIncomeExpense.setGrossAmount(Integer.parseInt(grossAmountTxt.getText().toString()));
            cropIncomeExpense.setPaymentMode(paymentModeSpinner.getSelectedItem().toString());
            cropIncomeExpense.setPaymentStatus(paymentStatusSpinner.getSelectedItem().toString());
            cropIncomeExpense.setCustomerSupplier(customerSupplierSp.getText().toString());
            cropIncomeExpense.setSellingPrice(Float.parseFloat(sellingPriceTxt.getText().toString()));

            cropIncomeExpense.setItem(itemTxt.getText().toString());

            dbHandler.updateCropIncomeExpense(cropIncomeExpense);

        }
    }
    public void fillViews(){
        if(cropIncomeExpense != null){
          // DashboardActivity.selectSpinnerItemByValue(categorySpinner, cropIncomeExpense.getCategory());
            DashboardActivity.selectSpinnerItemById(cropSpinner, cropIncomeExpense.getCropId());
            DashboardActivity.selectSpinnerItemByValue(paymentModeSpinner, cropIncomeExpense.getPaymentMode());
            DashboardActivity.selectSpinnerItemByValue(paymentStatusSpinner, cropIncomeExpense.getPaymentStatus());
            DashboardActivity.selectSpinnerItemByValue(transactionSpinner, cropIncomeExpense.getTransaction());

            dateTxt.setText(cropIncomeExpense.getDate());
            itemTxt.setText(cropIncomeExpense.getItem());
           sellingPriceTxt.setText(cropIncomeExpense.getSellingPrice()+"");
            quantityTxt.setText(cropIncomeExpense.getQuantity()+"");
            grossAmountTxt.setText(cropIncomeExpense.getGrossAmount()+"");
            unitPriceTxt.setText(cropIncomeExpense.computeUnitPrice()+"");
            customerSupplierSp.setText(cropIncomeExpense.getCustomerSupplier());
            taxesTxt.setText(cropIncomeExpense.getTaxes()+"");
            //customerSupplierSp.setText(cropIncomeExpense.getCustomerSupplier());
        }

    }

    public float computeUnitPrice(){
        try{
            float grossAmount = Float.parseFloat(grossAmountTxt.getText().toString());
            float quantity = Float.parseFloat(quantityTxt.getText().toString());
            float unitPrice = (grossAmount/quantity);
            unitPriceTxt.setText(unitPrice+"");
            return unitPrice;
        }catch (Exception e){

        }
        return 0;
    }

    public float computeAmount(){
        try{
            float grossAmount = Float.parseFloat(grossAmountTxt.getText().toString());
            float taxes = Float.parseFloat(taxesTxt.getText().toString());
            float amount = (grossAmount-(grossAmount*(taxes/100)));
            amountTxt.setText(amount+"");
            return amount;
        }catch (Exception e){

        }
        return 0;
    }




    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }
        else if(itemTxt.getText().toString().isEmpty()){
            message = getString(R.string.item_not_entered_message);
            itemTxt.requestFocus();
        }
        else if(taxesTxt.getText().toString().isEmpty()){
            message = getString(R.string.taxes_not_entered_message);
            taxesTxt.requestFocus();
        }
        else if(sellingPriceTxt.getText().toString().isEmpty()){
            message = getString(R.string.selling_price_not_entered_message);
            sellingPriceTxt.requestFocus();
        }
        else if(quantityTxt.getText().toString().isEmpty()){
            message = getString(R.string.quantity_not_entered_message);
            quantityTxt.requestFocus();
        }
        else if(grossAmountTxt.getText().toString().isEmpty()){
            message = getString(R.string.gross_amount_not_entered_message);
            grossAmountTxt.requestFocus();
        }
        else if(unitPriceTxt.getText().toString().isEmpty()){
            message = getString(R.string.unit_price_not_entered_message);
            unitPriceTxt.requestFocus();
        }

        else if(transactionSpinner.getSelectedItemPosition()==0){
            message = getString(R.string.transaction_not_selected_message);
            transactionSpinner.requestFocus();
        }
        else if(categorySpinner.getSelectedItemPosition()==0){
            message = getString(R.string.category_not_selected);
            categorySpinner.requestFocus();
        }

        else if(paymentModeSpinner.getSelectedItemPosition()==0){
            message = getString(R.string.payment_mode_not_selected);
            paymentModeSpinner.requestFocus();
        }
        else if(paymentStatusSpinner.getSelectedItemPosition()==0){
            message = getString(R.string.payment_status_not_selected);
            paymentStatusSpinner.requestFocus();
        }


        if(message != null){
            Toast.makeText(CropIncomeExpenseManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }


    private class TransactionSpinnerItem implements CropSpinnerItem {

        String value;
        public TransactionSpinnerItem(String  value){
            this.value =value;
        }

        @Override
        public String toString() {
            return value;
        }

        @Override
        public String getId() {
            return value;
        }
    }


}