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
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;

import java.util.ArrayList;

public class CropListFragment extends Fragment {
    private Context context;
    private CropsListRecyclerAdapter cropListRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    ArrayList<Crop> cropArrayList = new ArrayList();
    NavController navController;

    private Toolbar toolbar;
    private RecyclerView cropsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop_list, container, false);
        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.toolbar_crop_list_fragment);
        cropsRecyclerView = view.findViewById(R.id.crop_list_recycler_view);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Crops");

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

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropListRecyclerAdapter = new CropsListRecyclerAdapter(cropArrayList, context);

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        cropsRecyclerView.setLayoutManager(linearLayoutManager);

        cropsRecyclerView.setAdapter(cropListRecyclerAdapter);

        loadCropInventories();
    }

    private void loadCropInventories() {
        cropListRecyclerAdapter.clearCropList();

        cropListRecyclerAdapter.addList(dbHandler.getCrops(DashboardActivity.RETRIEVED_USER_ID));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        // Log.d("ADD NEW SELECTED",item.getItemId()+" "+R.id.action_add_new);
        switch (item.getItemId()) {
            case R.id.action_add_new:
                navController.navigate(R.id.action_cropListFragment_to_addCropFragment);
                return true;
            default:
                navController.navigate(R.id.action_cropListFragment_to_cropRecordsFragment);
                return super.onOptionsItemSelected(item);
        }
    }
}