package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords.livestockrecords;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.BreedingStock;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddBreedingStockFragment extends DialogFragment {
    private static final String TAG = "BreedingFragment";
    private Context context;
    private BreedingStock breedingStock;

    private NavController navController;

    // image picker code
    private static final int IMAGE_PICK_CODE = 0;
    //permission code
    private static final int PERMISSION_CODE = 1;

    private Dialog dialog;
    private Uri produceImageUri = null;
    private Bitmap produceImageBitmap = null;
    private String encodedImage;
    private ImageView close, datePicker,imagePreview;
    private Button submit;
    private EditText name, earTag, colour, breed, weight, dateOfBirth;
    AutoCompleteTextView father, mother;
    private Spinner sex, source;
    private TextView photo, breeder_form_title,photoTextView;
    private MyFarmDbHandlerSingleton dbHandler;
    private LinearLayout galleryLayout;
    private String fetchedPhoto;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        //get arguments for edit

        View view = getLayoutInflater().inflate(R.layout.fragment_add_breeding_stock, null);
        initializeForm(view);
        if (getArguments() != null) {
            breedingStock = (BreedingStock) getArguments().getSerializable("breedingStock");

            fillViews();

        }
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    public void initializeForm(View view) {
        breeder_form_title = view.findViewById(R.id.breeder_form_title);
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
        photoTextView = view.findViewById(R.id.add_animal_photo_text_view);
        imagePreview = view.findViewById(R.id.image_preview);
        galleryLayout = view.findViewById(R.id.gallery_layout);
        ((ArrayAdapter) sex.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((ArrayAdapter) source.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);
        DashboardActivity.addDatePickerImageView(datePicker, dateOfBirth, context);
        DashboardActivity.addDatePicker( dateOfBirth, context);

        close.setOnClickListener(view1 -> dismiss());
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
        photoTextView.setOnClickListener(v -> {
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


        ArrayList<String> sireList = new ArrayList<>(), damnList = new ArrayList<>();

        SharedPreferenceHelper preferenceModel = new SharedPreferenceHelper(context);

        for (BreedingStock x : dbHandler.getBreedingStockBySex(DashboardActivity.RETRIEVED_USER_ID,preferenceModel.getSelectedAnimal(),"Male")) {
            sireList.add(x.getName());
        }
        for (BreedingStock x : dbHandler.getBreedingStockBySex(DashboardActivity.RETRIEVED_USER_ID,preferenceModel.getSelectedAnimal(),"Female")) {
            damnList.add(x.getName());
        }
        ArrayAdapter<String> animalListAdapter = new ArrayAdapter<String>(context,  android.R.layout.simple_dropdown_item_1line, sireList);
        father.setThreshold(1);
        father.setAdapter(animalListAdapter);
        father.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                father.showDropDown();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, damnList);
        mother.setThreshold(1);
        mother.setAdapter(adapter);

        mother.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mother.showDropDown();
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
                    //getting image path
                    String path = produceImageUri.getPath();
                    File f = new File(path);
                    String imageName = f.getName();
                    photoTextView.setText(imageName);
                    fetchedPhoto  = encodedImage;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void fillViews() {
        if (breedingStock != null) {
            galleryLayout.setVisibility(View.VISIBLE);
            fetchedPhoto = breedingStock.getPhoto();
            //pick photo
            Glide.with(context).load(Base64.decode(fetchedPhoto, Base64.DEFAULT)).into(imagePreview);
            submit.setText(getString(R.string.update));
            breeder_form_title.setText(getString(R.string.update_animal));

            name.setText(breedingStock.getName());
            earTag.setText(breedingStock.getEarTag());
            colour.setText(breedingStock.getColor());
            breed.setText(breedingStock.getBreed());
            dateOfBirth.setText(breedingStock.getDateOfBirth());
            weight.setText(breedingStock.getWeight() + "");
            father.setText(breedingStock.getFather());
            mother.setText(breedingStock.getMotherDam());
            DashboardActivity.selectSpinnerItemByValue(sex, breedingStock.getSex());
            DashboardActivity.selectSpinnerItemByValue(source, breedingStock.getSource());
        }
    }

    public void saveBreedingStock() {
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);

        Log.d(TAG, "onCreateView: SharedPreference = " + sharedPreferenceHelper.getSelectedAnimal());

        breedingStock = new BreedingStock();
        breedingStock.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        breedingStock.setName(name.getText().toString());
        breedingStock.setEarTag(earTag.getText().toString());
        breedingStock.setColor(colour.getText().toString());
        breedingStock.setSex(sex.getSelectedItem().toString());
        breedingStock.setBreed(breed.getText().toString());
        breedingStock.setDateOfBirth(dateOfBirth.getText().toString());
        breedingStock.setSource(source.getSelectedItem().toString());
        breedingStock.setWeight(Float.parseFloat("0" + weight.getText().toString()));
        breedingStock.setFather(father.getText().toString());
        breedingStock.setMotherDam(mother.getText().toString());
        breedingStock.setPhoto(fetchedPhoto);
        breedingStock.setAnimalType(sharedPreferenceHelper.getSelectedAnimal());
        dbHandler.insertBreedingStock(breedingStock);
    }

    public void updateBreedingStock() {
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(context);

        if (breedingStock != null) {
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
            breedingStock.setPhoto(fetchedPhoto);
            breedingStock.setAnimalType(sharedPreferenceHelper.getSelectedAnimal());
            dbHandler.updateBreedingStock(breedingStock);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }

    public boolean validateEntries() {
        String message = null;
        if (name.getText().toString().isEmpty()) {
            message = getString(R.string.name_not_entered_message);
            name.requestFocus();
        } else if (colour.getText().toString().isEmpty()) {
            message = getString(R.string.color_not_entered_message);
            colour.requestFocus();
        } else if (sex.getSelectedItemPosition() == 0) {
            message = getString(R.string.sex_not_selected);
            sex.requestFocus();
        } else if (source.getSelectedItemPosition() == 0) {
            message = getString(R.string.source_not_selected);
            source.requestFocus();
        }

        if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        }
        // Log.d("ERROR",message);
        return true;
    }
}