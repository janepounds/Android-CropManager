package com.myfarmnow.myfarmcrop.fragments.predictiontools;

import android.Manifest;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Toast;

import com.highsoft.highcharts.common.hichartsclasses.HICSSObject;
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPie;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.core.HIChartView;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.databinding.FragmentRevenueEstimatorResultsBinding;
import com.myfarmnow.myfarmcrop.singletons.CropROICalculatorSingleton;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class RevenueEstimatorResultsFragment extends Fragment {
    private FragmentRevenueEstimatorResultsBinding binding;
    private Context context;
    private NavController navController;
    private Bitmap bitmap;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String fileName ="test";
    private HIChartView pieChartView;
//    private String currency= CropSettingsSingleton.getInstance().getCurrency()+" ";
    private String currency="UGX ";
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_revenue_estimator_results, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Revenue Estimator");
        binding.toolbar.setNavigationOnClickListener(view -> navController.popBackStack());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        initializeViews();

    }

    public void initializeViews(){

        fillViews();

        final ViewTreeObserver observer = binding.scrollViewCropRoiResult.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    binding.scrollViewCropRoiResult.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    binding.scrollViewCropRoiResult.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                bitmap = loadBitmapFromView(binding.scrollViewCropRoiResult,2*binding.scrollViewCropRoiResult.getWidth(),4*binding.scrollViewCropRoiResult.getHeight());

                // Log.d("HEIGHT ",summaryScrollView.getHeight()+": " +summaryScrollView.getMeasuredHeight());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        //createPdf();
                    } else {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_REQUEST_CODE);
                    }
                }

            }
        });
    }
    public void fillViews(){
        binding.txtCropRoiResultsReturnOnCapital.setText(CropROICalculatorSingleton.getInstance().computeReturnOnCapital()+"%");
        binding.txtCropRoiResultsReturnOverVariableCosts.setText(currency+ NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeResultReturnOverVariableCosts()));
        binding.txtCropRoiResultsReturnOverTotalExpense.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeResultReturnOverTotalExpenses()));
        binding.txtCropRoiResultsBreakEvenPriceCoverTotalExpenses.setText(NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeResultBreakevenYieldToCoverTotalExpenses()));
        binding.txtCropRoiResultsBreakEvenPriceCoverVariableExpenses.setText(NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeResultBreakevenYieldToCoverVariableExpenses()));
        binding.txtCropRoiResultsBreakEvenPriceCoverTotalExpenses.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeResultBreakevenPriceToCoverTotalExpenses()));
        binding.txtCropRoiResultsBreakEvenPriceCoverTotalExpenses.setText(currency+NumberFormat.getInstance().format(CropROICalculatorSingleton.getInstance().computeResultBreakevenPriceToCoverVariableExpenses()));

        binding.txtCropRoiResultsBreakEvenYieldCoverVariableExpensesUnits.setText(CropROICalculatorSingleton.getInstance().getStep1YieldUnits());
        binding.txtCropRoiResultsBreakEvenYieldCoverTotalExpensesUnits.setText(CropROICalculatorSingleton.getInstance().getStep1YieldUnits());

        HIOptions options = new HIOptions();
        HIChart chart = new HIChart();
        chart.setType("pie");
        chart.setBackgroundColor(null);
        chart.setPlotBorderWidth(null);
        chart.setPlotShadow(false);
        options.setChart(chart);

        HICSSObject hicssObject = new HICSSObject();
        hicssObject.setColor("#4B6F4A");

        HITitle title = new HITitle();
        title.setText("Pie Chart Representation");
        title.setStyle(hicssObject);
        options.setTitle(title);

        HITooltip tooltip = new HITooltip();
        tooltip.setPointFormat("{series.name}: <b>{point.percentage:.1f}%</b>");
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setPie(new HIPie());
        plotOptions.getPie().setAllowPointSelect(true);

        plotOptions.getPie().setCursor("pointer");
        plotOptions.getPie().setDataLabels(new HIDataLabels());
        plotOptions.getPie().getDataLabels().setEnabled(true);
        plotOptions.getPie().getDataLabels().setFormat("<b>{point.name}</b>:<br /> <center>{point.percentage:.1f}</center> %"); ;
        plotOptions.getPie().getDataLabels().setStyle(new HICSSObject());
        plotOptions.getPie().getDataLabels().getStyle().setColor("black");
        options.setPlotOptions(plotOptions);

        HICredits credits = new HICredits();
        credits.setEnabled(false);
        options.setCredits(credits);
        HIExporting exporting = new HIExporting();
        exporting.setEnabled(false);
        options.setExporting(exporting);

        HIPie pie = new HIPie();
        pie.setName("Percentage");
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name", "Income");
        map1.put("y", CropROICalculatorSingleton.getInstance().computeStep1GrossRevenue());
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("name", "Direct Expenses");
        map2.put("y",  CropROICalculatorSingleton.getInstance().computeStep2TotalVariableCosts());
        //map2.put("sliced", true);
        //map2.put("selected", true);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("name", "Overhead Expenses");
        map3.put("y", CropROICalculatorSingleton.getInstance().computeStep3TotalOverheadCosts());
        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("name", "Margin");
        map4.put("y",CropROICalculatorSingleton.getInstance().computeMargin() );

        pie.setData(new ArrayList<>(Arrays.asList(map1, map2, map3, map4)));

        options.setSeries(new ArrayList<HISeries>(Arrays.asList(pie)));
        String colors []=new String[]{"#51925E","#34463F","#97A162","#CFE8D8"};
        options.setColors(new ArrayList<String>(Arrays.asList(colors)));

        binding.chartCropResultsPie.setOptions(options);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
                createPdf();
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openGeneratedPDF(){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName + ".pdf");
        if (file.exists())
        {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            //Log.e("pathOpen", file.getPath());

            Uri contentUri;
            contentUri = Uri.fromFile(file);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            try
            {

                if (Build.VERSION.SDK_INT >= 24) {

                    Uri apkURI = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
                    intent.setDataAndType(apkURI, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                } else {

                    intent.setDataAndType(contentUri, "application/pdf");
                }
                startActivity(intent);
            }
            catch(ActivityNotFoundException e)
            {
                Toast.makeText(context, "No Application available to view pdf", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(context, "File was not created", Toast.LENGTH_LONG).show();
        }
    }
    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);

        return b;
    }
    private void createPdf(){
        // = loadBitmapFromView(summaryScrollView,s)
        WindowManager wm = (WindowManager) requireActivity().getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
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

            PdfDocument.PageInfo pageInfo2 = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 2).create();
            PdfDocument.Page page2 = document.startPage(pageInfo);

            Canvas canvas2 = page.getCanvas();

            Paint paint2 = new Paint();
            canvas.drawPaint(paint);

            //bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0 , null);
            document.finishPage(page);

            File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName + ".pdf");
            try {
                document.writeTo(new FileOutputStream(filePath));


            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
                Log.d("TEST",e.toString());
            }

            // close the document
            document.close();

            openGeneratedPDF();


        }


    }

}