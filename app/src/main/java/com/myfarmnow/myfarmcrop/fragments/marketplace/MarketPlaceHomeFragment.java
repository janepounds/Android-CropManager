package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentMarketplaceHomeBinding;

public class MarketPlaceHomeFragment extends Fragment {
    private static final String TAG = "MarketPlaceHomeFragment";
    private FragmentMarketplaceHomeBinding binding;
    private Context context;
    private ActionBar actionBar;

    FragmentManager fragmentManager;
    Fragment currentFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_marketplace_home, container, false);
        fragmentManager=getActivity().getSupportFragmentManager();
        currentFragment=fragmentManager.getPrimaryNavigationFragment();

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionMarketPlace));



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

        binding.sellProduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment sellproducefragment= new SellProduceFragment();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment_container, sellproducefragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment_container, sellproducefragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();

                currentFragment = sellproducefragment;
            }
        });

        binding.buyInputs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment buyinputshome= new BuyInputsHomePage();
                if (currentFragment == null)
                    fragmentManager.beginTransaction()
                            .add(R.id.main_fragment_container, buyinputshome)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();
                else
                    fragmentManager.beginTransaction()
                            .hide(currentFragment)
                            .add(R.id.main_fragment_container, buyinputshome)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(getString(R.string.actionHome)).commit();

            currentFragment = buyinputshome;



            }
        });
    }


    }


