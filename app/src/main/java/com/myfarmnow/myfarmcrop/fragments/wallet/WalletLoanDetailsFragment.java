package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.CropHarvest;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;

import java.util.Calendar;

public class WalletLoanDetailsFragment extends Fragment {
    private static final String TAG = "WalletLoanAppInitiateFr";
    private Context context;

    AppBarConfiguration appBarConfiguration;

    String[] descriptionData = {"Loan\nDetails", "Farming\nDetails", "Preview", "KYC\nDetails"};

    private Toolbar toolbar;
    private StateProgressBar loanProgressBarId;
    private Button btnLoanNextStep;
    private EditText txtLoanApplicationAmount,loanpayments_edtxt;
    TextView loanpaymentfrequency,amount_due_txtview,txt_loan_application_duration,loanpayment_duration_units;
    private Spinner spLoanApplicationType;
    Float interest=0F;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            interest=getArguments().getFloat("interest");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_loan_details, container, false);
        toolbar = view.findViewById(R.id.toolbar_wallet_loan_app_initiate);
        loanpaymentfrequency = view.findViewById(R.id.loanpaymentfrequency);
        amount_due_txtview = view.findViewById(R.id.amount_due_txtview);
        txt_loan_application_duration = view.findViewById(R.id.txt_loan_application_duration);
        loanpayment_duration_units = view.findViewById(R.id.loanpayment_duration_units);
        loanProgressBarId = view.findViewById(R.id.loan_progress_bar_id);
        btnLoanNextStep = view.findViewById(R.id.btn_loan_next_step);
        txtLoanApplicationAmount = view.findViewById(R.id.txt_loan_application_amount);
        spLoanApplicationType = view.findViewById(R.id.sp_loan_application_type);
        loanpayments_edtxt= view.findViewById(R.id.loanpayments_edtxt);
        loanProgressBarId.setStateDescriptionData(descriptionData);
        loanProgressBarId.setStateDescriptionTypeface("fonts/JosefinSans-SemiBold.ttf");

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        spLoanApplicationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //Change selected text color
                    ((TextView) view).setTextColor(getResources().getColor(R.color.textColor));
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);//Change selected text size
                } catch (Exception e) {

                }
                if(position==0){
                    loanpaymentfrequency.setText(getString(R.string.per_day));
                    loanpayment_duration_units.setText(getString(R.string.days));
                }
                else if(position==1){
                    loanpaymentfrequency.setText(getString(R.string.per_week));
                    loanpayment_duration_units.setText(getString(R.string.weeks));
                }
                else if(position==2){
                    loanpaymentfrequency.setText(getString(R.string.per_month));
                    loanpayment_duration_units.setText(getString(R.string.months));
                }
                else if(position==3){
                    loanpaymentfrequency.setText(getString(R.string.per_after3months));
                    loanpayment_duration_units.setText(getString(R.string.quarteryear));
                }
                else if(position==4){
                    loanpaymentfrequency.setText(getString(R.string.per_after6months));
                    loanpayment_duration_units.setText(getString(R.string.halfyear));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txtLoanApplicationAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!txtLoanApplicationAmount.getText().toString().isEmpty() ){
                    int applicationAmount=Integer.parseInt(txtLoanApplicationAmount.getText().toString());
                    int amountDue= (int) ((int) applicationAmount*( (interest+100)/100));
                    amount_due_txtview.setText(amountDue+"");
                    Log.e("intrestbug",interest+"");
                }
                if(!amount_due_txtview.toString().isEmpty() && !loanpayments_edtxt.getText().toString().isEmpty()){

                    Float amount_due= Float.parseFloat(amount_due_txtview.getText().toString());
                    Float loanpayments =Float.parseFloat( loanpayments_edtxt.getText().toString());
                    int loanduration_integer=(int) Math.ceil(amount_due/loanpayments);


                    txt_loan_application_duration.setText(loanduration_integer+"");
                }
            }
        });

        loanpayments_edtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!amount_due_txtview.toString().isEmpty() && !loanpayments_edtxt.getText().toString().isEmpty()){

                    Float amount_due= Float.parseFloat(amount_due_txtview.getText().toString());
                    Float loanpayments =Float.parseFloat( loanpayments_edtxt.getText().toString());
                    int loanduration_integer=(int) Math.ceil(amount_due/loanpayments);


                    txt_loan_application_duration.setText(loanduration_integer+"");
                }
                if (!txtLoanApplicationAmount.getText().toString().isEmpty() ){
                    int applicationAmount=Integer.parseInt(txtLoanApplicationAmount.getText().toString());
                    int amountDue= (int) ((int) applicationAmount*( (interest+100)/100));
                    amount_due_txtview.setText(amountDue+"");
                    Log.e("intrestbug",interest+"");
                }
            }
        });



        btnLoanNextStep.setOnClickListener(v -> {
            if (txtLoanApplicationAmount.getText().toString().trim() == null || txtLoanApplicationAmount.getText().toString().trim().isEmpty()) {
                txtLoanApplicationAmount.setError("Please enter value");

            } else if (loanpayments_edtxt.getText().toString().trim() == null || txt_loan_application_duration.getText().toString().trim().isEmpty()) {
                loanpayments_edtxt.setError("Please enter value");
            } else {
                LoanApplication loanApplication = new LoanApplication();
                loanApplication.setAmount(Float.valueOf(txtLoanApplicationAmount.getText().toString()));
                loanApplication.setDuration(Integer.parseInt(txt_loan_application_duration.getText().toString()));
                loanApplication.setLoanType(spLoanApplicationType.getSelectedItem().toString());

                Bundle bundle = new Bundle();
                assert getArguments() != null;
                bundle.putFloat("interest", getArguments().getFloat("interest"));
                bundle.putSerializable("loanApplication", loanApplication);

               navController.navigate(R.id.action_walletLoanAppInitiateFragment_to_walletLoanFarmingDetailsFragment,bundle);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}