package com.myfarmnow.myfarmcrop.fragments.wallet;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.wallet.WalletAuthActivity;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoanAppPhotosBinding;
import com.myfarmnow.myfarmcrop.databinding.FragmentWalletLoanPreviewRequestBinding;
import com.myfarmnow.myfarmcrop.helpers.ImageUtils;
import com.myfarmnow.myfarmcrop.models.retrofitResponses.WalletLoanAddPicResponse;
import com.myfarmnow.myfarmcrop.models.wallet.ApiPaths;
import com.myfarmnow.myfarmcrop.models.wallet.LoanApplication;
import com.myfarmnow.myfarmcrop.network.APIClient;
import com.myfarmnow.myfarmcrop.network.APIRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletLoanAppPhotosFragment extends Fragment {
    private static final String TAG = "WalletLoanAppPhotosFragment";
    private Context context;

    private FragmentWalletLoanAppPhotosBinding binding;
    NavController navController;
    AppBarConfiguration appBarConfiguration;

    Bitmap bitmap;
    private static final int GALLERY_REQUESTED_NID_BACK = 1;
    private static final int GALLERY_REQUESTED_NID_FRONT = 2;
    private static final int GALLERY_REQUESTED_SELFIE = 3;
    private static final int CAMERA_REQUESTED_NID_BACK = 4;
    private static final int CAMERA_REQUESTED_NID_FRONT = 5;
    private static final int CAMERA_REQUESTED_SELFIE = 6;
    private static final int REQUESTED_SELFIE = 7;
    private static final int REQUESTED_NID_BACK = 8;
    private static final int REQUESTED_NID_FRONT = 9;

    ProgressDialog dialog;
    String[] descriptionData = {"Amount", "Confirm", "Photo", "Referees"};

    Boolean isNidFrontAdded = false, isNidbackAdded = false, isSelfieAdded = false;
    String loanApplicationId;
    LoanApplication loanApplication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet_loan_app_photos, container, false);

        assert getArguments() != null;
        if (getArguments().getString("loanApplicationId") != null) {
            loanApplicationId = getArguments().getString("loanApplicationId");
        } else if (getArguments().getSerializable("loanApplication") != null) {
            loanApplication = (LoanApplication) getArguments().getSerializable("loanApplication");
            assert loanApplication != null;
            loanApplicationId = loanApplication.getId();
        } else {
            requireActivity().finish();
        }

        initializeActivity();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         navController = Navigation.findNavController(view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);
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

        binding.loanProgressBarId.setStateDescriptionData(descriptionData);

        binding.btnLoanNextStep.setOnClickListener(v -> submitPhotos());

        binding.imageViewLoanPhotosNidFront.setOnClickListener(v -> showPictureDialog(REQUESTED_NID_FRONT));

        binding.imageViewLoanPhotosNidBack.setOnClickListener(v -> showPictureDialog(REQUESTED_NID_BACK));

        binding.imageViewLoanPhotosSelfie.setOnClickListener(v -> showPictureDialog(REQUESTED_SELFIE));

        if (loanApplication != null) {
            binding.loanProgressBarId.setVisibility(View.GONE);

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
                    .into(binding.imageViewLoanPhotosNidBack);

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
                    .into(binding.imageViewLoanPhotosNidFront);

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
                    .into(binding.imageViewLoanPhotosSelfie);

            binding.btnLoanNextStep.setText("Update");
            binding.textViewLoanPhotosTitle.setText("Update Photos");
        }
    }

    public void submitPhotos() {
        JSONObject requestObject = new JSONObject();

        if (isNidFrontAdded || isSelfieAdded || isNidbackAdded) {
            String access_token = WalletAuthActivity.WALLET_ACCESS_TOKEN;

            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(20000);
            client.addHeader("Authorization", "Bearer " + WalletAuthActivity.WALLET_ACCESS_TOKEN);

            try {
                dialog.show();
                requestObject.put("loanApplicationId", loanApplicationId);

                if (isNidFrontAdded) {
                    requestObject.put("nationalIdPicBack", Base64.encodeToString(getBitmapAsByteArray(((BitmapDrawable) binding.imageViewLoanPhotosNidBack.getDrawable()).getBitmap()), Base64.DEFAULT));
                }

                if (isSelfieAdded) {
                    requestObject.put("nationalIdPicFront", Base64.encodeToString(getBitmapAsByteArray(((BitmapDrawable) binding.imageViewLoanPhotosNidFront.getDrawable()).getBitmap()), Base64.DEFAULT));
                }
                if (isSelfieAdded) {
                    requestObject.put("userPhoto", Base64.encodeToString(getBitmapAsByteArray(((BitmapDrawable) binding.imageViewLoanPhotosSelfie.getDrawable()).getBitmap()), Base64.DEFAULT));
                }



            /*******************RETROFIT IMPLEMENTATION****************************/
                APIRequests apiRequests = APIClient.getWalletInstance();
                Call<WalletLoanAddPicResponse> call = apiRequests.addLoanPhotos(access_token,requestObject);
                call.enqueue(new Callback<WalletLoanAddPicResponse>() {
                    @Override
                    public void onResponse(Call<WalletLoanAddPicResponse> call, Response<WalletLoanAddPicResponse> response) {
                        if(response.code()== 200){
                            Bundle bundle = new Bundle();
                            assert getArguments() != null;
                            bundle.putString("loanApplicationId", loanApplicationId);
                            bundle.putInt("refereeNumber", 1);
                            navController.navigate(R.id.action_walletLoanAppPhotosFragment_to_walletLoansListFragment, bundle);
                        }else if(response.code()==401){
                            WalletAuthActivity.startAuth(context, true);
                        } else if (response.code() == 500) {
                            binding.textViewErrorMessage.setText("Error Occurred Try again later");
                            Log.e("info 500", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                        } else if (response.code() == 400) {
                            binding.textViewErrorMessage.setText(response.errorBody().toString());
                            Log.e("info 500", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                        } else if (response.code() == 406) {
                            binding.textViewErrorMessage.setText(response.errorBody().toString());
                            Log.e("info 406", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                        } else {
                            binding.textViewErrorMessage.setText("Error Occurred Try again later");
                            if (response.errorBody() != null) {
                                Log.e("info", new String(String.valueOf(response.errorBody())) + ", code: " + response.code());
                            } else {
                                Log.e("info", "Something got very wrong, code: " + response.code());
                            }
                        }
                        binding.textViewErrorMessage.setVisibility(View.VISIBLE);
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<WalletLoanAddPicResponse> call, Throwable t) {

                            Log.e("info 1A: " , new String(String.valueOf(t.getMessage())));

                            Log.e("info 1A: ", "Something got very very wrong");


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

    private void showPictureDialog(final int imageTypeRequested) {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(context);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select from gallery",
                "Take with camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            if (imageTypeRequested == REQUESTED_NID_BACK) {
                                choosePhotoFromGallery(GALLERY_REQUESTED_NID_BACK);
                            } else if (imageTypeRequested == REQUESTED_NID_FRONT) {
                                choosePhotoFromGallery(GALLERY_REQUESTED_NID_FRONT);
                            } else if (imageTypeRequested == REQUESTED_SELFIE) {
                                choosePhotoFromGallery(GALLERY_REQUESTED_SELFIE);
                            }
                            break;
                        case 1:

                            int type = 0;
                            if (imageTypeRequested == REQUESTED_NID_BACK) {
                                type = CAMERA_REQUESTED_NID_BACK;
                            } else if (imageTypeRequested == REQUESTED_NID_FRONT) {
                                type = CAMERA_REQUESTED_NID_FRONT;
                            } else if (imageTypeRequested == REQUESTED_SELFIE) {
                                type = CAMERA_REQUESTED_SELFIE;
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                                        != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                                            type);

                                    return;
                                } else {
                                    takePhotoFromCamera(type);
                                }
                            } else {
                                takePhotoFromCamera(type);
                            }

                            break;
                    }
                });
        pictureDialog.show();
    }

    private File persistImage(Bitmap bitmap, String name, ImageView photoImageView) {

        File filesDir = getActivity().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);

            os.flush();
            os.close();
            photoImageView.setImageBitmap(bitmap);
            if (photoImageView == binding.imageViewLoanPhotosNidFront) {
                isNidFrontAdded = true;
            } else if (photoImageView == binding.imageViewLoanPhotosNidBack) {
                isNidbackAdded = true;
            } else if (photoImageView == binding.imageViewLoanPhotosSelfie) {
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GALLERY_REQUESTED_NID_BACK:
                bitmap = ImageUtils.getImageFromResult(context, resultCode, data);
                persistImage(bitmap, "photo", binding.imageViewLoanPhotosNidBack);
                break;
            case GALLERY_REQUESTED_NID_FRONT:
                bitmap = ImageUtils.getImageFromResult(context, resultCode, data);
                persistImage(bitmap, "photo", binding.imageViewLoanPhotosNidFront);
                break;
            case GALLERY_REQUESTED_SELFIE:
                bitmap = ImageUtils.getImageFromResult(context, resultCode, data);
                persistImage(bitmap, "photo", binding.imageViewLoanPhotosSelfie);
            case CAMERA_REQUESTED_SELFIE:
                buildImageFromCamera(data, binding.imageViewLoanPhotosSelfie);
                break;
            case CAMERA_REQUESTED_NID_BACK:
                buildImageFromCamera(data, binding.imageViewLoanPhotosNidBack);
                break;
            case CAMERA_REQUESTED_NID_FRONT:
                buildImageFromCamera(data, binding.imageViewLoanPhotosNidFront);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    protected void buildImageFromCamera(Intent data, ImageView imageView) {
        if (data != null) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");


            persistImage(bitmap, "photo", imageView);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == CAMERA_REQUESTED_SELFIE || requestCode == CAMERA_REQUESTED_NID_BACK || requestCode == CAMERA_REQUESTED_NID_FRONT) {
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