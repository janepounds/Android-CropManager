package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletLoanAppPhotos;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletLoanPreviewRequest;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoanAppInitiateBinding;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoanPreviewRequestBinding;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;

public class WalletLoanPreviewRequestFragment extends Fragment {
    private static final String TAG = "WalletLoanPreviewRequest";

    private Context context;

    String[] descriptionData = {"Amount", "Confirm", "Photo", "Referees"};
    LoanApplication loanApplication;
    ProgressDialog dialog;

    private FragmentWalletLoanPreviewRequestBinding binding;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_loan_preview_request, container, false);

        binding.loanProgressBarId.setStateDescriptionData(descriptionData);

        assert getArguments() != null;
        loanApplication = (LoanApplication) getArguments().getSerializable("loanApplication");

        if (loanApplication != null) {
            loanApplication.setInterestRate((float) getArguments().getFloat("interest"));
            initializeActivity();
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void initializeActivity() {
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);

        binding.textViewLoanPreviewAmount.setText("UGX " + NumberFormat.getInstance().format(loanApplication.getAmount()));
        binding.textViewLoanPreviewInterestRate.setText(NumberFormat.getInstance().format(loanApplication.getInterestRate()) + "%");
        binding.textViewLoanPreviewDuration.setText(loanApplication.getDuration() + " " + loanApplication.getDurationLabel());
        binding.textViewLoanPreviewDueDate.setText(loanApplication.computeDueDate());
        binding.textViewLoanPreviewDueAmount.setText("UGX " + NumberFormat.getInstance().format(loanApplication.computeDueAmount()));
        binding.btnLoanNextStep.setOnClickListener(view -> initiateApplication());
    }

    public void initiateApplication() {
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();
        client.addHeader("Authorization", "Bearer " + WalletAuthActivity.WALLET_ACCESS_TOKEN);
        params.put("userId", WalletHomeFragment.getPreferences(WalletHomeFragment.PREFERENCES_USER_ID, context));
        params.put("amount", loanApplication.getAmount());
        params.put("duration", loanApplication.getDuration());
        params.put("loanType", loanApplication.getLoanType());
        params.put("interest", loanApplication.getInterestRate());
        client.setTimeout(20000);
        client.post(ApiPaths.WALLET_LOAN_APPLICATION_INITIATE, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                dialog.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    Bundle bundle = new Bundle();
                    assert getArguments() != null;
                    bundle.putString("loanApplicationId", response.getString("loanApplicationId"));

                    navController.navigate(R.id.action_walletLoanPreviewRequestFragment_to_walletLoanAppPhotosFragment, bundle);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (statusCode == 401) {
                    WalletAuthActivity.startAuth(context, true);
                } else if (statusCode == 500) {
                    binding.textViewErrorMessage.setText("Error Occurred Try again later");
                    Log.e("info 500", new String(String.valueOf(errorResponse)) + ", code: " + statusCode);
                } else if (statusCode == 400) {
                    try {
                        binding.textViewErrorMessage.setText(errorResponse.getString("errors"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("info 500", new String(String.valueOf(errorResponse)) + ", code: " + statusCode);
                } else if (statusCode == 406) {
                    try {
                        binding.textViewErrorMessage.setText(errorResponse.getString("errors"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("info 406", new String(String.valueOf(errorResponse)) + ", code: " + statusCode);
                } else {
                    binding.textViewErrorMessage.setText("Error Occurred Try again later");
                    if (errorResponse != null) {
                        Log.e("info", new String(String.valueOf(errorResponse)) + ", code: " + statusCode);
                    } else {
                        Log.e("info", "Something got very very wrong, code: " + statusCode);
                    }
                }
                binding.textViewErrorMessage.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : " + statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : " + statusCode, "Something got very very wrong");
                }
                binding.textViewErrorMessage.setText("Error Occurred Try again later");
                binding.textViewErrorMessage.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }


        });
    }
}
