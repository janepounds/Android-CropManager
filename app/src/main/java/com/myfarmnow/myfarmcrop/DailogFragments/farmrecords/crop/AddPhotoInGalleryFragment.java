package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords.crop;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

import com.myfarmnow.myfarmcrop.models.farmrecords.CropGallery;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class AddPhotoInGalleryFragment extends DialogFragment {
    private Context context;
    private NavController navController;
    private MyFarmDbHandlerSingleton dbHandler;
    private String cropId;
    private CropGallery cropGallery;
    private EditText caption,photo;
    private Button btnSave;
    private TextView browsePhoto,title;
    // image picker code
    private static final int IMAGE_PICK_CODE = 0;
    //permission code
    private static final int PERMISSION_CODE = 1;

    private Dialog dialog;
    private Uri produceImageUri = null;
    private Bitmap produceImageBitmap = null;
    private String encodedImage;
    private ImageView close,imagePreview;
    private LinearLayout galleryLayout;
    private String fetchedPhoto;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomAlertDialog);
        if(getArguments()!=null) {
            cropId = getArguments().getString("cropId");
            Log.e("CropID", cropId);
            cropGallery = (CropGallery) getArguments().getSerializable("cropGallery");
        }

        View view =getLayoutInflater().inflate(R.layout.fragment_add_photo_in_gallery, null);
//        navController = Navigation.findNavController(view);
        initializeForm(view);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


    }

    public void initializeForm(View view){
      close = view.findViewById(R.id.gallery_close);
     dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
     btnSave = view.findViewById(R.id.add_photo_submit);
     caption = view.findViewById(R.id.gallery_caption);
     photo = view.findViewById(R.id.gallery_photo);
     browsePhoto = view.findViewById(R.id.add_gallery_photo);
     title = view.findViewById(R.id.gallery_form_title);
     galleryLayout = view.findViewById(R.id.gallery_layout);
     imagePreview = view.findViewById(R.id.image_preview);
       btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    if(cropGallery ==null){
                        saveGallery();

                    }
                    else{
                        updateGallery();
                    }
                    //pop back
                    Bundle bundle = new Bundle();
                    bundle.putString("cropId",cropGallery.getParentId());
                    navController = Navigation.findNavController(getParentFragment().getView());
                    navController.navigate(R.id.action_addPhotoInGalleryFragment_to_galleryViewFragment,bundle);



            }
        });

        close.setOnClickListener(v -> dismiss());

        fillViews();

        browsePhoto.setOnClickListener(v -> {
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
                    photo.setText(imageName);
                    fetchedPhoto = encodedImage;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void saveGallery(){
        cropGallery = new CropGallery();
        cropGallery.setParentId(cropId);
        cropGallery.setUserId(DashboardActivity.RETRIEVED_USER_ID);
        cropGallery.setPhoto(fetchedPhoto);
        cropGallery.setCaption(caption.getText().toString());
        dbHandler.insertCropGallery(cropGallery);
    }

    public void updateGallery(){
        if(cropGallery != null){

            cropGallery.setParentId(cropId);
            cropGallery.setUserId(DashboardActivity.RETRIEVED_USER_ID);
            cropGallery.setPhoto(fetchedPhoto);
            cropGallery.setCaption(caption.getText().toString());
            dbHandler.updateCropGallery(cropGallery);
        }

    }

    public void fillViews(){
        if(cropGallery != null){
            galleryLayout.setVisibility(View.VISIBLE);

            fetchedPhoto = cropGallery.getPhoto();
            //pick photo
            byte[] decodedString = Base64.decode(cropGallery.getPhoto(),Base64.NO_WRAP);
            InputStream inputStream  = new ByteArrayInputStream(decodedString);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            imagePreview.setImageBitmap(bitmap);
            title.setText("Edit photo");
            btnSave.setText("Update");
            caption.setText(cropGallery.getCaption());
        }
    }



    //    public boolean validateEntries(){
////        String message = null;
////        if(photo.getText().toString().isEmpty()){
////            message = getString(R.string.photo_not_selected_message);
////          photo.requestFocus();
////        }
//
////
////        if(message != null){
////            Toast.makeText(context, getString(R.string.missing_fields_message)+message, Toast.LENGTH_LONG).show();
////            return false;
////        }
////        // Log.d("ERROR",message);
//        return true;
//    }

}