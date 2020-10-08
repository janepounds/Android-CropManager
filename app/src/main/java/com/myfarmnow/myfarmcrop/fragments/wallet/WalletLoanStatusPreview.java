package com.myfarmnow.myfarmcrop.fragments.wallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;

import java.text.NumberFormat;


public class WalletLoanStatusPreview extends Fragment {
    private static final String TAG = "WalletLoanStatusPreview";
    private Context context;
    private AppBarConfiguration appBarConfiguration;

    private View mContentView;
    private View mControlsView;
    LoanApplication loanApplication;

    private Toolbar toolbar;
    private TextView textViewLoanStatusPreviewDueDate, textViewLoanStatusPreviewDuration, textViewLoanStatusPreviewStatus, textViewLoanStatusPreviewAmount,
            textViewLoanStatusEditPhotos, textViewLoanStatusPreviewInterestRate, textViewLoanStatusPreviewDueAmount, textViewLoanStatusPreviewPayments,
            textViewLoanStatusPreviewFines;
    private ImageView imageViewLoanStatusPreviewNidBack, imageViewLoanStatusPreviewNidFront, imageViewLoanStatusPreviewUserPhoto;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wallet_loanstatus_preview, container, false);

        toolbar = view.findViewById(R.id.toolbar_wallet_loan_status_preview);
        textViewLoanStatusPreviewDueDate = view.findViewById(R.id.text_view_loan_status_preview_due_date);
        textViewLoanStatusPreviewDuration = view.findViewById(R.id.text_view_loan_status_preview_duration);
        textViewLoanStatusPreviewStatus = view.findViewById(R.id.text_view_loan_status_preview_status);
        textViewLoanStatusPreviewAmount = view.findViewById(R.id.text_view_loan_status_preview_amount);
        textViewLoanStatusEditPhotos = view.findViewById(R.id.text_view_loan_status_edit_photos);
        textViewLoanStatusPreviewInterestRate = view.findViewById(R.id.text_view_loan_status_preview_interest_rate);
        textViewLoanStatusPreviewDueAmount = view.findViewById(R.id.text_view_loan_status_preview_due_amount);
        textViewLoanStatusPreviewPayments = view.findViewById(R.id.text_view_loan_status_preview_payments);
        textViewLoanStatusPreviewFines = view.findViewById(R.id.text_view_loan_status_preview_fines);
        imageViewLoanStatusPreviewNidBack = view.findViewById(R.id.image_view_loan_status_preview_nid_back);
        imageViewLoanStatusPreviewNidFront = view.findViewById(R.id.image_view_loan_status_preview_nid_front);
        imageViewLoanStatusPreviewUserPhoto = view.findViewById(R.id.image_view_loan_status_preview_user_photo);

//        assert getArguments() != null;
//        loanApplication = (LoanApplication) getArguments().getSerializable("loanApplication");

//        initializeActivity();

        return view;
    }

    public void initializeActivity() {
        textViewLoanStatusPreviewDuration.setText(loanApplication.getDuration() + " " + loanApplication.getDurationLabel());
        //referee1ImageView, referee2ImageView
        textViewLoanStatusPreviewDueDate.setText(loanApplication.getDueDate());
        textViewLoanStatusPreviewStatus.setText(loanApplication.getStatus());
        textViewLoanStatusPreviewAmount.setText("UGX " + NumberFormat.getInstance().format(loanApplication.getAmount()));

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
                .into(imageViewLoanStatusPreviewNidBack);

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
                .into(imageViewLoanStatusPreviewNidFront);

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
                .into(imageViewLoanStatusPreviewUserPhoto);

        textViewLoanStatusEditPhotos.setOnClickListener(v -> {
//                Intent startNext = new Intent(LoanStatusPreviewActivity.this,WalletLoanAppPhotos.class);
//                startNext.putExtra("loanApplication",loanApplication);
//                startNext.putExtra("isEdit",true);
//                startActivity(startNext);
        });

        textViewLoanStatusPreviewInterestRate.setText(loanApplication.getInterestRate() + "%");
        textViewLoanStatusPreviewDueAmount.setText("UGX " + NumberFormat.getInstance().format(loanApplication.getDueAmount()));
        textViewLoanStatusPreviewPayments.setText("UGX " + NumberFormat.getInstance().format(loanApplication.getAmountPaid()));
        textViewLoanStatusPreviewFines.setText("UGX " + NumberFormat.getInstance().format(loanApplication.getTotalFines()));

        if (!loanApplication.isEditable()) {
            textViewLoanStatusEditPhotos.setVisibility(View.GONE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        // Set up the user interaction to manually show or hide the system UI.

//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }
}