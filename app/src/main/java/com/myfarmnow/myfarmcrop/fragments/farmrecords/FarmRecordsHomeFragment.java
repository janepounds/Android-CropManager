package com.myfarmnow.myfarmcrop.fragments.farmrecords;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class FarmRecordsHomeFragment extends Fragment {
    private static final String TAG = "FarmRecordsHomeFragment";
    private Context context;
    private NavController navController;

    private Toolbar toolbar;
    private LinearLayout cropRecords, livestockRecords, store, financialRecords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_farm_records_home, container, false);

        toolbar = view.findViewById(R.id.toolbar_farm_records_home);
        cropRecords = view.findViewById(R.id.layout_crop_dashboard_crops);
        livestockRecords = view.findViewById(R.id.layout_crop_dashboard_livestock_records);
        store = view.findViewById(R.id.layout_dashboard_store);
        financialRecords = view.findViewById(R.id.layout_dashboard_financial_records);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());

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

        cropRecords.setOnClickListener(v -> navController.navigate(R.id.action_farmRecordsHomeFragment_to_cropRecordsFragment));
        store.setOnClickListener(v -> navController.navigate(R.id.action_farmRecordsHomeFragment_to_storeFragment));
        financialRecords.setOnClickListener(v -> navController.navigate(R.id.action_farmRecordsHomeFragment_to_financialRecordsFragment));

        livestockRecords.setOnClickListener(v -> SelectAnimal());
    }

    private void SelectAnimal() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Animal");
        // add a list
        String[] forms = {"Cattle", "Goat", "Rabbit", "Sheep", "Pig"};

        builder.setItems(forms, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                String selectedAnimal = null;

                switch (item) {
                    case 0: // Cattle
                        selectedAnimal = "Cattle";
                        break;
                    case 1: // Goats
                        selectedAnimal = "Goat";
                        break;
                    case 2: // Rabbits
                        selectedAnimal = "Rabbit";
                        break;
                    case 3: // Sheep
                        selectedAnimal = "Sheep";
                        break;
                    case 4: // Pig
                        selectedAnimal = "Pig";
                        break;
                }

                if (selectedAnimal != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("animal", selectedAnimal);
                    navController.navigate(R.id.action_farmRecordsHomeFragment_to_livestockRecordsFragment, bundle);
                } else {
                    // TODO
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}