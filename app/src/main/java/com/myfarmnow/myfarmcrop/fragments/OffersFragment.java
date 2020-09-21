package com.myfarmnow.myfarmcrop.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentOffersBinding;

public class OffersFragment extends Fragment {
    private static final String TAG = "OffersFragment";
    private Context context;
    private FragmentOffersBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offers, container, false);

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        context = context;
    }
}
