package com.myfarmnow.myfarmcrop.fragments.farmrecords.liveStockRecords;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.BreedingStockListAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropInventory;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BreedingStockViewFragment extends Fragment {
    private static final String TAG = "BreedingFragment";
    private Context context;
    private BreedingStock breedingStock;
    private BreedingStockListAdapter breedingStockListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private NavController navController;
    public ArrayList<BreedingStock> breedingStockArrayList = new ArrayList();
    public ArrayList<BreedingStock> breedingBackupList = new ArrayList();
    private MyFarmDbHandlerSingleton dbHandler;
    private ArrayList<String> sortedList = new ArrayList<>();
    private EditText searchEdit;
    private ImageView breedingSearch;
    ArrayList<BreedingStock> filteredList = new ArrayList<>();
    private LinearLayout nameSort,breedSort;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breeding_stock_view, container, false);

        toolbar = view.findViewById(R.id.toolbar_breeding_stock_view);
        recyclerView = view.findViewById(R.id.breeding_stock_recyclerView);
        breedingSearch = view.findViewById(R.id.breeding_stock_search);
        searchEdit = view.findViewById(R.id.animal_section_filter);
        nameSort = view.findViewById(R.id.btn_filter_by_name);
        breedSort = view.findViewById(R.id.btn_filter_by_breed);

        setHasOptionsMenu(true);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        //



        //implementing search
        breedingSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String searchText = searchEdit.getText().toString();

                Log.w("filteredList",filteredList.toString());

                filteredList.clear();
                for (BreedingStock x : breedingBackupList) {
                   addToList(searchText.toLowerCase(),x);



                }

                breedingStockListAdapter.changeList(filteredList);
            }
        });


        //sort by name
        nameSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameSort.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Collections.sort(breedingBackupList,BreedingStock.nameComparator);
                breedingStockListAdapter.changeList(breedingBackupList);
            }
        });

        breedSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                breedSort.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Collections.sort(breedingBackupList,BreedingStock.breedComparator);
                breedingStockListAdapter.changeList(breedingBackupList);
            }
        });





        return view;
    }
        public  void addToList(String searchtext,BreedingStock x){
            if (x.getName().toLowerCase().contains(searchtext)) {
                filteredList.add(x);
            }else if(x.getEarTag().toLowerCase().contains(searchtext)) {
                filteredList.add(x);
            }
            else if(x.getBreed().toLowerCase().contains(searchtext)) {
                filteredList.add(x);
            }
            else if(x.getColor().toLowerCase().contains(searchtext)) {
                filteredList.add(x);
            }

        }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        breedingStockListAdapter = new BreedingStockListAdapter(context, breedingStockArrayList);
        recyclerView.setAdapter(breedingStockListAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setNavigationOnClickListener(view1->navController.popBackStack());
        loadBreedingStock();
//        filteredList = breedingStockArrayList;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.breeding_stock_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new_animal:
            navController.navigate(R.id.action_breedingStockViewFragment_to_addBreedingStockFragment);

                return true;
            default:
                navController.navigate(R.id.action_breedingStockViewFragment_to_livestockRecordsFragment);
                return true;
//                return super.onOptionsItemSelected(item);
        }
    }

    private void loadBreedingStock() {
        breedingStockListAdapter.clearBreedingStockList();
        SharedPreferenceHelper preferenceModel = new SharedPreferenceHelper(context);
        breedingStockListAdapter.addList(dbHandler.getBreedingStocks(DashboardActivity.RETRIEVED_USER_ID,preferenceModel.getSelectedAnimal()));

        if (breedingBackupList.size() == 0) {
            // cropArrayList.clear();
            for (BreedingStock x : breedingStockListAdapter.getBreedingStocksList()) {
                breedingBackupList.add(x);
            }
        }
    }
}