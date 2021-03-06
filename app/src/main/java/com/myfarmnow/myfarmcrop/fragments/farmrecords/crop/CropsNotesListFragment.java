package com.myfarmnow.myfarmcrop.fragments.farmrecords.crop;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropsNotesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;

public class CropsNotesListFragment extends Fragment {
    private Context context;
    private NavController navController;
    private CropsNotesListRecyclerAdapter cropsNotesListRecyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private MyFarmDbHandlerSingleton dbHandler;
    private String cropId = null;

    private Toolbar toolbar;
    private RecyclerView cropsNotesRecyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crops_notes_list, container, false);

        toolbar = view.findViewById(R.id.toolbar_crops_notes_list_fragment);
        cropsNotesRecyclerView = view.findViewById(R.id.crops_notes_recyclerView);

        setHasOptionsMenu(true);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Notes");
        // Inflate the layout for this fragment
        requireActivity().getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                // Check BackStackEntryCount of FragmentManager
                //reload
                dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
                cropsNotesListRecyclerAdapter = new CropsNotesListRecyclerAdapter(context, cropId, dbHandler.getCropNotes(cropId, CropNote.IS_FOR_CROP));
                cropsNotesRecyclerView.setAdapter(cropsNotesListRecyclerAdapter);
                linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                cropsNotesRecyclerView.setLayoutManager(linearLayoutManager);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        if (getArguments() != null) {
            cropId = getArguments().getString("cropId");
            Log.e("CropID", cropId);
        }

        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        cropsNotesListRecyclerAdapter = new CropsNotesListRecyclerAdapter(context, cropId, dbHandler.getCropNotes(cropId, CropNote.IS_FOR_CROP));
        cropsNotesRecyclerView.setAdapter(cropsNotesListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        cropsNotesRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_add_new:
                item.setTitle("Add New");
                //navigate to add notes

                Bundle bundle = new Bundle();
                bundle.putString("cropId", cropId);
                navController.navigate(R.id.action_cropsNotesListFragment_to_addCropNotesFragment, bundle);

                return true;
            default:
                navController.navigate(R.id.action_cropsNotesListFragment_to_cropListFragment);
//                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }
}