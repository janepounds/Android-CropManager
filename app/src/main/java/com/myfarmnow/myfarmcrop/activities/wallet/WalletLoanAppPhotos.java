package com.myfarmnow.myfarmcrop.activities.wallet;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.helpers.ImageUtils;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class WalletLoanAppPhotos extends AppCompatActivity {
    Bitmap bitmap;
    private static final int GALLERY_REQUESTED_NID_BACK = 1 ;
    private static final int GALLERY_REQUESTED_NID_FRONT = 2 ;
    private static final int GALLERY_REQUESTED_SELFIE = 3 ;
    private static final int CAMERA_REQUESTED_NID_BACK = 4 ;
    private static final int CAMERA_REQUESTED_NID_FRONT = 5 ;
    private static final int CAMERA_REQUESTED_SELFIE = 6 ;
    private static final int REQUESTED_SELFIE =7;
    private static final int REQUESTED_NID_BACK =8;
    private static final int REQUESTED_NID_FRONT =9;

    ProgressDialog dialog;

    Button nextStepBtn;
    String[] descriptionData = {"Amount", "Confirm","Photo", "Referees"};

    ImageView selfieImageView, nidBackImageView, nidFrontImageView;

    Boolean isNidFrontAdded=false,isNidbackAdded=false,isSelfieAdded=false;
    TextView errorMsgTxt, titleTxt;
    String loanApplicationId;
    LoanApplication loanApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_loan_app_photos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("loanApplicationId")){
            loanApplicationId = getIntent().getStringExtra("loanApplicationId");
        }
        else if(getIntent().hasExtra("loanApplication")){
            loanApplication=(LoanApplication) getIntent().getSerializableExtra("loanApplication");
            loanApplicationId = loanApplication.getId();
        }
        else{
            finish();
        }
        initializeActivity();
    }

    public void initializeActivity(){
        dialog = new ProgressDialog(WalletLoanAppPhotos.this);
        dialog.setIndeterminate(true);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        StateProgressBar stateProgressBar =  findViewById(R.id.loan_progress_bar_id);
        stateProgressBar.setStateDescriptionData(descriptionData);
        errorMsgTxt = findViewById(R.id.text_view_error_message);
        nextStepBtn = findViewById(R.id.btn_loan_next_step);
        selfieImageView = findViewById(R.id.image_view_loan_photos_selfie);
        nidBackImageView = findViewById(R.id.image_view_loan_photos_nid_back);
        nidFrontImageView = findViewById(R.id.image_view_loan_photos_nid_front);
        titleTxt = findViewById(R.id.text_view_loan_photos_title);
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPhotos();
            }
        });

        nidFrontImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog(REQUESTED_NID_FRONT);
            }
        });
        nidBackImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog(REQUESTED_NID_BACK);
            }
        });
        selfieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog(REQUESTED_SELFIE);
            }
        });

        if(loanApplication != null){
            stateProgressBar.setVisibility(View.GONE);

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
                    .into(selfieImageView);

            nextStepBtn.setText("Update");
            titleTxt.setText("Update Photos");
        }
    }

    public void submitPhotos(){
        JSONObject requestObject = new JSONObject();

        if(isNidFrontAdded || isSelfieAdded || isNidbackAdded){

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(20000);
            client.addHeader("Authorization","Bearer "+ WalletAuthActivity.WALLET_ACCESS_TOKEN);

            try {
                dialog.show();
                requestObject.put("loanApplicationId",loanApplicationId);

                if(isNidFrontAdded){
                    requestObject.put("nationalIdPicBack", Base64.encodeToString(getBitmapAsByteArray(((BitmapDrawable)nidBackImageView.getDrawable()).getBitmap()), Base64.DEFAULT));
                }

                if(isSelfieAdded){
                    requestObject.put("nationalIdPicFront", Base64.encodeToString(getBitmapAsByteArray(((BitmapDrawable)nidFrontImageView.getDrawable()).getBitmap()), Base64.DEFAULT));
                }
                if(isSelfieAdded){
                    requestObject.put("userPhoto", Base64.encodeToString(getBitmapAsByteArray(((BitmapDrawable)selfieImageView.getDrawable()).getBitmap()), Base64.DEFAULT));
                }

                HttpEntity params ;
                try {
                    params = new StringEntity(requestObject.toString());
                    ((StringEntity) params).setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return;
                }


                client.post(WalletLoanAppPhotos.this, ApiPaths.WALLET_LOAN_APPLICATION_ADD_PHOTOS,params,"application/json", new JsonHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        dialog.show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Intent startNext = new Intent(WalletLoanAppPhotos.this,WalletLoansListActivity.class);
                        startNext.putExtra("loanApplicationId",loanApplicationId);
                        startNext.putExtra("refereeNumber",1);
                        startActivity(startNext);
                        dialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        if(statusCode==401){
                            WalletAuthActivity.startAuth(WalletLoanAppPhotos.this, true);
                        }else if(statusCode == 500){
                            errorMsgTxt.setText("Error Occurred Try again later");
                            Log.e("info 500", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                        }
                        else if(statusCode == 400){
                            try {
                                errorMsgTxt.setText(errorResponse.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("info 500", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                        }
                        else if(statusCode == 406){
                            try {
                                errorMsgTxt.setText(errorResponse.getString("message"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("info 406", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                        }
                        else {
                            errorMsgTxt.setText("Error Occurred Try again later");
                            if (errorResponse != null) {
                                Log.e("info", new String(String.valueOf(errorResponse))+", code: "+statusCode);
                            } else {
                                Log.e("info", "Something got very wrong, code: "+statusCode);
                            }
                        }
                        errorMsgTxt.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String errorResponse, Throwable throwable) {
                        if (errorResponse != null) {
                            Log.e("info 1A: "+statusCode, new String(String.valueOf(errorResponse)));
                        } else {
                            Log.e("info 1A: "+statusCode, "Something got very very wrong");
                        }
                       
                        dialog.dismiss();

                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void choosePhotoFromGallery(int typeRequested) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, typeRequested);
    }

    private void takePhotoFromCamera(int typeRequested) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, typeRequested);
    }

    private void showPictureDialog(final int imageTypeRequested){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select from gallery",
                "Take with camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                if(imageTypeRequested==REQUESTED_NID_BACK){
                                    choosePhotoFromGallery(GALLERY_REQUESTED_NID_BACK);
                                }else if( imageTypeRequested==REQUESTED_NID_FRONT){
                                    choosePhotoFromGallery(GALLERY_REQUESTED_NID_FRONT);
                                }
                                else if( imageTypeRequested==REQUESTED_SELFIE){
                                    choosePhotoFromGallery(GALLERY_REQUESTED_SELFIE);
                                }
                                break;
                            case 1:

                                int type =0;
                                if(imageTypeRequested==REQUESTED_NID_BACK){
                                    type = CAMERA_REQUESTED_NID_BACK;
                                }else if( imageTypeRequested==REQUESTED_NID_FRONT){
                                    type = CAMERA_REQUESTED_NID_FRONT;
                                }
                                else if( imageTypeRequested==REQUESTED_SELFIE){
                                    type = CAMERA_REQUESTED_SELFIE;
                                }
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    if (checkSelfPermission(Manifest.permission.CAMERA)
                                            != PackageManager.PERMISSION_GRANTED) {
                                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                                type);

                                        return;
                                    }
                                    else{
                                        takePhotoFromCamera(type);
                                    }
                                }
                                else {
                                    takePhotoFromCamera(type);
                                }

                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private File persistImage(Bitmap bitmap, String name, ImageView photoImageView) {

        File filesDir = getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);

            os.flush();
            os.close();
            photoImageView.setImageBitmap(bitmap);
            if(photoImageView==nidFrontImageView){
                isNidFrontAdded = true;
            } else if(photoImageView==nidBackImageView){
                isNidbackAdded = true;
            } else if(photoImageView==selfieImageView){
                isSelfieAdded = true;
            }
        } catch (Exception e) {
            Log.e("Error:", "Error writing bitmap", e);
        }


        return imageFile;
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        return outputStream.toByteArray();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GALLERY_REQUESTED_NID_BACK:
                bitmap = ImageUtils.getImageFromResult(this, resultCode, data);
                persistImage(bitmap, "photo",nidBackImageView);
                break;
            case GALLERY_REQUESTED_NID_FRONT:
                bitmap = ImageUtils.getImageFromResult(this, resultCode, data);
                persistImage(bitmap, "photo",nidFrontImageView);
                break;
            case GALLERY_REQUESTED_SELFIE:
                bitmap = ImageUtils.getImageFromResult(this, resultCode, data);
                persistImage(bitmap, "photo",selfieImageView);
            case CAMERA_REQUESTED_SELFIE:
                buildImageFromCamera(data, selfieImageView);
                break;
            case CAMERA_REQUESTED_NID_BACK:
                buildImageFromCamera(data, nidBackImageView);
                break;
            case CAMERA_REQUESTED_NID_FRONT:
                buildImageFromCamera(data, nidFrontImageView);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    protected void buildImageFromCamera(Intent data, ImageView imageView){
        if (data != null) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");


            persistImage(bitmap, "photo", imageView);

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

       if(requestCode==CAMERA_REQUESTED_SELFIE || requestCode==CAMERA_REQUESTED_NID_BACK ||requestCode==CAMERA_REQUESTED_NID_FRONT ){
           if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               takePhotoFromCamera(requestCode);
           } else {

               // permission denied, boo! Disable the
               // functionality that depends on this permission.
           }
           return;
       }
    }
}
