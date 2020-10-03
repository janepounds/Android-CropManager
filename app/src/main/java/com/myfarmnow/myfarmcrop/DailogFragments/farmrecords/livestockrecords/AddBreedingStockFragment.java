package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords.livestockrecords;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
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

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.BreedingStockListAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.models.livestock_models.Mating;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AddBreedingStockFragment extends DialogFragment {
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
    private ImageView close,datePicker;
    private Button submit;
    private EditText name,earTag,colour,breed,weight,father,mother,dateOfBirth;
    private Spinner sex,source;
    private TextView photo;
    public ArrayList<BreedingStock> breedingStockArrayList = new ArrayList();

    private MyFarmDbHandlerSingleton dbHandler;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomAlertDialog);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        //get arguments for edit
        if(getArguments()!=null){
            breedingStock = (BreedingStock) getArguments().getSerializable("breedingStock");
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Animal");
        }
        View view =getLayoutInflater().inflate(R.layout.fragment_add_breeding_stock, null);
        initializeForm(view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }


    public void initializeForm(View view) {
        close = view.findViewById(R.id.add_animal_close);
        name = view.findViewById(R.id.add_animal_name);
        earTag = view.findViewById(R.id.add_animal_eartag);
        colour = view.findViewById(R.id.add_animal_colour);
        sex = view.findViewById(R.id.add_animal_sex);
        breed = view.findViewById(R.id.add_animal_breed);
        dateOfBirth = view.findViewById(R.id.add_animal_dob);
        source = view.findViewById(R.id.add_animal_source);
        weight = view.findViewById(R.id.add_animal_weight);
        father = view.findViewById(R.id.add_animal_father);
        mother = view.findViewById(R.id.add_animal_mother);
        photo = view.findViewById(R.id.add_animal_photo);
        submit = view.findViewById(R.id.add_animal_submit);
        datePicker = view.findViewById(R.id.image_dob);
        ((ArrayAdapter) sex.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) source.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        DashboardActivity.addDatePickerImageView(datePicker, dateOfBirth, context);

        close.setOnClickListener(view1-> dialog.dismiss());
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (position == 0) {
                        // Set the hint text color gray
                        ((TextView) view).setTextColor(Color.GRAY);
                    }

                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//Change selected text size
                } catch (Exception e) {

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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validateEntries()) {
                    if (breedingStock == null) {
                        saveBreedingStock();
                    } else {
                        updateBreedingStock();
                    }
                    //dismiss dialog and refresh fragment

                    navController = Navigation.findNavController(getParentFragment().getView());
                    navController.navigate(R.id.action_addBreedingStockFragment_to_breedingStockViewFragment);



                } else {
                    Log.d("ERROR", "Testing");
                }
            }
        });

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