package com.myfarmnow.myfarmcrop.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.highsoft.highcharts.common.HIColor;
import com.highsoft.highcharts.common.hichartsclasses.HICSSObject;
import com.highsoft.highcharts.common.hichartsclasses.HIChart;
import com.highsoft.highcharts.common.hichartsclasses.HIColumn;
import com.highsoft.highcharts.common.hichartsclasses.HICredits;
import com.highsoft.highcharts.common.hichartsclasses.HICrosshair;
import com.highsoft.highcharts.common.hichartsclasses.HIData;
import com.highsoft.highcharts.common.hichartsclasses.HIDataLabels;
import com.highsoft.highcharts.common.hichartsclasses.HIExporting;
import com.highsoft.highcharts.common.hichartsclasses.HILabels;
import com.highsoft.highcharts.common.hichartsclasses.HILegend;
import com.highsoft.highcharts.common.hichartsclasses.HIOptions;
import com.highsoft.highcharts.common.hichartsclasses.HIPie;
import com.highsoft.highcharts.common.hichartsclasses.HIPlotOptions;
import com.highsoft.highcharts.common.hichartsclasses.HISeries;
import com.highsoft.highcharts.common.hichartsclasses.HISubtitle;
import com.highsoft.highcharts.common.hichartsclasses.HITitle;
import com.highsoft.highcharts.common.hichartsclasses.HITooltip;
import com.highsoft.highcharts.common.hichartsclasses.HIXAxis;
import com.highsoft.highcharts.common.hichartsclasses.HIYAxis;
import com.highsoft.highcharts.core.HIChartView;
import com.highsoft.highcharts.core.HIFunction;
import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.GraphRecord;
import com.myfarmnow.myfarmcrop.singletons.CropSettingsSingleton;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static com.myfarmnow.myfarmcrop.activities.CropFarmReportsActivity.getGraphDateRange;

/**
 * A simple {@link Fragment} subclass.
 */
public class CropDashboardGraphsFragment extends Fragment {


    public CropDashboardGraphsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    HIChartView incomeExpenserBarChartView,cropsIncomePieChartView;
    TextView totalIncomeTextView,totalExpensesTextView, profitMarginTextView;
    MyFarmDbHandlerSingleton dbHandler;
    Spinner barRangeSpinner,yearsSpinner,seasonsSpinner;
    String currency = CropSettingsSingleton.getInstance().getCurrency()+" ";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_crop_dashboard_graphs, container, false);
        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(getActivity());
        incomeExpenserBarChartView = view.findViewById(R.id.chart_crop_income_bar_graph);
        cropsIncomePieChartView = view.findViewById(R.id.chart_crop_crops_pie_graph);
        totalIncomeTextView = view.findViewById(R.id.text_view_crop_income_expense_total_income);
        totalExpensesTextView = view.findViewById(R.id.text_view_crop_income_expense_total_expenses);
        profitMarginTextView = view.findViewById(R.id.text_view_crop_income_expense_total_profit_margin);
        incomeExpenserBarChartView.setOptions(new HIOptions());
        cropsIncomePieChartView.setOptions(new HIOptions());

        barRangeSpinner = view.findViewById(R.id.crop_graphs_bar_income_expense_spinner);
        yearsSpinner = view.findViewById(R.id.crop_graphs_bar_crop_income_year_spinner);
        seasonsSpinner = view.findViewById(R.id.crop_graphs_bar_crop_income_season_spinner);

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int endYear = cal.get(Calendar.YEAR);

        ArrayList<String> years = new ArrayList<String>();
        for(int year =endYear;year>= 2015; year--){
            years.add(year+"");
        }
        ArrayAdapter<String> yearsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,years);
        yearsSpinner.setAdapter(yearsAdapter);
        drawGraphs(endYear+"-01-01",endYear+"-12-31");
        drawCropsPie(endYear,"First Season");

        /*
            <item>This year</item>
        <item>Last 3 months</item>
        <item>Last 6 months</item>
        <item>Last 12 months</item>
        <item>Last year</item>
         */

        barRangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getActivity().getColor(R.color.financial_summary_summary_graph_text_color));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.financial_summary_summary_graph_text_color)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                HashMap<String, String> dates = getGraphDateRange(position);
                drawGraphs(dates.get("startDate"),dates.get("endDate"));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getActivity().getColor(R.color.financial_summary_summary_graph_text_color));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.financial_summary_summary_graph_text_color)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                drawCropsPie(Integer.parseInt(yearsSpinner.getSelectedItem().toString()),seasonsSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        seasonsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ((TextView) view).setTextColor(getActivity().getColor(R.color.financial_summary_summary_graph_text_color));

                    }
                    else {
                        ((TextView) view).setTextColor(getResources().getColor(R.color.financial_summary_summary_graph_text_color)); //Change selected text color
                    }
                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP,14);//Change selected text size
                }catch (Exception e){

                }
                drawCropsPie(Integer.parseInt(yearsSpinner.getSelectedItem().toString()),seasonsSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        seasonsSpinner.setSelection(0);
        yearsSpinner.setSelection(0);
        barRangeSpinner.setSelection(0);


        return view;
    }


    public void drawGraphs(String startDate, String endDate){
        float totalIncome=0, totalExpenses =0,margin=0;
        ArrayList<GraphRecord> expenseRecords = dbHandler.getGraphExpensesByCategory(startDate,endDate);
        LinkedHashMap<String, Double> expensesPerMonth = groupRecordByMonth(expenseRecords,startDate,endDate);

        ArrayList<HIData> expenses = new ArrayList<>();
        for(String month : expensesPerMonth.keySet()){
            HIData dataPoint = new HIData();
            dataPoint.setY(expensesPerMonth.get(month));
            expenses.add(dataPoint);
            totalExpenses+=expensesPerMonth.get(month);
        }


        ArrayList<GraphRecord> incomeRecords =dbHandler.getGraphIncomes(startDate,endDate);

        LinkedHashMap<String, Double> incomesPerMonth = groupRecordByMonth(incomeRecords,startDate,endDate);




        ArrayList<HIData> incomes = new ArrayList<>();


        for(String month : incomesPerMonth.keySet()){
            HIData dataPoint = new HIData();
            dataPoint.setY(incomesPerMonth.get(month));
            dataPoint.setName(month);
            incomes.add(dataPoint);
            totalIncome+=incomesPerMonth.get(month);

        }

        margin = totalIncome-totalExpenses;
        profitMarginTextView.setText(currency+NumberFormat.getInstance().format(margin));
        totalIncomeTextView.setText(currency+NumberFormat.getInstance().format(totalIncome));
        totalExpensesTextView.setText(currency+NumberFormat.getInstance().format(totalExpenses));

        HIOptions barGraphOptions = incomeExpenserBarChartView.getOptions();

        HIChart barChart = new HIChart();
        barChart.setType( "column");
        barChart.setPlotBorderWidth(0);
        barChart.setPlotShadow(false);

        barChart.setBackgroundColor(HIColor.initWithHexValue("B6C5B6"));

        barGraphOptions.setChart(barChart);

        HICredits credits = new HICredits();
        credits.setEnabled(false);
        barGraphOptions.setCredits(credits);
        HIExporting exporting = new HIExporting();
        exporting.setEnabled(false);
        barGraphOptions.setExporting(exporting);



        HITitle title = new HITitle();
        title.setText("");
        barGraphOptions.setTitle(title);


        HIColumn expensesBarSeries = new HIColumn();
        expensesBarSeries.setName("Expense");
        expensesBarSeries.setData(expenses);
       expensesBarSeries.setColor(HIColor.initWithHexValue("C1CC28"));

        HIColumn incomeBarSeries = new HIColumn();
        incomeBarSeries.setName("Income");
        incomeBarSeries.setData(incomes);
        incomeBarSeries.setColor(HIColor.initWithHexValue("49C051"));

        HICSSObject hicssObject = new HICSSObject();
        hicssObject.setColor("#4B6F4A");


        barGraphOptions.setSeries(new ArrayList<HISeries>(Arrays.asList(expensesBarSeries,incomeBarSeries)));

        //growthLineChartHiOptions.setTitle(subtitle);
        System.out.println(incomesPerMonth.keySet());
        final HIXAxis xAxis = new HIXAxis();
        xAxis.setCategories(new ArrayList<>(incomesPerMonth.keySet()));
        HITitle xTitle = new HITitle();
        xTitle.setText("Month");
        xTitle.setStyle(hicssObject);
        xAxis.setTitle(xTitle);

        xAxis.setLabels(new HILabels());

        xAxis. getLabels().setStyle(hicssObject);

        xAxis.setCrosshair(new HICrosshair());
        barGraphOptions.setXAxis(new ArrayList<HIXAxis>(){{add(xAxis);}});

        final HIYAxis yAxis = new HIYAxis();
        yAxis.setMin(0);
        yAxis.setLabels(new HILabels());
        yAxis.setTitle( new HITitle());
        yAxis. getLabels().setStyle(hicssObject);
        yAxis. getTitle().setStyle(hicssObject);
        yAxis.getTitle().setText("Amount "+currency);
        barGraphOptions.setYAxis(new ArrayList<HIYAxis>(){{add(yAxis);}});

        HITooltip tooltip = new HITooltip();
        tooltip.setHeaderFormat( "<span style=\"font-size:10px\">{point.key}</span><table>");
        tooltip.setPointFormat("<tr><td style=\"color:{series.color};padding:0\">{series.name}: </td><td style=\"padding:0\"><b>{point.y:.1f}</b></td></tr>");
        tooltip.setFooterFormat("</table>");
        tooltip.setShared(true);
        tooltip.setUseHTML(true);
        barGraphOptions.setTooltip(tooltip);
        //growthLineChartHiOptions.setTooltip(tooltip);


        HILegend legend = new HILegend();
        legend.setLayout("horizontal");
        legend.setAlign("center");
        legend.setVerticalAlign("bottom");
        legend.setX(-40);
        legend.setY(80);
        legend.setFloating(true);
        legend.setBorderWidth(1);
        legend.setBackgroundColor(HIColor.initWithHexValue("FFFFFF"));
        legend.setShadow(true);
        barGraphOptions.setLegend(legend);


        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setColumn(new HIColumn());
       // plotOptions.getColumn().setLineWidth(1);
        plotOptions.getColumn().setPointPadding(0);
        barGraphOptions.setPlotOptions(plotOptions);



       // growthLineChartHiOptions.setPlotOptions(linePlotOptions);
        //growthLineChartHiOptions.getChart().


        incomeExpenserBarChartView.setOptions(barGraphOptions);
        //growthLineHiChartView.setOptions(growthLineChartHiOptions);


    }

    public void drawCropsPie(int year,String season){


        HIOptions options = cropsIncomePieChartView.getOptions();

        HIChart chart = new HIChart();
        chart.setType("pie");
        chart.setBackgroundColor(null);
        chart.setPlotBorderWidth(0);
        chart.setPlotShadow(false);
        chart.setBackgroundColor(HIColor.initWithHexValue("B6C5B6"));
        options.setChart(chart);

        HICSSObject hicssObject = new HICSSObject();
        hicssObject.setColor("#4B6F4A");



        HITooltip tooltip = new HITooltip();
        tooltip.setPointFormat("{series.name}: <b>"+currency+"{point.y:.2f} ({point.percentage:.1f}%)</b>");
        options.setTooltip(tooltip);

        HIPlotOptions plotOptions = new HIPlotOptions();
        plotOptions.setPie(new HIPie());
        plotOptions.getPie().setDataLabels(new HIDataLabels());
        plotOptions.getPie().getDataLabels().setEnabled(false);
        plotOptions.getPie().getDataLabels().setDistance(-50);
        plotOptions.getPie().getDataLabels().setStyle(new HICSSObject());
        plotOptions.getPie().getDataLabels().getStyle().setFontWeight("bold");
        plotOptions.getPie().getDataLabels().getStyle().setColor("white");
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
        ArrayList<GraphRecord> incomeRecords = dbHandler.getGraphIncomesByCrop(year,season);


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
        title.setText("Total <br />"+"UGX "+ NumberFormat.getInstance().format(total));
        title.setAlign("center");
        title.setVerticalAlign("middle");
        title.setY(0);
        title.setStyle(hicssObject);
        //title.
        options.setTitle(title);

        HISubtitle subtitle = new HISubtitle();
        subtitle.setText("Income By Crop");
        subtitle.setStyle(hicssObject);
        options.setSubtitle(subtitle);
        options.setSeries(new ArrayList<HISeries>(Arrays.asList(series1)));

        cropsIncomePieChartView.setOptions(options);
    }

    public LinkedHashMap<String, Double> groupRecordByMonth(ArrayList<GraphRecord> graphRecords,String startDate,String endDate){
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        LinkedHashMap<String, Double> groups = new LinkedHashMap<>();
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar= Calendar.getInstance();
        String months ="";
        try {
            startCalendar.setTime(format.parse(startDate));
            endCalendar.setTime(format.parse(endDate));

            while(startCalendar.before(endCalendar)){
                String month = new SimpleDateFormat("MMM").format(startCalendar.getTime());
                groups.put(month,0.0);
                months+=" "+month;
                startCalendar.add(Calendar.MONTH,1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println(groups);
        System.out.println(months);

        Calendar calendar = Calendar.getInstance();
        for (GraphRecord record:graphRecords){
            try {
                calendar.setTime(format.parse(record.getDate()));
                String month = new SimpleDateFormat("MMM").format(calendar.getTime());
                if(groups.containsKey(month)){
                    groups.put(month,groups.get(month)+record.getAmount());
                }
                else{
                    groups.put(month,(double)record.getAmount());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return groups;
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
}
