package com.myfarmnow.myfarmcrop.fragments.farmrecords.liveStockRecords;

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
import com.myfarmnow.myfarmcrop.adapters.livestockrecords.MatingListAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.livestock_models.Mating;

import java.util.ArrayList;


public class MatingsViewFragment extends Fragment {
    private static final String TAG = "MatingViewFragment";
    private Context context;
    private Mating mating;
    private MatingListAdapter matingListAdapter;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyFarmDbHandlerSingleton dbHandler;
    NavController navController;
    public ArrayList<Mating> matingArrayList = new ArrayList();
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matings_view, container, false);

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
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        matingListAdapter = new MatingListAdapter(context, matingArrayList);
        recyclerView.setAdapter(matingListAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        toolbar.setNavigationOnClickListener(view1->navController.navigate(R.id.action_matingsViewFragment_to_livestockRecordsFragment));
        loadMatings();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mating_view_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadMatings(){
        matingListAdapter.clearMatingList();
        matingListAdapter.addList(dbHandler.getMatings(DashboardActivity.RETRIEVED_USER_ID));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new_mating:
                navController.navigate(R.id.action_matingsViewFragment_to_addMatingFragment);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }