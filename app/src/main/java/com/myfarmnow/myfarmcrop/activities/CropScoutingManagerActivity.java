package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropScouting;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.util.ArrayList;

public class CropScoutingManagerActivity extends AppCompatActivity {



    EditText scoutingDateTxt,scoutingMethodTxt,costTxt,remarksTxt,weeksTxt,repeatUntilTxt,daysBeforeTxt;
    TextView currencyTxt;
    Spinner infestedSpinner,infestationTypeSpinner,infestationSpinner,infestationLevelSpinner,recurrenceSp,remindersSp;
    LinearLayout infestationShowHideLayout,weeklyRecurrenceLayout,daysBeforeLayout,remindersLayout;
    Button saveBtn;
    CropSpinnerAdapter infestationAdapter;
    MyFarmDbHandlerSingleton dbHandler;
    CropScouting cropScouting=null;
    String cropId;
    boolean infestationSet = false;

    ArrayList<CropSpinnerItem> diseaseArrayList= new ArrayList<>();
    ArrayList<CropSpinnerItem> nematodeArrayList= new ArrayList<>();
    ArrayList<CropSpinnerItem>  pestArrayList = new ArrayList<>();
    ArrayList<CropSpinnerItem>  rodentArrayList = new ArrayList<>();
    ArrayList<CropSpinnerItem>  weedArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_scouting_manager);
        if(getIntent().hasExtra("cropScouting")){
            cropScouting = (CropScouting) getIntent().getSerializableExtra("cropScouting");
        }
        if(getIntent().hasExtra("cropId")){
            cropId =getIntent().getStringExtra("cropId");
        }
        else{
            finish();
        }
        initializeForm();
    }

    public void initializeForm(){

        scoutingDateTxt = findViewById(R.id.txt_crop_scouting_date);
        scoutingMethodTxt = findViewById(R.id.txt_crop_scouting_method);
        infestedSpinner = findViewById(R.id.sp_crop_scouting_infested);
        infestationTypeSpinner = findViewById(R.id.sp_crop_scouting_infestation_type);
        infestationSpinner = findViewById(R.id.sp_crop_scouting_infestation);
        infestationLevelSpinner = findViewById(R.id.sp_crop_scouting_infestation_level);
        costTxt = findViewById(R.id.txt_crop_scouting_cost);
        currencyTxt = findViewById(R.id.txt_crop_scouting_currency);

        remarksTxt = findViewById(R.id.txt_crop_scouting_remarks);
        infestationShowHideLayout = findViewById(R.id.crop_scouting_show_hide_infestation);
        remindersSp = findViewById(R.id.sp_crop_scouting_reminders);
        recurrenceSp = findViewById(R.id.sp_crop_scouting_recurrence);
        weeksTxt = findViewById(R.id.txt_crop_scouting_weekly_weeks);
        repeatUntilTxt = findViewById(R.id.txt_crop_scouting_repeat_until);
        daysBeforeTxt = findViewById(R.id.txt_crop_scouting_days_before);
        weeklyRecurrenceLayout = findViewById(R.id.layout_crop_scouting_weekly_reminder);
        daysBeforeLayout = findViewById(R.id.layout_crop_scouting_days_before);
        remindersLayout = findViewById(R.id.layout_crop_scouting_reminders);

        infestedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                if(position==1){
                    infestationShowHideLayout.setVisibility(View.VISIBLE);
                }
                else if(position==2){
                    infestationShowHideLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        infestationAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"Infestation",this);
        infestationSpinner.setAdapter(infestationAdapter);
        String disease [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_disease);
        String nematode [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_nematode);
        String pest [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_pest);
        String rodent [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_rodent);
        String weed [] = getResources().getStringArray(R.array.sp_crop_scouting_infestation_weed);

        for(String x: disease){
            diseaseArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        for(String x: nematode){
            nematodeArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        for(String x: pest){
            pestArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        for(String x: rodent){
            rodentArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        for(String x: weed){
            weedArrayList.add(new InfestationTypeSpinnerItem(x));
        }
        infestationTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                if(selection.toLowerCase().equals("disease")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(diseaseArrayList);

                }
                else if(selection.toLowerCase().equals("nematode")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(nematodeArrayList);
                }
                else if(selection.toLowerCase().equals("pest")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(pestArrayList);
                }
                else if(selection.toLowerCase().equals("rodent")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(rodentArrayList);
                }
                else if(selection.toLowerCase().equals("weed")){
                    infestationSpinner.setEnabled(true);
                    infestationAdapter.changeItems(weedArrayList);
                }
                else{
                    infestationSpinner.setEnabled(false);
                }
                if(!infestationSet && cropScouting != null){
                    CropDashboardActivity.selectSpinnerItemById(infestationSpinner, cropScouting.getInfestation());
                    infestationSet =true;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recurrenceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                if(selection.toLowerCase().equals("weekly")){
                    weeklyRecurrenceLayout.setVisibility(View.VISIBLE);
                }
                else{
                    weeklyRecurrenceLayout.setVisibility(View.GONE);

                }
                if(selection.toLowerCase().equals("daily")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }
                else{
                    remindersSp.setSelection(0);
                }

                if(selection.toLowerCase().equals("once")){
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }
                else{
                    remindersSp.setSelection(0);
                }
                if(selection.toLowerCase().equals("monthly")){
                    remindersLayout.setVisibility(View.VISIBLE);
                }
                if(selection.toLowerCase().equals("annually")){
                    remindersLayout.setVisibility(View.VISIBLE);
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
                if(selection.toLowerCase().equals("yes")){
                    daysBeforeLayout.setVisibility(View.VISIBLE);
                }
                else{
                    daysBeforeLayout.setVisibility(View.GONE);

                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        saveBtn = findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        CropDashboardActivity.addDatePicker(scoutingDateTxt,this);
        CropDashboardActivity.addDatePicker(repeatUntilTxt,this);
        currencyTxt.setText(CropSettingsSingleton.getInstance().getCurrency());
        ((ArrayAdapter)infestedSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)infestationTypeSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)infestationSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)infestationLevelSpinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)recurrenceSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)remindersSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
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

        infestationSpinner.setOnItemSelectedListener(onItemSelectedListener);
        infestationLevelSpinner.setOnItemSelectedListener(onItemSelectedListener);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(cropScouting==null){
                        saveScouting();
                    }
                    else{
                        updateScouting();
                    }

                    Intent cropScouting = new Intent(CropScoutingManagerActivity.this, CropActivitiesListActivity.class);
                    cropScouting.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    cropScouting.putExtra("cropId",cropId);
                    startActivity(cropScouting);
                    finish();
                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });


        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
        fillViews();
    }
    public void saveScouting() {
        cropScouting = new CropScouting();
        cropScouting.setUserId(CropDashboardActivity.getPreferences("userId", this));
        cropScouting.setCropId(cropId);
        cropScouting.setDate(scoutingDateTxt.getText().toString());
        cropScouting.setMethod(scoutingMethodTxt.getText().toString());
        cropScouting.setInfested(infestedSpinner.getSelectedItem().toString());
        if(infestedSpinner.getSelectedItemPosition()==1) {
            cropScouting.setInfestationType(infestationTypeSpinner.getSelectedItem().toString());
            cropScouting.setInfestation(infestationSpinner.getSelectedItem().toString());
            cropScouting.setInfestationLevel(infestationLevelSpinner.getSelectedItem().toString());
        }
        cropScouting.setCost(Float.parseFloat(costTxt.getText().toString()));
        cropScouting.setRemarks(remarksTxt.getText().toString());
        cropScouting.setRecurrence(recurrenceSp.getSelectedItem().toString());
        cropScouting.setReminders(remindersSp.getSelectedItem().toString());
        if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
            String weeks = weeksTxt.getText().toString();
            String repeatUntil = repeatUntilTxt.getText().toString();

            cropScouting.setFrequency(Float.parseFloat(weeks));
            cropScouting.setRepeatUntil(repeatUntil);
        }
        if(daysBeforeLayout.getVisibility()==View.VISIBLE){
            String days = daysBeforeTxt.getText().toString();


            cropScouting.setDaysBefore(days);

        }

        dbHandler.insertCropScouting(cropScouting);
    }

    public void updateScouting(){
        if(cropScouting != null){
            cropScouting.setUserId(CropDashboardActivity.getPreferences("userId", this));
            cropScouting.setCropId(cropId);
            cropScouting.setDate(scoutingDateTxt.getText().toString());
            cropScouting.setMethod(scoutingMethodTxt.getText().toString());
            cropScouting.setInfested(infestedSpinner.getSelectedItem().toString());
            if(infestedSpinner.getSelectedItemPosition()==1) {
                cropScouting.setInfestationType(infestationTypeSpinner.getSelectedItem().toString());
                cropScouting.setInfestation(infestationSpinner.getSelectedItem().toString());
                cropScouting.setInfestationLevel(infestationLevelSpinner.getSelectedItem().toString());
            }cropScouting.setCost(Float.parseFloat(costTxt.getText().toString()));
            cropScouting.setRemarks(remarksTxt.getText().toString());
            cropScouting.setRecurrence(recurrenceSp.getSelectedItem().toString());
            cropScouting.setReminders(remindersSp.getSelectedItem().toString());
            if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE){
                String weeks = weeksTxt.getText().toString();
                String repeatUntil = repeatUntilTxt.getText().toString();

                cropScouting.setFrequency(Float.parseFloat(weeks));
                cropScouting.setRepeatUntil(repeatUntil);
            }
            if(daysBeforeLayout.getVisibility()==View.VISIBLE){
                String days = daysBeforeTxt.getText().toString();


                cropScouting.setDaysBefore(days);

            }

            dbHandler.updateCropScouting(cropScouting);
        }
    }
    public void fillViews(){
        if(cropScouting != null){
            CropDashboardActivity.selectSpinnerItemByValue(infestedSpinner, cropScouting.getInfested());
            CropDashboardActivity.selectSpinnerItemByValue(infestationTypeSpinner, cropScouting.getInfestationType());
           // CropDashboardActivity.selectSpinnerItemById(infestationSpinner, cropScouting.getInfestation());
            CropDashboardActivity.selectSpinnerItemByValue(infestationLevelSpinner, cropScouting.getInfestationLevel());
            CropDashboardActivity.selectSpinnerItemByValue(recurrenceSp, cropScouting.getRecurrence());
            CropDashboardActivity.selectSpinnerItemByValue(remindersSp, cropScouting.getReminders());

            scoutingDateTxt.setText(cropScouting.getDate());
            scoutingMethodTxt.setText(cropScouting.getMethod());
            costTxt.setText(cropScouting.getCost()+"");
            remarksTxt.setText(cropScouting.getRemarks());
            weeksTxt.setText(cropScouting.getFrequency()+"");
            repeatUntilTxt.setText(cropScouting.getRepeatUntil());
            daysBeforeTxt.setText(cropScouting.getDaysBefore());

        }

    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateEntries(){
        String message = null;
        if(scoutingDateTxt.getText().toString().isEmpty()){
            message = getString(R.string.scounting_date_not_selected_message);
            scoutingDateTxt.requestFocus();
        }
        else if(costTxt.getText().toString().isEmpty()){
            message = getString(R.string.cost_not_entered);
            costTxt.requestFocus();
        }
        else if(infestedSpinner.getSelectedItemPosition()==0) {
            message = getString(R.string.infested_not_selected);
            infestedSpinner.requestFocus();
        }
        else if(recurrenceSp.getSelectedItemPosition()==0){
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        }
        else if(remindersSp.getSelectedItemPosition()==0){
            message = getString(R.string.reminders_not_selected);
            remindersSp.requestFocus();
        }
        else if(weeklyRecurrenceLayout.getVisibility()==View.VISIBLE && repeatUntilTxt.getText().toString().isEmpty()){
            message = getString(R.string.repeat_until_not_selected);
            repeatUntilTxt.requestFocus();
        }

        if(message != null){
            Toast.makeText(CropScoutingManagerActivity.this, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }





    public class InfestationTypeSpinnerItem implements CropSpinnerItem {

        String value;
        public InfestationTypeSpinnerItem(String  value){
            this.value =value;
        }

        @Override
        public String getId() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }


    }


}
