package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myfarmnow.myfarmcrop.R;

public class MarketPlaceHomeFragment extends Fragment {
    private static final String TAG = "MarketPlaceHomeFragment";
    private Context context;
    private ActionBar actionBar;

    FragmentManager fragmentManager;
    Fragment currentFragment;

    private CardView buyInputs, sellProduce;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_marketplace_home, container, false);

        buyInputs = view.findViewById(R.id.buy_inputs_marketplace_home_fragment);
        sellProduce = view.findViewById(R.id.sell_produce_marketplace_home_fragment);

        fragmentManager = getActivity().getSupportFragmentManager();
        currentFragment = fragmentManager.getPrimaryNavigationFragment();

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionMarketPlace));

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

        sellProduce.setOnClickListener((View.OnClickListener) v -> {
            Fragment sellproducefragment = new SellProduceFragment();
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
        });

        buyInputs.setOnClickListener((View.OnClickListener) v -> {
            Fragment buyinputshome = new BuyInputsHomePage();
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
        });
    }
}


