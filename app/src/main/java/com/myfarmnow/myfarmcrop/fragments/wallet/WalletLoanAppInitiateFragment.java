package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletLoanAppInitiateActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletLoanPreviewRequest;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoanAppInitiateBinding;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoansListBinding;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;

public class WalletLoanAppInitiateFragment extends Fragment {
    private static final String TAG = "WalletLoanAppInitiateFr";
    private Context context;

    private FragmentWalletLoanAppInitiateBinding binding;
    AppBarConfiguration appBarConfiguration;

    String[] descriptionData = {"Amount", "Confirm", "Photo", "Referees"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_loan_app_initiate, container, false);

        binding.loanProgressBarId.setStateDescriptionData(descriptionData);
        initialize();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
    }

    public void initialize() {
        //errorMsgTxt = findViewById(R.id.text_view_error_message);
        binding.btnLoanNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.txtLoanApplicationAmount.getText().toString().trim() == null || binding.txtLoanApplicationAmount.getText().toString().trim().isEmpty()) {
                    binding.txtLoanApplicationAmount.setError("Amount Required");
                } else {
                    LoanApplication loanApplication = new LoanApplication();
                    loanApplication.setAmount(Float.valueOf(binding.txtLoanApplicationAmount.getText().toString()));
//                    loanApplication.setDuration(Integer.parseInt(binding.txtLoanApplicationDuration.getText().toString()));
//                    loanApplication.setLoanType(binding.spLoanApplicationType.getSelectedItem().toString().equals("Months") ? "Monthly" : "Weekly");

                    Intent startNext = new Intent(context, WalletLoanPreviewRequest.class);
//                    startNext.putExtra("loanApplication", loanApplication);
//                    startNext.putExtra("interest", (float) getActivity().getIntent().getFloatExtra("interest", 0));
                    startActivity(startNext);
                }
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}