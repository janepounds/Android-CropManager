package com.myfarmnow.myfarmcrop.fragments.buyInputsFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.Login;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.fragments.UpdateAccountFragment;
import com.myfarmnow.myfarmcrop.utils.Utilities;

public class MeFragment extends Fragment implements View.OnClickListener {


    TextView loginRegister;
    TextView aboutUs;
    TextView intro;
    TextView shareApp;
    TextView rateReview;
    TextView settings;
    TextView contactUS;
    ImageView languages;
    ImageView currencies;

    Activity activity;
    FragmentManager fm;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);

        activity= getActivity();
        fm=getActivity().getSupportFragmentManager();
        loginRegister = rootView.findViewById(R.id.loginRegister);
        aboutUs = rootView.findViewById(R.id.aboutUs);
        intro = rootView.findViewById(R.id.intro);
        shareApp = rootView.findViewById(R.id.shareApp);
        rateReview = rootView.findViewById(R.id.rateReview);
        settings = rootView.findViewById(R.id.settings);
        contactUS = rootView.findViewById(R.id.contactUS);
        languages = rootView.findViewById(R.id.languages);
        currencies = rootView.findViewById(R.id.currencies);

        loginRegister.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        intro.setOnClickListener(this);
        shareApp.setOnClickListener(this);
        rateReview.setOnClickListener(this);
        settings.setOnClickListener(this);
        contactUS.setOnClickListener(this);
        languages.setOnClickListener(this);
        currencies.setOnClickListener(this);

        if (ConstantValues.IS_USER_LOGGED_IN)
            loginRegister.setText(getString(R.string.actionAccount));

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginRegister:
                if (ConstantValues.IS_USER_LOGGED_IN){
                    fm
                            .beginTransaction()
                            .add(R.id.main_fragment_container, new UpdateAccountFragment())
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(null).commit();
                } else {
                    startActivity(new Intent(activity, Login.class));
                }
                break;
            case R.id.shareApp:
                Utilities.shareMyApp(activity);
                break;
            case R.id.rateReview:
//                Utilities.rateMyApp(activity);
                break;
            case R.id.settings:
                fm
                        .beginTransaction()
                        .add(R.id.main_fragment_container, new SettingsFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                break;
            case R.id.languages:
                fm
                        .beginTransaction()
                        .add(R.id.main_fragment_container, new Languages())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();
                break;
            case R.id.currencies:
                fm
                        .beginTransaction()
                        .add(R.id.main_fragment_container, new CurrencyFrag())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null).commit();

                break;
        }
    }
}
