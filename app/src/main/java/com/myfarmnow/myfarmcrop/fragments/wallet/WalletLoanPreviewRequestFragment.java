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
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoanAppInitiateBinding;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoanPreviewRequestBinding;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.RequestLoanresponse;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        /*****************RETROFIT IMPLEMENTATION*******************/
        String access_token = WalletAuthActivity.WALLET_ACCESS_TOKEN;
        String userId =WalletHomeActivity.getPreferences(WalletHomeActivity.PREFERENCES_USER_ID, context);
        float amount = loanApplication.getAmount();
        int duration =  loanApplication.getDuration();
        String loanType = loanApplication.getLoanType();
        double interest =loanApplication.getInterestRate();

        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<RequestLoanresponse> call = apiRequests.requestLoans(access_token,userId,amount,duration,loanType);
        call.enqueue(new Callback<RequestLoanresponse>() {
            @Override
            public void onResponse(Call<RequestLoanresponse> call, Response<RequestLoanresponse> response) {
                if(response.code()== 200){

                    Bundle bundle = new Bundle();
                    assert getArguments() != null;
                    bundle.putString("loanApplicationId", String.valueOf(Integer.parseInt(response.body().getData().getLoanApplicationId())));
                }else if(response.code() == 401) {
                    WalletAuthActivity.startAuth(context, true);
                } else if (response.code() == 500) {
                        binding.textViewErrorMessage.setText("Error Occurred Try again later");
                        Log.e("info 500", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                    } else if (response.code() == 400) {
                    binding.textViewErrorMessage.setText(response.errorBody().toString());
                    Log.e("info 500", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                    } else if (response.code() == 406) {
                    binding.textViewErrorMessage.setText(response.errorBody().toString());
                    Log.e("info 406", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                    } else {
                        binding.textViewErrorMessage.setText("Error Occurred Try again later");
                        if (response.errorBody() != null) {
                            Log.e("info", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                        } else {
                            Log.e("info", "Something got very very wrong, code: " + response.code());
                        }
                    }
                    binding.textViewErrorMessage.setVisibility(View.VISIBLE);
                    dialog.dismiss();
                }


            @Override
            public void onFailure(Call<RequestLoanresponse> call, Throwable t) {

                Log.e("info : " , "Something got very very wrong");

                binding.textViewErrorMessage.setText("Error Occurred Try again later");
                binding.textViewErrorMessage.setVisibility(View.VISIBLE);
                dialog.dismiss();

            }
        });

    }
}
