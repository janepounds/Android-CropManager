package com.myfarmnow.myfarmcrop.fragments.agronomy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
                FragmentTransaction t = getFragmentManager().beginTransaction();
                Fragment mFrag = new CropPDFViewFragment();
                t.replace(R.id.relative_container, mFrag);
                bundle.putString("fileName","eggplants.pdf");
                bundle.putString("pageTitle","Egg Plants Best Practices");
                t.commit();

            }


    });

        binding.layoutCropDashboardFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                FragmentTransaction t = getFragmentManager().beginTransaction();
                Fragment mFrag = new CropPDFViewFragment();
                t.replace(R.id.relative_container, mFrag);
                bundle.putString("fileName","maize.pdf");
                bundle.putString("pageTitle","Maize Best Practices");
                t.commit();

            }
        });

        binding.layoutCropDashboardCrops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });





    }
//    public void openMaizeBestPractices(View view){
//        Bundle bundle = new Bundle();
//        FragmentTransaction t = this.getFragmentManager().beginTransaction();
//        Fragment mFrag = new CropPDFViewFragment();
//        t.replace(R.id.relative_container, mFrag);
//        bundle.putString("fileName","maize.pdf");
//        bundle.putString("pageTitle","Maize Best Practices");
//        t.commit();
//
//
//
//    }

//    public void openBeansBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","beans.pdf");
//        openPDF.putExtra("pageTitle","Beans Best Practices");
//        startActivity(openPDF);
//    }
//    public void openCabbageBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","cabbage.pdf");
//        openPDF.putExtra("pageTitle","Cabbage Best Practices");
//        startActivity(openPDF);
//    }
//    public void openCarrotBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","carrot.pdf");
//        openPDF.putExtra("pageTitle","Carrot Best Practices");
//        startActivity(openPDF);
//    }
//    public void openEggPlantBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","eggplants.pdf");
//        openPDF.putExtra("pageTitle","Egg Plants Best Practices");
//        startActivity(openPDF);
//    }
//    public void openGroundNutsBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","ground_nuts.pdf");
//        openPDF.putExtra("pageTitle","Ground Nuts Best Practices");
//        startActivity(openPDF);
//    }
//    public void openOnionBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","onions.pdf");
//        openPDF.putExtra("pageTitle","Onions Best Practices");
//        startActivity(openPDF);
//    }
//    public void openPassionFruitBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","passion_fruit.pdf");
//        openPDF.putExtra("pageTitle","Passion Fruit Best Practices");
//        startActivity(openPDF);
//    }
//    public void openPineappleBestPractices(View view){
//        Intent openPDF = new Intent(this,CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","pinneapple.pdf");
//        openPDF.putExtra("pageTitle","Pineapple Best Practices");
//        startActivity(openPDF);
//    }
//    public void openPumpkinBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","pumpkin.pdf");
//        openPDF.putExtra("pageTitle","Pumpkin Best Practices");
//        startActivity(openPDF);
//    }
//    public void openSoyaBeansBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","soya_bean.pdf");
//        openPDF.putExtra("pageTitle","Soya Beans Best Practices");
//        startActivity(openPDF);
//    }
//    public void openTomatoBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","tomato.pdf");
//        openPDF.putExtra("pageTitle","Tomatoes Best Practices");
//        startActivity(openPDF);
//    }
//    public void openWaterMelonBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","water_melon.pdf");
//        openPDF.putExtra("pageTitle","Water Melon Best Practices");
//        startActivity(openPDF);
//    }
//    public void openSorghumBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","sorghum.pdf");
//        openPDF.putExtra("pageTitle","Sorghum Best Practices");
//        startActivity(openPDF);
//    }
//    public void openGarlicBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","garlic.pdf");
//        openPDF.putExtra("pageTitle","Garlic Best Practices");
//        startActivity(openPDF);
//    }
//
//    public void openBananaBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","banana.pdf");
//        openPDF.putExtra("pageTitle","Banana Best Practices");
//        startActivity(openPDF);
//    }
//    public void openCoffeeBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","coffee.pdf");
//        openPDF.putExtra("pageTitle","Coffee Best Practices");
//        startActivity(openPDF);
//    }
//    public void openRiceBestPractices(View view){
//        Intent openPDF = new Intent(this, CropPDFViewActivity.class);
//        openPDF.putExtra("fileName","rice.pdf");
//        openPDF.putExtra("pageTitle","Rice Best Practices");
//        startActivity(openPDF);
//    }


}