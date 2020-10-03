package com.myfarmnow.myfarmcrop.DailogFragments.farmrecords.gallery;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.livestock_models.Medication;


public class AddPhotoInGalleryFragment extends DialogFragment {
private Context context;




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context =context;
    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.CustomAlertDialog);


        View view =getLayoutInflater().inflate(R.layout.fragment_add_photo_in_gallery, null);
        builder.setView(view);
        return builder.create();
    }
}