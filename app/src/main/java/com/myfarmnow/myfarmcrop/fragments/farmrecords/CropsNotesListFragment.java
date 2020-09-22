package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.CropsNotesManagerActivity;
import com.myfarmnow.myfarmcrop.adapters.CropsNotesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentCropsNotesListBinding;
import com.myfarmnow.myfarmcrop.models.CropNote;

public class CropsNotesListFragment extends Fragment {
    private Context context;
    private NavController navController;
    private FragmentCropsNotesListBinding binding;
    private CropsNotesListRecyclerAdapter cropsNotesListRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MyFarmDbHandlerSingleton dbHandler;
    private  String cropId =null;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_crops_notes_list, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Notes");
        binding.toolbar.setNavigationOnClickListener(view -> navController.navigate(R.id.action_cropsNotesListFragment_to_cropListFragment));
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        if(getArguments()!=null){
            cropId = getArguments().getString("cropId");
            Log.e("CropID",cropId);
        }
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropsNotesListRecyclerAdapter = new CropsNotesListRecyclerAdapter(context,dbHandler.getCropNotes(cropId, CropNote.IS_FOR_CROP));
        binding.cropsNotesRecycView.setAdapter(cropsNotesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        binding.cropsNotesRecycView.setLayoutManager(linearLayoutManager);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);


    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add_new:
                item.setTitle("Add New");
               //navigate to add notes

                Bundle bundle = new Bundle();
                bundle.putString("cropId",cropId);
                navController.navigate(R.id.action_cropsNotesListFragment_to_addCropNotesFragment,bundle);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}