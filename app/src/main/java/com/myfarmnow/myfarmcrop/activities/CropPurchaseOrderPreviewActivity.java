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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.myfarmnow.myfarmcrop.models.CropSupplier;
import com.myfarmnow.myfarmcrop.models.CropCustomer;
import com.myfarmnow.myfarmcrop.models.CropPurchaseOrder;
import com.myfarmnow.myfarmcrop.models.CropProductItem;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CropPurchaseOrderPreviewActivity extends AppCompatActivity {

    CropPreviewItemListRecyclerAdapter itemListRecyclerAdapter;
    RecyclerView itemListRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ScrollView summaryScrollView;
    CropPurchaseOrder cropPurchaseOrder;
    CropSupplier cropSupplier;
    CropCustomer cropCustomer;
    Bitmap bitmap;
    TextView subTotalTextView, totalTextView,discountAmountTextView,referenceNumberTextView, deliveryDateTextView,notesTextView, purchaseDateTextView, shipmentPreferenceTextView,
    termsTextView,purchaseOrderNumberTextView,myAddressNameTextView,myAddressCompanyTextView,myAddressCityCountryTextView,myAddressStreetTextView,
    supplierCompanyTextView,supplierCityCountryTextView,supplierStreetTextView;

    TextView farmNameTextView, userStreetTextView, userCityTextView, userCountryTextView;

    private static final int PERMISSION_REQUEST_CODE = 1;
    public static final int BILL_ACTION_DOWNLOAD = 134;
    public static final int BILL_ACTION_EMAIL = 124;
    public static final int BILL_ACTION_PREVIEW= 114;
    MyFarmDbHandlerSingleton dbHandler;

    int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_purchase_order_preview);

        if(getIntent().hasExtra("cropPurchaseOrder")){
            cropPurchaseOrder = (CropPurchaseOrder) getIntent().getSerializableExtra("cropPurchaseOrder");
        }

        action = getIntent().getIntExtra("action",BILL_ACTION_PREVIEW);
        initializeView();


    }

    public void initializeView(){


        subTotalTextView = findViewById(R.id.txt_view_crop__purchase_order_summary_sub_total);

        supplierCompanyTextView = findViewById(R.id.text_view_purchase_order_summary_supplier_company);
        supplierCityCountryTextView = findViewById(R.id.text_view_purchase_order_summary_supplier_city_country);
        supplierStreetTextView = findViewById(R.id.text_view_purchase_order_summary_supplier_street);
        totalTextView = findViewById(R.id.txt_view_crop_purchase_order_summary_total);
        discountAmountTextView = findViewById(R.id.txt_view_crop__purchase_order_summary_discount);
        purchaseOrderNumberTextView = findViewById(R.id.text_view_crop_purchase_order_summary_number);
        purchaseDateTextView = findViewById(R.id.text_view_crop_purchase_order_summary_purchase_date);
        termsTextView = findViewById(R.id.text_view_crop_purchase_order_summary_terms);
        notesTextView = findViewById(R.id.text_view_crop_purchase_order_summary_notes);
        myAddressCompanyTextView = findViewById(R.id.text_view_purchase_order_summary_customer_name);
        myAddressNameTextView = findViewById(R.id.text_view_purchase_order_summary_customer_name);
        myAddressCityCountryTextView = findViewById(R.id.text_view_purchase_order_summary_customer_city_country);
        myAddressStreetTextView = findViewById(R.id.text_view_purchase_order_summary_customer_street);
        shipmentPreferenceTextView = findViewById(R.id.text_view_crop_purchase_order_summary_delivery_method);
        deliveryDateTextView = findViewById(R.id.text_view_crop_purchase_order_summary_delivery_date);
        referenceNumberTextView = findViewById(R.id.text_view_crop_purchase_order_summary_reference_number);

        summaryScrollView = findViewById(R.id.scroll_view_purchase_order_summary);
        itemListRecyclerView = findViewById(R.id.recyc_view_crop_invoice_item_list);

        farmNameTextView = findViewById(R.id.text_view_crop_invoice_estimate_farm_name);
        userStreetTextView = findViewById(R.id.text_view_crop_invoice_estimate_user_street);
        userCityTextView = findViewById(R.id.text_view_crop_invoice_estimate_user_city);
        userCountryTextView = findViewById(R.id.text_view_crop_invoice_estimate_user_country);

        farmNameTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.FARM_NAME_PREFERENCES_ID,this));
        myAddressCompanyTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.FARM_NAME_PREFERENCES_ID,this));
        userStreetTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.STREET_PREFERENCES_ID,this));
        myAddressStreetTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.STREET_PREFERENCES_ID,this));
        userCityTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.CITY_PREFERENCES_ID,this));
        userCountryTextView.setText(CropDashboardActivity.getPreferences(CropDashboardActivity.COUNTRY_PREFERENCES_ID,this));
        myAddressCityCountryTextView.setText(
                CropDashboardActivity.getPreferences(CropDashboardActivity.CITY_PREFERENCES_ID,this)+" , "
                        +CropDashboardActivity.getPreferences(CropDashboardActivity.COUNTRY_PREFERENCES_ID,this));



        ((TextView)findViewById(R.id.txt_view_crop_estimate_total_label)).setText("Total ("+ CropSettingsSingleton.getInstance().getCurrency()+")");

        ArrayList<CropProductItem> suppliersList = new ArrayList<>();
        for(CropProductItem x: cropPurchaseOrder.getItems()){
            suppliersList.add(x);
        }
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        itemListRecyclerAdapter = new CropPreviewItemListRecyclerAdapter(this,suppliersList);
        itemListRecyclerView.setAdapter(itemListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        itemListRecyclerView.setLayoutManager(linearLayoutManager);
        cropSupplier = dbHandler.getCropSupplier(cropPurchaseOrder.getSupplierId());
        if(cropSupplier == null){
            finish();
        }


        subTotalTextView.setText(NumberFormat.getInstance().format(cropPurchaseOrder.computeSubTotal()));
        totalTextView.setText( NumberFormat.getInstance().format(cropPurchaseOrder.computeTotal()));
        discountAmountTextView.setText(NumberFormat.getInstance().format(cropPurchaseOrder.computeDiscount()));

        purchaseOrderNumberTextView.setText("#"+cropPurchaseOrder.getNumber());
        deliveryDateTextView.setText(cropPurchaseOrder.getDeliveryDate());
        purchaseDateTextView.setText(cropPurchaseOrder.getPurchaseDate());
        notesTextView.setText(cropPurchaseOrder.getNotes());
        termsTextView.setText(cropPurchaseOrder.getTermsAndConditions());
        /*myAddressNameTextView.setText(cropCustomer.getName());
        myAddressCityCountryTextView.setText(cropCustomer.getBillingCityOrTown()+","+cropCustomer.getBillingCountry());
        myAddressStreetTextView.setText(cropCustomer.getBillingStreet());

        myAddressCompanyTextView.setText(cropCustomer.getCompany());*/

        supplierCityCountryTextView.setText(cropSupplier.getInvoiceCityOrTown()+" , "+cropSupplier.getInvoiceCountry());
        supplierStreetTextView.setText(cropSupplier.getInvoiceStreet());
        supplierCompanyTextView.setText(cropSupplier.getCompany());



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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
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

            File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), cropPurchaseOrder.getNumber() + ".pdf");
            try {
                document.writeTo(new FileOutputStream(filePath));


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
                Log.d("TEST",e.toString());
            }

            // close the document
            document.close();

            if(action==BILL_ACTION_DOWNLOAD){
                openGeneratedPDF();
            }
            else if (action==BILL_ACTION_EMAIL){
                openEmail();
            }
            else{
                //do nothing
            }

        }


    }

    private void openEmail(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), cropPurchaseOrder.getNumber() + ".pdf");
        if (file.exists()) {
            Uri contentUri = Uri.fromFile(file);
            Intent mailIntent = new Intent(Intent.ACTION_SEND);
            mailIntent.setType("message/rfc822");
            mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"robein@ymail.com"});

            mailIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mailIntent.putExtra(Intent.EXTRA_SUBJECT, "PurchaseOrder : "+cropPurchaseOrder.getNumber());
            mailIntent.putExtra(Intent.EXTRA_TEXT, "Kindly Receive this bill");
            mailIntent.putExtra(Intent.EXTRA_STREAM, contentUri);


            try {
                if (Build.VERSION.SDK_INT >= 24) {

                    Uri apkURI = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", file);
                    mailIntent.setDataAndType(apkURI, "message/rfc822");
                    mailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                } else {
                    mailIntent.setDataAndType(contentUri, "application/pdf");
                }
                startActivity(Intent.createChooser(mailIntent, "Send email.."));
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(this, "No Mail Service On this Device", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openGeneratedPDF(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), cropPurchaseOrder.getNumber() + ".pdf");
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
