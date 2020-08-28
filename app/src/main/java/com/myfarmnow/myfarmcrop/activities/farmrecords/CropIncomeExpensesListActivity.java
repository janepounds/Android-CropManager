package com.myfarmnow.myfarmcrop.activities.farmrecords;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropIncomeExpensesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropIncomeExpensesListActivity extends AppCompatActivity {
    RecyclerView incomeExpensesListRecyclerView;
    CropIncomeExpensesListRecyclerAdapter cropIncomeExpensesListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_income_expenses_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        incomeExpensesListRecyclerView = findViewById(R.id.crop_income_expense_recyc_view);
        cropIncomeExpensesListRecyclerAdapter = new CropIncomeExpensesListRecyclerAdapter(this,dbHandler.getCropIncomeExpenses(DashboardActivity.getPreferences("userId",this)));
        incomeExpensesListRecyclerView.setAdapter(cropIncomeExpensesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        incomeExpensesListRecyclerView.setLayoutManager(linearLayoutManager);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new:
                openCropIncome_ExpenseManagerActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropIncome_ExpenseManagerActivity() {
        Intent intent = new Intent(this, CropIncomeExpenseManagerActivity.class);
        startActivity(intent);
    }

}
