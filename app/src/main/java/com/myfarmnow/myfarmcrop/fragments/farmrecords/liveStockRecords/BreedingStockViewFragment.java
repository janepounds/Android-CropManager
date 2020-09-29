package com.myfarmnow.myfarmcrop.fragments.farmrecords.liveStockRecords;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.databinding.FragmentMyProduceBinding;
import com.myfarmnow.myfarmcrop.fragments.marketplace.MyProduceFragment;
import com.myfarmnow.myfarmcrop.models.marketplace.MyProduce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BreedingStockViewFragment extends Fragment {
    private static final String TAG = "BreedingFragment";
    private Context context;

    private Toolbar toolbar;
    private Button addAnimal;
    private RecyclerView recyclerView;

    // image picker code
    private static final int IMAGE_PICK_CODE = 0;
    //permission code
    private static final int PERMISSION_CODE = 1;

    private Dialog dialog;
    private Uri produceImageUri = null;
    private Bitmap produceImageBitmap = null;
    private String encodedImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_breeding_stock_view, container, false);

        toolbar = view.findViewById(R.id.toolbar_breeding_stock_view);
        addAnimal = view.findViewById(R.id.add_animal);
        recyclerView = view.findViewById(R.id.breeding_stock_recyclerView);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        addAnimal.setOnClickListener(v -> addAnimal());
    }

    private void addAnimal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);

        View addAnimalDialog = getLayoutInflater().inflate(R.layout.add_animal_dialog, null);

        ImageView close = addAnimalDialog.findViewById(R.id.add_animal_close);
        EditText name = addAnimalDialog.findViewById(R.id.add_animal_name);
        EditText earTag = addAnimalDialog.findViewById(R.id.add_animal_eartag);
        EditText colour = addAnimalDialog.findViewById(R.id.add_animal_colour);
        Spinner sex = addAnimalDialog.findViewById(R.id.add_animal_sex);
        EditText breed = addAnimalDialog.findViewById(R.id.add_animal_breed);
        Spinner source = addAnimalDialog.findViewById(R.id.add_animal_source);
        EditText weight = addAnimalDialog.findViewById(R.id.add_animal_weight);
        EditText father = addAnimalDialog.findViewById(R.id.add_animal_father);
        EditText mother = addAnimalDialog.findViewById(R.id.add_animal_mother);
        TextView photo = addAnimalDialog.findViewById(R.id.add_animal_photo);
        Button submit = addAnimalDialog.findViewById(R.id.add_animal_submit);

        close.setOnClickListener(view -> dialog.dismiss());

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
            //TODO
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
}