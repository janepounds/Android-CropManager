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
import androidx.cardview.widget.CardView;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropFieldsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropInventoryListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.BreedingStockListAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BreedingStockViewFragment extends Fragment {
    private static final String TAG = "BreedingFragment";
    private Context context;
    private BreedingStock breedingStock;
    private BreedingStockListAdapter breedingStockListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Toolbar toolbar;
    // private Button addAnimal;
    private RecyclerView recyclerView;
    private NavController navController;

    // image picker code
    private static final int IMAGE_PICK_CODE = 0;
    //permission code
    private static final int PERMISSION_CODE = 1;

    private Dialog dialog;
    private Uri produceImageUri = null;
    private Bitmap produceImageBitmap = null;
    private String encodedImage;
    private ImageView close, datePicker;
    private Button submit;
    private EditText name, earTag, colour, breed, weight, father, mother, dateOfBirth;
    private Spinner sex, source;
    private TextView photo;
    public ArrayList<BreedingStock> breedingStockArrayList = new ArrayList();

    private MyFarmDbHandlerSingleton dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breeding_stock_view, container, false);

        toolbar = view.findViewById(R.id.toolbar_breeding_stock_view);
        // addAnimal = view.findViewById(R.id.add_animal);
        recyclerView = view.findViewById(R.id.breeding_stock_recyclerView);
        setHasOptionsMenu(true);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        return view;
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
        //  addAnimal.setOnClickListener(v -> addAnimal());
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
                return super.onOptionsItemSelected(item);
        }

    }




    private void loadBreedingStock() {
        breedingStockListAdapter.clearBreedingStockList();

        breedingStockListAdapter.addList(dbHandler.getBreedingStocks(DashboardActivity.RETRIEVED_USER_ID));
    }


}