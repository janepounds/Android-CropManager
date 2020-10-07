package com.myfarmnow.myfarmcrop.fragments.farmrecords.crop;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropActivitiesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.CropSpinnerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentCropActivitiesListBinding;
import com.myfarmnow.myfarmcrop.models.CropActivity;
import com.myfarmnow.myfarmcrop.models.CropFertilizerApplication;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.CropSpraying;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CropActivitiesListFragment extends Fragment {
   private FragmentCropActivitiesListBinding binding;
   private Context context;
   private MyFarmDbHandlerSingleton dbHandler;
   private NavController navController;
    LinearLayoutManager linearLayoutManager;
    CropActivitiesListRecyclerAdapter activitiesListRecyclerAdapter;
    String cropId;
    ArrayList<CropActivity> cropActivities = new ArrayList<>();
    ArrayList<CropActivity> cropInventoryList = new ArrayList();
    ArrayList<CropActivity> cropListBackUp = new ArrayList();
    CropSpinnerAdapter cropSpinnerAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_crop_activities_list,container,false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Production Activities");
        binding.toolbar.setNavigationOnClickListener(view -> navController.popBackStack());
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
        navController = Navigation.findNavController(view);

        if(getArguments()!=null){
            cropId = getArguments().getString("cropId");

        }

    //implementing adapter
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);

        ArrayList<CropFertilizerApplication> cropFertilizerApplications =dbHandler.getCropFertilizerApplications(cropId);

        for(CropFertilizerApplication application :cropFertilizerApplications ){
            cropActivities.add(application);
        }

        ArrayList<CropSpraying> cropSprayings =dbHandler.getCropSprayings(cropId);

        for(CropSpraying spraying :cropSprayings ){
            cropActivities.add(spraying);
        }


        ArrayList<CropHarvest> cropHarvests =dbHandler.getCropHarvests(cropId);

        for(CropHarvest harvest :cropHarvests ){
            cropActivities.add(harvest);
        }

        activitiesListRecyclerAdapter = new CropActivitiesListRecyclerAdapter(cropActivities,context);
        binding.cropActivitiesRecycView.setAdapter(activitiesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        binding.cropActivitiesRecycView.setLayoutManager(linearLayoutManager);

        cropSpinnerAdapter = new CropSpinnerAdapter(new ArrayList<CropSpinnerItem>(),"All Activities",context);
        binding.selectActivitySpinner.setAdapter(cropSpinnerAdapter);
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Fertilizer Application",CropActivity.CROP_ACTIVITY_FERTILIZER_APPLICATION));
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Spraying",CropActivity.CROP_ACTIVITY_SPRAYING));
        cropSpinnerAdapter.add(new CropActivitySpinnerItem("Harvesting",CropActivity.CROP_ACTIVITY_HARVESTING));

        binding.selectActivitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<CropActivity> filteredList = new ArrayList<>();
                if(cropListBackUp.size() ==0 ){
                    // cropArrayList.clear();
                    for(CropActivity x :activitiesListRecyclerAdapter.getCropsList()){
                        cropListBackUp.add(x);
                    }
                }
                if(position !=0){
                    int selection = ((CropActivitySpinnerItem)binding.selectActivitySpinner.getSelectedItem()).getIdentifier();
                    //filteredList.clear();
                    for(CropActivity x :cropListBackUp){

                        if(selection==x.getType()){
                            filteredList.add(x);
//                            Collections.sort(filteredList);
                        }

                    }
                    activitiesListRecyclerAdapter.changeList(filteredList);

                }
                else{
                    for(CropActivity x :cropListBackUp){
                        filteredList.add(x);
                    }
                    activitiesListRecyclerAdapter.changeList(filteredList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private class CropActivitySpinnerItem implements CropSpinnerItem{

        int identifier=0;
        String label;
        public CropActivitySpinnerItem(String label, int identifier){
            this.label =label;
            this.identifier =identifier;
        }
        @Override
        public String getId() {
            return null;
        }

        public int getIdentifier() {
            return identifier;
        }

        @Override
        public String toString() {
            return label;
        }
    }


}