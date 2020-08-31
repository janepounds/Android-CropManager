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

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        binding.btnLoanNextStep.setOnClickListener(v -> {
            if (binding.txtLoanApplicationAmount.getText().toString().trim() == null || binding.txtLoanApplicationAmount.getText().toString().trim().isEmpty()) {
                binding.txtLoanApplicationAmount.setError("Amount Required");
            } else if (binding.txtLoanApplicationDuration.getText().toString().trim() == null || binding.txtLoanApplicationDuration.getText().toString().trim().isEmpty()) {
                binding.txtLoanApplicationDuration.setError("Required");
            } else {
                LoanApplication loanApplication = new LoanApplication();
                loanApplication.setAmount(Float.valueOf(binding.txtLoanApplicationAmount.getText().toString()));
                loanApplication.setDuration(Integer.parseInt(binding.txtLoanApplicationDuration.getText().toString()));
                loanApplication.setLoanType(binding.spLoanApplicationType.getSelectedItem().toString().equals("Months") ? "Monthly" : "Weekly");

                Bundle bundle = new Bundle();
                assert getArguments() != null;
                bundle.putFloat("interest", getArguments().getFloat("interest"));
                bundle.putSerializable("loanApplication", loanApplication);

                binding.btnLoanNextStep.setOnClickListener(view1 -> navController.navigate(R.id.action_walletLoanAppInitiateFragment_to_walletLoanPreviewRequestFragment, bundle));
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}