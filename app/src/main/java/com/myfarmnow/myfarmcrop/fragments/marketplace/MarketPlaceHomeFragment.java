package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawableWrapper;
import androidx.core.graphics.drawable.WrappedDrawable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.customs.NotificationBadger;
import com.myfarmnow.myfarmcrop.databinding.FragmentMarketplaceHomeBinding;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.BuyInputsHomePage;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.CurrencyFrag;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.Languages;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.My_Cart;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.SearchFragment;
import com.myfarmnow.myfarmcrop.fragments.buyInputsFragments.UpdateAccountFragment;
import com.myfarmnow.myfarmcrop.utils.Utilities;

import static com.facebook.FacebookSdk.getApplicationContext;

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
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate toolbar_menu Menu
        // Bind Menu Items
        MenuItem languageItem = menu.findItem(R.id.toolbar_ic_language);
        MenuItem currencyItem = menu.findItem(R.id.toolbar_ic_currency);
        MenuItem profileItem = menu.findItem(R.id.toolbar_edit_profile);
        MenuItem searchItem = menu.findItem(R.id.toolbar_ic_search);
        MenuItem cartItem = menu.findItem(R.id.toolbar_ic_cart);

        profileItem.setVisible(false);
        languageItem.setVisible(false);
        currencyItem.setVisible(false);
        searchItem.setVisible(false);
        cartItem.setVisible(false);

    }

    }


