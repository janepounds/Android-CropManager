package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletHomeActivity;
import com.myfarmnow.myfarmcrop.adapters.wallet.LoansListAdapter;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.LoanListResponse;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.myfarmnow.myfarmcrop.DailogFragments.wallet.PayLoan;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletLoansListFragment extends Fragment {
    private static final String TAG = "WalletLoansList";
    private Context context;

    AppBarConfiguration appBarConfiguration;

    LoansListAdapter statementAdapter;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    private List<LoanApplication> dataList = new ArrayList<>();
    private float interest;

    private Toolbar toolbar;
    private FrameLayout walletPayLoanLayout, walletApplyLoanLayout;
    private RecyclerView loansListRecyclerView;
    private Button walletApplyLoanBtn, walletPayLoanBtn;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_loans_list, container, false);

        toolbar = view.findViewById(R.id.toolbar_wallet_loans_list);
        walletApplyLoanLayout = view.findViewById(R.id.wallet_apply_loan_layout);
        walletPayLoanLayout = view.findViewById(R.id.wallet_pay_loan_layout);
        loansListRecyclerView = view.findViewById(R.id.loans_list_recycler_view);
        walletApplyLoanBtn = view.findViewById(R.id.wallet_apply_loan_btn);
        walletPayLoanBtn = view.findViewById(R.id.wallet_pay_loan_btn);

        walletPayLoanLayout.setVisibility(View.GONE);
        loansListRecyclerView.setLayoutManager(new LinearLayoutManager(context));

        statementAdapter = new LoansListAdapter(dataList);
        loansListRecyclerView.setAdapter(statementAdapter);

        actualStatementData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        Bundle bundle = new Bundle();
        bundle.putFloat("interest", interest);
        walletApplyLoanBtn.setOnClickListener(view1 -> navController.navigate(R.id.action_walletLoansListFragment_to_walletLoanAppInitiateFragment, bundle));
        walletPayLoanBtn.setOnClickListener(view2 -> payLoan());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void actualStatementData() {

        ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        dialog.show();

        /*************RETROFIT IMPLEMENTATION********************/

        String access_token = WalletAuthActivity.WALLET_ACCESS_TOKEN;
        String userId = WalletHomeActivity.getPreferences(DashboardActivity.PREFERENCES_USER_ID, context);
        APIRequests apiRequests = APIClient.getWalletInstance();

        Call<LoanListResponse> call = apiRequests.getUserLoans(userId);

        call.enqueue(new Callback<LoanListResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoanListResponse> call, @NotNull Response<LoanListResponse> response) {
                Log.d(TAG, "onResponse: Call Successful");

                Log.d(TAG, "onResponse: Code = " + response.code());

                dialog.dismiss();

                if (response.code() == 200) {

                        LoanApplication data = null;
                        List<LoanListResponse.Loans> loans = response.body().getLoans();

                        interest = (float) response.body().getInterest();

                        for (int i = 0; i < loans.size(); i++) {

                            LoanListResponse.Loans record = loans.get(i);
                            Gson gson = new Gson();
                            String res = gson.toJson(record);
                            JSONObject jsonObject = null;

                            try {
                                jsonObject = new JSONObject(res);
                                data = new LoanApplication(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            
                            dataList.add(data);
                            Log.d(TAG, "onResponse: Data = " + data);

//                            if (data.getStatus().equals("Approved") || data.getStatus().equals("Partially Paid")) {
//                                walletApplyLoanLayout.setVisibility(View.GONE);
//                                walletPayLoanLayout.setVisibility(View.VISIBLE);
//                            }

                        }
                        statementAdapter.notifyDataSetChanged();

                } else if (response.code() == 401) {

                    if (response.errorBody() != null) {
                        Log.e("info", String.valueOf(response.errorBody()));
                    } else {
                        Log.e("info", "Something got very very wrong");
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoanListResponse> call, @NotNull Throwable t) {

                Log.e("info : ", new String(String.valueOf(t.getMessage())));
                Log.e("info : ", "Something got very very wrong");

                dialog.dismiss();
                WalletAuthActivity.startAuth(context, true);
            }
        });
    }

    public void payLoan() {
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment payLoandialog = new PayLoan(context, fm);
        payLoandialog.show(ft, "dialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        dataList.clear();
    }
}