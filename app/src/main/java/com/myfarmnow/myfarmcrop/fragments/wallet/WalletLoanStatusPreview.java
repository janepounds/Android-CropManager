package com.myfarmnow.myfarmcrop.fragments.wallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
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


public class WalletLoanStatusPreview extends Fragment {
    private static final String TAG = "WalletLoanStatusPreview";
    private Context context;
    private AppBarConfiguration appBarConfiguration;
    private WalletLoanstatusPreviewBinding binding;

    private View mContentView;
    private View mControlsView;
    LoanApplication loanApplication;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.wallet_loanstatus_preview, container, false);

//        assert getArguments() != null;
//        loanApplication = (LoanApplication) getArguments().getSerializable("loanApplication");

//        initializeActivity();


        return binding.getRoot();
    }

    public void initializeActivity() {
        binding.textViewLoanStatusPreviewDuration.setText(loanApplication.getDuration() + " " + loanApplication.getDurationLabel());
        //referee1ImageView, referee2ImageView
        binding.textViewLoanStatusPreviewDueDate.setText(loanApplication.getDueDate());
        binding.textViewLoanStatusPreviewStatus.setText(loanApplication.getStatus());
        binding.textViewLoanStatusPreviewAmount.setText("UGX " + NumberFormat.getInstance().format(loanApplication.getAmount()));

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


        binding.textViewLoanStatusPreviewInterestRate.setText(loanApplication.getInterestRate() + "%");
        binding.textViewLoanStatusPreviewDueAmount.setText("UGX " + NumberFormat.getInstance().format(loanApplication.getDueAmount()));
        binding.textViewLoanStatusPreviewPayments.setText("UGX " + NumberFormat.getInstance().format(loanApplication.getAmountPaid()));
        binding.textViewLoanStatusPreviewFines.setText("UGX " + NumberFormat.getInstance().format(loanApplication.getTotalFines()));

        if (!loanApplication.isEditable()) {
            binding.textViewLoanStatusEditPhotos.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        // Set up the user interaction to manually show or hide the system UI.

//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }
}