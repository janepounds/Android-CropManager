package com.myfarmnow.myfarmcrop.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropPreviewItemListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropEstimate;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CropEstimatePreviewActivity extends AppCompatActivity {
    
    CropPreviewItemListRecyclerAdapter itemListRecyclerAdapter;
    RecyclerView itemListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ScrollView summaryScrollView;
    CropEstimate cropEstimate;
    CropCustomer cropCustomer;
    Bitmap bitmap;
    TextView subTotalTextView, totalTextView,shippingChargesTextView,discountAmountTextView,numberTextView, dateTextView,
            termsTextView,balanceTextView,balanceDueTextView,paymentMadeTextView, dueDateTextView,orderNumberTextView,referenceTextView,
            customerNameTextView,customerCompanyTextView,cityCountryTextView,streetTextView;
    TextView farmNameTextView, userStreetTextView, userCityTextView, userCountryTextView;
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static final int INVOICE_ACTION_DOWNLOAD = 134;
    public static final int INVOICE_ACTION_EMAIL = 124;
    public static final int INVOICE_ACTION_PREVIEW= 114;
    MyFarmDbHandlerSingleton dbHandler;

    int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_estimate_preview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("cropEstimate")){
            cropEstimate = (CropEstimate) getIntent().getSerializableExtra("cropEstimate");
        }

        action = getIntent().getIntExtra("action",INVOICE_ACTION_PREVIEW);
        initializeView();


    }

    public void initializeView(){
        //text_view_text_view_customer_city
        //
        //
        //
        //

        subTotalTextView = findViewById(R.id.txt_view_crop_estimate_sub_total);
        customerNameTextView = findViewById(R.id.text_view_invoice_summary_customer_name);
        customerCompanyTextView = findViewById(R.id.text_view_invoice_summary_customer_company);
        cityCountryTextView = findViewById(R.id.text_view_invoice_summary_city_country);
        streetTextView = findViewById(R.id.text_view_invoice_summary_customer_street);
        totalTextView = findViewById(R.id.txt_view_crop_estimate_total);
        discountAmountTextView = findViewById(R.id.txt_view_crop_estimate_discount);
        shippingChargesTextView = findViewById(R.id.txt_view_crop_estimate_shipping_charges);
        numberTextView = findViewById(R.id.text_view_crop_invoice_estimate_number);
        dateTextView = findViewById(R.id.text_view_crop_invoice_estimate_summary_date);
        termsTextView = findViewById(R.id.text_view_crop_invoice_estimate_terms);
        balanceTextView = findViewById(R.id.text_view_crop_invoice_balance);
        balanceDueTextView = findViewById(R.id.txt_view_crop_invoice_balance_due);
        paymentMadeTextView = findViewById(R.id.txt_view_crop_invoice_payment_made);
        dueDateTextView = findViewById(R.id.text_view_crop_invoice_due_date);
        orderNumberTextView = findViewById(R.id.text_view_crop_invoice_order_number);
        referenceTextView = findViewById(R.id.text_view_crop_invoice_reference_number);
        summaryScrollView = findViewById(R.id.scroll_view_invoice_summary);
        itemListRecyclerView = findViewById(R.id.recyc_view_crop_invoice_item_list);

        farmNameTextView = findViewById(R.id.text_view_crop_invoice_estimate_farm_name);
        userStreetTextView = findViewById(R.id.text_view_crop_invoice_estimate_user_street);
        userCityTextView = findViewById(R.id.text_view_crop_invoice_estimate_user_city);
        userCountryTextView = findViewById(R.id.text_view_crop_invoice_estimate_user_country);

        farmNameTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.FARM_NAME_PREFERENCES_ID,this));
        userStreetTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.STREET_PREFERENCES_ID,this));
        userCityTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.CITY_PREFERENCES_ID,this));
        userCountryTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.COUNTRY_PREFERENCES_ID,this));

        ((TextView)findViewById(R.id.txt_view_crop_estimate_total_label)).setText("Total ("+ CropSettingsSingleton.getInstance().getCurrency()+")");

        //android:id="@+id/text_view_crop_invoice_estimate_user_street"

        ArrayList<CropProductItem> customersList = new ArrayList<>();
        for(CropProductItem x: cropEstimate.getItems()){
            customersList.add(x);
        }
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        itemListRecyclerAdapter = new CropPreviewItemListRecyclerAdapter(this,customersList);
        itemListRecyclerView.setAdapter(itemListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        itemListRecyclerView.setLayoutManager(linearLayoutManager);
        cropCustomer = dbHandler.getCropCustomer(cropEstimate.getCustomerId());
        if(cropCustomer == null){
            finish();
        }
        subTotalTextView.setText(NumberFormat.getInstance().format(cropEstimate.computeSubTotal()));
        totalTextView.setText( NumberFormat.getInstance().format(cropEstimate.computeTotal()));
        shippingChargesTextView.setText(NumberFormat.getInstance().format(cropEstimate.getShippingCharges()));
        discountAmountTextView.setText(NumberFormat.getInstance().format(cropEstimate.computeDiscount()));

        numberTextView.setText("#"+cropEstimate.getNumber());
        dateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(cropEstimate.getDate()));
        referenceTextView.setText(cropEstimate.getReferenceNumber());
        dueDateTextView.setText(CropSettingsSingleton.getInstance().convertToUserFormat(cropEstimate.getExpiryDate()));

        customerNameTextView.setText(cropCustomer.getName());
        cityCountryTextView.setText(cropCustomer.getBillingCityOrTown()+" , "+cropCustomer.getBillingCountry());
        streetTextView.setText(cropCustomer.getBillingStreet());
        customerCompanyTextView.setText(cropCustomer.getCompany());



        final ViewTreeObserver observer = summaryScrollView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    summaryScrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    summaryScrollView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                bitmap = loadBitmapFromView(summaryScrollView,2*summaryScrollView.getHeight(),3*summaryScrollView.getWidth());

                // Log.d("HEIGHT ",summaryScrollView.getHeight()+": " +summaryScrollView.getMeasuredHeight());
                if (checkPermission()){
                    createPdf();
                }else{
                    requestPermission();
                }


            }
        });



    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }
    private void createPdf(){
        // = loadBitmapFromView(summaryScrollView,s)
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();

            Paint paint = new Paint();
            canvas.drawPaint(paint);

            //bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0 , null);
            document.finishPage(page);

            File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), cropEstimate.getNumber() + ".pdf");
            try {
                document.writeTo(new FileOutputStream(filePath));


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
                Log.d("TEST",e.toString());
            }

            // close the document
            document.close();

            if(action==INVOICE_ACTION_DOWNLOAD){
                openGeneratedPDF();
            }
            else if (action==INVOICE_ACTION_EMAIL){
                openEmail();
            }
            else{
                //do nothing
            }

        }


    }

    private void openEmail(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), cropEstimate.getNumber() + ".pdf");
        if (file.exists()) {
            Uri contentUri = Uri.fromFile(file);
            Intent mailIntent = new Intent(Intent.ACTION_SEND);
            mailIntent.setType("message/rfc822");
            mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{cropCustomer.getEmail()});

            mailIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Estimate : "+cropEstimate.getNumber());
            mailIntent.putExtra(Intent.EXTRA_TEXT, "Kindly Receive this invoice");
            mailIntent.putExtra(Intent.EXTRA_STREAM, contentUri);


            try {
                if (Build.VERSION.SDK_INT >= 24) {

                    Uri apkURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
                    mailIntent.setDataAndType(apkURI, "message/rfc822");
                    mailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                } else {
                    mailIntent.setDataAndType(contentUri, "message/rfc822");
                }
                startActivity(Intent.createChooser(mailIntent, "Send email.."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "No Mail Service On this Device", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openGeneratedPDF(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), cropEstimate.getNumber() + ".pdf");
        if (file.exists())
        {
            Log.d("YEY","ENTERED");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //Log.e("pathOpen", file.getPath());

            Uri contentUri;
            contentUri = Uri.fromFile(file);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            try
            {

                if (Build.VERSION.SDK_INT >= 24) {

                    Uri apkURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
                    intent.setDataAndType(apkURI, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                } else {

                    intent.setDataAndType(contentUri, "application/pdf");
                }
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(this, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "File was not created", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store files on your phone. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                    createPdf();
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

}
