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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropIncomeExpense;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;

import java.util.ArrayList;

public class AddFinancialRecordFragment extends Fragment {
    private static final String TAG = "AddFinancialRecord";
    private Context context;
    private MyFarmDbHandlerSingleton dbHandler;
    private NavController navController;
    private CropIncomeExpense cropIncomeExpense = null;
    private ArrayList<CropSpinnerItem> incomeArrayList = new ArrayList<>();
    private ArrayList<CropSpinnerItem> expensesArrayList = new ArrayList<>();

    private Toolbar toolbar;
    private ImageView imageDatePicker;

    private Spinner expenseTransaction, expenseCategory, expensePaymentMode, expensePaymentStatus,department;
    private EditText expenseDate, item, grossAmount;
    private AutoCompleteTextView customerSupplier;
    private Button btnSave;

    private ArrayList<String> customersList = new ArrayList<>();
    private ArrayList<String> suppliersList = new ArrayList<>();
    private CropSpinnerAdapter cropsSpinnerAdapter, categoryAdapter;
    private ArrayAdapter<String> customerSupplierAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_financial_record, container, false);
        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.toolbar_add_financial_record);
        imageDatePicker = view.findViewById(R.id.image_date_picker);
        expenseTransaction = view.findViewById(R.id.sp_crop_income_expense_transaction);
        expenseCategory = view.findViewById(R.id.sp_crop_income_expense_category);
        expensePaymentMode = view.findViewById(R.id.sp_crop_income_expense_payment_mode);
        expensePaymentStatus = view.findViewById(R.id.sp_crop_income_expense_payment_status);
        expenseDate = view.findViewById(R.id.txt_crop_income_expense_date);
        item = view.findViewById(R.id.txt_crop_income_expense_item);
        department = view.findViewById(R.id.sp_department);
        grossAmount = view.findViewById(R.id.txt_crop_income_expense_gross_amount);
        customerSupplier = view.findViewById(R.id.spinner_crop_income_expense_customer_supplier);
        btnSave = view.findViewById(R.id.btn_save);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_addFinancialRecordFragment_to_financialRecordsFragment);
            }
        });

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        //get arguments for edit
        if (getArguments() != null) {
            cropIncomeExpense = (CropIncomeExpense) getArguments().getSerializable("cropIncomeExpense");
            btnSave.setText(getString(R.string.update));
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(getString(R.string.update_financial_record));
        }

        initializeForm();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

    }

    public void initializeForm() {
        categoryAdapter = new CropSpinnerAdapter(new ArrayList<>(), "Category", context);
        expenseCategory.setAdapter(categoryAdapter);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        ((ArrayAdapter) expensePaymentMode.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) expensePaymentStatus.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) expenseTransaction.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) expenseCategory.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        DashboardActivity.addDatePicker(expenseDate,context);
        DashboardActivity.addDatePickerImageView(imageDatePicker, expenseDate, context);
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        expensePaymentStatus.setOnItemSelectedListener(onItemSelectedListener);
        expensePaymentMode.setOnItemSelectedListener(onItemSelectedListener);

        DashboardActivity.addDatePicker(expenseDate, context);

        String income[] = getResources().getStringArray(R.array.sp_crop_income_category);
        String expenses[] = getResources().getStringArray(R.array.sp_crop_expenses_category);

        for (String x : income) {
            incomeArrayList.add(new TransactionSpinnerItem(x));
        }
        for (String x : expenses) {
            expensesArrayList.add(new TransactionSpinnerItem(x));
        }

        expenseTransaction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    if (position == 0) {
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }

                } catch (Exception e) {

                }
                String selection = parent.getItemAtPosition(position).toString();

                if (selection.toLowerCase().equals("income")) {
                    expenseCategory.setEnabled(true);
                    categoryAdapter.changeItems(incomeArrayList);
                    customerSupplier.setAdapter(new ArrayAdapter<String>(context,  android.R.layout.simple_dropdown_item_1line, customersList));
                    customerSupplier.setEnabled(true);
                } else if (selection.toLowerCase().equals("expense")) {
                    expenseCategory.setEnabled(true);
                    categoryAdapter.changeItems(expensesArrayList);
                    customerSupplier.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, suppliersList));
                    customerSupplierAdapter.addAll(suppliersList);
                    customerSupplier.setEnabled(true);

                } else {
                    expenseCategory.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSave.setOnClickListener(v -> {
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
        });

        ArrayList<CropSpinnerItem> cropsItems = new ArrayList<>();
        for (Crop x : dbHandler.getCrops(DashboardActivity.RETRIEVED_USER_ID)) {
            cropsItems.add(x);
        }

        cropsSpinnerAdapter = new CropSpinnerAdapter(cropsItems, "Crops", context);

        cropsSpinnerAdapter.changeDefaultItem(new CropSpinnerItem() {
            @Override
            public String getId() {
                return null;
            }

            public String toString() {
                return "All Crops";
            }
        });

        TextWatcher watcher = new TextWatcher() {
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

        grossAmount.addTextChangedListener(watcher);


        fillViews();
    }

    public void saveFields() {
        cropIncomeExpense = new CropIncomeExpense();
        cropIncomeExpense.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        cropIncomeExpense.setDate(expenseDate.getText().toString());
        cropIncomeExpense.setCategory(expenseCategory.getSelectedItem().toString());
        cropIncomeExpense.setTransaction(expenseTransaction.getSelectedItem().toString());
        cropIncomeExpense.setGrossAmount(Integer.valueOf(grossAmount.getText().toString()));
        cropIncomeExpense.setPaymentMode(expensePaymentMode.getSelectedItem().toString());
        cropIncomeExpense.setPaymentStatus(expensePaymentStatus.getSelectedItem().toString());
        cropIncomeExpense.setCustomerSupplier(customerSupplier.getText().toString());
        cropIncomeExpense.setItem(item.getText().toString());
        cropIncomeExpense.setDepartment(department.getSelectedItem().toString());
        dbHandler.insertCropIncomeExpense(cropIncomeExpense);
    }

    public void updateField() {
        if (cropIncomeExpense != null) {

            cropIncomeExpense.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            cropIncomeExpense.setDate(expenseDate.getText().toString());
            cropIncomeExpense.setCategory(expenseCategory.getSelectedItem().toString());
            cropIncomeExpense.setTransaction(expenseTransaction.getSelectedItem().toString());
            cropIncomeExpense.setGrossAmount(Integer.parseInt(grossAmount.getText().toString()));
            cropIncomeExpense.setPaymentMode(expensePaymentMode.getSelectedItem().toString());
            cropIncomeExpense.setPaymentStatus(expensePaymentStatus.getSelectedItem().toString());
            cropIncomeExpense.setCustomerSupplier(customerSupplier.getText().toString());
            cropIncomeExpense.setDepartment(department.getSelectedItem().toString());
            cropIncomeExpense.setItem(item.getText().toString());

            dbHandler.updateCropIncomeExpense(cropIncomeExpense);
        }
    }

    public void fillViews() {
        if (cropIncomeExpense != null) {
//            DashboardActivity.selectSpinnerItemById(binding.spCropIncomeExpenseCrop, cropIncomeExpense.getCropId());
            DashboardActivity.selectSpinnerItemByValue(expensePaymentMode, cropIncomeExpense.getPaymentMode());
            DashboardActivity.selectSpinnerItemByValue(expensePaymentStatus, cropIncomeExpense.getPaymentStatus());
            DashboardActivity.selectSpinnerItemByValue(expenseTransaction, cropIncomeExpense.getTransaction());
            DashboardActivity.selectSpinnerItemByValue(department, cropIncomeExpense.getDepartment());

            if (cropIncomeExpense.getTransaction().toLowerCase().equals("income")) {
                expenseCategory.setEnabled(true);
                categoryAdapter.changeItems(incomeArrayList);


            } else if (cropIncomeExpense.getTransaction().toLowerCase().equals("expense")) {
                expenseCategory.setEnabled(true);
                categoryAdapter.changeItems(expensesArrayList);

            }
            expenseCategory.setAdapter(categoryAdapter);

            DashboardActivity.selectSpinnerItemByValue(expenseCategory, cropIncomeExpense.getCategory());

            expenseDate.setText(cropIncomeExpense.getDate());
            item.setText(cropIncomeExpense.getItem());
            grossAmount.setText(cropIncomeExpense.getGrossAmount() + "");
            customerSupplier.setText(cropIncomeExpense.getCustomerSupplier());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries() {
        String message = null;
        if (expenseDate.getText().toString().isEmpty()) {
            message = getString(R.string.date_not_entered_message);
            expenseDate.requestFocus();
        } else if (item.getText().toString().isEmpty()) {
            message = getString(R.string.item_not_entered_message);
            item.requestFocus();
        } else if (department.getSelectedItem().toString().isEmpty()) {
            message = "You must enter a department";
        } else if (grossAmount.getText().toString().isEmpty()) {
            message = getString(R.string.gross_amount_not_entered_message);
            grossAmount.requestFocus();
        } else if (expenseTransaction.getSelectedItemPosition() == 0) {
            message = getString(R.string.transaction_not_selected_message);
            expenseTransaction.requestFocus();
        } else if (expenseCategory.getSelectedItemPosition() == 0) {
            message = getString(R.string.category_not_selected);
            expenseCategory.requestFocus();
        } else if (expensePaymentMode.getSelectedItemPosition() == 0) {
            message = getString(R.string.payment_mode_not_selected);
            expensePaymentMode.requestFocus();
        } else if (expensePaymentStatus.getSelectedItemPosition() == 0) {
            message = getString(R.string.payment_status_not_selected);
            expensePaymentStatus.requestFocus();
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

    private class TransactionSpinnerItem implements CropSpinnerItem {

        String value;

        public TransactionSpinnerItem(String value) {
            this.value = value;
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