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
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropGalleryRecyclerAdapter;
import com.myfarmnow.myfarmcrop.adapters.farmrecords.CropsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropNote;
import com.myfarmnow.myfarmcrop.models.farmrecords.Crop;
import com.myfarmnow.myfarmcrop.models.farmrecords.CropGallery;
import com.myfarmnow.myfarmcrop.utils.SharedPreferenceHelper;

import java.util.ArrayList;


public class GalleryViewFragment extends Fragment {
    private NavController navController;
    private MyFarmDbHandlerSingleton dbHandler;
    private Toolbar toolbar;
    private Context context;
    private  String cropId =null;
    private RecyclerView recyclerView;
    private CropGalleryRecyclerAdapter cropGalleryRecyclerAdapter;
    private GridLayoutManager gridLayoutManager;
    ArrayList<CropGallery> cropGalleryArrayList = new ArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_gallery_view, container, false);
        toolbar = view.findViewById(R.id.toolbar_gallery);
        recyclerView = view.findViewById(R.id.gallery_recycler_view);

        setHasOptionsMenu(true);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Gallery");



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
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        if(getArguments()!=null){
            cropId = getArguments().getString("cropId");
            Log.e("CropID",cropId);
        }

        cropGalleryRecyclerAdapter = new CropGalleryRecyclerAdapter(context,cropId,cropGalleryArrayList);

        gridLayoutManager = new GridLayoutManager(context, 2);
        // Set layout manager.
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(cropGalleryRecyclerAdapter);
        loadGallery();
        toolbar.setNavigationOnClickListener(view1->navController.popBackStack(R.id.cropListFragment,false));

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.gallery_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadGallery(){
        cropGalleryRecyclerAdapter.clearGalleryList();
        cropGalleryRecyclerAdapter.addList(dbHandler.getCropgalleries(cropId, DashboardActivity.RETRIEVED_USER_ID));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_photo:
               //open dialog for adding pic
                Bundle bundle = new Bundle();
                bundle.putString("cropId",cropId);
                navController.navigate(R.id.action_galleryViewFragment_to_addPhotoInGalleryFragment,bundle);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}