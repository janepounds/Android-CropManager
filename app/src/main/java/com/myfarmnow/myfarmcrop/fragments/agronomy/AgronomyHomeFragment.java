package com.myfarmnow.myfarmcrop.fragments.agronomy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class AgronomyHomeFragment extends Fragment {
    private Context context;

    private Toolbar toolbar;
    private LinearLayout layoutCropDashboardCrops, layoutAskAgronomist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agronomy_home, container, false);

        toolbar = view.findViewById(R.id.toolbar_agronomy_home);
        layoutCropDashboardCrops = view.findViewById(R.id.layout_crop_dashboard_crops);
        layoutAskAgronomist = view.findViewById(R.id.layout_ask_agronomist);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

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
        NavController navController = Navigation.findNavController(view);

        layoutCropDashboardCrops.setOnClickListener(view1 -> navController.navigate(R.id.action_agronomyHomeFragment_to_cropsFragment));
        layoutAskAgronomist.setOnClickListener(view12 -> showDialPad());
    }

    public void showDialPad() {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        String tel = "0800399399";
        callIntent.setData(Uri.parse("tel:" + tel.trim()));
        startActivity(callIntent);
    }
}