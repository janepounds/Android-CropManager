package com.myfarmnow.myfarmcrop.activities.predictiontools;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.ActivityPredictiontoolsDashboardBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class PredictionToolsDashboardActivity extends AppCompatActivity  {
    private ActivityPredictiontoolsDashboardBinding binding;
    Toolbar toolbar;


    LinearLayout nutrientslossLinearLayout,fertilizercompositionLinearLayout,revenue_estimation_linearestimate,
             yieldestimationLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_predictiontools_dashboard);

        MyFarmDbHandlerSingleton.getHandlerInstance(this).initializeSettings(getPreferences("userId",this));
//        initializeDashboard();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


//    public void initializeDashboard(){
//
//        revenue_estimation_linearestimate =findViewById(R.id.layout_dashboard_revenue_estimation);
//        yieldestimationLinearLayout =findViewById(R.id.layout_dashboard_yieldestimation);
//        nutrientslossLinearLayout =findViewById(R.id.layout_dashboard_nutrientsloss);
//
//        fertilizercompositionLinearLayout =findViewById(R.id.layout_dashboard_fertilizercomposition);
//
//
//        revenue_estimation_linearestimate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openInventory = new Intent(PredictionToolsDashboardActivity.this, CropROIStep1Activity.class);
//                startActivity(openInventory);
//            }
//        });
//
//        nutrientslossLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openInventory = new Intent(PredictionToolsDashboardActivity.this, CropNutrientsCalculatorEntryActivity.class);
//                startActivity(openInventory);
//            }
//        });
//
//        yieldestimationLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openIncomeExpense = new Intent(PredictionToolsDashboardActivity.this, CropYieldPerformanceActivity.class);
//                startActivity(openIncomeExpense);
//            }
//        });
//
//        fertilizercompositionLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openfertilizercalculator = new Intent(PredictionToolsDashboardActivity.this, CropFertilizerCalculatorEntryActivity.class);
//                startActivity(openfertilizercalculator);
//            }
//        });
//
//
//
//
//    }

    public static  void addDatePicker(final EditText ed_, final Context context){
        ed_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        int month = selectedmonth + 1;
                        NumberFormat formatter = new DecimalFormat("00");
                        ed_.setText( selectedyear+ "-" + formatter.format(month) + "-" +formatter.format(selectedday) );
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();

            }
        });
        ed_.setInputType(InputType.TYPE_NULL);
    }


    public static String getPreferences(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref",
                0);
        return sharedPreferences.getString(key, "");

    }

    public static void selectSpinnerItemByValue(Spinner spnr, String value) {

        ArrayAdapter<String> adapter = (ArrayAdapter) spnr.getAdapter();
        //((TextView) spnr.getChildAt(0)).setTextColor(Color.BLUE);
        if(value==null){
            return;
        }

        for (int position = 0; position < adapter.getCount(); position++) {

            String item = adapter.getItem(position);
            if(item.toLowerCase().equals(value.toLowerCase())){
                spnr.setSelection(position);
                return;
            }

        }
    }

    public static void selectSpinnerItemById(Spinner spnr, String id) {

        CropSpinnerAdapter adapter = (CropSpinnerAdapter) spnr.getAdapter();
        if(id==null || adapter==null){
            return;
        }

        for (int position = 0; position < adapter.getCount(); position++) {
            String item =adapter.getItem(position).getId();
            if(item==null){
                continue;//this occurs for the first element
            }

            if(item.toLowerCase().equals(id.toLowerCase())){

                spnr.setSelection(position);
                return;
            }

        }
    }

}
