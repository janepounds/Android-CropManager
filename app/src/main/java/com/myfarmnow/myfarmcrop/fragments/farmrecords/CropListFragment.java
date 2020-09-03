package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.myfarmnow.myfarmcrop.activities.farmrecords.CropsListActivity;
import com.myfarmnow.myfarmcrop.activities.farmrecords.CropsManagerActivity;
import com.myfarmnow.myfarmcrop.adapters.CropFieldsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.CropsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentCropListBinding;
import com.myfarmnow.myfarmcrop.databinding.FragmentCropRecordsBinding;
import com.myfarmnow.myfarmcrop.models.Crop;

import java.util.ArrayList;


public class CropListFragment extends Fragment {
    private FragmentCropListBinding binding;
    private Context context;
    private CropsListRecyclerAdapter cropListRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    ArrayList<Crop> cropArrayList = new ArrayList();
    NavController navController;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_crop_list,container,false);
        setHasOptionsMenu(true);

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
         navController = Navigation.findNavController(view);


        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropListRecyclerAdapter = new CropsListRecyclerAdapter(cropArrayList,context);

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        binding.cropsRecycView.setLayoutManager(linearLayoutManager);
        loadCropInventories();

//        ropFieldsListRecyclerAdapter = new CropFieldsListRecyclerAdapter(context,dbHandler.getCropFields(DashboardActivity.getPreferences("userId",context)));
        binding.cropsRecycView.setAdapter(cropListRecyclerAdapter);






    }

    private void loadCropInventories(){

        cropListRecyclerAdapter.addList(dbHandler.getCrops(DashboardActivity.getPreferences("userId",context)));

    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){

        inflater.inflate(R.menu.crop_list_activitys_menu, menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        // Log.d("ADD NEW SELECTED",item.getItemId()+" "+R.id.action_add_new);

        switch (item.getItemId()) {

            case R.id.action_add_new:
                openCropsManagerActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropsManagerActivity() {
       //load crop form

    }
}