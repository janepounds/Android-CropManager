package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropEmployeesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropEmployeesListActivity extends AppCompatActivity {

    RecyclerView employeesListRecyclerView;
    CropEmployeesListRecyclerAdapter cropEmployeesListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_employees_list);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);

        employeesListRecyclerView = findViewById(R.id.crop_employee_recyc_view);
        cropEmployeesListRecyclerAdapter = new CropEmployeesListRecyclerAdapter(this,dbHandler.getCropEmployees(DashboardActivity.getPreferences("userId",this)));
        employeesListRecyclerView.setAdapter(cropEmployeesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        employeesListRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new:
                openCropEmployeeManagerActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropEmployeeManagerActivity() {
        Intent intent = new Intent(this, CropEmployeeManagerActivity.class);
        startActivity(intent);
    }


}
