package com.myfarmnow.myfarmcrop.fragments.farmrecords.crop;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.highsoft.highcharts.common.hichartsclasses.HICSSObject;
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HIData;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPie;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.core.HIChartView;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.DashboardActivity;
import com.myfarmnow.myfarmcrop.adapters.CropIncomeExpensesListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.GraphRecord;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class CropProductionReports extends Fragment {
    HIChartView incomesCategoryPieChartView,expensesCategoryPieChartView,cropsExpensesPieChartView,expensesByActivityPieChartView;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner incomeCategoryRangeSpinner,expensesCategoryRangeSpinner,expensesActivityRangeSpinner,yearsSpinner,seasonsSpinner;;
    String currency = CropSettingsSingleton.getInstance().getCurrency();
    private Context context;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crop_production_reports, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Production Reports");
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(v -> navController.popBackStack());

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(context);
        incomesCategoryPieChartView =view.findViewById(R.id.chart_crop_farm_reports_income_category_pie);
        incomeCategoryRangeSpinner =view.findViewById(R.id.spinner_crop_farm_reports_income_category_range);
        expensesCategoryPieChartView =view.findViewById(R.id.chart_crop_farm_reports_expense_category_pie);
        expensesByActivityPieChartView =view.findViewById(R.id.chart_crop_farm_reports_expense_activity_pie);
        cropsExpensesPieChartView =view.findViewById(R.id.chart_crop_farm_reports_crops_expenses_pie_activity_pie);

        expensesCategoryRangeSpinner =view.findViewById(R.id.spinner_crop_farm_reports_expense_category_range);
        expensesActivityRangeSpinner =view.findViewById(R.id.spinner_crop_farm_reports_expense_activity_range);
        incomesCategoryPieChartView.setOptions(new HIOptions());
        expensesCategoryPieChartView.setOptions(new HIOptions());
        expensesByActivityPieChartView.setOptions(new HIOptions());
        cropsExpensesPieChartView.setOptions(new HIOptions());

        yearsSpinner = view.findViewById(R.id.crop_graphs_bar_crop_income_year_spinner);
        seasonsSpinner = view.findViewById(R.id.crop_graphs_bar_crop_income_season_spinner);

        final Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);

        int endYear = cal.get(Calendar.YEAR);

        ArrayList<String> years = new ArrayList<String>();
        for(int yr =endYear;yr>= 2015; yr--){
            years.add(yr+"");
        }
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,years);
        yearsSpinner.setAdapter(yearsAdapter);

        drawIncomesCategoryPie(year+"-01-01",year+"-12-31");
        drawExpensesCategoryPie(year+"-01-01",year+"-12-31");
//        drawExpensesByActivityPie(year+"-01-01",year+"-12-31");
//        drawExpensesCropsPie(year,"First Season");

        incomeCategoryRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> dates = getGraphDateRange(position);
                drawIncomesCategoryPie(dates.get("startDate"),dates.get("endDate"));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        expensesCategoryRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String>dates = getGraphDateRange(position);
                drawExpensesCategoryPie(dates.get("startDate"),dates.get("endDate"));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        expensesActivityRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                HashMap<String, String>dates = getGraphDateRange(position);
//                drawExpensesByActivityPie(dates.get("startDate"),dates.get("endDate"));
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        yearsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                drawExpensesCropsPie(Integer.parseInt(yearsSpinner.getSelectedItem().toString()),seasonsSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        seasonsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                drawExpensesCropsPie(Integer.parseInt(yearsSpinner.getSelectedItem().toString()),seasonsSpinner.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    public void drawExpensesCropsPie(int year, String season){
        HIOptions options = cropsExpensesPieChartView.getOptions();

        HIChart chart = new HIChart();
        chart.setType("pie");
        chart.setBackgroundColor(null);
        chart.setPlotBorderWidth(0);
        chart.setPlotShadow(false);
        options.setChart(chart);



        HITooltip tooltip = new HITooltip();
        tooltip.setPointFormat("{series.name}: <b>"+currency+"{point.y:.2f} ({point.percentage:.1f}%)</b>");
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setPie(new HIPie());
//        plotOptions.getPie().setDataLabels(new HIDataLabels());
//        plotOptions.getPie().getDataLabels().setEnabled(false);
//        plotOptions.getPie().getDataLabels().setDistance(-50);
//        plotOptions.getPie().getDataLabels().setStyle(new HICSSObject());
//        plotOptions.getPie().getDataLabels().getStyle().setFontWeight("bold");
//        plotOptions.getPie().getDataLabels().getStyle().setColor("white");
        options.setPlotOptions(plotOptions);

        HIPie series1 = new HIPie();
        series1.setName("Income: ");
        series1.setInnerSize("50%");



        HICredits credits = new HICredits();
        credits.setEnabled(false);
        options.setCredits(credits);
        HIExporting exporting = new HIExporting();
        exporting.setEnabled(false);
        options.setExporting(exporting);
        ArrayList<GraphRecord> incomeRecords = dbHandler.getGraphExpensesByCrop(year,season);


        ArrayList<HIData> pieItems = new ArrayList<>();
        LinkedHashMap<String, Double> cropIncomes = groupRecordByCategory(incomeRecords);
        System.out.println(cropIncomes+" CROPS");
        float total = 0;
        for(String category : cropIncomes.keySet()){
            HIData dataPoint = new HIData();
            dataPoint.setY(cropIncomes.get(category));
            dataPoint.setName(category);
            pieItems.add(dataPoint);
            total+=cropIncomes.get(category);
        }
        series1.setData(pieItems);


        HITitle title = new HITitle();
        title.setText("Total <br />"+currency+ NumberFormat.getInstance().format(total));
        title.setAlign("center");
        title.setVerticalAlign("middle");
        title.setY(0);
        options.setTitle(title);

        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("Expenses By Crop");
        options.setSubtitle(subtitle);
        options.setSeries(new ArrayList<HISeries>(Arrays.asList(series1)));

        cropsExpensesPieChartView.setOptions(options);
    }

    public static HashMap<String, String> getGraphDateRange(int position){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> dates = new HashMap<>();
        String endDate ="";
        String startDate ="";
        if(position==3){
            //Last 12 months
            Calendar rightNow = Calendar.getInstance();
            rightNow.add(Calendar.DAY_OF_MONTH, 1);
            endDate= format.format(rightNow.getTime());
            rightNow.add(Calendar.DAY_OF_MONTH, -1); //neutralize the add
            rightNow.add(Calendar.MONTH,-11);
            rightNow.set(Calendar.DAY_OF_MONTH, 1);
            rightNow.add(Calendar.DAY_OF_MONTH, -1);
            startDate = format.format(rightNow.getTime());

        }
        else if(position==1){
            //3 months
            Calendar rightNow = Calendar.getInstance();
            // rightNow.set(Calendar.DAY_OF_MONTH, 1);
            rightNow.add(Calendar.DAY_OF_MONTH,1);
            endDate = format.format(rightNow.getTime());
            rightNow.add(Calendar.DAY_OF_MONTH, -1);//neutralize the add
            rightNow.add(Calendar.MONTH,-3);
            // rightNow.set(Calendar.DAY_OF_MONTH, 1);
            rightNow.add(Calendar.DAY_OF_MONTH, -1);
            startDate  = format.format(rightNow.getTime());

        }
        else if(position==2){
            //6 months
            Calendar rightNow = Calendar.getInstance();
            //rightNow.set(Calendar.DAY_OF_MONTH, 1);
            rightNow.add(Calendar.DAY_OF_MONTH,1);
            endDate = format.format(rightNow.getTime());
            rightNow.add(Calendar.DAY_OF_MONTH, -1);//neutralize the add
            rightNow.add(Calendar.MONTH,-6);
            rightNow.set(Calendar.DAY_OF_MONTH, 1);
            rightNow.add(Calendar.DAY_OF_MONTH, -1);
            startDate  = format.format(rightNow.getTime());

        }
        else if(position==4){
            //Last year
            Calendar rightNow = Calendar.getInstance();
            rightNow.add(Calendar.YEAR,-1);
            rightNow.set(Calendar.MONTH,0);
            rightNow.set(Calendar.DAY_OF_MONTH, 1);
            rightNow.add(Calendar.DAY_OF_MONTH,-1);
            startDate = format.format(rightNow.getTime());
            rightNow.add(Calendar.DAY_OF_MONTH,1);//neutralize the add
            rightNow.add(Calendar.MONTH,11);
            rightNow.set(Calendar.DAY_OF_MONTH, 31);
            rightNow.add(Calendar.DAY_OF_MONTH, 1);
            endDate = format.format(rightNow.getTime());

        }
        else if(position==0){
            //This year
            Calendar rightNow = Calendar.getInstance();
            rightNow.set(Calendar.MONTH,0);
            rightNow.set(Calendar.DAY_OF_MONTH, 1);
            rightNow.add(Calendar.DAY_OF_MONTH,-1);
            startDate = format.format(rightNow.getTime());
            rightNow.add(Calendar.DAY_OF_MONTH,1);
            rightNow.add(Calendar.MONTH,11);
            rightNow.set(Calendar.DAY_OF_MONTH, 31);
            rightNow.add(Calendar.DAY_OF_MONTH, 1);
            endDate = format.format(rightNow.getTime());

        }
        dates.put("endDate",endDate);
        dates.put("startDate",startDate);

        return dates;
    }

    public void drawExpensesByActivityPie(String startDate, String endDate){
        HIOptions options = expensesByActivityPieChartView.getOptions();

        HIChart chart = new HIChart();
        chart.setType("pie");
        chart.setBackgroundColor(null);
        chart.setPlotBorderWidth(0);
        chart.setPlotShadow(false);
        options.setChart(chart);



        HITooltip tooltip = new HITooltip();
        tooltip.setPointFormat("{series.name}: <b>"+currency+"{point.y:.2f} ({point.percentage:.1f}%)</b>");
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setPie(new HIPie());
//        plotOptions.getPie().setDataLabels(new HIDataLabels());
//        plotOptions.getPie().getDataLabels().setEnabled(false);
//        plotOptions.getPie().getDataLabels().setDistance(-50);
//        plotOptions.getPie().getDataLabels().setStyle(new HICSSObject());
//        plotOptions.getPie().getDataLabels().getStyle().setFontWeight("bold");
//        plotOptions.getPie().getDataLabels().getStyle().setColor("white");
        options.setPlotOptions(plotOptions);

        HIPie series1 = new HIPie();
        series1.setName("Expenses: ");
        series1.setInnerSize("50%");



        HICredits credits = new HICredits();
        credits.setEnabled(false);
        options.setCredits(credits);
        HIExporting exporting = new HIExporting();
        exporting.setEnabled(false);
        options.setExporting(exporting);
        ArrayList<GraphRecord> incomeRecords = dbHandler.getGraphExpensesByActivity(startDate,endDate);


        ArrayList<HIData> pieItems = new ArrayList<>();
        LinkedHashMap<String, Double> cropIncomes = groupRecordByCategory(incomeRecords);
        System.out.println(cropIncomes+" CROPS");
        float total = 0;
        for(String category : cropIncomes.keySet()){
            HIData dataPoint = new HIData();
            dataPoint.setY(cropIncomes.get(category));
            dataPoint.setName(category);
            pieItems.add(dataPoint);
            total+=cropIncomes.get(category);
        }
        series1.setData(pieItems);


        HITitle title = new HITitle();
        title.setText("Total <br />"+currency+ NumberFormat.getInstance().format(total));
        title.setAlign("center");
        title.setVerticalAlign("middle");
        title.setY(0);
        options.setTitle(title);

        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("Expenses By Activity");
        options.setSubtitle(subtitle);
        options.setSeries(new ArrayList<HISeries>(Arrays.asList(series1)));

        expensesByActivityPieChartView.setOptions(options);
    }

    public void drawExpensesCategoryPie(String startDate, String endDate){
        HIOptions options = expensesCategoryPieChartView.getOptions();

        HIChart chart = new HIChart();
        chart.setType("pie");
        chart.setBackgroundColor(null);
        chart.setPlotBorderWidth(0);
        chart.setPlotShadow(false);
        options.setChart(chart);



        HITooltip tooltip = new HITooltip();
        tooltip.setPointFormat("{series.name}: <b>"+currency+"{point.y:.2f} ({point.percentage:.1f}%)</b>");
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setPie(new HIPie());
//        plotOptions.getPie().setDataLabels(new HIDataLabels());
//        plotOptions.getPie().getDataLabels().setEnabled(false);
//        plotOptions.getPie().getDataLabels().setDistance(-50);
//        plotOptions.getPie().getDataLabels().setStyle(new HICSSObject());
//        plotOptions.getPie().getDataLabels().getStyle().setFontWeight("bold");
//        plotOptions.getPie().getDataLabels().getStyle().setColor("white");
        options.setPlotOptions(plotOptions);

        HIPie series1 = new HIPie();
        series1.setName("Expenses: ");
        series1.setInnerSize("50%");



        HICredits credits = new HICredits();
        credits.setEnabled(false);
        options.setCredits(credits);
        HIExporting exporting = new HIExporting();
        exporting.setEnabled(false);
        options.setExporting(exporting);
        ArrayList<GraphRecord> incomeRecords = dbHandler.getGraphExpensesByCategory(startDate,endDate);


        ArrayList<HIData> pieItems = new ArrayList<>();
        LinkedHashMap<String, Double> cropIncomes = groupRecordByCategory(incomeRecords);
        System.out.println(cropIncomes+" CROPS");
        float total = 0;
        for(String category : cropIncomes.keySet()){
            HIData dataPoint = new HIData();
            dataPoint.setY(cropIncomes.get(category));
            dataPoint.setName(category);
            pieItems.add(dataPoint);
            total+=cropIncomes.get(category);
        }
        series1.setData(pieItems);


        HITitle title = new HITitle();
        title.setText("Total <br />"+currency+ NumberFormat.getInstance().format(total));
        title.setAlign("center");
        title.setVerticalAlign("middle");
        title.setY(0);
        options.setTitle(title);

        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("Expenses By Category");
        options.setSubtitle(subtitle);
        options.setSeries(new ArrayList<HISeries>(Arrays.asList(series1)));

        expensesCategoryPieChartView.setOptions(options);
    }

    public void drawIncomesCategoryPie(String startDate, String endDate){

        HIOptions options = incomesCategoryPieChartView.getOptions();

        HIChart chart = new HIChart();
        chart.setType("pie");
        chart.setBackgroundColor(null);
        chart.setPlotBorderWidth(0);
        chart.setPlotShadow(false);
        options.setChart(chart);



        HITooltip tooltip = new HITooltip();
        tooltip.setPointFormat("{series.name}: <b>"+currency+"{point.y:.2f} ({point.percentage:.1f}%)</b>");
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setPie(new HIPie());
//        plotOptions.getPie().setDataLabels(new HIDataLabels());
//        plotOptions.getPie().getDataLabels().setEnabled(false);
//        plotOptions.getPie().getDataLabels().setDistance(-50);
//        plotOptions.getPie().getDataLabels().setStyle(new HICSSObject());
//        plotOptions.getPie().getDataLabels().getStyle().setFontWeight("bold");
//        plotOptions.getPie().getDataLabels().getStyle().setColor("white");
        options.setPlotOptions(plotOptions);

        HIPie series1 = new HIPie();
        series1.setName("Income: ");
        series1.setInnerSize("50%");



        HICredits credits = new HICredits();
        credits.setEnabled(false);
        options.setCredits(credits);
        HIExporting exporting = new HIExporting();
        exporting.setEnabled(false);
        options.setExporting(exporting);
        ArrayList<GraphRecord> incomeRecords = dbHandler.getGraphIncomes(startDate,endDate);


        ArrayList<HIData> pieItems = new ArrayList<>();
        LinkedHashMap<String, Double> cropIncomes = groupRecordByCategory(incomeRecords);
        float total = 0;
        for(String category : cropIncomes.keySet()){
            HIData dataPoint = new HIData();
            dataPoint.setY(cropIncomes.get(category));
            dataPoint.setName(category);
            pieItems.add(dataPoint);
            total+=cropIncomes.get(category);
        }
        series1.setData(pieItems);


        HITitle title = new HITitle();
        title.setText("Total <br />"+currency+ NumberFormat.getInstance().format(total));
        title.setAlign("center");
        title.setVerticalAlign("middle");
        title.setY(0);
        options.setTitle(title);

        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("Income By Category");
        options.setSubtitle(subtitle);
        options.setSeries(new ArrayList<HISeries>(Arrays.asList(series1)));

        incomesCategoryPieChartView.setOptions(options);
    }

    public LinkedHashMap<String, Double> groupRecordByCategory(ArrayList<GraphRecord> graphRecords){

        LinkedHashMap<String, Double> groups = new LinkedHashMap<>();

        for (GraphRecord record:graphRecords){
            String category = record.getCategory();
            if(groups.containsKey(category)){
                groups.put(category,groups.get(category)+record.getAmount());
            }
            else{
                groups.put(category,(double)record.getAmount());
            }

        }

        return groups;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.home) {
            navController.navigate(R.id.action_productionReportsFragment_to_cropRecordsFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
