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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

import java.util.ArrayList;


public class FertilizerApplicationFragment extends DialogFragment {
    private static final String TAG = "FertilizerApplication";
    EditText dateTxt, rateTxt, daysBeforeTxt;
    TextView rateUnitsTextView;
    Button btn_save;
    CropFertilizerApplication fertilizerApplication;
    String cropId;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner unitsSp, fertilizerFormSp, recurrenceSp, remindersSp;
    AutoCompleteTextView fertilizerName;
    LinearLayout daysBeforeLayout, remindersLayout;
    boolean applicationMethodSet = false;//
    CropSpinnerAdapter applicationMethodAdapter;
    ArrayList<CropSpinnerItem> solidMethodsArrayList = new ArrayList<CropSpinnerItem>();
    ArrayList<CropSpinnerItem> liquidMethodsArrayList = new ArrayList<CropSpinnerItem>();
    private NavController navController;
    ImageView fertilizerApplicationClose,datePicker;
    private Context context;
    private String pickedId = null;
    private String pickedName;
    private ArrayList<CropSpinnerItem> fertlizersList = new ArrayList<>();


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.fragment_fertilizer_application, null);
        builder.setView(view);
        if (getArguments() != null) {
            cropId = getArguments().getString("cropId");
            fertilizerApplication = (CropFertilizerApplication) getArguments().getSerializable("fertilizerApplication");
        }

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        initializeForm(view);

        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public void initializeForm(View view) {
        dateTxt = view.findViewById(R.id.txt_crop_fertilizer_application_date);
        fertilizerName = view.findViewById(R.id.sp_crop_fertilizer_application_fertilizername);
        fertilizerFormSp = view.findViewById(R.id.sp_crop_fertilizer_application_fertilizer_form);
        remindersSp = view.findViewById(R.id.sp_crop_fertilizer_application_reminders);
        recurrenceSp = view.findViewById(R.id.sp_crop_fertilizer_application_recurrence);
        daysBeforeTxt = view.findViewById(R.id.txt_crop_fertilizer_application_days_before);
        rateTxt = view.findViewById(R.id.txt_crop_fertilizer_qty);
        daysBeforeLayout = view.findViewById(R.id.layout_crop_fertilizer_application_days_before);
        remindersLayout = view.findViewById(R.id.layout_crop_fertilizer_application_reminders);
        datePicker = view.findViewById(R.id.image_date_picker);
        unitsSp= view.findViewById(R.id.spinner_crop_fertilizer_application_usage_unit);
        rateUnitsTextView= view.findViewById(R.id.txt_crop_fertilizer_unit);
        applicationMethodAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(), "Method", context);
        btn_save = view.findViewById(R.id.btn_save);
        fertilizerApplicationClose = view.findViewById(R.id.crop_fertilizer_application);
        DashboardActivity.addDatePicker(dateTxt, context);
        DashboardActivity.addDatePickerImageView(datePicker,dateTxt,context);
        String liquidApplicationMethods[] = getResources().getStringArray(R.array.crop_fertilizer_application_method_liquid);
        String solidApplicationMethods[] = getResources().getStringArray(R.array.crop_fertilizer_application_method_solid);

        for (String x : liquidApplicationMethods) {
            liquidMethodsArrayList.add(new FertilizerFormSpinnerItem(x));
        }
        for (String x : solidApplicationMethods) {
            solidMethodsArrayList.add(new FertilizerFormSpinnerItem(x));
        }


        recurrenceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {
                        ((TextView) view).setTextColor(Color.GRAY);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    } else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {

                }
                String selection = parent.getItemAtPosition(position).toString();
                if (selection.toLowerCase().equals("weekly")) {

                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }

                if (selection.toLowerCase().equals("daily")) {
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }

                if (selection.toLowerCase().equals("once")) {
                    remindersLayout.setVisibility(View.GONE);
                    remindersSp.setSelection(2);
                    daysBeforeLayout.setVisibility(View.GONE);
                }


                if (selection.toLowerCase().equals("monthly")) {
                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }


                if (selection.toLowerCase().equals("annually")) {
                    remindersLayout.setVisibility(View.VISIBLE);
                    remindersSp.setSelection(0);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        remindersSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {
                        ((TextView) view).setTextColor(Color.GRAY);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    } else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {

                }
                String selection = parent.getItemAtPosition(position).toString();
                if (selection.toLowerCase().equals("yes")) {
                    daysBeforeLayout.setVisibility(View.VISIBLE);

                } else {
                    daysBeforeLayout.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fertilizerApplicationClose.setOnClickListener(v -> getDialog().dismiss());
        if (fertilizerApplication != null)
            btn_save.setText(getString(R.string.update));

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEntries()) {
                    if (fertilizerApplication == null) {
                        saveFertilizerApplication();
                    } else {
                        updateFertilizerApplication();
                    }
                    navController = Navigation.findNavController(getParentFragment().getView());
                    //navigate to crop activities
                    Bundle bundle = new Bundle();
                    bundle.putString("cropId", cropId);
                    navController.navigate(R.id.action_fertilizerApplicationFragment_to_cropActivitiesListFragment, bundle);
                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });


        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);

        //set fertilizer name


        for (CropInventoryFertilizer x : dbHandler.getCropFertilizerInventorys(DashboardActivity.RETRIEVED_USER_ID)) {

            fertlizersList.add(new CropSpinnerItem() {

                @Override
                public String getId() {
                    return x.getId();
                }

                @NonNull
                @Override
                public String toString() {
                    return x.getName();
                }
                @Override
                public String getUnits(){
                    return x.getUsageUnits();
                }
            });
        }


        ArrayAdapter<CropSpinnerItem> fertilizerAdapter = new ArrayAdapter<CropSpinnerItem>(context, android.R.layout.simple_dropdown_item_1line, fertlizersList);
        fertilizerName.setThreshold(1);
        fertilizerName.setAdapter(fertilizerAdapter);
        Log.d(TAG, String.valueOf(fertlizersList.size()));

        fertilizerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fertilizerName.showDropDown();
            }
        });

        fertilizerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(fertlizersList.get(position).getUnits()!= null){

                    DashboardActivity.selectSpinnerItemByValue(unitsSp, fertlizersList.get(position).getUnits());

                    if(!fertlizersList.get(position).getUnits().equalsIgnoreCase("Select Unit"))
                    rateUnitsTextView.setText(fertlizersList.get(position).getUnits()+"");
                    //sprayType=inventorySpray.getType();
                }
            }
        });
        unitsSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    if (position == 0) {
                        ((TextView) view).setTextColor(Color.GRAY);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));
                    } else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String selection = parent.getItemAtPosition(position).toString();
                    if(!selection.equalsIgnoreCase("Select Unit"))
                        rateUnitsTextView.setText(selection);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        fillViews();
    }



    public void saveFertilizerApplication() {
        for (int i = 0; i < fertlizersList.size(); i++) {

            if (fertilizerName.getText().toString().equals(fertlizersList.get(i).toString())) {
                pickedId = fertlizersList.get(i).getId();

            } else {
                pickedId = null;

            }


        }
        fertilizerApplication = new CropFertilizerApplication();
        fertilizerApplication.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        fertilizerApplication.setDate(dateTxt.getText().toString());
        fertilizerApplication.setCropId(cropId);
        fertilizerApplication.setFertilizerForm(fertilizerFormSp.getSelectedItem().toString());
        fertilizerApplication.setFertilizerId(pickedId);
        fertilizerApplication.setFertilizerName(fertilizerName.getText().toString());
        fertilizerApplication.setRecurrence(recurrenceSp.getSelectedItem().toString());
        fertilizerApplication.setReminders(remindersSp.getSelectedItem().toString());
        fertilizerApplication.setDaysBefore(Float.parseFloat("0" + daysBeforeTxt.getText().toString()));
        fertilizerApplication.setRate(Float.parseFloat("0" + rateTxt.getText().toString()));
        fertilizerApplication.setUnits(unitsSp.getSelectedItem().toString());
        dbHandler.insertCropFertilizerApplication(fertilizerApplication);

    }

    public void updateFertilizerApplication() {
        if (fertilizerApplication != null) {
            for (int i = 0; i < fertlizersList.size(); i++) {

                if (fertilizerName.getText().toString().equals(fertlizersList.get(i).toString())) {
                    pickedId = fertlizersList.get(i).getId();

                } else {
                    pickedId = null;

                }

            }
            fertilizerApplication.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            fertilizerApplication.setDate(dateTxt.getText().toString());
            fertilizerApplication.setCropId(cropId);
            fertilizerApplication.setFertilizerForm(fertilizerFormSp.getSelectedItem().toString());
            fertilizerApplication.setFertilizerId(pickedId);
            fertilizerApplication.setFertilizerName(fertilizerName.getText().toString());
            fertilizerApplication.setRecurrence(recurrenceSp.getSelectedItem().toString());
            fertilizerApplication.setReminders(remindersSp.getSelectedItem().toString());
            fertilizerApplication.setDaysBefore(Float.parseFloat("0" + daysBeforeTxt.getText().toString()));
            fertilizerApplication.setRate(Float.parseFloat("0" + rateTxt.getText().toString()));
            fertilizerApplication.setUnits(unitsSp.getSelectedItem().toString());
            dbHandler.updateCropFertilizerApplication(fertilizerApplication);

        }
    }

    public void fillViews() {
        if (fertilizerApplication != null) {
            DashboardActivity.selectSpinnerItemByValue(fertilizerFormSp, fertilizerApplication.getFertilizerForm());
            DashboardActivity.selectSpinnerItemByValue(recurrenceSp, fertilizerApplication.getRecurrence());
            DashboardActivity.selectSpinnerItemByValue(remindersSp, fertilizerApplication.getReminders());
            dateTxt.setText(fertilizerApplication.getDate());
            rateTxt.setText("0" + fertilizerApplication.getRate());
            daysBeforeTxt.setText(fertilizerApplication.getDaysBefore() + "");
            fertilizerName.setText(fertilizerApplication.getFertilizerName());

        }
    }

    public boolean validateEntries() {
        String message = null;
        if (dateTxt.getText().toString().isEmpty()) {
            message = getString(R.string.date_not_entered_message);
            dateTxt.requestFocus();
        } else if (fertilizerName.getText().toString().isEmpty()) {
            message = getString(R.string.fertilizer_name_not_entered);
            fertilizerName.requestFocus();
        } else if (recurrenceSp.getSelectedItemPosition() == 0) {
            message = getString(R.string.recurrence_not_selected);
            recurrenceSp.requestFocus();
        } else if (remindersSp.getSelectedItemPosition() == 0) {
            message = getString(R.string.reminders_not_selected);
            remindersSp.requestFocus();
        }
        else if (unitsSp.getSelectedItemPosition() == 0) {
            message = getString(R.string.usage_units_not_selected);
            unitsSp.requestFocus();
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }

    private class FertilizerFormSpinnerItem implements CropSpinnerItem {

        String value;

        FertilizerFormSpinnerItem(String value) {
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
        @Override
        public String getUnits(){
            return  null;
        }
    }

}