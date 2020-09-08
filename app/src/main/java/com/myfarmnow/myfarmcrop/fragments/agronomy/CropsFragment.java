package com.myfarmnow.myfarmcrop.fragments.agronomy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.agronomy.CropPDFViewActivity;
import com.myfarmnow.myfarmcrop.databinding.FragmentCropsBinding;


public class CropsFragment extends Fragment {
    private FragmentCropsBinding binding;
    private Context context;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_crops,container,false);
        Toolbar toolbar = binding.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


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
        binding.medicationcount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("fileName","eggplants.pdf");
                bundle.putString("pageTitle","Egg Plants Best Practices");
                navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

            }


    });

        binding.layoutCropDashboardFields.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","maize.pdf");
            bundle.putString("pageTitle","Maize Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });

        binding.layoutCropDashboardCrops.setOnClickListener(view12 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","beans.pdf");
            bundle.putString("pageTitle","Beans Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutCropDashboardInventory.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","cabbage.pdf");
            bundle.putString("pageTitle","Cabbage Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);


        });
        binding.layoutCropDashboardMachines.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","carrot.pdf");
            bundle.putString("pageTitle","Carrot Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);
        });
        binding.layoutCropDashboardTasks.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","ground_nuts.pdf");
            bundle.putString("pageTitle","Ground Nuts Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutDashboardOnions.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","onions.pdf");
            bundle.putString("pageTitle","Onions Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutDashboardPassion.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","passion.pdf");
            bundle.putString("pageTitle","Passion Fruit Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layutDashboardPineapple.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","pineapple.pdf");
            bundle.putString("pageTitle","Pineapple Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutCropDashboardIncomeExpense.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","pumpkin.pdf");
            bundle.putString("pageTitle","Pumpkin Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutCropDashboardWeatherForecast.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","soya_bean.pdf");
            bundle.putString("pageTitle","Soya Beans Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutDashoardTomato.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","tomato.pdf");
            bundle.putString("pageTitle","Tomato Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutDashboardWatermelon.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","water_melon.pdf");
            bundle.putString("pageTitle","Water Melon Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutDashboardSorghum.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","sorghum.pdf");
            bundle.putString("pageTitle","Sorghum Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutDashboardGarlic.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","garlic.pdf");
            bundle.putString("pageTitle","Garlic Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutDashboardBanana.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","banana.pdf");
            bundle.putString("pageTitle","Banana Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutDashboardCoffee.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","coffee.pdf");
            bundle.putString("pageTitle","Coffee Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });
        binding.layoutDashboardRice.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName","rice.pdf");
            bundle.putString("pageTitle","Rice Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment,bundle);

        });





    }



}