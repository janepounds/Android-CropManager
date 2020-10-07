package com.myfarmnow.myfarmcrop.fragments.agronomy;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.myfarmnow.myfarmcrop.R;

public class CropsFragment extends Fragment {
    private Context context;

    private Toolbar toolbar;
    private LinearLayout medicationCount1, layoutCropDashboardFields, layoutCropDashboardCrops, layoutCropDashboardInventory, layoutCropDashboardMachines,
            layoutCropDashboardTasks, layoutDashboardOnions, layoutDashboardPassion, layoutDashboardPineapple, layoutCropDashboardIncomeExpense,
            layoutCropDashboardWeatherForecast, layoutDashboardTomato, layoutDashboardWatermelon, layoutDashboardSorghum, layoutDashboardGarlic,
            layoutDashboardBanana, layoutDashboardCoffee, layoutDashboardRice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crops, container, false);

        toolbar = view.findViewById(R.id.toolbar_crops_fragment);
        medicationCount1 = view.findViewById(R.id.medication_count1);
        layoutCropDashboardFields = view.findViewById(R.id.layout_crop_dashboard_fields);
        layoutCropDashboardCrops = view.findViewById(R.id.layout_crop_dashboard_crops);
        layoutCropDashboardInventory = view.findViewById(R.id.layout_crop_dashboard_inventory);
        layoutCropDashboardMachines = view.findViewById(R.id.layout_crop_dashboard_machines);
        layoutCropDashboardTasks = view.findViewById(R.id.layout_crop_dashboard_tasks);
        layoutDashboardOnions = view.findViewById(R.id.layout_dashboard_onions);
        layoutDashboardPassion = view.findViewById(R.id.layout_dashboard_passion);
        layoutDashboardPineapple = view.findViewById(R.id.layout_dashboard_pineapple);
        layoutCropDashboardIncomeExpense = view.findViewById(R.id.layout_crop_dashboard_income_expense);
        layoutCropDashboardWeatherForecast = view.findViewById(R.id.layout_crop_dashboard_weather_forecast);
        layoutDashboardTomato = view.findViewById(R.id.layout_dashboard_tomato);
        layoutDashboardWatermelon = view.findViewById(R.id.layout_dashboard_watermelon);
        layoutDashboardSorghum = view.findViewById(R.id.layout_dashboard_sorghum);
        layoutDashboardGarlic = view.findViewById(R.id.layout_dashboard_garlic);
        layoutDashboardBanana = view.findViewById(R.id.layout_dashboard_banana);
        layoutDashboardCoffee = view.findViewById(R.id.layout_dashboard_coffee);
        layoutDashboardRice = view.findViewById(R.id.layout_dashboard_rice);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return view;
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
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        medicationCount1.setOnClickListener(view13 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "eggplants.pdf");
            bundle.putString("pageTitle", "Egg Plants Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutCropDashboardFields.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "maize.pdf");
            bundle.putString("pageTitle", "Maize Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutCropDashboardCrops.setOnClickListener(view12 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "beans.pdf");
            bundle.putString("pageTitle", "Beans Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutCropDashboardInventory.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "cabbage.pdf");
            bundle.putString("pageTitle", "Cabbage Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutCropDashboardMachines.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "carrot.pdf");
            bundle.putString("pageTitle", "Carrot Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutCropDashboardTasks.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "ground_nuts.pdf");
            bundle.putString("pageTitle", "Ground Nuts Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardOnions.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "onions.pdf");
            bundle.putString("pageTitle", "Onions Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardPassion.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "passion.pdf");
            bundle.putString("pageTitle", "Passion Fruit Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardPineapple.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "pineapple.pdf");
            bundle.putString("pageTitle", "Pineapple Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutCropDashboardIncomeExpense.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "pumpkin.pdf");
            bundle.putString("pageTitle", "Pumpkin Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutCropDashboardWeatherForecast.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "soya_bean.pdf");
            bundle.putString("pageTitle", "Soya Beans Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardTomato.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "tomato.pdf");
            bundle.putString("pageTitle", "Tomato Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardWatermelon.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "water_melon.pdf");
            bundle.putString("pageTitle", "Water Melon Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardSorghum.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "sorghum.pdf");
            bundle.putString("pageTitle", "Sorghum Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardGarlic.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "garlic.pdf");
            bundle.putString("pageTitle", "Garlic Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardBanana.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "banana.pdf");
            bundle.putString("pageTitle", "Banana Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardCoffee.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "coffee.pdf");
            bundle.putString("pageTitle", "Coffee Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });

        layoutDashboardRice.setOnClickListener(view1 -> {
            Bundle bundle = new Bundle();
            bundle.putString("fileName", "rice.pdf");
            bundle.putString("pageTitle", "Rice Best Practices");
            navController.navigate(R.id.action_cropsFragment_to_cropPDFViewFragment, bundle);
        });
    }
}