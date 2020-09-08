package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.os.Build;
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

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropInventoryListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentStoreBinding;
import com.myfarmnow.myfarmcrop.models.CropInventory;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;

import java.util.ArrayList;


public class StoreFragment extends Fragment {
    private FragmentStoreBinding binding;
    private Context context;
    public ArrayList<CropInventory> cropInventoryList = new ArrayList();
    ArrayList<CropInventory> cropListBackUp = new ArrayList();
    MyFarmDbHandlerSingleton dbHandler;
    CropInventoryListRecyclerAdapter cropListRecyclerAdapter;
    NavController navController;


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store, container, false);
        setHasOptionsMenu(true);

        Toolbar toolbar = binding.toolbar;
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        binding.inventoryRecycView.setLayoutManager(linearLayoutManager);
        cropListRecyclerAdapter = new CropInventoryListRecyclerAdapter(cropInventoryList, context);
        binding.inventoryRecycView.setAdapter(cropListRecyclerAdapter);
        loadCropInventories();
        binding.selectInventorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary));

                    } else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {

                }
                if (position != 0) {
                    String selection = parent.getSelectedItem().toString();
                    ArrayList<CropInventory> filteredList = new ArrayList<>();
                    if (cropListBackUp.size() == 0) {
                        // cropArrayList.clear();
                        for (CropInventory x : cropListRecyclerAdapter.getInventoryList()) {
                            cropListBackUp.add(x);
                        }
                    }

                    //filteredList.clear();
                    for (CropInventory x : cropListBackUp) {
                        if (selection.toLowerCase().contains(x.getInventoryType().toLowerCase()) || position == 1) {
                            filteredList.add(x);
                        }

                    }

                    cropListRecyclerAdapter.changeList(filteredList);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return binding.getRoot();
    }

    public void loadCropInventories() {
        AsyncHttpClient client = new AsyncHttpClient();
        for (CropInventorySeeds seedsInventory : dbHandler.getCropSeeds(DashboardActivity.getPreferences("userId", context))) {
            cropListRecyclerAdapter.addInventory(seedsInventory);
        }
        for (CropInventoryFertilizer fertilizerInventory : dbHandler.getCropFertilizerInventorys(DashboardActivity.getPreferences("userId", context))) {
            cropListRecyclerAdapter.addInventory(fertilizerInventory);
        }
        for (CropInventorySpray spraysInventory : dbHandler.getCropSpray(DashboardActivity.getPreferences("userId", context))) {
            cropListRecyclerAdapter.addInventory(spraysInventory);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.crop_menu_inventory, menu);

        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            switch (id) {


                case R.id.storeAddFertilizerFragment:
                   navController.navigate(R.id.action_storeFragment_to_storeAddFertilizerFragment);

                    return true;

                case R.id.storeAddSeedFragment:

                    navController.navigate(R.id.action_storeFragment_to_storeAddSeedFragment);
                    return true;
                case R.id.storeAddSprayFragment:
                navController.navigate(R.id.action_storeFragment_to_storeAddSprayFragment);

                default:
                    return super.onOptionsItemSelected(item);
            }
    }
}


