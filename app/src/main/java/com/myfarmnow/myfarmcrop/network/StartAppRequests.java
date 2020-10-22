package com.myfarmnow.myfarmcrop.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.app.CropManagerApp;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.address_model.AddressData;
import com.myfarmnow.myfarmcrop.models.address_model.RegionDetails;
import com.myfarmnow.myfarmcrop.models.address_model.Regions;
import com.myfarmnow.myfarmcrop.models.banner_model.BannerData;
import com.myfarmnow.myfarmcrop.models.category_model.CategoryData;
import com.myfarmnow.myfarmcrop.models.device_model.AppSettingsData;
import com.myfarmnow.myfarmcrop.models.device_model.DeviceInfo;
import com.myfarmnow.myfarmcrop.models.pages_model.PagesData;
import com.myfarmnow.myfarmcrop.models.pages_model.PagesDetails;
import com.myfarmnow.myfarmcrop.models.user_model.UserData;
import com.myfarmnow.myfarmcrop.utils.Utilities;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * StartAppRequests contains some Methods and API Requests, that are Executed on Application Startup
 **/

public class StartAppRequests {
    private static final String TAG = "StartAppRequests";
    private List<Regions> dataList = new ArrayList<>();
    private MyFarmDbHandlerSingleton dbHandler;
    private Context context;

    private CropManagerApp cropManagerApp = new CropManagerApp();


    public StartAppRequests(Context context) {
        cropManagerApp = ((CropManagerApp) context.getApplicationContext());
        this.context = context;
    }



    //*********** Contains all methods to Execute on Startup ********//

    public void StartRequests(){

        //RequestBanners();
        RequestAllRegions();
        RequestAppSetting();
        RequestStaticPagesData();
        
    }

    public void RequestAllRegions() {
        dbHandler = MyFarmDbHandlerSingleton.getHandlerInstance(context);
        int regionId =dbHandler.getMaxRegionId();
        Log.d(TAG, "RequestAllRegions: "+ regionId);
        Call<Regions> call = BuyInputsAPIClient.getInstance()
                .getAllRegions(regionId);
        try {
            Response<Regions> response = call.execute();

            if (response.isSuccessful()) {


                Regions regionsData = null;
                regionsData = response.body();
                //Log.e("DataCheck0: ",appSettingsData.getAppDetails().getMaintenance_text());
                String strJson = new Gson().toJson(regionsData);
                List<RegionDetails> regionDetails = regionsData.getData();

                 dbHandler.insertRegionDetails(regionDetails);
//                if (!TextUtils.isEmpty(regionsData.getSuccess()))
//                    cropManagerApp.setAppSettingsDetails(regionsData.getData());
                Log.d(TAG, "RequestAllRegions: " + regionDetails);
            }
            else {
                Log.e(TAG, "RequestAllRegions: Response is not successful");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    //*********** Register Device to Admin Panel with the Device's Info ********//
    
    public static void RegisterDeviceForFCM(final Context context) {
    
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);

        final DeviceInfo device = Utilities.getDeviceInfo(context);
        final String customer_ID = sharedPreferences.getString("userID", "");
        
        

            //deviceID = FirebaseInstanceId.getInstance().getToken();
            if(customer_ID!=null)
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    String deviceID =instanceIdResult.getToken();

                    Call<UserData> call = BuyInputsAPIClient.getInstance()
                            .registerDeviceToFCM
                                    (
                                            deviceID,
                                            device.getDeviceType(),
                                            device.getDeviceRAM(),
                                            device.getDeviceProcessors(),
                                            device.getDeviceAndroidOS(),
                                            device.getDeviceLocation(),
                                            device.getDeviceModel(),
                                            device.getDeviceManufacturer(),
                                            customer_ID
                                    );

                    call.enqueue(new Callback<UserData>() {
                        @Override
                        public void onResponse(Call<UserData> call, Response<UserData> response) {

                            if (response.isSuccessful()) {
                                if (response.body().getSuccess().equalsIgnoreCase("1")) {

                                    Log.i("notification", response.body().getMessage());
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();

                                }
                                else {

                                    Log.i("notification", response.body().getMessage());
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Log.i("notification", response.errorBody().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<UserData> call, Throwable t) {
//                Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
    }

    //*********** API Request Method to Fetch App Banners ********//

    public void RequestBanners() {
    
        Call<BannerData> call = BuyInputsAPIClient.getInstance()
                .getBanners();
    
        try {
            Response<BannerData> response = call.execute();
    
            BannerData bannerData = new BannerData();
        
            if (response.isSuccessful()) {
    
                bannerData = response.body();
    
                if (!TextUtils.isEmpty(bannerData.getSuccess()))
                    cropManagerApp.setBannersList(bannerData.getData());
            
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }


    //*********** API Request Method to Fetch All Categories ********//
    
    public void RequestAllCategories() {
    
        Call<CategoryData> call = BuyInputsAPIClient.getInstance()
                .getAllCategories
                        (
                                ConstantValues.LANGUAGE_ID
                        );
        
        try {
            Response<CategoryData> response = call.execute();

            CategoryData categoryData = new CategoryData();
            
            if (response.isSuccessful()) {

                String json= new Gson().toJson(response.body());
                categoryData = response.body();

                if (!TextUtils.isEmpty(categoryData.getSuccess()))
                    cropManagerApp.setCategoriesList(categoryData.getData());
            
            }
        
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Error: ", e.getMessage() );
        }
        
    }

    //*********** Request App Settings from the Server ********//
    
    private void RequestAppSetting() {
        Call<AppSettingsData> call = BuyInputsAPIClient.getInstance()
                .getAppSetting();
        try {
            Response<AppSettingsData> response = call.execute();
        
            if (response.isSuccessful()) {
    

                AppSettingsData appSettingsData = null;
                appSettingsData = response.body();
                //Log.e("DataCheck0: ",appSettingsData.getAppDetails().getMaintenance_text());
                String strJson = new Gson().toJson(appSettingsData);

                if (!TextUtils.isEmpty(appSettingsData.getSuccess()))
                    cropManagerApp.setAppSettingsDetails(appSettingsData.getAppDetails());
            }
           else {
               Log.e("Appsettings","Response is not successful");
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //*********** Request Static Pages Data from the Server ********//
    
    private void RequestStaticPagesData() {
    
        ConstantValues.ABOUT_US = cropManagerApp.getString(R.string.lorem_ipsum);
        ConstantValues.TERMS_SERVICES = cropManagerApp.getString(R.string.lorem_ipsum);
        ConstantValues.PRIVACY_POLICY = cropManagerApp.getString(R.string.lorem_ipsum);
        ConstantValues.REFUND_POLICY = cropManagerApp.getString(R.string.lorem_ipsum);
        ConstantValues.A_Z = cropManagerApp.getString(R.string.lorem_ipsum);

        Call<PagesData> call = BuyInputsAPIClient.getInstance()
                .getStaticPages
                        (
                                ConstantValues.LANGUAGE_ID
                        );
    
        try {
            Response<PagesData> response = call.execute();
    
            PagesData pages = new PagesData();
            
            if (response.isSuccessful()) {
                
                pages = response.body();
    
                if (pages.getSuccess().equalsIgnoreCase("1")) {
        
                    cropManagerApp.setStaticPagesDetails(pages.getPagesData());
        
                    for (int i=0;  i<pages.getPagesData().size();  i++) {
                        PagesDetails page = pages.getPagesData().get(i);
            
                        if (page.getSlug().equalsIgnoreCase("about-us")) {
                            ConstantValues.ABOUT_US = page.getDescription();
                        }
                        else if (page.getSlug().equalsIgnoreCase("term-services")) {
                            ConstantValues.TERMS_SERVICES = page.getDescription();
                        }
                        else if (page.getSlug().equalsIgnoreCase("privacy-policy")) {
                            ConstantValues.PRIVACY_POLICY = page.getDescription();
                        }
                        else if (page.getSlug().equalsIgnoreCase("refund-policy")) {
                            ConstantValues.REFUND_POLICY = page.getDescription();
                        }
                        else if(page.getSlug().equalsIgnoreCase("A-Z")){
                            ConstantValues.A_Z = page.getDescription();
                        }
                    }
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
