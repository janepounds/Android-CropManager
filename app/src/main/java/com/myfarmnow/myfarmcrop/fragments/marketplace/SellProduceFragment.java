package com.myfarmnow.myfarmcrop.fragments.marketplace;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.marketplace.SellProduceViewPagerAdapter;
import com.myfarmnow.myfarmcrop.databinding.FragmentSellProduceBinding;
import com.myfarmnow.myfarmcrop.utils.Utilities;

import java.util.Objects;

public class SellProduceFragment extends Fragment {
    private static final String TAG = "SellProduceFragment";
    private FragmentSellProduceBinding binding;
    private Context context;
    
    private SellProduceViewPagerAdapter sellProduceViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sell_produce, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.actionproducemarket));

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

        sellProduceViewPagerAdapter = new SellProduceViewPagerAdapter(requireActivity().getSupportFragmentManager());

        binding.viewPager.setAdapter(sellProduceViewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        sellProduceViewPagerAdapter = new SellProduceViewPagerAdapter(getChildFragmentManager());

        binding.viewPager.setAdapter(sellProduceViewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }


}