package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.myfarmnow.myfarmcrop.R;

public class CropDigitalWalletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_digital_wallet);
    }

    public void openAddMoney(View view){

        Intent openAM = new Intent(this,CropDigitalWalletAddMoneyActivity.class);
        startActivity(openAM);
    }
    public void openWithdraw(View view){

        Intent openW = new Intent(this, CropDigitalWalletWithdrawActivity.class);
        startActivity(openW);
    }
    public void openTransfer(View view){
        Intent openT = new Intent(this,CropDigitalWalletTransferActivity.class);
        startActivity(openT);
    }
}
