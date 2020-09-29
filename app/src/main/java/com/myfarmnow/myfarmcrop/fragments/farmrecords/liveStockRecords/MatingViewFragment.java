package com.myfarmnow.myfarmcrop.fragments.farmrecords.liveStockRecords;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;

public class MatingViewFragment extends Fragment {
    private static final String TAG = "MatingViewFragment";
    private Context context;

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    NavController navController;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mating_view, container, false);

        toolbar = view.findViewById(R.id.toolbar_matings);
        recyclerView = view.findViewById(R.id.matings_recyclerView);

        setHasOptionsMenu(true);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> navController.popBackStack());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mating_view_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new_mating:
                addMating();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addMating() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CustomAlertDialog);

        View addMatingDialog = getLayoutInflater().inflate(R.layout.add_mating_dialog, null);

        ImageView close = addMatingDialog.findViewById(R.id.add_mating_close);
        EditText matingDate = addMatingDialog.findViewById(R.id.add_mating_date);
        EditText femaleName = addMatingDialog.findViewById(R.id.add_mating_female_name);
        EditText maleName = addMatingDialog.findViewById(R.id.add_mating_male_name);
        Spinner method = addMatingDialog.findViewById(R.id.add_mating_method);
        EditText gestationPeriod = addMatingDialog.findViewById(R.id.add_mating_gestation_period);
        EditText deliveryAlert = addMatingDialog.findViewById(R.id.add_mating_delivery_alert);
        EditText note = addMatingDialog.findViewById(R.id.add_mating_note);
        Button submit = addMatingDialog.findViewById(R.id.add_mating_submit);

        close.setOnClickListener(view -> dialog.dismiss());

        submit.setOnClickListener(v -> {
            //TODO
        });

        builder.setView(addMatingDialog);
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }
}