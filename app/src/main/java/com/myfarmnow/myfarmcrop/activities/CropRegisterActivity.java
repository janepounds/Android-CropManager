package com.myfarmnow.myfarmcrop.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.myfarmnow.myfarmcrop.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.greysonparrelli.permiso.Permiso;
import com.greysonparrelli.permiso.PermisoActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myfarmnow.myfarmcrop.models.ApiPaths;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;

import cz.msebera.android.httpclient.Header;

public class CropRegisterActivity extends PermisoActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    Spinner  spinnercountry,addressCountrySp;
    EditText edtfirstname, edtlastname, edtcountry, edtemail, edtconemail, edtpassword, edtconpwd, edtfarmname, edtAddress, edtContact,
    edtAdressTownorCity,edtCountryCode,currentPasswordTxt;
    Button btnSignUp;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public static Location mCurrentLocation;
    Integer random;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    TextView tvlogin, titleTextView, errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinnercountry = (Spinner) findViewById(R.id.spinnercountry);
        edtfirstname =  findViewById(R.id.edtfirstname);
        edtlastname =  findViewById(R.id.edtlastname);
        edtcountry =  findViewById(R.id.edtcountry);
        edtemail =  findViewById(R.id.edtemail);
        edtconemail =  findViewById(R.id.edtconemail);
        edtpassword =  findViewById(R.id.edtpassword);
        edtconpwd =  findViewById(R.id.edtconpwd);
        edtfarmname =  findViewById(R.id.edtfarmname);
        edtAddress =  findViewById(R.id.edtAddress);
        edtContact =  findViewById(R.id.edtContact);
        edtCountryCode = findViewById(R.id.edtCountryCode);
        edtAdressTownorCity =  findViewById(R.id.editAdressTownorCity);
        currentPasswordTxt =  findViewById(R.id.txt_crop_user_current_password);
        addressCountrySp =  findViewById(R.id.spinnerAdressCountry);
        titleTextView =  findViewById(R.id.text_view_crop_register_title);
        errorTextView =  findViewById(R.id.text_view_crop_user_error);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvlogin = (TextView) findViewById(R.id.tvlogin);


        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(getIntent().hasExtra("editUser")){
                    startActivity(new Intent(CropRegisterActivity.this, CropForgotPasswordRequestCode.class));
                }else{
                    startActivity(new Intent(CropRegisterActivity.this, CropLoginActivity.class));
                }

            }
        });

        spinnercountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getColor(R.color.White));
                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.White)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                if(position !=0 ){
                   addressCountrySp.setSelection(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (isGooglePlayServicesAvailable()) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(INTERVAL);
            mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            //mLocationRequest.setSmallestDisplacement(10.0f);  /* min dist for location change, here it is 10 meter */
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mGoogleApiClient.connect();
        }


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(getIntent().hasExtra("editUser")){
                    updateUser();
                }
                else {
                    if (edtfirstname.getText().toString().length() <= 0) {
                        Toast.makeText(CropRegisterActivity.this, "Enter FirstName..", Toast.LENGTH_SHORT).show();
                    } else if (edtlastname.getText().toString().length() <= 0) {
                        Toast.makeText(CropRegisterActivity.this, "Enter LastName..", Toast.LENGTH_SHORT).show();
                    } else if (edtemail.getText().toString().length() <= 0) {
                        Toast.makeText(CropRegisterActivity.this, "Enter Email..", Toast.LENGTH_SHORT).show();
                    } else if (edtpassword.getText().toString().length() <= 0) {
                        Toast.makeText(CropRegisterActivity.this, "Enter Password..", Toast.LENGTH_SHORT).show();
                    } else if (edtconpwd.getText().toString().length() <= 0) {
                        Toast.makeText(CropRegisterActivity.this, "Enter Confirm Password..", Toast.LENGTH_SHORT).show();
                    } else if (!edtpassword.getText().toString().equalsIgnoreCase(edtconpwd.getText().toString())) {
                        Toast.makeText(CropRegisterActivity.this, "Password Mismatch..", Toast.LENGTH_SHORT).show();
                    } else if (edtfarmname.getText().toString().length() <= 0) {
                        Toast.makeText(CropRegisterActivity.this, "Enter Firm Name", Toast.LENGTH_SHORT).show();
                    } else if (edtAddress.getText().toString().length() <= 0) {
                        Toast.makeText(CropRegisterActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();
                    } else if (edtContact.getText().toString().length() <= 0) {
                        Toast.makeText(CropRegisterActivity.this, "Enter Contact", Toast.LENGTH_SHORT).show();
                    }   else if(spinnercountry.getSelectedItemPosition()==0){
                        spinnercountry.requestFocus();
                        Toast.makeText(CropRegisterActivity.this, "Select country", Toast.LENGTH_SHORT).show();
                        return;
                    }else if(addressCountrySp.getSelectedItemPosition()==0){
                        addressCountrySp.requestFocus();
                        Toast.makeText(CropRegisterActivity.this, "Select address country", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else {

                        userRegister();

                    }
                }

            }
        });
        AdapterView.OnItemSelectedListener onItemSelectedListener =new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getColor(R.color.White));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.White)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        addressCountrySp.setOnItemSelectedListener(onItemSelectedListener);

        if(getIntent().hasExtra("editUser")){
            fillViews();
        }
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        return ConnectionResult.SUCCESS == status;
    }



    public void updateUser(){

        if (currentPasswordTxt.getText().toString().length() <= 0) {
            Toast.makeText(CropRegisterActivity.this, "You must enter the password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtfirstname.getText().toString().length() <= 0) {
            Toast.makeText(CropRegisterActivity.this, "Enter FirstName..", Toast.LENGTH_SHORT).show();
            return;
        } else if (edtlastname.getText().toString().length() <= 0) {
            Toast.makeText(CropRegisterActivity.this, "Enter LastName..", Toast.LENGTH_SHORT).show();
            return;
        } else if (edtemail.getText().toString().length() <= 0) {
            Toast.makeText(CropRegisterActivity.this, "Enter Email..", Toast.LENGTH_SHORT).show();
            return;
        }  else if (edtfarmname.getText().toString().length() <= 0) {
            Toast.makeText(CropRegisterActivity.this, "Enter Firm Name", Toast.LENGTH_SHORT).show();
            return;
        } else if (edtAddress.getText().toString().length() <= 0) {
            Toast.makeText(CropRegisterActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();
            return;
        } else if (edtContact.getText().toString().length() <= 0) {
            edtContact.requestFocus();
            Toast.makeText(CropRegisterActivity.this, "Enter Contact", Toast.LENGTH_SHORT).show();
            return;
        } else if (edtCountryCode.getText().toString().length() <= 0) {
            edtContact.requestFocus();
            Toast.makeText(CropRegisterActivity.this, "Enter Contact", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(addressCountrySp.getSelectedItemPosition()==0){
            addressCountrySp.requestFocus();
            Toast.makeText(CropRegisterActivity.this, "Select address country", Toast.LENGTH_SHORT).show();
            return;
        } else if(spinnercountry.getSelectedItemPosition()==0){
            spinnercountry.requestFocus();
            Toast.makeText(CropRegisterActivity.this, "Select country", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!edtpassword.getText().toString().isEmpty()){
            if(edtconpwd.getText().toString().isEmpty()){
                edtconpwd.requestFocus();
                Toast.makeText(CropRegisterActivity.this, "Confirm Password", Toast.LENGTH_SHORT).show();
                return;
            }else if(!edtconpwd.getText().toString().equals(edtpassword.getText().toString())){
                edtconpwd.requestFocus();
                Toast.makeText(CropRegisterActivity.this, "Passwords must match", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

        //   String tokens = SharedPreferences.(this).gettoken();

        params.put("id", CropDashboardActivity.getPreferences("userId",this));
        params.put("firstname", "" + edtfirstname.getText().toString());
        params.put("lastname", "" + edtlastname.getText().toString());
        params.put("country", "" + spinnercountry.getSelectedItem().toString());
        params.put("addressCountry", "" + addressCountrySp.getSelectedItem().toString());
        params.put("addressStreet", "" + edtAddress.getText().toString());
        params.put("addressCityOrTown", "" + edtAdressTownorCity.getText().toString());
        params.put("email", "" + edtemail.getText().toString());
        params.put("farmname", "" + edtfarmname.getText().toString());
        params.put("phoneNumber", "+" +edtCountryCode.getText().toString()+edtContact.getText().toString());
        params.put("countryCode", "" + edtCountryCode.getText().toString());
        params.put("oldPassword", currentPasswordTxt.getText().toString());
        params.put("latitude", "" + lat);
        params.put("longitude", "" + lng);
        if(!edtpassword.getText().toString().isEmpty()){
            params.put("password", edtpassword.getText().toString());
        }

        client.post(ApiPaths.CROP_USER_UPDATE, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {

                dialog = new ProgressDialog(CropRegisterActivity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");
                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONObject user = response.getJSONObject("user");
                    Toast.makeText(CropRegisterActivity.this, "Successfully Loged in..", Toast.LENGTH_SHORT).show();
                    Log.e("response", response.toString());
                    CropDashboardActivity.saveUser(user,CropRegisterActivity.this);

                    if(response.getString("message").equals("Verification required")){
                        Intent verifyPhoneNumber = new Intent(CropRegisterActivity.this, CropVerifyPhoneNumberActivity.class);
                        verifyPhoneNumber.putExtra("userId",user.getString("id"));
                        verifyPhoneNumber.putExtra("phoneNumber",user.getString("phoneNumber"));
                        verifyPhoneNumber.putExtra("countryCode",user.getString("countryCode"));
                        verifyPhoneNumber.putExtra("resendCode","yes");
                        startActivity(verifyPhoneNumber);
                    }

                    finish();
                    dialog.dismiss();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    try {
                        String errorTxt ="";
                        JSONObject errors = errorResponse.getJSONObject("errors");
                        Iterator<String> errorKeys = errors.keys();
                        while (errorKeys.hasNext()){
                            JSONArray errorMessages = errors.getJSONArray(errorKeys.next());
                            for(int i=0; i<errorMessages.length(); i++){
                                errorTxt+=errorMessages.get(i)+",";
                            }

                        }
                        errorTextView.setText(errorTxt);
                        errorTextView.setVisibility(View.VISIBLE);
                        errorTextView.requestFocus();
                        Toast.makeText(CropRegisterActivity.this, errorResponse.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }
                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                dialog.dismiss();
            }
        });



    }

    public void userRegister() {


        AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams params = new RequestParams();

     //   String tokens = SharedPreferences.(this).gettoken();

        params.put("firstname", "" + edtfirstname.getText().toString());
        params.put("lastname", "" + edtlastname.getText().toString());
        params.put("country", "" + spinnercountry.getSelectedItem().toString());
        params.put("addressCountry", "" + addressCountrySp.getSelectedItem().toString());
        params.put("addressStreet", "" + edtAddress.getText().toString());
        params.put("addressCityOrTown", "" + edtAdressTownorCity.getText().toString());
        params.put("email", "" + edtemail.getText().toString());
        params.put("password", "" + edtpassword.getText().toString());
        params.put("farmname", "" + edtfarmname.getText().toString());
        params.put("phoneNumber", "+" +edtCountryCode.getText().toString()+edtContact.getText().toString());
        params.put("countryCode", "" + edtCountryCode.getText().toString());
        params.put("latitude", "" + lat);
        params.put("longitude", "" + lng);


        client.post(ApiPaths.CROP_CREATE_USER, params, new JsonHttpResponseHandler() {
            ProgressDialog dialog;

            @Override
            public void onStart() {

                dialog = new ProgressDialog(CropRegisterActivity.this);
                dialog.setIndeterminate(true);
                dialog.setMessage("Please Wait..");

                dialog.setCancelable(false);
                dialog.show();
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                        JSONObject user = response.getJSONObject("user");
                        Toast.makeText(CropRegisterActivity.this, "Successfully Loged in..", Toast.LENGTH_SHORT).show();
                        Log.e("response", response.toString());
                        CropDashboardActivity.saveUser(user,CropRegisterActivity.this);
                        Intent verifyPhoneNumber = new Intent(CropRegisterActivity.this, CropVerifyPhoneNumberActivity.class);
                        verifyPhoneNumber.putExtra("userId",user.getString("id"));
                        verifyPhoneNumber.putExtra("phoneNumber",user.getString("phoneNumber"));
                        verifyPhoneNumber.putExtra("countryCode",user.getString("countryCode"));
                        startActivity(verifyPhoneNumber);
                        finish();
                    dialog.dismiss();



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    try {
                        String errorTxt ="";
                        JSONObject errors = errorResponse.getJSONObject("errors");
                        Iterator<String> errorKeys = errors.keys();
                        while (errorKeys.hasNext()){
                            JSONArray errorMessages = errors.getJSONArray(errorKeys.next());
                            for(int i=0; i<errorMessages.length(); i++){
                                errorTxt+=errorMessages.get(i)+",";
                            }

                        }
                        errorTextView.setText(errorTxt);
                        errorTextView.setVisibility(View.VISIBLE);
                        errorTextView.requestFocus();
                        Toast.makeText(CropRegisterActivity.this, errorResponse.getString("message"), Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("info", new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info", "Something got very very wrong");
                }
                dialog.dismiss();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String errorResponse,Throwable throwable) {
                if (errorResponse != null) {
                    Log.e("info : "+statusCode, new String(String.valueOf(errorResponse)));
                } else {
                    Log.e("info : "+statusCode, "Something got very very wrong");
                }
                dialog.dismiss();
            }
        });
    }

    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("pref",
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String getpreferences(String key) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("pref",
                0);

        return sharedPreferences.getString(key, "0");

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            startLocationUpdates();
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, new com.google.android.gms.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }
            });
            Log.e("stopp", "Location update stopped .......................");
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
        catch (Exception exceptio){

        }

    }

    protected void startLocationUpdates() {


        Permiso.getInstance().requestPermissions(new Permiso.IOnPermissionResult() {
            @Override
            public void onPermissionResult(Permiso.ResultSet resultSet) {
                if (resultSet.areAllPermissionsGranted()) {
                    // Permission granted!
                    try {

                        @SuppressLint("MissingPermission") PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                                mGoogleApiClient, mLocationRequest, CropRegisterActivity.this);
                    } catch (IllegalStateException e) {
                        Log.e("Errorinservice", "" + e.toString());

                    }
                } else {
                    // Permission denied.
                    startLocationUpdates();
                }
            }

            @Override
            public void onRationaleRequested(Permiso.IOnRationaleProvided callback, String... permissions) {
                Permiso.getInstance().showRationaleInDialog("Title", "Message", null, callback);
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION);

    }

    String lat = "";
    String lng = "";

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

        String mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        lat = String.valueOf(mCurrentLocation.getLatitude());
        lng = String.valueOf(mCurrentLocation.getLongitude());

        Log.e("lcoation=", "" + "At Time: " + mLastUpdateTime + "\n" +
                "Latitude: " + lat + "\n" +
                "Longitude: " + lng + "\n" +
                "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
                "Provider: " + mCurrentLocation.getProvider());

    }

    public void fillViews(){
        //, edtcountry, edtconemail, edtpassword, edtconpwd, , edtAddress,   edtAdressTownorCity,;
        currentPasswordTxt.setVisibility(View.VISIBLE);
        edtfirstname.setText(CropDashboardActivity.getPreferences("firstname",this));
        edtlastname.setText(CropDashboardActivity.getPreferences("lastname",this));
        edtemail.setText(CropDashboardActivity.getPreferences("email",this));
        edtfarmname.setText(CropDashboardActivity.getPreferences("farmname",this));
        edtContact.setText(CropDashboardActivity.getPreferences("phoneNumber",this).replace("+"+CropDashboardActivity.getPreferences("countryCode",this),""));
        edtCountryCode.setText(CropDashboardActivity.getPreferences("countryCode",this));
        edtAddress.setText(CropDashboardActivity.getPreferences("addressStreet",this));
        edtAdressTownorCity.setText(CropDashboardActivity.getPreferences("addressCityOrTown",this));
        CropDashboardActivity.selectSpinnerItemByValue(spinnercountry,CropDashboardActivity.getPreferences("country",this));
        CropDashboardActivity.selectSpinnerItemByValue(addressCountrySp,CropDashboardActivity.getPreferences("addressCountry",this));
        titleTextView.setText(R.string.edit_profile_title);
        btnSignUp.setText(R.string.update);
        tvlogin.setText(R.string.forgot_password_prompt_request_code);
        //CropDashboardActivity.savePreferences("latitude", user.getString("latitude"), context);
        //CropDashboardActivity.savePreferences("longitude", user.getString("longitude"), context);
    }
}
