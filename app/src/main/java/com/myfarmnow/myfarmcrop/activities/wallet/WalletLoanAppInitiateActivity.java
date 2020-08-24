package com.cabral.emaisha.wallet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.cabral.emaisha.R;
import com.cabral.emaisha.wallet.models.LoanApplication;

public class WalletLoanAppInitiateActivity extends AppCompatActivity {

    String[] descriptionData = {"Amount", "Confirm","Photo", "Referees"};
    Button nextStepBtn;
    TextView amountTxt, durationTxt,errorMsgTxt;
    Spinner durationTypeSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_loan_app_add_amount);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        StateProgressBar stateProgressBar =  findViewById(R.id.loan_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);
        initializeActivity();
    }

    public void initializeActivity(){
        nextStepBtn = findViewById(R.id.btn_loan_next_step);
        amountTxt = findViewById(R.id.txt_loan_application_amount);
        durationTxt = findViewById(R.id.txt_loan_application_duration);
        durationTypeSp = findViewById(R.id.sp_loan_application_type);
        errorMsgTxt = findViewById(R.id.text_view_error_message);
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanApplication loanApplication = new LoanApplication();
                loanApplication.setAmount(Float.parseFloat(amountTxt.getText().toString()));
                loanApplication.setDuration(Integer.parseInt(durationTxt.getText().toString()));
                loanApplication.setLoanType(durationTypeSp.getSelectedItem().toString().equals("Months")?"Monthly":"Weekly");

                Intent startNext = new Intent(WalletLoanAppInitiateActivity.this,WalletLoanPreviewRequest.class);
                startNext.putExtra("loanApplication",loanApplication);
                startNext.putExtra("interest", (float) getIntent().getFloatExtra("interest", 0));

                startActivity(startNext);
            }
        });

    }


}
