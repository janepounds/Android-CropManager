package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.farmrecords.CropIncomeExpenseManagerActivity;
import com.myfarmnow.myfarmcrop.adapters.CropIncomeExpensesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentFinancialRecordsBinding;


public class FinancialRecordsFragment extends Fragment {
    private FragmentFinancialRecordsBinding binding;
    private Context context;
    private CropIncomeExpensesListRecyclerAdapter cropIncomeExpensesListRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MyFarmDbHandlerSingleton dbHandler;




    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_financial_records, container, false);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);

        cropIncomeExpensesListRecyclerAdapter = new CropIncomeExpensesListRecyclerAdapter(context,dbHandler.getCropIncomeExpenses(DashboardActivity.getPreferences("userId",context)));
        binding.cropIncomeExpenseRecycView.setAdapter(cropIncomeExpensesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        binding.cropIncomeExpenseRecycView.setLayoutManager(linearLayoutManager);


        return binding.getRoot();
    }

    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);

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
    //load financial records form

    }

}