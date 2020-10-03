package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

public class LivestockRecordsFragment extends Fragment {
    private static final String TAG = "LivestockRecords";
    private Context context;

    private Toolbar toolbar;
    private LinearLayout layout_breeding_stock, layout_matings, layout_litters, layout_medications;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_livestock_records, container, false);

        toolbar = view.findViewById(R.id.toolbar_livestock_records);
        layout_breeding_stock = view.findViewById(R.id.layout_breeding_stock);
        layout_matings = view.findViewById(R.id.layout_matings);
        layout_litters = view.findViewById(R.id.layout_litters);
        layout_medications = view.findViewById(R.id.layout_medications);

        assert getArguments() != null;
        Log.d(TAG, "onCreateView: Animal = " + getArguments().getString("animal"));

        SharedPreferenceHelper preferenceModel = new SharedPreferenceHelper(context);
        preferenceModel.insertSelectedAnimal(getArguments().getString("animal"));

        Log.d(TAG, "onCreateView: SharedPreference = " + preferenceModel.getSelectedAnimal());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        layout_breeding_stock.setOnClickListener(v -> navController.navigate(R.id.action_livestockRecordsFragment_to_breedingStockViewFragment));
        layout_matings.setOnClickListener(v -> navController.navigate(R.id.action_livestockRecordsFragment_to_matingViewFragment));
        layout_litters.setOnClickListener(v -> navController.navigate(R.id.action_livestockRecordsFragment_to_littersViewFragment));
        layout_medications.setOnClickListener(v -> navController.navigate(R.id.action_livestockRecordsFragment_to_medicationsViewFragment));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}