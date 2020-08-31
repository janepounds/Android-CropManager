package com.myfarmnow.myfarmcrop.popupDailogs.farmrecords;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.myfarmnow.myfarmcrop.R;

public class AddFertilizer extends DialogFragment {

    public AddFertilizer(){

    }
    public static AddFertilizer newInstance(String title) {

        AddFertilizer frag = new AddFertilizer();

        Bundle args = new Bundle();

        args.putString("title", title);

        frag.setArguments(args);

        return frag;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        getDialog().requestWindowFeature(Window.FEATURE_RIGHT_ICON);
        getDialog().setFeatureDrawableResource(Window.FEATURE_RIGHT_ICON, R.drawable.ic_close);
        return inflater.inflate(R.layout.content_crop_inventory_fertilizer_manager, container, false);


    }


    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view =inflater.inflate(R.layout.content_crop_inventory_fertilizer_manager, null);

        builder.setView(view);
        builder.setCancelable(true);



        initializeView(view);
        return builder.create();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "FERTILIZER");
        getDialog().setCancelable(true);

        getDialog().setTitle(title);

    }


    public void initializeView(View view){


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }

}
