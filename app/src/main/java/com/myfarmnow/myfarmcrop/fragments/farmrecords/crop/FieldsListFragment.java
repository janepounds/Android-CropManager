package com.myfarmnow.myfarmcrop.fragments.farmrecords.crop;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropFieldsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class FieldsListFragment extends Fragment {
    public Context context;
    private CropFieldsListRecyclerAdapter cropFieldsListRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MyFarmDbHandlerSingleton dbHandler;
    NavController navController;

    private Toolbar toolbar;
    private RecyclerView cropFieldRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fields_list, container, false);
        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.toolbar_field_list_fragment);
        cropFieldRecyclerView = view.findViewById(R.id.crop_fields_list_recyclerView);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> navController.popBackStack());

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
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropFieldsListRecyclerAdapter = new CropFieldsListRecyclerAdapter(context, dbHandler.getCropFields(DashboardActivity.RETRIEVED_USER_ID));
        cropFieldRecyclerView.setAdapter(cropFieldsListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        cropFieldRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new:

                navController.navigate(R.id.action_fieldsListFragment_to_addFieldFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}