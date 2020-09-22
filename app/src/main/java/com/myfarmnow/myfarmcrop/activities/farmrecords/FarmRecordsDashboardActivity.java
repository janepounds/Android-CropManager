package com.myfarmnow.myfarmcrop.activities.farmrecords;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropBillsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropContactManagerActivity;
import com.myfarmnow.myfarmcrop.activities.CropCustomersListActivity;
import com.myfarmnow.myfarmcrop.activities.CropEmployeesListActivity;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropFertilizerCalculatorEntryActivity;
import com.myfarmnow.myfarmcrop.activities.CropInvoicesListActivity;
import com.myfarmnow.myfarmcrop.activities.CropPaymentBillsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropPaymentsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropProductsListActivity;
import com.myfarmnow.myfarmcrop.activities.CropPurchaseOrdersListActivity;
import com.myfarmnow.myfarmcrop.activities.CropSalesOrdersListActivity;
import com.myfarmnow.myfarmcrop.activities.CropSettingsActivity;
import com.myfarmnow.myfarmcrop.activities.CropSuppliersListActivity;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.ActivityFarmRecordsDashboardBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

public class FarmRecordsDashboardActivity extends AppCompatActivity  {
    ActivityFarmRecordsDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_farm_records_dashboard);

        MyFarmDbHandlerSingleton.getHandlerInstance(this).initializeSettings(DashboardActivity.RETRIEVED_USER_ID);


    }



//        imgBack.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
//                Intent imgback = new Intent(FarmRecordsDashboardActivity.this, DashboardActivity.class);
//                startActivity(imgback);
//            }
//        });



    public static  void addDatePicker(final EditText ed_, final Context context){
        ed_.setOnClickListener(view -> {
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

        });
        ed_.setInputType(InputType.TYPE_NULL);
    }


    public void openSettings(View view){
        Intent openSettings = new Intent(this, CropSettingsActivity.class);
        startActivity(openSettings);
        finish();
    }
    public void openEmployeeList(View view){
        Intent openList = new Intent(this, CropEmployeesListActivity.class);
        startActivity(openList);
    }



    public void openFertilizerCalculator(View view){
        //CropFertilizerCalculatorEntryActivity
        Intent openList = new Intent(this, CropFertilizerCalculatorEntryActivity.class);
        startActivity(openList);
    }


    public void openBillsList(View view){
        Intent openList = new Intent(this, CropBillsListActivity.class);
        startActivity(openList);
    }
    public void openBillPaymentsList(View view){
        Intent openList = new Intent(this, CropPaymentBillsListActivity.class);
        startActivity(openList);
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
