package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentAddFinancialRecordBinding;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSupplier;

import java.util.ArrayList;


public class AddFinancialRecordFragment extends Fragment {
    private FragmentAddFinancialRecordBinding binding;
    private Context context;
    MyFarmDbHandlerSingleton dbHandler;
    NavController navController;
    CropIncomeExpense cropIncomeExpense =null;
    ArrayList<CropSpinnerItem> incomeArrayList= new ArrayList<>();
    ArrayList<CropSpinnerItem> expensesArrayList= new ArrayList<>();

    ArrayList<String>  customersList = new ArrayList<>();
    ArrayList<String>  suppliersList = new ArrayList<>();
    CropSpinnerAdapter cropsSpinnerAdapter, categoryAdapter;
    ArrayAdapter<String>  customerSupplierAdapter;



    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_add_financial_record, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = binding.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        if (requireActivity().getIntent().hasExtra("cropIncomeExpense")) {
            cropIncomeExpense = (CropIncomeExpense) requireActivity().getIntent().getSerializableExtra("cropIncomeExpense");
        }
        initializeForm();

        return binding.getRoot();
    }

    public void initializeForm() {
        categoryAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Category",context);
        binding.spCropIncomeExpenseCategory.setAdapter(categoryAdapter);
        //transactionSpinner.setEnabled(false);
//        binding.txtCropIncomeExpenseUnitPrice.setEnabled(false);

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        ((ArrayAdapter)binding.spCropIncomeExpensePaymentMode.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)binding.spCropIncomeExpensePaymentStatus.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)binding.spCropIncomeExpenseTransaction.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)binding.spCropIncomeExpenseCategory.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                   else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

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

        binding.spCropIncomeExpensePaymentStatus.setOnItemSelectedListener(onItemSelectedListener);
        binding.spCropIncomeExpensePaymentMode.setOnItemSelectedListener(onItemSelectedListener);


        DashboardActivity.addDatePicker(binding.txtCropIncomeExpenseDate,context);

        String income [] = getResources().getStringArray(R.array.sp_crop_income_category);
        String expenses [] = getResources().getStringArray(R.array.sp_crop_expenses_category);

        for(String x: income){
            incomeArrayList.add(new TransactionSpinnerItem(x));
        }
        for(String x: expenses){
            expensesArrayList.add(new TransactionSpinnerItem(x));
        }

        binding.spCropIncomeExpenseTransaction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {



            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try{
                    if(position == 0){
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                String selection = parent.getItemAtPosition(position).toString();

                if(selection.toLowerCase().equals("income")){
                    binding.spCropIncomeExpenseCategory.setEnabled(true);
                    categoryAdapter.changeItems(incomeArrayList);
                    binding.spinnerCropIncomeExpenseCustomerSupplier.setAdapter( new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, customersList));;

                    binding.spinnerCropIncomeExpenseCustomerSupplier.setEnabled(true);
                }
                else if(selection.toLowerCase().equals("expense")){
                    binding.spCropIncomeExpenseCategory.setEnabled(true);
                    categoryAdapter.changeItems(expensesArrayList);
                    binding.spinnerCropIncomeExpenseCustomerSupplier.setAdapter( new ArrayAdapter<String>(context,
                            android.R.layout.simple_dropdown_item_1line, suppliersList));
                    customerSupplierAdapter.addAll(suppliersList);
                    binding.spinnerCropIncomeExpenseCustomerSupplier.setEnabled(true);

                }
                else{
                    binding.spCropIncomeExpenseCategory.setEnabled(false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if (validateEntries()) {
                    if (cropIncomeExpense == null) {
                        saveFields();
                    } else {
                        updateField();
                    }

                    navController.navigate(R.id.action_addFinancialRecordFragment_to_financialRecordsFragment);
                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });

        ArrayList<CropSpinnerItem> cropsItems = new ArrayList<>();
        for(Crop x: dbHandler.getCrops(DashboardActivity.RETRIEVED_USER_ID)){
            cropsItems.add(x);
        }
        cropsSpinnerAdapter = new CropSpinnerAdapter(cropsItems,"Crops",context);


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

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        binding.txtCropIncomeExpenseGrossAmount.addTextChangedListener(watcher);



        customersList = new ArrayList<>();
        for(CropCustomer x: dbHandler.getCropCustomers(DashboardActivity.RETRIEVED_USER_ID)){
            customersList.add(x.getName());
        }
        suppliersList = new ArrayList<>();
        for(CropSupplier x: dbHandler.getCropSuppliers(DashboardActivity.RETRIEVED_USER_ID)){
            suppliersList.add(x.getName());
        }
        customerSupplierAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, customersList);

        Log.d("Supplier",suppliersList.toString());

        binding.spinnerCropIncomeExpenseCustomerSupplier.setAdapter(customerSupplierAdapter);
        binding.spinnerCropIncomeExpenseCustomerSupplier.setEnabled(false);

        fillViews();

    }

    public void saveFields() {
        cropIncomeExpense = new CropIncomeExpense();
        cropIncomeExpense.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        cropIncomeExpense.setDate(binding.txtCropIncomeExpenseDate.getText().toString());
        cropIncomeExpense.setCategory(binding.spCropIncomeExpenseCategory.getSelectedItem().toString());
        cropIncomeExpense.setTransaction(binding.spCropIncomeExpenseTransaction.getSelectedItem().toString());
        cropIncomeExpense.setGrossAmount(Integer.parseInt(binding.txtCropIncomeExpenseGrossAmount.getText().toString()));
        cropIncomeExpense.setPaymentMode(binding.spCropIncomeExpensePaymentMode.getSelectedItem().toString());
        cropIncomeExpense.setPaymentStatus(binding.spCropIncomeExpensePaymentStatus.getSelectedItem().toString());
        cropIncomeExpense.setCustomerSupplier(binding.spinnerCropIncomeExpenseCustomerSupplier.getText().toString());
        cropIncomeExpense.setItem(binding.txtCropIncomeExpenseItem.getText().toString());
        dbHandler.insertCropIncomeExpense(cropIncomeExpense);


    }
    public void updateField(){
        if(cropIncomeExpense !=null){

            cropIncomeExpense.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            cropIncomeExpense.setDate(binding.txtCropIncomeExpenseDate.getText().toString());
            cropIncomeExpense.setCategory(binding.spCropIncomeExpenseCategory.getSelectedItem().toString());
            cropIncomeExpense.setTransaction(binding.spCropIncomeExpenseTransaction.getSelectedItem().toString());
//            cropIncomeExpense.setCropId(((CropSpinnerItem)binding.spCropIncomeExpenseCrop.getSelectedItem()).getId());
//            cropIncomeExpense.setTaxes(Float.parseFloat(binding.txtCropIncomeExpenseTaxes.getText().toString()));
//            cropIncomeExpense.setQuantity(Float.parseFloat(binding.txtCropIncomeExpenseQuantity.getText().toString()));
            cropIncomeExpense.setGrossAmount(Integer.parseInt(binding.txtCropIncomeExpenseGrossAmount.getText().toString()));
            cropIncomeExpense.setPaymentMode(binding.spCropIncomeExpensePaymentMode.getSelectedItem().toString());
            cropIncomeExpense.setPaymentStatus(binding.spCropIncomeExpensePaymentStatus.getSelectedItem().toString());

            cropIncomeExpense.setCustomerSupplier(binding.spinnerCropIncomeExpenseCustomerSupplier.getText().toString());


//            cropIncomeExpense.setSellingPrice(Float.parseFloat(binding.txtCropIncomeExpenseSellingPrice.getText().toString()));
            cropIncomeExpense.setItem(binding.txtCropIncomeExpenseItem.getText().toString());

            dbHandler.updateCropIncomeExpense(cropIncomeExpense);

        }
    }
    public void fillViews(){
        if(cropIncomeExpense != null){
            // DashboardActivity.selectSpinnerItemByValue(categorySpinner, cropIncomeExpense.getCategory());
//            DashboardActivity.selectSpinnerItemById(binding.spCropIncomeExpenseCrop, cropIncomeExpense.getCropId());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropIncomeExpensePaymentMode, cropIncomeExpense.getPaymentMode());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropIncomeExpensePaymentStatus, cropIncomeExpense.getPaymentStatus());
            DashboardActivity.selectSpinnerItemByValue(binding.spCropIncomeExpenseTransaction, cropIncomeExpense.getTransaction());

            binding.txtCropIncomeExpenseDate.setText(cropIncomeExpense.getDate());
            binding.txtCropIncomeExpenseItem.setText(cropIncomeExpense.getItem());
//            binding.txtCropIncomeExpenseSellingPrice.setText(cropIncomeExpense.getSellingPrice()+"");
//            binding.txtCropIncomeExpenseQuantity.setText(cropIncomeExpense.getQuantity()+"");
            binding.txtCropIncomeExpenseGrossAmount.setText(cropIncomeExpense.getGrossAmount()+"");
//            binding.txtCropIncomeExpenseUnitPrice.setText(cropIncomeExpense.computeUnitPrice()+"");
            binding.spinnerCropIncomeExpenseCustomerSupplier.setText(cropIncomeExpense.getCustomerSupplier());
//            binding.txtCropIncomeExpenseTaxes.setText(cropIncomeExpense.getTaxes()+"");
            //customerSupplierSp.setText(cropIncomeExpense.getCustomerSupplier());
        }

    }

//    public float computeUnitPrice(){
//        try{
//            float grossAmount = Float.parseFloat(binding.txtCropIncomeExpenseGrossAmount.getText().toString());
//            float quantity = Float.parseFloat(binding.txtCropIncomeExpenseQuantity.getText().toString());
//            float unitPrice = (grossAmount/quantity);
//            binding.txtCropIncomeExpenseUnitPrice.setText(unitPrice+"");
//            return unitPrice;
//        }catch (Exception e){
//
//        }
//        return 0;
//    }

//    public float computeAmount(){
//        try{
//            float grossAmount = Float.parseFloat(binding.txtCropIncomeExpenseGrossAmount.getText().toString());
//            float taxes = Float.parseFloat(binding.txtCropIncomeExpenseTaxes.getText().toString());
//            float amount = (grossAmount-(grossAmount*(taxes/100)));
//            binding.txtCropIncomeExpenseGrossAmount.setText(amount+"");
//            return amount;
//        }catch (Exception e){
//
//        }
//        return 0;
//    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(binding.txtCropIncomeExpenseDate.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            binding.txtCropIncomeExpenseDate.requestFocus();
        }
        else if(binding.txtCropIncomeExpenseItem.getText().toString().isEmpty()){
            message = getString(R.string.item_not_entered_message);
            binding.txtCropIncomeExpenseItem.requestFocus();
        }
//        else if(binding.txtCropIncomeExpenseTaxes.getText().toString().isEmpty()){
//            message = getString(R.string.taxes_not_entered_message);
//            binding.txtCropIncomeExpenseTaxes.requestFocus();
//        }
//        else if(binding.txtCropIncomeExpenseSellingPrice.getText().toString().isEmpty()){
//            message = getString(R.string.selling_price_not_entered_message);
//            binding.txtCropIncomeExpenseSellingPrice.requestFocus();
//        }
//        else if(binding.txtCropIncomeExpenseQuantity.getText().toString().isEmpty()){
//            message = getString(R.string.quantity_not_entered_message);
//            binding.txtCropIncomeExpenseQuantity.requestFocus();
//        }
        else if(binding.txtCropIncomeExpenseGrossAmount.getText().toString().isEmpty()){
            message = getString(R.string.gross_amount_not_entered_message);
            binding.txtCropIncomeExpenseGrossAmount.requestFocus();
        }
//        else if(binding.txtCropIncomeExpenseUnitPrice.getText().toString().isEmpty()){
//            message = getString(R.string.unit_price_not_entered_message);
//            binding.txtCropIncomeExpenseUnitPrice.requestFocus();
//        }

        else if(binding.spCropIncomeExpenseTransaction.getSelectedItemPosition()==0){
            message = getString(R.string.transaction_not_selected_message);
            binding.spCropIncomeExpenseTransaction.requestFocus();
        }
        else if(binding.spCropIncomeExpenseCategory.getSelectedItemPosition()==0){
            message = getString(R.string.category_not_selected);
            binding.spCropIncomeExpenseCategory.requestFocus();
        }

        else if(binding.spCropIncomeExpensePaymentMode.getSelectedItemPosition()==0){
            message = getString(R.string.payment_mode_not_selected);
            binding.spCropIncomeExpensePaymentMode.requestFocus();
        }
        else if(binding.spCropIncomeExpensePaymentStatus.getSelectedItemPosition()==0){
            message = getString(R.string.payment_status_not_selected);
            binding.spCropIncomeExpensePaymentStatus.requestFocus();
        }


        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
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