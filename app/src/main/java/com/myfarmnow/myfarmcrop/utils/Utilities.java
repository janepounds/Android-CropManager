package com.myfarmnow.myfarmcrop.utils;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.ColorRes;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.graphics.drawable.DrawableCompat;

import com.myfarmnow.myfarmcrop.BuildConfig;
import com.myfarmnow.myfarmcrop.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


import static android.content.Context.LOCATION_SERVICE;


/**
 * Utilities class has different static methods, that can be implemented in any other class
 **/

public class Utilities {


    //*********** Checks if the Device is Connected to any Network ********//

    public static boolean isNetworkAvailable(Context activity) {

        ConnectivityManager connectivity = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Network[] networks = connectivity.getAllNetworks();
            NetworkInfo networkInfo;

            for (Network mNetwork : networks) {

                networkInfo = connectivity.getNetworkInfo(mNetwork);

                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }

        }
        else {
            if (connectivity != null) {

                NetworkInfo[] info = connectivity.getAllNetworkInfo();

                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }


    public static boolean hasActiveInternetConnection(Context context) {

        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(10000);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e("Net_Available", "Error checking internet connection", e);
            }
        } else {
            Log.d("Net_Available", "No network available!");
        }
        return false;
    }
    



    @SuppressLint("SimpleDateFormat")
    public static String formatData(String data, Context context) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        Date date;
        String strData = "";
        try {
            date = parser.parse(data);
            strData = DateUtils.getRelativeDateTimeString(context, date.getTime(), DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strData;
    }

    // Reconvert Currency Code to Symbol
    public static String getCurrencySymbol(String currencyCode) {
        try {
            Currency currency = Currency.getInstance(currencyCode);
            return currency.getSymbol();
        } catch (Exception e) {
            return currencyCode;
        }
    }
    
    //*********** Convert given String to Md5Hash ********//
    
    public static String getMd5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String md5 = number.toString(16);
            
            while (md5.length() < 32)
                md5 = "0" + md5;
            
            return md5;
        } catch (NoSuchAlgorithmException e) {
            Log.e("MD5", e.getLocalizedMessage());
            return null;
        }
    }
    
    

    
    //*********** Returns the current DataTime of Device ********//

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();

        return dateFormat.format(date);
    }



    //*********** Used to Tint the MenuIcons ********//

    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
        Drawable normalDrawable = item.getIcon();
        Drawable wrapDrawable = DrawableCompat.wrap(normalDrawable);
        DrawableCompat.setTint(wrapDrawable, context.getResources().getColor(color));

        item.setIcon(wrapDrawable);
    }


    //*********** Checks if the Date is not Passed ********//

    public static boolean checkIsDatePassed(String givenDate) {

        boolean isPassed = false;

        Date dateGiven, dateSystem;

        Calendar calender = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = dateFormat.format(calender.getTime());

        try {
            dateSystem = dateFormat.parse(currentDate);
            dateGiven = dateFormat.parse(givenDate);

            if (dateSystem.getTime() >= dateGiven.getTime()) {
                isPassed = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isPassed;
    }



    public static String getCompleteAddressString(Context context,double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    public static String getCityCountry(Context context,double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                strReturnedAddress.append(returnedAddress.getLocality()).append(" ");
                strReturnedAddress.append(returnedAddress.getCountryName()).append("");

                strAdd = strReturnedAddress.toString();
                Log.w("Current loction address", strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current loction address", "Canont get Address!");
        }
        return strAdd;
    }


    //*********** Used to Share the App with Others ********//

    public static void shareMyApp(Context context) {
    
        String link = "https://play.google.com/store/apps/details?id="+context.getPackageName();
        
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/*");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, link);
        context.startActivity(Intent.createChooser(sharingIntent, "Share Via"));
    }



    //*********** Shares the Product with its Image and Url ********//

    public static void shareProduct(Context context, String subject, ImageView imageView, String url) {

        Uri bmpUri = getLocalBitmapUri(context, imageView);

        if (bmpUri != null) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/*");
            shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.putExtra(Intent.EXTRA_TEXT, url);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);

            context.startActivity(Intent.createChooser(shareIntent, "Share via"));

        } else {
            Toast.makeText(context, "Null bmpUri", Toast.LENGTH_SHORT).show();
        }
    }



    //*********** Convert Bitmap into Uri ********//

    public static Uri getLocalBitmapUri(Context context, ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = null;
        
        if (drawable instanceof BitmapDrawable){
            bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        }
        else {
            Toast.makeText(context, "drawable isn't instanceof BitmapDrawable", Toast.LENGTH_SHORT).show();
            return null;
        }
        
        // Store image to default external storage directory
        Uri bitmapUri = null;
        
        try {
            // Use methods on Context to access package-specific directories on external storage.
            // This way, you don't need to request external read/write permission.
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            
//            bitmapUri = Uri.fromFile(file);
            bitmapUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
            
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "IOException"+e, Toast.LENGTH_SHORT).show();
        }
        
        
        return bitmapUri;
    }



    //*********** Converts any Bitmap to Base64String ********//

    public static String getBase64ImageStringFromBitmap(Bitmap bitmap) {
        String imgString;
        
        if(bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            
            byte[] profileImage = byteArrayOutputStream.toByteArray();

            imgString = "data:image/jpeg;base64,"+Base64.encodeToString(profileImage, Base64.NO_WRAP);
            
        }
        else{
            imgString = "";
        }
        
        
        return imgString;
    }



    //*********** Converts a Base64String to the Bitmap ********//

    public static Bitmap getBitmapFromBase64ImageString(String b64) {
        Bitmap bitmap = null;
        
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        
        
        return bitmap;
    }
    

}

