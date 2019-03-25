package com.myfarmnow.myfarmcrop.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class CropRegisterActivity extends PermisoActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    Spinner  spinnercountry,addressCountrySp;
    EditText edtfirstname, edtlastname, edtcountry, edtemail, edtconemail, edtpassword, edtconpwd, edtfarmname, edtAddress, edtContact,
    edtAdressTownorCity,edtCountryCode;
    Button btnSignUp;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public static Location mCurrentLocation;
    Integer random;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    TextView tvlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        spinnercountry = (Spinner) findViewById(R.id.spinnercountry);
        edtfirstname = (EditText) findViewById(R.id.edtfirstname);
        edtlastname = (EditText) findViewById(R.id.edtlastname);
        edtcountry = (EditText) findViewById(R.id.edtcountry);
        edtemail = (EditText) findViewById(R.id.edtemail);
        edtconemail = (EditText) findViewById(R.id.edtconemail);
        edtpassword = (EditText) findViewById(R.id.edtpassword);
        edtconpwd = (EditText) findViewById(R.id.edtconpwd);
        edtfarmname = (EditText) findViewById(R.id.edtfarmname);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtContact = (EditText) findViewById(R.id.edtContact);
        edtCountryCode = findViewById(R.id.edtCountryCode);
        edtAdressTownorCity =  findViewById(R.id.editAdressTownorCity);
        addressCountrySp =  findViewById(R.id.spinnerAdressCountry);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        tvlogin = (TextView) findViewById(R.id.tvlogin);


        tvlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(CropRegisterActivity.this, CropLoginActivity.class));
            }
        });

        spinnercountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                if (edtfirstname.getText().toString().length() <= 0) {
                    Toast.makeText(CropRegisterActivity.this, "Enter FirstName..", Toast.LENGTH_SHORT).show();
                } else if (edtlastname.getText().toString().length() <= 0) {
                    Toast.makeText(CropRegisterActivity.this, "Enter LastName..", Toast.LENGTH_SHORT).show();
                } else if (edtemail.getText().toString().length() <= 0) {
                    Toast.makeText(CropRegisterActivity.this, "Enter Email..", Toast.LENGTH_SHORT).show();
                }
//               else if (edtconemail.getText().toString().length() <= 0) {
//                    Toast.makeText(CropRegisterActivity.this, "Enter Confirm Email..", Toast.LENGTH_SHORT).show();
//                } else if (!edtemail.getText().toString().equalsIgnoreCase(edtconemail.getText().toString())) {
//                    Toast.makeText(CropRegisterActivity.this, "Email Mismatch..", Toast.LENGTH_SHORT).show();
//                }
                else if (edtpassword.getText().toString().length() <= 0) {
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
                }else {
                    userregister();

                }

            }
        });
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        return ConnectionResult.SUCCESS == status;
    }


    public void userregister() {


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
}
