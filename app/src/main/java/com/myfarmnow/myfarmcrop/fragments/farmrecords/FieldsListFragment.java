package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import com.myfarmnow.myfarmcrop.activities.farmrecords.CropFieldManagerActivity;
import com.myfarmnow.myfarmcrop.adapters.CropFieldsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentCropRecordsBinding;


public class FieldsListFragment extends Fragment {
    private FragmentCropRecordsBinding binding;
    private Context context;
    private CropFieldsListRecyclerAdapter cropFieldsListRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MyFarmDbHandlerSingleton dbHandler;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_fields_list,container,false);



        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);


        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropFieldsListRecyclerAdapter = new CropFieldsListRecyclerAdapter(context,dbHandler.getCropFields(DashboardActivity.getPreferences("userId",context)));
//        binding..setAdapter(cropFieldsListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);

//        binding.recycler.setLayoutManager(linearLayoutManager);



    }
        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new:
                openCropFieldManagerActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropFieldManagerActivity() {
        FragmentTransaction t = this.getFragmentManager().beginTransaction();
        Fragment mFrag = new AddFieldFragment();
        t.replace(R.id.crop_field_layout, mFrag);
        t.commit();


    }

}