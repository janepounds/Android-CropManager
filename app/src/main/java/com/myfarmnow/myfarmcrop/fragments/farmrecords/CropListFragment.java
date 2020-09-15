package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.os.AsyncTask;
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
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropFieldsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.database.MyFarmRoomDatabase;
import com.myfarmnow.myfarmcrop.databinding.FragmentCropListBinding;
import com.myfarmnow.myfarmcrop.models.CropSpinnerItem;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class CropListFragment extends Fragment {
    private FragmentCropListBinding binding;
    private Context context;
    private CropsListRecyclerAdapter cropListRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    public  MyFarmRoomDatabase myFarmRoomDatabase;
    MyFarmDbHandlerSingleton dbHandler;
    ArrayList<Crop> cropArrayList = new ArrayList();
    NavController navController;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_crop_list,container,false);
        myFarmRoomDatabase= MyFarmRoomDatabase.getInstance(context);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Crops");

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
            }
        });
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

        binding.cropsRecycView.setAdapter(cropListRecyclerAdapter);

        loadCropInventories();




    }

    private void loadCropInventories(){
        cropListRecyclerAdapter.clearCropList();

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
                navController.navigate(R.id.action_cropListFragment_to_addCropFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}