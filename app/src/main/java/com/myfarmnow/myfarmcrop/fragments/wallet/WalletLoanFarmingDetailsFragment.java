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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;


public class WalletLoanFarmingDetailsFragment extends Fragment {

    Toolbar toolbar;
    Button previousBtn, nextBtn;
    TextView harvesting_unit_txt, per_harvesting_unit_txt;
    Spinner harvesting_unit_spn;
    NavController navController;
    private StateProgressBar loanProgressBarId;
    String[] descriptionData = {"Loan\nDetails", "Farming\nDetails", "Preview", "KYC\nDetails"};

    AppBarConfiguration appBarConfiguration;

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

        toolbar = view.findViewById(R.id.toolbar_wallet_loan_app_initiate);
        previousBtn = view.findViewById(R.id.btn_previous);
        nextBtn = view.findViewById(R.id.btn_loan_next_step);
        loanProgressBarId = view.findViewById(R.id.loan_progress_bar_id);

        harvesting_unit_spn= view.findViewById(R.id.harvesting_unit_spn);
        harvesting_unit_txt= view.findViewById(R.id.harvesting_unit_txt);
        per_harvesting_unit_txt= view.findViewById(R.id.per_harvesting_unit_txt);

        loanProgressBarId.setStateDescriptionData(descriptionData);
        loanProgressBarId.setStateDescriptionTypeface("fonts/JosefinSans-SemiBold.ttf");


        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        harvesting_unit_spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem=harvesting_unit_spn.getSelectedItem().toString();
                if(!selectedItem.isEmpty()){
                    harvesting_unit_txt.setText(selectedItem);
                    per_harvesting_unit_txt.setText("/"+selectedItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        previousBtn.setOnClickListener(view2 -> navController.popBackStack());

        Bundle bundle = new Bundle();
        if(getArguments() != null){

            bundle.putFloat("interest", getArguments().getFloat("interest"));
            LoanApplication loanApplication= (LoanApplication) getArguments().getSerializable("loanApplication");
            bundle.putSerializable("loanApplication", loanApplication);
        }
        nextBtn.setOnClickListener(view1 -> navController.navigate(R.id.action_walletLoanFarmingDetailsFragment_to_walletLoanPreviewRequestFragment,bundle));


    }

    }