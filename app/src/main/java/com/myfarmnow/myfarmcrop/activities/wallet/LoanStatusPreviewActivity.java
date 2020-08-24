package com.cabral.emaisha.wallet.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cabral.emaisha.constant.ConstantValues;
import com.cabral.emaisha.wallet.models.LoanApplication;
import com.cabral.emaisha.wallet.models.Referee;
import com.cabral.emaisha.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class LoanStatusPreviewActivity extends AppCompatActivity {

    TextView amountTxt, dueAmountTxt, dueDateTxt, interestRateTxt,durationTxt, dateTxt, statusTxt, paymentsTxt,finesTxt, editTxt;
    LoanApplication loanApplication;
    ImageView nidBackImageView, nidFrontImageView, userPhotoImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_loan_status_preview);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getIntent().hasExtra("loanApplication")){
            loanApplication = (LoanApplication)getIntent().getSerializableExtra("loanApplication");
        }
        else{
            finish();
        }
        initializeActivity();

    }

    public void initializeActivity(){
        amountTxt = findViewById(R.id.text_view_loan_status_preview_amount);
        dueAmountTxt = findViewById(R.id.text_view_loan_status_preview_due_amount);
        dueDateTxt = findViewById(R.id.text_view_loan_status_preview_due_date);
        interestRateTxt = findViewById(R.id.text_view_loan_status_preview_interest_rate);
        durationTxt = findViewById(R.id.text_view_loan_status_preview_duration);
        dateTxt = findViewById(R.id.text_view_loan_status_preview_date);
        statusTxt = findViewById(R.id.text_view_loan_status_preview_status);
        nidFrontImageView = findViewById(R.id.image_view_loan_status_preview_nid_front);
        nidBackImageView = findViewById(R.id.image_view_loan_status_preview_nid_back);
        userPhotoImageView = findViewById(R.id.image_view_loan_status_preview_user_photo);
        editTxt = findViewById(R.id.text_view_loan_status_edit_photos);
        paymentsTxt= findViewById(R.id.text_view_loan_status_preview_payments);
        finesTxt= findViewById(R.id.text_view_loan_status_preview_fines);

        durationTxt.setText(loanApplication.getDuration()+" "+loanApplication.getDurationLabel());
        //referee1ImageView, referee2ImageView
        dueDateTxt.setText(loanApplication.getDueDate());
        statusTxt.setText(loanApplication.getStatus());
        amountTxt.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.getAmount()));

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.add_default_image)
                .error(R.drawable.add_default_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(this)
                .setDefaultRequestOptions(options)
                .load(loanApplication.getNationalIDBackPic())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //bannerItemShimmer.stopShimmer();
                        //bannerItemShimmer.setVisibility(View.GONE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(nidBackImageView);

        Glide.with(this)
                .setDefaultRequestOptions(options)
                .load(loanApplication.getNationalIDFrontPic())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //bannerItemShimmer.stopShimmer();
                        //bannerItemShimmer.setVisibility(View.GONE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(nidFrontImageView);

        Glide.with(this)
                .setDefaultRequestOptions(options)
                .load(loanApplication.getUserPhotoPic())
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //bannerItemShimmer.stopShimmer();
                        //bannerItemShimmer.setVisibility(View.GONE);

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(userPhotoImageView);

        editTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startNext = new Intent(LoanStatusPreviewActivity.this,WalletLoanAppPhotos.class);
                startNext.putExtra("loanApplication",loanApplication);
                startNext.putExtra("isEdit",true);
                startActivity(startNext);
            }
        });

//        if(!loanApplication.isApproved()){
//            loanApplication.setInterestRate(8);
//            interestRateTxt.setText(loanApplication.getInterestRate()+"%");
//            dueAmountTxt.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.computeDueAmount()));
//            for(View view:getViewsByTag((ViewGroup) findViewById(R.id.text_view_loan_status_layout),"approved")){
//                view.setVisibility(View.GONE);
//            }
//        }

            interestRateTxt.setText(loanApplication.getInterestRate()+"%");
            interestRateTxt.setText(loanApplication.getInterestRate()+"%");
            dueAmountTxt.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.getDueAmount()));
            paymentsTxt.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.getAmountPaid()));
            finesTxt.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.getTotalFines()));

        if(!loanApplication.isEditable()){
            editTxt.setVisibility(View.GONE);
        }
    }

    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }

}
