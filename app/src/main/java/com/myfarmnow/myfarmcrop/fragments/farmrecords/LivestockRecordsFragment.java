package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

import java.util.Objects;

public class LivestockRecordsFragment extends Fragment {
    private static final String TAG = "LivestockRecords";
    private Context context;

    private Toolbar toolbar;
    private LinearLayout layout_breeding_stock, layout_matings, layout_litters, layout_medications;
    private ImageView animalImage;

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
        animalImage = view.findViewById(R.id.breeding_stock_animal_image);

        SharedPreferenceHelper preferenceModel = new SharedPreferenceHelper(context);
        if (getArguments() != null) {
            //Log.d(TAG, "onCreateView: Animal = " + getArguments().getString("animal"));
            preferenceModel.insertSelectedAnimal(getArguments().getString("animal"));
        }

        switch (preferenceModel.getSelectedAnimal()) {
            case "Goat":
                Glide.with(context).load(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_default_goat_coloured, context.getTheme())).into(animalImage);
                break;
            case "Rabbit":
                Glide.with(context).load(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_default_rabbit_coloured, context.getTheme())).into(animalImage);
                break;
            case "Sheep":
                Glide.with(context).load(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_default_sheep_coloured, context.getTheme())).into(animalImage);
                break;
            case "Pig":
                Glide.with(context).load(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_default_pig_coloured, context.getTheme())).into(animalImage);
                break;
        }

        Log.d(TAG, "onCreateView: SharedPreference = " + preferenceModel.getSelectedAnimal());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
//        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(R.string.title_livestock_records);

        toolbar.setNavigationOnClickListener(v -> navController.navigate(R.id.action_livestockRecordsFragment_to_farmRecordsHomeFragment));

        layout_breeding_stock.setOnClickListener(v -> navController.navigate(R.id.action_livestockRecordsFragment_to_breedingStockViewFragment));
        layout_matings.setOnClickListener(v -> navController.navigate(R.id.action_livestockRecordsFragment_to_matingsViewFragment));
        layout_litters.setOnClickListener(v -> navController.navigate(R.id.action_livestockRecordsFragment_to_littersViewFragment));
        layout_medications.setOnClickListener(v -> navController.navigate(R.id.action_livestockRecordsFragment_to_medicationsViewFragment));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}