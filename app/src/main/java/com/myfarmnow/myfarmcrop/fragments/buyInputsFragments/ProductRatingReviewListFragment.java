package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.myfarmnow.myfarmcrop.R;


public class ProductRatingReviewListFragment extends Fragment {

    FrameLayout writeReview;
    AppCompatButton writeReviewBtn;
    View rootView;
    public ProductRatingReviewListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_product_rating_review_list, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Review");

        ((AppCompatActivity)requireActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        writeReview = rootView.findViewById(R.id.write_review_layout_button);
        writeReviewBtn = rootView.findViewById(R.id.write_review_button);
        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProductRatingReviewFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, fragment)
                        .addToBackStack(null).commit();
            }
        });

        return rootView;
    }
}