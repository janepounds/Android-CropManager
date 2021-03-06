package com.myfarmnow.myfarmcrop.fragments.wallet;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.helpers.ImageUtils;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.RequestLoanresponse;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.myfarmnow.myfarmcrop.models.wallet.Referee;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletLoanKycDetailsFragment extends Fragment {
    private static final String TAG = "WalletLoanKycDetailsFragment";
    private Context context;

    NavController navController;
    AppBarConfiguration appBarConfiguration;

    private static final int GALLERY_REQUESTED_NID_BACK = 1;
    private static final int GALLERY_REQUESTED_NID_FRONT = 2;
    private static final int GALLERY_REQUESTED_SELFIE = 3;
    private static final int GALLERY_REQUESTED_FARM_PHOTO = 5;

    ProgressDialog dialog;
    String[] descriptionData = {"Loan\nDetails", "Farming\nDetails", "Preview", "KYC\nDetails"};
    String filePath, encodedFrontIdImageID,encodedBackIdImageID,encodedSelfieIdImageID,encodedlfarmImage;

    Bitmap selectedImage;
    LoanApplication loanApplication;

    private Toolbar toolbar;
    private StateProgressBar loanProgressBarId;
    private Button btnLoanNextStep;

    private EditText first_name_edt1, first_name_edt2, last_name_edt1, last_name_edt2,guarantor_contact_edt1, guarantor_contact_edt2;
    private TextView  textViewErrorMessage,front_id_photo_browse_tv, front_id_photo_tv,back_id_photo_tv, back_id_photo_browse_tv,
            selfie_id_photo_tv, selfie_id_photo_browse_tv,farm_photo_browse_tv, farm_photo_tv;
    private Spinner guarantor_relationship_spn1, guarantor_relationship_spn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wallet_loan_kyc_details, container, false);
        toolbar = view.findViewById(R.id.toolbar_wallet_loan_app_photos);
        loanProgressBarId = view.findViewById(R.id.loan_app_photos_progress_bar_id);
        btnLoanNextStep = view.findViewById(R.id.btn_loan_app_photos_next_step);
        front_id_photo_browse_tv= view.findViewById(R.id.front_id_photo_browse_tv);
        front_id_photo_tv= view.findViewById(R.id.front_id_photo_tv);
        back_id_photo_tv= view.findViewById(R.id.back_id_photo_tv);
        back_id_photo_browse_tv= view.findViewById(R.id.back_id_photo_browse_tv);
        selfie_id_photo_browse_tv= view.findViewById(R.id.selfie_id_photo_browse_tv);
        selfie_id_photo_tv= view.findViewById(R.id.selfie_id_photo_tv);
        farm_photo_tv= view.findViewById(R.id.farm_photo_tv);
        farm_photo_browse_tv= view.findViewById(R.id.farm_photo_browse_tv);

        first_name_edt1= view.findViewById(R.id.first_name_edt1);
        first_name_edt2= view.findViewById(R.id.first_name_edt2);
        last_name_edt1= view.findViewById(R.id.last_name_edt1);
        last_name_edt2= view.findViewById(R.id.last_name_edt2);
        guarantor_relationship_spn1= view.findViewById(R.id.guarantor_relationship_spn1);
        guarantor_relationship_spn2= view.findViewById(R.id.guarantor_relationship_spn2);
        guarantor_contact_edt1= view.findViewById(R.id.guarantor_contact_edt1);
        guarantor_contact_edt2= view.findViewById(R.id.guarantor_contact_edt2);

        textViewErrorMessage = view.findViewById(R.id.text_view_error_message);
        loanProgressBarId.setStateDescriptionTypeface("fonts/JosefinSans-SemiBold.ttf");
        assert getArguments() != null;
         if (getArguments().getSerializable("loanApplication") != null) {
            loanApplication = (LoanApplication) getArguments().getSerializable("loanApplication");
             double interest = loanApplication.getInterestRate();
        } else {
            requireActivity().finish();
        }

        initializeActivity();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void initializeActivity() {
        dialog = new ProgressDialog(context);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(true);

        loanProgressBarId.setStateDescriptionData(descriptionData);

        if (loanApplication != null) {
            loanProgressBarId.setVisibility(View.GONE);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.add_default_image)
                    .error(R.drawable.add_default_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);



//            Glide.with(this)
//                    .setDefaultRequestOptions(options)
//                    .load(loanApplication.getUserPhotoPic())
//                    .addListener(new RequestListener<Drawable>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                            //bannerItemShimmer.stopShimmer();
//                            //bannerItemShimmer.setVisibility(View.GONE);
//
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//
//                            return false;
//                        }
//                    })
//                    .into(imageViewLoanPhotosSelfie);

        }

        front_id_photo_browse_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, GALLERY_REQUESTED_NID_FRONT);
            } //front_id_photo_tv
        });
        back_id_photo_browse_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, GALLERY_REQUESTED_NID_BACK);
            }
        });
        selfie_id_photo_browse_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, GALLERY_REQUESTED_SELFIE);
            }
        });
        farm_photo_browse_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageSelectActivity.class);
                intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
                intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
                startActivityForResult(intent, GALLERY_REQUESTED_FARM_PHOTO);
            }
        });

        btnLoanNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initiateApplication();
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK)
        switch (requestCode) {
            case GALLERY_REQUESTED_NID_BACK:
                filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                selectedImage = BitmapFactory.decodeFile(filePath);
                encodedBackIdImageID = encodeImage(selectedImage);
                back_id_photo_tv.setText(filePath);
                break;
            case GALLERY_REQUESTED_NID_FRONT:
                filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                selectedImage = BitmapFactory.decodeFile(filePath);
                encodedFrontIdImageID = encodeImage(selectedImage);
                front_id_photo_tv.setText(filePath);
                break;
            case GALLERY_REQUESTED_SELFIE:
                filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                selectedImage = BitmapFactory.decodeFile(filePath);
                encodedSelfieIdImageID = encodeImage(selectedImage);
                selfie_id_photo_tv.setText(filePath);
                break;
            case GALLERY_REQUESTED_FARM_PHOTO:
                filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
                selectedImage = BitmapFactory.decodeFile(filePath);
                encodedlfarmImage = encodeImage(selectedImage);
                farm_photo_tv.setText(filePath);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }



    public void initiateApplication() {
        /*****************RETROFIT IMPLEMENTATION*******************/
        String access_token = WalletAuthActivity.WALLET_ACCESS_TOKEN;

        JSONObject requestObject = new JSONObject();
        try {
            dialog.show();

            if (!encodedBackIdImageID.isEmpty()) {
                loanApplication.setNationalIDBackPic(encodedBackIdImageID);
            }
            if (!encodedFrontIdImageID.isEmpty()) {
                loanApplication.setNationalIDFrontPic(encodedFrontIdImageID);
            }
            if (!encodedSelfieIdImageID.isEmpty()) {
                loanApplication.setUserPhotoPic(encodedSelfieIdImageID);
            }
            if (!encodedlfarmImage.isEmpty()) {
                loanApplication.setFarm_photo(encodedlfarmImage);
            }
            if(isValid()){
                loanApplication.setLoan_gaurantor1(new Referee(first_name_edt1.getText().toString(), last_name_edt1.getText().toString(), guarantor_relationship_spn1.getSelectedItem().toString(), guarantor_contact_edt1.getText().toString()));
                loanApplication.setLoan_gaurantor2(new Referee(first_name_edt2.getText().toString(), last_name_edt2.getText().toString(), guarantor_relationship_spn2.getSelectedItem().toString(), guarantor_contact_edt2.getText().toString()));

            }

            requestObject.put("loanParams", loanApplication);

        }catch (Exception e){
            Log.w("Error", e.getMessage());
        }


        APIRequests apiRequests = APIClient.getWalletInstance();
        Call<RequestLoanresponse> call = apiRequests.requestLoans(access_token, requestObject);
        call.enqueue(new Callback<RequestLoanresponse>() {
            @Override
            public void onResponse(Call<RequestLoanresponse> call, Response<RequestLoanresponse> response) {
                if (response.code() == 200) {
                    if( response.body().getData().getLoanApplicationId().equals("0") ){
                        Log.e("info 200", new String(String.valueOf(response.toString())) + ", code: " + response.code());
                        textViewErrorMessage.setText(response.body().getData().getMessage());
                    }else {

                        navController.navigate(R.id.action_walletLoanAppPhotosFragment_to_walletLoansListFragment);
                    }

                } else if (response.code() == 401) {
                    WalletAuthActivity.startAuth(context, true);
                } else if (response.code() == 500) {
                    textViewErrorMessage.setText("Error Occurred Try again later");
                    Log.e("info 500", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                } else if (response.code() == 400) {
                    Log.e("info 400", new String(String.valueOf(response.toString())) + ", code: " + response.code());
                    textViewErrorMessage.setText(response.body().getData().getMessage());
                } else if (response.code() == 406) {
                    textViewErrorMessage.setText(response.errorBody().toString());
                    Log.e("info 406", new String(String.valueOf(response.toString())) + ", code: " + response.code());
                } else {
                    textViewErrorMessage.setText("Error Occurred Try again later");
                    if (response.errorBody() != null) {
                        Log.e("info", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                    } else {
                        Log.e("info", "Something got very very wrong, code: " + response.code());
                    }
                }
                textViewErrorMessage.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }


            @Override
            public void onFailure(Call<RequestLoanresponse> call, Throwable t) {

                Log.e("info : ", "Something got very very wrong");

                textViewErrorMessage.setText("Error Occurred Try again later");
                textViewErrorMessage.setVisibility(View.VISIBLE);
                dialog.dismiss();

            }
        });

    }

    private boolean isValid() {
        if(first_name_edt1.getText().toString().isEmpty() || first_name_edt2.getText().toString().isEmpty() || last_name_edt1.getText().toString().isEmpty()
         || last_name_edt2.getText().toString().isEmpty()
         || guarantor_relationship_spn1.getSelectedItem().toString().equalsIgnoreCase("select")
         || guarantor_relationship_spn2.getSelectedItem().toString().equalsIgnoreCase("select")
         || guarantor_contact_edt1.getText().toString().isEmpty()  || guarantor_contact_edt2.getText().toString().isEmpty() ){
            return false;
        }


        return true;
    }


    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        return outputStream.toByteArray();
    }

}