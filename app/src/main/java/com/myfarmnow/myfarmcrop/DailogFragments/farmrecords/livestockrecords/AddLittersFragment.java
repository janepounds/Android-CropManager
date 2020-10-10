package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords.livestockrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.LivestockSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Litter;
import com.myfarmnow.myfarmcrop.models.livestock_models.LivestockSpinnerItem;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

import java.util.ArrayList;

public class AddLittersFragment extends DialogFragment {
    private Context context;
    private NavController navController;
    private Litter litter;
    private MyFarmDbHandlerSingleton dbHandler;
    private EditText litterDob, litterSize, bornAlive, bornDead, noOfMale, noOfFemale, weaning, weaningAlert;
    private ImageView close, datePicker;
    private AutoCompleteTextView motherDam, fatherSire;
    private Button submit;
    public LivestockSpinnerAdapter litterSpinnerAdapter;
    private TextView litterTitle;
    private  ArrayList<CropSpinnerItem>    damnList = new ArrayList<>();
    private String pickedBreedingId;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        if (getArguments() != null) {
            litter = (Litter) getArguments().getSerializable("litter");

        }

        View view = getLayoutInflater().inflate(R.layout.fragment_add_litters, null);
        initializeForm(view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        //get arguments for edit
    }

    public void initializeForm(View view) {
        litterTitle = view.findViewById(R.id.litter_title);
        close = view.findViewById(R.id.add_litter_close);
        litterDob = view.findViewById(R.id.add_litter_dob);
        litterSize = view.findViewById(R.id.add_litter_litters_size);
        bornAlive = view.findViewById(R.id.add_litter_born_alive_et);
        bornDead = view.findViewById(R.id.add_litter_born_dead_et);
        motherDam = view.findViewById(R.id.add_litter_mother_dam_spinner);
        fatherSire = view.findViewById(R.id.add_litter_father_sire_spinner);
        noOfMale = view.findViewById(R.id.add_litter_number_of_males_et);
        noOfFemale = view.findViewById(R.id.add_litter_number_of_females_et);
        weaning = view.findViewById(R.id.add_litter_weaning_et);
        weaningAlert = view.findViewById(R.id.add_litter_weaning_alert_et);
        datePicker = view.findViewById(R.id.image_dob);
        submit = view.findViewById(R.id.btn_save);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        DashboardActivity.addDatePickerImageView(datePicker, litterDob, context);
        DashboardActivity.addDatePicker(litterDob, context);


        ArrayList<String> sireList = new ArrayList<>();


        SharedPreferenceHelper preferenceModel = new SharedPreferenceHelper(context);

        for (BreedingStock x : dbHandler.getBreedingStockBySex(DashboardActivity.RETRIEVED_USER_ID,preferenceModel.getSelectedAnimal(),"Male")) {
            sireList.add(x.getName());
        }
        for (BreedingStock x : dbHandler.getBreedingStockBySex(DashboardActivity.RETRIEVED_USER_ID,preferenceModel.getSelectedAnimal(),"Female")) {

            damnList.add(new CropSpinnerItem() {
                @Override
                public String getId() {
                return  x.getId();
                }

                @Override
                public String toString() {
                    return x.getName();
                }

                @Override
                public String getUnits() {
                    return null;
                }
            });
        }
        ArrayAdapter<String> animalListAdapter = new ArrayAdapter<String>(context,  android.R.layout.simple_dropdown_item_1line, sireList);
        fatherSire.setThreshold(1);
        fatherSire.setAdapter(animalListAdapter);
        fatherSire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fatherSire.showDropDown();
            }
        });


        ArrayAdapter<CropSpinnerItem> adapter = new ArrayAdapter<CropSpinnerItem>(context, android.R.layout.simple_dropdown_item_1line, damnList);
        motherDam.setThreshold(1);
        motherDam.setAdapter(adapter);

        motherDam.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                motherDam.showDropDown();
            }
        });




        close.setOnClickListener(view1 -> dismiss());

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
                    if (litter == null) {
                        saveLitter();
                    } else {
                        updateLitter();
                    }
                    //dismiss dialog and refresh fragment
                    navController = Navigation.findNavController(getParentFragment().getView());
                    navController.navigate(R.id.action_addLittersFragment_to_littersViewFragment);
                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });
    }

    public void fillViews() {
        if (litter != null) {
            submit.setText(getString(R.string.update));
            litterTitle.setText(getString(R.string.update_litter));
            litterDob.setText(litter.getDateOfBirth());
            litterSize.setText(litter.getLitterSize() + "");
            bornAlive.setText(litter.getBornAlive() + "");
            bornDead.setText(litter.getBornDead() + "");
            noOfFemale.setText(litter.getNoOfFemale() + "");
            noOfMale.setText(litter.getNoOfMale() + "");
            weaning.setText(litter.getWeaning() + "");
            weaningAlert.setText(litter.getWeaningAlert() + "");
            motherDam.setText(litter.getMotherDam());
            fatherSire.setText(litter.getFatherSire());

        }
    }

    public void saveLitter() {
        for (int i = 0; i < damnList.size(); i++) {

            if (motherDam.getText().toString().equals(damnList.get(i).toString())) {
                pickedBreedingId = damnList.get(i).getId();

            } else {
                pickedBreedingId = null;

            }


        }
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);

        litter = new Litter();
        litter.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        litter.setDateOfBirth(litterDob.getText().toString());
        litter.setLitterSize(Integer.parseInt(litterSize.getText().toString()));
        litter.setBornAlive(Integer.parseInt(bornAlive.getText().toString()));
        litter.setBornDead(Integer.parseInt(bornDead.getText().toString()));
        litter.setNoOfFemale(Integer.parseInt(noOfFemale.getText().toString()));
        litter.setNoOfMale(Integer.parseInt(noOfMale.getText().toString()));
        if(!weaning.getText().toString().isEmpty())
            litter.setWeaning(Integer.parseInt(weaning.getText().toString()));
        litter.setMotherDam(motherDam.getText().toString());
        litter.setFatherSire(fatherSire.getText().toString());
        if(!weaningAlert.getText().toString().isEmpty())
            litter.setWeaningAlert(Integer.parseInt(weaningAlert.getText().toString()));

        litter.setAnimalType(sharedPreferenceHelper.getSelectedAnimal());
        litter.setBreedingId(pickedBreedingId);
        dbHandler.insertLitter(litter);
    }

    public void updateLitter() {
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);

        if (litter != null) {
            litter.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            litter.setDateOfBirth(litterDob.getText().toString());
            litter.setLitterSize(Integer.parseInt(litterSize.getText().toString()));
            litter.setBornAlive(Integer.parseInt(bornAlive.getText().toString()));
            litter.setBornDead(Integer.parseInt(bornDead.getText().toString()));
            litter.setNoOfFemale(Integer.parseInt(noOfFemale.getText().toString()));
            litter.setNoOfMale(Integer.parseInt(noOfMale.getText().toString()));
            litter.setWeaning(Integer.parseInt(weaning.getText().toString()));
            litter.setMotherDam(motherDam.getText().toString());
            litter.setFatherSire(fatherSire.getText().toString());

            if(!weaningAlert.getText().toString().isEmpty())
                 litter.setWeaningAlert(Integer.parseInt(weaningAlert.getText().toString()));

            litter.setAnimalType(sharedPreferenceHelper.getSelectedAnimal());
            dbHandler.updateLitter(litter);
        }
    }

    public boolean validateEntries() {
        String message = null;
        if (litterDob.getText().toString().isEmpty()) {
            message = getString(R.string.date_not_entered_message);
            litterDob.requestFocus();
        } else if (litterSize.getText().toString().isEmpty()) {
            message = getString(R.string.litter_size_not_entered);
            litterSize.requestFocus();
        } else if (motherDam.getText().toString().isEmpty()) {
            message = getString(R.string.mother_dam_not_selected);
            motherDam.requestFocus();
        } else if (bornAlive.getText().toString().isEmpty()) {
            message = getString(R.string.born_alive_not_entered);
            bornAlive.requestFocus();
        } else if (bornDead.getText().toString().isEmpty()) {
            message = getString(R.string.born_dead_not_entered);
            bornDead.requestFocus();
        } else if (noOfMale.getText().toString().isEmpty()) {
            message = getString(R.string.no_of_male_not_entered);
            noOfMale.requestFocus();
        } else if (noOfFemale.getText().toString().isEmpty()) {
            message = getString(R.string.no_of_female_not_entered);
            noOfFemale.requestFocus();
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}