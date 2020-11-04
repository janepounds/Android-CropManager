package com.myfarmnow.myfarmcrop.DailogFragments.wallet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.myfarmnow.myfarmcrop.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DepositPayments extends DialogFragment {
    private static final String TAG = "DepositPayments";
    private Context context;
    private double balance;

    private NavController navController;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.wallet_payment_methods, null);
        builder.setView(view);

        ImageView close = view.findViewById(R.id.wallet_deposit_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                requireContext().
            }
        });

        assert getArguments() != null;
        balance = getArguments().getFloat("interest");

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
