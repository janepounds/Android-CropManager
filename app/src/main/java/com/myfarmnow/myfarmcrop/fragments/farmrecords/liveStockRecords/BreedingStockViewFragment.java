package com.myfarmnow.myfarmcrop.fragments.farmrecords.liveStockRecords;

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
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.myfarmnow.myfarmcrop.R;

public class BreedingStockViewFragment extends Fragment {
    private static final String TAG = "BreedingStockViewFragment";
    private Context context;

    private Toolbar toolbar;
    private Button addAnimal;
    private RecyclerView recyclerView;

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

        addAnimal.setOnClickListener(v -> navController.navigate(R.id.action_breedingStockViewFragment_to_addBreedingStockFragment));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}