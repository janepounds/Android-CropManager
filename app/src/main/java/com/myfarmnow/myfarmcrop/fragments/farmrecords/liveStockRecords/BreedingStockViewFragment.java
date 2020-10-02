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
import com.myfarmnow.myfarmcrop.databinding.FragmentMyProduceBinding;
import com.myfarmnow.myfarmcrop.fragments.marketplace.MyProduceFragment;
import com.myfarmnow.myfarmcrop.models.CropInventory;
import com.myfarmnow.myfarmcrop.models.CropInventorySeeds;
import com.myfarmnow.myfarmcrop.models.CropInventorySpray;
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
    private  NavController navController;

    // image picker code
    private static final int IMAGE_PICK_CODE = 0;
    //permission code
    private static final int PERMISSION_CODE = 1;

    private Dialog dialog;
    private Uri produceImageUri = null;
    private Bitmap produceImageBitmap = null;
    private String encodedImage;
    private ImageView close,datePicker;
    private Button submit;
    private EditText name,earTag,colour,breed,weight,father,mother,dateOfBirth;
    private Spinner sex,source;
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
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        breedingStockListAdapter = new BreedingStockListAdapter(context, breedingStockArrayList);
        recyclerView.setAdapter(breedingStockListAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        loadBreedingStock();
      //  addAnimal.setOnClickListener(v -> addAnimal());
    }

    public void addAnimal(Context cntx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(cntx, R.style.CustomAlertDialog);

        View addAnimalDialog = getLayoutInflater().inflate(R.layout.add_animal_dialog, null);

         close = addAnimalDialog.findViewById(R.id.add_animal_close);
         name = addAnimalDialog.findViewById(R.id.add_animal_name);
         earTag = addAnimalDialog.findViewById(R.id.add_animal_eartag);
         colour = addAnimalDialog.findViewById(R.id.add_animal_colour);
         sex = addAnimalDialog.findViewById(R.id.add_animal_sex);
         breed = addAnimalDialog.findViewById(R.id.add_animal_breed);
         dateOfBirth = addAnimalDialog.findViewById(R.id.add_animal_dob);
         source = addAnimalDialog.findViewById(R.id.add_animal_source);
         weight = addAnimalDialog.findViewById(R.id.add_animal_weight);
         father = addAnimalDialog.findViewById(R.id.add_animal_father);
         mother = addAnimalDialog.findViewById(R.id.add_animal_mother);
         photo = addAnimalDialog.findViewById(R.id.add_animal_photo);
         submit = addAnimalDialog.findViewById(R.id.add_animal_submit);
        datePicker = addAnimalDialog.findViewById(R.id.image_dob);
        ((ArrayAdapter)sex.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter)source.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        DashboardActivity.addDatePickerImageView(datePicker,dateOfBirth,context);

        close.setOnClickListener(view -> dialog.dismiss());
        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position == 0){
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }

                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        fillViews();

        photo.setOnClickListener(v -> {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission granted
                    chooseImage();
                }
            } else {
                //version is less than marshmallow
                chooseImage();
            }
        });

        submit.setOnClickListener(v -> {

            if(validateEntries()){
                if(breedingStock==null){
                    saveBreedingStock();
                }
                else{
                    updateBreedingStock();
                }
                //dismiss dialog and refresh fragment
                dialog.dismiss();
               loadBreedingStock();
            }else{
                Log.d("ERROR","Testing");
            }
        });

        builder.setView(addAnimalDialog);
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.breeding_stock_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.action_add_new_animal) {
            addAnimal(context);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage();
            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            assert data != null;
            if (data.getData() != null) {
                produceImageUri = data.getData();

                try {
                    produceImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), produceImageUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    produceImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] b = byteArrayOutputStream.toByteArray();

                    encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadBreedingStock(){
        breedingStockListAdapter.clearBreedingStockList();

        breedingStockListAdapter.addList(dbHandler.getBreedingStocks(DashboardActivity.RETRIEVED_USER_ID));
    }

    public void fillViews(){
        if(breedingStock != null){
            name.setText(breedingStock.getName());
            earTag.setText(breedingStock.getEarTag());
            colour.setText(breedingStock.getColor());
            breed.setText(breedingStock.getBreed());
            dateOfBirth.setText(breedingStock.getDateOfBirth());
            weight.setText(breedingStock.getWeight()+ "");
            father.setText(breedingStock.getFather());
            mother.setText(breedingStock.getMotherDam());
            photo.setText(breedingStock.getPhoto());
            DashboardActivity.selectSpinnerItemByValue(sex,breedingStock.getSex());
            DashboardActivity.selectSpinnerItemByValue(source,breedingStock.getSource());
        }
    }

    public void saveBreedingStock(){
        breedingStock = new BreedingStock();
        breedingStock.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        breedingStock.setName(name.getText().toString());
        breedingStock.setEarTag(earTag.getText().toString());
        breedingStock.setColor(colour.getText().toString());
        breedingStock.setSex(sex.getSelectedItem().toString());
        breedingStock.setBreed(breed.getText().toString());
        breedingStock.setDateOfBirth(dateOfBirth.getText().toString());
        breedingStock.setSource(source.getSelectedItem().toString());
        breedingStock.setWeight(Float.parseFloat(weight.getText().toString()));
        breedingStock.setFather(father.getText().toString());
        breedingStock.setMotherDam(mother.getText().toString());
        breedingStock.setPhoto(encodedImage);
        dbHandler.insertBreedingStock(breedingStock);
    }

    public void updateBreedingStock(){

        if(breedingStock != null) {
            breedingStock.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            breedingStock.setName(name.getText().toString());
            breedingStock.setEarTag(earTag.getText().toString());
            breedingStock.setColor(colour.getText().toString());
            breedingStock.setSex(sex.getSelectedItem().toString());
            breedingStock.setBreed(breed.getText().toString());
            breedingStock.setDateOfBirth(dateOfBirth.getText().toString());
            breedingStock.setSource(source.getSelectedItem().toString());
            breedingStock.setWeight(Float.parseFloat(weight.getText().toString()));
            breedingStock.setFather(father.getText().toString());
            breedingStock.setMotherDam(mother.getText().toString());
            breedingStock.setPhoto(photo.getText().toString());
            dbHandler.updateBreedingStock(breedingStock);
        }
    }

    public boolean validateEntries(){
        String message = null;
        if(name.getText().toString().isEmpty()){
            message = getString(R.string.name_not_entered_message);
            name.requestFocus();
        }
        else if(earTag.getText().toString().isEmpty()){
            message = getString(R.string.ear_tag_not_entered_message);
            earTag.requestFocus();
        }

        else if(colour.getText().toString().isEmpty()){
            message = getString(R.string.color_not_entered_message);
            colour.requestFocus();
        }else if(sex.getSelectedItemPosition()==0){
            message = getString(R.string.sex_not_selected);
            sex.requestFocus();
        }
        else if(source.getSelectedItemPosition()==0){
            message = getString(R.string.source_not_selected);
            source.requestFocus();
        }else if(breed.getText().toString().isEmpty()){
            message = getString(R.string.breed_not_entered_message);
            breed.requestFocus();
        }
        else if(dateOfBirth.getText().toString().isEmpty()){
            message = getString(R.string.dob_not_entered_message);
            dateOfBirth.requestFocus();
        }
        else if(weight.getText().toString().isEmpty()){
            message = getString(R.string.weight_not_entered);
            weight.requestFocus();
        }
        else if(father.getText().toString().isEmpty()){
            message = getString(R.string.father_not_entered_message);
            father.requestFocus();
        }
        else if(mother.getText().toString().isEmpty()){
            message = getString(R.string.mother_not_entered_message);
            mother.requestFocus();
        }

        if(message != null){
            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }



}