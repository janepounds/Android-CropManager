package com.myfarmnow.myfarmcrop.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.customs.CircularImageView;
import com.myfarmnow.myfarmcrop.customs.DialogLoader;
import com.myfarmnow.myfarmcrop.database.User_Info_BuyInputsDB;
import com.myfarmnow.myfarmcrop.models.uploadimage.UploadImageModel;
import com.myfarmnow.myfarmcrop.models.user_model.UserData;
import com.myfarmnow.myfarmcrop.models.user_model.UserDetails;
import com.myfarmnow.myfarmcrop.network.BuyInputsAPIClient;
import com.myfarmnow.myfarmcrop.utils.CheckPermissions;
import com.myfarmnow.myfarmcrop.utils.ImagePicker;
import com.myfarmnow.myfarmcrop.utils.ValidateInputs;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import am.appwise.components.ni.NoInternetDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAccountFragment extends Fragment {
    private static final String TAG = "UpdateAccountFragment";
    private String image;

    private Context context;
    private String customers_id;
    private String profileImageCurrent = "";
    private File profileImageChanged;
    private static final int PICK_IMAGE_ID = 360;           // the number doesn't matter

    private String imageID;
    private Boolean isImgUploaded = false;

    private DialogLoader dialogLoader;

    private UserDetails userInfo;
    private User_Info_BuyInputsDB userInfoDB = new User_Info_BuyInputsDB();

    private EditText dob, firstName, lastName, email, contact;
    private FloatingActionButton userPhotoEditFab;
    private Button updateInfoBtn;
    private CircularImageView userPhoto;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);

        dob = view.findViewById(R.id.dob_fragment_update_account);
        firstName = view.findViewById(R.id.first_name_update_account_fragment);
        lastName = view.findViewById(R.id.last_name_update_account_fragment);
        email = view.findViewById(R.id.email_update_account_fragment);
        contact = view.findViewById(R.id.contact_update_account_fragment);
        userPhotoEditFab = view.findViewById(R.id.update_account_user_photo_edit_fab);
        updateInfoBtn = view.findViewById(R.id.update_account_info_btn);
        userPhoto = view.findViewById(R.id.update_account_user_photo);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        // MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(getString(R.string.actionAccount));

        NoInternetDialog noInternetDialog = new NoInternetDialog.Builder(getContext()).build();
        //noInternetDialog.show();

        // Get the CustomerID from SharedPreferences
        customers_id = DashboardActivity.RETRIEVED_USER_ID;
        Log.d(TAG, "onCreateView: Customer ID = " + customers_id);

        // Set KeyListener of some View to null
        dob.setKeyListener(null);

        dialogLoader = new DialogLoader(getContext());

        // Get the User's Info from the Local Databases User_Info_DB
        userInfo = userInfoDB.getUserData(customers_id);

        Log.d(TAG, "onCreateView: Email = " + userInfo.getEmail());
        Log.d(TAG, "onCreateView: First Name = " + userInfo.getFirstName());
        Log.d(TAG, "onCreateView: Last Name = " + userInfo.getLastName());
        Log.d(TAG, "onCreateView: Contact = " + userInfo.getPhone());
        Log.d(TAG, "onCreateView: Date of Birth = " + userInfo.getDob());

        // Set User's Info to Form Inputs
        firstName.setText(userInfo.getFirstName());
        lastName.setText(userInfo.getLastName());
        email.setText(userInfo.getEmail());
        contact.setText(userInfo.getPhone());

        // Set User's Date of Birth
        if (userInfo.getDob() == null || userInfo.getDob().equalsIgnoreCase("0000-00-00 00:00:00")) {
            dob.setText("");
        } else {
            // Get the String of Date from userInfo
            String dateString = userInfo.getDob();
            // Set Date Format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            // Convert String of Date to Date Format
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dob.setText(dateFormat.format(convertedDate));
        }

        // Set User's Photo

//        if (!TextUtils.isEmpty(userInfo.getAvatar()) && userInfo.getAvatar() != null) {
//            profileImageCurrent = userInfo.getAvatar();
//
//        } else {
//            profileImageCurrent = "";
//        }
//
//        Glide.with(getContext())
//                .asBitmap()
//                .apply(new RequestOptions()
//                        .placeholder(R.drawable.profile)
//                        .error(R.drawable.profile)
//                        .fitCenter())
//                .load(ConstantValues.ECOMMERCE_URL + profileImageCurrent)
//                .into(user_photo);

        // Handle Touch event of input_dob EditText
        dob.setOnTouchListener((v, event) -> {

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Get Calendar instance
                final Calendar calendar = Calendar.getInstance();

                // Initialize DateSetListener of DatePickerDialog
                DatePickerDialog.OnDateSetListener date = (v11, year, monthOfYear, dayOfMonth) -> {

                    // Set the selected Date Info to Calendar instance
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Set Date Format
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                    // Set Date in input_dob EditText
                    dob.setText(dateFormat.format(calendar.getTime()));
                };


                // Initialize DatePickerDialog
                DatePickerDialog datePicker = new DatePickerDialog
                        (
                                context,
                                date,
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                        );
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

                // Show datePicker Dialog
                datePicker.show();
            }

            return false;
        });

        // Handle Click event of user_photo_edit_fab FAB
        userPhotoEditFab.setOnClickListener(v -> {

            if (!CheckPermissions.is_CAMERA_PermissionGranted() && !CheckPermissions.is_STORAGE_PermissionGranted()) {
                requestPermissions
                        (
                                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                        );
            } else {
                pickImage();
            }

        });

        // Handle Click event of updateInfoBtn Button
        updateInfoBtn.setOnClickListener(v -> {
            // Validate User's Info Form Inputs
            boolean isValidData = validateInfoForm();

            if (isValidData) {

                updateCustomerInfo();
                    /*
                    if ("".equalsIgnoreCase(input_current_password.getText().toString()) && "".equalsIgnoreCase(input_new_password.getText().toString())) {
                        // Proceed User Registration
                        updateCustomerInfo();
                    } else {
                        if (validatePasswordForm())
                            updateCustomerInfo();
                    }
                    */
            }
        });

        return view;
    }

    //*********** Picks User Profile Image from Gallery or Camera ********//
    private void pickImage() {
        Intent intent = ImagePicker.getImagePickerIntent(context);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, PICK_IMAGE_ID);
    }

    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_ID) {

                // Get the User Selected Image as Bitmap from the static method of ImagePicker class
                Bitmap bitmap = ImagePicker.getImageFromResult(context, resultCode, data);

                // Upload the Bitmap to ImageView
                userPhoto.setImageBitmap(bitmap);

                // Get the converted Bitmap as Base64ImageString from the static method of Helper class
                //profileImageChanged = Utilities.getBase64ImageStringFromBitmap(bitmap);

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(context, bitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                profileImageChanged = new File(getRealPathFromURI(tempUri));

                RequestImageUpload();
            }
        }
    }

    private void RequestImageUpload() {
        dialogLoader.showProgressDialog();
        MultipartBody.Part filePart = null;

        if (profileImageChanged != null) {
            filePart = MultipartBody.Part.createFormData("file", profileImageChanged.getName(), RequestBody.create(MediaType.parse("image/*"), profileImageChanged));
            Log.d(TAG, "RequestImageUpload: filePart not null");
        }

        Call<UploadImageModel> call = BuyInputsAPIClient.getInstance().uploadImage(filePart);
        call.enqueue(new Callback<UploadImageModel>() {
            @Override
            public void onResponse(Call<UploadImageModel> call, Response<UploadImageModel> response) {
                Log.d(TAG, "onResponse: Response = " + response.code());
                Log.d(TAG, "onResponse: ResponseMessage = " + response.message());
                Log.d(TAG, "onResponse: Headers = " + response.headers());
                Log.d(TAG, "onResponse: Success = " + response.body().getSuccess());
                Log.d(TAG, "onResponse: Message = " + response.body().getMessage());
                Log.d(TAG, "onResponse: Response = " + response.body().getData().toString());

                if (response.isSuccessful()) {

                    if (response.body().getSuccess().equalsIgnoreCase("1")) {

                        imageID = "" + response.body().getData().get(0).getImageId();
                        Toast.makeText(getContext(), "Image added successfully!", Toast.LENGTH_SHORT).show();

                        isImgUploaded = true;
                    } else {

                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialogLoader.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<UploadImageModel> call, Throwable t) {
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_SHORT).show();
                dialogLoader.hideProgressDialog();
            }
        });
    }

    // Getting image URI
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // Get absolute image path
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    //*********** This method is invoked for every call on requestPermissions(Activity, String[], int) ********//
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CheckPermissions.PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // The Camera and Storage Permission is granted
                pickImage();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    // Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getString(R.string.permission_camera_storage));
                    builder.setMessage(getString(R.string.permission_camera_storage_needed));
                    builder.setPositiveButton(getString(R.string.grant), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            requestPermissions
                                    (
                                            new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                                    );
                        }
                    });
                    builder.setNegativeButton(getString(R.string.not_now), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.permission_rejected), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //*********** Updates User's Personal Information ********//
    private void updateCustomerInfo() {

        dialogLoader.showProgressDialog();

        Call<UserData> call = BuyInputsAPIClient.getInstance()
                .updateCustomerInfo
                        (customers_id,
                                firstName.getText().toString().trim(),
                                lastName.getText().toString().trim(),
                                "1",
                                contact.getText().toString().trim(),
                                dob.getText().toString().trim(),
                                imageID);

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1") && response.body().getData() != null) {
                        // User's Info has been Updated.

                        UserDetails userDetails = response.body().getData().get(0);

                        // Update in Local Databases as well
                        userInfoDB.updateUserData(userDetails);
                        userInfoDB.updateUserPassword(userDetails);

                        // Get the User's Info from the Local Databases User_Info_DB
                        userInfo = userInfoDB.getUserData(customers_id);

                        // Set the userName in SharedPreferences
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).edit();
                        editor.putString("userName", userDetails.getFirstName() + " " + userDetails.getLastName());
                        editor.apply();


                        Snackbar.make(requireActivity().findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    } else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Unable to Update User's Info.
                        Snackbar.make(requireActivity().findViewById(android.R.id.content), response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    } else if (response.body().getSuccess().equalsIgnoreCase("2")) {
                        // Unable to Update User's Info.
                        Snackbar.make(requireActivity().findViewById(android.R.id.content)
                                , response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                    } else {
                        // Unable to get Success status
                        Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : " + t, Toast.LENGTH_LONG).show();
            }
        });
    }

    //*********** Validate User Info Form Inputs ********//
    private boolean validateInfoForm() {
        if (!ValidateInputs.isValidName(firstName.getText().toString().trim())) {
            firstName.setError(getString(R.string.invalid_first_name));
            return false;
        } else if (!ValidateInputs.isValidName(lastName.getText().toString().trim())) {
            lastName.setError(getString(R.string.invalid_last_name));
            return false;
        } else {
            return true;
        }
    }

    //*********** Validate Password Info Form Inputs ********//
    /*
    private boolean validatePasswordForm() {
        if (!input_current_password.getText().toString().trim().equals(userInfo.getCustomersPassword())) {
            input_current_password.setError(getString(R.string.invalid_password));
            return false;
        } else*//* if (!ValidateInputs.isValidPassword(input_new_password.getText().toString().trim())) {
            input_new_password.setError(getString(R.string.invalid_password));
            return false;
        } else {
            return true;
        }
    }
    */

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
