package com.myfarmnow.myfarmcrop.activities;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.myfarmnow.myfarmcrop.R;

public class CropDigitalWalletWithdrawActivity extends AppCompatActivity {

    LinearLayout layoutWithdraw, layoutAddMoney, layoutTransfer;
    Button addMoneyBtn, withdrawBtn, transferBtn;
    Spinner countryCodeSp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_digital_wallet_withdraw);
    initializeForm();
    }

    public void initializeForm() {
        layoutAddMoney = findViewById(R.id.layout_add_money);
        layoutWithdraw = findViewById(R.id.layout_withdraw);

        addMoneyBtn = findViewById(R.id.button_add_money);
        withdrawBtn = findViewById(R.id.button_withdraw);


        countryCodeSp = findViewById(R.id.sp_crop_digital_wallet_country_code);

    layoutAddMoney.setVisibility(View.GONE);
    layoutWithdraw.setVisibility(View.VISIBLE );

    addMoneyBtn.setVisibility(View.GONE);
    withdrawBtn.setVisibility(View.VISIBLE);

        ((ArrayAdapter)countryCodeSp.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_item);

        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getColor(R.color.colorPrimary));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.colorPrimary)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,12);//Change selected text size
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        countryCodeSp.setOnItemSelectedListener(onItemSelectedListener);

    }
}
