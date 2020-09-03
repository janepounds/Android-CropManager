package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentFarmRecordsHomeBinding;

public class FarmRecordsHomeFragment extends Fragment {
    private static final String TAG = "FarmRecordsHomeFragment";
    private Context context;

    private FragmentFarmRecordsHomeBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_farm_records_home,container,false);

        return binding.getRoot();
    }

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
        binding.layoutCropDashboardCrops.setOnClickListener(view1 -> navController.navigate(R.id.action_farmRecordsHomeFragment_to_cropRecordsFragment));
//        binding.layoutInventoryStore.setOnClickListener(view1 -> navController.navigate(R.id.action_farmRecordsHomeFragment_to_livestockRecordsFragment));
        binding.layoutInventoryStore.setOnClickListener(view2 -> navController.navigate(R.id.action_farmRecordsHomeFragment_to_storeFragment));
        binding.layoutDashboardFinancialRecords.setOnClickListener(view3 -> navController.navigate(R.id.action_farmRecordsHomeFragment_to_financialRecordsFragment));
    }
}