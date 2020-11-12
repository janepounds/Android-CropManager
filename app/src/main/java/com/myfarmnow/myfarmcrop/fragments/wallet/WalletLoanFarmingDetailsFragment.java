package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.myfarmnow.myfarmcrop.R;


public class WalletLoanFarmingDetailsFragment extends Fragment {

    Toolbar toolbar;
    Button previousBtn, nextBtn;
    NavController navController;
    private StateProgressBar loanProgressBarId;
    String[] descriptionData = {"Loan\nDetails", "Farming\nDetails", "Preview", "KYC\nDetails"};




    public WalletLoanFarmingDetailsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_wallet_loan_farming_details, container, false);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Farming Details");

        toolbar = view.findViewById(R.id.toolbar_wallet_loan_app_initiate);
        previousBtn = view.findViewById(R.id.btn_previous);
        nextBtn = view.findViewById(R.id.btn_loan_next_step);
        loanProgressBarId = view.findViewById(R.id.loan_progress_bar_id);


        loanProgressBarId.setStateDescriptionData(descriptionData);
        loanProgressBarId.setStateDescriptionTypeface("fonts/JosefinSans-SemiBold.ttf");

        previousBtn.setOnClickListener(view2 -> navController.popBackStack());
        nextBtn.setOnClickListener(view1 -> navController.navigate(R.id.action_walletLoanFarmingDetailsFragment_to_walletLoanPreviewRequestFragment));


        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
    }