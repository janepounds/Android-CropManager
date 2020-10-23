package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.myfarmnow.myfarmcrop.R;


public class PaymentMethodsFragment extends Fragment {
    private View rootView;
    private RadioButton cashOnDelivery,eMaishaWallet,eMaishaCard,Visa,MobileMoney;
    private LinearLayout merchantCard,VisaCard,MobileM;

    public PaymentMethodsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_payment_methods, container, false);
        // Inflate the layout for this fragment


        cashOnDelivery = rootView.findViewById(R.id.radio_btn_cash_on_delivery);
        eMaishaWallet = rootView.findViewById(R.id.radio_btn_emaisha_wallet);
        eMaishaCard = rootView.findViewById(R.id.radio_btn_merchant_card);
        Visa = rootView.findViewById(R.id.radio_btn_visa_card);
        MobileMoney = rootView.findViewById(R.id.radio_btn_mobile_money);
        merchantCard = rootView.findViewById(R.id.layout_merchant_card_details);
        VisaCard = rootView.findViewById(R.id.layout_visa_card_details);
        MobileM = rootView.findViewById(R.id.layout_mobile_money_number);


        cashOnDelivery.setOnClickListener(v -> {
            MobileMoney.setChecked(false);
            eMaishaWallet.setChecked(false);
            eMaishaCard.setChecked(false);
            Visa.setChecked(false);
            MobileM.setVisibility(v.GONE);
            VisaCard.setVisibility(v.GONE);
            merchantCard.setVisibility(v.GONE);

        });
        eMaishaWallet.setOnClickListener(v -> {
            cashOnDelivery.setChecked(false);
            MobileMoney.setChecked(false);
            eMaishaCard.setChecked(false);
            Visa.setChecked(false);
            MobileM.setVisibility(v.GONE);
            VisaCard.setVisibility(v.GONE);
            merchantCard.setVisibility(v.GONE);
        });

        eMaishaCard.setOnClickListener(v ->{
            merchantCard.setVisibility(v.VISIBLE);
            cashOnDelivery.setChecked(false);
            eMaishaWallet.setChecked(false);
            MobileMoney.setChecked(false);
            Visa.setChecked(false);
            MobileM.setVisibility(v.GONE);
            VisaCard.setVisibility(v.GONE);

        });

        Visa.setOnClickListener(v ->{
            VisaCard.setVisibility(v.VISIBLE);
            cashOnDelivery.setChecked(false);
            eMaishaWallet.setChecked(false);
            eMaishaCard.setChecked(false);
            MobileMoney.setChecked(false);
            MobileM.setVisibility(v.GONE);
            merchantCard.setVisibility(v.GONE);
        });

        MobileMoney.setOnClickListener(v->{
            MobileM.setVisibility(v.VISIBLE);
            cashOnDelivery.setChecked(false);
            eMaishaWallet.setChecked(false);
            eMaishaCard.setChecked(false);
            Visa.setChecked(false);
            VisaCard.setVisibility(v.GONE);
            merchantCard.setVisibility(v.GONE);
        });




        return rootView;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void loadPayments(){


    }
}