package com.myfarmnow.myfarmcrop.popupDailogs.wallet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.WalletPaymentMethodsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DepositPayments extends DialogFragment {
    private static final String TAG = "DepositPayments";
    private Context context;
    private double balance;

    private WalletPaymentMethodsBinding binding;
    private NavController navController = null;
    AppBarConfiguration appBarConfiguration;

    LinearLayout layoutAddMoney;
    Button addMoneyImg;
    TextView addMoneyTxt, phoneNumberTxt, errorMsgTxt;
    static String PENDING_DEPOSIT_REFERENCE_NUMBER;
    TextView balanceTextView, titleTextView;

    ProgressDialog dialog;

    public static DepositPayments newInstance(double balance) {
        DepositPayments dialog = new DepositPayments();
        Bundle bundle = new Bundle();
        bundle.putDouble("balance", balance);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.wallet_payment_methods, null, false);

        assert getArguments() != null;
        balance = getArguments().getFloat("interest");

        return new AlertDialog.Builder(context).setView(binding.getRoot()).create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
