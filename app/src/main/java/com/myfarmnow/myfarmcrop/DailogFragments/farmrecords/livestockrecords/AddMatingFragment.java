package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords.livestockrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Mating;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

import java.util.ArrayList;


public class AddMatingFragment extends DialogFragment {


    private Context context;
    private Mating mating;
    private MyFarmDbHandlerSingleton dbHandler;
    NavController navController;
    private EditText matingDate, deliveryAlert, note, gestationPeriod;
    AutoCompleteTextView  femaleName, maleName;
    private Button submit;
    private Spinner method;
    private ImageView close;
    private TextView matingTitle;

    private SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);

        //get arguments for edit
        if (getArguments() != null) {
            mating = (Mating) getArguments().getSerializable("mating");

        }
        View view = getLayoutInflater().inflate(R.layout.fragment_add_mating, null);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        initializeForm(view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initializeForm(View view) {
        matingTitle = view.findViewById(R.id.mating_title);
        close = view.findViewById(R.id.add_mating_close);
        matingDate = view.findViewById(R.id.add_mating_date);
        femaleName = view.findViewById(R.id.add_mating_female_name);
        maleName = view.findViewById(R.id.add_mating_male_name);
        method = view.findViewById(R.id.add_mating_method);
        gestationPeriod = view.findViewById(R.id.add_mating_gestation_period);
        deliveryAlert = view.findViewById(R.id.add_mating_delivery_alert);
        note = view.findViewById(R.id.add_mating_note);
        submit = view.findViewById(R.id.add_mating_submit);
        DashboardActivity.addDatePicker(matingDate, context);

        close.setOnClickListener(view1 -> dismiss());
        ((ArrayAdapter) method.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }

                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {

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
                if (validateEntries()) {
                    if (mating == null) {
                        saveMating();
                    } else {
                        updateMating();
                    }
                    //dismiss dialog and refresh fragment
                    navController = Navigation.findNavController(getParentFragment().getView());
                    navController.navigate(R.id.action_addMatingFragment_to_matingsViewFragment);
                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });

        ArrayList<String> sireList = new ArrayList<>(), damnList = new ArrayList<>();

        SharedPreferenceHelper preferenceModel = new SharedPreferenceHelper(context);

        for (BreedingStock x : dbHandler.getBreedingStockBySex(DashboardActivity.RETRIEVED_USER_ID,preferenceModel.getSelectedAnimal(),"Male")) {
            sireList.add(x.getName());
        }
        for (BreedingStock x : dbHandler.getBreedingStockBySex(DashboardActivity.RETRIEVED_USER_ID,preferenceModel.getSelectedAnimal(),"Female")) {
            damnList.add(x.getName());
        }
        ArrayAdapter<String> animalListAdapter = new ArrayAdapter<String>(context,  android.R.layout.simple_dropdown_item_1line, sireList);
        maleName.setThreshold(1);
        maleName.setAdapter(animalListAdapter);
        maleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                maleName.showDropDown();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, damnList);
        femaleName.setThreshold(1);
        femaleName.setAdapter(adapter);

        femaleName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                femaleName.showDropDown();
            }
        });

        if(preferenceModel.getSelectedAnimal().equalsIgnoreCase("cattle"))
            deliveryAlert.setText("270");
        else if(preferenceModel.getSelectedAnimal().equalsIgnoreCase("rabbit"))
            deliveryAlert.setText("28");
        else if(preferenceModel.getSelectedAnimal().equalsIgnoreCase("pig"))
            deliveryAlert.setText("115");
        else if(preferenceModel.getSelectedAnimal().equalsIgnoreCase("goat") )
            deliveryAlert.setText("150");
        else if(preferenceModel.getSelectedAnimal().equalsIgnoreCase("sheep") )
            deliveryAlert.setText("152");

    }

    public void fillViews() {
        if (mating != null) {
            submit.setText(getString(R.string.update));
            matingTitle.setText(getString(R.string.update_mating));
            matingDate.setText(mating.getMatingDate());
            femaleName.setText(mating.getFemaleName());
            maleName.setText(mating.getMaleName());
            deliveryAlert.setText(mating.getDeliveryAlertDaysBefore() + "");
            note.setText(mating.getNotes());
            gestationPeriod.setText(mating.getGestationPeriod() + "");
            DashboardActivity.selectSpinnerItemByValue(method, mating.getMethod());
        }
    }

    public void saveMating() {

        mating = new Mating();
        mating.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        mating.setMatingDate(matingDate.getText().toString());
        mating.setFemaleName(femaleName.getText().toString());
        mating.setMaleName(maleName.getText().toString());
        if(deliveryAlert.getText().toString()!=null)
            mating.setDeliveryAlertDaysBefore(Float.parseFloat(deliveryAlert.getText().toString()));
        else
            mating.setDeliveryAlertDaysBefore(Float.parseFloat("0"));


        mating.setGestationPeriod(Float.parseFloat(gestationPeriod.getText().toString()));
        mating.setNotes(note.getText().toString());
        mating.setMethod(method.getSelectedItem().toString());
        mating.setAnimalType(sharedPreferenceHelper.getSelectedAnimal());
        dbHandler.insertMating(mating);
    }

    public void updateMating() {
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);

        if (mating != null) {
            mating.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            mating.setMatingDate(matingDate.getText().toString());
            mating.setFemaleName(femaleName.getText().toString());
            mating.setMaleName(maleName.getText().toString());
            mating.setDeliveryAlertDaysBefore(Float.parseFloat(deliveryAlert.getText().toString()));
            mating.setGestationPeriod(Float.parseFloat(gestationPeriod.getText().toString()));
            mating.setNotes(note.getText().toString());
            mating.setMethod(method.getSelectedItem().toString());
            mating.setAnimalType(sharedPreferenceHelper.getSelectedAnimal());
            dbHandler.updateMating(mating);
        }
    }

    public boolean validateEntries() {
        String message = null;
        if (matingDate.getText().toString().isEmpty()) {
            message = getString(R.string.mating_date_not_entered);
            matingDate.requestFocus();
        } else if (femaleName.getText().toString().isEmpty()) {
            message = getString(R.string.female_name_not_entered);
            femaleName.requestFocus();
        } else if (method.getSelectedItemPosition() == 0) {
            message = getString(R.string.method_not_selected);
            method.requestFocus();
        }


        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}