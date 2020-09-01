package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.myfarmnow.myfarmcrop.databinding.WalletLoanstatusPreviewBinding;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;

import java.text.NumberFormat;


public class WalletLoanStatusPreviw extends Fragment {


    private View mContentView;
    private View mControlsView;
    LoanApplication loanApplication;
    private WalletLoanstatusPreviewBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.wallet_loanstatus_preview, container, false);

        assert getArguments() != null;
        loanApplication = (LoanApplication) getArguments().getSerializable("loanApplication");

        initializeActivity();


        return binding.getRoot();
    }

    public void initializeActivity(){


        binding.textViewLoanStatusPreviewDuration.setText(loanApplication.getDuration()+" "+loanApplication.getDurationLabel());
        //referee1ImageView, referee2ImageView
        binding.textViewLoanStatusPreviewDueDate.setText(loanApplication.getDueDate());
        binding.textViewLoanStatusPreviewStatus.setText(loanApplication.getStatus());
        binding.textViewLoanStatusPreviewAmount.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.getAmount()));

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
                .into(binding.imageViewLoanStatusPreviewNidBack);

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
                .into(binding.imageViewLoanStatusPreviewNidFront);

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
                .into(binding.imageViewLoanStatusPreviewUserPhoto);

        binding.textViewLoanStatusEditPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent startNext = new Intent(LoanStatusPreviewActivity.this,WalletLoanAppPhotos.class);
//                startNext.putExtra("loanApplication",loanApplication);
//                startNext.putExtra("isEdit",true);
//                startActivity(startNext);
            }
        });


        binding.textViewLoanStatusPreviewInterestRate.setText(loanApplication.getInterestRate()+"%");
        binding.textViewLoanStatusPreviewDueAmount.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.getDueAmount()));
        binding.textViewLoanStatusPreviewPayments.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.getAmountPaid()));
        binding.textViewLoanStatusPreviewFines.setText("UGX "+ NumberFormat.getInstance().format(loanApplication.getTotalFines()));

        if(!loanApplication.isEditable()){
            binding.textViewLoanStatusEditPhotos.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && getActivity().getWindow() != null) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity() != null && getActivity().getWindow() != null) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

            // Clear the systemUiVisibility flag
            getActivity().getWindow().getDecorView().setSystemUiVisibility(0);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContentView = null;
        mControlsView = null;
    }


}