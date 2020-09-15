package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;


public class FertilizerApplicationFragment extends DialogFragment {
    EditText dateTxt,  operatorTxt,costTxt, rateTxt,reasonTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    TextView currency;
    Button btn_save;
    CropFertilizerApplication fertilizerApplication;
    String cropId;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner methodSp, fertilizerFormSp, fertilizerId,recurrenceSp,remindersSp;
    LinearLayout weeklyRecurrenceLayout,daysBeforeLayout, remindersLayout;
    boolean applicationMethodSet = false;//
    CropSpinnerAdapter applicationMethodAdapter,fertilizerAdapter;
    ArrayList<CropSpinnerItem> solidMethodsArrayList=new ArrayList<CropSpinnerItem>();
    ArrayList<CropSpinnerItem> liquidMethodsArrayList=new ArrayList<CropSpinnerItem>();
    private NavController navController;
    private Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        cropId = getArguments().getString("cropId");
//        Log.w("CropId",cropId);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fertilizer_application, container, false);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.fragment_fertilizer_application, null);
        builder.setView(view);
        initializeForm(view);
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
        dateTxt =view.findViewById(R.id.txt_crop_fertilizer_application_date);
        operatorTxt =view.findViewById(R.id.txt_crop_fertilizer_application_operator);
        costTxt =view.findViewById(R.id.txt_crop_fertilizer_application_labour_cost);
        rateTxt =view.findViewById(R.id.txt_crop_fertilizer_application_rate);
        reasonTxt =view.findViewById(R.id.txt_crop_fertilizer_application_reason);
        fertilizerId =view.findViewById(R.id.sp_crop_fertilizer_application_fertilizername);
        methodSp =view.findViewById(R.id.sp_crop_fertilizer_application_method);
        fertilizerFormSp =view.findViewById(R.id.sp_crop_fertilizer_application_fertilizer_form);
        remindersSp = view.findViewById(R.id.sp_crop_fertilizer_application_reminders);
        recurrenceSp = view.findViewById(R.id.sp_crop_fertilizer_application_recurrence);
        weeksTxt = view.findViewById(R.id.txt_crop_fertilizer_application_weekly_weeks);
        currency = view.findViewById(R.id.txt_crop_fertilizer_application_currency);
        repeatUntilTxt = view.findViewById(R.id.txt_crop_fertilizer_application_repeat_until);
        daysBeforeTxt = view.findViewById(R.id.txt_crop_fertilizer_application_days_before);
        weeklyRecurrenceLayout = view.findViewById(R.id.layout_crop_fertilizer_application_weekly_reminder);
        daysBeforeLayout = view.findViewById(R.id.layout_crop_fertilizer_application_days_before);
        remindersLayout = view.findViewById(R.id.layout_crop_fertilizer_application_reminders);

        applicationMethodAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Method",context);
//        methodSp.setAdapter(applicationMethodAdapter);
//        methodSp.setEnabled(false);
        btn_save = view.findViewById(R.id.btn_save);
        DashboardActivity.addDatePicker(dateTxt,context);
//        DashboardActivity.addDatePicker(repeatUntilTxt,context);

        String liquidApplicationMethods [] = getResources().getStringArray(R.array.crop_fertilizer_application_method_liquid);
        String solidApplicationMethods [] = getResources().getStringArray(R.array.crop_fertilizer_application_method_solid);

        for(String x: liquidApplicationMethods){
            liquidMethodsArrayList.add(new FertilizerFormSpinnerItem(x));
        }
        for(String x: solidApplicationMethods){
            solidMethodsArrayList.add(new FertilizerFormSpinnerItem(x));
        }

//        fertilizerFormSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                try{
//                    if(position==0){
//                        ((TextView) view).setTextColor(Color.GRAY);
//                    }
//                   else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
//
//                    }
//                    else {
//                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
//                    }
//                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
//                }catch (Exception e){
//
//                }
//                String selection = parent.getItemAtPosition(position).toString();
//                if(selection.toLowerCase().equals("solid")){
//                    methodSp.setEnabled(true);
//                    applicationMethodAdapter.changeItems(solidMethodsArrayList);
//                }
//                else if(selection.toLowerCase().equals("liquid")){
//                    methodSp.setEnabled(true);
//                    applicationMethodAdapter.changeItems(liquidMethodsArrayList);
//                }
//                else{
//                    methodSp.setEnabled(false);
//                }
//
//                if(!applicationMethodSet && fertilizerApplication != null){
//                    DashboardActivity.selectSpinnerItemById(methodSp, fertilizerApplication.getMethod());
//                    applicationMethodSet =true;
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        recurrenceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position==0){
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
                if(selection.toLowerCase().equals("weekly")){

                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }

                if(selection.toLowerCase().equals("daily")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }

                if(selection.toLowerCase().equals("once")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }


                if(selection.toLowerCase().equals("monthly")){
                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }


                if(selection.toLowerCase().equals("annually")){
                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        remindersSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                try{
//                    if(position==0){
//                        ((TextView) view).setTextColor(Color.GRAY);
//                    }
//                   else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
//
//                    }
//                    else {
//                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
//                    }
//                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
//                }catch (Exception e){
//
//                }
//                String selection = parent.getItemAtPosition(position).toString();
//                if(selection.toLowerCase().equals("yes")){
//                    daysBeforeLayout.setVisibility(View.VISIBLE);
//                    if(recurrenceSp.getSelectedItem().equals("Weekly")){
//                        weeklyRecurrenceLayout.setVisibility(View.VISIBLE);
//                    }
//
//                }
//                else{
//                    daysBeforeLayout.setVisibility(View.GONE);
//                    weeklyRecurrenceLayout.setVisibility(View.GONE);
//                }
//
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(fertilizerApplication==null){
                        saveFertilizerApplication();
                    }
                    else{
                        updateFertilizerApplication();
                    }
                    navController = Navigation.findNavController(v);
                    //navigate to crop activities
                    Bundle bundle = new Bundle();
                    bundle.putString("cropId",cropId);
                   navController.navigate(R.id.action_fertilizerApplicationFragment_to_cropActivitiesListFragment,bundle);
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });


        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
//        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
//        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
//        ((ArrayAdapter)fertilizerFormSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        ArrayList<CropSpinnerItem> fertlizersList = new ArrayList<>();
        for(CropInventoryFertilizer x: dbHandler.getCropFertilizerInventorys(DashboardActivity.getPreferences("userId",context))){
            fertlizersList.add(x);
        }
        fertilizerAdapter  =new CropSpinnerAdapter(fertlizersList,"Fertilizer",context);
        fertilizerId.setAdapter(fertilizerAdapter);

//        currency.setText(CropSettingsSingleton.getInstance().getCurrency());


        fillViews();
    }

    public void saveFertilizerApplication(){
        fertilizerApplication = new CropFertilizerApplication();
        fertilizerApplication.setUserId(DashboardActivity.getPreferences("userId",context));
        fertilizerApplication.setDate(dateTxt.getText().toString());
        fertilizerApplication.setCropId(cropId);
//        fertilizerApplication.setReason(reasonTxt.getText().toString());
//        fertilizerApplication.setFertilizerForm(fertilizerFormSp.getSelectedItem().toString());
//        fertilizerApplication.setMethod((methodSp.getSelectedItem()).toString());
        fertilizerApplication.setFertilizerId(((CropSpinnerItem) fertilizerId.getSelectedItem()).getId());
        fertilizerApplication.setRecurrence(recurrenceSp.getSelectedItem().toString());
        fertilizerApplication.setReminders(remindersSp.getSelectedItem().toString());
//        fertilizerApplication.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
//        fertilizerApplication.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));



        dbHandler.insertCropFertilizerApplication(fertilizerApplication);

    }
    public void updateFertilizerApplication(){
        if(fertilizerApplication != null){
            fertilizerApplication.setDate(dateTxt.getText().toString());
            fertilizerApplication.setRate(Float.parseFloat(rateTxt.getText().toString()));
            fertilizerApplication.setCropId(cropId);
            fertilizerApplication.setCost(Float.parseFloat(costTxt.getText().toString()));
            fertilizerApplication.setOperator(operatorTxt.getText().toString());
            fertilizerApplication.setReason(reasonTxt.getText().toString());
            fertilizerApplication.setFertilizerForm(fertilizerFormSp.getSelectedItem().toString());
            fertilizerApplication.setMethod((methodSp.getSelectedItem()).toString());
            fertilizerApplication.setFertilizerId(((CropSpinnerItem) fertilizerId.getSelectedItem()).getId());
            fertilizerApplication.setRecurrence(recurrenceSp.getSelectedItem().toString());
            fertilizerApplication.setReminders(remindersSp.getSelectedItem().toString());
            fertilizerApplication.setRepeatUntil(repeatUntilTxt.getText().toString());
            fertilizerApplication.setDaysBefore(Float.parseFloat(daysBeforeTxt.getText().toString()));
            fertilizerApplication.setFrequency(Float.parseFloat(weeksTxt.getText().toString()));


            dbHandler.updateCropFertilizerApplication(fertilizerApplication);

        }
    }
    public void fillViews(){
        if(fertilizerApplication != null){
            DashboardActivity.selectSpinnerItemByValue(fertilizerFormSp, fertilizerApplication.getFertilizerForm());
            DashboardActivity.selectSpinnerItemByValue(recurrenceSp, fertilizerApplication.getRecurrence());
            DashboardActivity.selectSpinnerItemByValue(remindersSp, fertilizerApplication.getReminders());
            DashboardActivity.selectSpinnerItemById(fertilizerId, fertilizerApplication.getFertilizerName());

            rateTxt.setText(fertilizerApplication.getRate()+"");
            dateTxt.setText(fertilizerApplication.getDate());
            reasonTxt.setText(fertilizerApplication.getReason());
            operatorTxt.setText(fertilizerApplication.getOperator());
            costTxt.setText(fertilizerApplication.getCost()+"");
            rateTxt.setText(fertilizerApplication.getRate()+"");
            weeksTxt.setText(fertilizerApplication.getFrequency()+"");
            repeatUntilTxt.setText(fertilizerApplication.getRepeatUntil());
            daysBeforeTxt.setText(fertilizerApplication.getDaysBefore()+"");

            DashboardActivity.selectSpinnerItemById(fertilizerId,fertilizerApplication.getFertilizerId());
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(dateTxt.getText().toString().isEmpty()){
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        }


        else if(fertilizerId.getSelectedItemPosition()==0){
            message = getString(R.string.fertilizer_name_not_entered);
            fertilizerId.requestFocus();
        }
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }
        else if(remindersSp.getSelectedItemPosition()==0){
            message = getString(R.string.reminders_not_selected);
            remindersSp.requestFocus();
        }
//        else if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE && repeatUntilTxt.getText().toString().isEmpty()){
//            message = getString(R.string.repeat_until_not_selected);
//            repeatUntilTxt.requestFocus();
//        }
        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

    private class FertilizerFormSpinnerItem implements CropSpinnerItem{

        String value;
        FertilizerFormSpinnerItem(String value){
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