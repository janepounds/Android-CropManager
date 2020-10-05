package com.myfarmnow.myfarmcrop.fragments.farmrecords.crop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.myfarmnow.myfarmcrop.R;

import java.util.Objects;

public class CropRecordsFragment extends Fragment {
    private Context context;
    private NavController navController;

    private Toolbar toolbar;
    private LinearLayout fields, crops, productionReports;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crop_records, container, false);

        toolbar = view.findViewById(R.id.toolbar_crop_records);
        fields = view.findViewById(R.id.layout_dashboard_fields);
        crops = view.findViewById(R.id.layout_dashboard_crops);
        productionReports = view.findViewById(R.id.layout_dashboard_production_reports);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.title_crop_records);

        toolbar.setNavigationOnClickListener(v -> navController.navigate(R.id.action_cropRecordsFragment_to_farmRecordsHomeFragment));

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        fields.setOnClickListener(view1 -> navController.navigate(R.id.action_cropRecordsFragment_to_fieldsListFragment));
        crops.setOnClickListener(view2 -> navController.navigate(R.id.action_cropRecordsFragment_to_cropListFragment));

        productionReports.setOnClickListener((View.OnClickListener) v -> {
            navController.navigate(R.id.action_cropRecordsFragment_to_productionReportsFragment);
        });
    }
}