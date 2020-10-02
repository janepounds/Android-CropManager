package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords.livestockrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.LivestockSpinnerAdapter;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.MedicationsListAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Litter;
import com.myfarmnow.myfarmcrop.models.livestock_models.LivestockSpinnerItem;
import com.myfarmnow.myfarmcrop.models.livestock_models.Medication;

import java.util.ArrayList;

public class AddMedicationFragment extends DialogFragment {
    private Context context;
    private MyFarmDbHandlerSingleton dbHandler;
    private Medication medication;
    private EditText medicationDate,medicationName,manufacturer,dosage,treatmentPeriod,notes,technicalPersonnel;
    private ImageView close,datePicker;
    private Spinner Animal,HealthCondition,MedicationType;
    private Button submit;
    private LivestockSpinnerAdapter livestockSpinnerAdapter;
    private NavController navController;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomAlertDialog);

        //get arguments for edit
        if(getArguments()!=null){
            medication = (Medication) getArguments().getSerializable("medication");
        }
        View view =getLayoutInflater().inflate(R.layout.fragment_add_medication, null);
        initializeForm(view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);


    }


    public void initializeForm(View view){
        close = view.findViewById(R.id.add_medication_close);
        medicationDate = view.findViewById(R.id.add_medication_date);
        Animal = view.findViewById(R.id.add_medication_animal_sp);
        MedicationType = view.findViewById(R.id.add_medication_type_sp);
        HealthCondition = view.findViewById(R.id.add_medical_health_condition_sp);
        medicationName = view.findViewById(R.id.add_medication_name);
        manufacturer = view.findViewById(R.id.add_medication_manufacturer);
        dosage = view.findViewById(R.id.add_medication_dosage);
        treatmentPeriod = view.findViewById(R.id.add_medication_treatment_period);
        notes = view.findViewById(R.id.add_medication_notes);
        technicalPersonnel = view.findViewById(R.id.add_medication_technical_personnel);
        datePicker = view.findViewById(R.id.image_date_picker);
        submit = view.findViewById(R.id.btn_save);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePickerImageView(datePicker,medicationDate,context);

        ArrayList<LivestockSpinnerItem> Animals = new ArrayList<>();
        for (BreedingStock x : dbHandler.getBreedingStocks(DashboardActivity.RETRIEVED_USER_ID)) {
            Animals.add(new LivestockSpinnerItem() {
                @Override
                public String getId() {
                    return null;
                }

                @NonNull
                @Override
                public String toString() {
                    return x.getName();
                }
            });
        }

        livestockSpinnerAdapter = new LivestockSpinnerAdapter(Animals, "Animal", context);
        Animal.setAdapter(livestockSpinnerAdapter);

        close.setOnClickListener(view1 -> dismiss());

        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }

                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };



        fillViews();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()){
                    if(medication==null){
                        saveMedication();
                    }
                    else{
                        updateMedication();
                    }
                    //dismiss dialog and refresh fragment
                    navController = Navigation.findNavController(getParentFragment().getView());
                    navController.navigate(R.id.action_addMedicationFragment_to_medicationsViewFragment);




                }else{
                    Log.d("ERROR","Testing");
                }
            }
        });
    }
    public void fillViews(){
        if(medication != null){
            medicationDate.setText(medication.getMedicationDate());
            medicationName.setText(medication.getMedicationsName());
            manufacturer.setText(medication.getManufacturer());
            dosage.setText(medication.getDosage() + "");
            notes.setText(medication.getNote());
            technicalPersonnel.setText(medication.getTechnicalPersonal());
            DashboardActivity.selectSpinnerItemByValue(MedicationType,medication.getMedicationType());
            DashboardActivity.selectSpinnerItemByValue(Animal,medication.getAnimal());
            DashboardActivity.selectSpinnerItemByValue(HealthCondition,medication.getHealthCondition());


        }
    }
    public void saveMedication(){
        medication = new Medication();
        medication.setUserId(DashboardActivity.RETRIEVED_USER_ID);
//        medication.setBreedingId(Animal.);
        medication.setMedicationDate(medicationDate.getText().toString());
        medication.setMedicationType(MedicationType.getSelectedItem().toString());
        medication.setAnimal(Animal.getSelectedItem().toString());
        medication.setHealthCondition(HealthCondition.getSelectedItem().toString());
        medication.setMedicationsName(medicationName.getText().toString());
        medication.setManufacturer(manufacturer.getText().toString());
        medication.setDosage(Integer.parseInt(dosage.getText().toString()));
        medication.setTreatmentPeriod(Integer.parseInt(treatmentPeriod.getText().toString()));
        medication.setNote(notes.getText().toString());
        medication.setAnimal(Animal.getSelectedItem().toString());
        medication.setTechnicalPersonal(technicalPersonnel.getText().toString());
        Log.w("Animal",Animal.getSelectedItem().toString());
        dbHandler.insertMedication(medication);



    }
    public void updateMedication(){

        if(medication != null) {
            medication.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            medication.setMedicationDate(medicationDate.getText().toString());
            medication.setMedicationType(MedicationType.getSelectedItem().toString());
            medication.setAnimal(Animal.getSelectedItem().toString());
            medication.setHealthCondition(HealthCondition.getSelectedItem().toString());
            medication.setMedicationsName(medicationName.getText().toString());
//            medication.setBreedingId(((com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock)Animal.getSelectedItem()).getId());
            medication.setManufacturer(manufacturer.getText().toString());
            medication.setDosage(Integer.parseInt(dosage.getText().toString()));
            medication.setTreatmentPeriod(Integer.parseInt(treatmentPeriod.getText().toString()));
            medication.setNote(notes.getText().toString());
            medication.setTechnicalPersonal(technicalPersonnel.getText().toString());
            dbHandler.updateMedication(medication);
        }
    }

    public boolean validateEntries(){
        String message = null;

        if(medicationDate.getText().toString().isEmpty()){
            message = getString(R.string.medication_date_not_entered);
            medicationDate.requestFocus();
        }
        else if(MedicationType.getSelectedItemPosition()==0) {
            message = getString(R.string.medication_type_not_selected);
            MedicationType.requestFocus();
        }

        else if(Animal.getSelectedItemPosition()==0){
            message = getString(R.string.animal_not_selected);
            Animal.requestFocus();
        }else if(HealthCondition.getSelectedItemPosition()==0){
            message = getString(R.string.health_condition_not_selected);
            HealthCondition.requestFocus();
        }
        else if(medicationName.getText().toString().isEmpty()){
            message = getString(R.string.medication_name_not_entered);
            medicationName.requestFocus();
        }else if(manufacturer.getText().toString().isEmpty()){
            message = getString(R.string.manufacturer_not_entered);
            manufacturer.requestFocus();
        }else if(dosage.getText().toString().isEmpty()){
            message = getString(R.string.dosage_not_entered);
            dosage.requestFocus();
        }else if(treatmentPeriod.getText().toString().isEmpty()){
            message = getString(R.string.treatment_period_not_entered);
            treatmentPeriod.requestFocus();
        }else if(notes.getText().toString().isEmpty()){
            message = getString(R.string.notes_not_entered);
            notes.requestFocus();
        }else if(technicalPersonnel.getText().toString().isEmpty()){
            message = getString(R.string.technical_personnel_not_entered);
            technicalPersonnel.requestFocus();
        }

        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}