package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.Spinner;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;

public class WalletLoanAppInitiateFragment extends Fragment {
    private static final String TAG = "WalletLoanAppInitiateFr";
    private Context context;

    AppBarConfiguration appBarConfiguration;

    String[] descriptionData = {"Amount", "Confirm", "Photo", "Referees"};

    private Toolbar toolbar;
    private StateProgressBar loanProgressBarId;
    private Button btnLoanNextStep;
    private EditText txtLoanApplicationAmount, txtLoanApplicationDuration;
    private Spinner spLoanApplicationType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_loan_app_initiate, container, false);

        toolbar = view.findViewById(R.id.toolbar_wallet_loan_app_initiate);
        loanProgressBarId = view.findViewById(R.id.loan_progress_bar_id);
        btnLoanNextStep = view.findViewById(R.id.btn_loan_next_step);
        txtLoanApplicationAmount = view.findViewById(R.id.txt_loan_application_amount);
        txtLoanApplicationDuration = view.findViewById(R.id.txt_loan_application_duration);
        spLoanApplicationType = view.findViewById(R.id.sp_loan_application_type);

        loanProgressBarId.setStateDescriptionData(descriptionData);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        btnLoanNextStep.setOnClickListener(v -> {
            if (txtLoanApplicationAmount.getText().toString().trim() == null || txtLoanApplicationAmount.getText().toString().trim().isEmpty()) {
                txtLoanApplicationAmount.setError("Amount Required");
            } else if (txtLoanApplicationDuration.getText().toString().trim() == null || txtLoanApplicationDuration.getText().toString().trim().isEmpty()) {
                txtLoanApplicationDuration.setError("Required");
            } else {
                LoanApplication loanApplication = new LoanApplication();
                loanApplication.setAmount(Float.valueOf(txtLoanApplicationAmount.getText().toString()));
                loanApplication.setDuration(Integer.parseInt(txtLoanApplicationDuration.getText().toString()));
                loanApplication.setLoanType(spLoanApplicationType.getSelectedItem().toString().equals("Months") ? "Monthly" : "Weekly");

                Bundle bundle = new Bundle();
                assert getArguments() != null;
                bundle.putFloat("interest", getArguments().getFloat("interest"));
                bundle.putSerializable("loanApplication", loanApplication);

                btnLoanNextStep.setOnClickListener(view1 -> navController.navigate(R.id.action_walletLoanAppInitiateFragment_to_walletLoanPreviewRequestFragment, bundle));
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}