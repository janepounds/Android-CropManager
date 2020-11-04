package com.myfarmnow.myfarmcrop.app;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import androidx.multidex.MultiDexApplication;

import com.myfarmnow.myfarmcrop.TypefaceUtil;
import com.myfarmnow.myfarmcrop.constants.ConstantValues;
import com.myfarmnow.myfarmcrop.database.BuyInputsDB_Handler;
import com.myfarmnow.myfarmcrop.database.BuyInputsDB_Manager;
import com.myfarmnow.myfarmcrop.models.address_model.AddressDetails;
import com.myfarmnow.myfarmcrop.models.banner_model.BannerDetails;
import com.myfarmnow.myfarmcrop.models.category_model.CategoryDetails;
import com.myfarmnow.myfarmcrop.models.device_model.AppSettingsDetails;
import com.myfarmnow.myfarmcrop.models.pages_model.PagesDetails;
import com.myfarmnow.myfarmcrop.models.product_model.ProductDetails;
import com.myfarmnow.myfarmcrop.models.shipping_model.ShippingService;
//import com.google.common.io.BaseEncoding;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * App extending Application, is used to save some Lists and Objects with Application Context.
 **/


public class CropManagerApp extends MultiDexApplication {

    // Application Context
    private static Context context;
    private static BuyInputsDB_Handler db_handler;


    
    private AppSettingsDetails appSettingsDetails = null;
    private List<BannerDetails> bannersList = new ArrayList<>();
    private List<CategoryDetails> categoriesList = new ArrayList<>();
    private List<PagesDetails> staticPagesDetails = new ArrayList<>();

    private String tax = "";
    private ShippingService shippingService = null;
    private AddressDetails shippingAddress = new AddressDetails();
    private AddressDetails billingAddress = new AddressDetails();
    private ProductDetails productDetails = new ProductDetails();



    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/JosefinSans-Regular.ttf"); // font from assets: "assets/fonts/JosefinSans-Regular.ttf

        // set App Context
        context = this.getApplicationContext();




        // initialize DB_Handler and DB_Manager
        db_handler = new BuyInputsDB_Handler();
        BuyInputsDB_Manager.initializeInstance(db_handler);
        String pkg_name = context.getPackageName();
        ConstantValues.PKG_NAME = pkg_name;
        //ConstantValues.SHA1 = getSHA1(pkg_name);


    }

    //*********** Returns Application Context ********//

    public static Context getContext() {
        return context;
    }


    public AppSettingsDetails getAppSettingsDetails() {
        return appSettingsDetails;
    }
    
    public void setAppSettingsDetails(AppSettingsDetails appSettingsDetails) {
        this.appSettingsDetails = appSettingsDetails;
    }
    
    public List<BannerDetails> getBannersList() {
        return bannersList;
    }
    
    public void setBannersList(List<BannerDetails> bannersList) {
        this.bannersList = bannersList;
    }
    
    public List<CategoryDetails> getCategoriesList() {
        return categoriesList;
    }
    
    public void setCategoriesList(List<CategoryDetails> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public List<PagesDetails> getStaticPagesDetails() {
        return staticPagesDetails;
    }
    
    public void setStaticPagesDetails(List<PagesDetails> staticPagesDetails) {
        this.staticPagesDetails = staticPagesDetails;
    }


    public String getTax() {
        return tax;
    }
    
    public void setTax(String tax) {
        this.tax = tax;
    }
    
    public ShippingService getShippingService() {
        return shippingService;
    }
    
    public void setShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }
    
    
    public AddressDetails getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDetails shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressDetails getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDetails billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    
    private String getSHA1(String packageName){
        try {
            Signature[] signatures = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
            for (Signature signature: signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA-1");
                md.update(signature.toByteArray());
                //return BaseEncoding.base16().encode(md.digest());
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }
}


