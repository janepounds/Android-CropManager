package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentMarketplaceHomeBinding;

public class MarketPlaceHomeFragment extends Fragment {
    private static final String TAG = "MarketPlaceHomeFragment";
    private FragmentMarketplaceHomeBinding binding;
    private Context context;

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_marketplace_home, container, false);

        return binding.getRoot();
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
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        binding.buyInputs.setOnClickListener(v -> navController.navigate(R.id.action_marketPlaceHomeFragment_to_buyInputsFragment));
        binding.sellProduce.setOnClickListener(v -> navController.navigate(R.id.action_marketPlaceHomeFragment_to_sellProduceFragment));
    }
}