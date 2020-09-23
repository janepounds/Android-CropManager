package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.marketplace.BuyInputsActivity;
import com.myfarmnow.myfarmcrop.activities.marketplace.MarketPlaceActivity;
import com.myfarmnow.myfarmcrop.databinding.FragmentMarketplaceHomeBinding;

import java.util.Objects;

public class MarketPlaceHomeFragment extends Fragment {
    private static final String TAG = "MarketPlaceHomeFragment";
    private FragmentMarketplaceHomeBinding binding;
    private Context context;
    private ActionBar actionBar;

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_marketplace_home, container, false);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        setHasOptionsMenu(true);

        actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Marketplace");

        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

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

        binding.toolbar.setNavigationOnClickListener(v -> requireActivity().onBackPressed());

        binding.sellProduce.setOnClickListener(v -> navController.navigate(R.id.action_marketPlaceHomeFragment_to_sellProduceFragment));

        binding.buyInputs.setOnClickListener(v -> {
            Intent buyInputs = new Intent(getActivity(), BuyInputsActivity.class);
            requireActivity().startActivity(buyInputs);
        });
    }
}