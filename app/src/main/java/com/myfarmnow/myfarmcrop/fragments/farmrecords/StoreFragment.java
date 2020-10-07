package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropInventoryListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventory;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;

import java.util.ArrayList;

public class StoreFragment extends Fragment {
    private Context context;
    public ArrayList<CropInventory> cropInventoryList = new ArrayList();
    ArrayList<CropInventory> cropListBackUp = new ArrayList();
    MyFarmDbHandlerSingleton dbHandler;
    CropInventoryListRecyclerAdapter cropListRecyclerAdapter;
    NavController navController;
    StoreFragment storeFragment = null;

    private Toolbar toolbar;
    private Spinner selectInventorySpinner;
    private RecyclerView inventoryRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.toolbar_store_fragment);
        selectInventorySpinner = view.findViewById(R.id.select_inventory_spinner);
        inventoryRecyclerView = view.findViewById(R.id.inventory_recyclerview);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        inventoryRecyclerView.setLayoutManager(linearLayoutManager);
        cropListRecyclerAdapter = new CropInventoryListRecyclerAdapter(cropInventoryList, context);

        inventoryRecyclerView.setAdapter(cropListRecyclerAdapter);

        loadCropInventories();

        selectInventorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        setHasOptionsMenu(true);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Store");

        toolbar.setNavigationOnClickListener(v -> navController.navigate(R.id.action_storeFragment_to_farmRecordsHomeFragment));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void loadCropInventories() {
        for (CropInventorySeeds seedsInventory : dbHandler.getCropSeeds(DashboardActivity.RETRIEVED_USER_ID)) {
            cropListRecyclerAdapter.addInventory(seedsInventory);
        }
        for (CropInventoryFertilizer fertilizerInventory : dbHandler.getCropFertilizerInventorys(DashboardActivity.RETRIEVED_USER_ID)) {
            cropListRecyclerAdapter.addInventory(fertilizerInventory);
        }
        for (CropInventorySpray spraysInventory : dbHandler.getCropSpray(DashboardActivity.RETRIEVED_USER_ID)) {
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
            case R.id.home:
                navController.navigate(R.id.action_storeFragment_to_farmRecordsHomeFragment);
                return true;

            case R.id.storeAddFertilizerFragment:
                navController.navigate(R.id.action_storeFragment_to_storeAddFertilizerFragment);
                return true;

            case R.id.storeAddSeedFragment:
                navController.navigate(R.id.action_storeFragment_to_storeAddSeedFragment);
                return true;

            case R.id.storeAddSprayFragment:
                navController.navigate(R.id.action_storeFragment_to_storeAddSprayFragment);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        storeFragment = this;
    }
}


