package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropContact;
import com.myfarmnow.myfarmcrop.models.CropEmployee;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;


public class CropHarvestFragment extends DialogFragment {
    String cropId;
    CropHarvest cropHarvest=null;
    EditText harvestDateTxt,quantityTxt,dateSoldTxt,customerTxt,
            quantitySoldTxt,storageDateTxt,quantityStoredTxt, priceTxt;

    TextView quantityStoredUnitsTxt,pricePerUnitTxt,quantitySoldUnitsTxt,incomeGeneratedTxt,currency2Txt,harvestunitTxt,daysBeforeTxt;
    Spinner harvestUnitsSpinner,statusSpinner,recurrenceSp,remindersSp;
    ImageView harvestClose,datePicker;

    LinearLayout harvestSoldLayout,harvestStoredLayout,remindersLayout;

    Button saveBtn;
    MyFarmDbHandlerSingleton dbHandler;
    ArrayAdapter<String> employeesSpinnerAdapter;
    private NavController navController;
    private Context context;


    public CropHarvestFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crop_harvest, container, false);
    }


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.fragment_crop_harvest, null);
        builder.setView(view);
        if(getArguments()!=null){
            cropHarvest = (CropHarvest)getArguments().getSerializable("cropHarvest");
            cropId = getArguments().getString("cropId");
        }
        initializeForm(view);
        remindersLayout.setVisibility(View.INVISIBLE);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }

    public void initializeForm(View view){
        harvestDateTxt = view.findViewById(R.id.txt_crop_harvest_date);
        harvestUnitsSpinner = view.findViewById(R.id.sp_crop_harvest_units);
        quantityTxt = view.findViewById(R.id.txt_crop_harvest_quantity);
        statusSpinner = view.findViewById(R.id.sp_crop_harvest_status);
        dateSoldTxt = view.findViewById(R.id.txt_crop_harvest_date_sold);
        customerTxt = view.findViewById(R.id.txt_crop_harvest_customer);
        quantitySoldTxt = view.findViewById(R.id.txt_crop_harvest_quantity_sold);
        storageDateTxt = view.findViewById(R.id.txt_crop_harvest_storage_date);
        quantityStoredTxt = view.findViewById(R.id.txt_crop_harvest_quantity_stored);
        currency2Txt = view.findViewById(R.id.txt_crop_harvest_currency_2);
        harvestSoldLayout=view.findViewById(R.id.layout_crop_harvest_sold);
        harvestStoredLayout=view.findViewById(R.id.layout_crop_harvest_stored);
        quantityStoredUnitsTxt=view.findViewById(R.id.txt_crop_harvest_stored_unit);
        pricePerUnitTxt=view.findViewById(R.id.txt_crop_harvest_price_per_unit);
        quantitySoldUnitsTxt=view.findViewById(R.id.txt_crop_harvest_sold_unit);
        recurrenceSp = view.findViewById(R.id.sp_crop_harvest_recurrence);
        remindersSp = view.findViewById(R.id.sp_crop_harvest_reminders);
        harvestunitTxt = view.findViewById(R.id.txt_crop_harvest_unit);
        harvestClose = view.findViewById(R.id.harvest_close);
        datePicker = view.findViewById(R.id.image_date_picker);
        daysBeforeTxt = view.findViewById(R.id.txt_harvest_days_before);
        remindersLayout = view.findViewById(R.id.layout_crop_harvest_reminders);
        priceTxt = view.findViewById(R.id.txt_crops_estimated_revenue);
//        operatorSpinner = view.findViewById(R.id.sp_crop_harvest_operator);
//        costTxt = view.findViewById(R.id.txt_crop_harvest_cost);
//        currencyTxt = view.findViewById(R.id.txt_crop_harvest_currency);
//        harvestMethodTxt = view.findViewById(R.id.txt_crop_harvest_method);
//        weeksTxt = view.findViewById(R.id.txt_crop_harvest_weekly_weeks);
//        repeatUntilTxt = view.findViewById(R.id.txt_crop_harvest_repeat_until);
//        weeklyRecurrenceLayout = view.findViewById(R.id.layout_crop_harvest_weekly_reminder);
//        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());
//        currency2Txt.setText(CropSettingsSingleton.getInstance().getCurrency());
        harvestClose.setOnClickListener(view1 -> dismiss());
        DashboardActivity.addDatePicker(harvestDateTxt,context);

        datePicker.setOnClickListener(v ->   DashboardActivity.addDatePicker(harvestDateTxt,context));

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }

                if(position==1){
                    harvestSoldLayout.setVisibility(View.VISIBLE);
                }
                else if(position==2){
                    harvestSoldLayout.setVisibility(View.GONE);
                }

                if(position==2){
                    harvestStoredLayout.setVisibility(View.VISIBLE);
                }
                else if(position==1){
                    harvestStoredLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        harvestUnitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                if(selection.toLowerCase().equals("boxes")){
                    quantityStoredUnitsTxt.setText("Boxes");
                    quantitySoldUnitsTxt.setText("Boxes");
                    pricePerUnitTxt.setText("/ Box");
                    harvestunitTxt.setText("boxes");


                }
                else if(selection.toLowerCase().equals("kg")){

                    quantityStoredUnitsTxt.setText("Kg");
                    quantitySoldUnitsTxt.setText("Kg");
                    pricePerUnitTxt.setText("/ Kg");
                    harvestunitTxt.setText("kg");

                }
                else if(selection.toLowerCase().equals("tonnes")){
                    quantityStoredUnitsTxt.setText("Tonnes");
                    quantitySoldUnitsTxt.setText("Tonnes");
                    pricePerUnitTxt.setText("/ Tonne");
                    harvestunitTxt.setText("tonnes");



                }
                else if(selection.toLowerCase().equals("bushels")){

                    quantityStoredUnitsTxt.setText("Bushels");
                    quantitySoldUnitsTxt.setText("Bushels");
                    pricePerUnitTxt.setText("/ Bushel");
                    harvestunitTxt.setText("bushels");


                }
                else if(selection.toLowerCase().equals("bags")){

                    quantityStoredUnitsTxt.setText("Bags");
                    quantitySoldUnitsTxt.setText("Bags");
                    pricePerUnitTxt.setText("/ Bag");
                    harvestunitTxt.setText("bags");


                }
                else if(selection.toLowerCase().equals("bunches")){
                    quantityStoredUnitsTxt.setText("Bunches");
                    quantitySoldUnitsTxt.setText("Bunches");
                    pricePerUnitTxt.setText("/ Bunch");
                    harvestunitTxt.setText("bunch");


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                  else  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
//        operatorSpinner.setOnItemSelectedListener(onItemSelectedListener);

        saveBtn = view.findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePicker(harvestDateTxt,context);
        DashboardActivity.addDatePicker(dateSoldTxt,context);
        DashboardActivity.addDatePicker(storageDateTxt,context);
//        DashboardActivity.addDatePicker(repeatUntilTxt,context);


        ArrayList<String> employeesItems = new ArrayList<>();
        for(CropEmployee x: dbHandler.getCropEmployees(DashboardActivity.RETRIEVED_USER_ID)){
            employeesItems.add(x.getFullName());
        }
        for(CropContact x: dbHandler.getCropContacts(DashboardActivity.RETRIEVED_USER_ID)){
            employeesItems.add(x.getName());
        }
        employeesSpinnerAdapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line, employeesItems);;
//        operatorSpinner.setAdapter(employeesSpinnerAdapter);

        ((ArrayAdapter) harvestUnitsSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        //   ((ArrayAdapter)operatorSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) statusSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropHarvest==null){
                        Log.w("quantity sold",quantitySoldTxt.getText().toString());
                        saveHarvest();
                    }
                    else{
                        updateHarvest();
                    }

                    navController = Navigation.findNavController(getParentFragment().getView());
                    //open crop activities list
                    Bundle bundle = new Bundle();
                    bundle.putString("cropId",cropId);
                    navController.navigate(R.id.action_cropHarvestFragment_to_cropActivitiesListFragment,bundle);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
        TextWatcher watcher =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                computeIncomeGenerated();
            }

            @Override
            public void afterTextChanged(Editable s) {
                computeIncomeGenerated();
            }
        };


//        priceTxt.addTextChangedListener(watcher);
        quantitySoldTxt.addTextChangedListener(watcher);

        recurrenceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position ==0){
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

                if(selection.toLowerCase().equals("once") || selection.toLowerCase().equals("daily") ){
                    remindersLayout.setVisibility(View.GONE);
                }else {
                    remindersLayout.setVisibility(View.VISIBLE);
                    daysBeforeTxt.setVisibility(View.GONE);
                }



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        remindersSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }
                  else  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }

                String selection = parent.getItemAtPosition(position).toString();

                if(selection.toLowerCase().equals("yes") ){
                    daysBeforeTxt.setVisibility(View.VISIBLE);
                }else {
                    daysBeforeTxt.setVisibility(View.GONE);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        fillViews();
    }

    public void saveHarvest() {

        cropHarvest = new CropHarvest();
        cropHarvest.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        cropHarvest.setCropId(cropId);
        cropHarvest.setDate(harvestDateTxt.getText().toString());
        cropHarvest.setUnits(harvestUnitsSpinner.getSelectedItem().toString());
        cropHarvest.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
//        cropHarvest.setOperator(operatorSpinner.getText().toString());
        cropHarvest.setStatus(statusSpinner.getSelectedItem().toString());
        cropHarvest.setDateSold(dateSoldTxt.getText().toString());
        cropHarvest.setCustomer(customerTxt.getText().toString());
        cropHarvest.setStorageDate(storageDateTxt.getText().toString());
        cropHarvest.setUnits(quantityStoredUnitsTxt.getText().toString());
        cropHarvest.setUnits("/ "+pricePerUnitTxt.getText().toString());
        cropHarvest.setUnits(quantitySoldUnitsTxt.getText().toString());
        cropHarvest.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropHarvest.setReminders(remindersSp.getSelectedItem().toString());
//        cropHarvest.setMethod(harvestMethodTxt.getText().toString());
        cropHarvest.setPrice(Float.parseFloat(priceTxt.getText().toString()));
//        cropHarvest.setCost(Float.parseFloat(costTxt.getText().toString()));
//        cropHarvest.setRepeatUntil(repeatUntilTxt.getText().toString());
        cropHarvest.setDaysBefore(Float.parseFloat("0"+daysBeforeTxt.getText().toString()));
//        cropHarvest.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));
        //check if quantity sold and stored is empty
        if(quantitySoldTxt.getText().toString().isEmpty()){
            cropHarvest.setQuantitySold(0);
        }
        else{
            cropHarvest.setQuantitySold(Float.parseFloat(quantitySoldTxt.getText().toString()));
        }
        if(quantityStoredTxt.getText().toString().isEmpty()){
            cropHarvest.setQuantityStored(0);
        }
        else{

            cropHarvest.setQuantityStored(Float.parseFloat(quantityStoredTxt.getText().toString()));
        }

        dbHandler.insertCropHarvest(cropHarvest);
    }

    public void updateHarvest(){
        if(cropHarvest != null){
            cropHarvest.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            cropHarvest.setCropId(cropId);
            cropHarvest.setDate(harvestDateTxt.getText().toString());
            cropHarvest.setUnits(harvestUnitsSpinner.getSelectedItem().toString());
            cropHarvest.setQuantity(Float.parseFloat(quantityTxt.getText().toString()));
            cropHarvest.setStatus(statusSpinner.getSelectedItem().toString());
            cropHarvest.setDateSold(dateSoldTxt.getText().toString());
            cropHarvest.setCustomer(customerTxt.getText().toString());
            cropHarvest.setQuantitySold(Float.parseFloat(quantitySoldTxt.getText().toString()));
            cropHarvest.setStorageDate(storageDateTxt.getText().toString());
            cropHarvest.setQuantityStored(Float.parseFloat(quantityStoredTxt.getText().toString()));
            cropHarvest.setUnits(quantityStoredUnitsTxt.getText().toString());
//            cropHarvest.setUnits("/ "+pricePerUnitTxt.getText().toString());
//            cropHarvest.setUnits(quantitySoldUnitsTxt.getText().toString());
            cropHarvest.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropHarvest.setReminders(remindersSp.getSelectedItem().toString());
//            cropHarvest.setOperator(operatorSpinner.getText().toString());
//            cropHarvest.setCost(Float.parseFloat(costTxt.getText().toString()));
            cropHarvest.setPrice(Float.parseFloat(priceTxt.getText().toString()));
//            cropHarvest.setMethod(harvestMethodTxt.getText().toString());
//            cropHarvest.setRepeatUntil(repeatUntilTxt.getText().toString());
//            cropHarvest.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
//            cropHarvest.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));
            dbHandler.updateCropHarvest(cropHarvest);
        }
    }

    public void fillViews(){
        if(cropHarvest != null){
            DashboardActivity.selectSpinnerItemByValue(harvestUnitsSpinner, cropHarvest.getUnits());
            DashboardActivity.selectSpinnerItemByValue(statusSpinner, cropHarvest.getStatus());
            DashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropHarvest.getRecurrence());
            DashboardActivity.selectSpinnerItemByValue(remindersSp, cropHarvest.getReminders());
            harvestDateTxt.setText(cropHarvest.getDate());
            quantityTxt.setText(cropHarvest.getQuantity()+"");
            dateSoldTxt.setText(cropHarvest.getDateSold());
            customerTxt.setText(cropHarvest.getCustomer());
            quantitySoldTxt.setText(cropHarvest.getQuantitySold()+"");
            storageDateTxt.setText(cropHarvest.getStorageDate());
            quantityStoredTxt.setText(cropHarvest.getQuantityStored()+"");
            quantityStoredUnitsTxt.setText(cropHarvest.getUnits());
            pricePerUnitTxt.setText(cropHarvest.getUnits());
            quantitySoldUnitsTxt.setText(cropHarvest.getUnits());
//            costTxt.setText(cropHarvest.getCost()+"");
//            priceTxt.setText(cropHarvest.getPrice()+"");
//            operatorSpinner.setText(cropHarvest.getEmployeeId());
//            harvestMethodTxt.setText(cropHarvest.getMethod());
//            weeksTxt.setText(cropHarvest.getFrequency()+"");
//            repeatUntilTxt.setText(cropHarvest.getRepeatUntil());
//            daysBeforeTxt.setText(cropHarvest.getDaysBefore()+"");

        }

    }

    public float computeIncomeGenerated(){
        try{
            float price = Float.parseFloat(pricePerUnitTxt.getText().toString());
            float quantitySold = Float.parseFloat(quantitySoldTxt.getText().toString());
            float incomeGenerated = (price*quantitySold);
            incomeGeneratedTxt.setText(incomeGenerated+"");
            return incomeGenerated;
        }catch (Exception e){

        }
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(harvestDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.harvest_date_not_selected_message);
            harvestDateTxt.requestFocus();
        }
        else if(quantityTxt.getText().toString().isEmpty()){
            message = getString(R.string.quantity_not_entered);
            quantityTxt.requestFocus();
        }
        else if(harvestUnitsSpinner.getSelectedItemPosition()==0) {
            message = getString(R.string.units_not_selected);
            harvestUnitsSpinner.requestFocus();
        }
        else if(statusSpinner.getSelectedItemPosition()==0) {
            message = getString(R.string.status_not_selected);
            statusSpinner.requestFocus();
        }
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }

        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

}